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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import rc.soop.sic.jpa.Abilita;
import rc.soop.sic.jpa.Categoria_Repertorio;
import rc.soop.sic.jpa.Certificazione;
import rc.soop.sic.jpa.Competenze;
import rc.soop.sic.jpa.EntityOp;
import rc.soop.sic.jpa.Livello_Certificazione;
import rc.soop.sic.jpa.Repertorio;
import rc.soop.sic.jpa.Tipologia_Repertorio;

/**
 *
 * @author Administrator
 */
public class ReadExcel {

    public static void inserisci_repertorio(List<ExcelValues> l_v) {
        List<Repertorio> elenco = new ArrayList<>();

        EntityOp e = new EntityOp();
        e.begin();
        List<Integer> righe = l_v.stream().filter(c -> c.getFoglio() == 0).map(c1 -> c1.getRiga()).distinct().collect(Collectors.toList());

        List<Tipologia_Repertorio> all_tipologia = e.findAll(Tipologia_Repertorio.class);
        List<Categoria_Repertorio> all_cat = e.findAll(Categoria_Repertorio.class);
        List<Certificazione> all_cert = e.findAll(Certificazione.class);

        righe.forEach(f1 -> {

            List<ExcelValues> contentfoglio1 = l_v.stream().filter(c -> c.getFoglio() == 0 && c.getRiga() == f1).distinct().collect(Collectors.toList());

            if (f1 > 0) {
                Repertorio r1 = new Repertorio();
                r1.setProvaingresso("NON PREVISTA");
                contentfoglio1.forEach(f2 -> {
                    switch (f2.getColonna()) {
                        case 0: {
                            r1.setIdrepertorio(Long.valueOf(f2.getValue()));
                            break;
                        }
                        case 1: {
                            r1.setEdizione(f2.getValue());
                            break;
                        }
                        case 2: {
                            r1.setDenominazione(f2.getValue());
                            r1.setDescrizione(f2.getValue());
                            break;
                        }
                        case 3: {
                            try {
                                r1.setTipologiacategoria(all_cat.stream().filter(t1 -> t1.getNome().equalsIgnoreCase(f2.getValue())).findAny().get());
                            } catch (Exception ex1) {
                                System.out.println("NOT FOUND () " + f2.getValue());
                            }
                            break;
                        }
                        case 4: {
                            try {
                                r1.setLivelloeqf(e.getEm().find(Livello_Certificazione.class, "EHF" + Integer.valueOf(f2.getValue())));
                            } catch (Exception ex1) {
                                r1.setLivelloeqf(e.getEm().find(Livello_Certificazione.class, "0"));
                            }
                            break;
                        }
                        case 6: {
                            try {
                                r1.setQualificarilasciata(all_cert.stream().filter(t1 -> t1.getNome().equalsIgnoreCase(f2.getValue())).findAny().get());
                            } catch (Exception ex1) {
                                r1.setQualificarilasciata(e.getEm().find(Certificazione.class, "00"));
                            }
                            break;
                        }
                        case 7: {
                            try {
                                r1.setTipologia(all_tipologia.stream().filter(t1 -> t1.getNome().equalsIgnoreCase(f2.getValue())).findAny().get());
                            } catch (Exception ex1) {
                                System.out.println("NOT FOUND () " + f2.getValue());
                            }
                            break;
                        }
                        case 8: {
                            r1.setAreaprofessionale(f2.getValue());
                            break;
                        }
                        case 9: {
                            r1.setSottoareaprofessionale(f2.getValue());
                            break;
                        }
                        case 15: {
                            try {
                                r1.setDurataprovafinale(Integer.valueOf(f2.getValue()) + " ORE");
                            } catch (Exception ex1) {
                                r1.setDurataprovafinale("NON INDICATA");
                            }

                            break;
                        }
                        case 25: {
                            r1.setNormativarif(f2.getValue());
                            break;
                        }
                        default:
                            break;
                    }

                });
                elenco.add(r1);
//                    System.out.println(r1.toString());
//                    System.out.println("tester.ReadExcel.main( ) " + r1.getQualificarilasciata().getNome());
//                    System.out.println("----------------------------------------------------------------------------------");
            } else {
                contentfoglio1.forEach(f2 -> {
                    System.out.println("INDEX ) " + f2.toString());
                });
            }
        });

        List<Repertorio> elencofinale = elenco.stream().distinct().collect(Collectors.toList());

        System.out.println("tester.ReadExcel.main() " + elencofinale.size());

        elencofinale.forEach(r1 -> {
            e.persist(r1);
            System.out.println(r1.toString());
        });
        e.commit();
        e.close();

    }

    public static void main(String[] args) {
        try {
            List<ExcelValues> l_v = new ArrayList<>();
            String fileLocation = "C:\\mnt\\demo\\ESTRAZIONE_REPERTORIO_v2.xlsx";
            try (FileInputStream file = new FileInputStream(new File(fileLocation)); XSSFWorkbook workbook = new XSSFWorkbook(file)) {
//                System.out.println("tester.ReadExcel.main() " + workbook.getNumberOfSheets());
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

            //FOGLIO 1 - ELENCO REPERTORIO
//            inserisci_repertorio(l_v);
            EntityOp e = new EntityOp();
            e.begin();
            List<Integer> righe = l_v.stream().filter(c -> c.getFoglio() == 1).map(c1 -> c1.getRiga()).distinct().collect(Collectors.toList());

            List<Competenze> clist = new ArrayList<>();
            List<Abilita> alist = new ArrayList<>();

            righe.forEach(f1 -> {

                List<ExcelValues> contentfoglio1 = l_v.stream().filter(c -> c.getFoglio() == 1 && c.getRiga() == f1).distinct().collect(Collectors.toList());

                Competenze c1 = new Competenze();

                Abilita a1 = new Abilita();

                Repertorio r1 = new Repertorio();

                if (f1 > 0) {

//                Id_abilita	Descrizione_abilita
                    contentfoglio1.forEach(f2 -> {
                        System.out.println(f2.toString());
                        switch (f2.getColonna()) {
                            case 0: {
                                r1.setIdrepertorio(Long.valueOf(f2.getValue()));
                                break;
                            }
                            case 2: {
                                c1.setIdcompetenze(Long.valueOf(f2.getValue()));
                                break;
                            }
                            case 3: {
                                c1.setNumero(Integer.parseInt(f2.getValue()));
                                break;
                            }
                            case 4: {
                                c1.setDescrizione(f2.getValue());
                                break;
                            }
                            case 5: {
                                a1.setIdabilita(Long.valueOf(f2.getValue()));
                                break;
                            }
                            case 6: {
                                a1.setDescrizione(f2.getValue());
                                break;
                            }                            
                            default:
                                break;
                        }

                    });
                    
                    a1.setCompetenza(c1);
                    
                    
                    
                    
                }
            });

            e.close();
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
