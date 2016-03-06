/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import entity.Artikel;
import entity.Benutzer;
import entity.Veranstaltung;
import interfaces.SitInPartyConnection;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
/**
 *
 * @author Markus
 */
public class AdminClient extends UnicastRemoteObject implements SitInPartyConnection {

    private Benutzer aktuellerBenutzer;
    private SitInPartyConnection server;

    /**
     *
     * @param serverURL
     * @throws NotBoundException
     * @throws MalformedURLException
     * @throws RemoteException
     */
    public AdminClient(String serverURL) throws NotBoundException, MalformedURLException, RemoteException {
        this.server = (SitInPartyConnection) Naming.lookup(serverURL);

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
        return this.server.anmelden(benutzername, passwort);
    }

    @Override
    public boolean artikelWegVonBenoetigteArtikel(String bezeichnung, String name) throws RemoteException {
        return this.server.artikelWegVonBenoetigteArtikel(bezeichnung, name);
    }

    @Override
    public boolean artikelWegVonVorhandeneArtikel(String bezeichnung, String name) throws RemoteException {
        return this.server.artikelWegVonVorhandeneArtikel(bezeichnung, name);
    }

    @Override
    public boolean artikelZuBenoetigteArtikel(String bezeichnung, String name) throws RemoteException {
        return this.server.artikelZuBenoetigteArtikel(bezeichnung, name);
    }

    @Override
    public boolean artikelZuVorhandeneArtikel(String bezeichnung, String name) throws RemoteException {
        return this.server.artikelZuVorhandeneArtikel(bezeichnung, name);
    }


    /**
     *
     * @param benutzername
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean benutzerLoeschen(String benutzername) throws RemoteException {
        return this.server.benutzerLoeschen(benutzername);
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
        return this.server.benutzerWegVonVeranstaltung(benutzername, name);
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
        return this.server.benutzerZuVeranstaltung(benutzername, name);
    }


    /**
     *
     * @return
     */
    public Benutzer getAktuellerBenutzer() {
        return aktuellerBenutzer;
    }

    /**
     *
     * @return @throws RemoteException
     */
    @Override
    public Map<String, Benutzer> getAlleBenutzer() throws RemoteException {
        return this.server.getAlleBenutzer();
    }
    /**
     *
     * @return @throws RemoteException
     */
    @Override
    public Map<String, Veranstaltung> getAlleVeranstaltungen() throws RemoteException {
        return this.server.getAlleVeranstaltungen();
    }

    @Override
    public Map<String, Artikel> getBenoetigteArtikel(String name) throws RemoteException {
        return this.server.getBenoetigteArtikel(name);
    }

    /**
     *
     * @param benutzername
     * @return
     * @throws RemoteException
     */
    @Override
    public Benutzer getBenutzer(String benutzername) throws RemoteException {
        return this.server.getBenutzer(benutzername);
    }
    /**
     *
     * @param benutzername
     * @return
     * @throws RemoteException
     */
    @Override
    public Map<String, Veranstaltung> getErstellteVeranstaltungenVonBenutzer(String benutzername) throws RemoteException {
        return this.server.getErstellteVeranstaltungenVonBenutzer(benutzername);
    }

    /**
     *
     * @return
     */
    public SitInPartyConnection getServer() {
        return this.server;
    }

    /**
     *
     * @param veranstaltung
     * @return
     * @throws RemoteException
     */
    @Override
    public HashSet<Benutzer> getTeilnehmerVonVeranstaltung(Veranstaltung veranstaltung) throws RemoteException {
        return this.server.getTeilnehmerVonVeranstaltung(veranstaltung);
    }

    /**
     *
     * @param name
     * @return
     * @throws RemoteException
     */
    @Override
    public Veranstaltung getVeranstaltung(String name) throws RemoteException {
        return this.server.getVeranstaltung(name);
    }

    @Override
    public Map<String, Artikel> getVorhandeneArtikel(String name) throws RemoteException {
        return this.server.getVorhandeneArtikel(name);
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
        return this.server.registrieren(benutzername, email, istAdmin, nachname, passwort, vorname);
    }

    /**
     *
     * @param aktuellerBenutzer
     */
    public void setAktuellerBenutzer(Benutzer aktuellerBenutzer) {
        this.aktuellerBenutzer = aktuellerBenutzer;
    }

    /**
     *
     * @param server
     */
    public void setServer(SitInPartyConnection server) {
        this.server = server;
    }

    /**
     *
     * @throws RemoteException
     */
    @Override
    public void testDatenAnlegen() throws RemoteException {
        this.server.testDatenAnlegen();
    }

    /**
     *
     * @param beschreibung
     * @param ort
     * @param name
     * @param startTermin
     * @param veranstalter
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean veranstaltungHinzufuegen(HashMap<String, Artikel> benoetigteArtikel, String beschreibung, Integer maxTeilnehmerzahl, String name, String ort, Date startTermin, HashSet<Benutzer> teilnehmendeBenutzer, Benutzer veranstalter, HashMap<String, Artikel> vorhandeneArtikel) throws RemoteException {
        return this.server.veranstaltungHinzufuegen(benoetigteArtikel, beschreibung, maxTeilnehmerzahl, name, ort, startTermin, teilnehmendeBenutzer, veranstalter, vorhandeneArtikel);
    }

    @Override
    public boolean veranstaltungHinzufuegen(String beschreibung, Integer maxTeilnehmerzahl, String name, String ort, Date startTermin, Benutzer veranstalter) throws RemoteException {
        return this.server.veranstaltungHinzufuegen(beschreibung, maxTeilnehmerzahl, name, ort, startTermin, veranstalter);
    }

    /**
     *
     * @param name
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean veranstaltungLoeschen(String name) throws RemoteException {
        return this.server.veranstaltungLoeschen(name);
    }
}
