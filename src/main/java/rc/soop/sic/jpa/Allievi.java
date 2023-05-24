/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import static rc.soop.sic.jpa.Stati.ABILITATO;

/**
 *
 * @author Administrator
 */
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

    @Enumerated(EnumType.STRING)
    @Column(name = "statoallievo")
    private Stati statoallievo;

    @Column(name = "email")
    private String email;
    
    @Column(name = "telefono")
    private String telefono;

    @Transient
    private String etichettastato;

    public String getEtichettastato() {
        switch (this.statoallievo) {
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
    
    

    
}
