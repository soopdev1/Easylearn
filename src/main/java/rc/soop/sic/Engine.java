/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic;

import java.util.List;
import javax.servlet.http.HttpSession;
import static rc.soop.sic.Utils.estraiEccezione;
import rc.soop.sic.jpa.Certificazione;
import rc.soop.sic.jpa.EntityOp;
import static rc.soop.sic.jpa.EntityOp.trackingAction;
import rc.soop.sic.jpa.Livello_Certificazione;
import rc.soop.sic.jpa.Path;
import rc.soop.sic.jpa.Repertorio;
import rc.soop.sic.jpa.Scheda_Attivita;

/**
 *
 * @author Raffaele
 */
public class Engine {

    public static List<Repertorio> repertorio_completo() {
        List<Repertorio> all = (List<Repertorio>) new EntityOp().findAll(Repertorio.class);
        return all;
    }

    public static List<Scheda_Attivita> repertorio_completo_scheda() {
        List<Scheda_Attivita> all = (List<Scheda_Attivita>) new EntityOp().findAll(Scheda_Attivita.class);
        return all;
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

    public static String getPath(String id) {

        try {
            Path res = new EntityOp().getEm().find(Path.class, id);
            return res.getDescrizione();
        } catch (Exception ex) {
            trackingAction("service", estraiEccezione(ex));
        }
        return "";
    }

}
