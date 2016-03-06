/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servelts;

import client.AdminClient;
import entity.Artikel;
import entity.Benutzer;
import entity.Veranstaltung;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
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
@WebServlet(name = "Home", urlPatterns = {"/Home"})
public class Home extends HttpServlet {

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
        Benutzer benutzer = (Benutzer) session.getAttribute("benutzer");

        DrawHeaderFooter dhf = new DrawHeaderFooter();
        dhf.drawHeader(out);

        out.println("<h2>Willkommen " + benutzer.getBenutzername() + "!</h2>");
        out.println("<p>Du hast dich erfolgreich als Teilnehmer angemeldet.</p>");

        request.getRequestDispatcher("veranstaltungAddModal.jsp").include(request, response);
        out.println("<p><button type='button' class='btn btn-default btn-success' data-toggle='modal' data-target='#myModal'>Veranstaltung hinzufügen</button></p>");
        out.println("<form action='MenuController' method='POST'>");
        out.println("<p><input type='submit' name='operation' value='Mein Profil' class='btn btn-default btn-primary'>");
        if (benutzer.getIstAdmin()) {
            out.println("<input type='submit' name='operation' value='Benutzerverwaltung' class='btn btn-default btn-primary'>");
            out.println("<input type='submit' name='operation' value='Veranstaltungsverwaltung' class='btn btn-default btn-primary'></p>");
        }
        out.println("<p><input type='submit' name='operation' value='Abmelden' class='btn btn-default btn-danger'></p>");

        out.println("</form>");

        if (request.getAttribute("message") != null) {
            out.println(request.getAttribute("message"));
        }

        Map<String, Veranstaltung> alleVeranstaltungen;
        alleVeranstaltungen = client.getAlleVeranstaltungen();
        if (alleVeranstaltungen.isEmpty()) {
            out.println("<div class='box'>");
            out.println("<article>");
            out.println("<h2>Keine Veranstaltungen vorhanden</h2>");
            out.println("</article>");
            out.println("</div>");
        }
        Map<String, Veranstaltung> treeMap = new TreeMap<String, Veranstaltung>(alleVeranstaltungen);
        for (Veranstaltung veranstaltung : treeMap.values()) {
            out.println("<div class='box'>");
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            out.println("<article>");
            out.println("<h1>Veranstaltungsdaten</h1>");
            out.println("<table class='table'>"
                    + "<tr>"
                    + "<td><strong>Wann?</strong></td>"
                    + "<td><p>" + df.format(veranstaltung.getStartTermin()) + "</p></td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td><strong>Name:</strong></td>"
                    + "<td><p>" + veranstaltung.getName() + "</p></td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td><strong>Ort:</strong></td>"
                    + "<td><p>" + veranstaltung.getOrt() + "</p></td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td><strong>Veranstalter:</strong></td>"
                    + "<td><p>");

            out.println("<form action='MenuController' method='POST'>");
            out.println("<input type='submit' name='benutzername' id='benutzername' class='btn btn-default btn-info' value='" + veranstaltung.getVeranstalter().getBenutzername() + "'>");
            out.println("</form>");
            out.println("</p></td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td colspan='2' style='width:10px;'><p>" + veranstaltung.getBeschreibung() + "</p></td>"
                    + "</tr>"
                    + "</table>");
            out.println("</form>");
            out.println("</article>");

            out.println("<article>");
            out.println("<h1>Teilnehmer</h1>");

            out.println("<table class='table'>");
            out.println("<thead align='left' style='display: table-header-group'>");
            out.println("<tr>");
            out.println("<th>");
            if (veranstaltung.getAnzahlTeilnehmer() < veranstaltung.getMaxTeilnehmerzahl()) {
                out.println("<button class='btn btn-default btn-success'>" + veranstaltung.getAnzahlTeilnehmer() + "/" + veranstaltung.getMaxTeilnehmerzahl() + "</button>");
            } else {
                out.println("<button class='btn btn-default btn-danger'>" + veranstaltung.getAnzahlTeilnehmer() + "/" + veranstaltung.getMaxTeilnehmerzahl() + "</button>");

            }
            out.println("</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            if (veranstaltung.getTeilnehmendeBenutzer() != null) {
                for (Benutzer tmpBenutzer : veranstaltung.getTeilnehmendeBenutzer()) {
                    out.println("<tr>");
                    out.println("<form action='MenuController' method='POST'>");
                    out.println("<td><input type='submit' name='benutzername' id='benutzername' class='btn btn-default btn-info' value='" + tmpBenutzer.getBenutzername() + "'><td>");
                    out.println("</form>");
                    out.println("<tr>");
                }
            }
            out.println("</tbody>");
            out.println("</table>");
            out.println("</article>");

            // Artikelverwaltung
            out.println("<article>");
            out.println("<h1>Artikel benötigt</h1>");
            // ANFANG benoetigte Artikel
            out.println("<table class='table'>");
            out.println("<thead align='left' style='display: table-header-group'>");
            out.println("<tr>");
            out.println("<th></th>");
            out.println("<th>mitbringen?</th>");
            out.println("<th>entfernen?</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            HashMap<String, Artikel> benoetigteArtikel = veranstaltung.getBenoetigteArtikel();
            if (benoetigteArtikel != null) {
                Map<String, Artikel> treeMap1 = new TreeMap<String, Artikel>(benoetigteArtikel);
                for (Artikel artikel : treeMap1.values()) {
                    out.println("<tr>");
                    out.println("<form action='Artikelverwaltung' method='POST'>");
                    out.println("<td>" + artikel.getBezeichnung() + "</td>");
                    out.println("<input type='hidden' name='name' id='name' value='" + veranstaltung.getName() + "'>");
                    out.println("<input type='hidden' name='bezeichnung' id='bezeichnung' value='" + artikel.getBezeichnung() + "'>");
                    out.println("<td><button type='submit' name='artikelZuVorhandeneArtikel' id='artikelZuVorhandeneArtikel' class='btn btn-default btn-success'><i class='glyphicon glyphicon-plus'></i></button></td>");

                    if (veranstaltung.getVeranstalter().equals(benutzer)) {
                        out.println("<td><button type='submit' name='benoetigtenArtikelLoeschen' id='benoetigtenArtikelLoeschen' class='btn btn-default btn-danger'><i class='glyphicon glyphicon-minus'></i></button></td>");
                    } else {
                        out.println("<td><button type='submit' name='benoetigtenArtikelLoeschen' id='benoetigtenArtikelLoeschen' class='btn btn-default btn-danger' disabled><i class='glyphicon glyphicon-minus'></i></button></td>");

                    }
                    out.println("</form>");
                    out.println("</tr>");
                }
            }
            out.println("</tbody>");
            out.println("</table>");
            // ANFANG Artikel hinzufuegen
            out.println("<form action='Artikelverwaltung' method='POST'>");
            out.println("<h1>Was wird noch benötigt?</h1>");
            out.println("<input type='hidden' name='name' id='name' value='" + veranstaltung.getName() + "'>");
            out.println("<input type='text' name='bezeichnung' id='bezeichnung' placeholder='Bezeichnug'>");
            out.println("<button type='submit' name='artikelHinzufuegen' id='artikelHinzufuegen' class='btn btn-default btn-success'><i class='glyphicon glyphicon-plus'></i></button>");
            out.println("</form>");

            // ENDE Artikel hinzufuegen            
            out.println("</article>");
            // ENDE benoetigte Artikel

            out.println("<article>");
            out.println("<h1>Artikel vorhanden</h1>");
            // ANFANG vorhandene Artikel
            out.println("<table class='table'>");
            out.println("<thead align='left' style='display: table-header-group'>");
            out.println("<tr>");
            out.println("<th></th>");
            out.println("<th>entfernen?</th>");
            out.println("<th>zurücktragen?</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");

            if (veranstaltung.getVorhandeneArtikel() != null) {
                Map<String, Artikel> treeMap2 = new TreeMap<String, Artikel>(veranstaltung.getVorhandeneArtikel());
                for (Artikel artikel : treeMap2.values()) {
                    out.println("<tr>");
                    out.println("<form action='Artikelverwaltung' method='POST'>");
                    out.println("<td>" + artikel.getBezeichnung() + "</td>");
                    out.println("<input type='hidden' name='name' id='name' value='" + veranstaltung.getName() + "'>");
                    out.println("<input type='hidden' name='bezeichnung' id='bezeichnung' value='" + artikel.getBezeichnung() + "'>");
                    if (veranstaltung.getVeranstalter().equals(benutzer)) {
                        out.println("<td><button type='submit' name='vorhandenenArtikelLoeschen' id='vorhandenenArtikelLoeschen' class='btn btn-default btn-danger'><i class='glyphicon glyphicon-minus'></i></button></td>");
                    } else {
                        out.println("<td><button type='submit' name='vorhandenenArtikelLoeschen' id='vorhandenenArtikelLoeschen' class='btn btn-default btn-danger' disabled><i class='glyphicon glyphicon-minus'></i></button></td>");
                    }
                    out.println("<td><button type='submit' name='vorhandenenArtikelZurueck' id='vorhandenenArtikelZurueck' class='btn btn-default btn-warning'><i class='glyphicon glyphicon-minus'></i></button></td>");
                    out.println("</form>");
                    out.println("</tr>");
                }
            }
            out.println("</tbody>");
            out.println("</table>");

            out.println("</article>");
            out.println("<article>");
            //out.println("<div class='aktionen'>");
            out.println("<h1>Aktionen</h1>");
            out.println("<form action='Veranstaltungsverwaltung' method='POST'>");
            out.println("<input type='hidden' name='name' id='name' value='" + veranstaltung.getName() + "'>");
            if (veranstaltung.getTeilnehmendeBenutzer().contains(benutzer)) {
                out.println("<input type='submit' name='teilnehmen' id='teilnehmen' class='btn btn-block btn-success' value='teilnehmen' disabled >");
                out.println("<input type='submit' name='austragen' id='austragen' class='btn btn-block btn-warning' value='austragen'>");
            } else {
                if (veranstaltung.getAnzahlTeilnehmer() < veranstaltung.getMaxTeilnehmerzahl() && !veranstaltung.getVeranstalter().equals(benutzer)) {
                    out.println("<input type='submit' name='teilnehmen' id='teilnehmen' class='btn btn-block btn-success' value='teilnehmen'>");
                } else {
                    out.println("<input type='submit' name='teilnehmen' id='teilnehmen' class='btn btn-block btn-success' value='teilnehmen' disabled>");
                }
                out.println("<input type='submit' name='austragen' id='austragen' class='btn btn-block btn-warning' value='austragen' disabled>");
            }
            if (veranstaltung.getVeranstalter().equals(benutzer)) {
                out.println("<input type='submit' name='veranstaltungEntfernen' id='veranstaltungEntfernen' class='btn btn-block btn-danger' value='Veranstaltung entfernen'>");
            } else {
                out.println("<input type='submit' name='veranstaltungEntfernen' id='veranstaltungEntfernen' class='btn btn-block btn-danger' value='Veranstaltung entfernen' disabled>");
            }

            // out.println("</div>");
            out.println("</article>");
            out.println("</div>");
        }
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
