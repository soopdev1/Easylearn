package rc.soop.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.dmfs.httpessentials.client.HttpRequestExecutor;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.httpessentials.httpurlconnection.HttpUrlConnectionExecutor;
import org.dmfs.oauth2.client.BasicOAuth2AuthorizationProvider;
import org.dmfs.oauth2.client.BasicOAuth2Client;
import org.dmfs.oauth2.client.BasicOAuth2ClientCredentials;
import org.dmfs.oauth2.client.OAuth2AccessToken;
import org.dmfs.oauth2.client.OAuth2AuthorizationProvider;
import org.dmfs.oauth2.client.OAuth2Client;
import org.dmfs.oauth2.client.OAuth2ClientCredentials;
import org.dmfs.oauth2.client.OAuth2InteractiveGrant;
import org.dmfs.oauth2.client.grants.AuthorizationCodeGrant;
import org.dmfs.oauth2.client.grants.ImplicitGrant;
import org.dmfs.oauth2.client.scope.BasicScope;
import org.dmfs.rfc3986.Uri;
import org.dmfs.rfc3986.UriEncoded;
import org.dmfs.rfc3986.encoding.Precoded;
import org.dmfs.rfc3986.uris.LazyUri;
import org.dmfs.rfc5545.Duration;
import static rc.soop.sic.Constant.LOGGER;
import static rc.soop.sic.Utils.estraiEccezione;
import static rc.soop.sic.Utils.getRequestValue;
import static rc.soop.sic.Utils.redirect;
import rc.soop.sic.jpa.EntityOp;
import rc.soop.sic.jpa.User;

/**
 *
 * @author raf
 */
public class LoginOperations extends HttpServlet {

    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.getSession().setAttribute("us_memory", null);
            request.getSession().setAttribute("us_cod", null);
            request.getSession().setAttribute("us_pwd", null);
            request.getSession().setAttribute("us_rolecod", null);
            request.getSession().setAttribute("us_mail", null);
            request.getSession().setAttribute("ses_idcorso", null);
            request.getSession().setAttribute("ses_idist", null);
            request.getSession().setAttribute("ses_idalleg", null);
            request.getSession().setAttribute("ses_idcorso", null);
        } catch (Exception e) {
        }
        request.getSession().invalidate();
        redirect(request, response, "login.jsp");
    }

    protected void spid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpRequestExecutor executor = new HttpUrlConnectionExecutor();
            OAuth2AuthorizationProvider provider = new BasicOAuth2AuthorizationProvider(
                    URI.create("https://is-test.regione.sicilia.it/oauth2/authorize"),
                    URI.create("https://is-test.regione.sicilia.it/oauth2/token"),
                    new Duration(1, 0, 3600));
            OAuth2ClientCredentials credentials = new BasicOAuth2ClientCredentials(
                    "fq8aefusOqwjKNc5dkchF62ncLwa", "YTnffqNEfhfHqLTPUjfsumMaCAYa");
            OAuth2Client client = new BasicOAuth2Client(
                    provider,
                    credentials,
                    new LazyUri(new Precoded("https://tu.formati.education/demo/SPIDSERVLET")) /* Redirect URL */);
            OAuth2InteractiveGrant grant = new AuthorizationCodeGrant(
                    client, new BasicScope("scope"));
            URI authorizationUrl = grant.authorizationUrl();
            //            OAuth2AccessToken tk1 = grant.accessToken(executor);
//            OAuth2AccessToken token = grant.withRedirect(new LazyUri(new Precoded("https://is-test.regione.sicilia.it/oauth2/token")))
//                    .accessToken(executor);
////            
//            System.out.println("rc.soop.servlet.LoginOperations.spid() "+token.toString());

            redirect(request, response, authorizationUrl.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
                            exitredirect.append("ERROR LOGIN");
                            break;
                    }
                } else {
                    EntityOp.trackingAction(username, "LOGIN KO 1");
                    exitredirect.append("ERROR LOGIN");
                }
            } else {
                EntityOp.trackingAction(username, "LOGIN KO 2");
                exitredirect.append("ERROR LOGIN");
            }
        } else {
            EntityOp.trackingAction(username, "LOGIN KO 3");
            exitredirect.append("ERROR LOGIN");
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
                case "spid":
                    spid(request, response);
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
