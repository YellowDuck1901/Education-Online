/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import JavaBean.Constants;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
public class Setting_site extends HttpServlet {

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

        System.out.println("Setting site");

        String control = request.getParameter("changepass");
        if (control != null) {
            ServletContext sv = getServletContext();
//            JavaBean.User user = (JavaBean.User) sv.getAttribute("user");
            HttpSession session = request.getSession();
            JavaBean.User user = (JavaBean.User) session.getAttribute(Constants.SESSION_USER_KEY);
            String username = user.getUsername();

            request.removeAttribute("message");
            String oldpw = request.getParameter("oldpw");
            String newpw = request.getParameter("newpw");
            String confirmpw = request.getParameter("confirmpw");
            try {
                if (DB.lib.DB_CheckPassword(oldpw, username)) {
                    if (newpw.equals(confirmpw)) {
                        DB.lib.DB_ChangePassword(newpw, confirmpw, username);
                        request.setAttribute("message", "Successful");
                        RequestDispatcher dispatcher;
                        dispatcher = getServletContext().getRequestDispatcher(lib.Web.SETTING);
                        dispatcher.forward(request, response);
                    } else {
                        request.setAttribute("message", "Password not match");
                        RequestDispatcher dispatcher;
                        dispatcher = getServletContext().getRequestDispatcher(lib.Web.SETTING);
                        dispatcher.forward(request, response);
                    }
                    return;
                } else {
                    request.setAttribute("message", "Wrong password!");
                    RequestDispatcher dispatcher;
                    dispatcher = getServletContext().getRequestDispatcher(lib.Web.SETTING);
                    dispatcher.forward(request, response);
                    return;
                }
            } catch (SQLException ex) {
            }
        }
        RequestDispatcher dispatcher;
        dispatcher = getServletContext().getRequestDispatcher(lib.Web.SETTING);
        dispatcher.forward(request, response);
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
