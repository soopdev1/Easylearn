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
@Table(name = "certificationlevel")
public class Livello_Certificazione implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "codicelivellocertificazione")
    private String codicelivellocertificazione;
    @Column(name = "nome")
    private String nome;

    public Livello_Certificazione() {
    }

    public String getCodicelivellocertificazione() {
        return codicelivellocertificazione;
    }

    public void setCodicelivellocertificazione(String codicelivellocertificazione) {
        this.codicelivellocertificazione = codicelivellocertificazione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
    
}
