/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tester;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.Period;
import rc.soop.sic.Constant;
import static rc.soop.sic.Constant.sdf_PATTERNDATE6;
import static rc.soop.sic.Constant.sdf_PATTERNDATE9;
import static rc.soop.sic.Utils.getRequestValue;
import rc.soop.sic.jpa.Calendario_Lezioni;
import rc.soop.sic.jpa.EntityOp;

/**
 *
 * @author Raffaele
 */
public class T {

    public static void main(String[] args) {

        try {

            
            String d1 = "2587/2§2022-08-01";
            
            
//            long lezione = 13L;
//
//            EntityOp ep1 = new EntityOp();
//            Calendario_Lezioni is1 = ep1.getEm().find(Calendario_Lezioni.class, lezione);
//            if (is1 != null) {
//                boolean ok = true;
//                List<Calendario_Lezioni> lezioni = ep1.calendario_lezioni_corso(is1.getCorsodiriferimento()).stream().filter(l1 -> l1.getStatolezione() != null
//                        && l1.getStatolezione().getCodicestatocorso().equals("61")).collect(Collectors.toList());
//                Interval tocheck = new Interval(new DateTime(sdf_PATTERNDATE9.parse(sdf_PATTERNDATE6.format(is1.getDatalezione()) + " " + is1.getOrainizio())),
//                        new DateTime(sdf_PATTERNDATE9.parse(sdf_PATTERNDATE6.format(is1.getDatalezione()) + " " + is1.getOrafine())));
//                for (Calendario_Lezioni conv : lezioni) {
//                    String datainizio = sdf_PATTERNDATE6.format(conv.getDatalezione()) + " " + conv.getOrainizio();
//                    String datafine = sdf_PATTERNDATE6.format(conv.getDatalezione()) + " " + conv.getOrafine();
//                    Interval p1 = new Interval(new DateTime(sdf_PATTERNDATE9.parse(datainizio)), new DateTime(sdf_PATTERNDATE9.parse(datafine)));
//                    if(tocheck.overlaps(p1)){
//                        ok = false;
//                        System.out.println("tester.T.main(SOVRAPPOSIZIONE)");
//                        break;
//                    }
//                }
//                
//                System.out.println("tester.T.main() "+ok);
//
//            }
//            String dataesame = "2018-06-12T19:30";
//            
//            String format = "yyyy-MM-dd'T'HH:mm";
//            DateTime dt1 = new DateTime(1986, 5, 19, 0, 0);
//            DateTime dt2 = new DateTime();
//            
//            
//            
//            System.out.println(Years.yearsBetween(dt1,dt2).getYears());
//            ObjectMapper om = new ObjectMapper();
//            om.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
////                om.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
////                om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//            List<EsamefinaleDetails> da_int = om.readValue(
//                    StringUtils.replace("[{\n"
//                            + "  \"IDALLIEVI\": 10,\n"
//                            + "  \"interno\": true,\n"
//                            + "  \"SESSO\": \"F\",\n"
//                            + "  \"AMMESSO\": \"SI\",\n"
//                            + "  \"AMMESSOFORZATO\": \"NO\",\n"
//                            + "  \"PRESENTE\": \"SI\",\n"
//                            + "  \"VOTOAMM\": \"50.00\",\n"
//                            + "  \"VOTOMEDIA\": \"50.00\",\n"
//                            + "  \"VOTOPSC\": \"50.00\",\n"
//                            + "  \"VOTOCOLL\": \"100.00\",\n"
//                            + "  \"VOTOPPR\": \"100.00\",\n"
//                            + "  \"VOTOFINALE\": \"80.00\",\n"
//                            + "  \"ESITO\": \"IDONEO\",\n"
//                            + "  \"eta\": 92\n"
//                            + "}{\n"
//                            + "  \"IDALLIEVI\": 1,\n"
//                            + "  \"interno\": true,\n"
//                            + "  \"SESSO\": \"M\",\n"
//                            + "  \"AMMESSO\": \"SI\",\n"
//                            + "  \"AMMESSOFORZATO\": \"NO\",\n"
//                            + "  \"PRESENTE\": \"SI\",\n"
//                            + "  \"VOTOAMM\": \"85.00\",\n"
//                            + "  \"VOTOMEDIA\": \"90.00\",\n"
//                            + "  \"VOTOPSC\": \"90.00\",\n"
//                            + "  \"VOTOCOLL\": \"80.00\",\n"
//                            + "  \"VOTOPPR\": \"50.00\",\n"
//                            + "  \"VOTOFINALE\": \"72.00\",\n"
//                            + "  \"ESITO\": \"IDONEO\",\n"
//                            + "  \"eta\": 37\n"
//                            + "}{\n"
//                            + "  \"IDALLIEVI\": 8,\n"
//                            + "  \"interno\": true,\n"
//                            + "  \"SESSO\": \"M\",\n"
//                            + "  \"AMMESSO\": \"SI\",\n"
//                            + "  \"AMMESSOFORZATO\": \"NO\",\n"
//                            + "  \"PRESENTE\": \"NO\",\n"
//                            + "  \"VOTOAMM\": \"100.00\",\n"
//                            + "  \"VOTOMEDIA\": \"90.00\",\n"
//                            + "  \"VOTOPSC\": \"0.00\",\n"
//                            + "  \"VOTOCOLL\": \"0.00\",\n"
//                            + "  \"VOTOPPR\": \"0.00\",\n"
//                            + "  \"VOTOFINALE\": \"0.00\",\n"
//                            + "  \"ESITO\": \"ASSENTE\",\n"
//                            + "  \"eta\": 27\n"
//                            + "}]", "}{", "},{"),
//                    new TypeReference<List<EsamefinaleDetails>>() {
//            });
//        EntityOp ep1 = new EntityOp();
//        Pdf.GENERAVERBALE(ep1, 
//                ep1.getEm().find(Corsoavviato.class, 10L));
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
