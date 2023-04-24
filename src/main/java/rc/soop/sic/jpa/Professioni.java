/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "professioni")
public class Professioni implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "codiceprofessioni")
    private String codiceprofessioni;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descrizione", columnDefinition = "LONGTEXT")
    private String descrizione;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "repertorio_professioni",
            joinColumns = @JoinColumn(name = "codiceprofessioni"),
            inverseJoinColumns = @JoinColumn(name = "idrepertorio"))
    List<Repertorio> repertorio;

    public Professioni() {
    }

    public String getCodiceProfessioni() {
        return codiceprofessioni;
    }

    public void setCodiceProfessioni(String codiceprofessioni) {
        this.codiceprofessioni = codiceprofessioni;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public List<Repertorio> getRepertorio() {
        List<Repertorio> rep_t = new ArrayList<>();
        rep_t.addAll(repertorio);
        return rep_t;
    }

    public void setRepertorio(List<Repertorio> repertorio) {
        this.repertorio = repertorio;
    }

}
