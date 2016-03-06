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
import entity.Benutzer;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Markus
 */
public class Dialog {


    private static Dialog instanz = null;

    /**
     *
     * @return
     */
    public static Dialog getInstanz() {
        if (instanz == null) {
            try {
                instanz = new Dialog(new AdminClient("rmi://localhost:1099/SitInPartyConnectionServer"));
            } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                Logger.getLogger(Dialog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instanz;
    }
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Dialog dialog = getInstanz();
        dialog.startMenue();
    }
    private final AdminClient client;

    /**
     *
     * @param client
     */
    public Dialog(AdminClient client) {
        this.client = client;
    }

    /**
     * anmelden
     */
    public void anmelden() {
        System.out.println(ANSI_BLUE + "Anmeldung" + ANSI_RESET);
        String benutzername = Eingabe.leseString("Benutzername");
        String passwort = Eingabe.lesePasswort("Passwort");
        Benutzer benutzer;
        try {
            benutzer = this.client.anmelden(benutzername, passwort);
            if (benutzer != null) {
                this.client.setAktuellerBenutzer(benutzer);
                willkommen();
                verwaltungsMenue();
            } else {
                System.out.println(ANSI_RED + "Anmeldung fehlgeschlagen." + ANSI_RESET);
                startMenue();
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Dialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * beenden
     */
    public void beenden() {
        System.out.println(ANSI_GREEN + "Der Client wird beendet..." + ANSI_RESET);
        System.exit(0);
    }

    /**
     * Menue Datenbank
     */
    public void datenbankMenue() {
        System.out.println(ANSI_BLUE + "Datenbankverwaltung" + ANSI_RESET);
        switch (menu("Test Daten anlegen", "Zurueck")) {
            case 1:
                testDatenAnlegen();
                break;
            case 2:
                verwaltungsMenue();
                break;
        }
    }

    /**
     * Testdaten in anlegen
     */
    public void testDatenAnlegen() {
        try {
            this.client.testDatenAnlegen();
            System.out.println(ANSI_GREEN + "Test Daten wurden erfolgreich angelegt oder sind bereits vorhanden." + ANSI_RESET);
            datenbankMenue();
        } catch (RemoteException ex) {
            Logger.getLogger(Dialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return
     */
    public AdminClient getClient() {
        return client;
    }

    /**
     *
     * @param optionen
     * @return
     */
    public int menu(String... optionen) {
        for (int i = 0; i < optionen.length; i++) {
            System.out.println("[" + (i + 1) + "] " + optionen[i]);
        }
        return Eingabe.leseInt("Auswahl", 1, optionen.length);
    }

    /**
     * registrieren
     */
    public void registrieren() {
        System.out.println(ANSI_BLUE + "Registrierung" + ANSI_RESET);
        String benutzername = Eingabe.leseString("Benutzername");
        String passwort = Eingabe.lesePasswort("Passwort");
        String passwortconfirm = Eingabe.lesePasswort("Passwort wiederholen");
        while (!passwort.equals(passwortconfirm)) {
            System.out.println(ANSI_RED + "Die Passwörter stimmen nicht überein." + ANSI_RESET);
            passwort = Eingabe.lesePasswort("Passwort");
            passwortconfirm = Eingabe.lesePasswort("Passwort wiederholen");
        }
        String vorname = Eingabe.leseString("Vorname");
        String nachnamen = Eingabe.leseString("Nachname");
        String email = Eingabe.leseString("E-Mail");
        Boolean istAdmin = Eingabe.leseBoolean("Admin");
        try {
            if (this.client.registrieren(benutzername, email, istAdmin, nachnamen, passwort, vorname)) {
                System.out.println(ANSI_GREEN + "Benutzer " + benutzername + " erfolgreich angelegt" + ANSI_RESET);
                Benutzer benutzer;
                try {
                    benutzer = this.client.anmelden(benutzername, passwort);
                    if (benutzer != null) {
                        this.client.setAktuellerBenutzer(benutzer);
                        willkommen();
                        verwaltungsMenue();
                    } else {
                        System.out.println(ANSI_RED + "Anmeldung fehlgeschlagen." + ANSI_RESET);
                        startMenue();
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(Dialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println(ANSI_RED + "Der Benutzername " + benutzername + " ist bereits vergeben" + ANSI_RESET);
                startMenue();
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Dialog.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    /**
     * Menue Start
     */
    public void startMenue() {
        System.out.println(ANSI_BLUE + "Sit-In Party Connection" + ANSI_RESET);
        switch (menu("Anmelden", "Registrieren", "Beenden")) {
            case 1:
                anmelden();
                break;
            case 2:
                registrieren();
                break;
            case 3:
                beenden();
                break;
        }
    }

    /**
     * Menue Verwaltung
     */
    public void verwaltungsMenue() {
        System.out.println(ANSI_BLUE + "Verwaltung" + ANSI_RESET);

        Benutzerdialog bd = Benutzerdialog.getInstanz();
        Veranstaltungendialog vd = Veranstaltungendialog.getInstanz();
        Artikeldialog ad = Artikeldialog.getInstanz();

        System.out.println("Was möchtest du jetzt tun?");
        switch (this.menu("Benutzerverwaltung", "Veranstaltungsverwaltung", "Artikelverwaltung", "Datenbankverwaltung", "Abmelden")) {
            case 1:
                bd.benutzerMenue();
                break;
            case 2:
                vd.veranstaltungsMenue();
                break;
            case 3:
                ad.artikelMenue();
                break;
            case 4:
                datenbankMenue();
                break;
            case 5:
                startMenue();
                break;
        }
    }
    
    /**
     * Willkommen
     */
    public void willkommen() {
        System.out.println(ANSI_BLUE + "Sit-In Party Connection" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Willkommen " + this.client.getAktuellerBenutzer().getBenutzername() + "!" + ANSI_RESET);
        if (this.client.getAktuellerBenutzer().getIstAdmin()) {
            System.out.println(ANSI_GREEN + "Du hast dich erfolgreich als Administrator angemeldet." + ANSI_RESET);
        } else {
            System.out.println(ANSI_GREEN + "Du hast dich erfolgreich als Teilnehmer angemeldet." + ANSI_RESET);
        }
    }
}
