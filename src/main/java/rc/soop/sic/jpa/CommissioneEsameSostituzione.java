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
    @NamedQuery(name = "commissionesostituzione.commissione", 
            query = "SELECT u FROM CommissioneEsameSostituzione u WHERE u.commissione=:commissione ORDER BY u.datainserimento DESC"),
})
@Entity
@Table(name = "commissionesostituzione")
public class CommissioneEsameSostituzione implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idcommissionesostituzione")
    private Long idcommissionesostituzione;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idcommissione")
    private CommissioneEsame commissione;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "originale")
    private Docente originale;
    
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "sostituto")
    private Docente sostituto;
    
    @Column(name = "datainserimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datainserimento;

    @Column(name = "notasostituzione")
    private String notasostituzione;
    
    @Column(name = "datanotasostituzione")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datanotasostituzione;
    
    @Column(name = "utenteinserimento")
    private String utenteinserimento;
    
    public CommissioneEsameSostituzione() {
    }

    public Long getIdcommissionesostituzione() {
        return idcommissionesostituzione;
    }

    public void setIdcommissionesostituzione(Long idcommissionesostituzione) {
        this.idcommissionesostituzione = idcommissionesostituzione;
    }

    public CommissioneEsame getCommissione() {
        return commissione;
    }

    public void setCommissione(CommissioneEsame commissione) {
        this.commissione = commissione;
    }

    public Docente getOriginale() {
        return originale;
    }

    public void setOriginale(Docente originale) {
        this.originale = originale;
    }

    public Docente getSostituto() {
        return sostituto;
    }

    public void setSostituto(Docente sostituto) {
        this.sostituto = sostituto;
    }

    public String getNotasostituzione() {
        return notasostituzione;
    }

    public void setNotasostituzione(String notasostituzione) {
        this.notasostituzione = notasostituzione;
    }

    public Date getDatanotasostituzione() {
        return datanotasostituzione;
    }

    public void setDatanotasostituzione(Date datanotasostituzione) {
        this.datanotasostituzione = datanotasostituzione;
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
        CommissioneEsameSostituzione other = (CommissioneEsameSostituzione) obj;
        return Objects.equals(idcommissionesostituzione, other.idcommissionesostituzione);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.idcommissionesostituzione);
        return hash;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("{")
                .append("\"").append("idcommissionesostituzione").append("\"").append(":").append("\"").append(this.idcommissionesostituzione).append("\"").append(",")
                .append("}")
                .toString();
    }

}
