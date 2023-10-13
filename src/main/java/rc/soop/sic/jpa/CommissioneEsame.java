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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
})
@Entity
@Table(name = "commissione")
public class CommissioneEsame implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idcommissione")
    private Long idcommissione;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idcorsoavviato")
    private Corsoavviato corsodiriferimento;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "tit1_iddocente")
    private Docente titolare1;
    
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "tit2_iddocente")
    private Docente titolare2;
    
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "sos1_iddocente")
    private Docente sostituto1;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "sos2_iddocente")
    private Docente sostituto2;
    @Column(name = "datainserimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datainserimento;

    @Column(name = "espertosettore")
    private String espertosettore;
    
    @Column(name = "utenteinserimento")
    private String utenteinserimento;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "statocommissione")
    private Stati statocommissione;
        
    public CommissioneEsame() {
    }

    public String getUtenteinserimento() {
        return utenteinserimento;
    }

    public void setUtenteinserimento(String utenteinserimento) {
        this.utenteinserimento = utenteinserimento;
    }
    
    public Corsoavviato getCorsodiriferimento() {
        return corsodiriferimento;
    }

    public void setCorsodiriferimento(Corsoavviato corsodiriferimento) {
        this.corsodiriferimento = corsodiriferimento;
    }


    public Date getDatainserimento() {
        return datainserimento;
    }

    public void setDatainserimento(Date datainserimento) {
        this.datainserimento = datainserimento;
    }

    public Long getIdcommissione() {
        return idcommissione;
    }

    public void setIdcommissione(Long idcommissione) {
        this.idcommissione = idcommissione;
    }

    public Docente getTitolare1() {
        return titolare1;
    }

    public void setTitolare1(Docente titolare1) {
        this.titolare1 = titolare1;
    }

    public Docente getTitolare2() {
        return titolare2;
    }

    public void setTitolare2(Docente titolare2) {
        this.titolare2 = titolare2;
    }

    public Docente getSostituto1() {
        return sostituto1;
    }

    public void setSostituto1(Docente sostituto1) {
        this.sostituto1 = sostituto1;
    }

    public Docente getSostituto2() {
        return sostituto2;
    }

    public void setSostituto2(Docente sostituto2) {
        this.sostituto2 = sostituto2;
    }

    public String getEspertosettore() {
        return espertosettore;
    }

    public void setEspertosettore(String espertosettore) {
        this.espertosettore = espertosettore;
    }

    public Stati getStatocommissione() {
        return statocommissione;
    }

    public void setStatocommissione(Stati statocommissione) {
        this.statocommissione = statocommissione;
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
        CommissioneEsame other = (CommissioneEsame) obj;
        return Objects.equals(idcommissione, other.idcommissione);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.idcommissione);
        return hash;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("{")
                .append("\"").append("idcalendariolezioni").append("\"").append(":").append("\"").append(this.idcommissione).append("\"").append(",")
                .append("}")
                .toString();
    }

}
