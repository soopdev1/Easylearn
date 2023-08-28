/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Administrator
 */
@Embeddable
public class CorsoAvviato_AltroPersonaleId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idaltropersonale;
    private Long idcorsoavviato;

    public CorsoAvviato_AltroPersonaleId() {

    }

    public CorsoAvviato_AltroPersonaleId(Long idaltropersonale, Long idcorsoavviato) {
        super();
        this.idaltropersonale = idaltropersonale;
        this.idcorsoavviato = idcorsoavviato;
    }

    public Long getIdaltropersonale() {
        return idaltropersonale;
    }

    public void setIdaltropersonale(Long idaltropersonale) {
        this.idaltropersonale = idaltropersonale;
    }

    public Long getIdcorsoavviato() {
        return idcorsoavviato;
    }

    public void setIdcorsoavviato(Long idcorsoavviato) {
        this.idcorsoavviato = idcorsoavviato;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idaltropersonale == null) ? 0 : idaltropersonale.hashCode());
        result = prime * result
                + ((idcorsoavviato == null) ? 0 : idcorsoavviato.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CorsoAvviato_AltroPersonaleId other = (CorsoAvviato_AltroPersonaleId) obj;
        return Objects.equals(getIdaltropersonale(), other.getIdaltropersonale()) && Objects.equals(getIdcorsoavviato(), other.getIdcorsoavviato());
    }
}
