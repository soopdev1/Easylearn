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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import javax.persistence.Transient;
import rc.soop.sic.Utils;

/**
 *
 * @author Raffaele
 */
@NamedQueries(value = {
    @NamedQuery(name = "entestage.soggetto", query = "SELECT i FROM EnteStage i WHERE i.soggetto=:soggetto ORDER BY i.RAGIONESOCIALE ASC"),
    @NamedQuery(name = "entestage.soggetto.pi", query = "SELECT i FROM EnteStage i WHERE i.soggetto=:soggetto AND i.PARTITAIVA=:partitaiva ORDER BY i.RAGIONESOCIALE ASC")
})

@Entity
@Table(name = "entestage")
public class EnteStage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "identestage")
    Long identestage;
    @Column(name = "RAGIONESOCIALE")
    private String RAGIONESOCIALE;
    @Column(name = "CODICEATECO")
    private String CODICEATECO;
    @Column(name = "PARTITAIVA")
    private String PARTITAIVA;
    @Column(name = "TELEFONO")
    private String TELEFONO;
    @Column(name = "EMAIL")
    private String EMAIL;
    @Column(name = "PEC")
    private String PEC;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idsoggetto")
    private SoggettoProponente soggetto;

    
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "sedelegale")
    Sede sedelegale;

    @OneToMany(mappedBy = "entestage", fetch = FetchType.EAGER)
    List<Sede> sedistage;

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
    @Column(name = "rap_carica")
    private String rap_carica;

    @Enumerated(EnumType.STRING)
    @Column(name = "statoente")
    private Stati statoente;
    
    @Transient
    private String etichettastato;

    public String getEtichettastato() {
        this.etichettastato = Utils.getEtichettastato(this.statoente);
        return this.etichettastato;
    }

    public void setEtichettastato(String etichettastato) {
        this.etichettastato = etichettastato;
    }
    
    public EnteStage() {
    }

    public Stati getStatoente() {
        return statoente;
    }

    public void setStatoente(Stati statoente) {
        this.statoente = statoente;
    }
    
    public SoggettoProponente getSoggetto() {
        return soggetto;
    }

    public void setSoggetto(SoggettoProponente soggetto) {
        this.soggetto = soggetto;
    }

    public String getRAGIONESOCIALE() {
        return RAGIONESOCIALE;
    }

    public void setRAGIONESOCIALE(String RAGIONESOCIALE) {
        this.RAGIONESOCIALE = RAGIONESOCIALE;
    }

    public String getCODICEATECO() {
        return CODICEATECO;
    }

    public void setCODICEATECO(String CODICEATECO) {
        this.CODICEATECO = CODICEATECO;
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

    public Sede getSedelegale() {
        return sedelegale;
    }

    public void setSedelegale(Sede sedelegale) {
        this.sedelegale = sedelegale;
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

    public String getRap_carica() {
        return rap_carica;
    }

    public void setRap_carica(String rap_carica) {
        this.rap_carica = rap_carica;
    }

    public Long getIdentestage() {
        return identestage;
    }

    public void setIdentestage(Long identestage) {
        this.identestage = identestage;
    }

    public List<Sede> getSedistage() {
        return sedistage;
    }

    public void setSedistage(List<Sede> sedistage) {
        this.sedistage = sedistage;
    }

}
