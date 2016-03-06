/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Markus, Simon
 */
public class Artikel  implements Serializable {

    private static final long serialVersionUID = 6529682111267757690L;
    private String bezeichnung;

    /**
     *
     * @param bezeichnung
     */
    public Artikel(String bezeichnung) {
        this.bezeichnung = bezeichnung;
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
        final Artikel other = (Artikel) obj;
        if (!Objects.equals(this.bezeichnung, other.bezeichnung)) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    public String getBezeichnung() {
        return bezeichnung;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.bezeichnung);
        return hash;
    }
    /**
     *
     * @param bezeichnung
     */
    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    @Override
    public String toString() {
        return "Artikel{" + "bezeichnung=" + bezeichnung + '}';
    }
}
