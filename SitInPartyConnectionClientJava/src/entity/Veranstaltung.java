/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import static entity.Hilfsklasse.ANSI_GREEN;
import static entity.Hilfsklasse.ANSI_RESET;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

/**
 *
 * @author Markus
 */
public class Veranstaltung implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;
    private HashMap<String, Artikel> benoetigteArtikel;
    private String beschreibung;
    private Integer maxTeilnehmerzahl;
    private String name;
    private String ort;
    private Date startTermin;
    private HashSet<Benutzer> teilnehmendeBenutzer;
    private Benutzer veranstalter;
    private HashMap<String, Artikel> vorhandeneArtikel;

    /**
     *
     * @param benoetigteArtikel
     * @param beschreibung
     * @param maxTeilnehmerzahl
     * @param name
     * @param ort
     * @param startTermin
     * @param teilnehmendeBenutzer
     * @param veranstalter
     * @param vorhandeneArtikel
     */
    public Veranstaltung(HashMap<String, Artikel> benoetigteArtikel, String beschreibung, Integer maxTeilnehmerzahl, String name, String ort, Date startTermin, HashSet<Benutzer> teilnehmendeBenutzer, Benutzer veranstalter, HashMap<String, Artikel> vorhandeneArtikel) {
        this.benoetigteArtikel = benoetigteArtikel;
        this.beschreibung = beschreibung;
        this.maxTeilnehmerzahl = maxTeilnehmerzahl;
        this.name = name;
        this.ort = ort;
        this.startTermin = startTermin;
        this.teilnehmendeBenutzer = teilnehmendeBenutzer;
        this.veranstalter = veranstalter;
        this.vorhandeneArtikel = vorhandeneArtikel;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Veranstaltung other = (Veranstaltung) obj;
        if (!Objects.equals(this.beschreibung, other.beschreibung)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.ort, other.ort)) {
            return false;
        }
        if (!Objects.equals(this.benoetigteArtikel, other.benoetigteArtikel)) {
            return false;
        }
        if (!Objects.equals(this.maxTeilnehmerzahl, other.maxTeilnehmerzahl)) {
            return false;
        }
        if (!Objects.equals(this.startTermin, other.startTermin)) {
            return false;
        }
        if (!Objects.equals(this.teilnehmendeBenutzer, other.teilnehmendeBenutzer)) {
            return false;
        }
        if (!Objects.equals(this.veranstalter, other.veranstalter)) {
            return false;
        }
        if (!Objects.equals(this.vorhandeneArtikel, other.vorhandeneArtikel)) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    public int getAnzahlTeilnehmer() {
        return teilnehmendeBenutzer.size();
    }

    /**
     *
     * @return
     */
    public HashMap<String, Artikel> getBenoetigteArtikel() {
        if (benoetigteArtikel == null) {
            return benoetigteArtikel = new HashMap<>();
        }
        return benoetigteArtikel;

    }

    /**
     *
     * @return
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     *
     * @return
     */
    public Integer getMaxTeilnehmerzahl() {
        return maxTeilnehmerzahl;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public String getOrt() {
        return ort;
    }

    /**
     *
     * @return
     */
    public Date getStartTermin() {
        return startTermin;
    }

    /**
     *
     * @return
     */
    public HashSet<Benutzer> getTeilnehmendeBenutzer() {
        if (teilnehmendeBenutzer == null) {
            return teilnehmendeBenutzer = new HashSet<>();
        }
        return teilnehmendeBenutzer;
    }

    /**
     *
     * @return
     */
    public Benutzer getVeranstalter() {
        return veranstalter;
    }

    /**
     *
     * @return
     */
    public HashMap<String, Artikel> getVorhandeneArtikel() {
        if (vorhandeneArtikel == null) {
            vorhandeneArtikel = new HashMap<>();
        }
        return vorhandeneArtikel;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.benoetigteArtikel);
        hash = 17 * hash + Objects.hashCode(this.beschreibung);
        hash = 17 * hash + Objects.hashCode(this.maxTeilnehmerzahl);
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + Objects.hashCode(this.ort);
        hash = 17 * hash + Objects.hashCode(this.startTermin);
        hash = 17 * hash + Objects.hashCode(this.teilnehmendeBenutzer);
        hash = 17 * hash + Objects.hashCode(this.veranstalter);
        hash = 17 * hash + Objects.hashCode(this.vorhandeneArtikel);
        return hash;
    }

    /**
     *
     * @param benoetigteArtikel
     */
    public void setBenoetigteArtikel(HashMap<String, Artikel> benoetigteArtikel) {
        this.benoetigteArtikel = benoetigteArtikel;
    }

    /**
     *
     * @param beschreibung
     */
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    /**
     *
     * @param maxTeilnehmerzahl
     */
    public void setMaxTeilnehmerzahl(Integer maxTeilnehmerzahl) {
        this.maxTeilnehmerzahl = maxTeilnehmerzahl;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param ort
     */
    public void setOrt(String ort) {
        this.ort = ort;
    }

    /**
     *
     * @param startTermin
     */
    public void setStartTermin(Date startTermin) {
        this.startTermin = startTermin;
    }

    /**
     *
     * @param teilnehmendeBenutzer
     */
    public void setTeilnehmendeBenutzer(HashSet<Benutzer> teilnehmendeBenutzer) {
        this.teilnehmendeBenutzer = teilnehmendeBenutzer;
    }

    /**
     *
     * @param veranstalter
     */
    public void setVeranstalter(Benutzer veranstalter) {
        this.veranstalter = veranstalter;
    }

    /**
     *
     * @param vorhandeneArtikel
     */
    public void setVorhandeneArtikel(HashMap<String, Artikel> vorhandeneArtikel) {
        this.vorhandeneArtikel = vorhandeneArtikel;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String ergebnis = df.format(startTermin);

        sb.append("Name: " + ANSI_GREEN + name + ANSI_RESET
                + "; Start Termin: " + ANSI_GREEN + ergebnis + ANSI_RESET
                + "; Ort: " + ANSI_GREEN + ort + ANSI_RESET
                + "; Beschreibung: " + ANSI_GREEN + beschreibung + ANSI_RESET
                + "; Veranstalter: " + ANSI_GREEN + veranstalter.getBenutzername() + ANSI_RESET
                + "; Max Teilnehmerzahl: " + ANSI_GREEN + maxTeilnehmerzahl + ANSI_RESET
                + "; Teilnehmer:");
        if (this.teilnehmendeBenutzer != null) {
            for (Benutzer benutzer : this.teilnehmendeBenutzer) {
                sb.append(" " + ANSI_GREEN + benutzer.getBenutzername() + ANSI_RESET);
            }
        }
        sb.append("; benoetigte Artikel:");
        if (this.benoetigteArtikel != null) {
            for (Artikel artikel : benoetigteArtikel.values()) {
                sb.append(" " + ANSI_GREEN + artikel.getBezeichnung() + ANSI_RESET);
            }
        }
        sb.append("; vorhandene Artikel:");
        if (this.vorhandeneArtikel != null) {
            for (Artikel artikel : vorhandeneArtikel.values()) {
                sb.append(" " + ANSI_GREEN + artikel.getBezeichnung() + ANSI_RESET);
            }
        }
        return sb.toString();
    }
}
