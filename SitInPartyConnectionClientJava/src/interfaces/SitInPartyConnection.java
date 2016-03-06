/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entity.Artikel;
import entity.Benutzer;
import entity.Veranstaltung;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author Markus
 */
public interface SitInPartyConnection extends Remote {

    /**
     *
     * @param benutzername
     * @param passwort
     * @return
     * @throws RemoteException
     */
    public Benutzer anmelden(String benutzername, String passwort) throws RemoteException;

    /**
     *
     * @param bezeichnung
     * @param name
     * @return
     * @throws RemoteException
     */
    public boolean artikelWegVonBenoetigteArtikel(String bezeichnung, String name) throws RemoteException;

    /**
     *
     * @param bezeichnung
     * @param name
     * @return
     * @throws RemoteException
     */
    public boolean artikelWegVonVorhandeneArtikel(String bezeichnung, String name) throws RemoteException;

    /**
     *
     * @param bezeichnung
     * @param name
     * @return
     * @throws RemoteException
     */
    public boolean artikelZuBenoetigteArtikel(String bezeichnung, String name) throws RemoteException;

    /**
     *
     * @param bezeichnung
     * @param name
     * @return
     * @throws RemoteException
     */
    public boolean artikelZuVorhandeneArtikel(String bezeichnung, String name) throws RemoteException;

    /**
     *
     * @param benutzername
     * @return
     * @throws RemoteException
     */
    public boolean benutzerLoeschen(String benutzername) throws RemoteException;

    /**
     *
     * @param benutzername
     * @param name
     * @return
     * @throws RemoteException
     */
    public boolean benutzerWegVonVeranstaltung(String benutzername, String name) throws RemoteException;

    /**
     *
     * @param benutzername
     * @param name
     * @return
     * @throws RemoteException
     */
    public boolean benutzerZuVeranstaltung(String benutzername, String name) throws RemoteException;

    /**
     *
     * @return @throws RemoteException
     */
    public Map<String, Benutzer> getAlleBenutzer() throws RemoteException;

    /**
     *
     * @return @throws RemoteException
     */
    public Map<String, Veranstaltung> getAlleVeranstaltungen() throws RemoteException;

    /**
     *
     * @param name
     * @return
     * @throws RemoteException
     */
    public Map<String, Artikel> getBenoetigteArtikel(String name) throws RemoteException;

    /**
     *
     * @param benutzername
     * @return
     * @throws RemoteException
     */
    public Benutzer getBenutzer(String benutzername) throws RemoteException;

    /**
     *
     * @param benutzername
     * @return
     * @throws RemoteException
     */
    public Map<String, Veranstaltung> getErstellteVeranstaltungenVonBenutzer(String benutzername) throws RemoteException;

    /**
     *
     * @param veranstaltung
     * @return
     * @throws RemoteException
     */
    public HashSet<Benutzer> getTeilnehmerVonVeranstaltung(Veranstaltung veranstaltung) throws RemoteException;

    /**
     *
     * @param name
     * @return
     * @throws RemoteException
     */
    public Veranstaltung getVeranstaltung(String name) throws RemoteException;

    /**
     *
     * @param name
     * @return
     * @throws RemoteException
     */
    public Map<String, Artikel> getVorhandeneArtikel(String name) throws RemoteException;

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
    public boolean registrieren(String benutzername, String email, Boolean istAdmin, String nachname, String passwort, String vorname) throws RemoteException;

    /**
     *
     * @throws RemoteException
     */
    public void testDatenAnlegen() throws RemoteException;

    /**
     *
     * @param benoetigteArtikel
     * @param beschreibung
     * @param maxTeilnehmerzahl
     * @param ort
     * @param name
     * @param startTermin
     * @param teilnehmendeBenutzer
     * @param veranstalter
     * @param vorhandeneArtikel
     * @return
     * @throws RemoteException
     */
    public boolean veranstaltungHinzufuegen(HashMap<String, Artikel> benoetigteArtikel, String beschreibung, Integer maxTeilnehmerzahl, String name, String ort, Date startTermin, HashSet<Benutzer> teilnehmendeBenutzer, Benutzer veranstalter, HashMap<String, Artikel> vorhandeneArtikel) throws RemoteException;

    /**
     *
     * @param beschreibung
     * @param maxTeilnehmerzahl
     * @param name
     * @param ort
     * @param startTermin
     * @param veranstalter
     * @return
     * @throws RemoteException
     */
    public boolean veranstaltungHinzufuegen(String beschreibung, Integer maxTeilnehmerzahl, String name, String ort, Date startTermin, Benutzer veranstalter) throws RemoteException;

    /**
     *
     * @param name
     * @return
     * @throws RemoteException
     */
    public boolean veranstaltungLoeschen(String name) throws RemoteException;

}
