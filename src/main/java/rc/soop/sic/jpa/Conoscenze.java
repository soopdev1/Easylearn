/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "conoscenze")
public class Conoscenze implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idconoscenze")
    private Long idconoscenze;

    @Column(name = "descrizione", columnDefinition = "LONGTEXT")
    private String descrizione;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idcompetenze")
    private Competenze competenza;

    public Conoscenze() {
    }

    public Long getIdconoscenze() {
        return idconoscenze;
    }

    public void setIdconoscenze(Long idconoscenze) {
        this.idconoscenze = idconoscenze;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Competenze getCompetenza() {
        return competenza;
    }

    public void setCompetenza(Competenze competenza) {
        this.competenza = competenza;
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
        Conoscenze other = (Conoscenze) obj;
        return Objects.equals(idconoscenze, other.idconoscenze);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.idconoscenze);
        return hash;
    }
}
