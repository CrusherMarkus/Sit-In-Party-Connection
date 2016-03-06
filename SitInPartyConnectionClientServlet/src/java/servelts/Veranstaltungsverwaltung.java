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
import java.io.InputStream;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import servelts.hilfsklasse.DrawHeaderFooter;

/**
 *
 * @author Markus
 */
@WebServlet(name = "Veranstaltungsverwaltung", urlPatterns = {"/Veranstaltungsverwaltung"})
@MultipartConfig(maxFileSize = 16177215)
public class Veranstaltungsverwaltung extends HttpServlet {

    private void austragen(AdminClient client, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String name = request.getParameter("name");
        Benutzer benutzer = (Benutzer) session.getAttribute("benutzer");
        Veranstaltung veranstaltung;
        try {
            veranstaltung = client.getVeranstaltung(name);
            if (veranstaltung.getTeilnehmendeBenutzer().contains(benutzer)) {
                System.out.println("benutzer.getBenutzername():" + benutzer.getBenutzername());
                System.out.println("name:" + name);
                if (client.benutzerWegVonVeranstaltung(benutzer.getBenutzername(), name)) {
                    request.setAttribute("message", "<div class='alert alert-success' role='alert'><strong>Erfolgreich!</strong> Der Benutzer " + benutzer.getBenutzername() + " wurde aus der Veranstaltung " + name + " entfernt.</div>");
                } else {
                    request.setAttribute("message", "<div class='alert alert-danger' role='alert'><strong>Gescheitert!</strong> Der Benutzer " + benutzer.getBenutzername() + " wurde aus der Veranstaltung " + name + " nicht entfernt.</div>");
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Veranstaltungsverwaltung.class.getName()).log(Level.SEVERE, null, ex);
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

        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        PrintWriter out = response.getWriter();
        AdminClient client = (AdminClient) session.getAttribute("client");
        if (client == null) {
            out.println("<html><head><title>Session Fehler</title></head>");
            out.close();
        }

        String veranstaltungHinzufuegen = request.getParameter("veranstaltungHinzufuegen");
        String veranstaltungLoeschen = request.getParameter("veranstaltungLoeschen");
        String veranstaltungAddTeilnehmer = request.getParameter("veranstaltungAddTeilnehmer");
        String teilnehmen = request.getParameter("teilnehmen");
        String austragen = request.getParameter("austragen");
        String veranstaltungEntfernen = request.getParameter("veranstaltungEntfernen");

        if (veranstaltungHinzufuegen != null) {
            veranstaltungHinzufuegen(client, request);
            RequestDispatcher rs = request.getRequestDispatcher("veranstaltungsverwaltung.jsp");
            rs.include(request, response);
        }

        if (veranstaltungLoeschen != null) {
            veranstaltungLoeschen(client, request);
            RequestDispatcher rs = request.getRequestDispatcher("veranstaltungsverwaltung.jsp");
            rs.include(request, response);
        }

        if (veranstaltungAddTeilnehmer != null) {
            veranstaltungHinzufuegen(client, request);
            RequestDispatcher rs = request.getRequestDispatcher("Home");
            rs.include(request, response);
        }

        if (teilnehmen != null) {
            teilnehmen(client, request);
            RequestDispatcher rs = request.getRequestDispatcher("Home");
            rs.include(request, response);
        }

        if (austragen != null) {
            austragen(client, request);
            RequestDispatcher rs = request.getRequestDispatcher("Home");
            rs.include(request, response);
        }

        if (veranstaltungEntfernen != null) {
            veranstaltungEntfernen(client, request);
            RequestDispatcher rs = request.getRequestDispatcher("Home");
            rs.include(request, response);
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

    private void teilnehmen(AdminClient client, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Benutzer benutzer = (Benutzer) session.getAttribute("benutzer");
        String name = request.getParameter("name");
        Veranstaltung veranstaltung;
        try {
            veranstaltung = client.getVeranstaltung(name);
            if (name.matches(veranstaltung.getName())) {
                if (client.benutzerZuVeranstaltung(benutzer.getBenutzername(), name)) {
                    request.setAttribute("message", "<div class='alert alert-success' role='alert'><strong>Erfolgreich!</strong> Der Benutzer " + benutzer.getBenutzername() + " wurde der Veranstaltung " + name + " hinzugefügt.</div>");
                } else if (veranstaltung.getAnzahlTeilnehmer() >= veranstaltung.getMaxTeilnehmerzahl()) {
                    request.setAttribute("message", "<div class='alert alert-danger' role='alert'><strong>Gescheitert!</strong> Die maximale Teilnehmerzahl wurde schon erreicht.</div>");
                } else {
                    request.setAttribute("message", "<div class='alert alert-danger' role='alert'><strong>Gescheitert!</strong> Der Benutzer " + benutzer.getBenutzername() + " ist bereits in der Veranstaltung " + name + " vorhanden.</div>");
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Veranstaltungsverwaltung.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void veranstaltungEntfernen(AdminClient client, HttpServletRequest request) {
        String name = request.getParameter("name");
        try {
            Map<String, Veranstaltung> alleVeranstaltungen;
            alleVeranstaltungen = client.getAlleVeranstaltungen();
            if (alleVeranstaltungen.containsKey(name)) {
                if (client.veranstaltungLoeschen(name)) {
                    request.setAttribute("message", "<div class='alert alert-success' role='alert'><strong>Erfolgreich!</strong> Die Veranstaltung " + name + " wurde gelöscht.</div>");
                } else {
                    request.setAttribute("message", "<div class='alert alert-danger' role='alert'><strong>Erfolgreich!</strong> Die Veranstaltung " + name + " wurde nicht gelöscht.</div>");
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Veranstaltungsverwaltung.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void veranstaltungHinzufuegen(AdminClient client, HttpServletRequest request) {
        String name = request.getParameter("nameHinzufuegen");
        String startTerminString = request.getParameter("startTerminHinzufuegen");
        String ort = request.getParameter("ortHinzufuegen");
        String beschreibung = request.getParameter("beschreibungHinzufuegen");
        Integer maxTeilnehmerzahl = Integer.parseInt(request.getParameter("maxTeilnehmerzahlHinzufuegen"));
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date startTermin;
        if (isValidFormat("dd.MM.yyyy HH:mm", startTerminString)) {
            try {
                startTermin = df.parse(startTerminString);
                client.veranstaltungHinzufuegen(beschreibung, maxTeilnehmerzahl, name, ort, startTermin, client.getAktuellerBenutzer());
                request.setAttribute("message", "<div class='alert alert-success' role='alert'><strong>Erfolgreich!</strong> Die Veranstaltung " + name + " wurde angelegt.</div>");
            } catch (ParseException | RemoteException ex) {
                Logger.getLogger(Veranstaltungsverwaltung.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            request.setAttribute("message", "<div class='alert alert-danger' role='alert'><strong>Gescheitert!</strong> Das Datum wurde falsch eingegeben.</div>");
        }
    }

    private void veranstaltungLoeschen(AdminClient client, HttpServletRequest request) {
        String name = request.getParameter("veranstaltungnameLoeschen");
        try {
            if (client.veranstaltungLoeschen(name)) {
                request.setAttribute("message", "<div class='alert alert-success' role='alert'><strong>Erfolgreich!</strong> Die Veranstaltung " + name + " wurde gelöscht.</div>");

            } else {
                request.setAttribute("message", "<div class='alert alert-success' role='alert'><strong>Erfolgreich!</strong> Die Veranstaltung " + name + " wurde nicht gelöscht.</div>");
            }
            request.setAttribute("message", "<div class='alert alert-success' role='alert'><strong>Erfolgreich!</strong> Die Veranstaltung " + name + " wurde gelöscht.</div>");
        } catch (RemoteException ex) {
            Logger.getLogger(Veranstaltungsverwaltung.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param format
     * @param value
     * @return
     */
    public static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            Logger.getLogger(Veranstaltungsverwaltung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date != null;
    }

}
