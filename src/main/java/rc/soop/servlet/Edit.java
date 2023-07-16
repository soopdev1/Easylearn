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
import org.joda.time.DateTime;
import static rc.soop.sic.Constant.PATTERNDATE4;
import static rc.soop.sic.Utils.estraiEccezione;
import static rc.soop.sic.Utils.redirect;
import rc.soop.sic.jpa.EntityOp;
import rc.soop.sic.jpa.Tipologia_Percorso;

/**
 *
 * @author Administrator
 */
public class Edit extends HttpServlet {

    private static final String ITOTALRECORDS = "iTotalRecords";
    private static final String ITOTALDISPLAY = "iTotalDisplayRecords";
    private static final String SECHO = "sEcho";
    private static final String SCOLUMS = "sColumns";
    private static final String AADATA = "aaData";
    private static final String RECORDID = "RecordID";
    private static final String APPJSON = "application/json";
    private static final String CONTENTTYPE = "Content-Type";

    protected void backoffice_tipologiapercorso(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            JsonObject jMembers = new JsonObject();
            JsonArray data = new JsonArray();

            List<Tipologia_Percorso> result = (List<Tipologia_Percorso>) new EntityOp().findAll(Tipologia_Percorso.class);

            jMembers.addProperty(ITOTALRECORDS, result.size());
            jMembers.addProperty(ITOTALDISPLAY, result.size());
            jMembers.addProperty(SECHO, 0);
            jMembers.addProperty(SCOLUMS, "");
            AtomicInteger at = new AtomicInteger(1);
            result.forEach(res -> {

                JsonObject data_value = new JsonObject();
                data_value.addProperty(RECORDID, at.get());
                data_value.addProperty("tipo", res.getTipocorso().name());
                data_value.addProperty("descrizione", res.getNometipologia());
                data_value.addProperty("datastart", new DateTime(res.getDatastart()).toString(PATTERNDATE4));
                data_value.addProperty("dataend", new DateTime(res.getDataend()).toString(PATTERNDATE4));
                data_value.addProperty("stato", res.getEtichettastato());
                data_value.addProperty("maxcorsi", res.getMaxcorsi());
                data_value.addProperty("maxedizioni", res.getMaxedizioni());
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
                case "backoffice_tipologiapercorso":
                    backoffice_tipologiapercorso(request, response);
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
