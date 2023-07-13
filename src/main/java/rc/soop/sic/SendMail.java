/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic;

import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import static rc.soop.sic.Constant.LOGGER;
import static rc.soop.sic.Constant.sdf_PATTERNDATE5;
import static rc.soop.sic.Utils.estraiEccezione;
import rc.soop.sic.jpa.EntityOp;
import rc.soop.sic.jpa.Path;

/**
 *
 * @author Administrator
 */
public class SendMail {

    public static boolean inviaNotificaADMIN_presentazioneIstanza(EntityOp eo){
        
        
        
        return false;
//        return sendPec(eo, eo.getEm().find(Path.class, "pec.sender").getDescrizione(),
//                "EasyLearn", content, fileattach);
    }
    
    
    private static boolean sendPec(EntityOp eo, String dest, String subject, String content, String fileattach) {

        String host = eo.getEm().find(Path.class, "pec.smtp").getDescrizione();
        String port = eo.getEm().find(Path.class, "pec.port").getDescrizione();
        // Credenziali per l'autenticazione
        String username = eo.getEm().find(Path.class, "pec.username").getDescrizione();
        String password = eo.getEm().find(Path.class, "pec.password").getDescrizione();
        String sender = eo.getEm().find(Path.class, "pec.sender").getDescrizione();

        // Configurazione del server SMTP PEC
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.socketFactory.port", port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");

        // Creazione della sessione SMTP
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Creazione del messaggio email

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(dest));
            message.setSubject(subject);

            Multipart mp = new MimeMultipart();
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(content);
            mp.addBodyPart(mbp1);
            if (fileattach != null) {
                MimeBodyPart mbp2 = new MimeBodyPart();
                FileDataSource fds = new FileDataSource(fileattach);
                mbp2.setDataHandler(new DataHandler(fds));
                mbp2.setFileName(fds.getName());
                mp.addBodyPart(mbp2);
            }
            message.setContent(mp, "text/html; charset=utf-8");
            
            // Aggiunta del listener per la ricevuta di invio
            Transport transport = session.getTransport();
            transport.addTransportListener(new TransportListener() {
                @Override
                public void messageDelivered(TransportEvent transportEvent) {
                    // Informazioni sulla ricevuta di invio
                    Date sentDate = new Date();
                    Address[] recipients = transportEvent.getValidSentAddresses();

                    // Stampa della ricevuta di invio
                    LOGGER.info("------ Ricevuta di invio ------");
                    LOGGER.log(Level.INFO, "Data e ora di invio: {0}", sdf_PATTERNDATE5.format(sentDate));
                    try {
                        LOGGER.log(Level.INFO, "Mittente: {0}", message.getFrom()[0]);
                    } catch (Exception e) {
                    }
                    LOGGER.info("Destinatari: ");
                    for (Address recipient : recipients) {
                        LOGGER.info(recipient.toString());
                    }
                    LOGGER.info("--------------------------------");
                }

                @Override
                public void messageNotDelivered(TransportEvent transportEvent) {
                    // Informazioni sull'errore di invio
                    Address[] invalidRecipients = transportEvent.getInvalidAddresses();
                    Address[] validUnsentRecipients = transportEvent.getValidUnsentAddresses();

                    // Stampa delle informazioni sull'errore di invio
                    LOGGER.severe("------ Errore nell'invio dell'email ------");

                    LOGGER.severe("Destinatari non validi: ");
                    for (Address recipient : invalidRecipients) {
                        LOGGER.severe(recipient.toString());
                    }
                    LOGGER.severe("Destinatari validi a cui non è stata inviata l'email : ");
                    for (Address recipient : validUnsentRecipients) {
                        LOGGER.severe(recipient.toString());
                    }
                    LOGGER.severe("------------------------------------------");
                }

                @Override
                public void messagePartiallyDelivered(TransportEvent transportEvent) {
                    // Informazioni sugli indirizzi dei destinatari parzialmente inviati
                    Address[] validSentRecipients = transportEvent.getValidSentAddresses();
                    Address[] validUnsentRecipients = transportEvent.getValidUnsentAddresses();

                    // Stampa delle informazioni sugli indirizzi dei destinatari parzialmente inviati
                    LOGGER.warning("------ Invio parziale dell'email ------");

                    LOGGER.warning("Destinatari a cui è stata inviata correttamente: ");
                    for (Address recipient : validSentRecipients) {
                        LOGGER.warning(recipient.toString());
                    }
                    LOGGER.warning("Destinatari a cui non è stata inviata correttamente: ");
                    for (Address recipient : validUnsentRecipients) {
                        LOGGER.warning(recipient.toString());
                    }
                    LOGGER.warning("--------------------------------------");
                }
            });
            // Invio del messaggio email
            Transport.send(message);
            LOGGER.info("Email inviata con successo!");
            return true;
        } catch (Exception ex) {
            LOGGER.severe(estraiEccezione(ex));
        }
        return false;
    }
}
