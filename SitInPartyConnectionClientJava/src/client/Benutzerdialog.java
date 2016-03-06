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
import java.rmi.RemoteException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Markus
 */
public class Benutzerdialog {

    private static Benutzerdialog instanz = null;

    /**
     *
     * @return
     */
    public static Benutzerdialog getInstanz() {
        if (instanz == null) {
            instanz = new Benutzerdialog();
        }
        return instanz;
    }
    private final AdminClient client = Dialog.getInstanz().getClient();

    private void alleBenutzerDatenAnzeigen() {
        System.out.println(ANSI_BLUE + "Alle Benutzerdaten anzeigen" + ANSI_RESET);
        Map<String, Benutzer> alleBenutzer;
        try {
            alleBenutzer = this.client.getAlleBenutzer();
            Map<String, Benutzer> treeMap = new TreeMap<String, Benutzer>(alleBenutzer);
            for (Benutzer benutzer : treeMap.values()) {
                System.out.println("[-] " + benutzer.toString());
            }
        } catch (RemoteException ex) { 
            Logger.getLogger(Benutzerdialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        benutzerMenue();
    }

    private void benutzerBearbeiten() {
        System.out.println(ANSI_BLUE + "Benutzer bearbeiten" + ANSI_RESET);
        Map<String, Benutzer> alleBenutzer;
        try {
            alleBenutzer = this.client.getAlleBenutzer();
            System.out.println("Welchen Benutzer wollen sie bearbeiten?");
            uebersichtBenutzer();
            String benutzername = Eingabe.leseString("Benutzername angeben");
            if (alleBenutzer.containsKey(benutzername)) {
                System.out.println(ANSI_YELLOW + "Benutzer " + benutzername + " ist vorhanden." + ANSI_RESET);
                if (this.client.benutzerLoeschen(benutzername)) {
                    System.out.println("Neue Benutzerdaten eingeben:");
                    benutzerHinzufuegen();
                    System.out.println(ANSI_GREEN + benutzername + " wurde erfolgreich bearbeitet!" + ANSI_RESET);
                } else {
                    System.out.println(ANSI_RED + benutzername + " wurde nicht bearbeitet!" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Benutzer " + benutzername + " ist nicht vorhanden" + ANSI_RESET);
                benutzerMenue();
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Benutzerdialog.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    private void benutzerHinzufuegen() {
        System.out.println(ANSI_BLUE + "Benutzer hinzufuegen" + ANSI_RESET);
        String benutzername = Eingabe.leseString("Benutzername");
        String passwort = Eingabe.lesePasswort("Passwort");
        String passwortconfirm = Eingabe.lesePasswort("Passwort wiederholen");
        while (!passwort.equals(passwortconfirm)) {
            System.out.println("Die Passwörter stimmen nicht überein.");
            passwort = Eingabe.lesePasswort("Passwort");
            passwortconfirm = Eingabe.lesePasswort("Passwort wiederholen");
        }
        String vorname = Eingabe.leseString("Vorname");
        String nachname = Eingabe.leseString("Nachname");
        String email = Eingabe.leseString("E-Mail");
        Boolean istAdmin = Eingabe.leseBoolean("Admin");

        try {
            if (this.client.registrieren(benutzername, email, istAdmin, nachname, passwort, vorname)) {
                System.out.println(ANSI_GREEN + "Benutzer " + benutzername + " erfolgreich angelegt" + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "Benutzer " + benutzername + " nicht angelegt" + ANSI_RESET);
            }
        } catch (RemoteException ex) { 
            Logger.getLogger(Benutzerdialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        benutzerMenue();
    }

    private void benutzerLoeschen() {
        System.out.println(ANSI_BLUE + "Benutzer loeschen" + ANSI_RESET);
        Map<String, Benutzer> alleBenutzer;
        try {
            alleBenutzer = this.client.getAlleBenutzer();
            System.out.println("Welchen Benutzer wollen sie loeschen?");
            uebersichtBenutzer();
            String benutzername = Eingabe.leseString("Benutzername angeben");
            if (alleBenutzer.containsKey(benutzername)) {
                System.out.println(ANSI_YELLOW + "Benutzer " + benutzername + " ist vorhanden und ");
                if (this.client.benutzerLoeschen(benutzername)) {
                    System.out.println(ANSI_GREEN + "wurde erfolgreich gelöscht!" + ANSI_RESET);
                    if (this.client.getAktuellerBenutzer().getBenutzername().equals(benutzername)) {
                        System.out.println(ANSI_RED + "Sie haben den aktuellen Benutzer geloescht." + ANSI_RESET);
                        System.out.println(ANSI_GREEN + "Sie wurden abgemeldet." + ANSI_RESET);
                        Dialog.getInstanz().startMenue();
                    } else {
                        benutzerMenue();
                    }
                } else {
                    System.out.println(ANSI_RED + "wurde nicht gelöscht!" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Benutzer " + benutzername + " ist nicht vorhanden" + ANSI_RESET);
                benutzerMenue();
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Benutzerdialog.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    /**
     * Menue Benutzerverwaltung
     */
    protected void benutzerMenue() {
        System.out.println(ANSI_BLUE + "Benutzerverwaltung" + ANSI_RESET);
        switch (Dialog.getInstanz().menu("Benutzer hinzufügen",
                "Benutzer bearbeiten",
                "Benutzer löschen",
                "Alle Benutzernamen anzeigen",
                "Alle Benutzerdaten anzeigen",
                "Zurueck")) {
            case 1:
                benutzerHinzufuegen();
                break;
            case 2:
                benutzerBearbeiten();
                break;
            case 3:
                benutzerLoeschen();
                break;
            case 4:
                uebersichtBenutzer();
                benutzerMenue();
                break;
            case 5:
                alleBenutzerDatenAnzeigen();
                break;
            case 6:
                Dialog.getInstanz().verwaltungsMenue();
                break;
        }
    }

    /**
     * Uebersicht Benutzer
     */
    public void uebersichtBenutzer() {
        System.out.println(ANSI_BLUE + "Benutzeruebersicht" + ANSI_RESET);
        Map<String, Benutzer> alleBenutzer;
        try {
            alleBenutzer = this.client.getAlleBenutzer();
            Map<String, Benutzer> treeMap = new TreeMap<String, Benutzer>(alleBenutzer);
            for (Benutzer benutzer : treeMap.values()) {
                System.out.println("[-] " + ANSI_GREEN + benutzer.getBenutzername() + ANSI_RESET);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Benutzerdialog.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

}
