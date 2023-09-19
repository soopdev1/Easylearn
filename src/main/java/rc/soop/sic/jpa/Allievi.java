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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import rc.soop.sic.Utils;

/**
 *
 * @author Administrator
 */
@NamedQueries(value = {
    @NamedQuery(name = "allievi.corsoavviato", query = "SELECT i FROM Allievi i WHERE i.corsodiriferimento=:corsodiriferimento ORDER BY i.cognome ASC"),
    @NamedQuery(name = "allievi.soggetto", query = "SELECT i FROM Allievi i WHERE i.soggetto=:soggetto AND i.statoallievo<>:inattivo ORDER BY i.cognome ASC"),
    @NamedQuery(name = "allievi.attivi.cf", query = "SELECT i FROM Allievi i WHERE i.codicefiscale=:codicefiscale AND i.statoallievo<>:inattivo"),
    @NamedQuery(name = "allievi.attivi.new", query = "SELECT i FROM Allievi i WHERE i.soggetto=:soggetto AND i.statoallievo=:check")
})
@Entity
@Table(name = "allievi")
public class Allievi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idallievi")
    private Long idallievi;

    @Column(name = "cognome")
    private String cognome;
    @Column(name = "nome")
    private String nome;
    @Column(name = "codicefiscale")
    private String codicefiscale;

    @Column(name = "datanascita")
    @Temporal(TemporalType.DATE)
    private Date datanascita;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idsoggetto")
    private SoggettoProponente soggetto;

    @Enumerated(EnumType.STRING)
    @Column(name = "statoallievo")
    private Stati statoallievo;

    @Column(name = "email")
    private String email;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "numdocid")
    private String numdocid;

    @Column(name = "datadocid")
    @Temporal(TemporalType.DATE)
    private Date datadocid;

    @Column(name = "datainserimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datainserimento;

    @Column(name = "titolostudio")
    private String titolostudio;

    @Column(name = "luogonascita")
    private String luogonascita;

    @Column(name = "catprot", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean catprot;

    @Column(name = "sesso")
    private String sesso;
    
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idcorsoavviato")
    private Corsoavviato corsodiriferimento;

    public Corsoavviato getCorsodiriferimento() {
        return corsodiriferimento;
    }

    public void setCorsodiriferimento(Corsoavviato corsodiriferimento) {
        this.corsodiriferimento = corsodiriferimento;
    }
    
    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getNumdocid() {
        return numdocid;
    }

    public void setNumdocid(String numdocid) {
        this.numdocid = numdocid;
    }

    public Date getDatainserimento() {
        return datainserimento;
    }

    public void setDatainserimento(Date datainserimento) {
        this.datainserimento = datainserimento;
    }

    public Date getDatadocid() {
        return datadocid;
    }

    public void setDatadocid(Date datadocid) {
        this.datadocid = datadocid;
    }

    public String getTitolostudio() {
        return titolostudio;
    }

    public void setTitolostudio(String titolostudio) {
        this.titolostudio = titolostudio;
    }

    public boolean isCatprot() {
        return catprot;
    }

    public void setCatprot(boolean catprot) {
        this.catprot = catprot;
    }

    @Transient
    private String etichettastato;

    public String getEtichettastato() {
        this.etichettastato = Utils.getEtichettastato(this.statoallievo);
        return this.etichettastato;
    }

    public void setEtichettastato(String etichettastato) {
        this.etichettastato = etichettastato;
    }

    public String getCognome() {
        return cognome;
    }

    public String getLuogonascita() {
        return luogonascita;
    }

    public void setLuogonascita(String luogonascita) {
        this.luogonascita = luogonascita;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public SoggettoProponente getSoggetto() {
        return soggetto;
    }

    public void setSoggetto(SoggettoProponente soggetto) {
        this.soggetto = soggetto;
    }

    public Long getIdallievi() {
        return idallievi;
    }

    public void setIdallievi(Long idallievi) {
        this.idallievi = idallievi;
    }

    public Stati getStatoallievo() {
        return statoallievo;
    }

    public void setStatoallievo(Stati statoallievo) {
        this.statoallievo = statoallievo;
    }

    public Date getDatanascita() {
        return datanascita;
    }

    public void setDatanascita(Date datanascita) {
        this.datanascita = datanascita;
    }

}
