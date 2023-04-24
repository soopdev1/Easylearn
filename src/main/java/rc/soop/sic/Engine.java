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

/**
 *
 * @author Raffaele
 */
public class Engine {

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

}
