/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tester;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import rc.soop.sic.Utils;
import static rc.soop.sic.Utils.parseIntR;
import rc.soop.sic.jpa.Abilita;
import rc.soop.sic.jpa.Categoria_Repertorio;
import rc.soop.sic.jpa.Certificazione;
import rc.soop.sic.jpa.Competenze;
import rc.soop.sic.jpa.Conoscenze;
import rc.soop.sic.jpa.Docente;
import rc.soop.sic.jpa.EntityOp;
import rc.soop.sic.jpa.Livello_Certificazione;
import rc.soop.sic.jpa.Professioni;
import rc.soop.sic.jpa.Repertorio;
import rc.soop.sic.jpa.Scheda_Attivita;
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

    public static void inserisci_conoscenze(List<ExcelValues> l_v) {
        EntityOp e = new EntityOp();
        e.begin();
        List<Integer> righe = l_v.stream().filter(c -> c.getFoglio() == 2).map(c1 -> c1.getRiga()).distinct().collect(Collectors.toList());

        List<Competenze> clist = new ArrayList<>();
        List<Conoscenze> colist = new ArrayList<>();

        righe.forEach(f1 -> {

            List<ExcelValues> contentfoglio1 = l_v.stream().filter(c -> c.getFoglio() == 2 && c.getRiga() == f1).distinct().collect(Collectors.toList());

            Competenze c1 = new Competenze();

            Conoscenze co1 = new Conoscenze();

            Repertorio r1 = new Repertorio();

            if (f1 > 0) {

                contentfoglio1.forEach(f2 -> {
//                        System.out.println(f2.toString());
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
                            co1.setIdconoscenze(Long.valueOf(f2.getValue()));
                            break;
                        }
                        case 6: {
                            co1.setDescrizione(f2.getValue());
                            break;
                        }
                        default:
                            break;
                    }

                });

                Repertorio rf = e.getEm().find(Repertorio.class, r1.getIdrepertorio());
                c1.setRepertorio(rf);
                clist.add(c1);
//
                co1.setCompetenza(c1);
                colist.add(co1);
            }
        });

        List<Competenze> clist_F = clist.stream().distinct().collect(Collectors.toList());
        List<Conoscenze> colist_F = colist.stream().distinct().collect(Collectors.toList());

        clist_F.forEach(c1 -> {
            if (e.getEm().find(Competenze.class, c1.getIdcompetenze()) == null) {
                e.persist(c1);
            }
        });

        colist_F.forEach(a1 -> {
            if (e.getEm().find(Conoscenze.class, a1.getIdconoscenze()) == null) {
                e.persist(a1);
            }
        });
        e.commit();
        e.close();
    }

    public static void inserisci_abilita(List<ExcelValues> l_v) {
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

                contentfoglio1.forEach(f2 -> {
//                        System.out.println(f2.toString());
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

                Repertorio rf = e.getEm().find(Repertorio.class, r1.getIdrepertorio());
                c1.setRepertorio(rf);
                clist.add(c1);

                a1.setCompetenza(c1);
                alist.add(a1);
            }
        });

        List<Competenze> clist_F = clist.stream().distinct().collect(Collectors.toList());
        List<Abilita> alist_F = alist.stream().distinct().collect(Collectors.toList());

        clist_F.forEach(c1 -> {
            if (e.getEm().find(Competenze.class, c1.getIdcompetenze()) == null) {
                e.persist(c1);
            }
        });

        alist_F.forEach(a1 -> {
            if (e.getEm().find(Abilita.class, a1.getIdabilita()) == null) {
                e.persist(a1);
            }
        });

        e.commit();
        e.close();
    }

    public static void elenco_professioni() {
        String fileLocation = "C:\\mnt\\demo\\CP2021.xlsx";
        List<ExcelValues> l_v = new ArrayList<>();

        AtomicInteger maxfogli = new AtomicInteger(0);
        try (FileInputStream file = new FileInputStream(new File(fileLocation)); XSSFWorkbook workbook = new XSSFWorkbook(file)) {
//                System.out.println("tester.ReadExcel.main() " + workbook.getNumberOfSheets());
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

            EntityOp eo = new EntityOp();
            eo.begin();

            AtomicInteger foglio = new AtomicInteger(0);

            while (foglio.get() < maxfogli.get()) {
                List<ExcelValues> perfoglio = l_v.stream().filter(v1 -> v1.getFoglio() == foglio.get()).collect(Collectors.toList());

                List<Integer> righe = perfoglio.stream().map(c1 -> c1.getRiga()).distinct().collect(Collectors.toList());

                righe.forEach(f1 -> {

                    List<ExcelValues> contentfoglio1 = perfoglio.stream().filter(c -> c.getRiga() == f1).distinct().collect(Collectors.toList());

                    if (f1 > 0) {

                        Professioni pr = new Professioni();

                        contentfoglio1.forEach(f2 -> {
                            switch (f2.getColonna()) {
                                case 0: {
                                    pr.setCodiceProfessioni(formatcodeNUP(f2.getValue().trim()));
                                    break;
                                }
                                case 1: {
                                    pr.setNome(f2.getValue().trim());
                                    break;
                                }
                                case 2: {
                                    pr.setDescrizione(f2.getValue().trim());
                                    break;
                                }
                                default:
                                    break;
                            }

                        });

                        if (pr.getCodiceProfessioni() != null && eo.getEm().find(Professioni.class, pr.getCodiceProfessioni()) == null) {
                            eo.persist(pr);
                        }

                    }

                });

                foglio.addAndGet(1);
            }

            eo.commit();
            eo.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void repertorio_e_professioni(List<ExcelValues> l_v) {
        try {
            EntityOp eo = new EntityOp();
            eo.begin();

            List<ExcelValues> perfoglio = l_v.stream().filter(v1 -> v1.getFoglio() == 3).collect(Collectors.toList());

            List<Integer> righe = perfoglio.stream().map(c1 -> c1.getRiga()).distinct().collect(Collectors.toList());

            righe.forEach(f1 -> {

                List<ExcelValues> contentfoglio1 = perfoglio.stream().filter(c -> c.getRiga() == f1).distinct().collect(Collectors.toList());

                if (f1 > 0) {

                    Repertorio r0 = new Repertorio();

                    Professioni p0 = new Professioni();

                    contentfoglio1.forEach(f2 -> {

//                        System.out.println(f2.toString());
                        switch (f2.getColonna()) {
                            case 0: {
                                r0.setIdrepertorio(Long.valueOf(f2.getValue().trim()));
                                break;
                            }
                            case 3: {
                                r0.setDescrizione(f2.getValue().trim());
                                break;
                            }
                            case 4: {
                                p0.setCodiceProfessioni(formatcodeNUP(f2.getValue().trim()));
                                break;
                            }
                        }
//                            case 0: {
//                                pr.setCodiceProfessioni(formatcodeNUP(f2.getValue().trim()));
//                                break;
//                            }
//                            case 1: {
//                                pr.setNome(f2.getValue().trim());
//                                break;
//                            }
//                            case 2: {
//                                pr.setDescrizione(f2.getValue().trim());
//                                break;
//                            }
//                            default:
//                                break;
//                        }

                    });

//                    System.out.println("tester.ReadExcel.main() "+r0.getIdrepertorio());
//                    System.out.println("tester.ReadExcel.main() "+r0.getDescrizione());
//                    System.out.println("tester.ReadExcel.main() "+p0.getCodiceProfessioni());
                    Repertorio r1 = eo.getEm().find(Repertorio.class, r0.getIdrepertorio());
                    if (r1 == null) {
                        System.out.println(r0.getIdrepertorio() + " REPERTORIO NON TROVATO");
                    } else {
                        Professioni p1 = eo.getEm().find(Professioni.class, p0.getCodiceProfessioni());
                        if (p1 == null) {
                            System.out.println(p0.getCodiceProfessioni() + " PROFESSIONE NON TROVATA");
                        } else if (!r1.getProfessioni().contains(p1)) {
                            List<Professioni> l1 = r1.getProfessioni();
                            l1.add(p1);
                            r1.setProfessioni(l1);
                            eo.merge(p1);
                        }
                    }
//                    if (pr.getCodiceProfessioni() != null && eo.getEm().find(Professioni.class, pr.getCodiceProfessioni()) == null) {
//                        eo.persist(pr);
//                    }
                }

            });

//            l_v.forEach(v1 -> {
//
//                Professioni pr = new Professioni();
//                switch (v1.getColonna()) {
//                    case 0: {
//                        pr.setCodiceProfessioni(formatcodeNUP(v1.getValue().trim()));
//                        break;
//                    }
//                    case 1: {
//                        pr.setNome(v1.getValue().trim());
//                        break;
//                    }
//                    case 2: {
//                        pr.setDescrizione(v1.getValue().trim());
//                        break;
//                    }
//                    default:
//                        break;
//                }
//
//                System.out.println("tester.ReadExcel.main() " + pr.getCodiceProfessioni());
//                System.out.println("tester.ReadExcel.main() " + pr.getNome());
//                System.out.println("tester.ReadExcel.main() " + pr.getDescrizione());
////                if (pr.getCodiceProfessioni()!= null && eo.getEm().find(Professioni.class, pr.getCodiceProfessioni()) == null) {
////                     eo.persist(pr);
////                }
//            });
            eo.commit();
            eo.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            List<ExcelValues> l_v = new ArrayList<>();
            String fileLocation = "C:\\mnt\\demo\\Estrazione S.ARF 25_07_2023.xlsx";
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

            //FOGLIO 1 - ELENCO REPERTORIO
//            inserisci_repertorio(l_v);
            //FOGLIO 2 - ELENCO ABILITA
//            inserisci_abilita(l_v);
            //FOGLIO 3 - ELENCO CONOSCENZE
            //inserisci_conoscenze(l_v);
            //FOGLIO 4 - ASSOCIA REPERTORIO CON PROFESSIONI
//            repertorio_e_professioni(l_v);
            EntityOp e = new EntityOp();
            e.begin();
            List<Integer> righe = l_v.stream().filter(c -> c.getFoglio() == 0).map(c1 -> c1.getRiga()).distinct().collect(Collectors.toList());
//
            righe.forEach(f1 -> {
//
                List<ExcelValues> contentfoglio1 = l_v.stream().filter(c -> c.getFoglio() == 0 && c.getRiga() == f1).distinct().collect(Collectors.toList());
//
                if (f1 > 0) {
//                    Repertorio r1 = new Repertorio();
//                    Scheda_Attivita sa1 = new Scheda_Attivita();
//
                    Docente do1 = new Docente();
                    StringBuilder sb_titst = new StringBuilder();

                    StringBuilder sb_areaf = new StringBuilder();

                    StringBuilder sb_profi = new StringBuilder();

                    contentfoglio1.forEach(f2 -> {
                        switch (f2.getColonna()) {
                            case 0: {
                                do1.setNome(f2.getValue().trim().toUpperCase());
                                break;
                            }
                            case 1: {
                                do1.setCognome(f2.getValue().trim().toUpperCase());
                                break;
                            }
                            case 2: {
                                do1.setCodicefiscale(f2.getValue().trim().toUpperCase());
                                break;
                            }
                            case 3:
                            case 4: {
                                sb_titst.append(f2.getValue().trim().toUpperCase()).append(" ");
                                break;
                            }
                            case 5: {
                                sb_areaf.append(f2.getValue().trim().toUpperCase());
                                break;
                            }
                            case 6: {
                                sb_profi.append(f2.getValue().trim().toUpperCase());
                                break;
                            }
                        }
                    });
                    do1.setTitolostudio(Utils.normalizeSP(sb_titst.toString().trim()));
                    if (sb_profi.toString().trim().equals("")) {
                        do1.setProfiloprof("FORMATORE");
                    } else {
                        do1.setProfiloprof(Utils.normalizeSP(sb_profi.toString()));
                    }

                    if (sb_areaf.toString().trim().equals("")) {
                        do1.setTipologia("AREA FUNZIONALE 3 - EROGAZIONE");
                    } else {
                        do1.setTipologia(Utils.normalizeSP(sb_areaf.toString().trim()));
                    }

                    if (do1.getTipologia().contains("EROGAZIONE")) {
                        e.persist(do1);
                    }

//
//                        switch (f2.getColonna()) {
//                            case 0: {
//                                r1.setIdrepertorio(Long.valueOf(f2.getValue().trim()));
//                                break;
//                            }
//                            case 10: {
//                                if (!f2.getValue().trim().equals("")) {
//                                    if (Long.parseLong(f2.getValue().trim()) > 0) {
//                                        sa1.setIdschedaattivita(Long.valueOf(f2.getValue().trim()));
//                                    }
//                                }
//                                break;
//                            }
//                            case 11: {
//                                if (!f2.getValue().trim().equals("")) {
//                                    sa1.setTipologiapercorso(f2.getValue().trim());
//                                }
//                                break;
//                            }
//                            case 12: {
//                                if (!f2.getValue().trim().equals("")) {
//                                    sa1.setTitoloattestato(f2.getValue().trim());
//                                    sa1.setTitolopercorso(f2.getValue().trim());
//                                }
//                                break;
//                            }
//                            case 13: {
//                                if (!f2.getValue().trim().equals("")) {
//                                    sa1.setCertificazioneuscita(e.getCertif(f2.getValue().trim()));
//                                }
//                                break;
//                            }
//                            case 14: {
//                                if (!f2.getValue().trim().equals("")) {
//                                    sa1.setTipologiaprovafinale(f2.getValue().trim());
//                                }
//                                break;
//                            }
//                            case 15: {
//                                if (!f2.getValue().trim().equals("")) {
//                                    sa1.setDurataprovafinale(parseIntR(f2.getValue().trim()));
//                                } else {
//                                    sa1.setDurataprovafinale(0);
//                                }
//                                break;
//                            }
//                            case 16: {
//                                if (!f2.getValue().trim().equals("")) {
//                                    sa1.setOrecorsomassime_num(parseIntR(f2.getValue().trim()));
//                                    sa1.setOrecorsominime_num(parseIntR(f2.getValue().trim()));
//                                } else {
//                                    sa1.setOrecorsomassime_num(0);
//                                    sa1.setOrecorsominime_num(0);
//                                }
//                                break;
//                            }
//                            case 17: {
//                                if (!f2.getValue().trim().equals("") && sa1.getOrecorsominime_num() == 0) {
//                                    sa1.setOrecorsominime_num(parseIntR(f2.getValue().trim()));
//                                }
//                                break;
//                            }
//                            case 18: {
//                                if (!f2.getValue().trim().equals("") && sa1.getOrecorsomassime_num() == 0) {
//                                    sa1.setOrecorsomassime_num(parseIntR(f2.getValue().trim()));
//                                }
//                                break;
//                            }
//                            case 19: {
//                                if (!f2.getValue().trim().equals("")) {
//                                    sa1.setOrestageminime_num(parseIntR(f2.getValue().trim()));
//                                } else {
//                                    sa1.setOrestageminime_num(0);
//                                }
//                                break;
//                            }
//                            case 20: {
//                                if (!f2.getValue().trim().equals("")) {
//                                    sa1.setOrestagemassime_num(parseIntR(f2.getValue().trim()));
//                                } else {
//                                    sa1.setOrestagemassime_num(0);
//                                }
//                                break;
//                            }
//                            case 22: {
//                                if (!f2.getValue().trim().equals("")) {
//                                    sa1.setOreelearningeminime_perc(parseIntR(f2.getValue().trim()));
//                                } else {
//                                    sa1.setOreelearningeminime_perc(0);
//                                }
//                                break;
//                            }
//                            case 23: {
//                                if (!f2.getValue().trim().equals("")) {
//                                    sa1.setOreelearningemassime_perc(parseIntR(f2.getValue().trim()));
//                                } else {
//                                    sa1.setOreelearningemassime_perc(0);
//                                }
//                                break;
//                            }
//                            case 24: {
//                                if (!f2.getValue().trim().equals("")) {
//                                    sa1.setOreassenzamassime_perc(parseIntR(f2.getValue().trim()));
//                                } else {
//                                    sa1.setOreassenzamassime_perc(0);
//                                }
//                                break;
//                            }
//                            case 25: {
//                                sa1.setNormativa(f2.getValue().trim());
//                                break;
//                            }
//                            case 26: {
//                                sa1.setPrerequisiti(f2.getValue().trim());
//                                break;
//                            }
//                            case 27: {
//                                sa1.setUlterioriindicazioni(f2.getValue().trim());
//                                break;
//                            }
//                            case 28: {
//                                if (!f2.getValue().trim().equals("")) {
//                                    sa1.setEtaminima(parseIntR(f2.getValue().trim()));
//                                } else {
//                                    sa1.setEtaminima(0);
//                                }
//                                break;
//                            }
//                            case 29: {
//                                sa1.setLivellominimoscolarita(f2.getValue().trim());
//                                break;
//                            }
//                            case 30: {
//                                sa1.setLivellomassimoscolarita(f2.getValue().trim());
//                                break;
//                            }
//                            default: {
//                                break;
//                            }
//
//                        }
//
//                    });
//
//                    Repertorio r2 = e.getEm().find(Repertorio.class, r1.getIdrepertorio());
//                    if (r2 == null) {
////                        System.out.println(r1.getIdrepertorio() + " REPERTORIO NON TROVATO");
//                    } else if (sa1.getIdschedaattivita() != null) {
//                        sa1.setRepertorio(r2);
//                        sa1.setTitolopercorso(r2.getDenominazione());
//                        e.persist(sa1);
////                        System.out.println(r1.getIdrepertorio() + " SCHEDA -> " + sa1.getIdschedaattivita());
//                    }
//
                }
            });
//
            e.commit();
            e.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static String formatcodeNUP(String ing) {
        try {
            ing = StringUtils.replace(ing, ",", "\\.");

            int occur = ing.length() - ing.replaceAll("[.!?]+", "").length();
            switch (occur) {
                case 0:
                    return ing + ".0.0.0.0";
                case 1:
                    return ing + ".0.0.0";
                case 2:
                    return ing + ".0.0";
                case 3:
                    return ing + ".0";
                default:
                    return ing;
            }
        } catch (Exception ex0) {
            ex0.printStackTrace();
        }
        return ing;
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
