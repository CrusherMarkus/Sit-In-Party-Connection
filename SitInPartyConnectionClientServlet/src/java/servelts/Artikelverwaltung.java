/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servelts;

import client.AdminClient;
import entity.Veranstaltung;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
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
@WebServlet(name = "Artikelverwaltung", urlPatterns = {"/Artikelverwaltung"})
public class Artikelverwaltung extends HttpServlet {

    private void artikelHinzufuegen(AdminClient client, HttpServletRequest request) {

        String name = request.getParameter("name");
        String bezeichnung = request.getParameter("bezeichnung");

        try {
            if (!client.getVeranstaltung(name).getBenoetigteArtikel().containsKey(bezeichnung)) {
                if (!client.getVeranstaltung(name).getVorhandeneArtikel().containsKey(bezeichnung)) {
                    client.artikelZuBenoetigteArtikel(bezeichnung, name);
                    request.setAttribute("message", "<div class='alert alert-success' role='alert'><strong>Erfolgreich!</strong> Der Artikel " + bezeichnung + " wurde angelegt.</div>");
                } else {
                    request.setAttribute("message", "<div class='alert alert-danger' role='alert'><strong>Gescheitert!</strong> Der Artikel " + bezeichnung + " ist bereits vorhanden.</div>");
                }
            } else {
                request.setAttribute("message", "<div class='alert alert-danger' role='alert'><strong>Gescheitert!</strong> Der Artikel " + bezeichnung + " ist bereits vorhanden.</div>");
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Artikelverwaltung.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void artikelZuVorhandeneArtikel(AdminClient client, HttpServletRequest request) {
        String name = request.getParameter("name");
        String bezeichnung = request.getParameter("bezeichnung");

        Veranstaltung veranstaltung;
        try {
            veranstaltung = client.getVeranstaltung(name);
            if (name.matches(veranstaltung.getName())) {
                if (client.artikelZuVorhandeneArtikel(bezeichnung, name)) {
                    request.setAttribute("message", "<div class='alert alert-success' role='alert'><strong>Erfolgreich!</strong> Der Artikel " + bezeichnung + " wurde mitgebracht.</div>");
                } else {
                    request.setAttribute("message", "<div class='alert alert-danger' role='alert'><strong>Gescheitert!</strong> Der Artikel " + bezeichnung + " wurde nicht mitgebracht.</div>");
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Artikelverwaltung.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void benoetigtenArtikelLoeschen(AdminClient client, HttpServletRequest request) {

        String name = request.getParameter("name");
        String bezeichnung = request.getParameter("bezeichnung");

        Veranstaltung veranstaltung;
        try {
            veranstaltung = client.getVeranstaltung(name);
            if (name.matches(veranstaltung.getName())) {
                if (client.artikelWegVonBenoetigteArtikel(bezeichnung, name)) {
                    request.setAttribute("message", "<div class='alert alert-success' role='alert'><strong>Erfolgreich!</strong> Der benötigte Artikel " + bezeichnung + " wurde gelöscht.</div>");
                } else {
                    request.setAttribute("message", "<div class='alert alert-danger' role='alert'><strong>Gescheitert!</strong> Der benötigte Artikel " + bezeichnung + " wurde nicht gelöscht.</div>");
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Artikelverwaltung.class.getName()).log(Level.SEVERE, null, ex);
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

        String artikelHinzufuegen = request.getParameter("artikelHinzufuegen");
        String artikelZuVorhandeneArtikel = request.getParameter("artikelZuVorhandeneArtikel");

        String benoetigtenArtikelLoeschen = request.getParameter("benoetigtenArtikelLoeschen");
        String vorhandenenArtikelLoeschen = request.getParameter("vorhandenenArtikelLoeschen");
        String vorhandenenArtikelZurueck = request.getParameter("vorhandenenArtikelZurueck");

        if (artikelHinzufuegen != null) {
            artikelHinzufuegen(client, request);
            RequestDispatcher rs = request.getRequestDispatcher("Home");
            rs.include(request, response);
        }

        if (artikelZuVorhandeneArtikel != null) {
            artikelZuVorhandeneArtikel(client, request);
            RequestDispatcher rs = request.getRequestDispatcher("Home");
            rs.include(request, response);
        }

        if (benoetigtenArtikelLoeschen != null) {
            benoetigtenArtikelLoeschen(client, request);
            RequestDispatcher rs = request.getRequestDispatcher("Home");
            rs.include(request, response);
        }

        if (vorhandenenArtikelLoeschen != null) {
            vorhandenenArtikelLoeschen(client, request);
            RequestDispatcher rs = request.getRequestDispatcher("Home");
            rs.include(request, response);
        }

        if (vorhandenenArtikelZurueck != null) {
            vorhandenenArtikelZurueck(client, request);
            RequestDispatcher rs = request.getRequestDispatcher("Home");
            rs.include(request, response);
        }

    }

    private void vorhandenenArtikelLoeschen(AdminClient client, HttpServletRequest request) {
        String name = request.getParameter("name");
        String bezeichnung = request.getParameter("bezeichnung");

        Veranstaltung veranstaltung;
        try {
            veranstaltung = client.getVeranstaltung(name);
            if (name.matches(veranstaltung.getName())) {
                if (client.artikelWegVonVorhandeneArtikel(bezeichnung, name)) {
                    request.setAttribute("message", "<div class='alert alert-success' role='alert'><strong>Erfolgreich!</strong> Der vorhandenen Artikel " + bezeichnung + " wurde gelöscht.</div>");
                } else {
                    request.setAttribute("message", "<div class='alert alert-danger' role='alert'><strong>Gescheitert!</strong> Der vorhandenen Artikel " + bezeichnung + " wurde nicht gelöscht.</div>");
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Artikelverwaltung.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void vorhandenenArtikelZurueck(AdminClient client, HttpServletRequest request) {
        vorhandenenArtikelLoeschen(client, request);
        artikelHinzufuegen(client, request);
    }
}
