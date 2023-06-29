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
    @NamedQuery(name = "cert.name", query = "SELECT u FROM Certificazione u WHERE u.nome=:nome"),
})
@Entity
@Table(name = "certification")
public class Certificazione implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "codicecertificazione")
    private String codicecertificazione;
    @Column(name = "nome")
    private String nome;

    public Certificazione() {
    }

    public String getCodicecertificazione() {
        return codicecertificazione;
    }

    public void setCodicecertificazione(String codicecertificazione) {
        this.codicecertificazione = codicecertificazione;
    }

    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
    
}
