/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "esamefinale")
public class Esamefinale implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idesamefinale")
    private Long idesamefinale;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idcorsoavviato")
    private Corsoavviato corsodiriferimento;

    @Column(name = "datainserimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datainserimento;

    @Column(name = "dataoraesame")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataoraesame;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idpresidente")
    private PresidenteCommissione presidentecommissione;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idcommissione")
    private CommissioneEsame commissioneesame;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idcommissionesostituzione1")
    private CommissioneEsameSostituzione sostituzione1;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "iddocentecom1")
    private Docente commissionenome1;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "iddocentecom2")
    private Docente commissionenome2;
    
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idcommissionesostituzione2")
    private CommissioneEsameSostituzione sostituzione2;

    @Column(name = "verificaintermedia", columnDefinition = "tinyint(1) default 0")
    private boolean verificaintermedia;

    @Column(name = "dataverificaintermedia")
    @Temporal(TemporalType.DATE)
    private Date dataverificaintermedia;

    @Column(name = "puntoa", columnDefinition = "LONGTEXT")
    private String puntoa;
    @Column(name = "puntob", columnDefinition = "LONGTEXT")
    private String puntob;
    @Column(name = "puntoe", columnDefinition = "LONGTEXT")
    private String puntoe;
    @Column(name = "puntof", columnDefinition = "LONGTEXT")
    private String puntof;
    @Column(name = "puntog", columnDefinition = "LONGTEXT")
    private String puntog;
    @Column(name = "puntoh", columnDefinition = "LONGTEXT")
    private String puntoh;

    @Column(name = "oraestrazione")
    private String oraestrazione;

    @Column(name = "utenteestrazione")
    private String utenteestrazione;

    @Column(name = "primaprova", columnDefinition = "LONGTEXT")
    private String primaprova;

    @Column(name = "secondaprova", columnDefinition = "LONGTEXT")
    private String secondaprova;
    
    
    @Column(name = "iscritti")
    private int iscritti;
    @Column(name = "ammessi")
    private int ammessi;
    @Column(name = "assenti")
    private int assenti;
    @Column(name = "esaminati")
    private int esaminati;
    @Column(name = "idonei")
    private int idonei;
    @Column(name = "nonidonei")
    private int nonidonei;
    @Column(name = "interni")
    private int interni;
    @Column(name = "esterni")
    private int esterni;
    
    @Column(name = "underM")
    private int underM;
    @Column(name = "underF")
    private int underF;
    @Column(name = "overM")
    private int overM;
    @Column(name = "overF")
    private int overF;
    @Column(name = "interniOK")
    private int interniOK;
    @Column(name = "esterniOK")
    private int esterniOK;

    
    @Column(name = "protocolloverbale")
    private String protocolloverbale;
    
    @Column(name = "dataverbale")
    @Temporal(TemporalType.DATE)
    private Date dataverbale;
    
    @Column(name = "nomesosttoscrittore")
    private String nomesottoscrittore;
    
    @Column(name = "ruolososttoscrittore")
    private String ruolososttoscrittore;
    
    public Esamefinale() {
    }

    public Long getIdesamefinale() {
        return idesamefinale;
    }

    public void setIdesamefinale(Long idesamefinale) {
        this.idesamefinale = idesamefinale;
    }

    public Corsoavviato getCorsodiriferimento() {
        return corsodiriferimento;
    }

    public void setCorsodiriferimento(Corsoavviato corsodiriferimento) {
        this.corsodiriferimento = corsodiriferimento;
    }

    public Date getDatainserimento() {
        return datainserimento;
    }

    public void setDatainserimento(Date datainserimento) {
        this.datainserimento = datainserimento;
    }

    public Date getDataoraesame() {
        return dataoraesame;
    }

    public void setDataoraesame(Date dataoraesame) {
        this.dataoraesame = dataoraesame;
    }

    public PresidenteCommissione getPresidentecommissione() {
        return presidentecommissione;
    }

    public void setPresidentecommissione(PresidenteCommissione presidentecommissione) {
        this.presidentecommissione = presidentecommissione;
    }

    public CommissioneEsame getCommissioneesame() {
        return commissioneesame;
    }

    public void setCommissioneesame(CommissioneEsame commissioneesame) {
        this.commissioneesame = commissioneesame;
    }

    public CommissioneEsameSostituzione getSostituzione1() {
        return sostituzione1;
    }

    public void setSostituzione1(CommissioneEsameSostituzione sostituzione1) {
        this.sostituzione1 = sostituzione1;
    }

    public Docente getCommissionenome1() {
        return commissionenome1;
    }

    public void setCommissionenome1(Docente commissionenome1) {
        this.commissionenome1 = commissionenome1;
    }

    public Docente getCommissionenome2() {
        return commissionenome2;
    }

    public void setCommissionenome2(Docente commissionenome2) {
        this.commissionenome2 = commissionenome2;
    }

    public CommissioneEsameSostituzione getSostituzione2() {
        return sostituzione2;
    }

    public void setSostituzione2(CommissioneEsameSostituzione sostituzione2) {
        this.sostituzione2 = sostituzione2;
    }

    public boolean isVerificaintermedia() {
        return verificaintermedia;
    }

    public void setVerificaintermedia(boolean verificaintermedia) {
        this.verificaintermedia = verificaintermedia;
    }

    public Date getDataverificaintermedia() {
        return dataverificaintermedia;
    }

    public void setDataverificaintermedia(Date dataverificaintermedia) {
        this.dataverificaintermedia = dataverificaintermedia;
    }

    public String getPuntoa() {
        return puntoa;
    }

    public void setPuntoa(String puntoa) {
        this.puntoa = puntoa;
    }

    public String getPuntob() {
        return puntob;
    }

    public void setPuntob(String puntob) {
        this.puntob = puntob;
    }

    public String getPuntoe() {
        return puntoe;
    }

    public void setPuntoe(String puntoe) {
        this.puntoe = puntoe;
    }

    public String getPuntof() {
        return puntof;
    }

    public void setPuntof(String puntof) {
        this.puntof = puntof;
    }

    public String getPuntog() {
        return puntog;
    }

    public void setPuntog(String puntog) {
        this.puntog = puntog;
    }

    public String getPuntoh() {
        return puntoh;
    }

    public void setPuntoh(String puntoh) {
        this.puntoh = puntoh;
    }

    public String getOraestrazione() {
        return oraestrazione;
    }

    public void setOraestrazione(String oraestrazione) {
        this.oraestrazione = oraestrazione;
    }

    public String getUtenteestrazione() {
        return utenteestrazione;
    }

    public void setUtenteestrazione(String utenteestrazione) {
        this.utenteestrazione = utenteestrazione;
    }

    public String getPrimaprova() {
        return primaprova;
    }

    public void setPrimaprova(String primaprova) {
        this.primaprova = primaprova;
    }

    public String getSecondaprova() {
        return secondaprova;
    }

    public void setSecondaprova(String secondaprova) {
        this.secondaprova = secondaprova;
    }

    public int getIscritti() {
        return iscritti;
    }

    public void setIscritti(int iscritti) {
        this.iscritti = iscritti;
    }

    public int getAmmessi() {
        return ammessi;
    }

    public void setAmmessi(int ammessi) {
        this.ammessi = ammessi;
    }

    public int getAssenti() {
        return assenti;
    }

    public void setAssenti(int assenti) {
        this.assenti = assenti;
    }

    public int getEsaminati() {
        return esaminati;
    }

    public void setEsaminati(int esaminati) {
        this.esaminati = esaminati;
    }

    public int getIdonei() {
        return idonei;
    }

    public void setIdonei(int idonei) {
        this.idonei = idonei;
    }

    public int getNonidonei() {
        return nonidonei;
    }

    public void setNonidonei(int nonidonei) {
        this.nonidonei = nonidonei;
    }

    public int getInterni() {
        return interni;
    }

    public void setInterni(int interni) {
        this.interni = interni;
    }

    public int getEsterni() {
        return esterni;
    }

    public void setEsterni(int esterni) {
        this.esterni = esterni;
    }

    public int getUnderM() {
        return underM;
    }

    public void setUnderM(int underM) {
        this.underM = underM;
    }

    public int getUnderF() {
        return underF;
    }

    public void setUnderF(int underF) {
        this.underF = underF;
    }

    public int getOverM() {
        return overM;
    }

    public void setOverM(int overM) {
        this.overM = overM;
    }

    public int getOverF() {
        return overF;
    }

    public void setOverF(int overF) {
        this.overF = overF;
    }

    public int getInterniOK() {
        return interniOK;
    }

    public void setInterniOK(int interniOK) {
        this.interniOK = interniOK;
    }

    public int getEsterniOK() {
        return esterniOK;
    }

    public void setEsterniOK(int esterniOK) {
        this.esterniOK = esterniOK;
    }

    public String getProtocolloverbale() {
        return protocolloverbale;
    }

    public void setProtocolloverbale(String protocolloverbale) {
        this.protocolloverbale = protocolloverbale;
    }

    public Date getDataverbale() {
        return dataverbale;
    }

    public void setDataverbale(Date dataverbale) {
        this.dataverbale = dataverbale;
    }

    public String getNomesottoscrittore() {
        return nomesottoscrittore;
    }

    public void setNomesottoscrittore(String nomesottoscrittore) {
        this.nomesottoscrittore = nomesottoscrittore;
    }

    public String getRuolososttoscrittore() {
        return ruolososttoscrittore;
    }

    public void setRuolososttoscrittore(String ruolososttoscrittore) {
        this.ruolososttoscrittore = ruolososttoscrittore;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.idesamefinale);
        return hash;
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
        final Esamefinale other = (Esamefinale) obj;
        return Objects.equals(this.idesamefinale, other.idesamefinale);
    }
    
    
}
