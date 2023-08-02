/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Raffaele
 */
@NamedQueries(value = {
    @NamedQuery(name = "corso.istanza", query = "SELECT c FROM Corso c WHERE c.istanza=:codiceistanza"),})
@Entity
@Table(name = "corso")
public class Corso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idcorso")
    private Long idcorso;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "idsoggetto")
    private SoggettoProponente soggetto;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "stato")
    private CorsoStato statocorso;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "repertorio")
    private Repertorio repertorio;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
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
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "idsede")
    private Sede sedescelta;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "istanza")
    private Istanza istanza;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "tipologiapercorso")
    private Tipologia_Percorso tipologiapercorso;

    @Column(name = "costostimatoallievo")
    private double costostimatoallievo;

    @OneToMany(mappedBy = "corso", fetch = FetchType.EAGER)
    private List<Attrezzature> attrezzature;
    
    @Column(name = "identificativocorso")
    private String identificativocorso;
    
    @Column(name = "cip_corso")
    private String cip_corso;
    @Column(name = "cup_corso")
    private String cup_corso;
    @Column(name = "id_corso")
    private String id_corso;
    @Column(name = "cs_corso")
    private String cs_corso;
    @Column(name = "ed_corso")
    private String ed_corso;
    
    public Corso() {
    }

    public String getIdentificativocorso() {
        return identificativocorso;
    }

    public void setIdentificativocorso(String identificativocorso) {
        this.identificativocorso = identificativocorso;
    }

    public String getCip_corso() {
        return cip_corso;
    }

    public void setCip_corso(String cip_corso) {
        this.cip_corso = cip_corso;
    }

    public String getCup_corso() {
        return cup_corso;
    }

    public void setCup_corso(String cup_corso) {
        this.cup_corso = cup_corso;
    }

    public String getId_corso() {
        return id_corso;
    }

    public void setId_corso(String id_corso) {
        this.id_corso = id_corso;
    }

    public String getCs_corso() {
        return cs_corso;
    }

    public void setCs_corso(String cs_corso) {
        this.cs_corso = cs_corso;
    }

    public String getEd_corso() {
        return ed_corso;
    }

    public void setEd_corso(String ed_corso) {
        this.ed_corso = ed_corso;
    }
    
    

    public Long getIdcorso() {
        return idcorso;
    }

    public List<Attrezzature> getAttrezzature() {
        return attrezzature;
    }

    public void setAttrezzature(List<Attrezzature> attrezzature) {
        this.attrezzature = attrezzature;
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
