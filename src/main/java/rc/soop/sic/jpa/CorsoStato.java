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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Raffaele
 */
@NamedQueries(value = {
    @NamedQuery(name = "stati.elenco", query = "SELECT c FROM CorsoStato c WHERE c.tipostato=:tipostato ORDER BY c.ordine")
})
@Entity
@Table(name = "corso_stato")
public class CorsoStato implements Serializable {

    @Id
    @Column(name = "codicestatocorso")
    private String codicestatocorso;

    @Column(name = "nome")
    private String nome;

    @Column(name = "ordine")
    private int ordine;

    @Column(name = "htmlicon")
    private String htmlicon;

    @Column(name = "htmldescr")
    private String htmldescr;

    @Column(name = "ok", columnDefinition = "tinyint(1) default 1")
    private boolean ok;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipostato")
    private TipoStato tipostato;

    public CorsoStato() {
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
    
    public String getHtmldescr() {
        return htmldescr;
    }

    public void setHtmldescr(String htmldescr) {
        this.htmldescr = htmldescr;
    }

    public String getCodicestatocorso() {
        return codicestatocorso;
    }

    public void setCodicestatocorso(String codicestatocorso) {
        this.codicestatocorso = codicestatocorso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getOrdine() {
        return ordine;
    }

    public void setOrdine(int ordine) {
        this.ordine = ordine;
    }

    public String getHtmlicon() {
        return htmlicon;
    }

    public void setHtmlicon(String htmlicon) {
        this.htmlicon = htmlicon;
    }

    public TipoStato getTipostato() {
        return tipostato;
    }

    public void setTipostato(TipoStato tipostato) {
        this.tipostato = tipostato;
    }

}
