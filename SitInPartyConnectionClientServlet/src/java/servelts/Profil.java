/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servelts;

import client.AdminClient;
import entity.Benutzer;
import entity.Veranstaltung;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import servelts.hilfsklasse.DrawHeaderFooter;

/**
 *
 * @author Markus
 */
@WebServlet(name = "Profil", urlPatterns = {"/Profil"})
public class Profil extends HttpServlet {

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
        Benutzer benutzer;
        if (request.getAttribute("benutzer") != null) {
            benutzer = (Benutzer) request.getAttribute("benutzer");
        } else {
            benutzer = client.getAktuellerBenutzer();
        }

        DrawHeaderFooter dhf = new DrawHeaderFooter();
        dhf.drawHeader(out);
        out.println("<a href='Home' class='btn btn-default btn-primary'><i class='glyphicon glyphicon-arrow-left'></i> Zurück</a>");
        out.println("<h2>Profil von " + benutzer.getBenutzername() + "</h2>");
        out.println("<table class='table table-hover'><tr><th>Benutzername:</th><td>" + benutzer.getBenutzername() + "</td></tr>"
                + "<tr><th>Vorname:</th><td>" + benutzer.getVorname() + "</td></tr>\n"
                + "<tr><th>Nachname:</th><td>" + benutzer.getNachname() + "</td></tr>\n"
                + "<tr><th>E-Mail:</th><td>" + benutzer.getEmail() + "</td></tr>\n"
                + "<tr><th>Administrator?</th><td>" + benutzer.getIstAdmin() + "</td></tr>");
out.println("<tr><th>Erstellte Veranstaltungen</th><td>");
        Map<String, Veranstaltung> erstellteVeranstaltungen;
        erstellteVeranstaltungen = client.getErstellteVeranstaltungenVonBenutzer(benutzer.getBenutzername());
        // Sortierte Map
        Map<String, Veranstaltung> treeMap = new TreeMap<String, Veranstaltung>(erstellteVeranstaltungen);
        for (Veranstaltung veranstaltung : treeMap.values()) {
            out.println("" + veranstaltung.getName() + ", ");
        }
        out.println("</td></tr></table>");

        dhf.drawFooter(out);

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
