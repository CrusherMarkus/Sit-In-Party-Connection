/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Benutzer;
import static entity.Hilfsklasse.ANSI_GREEN;
import static entity.Hilfsklasse.ANSI_RED;
import static entity.Hilfsklasse.ANSI_RESET;
import entity.Veranstaltung;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Markus, Simon
 */
public class Benutzerverwaltung {

    private static Map<String, Benutzer> alleBenutzer = null;
    private static Benutzerverwaltung instanz = null;

    /**
     *
     * @return
     */
    public static Benutzerverwaltung getInstanz() {
        if (instanz == null) {
            instanz = new Benutzerverwaltung();
        }
        return instanz;
    }

    /**
     *
     * @param benutzername
     * @param passwort
     * @return
     */
    public Benutzer benutzerAnmelden(String benutzername, String passwort) {
        if (alleBenutzer.containsKey(benutzername)) {
            Benutzer benutzer = alleBenutzer.get(benutzername);
            if (benutzer.getPasswort().equals(passwort)) {
                System.out.println(ANSI_GREEN + "Erfolgreich als " + benutzername + " angemeldet." + ANSI_RESET);
                return benutzer;
            } else {
                System.out.println(ANSI_RED + "Das eingegebene Passwort falsch." + ANSI_RESET);
            }
        } else {
            System.out.println(ANSI_RED + "Der Benutzer " + benutzername + " wurde nicht gefunden." + ANSI_RESET);
        }
        return null;
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
     */
    public boolean benutzerErstellen(String benutzername, String email, Boolean istAdmin, String nachname, String passwort, String vorname) {
        if (alleBenutzer.containsKey(benutzername)) {
            System.out.println(ANSI_RED + "Der Benutzername " + benutzername + " ist bereits vergeben." + ANSI_RESET);
            return false;
        }
        alleBenutzer.put(benutzername, new Benutzer(benutzername, email, istAdmin, nachname, passwort, vorname));
        Datenbankverwaltung dv = Datenbankverwaltung.getInstanz();
        if (dv.benutzerSpeichern(benutzername, email, istAdmin, nachname, passwort, vorname)) {
            System.out.println(ANSI_GREEN + "Der Benutzer " + benutzername + " wurde erfolgreich angelegt." + ANSI_RESET);
            return true;
        } else {
            System.out.println(ANSI_RED + "Der Benutzer " + benutzername + " wurde nicht angelegt." + ANSI_RESET);
            return false;
        }
    }

    /**
     *
     * @param benutzername
     * @return
     */
    public boolean benutzerLoeschen(String benutzername) {
        if (alleBenutzer.containsKey(benutzername)) {
            
            Veranstaltungsverwaltung vv = Veranstaltungsverwaltung.getInstanz();
                        
            // Als Teilnehmer austragen
            Map<String, Veranstaltung> alleVeranstaltungen = vv.getAlleVeranstaltungen();
            for(Veranstaltung veranstaltung : alleVeranstaltungen.values()) {
                if(veranstaltung.getTeilnehmendeBenutzer().contains(getBenutzer(benutzername))) {
                    vv.benutzerWegVonVeranstaltung(benutzername, veranstaltung.getName());
                }
            }
            
            // Veranstaltungen des Benutzers loeschen
            Map<String, Veranstaltung> erstellteVeranstaltungenVonBenutzer = vv.getErstellteVeranstaltungenVonBenutzer(benutzername);
            for (Veranstaltung veranstaltung : erstellteVeranstaltungenVonBenutzer.values()) {                
                vv.veranstaltungLoeschen(veranstaltung.getName());
            }

            // Benutzer loeschen
            alleBenutzer.remove(benutzername);

            Datenbankverwaltung dv = Datenbankverwaltung.getInstanz();
            if (dv.benutzerLoeschen(benutzername)) {
                System.out.println(ANSI_GREEN + "Der Benutzer " + benutzername + " wurde geloescht." + ANSI_RESET);
                return true;
            } else {
                System.out.println(ANSI_GREEN + "Der Benutzer " + benutzername + " wurde nicht geloescht." + ANSI_RESET);
                return false;
            }
        } else {
            System.out.println(ANSI_RED + "Der Benutzer " + benutzername + " ist nicht vorhanden." + ANSI_RESET);
            return false;
        }
    }

    /**
     *
     * @return
     */
    public Map<String, Benutzer> getAlleBenutzer() {
        if (alleBenutzer != null) {
            return alleBenutzer;
        } else {
            return alleBenutzer = new HashMap<>();
        }
    }

    /**
     *
     * @param benutzername
     * @return
     */
    public Benutzer getBenutzer(String benutzername) {
        if (alleBenutzer.containsKey(benutzername)) {
            return alleBenutzer.get(benutzername);
        } else {
            System.out.println(ANSI_RED + "Der Benutzer konnte nicht geholt werden." + ANSI_RESET);
            return null;
        }
    }
}
