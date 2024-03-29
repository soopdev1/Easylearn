/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Transient;
import static rc.soop.sic.Utils.roundDoubleandFormat;
import static rc.soop.sic.jpa.Stati.ABILITATO;

/**
 *
 * @author Administrator
 */
@NamedQueries(value = {
    @NamedQuery(name = "altrop.all", query = "SELECT c FROM Altropersonale c ORDER BY c.cognome")
})
@Entity
@Table(name = "altropersonale")
public class Altropersonale implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idaltropersonale")
    private Long idaltropersonale;

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

    @Transient
    private List<Moduli_Docenti> elencomoduli;

    public String formatElencomoduli() {

        if (this.elencomoduli != null) {
            String view = "";
            for (Moduli_Docenti md : this.elencomoduli) {
                view = view + md.getModuloformativo().getCodicemodulo() + " (" + roundDoubleandFormat(md.getOrepreviste(), 1) + ")<br>";
            }
            return view;
        }

        return "";
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "statoaltropersonale")
    private Stati statoaltropersonale;

    @Transient
    private String etichettastato;

    public String getEtichettastato() {
        switch (this.statoaltropersonale) {
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

    public List<Moduli_Docenti> getElencomoduli() {
        return elencomoduli;
    }

    public void setElencomoduli(List<Moduli_Docenti> elencomoduli) {
        this.elencomoduli = elencomoduli;
    }

    public Long getIdaltropersonale() {
        return idaltropersonale;
    }

    public void setIdaltropersonale(Long idaltropersonale) {
        this.idaltropersonale = idaltropersonale;
    }

    public Stati getStatoaltropersonale() {
        return statoaltropersonale;
    }

    public void setStatoaltropersonale(Stati statoaltropersonale) {
        this.statoaltropersonale = statoaltropersonale;
    }
    
}