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
    @NamedQuery(name = "presenzelezioni.allievo_corso", 
            query = "SELECT u FROM Presenze_Lezioni_Allievi u WHERE u.presenzelezioni.corsodiriferimento=:corsodiriferimento ORDER BY u.presenzelezioni.datarealelezione,u.orainizio"),
    @NamedQuery(name = "presenzelezioni.giornata", 
            query = "SELECT u FROM Presenze_Lezioni_Allievi u WHERE u.presenzelezioni=:presenzelezioni ORDER BY u.orainizio,u.allievo.cognome"),
    @NamedQuery(name = "presenzelezioni.allievo", 
            query = "SELECT u FROM Presenze_Lezioni_Allievi u WHERE u.allievo=:allievo ORDER BY u.presenzelezioni.datarealelezione,u.orainizio"),
    @NamedQuery(name = "presenzelezioni.allievo_giornata", 
            query = "SELECT u FROM Presenze_Lezioni_Allievi u WHERE u.presenzelezioni=:presenzelezioni AND u.allievo=:allievo"),
})
@Entity
@Table(name = "presenzelezioniallievi")
public class Presenze_Lezioni_Allievi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idpresenzelezioniallievi")
    private Long idpresenzelezioniallievi;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idpresenzelezioni")
    private Presenze_Lezioni presenzelezioni;
    
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idallievi")
    private Allievi allievo;
    
    @Column(name = "orainizio")
    private String orainizio;

    @Column(name = "orafine")
    private String orafine;

    @Column(name = "durata")
    private Long durata;

    @Column(name = "datarealelezione")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datainserimento;

    public Presenze_Lezioni_Allievi() {
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

    public Long getIdpresenzelezioniallievi() {
        return idpresenzelezioniallievi;
    }

    public void setIdpresenzelezioniallievi(Long idpresenzelezioniallievi) {
        this.idpresenzelezioniallievi = idpresenzelezioniallievi;
    }

    public Presenze_Lezioni getPresenzelezioni() {
        return presenzelezioni;
    }

    public void setPresenzelezioni(Presenze_Lezioni presenzelezioni) {
        this.presenzelezioni = presenzelezioni;
    }

    public Long getDurata() {
        return durata;
    }

    public void setDurata(Long durata) {
        this.durata = durata;
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
        Presenze_Lezioni_Allievi other = (Presenze_Lezioni_Allievi) obj;
        return Objects.equals(idpresenzelezioniallievi, other.idpresenzelezioniallievi);
    }

    public Allievi getAllievo() {
        return allievo;
    }

    public void setAllievo(Allievi allievo) {
        this.allievo = allievo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.idpresenzelezioniallievi);
        return hash;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("{")
                .append("\"").append("idcalendariolezioni").append("\"").append(":").append("\"").append(this.idpresenzelezioniallievi).append("\"").append(",")
                .append("}")
                .toString();
    }

}
