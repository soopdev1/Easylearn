/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@NamedQueries(value = {
    @NamedQuery(name = "corsoavviataltropers.elencobyutente", query = "SELECT u FROM CorsoAvviato_AltroPersonale u WHERE u.altropersonale=:altropersonale"),
    @NamedQuery(name = "corsoavviataltropers.elencobycorso", query = "SELECT u FROM CorsoAvviato_AltroPersonale u WHERE u.corsoavviato=:corsoavviato")
})
@Entity
@Table(name = "corsoavviato_altropersonale")
public class CorsoAvviato_AltroPersonale implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @Column(name = "idcorsoavviatoaltropersonale")
    private CorsoAvviato_AltroPersonaleId idcorsoavviatoaltropersonale = new CorsoAvviato_AltroPersonaleId();

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @MapsId("idaltropersonale")
    private Altropersonale altropersonale;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @MapsId("idcorsoavviato")
    private Corsoavviato corsoavviato;

    public CorsoAvviato_AltroPersonale() {
    }

    public Corsoavviato getCorsoavviato() {
        return corsoavviato;
    }

    public void setCorsoavviato(Corsoavviato corsoavviato) {
        this.corsoavviato = corsoavviato;
    }

    public CorsoAvviato_AltroPersonaleId getIdcorsoavviatoaltropersonale() {
        return idcorsoavviatoaltropersonale;
    }

    public void setIdcorsoavviatoaltropersonale(CorsoAvviato_AltroPersonaleId idcorsoavviatoaltropersonale) {
        this.idcorsoavviatoaltropersonale = idcorsoavviatoaltropersonale;
    }

    public Altropersonale getAltropersonale() {
        return altropersonale;
    }

    public void setAltropersonale(Altropersonale altropersonale) {
        this.altropersonale = altropersonale;
    }

}
