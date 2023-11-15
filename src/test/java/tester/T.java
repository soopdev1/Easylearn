/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tester;

import java.math.BigDecimal;
import static java.math.BigDecimal.ROUND_HALF_DOWN;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import rc.soop.sic.Pdf;
import rc.soop.sic.SendMail;
import rc.soop.sic.Utils;
import rc.soop.sic.jpa.Corsoavviato;
import rc.soop.sic.jpa.EntityOp;
import rc.soop.sic.jpa.Istanza;

/**
 *
 * @author Raffaele
 */
public class T {

    public static void main(String[] args) {
        
        EntityOp ep1 = new EntityOp();
        Pdf.GENERANOMINAPRES(ep1, 
                ep1.getEm().find(Corsoavviato.class, 10L));
        
//        DateTime dt1 = new DateTime(2023, 1, 1, Utils.parseIntR("08"), Utils.parseIntR("30"));
//        DateTime dt2 = new DateTime(2023, 1, 1, Utils.parseIntR("17"), Utils.parseIntR("00"));
//        
//        System.out.println(Minutes.minutesBetween(dt1, dt2).getMinutes()/60.0);
        
//        EntityOp ep1 = new EntityOp();
//        String[] to = {"developers@smartoop.it"};
//        SendMail.sendPec(ep1, to, null, null, "TEST INVIO PEC PIATTAFORMA AUTORIZZAZIONE CORSI", "TEST INVIO PEC PIATTAFORMA AUTORIZZAZIONE CORSI", null);

//        Istanza is1 = ep1.getEm().find(Istanza.class, Long.valueOf("26"));
//
//        Pdf.GENERADECRETODDSFTO(ep1, is1);
//        double d = 1500.0;
//        
//        System.out.println("tester.T.main() "+Utils.roundDoubleandFormat(d, 2));
//        System.out.println("tester.T.main() "+String.format("%.2f", d));
//        
//        EntityOp eo = new EntityOp();
//        
//        SendMail.inviaNotificaADMIN_presentazioneIstanza(eo, eo.getEm().find(Istanza.class, 7L));
//        
//        double d = 23.599;
//        int scale = 2;
//               
//        EntityOp e = new EntityOp();
//        GENERADECRETOAPPROVATIVO(e.getEm().find(Istanza.class, 8L));
//        System.out.println("tester.T.main() " + checkFirmaQRpdfA("ISTANZA", "221211205027474SJYgJuJv14hz0QZ",
//                new File("D:\\SmartOOP\\RegioneSiciliaFormazione\\Template\\TMP\\ISTANZA_221211205027474SJYgJuJv14hz0QZ_221212223911748.IF_signed.pdf"),
//                "CSCRFL86E19C352O",
//                "20;0;60;60"));
//        
    }

}
