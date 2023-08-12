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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import rc.soop.sic.Utils;
import rc.soop.sic.jpa.Docente;
import rc.soop.sic.jpa.EntityOp;

/**
 *
 * @author Administrator
 */
public class cf {

    public static void main(String[] args) {
        try {
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
                                    l_v.add(new ExcelValues(i, cell.getRowIndex(), cell.getColumnIndex(), String.valueOf(Double.valueOf(cell.getNumericCellValue()).intValue()).trim()));
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

//            EntityOp e = new EntityOp();
//            e.begin();
//            
            for (int i = 0; i < maxfogli.get(); i++) {
                AtomicInteger inti = new AtomicInteger(i);
                List<Integer> righe = l_v.stream().filter(c -> c.getFoglio() == inti.get()).map(c1 -> c1.getRiga()).distinct().collect(Collectors.toList());
                righe.forEach(f1 -> {
                    List<ExcelValues> contentfoglio1 = l_v.stream().filter(c -> c.getFoglio() == 0 && c.getRiga() == f1).distinct().collect(Collectors.toList());
                    if (f1 > 0) {
                        contentfoglio1.forEach(f2 -> {
                            System.out.println(inti.get()+") tester.cf.main() "+f2.toString());
                            switch (f2.getColonna()) {
                                case 0: {
                                }
                            }
                        });
                    }
                });
            }
//            e.commit();
//            e.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
