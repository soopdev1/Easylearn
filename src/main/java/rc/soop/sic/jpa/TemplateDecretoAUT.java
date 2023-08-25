/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Raffaele
 */
@NamedQueries(value = {
    @NamedQuery(name = "template.decreto.aut", query = "SELECT i FROM TemplateDecretoAUT i ORDER BY i.DATAINSERIMENTO DESC"),
})
@Entity
@Table(name = "template_decretoaut")
public class TemplateDecretoAUT implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idtemplatedecretoaut")
    Long idtemplatedecretoaut;

    @Column(name = "nomeautore")
    private String nomeautore;

    @Column(name = "nomeservizio")
    private String NOMESERVIZIO;

    @Column(name = "funzionariocarica")
    private String FUNZCARICA;

    @Column(name = "funzionarionome")
    private String FUNZNOME;

    @Column(name = "dirigentecarica")
    private String DIRIGCARICA;

    @Column(name = "dirigentenome")
    private String DIRIGNOME;

    @Column(name = "visto1", columnDefinition = "LONGTEXT")
    private String VISTO1;

    @Column(name = "datainserimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date DATAINSERIMENTO;
    
    public TemplateDecretoAUT() {
    }

    public Date getDATAINSERIMENTO() {
        return DATAINSERIMENTO;
    }

    public void setDATAINSERIMENTO(Date DATAINSERIMENTO) {
        this.DATAINSERIMENTO = DATAINSERIMENTO;
    }
    
    public Long getIdtemplatedecretoaut() {
        return idtemplatedecretoaut;
    }

    public void setIdtemplatedecretoaut(Long idtemplatedecretoaut) {
        this.idtemplatedecretoaut = idtemplatedecretoaut;
    }

    public String getNomeautore() {
        return nomeautore;
    }

    public void setNomeautore(String nomeautore) {
        this.nomeautore = nomeautore;
    }

    public String getNOMESERVIZIO() {
        return NOMESERVIZIO;
    }

    public void setNOMESERVIZIO(String NOMESERVIZIO) {
        this.NOMESERVIZIO = NOMESERVIZIO;
    }

    public String getFUNZCARICA() {
        return FUNZCARICA;
    }

    public void setFUNZCARICA(String FUNZCARICA) {
        this.FUNZCARICA = FUNZCARICA;
    }

    public String getFUNZNOME() {
        return FUNZNOME;
    }

    public void setFUNZNOME(String FUNZNOME) {
        this.FUNZNOME = FUNZNOME;
    }

    public String getDIRIGCARICA() {
        return DIRIGCARICA;
    }

    public void setDIRIGCARICA(String DIRIGCARICA) {
        this.DIRIGCARICA = DIRIGCARICA;
    }

    public String getDIRIGNOME() {
        return DIRIGNOME;
    }

    public void setDIRIGNOME(String DIRIGNOME) {
        this.DIRIGNOME = DIRIGNOME;
    }

    public String getVISTO1() {
        return VISTO1;
    }

    public void setVISTO1(String VISTO1) {
        this.VISTO1 = VISTO1;
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
        TemplateDecretoAUT other = (TemplateDecretoAUT) obj;
        return Objects.equals(idtemplatedecretoaut, other.idtemplatedecretoaut);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.idtemplatedecretoaut);
        return hash;
    }
}
