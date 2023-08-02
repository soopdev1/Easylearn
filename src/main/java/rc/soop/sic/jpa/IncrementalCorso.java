/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
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

/**
 *
 * @author Administrator
 */
@NamedQueries(value = {
    @NamedQuery(name = "inc.corso", query = "SELECT c FROM IncrementalCorso c WHERE c.corso=:corso"),
})
@Entity
@Table(name = "incrementalcorso")
public class IncrementalCorso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idincrementalcorso")
    private Long idincrementalcorso;
    
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idcorso")
    private Corso corso;

    public IncrementalCorso() {
    }

    public Long getIdincrementalcorso() {
        return idincrementalcorso;
    }

    public void setIdincrementalcorso(Long idincrementalcorso) {
        this.idincrementalcorso = idincrementalcorso;
    }

    public Corso getCorso() {
        return corso;
    }

    public void setCorso(Corso corso) {
        this.corso = corso;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idincrementalcorso != null ? idincrementalcorso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IncrementalCorso)) {
            return false;
        }
        IncrementalCorso other = (IncrementalCorso) object;
        if ((this.idincrementalcorso == null && other.idincrementalcorso != null) || (this.idincrementalcorso != null && !this.idincrementalcorso.equals(other.idincrementalcorso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rc.soop.sic.jpa.IncrementalCorso[ idincrementalcorso=" + idincrementalcorso + " ]";
    }
    
}
