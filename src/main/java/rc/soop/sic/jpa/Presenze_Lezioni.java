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
    @NamedQuery(name = "presenzelezioni.corso", query = "SELECT u FROM Calendario_Lezioni u WHERE u.corsodiriferimento=:corsodiriferimento ORDER BY u.datalezione,u.orainizio"),
    @NamedQuery(name = "presenzelezioni.lezioni", query = "SELECT u FROM Calendario_Lezioni u WHERE u.corsodiriferimento=:corsodiriferimento ORDER BY u.datalezione,u.orainizio")
})
@Entity
@Table(name = "presenzelezioni")
public class Presenze_Lezioni implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idpresenzelezioni")
    private Long idpresenzelezioni;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idcorsoavviato")
    private Corsoavviato corsodiriferimento;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idcalendariolezioni")
    private Calendario_Lezioni calendariolezioni;

    @Column(name = "datarealelezione")
    @Temporal(TemporalType.DATE)
    private Date datarealelezione;
    
    @Column(name = "orainizio")
    private String orainizio;
    
    @Column(name = "orafine")
    private String orafine;
    
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "iddocente")
    private Docente docente;
    
    @Column(name = "datarealelezione")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datainserimento;

    @Column(name = "utenteinserimento")
    private String utenteinserimento;
    
    
    public Presenze_Lezioni() {
    }

    
    public Corsoavviato getCorsodiriferimento() {
        return corsodiriferimento;
    }

    public void setCorsodiriferimento(Corsoavviato corsodiriferimento) {
        this.corsodiriferimento = corsodiriferimento;
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
        Presenze_Lezioni other = (Presenze_Lezioni) obj;
        return Objects.equals(idpresenzelezioni, other.idpresenzelezioni);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.idpresenzelezioni);
        return hash;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("{")
                .append("\"").append("idcalendariolezioni").append("\"").append(":").append("\"").append(this.idpresenzelezioni).append("\"").append(",")
                .append("}")
                .toString();
    }

}
