/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "competenze")
public class Competenze implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idcompetenze")
    private Long idcompetenze;

    @Column(name = "numero")
    private int numero;

    @Column(name = "descrizione", columnDefinition = "LONGTEXT")
    private String descrizione;

    @ManyToOne
    @JoinColumn(name = "idrepertorio")
    private Repertorio repertorio;
    
    @OneToMany(mappedBy = "competenza",fetch = FetchType.LAZY)
    private List<Abilita> abilita;
    
    @OneToMany(mappedBy = "competenza",fetch = FetchType.LAZY)
    private List<Conoscenze> conoscenze;
    
    public Competenze() {
    }

    public Repertorio getRepertorio() {
        return repertorio;
    }

    public void setRepertorio(Repertorio repertorio) {
        this.repertorio = repertorio;
    }

    public Long getIdcompetenze() {
        return idcompetenze;
    }

    public void setIdcompetenze(Long idcompetenze) {
        this.idcompetenze = idcompetenze;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public List<Abilita> getAbilita() {
        return abilita;
    }

    public void setAbilita(List<Abilita> abilita) {
        this.abilita = abilita;
    }

    public List<Conoscenze> getConoscenze() {
        return conoscenze;
    }

    public void setConoscenze(List<Conoscenze> conoscenze) {
        this.conoscenze = conoscenze;
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
        Competenze other = (Competenze) obj;
        return Objects.equals(idcompetenze, other.idcompetenze);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.idcompetenze);
        return hash;
    }
    
}
