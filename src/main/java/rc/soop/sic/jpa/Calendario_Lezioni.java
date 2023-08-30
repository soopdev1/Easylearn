/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @NamedQuery(name = "calendariolezioni.corso", query = "SELECT u FROM Calendario_Lezioni u WHERE u.corsodiriferimento=:corsodiriferimento ORDER BY u.datalezione,u.orainizio")
})
@Entity
@Table(name = "calendariolezioni")
public class Calendario_Lezioni implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idcalendariolezioni")
    private Long idcalendariolezioni;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idcorsoavviato")
    private Corsoavviato corsodiriferimento;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idcalendarioformativo")
    private Calendario_Formativo calendarioformativo;

    @Column(name = "datalezione")
    @Temporal(TemporalType.DATE)
    private Date datalezione;
    
    @Column(name = "orainizio")
    private String orainizio;
    
    @Column(name = "orafine")
    private String orafine;
    
    @Column(name = "ore")
    private double ore;
    
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "iddocente")
    private Docente docente;
    
    @Column(name = "datainserimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datainserimento;
    
    public Calendario_Lezioni() {
    }

    public double getOre() {
        return ore;
    }

    public void setOre(double ore) {
        this.ore = ore;
    }
    
    public Long getIdcalendariolezioni() {
        return idcalendariolezioni;
    }

    public void setIdcalendariolezioni(Long idcalendariolezioni) {
        this.idcalendariolezioni = idcalendariolezioni;
    }

    public Corsoavviato getCorsodiriferimento() {
        return corsodiriferimento;
    }

    public void setCorsodiriferimento(Corsoavviato corsodiriferimento) {
        this.corsodiriferimento = corsodiriferimento;
    }

    public Calendario_Formativo getCalendarioformativo() {
        return calendarioformativo;
    }

    public void setCalendarioformativo(Calendario_Formativo calendarioformativo) {
        this.calendarioformativo = calendarioformativo;
    }

    public Date getDatalezione() {
        return datalezione;
    }

    public void setDatalezione(Date datalezione) {
        this.datalezione = datalezione;
    }

    public String getOrainizio() {
        return orainizio;
    }

    public void setOrainizio(String orainizio) {
        this.orainizio = orainizio;
    }

    public String getOrafine() {
        return orafine;
    }

    public void setOrafine(String orafine) {
        this.orafine = orafine;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Date getDatainserimento() {
        return datainserimento;
    }

    public void setDatainserimento(Date datainserimento) {
        this.datainserimento = datainserimento;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Calendario_Lezioni other = (Calendario_Lezioni) obj;
        return Objects.equals(idcalendariolezioni, other.idcalendariolezioni);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.idcalendariolezioni);
        return hash;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("{")
                .append("\"").append("idcalendariolezioni").append("\"").append(":").append("\"").append(this.idcalendariolezioni).append("\"").append(",")
                .append("}")
                .toString();
    }

}
