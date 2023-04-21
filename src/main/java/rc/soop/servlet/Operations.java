/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package rc.soop.servlet;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.String.format;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import rc.soop.sic.Constant;
import static rc.soop.sic.Constant.LOGGER;
import static rc.soop.sic.Constant.PATTERNDATE3;
import static rc.soop.sic.Constant.PATTERNDATE4;
import static rc.soop.sic.Constant.PATTERNDATE5;
import static rc.soop.sic.Constant.sdf_PATTERNDATE6;
import rc.soop.sic.Pdf;
import static rc.soop.sic.Pdf.GENERADECRETOAPPROVATIVO;
import static rc.soop.sic.Pdf.checkFirmaQRpdfA;
import rc.soop.sic.Utils;
import static rc.soop.sic.Utils.estraiEccezione;
import static rc.soop.sic.Utils.generaId;
import static rc.soop.sic.Utils.getRequestValue;
import static rc.soop.sic.Utils.parseIntR;
import static rc.soop.sic.Utils.parseLongR;
import static rc.soop.sic.Utils.redirect;
import rc.soop.sic.jpa.Certificazione;
import rc.soop.sic.jpa.Corso;
import rc.soop.sic.jpa.CorsoStato;
import rc.soop.sic.jpa.Corsoavviato;
import rc.soop.sic.jpa.EntityOp;
import rc.soop.sic.jpa.Istanza;
import rc.soop.sic.jpa.Livello_Certificazione;
import rc.soop.sic.jpa.Path;
import rc.soop.sic.jpa.Sede;
import rc.soop.sic.jpa.SoggettoProponente;
import rc.soop.sic.jpa.User;

/**
 *
 * @author Raffaele
 */
public class Operations extends HttpServlet {

    protected void UPLISTA1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("is_qrerror", "");
        String codiceISTANZA = null;
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            try {
                EntityOp e = new EntityOp();
                SoggettoProponente so = ((User) request.getSession().getAttribute("us_memory")).getSoggetto();
                Path pathtemp = e.getEm().find(Path.class, "path.temp");
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                File nomefile = null;
                List<FileItem> items = upload.parseRequest(request);
                Iterator<FileItem> iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    if (item.isFormField()) {
                        if (item.getFieldName().equals("codice_istanza")) {
                            codiceISTANZA = item.getString();
                        }
                    } else {
                        String fileName = item.getName();
                        String estensione = fileName.substring(fileName.lastIndexOf("."));
                        String nome = "ISTANZA_" + new DateTime().toString(PATTERNDATE3) + "_SIGN_" + RandomStringUtils.randomAlphabetic(15) + estensione;
                        nomefile = new File(pathtemp.getDescrizione() + nome);
                        try {
                            item.write(nomefile);
                        } catch (Exception ex) {
                            LOGGER.severe(estraiEccezione(ex));
                            nomefile = null;
                        }
                    }
                }

                if (nomefile != null && codiceISTANZA != null) {
                    //CONTROLLO QRCODE
                    //CONTROLLO FIRMA DIGITALE
                    try {
                        Path qrcrop = e.getEm().find(Path.class, "pdf.qr.crop");
                        String res = checkFirmaQRpdfA("ISTANZA", codiceISTANZA,
                                nomefile, so.getRap_cf(), qrcrop.getDescrizione());
                        if (!res.equals("OK")) {
                            request.getSession().setAttribute("is_qrerror", res);
                            redirect(request, response, "US_upload.jsp?codice_istanza=" + codiceISTANZA + "&esito=KO5");
                        } else {
                            try {
                                Istanza is = e.getIstanza(so, codiceISTANZA);
                                e.begin();
                                is.setPathfirmato(nomefile.getPath());
                                e.merge(is);
                                e.commit();
                                e.close();
                                request.getSession().setAttribute("is_memory", is);
                                redirect(request, response, "US_upload.jsp?codice_istanza=" + codiceISTANZA + "&esito=OK");
                            } catch (Exception ex1) {
                                LOGGER.severe(estraiEccezione(ex1));
                                redirect(request, response, "US_upload.jsp?codice_istanza=" + codiceISTANZA + "&esito=KO6");
                            }
                        }
                    } catch (Exception ex0) {
                        LOGGER.severe(estraiEccezione(ex0));
                        redirect(request, response, "US_upload.jsp?codice_istanza=" + codiceISTANZA + "&esito=KO2");
                    }
                } else {
                    redirect(request, response, "US_upload.jsp?codice_istanza=" + codiceISTANZA + "&esito=KO1");
                }
            } catch (Exception ex) {
                LOGGER.severe(estraiEccezione(ex));
                redirect(request, response, "US_upload.jsp?codice_istanza=" + codiceISTANZA + "&esito=KO3");
            }
        } else {
            redirect(request, response, "US_upload.jsp?codice_istanza=" + codiceISTANZA + "&esito=KO4");
        }
    }

    protected void SENDISTANZA(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            SoggettoProponente so = ((User) request.getSession().getAttribute("us_memory")).getSoggetto();
            String codiceis = getRequestValue(request, "codice_istanza");
            EntityOp e = new EntityOp();
            Istanza is = e.getIstanza(so, codiceis);
            e.begin();
            is.setStatocorso(e.getEm().find(CorsoStato.class, "07"));
            is.setDatainvio(new DateTime().toString(PATTERNDATE5));
            e.merge(is);
            e.commit();
            e.close();
            request.getSession().setAttribute("is_memory", is);
            redirect(request, response, "US_gestioneistanza.jsp");
        } catch (Exception ex) {
            Constant.LOGGER.severe(Utils.estraiEccezione(ex));
            redirect(request, response, "US_gestioneistanza.jsp?esito=KO");
        }
    }

    protected void APPROVAISTANZA(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Utils.printRequest(request);
            String codiceis = getRequestValue(request, "codice_istanza");
            EntityOp e = new EntityOp();
            Istanza is = e.getIstanza(codiceis);
            File pdf = GENERADECRETOAPPROVATIVO(is);
            if (pdf != null) {
                e.begin();
                is.setStatocorso(e.getEm().find(CorsoStato.class, "08"));
                is.setDecreto(pdf.getPath());
                is.setDatagestione(new DateTime().toString(PATTERNDATE5));
                e.merge(is);
                e.flush();
                e.commit();
                e.close();
                redirect(request, response, "ADM_istanze.jsp?esito=APPROVED");
            } else {
                redirect(request, response, "ADM_istanze.jsp?esito=KO1");
            }
        } catch (Exception ex) {
            Constant.LOGGER.severe(Utils.estraiEccezione(ex));
            redirect(request, response, "ADM_istanze.jsp?esito=KO");
        }
    }

    protected void ELIMINAISTANZAFIRMATA(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            SoggettoProponente so = ((User) request.getSession().getAttribute("us_memory")).getSoggetto();
            String codiceis = getRequestValue(request, "codice_istanza");
            EntityOp e = new EntityOp();

            Istanza is = e.getIstanza(so, codiceis);

            e.begin();
            is.setPathfirmato(null);
            e.merge(is);
            e.commit();
            e.close();
            request.getSession().setAttribute("is_memory", is);
            redirect(request, response, "US_gestioneistanza.jsp");
        } catch (Exception ex) {
            Constant.LOGGER.severe(Utils.estraiEccezione(ex));
            redirect(request, response, "US_gestioneistanza.jsp?esito=KO");
        }
    }

    protected void SCARICAPDFTEST(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            EntityOp e = new EntityOp();
            File cons2 = new File(e.getEm().find(Path.class, "pdf.temp").getDescrizione());
            if (cons2.exists() && cons2.canRead()) {
                String mimeType = Files.probeContentType(cons2.toPath());
                if (mimeType == null) {
                    mimeType = "application/pdf";
                }
                response.setContentType(mimeType);
                String headerKey = "Content-Disposition";
                String headerValue = format("attach; filename=\"%s\"", cons2.getName());
                response.setHeader(headerKey, headerValue);
                response.setContentLength(-1);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(FileUtils.readFileToByteArray(cons2));
                }
            }

        } catch (Exception ex) {
            Constant.LOGGER.severe(Utils.estraiEccezione(ex));
            redirect(request, response, "ADM_visdco.jsp?esito=KO");
        }
    }

    protected void SCARICADECRETOISTANZA(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            SoggettoProponente so = ((User) request.getSession().getAttribute("us_memory")).getSoggetto();

            String codiceis = getRequestValue(request, "codice_istanza");
            EntityOp e = new EntityOp();
            Istanza is;
            if (so == null) {
                is = e.getIstanza(codiceis);
            } else {
                is = e.getIstanza(so, codiceis);
            }
            File cons2 = new File(is.getDecreto());
            if (cons2.exists() && cons2.canRead()) {
                String mimeType = Files.probeContentType(cons2.toPath());
                if (mimeType == null) {
                    mimeType = "application/pdf";
                }
                response.setContentType(mimeType);
                String headerKey = "Content-Disposition";
                String headerValue = format("attach; filename=\"%s\"", cons2.getName());
                response.setHeader(headerKey, headerValue);
                response.setContentLength(-1);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(FileUtils.readFileToByteArray(cons2));
                }
            }
        } catch (Exception ex) {
            Constant.LOGGER.severe(Utils.estraiEccezione(ex));
            redirect(request, response, "US_gestioneistanza.jsp?esito=KO");
        }
    }

    protected void SCARICAISTANZAFIRMATA(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            SoggettoProponente so = ((User) request.getSession().getAttribute("us_memory")).getSoggetto();

            String codiceis = getRequestValue(request, "codice_istanza");
            EntityOp e = new EntityOp();
            Istanza is;
            if (so == null) {
                is = e.getIstanza(codiceis);
            } else {
                is = e.getIstanza(so, codiceis);
            }

            File cons2 = new File(is.getPathfirmato());
            if (cons2.exists() && cons2.canRead()) {
                String mimeType = Files.probeContentType(cons2.toPath());
                if (mimeType == null) {
                    mimeType = "application/pdf";
                }
                response.setContentType(mimeType);
                String headerKey = "Content-Disposition";
                String headerValue = format("attach; filename=\"%s\"", cons2.getName());
                response.setHeader(headerKey, headerValue);
                response.setContentLength(-1);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(FileUtils.readFileToByteArray(cons2));
                }
            }
        } catch (Exception ex) {
            Constant.LOGGER.severe(Utils.estraiEccezione(ex));
            redirect(request, response, "US_gestioneistanza.jsp?esito=KO");
        }

    }

    protected void GENERAISTANZA(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Utils.printRequest(request);

        try {
            SoggettoProponente so = ((User) request.getSession().getAttribute("us_memory")).getSoggetto();
            String codiceis = getRequestValue(request, "codice_istanza");
            EntityOp e = new EntityOp();

            Istanza is = e.getIstanza(so, codiceis);

            if (is != null) {

                File cons2 = Pdf.GENERAISTANZA(is);
                if (Pdf.checkPDF(cons2)) {
                    String mimeType = Files.probeContentType(cons2.toPath());
                    if (mimeType == null) {
                        mimeType = "application/pdf";
                    }
                    response.setContentType(mimeType);
                    String headerKey = "Content-Disposition";
                    String headerValue = format("attach; filename=\"%s\"", cons2.getName());
                    response.setHeader(headerKey, headerValue);
                    response.setContentLength(-1);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(FileUtils.readFileToByteArray(cons2));
                    }
                } else {
                    redirect(request, response, "404.jsp");
                }

            } else {
                redirect(request, response, "US_gestioneistanza.jsp?esito=KO1");
            }
        } catch (Exception ex) {
            Constant.LOGGER.severe(Utils.estraiEccezione(ex));
            redirect(request, response, "US_gestioneistanza.jsp?esito=KO");
        }

    }

    protected void SALVACORSI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            SoggettoProponente so = ((User) request.getSession().getAttribute("us_memory")).getSoggetto();
            String codiceis = getRequestValue(request, "codice_istanza");
            EntityOp e = new EntityOp();

            Istanza is = e.getIstanza(so, codiceis);

            if (is != null) {
                e.begin();
                is.setStatocorso(e.getEm().find(CorsoStato.class, "02"));
                e.merge(is);

                e.flush();
                e.commit();
                e.close();
                HttpSession se = request.getSession();
                se.setAttribute("is_memory", is);
                redirect(request, response, "US_gestioneistanza.jsp");
            } else {
                redirect(request, response, "US_gestioneistanza.jsp?esito=KO1");
            }

        } catch (Exception ex) {
            Constant.LOGGER.severe(Utils.estraiEccezione(ex));
            redirect(request, response, "US_gestioneistanza.jsp?esito=KO");
        }

    }

    protected void NOMINACOMMISSIONE(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utils.printRequest(request);
        try {
            EntityOp e = new EntityOp();
            Long idc = Long.valueOf(getRequestValue(request, "idcorso"));
            Corsoavviato c = e.getEm().find(Corsoavviato.class, idc);

            String pres = getRequestValue(request, "pres");
            String membri = getRequestValue(request, "membri");

            c.setPresidentecommissione(pres);
            c.setMembricommissione(membri);

            e.begin();
            e.merge(c);
            e.flush();
            e.commit();
            e.close();
            redirect(request, response, "ADM_commissione.jsp?esito=OK&idcorso=" + idc);
        } catch (Exception ex) {
            Constant.LOGGER.severe(Utils.estraiEccezione(ex));
            redirect(request, response, "US_gestionecorsi.jsp?esito=KO");
        }
    }

    protected void CONCLUDICORSO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            EntityOp e = new EntityOp();
            Long idc = Long.valueOf(getRequestValue(request, "idcorso"));
            Corsoavviato c = e.getEm().find(Corsoavviato.class, idc);
            c.setStatocorso(e.getEm().find(CorsoStato.class, "21"));
            e.begin();
            e.persist(c);
            e.flush();
            e.commit();
            e.close();
            redirect(request, response, "US_gestionecorsi.jsp?esito=OK");
        } catch (Exception ex) {
            Constant.LOGGER.severe(Utils.estraiEccezione(ex));
            redirect(request, response, "US_gestionecorsi.jsp?esito=KO");
        }
    }

    protected void AVVIANUOVOCORSO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Utils.printRequest(request);
        try {
            SoggettoProponente so = ((User) request.getSession().getAttribute("us_memory")).getSoggetto();
            EntityOp e = new EntityOp();

            String codiceistanza = getRequestValue(request, "codiceistanza");
            String datainizio = getRequestValue(request, "datainizio");
            String datafine = getRequestValue(request, "datafine");

            String docenti = getRequestValue(request, "docenti");
            String allievi = getRequestValue(request, "allievi");

            Corsoavviato ca = new Corsoavviato();

            ca.setDatafine(sdf_PATTERNDATE6.parse(datafine));
            ca.setDatainizio(sdf_PATTERNDATE6.parse(datainizio));
            ca.setElencoallievi(allievi);
            ca.setElencodocenti(docenti);
            ca.setIstanza(e.getIstanza(so, codiceistanza));
            ca.setSoggetto(so);
            ca.setStatocorso(e.getEm().find(CorsoStato.class, "20"));

            e.begin();
            e.persist(ca);
            e.flush();
            e.commit();
            e.close();
            redirect(request, response, "US_nuovocorso.jsp?esito=ADDED");
        } catch (Exception ex) {
            Constant.LOGGER.severe(Utils.estraiEccezione(ex));
            redirect(request, response, "US_compilacorsi.jsp?esito=KO");
        }
    }

    protected void ADDCORSO(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            SoggettoProponente so = ((User) request.getSession().getAttribute("us_memory")).getSoggetto();
            Istanza is = (Istanza) request.getSession().getAttribute("is_memory");
            String codiceis = generaId(30);
            EntityOp e = new EntityOp();
            e.begin();

            if (is != null) {
                is.setQuantitarichiesta(is.getQuantitarichiesta() + parseIntR(getRequestValue(request, "quantitarichiesta")));
                is.setProtocollosoggetto(getRequestValue(request, "protnum"));
                is.setProtocollosoggettodata(new DateTime().toString(PATTERNDATE4));
                e.merge(is);
            } else {
                is = new Istanza();
                is.setProtocollosoggetto(getRequestValue(request, "protnum"));
                is.setProtocollosoggettodata(new DateTime().toString(PATTERNDATE4));
                is.setCodice(codiceis);
                is.setSoggetto(so);
                is.setStatocorso(e.getEm().find(CorsoStato.class, "01"));
                is.setQuantitarichiesta(parseIntR(getRequestValue(request, "quantitarichiesta")));
                e.persist(is);
            }

            Corso c = new Corso();
            c.setDuratagiorni(parseIntR(getRequestValue(request, "duratagiorni")));
            c.setDurataore(180);
            c.setStageore(parseIntR(getRequestValue(request, "stageore")));
            c.setNumeroallievi(parseIntR(getRequestValue(request, "numeroallievi")));
            c.setElearningperc(parseIntR(getRequestValue(request, "stageore")));
            c.setReq1(getRequestValue(request, "req1"));
            c.setIstat(getRequestValue(request, "istat"));
            c.setSoggetto(so);
            c.setCertif(e.getEm().find(Certificazione.class, getRequestValue(request, "certif")));
            c.setLivellocertif(e.getEm().find(Livello_Certificazione.class, getRequestValue(request, "levelcertif")));
            c.setSedescelta(e.getEm().find(Sede.class, parseLongR(getRequestValue(request, "sedescelta"))));
            c.setQuantitarichiesta(parseIntR(getRequestValue(request, "quantitarichiesta")));
            c.setStatocorso(e.getEm().find(CorsoStato.class, "01"));
            c.setCodiceistanza(codiceis);
            e.persist(c);
            e.flush();
            e.commit();
            e.close();
            HttpSession se = request.getSession();
            se.setAttribute("is_memory", is);
            redirect(request, response, "US_gestioneistanza.jsp");
        } catch (Exception ex) {
            Constant.LOGGER.severe(Utils.estraiEccezione(ex));
            redirect(request, response, "US_compilacorsi.jsp?esito=KO");
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
                case "ADDCORSO":
                    ADDCORSO(request, response);
                    break;
                case "SALVACORSI":
                    SALVACORSI(request, response);
                    break;
                case "GENERAISTANZA":
                    GENERAISTANZA(request, response);
                    break;
                case "UPLISTA1":
                    UPLISTA1(request, response);
                    break;
                case "SCARICAISTANZAFIRMATA":
                    SCARICAISTANZAFIRMATA(request, response);
                    break;
                case "ELIMINAISTANZAFIRMATA":
                    ELIMINAISTANZAFIRMATA(request, response);
                    break;
                case "SENDISTANZA":
                    SENDISTANZA(request, response);
                    break;
                case "APPROVAISTANZA":
                    APPROVAISTANZA(request, response);
                    break;
                case "SCARICADECRETOISTANZA":
                    SCARICADECRETOISTANZA(request, response);
                    break;
                case "SCARICAPDFTEST":
                    SCARICAPDFTEST(request, response);
                    break;
                case "AVVIANUOVOCORSO":
                    AVVIANUOVOCORSO(request, response);
                    break;
                case "CONCLUDICORSO":
                    CONCLUDICORSO(request, response);
                    break;
                case "NOMINACOMMISSIONE":
                    NOMINACOMMISSIONE(request, response);
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {

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
