package rc.soop.servlet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.apache.commons.codec.digest.DigestUtils.md5Hex;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.mime.MimeTypes;
import org.joda.time.DateTime;
import rc.soop.sic.Constant;
import static rc.soop.sic.Constant.PATTERNDATE3;
import static rc.soop.sic.Constant.PATTERNDATE4;
import static rc.soop.sic.Constant.PATTERNDATE5;
import static rc.soop.sic.Constant.sdf_PATTERNDATE5;
import static rc.soop.sic.Constant.sdf_PATTERNDATE6;
import rc.soop.sic.Engine;
import rc.soop.sic.Pdf;
import rc.soop.sic.SendMail;
import rc.soop.sic.Utils;
import static rc.soop.sic.Utils.calcolaMillis;
import static rc.soop.sic.Utils.calcolaore;
import static rc.soop.sic.Utils.createPassword;
import static rc.soop.sic.Utils.createUsername;
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
import static rc.soop.sic.Utils.roundDoubleandFormat;
import rc.soop.sic.jpa.Abilita;
import rc.soop.sic.jpa.Allegati;
import rc.soop.sic.jpa.Allievi;
import rc.soop.sic.jpa.Altropersonale;
import rc.soop.sic.jpa.Attrezzature;
import rc.soop.sic.jpa.Calendario_Formativo;
import rc.soop.sic.jpa.Calendario_Lezioni;
import rc.soop.sic.jpa.CommissioneEsame;
import rc.soop.sic.jpa.Competenze;
import rc.soop.sic.jpa.Competenze_Trasversali;
import rc.soop.sic.jpa.Conoscenze;
import rc.soop.sic.jpa.Corso;
import rc.soop.sic.jpa.CorsoAvviato_AltroPersonale;
import rc.soop.sic.jpa.CorsoAvviato_AltroPersonaleId;
import rc.soop.sic.jpa.CorsoAvviato_Docenti;
import rc.soop.sic.jpa.CorsoAvviato_DocentiId;
import rc.soop.sic.jpa.CorsoStato;
import rc.soop.sic.jpa.Corsoavviato;
import rc.soop.sic.jpa.Docente;
import rc.soop.sic.jpa.EnteStage;
import rc.soop.sic.jpa.EntityOp;
import rc.soop.sic.jpa.IncrementalCorso;
import rc.soop.sic.jpa.Information;
import rc.soop.sic.jpa.Istanza;
import rc.soop.sic.jpa.Lingua;
import rc.soop.sic.jpa.Modacquisizione;
import rc.soop.sic.jpa.Moduli_Docenti;
import rc.soop.sic.jpa.Moduli_DocentiId;
import rc.soop.sic.jpa.Path;
import rc.soop.sic.jpa.Presenze_Lezioni;
import rc.soop.sic.jpa.Presenze_Lezioni_Allievi;
import rc.soop.sic.jpa.Presenze_Tirocinio_Allievi;
import rc.soop.sic.jpa.PresidenteCommissione;
import rc.soop.sic.jpa.Repertorio;
import rc.soop.sic.jpa.Scheda_Attivita;
import rc.soop.sic.jpa.Sede;
import rc.soop.sic.jpa.SoggettoProponente;
import rc.soop.sic.jpa.Stati;
import rc.soop.sic.jpa.TemplateDecretoAUT;
import rc.soop.sic.jpa.TipoCorso;
import rc.soop.sic.jpa.TipoSede;
import rc.soop.sic.jpa.Tipologia_Percorso;
import rc.soop.sic.jpa.TirocinioStage;
import rc.soop.sic.jpa.User;

/**
 *
 * @author Raffaele
 */
public class Operations extends HttpServlet {

    protected void PDFVERBALE(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idcorso = Utils.dec_string(getRequestValue(request, "idcorso"));

        EntityOp ep1 = new EntityOp();

        try {
            String contentb64 = ep1.getEm().find(Path.class, "pdf.test.verbale").getDescrizione();
            Corsoavviato ca1 = ep1.getEm().find(Corsoavviato.class, Long.valueOf(idcorso));
            if (ca1 != null) {

                String mimeType = "application/pdf";
                response.setContentType(mimeType);
                String headerKey = "Content-Disposition";
                String headerValue = format("attach; filename=\"%s\"",
                        Utils.generaId(50)
                        + MimeTypes.getDefaultMimeTypes().forName(mimeType).getExtension());
                response.setHeader(headerKey, headerValue);
                response.setContentLength(-1);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(Base64.decodeBase64(contentb64));
                }
            } else {
                redirect(request, response, "404.jsp");
            }
        } catch (Exception ex1) {
            ex1.printStackTrace();
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            redirect(request, response, "404.jsp");
        }
    }

    protected void ATTESTATI(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idcorso = Utils.dec_string(getRequestValue(request, "idcorso"));
        EntityOp ep1 = new EntityOp();

        try {
            String contentb64 = ep1.getEm().find(Path.class, "pdf.test.attestati").getDescrizione();
            Corsoavviato ca1 = ep1.getEm().find(Corsoavviato.class, Long.valueOf(idcorso));
            if (ca1 != null) {
                String mimeType = "application/zip";
                response.setContentType(mimeType);
                String headerKey = "Content-Disposition";
                String headerValue = format("attach; filename=\"%s\"",
                        Utils.generaId(50)
                        + MimeTypes.getDefaultMimeTypes().forName(mimeType).getExtension());
                response.setHeader(headerKey, headerValue);
                response.setContentLength(-1);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(Base64.decodeBase64(contentb64));
                }
            } else {
                redirect(request, response, "500.jsp");
            }
        } catch (Exception ex1) {
            ex1.printStackTrace();
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            redirect(request, response, "404.jsp");
        }
    }

    protected void ESAMIFINALI(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Utils.printRequest(request);

        try {

            String IDCORSO = getRequestValue(request, "IDCORSO");

            EntityOp ep1 = new EntityOp();
            Corsoavviato ca1 = ep1.getEm().find(Corsoavviato.class, Long.valueOf(IDCORSO));
            if (ca1 != null) {

                String VERBPROT = getRequestValue(request, "VERBPROT");
                String VERBDATA = getRequestValue(request, "VERBDATA");//SQL
                String DATAESAME = getRequestValue(request, "DATAESAME"); //SQL
                int ORESVOLTE = parseIntR(getRequestValue(request, "ORESVOLTE"));
                List<Allievi> allievi = ep1.getAllieviCorsoAvviato(ca1);

                for (Allievi a1 : allievi) {
                    int ALLIEVOOREPRESENZA = parseIntR(getRequestValue(request, "OREPRES_" + a1.getIdallievi()));
                    String ALLIEVOGIUDIZIO = getRequestValue(request, "GIUDIZIO_" + a1.getIdallievi());
                    int ALLIEVOVOTO = parseIntR(getRequestValue(request, "VOTO_" + a1.getIdallievi()));
                    String ALLIEVOESITO = getRequestValue(request, "ESITO_" + a1.getIdallievi());
                    if (!ALLIEVOESITO.equals("")) {
                        //SAVE
                    }
                }
                ca1.setStatocorso(ep1.getEm().find(CorsoStato.class, "52"));
                ep1.begin();
                ep1.merge(ca1);
                ep1.commit();
                ep1.close();
                redirect(request, response, "PRE_dashboard.jsp");
            } else {
                redirect(request, response, "Page_message.jsp?esito=KO_MOSD1");
            }

        } catch (Exception ex1) {
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            redirect(request, response, "Page_message.jsp?esito=KO_MOSD2");
        }
    }

    protected void NOMINAPRESIDENTECOMMISSIONE(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String IDCORSO = getRequestValue(request, "IDCORSO");
            String PRESIDENTE = getRequestValue(request, "PRESIDENTE");
            String NUMPROTNOMINA = getRequestValue(request, "NUMPROTNOMINA");
            String DATAPROTNOMINA = getRequestValue(request, "DATAPROTNOMINA");

            EntityOp ep1 = new EntityOp();
            Corsoavviato ca1 = ep1.getEm().find(Corsoavviato.class, Long.valueOf(IDCORSO));
            PresidenteCommissione pr1 = ep1.getEm().find(PresidenteCommissione.class, Long.valueOf(PRESIDENTE));
            if (ca1 != null && pr1 != null) {
                ca1.setPresidentecommissione(pr1);
                ca1.setProtnomina(NUMPROTNOMINA);
                ca1.setDataprotnomina(sdf_PATTERNDATE6.parse(DATAPROTNOMINA));
                ca1.setPdfnomina(null); //da sistemare
                ca1.setStatocorso(ep1.getEm().find(CorsoStato.class, "51"));
                ep1.begin();
                ep1.merge(ca1);
                ep1.commit();
                ep1.close();
                redirect(request, response, "Page_message.jsp?esito=OKRI_IS1");
            } else {
                redirect(request, response, "Page_message.jsp?esito=KO_MOSD1");
            }
        } catch (Exception ex1) {
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            redirect(request, response, "Page_message.jsp?esito=KO_MOSD2");
        }

    }

    protected void RIGETTACOMMISSIONE(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonObject resp_out = new JsonObject();
        try {
            String IDCOMM = getRequestValue(request, "IDCOMM");

            EntityOp ep1 = new EntityOp();
            CommissioneEsame ca1 = ep1.getEm().find(CommissioneEsame.class, Long.valueOf(IDCOMM));
            if (ca1 != null) {
                ca1.setStatocommissione(Stati.RESPINTA);
                ca1.setUtenteinserimento(request.getSession().getAttribute("us_cod").toString());
                ca1.setNumprotrichiesta(getRequestValue(request, "NUMPROTRICH"));
                ca1.setDataprotrichiesta(sdf_PATTERNDATE6.parse(getRequestValue(request, "DATAPROTRICH")));
                Corsoavviato ca = ca1.getCorsodiriferimento();
                ca.setStatocorso(ep1.getEm().find(CorsoStato.class, "49"));
                ep1.begin();
                ep1.merge(ca1);
                ep1.merge(ca);
                ep1.commit();
                ep1.close();
                resp_out.addProperty("result",
                        true);
            } else {
                resp_out.addProperty("result",
                        false);
                resp_out.addProperty("message",
                        "COMMISSIONE NON TROVATA.");
            }
        } catch (Exception ex1) {
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            resp_out.addProperty("result",
                    false);
            resp_out.addProperty("message",
                    estraiEccezione(ex1));
        }
        try (PrintWriter out = response.getWriter()) {
            out.print(resp_out.toString());
        }

    }

    protected void APPROVACOMMISSIONE(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonObject resp_out = new JsonObject();
        try {

            String IDCOMM = getRequestValue(request, "IDCOMM");

            EntityOp ep1 = new EntityOp();
            CommissioneEsame ca1 = ep1.getEm().find(CommissioneEsame.class, Long.valueOf(IDCOMM));
            if (ca1 != null) {
                ca1.setStatocommissione(Stati.APPROVATA);
                ca1.setUtenteinserimento(request.getSession().getAttribute("us_cod").toString());
                ca1.setNumprotrichiesta(getRequestValue(request, "NUMPROTRICH"));
                ca1.setDataprotrichiesta(sdf_PATTERNDATE6.parse(getRequestValue(request, "DATAPROTRICH")));
                Corsoavviato ca = ca1.getCorsodiriferimento();
                ca.setStatocorso(ep1.getEm().find(CorsoStato.class, "49"));
                ep1.begin();
                ep1.merge(ca1);
                ep1.merge(ca);
                ep1.commit();
                ep1.close();
                resp_out.addProperty("result",
                        true);
            } else {
                resp_out.addProperty("result",
                        false);
                resp_out.addProperty("message",
                        "COMMISSIONE NON TROVATA.");
            }
        } catch (Exception ex1) {
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            resp_out.addProperty("result",
                    false);
            resp_out.addProperty("message",
                    estraiEccezione(ex1));
        }
        
        try (PrintWriter out = response.getWriter()) {
            out.print(resp_out.toString());
        }

    }

    protected void RICHIEDINOMINACOMMISSIONE(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String IDCORSO = getRequestValue(request, "IDCORSO");
            String esperto = getRequestValue(request, "esperto");
            ObjectMapper om = new ObjectMapper();
            om.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            List<String> JSON_commissione = om.readValue(getRequestValue(request, "commissione"),
                    new TypeReference<List<String>>() {
            });
            List<String> JSON_sostituti = om.readValue(getRequestValue(request, "sostituti"),
                    new TypeReference<List<String>>() {
            });
            EntityOp ep1 = new EntityOp();
            Corsoavviato ca1 = ep1.getEm().find(Corsoavviato.class, Long.valueOf(IDCORSO));
            if (ca1 != null && !JSON_commissione.isEmpty() && !JSON_sostituti.isEmpty()) {
                CommissioneEsame ce = new CommissioneEsame();
                ce.setCorsodiriferimento(ca1);
                ce.setDatainserimento(new DateTime().toDate());
                ce.setEspertosettore(esperto);
                ce.setTitolare1(ep1.getEm().find(Docente.class, Long.valueOf(JSON_commissione.get(0))));
                ce.setTitolare2(ep1.getEm().find(Docente.class, Long.valueOf(JSON_commissione.get(1))));
                ce.setSostituto1(ep1.getEm().find(Docente.class, Long.valueOf(JSON_sostituti.get(0))));
                ce.setSostituto2(ep1.getEm().find(Docente.class, Long.valueOf(JSON_sostituti.get(1))));
                ce.setUtenteinserimento(request.getSession().getAttribute("us_cod").toString());
                ce.setStatocommissione(Stati.RICHIESTA);
                ep1.begin();
                ep1.persist(ce);
                ca1.setStatocorso(ep1.getEm().find(CorsoStato.class, "48"));
                ep1.commit();
                ep1.close();
                redirect(request, response, "Page_message.jsp?esito=OKRI_IS1");
            } else {
                redirect(request, response, "Page_message.jsp?esito=KO_MOSD1");
            }
        } catch (Exception ex1) {
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            redirect(request, response, "Page_message.jsp?esito=KO_MOSD2");
        }
    }

    protected void INSERISCIPRESENZATIROCINIO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String idtirociniostage = getRequestValue(request, "idtirociniostage");
        String datetiroc = getRequestValue(request, "datetiroc");
        String orai = getRequestValue(request, "orai");
        String oraf = getRequestValue(request, "oraf");

        try {

            EntityOp ep1 = new EntityOp();
            ep1.begin();

            TirocinioStage al1 = ep1.getEm().find(TirocinioStage.class, Long.valueOf(idtirociniostage));

            Presenze_Tirocinio_Allievi pta = new Presenze_Tirocinio_Allievi();

            pta.setAllievi(al1.getAllievi());
            pta.setDatapresenza(sdf_PATTERNDATE6.parse(datetiroc));
            pta.setDatainserimento(new DateTime().toDate());
            pta.setTirociniostage(al1);
            pta.setOre(calcolaore(orai, oraf));
            pta.setOrainizio(orai);
            pta.setOrafine(oraf);
            pta.setUtenteinserimento(request.getSession().getAttribute("us_cod").toString());
            pta.setStatolezione(ep1.getEm().find(CorsoStato.class, "60"));
            ep1.persist(pta);
            ep1.commit();
            ep1.close();

            redirect(request, response, "US_dettaglitirocinioallievo.jsp?idtirociniostage=" + idtirociniostage);
        } catch (Exception ex1) {
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            redirect(request, response, "Page_message.jsp?esito=KO_TSAL1");
        }

    }

    protected void AVVIATIROCINIOALLIEVO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String idallievo = getRequestValue(request, "idallievo");
        String ENTESTAGE = getRequestValue(request, "ENTESTAGE");
        String SEDESTAGE = getRequestValue(request, "SEDESTAGE");
        String ORE = roundDoubleandFormat(fd(formatDoubleforMysql(getRequestValue(request, "ORE"))), 0);

        String DATAINIZIO = getRequestValue(request, "DATAINIZIO");
        String DATAFINE = getRequestValue(request, "DATAFINE");

        try {

            EntityOp ep1 = new EntityOp();
            ep1.begin();
            Allievi al1 = ep1.getEm().find(Allievi.class, Long.valueOf(idallievo));
            EnteStage es1 = ep1.getEm().find(EnteStage.class, Long.valueOf(ENTESTAGE));
            Sede se1 = ep1.getEm().find(Sede.class, Long.valueOf(SEDESTAGE));
            TirocinioStage ts1 = new TirocinioStage();
            ts1.setAllievi(al1);
            ts1.setDatainserimento(new DateTime().toDate());
            ts1.setDatainizio(sdf_PATTERNDATE6.parse(DATAINIZIO));
            ts1.setDatafine(sdf_PATTERNDATE6.parse(DATAFINE));
            ts1.setEntestage(es1);
            ts1.setSedestage(se1);
            ts1.setOrepreviste(parseIntR(ORE));
            ts1.setUtenteinserimento(request.getSession().getAttribute("us_cod").toString());
            ep1.persist(ts1);
            al1.setStatotirocinio(Stati.ATTIVO);
            ep1.merge(al1);
            ep1.commit();
            ep1.close();
            //NOTIFICA MAIL PEC
            redirect(request, response, "US_tirocinioallievo.jsp");
        } catch (Exception ex1) {
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            redirect(request, response, "Page_message.jsp?esito=KO_TSAL1");
        }

    }

    protected void MODIFICALEZIONE(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idcalendariolezione = getRequestValue(request, "idcalendariolezione");
        String docente = getRequestValue(request, "docente");
        String orai = getRequestValue(request, "orai");
        String oraf = getRequestValue(request, "oraf");
        String datelez = getRequestValue(request, "datelez");

        try {

            EntityOp ep1 = new EntityOp();
            ep1.begin();
            Calendario_Lezioni cl1 = ep1.getEm().find(Calendario_Lezioni.class, Long.valueOf(idcalendariolezione));
            Docente d1 = ep1.getEm().find(Docente.class, Long.valueOf(docente));
            cl1.setDatalezione(Constant.sdf_PATTERNDATE6.parse(datelez));
            cl1.setDocente(d1);
            cl1.setOrainizio(orai);
            cl1.setOrafine(oraf);
            cl1.setOre(calcolaore(orai, oraf));
            cl1.setDatainserimento(new DateTime().toDate());
            ep1.merge(cl1);
            ep1.commit();
            ep1.close();
            //NOTIFICA MAIL PEC
            redirect(request, response, "Page_message.jsp?esito=OK_MOSD");
        } catch (Exception ex1) {
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            redirect(request, response, "Page_message.jsp?esito=KO_LEZ1");
        }

    }

    protected void AGGIUNGIPRESENZELEZIONE(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idcalendariolezione = getRequestValue(request, "idcalendariolezione");
        String idpresenza = getRequestValue(request, "idpresenza");
        String docente = getRequestValue(request, "docente");
        String orai = getRequestValue(request, "orai");
        String oraf = getRequestValue(request, "oraf");
        String datelez = getRequestValue(request, "datelez");

        try {

            EntityOp ep1 = new EntityOp();
            ep1.begin();
            Calendario_Lezioni cl1 = ep1.getEm().find(Calendario_Lezioni.class, Long.valueOf(idcalendariolezione));
            Docente d1 = ep1.getEm().find(Docente.class, Long.valueOf(docente));

            Presenze_Lezioni pl1;
            if (!idpresenza.equals("0")) {
                pl1 = ep1.getEm().find(Presenze_Lezioni.class, Long.valueOf(idpresenza));
                //MERGE
                pl1.setDatarealelezione(Constant.sdf_PATTERNDATE6.parse(datelez));
                pl1.setDocente(d1);
                pl1.setOrainizio(orai);
                pl1.setOrafine(oraf);
                pl1.setUtenteinserimento(request.getSession().getAttribute("us_cod").toString());
                pl1.setDatainserimento(new DateTime().toDate());
                cl1.setStatolezione(ep1.getEm().find(CorsoStato.class, "60"));
                ep1.merge(pl1);
                ep1.merge(cl1);
            } else {
                //ADD
                pl1 = new Presenze_Lezioni();
                pl1.setDatarealelezione(Constant.sdf_PATTERNDATE6.parse(datelez));
                pl1.setDocente(d1);
                pl1.setOrainizio(orai);
                pl1.setOrafine(oraf);
                pl1.setUtenteinserimento(request.getSession().getAttribute("us_cod").toString());
                pl1.setCalendariolezioni(cl1);
                pl1.setCorsodiriferimento(cl1.getCorsodiriferimento());
                pl1.setDatainserimento(new DateTime().toDate());
                cl1.setStatolezione(ep1.getEm().find(CorsoStato.class, "60"));
                ep1.persist(pl1);
                ep1.merge(cl1);
            }
            ep1.commit();
            ep1.close();

            EntityOp ep2 = new EntityOp();
            ep2.begin();
            List<Allievi> allievi = ep2.getAllieviCorsoAvviato(cl1.getCorsodiriferimento());
            for (Allievi a1 : allievi) {
                Long allievo_durata = 0L;
                boolean allievo_presente = getRequestValue(request, "sino_" + a1.getIdallievi()).equals("1");
                String allievo_orai = getRequestValue(request, "orai_" + a1.getIdallievi());
                String allievo_oraf = getRequestValue(request, "oraf_" + a1.getIdallievi());
                if (!allievo_presente) {
                    allievo_orai = "00:00";
                    allievo_oraf = "00:00";
                } else {
                    allievo_durata = calcolaMillis(orai, oraf);
                }
                boolean modify = getRequestValue(request, "modify_" + a1.getIdallievi()).equals("true");
                Presenze_Lezioni_Allievi pla = ep2.getPresenzeLezioneAllievo(pl1, a1);
                if (pla != null && modify) {
                    pla.setDatainserimento(new DateTime().toDate());
                    pla.setDurata(allievo_durata);
                    pla.setOrainizio(allievo_orai);
                    pla.setOrafine(allievo_oraf);
                    ep2.merge(pla);
                    //MERGE
                } else {
                    //PERSIST
                    pla = new Presenze_Lezioni_Allievi();
                    pla.setDatainserimento(new DateTime().toDate());
                    pla.setDurata(allievo_durata);
                    pla.setOrainizio(allievo_orai);
                    pla.setOrafine(allievo_oraf);
                    pla.setAllievo(a1);
                    pla.setPresenzelezioni(pl1);
                    ep2.persist(pla);
                }
            }
            ep2.commit();
            ep2.close();
            redirect(request, response, "Page_message.jsp?esito=OK_MOSD");
        } catch (Exception ex1) {
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            redirect(request, response, "Page_message.jsp?esito=KO_PRLE1");
        }
    }

    protected void CAMBIASTATOALLIEVO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JsonObject resp_out = new JsonObject();
        try {

            String idallievo = getRequestValue(request, "idallievo");
            String NUOVOSTATO = getRequestValue(request, "NUOVOSTATO");

            EntityOp ep1 = new EntityOp();
            Allievi al1 = ep1.getEm().find(Allievi.class, Long.valueOf(idallievo));
            if (al1 != null) {

                switch (NUOVOSTATO) {
                    case "RITIRATO": {
                        al1.setStatoallievo(Stati.RITIRATO);
                        ep1.begin();
                        ep1.merge(al1);
                        ep1.commit();
                        ep1.close();
                        resp_out.addProperty("result",
                                true);
                        break;
                    }
                    default: {
                        resp_out.addProperty("result",
                                false);
                        resp_out.addProperty("message",
                                "VALORE NON VALIDO.");
                        break;
                    }
                }
            } else {
                resp_out.addProperty("result",
                        false);
                resp_out.addProperty("message",
                        "CORSO NON TROVATO.");
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

    protected void MODIFICAPERSONALECORSO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String IDCORSO = getRequestValue(request, "IDCORSO");
        String ALTROP = getRequestValue(request, "ALTROP");
        try {
            EntityOp ep1 = new EntityOp();
            Corsoavviato ca1 = ep1.getEm().find(Corsoavviato.class, Long.valueOf(IDCORSO));
            ObjectMapper om = new ObjectMapper();
            om.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            List<String> ALTROPJsonList = om.readValue(ALTROP, new TypeReference<List<String>>() {
            });
            if (!ALTROPJsonList.isEmpty()) {
                ep1.begin();
                for (String a1 : ALTROPJsonList) {
                    Altropersonale d1 = ep1.getEm().find(Altropersonale.class, parseLongR(a1));
                    if (d1 != null) {
                        CorsoAvviato_AltroPersonale cad = new CorsoAvviato_AltroPersonale();
                        cad.setIdcorsoavviatoaltropersonale(new CorsoAvviato_AltroPersonaleId(d1.getIdaltropersonale(), ca1.getIdcorsoavviato()));
                        cad.setAltropersonale(d1);
                        cad.setCorsoavviato(ca1);
                        ep1.persist(cad);
                    }
                }
                ep1.commit();
                ep1.close();
            }
            redirect(request, response, "Page_message.jsp?esito=OK_MOSD");
        } catch (Exception ex1) {
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            redirect(request, response, "Page_message.jsp?esito=KO_MOSD3");
        }
    }

    protected void MODIFICADOCENTICORSO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String IDCORSO = getRequestValue(request, "IDCORSO");
        String DOCENTI = getRequestValue(request, "DOCENTI");
        try {
            EntityOp ep1 = new EntityOp();
            Corsoavviato ca1 = ep1.getEm().find(Corsoavviato.class, Long.valueOf(IDCORSO));
            ObjectMapper om = new ObjectMapper();
            om.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            List<String> DOCENTIJsonList = om.readValue(DOCENTI, new TypeReference<List<String>>() {
            });
            if (!DOCENTIJsonList.isEmpty()) {
                ep1.begin();
                for (String a1 : DOCENTIJsonList) {
                    Docente d1 = ep1.getEm().find(Docente.class, parseLongR(a1));
                    if (d1 != null) {
                        CorsoAvviato_Docenti cad = new CorsoAvviato_Docenti();
                        cad.setIdcorsoavviatodocenti(new CorsoAvviato_DocentiId(d1.getIddocente(), ca1.getIdcorsoavviato()));
                        cad.setDocente(d1);
                        cad.setCorsoavviato(ca1);
                        ep1.persist(cad);
                    }
                }
                ep1.commit();
                ep1.close();
            }
            redirect(request, response, "Page_message.jsp?esito=OK_MOSD");
        } catch (Exception ex1) {
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            redirect(request, response, "Page_message.jsp?esito=KO_MOSD3");
        }
    }

    protected void MODIFICASEDECORSO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String IDCORSO = getRequestValue(request, "IDCORSO");
            String sedescelta = getRequestValue(request, "sedescelta");
            EntityOp ep1 = new EntityOp();
            Corsoavviato ca1 = ep1.getEm().find(Corsoavviato.class, Long.valueOf(IDCORSO));
            Sede se1 = ep1.getEm().find(Sede.class, Long.valueOf(sedescelta));

            if (ca1 == null) {
                redirect(request, response, "Page_message.jsp?esito=KO_MOSD1");
            } else if (se1 == null) {
                redirect(request, response, "Page_message.jsp?esito=KO_MOSD2");
            } else {
                ep1.begin();
                Corso co = ca1.getCorsobase();
                co.setSedescelta(se1);
                ep1.merge(co);
                //COMUNICAZIONE AD ENTE
                ca1.setStatocorso(ep1.getEm().find(CorsoStato.class, "46"));
                ep1.merge(ca1);
                ep1.commit();
                ep1.close();
                redirect(request, response, "Page_message.jsp?esito=OK_MOSD");
            }
        } catch (Exception ex1) {
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            redirect(request, response, "Page_message.jsp?esito=KO_MOSD3");
        }
    }

    protected void MODIFICADIRETTORE(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject resp_out = new JsonObject();
        try {
            String IDCORSO = getRequestValue(request, "IDCORSO");
            String NAME = normalizeUTF8(getRequestValue(request, "NAME"));
            EntityOp ep1 = new EntityOp();
            Corsoavviato ca1 = ep1.getEm().find(Corsoavviato.class, Long.valueOf(IDCORSO));
            if (ca1 != null) {
                ca1.setDirettorecorso(NAME);
                ep1.begin();
                ep1.merge(ca1);
                ep1.commit();
                ep1.close();
                resp_out.addProperty("result",
                        true);
            } else {
                resp_out.addProperty("result",
                        false);
                resp_out.addProperty("message",
                        "CORSO NON TROVATO.");
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

    protected void AUTORIZZAAVVIOCORSO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject resp_out = new JsonObject();
        try {
            String IDCORSO = getRequestValue(request, "IDCORSO");
            EntityOp ep1 = new EntityOp();
            Corsoavviato ca1 = ep1.getEm().find(Corsoavviato.class, Long.valueOf(IDCORSO));
            if (ca1 != null) {
                ca1.setStatocorso(ep1.getEm().find(CorsoStato.class, "43"));
                ep1.begin();
                ep1.merge(ca1);
                ep1.commit();
                ep1.close();
                resp_out.addProperty("result",
                        true);
            } else {
                resp_out.addProperty("result",
                        false);
                resp_out.addProperty("message",
                        "CORSO NON TROVATO.");
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

    protected void IMPOSTADESIGNAZIONE(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject resp_out = new JsonObject();
        try {
            String IDCORSO = getRequestValue(request, "IDCORSO");
            EntityOp ep1 = new EntityOp();
            Corsoavviato ca1 = ep1.getEm().find(Corsoavviato.class, Long.valueOf(IDCORSO));
            if (ca1 != null) {
                ca1.setStatocorso(ep1.getEm().find(CorsoStato.class, "50"));
                ep1.begin();
                ep1.merge(ca1);
                ep1.commit();
                ep1.close();
                resp_out.addProperty("result",
                        true);
            } else {
                resp_out.addProperty("result",
                        false);
                resp_out.addProperty("message",
                        "CORSO NON TROVATO.");
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

    protected void AUTORIZZACAMBIOSEDE(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject resp_out = new JsonObject();
        try {
            String IDCORSO = getRequestValue(request, "IDCORSO");
            EntityOp ep1 = new EntityOp();
            Corsoavviato ca1 = ep1.getEm().find(Corsoavviato.class, Long.valueOf(IDCORSO));
            if (ca1 != null) {
                try {
                    List<Calendario_Lezioni> lezioni = ep1.calendario_lezioni_corso(ca1);
                    DateTime dt1 = new DateTime(lezioni.get(0).getDatalezione().getTime());
                    DateTime dt2 = new DateTime();
                    if (dt1.isAfter(dt2)) { //DA AVVIARE 
                        ca1.setStatocorso(ep1.getEm().find(CorsoStato.class, "43"));
                    } else { //AVVIATO
                        ca1.setStatocorso(ep1.getEm().find(CorsoStato.class, "44"));
                    }
                } catch (Exception ex2) {
                    ca1.setStatocorso(ep1.getEm().find(CorsoStato.class, "43"));
                    EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex2));
                }
                ep1.begin();
                ep1.merge(ca1);
                ep1.commit();
                ep1.close();
                resp_out.addProperty("result",
                        true);
            } else {
                resp_out.addProperty("result",
                        false);
                resp_out.addProperty("message",
                        "CORSO NON TROVATO.");
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

    protected void RICHIEDIAVVIOCORSO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject resp_out = new JsonObject();
//        if (isAdmin(request.getSession())) {
        try {
            String IDCORSO = getRequestValue(request, "IDCORSO");
            EntityOp ep1 = new EntityOp();
            Corsoavviato ca1 = ep1.getEm().find(Corsoavviato.class, Long.valueOf(IDCORSO));
            if (ca1 != null) {

                List<Calendario_Formativo> cal_istanza = ep1.calendario_formativo_corso(ca1.getCorsobase());
                List<Calendario_Lezioni> lezioni = ep1.calendario_lezioni_corso(ca1);
                AtomicDouble ore_cal = new AtomicDouble(0.0);
                AtomicDouble ore_lez = new AtomicDouble(0.0);
                cal_istanza.stream().map(c1 -> c1.getOre()).collect(Collectors.toList()).forEach(c2 -> {
                    ore_cal.addAndGet(c2);
                });
                lezioni.stream().map(c1 -> c1.getOre()).collect(Collectors.toList()).forEach(c2 -> {
                    ore_lez.addAndGet(c2);
                });

                if (ore_cal.get() == ore_lez.get()) {
                    ca1.setStatocorso(ep1.getEm().find(CorsoStato.class, "41"));
                    ep1.begin();
                    ep1.merge(ca1);
                    ep1.commit();
                    ep1.close();
                    resp_out.addProperty("result",
                            true);
                } else {
                    resp_out.addProperty("result",
                            false);
                    resp_out.addProperty("message",
                            "Il numero di ore lezione pianificate (" + Utils.roundDoubleandFormat(ore_lez.get(), 1) + ") non corrisponde al numero di ore previste dal corso (" + Utils.roundDoubleandFormat(ore_cal.get(), 1) + "). Controllare.");
                }
            } else {
                resp_out.addProperty("result",
                        false);
                resp_out.addProperty("message",
                        "CORSO NON TROVATO.");
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

    protected void INSERISCILEZIONE(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            EntityOp ep1 = new EntityOp();
            String CORSO = getRequestValue(request, "idcorsodasalvare");
            String docente = getRequestValue(request, "docente");
            String modulo = getRequestValue(request, "modulo");
            String orai = getRequestValue(request, "orai");
            String oraf = getRequestValue(request, "oraf");
            String tipolezione = getRequestValue(request, "tipolez");
            Date data = sdf_PATTERNDATE6.parse(getRequestValue(request, "data"));
            Calendario_Lezioni cl1 = new Calendario_Lezioni();
            cl1.setCalendarioformativo(ep1.getEm().find(Calendario_Formativo.class, parseLongR(modulo)));
            cl1.setCorsodiriferimento(ep1.getEm().find(Corsoavviato.class, parseLongR(CORSO)));
            cl1.setDocente(ep1.getEm().find(Docente.class, parseLongR(docente)));
            cl1.setDatainserimento(new Date());
            cl1.setDatalezione(data);
            cl1.setOrainizio(orai);
            cl1.setOrafine(oraf);
            cl1.setOre(calcolaore(orai, oraf));
            cl1.setTipolezione(tipolezione);
            ep1.begin();
            ep1.persist(cl1);
            ep1.commit();
            ep1.close();
            redirect(request, response, "US_calendariolezioni.jsp?esito=OK");
        } catch (Exception ex1) {
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            ex1.printStackTrace();
            redirect(request, response, "Page_message.jsp?esito=KO_LEZ1");
        }
    }

    protected void AGGIUNGIALLIEVICORSO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            EntityOp ep1 = new EntityOp();

            String IDCORSO = getRequestValue(request, "IDCORSO");
            String ALLIEVI = getRequestValue(request, "ALLIEVI");

            Corsoavviato ca = ep1.getEm().find(Corsoavviato.class, parseLongR(IDCORSO));

            ObjectMapper om = new ObjectMapper();
            om.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

            ep1.begin();

            List<String> ALLIEVIJsonList = om.readValue(ALLIEVI, new TypeReference<List<String>>() {
            });

            List<Calendario_Lezioni> lezioni = ep1.calendario_lezioni_corso(ca);
            DateTime dt1 = new DateTime(lezioni.get(0).getDatalezione().getTime());
            DateTime dt2 = new DateTime();
            boolean daavviare = dt1.isAfter(dt2);
            for (String a1 : ALLIEVIJsonList) {
                Allievi al1 = ep1.getEm().find(Allievi.class, parseLongR(a1));
                if (al1 != null) {
                    al1.setCorsodiriferimento(ca);
                    if (daavviare) {
                        al1.setStatoallievo(Stati.AVVIO);
                    } else {
                        al1.setStatoallievo(Stati.ATTIVO);
                    }
                    ep1.merge(al1);
                }
            }
            ep1.commit();
            ep1.close();
            redirect(request, response, "Page_message.jsp?esito=OK_CL");
        } catch (Exception ex1) {
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            ex1.printStackTrace();
            redirect(request, response, "Page_message.jsp?esito=KO_NCIN2");
        }

    }

    protected void AVVIANUOVOCORSO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            EntityOp ep1 = new EntityOp();

            String ISTANZA = getRequestValue(request, "ISTANZA");
            String CORSO = getRequestValue(request, "CORSO");
            String DOCENTI = getRequestValue(request, "DOCENTI");
            String ALLIEVI = getRequestValue(request, "ALLIEVI");
            String DIRETTORE = normalizeUTF8(getRequestValue(request, "DIRETTORE"));
            String ALTROP = getRequestValue(request, "ALTROP");

            Corsoavviato ca = new Corsoavviato();
            ca.setCorsobase(ep1.getEm().find(Corso.class, parseLongR(CORSO)));
            ca.setDatafine(sdf_PATTERNDATE6.parse(getRequestValue(request, "DATAFINE")));
            ca.setDatainizio(sdf_PATTERNDATE6.parse(getRequestValue(request, "DATAINIZIO")));

            ca.setDirettorecorso(DIRETTORE);
//            ca.setDirettore(ep1.getEm().find(Altropersonale.class, parseLongR(DIRETTORE)));
            ca.setStatocorso(ep1.getEm().find(CorsoStato.class, "40"));

            ca.setDatainserimento(new DateTime().toDate());

            Istanza is1 = ep1.getEm().find(Istanza.class, parseLongR(ISTANZA));

            ObjectMapper om = new ObjectMapper();
            om.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

            if (is1.equals(ca.getCorsobase().getIstanza())) {
                ep1.begin();
                ep1.persist(ca);
                ep1.commit();
                ep1.close();

                EntityOp ep2 = new EntityOp();
                ep2.begin();
                List<String> DOCENTIJsonList = om.readValue(DOCENTI, new TypeReference<List<String>>() {
                });

                for (String a1 : DOCENTIJsonList) {
                    Docente d1 = ep2.getEm().find(Docente.class, parseLongR(a1));
                    if (d1 != null) {
                        CorsoAvviato_Docenti cad = new CorsoAvviato_Docenti();
                        cad.setIdcorsoavviatodocenti(new CorsoAvviato_DocentiId(d1.getIddocente(), ca.getIdcorsoavviato()));
                        cad.setDocente(d1);
                        cad.setCorsoavviato(ca);
                        ep2.persist(cad);
                    }
                }
                List<String> ALLIEVIJsonList = om.readValue(ALLIEVI, new TypeReference<List<String>>() {
                });

                for (String a1 : ALLIEVIJsonList) {

                    Allievi al1 = ep2.getEm().find(Allievi.class, parseLongR(a1));
                    if (al1 != null) {
                        al1.setCorsodiriferimento(ca);
                        al1.setStatoallievo(Stati.AVVIO);
                        if (ca.getCorsobase().getStageore() > 0) {
                            al1.setStatotirocinio(Stati.AVVIARE);
                        } else {
                            al1.setStatotirocinio(Stati.NONPREVISTO);
                        }
                        ep2.merge(al1);
                    }
                }

                List<String> ALTROPJsonList = om.readValue(ALTROP, new TypeReference<List<String>>() {
                });
                for (String a1 : ALTROPJsonList) {

                    Altropersonale d1 = ep2.getEm().find(Altropersonale.class, parseLongR(a1));
                    if (d1 != null) {
                        CorsoAvviato_AltroPersonale cad = new CorsoAvviato_AltroPersonale();
                        cad.setIdcorsoavviatoaltropersonale(new CorsoAvviato_AltroPersonaleId(d1.getIdaltropersonale(), ca.getIdcorsoavviato()));
                        cad.setAltropersonale(d1);
                        cad.setCorsoavviato(ca);
                        ep2.persist(cad);
                    }
                }

                ep2.commit();
                ep2.close();
                redirect(request, response, "US_gestionecorsi.jsp?esito=OK");
            } else {
                //ERRORE ISTANZA
                redirect(request, response, "Page_message.jsp?esito=KO_NCIN1");
            }

        } catch (Exception ex1) {
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            ex1.printStackTrace();
            redirect(request, response, "Page_message.jsp?esito=KO_NCIN2");
        }
    }

    protected void BO_SALVADATITEMPLATEDECRETOAUT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            EntityOp ep1 = new EntityOp();

            TemplateDecretoAUT td = new TemplateDecretoAUT();
            td.setDIRIGCARICA(normalizeUTF8(getRequestValue(request, "DIRIGCARICA")));
            td.setDIRIGNOME(normalizeUTF8(getRequestValue(request, "DIRIGNOME")));
            td.setFUNZCARICA(normalizeUTF8(getRequestValue(request, "FUNZCARICA")));
            td.setFUNZNOME(normalizeUTF8(getRequestValue(request, "FUNZNOME")));
            td.setNOMESERVIZIO(normalizeUTF8(getRequestValue(request, "NOMESERVIZIO")));
            td.setVISTO1(normalizeUTF8(getRequestValue(request, "VISTO1")));
            td.setNomeautore(request.getSession().getAttribute("us_cod").toString());
            td.setDATAINSERIMENTO(new DateTime().toDate());
            ep1.begin();
            ep1.persist(td);
            ep1.commit();
            ep1.close();

            redirect(request, response, "ADM_backoffice.jsp?ESITO=OK1");

        } catch (Exception ex1) {
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            redirect(request, response, "ADM_backoffice.jsp?ESITO=KO1");
        }

    }

    protected void BO_EDIT_TIPOPERCORSO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            EntityOp ep1 = new EntityOp();
            Tipologia_Percorso tc = ep1.getEm().find(Tipologia_Percorso.class, Long.valueOf(getRequestValue(request, "idptipop")));
            String DESCRIZIONE = normalizeUTF8(getRequestValue(request, "DESCRIZIONE"));
            tc.setDataend(sdf_PATTERNDATE6.parse(getRequestValue(request, "DATAFINE")));
            tc.setDatastart(sdf_PATTERNDATE6.parse(getRequestValue(request, "DATAINIZIO")));
            tc.setNometipologia(DESCRIZIONE);
            tc.setMaxcorsi(parseIntR(getRequestValue(request, "MAXCORSI")));
            tc.setMaxedizioni(parseIntR(getRequestValue(request, "MAXEDIZIONI")));
            ep1.begin();
            ep1.merge(tc);
            ep1.commit();
            ep1.close();
            redirect(request, response, "Page_message.jsp?esito=OK_UPAL");

        } catch (Exception ex1) {
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            redirect(request, response, "Page_message.jsp?esito=KO_NTP1");
        }

    }

    protected void BO_ADDTIPOPERCORSO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            EntityOp ep1 = new EntityOp();
//            SoggettoProponente so = ((User) request.getSession().getAttribute("us_memory")).getSoggetto();

            String TIPOPERCORSO = getRequestValue(request, "TIPOPERCORSO");
            String DESCRIZIONE = normalizeUTF8(getRequestValue(request, "DESCRIZIONE"));

            Tipologia_Percorso tc = new Tipologia_Percorso();
            tc.setDataend(sdf_PATTERNDATE6.parse(getRequestValue(request, "DATAFINE")));
            tc.setDatastart(sdf_PATTERNDATE6.parse(getRequestValue(request, "DATAINIZIO")));
            tc.setNometipologia(DESCRIZIONE);
            tc.setTipocorso(TipoCorso.valueOf(TIPOPERCORSO));
            tc.setStatotipologiapercorso(Stati.ABILITATO);
            tc.setMaxcorsi(parseIntR(getRequestValue(request, "MAXCORSI")));
            tc.setMaxedizioni(parseIntR(getRequestValue(request, "MAXEDIZIONI")));
            ep1.begin();
            ep1.persist(tc);
            ep1.commit();
            ep1.close();
            redirect(request, response, "Page_message.jsp?esito=OK_UPAL");

        } catch (Exception ex1) {
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            redirect(request, response, "Page_message.jsp?esito=KO_NTP1");
        }

    }

    protected void ADDSEDESTAGETIROCINIOENTE(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            EntityOp ep1 = new EntityOp();
//            SoggettoProponente so = ((User) request.getSession().getAttribute("us_memory")).getSoggetto();

            EnteStage es = ep1.getEm().find(EnteStage.class, Long.valueOf(getRequestValue(request, "idente")));

            String SF_INDIRIZZO = normalizeUTF8(getRequestValue(request, "SF_INDIRIZZO"));
            String SF_CAP = normalizeUTF8(getRequestValue(request, "SF_CAP"));
            String SF_COMUNE = normalizeUTF8(getRequestValue(request, "SF_COMUNE"));
            String SF_PROVINCIA = normalizeUTF8(getRequestValue(request, "SF_PROVINCIA"));

            Sede ss1 = new Sede();
            ss1.setCap(SF_CAP);
            ss1.setComune(SF_COMUNE);
            ss1.setIndirizzo(SF_INDIRIZZO);
            ss1.setProvincia(SF_PROVINCIA);
            ss1.setEntestage(es);
            ss1.setTipo(ep1.getEm().find(TipoSede.class, 4L));

            ep1.begin();
            ep1.persist(ss1);
            ep1.commit();
            ep1.close();
            redirect(request, response, "Page_message.jsp?esito=OK_UPAL");

        } catch (Exception ex1) {
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            redirect(request, response, "Page_message.jsp?esito=KO_NSED1");
        }
    }

    protected void ADDENTEOSPITANTE(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            EntityOp ep1 = new EntityOp();
            SoggettoProponente so = ((User) request.getSession().getAttribute("us_memory")).getSoggetto();
            String RAGIONESOCIALE = normalizeUTF8(getRequestValue(request, "RAGIONESOCIALE"));
            String PIVA = getRequestValue(request, "PIVA");
            String ATECO = getRequestValue(request, "ATECO");
            String SL_INDIRIZZO = normalizeUTF8(getRequestValue(request, "SL_INDIRIZZO"));
            String SL_CAP = normalizeUTF8(getRequestValue(request, "SL_CAP"));
            String SL_COMUNE = normalizeUTF8(getRequestValue(request, "SL_COMUNE"));
            String SL_PROVINCIA = normalizeUTF8(getRequestValue(request, "SL_PROVINCIA"));
            String COGNOME = normalizeUTF8(getRequestValue(request, "COGNOME"));
            String NOME = normalizeUTF8(getRequestValue(request, "NOME"));
            String SF_INDIRIZZO = normalizeUTF8(getRequestValue(request, "SF_INDIRIZZO"));
            String SF_CAP = normalizeUTF8(getRequestValue(request, "SF_CAP"));
            String SF_COMUNE = normalizeUTF8(getRequestValue(request, "SF_COMUNE"));
            String SF_PROVINCIA = normalizeUTF8(getRequestValue(request, "SF_PROVINCIA"));

            EnteStage es = new EnteStage();
            es.setPARTITAIVA(PIVA);
            es.setRAGIONESOCIALE(RAGIONESOCIALE);
            es.setCODICEATECO(ATECO);
            es.setRap_cognome(COGNOME);
            es.setRap_nome(NOME);
            es.setSoggetto(so);
            es.setStatoente(Stati.ABILITATO);

            Sede sl1 = new Sede();
            sl1.setCap(SL_CAP);
            sl1.setComune(SL_COMUNE);
            sl1.setIndirizzo(SL_INDIRIZZO);
            sl1.setProvincia(SL_PROVINCIA);
            sl1.setEntestage(es);
            sl1.setTipo(ep1.getEm().find(TipoSede.class, 1L));

            Sede ss1 = new Sede();
            ss1.setCap(SF_CAP);
            ss1.setComune(SF_COMUNE);
            ss1.setIndirizzo(SF_INDIRIZZO);
            ss1.setProvincia(SF_PROVINCIA);
            ss1.setEntestage(es);
            ss1.setTipo(ep1.getEm().find(TipoSede.class, 4L));

            if (ep1.esisteEnteStageSoggetto(so, PIVA)) {
                redirect(request, response, "Page_message.jsp?esito=KO_NEN2");
            } else {
                ep1.begin();
                ep1.persist(es);
                ep1.persist(sl1);
                ep1.persist(ss1);
                es.setSedelegale(sl1);
                ep1.merge(es);
                ep1.commit();
                ep1.close();
                redirect(request, response, "Page_message.jsp?esito=OK_UPAL");
            }

        } catch (Exception ex1) {
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            redirect(request, response, "Page_message.jsp?esito=KO_NEN2");
        }

    }

    protected void ADDPRESIDENTE(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            if (isAdmin(request.getSession())) {
                String COGNOME = normalizeUTF8(getRequestValue(request, "COGNOME"));
                String NOME = normalizeUTF8(getRequestValue(request, "NOME"));
                String QUALIFICA = normalizeUTF8(getRequestValue(request, "QUALIFICA"));
                String DIPARTIMENTO = normalizeUTF8(getRequestValue(request, "DIPARTIMENTO"));
                String AREA = normalizeUTF8(getRequestValue(request, "AREA"));
                String EMAIL = normalizeUTF8(getRequestValue(request, "EMAIL"));

                PresidenteCommissione pc = new PresidenteCommissione();
                pc.setCognome(COGNOME);
                pc.setNome(NOME);
                pc.setQualifica(QUALIFICA);
                pc.setDipartimento(DIPARTIMENTO);
                pc.setAreaservizio(AREA);
                pc.setMail(EMAIL);
                pc.setDatainserimento(new DateTime().toDate());
                pc.setStatopresidente(Stati.ATTIVO);

                EntityOp ep1 = new EntityOp();
                ep1.begin();
                ep1.persist(pc);
                ep1.commit();
                ep1.close();

                String username = createUsername(NOME, COGNOME);
                String psw_plain = createPassword();

                User uspres = new User();
                uspres.setUsername(username);
                uspres.setPassword(md5Hex(psw_plain));
                uspres.setEmail(EMAIL);
                uspres.setCreationdate(new DateTime().toDate());
                uspres.setPresidente(pc);
                uspres.setStato(1);
                uspres.setTipo(3);

                EntityOp ep2 = new EntityOp();
                ep2.begin();
                ep2.persist(uspres);
                ep2.commit();
                ep2.close();

                //sendmail
                redirect(request, response, "Page_message.jsp?esito=OK_MOSD");

            } else {
                redirect(request, response, "Page_message.jsp?esito=KO_INPR1");
            }
        } catch (Exception ex1) {
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            redirect(request, response, "Page_message.jsp?esito=KO_INPR2");
        }

    }

    protected void ADDALLIEVO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            SoggettoProponente so = ((User) request.getSession().getAttribute("us_memory")).getSoggetto();

            String COGNOME = normalizeUTF8(getRequestValue(request, "COGNOME"));
            String NOME = normalizeUTF8(getRequestValue(request, "NOME"));
            String CODICEFISCALE = normalizeUTF8(getRequestValue(request, "CODICEFISCALE"));
            String TELEFONO = StringUtils.replace(getRequestValue(request, "TELEFONO"), "_", "");
            String EMAIL = normalizeUTF8(getRequestValue(request, "EMAIL"));
            String DOCID = normalizeUTF8(getRequestValue(request, "DOCID"));
            String TITSTUDIO = normalizeUTF8(getRequestValue(request, "TITSTUDIO"));
            boolean CATPROT = getRequestValue(request, "CATPROT").equals("on");

            Allievi al1 = new Allievi();
            al1.setCognome(COGNOME);
            al1.setNome(NOME);
            al1.setCodicefiscale(CODICEFISCALE);
            al1.setTelefono(TELEFONO);
            al1.setEmail(EMAIL);
            al1.setNumdocid(DOCID);
            al1.setDatadocid(sdf_PATTERNDATE6.parse(getRequestValue(request, "DATEDOCID")));
            al1.setSoggetto(so);
            al1.setTitolostudio(TITSTUDIO);
            al1.setCatprot(CATPROT);
            al1.setStatoallievo(Stati.CHECK);
            al1.setDatainserimento(new DateTime().toDate());

            Utils.ricavaDatidaCF(al1);

            EntityOp ep1 = new EntityOp();

            if (ep1.esisteAllievoCF(CODICEFISCALE)) {
                redirect(request, response, "Page_message.jsp?esito=KO_NAL2");
            } else {
                ep1.begin();
                ep1.persist(al1);
                ep1.commit();
                ep1.close();
                redirect(request, response, "Page_message.jsp?esito=OK_UPAL");
            }

        } catch (Exception ex1) {
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            redirect(request, response, "Page_message.jsp?esito=KO_NAL1");

        }

    }

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

//            String utentecaricamento = (String) request.getSession().getAttribute("us_cod");
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
                                c2.setIdentificativocorso("ID" + ID + "/CS" + CS + "/ED" + ED);
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

    protected void RIGETTACORSO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isAdmin(request.getSession())) {
            String utentecaricamento = (String) request.getSession().getAttribute("us_cod");
            String idcorso = getRequestValue(request, "idcorso");

            EntityOp ep1 = new EntityOp();

            try {
                Corsoavviato is1 = ep1.getEm().find(Corsoavviato.class, Long.valueOf(idcorso));
                if (is1 != null) {
                    boolean soccorsoistr = getRequestValue(request, "soccorsoistr").equalsIgnoreCase("on");
                    String motivazione = getRequestValue(request, "motivazione");
                    List<Allegati> la = ep1.list_allegati(null, null, is1, null, null, null);
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
                        is1.setStatocorso(ep1.getEm().find(CorsoStato.class, "42"));
                        //MAIL CON SOCCORSO
                        //SendMail.inviaNotificaSP_rigettoIstanzaSOCCORSO(ep1, is1, motivazione, idko);
                    } else {
                        is1.setStatocorso(ep1.getEm().find(CorsoStato.class, "09"));
                        //MAIL SOLO RIGETTO
                        //SendMail.inviaNotificaSP_rigettoIstanza(ep1, is1, motivazione);
                    }
                    ep1.merge(is1);

                    Information info1 = new Information();
                    info1.setDatacreazione(new Date());
                    info1.setCorsoavviato(is1);
                    info1.setMotivazione(motivazione);
                    info1.setUtente(utentecaricamento);
                    ep1.persist(info1);
                    ep1.commit();
                    ep1.close();
                    redirect(request, response, "Page_message.jsp?esito=OKRI_IS1");
                } else {
                    redirect(request, response, "Page_message.jsp?esito=KORI_CO1");
                }
            } catch (Exception ex1) {
                EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
                redirect(request, response, "Page_message.jsp?esito=KORI_CO2");
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
                    List<Allegati> la = ep1.list_allegati(is1, null, null, null, null, null);

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
//        if (isAdmin(request.getSession())) {

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
//        } else {
//            redirect(request, response, "404.jsp");
//        }

    }

    protected void UPLDECAUT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isAdmin(request.getSession())) {

            try {

                EntityOp ep1 = new EntityOp();
//                String utentecaricamento = (String) request.getSession().getAttribute("us_cod");
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
                        switch (item.getFieldName()) {
                            case "idist":
                                IDIST = item.getString();
                                break;
                            case "DDSNUMERO":
                                DDSNUMERO = normalizeUTF8(normalize(item.getString()));
                                break;
                            case "DDSDATA":
                                DDSDATA = normalizeUTF8(normalize(item.getString()));
                                break;
                            default:
                                break;
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

    protected void UPLDOCCORSO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            EntityOp ep1 = new EntityOp();
            String utentecaricamento = (String) request.getSession().getAttribute("us_cod");
            Path pathtemp = ep1.getEm().find(Path.class, "path.temp");
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            File nomefile = null;
            List<FileItem> items = upload.parseRequest(request);
            Iterator<FileItem> iterator = items.iterator();
            String IDCORSO = null;
            String DESCRIZIONE = null;
            String MIME = null;
            String codiceDOC = generateIdentifier(6);
            while (iterator.hasNext()) {
                FileItem item = (FileItem) iterator.next();
                if (item.isFormField()) {
                    if (item.getFieldName().equals("idcorsoavviato")) {
                        IDCORSO = item.getString();
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

                if (IDCORSO != null) {
                    request.getSession().setAttribute("ses_idcorso", Utils.enc_string(IDCORSO));
                    Corsoavviato all0 = ep1.getEm().find(Corsoavviato.class, Long.valueOf(IDCORSO));
                    if (all0 != null) {
                        Allegati al1 = new Allegati();
                        al1.setCorsoavviato(all0);
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
                        redirect(request, response, "US_allegaticorso.jsp");
                    } else {
                        redirect(request, response, "Page_message.jsp?esito=KOUP_CA1");
                    }
                } else {
                    redirect(request, response, "Page_message.jsp?esito=KOUP_CA2");
                }
            } else {
                redirect(request, response, "Page_message.jsp?esito=KOUP_IS3");
            }

        } catch (Exception ex1) {
            ex1.printStackTrace();
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            redirect(request, response, "Page_message.jsp?esito=KOUP_IS4");
        }
    }

    protected void UPLDOCENTE(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            String IDENTE = null;
            String DESCRIZIONE = null;
            String MIME = null;
            String codiceDOC = generateIdentifier(6);
            while (iterator.hasNext()) {
                FileItem item = (FileItem) iterator.next();
                if (item.isFormField()) {
                    if (item.getFieldName().equals("idente")) {
                        IDENTE = item.getString();
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

                if (IDENTE != null) {
                    request.getSession().setAttribute("ses_idente", Utils.enc_string(IDENTE));
                    EnteStage all0 = ep1.getEm().find(EnteStage.class, Long.valueOf(IDENTE));
                    if (all0 != null) {

                        Allegati al1 = new Allegati();
                        al1.setEntestage(all0);
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
                        redirect(request, response, "US_allegatiente.jsp");
                    } else {
                        redirect(request, response, "Page_message.jsp?esito=KOUP_EN1");
                    }
                } else {
                    redirect(request, response, "Page_message.jsp?esito=KOUP_EN2");
                }
            } else {
                redirect(request, response, "Page_message.jsp?esito=KOUP_IS3");
            }

        } catch (Exception ex1) {
            ex1.printStackTrace();
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            redirect(request, response, "Page_message.jsp?esito=KOUP_IS4");
        }
    }

    protected void UPLDOCALLIEVO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            String IDALLIEVO = null;
            String DESCRIZIONE = null;
            String MIME = null;
            String codiceDOC = generateIdentifier(6);
            while (iterator.hasNext()) {
                FileItem item = (FileItem) iterator.next();
                if (item.isFormField()) {
                    if (item.getFieldName().equals("idallievo")) {
                        IDALLIEVO = item.getString();
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

                if (IDALLIEVO != null) {
                    request.getSession().setAttribute("ses_idallievo", Utils.enc_string(IDALLIEVO));
                    Allievi all0 = ep1.getEm().find(Allievi.class, Long.valueOf(IDALLIEVO));
                    if (all0 != null) {

                        Allegati al1 = new Allegati();
                        al1.setAllievi(all0);
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
                        redirect(request, response, "US_allegatiallievi.jsp");
                    } else {
                        redirect(request, response, "Page_message.jsp?esito=KOUP_AL1");
                    }
                } else {
                    redirect(request, response, "Page_message.jsp?esito=KOUP_AL2");
                }
            } else {
                redirect(request, response, "Page_message.jsp?esito=KOUP_IS3");
            }

        } catch (Exception ex1) {
            ex1.printStackTrace();
            EntityOp.trackingAction(request.getSession().getAttribute("us_cod").toString(), estraiEccezione(ex1));
            redirect(request, response, "Page_message.jsp?esito=KOUP_IS4");
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

    protected void DELETEALLIEVO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject resp_out = new JsonObject();
        try {

            EntityOp ep1 = new EntityOp();
            Allievi al1 = ep1.getEm().find(Allievi.class, Long.valueOf(getRequestValue(request, "IDALLIEVO")));

            if (al1 != null) {
                ep1.begin();
                al1.setStatoallievo(Stati.INATTIVO);
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

    protected void CONVALIDAPRESENZATIROCINIO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject resp_out = new JsonObject();
        try {
            EntityOp ep1 = new EntityOp();
            Presenze_Tirocinio_Allievi is1 = ep1.getEm().find(Presenze_Tirocinio_Allievi.class, Long.valueOf(getRequestValue(request, "IDPRESENZA")));
            if (is1 != null) {
                ep1.begin();
                is1.setStatolezione(ep1.getEm().find(CorsoStato.class, "61"));
                ep1.merge(is1);
                ep1.commit();
                ep1.close();
                resp_out.addProperty("result",
                        true);
            } else {
                resp_out.addProperty("result",
                        false);
                resp_out.addProperty("message",
                        "PRESENZA TIROCINIO NON TROVATA.");
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

    protected void CONVALIDALEZIONE(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JsonObject resp_out = new JsonObject();

        try {

            EntityOp ep1 = new EntityOp();
            Calendario_Lezioni is1 = ep1.getEm().find(Calendario_Lezioni.class, Long.valueOf(getRequestValue(request, "IDLEZIONE")));

            if (is1 != null) {
                ep1.begin();
                is1.setStatolezione(ep1.getEm().find(CorsoStato.class, "61"));
                ep1.merge(is1);
                ep1.commit();
                ep1.close();
                resp_out.addProperty("result",
                        true);
            } else {
                resp_out.addProperty("result",
                        false);
                resp_out.addProperty("message",
                        "LEZIONE NON TROVATA.");
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

    protected void DELETEPRESENZATIROCINIO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject resp_out = new JsonObject();

        try {

            EntityOp ep1 = new EntityOp();
            Presenze_Tirocinio_Allievi is1 = ep1.getEm().find(Presenze_Tirocinio_Allievi.class,
                    Long.valueOf(getRequestValue(request, "IDPRESENZA")));
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
                        "PRESENZA TIROCINIO NON TROVATA.");
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

    protected void DELETELEZIONE(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JsonObject resp_out = new JsonObject();

        try {

            EntityOp ep1 = new EntityOp();
            Calendario_Lezioni is1 = ep1.getEm().find(Calendario_Lezioni.class, Long.valueOf(getRequestValue(request, "IDLEZIONE")));

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
                        "LEZIONE NON TROVATA.");
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
                ct.setOre_teorica_aula(OREAULATEO);
                ct.setOre_tecnica_operativa(OREAULATEC);
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

            double min_oretec = Utils.calcolaPercentuale(String.valueOf(co1.getDurataore()), String.valueOf(60));

            //  double mx_oreteo = Utils.calcolaPercentuale(String.valueOf(co1.getDurataore()), String.valueOf(60));
            //  double mx_oretec = Utils.calcolaPercentuale(String.valueOf(co1.getDurataore()), String.valueOf(40));
            //  double oremaxlearn = calcolaPercentuale(String.valueOf(co1.getDurataore()), String.valueOf(co1.getElearningperc()));
            double oremaxlearn = fd(String.valueOf(co1.getElearningperc()));
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

            boolean check_tecnic = dapian - (ore_pteo.get() + orelearning.get() + OREAULATEO + OREELE) >= (min_oretec - ore_ptec.get() - OREAULATEC);
//            boolean check_teoria = ore_pteo.get() + OREAULATEO <= mx_oreteo;
//            boolean check_tecnic = ore_ptec.get() + OREAULATEC <= mx_oretec;
            if (!check_oretotali) {
                resp_out.addProperty("result", false);
                resp_out.addProperty("message", "Ore superiori a quelle consentite.<br>Ore residue da pianificare: " + roundDoubleandFormat(dapian, 0));
//            } else if (!check_teoria) {
//                resp_out.addProperty("result", false);
//                resp_out.addProperty("message",
//                        "Ore di aula teorica superiori al massimo consentito (60% del totale corso). Massime ore di questa tipologia pianificabili: " + (mx_oreteo - ore_pteo.get()));
            } else if (!check_tecnic) {
                resp_out.addProperty("result", false);
                resp_out.addProperty("message",
                        "E' necessario che le ore pianificate di tipo TECNICO/OPERATIVA siano almeno il 60% del totale del corso (" + roundDoubleandFormat(min_oretec, 0) + " SU " + String.valueOf(co1.getDurataore()) + "). Controllare.");
            } else if (!check_elearning) {
                resp_out.addProperty("result", false);
                resp_out.addProperty("message", "Ore e-learning superiori a quelle consentite.<br>Ore residue e-learning da pianificare: " + roundDoubleandFormat(dapianEL, 0));
            } else if (!check_sum) {
                resp_out.addProperty("result", false);
                resp_out.addProperty("message", "La somma delle ore inserite ORE AULA TEORICA + ORE AULA TECNICO/OPERATIVA + ORE E-LEARNING ("
                        + roundDoubleandFormat((OREAULATEO + OREAULATEC + OREELE), 0) + ") non corrisponde alle ORE TOTALI (" + roundDoubleandFormat(ORETOTALI, 0) + ") inserite . Controllare.");
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
                case "DELETELEZIONE":
                    DELETELEZIONE(request, response);
                    break;
                case "DELETEPRESENZATIROCINIO":
                    DELETEPRESENZATIROCINIO(request, response);
                    break;
                case "CONVALIDALEZIONE":
                    CONVALIDALEZIONE(request, response);
                    break;
                case "CONVALIDAPRESENZATIROCINIO":
                    CONVALIDAPRESENZATIROCINIO(request, response);
                    break;
                case "DELETEDOCUMENT":
                    DELETEDOCUMENT(request, response);
                    break;
                case "DELETEALLIEVO":
                    DELETEALLIEVO(request, response);
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
                case "UPLDOCALLIEVO":
                    UPLDOCALLIEVO(request, response);
                    break;
                case "UPLDOCENTE":
                    UPLDOCENTE(request, response);
                    break;
                case "UPLDOCCORSO":
                    UPLDOCCORSO(request, response);
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
                case "RIGETTACORSO":
                    RIGETTACORSO(request, response);
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
                case "ADDALLIEVO":
                    ADDALLIEVO(request, response);
                    break;
                case "ADDPRESIDENTE":
                    ADDPRESIDENTE(request, response);
                    break;
                case "ADDENTEOSPITANTE":
                    ADDENTEOSPITANTE(request, response);
                    break;
                case "ADDSEDESTAGETIROCINIOENTE":
                    ADDSEDESTAGETIROCINIOENTE(request, response);
                    break;
                case "BO_ADDTIPOPERCORSO":
                    BO_ADDTIPOPERCORSO(request, response);
                    break;
                case "BO_EDIT_TIPOPERCORSO":
                    BO_EDIT_TIPOPERCORSO(request, response);
                    break;
                case "BO_SALVADATITEMPLATEDECRETOAUT":
                    BO_SALVADATITEMPLATEDECRETOAUT(request, response);
                    break;
                case "AVVIANUOVOCORSO":
                    AVVIANUOVOCORSO(request, response);
                    break;
                case "AGGIUNGIALLIEVICORSO":
                    AGGIUNGIALLIEVICORSO(request, response);
                    break;
                case "INSERISCILEZIONE":
                    INSERISCILEZIONE(request, response);
                    break;
                case "RICHIEDIAVVIOCORSO":
                    RICHIEDIAVVIOCORSO(request, response);
                    break;
                case "AUTORIZZAAVVIOCORSO":
                    AUTORIZZAAVVIOCORSO(request, response);
                    break;
                case "AUTORIZZACAMBIOSEDE":
                    AUTORIZZACAMBIOSEDE(request, response);
                    break;
                case "IMPOSTADESIGNAZIONE":
                    IMPOSTADESIGNAZIONE(request, response);
                    break;
                case "MODIFICADIRETTORE":
                    MODIFICADIRETTORE(request, response);
                    break;
                case "MODIFICASEDECORSO":
                    MODIFICASEDECORSO(request, response);
                    break;
                case "MODIFICADOCENTICORSO":
                    MODIFICADOCENTICORSO(request, response);
                    break;
                case "MODIFICAPERSONALECORSO":
                    MODIFICAPERSONALECORSO(request, response);
                    break;
                case "CAMBIASTATOALLIEVO":
                    CAMBIASTATOALLIEVO(request, response);
                    break;
                case "AGGIUNGIPRESENZELEZIONE":
                    AGGIUNGIPRESENZELEZIONE(request, response);
                    break;
                case "MODIFICALEZIONE":
                    MODIFICALEZIONE(request, response);
                    break;
                case "AVVIATIROCINIOALLIEVO":
                    AVVIATIROCINIOALLIEVO(request, response);
                    break;
                case "INSERISCIPRESENZATIROCINIO":
                    INSERISCIPRESENZATIROCINIO(request, response);
                    break;
                case "RICHIEDINOMINACOMMISSIONE":
                    RICHIEDINOMINACOMMISSIONE(request, response);
                    break;
                case "APPROVACOMMISSIONE":
                    APPROVACOMMISSIONE(request, response);
                    break;
                case "RIGETTACOMMISSIONE":
                    RIGETTACOMMISSIONE(request, response);
                    break;
                case "NOMINAPRESIDENTECOMMISSIONE":
                    NOMINAPRESIDENTECOMMISSIONE(request, response);
                    break;
                case "ESAMIFINALI":
                    ESAMIFINALI(request, response);
                    break;
                case "PDFVERBALE":
                    PDFVERBALE(request, response);
                    break;
                case "ATTESTATI":
                    ATTESTATI(request, response);
                    break;
                default: {
                    String p = request.getContextPath();
                    request.getSession().invalidate();
                    redirect(request, response, p);
                }
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
