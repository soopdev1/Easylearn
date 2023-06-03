package rc.soop.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static rc.soop.sic.Constant.LOGGER;
import static rc.soop.sic.Utils.estraiEccezione;
import static rc.soop.sic.Utils.getRequestValue;
import static rc.soop.sic.Utils.redirect;
import rc.soop.sic.jpa.EntityOp;
import rc.soop.sic.jpa.Istanza;
import rc.soop.sic.jpa.User;

/**
 *
 * @author raf
 */
public class LoginOperations extends HttpServlet {

    //SPID
    //https://github.com/italia/spid-spring
    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        redirect(request, response, "login.jsp");
    }

    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = getRequestValue(request, "username");
        String password = getRequestValue(request, "password");
        StringBuilder exitredirect = new StringBuilder("");

        if (username != null && password != null) {
            if (!username.trim().equals("") && !password.trim().equals("")) {
                EntityOp e = new EntityOp();
                User user = e.getUser(username, password);
                if (user != null) {

                    HttpSession se = request.getSession();
                    se.setAttribute("us_memory", user);
                    se.setAttribute("us_cod", user.getUsername());
                    se.setAttribute("us_pwd", user.getPassword());
                    se.setAttribute("us_rolecod", user.getTipo());
                    se.setAttribute("us_mail", user.getEmail());
                    EntityOp.trackingAction(username, "LOGIN OK");
//                    Istanza is = e.getIstanzaWaiting(user.getSoggetto());
//                    if (is != null) {
//                        se.setAttribute("is_memory", is);
//                    }
                    switch (user.getTipo()) {
                        case 1:
                            exitredirect.append("ADM_dashboard.jsp");
                            break;
                        case 2:
                            exitredirect.append("US_dashboard.jsp");
                            break;
                        case 3:
                            exitredirect.append("PRE_dashboard.jsp");
                            break;
                        default:
                            EntityOp.trackingAction(username, "LOGIN KO TIPO USER");
                            exitredirect.append("ERROR1");
                            break;
                    }
                } else {
                    EntityOp.trackingAction(username, "LOGIN KO 1");
                    exitredirect.append("ERROR1");
                }
            } else {
                EntityOp.trackingAction(username, "LOGIN KO 2");
                exitredirect.append("ERROR2");
            }
        } else {
            EntityOp.trackingAction(username, "LOGIN KO 3");
            exitredirect.append("ERROR3");
        }

        try (PrintWriter pw = response.getWriter()) {
            pw.print(exitredirect.toString());
            pw.flush();
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
                case "login":
                    login(request, response);
                    break;
                case "logout":
                    logout(request, response);
                    break;
            }
//                case "changepassword":
//                    changepassword(request, response);
//                    break;
//                case "forgotPwd":
//                    forgotPwd(request, response);
//                    break;
        } catch (Exception ex) {
            LOGGER.severe(estraiEccezione(ex));
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
