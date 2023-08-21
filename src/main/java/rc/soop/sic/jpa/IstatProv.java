/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "istatprov")
public class IstatProv implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idistatprov")
    private String idistatprov;

    @Column(name = "nome", columnDefinition = "TINYTEXT")
    private String nome;

    public IstatProv() {
    }

    public String getIdistatprov() {
        return idistatprov;
    }

    public void setIdistatprov(String idistatprov) {
        this.idistatprov = idistatprov;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        IstatProv other = (IstatProv) obj;
        return Objects.equals(idistatprov, other.idistatprov);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.idistatprov);
        return hash;
    }
}
