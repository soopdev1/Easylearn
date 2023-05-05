/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Raffaele
 */
@Entity
@Table(name = "tipopercorso")
public class Tipologia_Percorso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idtipopercorso")
    Long idtipopercorso;
    @Column(name = "nometipologia")
    private String nometipologia;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipocorso")
    private TipoCorso tipocorso;
    
    public Tipologia_Percorso() {
    }

    public Long getIdtipopercorso() {
        return idtipopercorso;
    }

    public void setIdtipopercorso(Long idtipopercorso) {
        this.idtipopercorso = idtipopercorso;
    }

    public String getNometipologia() {
        return nometipologia;
    }

    public void setNometipologia(String nometipologia) {
        this.nometipologia = nometipologia;
    }

    public TipoCorso getTipocorso() {
        return tipocorso;
    }

    public void setTipocorso(TipoCorso tipocorso) {
        this.tipocorso = tipocorso;
    }

    

}
