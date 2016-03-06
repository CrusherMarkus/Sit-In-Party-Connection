/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import controller.Artikelverwaltung;
import controller.Benutzerverwaltung;
import controller.Datenbankverwaltung;
import controller.Veranstaltungsverwaltung;
import entity.Artikel;
import entity.Benutzer;
import static entity.Hilfsklasse.ANSI_BLUE;
import static entity.Hilfsklasse.ANSI_GREEN;
import static entity.Hilfsklasse.ANSI_PURPLE;
import static entity.Hilfsklasse.ANSI_RESET;
import entity.Veranstaltung;
import interfaces.SitInPartyConnection;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Markus
 */
public class Server extends UnicastRemoteObject implements SitInPartyConnection {

    private static Server instanz = null;

    /**
     *
     * @return
     */
    public static Server getInstanz() {
        if (instanz == null) {
            try {
                instanz = new Server();
            } catch (ClassNotFoundException | IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instanz;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Server server = getInstanz();
    }

    private Benutzerverwaltung benutzerVerwaltung;
    private Datenbankverwaltung datenbankVerwaltung;
    private Veranstaltungsverwaltung veranstaltungsVerwaltung;
    private Artikelverwaltung artikelVerwaltung;

    /**
     *
     * @throws RemoteException
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public Server() throws RemoteException, ClassNotFoundException, IOException {
        super();

        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("SitInPartyConnectionServer", this);
        veranstaltungsVerwaltung = Veranstaltungsverwaltung.getInstanz();
        benutzerVerwaltung = Benutzerverwaltung.getInstanz();
        artikelVerwaltung = Artikelverwaltung.getInstanz();
        datenbankVerwaltung = Datenbankverwaltung.getInstanz();
        datenbankVerwaltung.verbindungMitDatenbank();
        System.out.println(ANSI_BLUE + "Zusammenfassung" + ANSI_RESET);
        System.out.println("Anzahl der Benutzer:" + ANSI_PURPLE + benutzerVerwaltung.getAlleBenutzer().size() + ANSI_RESET);
        System.out.println("Anzahl der Veranstaltungen:" + ANSI_PURPLE + veranstaltungsVerwaltung.getAlleVeranstaltungen().size() + ANSI_RESET);
        System.out.println(ANSI_BLUE + "Server Status" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "SitInPartyConnectionServer läuft..." + ANSI_RESET);
    }

    /**
     *
     * @param benutzername
     * @param passwort
     * @return
     * @throws RemoteException
     */
    @Override
    public Benutzer anmelden(String benutzername, String passwort) throws RemoteException {
        return this.benutzerVerwaltung.benutzerAnmelden(benutzername, passwort);
    }

    @Override
    public boolean artikelWegVonBenoetigteArtikel(String bezeichnung, String name) throws RemoteException {
        return this.artikelVerwaltung.artikelWegVonBenoetigteArtikel(bezeichnung, name);
    }

    @Override
    public boolean artikelWegVonVorhandeneArtikel(String bezeichnung, String name) throws RemoteException {
        return this.artikelVerwaltung.artikelWegVonVorhandeneArtikel(bezeichnung, name);
    }

    @Override
    public boolean artikelZuBenoetigteArtikel(String bezeichnung, String name) throws RemoteException {
        return this.artikelVerwaltung.artikelZuBenoetigteArtikel(bezeichnung, name);
    }

    @Override
    public boolean artikelZuVorhandeneArtikel(String bezeichnung, String name) throws RemoteException {
        return this.artikelVerwaltung.artikelZuVorhandeneArtikel(bezeichnung, name);
    }

    /**
     *
     * @param benutzername
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean benutzerLoeschen(String benutzername) throws RemoteException {
        return this.benutzerVerwaltung.benutzerLoeschen(benutzername);
    }

    /**
     *
     * @param benutzername
     * @param name
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean benutzerWegVonVeranstaltung(String benutzername, String name) throws RemoteException {
        return this.veranstaltungsVerwaltung.benutzerWegVonVeranstaltung(benutzername, name);
    }

    /**
     *
     * @param benutzername
     * @param name
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean benutzerZuVeranstaltung(String benutzername, String name) throws RemoteException {
        return this.veranstaltungsVerwaltung.benutzerZuVeranstaltung(benutzername, name);
    }

    /**
     *
     * @return @throws RemoteException
     */
    @Override
    public Map<String, Benutzer> getAlleBenutzer() throws RemoteException {
        return this.benutzerVerwaltung.getAlleBenutzer();
    }

    /**
     *
     * @return @throws RemoteException
     */
    @Override
    public Map<String, Veranstaltung> getAlleVeranstaltungen() throws RemoteException {
        return this.veranstaltungsVerwaltung.getAlleVeranstaltungen();
    }

    @Override
    public Map<String, Artikel> getBenoetigteArtikel(String name) throws RemoteException {
        return this.veranstaltungsVerwaltung.getBenoetigteArtikel(name);
    }

    /**
     *
     * @param benutzername
     * @return
     * @throws RemoteException
     */
    @Override
    public Benutzer getBenutzer(String benutzername) throws RemoteException {
        return this.benutzerVerwaltung.getBenutzer(benutzername);
    }

    /**
     *
     * @return
     */
    public Benutzerverwaltung getBenutzerVerwaltung() {
        return benutzerVerwaltung;
    }

    @Override
    public Map<String, Veranstaltung> getErstellteVeranstaltungenVonBenutzer(String benutzername) throws RemoteException {
        return this.veranstaltungsVerwaltung.getErstellteVeranstaltungenVonBenutzer(benutzername);
    }

    @Override
    public HashSet<Benutzer> getTeilnehmerVonVeranstaltung(Veranstaltung veranstaltung) throws RemoteException {
        return this.veranstaltungsVerwaltung.getTeilnehmerVonVeranstaltung(veranstaltung);
    }

    /**
     *
     * @param name
     * @return
     * @throws RemoteException
     */
    @Override
    public Veranstaltung getVeranstaltung(String name) throws RemoteException {
        return this.veranstaltungsVerwaltung.getVeranstaltung(name);
    }

    /**
     *
     * @return
     */
    public Veranstaltungsverwaltung getVeranstaltungsVerwaltung() {
        return this.veranstaltungsVerwaltung;
    }

    @Override
    public Map<String, Artikel> getVorhandeneArtikel(String name) throws RemoteException {
        return this.veranstaltungsVerwaltung.getVorhandeneArtikel(name);
    }

    /**
     *
     * @param benutzername
     * @param email
     * @param istAdmin
     * @param nachname
     * @param passwort
     * @param vorname
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean registrieren(String benutzername, String email, Boolean istAdmin, String nachname, String passwort, String vorname) throws RemoteException {
        return this.benutzerVerwaltung.benutzerErstellen(benutzername, email, istAdmin, nachname, passwort, vorname);
    }

    /**
     *
     * @param benutzerVerwaltung
     */
    public void setBenutzerVerwaltung(Benutzerverwaltung benutzerVerwaltung) {
        this.benutzerVerwaltung = benutzerVerwaltung;
    }

    /**
     *
     * @param veranstaltungsVerwaltung
     */
    public void setVeranstaltungsVerwaltung(Veranstaltungsverwaltung veranstaltungsVerwaltung) {
        this.veranstaltungsVerwaltung = veranstaltungsVerwaltung;
    }

    /**
     *
     */
    @Override
    public void testDatenAnlegen() {
        benutzerVerwaltung.benutzerErstellen("mklemann", "Markus.Klemann@hs-osnabrueck.de", true, "Klemann", "pw", "Markus");
        benutzerVerwaltung.benutzerErstellen("testuser", "testuser@hs-osnabrueck.de", false, "User", "pw", "Test");
        benutzerVerwaltung.benutzerErstellen("testuser1", "testuser1@hs-osnabrueck.de", false, "User1", "pw", "Test1");
        benutzerVerwaltung.benutzerErstellen("testuser2", "testuser2@hs-osnabrueck.de", false, "User2", "pw", "Test2");
        benutzerVerwaltung.benutzerErstellen("testuser3", "testuser3@hs-osnabrueck.de", false, "User3", "pw", "Test3");
        benutzerVerwaltung.benutzerErstellen("testuser4", "testuser4@hs-osnabrueck.de", false, "User4", "pw", "Test4");

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        try {
            veranstaltungsVerwaltung.veranstaltungErstellen(new HashMap<String, Artikel>(), "Test Beschreibung", 5, "Test Name", "Test Ort", df.parse("01.05.2016 10:30"), new HashSet<Benutzer>(), benutzerVerwaltung.getBenutzer("testuser"), new HashMap<>());
            veranstaltungsVerwaltung.veranstaltungErstellen(new HashMap<String, Artikel>(), "Test Beschreibung1", 5, "Test Name1", "Test Ort1", df.parse("12.01.2016 06:30"), new HashSet<Benutzer>(), benutzerVerwaltung.getBenutzer("testuser"), new HashMap<>());
            veranstaltungsVerwaltung.veranstaltungErstellen(new HashMap<String, Artikel>(), "Test Beschreibung2", 7, "Test Name2", "Test Ort2", df.parse("01.07.2016 14:40"), new HashSet<Benutzer>(), benutzerVerwaltung.getBenutzer("testuser"), new HashMap<>());
            veranstaltungsVerwaltung.veranstaltungErstellen(new HashMap<String, Artikel>(), "Test Beschreibung3", 5, "Test Name3", "Test Ort3", df.parse("26.12.2016 21:30"), new HashSet<Benutzer>(), benutzerVerwaltung.getBenutzer("testuser1"), new HashMap<>());
            veranstaltungsVerwaltung.veranstaltungErstellen(new HashMap<String, Artikel>(), "Test Beschreibung4", 10, "Test Name4", "Test Ort4", df.parse("02.02.2016 14:15"), new HashSet<Benutzer>(), benutzerVerwaltung.getBenutzer("testuser1"), new HashMap<>());
            veranstaltungsVerwaltung.veranstaltungErstellen(new HashMap<String, Artikel>(), "Test Beschreibung5", 15, "Test Name5", "Test Ort5", df.parse("07.03.2016 14:30"), new HashSet<Benutzer>(), benutzerVerwaltung.getBenutzer("testuser2"), new HashMap<>());
            veranstaltungsVerwaltung.veranstaltungErstellen(new HashMap<String, Artikel>(), "Test Beschreibung6", 15, "Test Name6", "Test Ort6", df.parse("13.02.2016 14:00"), new HashSet<Benutzer>(), benutzerVerwaltung.getBenutzer("testuser3"), new HashMap<>());
            veranstaltungsVerwaltung.veranstaltungErstellen(new HashMap<String, Artikel>(), "Test Beschreibung7", 5, "Test Name7", "Test Ort7", df.parse("28.01.2016 15:30"), new HashSet<Benutzer>(), benutzerVerwaltung.getBenutzer("testuser4"), new HashMap<>());
        } catch (ParseException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        veranstaltungsVerwaltung.benutzerZuVeranstaltung("testuser1", "Test Name");
        veranstaltungsVerwaltung.benutzerZuVeranstaltung("testuser2", "Test Name");
        veranstaltungsVerwaltung.benutzerZuVeranstaltung("testuser3", "Test Name1");
        veranstaltungsVerwaltung.benutzerZuVeranstaltung("testuser4", "Test Name1");
        veranstaltungsVerwaltung.benutzerZuVeranstaltung("testuser1", "Test Name2");
        veranstaltungsVerwaltung.benutzerZuVeranstaltung("testuser3", "Test Name2");
        veranstaltungsVerwaltung.benutzerZuVeranstaltung("testuser4", "Test Name2");
        veranstaltungsVerwaltung.benutzerZuVeranstaltung("testuser", "Test Name3");

        artikelVerwaltung.artikelZuBenoetigteArtikel("Test Artikel", "Test Name");
        artikelVerwaltung.artikelZuBenoetigteArtikel("Test Artikel1", "Test Name");
        artikelVerwaltung.artikelZuBenoetigteArtikel("Test Artikel2", "Test Name");
        artikelVerwaltung.artikelZuBenoetigteArtikel("Test Artikel3", "Test Name");
        artikelVerwaltung.artikelZuBenoetigteArtikel("Test Artikel4", "Test Name");
        artikelVerwaltung.artikelZuBenoetigteArtikel("Test Artikel5", "Test Name");
        artikelVerwaltung.artikelZuBenoetigteArtikel("Test Artikel6", "Test Name");
        artikelVerwaltung.artikelZuBenoetigteArtikel("Test Artikel7", "Test Name");
        artikelVerwaltung.artikelZuBenoetigteArtikel("Test Artikel8", "Test Name1");
        artikelVerwaltung.artikelZuBenoetigteArtikel("Test Artikel9", "Test Name1");
        artikelVerwaltung.artikelZuBenoetigteArtikel("Test Artikel10", "Test Name1");
        artikelVerwaltung.artikelZuBenoetigteArtikel("Test Artikel11", "Test Name1");
        artikelVerwaltung.artikelZuBenoetigteArtikel("Test Artikel12", "Test Name2");
        artikelVerwaltung.artikelZuBenoetigteArtikel("Test Artikel13", "Test Name2");
        artikelVerwaltung.artikelZuBenoetigteArtikel("Test Artikel14", "Test Name2");
        artikelVerwaltung.artikelZuBenoetigteArtikel("Test Artikel15", "Test Name2");
        artikelVerwaltung.artikelZuBenoetigteArtikel("Test Artikel16", "Test Name3");
        artikelVerwaltung.artikelZuBenoetigteArtikel("Test Artikel17", "Test Name3");        
        
        artikelVerwaltung.artikelZuVorhandeneArtikel("Test Artikel3", "Test Name");
        artikelVerwaltung.artikelZuVorhandeneArtikel("Test Artikel4", "Test Name");
        artikelVerwaltung.artikelZuVorhandeneArtikel("Test Artikel5", "Test Name");
        artikelVerwaltung.artikelZuVorhandeneArtikel("Test Artikel6", "Test Name");
        artikelVerwaltung.artikelZuVorhandeneArtikel("Test Artikel10", "Test Name1");
        artikelVerwaltung.artikelZuVorhandeneArtikel("Test Artikel11", "Test Name1");
        artikelVerwaltung.artikelZuVorhandeneArtikel("Test Artikel13", "Test Name2");      
    }

    /**
     *
     * @param beschreibung
     * @param maxTeilnehmerzahl
     * @param ort
     * @param name
     * @param startTermin
     * @param veranstalter
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean veranstaltungHinzufuegen(HashMap<String, Artikel> benoetigteArtikel, String beschreibung, Integer maxTeilnehmerzahl, String name, String ort, Date startTermin, HashSet<Benutzer> teilnehmendeBenutzer, Benutzer veranstalter, HashMap<String, Artikel> vorhandeneArtikel) throws RemoteException {
        return this.veranstaltungsVerwaltung.veranstaltungErstellen(benoetigteArtikel, beschreibung, maxTeilnehmerzahl, name, ort, startTermin, teilnehmendeBenutzer, veranstalter, vorhandeneArtikel);
    }

    @Override
    public boolean veranstaltungHinzufuegen(String beschreibung, Integer maxTeilnehmerzahl, String name, String ort, Date startTermin, Benutzer veranstalter) throws RemoteException {
        return this.veranstaltungsVerwaltung.veranstaltungErstellen(beschreibung, maxTeilnehmerzahl, name, ort, startTermin, veranstalter);
    }

    /**
     *
     * @param name
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean veranstaltungLoeschen(String name) throws RemoteException {
        return this.veranstaltungsVerwaltung.veranstaltungLoeschen(name);
    }
}
