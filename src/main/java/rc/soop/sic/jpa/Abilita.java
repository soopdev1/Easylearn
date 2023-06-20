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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "abilita")
public class Abilita implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idabilita")
    private Long idabilita;
    
    @Column(name = "descrizione", columnDefinition = "LONGTEXT")
    private String descrizione;
    
    @ManyToOne
    @JoinColumn(name = "idcompetenze")
    private Competenze competenza;

    public Abilita() {
    }

    public Long getIdabilita() {
        return idabilita;
    }

    public void setIdabilita(Long idabilita) {
        this.idabilita = idabilita;
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
        Abilita other = (Abilita) obj;
        return Objects.equals(idabilita, other.idabilita);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.idabilita);
        return hash;
    }
}