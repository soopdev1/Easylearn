/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package rc.soop.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import rc.soop.sic.Utils;
import static rc.soop.sic.Utils.getRequestValue;
import static rc.soop.sic.Utils.redirect;
import rc.soop.sic.jpa.EntityOp;
import static rc.soop.sic.jpa.EntityOp.trackingAction;
import rc.soop.sic.jpa.User;

/**
 *
 * @author Administrator
 */
public class SPIDSERVLET extends HttpServlet {

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
        String code = getRequestValue(request, "code");
        String state = getRequestValue(request, "state");
        if (!code.equals("") && !state.equals("")) {
            String username = "testsa";
            String password = "12345";
            EntityOp e = new EntityOp();
            User user = e.getUser(username, password);
            HttpSession se = request.getSession();
            se.setAttribute("us_memory", user);
            se.setAttribute("us_cod", user.getUsername());
            se.setAttribute("us_rolecod", user.getTipo());
            se.setAttribute("us_mail", user.getEmail());
            trackingAction(username, "LOGIN OK SPID SERVLET");
            redirect(request, response, "US_dashboard.jsp");            
        } else {
            redirect(request, response, "login.jsp");
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
        processRequest(request, response);
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
