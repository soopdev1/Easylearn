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
    @NamedQuery(name = "corso.istanza", query = "SELECT c FROM Corso c WHERE c.soggetto=:soggetto AND c.codiceistanza=:codiceistanza"),
})
@Entity
@Table(name = "corso")
public class Corso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idcorso")
    private Long idcorso;
    @ManyToOne
    @JoinColumn(name = "idsoggetto")
    private SoggettoProponente soggetto;
    @ManyToOne
    @JoinColumn(name = "stato")
    private CorsoStato statocorso;
    @Column(name = "istat")
    private String istat;
    @ManyToOne
    @JoinColumn(name = "codicecertif")
    private Certificazione certif;
    @ManyToOne
    @JoinColumn(name = "codicelivellocertif")
    private Livello_Certificazione livellocertif;

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
    @Column(name = "req1")
    private String req1;

    @Column(name = "quantitarichiesta")
    private int quantitarichiesta;

    @ManyToOne
    @JoinColumn(name = "idsede")
    private Sede sedescelta;

    @Column(name = "codiceistanza")
    private String codiceistanza;

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

    public String getIstat() {
        return istat;
    }

    public void setIstat(String istat) {
        this.istat = istat;
    }

    public Certificazione getCertif() {
        return certif;
    }

    public void setCertif(Certificazione certif) {
        this.certif = certif;
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

    public String getReq1() {
        return req1;
    }

    public void setReq1(String req1) {
        this.req1 = req1;
    }

    public Sede getSedescelta() {
        return sedescelta;
    }

    public void setSedescelta(Sede sedescelta) {
        this.sedescelta = sedescelta;
    }

    public Livello_Certificazione getLivellocertif() {
        return livellocertif;
    }

    public void setLivellocertif(Livello_Certificazione livellocertif) {
        this.livellocertif = livellocertif;
    }

    public int getQuantitarichiesta() {
        return quantitarichiesta;
    }

    public void setQuantitarichiesta(int quantitarichiesta) {
        this.quantitarichiesta = quantitarichiesta;
    }

    public String getCodiceistanza() {
        return codiceistanza;
    }

    public void setCodiceistanza(String codiceistanza) {
        this.codiceistanza = codiceistanza;
    }

    public int getElearningperc() {
        return elearningperc;
    }

    public void setElearningperc(int elearningperc) {
        this.elearningperc = elearningperc;
    }

}
