/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Raffaele
 */

@NamedQueries(value = {
    @NamedQuery(name = "corso.istanza", query = "SELECT c FROM Corso c WHERE c.istanza=:codiceistanza"),
})
@Entity
@Table(name = "corso")
public class Corso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name = "idcorso")
    private Long idcorso;
    @ManyToOne
    @JoinColumn(name = "idsoggetto")
    private SoggettoProponente soggetto;
    @ManyToOne
    @JoinColumn(name = "stato")
    private CorsoStato statocorso;    
    @ManyToOne
    @JoinColumn(name = "repertorio")
    private Repertorio repertorio;
    @ManyToOne
    @JoinColumn(name = "schedaattivita")
    private Scheda_Attivita schedaattivita;
    @Column(name = "durataore")
    private int durataore;
    @Column(name = "stageore")
    private int stageore;
    @Column(name = "duratagiorni")
    private int duratagiorni;
    @Column(name = "elearningperc")
    private int elearningperc;
    @Column(name = "numeroallievi")
    private int numeroallievi;
    @Column(name = "quantitarichiesta")
    private int quantitarichiesta;
    @ManyToOne
    @JoinColumn(name = "idsede")
    private Sede sedescelta;
    @ManyToOne
    @JoinColumn(name = "istanza")
    private Istanza istanza;
    @ManyToOne
    @JoinColumn(name = "tipologiapercorso")
    private Tipologia_Percorso tipologiapercorso;
    
    @Column(name = "costostimatoallievo")
    private double costostimatoallievo;
    
    
    public Corso() {
    }

    public Long getIdcorso() {
        return idcorso;
    }

    public void setIdcorso(Long idcorso) {
        this.idcorso = idcorso;
    }

    public SoggettoProponente getSoggetto() {
        return soggetto;
    }

    public void setSoggetto(SoggettoProponente soggetto) {
        this.soggetto = soggetto;
    }

    public CorsoStato getStatocorso() {
        return statocorso;
    }

    public void setStatocorso(CorsoStato statocorso) {
        this.statocorso = statocorso;
    }

    public Repertorio getRepertorio() {
        return repertorio;
    }

    public void setRepertorio(Repertorio repertorio) {
        this.repertorio = repertorio;
    }

    public Scheda_Attivita getSchedaattivita() {
        return schedaattivita;
    }

    public void setSchedaattivita(Scheda_Attivita schedaattivita) {
        this.schedaattivita = schedaattivita;
    }

    public int getDurataore() {
        return durataore;
    }

    public void setDurataore(int durataore) {
        this.durataore = durataore;
    }

    public int getStageore() {
        return stageore;
    }

    public void setStageore(int stageore) {
        this.stageore = stageore;
    }

    public int getDuratagiorni() {
        return duratagiorni;
    }

    public void setDuratagiorni(int duratagiorni) {
        this.duratagiorni = duratagiorni;
    }

    public int getNumeroallievi() {
        return numeroallievi;
    }

    public void setNumeroallievi(int numeroallievi) {
        this.numeroallievi = numeroallievi;
    }

    public Sede getSedescelta() {
        return sedescelta;
    }

    public void setSedescelta(Sede sedescelta) {
        this.sedescelta = sedescelta;
    }

    public int getQuantitarichiesta() {
        return quantitarichiesta;
    }

    public void setQuantitarichiesta(int quantitarichiesta) {
        this.quantitarichiesta = quantitarichiesta;
    }

    public Istanza getIstanza() {
        return istanza;
    }

    public void setIstanza(Istanza istanza) {
        this.istanza = istanza;
    }

    public int getElearningperc() {
        return elearningperc;
    }

    public void setElearningperc(int elearningperc) {
        this.elearningperc = elearningperc;
    }

    public Tipologia_Percorso getTipologiapercorso() {
        return tipologiapercorso;
    }

    public void setTipologiapercorso(Tipologia_Percorso tipologiapercorso) {
        this.tipologiapercorso = tipologiapercorso;
    }

    public double getCostostimatoallievo() {
        return costostimatoallievo;
    }

    public void setCostostimatoallievo(double costostimatoallievo) {
        this.costostimatoallievo = costostimatoallievo;
    }
    
}
