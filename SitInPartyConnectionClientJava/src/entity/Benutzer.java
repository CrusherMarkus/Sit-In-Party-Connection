/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import static entity.Hilfsklasse.ANSI_GREEN;
import static entity.Hilfsklasse.ANSI_RESET;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Markus
 */
public class Benutzer implements Serializable {

    private static final long serialVersionUID = 6529682348267757690L;
    private String benutzername;
    private String email;
    private Boolean istAdmin;
    private String nachname;
    private String passwort;
    private String vorname;

    /**
     * Standard-Konstruktor ohne Uebergabeparameter
     */
    public Benutzer() {
    }
    
    /**
     *
     * @param benutzername
     * @param email
     * @param istAdmin
     * @param nachname
     * @param passwort
     * @param vorname
     */
    public Benutzer(String benutzername, String email, Boolean istAdmin, String nachname, String passwort, String vorname) {
        this.benutzername = benutzername;
        this.email = email;
        this.istAdmin = istAdmin;
        this.nachname = nachname;
        this.passwort = passwort;
        this.vorname = vorname;
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
        final Benutzer other = (Benutzer) obj;
        if (!Objects.equals(this.benutzername, other.benutzername)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.nachname, other.nachname)) {
            return false;
        }
        if (!Objects.equals(this.passwort, other.passwort)) {
            return false;
        }
        if (!Objects.equals(this.vorname, other.vorname)) {
            return false;
        }
        if (!Objects.equals(this.istAdmin, other.istAdmin)) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    public String getBenutzername() {
        return benutzername;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @return
     */
    public Boolean getIstAdmin() {
        return istAdmin;
    }

    /**
     *
     * @return
     */
    public String getNachname() {
        return nachname;
    }

    /**
     *
     * @return
     */
    public String getPasswort() {
        return passwort;
    }

    /**
     *
     * @return
     */
    public String getVorname() {
        return vorname;
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.benutzername);
        hash = 37 * hash + Objects.hashCode(this.email);
        hash = 37 * hash + Objects.hashCode(this.istAdmin);
        hash = 37 * hash + Objects.hashCode(this.nachname);
        hash = 37 * hash + Objects.hashCode(this.passwort);
        hash = 37 * hash + Objects.hashCode(this.vorname);
        return hash;
    }

    /**
     *
     * @param benutzername
     */
    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @param istAdmin
     */
    public void setIstAdmin(Boolean istAdmin) {
        this.istAdmin = istAdmin;
    }

    /**
     *
     * @param nachname
     */
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    /**
     *
     * @param passwort
     */
    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    /**
     *
     * @param vorname
     */
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    @Override
    public String toString() {
        return "Benutzer: " + ANSI_GREEN + benutzername + ANSI_RESET
                + "; Passwort: " + ANSI_GREEN + passwort + ANSI_RESET
                + "; Vorname: " + ANSI_GREEN + vorname + ANSI_RESET
                + "; Nachname: " + ANSI_GREEN + nachname + ANSI_RESET
                + "; E-Mail: " + ANSI_GREEN + email + ANSI_RESET
                + "; istAdmin: " + ANSI_GREEN + istAdmin + ANSI_RESET;
    }
}
