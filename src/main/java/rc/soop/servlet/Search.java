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
import static rc.soop.sic.Utils.estraiEccezione;
import static rc.soop.sic.Utils.redirect;
import rc.soop.sic.jpa.Allievi;
import rc.soop.sic.jpa.Docente;
import rc.soop.sic.jpa.EntityOp;
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
