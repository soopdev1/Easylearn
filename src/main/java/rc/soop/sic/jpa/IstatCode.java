/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Administrator
 */
@NamedQueries(value = {
    @NamedQuery(name = "istat.codice", query = "SELECT i FROM IstatCode i WHERE i.codicecf=:codicecf"),
})
@Entity
@Table(name = "istatcode")
public class IstatCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idistatcode")
    private String idistatcode;

    @Column(name = "nome", columnDefinition = "TINYTEXT")
    private String nome;

    @Column(name = "codicecf")
    private String codicecf;

    @Column(name = "datainizio")
    @Temporal(TemporalType.DATE)
    private Date datainizio;

    public IstatCode() {
    }

    public String getIdistatcode() {
        return idistatcode;
    }

    public void setIdistatcode(String idistatcode) {
        this.idistatcode = idistatcode;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodicecf() {
        return codicecf;
    }

    public void setCodicecf(String codicecf) {
        this.codicecf = codicecf;
    }

    public Date getDatainizio() {
        return datainizio;
    }

    public void setDatainizio(Date datainizio) {
        this.datainizio = datainizio;
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
        IstatCode other = (IstatCode) obj;
        return Objects.equals(idistatcode, other.idistatcode);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.idistatcode);
        return hash;
    }
}
