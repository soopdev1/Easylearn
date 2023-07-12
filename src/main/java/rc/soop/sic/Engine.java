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
import rc.soop.sic.jpa.Calendario_Formativo;
import rc.soop.sic.jpa.Certificazione;
import rc.soop.sic.jpa.Competenze_Trasversali;
import rc.soop.sic.jpa.Corso;
import rc.soop.sic.jpa.CorsoStato;
import rc.soop.sic.jpa.Docente;
import rc.soop.sic.jpa.EntityOp;
import static rc.soop.sic.jpa.EntityOp.trackingAction;
import rc.soop.sic.jpa.Lingua;
import rc.soop.sic.jpa.Livello_Certificazione;
import rc.soop.sic.jpa.Modacquisizione;
import rc.soop.sic.jpa.Moduli_Docenti;
import rc.soop.sic.jpa.Path;
import rc.soop.sic.jpa.Repertorio;
import rc.soop.sic.jpa.Scheda_Attivita;
import rc.soop.sic.jpa.Stati;
import rc.soop.sic.jpa.Tipologia_Percorso;

/**
 *
 * @author Raffaele
 */
public class Engine {

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

        } catch (Exception ex) {
            trackingAction(se.getAttribute("us_cod").toString(), estraiEccezione(ex));
        }

        return contatori;
    }

    public static String[] contatori_home_ADMIN(HttpSession se) {
        String[] contatori = {"0", "0", "0", "0"};

        try {

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

}
