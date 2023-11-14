/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Raffaele
 */
@NamedQueries(value = {
    @NamedQuery(name = "corsoavviato.stato", query = "SELECT c FROM Corsoavviato c WHERE c.statocorso=:stato"),
    @NamedQuery(name = "corsoavviato.corsobase", query = "SELECT c FROM Corsoavviato c WHERE c.corsobase=:corsobase"),
    @NamedQuery(name = "corsoavviato.soggetto", query = "SELECT c FROM Corsoavviato c WHERE c.corsobase.soggetto=:soggetto"),
    @NamedQuery(name = "corsoavviato.contatoreok", query = "SELECT c FROM Corsoavviato c WHERE c.statocorso IN :statusok")
    
})
@Entity
@Table(name = "corsoavviato")
public class Corsoavviato implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idcorsoavviato")
    private Long idcorsoavviato;

    @Column(name = "datainizio")
    @Temporal(TemporalType.DATE)
    private Date datainizio;

    @Column(name = "datafine")
    @Temporal(TemporalType.DATE)
    private Date datafine;

    //DA RIMUOVERE
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "direttore")
    private Altropersonale direttore;

    @Column(name = "direttorecorso")
    private String direttorecorso;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "stato")
    private CorsoStato statocorso;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idcorso")
    private Corso corsobase;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "presidentecommissione")
    private PresidenteCommissione presidentecommissione;

    @Column(name = "protgab")
    private String protgab;
    @Column(name = "dataprotgab")
    @Temporal(TemporalType.DATE)
    private Date dataprotgab;
    
    
    @Column(name = "protnomina")
    private String protnomina;
    @Column(name = "dataprotnomina")
    @Temporal(TemporalType.DATE)
    private Date dataprotnomina;
    @Column(name = "utentenomina")
    private String utentenomina;
    @Column(name = "pdfnomina", columnDefinition = "LONGTEXT")
    private String pdfnomina;
    
    

    @Column(name = "datainserimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datainserimento;

    public String getPdfnomina() {
        return pdfnomina;
    }

    public void setPdfnomina(String pdfnomina) {
        this.pdfnomina = pdfnomina;
    }

    public String getProtgab() {
        return protgab;
    }

    public void setProtgab(String protgab) {
        this.protgab = protgab;
    }

    public Date getDataprotgab() {
        return dataprotgab;
    }

    public void setDataprotgab(Date dataprotgab) {
        this.dataprotgab = dataprotgab;
    }

    public String getUtentenomina() {
        return utentenomina;
    }

    public void setUtentenomina(String utentenomina) {
        this.utentenomina = utentenomina;
    }

    public String getProtnomina() {
        return protnomina;
    }

    public void setProtnomina(String protnomina) {
        this.protnomina = protnomina;
    }

    public Date getDataprotnomina() {
        return dataprotnomina;
    }

    public void setDataprotnomina(Date dataprotnomina) {
        this.dataprotnomina = dataprotnomina;
    }

    public Date getDatainserimento() {
        return datainserimento;
    }

    public void setDatainserimento(Date datainserimento) {
        this.datainserimento = datainserimento;
    }

    public Altropersonale getDirettore() {
        return direttore;
    }

    public void setDirettore(Altropersonale direttore) {
        this.direttore = direttore;
    }

    public Long getIdcorsoavviato() {
        return idcorsoavviato;
    }

    public void setIdcorsoavviato(Long idcorsoavviato) {
        this.idcorsoavviato = idcorsoavviato;
    }

    public Date getDatainizio() {
        return datainizio;
    }

    public void setDatainizio(Date datainizio) {
        this.datainizio = datainizio;
    }

    public Corso getCorsobase() {
        return corsobase;
    }

    public void setCorsobase(Corso corsobase) {
        this.corsobase = corsobase;
    }

    public Date getDatafine() {
        return datafine;
    }

    public String getDirettorecorso() {
        return direttorecorso;
    }

    public void setDirettorecorso(String direttorecorso) {
        this.direttorecorso = direttorecorso;
    }

    public void setDatafine(Date datafine) {
        this.datafine = datafine;
    }

    public CorsoStato getStatocorso() {
        return statocorso;
    }

    public void setStatocorso(CorsoStato statocorso) {
        this.statocorso = statocorso;
    }

    public PresidenteCommissione getPresidentecommissione() {
        return presidentecommissione;
    }

    public void setPresidentecommissione(PresidenteCommissione presidentecommissione) {
        this.presidentecommissione = presidentecommissione;
    }

}
