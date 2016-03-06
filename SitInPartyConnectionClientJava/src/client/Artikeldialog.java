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
import entity.Artikel;
import entity.Benutzer;
import entity.Veranstaltung;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Markus
 */
public class Artikeldialog {

    private static Artikeldialog instanz = null;

    /**
     *
     * @return
     */
    public static Artikeldialog getInstanz() {
        if (instanz == null) {
            instanz = new Artikeldialog();
        }
        return instanz;
    }
    private final AdminClient client = Dialog.getInstanz().getClient();

    /**
     * Menue Artikelverwaltung
     */
    public void artikelMenue() {
        System.out.println(ANSI_BLUE + "Artikelverwaltung" + ANSI_RESET);
        switch (Dialog.getInstanz().menu(
                "Benoetigte Artikel einer Veranstaltung anzeigen",
                "Vorhandene Artikel einer Veranstaltung anzeigen",
                "Artikel zu benoetigte Artikel",
                "Artikel zu vorhandene Artikel",
                "Artikel weg von benoetigte Artikel",
                "Artikel weg von vorhandene Artikel",
                "Zurueck")) {
            case 1:
                benoetigteArtikelAnzeigen();
                break;
            case 2:
                vorhandeneArtikelAnzeigen();
                break;
            case 3:
                artikelZuBenoetigteArtikel();
                break;
            case 4:
                artikelZuVorhandeneArtikel();
                break;
            case 5:
                artikelWegVonBenoetigteArtikel();
                break;
            case 6:
                artikelWegVonVorhandeneArtikel();
                break;
            case 7:
                Dialog d = Dialog.getInstanz();
                d.verwaltungsMenue();
                break;
        }
    }

    /**
     * Artikel weg von benoetigte Artikel
     */
    public void artikelWegVonBenoetigteArtikel() {

        System.out.println(ANSI_BLUE + "Benoetigten Artikel aus Veranstaltung entfernen" + ANSI_RESET);
        System.out.println("Von welcher Veranstaltung wollen Sie einen benoetigten Artikel entfernen?");
        Veranstaltungendialog vd = Veranstaltungendialog.getInstanz();
        vd.uebersichtVeranstaltungen();
        Map<String, Veranstaltung> alleVeranstaltungen;
        try {
            alleVeranstaltungen = this.client.getAlleVeranstaltungen();
            String name = Eingabe.leseString("Name angeben");
            if (alleVeranstaltungen.containsKey(name)) {
                System.out.println(ANSI_GREEN + "Veranstaltung " + name + " ist vorhanden." + ANSI_RESET);
                System.out.println(ANSI_BLUE + "Welchen Artikel wollen Sie entfernen" + ANSI_RESET);
                uebersichtBenoetigteArtikel(name);
                Map<String, Artikel> benoetigteArtikel = this.client.getBenoetigteArtikel(name);
                if (!benoetigteArtikel.isEmpty()) {
                        String bezeichnung = Eingabe.leseString("Artikel Bezeichnung");
                        if (benoetigteArtikel.containsKey(bezeichnung)) {
                            this.client.artikelWegVonBenoetigteArtikel(bezeichnung, name);
                            System.out.println("Artikel erfolgreich entfernt.");
                        } else {
                            System.out.println("Artikel nicht vorhanden.");
                        }
                } else {
                    System.out.println(ANSI_RED + "Keine Artikel vorhanden" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Veranstaltung " + name + " ist nicht vorhanden," + ANSI_RESET);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Artikeldialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        artikelMenue();
    }

    /**
     * Artikel weg von vorhandene Artikel
     */
    public void artikelWegVonVorhandeneArtikel() {

        System.out.println(ANSI_BLUE + "Vorhandenen Artikel aus Veranstaltung entfernen" + ANSI_RESET);
        System.out.println("Von welcher Veranstaltung wollen Sie einen vorhandenen Artikel entfernen?");
        Veranstaltungendialog vd = Veranstaltungendialog.getInstanz();
        vd.uebersichtVeranstaltungen();
        Map<String, Veranstaltung> alleVeranstaltungen;
        try {
            alleVeranstaltungen = this.client.getAlleVeranstaltungen();
            String name = Eingabe.leseString("Name angeben");
            if (alleVeranstaltungen.containsKey(name)) {
                System.out.println(ANSI_GREEN + "Veranstaltung " + name + " ist vorhanden." + ANSI_RESET);
                System.out.println(ANSI_BLUE + "Welchen Artikel wollen Sie entfernen" + ANSI_RESET);
                Map<String, Artikel> vorhandeneArtikel = this.client.getVorhandeneArtikel(name);
                if (!vorhandeneArtikel.isEmpty()) {
                    for (Artikel artikel : vorhandeneArtikel.values()) {
                        System.out.println(ANSI_GREEN + artikel.getBezeichnung() + ANSI_RESET);
                    }
                    String bezeichnung = Eingabe.leseString("Artikel Bezeichnung");
                    if (vorhandeneArtikel.containsKey(bezeichnung)) {
                        this.client.artikelWegVonVorhandeneArtikel(bezeichnung, name);
                        System.out.println("Artikel erfolgreich entfernt.");
                    } else {
                        System.out.println("Artikel nicht vorhanden.");
                    }
                } else {
                    System.out.println(ANSI_RED + "Keine Artikel vorhanden" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Veranstaltung " + name + " ist nicht vorhanden," + ANSI_RESET);
            }
        } catch (RemoteException ex) { 
            Logger.getLogger(Artikeldialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        artikelMenue();
    }

    /**
     * Artikel zu benoetigte Artikel
     */
    public void artikelZuBenoetigteArtikel() {

        System.out.println(ANSI_BLUE + "Artikel zu benoetigte Artikel" + ANSI_RESET);
        System.out.println("Welcher Veranstaltung wollen Sie einen benoetigten Artikel hinzufuegen?");
        Veranstaltungendialog vd = Veranstaltungendialog.getInstanz();
        vd.uebersichtVeranstaltungen();
        try {
            Map<String, Veranstaltung> alleVeranstaltungen = this.client.getAlleVeranstaltungen();
            String name = Eingabe.leseString("Name angeben");
            if (alleVeranstaltungen.containsKey(name)) {
                System.out.println(ANSI_GREEN + "Veranstaltung " + name + " ist vorhanden." + ANSI_RESET);
                uebersichtBenoetigteArtikel(name);
                String bezeichnung = Eingabe.leseString("Artikel Bezeichnung");
                if (this.client.artikelZuBenoetigteArtikel(bezeichnung, name)) {
                    System.out.println(ANSI_GREEN + "Der benoetigte Artikel " + bezeichnung + " wurde der " + name + " erfolgreich zugeordnet." + ANSI_RESET);
                } else {
                    System.out.println(ANSI_RED + "Der benoetigte Artikel " + bezeichnung + " wurde der " + name + " nicht zugeordnet." + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Veranstaltung " + name + " ist nicht vorhanden," + ANSI_RESET);
            }
        } catch (RemoteException ex) { 
            Logger.getLogger(Artikeldialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        artikelMenue();
    }

    /**
     * Artikel zu vorhandene Artikel
     */
    public void artikelZuVorhandeneArtikel() {

        System.out.println(ANSI_BLUE + "Artikel zu vorhandene Artikel" + ANSI_RESET);
        System.out.println("Welcher Veranstaltung wollen Sie einen vorhandenen Artikel hinzufuegen?");
        Veranstaltungendialog vd = Veranstaltungendialog.getInstanz();
        vd.uebersichtVeranstaltungen();
        Map<String, Veranstaltung> alleVeranstaltungen;
        try {
            alleVeranstaltungen = this.client.getAlleVeranstaltungen();
            String name = Eingabe.leseString("Name angeben");
            if (alleVeranstaltungen.containsKey(name)) {
                System.out.println(ANSI_GREEN + "Veranstaltung " + name + " ist vorhanden." + ANSI_RESET);
                uebersichtBenoetigteArtikel(name);
                String bezeichnung = Eingabe.leseString("Artikel Bezeichnung");
                
                if (this.client.artikelZuVorhandeneArtikel(bezeichnung, name)) {
                    System.out.println(ANSI_GREEN + "Der vorhandene Artikel " + bezeichnung + " wurde der " + name + " erfolgreich zugeordnet." + ANSI_RESET);
                } else {
                    System.out.println(ANSI_RED + "Der vorhandene Artikel " + bezeichnung + " wurde der " + name + " nicht zugeordnet." + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Veranstaltung " + name + " ist nicht vorhanden," + ANSI_RESET);
            }
        } catch (RemoteException ex) { 
            Logger.getLogger(Artikeldialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        artikelMenue();
    }

    private void benoetigteArtikelAnzeigen() {
        Map<String, Veranstaltung> alleVeranstaltungen;
        try {
            alleVeranstaltungen = this.client.getAlleVeranstaltungen();
            Veranstaltungendialog vd = Veranstaltungendialog.getInstanz();
            vd.uebersichtVeranstaltungen();
            String name = Eingabe.leseString("Name angeben");
            if (alleVeranstaltungen.containsKey(name)) {
                System.out.println(ANSI_GREEN + "Veranstaltung " + name + " ist vorhanden." + ANSI_RESET);
                uebersichtBenoetigteArtikel(name);
            } else {
                System.out.println(ANSI_RED + "Veranstaltung " + name + " ist nicht vorhanden," + ANSI_RESET);
            }
            artikelMenue();
        } catch (RemoteException ex) {
            Logger.getLogger(Artikeldialog.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    private void uebersichtBenoetigteArtikel(String name) {
        System.out.println(ANSI_BLUE + "Benoetigte Artikel von " + name + ANSI_RESET);
        try {
            Veranstaltung veranstaltung = this.client.getVeranstaltung(name);
            HashMap<String, Artikel> benoetigteArtikel = veranstaltung.getBenoetigteArtikel();
            if (!benoetigteArtikel.isEmpty()) {
                for (Artikel artikel : benoetigteArtikel.values()) {
                    System.out.println(ANSI_GREEN + artikel.getBezeichnung() + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Keine benoetigte Artikel vorhanden" + ANSI_RESET);
            }

        } catch (RemoteException ex) {
            Logger.getLogger(Artikeldialog.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    private void uebersichtVorhandeneArtikel(String name) {
        System.out.println(ANSI_BLUE + "Vorhandene Artikel von " + name + ANSI_RESET);
        try {
            Veranstaltung veranstaltung = this.client.getVeranstaltung(name);
            HashMap<String, Artikel> vorhandeneArtikel = veranstaltung.getVorhandeneArtikel();
            if (!vorhandeneArtikel.isEmpty()) {
                for (Artikel artikel : vorhandeneArtikel.values()) {
                    System.out.println(ANSI_GREEN + artikel.getBezeichnung() + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Keine vorhandenen Artikel vorhanden" + ANSI_RESET);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Artikeldialog.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    private void vorhandeneArtikelAnzeigen() {
        Map<String, Veranstaltung> alleVeranstaltungen;
        try {
            alleVeranstaltungen = this.client.getAlleVeranstaltungen();
            Veranstaltungendialog vd = Veranstaltungendialog.getInstanz();
            vd.uebersichtVeranstaltungen();
            String name = Eingabe.leseString("Name angeben");
            if (alleVeranstaltungen.containsKey(name)) {
                System.out.println(ANSI_GREEN + "Veranstaltung " + name + " ist vorhanden." + ANSI_RESET);
                uebersichtVorhandeneArtikel(name);
            } else {
                System.out.println(ANSI_RED + "Veranstaltung " + name + " ist nicht vorhanden," + ANSI_RESET);
            }
            artikelMenue();
        } catch (RemoteException ex) {
            Logger.getLogger(Artikeldialog.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

}
