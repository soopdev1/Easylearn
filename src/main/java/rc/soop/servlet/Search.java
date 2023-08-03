package rc.soop.servlet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import rc.soop.sic.Utils;
import static rc.soop.sic.Utils.estraiEccezione;
import static rc.soop.sic.Utils.getRequestValue;
import static rc.soop.sic.Utils.redirect;
import rc.soop.sic.jpa.Allievi;
import rc.soop.sic.jpa.Corso;
import rc.soop.sic.jpa.Docente;
import rc.soop.sic.jpa.EntityOp;
import rc.soop.sic.jpa.Istanza;
import rc.soop.sic.jpa.Sede;
import rc.soop.sic.jpa.SoggettoProponente;
import rc.soop.sic.jpa.User;

/**
 *
 * @author Administrator
 */
public class Search extends HttpServlet {

    private static final String ITOTALRECORDS = "iTotalRecords";
    private static final String ITOTALDISPLAY = "iTotalDisplayRecords";
    private static final String SECHO = "sEcho";
    private static final String SCOLUMS = "sColumns";
    private static final String AADATA = "aaData";
    private static final String RECORDID = "RecordID";
    private static final String APPJSON = "application/json";
    private static final String CONTENTTYPE = "Content-Type";

    protected void list_istanze_adm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            JsonObject jMembers = new JsonObject();
            JsonArray data = new JsonArray();

            String statoistanza = getRequestValue(request, "statoistanza");
            String tipopercorso = getRequestValue(request, "tipopercorso");

            List<Istanza> result = new EntityOp().list_istanze_adm(tipopercorso, statoistanza);

            jMembers.addProperty(ITOTALRECORDS, result.size());
            jMembers.addProperty(ITOTALDISPLAY, result.size());
            jMembers.addProperty(SECHO, 0);
            jMembers.addProperty(SCOLUMS, "");
            AtomicInteger at = new AtomicInteger(1);
            result.forEach(res -> {
                JsonObject data_value = new JsonObject();
                data_value.addProperty(RECORDID, at.get());
                data_value.addProperty("id", res.getIdistanza());
                data_value.addProperty("soggetto", res.getSoggetto().getRAGIONESOCIALE());
                data_value.addProperty("corsi", res.getSoggetto().getRAGIONESOCIALE());
                String corsi = "<b>Tipologia Percorsi: " + res.getTipologiapercorso().getNometipologia() + "</b><br/><hr>";
                List<Corso> c1 = new EntityOp().getCorsiIstanza(res);

                if (res.getStatocorso().getCodicestatocorso().equals("08")) {
                    for (Corso cor : c1) {
                        if (cor.getStatocorso().getCodicestatocorso().equals("24")) {
                            corsi += "CODICE: " + cor.getIdentificativocorso() + "<br><u>" + cor.getRepertorio().getDenominazione() + "</u> - Edizioni: " + cor.getQuantitarichiesta() + "<br/>";
                        }
                    }
                } else {
                    for (Corso cor : c1) {
                        corsi += "<u>" + cor.getRepertorio().getDenominazione() + "</u>- Edizioni: " + cor.getQuantitarichiesta() + "<br/>";
                    }
                }

                data_value.addProperty("corsi", corsi);
                data_value.addProperty("protocollo", res.getProtocollosoggetto() + " " + res.getProtocollosoggettodata());
                data_value.addProperty("data", res.getDatainvio());

                data_value.addProperty("stato", res.getStatocorso().getHtmldescr());

                String azioni = "<i class='fa fa-hourglass'></i>";
                if (res.getStatocorso().getCodicestatocorso().equals("07")) {//DA GESTIRE
                    azioni = "<div class=\"p-2 min-w-150px btn-group btn-group-justified\" role=\"group\" aria-label=\"Basic example\">"
                            + "<form action=\"US_showistanza.jsp\" method=\"POST\" target=\"_blank\">"
                            + "<input type=\"hidden\" name=\"idist\" value=\"" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "\"/>"
                            + "<button type=\"submit\"class=\"btn btn-sm btn-primary\" data-bs-toggle=\"tooltip\" title=\"VISUALIZZA ISTANZA PRESENTATA\" data-preload='false'>"
                            + "<i class=\"fa fa-file-text\"></i></button>"
                            + "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"VISUALIZZA ALLEGATI\" data-preload='false' class=\"btn btn-sm btn-bg-light btn-secondary\" "
                            + " onclick=\"return document.getElementById('gestall_" + res.getIdistanza() + "').submit();\"><i class=\"fa fa-file-clipboard\"></i></button>"
                            + "&nbsp;&nbsp;"
                            + "<a href='ADM_approvaistanza.jsp?idist=" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "' data-fancybox data-type='iframe' data-preload='false' data-width='75%' data-height='75%'"
                            + " class=\"btn btn-sm btn-bg-light btn-success fan1\" data-bs-toggle=\"tooltip\" title=\"APPROVA ISTANZA\" data-preload='false' "
                            + "\"><i class=\"fa fa-check\"></i></a>"
                            + "<a href='ADM_rigettaistanza.jsp?idist=" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "' data-fancybox data-type='iframe' data-preload='false' data-width='75%' data-height='75%'"
                            + " class=\"btn btn-sm btn-bg-light btn-danger fan1\" data-bs-toggle=\"tooltip\" title=\"RIGETTA ISTANZA\" data-preload='false' "
                            + "\"><i class=\"fa fa-remove\"></i></a>"
                            + "</form>"
                            + "<form action=\"ADM_allegati.jsp\" method=\"POST\" target=\"_blank\" id=\"gestall_" + res.getIdistanza() + "\">"
                            + "<input type=\"hidden\" name=\"idist\" value=\"" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "\"/></form></div>";
                } else if (res.getStatocorso().getCodicestatocorso().equals("08")) { //APPROVATA
                    azioni = "<div class=\"p-2 min-w-150px btn-group btn-group-justified\" role=\"group\" aria-label=\"Basic example\">"
                            + "<form action=\"US_showistanza.jsp\" method=\"POST\" target=\"_blank\">"
                            + "<input type=\"hidden\" name=\"idist\" value=\"" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "\"/>"
                            + "<button type=\"submit\"class=\"btn btn-sm btn-primary\" data-bs-toggle=\"tooltip\" title=\"VISUALIZZA ISTANZA PRESENTATA\" data-preload='false'>"
                            + "<i class=\"fa fa-file-text\"></i></button>"
                            + "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"VISUALIZZA ALLEGATI\" data-preload='false' class=\"btn btn-sm btn-bg-light btn-secondary\" "
                            + " onclick=\"return document.getElementById('gestall_" + res.getIdistanza() + "').submit();\"><i class=\"fa fa-file-clipboard\"></i></button>"
                            + "<br>";
                    if (res.getPathfirmato() != null) {
                        azioni
                                += "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"VISUALIZZA DECRETO AUT.\" data-preload='false' "
                                + "class=\"btn btn-sm btn-bg-light btn-dark\" "
                                + " onclick=\"return document.getElementById('generadecretotemplate1_" + res.getIdistanza()
                                + "').submit();\"><i class=\"fa fa-file-pdf\"></i></button>"
                                + "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"VISUALIZZA DECRETO AUT.FIRMATO\" data-preload='false' "
                                + "class=\"btn btn-sm btn-bg-light btn-success\" "
                                + " onclick=\"return document.getElementById('scaricadecretofirmato1_" + res.getIdistanza()
                                + "').submit();\"><i class=\"fa fa-file-pdf\"></i></button>"
                                + "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"VISUALIZZA DECRETO AUT. V2\" data-preload='false' "
                                + "class=\"btn btn-sm btn-bg-light btn-danger\" "
                                + " onclick=\"return document.getElementById('scaricadecretofto1_" + res.getIdistanza()
                                + "').submit();\"><i class=\"fa fa-file-pdf\"></i></button>"
                                + "<button type=\"button\"class=\"btn btn-sm btn-warning\"\n"
                                + "data-bs-toggle=\"tooltip\" title=\"NOTIFICA SOGGETTO PROPONENTE\" data-preload='false' "
                                + "onclick=\"return invianotificaddecreto('" + res.getIdistanza() + "')\" ><i class=\"fa fa-envelope\"></i>"
                                + "</form>"
                                + "<form action=\"ADM_allegati.jsp\" method=\"POST\" target=\"_blank\" id=\"gestall_" + res.getIdistanza() + "\">"
                                + "<input type=\"hidden\" name=\"idist\" value=\"" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "\"/>"
                                + "</form>"
                                + "<form action=\"Operations\" method=\"POST\" target=\"_blank\" id=\"generadecretotemplate1_" + res.getIdistanza() + "\">"
                                + "<input type=\"hidden\" name=\"type\" value=\"GENERADECRETOBASE\"/>"
                                + "<input type=\"hidden\" name=\"idist\" value=\"" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "\"/>"
                                + "</form>"
                                + "<form action=\"Operations\" method=\"POST\" target=\"_blank\" id=\"scaricadecretofirmato1_" + res.getIdistanza() + "\">"
                                + "<input type=\"hidden\" name=\"type\" value=\"SCARICADECRETOFIRMATO\"/>"
                                + "<input type=\"hidden\" name=\"idist\" value=\"" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "\"/>"
                                + "</form>"
                                + "<form action=\"Operations\" method=\"POST\" target=\"_blank\" id=\"scaricadecretofto1_" + res.getIdistanza() + "\">"
                                + "<input type=\"hidden\" name=\"type\" value=\"GENERADECRETODDSFTO\"/>"
                                + "<input type=\"hidden\" name=\"idist\" value=\"" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "\"/>"
                                + "</form>";
                    } else {
                        azioni += "<button type=\"button\" data-bs-toggle=\"tooltip\" title=\"SCARICA DECRETO AUT.\" data-preload='false' "
                                + "class=\"btn btn-sm btn-bg-light btn-dark\" "
                                + " onclick=\"return document.getElementById('generadecretotemplate1_" + res.getIdistanza() + "').submit();\"><i class=\"fa fa-file-pdf\"></i></button>"
                                + "<a href='ADM_uploaddecretofirmato.jsp?idist=" + Utils.enc_string(String.valueOf(res.getIdistanza()))
                                + "' data-fancybox data-type='iframe' data-preload='false' data-width='75%' data-height='75%'"
                                + " class=\"btn btn-sm btn-bg-light btn-warning fan1\" data-bs-toggle=\"tooltip\" title=\"UPLOAD DECRETO FIRMATO\" data-preload='false' "
                                + "><i class=\"fa fa-upload\"></i></a>"
                                + "</form>"
                                + "<form action=\"ADM_allegati.jsp\" method=\"POST\" target=\"_blank\" id=\"gestall_" + res.getIdistanza() + "\">"
                                + "<input type=\"hidden\" name=\"idist\" value=\"" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "\"/>"
                                + "</form>"
                                + "<form action=\"Operations\" method=\"POST\" target=\"_blank\" id=\"generadecretotemplate1_" + res.getIdistanza() + "\">"
                                + "<input type=\"hidden\" name=\"type\" value=\"GENERADECRETOBASE\"/>"
                                + "<input type=\"hidden\" name=\"idist\" value=\"" + Utils.enc_string(String.valueOf(res.getIdistanza())) + "\"/>"
                                + "</form>";
                    }

                    azioni += "</div>";

                }

                data_value.addProperty("azioni", azioni);
                data.add(data_value);

                at.addAndGet(1);
            });
            jMembers.add(AADATA, data);
            response.setContentType(APPJSON);
            response.setHeader(CONTENTTYPE, APPJSON);
            out.print(jMembers.toString());
        }

    }

    protected void list_docenti(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            JsonObject jMembers = new JsonObject();
            JsonArray data = new JsonArray();

            List<Docente> result = new EntityOp().findAll(Docente.class);

            jMembers.addProperty(ITOTALRECORDS, result.size());
            jMembers.addProperty(ITOTALDISPLAY, result.size());
            jMembers.addProperty(SECHO, 0);
            jMembers.addProperty(SCOLUMS, "");
            AtomicInteger at = new AtomicInteger(1);
            result.forEach(res -> {
//                String pdf
//                        = "<a href='javascript:void(0)' onclick='return $(\"#frm\").submit();' class='btn btn-sm btn-outline-primary m-btn m-btn--icon m-btn--icon-only m-btn--custom m-btn--pill m-btn--air' "
//                        + "data-toggle='popover' data-placement='right' title='Visualizza File' data-content='Visualizza file documento.'><i class='far fa-file-pdf'></i></a>"
//                        + "<form id='frm_' target='_blank' method='POST' action='Operations'>"
//                        + "<input type='hidden' name='type' value='showPDF'/>"
//                        + "<input type='hidden' name='ido' value='' />"
//                        + "</form>";
//
//                String edit = "";
                JsonObject data_value = new JsonObject();
                data_value.addProperty(RECORDID, at.get());
                data_value.addProperty("stato", res.getEtichettastato());
                data_value.addProperty("cognome", res.getCognome());
                data_value.addProperty("nome", res.getNome());
                data_value.addProperty("cf", res.getCodicefiscale());
                data_value.addProperty("titolo", res.getTitolostudio());
                data_value.addProperty("tipologia", res.getTipologia());
                data_value.addProperty("profiloprof", res.getProfiloprof());
                data_value.addProperty("azioni", "<i class='fa fa-hourglass'></i>");
                data.add(data_value);

                at.addAndGet(1);
            });
            jMembers.add(AADATA, data);
            response.setContentType(APPJSON);
            response.setHeader(CONTENTTYPE, APPJSON);
            out.print(jMembers.toString());
        }
    }

    protected void list_allievi(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            JsonObject jMembers = new JsonObject();
            JsonArray data = new JsonArray();

            List<Allievi> result = new EntityOp().findAll(Allievi.class);

            jMembers.addProperty(ITOTALRECORDS, result.size());
            jMembers.addProperty(ITOTALDISPLAY, result.size());
            jMembers.addProperty(SECHO, 0);
            jMembers.addProperty(SCOLUMS, "");
            AtomicInteger at = new AtomicInteger(1);
            result.forEach(res -> {

//                String pdf
//                        = "<a href='javascript:void(0)' onclick='return $(\"#frm\").submit();' class='btn btn-sm btn-outline-primary m-btn m-btn--icon m-btn--icon-only m-btn--custom m-btn--pill m-btn--air' "
//                        + "data-toggle='popover' data-placement='right' title='Visualizza File' data-content='Visualizza file documento.'><i class='far fa-file-pdf'></i></a>"
//                        + "<form id='frm_' target='_blank' method='POST' action='Operations'>"
//                        + "<input type='hidden' name='type' value='showPDF'/>"
//                        + "<input type='hidden' name='ido' value='' />"
//                        + "</form>";
//
//                String edit = "";
                JsonObject data_value = new JsonObject();
                data_value.addProperty(RECORDID, at.get());
                data_value.addProperty("stato", res.getEtichettastato());
                data_value.addProperty("cognome", res.getCognome());
                data_value.addProperty("nome", res.getNome());
                data_value.addProperty("cf", res.getCodicefiscale());
                data_value.addProperty("email", res.getEmail());
                data_value.addProperty("telefono", res.getTelefono());
                data_value.addProperty("azioni", "<i class='fa fa-hourglass'></i>");
                data.add(data_value);

                at.addAndGet(1);
            });
            jMembers.add(AADATA, data);
            response.setContentType(APPJSON);
            response.setHeader(CONTENTTYPE, APPJSON);
            out.print(jMembers.toString());
        }
    }

    protected void list_sedi_soggetto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            JsonObject jMembers = new JsonObject();
            JsonArray data = new JsonArray();
            SoggettoProponente so = ((User) request.getSession().getAttribute("us_memory")).getSoggetto();

            List<Sede> result = so.getSediformazione();

            jMembers.addProperty(ITOTALRECORDS, result.size());
            jMembers.addProperty(ITOTALDISPLAY, result.size());
            jMembers.addProperty(SECHO, 0);
            jMembers.addProperty(SCOLUMS, "");
            AtomicInteger at = new AtomicInteger(1);
            result.forEach(res -> {
//                String pdf
//                        = "<a href='javascript:void(0)' onclick='return $(\"#frm\").submit();' class='btn btn-sm btn-outline-primary m-btn m-btn--icon m-btn--icon-only m-btn--custom m-btn--pill m-btn--air' "
//                        + "data-toggle='popover' data-placement='right' title='Visualizza File' data-content='Visualizza file documento.'><i class='far fa-file-pdf'></i></a>"
//                        + "<form id='frm_' target='_blank' method='POST' action='Operations'>"
//                        + "<input type='hidden' name='type' value='showPDF'/>"
//                        + "<input type='hidden' name='ido' value='' />"
//                        + "</form>";
                JsonObject data_value = new JsonObject();
                data_value.addProperty(RECORDID, at.get());
                data_value.addProperty("stato", res.getEtichettastato());
                data_value.addProperty("tipo", res.getTipo().getNome());
                data_value.addProperty("indirizzo", res.getIndirizzo());
                data_value.addProperty("cap", res.getCap());
                data_value.addProperty("comune", res.getComune());
                data_value.addProperty("provincia", res.getProvincia());
                data_value.addProperty("azioni", "<i class='fa fa-hourglass'></i>");
                data.add(data_value);
                at.addAndGet(1);
            });
            jMembers.add(AADATA, data);
            response.setContentType(APPJSON);
            response.setHeader(CONTENTTYPE, APPJSON);
            out.print(jMembers.toString());
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
                case "list_docenti":
                    list_docenti(request, response);
                    break;
                case "list_allievi":
                    list_allievi(request, response);
                    break;
                case "list_sedi_soggetto":
                    list_sedi_soggetto(request, response);
                    break;
                case "list_istanze_adm":
                    list_istanze_adm(request, response);
                    break;
                default: {
                    String p = request.getContextPath();
                    request.getSession().invalidate();
                    redirect(request, response, p);
                    break;
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
