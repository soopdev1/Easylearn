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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 *
 * @author Raffaele
 */
@Entity
@Table(name = "tipopercorso")
public class Tipologia_Percorso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idtipopercorso")
    Long idtipopercorso;
    
    @Column(name = "nometipologia")
    private String nometipologia;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipocorso")
    private TipoCorso tipocorso;

    @Column(name = "datastart")
    @Temporal(TemporalType.DATE)
    private Date datastart;

    @Column(name = "dataend")
    @Temporal(TemporalType.DATE)
    private Date dataend;

    @Column(name = "maxcorsi")
    private int maxcorsi;
    
    @Column(name = "maxedizioni")
    private int maxedizioni;
    
    @Transient
    private Stati statotipologiapercorso;

    public Stati getStatotipologiapercorso() {
        try {
            DateTime now = new DateTime(DateTimeZone.forID("Europe/Rome")).withMillisOfDay(0);
            DateTime sta1 = new DateTime(this.datastart.getTime());
            DateTime end1 = new DateTime(this.dataend.getTime());
            if ((now.isAfter(sta1) || now.isEqual(sta1)) && (now.isBefore(end1) || now.isEqual(end1))) {
                this.statotipologiapercorso = Stati.ATTIVO;
            } else {
                this.statotipologiapercorso = Stati.INATTIVO;
            }
        } catch (Exception e) {
            this.statotipologiapercorso = Stati.INATTIVO;
        }
        return this.statotipologiapercorso;
    }
    
    @Transient
    private String etichettastato;

    public String getEtichettastato() {
        switch (getStatotipologiapercorso()) {
            case ATTIVO:
                this.etichettastato = "<i class='fa fa-check'></i> Attivo";
                break;
            case INATTIVO:
                this.etichettastato = "<i class='fa fa-lock'></i> Inattivo";
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
    
    public void setStatotipologiapercorso(Stati statotipologiapercorso) {
        this.statotipologiapercorso = statotipologiapercorso;
    }

    public Tipologia_Percorso() {
    }

    public Long getIdtipopercorso() {
        return idtipopercorso;
    }

    public void setIdtipopercorso(Long idtipopercorso) {
        this.idtipopercorso = idtipopercorso;
    }

    public String getNometipologia() {
        return nometipologia;
    }

    public void setNometipologia(String nometipologia) {
        this.nometipologia = nometipologia;
    }

    public TipoCorso getTipocorso() {
        return tipocorso;
    }

    public void setTipocorso(TipoCorso tipocorso) {
        this.tipocorso = tipocorso;
    }

    public Date getDatastart() {
        return datastart;
    }

    public void setDatastart(Date datastart) {
        this.datastart = datastart;
    }

    public Date getDataend() {
        return dataend;
    }

    public void setDataend(Date dataend) {
        this.dataend = dataend;
    }

    public int getMaxcorsi() {
        return maxcorsi;
    }

    public void setMaxcorsi(int maxcorsi) {
        this.maxcorsi = maxcorsi;
    }

    public int getMaxedizioni() {
        return maxedizioni;
    }

    public void setMaxedizioni(int maxedizioni) {
        this.maxedizioni = maxedizioni;
    }

}