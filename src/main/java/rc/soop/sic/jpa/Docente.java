/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import static rc.soop.sic.jpa.Stati.ABILITATO;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "docente")
public class Docente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "iddocente")
    private Long iddocente;

    @Column(name = "cognome")
    private String cognome;
    @Column(name = "nome")
    private String nome;
    @Column(name = "codicefiscale")
    private String codicefiscale;

    @Column(name = "datanascita")
    @Temporal(TemporalType.DATE)
    private Date datanascita;

    @Column(name = "titolostudio", columnDefinition = "LONGTEXT")
    private String titolostudio;

    @Column(name = "profiloprof", columnDefinition = "LONGTEXT")
    private String profiloprof;

    @Column(name = "tipologia")
    private String tipologia;

    @Column(name = "anniesperienzaprof", nullable = false, columnDefinition = "integer default 0")
    private int anniesperienzaprof;

    @Column(name = "anniesperienzadid", nullable = false, columnDefinition = "integer default 0")
    private int anniesperienzadid;

    @Enumerated(EnumType.STRING)
    @Column(name = "statodocente")
    private Stati statodocente;

    @Transient
    private String etichettastato;

    public String getEtichettastato() {
        switch (this.statodocente) {
            case ABILITATO:
                this.etichettastato = "<i class='fa fa-check'></i> Abilitato";
                break;
            case DISABILITATO:
                this.etichettastato = "<i class='fa fa-lock'></i> Disabilitato";
                break;
            case CHECK:
                this.etichettastato = "<i class='fa fa-warning'></i> Da Verificare";
                break;
            default:
                this.etichettastato = "";
                break;
        }
        return etichettastato;
    }

    public void setEtichettastato(String etichettastato) {
        this.etichettastato = etichettastato;
    }

    public Long getIddocente() {
        return iddocente;
    }

    public void setIddocente(Long iddocente) {
        this.iddocente = iddocente;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodicefiscale() {
        return codicefiscale;
    }

    public void setCodicefiscale(String codicefiscale) {
        this.codicefiscale = codicefiscale;
    }

    public Date getDatanascita() {
        return datanascita;
    }

    public void setDatanascita(Date datanascita) {
        this.datanascita = datanascita;
    }

    public String getTitolostudio() {
        return titolostudio;
    }

    public void setTitolostudio(String titolostudio) {
        this.titolostudio = titolostudio;
    }

    public Stati getStatodocente() {
        return statodocente;
    }

    public void setStatodocente(Stati statodocente) {
        this.statodocente = statodocente;
    }

    public int getAnniesperienzaprof() {
        return anniesperienzaprof;
    }

    public void setAnniesperienzaprof(int anniesperienzaprof) {
        this.anniesperienzaprof = anniesperienzaprof;
    }

    public int getAnniesperienzadid() {
        return anniesperienzadid;
    }

    public void setAnniesperienzadid(int anniesperienzadid) {
        this.anniesperienzadid = anniesperienzadid;
    }

    public String getProfiloprof() {
        return profiloprof;
    }

    public void setProfiloprof(String profiloprof) {
        this.profiloprof = profiloprof;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

}
