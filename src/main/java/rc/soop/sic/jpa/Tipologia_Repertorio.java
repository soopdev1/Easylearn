/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "tipologiarepertorio")
public class Tipologia_Repertorio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idtipologiarepertorio")
    Long idtipologiarepertorio;
    @Column(name = "nome")
    private String nome;

    public Tipologia_Repertorio() {
    }

    public Long getIdtipologiarepertorio() {
        return idtipologiarepertorio;
    }

    public void setIdtipologiarepertorio(Long idtipologiarepertorio) {
        this.idtipologiarepertorio = idtipologiarepertorio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    
    
}
