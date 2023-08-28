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
public class CorsoAvviato_DocentiId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long iddocente;
    private Long idcorsoavviato;

    public CorsoAvviato_DocentiId() {

    }

    public CorsoAvviato_DocentiId(Long iddocente, Long idcorsoavviato) {
        super();
        this.iddocente = iddocente;
        this.idcorsoavviato = idcorsoavviato;
    }

    public Long getIddocente() {
        return iddocente;
    }

    public void setIddocente(Long iddocente) {
        this.iddocente = iddocente;
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
                + ((iddocente == null) ? 0 : iddocente.hashCode());
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
        CorsoAvviato_DocentiId other = (CorsoAvviato_DocentiId) obj;
        return Objects.equals(getIddocente(), other.getIddocente()) && Objects.equals(getIdcorsoavviato(), other.getIdcorsoavviato());
    }
}
