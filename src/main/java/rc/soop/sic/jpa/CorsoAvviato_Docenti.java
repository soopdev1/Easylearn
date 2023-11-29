/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@NamedQueries(value = {
    @NamedQuery(name = "corsoavviatodocenti.elencobydocente", query = "SELECT u FROM CorsoAvviato_Docenti u WHERE u.docente=:docente"),
    @NamedQuery(name = "corsoavviatodocenti.elencobycorso", query = "SELECT u FROM CorsoAvviato_Docenti u WHERE u.corsoavviato=:corsoavviato")
})
@Entity
@Table(name = "corsoavviato_docenti")
public class CorsoAvviato_Docenti implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @Column(name = "idcorsoavviatodocenti")
    private CorsoAvviato_DocentiId idcorsoavviatodocenti = new CorsoAvviato_DocentiId();

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @MapsId("iddocente")
    private Docente docente;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @MapsId("idcorsoavviato")
    private Corsoavviato corsoavviato;

    public CorsoAvviato_Docenti() {
    }

    

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public CorsoAvviato_DocentiId getIdcorsoavviatodocenti() {
        return idcorsoavviatodocenti;
    }

    public void setIdcorsoavviatodocenti(CorsoAvviato_DocentiId idcorsoavviatodocenti) {
        this.idcorsoavviatodocenti = idcorsoavviatodocenti;
    }

    public Corsoavviato getCorsoavviato() {
        return corsoavviato;
    }

    public void setCorsoavviato(Corsoavviato corsoavviato) {
        this.corsoavviato = corsoavviato;
    }

}
