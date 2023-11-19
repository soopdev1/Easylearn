/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import java.util.Date;
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
    @NamedQuery(name = "attestatiqualifica.corsoavviato", query = "SELECT i FROM AttestatiQualifica i WHERE i.corsodiriferimento=:corsodiriferimento ORDER BY i.datagenerazione ASC"),})
@Entity
@Table(name = "attestatiqualifica")
public class AttestatiQualifica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "numeroattestato")
    private Long numeroattestato;

    @Column(name = "datagenerazione")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datagenerazione;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "esame")
    private Esamefinale esame;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "allievointerno")
    private Allievi allievointerno;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "allievoesterno")
    private AllieviEsterni allievoesterno;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idcorsoavviato")
    private Corsoavviato corsodiriferimento;

    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    public AttestatiQualifica() {
    }
    
    @Column(name = "filename")
    private String filename;

    public Long getNumeroattestato() {
        return numeroattestato;
    }

    public void setNumeroattestato(Long numeroattestato) {
        this.numeroattestato = numeroattestato;
    }

    public Date getDatagenerazione() {
        return datagenerazione;
    }

    public void setDatagenerazione(Date datagenerazione) {
        this.datagenerazione = datagenerazione;
    }

    public Esamefinale getEsame() {
        return esame;
    }

    public void setEsame(Esamefinale esame) {
        this.esame = esame;
    }

    public Allievi getAllievointerno() {
        return allievointerno;
    }

    public void setAllievointerno(Allievi allievointerno) {
        this.allievointerno = allievointerno;
    }

    public AllieviEsterni getAllievoesterno() {
        return allievoesterno;
    }

    public void setAllievoesterno(AllieviEsterni allievoesterno) {
        this.allievoesterno = allievoesterno;
    }

    public Corsoavviato getCorsodiriferimento() {
        return corsodiriferimento;
    }

    public void setCorsodiriferimento(Corsoavviato corsodiriferimento) {
        this.corsodiriferimento = corsodiriferimento;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }



}
