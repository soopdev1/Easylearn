/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import static rc.soop.sic.jpa.Stati.ABILITATO;
import static rc.soop.sic.jpa.Stati.CHECK;
import static rc.soop.sic.jpa.Stati.DISABILITATO;

/**
 *
 * @author Raffaele
 */
@Entity
@Table(name = "sede")
public class Sede implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idsede")
    Long idsede;    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sedetipo", referencedColumnName = "idtiposede")
    TipoSede tipo;
    @Column(name = "indirizzo")
    private String indirizzo;
    @Column(name = "cap")
    private String cap;
    @Column(name = "comune")
    private String comune;
    @Column(name = "provincia")
    private String provincia;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idsoggetto")
    SoggettoProponente soggetto;
            
    @Enumerated(EnumType.STRING)
    @Column(name = "statosede")
    private Stati statosede;

        @Transient
    private String etichettastato;

    public String getEtichettastato() {
        switch (this.statosede) {
            case ABILITATO:
                this.etichettastato = "<i class='fa fa-check'></i> Abilitata";
                break;
            case DISABILITATO:
                this.etichettastato = "<i class='fa fa-lock'></i> Disabilitata";
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
    
    
    
    public Sede() {
    }

    public Stati getStatosede() {
        return statosede;
    }

    public void setStatosede(Stati statosede) {
        this.statosede = statosede;
    }

    public SoggettoProponente getSoggetto() {
        return soggetto;
    }

    public void setSoggetto(SoggettoProponente soggetto) {
        this.soggetto = soggetto;
    }

    
    public Long getIdsede() {
        return idsede;
    }

    public void setIdsede(Long idsede) {
        this.idsede = idsede;
    }

    public TipoSede getTipo() {
        return tipo;
    }

    public void setTipo(TipoSede tipo) {
        this.tipo = tipo;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    
    
    
    
}
