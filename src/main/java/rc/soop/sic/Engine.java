/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic;

import com.google.common.util.concurrent.AtomicDouble;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import static rc.soop.sic.Utils.estraiEccezione;
import static rc.soop.sic.Utils.fd;
import rc.soop.sic.jpa.Allievi;
import rc.soop.sic.jpa.Calendario_Formativo;
import rc.soop.sic.jpa.Calendario_Lezioni;
import rc.soop.sic.jpa.Certificazione;
import rc.soop.sic.jpa.CommissioneEsameSostituzione;
import rc.soop.sic.jpa.Competenze_Trasversali;
import rc.soop.sic.jpa.Corso;
import rc.soop.sic.jpa.CorsoStato;
import rc.soop.sic.jpa.Corsoavviato;
import rc.soop.sic.jpa.Docente;
import rc.soop.sic.jpa.EntityOp;
import static rc.soop.sic.jpa.EntityOp.trackingAction;
import rc.soop.sic.jpa.Istanza;
import rc.soop.sic.jpa.Lingua;
import rc.soop.sic.jpa.Livello_Certificazione;
import rc.soop.sic.jpa.Modacquisizione;
import rc.soop.sic.jpa.Moduli_Docenti;
import rc.soop.sic.jpa.Path;
import rc.soop.sic.jpa.Presenze_Lezioni;
import rc.soop.sic.jpa.Presenze_Lezioni_Allievi;
import rc.soop.sic.jpa.Presenze_Tirocinio_Allievi;
import rc.soop.sic.jpa.Repertorio;
import rc.soop.sic.jpa.Scheda_Attivita;
import rc.soop.sic.jpa.SoggettoProponente;
import rc.soop.sic.jpa.Stati;
import rc.soop.sic.jpa.Tipologia_Percorso;
import rc.soop.sic.jpa.TirocinioStage;
import rc.soop.sic.jpa.User;

/**
 *
 * @author Raffaele
 */
public class Engine {

    public static List<Tipologia_Percorso> tipo_percorso_attivi(EntityOp eo) {
        List<Tipologia_Percorso> result = (List<Tipologia_Percorso>) eo.findAll(Tipologia_Percorso.class);
        return result.stream().filter(p1 -> p1.getStatotipologiapercorso().equals(Stati.ATTIVO)).collect(Collectors.toList());
    }

    public static List<Tipologia_Percorso> tipo_percorso_attivi() {
        List<Tipologia_Percorso> result = (List<Tipologia_Percorso>) new EntityOp().findAll(Tipologia_Percorso.class);
        return result.stream().filter(p1 -> p1.getStatotipologiapercorso().equals(Stati.ATTIVO)).collect(Collectors.toList());
    }

    public static List<Repertorio> repertorio_completo() {
        List<Repertorio> all = (List<Repertorio>) new EntityOp().findAll(Repertorio.class);
        return all;
    }

    public static List<Scheda_Attivita> repertorio_completo_scheda() {
        return new EntityOp().repertorio_completo_scheda();
    }

    public static List<Competenze_Trasversali> elenco_competenze_trasversali() {
        List<Competenze_Trasversali> all = (List<Competenze_Trasversali>) new EntityOp().findAll(Competenze_Trasversali.class);
        return all;
    }

    public static List<Lingua> elenco_lingua() {
        return new EntityOp().getLingue();
    }

    public static List<Certificazione> elenco_certificazioni() {
        List<Certificazione> all = (List<Certificazione>) new EntityOp().findAll(Certificazione.class);
        return all;
    }

    public static List<Livello_Certificazione> elenco_livelli_certificazioni() {
        List<Livello_Certificazione> all = (List<Livello_Certificazione>) new EntityOp().findAll(Livello_Certificazione.class);
        return all;
    }

    public static String[] contatori_home_SA(HttpSession se) {
        String[] contatori = {"0", "0", "0", "0"};

        try {
            SoggettoProponente so = ((User) se.getAttribute("us_memory")).getSoggetto();
            EntityOp eo = new EntityOp();

            List<Istanza> all = eo.getIstanzeSoggetto(so);
            List<Corsoavviato> ca = eo.getCorsiAvviati_Soggetto(so);
            try {
                contatori[0] = String.valueOf(all.stream().filter(is1 -> is1.getStatocorso().getCodicestatocorso().equals("07")).collect(Collectors.toList()).size());
            } catch (Exception ex0) {
                trackingAction(se.getAttribute("us_cod").toString(), estraiEccezione(ex0));
            }
            try {
                contatori[1] = String.valueOf(all.stream().filter(is1 -> is1.getStatocorso().getCodicestatocorso().equals("08")).collect(Collectors.toList()).size());
            } catch (Exception ex0) {
                trackingAction(se.getAttribute("us_cod").toString(), estraiEccezione(ex0));
            }

            try {
                contatori[2] = String.valueOf(ca.stream().filter(is1 -> !is1.getStatocorso().getCodicestatocorso().equals("40")).collect(Collectors.toList()).size());
            } catch (Exception ex0) {
                trackingAction(se.getAttribute("us_cod").toString(), estraiEccezione(ex0));
            }

            try {
                contatori[3] = String.valueOf(ca.stream().filter(is1 -> (is1.getStatocorso().getCodicestatocorso().equals("44")
                        || is1.getStatocorso().getCodicestatocorso().equals("46"))).collect(Collectors.toList()).size());
            } catch (Exception ex0) {
                trackingAction(se.getAttribute("us_cod").toString(), estraiEccezione(ex0));
            }

        } catch (Exception ex) {
            trackingAction(se.getAttribute("us_cod").toString(), estraiEccezione(ex));
        }

        return contatori;
    }

    public static String[] contatori_home_ADMIN(HttpSession se) {
        String[] contatori = {"0", "0", "0", "0"};

        try {

            EntityOp eo = new EntityOp();

            List<Istanza> dagestire = eo.getIstanzedaGestire();
            List<Istanza> gestite = eo.getIstanzeGestite();

            try {
                contatori[0] = String.valueOf(dagestire.size());
                contatori[1] = String.valueOf(
                        gestite.stream().filter(i1 -> i1.getStatocorso().getCodicestatocorso().equals("08"))
                                .collect(Collectors.toList()).size());
            } catch (Exception ex1) {
                trackingAction(se.getAttribute("us_cod").toString(), estraiEccezione(ex1));
            }

            try {
                List<Corsoavviato> ca = eo.getCorsiAvviati_Admin();
                contatori[2] = String.valueOf(ca.size());
            } catch (Exception ex1) {
                trackingAction(se.getAttribute("us_cod").toString(), estraiEccezione(ex1));
            }
            //corsi avviati
        } catch (Exception ex) {
            trackingAction(se.getAttribute("us_cod").toString(), estraiEccezione(ex));
        }

        return contatori;
    }

    public static String getPath(String id) {
        try {
            Path res = new EntityOp().getEm().find(Path.class, id);
            return res.getDescrizione();
        } catch (Exception ex) {
            trackingAction("service", estraiEccezione(ex));
        }
        return "";
    }

    public static String getPath(String id, EntityOp eo) {
        try {
            Path res = eo.getEm().find(Path.class, id);
            return res.getDescrizione();
        } catch (Exception ex) {
            trackingAction("service", estraiEccezione(ex));
        }
        return "";
    }

    public static String getIdNewIstance(HttpSession se) {
        String out = Utils.generateIdentifier(50);
        try {
            if (se.getAttribute("ses.newinstancecode") != null) {
                out = se.getAttribute("ses.newinstancecode").toString();
            } else {
                se.setAttribute("ses.newinstancecode", out);
            }
        } catch (Exception ex) {
            trackingAction("service", estraiEccezione(ex));
        }
        return out;
    }

    public static boolean calendario_completo(EntityOp eo, Corso co1) {

        List<Calendario_Formativo> calendar = eo.calendario_formativo_corso_solomoduli(co1);

        AtomicDouble orepian = new AtomicDouble(0.0);
        calendar.forEach(c1 -> {
            orepian.addAndGet(Double.parseDouble(String.valueOf(c1.getOre())));
        });

        double dapian = fd(String.valueOf(co1.getDurataore())) - orepian.get();
        return dapian == 0.0;
    }

    public static int verificamoduliassegnati(List<Docente> assegnati) {
        try {
            List<Long> id = new ArrayList<>();
            assegnati.forEach(d1 -> {
                List<Moduli_Docenti> md = (d1.getElencomoduli() == null) ? new ArrayList<>() : d1.getElencomoduli();
                id.addAll(md.stream().map(m1 -> m1.getModuloformativo().getIdcalendarioformativo()).distinct().collect(Collectors.toList()));
            });
            return id.stream().distinct().collect(Collectors.toList()).size();
        } catch (Exception ex) {
            trackingAction("service", estraiEccezione(ex));
        }
        return 0;
    }

    public static void verificacorsodainviare(Corso co1, boolean calendariocompleto, int modulidaassegnare) {
        try {
            EntityOp eo = new EntityOp();
            if (calendariocompleto && modulidaassegnare == 0) {
                co1.setStatocorso(eo.getEm().find(CorsoStato.class, "21"));
            } else {
                co1.setStatocorso(eo.getEm().find(CorsoStato.class, "20"));
            }
            eo.begin();
            eo.merge(co1);
            eo.commit();
            eo.close();
        } catch (Exception ex) {
            trackingAction("service", estraiEccezione(ex));
        }
    }

    public static List<String> exportEnum(String name) {
        switch (name) {
            case "MA":
                return EnumSet.allOf(Modacquisizione.class).stream().map(e -> e.name()).collect(Collectors.toList());
            default:
                return new ArrayList<>();
        }
    }

    public static String letterecorsi(int numerocorso) {
        String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if (numerocorso >= 0 && numerocorso <= 25) {
            return String.valueOf(LETTERS.substring(numerocorso, numerocorso + 1).charAt(0));
        } else {
            return "?";
        }
    }

    public static boolean checkexist_CT(List<Calendario_Formativo> calendar, Competenze_Trasversali ct) {
        try {
            return calendar.stream().anyMatch(c3 -> c3.getCompetenzetrasversali().equals(ct));
        } catch (Exception ex) {
            trackingAction("service", estraiEccezione(ex));
            return false;
        }
    }

    public static Calendario_Formativo getexist_CT(List<Calendario_Formativo> calendar, Competenze_Trasversali ct) {
        try {
            return calendar.stream().filter(c3 -> c3.getCompetenzetrasversali().equals(ct)).findAny().get();
        } catch (Exception ex) {
            trackingAction("service", estraiEccezione(ex));
            return null;
        }
    }

    public static CommissioneEsameSostituzione isSostituito(List<CommissioneEsameSostituzione> sost, Docente d1) {
        try {
            return sost.stream().filter(s1 -> s1.getOriginale().getIddocente().equals(d1.getIddocente())).findAny().orElse(null);
        } catch (Exception ex) {
            trackingAction("service", estraiEccezione(ex));
            return null;
        }
    }

    public static double getOretotalipresenza(EntityOp eo, Allievi a1) {
        try {
            List<Calendario_Lezioni> lezioni = eo.calendario_lezioni_corso(a1.getCorsodiriferimento());
            long duratamillis = 0L;
            for (Calendario_Lezioni c1 : lezioni) {
                if (c1.getStatolezione() != null && c1.getStatolezione().getCodicestatocorso().equals("61")) { //SOLO CONVALIDATE
                    List<Presenze_Lezioni_Allievi> pa1 = eo.getpresenzelezioniGiornata(eo.getPresenzeLezione(c1));
                    for (Presenze_Lezioni_Allievi pla : pa1) {
                        if (pla.getAllievo().getIdallievi().equals(a1.getIdallievi())) {
                            duratamillis += pla.getDurata();
                        }
                    }
                }
            }
//            return Long.valueOf(duratamillis/3600000L).doubleValue();
        } catch (Exception ex) {
            trackingAction("service", estraiEccezione(ex));
        }
        return 40.00;
    }

    public static double getOretotalitirocinio(EntityOp eo, Allievi a1) {
        try {
            double total = 0.0;
            List<TirocinioStage> list_tirocini_allievo = eo.list_tirocini_allievo(a1);

            for (TirocinioStage ts1 : list_tirocini_allievo) {
                List<Presenze_Tirocinio_Allievi> lpr = eo.list_presenzetirocinio_allievo(ts1);

                String presenzeconvalid = Utils.countOreTirocinio(lpr, "61");
                total += fd(presenzeconvalid);
            }
//            return total;
        } catch (Exception ex) {
            trackingAction("service", estraiEccezione(ex));
        }
            return 100.00;

    }
}
