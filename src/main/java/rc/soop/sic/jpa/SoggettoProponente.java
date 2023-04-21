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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Raffaele
 */
@Entity
@Table(name = "soggetto")
public class SoggettoProponente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idsoggetto")
    Long idsoggetto;
    @Column(name = "RAGIONESOCIALE")
    private String RAGIONESOCIALE;
    @Column(name = "DATADICOSTITUZIONE")
    private String DATADICOSTITUZIONE;
    @Column(name = "CODICEFISCALE")
    private String CODICEFISCALE;
    @Column(name = "PARTITAIVA")
    private String PARTITAIVA;
    @Column(name = "TELEFONO")
    private String TELEFONO;
    @Column(name = "EMAIL")
    private String EMAIL;
    @Column(name = "PEC")
    private String PEC;
    @Column(name = "CIR")
    private String CIR;
    
    @Column(name = "TIPOLOGIA")
    private String TIPOLOGIA;
    
    @Column(name = "DDGNUMERO")
    private String DDGNUMERO;
    @Column(name = "DDGDATA")
    private String DDGDATA;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sedelegale")
    Sede sedelegale;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sedeoperativa")
    Sede sedeoperativa;

    @OneToMany(mappedBy = "soggetto", fetch = FetchType.LAZY)
    List<Sede> sediformazione;

    @Column(name = "rap_cognome")
    private String rap_cognome;
    @Column(name = "rap_nome")
    private String rap_nome;
    @Column(name = "rap_cf")
    private String rap_cf;
    @Column(name = "rap_luogonascita")
    private String rap_luogonascita;
    @Column(name = "rap_datanascita")
    private String rap_datanascita;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rap_sede")
    Sede rap_sede;
    @Column(name = "rap_carica")
    private String rap_carica;

    public SoggettoProponente() {
    }

    public Long getIdsoggetto() {
        return idsoggetto;
    }

    public void setIdsoggetto(Long idsoggetto) {
        this.idsoggetto = idsoggetto;
    }

    public String getRAGIONESOCIALE() {
        return RAGIONESOCIALE;
    }

    public void setRAGIONESOCIALE(String RAGIONESOCIALE) {
        this.RAGIONESOCIALE = RAGIONESOCIALE;
    }

    public String getDATADICOSTITUZIONE() {
        return DATADICOSTITUZIONE;
    }

    public void setDATADICOSTITUZIONE(String DATADICOSTITUZIONE) {
        this.DATADICOSTITUZIONE = DATADICOSTITUZIONE;
    }

    public String getCODICEFISCALE() {
        return CODICEFISCALE;
    }

    public void setCODICEFISCALE(String CODICEFISCALE) {
        this.CODICEFISCALE = CODICEFISCALE;
    }

    public String getPARTITAIVA() {
        return PARTITAIVA;
    }

    public void setPARTITAIVA(String PARTITAIVA) {
        this.PARTITAIVA = PARTITAIVA;
    }

    public String getTELEFONO() {
        return TELEFONO;
    }

    public void setTELEFONO(String TELEFONO) {
        this.TELEFONO = TELEFONO;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getPEC() {
        return PEC;
    }

    public void setPEC(String PEC) {
        this.PEC = PEC;
    }

    public String getCIR() {
        return CIR;
    }

    public void setCIR(String CIR) {
        this.CIR = CIR;
    }

    public String getDDGNUMERO() {
        return DDGNUMERO;
    }

    public void setDDGNUMERO(String DDGNUMERO) {
        this.DDGNUMERO = DDGNUMERO;
    }

    public String getDDGDATA() {
        return DDGDATA;
    }

    public void setDDGDATA(String DDGDATA) {
        this.DDGDATA = DDGDATA;
    }

    public Sede getSedelegale() {
        return sedelegale;
    }

    public void setSedelegale(Sede sedelegale) {
        this.sedelegale = sedelegale;
    }

    public Sede getSedeoperativa() {
        return sedeoperativa;
    }

    public void setSedeoperativa(Sede sedeoperativa) {
        this.sedeoperativa = sedeoperativa;
    }

    public List<Sede> getSediformazione() {
        return sediformazione;
    }

    public void setSediformazione(List<Sede> sediformazione) {
        this.sediformazione = sediformazione;
    }

    public String getRap_cognome() {
        return rap_cognome;
    }

    public void setRap_cognome(String rap_cognome) {
        this.rap_cognome = rap_cognome;
    }

    public String getRap_nome() {
        return rap_nome;
    }

    public void setRap_nome(String rap_nome) {
        this.rap_nome = rap_nome;
    }

    public String getRap_cf() {
        return rap_cf;
    }

    public void setRap_cf(String rap_cf) {
        this.rap_cf = rap_cf;
    }

    public String getRap_luogonascita() {
        return rap_luogonascita;
    }

    public void setRap_luogonascita(String rap_luogonascita) {
        this.rap_luogonascita = rap_luogonascita;
    }

    public String getRap_datanascita() {
        return rap_datanascita;
    }

    public void setRap_datanascita(String rap_datanascita) {
        this.rap_datanascita = rap_datanascita;
    }

    public Sede getRap_sede() {
        return rap_sede;
    }

    public void setRap_sede(Sede rap_sede) {
        this.rap_sede = rap_sede;
    }

    public String getRap_carica() {
        return rap_carica;
    }

    public void setRap_carica(String rap_carica) {
        this.rap_carica = rap_carica;
    }

    public String getTIPOLOGIA() {
        return TIPOLOGIA;
    }

    public void setTIPOLOGIA(String TIPOLOGIA) {
        this.TIPOLOGIA = TIPOLOGIA;
    }

}