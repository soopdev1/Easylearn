/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
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
    @NamedQuery(name = "allegati.istanza", query = "SELECT c FROM Allegati c WHERE c.istanza=:istanza"),
    @NamedQuery(name = "allegati.istanza.ok", query = "SELECT c FROM Allegati c WHERE c.istanza=:istanza AND c.stato.codicestatocorso<>'32'")
})
@Entity
@Table(name = "allegati")
public class Allegati implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idallegati")
    private Long idallegati;

    @Column(name = "codiceallegati")
    private String codiceallegati;

    @Column(name = "descrizione", columnDefinition = "LONGTEXT")
    private String descrizione;
    
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "errore", columnDefinition = "LONGTEXT")
    private String errore;

    @ManyToOne
    @JoinColumn(name = "idistanza")
    private Istanza istanza;

    @ManyToOne
    @JoinColumn(name = "idcorso")
    private Corso corso;

    @ManyToOne
    @JoinColumn(name = "idcorsoavviato")
    private Corsoavviato corsoavviato;

    @ManyToOne
    @JoinColumn(name = "iddocente")
    private Docente docente;

    @ManyToOne
    @JoinColumn(name = "idallievi")
    private Allievi allievi;

    @ManyToOne
    @JoinColumn(name = "codicestatocorso")
    private CorsoStato stato;

    @Column(name = "datacaricamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datacaricamento;

    @Column(name = "utentecaricamento")
    private String utentecaricamento;
    
    @Column(name = "mimetype")
    private String mimetype;

    public Long getIdallegati() {
        return idallegati;
    }

    public void setIdallegati(Long idallegati) {
        this.idallegati = idallegati;
    }

    public String getCodiceallegati() {
        return codiceallegati;
    }

    public void setCodiceallegati(String codiceallegati) {
        this.codiceallegati = codiceallegati;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getErrore() {
        return errore;
    }

    public void setErrore(String errore) {
        this.errore = errore;
    }

    public Istanza getIstanza() {
        return istanza;
    }

    public void setIstanza(Istanza istanza) {
        this.istanza = istanza;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public Corso getCorso() {
        return corso;
    }

    public void setCorso(Corso corso) {
        this.corso = corso;
    }

    public Corsoavviato getCorsoavviato() {
        return corsoavviato;
    }

    public void setCorsoavviato(Corsoavviato corsoavviato) {
        this.corsoavviato = corsoavviato;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Allievi getAllievi() {
        return allievi;
    }

    public void setAllievi(Allievi allievi) {
        this.allievi = allievi;
    }

    public CorsoStato getStato() {
        return stato;
    }

    public void setStato(CorsoStato stato) {
        this.stato = stato;
    }

    public Date getDatacaricamento() {
        return datacaricamento;
    }

    public void setDatacaricamento(Date datacaricamento) {
        this.datacaricamento = datacaricamento;
    }

    public String getUtentecaricamento() {
        return utentecaricamento;
    }

    public void setUtentecaricamento(String utentecaricamento) {
        this.utentecaricamento = utentecaricamento;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idallegati != null ? idallegati.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Allegati)) {
            return false;
        }
        Allegati other = (Allegati) object;
        if ((this.idallegati == null && other.idallegati != null) || (this.idallegati != null && !this.idallegati.equals(other.idallegati))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rc.soop.sic.jpa.Allegati[ id=" + idallegati + " ]";
    }

}
