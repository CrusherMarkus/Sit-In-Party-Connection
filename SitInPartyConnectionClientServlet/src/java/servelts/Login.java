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
import java.rmi.NotBoundException;
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
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    private AdminClient client;

    private boolean anmelden(HttpServletRequest request) {

        String benutzername = request.getParameter("benutzername");
        String passwort = request.getParameter("passwort");

        try {
            Benutzer benutzer = this.client.anmelden(benutzername, passwort);
            if (benutzer != null) {
                this.client.setAktuellerBenutzer(benutzer);
                HttpSession session = request.getSession(true);
                session.setAttribute("benutzer", benutzer);
                return true;
            } else {
                request.setAttribute("message", "<div class='alert alert-danger' role='alert'>Falsche Anmeldedaten</div>");
                return false;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(20 * 60);

        PrintWriter out;
        try {
            out = response.getWriter();

            this.client = new AdminClient("rmi://localhost:1099/SitInPartyConnectionServer");
            if (this.client == null) {
                out.println("<html><head><title>Session Fehler</title></head>");
                out.close();
            }
            session.setAttribute("client", this.client);

            String loginSubmit = request.getParameter("login-submit");
            String registerSubmit = request.getParameter("register-submit");

            // Registrierung
            if (registerSubmit != null) {
                registrieren(request);
                RequestDispatcher rs = request.getRequestDispatcher("Home");
                rs.include(request, response);
            } 
            
            // Anmeldung 
            if (loginSubmit != null) {
                if (anmelden(request)) {
                    RequestDispatcher rs = request.getRequestDispatcher("Home");
                    rs.include(request, response);
                } else {
                    RequestDispatcher rs = request.getRequestDispatcher("index.jsp");
                    rs.include(request, response);
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException | IOException | NotBoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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

    private void registrieren(HttpServletRequest request) {

        String benutzername = request.getParameter("benutzername");
        String passwort = request.getParameter("passwort");
        String email = request.getParameter("email");
        String nachname = request.getParameter("nachname");
        String vorname = request.getParameter("vorname");
        try {
            if (this.client.registrieren(benutzername, email, false, nachname, passwort, vorname)) {
                Benutzer benutzer = this.client.anmelden(benutzername, passwort);
                HttpSession session = request.getSession(true);
                session.setAttribute("benutzer", benutzer);
                if (benutzer != null) {
                    this.client.setAktuellerBenutzer(benutzer);
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
