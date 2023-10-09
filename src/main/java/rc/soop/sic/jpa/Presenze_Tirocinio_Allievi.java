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
    @NamedQuery(name = "presenzetirocinioallievi.tirociniostage",
            query = "SELECT u FROM Presenze_Tirocinio_Allievi u WHERE u.tirociniostage=:tirociniostage ORDER BY u.datapresenza,u.orainizio")
})
@Entity
@Table(name = "presenzetirocinioallievi")
public class Presenze_Tirocinio_Allievi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idpresenzetirocinioallievi")
    private Long idpresenzetirocinioallievi;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idtirociniostage")
    private TirocinioStage tirociniostage;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idallievi")
    private Allievi allievi;

    @Column(name = "datapresenza")
    @Temporal(TemporalType.DATE)
    private Date datapresenza;

    @Column(name = "orainizio")
    private String orainizio;

    @Column(name = "orafine")
    private String orafine;

    @Column(name = "ore")
    private double ore;

    @Column(name = "datainserimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datainserimento;

    @Column(name = "utenteinserimento")
    private String utenteinserimento;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "statopresenza")
    private CorsoStato statolezione;
    
    public Presenze_Tirocinio_Allievi() {
    }

    public CorsoStato getStatolezione() {
        return statolezione;
    }

    public void setStatolezione(CorsoStato statolezione) {
        this.statolezione = statolezione;
    }
    
    public Date getDatapresenza() {
        return datapresenza;
    }

    public void setDatapresenza(Date datapresenza) {
        this.datapresenza = datapresenza;
    }

    public String getUtenteinserimento() {
        return utenteinserimento;
    }

    public void setUtenteinserimento(String utenteinserimento) {
        this.utenteinserimento = utenteinserimento;
    }

    public Long getIdpresenzetirocinioallievi() {
        return idpresenzetirocinioallievi;
    }

    public void setIdpresenzetirocinioallievi(Long idpresenzetirocinioallievi) {
        this.idpresenzetirocinioallievi = idpresenzetirocinioallievi;
    }

    public TirocinioStage getTirociniostage() {
        return tirociniostage;
    }

    public void setTirociniostage(TirocinioStage tirociniostage) {
        this.tirociniostage = tirociniostage;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getOrainizio() {
        return orainizio;
    }

    public double getOre() {
        return ore;
    }

    public void setOre(double ore) {
        this.ore = ore;
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
        Presenze_Tirocinio_Allievi other = (Presenze_Tirocinio_Allievi) obj;
        return Objects.equals(idpresenzetirocinioallievi, other.idpresenzetirocinioallievi);
    }

    public Allievi getAllievi() {
        return allievi;
    }

    public void setAllievi(Allievi allievi) {
        this.allievi = allievi;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.idpresenzetirocinioallievi);
        return hash;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("{")
                .append("\"").append("idcalendariolezioni").append("\"").append(":").append("\"").append(this.idpresenzetirocinioallievi).append("\"").append(",")
                .append("}")
                .toString();
    }

}
