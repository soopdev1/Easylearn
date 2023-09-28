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
    @NamedQuery(name = "tirociniostage.allievo", query = "SELECT u FROM TirocinioStage u WHERE u.allievi=:allievi ORDER BY u.datainizio")
})
@Entity
@Table(name = "tirociniostage")
public class TirocinioStage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idtirociniostage")
    private Long idtirociniostage;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idallievi")
    private Allievi allievi;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "identestage")
    private EnteStage entestage;

    @Column(name = "datainizio")
    @Temporal(TemporalType.DATE)
    private Date datainizio;

    @Column(name = "datafine")
    @Temporal(TemporalType.DATE)
    private Date datafine;

    @Column(name = "orepreviste")
    private int orepreviste;

    @Column(name = "datainserimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datainserimento;

    @Column(name = "utenteinserimento")
    private String utenteinserimento;

    public TirocinioStage() {
    }

    public String getUtenteinserimento() {
        return utenteinserimento;
    }

    public void setUtenteinserimento(String utenteinserimento) {
        this.utenteinserimento = utenteinserimento;
    }

    public Date getDatainserimento() {
        return datainserimento;
    }

    public void setDatainserimento(Date datainserimento) {
        this.datainserimento = datainserimento;
    }

    public Long getIdtirociniostage() {
        return idtirociniostage;
    }

    public void setIdtirociniostage(Long idtirociniostage) {
        this.idtirociniostage = idtirociniostage;
    }

    public Allievi getAllievi() {
        return allievi;
    }

    public void setAllievi(Allievi allievi) {
        this.allievi = allievi;
    }

    public EnteStage getEntestage() {
        return entestage;
    }

    public void setEntestage(EnteStage entestage) {
        this.entestage = entestage;
    }

    public Date getDatainizio() {
        return datainizio;
    }

    public void setDatainizio(Date datainizio) {
        this.datainizio = datainizio;
    }

    public Date getDatafine() {
        return datafine;
    }

    public void setDatafine(Date datafine) {
        this.datafine = datafine;
    }

    public int getOrepreviste() {
        return orepreviste;
    }

    public void setOrepreviste(int orepreviste) {
        this.orepreviste = orepreviste;
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
        TirocinioStage other = (TirocinioStage) obj;
        return Objects.equals(idtirociniostage, other.idtirociniostage);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.idtirociniostage);
        return hash;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("{")
                .append("\"").append("idcalendariolezioni").append("\"").append(":").append("\"").append(this.idtirociniostage).append("\"").append(",")
                .append("}")
                .toString();
    }

}
