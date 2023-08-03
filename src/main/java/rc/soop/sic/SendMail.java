/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic;

import com.mailjet.client.ClientOptions;
import static com.mailjet.client.ClientOptions.builder;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import static com.mailjet.client.resource.Emailv31.MESSAGES;
import static com.mailjet.client.resource.Emailv31.Message.ATTACHMENTS;
import static com.mailjet.client.resource.Emailv31.Message.BCC;
import static com.mailjet.client.resource.Emailv31.Message.CC;
import static com.mailjet.client.resource.Emailv31.Message.FROM;
import static com.mailjet.client.resource.Emailv31.Message.HTMLPART;
import static com.mailjet.client.resource.Emailv31.Message.SUBJECT;
import static com.mailjet.client.resource.Emailv31.Message.TO;
import static com.mailjet.client.resource.Emailv31.resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import static java.nio.file.Files.probeContentType;
import java.util.Date;
import java.util.List;
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
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.IOUtils.toByteArray;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import static rc.soop.sic.Constant.LOGGER;
import static rc.soop.sic.Constant.RB;
import static rc.soop.sic.Constant.sdf_PATTERNDATE5;
import static rc.soop.sic.Utils.datemysqltoita;
import static rc.soop.sic.Utils.estraiEccezione;
import rc.soop.sic.jpa.EmailTemplate;
import rc.soop.sic.jpa.EntityOp;
import rc.soop.sic.jpa.Istanza;
import rc.soop.sic.jpa.Path;

/**
 *
 * @author Administrator
 */
public class SendMail {

    public static boolean inviaNotificaSP_rigettoIstanzaSOCCORSO(EntityOp eo, Istanza is1) {
        try {
            String DDS = is1.getDecreto().split("§")[0];
            String DDSDATA = datemysqltoita(is1.getDecreto().split("§")[1]);
            
            EmailTemplate et = eo.getEm().find(EmailTemplate.class, 4L);
            String name = "EasyLearn Notification Mail";
            String to[] = {is1.getSoggetto().getEMAIL()};
            String bcc[] = {eo.getEm().find(Path.class, "dest.support").getDescrizione()};

            String contenthtml = StringUtils.replace(et.getHtmlmail(), "@protocolloistanza", is1.getProtocolloreg());
            contenthtml = StringUtils.replace(contenthtml, "@dataprotocollo", is1.getProtocolloregdata());
            contenthtml = StringUtils.replace(contenthtml, "@idistanza", String.valueOf(is1.getIdistanza()));
            contenthtml = StringUtils.replace(contenthtml, "@codiceistanza", is1.getCodiceistanza());
            contenthtml = StringUtils.replace(contenthtml, "@version", Constant.VERSIONAPP);
            contenthtml = StringUtils.replace(contenthtml, "@DDSNUM", DDS);
            contenthtml = StringUtils.replace(contenthtml, "@DDSDATA", DDSDATA);

            return sendMail(name, to, null, bcc, contenthtml, et.getOggettomail(), null);

        } catch (Exception ex) {
            LOGGER.severe(estraiEccezione(ex));
        }
        return false;
    }
    public static boolean inviaNotificaSP_rigettoIstanzaSOCCORSO(EntityOp eo, Istanza is1,
            String motivazionerigetto, List<Long> idko) {

        try {
            EmailTemplate et = eo.getEm().find(EmailTemplate.class, 3L);
            String name = "EasyLearn Notification Mail";
            String to[] = {is1.getSoggetto().getEMAIL()};
            String bcc[] = {eo.getEm().find(Path.class, "dest.support").getDescrizione()};

            String contenthtml = StringUtils.replace(et.getHtmlmail(), "@protocolloistanza", is1.getProtocolloreg());
            contenthtml = StringUtils.replace(contenthtml, "@dataprotocollo", is1.getProtocolloregdata());
            contenthtml = StringUtils.replace(contenthtml, "@idistanza", String.valueOf(is1.getIdistanza()));
            contenthtml = StringUtils.replace(contenthtml, "@codiceistanza", is1.getCodiceistanza());
            contenthtml = StringUtils.replace(contenthtml, "@version", Constant.VERSIONAPP);
            contenthtml = StringUtils.replace(contenthtml, "@motivazionerigetto", motivazionerigetto);

            if (!idko.isEmpty()) {
                String eventualiallegati = "I seguenti ID identificano gli allegati che devono essere sostituiti:<br/>";
                for (Long ko1 : idko) {
                    eventualiallegati += String.valueOf(ko1) + "<br/>";
                }
                contenthtml = StringUtils.replace(contenthtml, "@eventualiallegati", eventualiallegati);
            }

            return sendMail(name, to, null, bcc, contenthtml, et.getOggettomail(), null);

        } catch (Exception ex) {
            LOGGER.severe(estraiEccezione(ex));
        }
        return false;
    }

    public static boolean inviaNotificaSP_rigettoIstanza(EntityOp eo, Istanza is1, String motivazionerigetto) {
        try {
            EmailTemplate et = eo.getEm().find(EmailTemplate.class, 2L);
            String name = "EasyLearn Notification Mail";
            String to[] = {is1.getSoggetto().getEMAIL()};
            String bcc[] = {eo.getEm().find(Path.class, "dest.support").getDescrizione()};

            String contenthtml = StringUtils.replace(et.getHtmlmail(), "@protocolloistanza", is1.getProtocolloreg());
            contenthtml = StringUtils.replace(contenthtml, "@dataprotocollo", is1.getProtocolloregdata());
            contenthtml = StringUtils.replace(contenthtml, "@idistanza", String.valueOf(is1.getIdistanza()));
            contenthtml = StringUtils.replace(contenthtml, "@codiceistanza", is1.getCodiceistanza());
            contenthtml = StringUtils.replace(contenthtml, "@version", Constant.VERSIONAPP);
            contenthtml = StringUtils.replace(contenthtml, "@motivazionerigetto", motivazionerigetto);

            return sendMail(name, to, null, bcc, contenthtml, et.getOggettomail(), null);

        } catch (Exception ex) {
            LOGGER.severe(estraiEccezione(ex));
        }
        return false;
    }

    public static boolean inviaNotificaADMIN_presentazioneIstanza(EntityOp eo, Istanza is1) {

        try {
            EmailTemplate et = eo.getEm().find(EmailTemplate.class, 1L);

            String name = "EasyLearn Notification Mail";

            String to[] = {eo.getEm().find(Path.class, "dest.nuovaistanza").getDescrizione()};

            String contenthtml = StringUtils.replace(et.getHtmlmail(), "@nomesoggetto", is1.getSoggetto().getRAGIONESOCIALE());
            contenthtml = StringUtils.replace(contenthtml, "@idistanza", String.valueOf(is1.getIdistanza()));
            contenthtml = StringUtils.replace(contenthtml, "@codiceistanza", is1.getCodiceistanza());
            contenthtml = StringUtils.replace(contenthtml, "@version", Constant.VERSIONAPP);

            return sendMail(name, to, null, null, contenthtml, et.getOggettomail() + is1.getSoggetto().getRAGIONESOCIALE(), null);

        } catch (Exception ex) {
            LOGGER.severe(estraiEccezione(ex));
        }

        return false;
//        return sendPec(eo, eo.getEm().find(Path.class, "pec.sender").getDescrizione(),
//                "EasyLearn", content, fileattach);
    }

    private static boolean sendMail(String name, String[] to, String[] cc, String[] ccn, String txt, String subject, File file) {
        try {
            MailjetClient client;
            MailjetRequest request;
            MailjetResponse response;

            String filename = "";
            String content_type = "";
            String b64 = "";

            String mailjet_api = RB.getString("mail.mj.api");
            String mailjet_secret = RB.getString("mail.mj.secret");
            String mailjet_name = RB.getString("mail.mj.name");

            ClientOptions options = builder()
                    .apiKey(mailjet_api)
                    .apiSecretKey(mailjet_secret)
                    .build();

            client = new MailjetClient(options);

            JSONArray dest = new JSONArray();
            JSONArray ccnj = new JSONArray();
            JSONArray ccj = new JSONArray();

            if (to != null) {
                for (String s : to) {
                    dest.put(new JSONObject().put("Email", s)
                            .put("Name", ""));
                }
            }

            if (cc != null) {
                for (String s : cc) {
                    ccj.put(new JSONObject().put("Email", s)
                            .put("Name", ""));
                }
            }

            if (ccn != null) {
                for (String s : ccn) {
                    ccnj.put(new JSONObject().put("Email", s)
                            .put("Name", ""));
                }
            }

            JSONObject mail = new JSONObject().put(FROM, new JSONObject()
                    .put("Email", mailjet_name)
                    .put("Name", name))
                    .put(TO, dest)
                    .put(CC, ccj)
                    .put(BCC, ccnj)
                    .put(SUBJECT, subject)
                    .put(HTMLPART, txt);

            if (file != null) {
                try {
                    filename = file.getName();
                    content_type = probeContentType(file.toPath());
                    try (InputStream i = new FileInputStream(file)) {
                        b64 = new String(encodeBase64(toByteArray(i)));
                    }
                    mail.put(ATTACHMENTS, new JSONArray()
                            .put(new JSONObject()
                                    .put("ContentType", content_type)
                                    .put("Filename", filename)
                                    .put("Base64Content", b64)));
                } catch (Exception ex1) {
                    LOGGER.severe(estraiEccezione(ex1));
                }
            }

            request = new MailjetRequest(resource)
                    .property(MESSAGES, new JSONArray()
                            .put(mail));

            response = client.post(request);

            boolean ok = response.getStatus() == 200;

            if (!ok) {
                LOGGER.log(Level.SEVERE, "sendMail - {0} -- {1} --- {2}", new Object[]{response.getStatus(), response.getRawResponseContent(), response.getData().toList()});
            }

            return ok;
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.severe(estraiEccezione(ex));
        }
        return false;
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
