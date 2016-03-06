/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Artikel;
import entity.Benutzer;
import static entity.Hilfsklasse.ANSI_BLUE;
import static entity.Hilfsklasse.ANSI_GREEN;
import static entity.Hilfsklasse.ANSI_PURPLE;
import static entity.Hilfsklasse.ANSI_RESET;
import entity.Veranstaltung;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Markus, Simon
 */
public class Datenbankverwaltung {

    private static Datenbankverwaltung instanz = null;

    /**
     *
     * @return
     */
    public static Datenbankverwaltung getInstanz() {
        if (instanz == null) {
            instanz = new Datenbankverwaltung();
        }
        return instanz;
    }
    private Connection con;

    boolean artikelWegVonBenoetigteArtikel(String bezeichnung, String name) {
        try {
            System.out.println(ANSI_PURPLE + "Der benoetigte Artikel " + bezeichnung + " wird aus der Veranstaltung " + name + " in der Datenbank gelöscht..." + ANSI_RESET);
            Statement stmt = getCon().createStatement();
            String sql = "DELETE FROM BenoetigteArtikel "
                    + "WHERE name = '" + name + "' AND bezeichnung = '" + bezeichnung + "'";
            stmt.executeUpdate(sql);
            System.out.println(ANSI_PURPLE + "Der benoetigte Artikel " + bezeichnung + " wurde erfolgreich aus der Veranstaltung " + name + " in der Datenbank gelöscht." + ANSI_RESET);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Datenbankverwaltung.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            System.out.println(ANSI_PURPLE + "Der vorhandene Artikel " + bezeichnung + " wird aus der Veranstaltung " + name + " in der Datenbank gelöscht..." + ANSI_RESET);
            Statement stmt = getCon().createStatement();
            String sql = "DELETE FROM VorhandeneArtikel "
                    + "WHERE name = '" + name + "' AND bezeichnung = '" + bezeichnung + "'";
            stmt.executeUpdate(sql);
            System.out.println(ANSI_PURPLE + "Der vorhandene Artikel " + bezeichnung + " wurde erfolgreich aus der Veranstaltung " + name + " in der Datenbank gelöscht." + ANSI_RESET);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Datenbankverwaltung.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            System.out.println(ANSI_PURPLE + "Der benoetigte Artikel " + bezeichnung + " wird zu der Veranstaltung " + name + " in die Datenbank geschrieben..." + ANSI_RESET);
            Statement stmt = getCon().createStatement();
            String sql = "INSERT INTO BenoetigteArtikel "
                    + "VALUES ('" + name + "', '" + bezeichnung + "')";
            stmt.executeUpdate(sql);
            System.out.println(ANSI_PURPLE + "Der benoetigte Artikel " + bezeichnung + " wurde erfolgreich in die Veranstaltung " + name + " in die Datenbank geschrieben." + ANSI_RESET);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Datenbankverwaltung.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            artikelWegVonBenoetigteArtikel(bezeichnung, name);
            System.out.println(ANSI_PURPLE + "Der vorhandene Artikel " + bezeichnung + " wird zu der Veranstaltung " + name + " in die Datenbank geschrieben..." + ANSI_RESET);
            Statement stmt = getCon().createStatement();
            String sql = "INSERT INTO VorhandeneArtikel "
                    + "VALUES ('" + name + "', '" + bezeichnung + "')";
            stmt.executeUpdate(sql);
            System.out.println(ANSI_PURPLE + "Der vorhandene Artikel " + bezeichnung + " wurde erfolgreich in die Veranstaltung " + name + " in die Datenbank geschrieben." + ANSI_RESET);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Datenbankverwaltung.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     *
     * @param benutzername
     * @return
     */
    public boolean benutzerLoeschen(String benutzername) {
        try {
            // Als Teilnehmer austragen
            System.out.println(ANSI_PURPLE + benutzername + " wird als Teilnehmer von Veranstaltungen aus der Datenbank gelöscht..." + ANSI_RESET);
            Statement stmtTeilnehmer = getCon().createStatement();
            String sqlTeilnehmer = "DELETE FROM TeilnehmendeBenutzer "
                    + "WHERE benutzername = '" + benutzername + "'";
            stmtTeilnehmer.executeUpdate(sqlTeilnehmer);
            System.out.println(ANSI_PURPLE + benutzername + " wurde erfolgreich als Teilnehmer von Veranstaltungen aus der Datenbank gelöscht." + ANSI_RESET);
            
            // Veranstaltungen des Benutzers loeschen
            System.out.println(ANSI_PURPLE + "Veranstaltungen von " + benutzername + " werden aus der Datenbank gelöscht..." + ANSI_RESET);
            Statement stmtVeranstaltung = getCon().createStatement();
            String sqlVeranstaltung = "DELETE FROM Veranstaltungen "
                    + "WHERE veranstalter = '" + benutzername + "'";
            stmtVeranstaltung.executeUpdate(sqlVeranstaltung);
            System.out.println(ANSI_PURPLE + "Veranstaltungen von " + benutzername + " wurde erfolgreich aus der Datenbank gelöscht." + ANSI_RESET);

            // Benutzer loeschen
            System.out.println(ANSI_PURPLE + "Benutzer " + benutzername + " wird aus der Datenbank gelöscht..." + ANSI_RESET);
            Statement stmtBenutzer = getCon().createStatement();
            String sqlBenutzer = "DELETE FROM Benutzer "
                    + "WHERE benutzername = '" + benutzername + "'";
            stmtBenutzer.executeUpdate(sqlBenutzer);
            System.out.println(ANSI_PURPLE + "Benutzer " + benutzername + " wurde erfolgreich aus der Datenbank gelöscht." + ANSI_RESET);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Datenbankverwaltung.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
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
    public boolean benutzerSpeichern(String benutzername, String email, Boolean istAdmin, String nachname, String passwort, String vorname) {
        try {
            System.out.println(ANSI_PURPLE + "Der Benutzer " + benutzername + " wird in die Datenbank geschrieben..." + ANSI_RESET);
            Statement stmt = getCon().createStatement();
            String sql = "INSERT INTO Benutzer "
                    + "VALUES ('" + benutzername + "', '" + email + "', " + istAdmin + ", '" + nachname + "', '" + passwort + "', '" + vorname + "')";
            stmt.executeUpdate(sql);
            System.out.println(ANSI_PURPLE + "Der Benutzer " + benutzername + " wurde erfolgreich in die Datenbank geschrieben." + ANSI_RESET);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Datenbankverwaltung.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     *
     * @param benutzername
     * @param name
     * @return
     */
    public boolean benutzerWegVonVeranstaltung(String benutzername, String name) {
        try {
            System.out.println(ANSI_PURPLE + "Der teilnehmender Benutzer " + benutzername + " wird aus der Veranstaltung " + name + " in der Datenbank gelöscht..." + ANSI_RESET);
            Statement stmt = getCon().createStatement();
            String sql = "DELETE FROM TeilnehmendeBenutzer "
                    + "WHERE name = '" + name + "' AND benutzername = '" + benutzername + "'";
            stmt.executeUpdate(sql);
            System.out.println(ANSI_PURPLE + "Der teilnehmender Benutzer " + benutzername + " wurde erfolgreich aus der Veranstaltung " + name + " in der Datenbank gelöscht." + ANSI_RESET);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Datenbankverwaltung.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            System.out.println(ANSI_PURPLE + "Der Benutzer " + benutzername + " wird in die Veranstaltung " + name + " in der Datenbank geschrieben..." + ANSI_RESET);
            Statement stmt = getCon().createStatement();
            String sql = "INSERT INTO TeilnehmendeBenutzer "
                    + "VALUES ('" + name + "', '" + benutzername + "')";
            stmt.executeUpdate(sql);
            System.out.println(ANSI_PURPLE + "Der Benutzer " + benutzername + " wurde erfolgreich in die Veranstaltung " + name + " in der Datenbank geschrieben." + ANSI_RESET);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Datenbankverwaltung.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     *
     * @return
     */
    public Connection getCon() {
        return con;
    }

    /**
     *
     * @param con
     */
    public void setCon(Connection con) {
        this.con = con;
    }

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
     * @return
     */
    public boolean veranstaltungErstellen(HashMap<String, Artikel> benoetigteArtikel, String beschreibung, Integer maxTeilnehmerzahl, String name, String ort, Date startTermin, HashSet<Benutzer> teilnehmendeBenutzer, Benutzer veranstalter, HashMap<String, Artikel> vorhandeneArtikel) {
        PreparedStatement pstmtVeranstaltungen;
        PreparedStatement pstmtVeranstaltungsBenutzer;
        try {
            System.out.println(ANSI_PURPLE + "Veranstaltung " + name + " wird in die Datenbank geschrieben..." + ANSI_RESET);

            // Date startTermin -> Datum, Zeit
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            String ergebnis = df.format(startTermin);
            String datum = ergebnis.substring(0, 10);
            String zeit = ergebnis.substring(11);

            pstmtVeranstaltungen = getCon().prepareStatement("INSERT INTO Veranstaltungen (name,beschreibung,ort,datum,zeit,veranstalter,maxteilnehmerzahl) VALUES (?,?,?,?,?,?,?)");
            pstmtVeranstaltungen.setString(1, name);
            pstmtVeranstaltungen.setString(2, beschreibung);
            pstmtVeranstaltungen.setString(3, ort);
            pstmtVeranstaltungen.setString(4, datum);
            pstmtVeranstaltungen.setString(5, zeit);
            pstmtVeranstaltungen.setString(6, veranstalter.getBenutzername());
            pstmtVeranstaltungen.setInt(7, maxTeilnehmerzahl);
            pstmtVeranstaltungen.executeUpdate();
            System.out.println(ANSI_PURPLE + "Veranstaltung " + name + " wurde erfolgreich in die Datenbank geschrieben." + ANSI_RESET);

            System.out.println(ANSI_PURPLE + "Teilnehmende Benutzer der Veranstaltung " + name + " werden in die Datenbank geschrieben..." + ANSI_RESET);
            for (Benutzer benutzer : teilnehmendeBenutzer) {
                pstmtVeranstaltungsBenutzer = getCon().prepareStatement("INSERT INTO TeilnehmendeBenutzer (name,benutzername) VALUES (?,?)");
                pstmtVeranstaltungsBenutzer.setString(1, name);
                pstmtVeranstaltungsBenutzer.setString(2, benutzer.getBenutzername());
                pstmtVeranstaltungsBenutzer.executeUpdate();
            }
            System.out.println(ANSI_PURPLE + "Teilnehmende Benutzer der Veranstaltung " + name + " wurden erfolgreich in die Datenbank geschrieben." + ANSI_RESET);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Datenbankverwaltung.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public boolean veranstaltungLoeschen(String name) {
        try {
            // Teilnehmer loeschen
            System.out.println(ANSI_PURPLE + "Teilnehmer der Veranstaltung " + name + " werden aus der Datenbank gelöscht..." + ANSI_RESET);
            Statement stmtTeilnehmendeBenutzer = getCon().createStatement();
            String sqlTeilnehmendeBenutzer = "DELETE FROM TeilnehmendeBenutzer "
                    + "WHERE name = '" + name + "'";
            stmtTeilnehmendeBenutzer.executeUpdate(sqlTeilnehmendeBenutzer);
            System.out.println(ANSI_PURPLE + "Teilnehmer der Veranstaltung " + name + " wurden erfolgreich aus der Datenbank gelöscht..." + ANSI_RESET);            
            
            // Benoetigte Artikel loeschen            
            System.out.println(ANSI_PURPLE + "Benoetigte Artikel der Veranstaltung " + name + " werden aus der Datenbank gelöscht..." + ANSI_RESET);
            Statement stmtBenoetigteArtikel = getCon().createStatement();
            String sqlBenoetigteArtikel = "DELETE FROM BenoetigteArtikel "
                    + "WHERE name = '" + name + "'";
            stmtBenoetigteArtikel.executeUpdate(sqlBenoetigteArtikel);
            System.out.println(ANSI_PURPLE + "Benoetigte Artikel der Veranstaltung " + name + " wurden erfolgreich aus der Datenbank gelöscht..." + ANSI_RESET);                   
            
            // Vorhandene Artikel loeschen            
            System.out.println(ANSI_PURPLE + "Vorhandene Artikel der Veranstaltung " + name + " werden aus der Datenbank gelöscht..." + ANSI_RESET);
            Statement stmtVorhandeneArtikel = getCon().createStatement();
            String sqlVorhandeneArtikel = "DELETE FROM VorhandeneArtikel "
                    + "WHERE name = '" + name + "'";
            stmtVorhandeneArtikel.executeUpdate(sqlVorhandeneArtikel);
            System.out.println(ANSI_PURPLE + "Vorhandene Artikel der Veranstaltung " + name + " wurden erfolgreich aus der Datenbank gelöscht..." + ANSI_RESET);                 
           
            // Veranstaltung loeschen
            System.out.println(ANSI_PURPLE + "Veranstaltung " + name + " wird aus der Datenbank gelöscht..." + ANSI_RESET);
            Statement stmtVeranstaltung = getCon().createStatement();
            String sqlVeranstaltung = "DELETE FROM Veranstaltungen "
                    + "WHERE name = '" + name + "'";
            stmtVeranstaltung.executeUpdate(sqlVeranstaltung);
            System.out.println(ANSI_PURPLE + "Veranstaltung " + name + " wurde erfolgreich aus der Datenbank gelöscht..." + ANSI_RESET);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Datenbankverwaltung.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Benutzerdaten werden aus der Datenbank geladen
     */
    public void benutzerAusDatenbankLaden() {

        Benutzerverwaltung bv = Benutzerverwaltung.getInstanz();

        try {
            PreparedStatement ps = getCon().prepareStatement("SELECT * FROM Benutzer");
            ResultSet rs = ps.executeQuery();
            System.out.println(ANSI_BLUE + "Neue Benutzer aus der Datenbank" + ANSI_RESET);
            while (rs.next()) {
                Benutzer b = new Benutzer();
                b.setBenutzername(rs.getString(1));
                System.out.print("Benutzername:" + ANSI_GREEN + rs.getString(1) + ANSI_RESET);
                b.setEmail(rs.getString(2));
                System.out.print("; Email:" + ANSI_GREEN + rs.getString(2) + ANSI_RESET);
                b.setIstAdmin(rs.getBoolean(3));
                System.out.print("; IstAdmin:" + ANSI_GREEN + rs.getBoolean(3) + ANSI_RESET);
                b.setNachname(rs.getString(4));
                System.out.print("; Nachname:" + ANSI_GREEN + rs.getString(4) + ANSI_RESET);
                b.setPasswort(rs.getString(5));
                System.out.print("; Passwort:" + ANSI_GREEN + rs.getString(5) + ANSI_RESET);
                b.setVorname(rs.getString(6));
                System.out.println("; Vorname:" + ANSI_GREEN + rs.getString(6) + ANSI_RESET);
                bv.getAlleBenutzer().put(b.getBenutzername(), b);
                System.out.println("Neuer Benutzer " + ANSI_PURPLE + rs.getString(1) + ANSI_RESET + " wurde erfolgreich aus der Datenbank geladen.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Datenbankverwaltung.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Veranstaltungsdaten werden aus der Datenbank geladen
     */
    public void veranstaltungenAusDatenbankLaden() {

        Benutzerverwaltung bv = Benutzerverwaltung.getInstanz();
        Veranstaltungsverwaltung vv = Veranstaltungsverwaltung.getInstanz();
        Artikelverwaltung av = Artikelverwaltung.getInstanz();

        HashMap<String, Artikel> benoetigteArtikel = new HashMap<>();
        String beschreibung = null;
        Integer maxTeilnehmerzahl = null;
        String name = null;
        String ort = null;
        Date startTermin = null;
        HashSet<Benutzer> teilnehmendeBenutzer = new HashSet<>();
        Benutzer veranstalter = null;
        HashMap<String, Artikel> vorhandeneArtikel = new HashMap<>();

        String nameVeranstaltungsBenutzer = null;
        String benutzernameVeranstaltungsBenutzer = null;

        String nameBenoetigteArtikel = null;
        String bezeichnungBenoetigteArtikel = null;

        String nameVorhandeneArtikel = null;
        String bezeichnungVorhandeneArtikel = null;
        try {
            PreparedStatement psVeranstaltungen = getCon().prepareStatement("SELECT * FROM Veranstaltungen");
            ResultSet rsVeranstaltungen = psVeranstaltungen.executeQuery();
            System.out.println(ANSI_BLUE + "Neue Veranstaltungen aus der Datenbank" + ANSI_RESET);
            while (rsVeranstaltungen.next()) {
                // Name
                name = rsVeranstaltungen.getString(1);
                System.out.print("Name:" + ANSI_GREEN + rsVeranstaltungen.getString(1) + ANSI_RESET);
                // Beschreibung
                beschreibung = rsVeranstaltungen.getString(2);
                System.out.print("; Beschreibung:" + ANSI_GREEN + rsVeranstaltungen.getString(2) + ANSI_RESET);
                // Ort
                ort = rsVeranstaltungen.getString(3);
                System.out.print("; Ort:" + ANSI_GREEN + rsVeranstaltungen.getString(3) + ANSI_RESET);
                //Datum und Zeit
                String datum = rsVeranstaltungen.getString(4);
                String zeit = rsVeranstaltungen.getString(5);
                StringBuilder sb = new StringBuilder();
                sb.append(datum + " " + zeit);
                String ergebnis = sb.toString();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                startTermin = df.parse(ergebnis);
                DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                System.out.print("; Start Zeit:" + ANSI_GREEN + df1.format(startTermin) + ANSI_RESET);
                // Veranstalter
                veranstalter = bv.getBenutzer(rsVeranstaltungen.getString(6));
                System.out.print("; Veranstalter:" + ANSI_GREEN + rsVeranstaltungen.getString(6) + ANSI_RESET);
                //. max Teilnehmerzahl
                maxTeilnehmerzahl = rsVeranstaltungen.getInt(7);
                System.out.print("; Max Teilnehmerzahl:" + ANSI_GREEN + rsVeranstaltungen.getInt(7) + ANSI_RESET);

                // Teilnehmende Benutzer
                System.out.print("; Teilnehmer: ");
                PreparedStatement psVeranstaltungsBenutzer = getCon().prepareStatement("SELECT * FROM TeilnehmendeBenutzer");
                ResultSet rsVeranstaltungsBenutzer = psVeranstaltungsBenutzer.executeQuery();
                while (rsVeranstaltungsBenutzer.next()) {
                    // Name
                    nameVeranstaltungsBenutzer = rsVeranstaltungsBenutzer.getString(1);
                    // benutzername
                    benutzernameVeranstaltungsBenutzer = rsVeranstaltungsBenutzer.getString(2);
                    if (name.equals(nameVeranstaltungsBenutzer)) {
                        teilnehmendeBenutzer.add(bv.getBenutzer(benutzernameVeranstaltungsBenutzer));
                        System.out.print(ANSI_GREEN + rsVeranstaltungsBenutzer.getString(2) + ANSI_RESET);
                        System.out.print("; ");
                    }
                }

                // Benoetigte Artikel
                System.out.print(" Benoetigte Artikel: ");
                PreparedStatement psBenoetigteArtikel = getCon().prepareStatement("SELECT * FROM BenoetigteArtikel");
                ResultSet rsBenoetigteArtikel = psBenoetigteArtikel.executeQuery();
                while (rsBenoetigteArtikel.next()) {
                    // Name
                    nameBenoetigteArtikel = rsBenoetigteArtikel.getString(1);
                    // Artikel
                    bezeichnungBenoetigteArtikel = rsBenoetigteArtikel.getString(2);
                    if (name.equals(nameBenoetigteArtikel)) {
                        benoetigteArtikel.put(bezeichnungBenoetigteArtikel, new Artikel(bezeichnungBenoetigteArtikel));
                        System.out.print(ANSI_GREEN + rsBenoetigteArtikel.getString(2) + ANSI_RESET);
                        System.out.print("; ");
                    }
                }

                // Vorhandene Artikel
                System.out.print(" Vorhandene Artikel: ");
                PreparedStatement psVorhandeneArtikel = getCon().prepareStatement("SELECT * FROM VorhandeneArtikel");
                ResultSet rsVorhandeneArtikel = psVorhandeneArtikel.executeQuery();
                while (rsVorhandeneArtikel.next()) {
                    // Name
                    nameVorhandeneArtikel = rsVorhandeneArtikel.getString(1);
                    // Artikel
                    bezeichnungVorhandeneArtikel = rsVorhandeneArtikel.getString(2);
                    if (name.equals(nameVorhandeneArtikel)) {
                        vorhandeneArtikel.put(bezeichnungVorhandeneArtikel, new Artikel(bezeichnungVorhandeneArtikel));
                        System.out.print(ANSI_GREEN + rsVorhandeneArtikel.getString(2) + ANSI_RESET);
                        System.out.print("; ");
                    }
                }

                System.out.println("");
                Veranstaltung v = new Veranstaltung(benoetigteArtikel, beschreibung, maxTeilnehmerzahl, name, ort, startTermin, teilnehmendeBenutzer, veranstalter, vorhandeneArtikel);
                vv.getAlleVeranstaltungen().put(v.getName(), v);

                System.out.println("Neue Veranstaltung " + ANSI_PURPLE + name + ANSI_RESET + " wurde erfolgreich aus der Datenbank geladen.");
                // zuruecksetzen
                benoetigteArtikel = new HashMap<>();
                teilnehmendeBenutzer = new HashSet<>();
                vorhandeneArtikel = new HashMap<>();
            }
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(Datenbankverwaltung.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Verbindung mit der Datenbank wird hergestellt
     */
    public void verbindungMitDatenbank() {

        String host = "jdbc:derby://localhost:1527/SitInPartyConnection;create=true";
        String uName = "vts";
        String uPass = "vts";
        try {
            System.out.println(ANSI_PURPLE + "Verbinde mit der Datenbank...");
            con = DriverManager.getConnection(host, uName, uPass);
            System.out.println(ANSI_PURPLE + "Erfolgreich mit der Datenbank verbunden...");
            benutzerAusDatenbankLaden();
            veranstaltungenAusDatenbankLaden();
        } catch (SQLException ex) {
            Logger.getLogger(Datenbankverwaltung.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
