/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import static client.Hilfsklasse.ANSI_BLUE;
import static client.Hilfsklasse.ANSI_GREEN;
import static client.Hilfsklasse.ANSI_RED;
import static client.Hilfsklasse.ANSI_RESET;
import static client.Hilfsklasse.ANSI_YELLOW;
import entity.Benutzer;
import entity.Veranstaltung;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Markus
 */
public class Veranstaltungendialog {

    private static Veranstaltungendialog instanz = null;

    /**
     *
     * @return
     */
    public static Veranstaltungendialog getInstanz() {
        if (instanz == null) {
            instanz = new Veranstaltungendialog();
        }
        return instanz;
    }
    
    private final AdminClient client = Dialog.getInstanz().getClient();

    /**
     * Alle Veranstaltungsdaten anzeigen
     */
    public void alleVeranstaltungsDatenAnzeigen() {
        Map<String, Veranstaltung> alleVeranstaltungen;
        try {
            alleVeranstaltungen = this.client.getAlleVeranstaltungen();
            System.out.println(ANSI_BLUE + "Alle Veranstaltungsdaten anzeigen" + ANSI_RESET);
            if (!alleVeranstaltungen.values().isEmpty()) {
                Map<String, Veranstaltung> treeMap = new TreeMap<String, Veranstaltung>(alleVeranstaltungen);
                for (Veranstaltung veranstaltung : treeMap.values()) {
                    System.out.println(veranstaltung.toString());
                }
            } else {
                System.out.println(ANSI_RED + "Keine Veranstaltungen vorhanden." + ANSI_RESET);
            }
        } catch (RemoteException ex) { 
            Logger.getLogger(Veranstaltungendialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        veranstaltungsMenue();
    }

    /**
     * Benutzer weg von Veranstaltung
     */
    public void benutzerWegVonVeranstaltung() {
        System.out.println(ANSI_BLUE + "Benutzer aus Veranstaltung entfernen" + ANSI_RESET);
        System.out.println("Welchen Benuter wollen Sie entfernen?");
        Benutzerdialog bd = Benutzerdialog.getInstanz();
        bd.uebersichtBenutzer();
        Map<String, Benutzer> alleBenutzer;
        try {
            alleBenutzer = this.client.getAlleBenutzer();
            String benutzername = Eingabe.leseString("Benutzername angeben");
            if (alleBenutzer.containsKey(benutzername)) {
                System.out.println(ANSI_YELLOW + "Benutzer " + benutzername + " ist vorhanden." + ANSI_RESET);
                System.out.println("Aus welcher Veranstaltung wollen Sie " + benutzername + " entfernen?");
                uebersichtVeranstaltungen();
                Map<String, Veranstaltung> alleVeranstaltungen = this.client.getAlleVeranstaltungen();
                String name = Eingabe.leseString("Name angeben");
                if (alleVeranstaltungen.containsKey(name)) {
                    System.out.println(ANSI_YELLOW + "Veranstaltung " + name + " ist vorhanden." + ANSI_RESET);
                    if (this.client.benutzerWegVonVeranstaltung(benutzername, name)) {
                        System.out.println(ANSI_GREEN + benutzername + " wurde aus " + name + " erfolgreich entfernt!" + ANSI_RESET);
                    } else {
                        System.out.println(ANSI_RED + benutzername + " wurde aus " + name + " nicht entfernt!" + ANSI_RESET);
                    }
                } else {
                    System.out.println(ANSI_RED + "Veranstaltung " + name + " ist nicht vorhanden" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Benutzer " + benutzername + " ist nicht vorhanden" + ANSI_RESET);
            }
        } catch (RemoteException ex) { 
            Logger.getLogger(Veranstaltungendialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        veranstaltungsMenue();
    }

    /**
     * Benutzer zu Veranstaltung
     */
    public void benutzerZuVeranstaltung() {
        System.out.println(ANSI_BLUE + "Benutzer einer Veranstaltung zuordnen" + ANSI_RESET);
        System.out.println("Welchen Benuter wollen Sie hinzufuegen?");
        Benutzerdialog bd = Benutzerdialog.getInstanz();
        bd.uebersichtBenutzer();
        Map<String, Benutzer> alleBenutzer;
        try {
            alleBenutzer = this.client.getAlleBenutzer();
            String benutzername = Eingabe.leseString("Benutzername angeben");
            if (alleBenutzer.containsKey(benutzername)) {
                System.out.println(ANSI_YELLOW + "Benutzer " + benutzername + " ist vorhanden." + ANSI_RESET);
                System.out.println("Welcher Veranstaltung wollen Sie " + benutzername + " hinzufuegen?");
                uebersichtVeranstaltungen();
                Map<String, Veranstaltung> alleVeranstaltungen = this.client.getAlleVeranstaltungen();
                String name = Eingabe.leseString("Name angeben");
                if (alleVeranstaltungen.containsKey(name)) {
                    System.out.println(ANSI_GREEN + "Veranstaltung " + name + " ist vorhanden." + ANSI_RESET);
                    if (this.client.benutzerZuVeranstaltung(benutzername, name)) {
                        System.out.println(ANSI_GREEN + benutzername + " wurde der " + name + " erfolgreich zugeordnet!" + ANSI_RESET);
                    } else {
                        System.out.println(ANSI_RED + "Die maximale Teilnehmerzahl wurde schon erreicht." + ANSI_RESET);
                    }
                } else {
                    System.out.println(ANSI_RED + "Veranstaltung " + name + " ist nicht vorhanden" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Benutzer " + benutzername + " ist nicht vorhanden" + ANSI_RESET);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Veranstaltungendialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        veranstaltungsMenue();
    }

    // Verbesserung: Nur die Benutzer anzeigen die schon eine Veranstaltung erstellt haben

    /**
     * Erstellte Veranstaltungen von Benutzer
     */
    public void erstellteVeranstaltungenVonBenutzer() {
        System.out.println(ANSI_BLUE + "Erstellte Veranstaltungen eines Benutzers anzeigen" + ANSI_RESET);
        Map<String, Benutzer> alleBenutzer;
        try {
            alleBenutzer = this.client.getAlleBenutzer();
            System.out.println("Von welchem Benutzer?");
            Benutzerdialog bd = Benutzerdialog.getInstanz();
            bd.uebersichtBenutzer();
            String benutzername = Eingabe.leseString("Benutzername");
            if (alleBenutzer.containsKey(benutzername)) {
                System.out.println(ANSI_YELLOW + "Benutzer " + benutzername + " ist vorhanden." + ANSI_RESET);
                Map<String, Veranstaltung> erstellteVeranstaltungenVonBenutzer = this.client.getErstellteVeranstaltungenVonBenutzer(benutzername);
                System.out.println(ANSI_BLUE + "Erstellte Veranstaltungen von " + benutzername + ANSI_RESET);
                if (!erstellteVeranstaltungenVonBenutzer.isEmpty()) {
                    Map<String, Veranstaltung> treeMap = new TreeMap<String, Veranstaltung>(erstellteVeranstaltungenVonBenutzer);
                    for (Veranstaltung veranstaltung : treeMap.values()) {
                        System.out.println(ANSI_GREEN + veranstaltung.getName() + ANSI_RESET);
                    }
                } else {
                    System.out.println(ANSI_RED + "Keine Veranstaltungen vorhanden" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Benutzer nicht gefunden" + ANSI_RESET);
            }
        } catch (RemoteException ex) { 
            Logger.getLogger(Veranstaltungendialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        veranstaltungsMenue();
    }

    /**
     * Teilnehmer von Veranstaltung anzeigen
     */
    public void teilnehmerVonVeranstaltungAnzeigen() {
        System.out.println(ANSI_BLUE + "Teilnehmer von Veranstaltung anzeigen" + ANSI_RESET);
        HashSet<Benutzer> teilnehmerVonVeranstaltung;
        System.out.println("Von welcher Veranstaltung wollen Sie die Teilnehmer anzeigen?");
        uebersichtVeranstaltungen();
        String name = Eingabe.leseString("Name angeben");
        Veranstaltung veranstaltung;
        Map<String, Veranstaltung> alleVeranstaltungen;
        try {
            alleVeranstaltungen = this.client.getAlleVeranstaltungen();
            if (alleVeranstaltungen.containsKey(name)) {
                veranstaltung = this.client.getVeranstaltung(name);
                teilnehmerVonVeranstaltung = this.client.getTeilnehmerVonVeranstaltung(veranstaltung);
                if (!teilnehmerVonVeranstaltung.isEmpty()) {               
                    for (Benutzer benutzer : teilnehmerVonVeranstaltung) {
                        System.out.println(ANSI_GREEN + benutzer.getBenutzername() + ANSI_RESET);
                    }
                } else {
                    System.out.println(ANSI_RED + "Keine Teilnehmer vorhanden" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Veranstaltung nicht gefunden" + ANSI_RESET);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Veranstaltungendialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        veranstaltungsMenue();
    }

    /**
     * Uebersicht Veranstaltungen
     */
    public void uebersichtVeranstaltungen() {
        System.out.println(ANSI_BLUE + "Veranstaltungsuebersicht" + ANSI_RESET);
        Map<String, Veranstaltung> alleVeranstaltungen;
        try {
            alleVeranstaltungen = this.client.getAlleVeranstaltungen();
            if (!alleVeranstaltungen.isEmpty()) {
                Map<String, Veranstaltung> treeMap = new TreeMap<String, Veranstaltung>(alleVeranstaltungen);
                for (Veranstaltung veranstaltung : treeMap.values()) {
                    System.out.println("[-] " + ANSI_GREEN + veranstaltung.getName() + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Keine Veranstaltungen vorhanden." + ANSI_RESET);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Veranstaltungendialog.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    /**
     * Veranstaltung bearbeiten
     */
    public void veranstaltungBearbeiten() {
        System.out.println(ANSI_BLUE + "Veranstaltung bearbeiten" + ANSI_RESET);
        Map<String, Veranstaltung> alleVeranstaltungen;
        try {
            alleVeranstaltungen = this.client.getAlleVeranstaltungen();
            System.out.println("Welche Veranstaltung wollen Sie bearbeiten?");
            uebersichtVeranstaltungen();
            String name = Eingabe.leseString("Name angeben");
            if (alleVeranstaltungen.containsKey(name)) {
                System.out.println(ANSI_YELLOW + "Veranstaltung " + name + " ist vorhanden." + ANSI_RESET);
                if (this.client.veranstaltungLoeschen(name)) {
                    System.out.println("Neue Daten eingeben");
                    veranstaltungHinzufuegen();
                    System.out.println(ANSI_GREEN + name + " wurde erfolgreich bearbeiten!" + ANSI_RESET);
                } else {
                    System.out.println(ANSI_RED + name + " wurde nicht bearbeitet!" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_YELLOW + "Veranstaltung " + name + " ist nicht vorhanden" + ANSI_RESET);
                veranstaltungsMenue();
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Veranstaltungendialog.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    /**
     * Veranstaltungen hinzufuegen
     */
    public void veranstaltungHinzufuegen() {
        try {
            System.out.println(ANSI_BLUE + "Veranstaltung hinzufuegen" + ANSI_RESET);
            String name = Eingabe.leseString("Name");
            Date startTemin = Eingabe.leseDatum("Start Termin eingeben");
            String ort = Eingabe.leseString("Ort");
            String beschreibung = Eingabe.leseString("Beschreibung");
            Integer maxTeilnehmerzahl = Eingabe.leseInt("Max Teilnehmer", 1, 1000);
            Benutzer aktuellerBenutzer = this.client.getAktuellerBenutzer();
            if (this.client.veranstaltungHinzufuegen(beschreibung, maxTeilnehmerzahl, name, ort, startTemin, aktuellerBenutzer)) {
                System.out.println(ANSI_GREEN + "Veranstaltung " + name + " erfolgreich angelegt" + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "Veranstaltung " + name + " nicht angelegt" + ANSI_RESET);
            }
            veranstaltungsMenue();
        } catch (ParseException | RemoteException ex) {
            Logger.getLogger(Veranstaltungendialog.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    /**
     * Veranstaltungen loeschen
     */
    public void veranstaltungLoeschen() {
        System.out.println(ANSI_BLUE + "Veranstaltung loeschen" + ANSI_RESET);
        Map<String, Veranstaltung> alleVeranstaltungen;
        try {
            alleVeranstaltungen = this.client.getAlleVeranstaltungen();
            System.out.println("Welche Veranstaltung wollen sie loeschen?");
            uebersichtVeranstaltungen();
            String namen = Eingabe.leseString("Name angeben");
            if (alleVeranstaltungen.containsKey(namen)) {
                System.out.println(ANSI_YELLOW + "Veranstaltung " + namen + " ist vorhanden und " + ANSI_RESET);
                try {
                    if (this.client.veranstaltungLoeschen(namen)) {
                        System.out.println(ANSI_GREEN + "wurde erfolgreich gelöscht!" + ANSI_RESET);
                    } else {
                        System.out.println(ANSI_RED + "wurde nicht gelöscht!" + ANSI_RESET);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(Veranstaltungendialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println(ANSI_RED + "Veranstaltung " + namen + " ist nicht vorhanden" + ANSI_RESET);
            }
        } catch (RemoteException ex) { 
            Logger.getLogger(Veranstaltungendialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        veranstaltungsMenue();
    }

    /**
     * Menue Veranstaltungen
     */
    public void veranstaltungsMenue() {

        System.out.println(ANSI_BLUE + "Veranstaltungsverwaltung" + ANSI_RESET);
        switch (Dialog.getInstanz().menu("Veranstaltung hinzufügen",
                "Veranstaltung bearbeiten",
                "Veranstaltung löschen",
                "Alle Veranstaltungsnamen anzeigen",
                "Alle Veranstaltungsdaten anzeigen",
                "Erstellte Veranstaltungen eines Benutzers anzeigen",
                "Teilnehmer von Veranstaltung anzeigen",
                "Benutzer einer Veranstaltung zuordnen",
                "Benutzer aus Veranstaltung entfernen",
                "Zurueck")) {
            case 1:
                veranstaltungHinzufuegen();
                break;
            case 2:
                veranstaltungBearbeiten();
                break;
            case 3:
                veranstaltungLoeschen();
                break;
            case 4:
                uebersichtVeranstaltungen();
                veranstaltungsMenue();
                break;
            case 5:
                alleVeranstaltungsDatenAnzeigen();
                break;
            case 6:
                erstellteVeranstaltungenVonBenutzer();
                break;
            case 7:
                teilnehmerVonVeranstaltungAnzeigen();
                break;

            case 8:
                benutzerZuVeranstaltung();
                break;
            case 9:
                benutzerWegVonVeranstaltung();
                break;
            case 10:
                Dialog d = Dialog.getInstanz();
                d.verwaltungsMenue();
                break;
        }
    }


}
