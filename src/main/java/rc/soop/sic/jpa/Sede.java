/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
            
    public Sede() {
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
