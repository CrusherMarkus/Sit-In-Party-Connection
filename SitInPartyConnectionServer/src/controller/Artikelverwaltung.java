/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Artikel;
import static entity.Hilfsklasse.ANSI_GREEN;
import static entity.Hilfsklasse.ANSI_RED;
import static entity.Hilfsklasse.ANSI_RESET;
import java.util.HashMap;

/**
 *
 * @author Markus, Simon
 */
public class Artikelverwaltung {

    private static Artikelverwaltung instanz = null;

    /**
     *
     * @return
     */
    public static Artikelverwaltung getInstanz() {
        if (instanz == null) {
            instanz = new Artikelverwaltung();
        }
        return instanz;
    }

    /**
     *
     * @param bezeichnung
     * @param name
     * @return
     */
    public boolean artikelWegVonBenoetigteArtikel(String bezeichnung, String name) {
        Veranstaltungsverwaltung vv = Veranstaltungsverwaltung.getInstanz();
        HashMap<String, Artikel> benoetigteArtikel = vv.getVeranstaltung(name).getBenoetigteArtikel();
        if (benoetigteArtikel.containsKey(bezeichnung)) {
            benoetigteArtikel.remove(bezeichnung);
            vv.getVeranstaltung(name).setBenoetigteArtikel(benoetigteArtikel);

            Datenbankverwaltung dv = Datenbankverwaltung.getInstanz();
            if (dv.artikelWegVonBenoetigteArtikel(bezeichnung, name)) {
                System.out.println(ANSI_GREEN + "Der benoetigte Artikel " + bezeichnung + " wurde aus der Veranstaltung " + name + " entfernt." + ANSI_RESET);
                return true;
            } else {
                System.out.println(ANSI_RED + "Der benoetigte Artikel " + bezeichnung + " wurde nicht aus der Veranstaltung " + name + " entfernt." + ANSI_RESET);
                return false;
            }
        } else {
            System.out.println(ANSI_RED + "Der benoetigte Artikel ist nicht in der Veranstaltung vorhanden.");
            return false;
        }
    }

    /**
     *
     * @param bezeichnung
     * @param name
     * @return
     */
    public boolean artikelWegVonVorhandeneArtikel(String bezeichnung, String name) {
        Veranstaltungsverwaltung vv = Veranstaltungsverwaltung.getInstanz();
        HashMap<String, Artikel> vorhandeneArtikel = vv.getVeranstaltung(name).getVorhandeneArtikel();
        if (vorhandeneArtikel.containsKey(bezeichnung)) {
            vorhandeneArtikel.remove(bezeichnung);
            vv.getVeranstaltung(name).setVorhandeneArtikel(vorhandeneArtikel);

            Datenbankverwaltung dv = Datenbankverwaltung.getInstanz();
            if (dv.artikelWegVonVorhandeneArtikel(bezeichnung, name)) {
                System.out.println(ANSI_GREEN + "Der vorhandene Artikel " + bezeichnung + " wurde aus der Veranstaltung " + name + " entfernt." + ANSI_RESET);
                return true;
            } else {
                System.out.println(ANSI_RED + "Der vorhandene Artikel " + bezeichnung + " wurde nicht aus der Veranstaltung " + name + " entfernt." + ANSI_RESET);
                return false;
            }
        } else {
            System.out.println(ANSI_RED + "Der vorhandene Artikel ist nicht in der Veranstaltung vorhanden.");
            return false;
        }
    }

    /**
     *
     * @param bezeichnung
     * @param name
     * @return
     */
    public boolean artikelZuBenoetigteArtikel(String bezeichnung, String name) {
        Artikel artikel = new Artikel(bezeichnung);
        Veranstaltungsverwaltung vv = Veranstaltungsverwaltung.getInstanz();
        HashMap<String, Artikel> benoetigteArtikel = vv.getVeranstaltung(name).getBenoetigteArtikel();
        if (benoetigteArtikel == null) {
            System.out.println("Keine benoetigte Artikel vorhanden.");
            benoetigteArtikel = new HashMap<String, Artikel>();
        }
        if (!benoetigteArtikel.containsKey(bezeichnung)) {
            if (!vv.getVeranstaltung(name).getVorhandeneArtikel().containsKey(bezeichnung)) {
                benoetigteArtikel.put(bezeichnung, artikel);
                vv.getVeranstaltung(name).setBenoetigteArtikel(benoetigteArtikel);

                Datenbankverwaltung dv = Datenbankverwaltung.getInstanz();
                if (dv.artikelZuBenoetigteArtikel(bezeichnung, name)) {
                    System.out.println(ANSI_GREEN + "Der benoetigte Artikel " + bezeichnung + " wurde der Veranstaltung " + name + " hinzugefuegt." + ANSI_RESET);
                    return true;
                } else {
                    System.out.println(ANSI_RED + "Der benoetigte Artikel " + bezeichnung + " wurde nicht zur Veranstaltung " + name + " hinzugefuegt." + ANSI_RESET);
                    return false;
                }
            } else {
            System.out.println(ANSI_RED + "Der  Artikel " + bezeichnung + " wird bereits mitgebracht." + ANSI_RESET);
            return false;
            }
        } else {
            System.out.println(ANSI_RED + "Der benoetigte Artikel " + bezeichnung + " ist bereits in der Veranstaltung " + name + " vorhanden." + ANSI_RESET);
            return false;
        }
    }

    /**
     *
     * @param bezeichnung
     * @param name
     * @return
     */
    public boolean artikelZuVorhandeneArtikel(String bezeichnung, String name) {
        Veranstaltungsverwaltung vv = Veranstaltungsverwaltung.getInstanz();
        HashMap<String, Artikel> benoetigteArtikel = vv.getVeranstaltung(name).getBenoetigteArtikel();
        HashMap<String, Artikel> vorhandeneArtikel = vv.getVeranstaltung(name).getVorhandeneArtikel();
        if (benoetigteArtikel == null) {
            System.out.println("Keine vorhandene Artikel");
            return false;
        }
        if (benoetigteArtikel.containsKey(bezeichnung)) {
            Artikel artikel = benoetigteArtikel.get(bezeichnung);
            benoetigteArtikel.remove(bezeichnung);
            vorhandeneArtikel.put(bezeichnung, artikel);

            Datenbankverwaltung dv = Datenbankverwaltung.getInstanz();
            if (dv.artikelZuVorhandeneArtikel(bezeichnung, name)) {
                System.out.println(ANSI_GREEN + "Der Artikel " + bezeichnung + " von benoetigte zu vorhandene Artikel erfolgreich." + ANSI_RESET);
                return true;
            } else {
                System.out.println(ANSI_RED + "Der Artikel " + bezeichnung + " von benoetigte zu vorhandene Artikel gescheitert." + ANSI_RESET);
                return false;
            }
        } else {
            System.out.println(ANSI_RED + "Artikel " + bezeichnung + " von benoetigte zu vorhandene Artikel gescheitert." + ANSI_RESET);
            return false;
        }
    }

}
