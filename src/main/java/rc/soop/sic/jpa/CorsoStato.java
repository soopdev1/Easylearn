/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Raffaele
 */
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
    
    public CorsoStato() {
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
    
}
