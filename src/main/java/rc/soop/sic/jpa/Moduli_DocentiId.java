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
public class Moduli_DocentiId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long iddocente;
    private Long idcalendarioformativo;

    public Moduli_DocentiId() {

    }

    public Moduli_DocentiId(Long iddocente, Long idcalendarioformativo) {
        super();
        this.iddocente = iddocente;
        this.idcalendarioformativo = idcalendarioformativo;
    }

    public Long getIddocente() {
        return iddocente;
    }

    public void setIddocente(Long iddocente) {
        this.iddocente = iddocente;
    }

    public Long getIdcalendarioformativo() {
        return idcalendarioformativo;
    }

    public void setIdcalendarioformativo(Long idcalendarioformativo) {
        this.idcalendarioformativo = idcalendarioformativo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((iddocente == null) ? 0 : iddocente.hashCode());
        result = prime * result
                + ((idcalendarioformativo == null) ? 0 : idcalendarioformativo.hashCode());
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
        Moduli_DocentiId other = (Moduli_DocentiId) obj;
        return Objects.equals(getIddocente(), other.getIddocente()) && Objects.equals(getIdcalendarioformativo(), other.getIdcalendarioformativo());
    }
}
