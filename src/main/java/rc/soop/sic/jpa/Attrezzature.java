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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "attrezzature")
public class Attrezzature implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idattrezzature")
    private Long idattrezzature;

    @Lob
    @Column(name = "descrizione")
    private String descrizione;

    @Enumerated(EnumType.STRING)
    @Column(name = "modalita")
    private Modacquisizione modalita;

    @Column(name = "quantita")
    private int quantita;

    @Column(name = "datainizio")
    @Temporal(TemporalType.DATE)
    private Date datainizio;
    
    
    @Column(name = "registroinventario")
    private String registroinventario;

    @Column(name = "numeroinventario")
    private String numeroinventario;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idcorso")
    private Corso corso;
    
    public Attrezzature() {
    }

    public Date getDatainizio() {
        return datainizio;
    }

    public void setDatainizio(Date datainizio) {
        this.datainizio = datainizio;
    }

    public Corso getCorso() {
        return corso;
    }

    public void setCorso(Corso corso) {
        this.corso = corso;
    }

    public Long getIdattrezzature() {
        return idattrezzature;
    }

    public void setIdattrezzature(Long idattrezzature) {
        this.idattrezzature = idattrezzature;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Modacquisizione getModalita() {
        return modalita;
    }

    public void setModalita(Modacquisizione modalita) {
        this.modalita = modalita;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public String getRegistroinventario() {
        return registroinventario;
    }

    public void setRegistroinventario(String registroinventario) {
        this.registroinventario = registroinventario;
    }

    public String getNumeroinventario() {
        return numeroinventario;
    }

    public void setNumeroinventario(String numeroinventario) {
        this.numeroinventario = numeroinventario;
    }

    
}
