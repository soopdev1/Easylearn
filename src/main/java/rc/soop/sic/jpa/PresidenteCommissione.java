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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Administrator
 */
@NamedQueries(value = {
    @NamedQuery(name = "presidentecommissione.stato", query = "SELECT c FROM PresidenteCommissione c WHERE c.statopresidente=:statopresidente"),
})
@Entity
@Table(name = "presidentecommissione")
public class PresidenteCommissione implements Serializable {

//     SESSO - NATOA – PROVINCIA – DOMICILIO
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idpresidente")
    private Long idpresidente;
    @Column(name = "cognome")
    private String cognome;
    @Column(name = "nome")
    private String nome;
    @Column(name = "codicefiscale")
    private String codicefiscale;
    @Column(name = "datanascita")
    @Temporal(TemporalType.DATE)
    private Date datanascita;
    @Column(name = "titolostudio")
    private String titolostudio;
    @Column(name = "qualifica")
    private String qualifica;
    @Column(name = "assessorato")
    private String assessorato;
    @Column(name = "dipartimento")
    private String dipartimento;
    @Column(name = "areaservizio")
    private String areaservizio;
    @Column(name = "mail")
    private String mail;
    @Column(name = "telefono")
    private String telefono;

    @Column(name = "attonomina")
    private String attonomina;
    @Column(name = "datanomina")
    @Temporal(TemporalType.DATE)
    private Date datanomina;
    @Column(name = "datarevoca")
    @Temporal(TemporalType.DATE)
    private Date datarevoca;
    
    @Column(name = "datainserimento")
    @Temporal(TemporalType.DATE)
    private Date datainserimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "statopresidente")
    private Stati statopresidente;

    public PresidenteCommissione() {
    }

    public Date getDatainserimento() {
        return datainserimento;
    }

    public void setDatainserimento(Date datainserimento) {
        this.datainserimento = datainserimento;
    }

    public String getAttonomina() {
        return attonomina;
    }

    public void setAttonomina(String attonomina) {
        this.attonomina = attonomina;
    }

    public Date getDatanomina() {
        return datanomina;
    }

    public void setDatanomina(Date datanomina) {
        this.datanomina = datanomina;
    }

    public Date getDatarevoca() {
        return datarevoca;
    }

    public void setDatarevoca(Date datarevoca) {
        this.datarevoca = datarevoca;
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

    public Long getIdpresidente() {
        return idpresidente;
    }

    public void setIdpresidente(Long idpresidente) {
        this.idpresidente = idpresidente;
    }

    public String getQualifica() {
        return qualifica;
    }

    public void setQualifica(String qualifica) {
        this.qualifica = qualifica;
    }

    public String getAssessorato() {
        return assessorato;
    }

    public void setAssessorato(String assessorato) {
        this.assessorato = assessorato;
    }

    public String getDipartimento() {
        return dipartimento;
    }

    public void setDipartimento(String dipartimento) {
        this.dipartimento = dipartimento;
    }

    public String getAreaservizio() {
        return areaservizio;
    }

    public void setAreaservizio(String areaservizio) {
        this.areaservizio = areaservizio;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Stati getStatopresidente() {
        return statopresidente;
    }

    public void setStatopresidente(Stati statopresidente) {
        this.statopresidente = statopresidente;
    }

}
