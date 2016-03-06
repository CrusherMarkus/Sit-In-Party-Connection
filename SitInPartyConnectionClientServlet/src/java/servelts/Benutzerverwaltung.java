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
import java.rmi.RemoteException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "Benutzerverwaltung", urlPatterns = {"/Benutzerverwaltung"})
public class Benutzerverwaltung extends HttpServlet {

    private void benutzerHinzufuegen(AdminClient client, HttpServletRequest request) {
        String benutzername = request.getParameter("benutzernameHinzufuegen");
        String passwort = request.getParameter("passwortHinzufuegen");
        String vorname = request.getParameter("vornameHinzufuegen");
        String nachname = request.getParameter("nachnameHinzufuegen");
        String email = request.getParameter("emailHinzufuegen");
        Boolean istAdmin;
        if (request.getParameter("istAdminHinzufuegen") == null) {
            istAdmin = false;
        } else {
            istAdmin = true;
        }
        Map<String, Benutzer> alleBenutzer;
        try {
            alleBenutzer = client.getAlleBenutzer();
            if (!alleBenutzer.containsKey(benutzername)) {
                client.registrieren(benutzername, email, istAdmin, nachname, passwort, vorname);
                request.setAttribute("message", "<div class='alert alert-success' role='alert'><strong>Erfolgreich!</strong> Der Benutzer " + benutzername + " wurde angelegt.</div>");
            } else {
                request.setAttribute("message", "<div class='alert alert-danger' role='alert'><strong>Erfolgreich!</strong> Der Benutzer " + benutzername + " ist bereits vorhanden.</div>");

            }
        } catch (RemoteException ex) {
            Logger.getLogger(Benutzerverwaltung.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void benutzerLoeschen(AdminClient client, HttpServletRequest request) {
        String benutzername = request.getParameter("benutzernameLoeschen");
        Map<String, Benutzer> alleBenutzer;
        try {
            alleBenutzer = client.getAlleBenutzer();
            if (alleBenutzer.containsKey(benutzername)) {
                if (client.benutzerLoeschen(benutzername)) {
                    request.setAttribute("message", "<div class='alert alert-success' role='alert'><strong>Erfolgreich!</strong> Der Benutzer " + benutzername + " wurde gelöscht.</div>");
                } else {
                    request.setAttribute("message", "<div class='alert alert-danger' role='alert'><strong>Erfolgreich!</strong> Der Benutzer " + benutzername + " wurde nicht gelöscht.</div>");
                }
            } else {
                request.setAttribute("message", "<div class='alert alert-danger' role='alert'><strong>Gescheitert!</strong> Der Benutzer " + benutzername + " ist nicht vorhanden.</div>");
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Benutzerverwaltung.class.getName()).log(Level.SEVERE, null, ex);
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
        HttpSession session = request.getSession(false);
        PrintWriter out = response.getWriter();
        AdminClient client = (AdminClient) session.getAttribute("client");
        if (client == null) {
            out.println("<html><head><title>Session Fehler</title></head>");
            out.close();
        }

        String benutzerHinzufuegen = request.getParameter("benutzerHinzufuegen");
        String benutzerLoeschen = request.getParameter("benutzerLoeschen");

        if (benutzerHinzufuegen != null) {
            benutzerHinzufuegen(client, request);
            RequestDispatcher rs = request.getRequestDispatcher("benutzerverwaltung.jsp");
            rs.include(request, response);
        }

        if (benutzerLoeschen != null) {
            benutzerLoeschen(client, request);
            RequestDispatcher rs = request.getRequestDispatcher("benutzerverwaltung.jsp");
            rs.include(request, response);
        }
    }
}
