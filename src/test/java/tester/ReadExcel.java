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
import java.util.stream.Collectors;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Administrator
 */
public class ReadExcel {

    public static void main(String[] args) {
        try {

            List<ExcelValues> l_v = new ArrayList<>();
            String fileLocation = "C:\\mnt\\demo\\ESTRAZIONE_REPERTORIO.xlsx";
            try (FileInputStream file = new FileInputStream(new File(fileLocation)); XSSFWorkbook workbook = new XSSFWorkbook(file)) {
                System.out.println("tester.ReadExcel.main() " + workbook.getNumberOfSheets());
                for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                    XSSFSheet sheet = workbook.getSheetAt(i);
                    for (Row row : sheet) {
                        Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            switch (cell.getCellType()) {
                                case STRING: {
                                    l_v.add(new ExcelValues(i, cell.getRowIndex(), cell.getColumnIndex(), cell.getStringCellValue().trim()));
                                    break;
                                }
                                case NUMERIC: {
                                    l_v.add(new ExcelValues(i, cell.getRowIndex(), cell.getColumnIndex(), String.valueOf(cell.getNumericCellValue()).trim()));
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

            List<ExcelValues> foglio1_index = l_v.stream().filter(c -> c.getFoglio() == 0 && c.getRiga()== 0).collect(Collectors.toList());

            foglio1_index.forEach(f1 -> {
                System.out.println(f1.toString());
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

class ExcelValues {

    int foglio, riga, colonna;

    String value;

    public ExcelValues(int foglio, int riga, int colonna, String value) {
        this.foglio = foglio;
        this.riga = riga;
        this.colonna = colonna;
        this.value = value;
    }

    public int getFoglio() {
        return foglio;
    }

    public void setFoglio(int foglio) {
        this.foglio = foglio;
    }

    public int getRiga() {
        return riga;
    }

    public void setRiga(int riga) {
        this.riga = riga;
    }

    public int getColonna() {
        return colonna;
    }

    public void setColonna(int colonna) {
        this.colonna = colonna;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return org.apache.commons.lang3.builder.ReflectionToStringBuilder.toString(this);
    }

}
