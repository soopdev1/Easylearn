/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "attivita")
public class Attivita implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idattivita")
    private Long idattivita;

    @Column(name = "codice")
    private String codice;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "elenco", columnDefinition = "LONGTEXT")
    private String elenco;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "repertorio_competenze",
            joinColumns = @JoinColumn(name = "idattivita"),
            inverseJoinColumns = @JoinColumn(name = "idrepertorio"))
    private List<Repertorio> repertorio;
    
    @ManyToOne
    @JoinColumn(name = "idcompetenze")
    private Competenze competenza;
    
    
    public Attivita() {
    }

    public Long getIdattivita() {
        return idattivita;
    }

    public void setIdattivita(Long idattivita) {
        this.idattivita = idattivita;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getElenco() {
        return elenco;
    }

    public void setElenco(String elenco) {
        this.elenco = elenco;
    }

    public List<Repertorio> getRepertorio() {
        List<Repertorio> rep1 = new ArrayList<>();
        rep1.addAll(repertorio);
        return rep1;
    }

    public void setRepertorio(List<Repertorio> repertorio) {
        this.repertorio = repertorio;
    }

    public Competenze getCompetenza() {
        return competenza;
    }

    public void setCompetenza(Competenze competenza) {
        this.competenza = competenza;
    }
    
}