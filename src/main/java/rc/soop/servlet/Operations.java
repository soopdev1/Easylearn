package rc.soop.servlet;

import com.google.common.util.concurrent.AtomicDouble;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import static java.lang.String.format;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.mime.MimeTypes;
import org.joda.time.DateTime;
import static rc.soop.sic.Constant.PATTERNDATE3;
import static rc.soop.sic.Constant.PATTERNDATE4;
import static rc.soop.sic.Constant.PATTERNDATE5;
import static rc.soop.sic.Constant.sdf_PATTERNDATE5;
import static rc.soop.sic.Constant.sdf_PATTERNDATE6;
import rc.soop.sic.Engine;
import rc.soop.sic.Pdf;
import rc.soop.sic.SendMail;
import rc.soop.sic.Utils;
import static rc.soop.sic.Utils.calcolaPercentuale;
import static rc.soop.sic.Utils.datemysqltoita;
import static rc.soop.sic.Utils.estraiEccezione;
import static rc.soop.sic.Utils.fd;
import static rc.soop.sic.Utils.formatDoubleforMysql;
import static rc.soop.sic.Utils.generaId;
import static rc.soop.sic.Utils.generateIdentifier;
import static rc.soop.sic.Utils.getMimeType;
import static rc.soop.sic.Utils.getRequestValue;
import static rc.soop.sic.Utils.isAdmin;
import static rc.soop.sic.Utils.normalize;
import static rc.soop.sic.Utils.normalizeUTF8;
import static rc.soop.sic.Utils.parseIntR;
import static rc.soop.sic.Utils.parseLongR;
import static rc.soop.sic.Utils.redirect;
import rc.soop.sic.jpa.Abilita;
import rc.soop.sic.jpa.Allegati;
import rc.soop.sic.jpa.Attrezzature;
import rc.soop.sic.jpa.Calendario_Formativo;
import rc.soop.sic.jpa.Competenze;
import rc.soop.sic.jpa.Competenze_Trasversali;
import rc.soop.sic.jpa.Conoscenze;
import rc.soop.sic.jpa.Corso;
import rc.soop.sic.jpa.CorsoStato;
import rc.soop.sic.jpa.Docente;
import rc.soop.sic.jpa.EntityOp;
import rc.soop.sic.jpa.IncrementalCorso;
import rc.soop.sic.jpa.Information;
import rc.soop.sic.jpa.Istanza;
import rc.soop.sic.jpa.Lingua;
import rc.soop.sic.jpa.Modacquisizione;
import rc.soop.sic.jpa.Moduli_Docenti;
import rc.soop.sic.jpa.Moduli_DocentiId;
import rc.soop.sic.jpa.Path;
import rc.soop.sic.jpa.Repertorio;
import rc.soop.sic.jpa.Scheda_Attivita;
import rc.soop.sic.jpa.Sede;
import rc.soop.sic.jpa.SoggettoProponente;
import rc.soop.sic.jpa.TipoCorso;
import rc.soop.sic.jpa.Tipologia_Percorso;
import rc.soop.sic.jpa.User;

/**
 *
 * @author Raffaele
 */
public class Operations extends HttpServlet {

    protected void INVIANOTIFICADECRETO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject resp_out = new JsonObject();
        if (isAdmin(request.getSession())) {
            try {
                String IDISTANZA = getRequestValue(request, "IDISTANZA");
                EntityOp ep1 = new EntityOp();
                Istanza is1 = ep1.getEm().find(Istanza.class, Long.valueOf(IDISTANZA));
                if (is1 != null) {

                    boolean sendmail = SendMail.inviaNotificaSP_rigettoIstanzaSOCCORSO(ep1, is1);
                    if (sendmail) {
                        resp_out.addProperty("result",
                                true);
                    } else {
                        resp_out.addProperty("result",
                                false);
                        resp_out.addProperty("message",
                                "Errore durante l'invio della mail. Riprovare in seguito.");
                    }
                } else {
                    resp_out.addProperty("result",
                            false);
                    resp_out.addProperty("message",
                            "Errore: ISTANZA NON TROVATA.");
                }
            } catch (Exception ex1) {
                resp_out.addProperty("result",
                        false);
                resp_out.addProperty("message",
                        "Errore: " + estraiEccezione(ex1));
                EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            }
        } else {
            resp_out.addProperty("result",
                    false);
            resp_out.addProperty("message",
                    "Errore: NON AUTORIZZATO.");
        }
        try (PrintWriter out = response.getWriter()) {
            out.print(resp_out.toString());
        }
    }

    protected void SCARICADECRETOFIRMATO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isAdmin(request.getSession())) {

            String idist = Utils.dec_string(getRequestValue(request, "idist"));

            EntityOp ep1 = new EntityOp();

            try {
                Istanza is1 = ep1.getEm().find(Istanza.class, Long.valueOf(idist));
                if (is1 != null) {

                    String mimeType = "application/pdf";

                    response.setContentType(mimeType);
                    String headerKey = "Content-Disposition";
                    String headerValue = format("attach; filename=\"%s\"",
                            Utils.generaId(50)
                            + MimeTypes.getDefaultMimeTypes().forName(mimeType).getExtension());
                    response.setHeader(headerKey, headerValue);
                    response.setContentLength(-1);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(Base64.decodeBase64(is1.getPathfirmato()));
                    }
                } else {
                    redirect(request, response, "404.jsp");
                }
            } catch (Exception ex1) {
                ex1.printStackTrace();
                EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
                redirect(request, response, "404.jsp");
            }
        } else {
            redirect(request, response, "404.jsp");
        }

    }

    protected void GENERADECRETOBASE(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isAdmin(request.getSession())) {

            String idist = Utils.dec_string(getRequestValue(request, "idist"));

            EntityOp ep1 = new EntityOp();

            try {
                Istanza is1 = ep1.getEm().find(Istanza.class, Long.valueOf(idist));
                if (is1 != null) {
                    File temp1 = Pdf.GENERADECRETOBASE(ep1, is1);

                    String mimeType = Files.probeContentType(temp1.toPath());
                    if (mimeType == null) {
                        mimeType = "application/pdf";
                    }
                    response.setContentType(mimeType);
                    String headerKey = "Content-Disposition";
                    String headerValue = format("attach; filename=\"%s\"", temp1.getName());
                    response.setHeader(headerKey, headerValue);
                    response.setContentLength(-1);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(FileUtils.readFileToByteArray(temp1));
                    }
                } else {
                    redirect(request, response, "404.jsp");
                }
            } catch (Exception ex1) {
                ex1.printStackTrace();
                EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
                redirect(request, response, "404.jsp");
            }
        } else {
            redirect(request, response, "404.jsp");
        }

    }

    protected void GENERADECRETODDSFTO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isAdmin(request.getSession())) {

            String idist = Utils.dec_string(getRequestValue(request, "idist"));

            EntityOp ep1 = new EntityOp();

            try {
                Istanza is1 = ep1.getEm().find(Istanza.class, Long.valueOf(idist));
                if (is1 != null) {
                    File temp1 = Pdf.GENERADECRETODDSFTO(ep1, is1);

                    String mimeType = Files.probeContentType(temp1.toPath());
                    if (mimeType == null) {
                        mimeType = "application/pdf";
                    }
                    response.setContentType(mimeType);
                    String headerKey = "Content-Disposition";
                    String headerValue = format("attach; filename=\"%s\"", temp1.getName());
                    response.setHeader(headerKey, headerValue);
                    response.setContentLength(-1);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(FileUtils.readFileToByteArray(temp1));
                    }
                } else {
                    redirect(request, response, "404.jsp");
                }
            } catch (Exception ex1) {
                ex1.printStackTrace();
                EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
                redirect(request, response, "404.jsp");
            }
        } else {
            redirect(request, response, "404.jsp");
        }

    }

    protected void APPROVAISTANZA(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isAdmin(request.getSession())) {

            String utentecaricamento = (String) request.getSession().getAttribute("us_cod");
            String idist = Utils.dec_string(getRequestValue(request, "idist"));

            EntityOp ep1 = new EntityOp();

            try {
                Istanza is1 = ep1.getEm().find(Istanza.class, Long.valueOf(idist));
                if (is1 != null) {
                    List<Corso> c1 = ep1.getCorsiIstanza(is1);
                    ep1.begin();

                    for (Corso c2 : c1) {
                        String corsoapproved = getRequestValue(request, "OK_" + c2.getIdcorso());
                        if (corsoapproved.equalsIgnoreCase("on")) {
                            c2.setStatocorso(ep1.getEm().find(CorsoStato.class, "24"));

                            if (is1.getTipologiapercorso().getTipocorso().equals(TipoCorso.FINANZIATO)) {
                                String CIP = getRequestValue(request, "CIP_" + c2.getIdcorso());
                                String CUP = getRequestValue(request, "CUP_" + c2.getIdcorso());
                                String ID = getRequestValue(request, "ID_" + c2.getIdcorso());
                                String CS = getRequestValue(request, "CS_" + c2.getIdcorso());
                                String ED = getRequestValue(request, "ED_" + c2.getIdcorso());
                                c2.setCip_corso(CIP);
                                c2.setCup_corso(CUP);
                                c2.setId_corso(ID);
                                c2.setCs_corso(CS);
                                c2.setEd_corso(ED);
                                c2.setIdentificativocorso(CIP + "/" + CUP + "/" + ID + "/" + CS + "/" + ED);
                            } else {
                                IncrementalCorso ics = new IncrementalCorso();
                                ics.setCorso(c2);
                                try {
                                    EntityOp ep2 = new EntityOp();
                                    ep2.begin();
                                    ep2.persist(ics);
                                    ep2.commit();
                                    ep2.close();
                                } catch (Exception ex2) {
                                    ex2.printStackTrace();
                                }
                                IncrementalCorso ics2 = ep1.getIC(c2);
                                c2.setIdentificativocorso(new DateTime().year().getAsText() + "/AUT/" + StringUtils.leftPad(String.valueOf(ics2.getIdincrementalcorso()), 4, "0"));
                            }
                        } else {
                            c2.setStatocorso(ep1.getEm().find(CorsoStato.class, "25"));
                        }
                        ep1.merge(c2);
                    }
                    is1.setStatocorso(ep1.getEm().find(CorsoStato.class, "08"));
                    is1.setDatagestione(sdf_PATTERNDATE5.format(new Date()));
                    ep1.merge(is1);
                    ep1.commit();
                    ep1.close();
                    redirect(request, response, "Page_message.jsp?esito=OKRI_IS1");
                } else {
                    redirect(request, response, "Page_message.jsp?esito=KORI_IS1");
                }
            } catch (Exception ex1) {
                ex1.printStackTrace();
                EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
                redirect(request, response, "Page_message.jsp?esito=KORI_IS2");
            }
        } else {
            redirect(request, response, "404.jsp");
        }
    }

    protected void PROTOCOLLAISTANZA(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isAdmin(request.getSession())) {

            String idist = Utils.dec_string(getRequestValue(request, "idist"));

            EntityOp ep1 = new EntityOp();
            try {
                Istanza is1 = ep1.getEm().find(Istanza.class, Long.valueOf(idist));
                if (is1 != null) {
                    String PROT = getRequestValue(request, "prot_num");
                    String DATAPROT = datemysqltoita(getRequestValue(request, "prot_dat"));
                    is1.setProtocolloreg(PROT);
                    is1.setProtocolloregdata(DATAPROT);
                    ep1.merge(is1);
                    ep1.commit();
                    ep1.close();
                    redirect(request, response, "Page_message.jsp?esito=OKRI_IS1");
                } else {
                    redirect(request, response, "Page_message.jsp?esito=KORI_IS1");
                }
            } catch (Exception ex1) {
                EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
                redirect(request, response, "Page_message.jsp?esito=KORI_IS2");
            }
        } else {
            redirect(request, response, "404.jsp");
        }
    }

    protected void RIGETTAISTANZA(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isAdmin(request.getSession())) {

            String utentecaricamento = (String) request.getSession().getAttribute("us_cod");
            String idist = Utils.dec_string(getRequestValue(request, "idist"));

            EntityOp ep1 = new EntityOp();

            try {
                Istanza is1 = ep1.getEm().find(Istanza.class, Long.valueOf(idist));
                if (is1 != null) {
                    boolean soccorsoistr = getRequestValue(request, "soccorsoistr").equalsIgnoreCase("on");
                    String motivazione = getRequestValue(request, "motivazione");
                    List<Allegati> la = ep1.list_allegati(is1, null, null, null, null);

                    List<Long> idko = new ArrayList<>();

                    for (Allegati a1 : la) {
                        String checkallegato = getRequestValue(request, "soc_ko_" + a1.getIdallegati());
                        if (checkallegato.equalsIgnoreCase("on")) {
                            idko.add(a1.getIdallegati());
                        }
                    }
                    ep1.begin();
                    if (soccorsoistr) {
                        for (Long idko1 : idko) {
                            Allegati a1 = ep1.getEm().find(Allegati.class, idko1);
                            if (a1 != null) {
                                a1.setStato(ep1.getEm().find(CorsoStato.class, "31"));
                                ep1.merge(a1);
                            }
                        }
                        is1.setStatocorso(ep1.getEm().find(CorsoStato.class, "10"));
                        //MAIL CON SOCCORSO
                        SendMail.inviaNotificaSP_rigettoIstanzaSOCCORSO(ep1, is1, motivazione, idko);
                    } else {
                        is1.setStatocorso(ep1.getEm().find(CorsoStato.class, "09"));
                        //MAIL SOLO RIGETTO
                        SendMail.inviaNotificaSP_rigettoIstanza(ep1, is1, motivazione);
                    }
                    ep1.merge(is1);

                    Information info1 = new Information();
                    info1.setDatacreazione(new Date());
                    info1.setIstanza(is1);
                    info1.setMotivazione(motivazione);
                    info1.setUtente(utentecaricamento);
                    ep1.persist(info1);
                    ep1.commit();
                    ep1.close();
                    redirect(request, response, "Page_message.jsp?esito=OKRI_IS1");
                } else {
                    redirect(request, response, "Page_message.jsp?esito=KORI_IS1");
                }
            } catch (Exception ex1) {
                EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
                redirect(request, response, "Page_message.jsp?esito=KORI_IS2");
            }
        } else {
            redirect(request, response, "404.jsp");
        }
    }

    protected void UPLSOST(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Utils.printRequest(request);
        if (isAdmin(request.getSession())) {

            try {

                EntityOp ep1 = new EntityOp();
                String utentecaricamento = (String) request.getSession().getAttribute("us_cod");
//            SoggettoProponente so = ((User) request.getSession().getAttribute("us_cod")).getSoggetto();
                Path pathtemp = ep1.getEm().find(Path.class, "path.temp");
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                File nomefile = null;
                List<FileItem> items = upload.parseRequest(request);
                Iterator<FileItem> iterator = items.iterator();
                String IDALLEGATO = null;
                String DESCRIZIONE = null;
                String MIME = null;
                String codiceDOC = generateIdentifier(6);
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    if (item.isFormField()) {
                        if (item.getFieldName().equals("idallegato")) {
                            IDALLEGATO = item.getString();
                        } else if (item.getFieldName().equals("DESCRIZIONE")) {
                            DESCRIZIONE = normalizeUTF8(normalize(item.getString()));
                        }
                    } else {
                        String fileName = item.getName();
                        String estensione = fileName.substring(fileName.lastIndexOf("."));
                        String nome = codiceDOC + new DateTime().toString(PATTERNDATE3)
                                + RandomStringUtils.randomAlphabetic(15) + estensione;
                        try {
                            nomefile = new File(pathtemp.getDescrizione() + nome);
                            item.write(nomefile);
                            MIME = getMimeType(nomefile);
                        } catch (Exception ex) {
                            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex));
                            nomefile = null;
                        }
                    }
                }

                if (nomefile != null) {

                    if (IDALLEGATO != null) {
                        request.getSession().setAttribute("ses_idalleg", IDALLEGATO);

                        Allegati original = ep1.getEm().find(Allegati.class, Long.valueOf(IDALLEGATO));
                        if (original != null) {

                            //CREO COPIA DI ALLEGATO PRECEDENTE "ELIMINATO"
                            Allegati al1 = new Allegati();
                            al1.setIstanza(original.getIstanza());
                            al1.setCodiceallegati(original.getCodiceallegati());
                            al1.setContent(original.getContent());
                            al1.setDescrizione(original.getDescrizione());
                            al1.setStato(ep1.getEm().find(CorsoStato.class, "32"));
                            al1.setMimetype(original.getMimetype());
                            al1.setUtentecaricamento(original.getUtentecaricamento());
                            al1.setDatacaricamento(original.getDatacaricamento());

                            ep1.begin();
                            ep1.persist(al1);

                            //AGGIORNO ALLEGATO ATTUALE
                            original.setContent(Base64.encodeBase64String(FileUtils.readFileToByteArray(nomefile)));
                            original.setDescrizione(DESCRIZIONE);
                            original.setStato(ep1.getEm().find(CorsoStato.class, "30"));
                            original.setMimetype(MIME);
                            original.setUtentecaricamento(utentecaricamento);
                            original.setDatacaricamento(new Date());
                            ep1.merge(original);
                            ep1.commit();
                            ep1.close();
                            redirect(request, response, "Page_message.jsp?esito=OK_UPAL");
                        } else {
                            redirect(request, response, "Page_message.jsp?esito=KOUP_IS1");
                        }
                    } else {
                        redirect(request, response, "Page_message.jsp?esito=KOUP_IS2");
                    }
                } else {
                    redirect(request, response, "Page_message.jsp?esito=KOUP_IS3");
                }

            } catch (Exception ex1) {
                ex1.printStackTrace();
                EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
                redirect(request, response, "Page_message.jsp?esito=KOUP_IS4");
            }
        } else {
            redirect(request, response, "404.jsp");
        }

    }

    protected void UPLDECAUT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isAdmin(request.getSession())) {

            try {

                EntityOp ep1 = new EntityOp();
                String utentecaricamento = (String) request.getSession().getAttribute("us_cod");
                Path pathtemp = ep1.getEm().find(Path.class, "path.temp");
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                File nomefile = null;
                List<FileItem> items = upload.parseRequest(request);
                Iterator<FileItem> iterator = items.iterator();
                String IDIST = null;
                String DDSNUMERO = null;
                String DDSDATA = null;
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    if (item.isFormField()) {
                        if (item.getFieldName().equals("idist")) {
                            IDIST = item.getString();
                        } else if (item.getFieldName().equals("DDSNUMERO")) {
                            DDSNUMERO = normalizeUTF8(normalize(item.getString()));
                        } else if (item.getFieldName().equals("DDSDATA")) {
                            DDSDATA = normalizeUTF8(normalize(item.getString()));
                        }
                    } else {
                        String fileName = item.getName();
                        String estensione = fileName.substring(fileName.lastIndexOf("."));
                        String nome = "DDUPL" + new DateTime().toString(PATTERNDATE3)
                                + RandomStringUtils.randomAlphabetic(15) + estensione;
                        try {
                            nomefile = new File(pathtemp.getDescrizione() + nome);
                            item.write(nomefile);
                        } catch (Exception ex) {
                            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex));
                            nomefile = null;
                        }
                    }
                }

                if (nomefile != null) {

                    if (IDIST != null) {
                        request.getSession().setAttribute("ses_idist", Utils.enc_string(IDIST));
                        Istanza is1 = ep1.getEm().find(Istanza.class, Long.valueOf(IDIST));
                        if (is1 != null) {
                            is1.setDecreto(DDSNUMERO + "§" + DDSDATA);
                            is1.setPathfirmato(Base64.encodeBase64String(FileUtils.readFileToByteArray(nomefile)));
                            ep1.begin();
                            ep1.merge(is1);
                            ep1.commit();
                            ep1.close();
                            redirect(request, response, "Page_message.jsp?esito=OK_UPAL");
                        } else {
                            redirect(request, response, "Page_message.jsp?esito=KOUP_IS1");
                        }
                    } else {
                        redirect(request, response, "Page_message.jsp?esito=KOUP_IS2");
                    }
                } else {
                    redirect(request, response, "Page_message.jsp?esito=KOUP_IS3");
                }

            } catch (Exception ex1) {
                ex1.printStackTrace();
                EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
                redirect(request, response, "Page_message.jsp?esito=KOUP_IS4");
            }
        } else {
            redirect(request, response, "404.jsp");
        }
    }

    protected void UPLGENERIC(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Utils.printRequest(request);
        if (isAdmin(request.getSession())) {

            try {

                EntityOp ep1 = new EntityOp();
                String utentecaricamento = (String) request.getSession().getAttribute("us_cod");
//            SoggettoProponente so = ((User) request.getSession().getAttribute("us_cod")).getSoggetto();
                Path pathtemp = ep1.getEm().find(Path.class, "path.temp");
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                File nomefile = null;
                List<FileItem> items = upload.parseRequest(request);
                Iterator<FileItem> iterator = items.iterator();
                String IDIST = null;
                String DESCRIZIONE = null;
                String MIME = null;
                String codiceDOC = generateIdentifier(6);
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    if (item.isFormField()) {
                        if (item.getFieldName().equals("idist")) {
                            IDIST = item.getString();
                        } else if (item.getFieldName().equals("DESCRIZIONE")) {
                            DESCRIZIONE = normalizeUTF8(normalize(item.getString()));
                        }
                    } else {
                        String fileName = item.getName();
                        String estensione = fileName.substring(fileName.lastIndexOf("."));
                        String nome = codiceDOC + new DateTime().toString(PATTERNDATE3)
                                + RandomStringUtils.randomAlphabetic(15) + estensione;
                        try {
                            nomefile = new File(pathtemp.getDescrizione() + nome);
                            item.write(nomefile);
                            MIME = getMimeType(nomefile);
                        } catch (Exception ex) {
                            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex));
                            nomefile = null;
                        }
                    }
                }

                if (nomefile != null) {

                    if (IDIST != null) {
                        request.getSession().setAttribute("ses_idist", Utils.enc_string(IDIST));
                        Istanza is1 = ep1.getEm().find(Istanza.class, Long.valueOf(IDIST));
                        if (is1 != null) {

                            Allegati al1 = new Allegati();
                            al1.setIstanza(is1);
                            al1.setCodiceallegati(codiceDOC);
                            al1.setContent(Base64.encodeBase64String(FileUtils.readFileToByteArray(nomefile)));
                            al1.setDescrizione(DESCRIZIONE);
                            al1.setStato(ep1.getEm().find(CorsoStato.class, "30"));
                            al1.setMimetype(MIME);
                            al1.setUtentecaricamento(utentecaricamento);
                            al1.setDatacaricamento(new Date());

                            ep1.begin();
                            ep1.persist(al1);
                            ep1.commit();
                            ep1.close();
                            redirect(request, response, "US_gestioneallegati.jsp");
                        } else {
                            redirect(request, response, "Page_message.jsp?esito=KOUP_IS1");
                        }
                    } else {
                        redirect(request, response, "Page_message.jsp?esito=KOUP_IS2");
                    }
                } else {
                    redirect(request, response, "Page_message.jsp?esito=KOUP_IS3");
                }

            } catch (Exception ex1) {
                ex1.printStackTrace();
                EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
                redirect(request, response, "Page_message.jsp?esito=KOUP_IS4");
            }
        } else {
            redirect(request, response, "404.jsp");
        }

    }

    protected void VISUALDOC(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            Long iddoc = Long.valueOf(getRequestValue(request, "iddocument"));
            EntityOp eo = new EntityOp();

            Allegati a1 = eo.getEm().find(Allegati.class, iddoc);
            if (a1 != null) {

                response.setContentType(a1.getMimetype());
                String headerKey = "Content-Disposition";
                String headerValue = format("attach; filename=\"%s\"",
                        Utils.generaId(50)
                        + MimeTypes.getDefaultMimeTypes().forName(a1.getMimetype()).getExtension());
                response.setHeader(headerKey, headerValue);
                response.setContentLength(-1);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(Base64.decodeBase64(a1.getContent()));
                }

            }

        } catch (Exception ex1) {

        }
    }

    protected void SENDISTANZA(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject resp_out = new JsonObject();
        try {
            EntityOp ep1 = new EntityOp();
            Istanza is1 = ep1.getEm().find(Istanza.class, Long.valueOf(getRequestValue(request, "IDISTANZA")));
            if (is1 != null) {
                List<Docente> eldoc = ep1.findAll(Docente.class);
                List<Corso> c1 = new EntityOp().getCorsiIstanza(is1);
                for (Corso c2 : c1) {
                    boolean calendariocompleto = Engine.calendario_completo(ep1, c2);
                    List<Calendario_Formativo> calendar = ep1.calendario_formativo_corso_lezioni(c2);
                    List<Docente> assegnati = ep1.list_docenti_moduli(eldoc, calendar);
                    int moduliassegnati = Engine.verificamoduliassegnati(assegnati);
                    int modulidaassegnare = calendar.size() - moduliassegnati;
                    if (calendariocompleto && modulidaassegnare == 0) {
                    } else {
                        resp_out.addProperty("result",
                                false);
                        resp_out.addProperty("message",
                                "Errore: UNO O PI&#217; CORSI NON SONO COMPLETI. IMPOSSIBILE PRESENTARE ISTANZA.");
                    }
                }
                if (resp_out.isEmpty()) {
                    ep1.begin();
                    is1.setStatocorso(ep1.getEm().find(CorsoStato.class, "07"));
                    is1.setDatainvio(new DateTime().toString(PATTERNDATE5));
                    ep1.merge(is1);
                    //INVIO PEC
                    try {
                        SendMail.inviaNotificaADMIN_presentazioneIstanza(ep1, is1);
                    } catch (Exception ex2) {
                        EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex2));
                    }
                    ep1.commit();
                    ep1.close();
                    resp_out.addProperty("result",
                            true);

                }
            } else {
                resp_out.addProperty("result",
                        false);
                resp_out.addProperty("message",
                        "Errore: ISTANZA NON TROVATA.");
            }

        } catch (Exception ex1) {
            resp_out.addProperty("result",
                    false);
            resp_out.addProperty("message",
                    "Errore: " + estraiEccezione(ex1));
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(resp_out.toString());
        }
    }

    protected void SAVEISTANZA(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject resp_out = new JsonObject();
        try {
            EntityOp ep1 = new EntityOp();
            Istanza is1 = ep1.getEm().find(Istanza.class, Long.valueOf(getRequestValue(request, "IDISTANZA")));

            if (is1 != null) {

                List<Docente> eldoc = ep1.findAll(Docente.class);
                List<Corso> c1 = new EntityOp().getCorsiIstanza(is1);
                for (Corso c2 : c1) {
                    boolean calendariocompleto = Engine.calendario_completo(ep1, c2);
                    List<Calendario_Formativo> calendar = ep1.calendario_formativo_corso_lezioni(c2);
                    List<Docente> assegnati = ep1.list_docenti_moduli(eldoc, calendar);
                    int moduliassegnati = Engine.verificamoduliassegnati(assegnati);
                    int modulidaassegnare = calendar.size() - moduliassegnati;
                    if (calendariocompleto && modulidaassegnare == 0) {
                    } else {
                        resp_out.addProperty("result",
                                false);
                        resp_out.addProperty("message",
                                "Errore: UNO O PI&#217; CORSI NON SONO COMPLETI. IMPOSSIBILE SALVARE ISTANZA SENZA AVERLI COMPLETATI.");
                    }
                }
                if (resp_out.isEmpty()) {
                    ep1.begin();
                    is1.setStatocorso(ep1.getEm().find(CorsoStato.class, "02"));
                    ep1.merge(is1);
                    ep1.commit();
                    ep1.close();
                    resp_out.addProperty("result",
                            true);
                }
            } else {
                resp_out.addProperty("result",
                        false);
                resp_out.addProperty("message",
                        "Errore: ISTANZA NON TROVATA.");
            }

        } catch (Exception ex1) {
            resp_out.addProperty("result",
                    false);
            resp_out.addProperty("message",
                    "Errore: " + estraiEccezione(ex1));
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(resp_out.toString());
        }
    }

    protected void DELETEDOCUMENT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject resp_out = new JsonObject();
        try {

            EntityOp ep1 = new EntityOp();
            Allegati al1 = ep1.getEm().find(Allegati.class, Long.valueOf(getRequestValue(request, "IDDOCUMENT")));

            if (al1 != null) {
                ep1.begin();
                al1.setStato(ep1.getEm().find(CorsoStato.class, "32"));
                ep1.merge(al1);
                ep1.commit();
                ep1.close();
                resp_out.addProperty("result",
                        true);
            } else {
                resp_out.addProperty("result",
                        false);
                resp_out.addProperty("message",
                        "Errore: ALLEGATO NON TROVATO.");
            }

        } catch (Exception ex1) {
            resp_out.addProperty("result",
                    false);
            resp_out.addProperty("message",
                    "Errore: " + estraiEccezione(ex1));
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(resp_out.toString());
        }

    }

    protected void DELETEISTANZA(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject resp_out = new JsonObject();

        try {

            EntityOp ep1 = new EntityOp();
            Istanza is1 = ep1.getEm().find(Istanza.class, Long.valueOf(getRequestValue(request, "IDISTANZA")));

            if (is1 != null) {
                ep1.begin();
                ep1.remove(is1);
                ep1.commit();
                ep1.close();
                resp_out.addProperty("result",
                        true);
            } else {
                resp_out.addProperty("result",
                        false);
                resp_out.addProperty("message",
                        "Errore: ISTANZA NON TROVATA.");
            }

        } catch (Exception ex1) {
            resp_out.addProperty("result",
                    false);
            resp_out.addProperty("message",
                    "Errore: " + estraiEccezione(ex1));
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(resp_out.toString());
        }
    }

    protected void DELETECORSOISTANZA(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JsonObject resp_out = new JsonObject();
        try {

            EntityOp ep1 = new EntityOp();
            Corso co1 = ep1.getEm().find(Corso.class, Long.valueOf(getRequestValue(request, "IDOCORSO")));

            if (co1 != null) {
                ep1.begin();
                ep1.remove(co1);
                ep1.commit();
                ep1.close();
                resp_out.addProperty("result",
                        true);
            } else {
                resp_out.addProperty("result",
                        false);
                resp_out.addProperty("message",
                        "Errore: CORSO NON TROVATO.");
            }

        } catch (Exception ex1) {
            resp_out.addProperty("result",
                    false);
            resp_out.addProperty("message",
                    "Errore: " + estraiEccezione(ex1));
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(resp_out.toString());
        }
    }

    protected void MODIFICAPIANIFICAZIONE(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            EntityOp ep1 = new EntityOp();
            Corso co1 = ep1.getEm().find(Corso.class, Long.valueOf(getRequestValue(request, "idcorsodasalvare")));

            if (co1 != null) {

                String NOMEMODULO = getRequestValue(request, "NOMEMODULO");
                double ORETOTALI = fd(formatDoubleforMysql(getRequestValue(request, "ORETOTALI")));
                double OREELE = fd(formatDoubleforMysql(getRequestValue(request, "OREELE")));
                double OREAULATEO = fd(formatDoubleforMysql(getRequestValue(request, "OREAULATEO")));
                double OREAULATEC = fd(formatDoubleforMysql(getRequestValue(request, "OREAULATEC")));

                List<Competenze> comp = ep1.competenze_correlate(co1.getRepertorio());

                List<Competenze> list_comp = new ArrayList<>();
                List<Abilita> list_abil = new ArrayList<>();
                List<Conoscenze> list_cono = new ArrayList<>();

                comp.forEach(c1 -> {
                    for (Abilita ab : c1.getAbilita()) {
                        String abnamecheck = "AB_" + ab.getIdabilita() + "_" + c1.getIdcompetenze();
                        String req1 = Utils.getRequestValue(request, abnamecheck);
                        if (req1 != null
                                && req1.equalsIgnoreCase("on")) {
                            if (!list_comp.contains(c1)) {
                                list_comp.add(c1);
                            }
                            if (!list_abil.contains(ab)) {
                                list_abil.add(ab);
                            }
                        }
                    }

                    for (Conoscenze co : c1.getConoscenze()) {
                        String conamecheck = "CO_" + co.getIdconoscenze() + "_" + c1.getIdcompetenze();
                        String req1 = Utils.getRequestValue(request, conamecheck);
                        if (req1 != null
                                && req1.equalsIgnoreCase("on")) {
                            if (!list_comp.contains(c1)) {
                                list_comp.add(c1);
                            }
                            if (!list_cono.contains(co)) {
                                list_cono.add(co);
                            }
                        }
                    }

                });

                List<Calendario_Formativo> calendar = ep1.calendario_formativo_corso(co1);

                Calendario_Formativo ct = new Calendario_Formativo();
                ct.setElencocompetenze(list_comp);
                ct.setElencoconoscenze(list_cono);
                ct.setElencoabilita(list_abil);
                ct.setCodicemodulo("MOD" + (calendar.stream().filter(c1 -> c1.getCodicemodulo().startsWith("MOD")).collect(Collectors.toList()).size() + 1));
                ct.setCorsodiriferimento(co1);
                ct.setNomemodulo(NOMEMODULO);
                ct.setOre(ORETOTALI);
                ct.setOre_aula(OREAULATEC + OREAULATEO);
                ct.setOre_teorica_aula(OREAULATEC);
                ct.setOre_tecnica_operativa(OREAULATEO);
                ct.setOre_teorica_el(OREELE);
                ct.setTipomodulo("MODULOFORMATIVO");
                ep1.begin();
                ep1.persist(ct);
                ep1.commit();
                ep1.close();
                redirect(request, response, "Page_message.jsp?esito=OK_SM");
            } else {
                request.getSession().setAttribute("ses_idcorso", Utils.enc_string(getRequestValue(request, "idcorsodasalvare")));
                redirect(request, response, "US_compilacorsi.jsp?esito=KO1");
            }
        } catch (Exception ex1) {
            ex1.printStackTrace();
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            request.getSession().setAttribute("ses_idcorso", Utils.enc_string(getRequestValue(request, "idcorsodasalvare")));
            redirect(request, response, "US_compilacorsi.jsp?esito=KO2");
        }

    }

    protected void CHECKMODULO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JsonObject resp_out = new JsonObject();

        EntityOp ep1 = new EntityOp();
        Corso co1 = ep1.getEm().find(Corso.class, Long.valueOf(getRequestValue(request, "IDOCORSO")));

        if (co1 != null) {

            double mx_oreteo = Utils.calcolaPercentuale(String.valueOf(co1.getDurataore()), String.valueOf(60));
            double mx_oretec = Utils.calcolaPercentuale(String.valueOf(co1.getDurataore()), String.valueOf(40));

            double oremaxlearn = calcolaPercentuale(String.valueOf(co1.getDurataore()), String.valueOf(co1.getElearningperc()));

            double ORETOTALI = fd(formatDoubleforMysql(Utils.getRequestValue(request, "ORETOTALI")));
            double OREELE = fd(formatDoubleforMysql(Utils.getRequestValue(request, "OREELE")));

            double OREAULATEO = fd(formatDoubleforMysql(Utils.getRequestValue(request, "OREAULATEO")));
            double OREAULATEC = fd(formatDoubleforMysql(Utils.getRequestValue(request, "OREAULATEC")));

            List<Calendario_Formativo> calendar = ep1.calendario_formativo_corso_solomoduli(co1);
            AtomicDouble orepian = new AtomicDouble(0.0);
            AtomicDouble orelearning = new AtomicDouble(0.0);

            AtomicDouble ore_pteo = new AtomicDouble(0.0);
            AtomicDouble ore_ptec = new AtomicDouble(0.0);

            calendar.forEach(c1 -> {
                orepian.addAndGet(Double.parseDouble(String.valueOf(c1.getOre())));
                orelearning.addAndGet(Double.parseDouble(String.valueOf(c1.getOre_teorica_el())));
                ore_pteo.addAndGet(Double.parseDouble(String.valueOf(c1.getOre_teorica_aula())));
                ore_ptec.addAndGet(Double.parseDouble(String.valueOf(c1.getOre_tecnica_operativa())));
            });

            double dapian = fd(String.valueOf(co1.getDurataore())) - orepian.get();
            double dapianEL = oremaxlearn - orelearning.get();

            boolean check_oretotali = orepian.get() + ORETOTALI <= fd(String.valueOf(co1.getDurataore()));
            boolean check_elearning = orelearning.get() + OREELE <= oremaxlearn;
            boolean check_sum = (ORETOTALI - OREAULATEO - OREAULATEC - OREELE) == 0;

            boolean check_teoria = ore_pteo.get() + OREAULATEO <= mx_oreteo;
            boolean check_tecnic = ore_ptec.get() + OREAULATEC <= mx_oretec;

            if (!check_oretotali) {
                resp_out.addProperty("result", false);
                resp_out.addProperty("message", "Ore superiori a quelle consentite.<br>Ore residue da pianificare: " + dapian);
            } else if (!check_teoria) {
                resp_out.addProperty("result", false);
                resp_out.addProperty("message",
                        "Ore di aula teorica superiori al massimo consentito (60% del totale corso). Massime ore di questa tipologia pianificabili: " + (mx_oreteo - ore_pteo.get()));
            } else if (!check_tecnic) {
                resp_out.addProperty("result", false);
                resp_out.addProperty("message",
                        "Ore di aula tecnico/operativa superiori al massimo consentito (40% del totale corso). Massime ore di questa tipologia pianificabili: " + (mx_oretec - ore_ptec.get()));
            } else if (!check_elearning) {
                resp_out.addProperty("result", false);
                resp_out.addProperty("message", "Ore e-learning superiori a quelle consentite.<br>Ore residue e-learning da pianificare: " + dapianEL);
            } else if (!check_sum) {
                resp_out.addProperty("result", false);
                resp_out.addProperty("message", "La somma delle ore inserite ORE AULA TEORICA + ORE AULA TECNICO/OPERATIVA + ORE E-LEARNING ("
                        + (OREAULATEO + OREAULATEC + OREELE) + ") non corrisponde alle ORE TOTALI (" + ORETOTALI + ") inserite . Controllare.");
            } else {
                resp_out.addProperty("result", true);
                resp_out.addProperty("message", "");

            }
        } else {
            resp_out.addProperty("result", false);
            resp_out.addProperty("message", "Errore durante l'individuazione del calendario formativo.");
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(resp_out.toString());
        }

    }

    protected void INSERISCIATTREZZATURA(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityOp ep1 = new EntityOp();

        Corso co1 = ep1.getEm().find(Corso.class, Long.valueOf(getRequestValue(request, "idcorsodasalvare")));

        if (co1 != null) {

            Attrezzature at = new Attrezzature();
            at.setCorso(co1);
            at.setDescrizione(normalize(getRequestValue(request, "descrizione")));
            try {
                at.setDatainizio(sdf_PATTERNDATE6.parse(getRequestValue(request, "datainizio")));
            } catch (Exception ex1) {
                at.setDatainizio(null);
            }
            try {
                at.setModalita(Modacquisizione.valueOf(getRequestValue(request, "modacq")));
            } catch (Exception ex1) {
                at.setModalita(Modacquisizione.POSSESSO);
            }
            at.setRegistroinventario(normalize(getRequestValue(request, "invent")));
            at.setQuantita(parseIntR(getRequestValue(request, "quant")));
            at.setNumeroinventario("");

            try {
                ep1.begin();
                ep1.persist(at);
                ep1.commit();
                ep1.close();
                request.getSession().setAttribute("ses_idcorso", Utils.enc_string(getRequestValue(request, "idcorsodasalvare")));
                redirect(request, response, "Page_message.jsp?esito=OK_SMAT");
            } catch (Exception ex1) {
                request.getSession().setAttribute("ses_idcorso", Utils.enc_string(getRequestValue(request, "idcorsodasalvare")));
                redirect(request, response, "US_programmacorsi_attr.jsp?esito=KO1");
            }

        } else {
            request.getSession().setAttribute("ses_idcorso", Utils.enc_string(getRequestValue(request, "idcorsodasalvare")));
            redirect(request, response, "US_programmacorsi_attr.jsp?esito=KO2");
        }
    }

    protected void MODIFICAPIANIFICAZIONEDOCENTI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityOp ep1 = new EntityOp();

        Corso co1 = ep1.getEm().find(Corso.class, Long.valueOf(getRequestValue(request, "idcorsodasalvare")));
        if (co1 != null) {
            Docente d1 = ep1.getEm().find(Docente.class, Long.valueOf(getRequestValue(request, "docente")));
            if (d1 != null) {
                ep1.begin();
                List<Calendario_Formativo> calendar = ep1.calendario_formativo_corso(co1);
                for (Calendario_Formativo c1 : calendar) {
                    String checkbox = getRequestValue(request, "CH_" + c1.getIdcalendarioformativo());
                    if (checkbox != null && checkbox.equalsIgnoreCase("on")) {
                        double orepreviste = fd(formatDoubleforMysql(Utils.getRequestValue(request, "ORE_" + c1.getIdcalendarioformativo())));
                        Moduli_Docenti md = new Moduli_Docenti();
                        md.setIdmodulidocenti(new Moduli_DocentiId(d1.getIddocente(), c1.getIdcalendarioformativo()));
                        md.setDocente(d1);
                        md.setModuloformativo(c1);
                        md.setOrepreviste(orepreviste);
                        ep1.persist(md);
                    }
                }

                ep1.commit();
                ep1.close();
                request.getSession().setAttribute("ses_idcorso", Utils.enc_string(getRequestValue(request, "idcorsodasalvare")));
                redirect(request, response, "Page_message.jsp?esito=OK_SMD");
            } else {
                request.getSession().setAttribute("ses_idcorso", Utils.enc_string(getRequestValue(request, "idcorsodasalvare")));
                redirect(request, response, "US_programmacorsi_docenti.jsp?esito=KO1");
            }
        } else {
            request.getSession().setAttribute("ses_idcorso", Utils.enc_string(getRequestValue(request, "idcorsodasalvare")));
            redirect(request, response, "US_programmacorsi_docenti.jsp?esito=KO2");
        }

    }

    protected void SALVAPIANIFICAZIONE(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityOp ep1 = new EntityOp();
        List<Lingua> language = ep1.getLingue();
        List<Competenze_Trasversali> comp_tr = (List<Competenze_Trasversali>) ep1.findAll(Competenze_Trasversali.class);
        Corso co1 = ep1.getEm().find(Corso.class, Long.valueOf(getRequestValue(request, "idcorsodasalvare")));
        if (co1 != null) {
            ep1.begin();

            co1.setCostostimatoallievo(fd(formatDoubleforMysql(getRequestValue(request, "COSTOSTIMATO"))));

            List<Calendario_Formativo> calendar = ep1.calendario_formativo_corso(co1);
            AtomicInteger indexmod = new AtomicInteger(1);
            if (calendar.isEmpty()) {//PRIMO INSERIMENTO
                comp_tr.forEach(competenzatras -> {
                    String reqing = getRequestValue(request, "ctreqing_" + competenzatras.getIdcompetenze());
                    String descr = getRequestValue(request, "ctdescr_" + competenzatras.getIdcompetenze());
                    String lingua = getRequestValue(request, "ctlingua_" + competenzatras.getIdcompetenze());
                    String nomemod = competenzatras.getDescrizione();
                    if (!lingua.equals("")) {
                        try {
                            String lan1 = language.stream().filter(l1 -> l1.getCodicelingua().equals(lingua)).findAny().get().getNome();
                            nomemod = nomemod + " - " + lan1;
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    nomemod = nomemod + " - " + descr;
                    if (reqing.equals("0")) {
                        Calendario_Formativo ct = new Calendario_Formativo();
                        ct.setCompetenzetrasversali(competenzatras);
                        ct.setCodicemodulo("MOD" + indexmod.get());
                        indexmod.addAndGet(1);
                        ct.setCorsodiriferimento(co1);
                        ct.setNomemodulo(nomemod);
                        ct.setOre(competenzatras.getDurataore());
                        ct.setOre_aula(competenzatras.getDurataore());
                        ct.setOre_teorica_aula(competenzatras.getDurataore());
                        ct.setTipomodulo("BASE");
                        ct.setCtcodicelingua(lingua);
                        ct.setCtdescrizione(descr);
                        ep1.persist(ct);
                    } else {
                        Calendario_Formativo ct = new Calendario_Formativo();
                        ct.setCompetenzetrasversali(competenzatras);
                        ct.setCodicemodulo("BASE1"); //
                        ct.setCorsodiriferimento(co1);
                        ct.setNomemodulo(nomemod);
                        ct.setTipomodulo("REQ");
                        ct.setCtcodicelingua(lingua);
                        ct.setCtdescrizione(descr);
                        ep1.persist(ct);
                    }
                });
            } else {

                comp_tr.forEach(competenzatras -> {
                    String reqing = getRequestValue(request, "ctreqing_" + competenzatras.getIdcompetenze());
                    String descr = getRequestValue(request, "ctdescr_" + competenzatras.getIdcompetenze());
                    String lingua = getRequestValue(request, "ctlingua_" + competenzatras.getIdcompetenze());
                    String nomemod = competenzatras.getDescrizione();
                    if (!lingua.equals("")) {
                        try {
                            String lan1 = language.stream().filter(l1 -> l1.getCodicelingua().equals(lingua)).findAny().get().getNome();
                            nomemod = nomemod + " - " + lan1;
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    nomemod = nomemod + " - " + descr;

                    if (calendar.stream().anyMatch(c3 -> c3.getCompetenzetrasversali().equals(competenzatras))) {
                        Calendario_Formativo ct2 = calendar.stream().filter(c3 -> c3.getCompetenzetrasversali().equals(competenzatras)).findAny().get();

                        if (reqing.equals("0")) {
                            ct2.setCodicemodulo("MOD" + indexmod.get());
                            indexmod.addAndGet(1);
                            ct2.setNomemodulo(nomemod);
                            ct2.setOre(competenzatras.getDurataore());
                            ct2.setOre_aula(competenzatras.getDurataore());
                            ct2.setOre_teorica_aula(competenzatras.getDurataore());
                            ct2.setTipomodulo("BASE");
                            ct2.setCtcodicelingua(lingua);
                            ct2.setCtdescrizione(descr);
                        } else {
                            ct2.setCodicemodulo("BASE1"); //
                            ct2.setNomemodulo(nomemod);
                            ct2.setTipomodulo("REQ");
                            ct2.setCtcodicelingua(lingua);
                            ct2.setCtdescrizione(descr);
                        }
                        ep1.merge(ct2);
                    } else {
                        if (reqing.equals("0")) {
                            Calendario_Formativo ct = new Calendario_Formativo();
                            ct.setCompetenzetrasversali(competenzatras);
                            ct.setCodicemodulo("MOD" + indexmod.get());
                            indexmod.addAndGet(1);
                            ct.setCorsodiriferimento(co1);
                            ct.setNomemodulo(nomemod);
                            ct.setOre(competenzatras.getDurataore());
                            ct.setOre_aula(competenzatras.getDurataore());
                            ct.setOre_teorica_aula(competenzatras.getDurataore());
                            ct.setTipomodulo("BASE");
                            ct.setCtcodicelingua(lingua);
                            ct.setCtdescrizione(descr);
                            ep1.persist(ct);
                        } else {
                            Calendario_Formativo ct = new Calendario_Formativo();
                            ct.setCompetenzetrasversali(competenzatras);
                            ct.setCodicemodulo("BASE1"); //
                            ct.setCorsodiriferimento(co1);
                            ct.setNomemodulo(nomemod);
                            ct.setTipomodulo("REQ");
                            ct.setCtcodicelingua(lingua);
                            ct.setCtdescrizione(descr);
                            ep1.persist(ct);
                        }
                    }

                });

            }//UPDATE
        }
        ep1.commit();
        ep1.close();
        request.getSession().setAttribute("ses_idcorso", Utils.enc_string(getRequestValue(request, "idcorsodasalvare")));
        redirect(request, response, "US_programmacorsi.jsp");
    }

    protected void SCELTAREPERTORIO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityOp ep1 = new EntityOp();

        Repertorio re = ep1.getEm().find(Repertorio.class, Long.valueOf(getRequestValue(request, "val")));
        if (re != null) {

            Scheda_Attivita sa = ep1.getEm().find(Scheda_Attivita.class, Long.valueOf(getRequestValue(request, "val_att")));
            if (sa != null) {
                try (PrintWriter pw = response.getWriter()) {
                    pw.print(re.toString() + "@@@" + sa.toString());
                }
            } else {

            }
        } else {

        }

    }

    protected void ADDCORSO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String closewindow = getRequestValue(request, "closewindow");
        SoggettoProponente so = ((User) request.getSession().getAttribute("us_memory")).getSoggetto();
        try {

            String codiceis = generaId(30);
            EntityOp e = new EntityOp();

            e.begin();

//            if (is != null) {
//                is.setQuantitarichiesta(is.getQuantitarichiesta() + parseIntR(getRequestValue(request, "quantitarichiesta")));
//                is.setProtocollosoggetto(getRequestValue(request, "protnum"));
//                is.setProtocollosoggettodata(new DateTime().toString(PATTERNDATE4));
//                e.merge(is);
//            } else {
            Istanza is;
            try {
                is = e.getEm().find(Istanza.class, Long.valueOf(getRequestValue(request, "istanzabase")));
            } catch (Exception ex2) {
                is = null;
            }

            if (is == null) {
                is = new Istanza();
                is.setProtocollosoggetto(getRequestValue(request, "protnum"));
                is.setProtocollosoggettodata(new DateTime().toString(PATTERNDATE4));
                is.setCodiceistanza(codiceis);
                is.setSoggetto(so);
                is.setStatocorso(e.getEm().find(CorsoStato.class, "01"));
                is.setQuantitarichiesta(parseIntR(getRequestValue(request, "quantitarichiesta")));
                is.setTipologiapercorso(e.getEm().find(Tipologia_Percorso.class, Long.valueOf(getRequestValue(request, "scelta"))));
                e.persist(is);
            }

            Corso c = new Corso();
            c.setDuratagiorni(parseIntR(getRequestValue(request, "duratagiorni")));
            c.setDurataore(parseIntR(getRequestValue(request, "durataore")));
            c.setStageore(parseIntR(getRequestValue(request, "stageore")));
            c.setNumeroallievi(parseIntR(getRequestValue(request, "numeroallievi")));
            c.setElearningperc(parseIntR(getRequestValue(request, "elearning")));
            c.setTipologiapercorso(e.getEm().find(Tipologia_Percorso.class, Long.valueOf(getRequestValue(request, "scelta"))));
            String splitvalue_rep = getRequestValue(request, "istat");
            c.setRepertorio(e.getEm().find(Repertorio.class, Long.valueOf(splitvalue_rep.split(";")[0])));
            c.setSchedaattivita(e.getEm().find(Scheda_Attivita.class, Long.valueOf(splitvalue_rep.split(";")[1])));
            c.setSoggetto(so);
            c.setSedescelta(e.getEm().find(Sede.class, parseLongR(getRequestValue(request, "sedescelta"))));
            c.setQuantitarichiesta(parseIntR(getRequestValue(request, "quantitarichiesta")));
            c.setStatocorso(e.getEm().find(CorsoStato.class, "20"));
            c.setIstanza(is);
            e.persist(c);
            e.flush();
            e.commit();
            e.close();
            if (closewindow.equals("YES")) {
                redirect(request, response, "Page_message.jsp?esito=OK_CL");
            } else {
                redirect(request, response, "US_gestioneistanza.jsp");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex));
            if (closewindow.equals("YES")) {
                redirect(request, response, "Page_message.jsp?esito=OK_CL");
            } else {
                redirect(request, response, "US_compilacorsi.jsp?esito=KO");
            }

        }

    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            String type = request.getParameter("type");
            switch (type) {
                case "SCELTAREPERTORIO":
                    SCELTAREPERTORIO(request, response);
                    break;
                case "SALVAPIANIFICAZIONE":
                    SALVAPIANIFICAZIONE(request, response);
                    break;
                case "CHECKMODULO":
                    CHECKMODULO(request, response);
                    break;
                case "MODIFICAPIANIFICAZIONE":
                    MODIFICAPIANIFICAZIONE(request, response);
                    break;
                case "MODIFICAPIANIFICAZIONEDOCENTI":
                    MODIFICAPIANIFICAZIONEDOCENTI(request, response);
                    break;
                case "INSERISCIATTREZZATURA":
                    INSERISCIATTREZZATURA(request, response);
                    break;
                case "DELETECORSOISTANZA":
                    DELETECORSOISTANZA(request, response);
                    break;
                case "DELETEISTANZA":
                    DELETEISTANZA(request, response);
                    break;
                case "DELETEDOCUMENT":
                    DELETEDOCUMENT(request, response);
                    break;
                case "SAVEISTANZA":
                    SAVEISTANZA(request, response);
                    break;
                case "SENDISTANZA":
                    SENDISTANZA(request, response);
                    break;
                case "ADDCORSO":
                    ADDCORSO(request, response);
                    break;
                case "VISUALDOC":
                    VISUALDOC(request, response);
                    break;
                case "APPROVAISTANZA":
                    APPROVAISTANZA(request, response);
                    break;
                case "UPLGENERIC":
                    UPLGENERIC(request, response);
                    break;
                case "UPLSOST":
                    UPLSOST(request, response);
                    break;
                case "UPLDECAUT":
                    UPLDECAUT(request, response);
                    break;
                case "RIGETTAISTANZA":
                    RIGETTAISTANZA(request, response);
                    break;
                case "PROTOCOLLAISTANZA":
                    PROTOCOLLAISTANZA(request, response);
                    break;
                case "GENERADECRETODDSFTO":
                    GENERADECRETODDSFTO(request, response);
                    break;
                case "GENERADECRETOBASE":
                    GENERADECRETOBASE(request, response);
                    break;
                case "SCARICADECRETOFIRMATO":
                    SCARICADECRETOFIRMATO(request, response);
                    break;
                case "INVIANOTIFICADECRETO":
                    INVIANOTIFICADECRETO(request, response);
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            EntityOp.trackingAction("SERVICE", estraiEccezione(ex));
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String p = request.getContextPath();
        request.getSession().invalidate();
        redirect(request, response, p);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
