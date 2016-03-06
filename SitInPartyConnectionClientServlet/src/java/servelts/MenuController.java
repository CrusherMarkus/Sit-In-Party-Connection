/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servelts;

import client.AdminClient;
import entity.Benutzer;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Markus
 */
@WebServlet(name = "MenuController", urlPatterns = {"/MenuController"})
public class MenuController extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        AdminClient client = (AdminClient) session.getAttribute("client");
        if (client == null) {
            out.println("<html><head><title>Session Fehler</title></head>");
            out.close();
        }
        String operation = request.getParameter("operation");

        if (operation != null) {
            if (operation.matches("Benutzerverwaltung")) {
                RequestDispatcher rs = request.getRequestDispatcher("benutzerverwaltung.jsp");
                rs.include(request, response);
            } else if (operation.matches("Veranstaltungsverwaltung")) {
                RequestDispatcher rs = request.getRequestDispatcher("veranstaltungsverwaltung.jsp");
                rs.include(request, response);
            } else if (operation.matches("Abmelden")) {
                session.invalidate();
                RequestDispatcher rs = request.getRequestDispatcher("index.jsp");
                request.setAttribute("message", "<div class='alert alert-success' role='alert'>" + client.getAktuellerBenutzer().getBenutzername() + " hat sich abgemeldet!</div>");
                rs.include(request, response);
            } else if (operation.matches("Mein Profil")) {
                Benutzer benutzer = (Benutzer) session.getAttribute("benutzer");
                request.setAttribute("benutzer", benutzer);
                RequestDispatcher rs = request.getRequestDispatcher("Profil");
                rs.include(request, response);
            } else if (operation.matches("Home")) {
                RequestDispatcher rs = request.getRequestDispatcher("Home");
                rs.include(request, response);
            }
        }
        String benutzername = request.getParameter("benutzername");

        if (benutzername != null) {
            Benutzer benutzer = client.getBenutzer(request.getParameter("benutzername"));
            if (benutzername.matches(benutzer.getBenutzername())) {
                request.setAttribute("benutzer", benutzer);
                RequestDispatcher rs = request.getRequestDispatcher("Profil");
                rs.include(request, response);
            }
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
