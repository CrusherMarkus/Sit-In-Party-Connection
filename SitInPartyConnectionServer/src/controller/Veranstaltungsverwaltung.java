/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Artikel;
import entity.Benutzer;
import static entity.Hilfsklasse.ANSI_GREEN;
import static entity.Hilfsklasse.ANSI_RED;
import static entity.Hilfsklasse.ANSI_RESET;
import entity.Veranstaltung;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author Markus, Simon
 */
public class Veranstaltungsverwaltung {

    private static Map<String, Veranstaltung> alleVeranstaltungen = null;
    private static Veranstaltungsverwaltung instanz = null;

    /**
     *
     * @return
     */
    public static Veranstaltungsverwaltung getInstanz() {
        if (instanz == null) {
            instanz = new Veranstaltungsverwaltung();
        }
        return instanz;
    }

    /**
     *
     * @param benutzername
     * @param name
     * @return
     */
    public boolean benutzerWegVonVeranstaltung(String benutzername, String name) {

        Benutzerverwaltung bv = Benutzerverwaltung.getInstanz();
        Benutzer benutzer = bv.getBenutzer(benutzername);

        Veranstaltung veranstaltung = alleVeranstaltungen.get(name);
        HashSet<Benutzer> teilnehmendeBenutzer = veranstaltung.getTeilnehmendeBenutzer();
        if (teilnehmendeBenutzer.contains(benutzer)) {
            if (teilnehmendeBenutzer.remove(benutzer)) {
                veranstaltung.setTeilnehmendeBenutzer(teilnehmendeBenutzer);

                Datenbankverwaltung dv = Datenbankverwaltung.getInstanz();
                if (dv.benutzerWegVonVeranstaltung(benutzername, name)) {
                    System.out.println(ANSI_GREEN + "Der Benutzer " + benutzername + " wurde aus der Veranstaltung " + name + " entfernt." + ANSI_RESET);
                    return true;
                } else {
                    System.out.println(ANSI_RED + "Der Benutzer " + benutzername + " wurde nicht aus der Veranstaltung " + name + " entfernt." + ANSI_RESET);
                    return false;
                }
            } else {
                System.out.println(ANSI_RED + "Der Benutzer " + benutzername + " wurde nicht aus der Veranstaltung " + name + " entfernt." + ANSI_RESET);
                return false;
            }
        } else {
            System.out.println(ANSI_RED + "Der Benutzer " + benutzername + " ist nicht in der Veranstaltung vorhanden.");
            return false;
        }
    }

    /**
     *
     * @param benutzername
     * @param name
     * @return
     */
    public boolean benutzerZuVeranstaltung(String benutzername, String name) {
        Benutzerverwaltung bv = Benutzerverwaltung.getInstanz();
        Benutzer benutzer = bv.getBenutzer(benutzername);

        Veranstaltung veranstaltung = alleVeranstaltungen.get(name);
        HashSet<Benutzer> teilnehmendeBenutzer = veranstaltung.getTeilnehmendeBenutzer();

        if (veranstaltung.getAnzahlTeilnehmer() < veranstaltung.getMaxTeilnehmerzahl() && teilnehmendeBenutzer.add(benutzer)) {
            if (teilnehmendeBenutzer.contains(benutzer)) {
                veranstaltung.setTeilnehmendeBenutzer(teilnehmendeBenutzer);

                Datenbankverwaltung dv = Datenbankverwaltung.getInstanz();
                if (dv.benutzerZuVeranstaltung(benutzername, name)) {
                    System.out.println(ANSI_GREEN + "Der Benutzer " + benutzername + " wurde zur Veranstaltung " + name + " hinzugefuegt." + ANSI_RESET);
                    return true;
                } else {
                    System.out.println(ANSI_RED + "Der Benutzer " + benutzername + " wurde nicht zur Veranstaltung " + name + " hinzugefuegt." + ANSI_RESET);
                    return false;
                }
            } else {
                System.out.println(ANSI_RED + "Der Benutzer " + benutzername + " wurde zur Veranstaltung " + name + " nicht hinzugefuegt." + ANSI_RESET);
                return false;
            }
        } else {
            System.out.println(ANSI_RED + "Die maximale Teilnehmerzahl wurde schon erreicht" + ANSI_RESET);
            return false;
        }
    }

    /**
     *
     * @return
     */
    public Map<String, Veranstaltung> getAlleVeranstaltungen() {
        if (alleVeranstaltungen == null) {
            return alleVeranstaltungen = new HashMap<>();
        } else {
            return alleVeranstaltungen;
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public Map<String, Artikel> getBenoetigteArtikel(String name) {

        Veranstaltung veranstaltung = getVeranstaltung(name);
        HashMap<String, Artikel> benoetigteArtikel = veranstaltung.getBenoetigteArtikel();

        if (benoetigteArtikel != null) {
            return benoetigteArtikel;
        } else {
            return null;
        }
    }

    /**
     *
     * @param benutzername
     * @return
     */
    public Map<String, Veranstaltung> getErstellteVeranstaltungenVonBenutzer(String benutzername) {
        Map<String, Veranstaltung> erstellteVeranstaltungenVonBenutzer = new HashMap<>();
        for (Veranstaltung veranstaltung : alleVeranstaltungen.values()) {
            if (veranstaltung.getVeranstalter().getBenutzername().equals(benutzername)) {
                erstellteVeranstaltungenVonBenutzer.put(veranstaltung.getName(), veranstaltung);
            }
        }
        return erstellteVeranstaltungenVonBenutzer;
    }

    /**
     *
     * @param veranstaltung
     * @return
     */
    public HashSet<Benutzer> getTeilnehmerVonVeranstaltung(Veranstaltung veranstaltung) {

        HashSet<Benutzer> teilnehmerVonVeranstaltung = veranstaltung.getTeilnehmendeBenutzer();

        if (teilnehmerVonVeranstaltung != null) {
            return teilnehmerVonVeranstaltung;
        } else {
            return null;
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public Veranstaltung getVeranstaltung(String name) {
        if (alleVeranstaltungen.containsKey(name)) {
            return alleVeranstaltungen.get(name);
        } else {
            return null;
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public Map<String, Artikel> getVorhandeneArtikel(String name) {

        Veranstaltung veranstaltung = getVeranstaltung(name);
        HashMap<String, Artikel> vorhandeneArtikel = veranstaltung.getVorhandeneArtikel();

        if (vorhandeneArtikel != null) {
            return vorhandeneArtikel;
        } else {
            return null;
        }
    }

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
     */
    public boolean veranstaltungErstellen(HashMap<String, Artikel> benoetigteArtikel, String beschreibung, Integer maxTeilnehmerzahl, String name, String ort, Date startTermin, HashSet<Benutzer> teilnehmendeBenutzer, Benutzer veranstalter, HashMap<String, Artikel> vorhandeneArtikel) {
        if (alleVeranstaltungen.containsKey(name)) {
            System.out.println(ANSI_RED + "Der Name ist bereits vergeben" + ANSI_RESET);
            return false;
        }
        alleVeranstaltungen.put(name, new Veranstaltung(benoetigteArtikel, beschreibung, maxTeilnehmerzahl, name, ort, startTermin, teilnehmendeBenutzer, veranstalter, vorhandeneArtikel));

        Datenbankverwaltung dv = Datenbankverwaltung.getInstanz();
        if (dv.veranstaltungErstellen(benoetigteArtikel, beschreibung, maxTeilnehmerzahl, name, ort, startTermin, teilnehmendeBenutzer, veranstalter, vorhandeneArtikel)) {
            System.out.println(ANSI_GREEN + "Veranstaltung " + name + " wurde erfolgreich angelegt." + ANSI_RESET);
            return true;
        } else {
            System.out.println(ANSI_RED + "Veranstaltung " + name + " wurde nicht angelegt." + ANSI_RESET);
            return false;
        }
    }

    /**
     *
     * @param beschreibung
     * @param maxTeilnehmerzahl
     * @param name
     * @param ort
     * @param startTermin
     * @param veranstalter
     * @return
     */
    public boolean veranstaltungErstellen(String beschreibung, Integer maxTeilnehmerzahl, String name, String ort, Date startTermin, Benutzer veranstalter) {
        HashMap<String, Artikel> benoetigteArtikel = new HashMap<>();
        HashSet<Benutzer> teilnehmendeBenutzer = new HashSet<>();
        HashMap<String, Artikel> vorhandeneArtikel = new HashMap<>();
        if (veranstaltungErstellen(benoetigteArtikel, beschreibung, maxTeilnehmerzahl, name, ort, startTermin, teilnehmendeBenutzer, veranstalter, vorhandeneArtikel)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public boolean veranstaltungLoeschen(String name) {
        if (alleVeranstaltungen.containsKey(name)) {

            // Veranstaltung loeschen            
            alleVeranstaltungen.remove(name);

            Datenbankverwaltung dv = Datenbankverwaltung.getInstanz();
            if (dv.veranstaltungLoeschen(name)) {
                System.out.println(ANSI_GREEN + "Veranstaltung " + name + " wurde geloescht." + ANSI_RESET);
                return true;
            } else {
                return false;
            }
        } else {
            System.out.println(ANSI_RED + "Veranstaltung konnte nicht gefunden werden!" + ANSI_RESET);
            return false;
        }
    }

}
