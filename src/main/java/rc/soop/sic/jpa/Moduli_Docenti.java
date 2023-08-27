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
    @NamedQuery(name = "md.elencobydocente", query = "SELECT u FROM Moduli_Docenti u WHERE u.docente=:docente"),
    @NamedQuery(name = "md.elencobycalendarioformativo", query = "SELECT u FROM Moduli_Docenti u WHERE u.moduloformativo=:moduloformativo"),
    @NamedQuery(name = "md.elenco", query = "SELECT u FROM Moduli_Docenti u WHERE u.docente=:docente AND u.moduloformativo=:moduloformativo"),
    @NamedQuery(name = "md.docenti", query = "SELECT DISTINCT u.docente FROM Moduli_Docenti u WHERE u.moduloformativo.corsodiriferimento=:corso")
})
@Entity
@Table(name = "moduli_docenti")
public class Moduli_Docenti implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @Column(name = "idmodulidocenti")
    private Moduli_DocentiId idmodulidocenti = new Moduli_DocentiId();

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @MapsId("iddocente")
    private Docente docente;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @MapsId("idcalendarioformativo")
    private Calendario_Formativo moduloformativo;

    @Column(name = "orepreviste")
    private double orepreviste;

    public Moduli_Docenti() {
    }

    public Moduli_DocentiId getIdmodulidocenti() {
        return idmodulidocenti;
    }

    public void setIdmodulidocenti(Moduli_DocentiId idmodulidocenti) {
        this.idmodulidocenti = idmodulidocenti;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Calendario_Formativo getModuloformativo() {
        return moduloformativo;
    }

    public void setModuloformativo(Calendario_Formativo moduloformativo) {
        this.moduloformativo = moduloformativo;
    }

    public double getOrepreviste() {
        return orepreviste;
    }

    public void setOrepreviste(double orepreviste) {
        this.orepreviste = orepreviste;
    }

}
