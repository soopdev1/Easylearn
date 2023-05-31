/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Raffaele
 */
@NamedQueries(value = {
    @NamedQuery(name = "lingua.order", query = "SELECT u FROM Lingua u ORDER BY u.ordine ASC"),
})
@Entity
@Table(name = "lingua")
public class Lingua implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "codicelingua")
    private String codicelingua;
    
    @Column(name = "nome")
    private String nome;
    
    @Column(name = "ordine")
    private int ordine;

    public Lingua() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodicelingua() {
        return codicelingua;
    }

    public void setCodicelingua(String codicelingua) {
        this.codicelingua = codicelingua;
    }

    public int getOrdine() {
        return ordine;
    }

    public void setOrdine(int ordine) {
        this.ordine = ordine;
    }
    
}