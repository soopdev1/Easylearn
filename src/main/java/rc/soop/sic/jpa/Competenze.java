/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
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
@Table(name = "competenze")
public class Competenze implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idcompetenze")
    private Long idcompetenze;

    @Column(name = "numero")
    private int numero;

    @Column(name = "descrizione", columnDefinition = "LONGTEXT")
    private String descrizione;

    @Column(name = "abilita", columnDefinition = "LONGTEXT")
    private String abilita;

    @Column(name = "abilita_strings", columnDefinition = "LONGTEXT")
    @Convert(converter = StringToListConverter.class)
    private List<String> abilita_strings;

    @Column(name = "conoscenze", columnDefinition = "LONGTEXT")
    private String conoscenze;

    @Column(name = "conoscenze_strings", columnDefinition = "LONGTEXT")
    @Convert(converter = StringToListConverter.class)
    private List<String> conoscenze_strings;

    public Competenze() {
    }

    public Long getIdcompetenze() {
        return idcompetenze;
    }

    public void setIdcompetenze(Long idcompetenze) {
        this.idcompetenze = idcompetenze;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getAbilita() {
        return abilita;
    }

    public void setAbilita(String abilita) {
        this.abilita = abilita;
    }

    public List<String> getAbilita_strings() {
        return abilita_strings;
    }

    public void setAbilita_strings(List<String> abilita_strings) {
        this.abilita_strings = abilita_strings;
    }

    public String getConoscenze() {
        return conoscenze;
    }

    public void setConoscenze(String conoscenze) {
        this.conoscenze = conoscenze;
    }

    public List<String> getConoscenze_strings() {
        return conoscenze_strings;
    }

    public void setConoscenze_strings(List<String> conoscenze_strings) {
        this.conoscenze_strings = conoscenze_strings;
    }

}
