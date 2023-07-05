/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "competenzetrasversali")
public class Competenze_Trasversali implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idcompetenze")
    private Long idcompetenze;

    @Column(name = "requisitoingresso", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean requisito_ingresso;

    @Column(name = "descrizione", columnDefinition = "LONGTEXT")
    private String descrizione;

    @Column(name = "durataore")
    private int durataore;

    @Column(name = "abilitato", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean abilitato;

    @Column(name = "tooltip", columnDefinition = "LONGTEXT")
    private String tooltip;

    public Competenze_Trasversali() {
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public boolean isAbilitato() {
        return abilitato;
    }

    public void setAbilitato(boolean abilitato) {
        this.abilitato = abilitato;
    }

    public boolean isRequisito_ingresso() {
        return requisito_ingresso;
    }

    public void setRequisito_ingresso(boolean requisito_ingresso) {
        this.requisito_ingresso = requisito_ingresso;
    }

    public int getDurataore() {
        return durataore;
    }

    public void setDurataore(int durataore) {
        this.durataore = durataore;
    }

    public Long getIdcompetenze() {
        return idcompetenze;
    }

    public void setIdcompetenze(Long idcompetenze) {
        this.idcompetenze = idcompetenze;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Competenze_Trasversali other = (Competenze_Trasversali) obj;
        return Objects.equals(idcompetenze, other.idcompetenze);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.idcompetenze);
        return hash;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("{")
                .append("\"").append("idcompetenze").append("\"").append(":").append("\"").append(this.idcompetenze).append("\"").append(",")
                .append("}")
                .toString();
    }

}
