/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tester;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.CellType.BLANK;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import static rc.soop.sic.Constant.sdf_PATTERNDATE4;
import rc.soop.sic.Utils;
import rc.soop.sic.jpa.Allievi;
import rc.soop.sic.jpa.Docente;
import rc.soop.sic.jpa.EntityOp;
import rc.soop.sic.jpa.IstatCode;

/**
 *
 * @author Administrator
 */
public class cf {

    public static void main(String[] args) {
        Allievi al1 = new Allievi();
        al1.setCodicefiscale("CSCRFL86E19C352O");        
        Utils.ricavaDatidaCF(al1);
        al1 = new Allievi();
        al1.setCodicefiscale("MPRGRT90P68G273S");        
        Utils.ricavaDatidaCF(al1);
    }
    
    public static void main() {
        try {
            DateTime dtstart = new DateTime(1901, 1, 1, 0, 0);
            List<ExcelValues> l_v = new ArrayList<>();
            String fileLocation = "C:\\mnt\\demo\\cf1.xlsx";
            AtomicInteger maxfogli = new AtomicInteger(0);
            try (FileInputStream file = new FileInputStream(new File(fileLocation)); XSSFWorkbook workbook = new XSSFWorkbook(file)) {
                maxfogli.set(workbook.getNumberOfSheets());
                for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                    XSSFSheet sheet = workbook.getSheetAt(i);
                    for (Row row : sheet) {
                        Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            switch (cell.getCellType()) {
                                case STRING: {
                                    if (cell.getStringCellValue().trim().equals("\\N")) {
                                        l_v.add(new ExcelValues(i, cell.getRowIndex(), cell.getColumnIndex(), ""));
                                    } else {
                                        l_v.add(new ExcelValues(i, cell.getRowIndex(), cell.getColumnIndex(), cell.getStringCellValue().trim()));
                                    }
                                    break;
                                }
                                case NUMERIC: {

                                    if (DateUtil.isCellDateFormatted(cell)) {
                                        l_v.add(new ExcelValues(i, cell.getRowIndex(), cell.getColumnIndex(),
                                                sdf_PATTERNDATE4.format(cell.getDateCellValue())));
                                    } else {
                                        l_v.add(new ExcelValues(i, cell.getRowIndex(), cell.getColumnIndex(),
                                                String.valueOf(Double.valueOf(cell.getNumericCellValue()).intValue()).trim()));
                                    }
                                    break;
                                }
                                case BLANK: {
                                    l_v.add(new ExcelValues(i, cell.getRowIndex(), cell.getColumnIndex(), ""));
                                    break;
                                }
                                default: {
                                    l_v.add(new ExcelValues(i, cell.getRowIndex(), cell.getColumnIndex(), ""));
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            EntityOp e = new EntityOp();
            e.begin();
//            
            for (int i = 0; i < maxfogli.get(); i++) {
                AtomicInteger inti = new AtomicInteger(i);
                List<Integer> righe = l_v.stream().filter(c -> c.getFoglio() == inti.get()).map(c1 -> c1.getRiga()).distinct().collect(Collectors.toList());
                righe.forEach(f1 -> {
                    List<ExcelValues> contentfoglio1 = l_v.stream().filter(c -> c.getFoglio() == inti.get() && c.getRiga() == f1).distinct().collect(Collectors.toList());

                    IstatCode is1 = new IstatCode();

                    if (f1 > 0) {
                        contentfoglio1.forEach(f2 -> {
                            if (inti.get() == 0) {
                                switch (f2.getColonna()) {
                                    case 0: {
                                        is1.setIdistatcode(f2.getValue().trim());
                                        break;
                                    }
                                    case 1: {
                                        is1.setNome(f2.getValue().trim().toUpperCase());
                                        break;
                                    }
                                    case 2: {
                                        is1.setCodicecf(f2.getValue().trim().toUpperCase());
                                        break;
                                    }
                                }
                            } else {
//                                System.out.println("tester.cf.main() " + f2.toString());
                                switch (f2.getColonna()) {
                                    case 2: {
                                        is1.setIdistatcode(f2.getValue().trim());
                                        break;
                                    }
                                    case 0: {
                                        is1.setNome(f2.getValue().trim().toUpperCase());
                                        break;
                                    }
                                    case 1: {
                                        try {
                                            is1.setDatainizio(sdf_PATTERNDATE4.parse(f2.getValue().trim().toUpperCase()));
                                        } catch (Exception ex2) {
//                                            System.out.println("tester.cf.main() "+f2.getValue().trim().toUpperCase());
                                            is1.setDatainizio(null);
                                        }
                                        break;
                                    }
                                }
                            }
                        });

                        if (inti.get() == 0) {
//                        if (is1.getIdistatcode() != null) {
//                            is1.setDatainizio(dtstart.toDate());
//                            e.persist(is1);
//                        }
                        } else {
                            if (is1.getIdistatcode() != null) {
                                IstatCode oldest = e.getEm().find(IstatCode.class, is1.getIdistatcode());
                                if (oldest != null) {
                                    oldest.setDatainizio(is1.getDatainizio());
                                    e.merge(oldest);
                                    
                                    is1.setCodicecf(oldest.getCodicecf());
                                    is1.setDatainizio(dtstart.toDate());
                                    is1.setIdistatcode(oldest.getIdistatcode()+"_"+Utils.generaId(20));
                                    e.persist(is1);
//                                    System.out.println(is1.getDatainizio() + " () " + is1.getIdistatcode());
                                }
                            }
                        }
                    }

                });
            }
            e.commit();
            e.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
