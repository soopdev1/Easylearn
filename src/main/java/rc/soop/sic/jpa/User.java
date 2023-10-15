/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

/**
 *
 * @author raf
 */
@NamedQueries(value = {
    @NamedQuery(name = "user.UsernamePwd", query = "SELECT u FROM User u WHERE u.username=:username AND u.password=:password"),
    @NamedQuery(name = "user.byUsername", query = "SELECT u FROM User u WHERE u.username=:username"),
    @NamedQuery(name = "user.byEmail", query = "SELECT u FROM User u WHERE u.email=:email"),
    @NamedQuery(name = "user.bySA", query = "SELECT u FROM User u WHERE u.soggetto=:sa")
})
@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "stato")
    private int stato = 2;
    @Column(name = "tipo")
    private int tipo;
    @Column(name = "creationdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationdate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "idsoggetto")
    private SoggettoProponente soggetto;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "idpresidente")
    private PresidenteCommissione presidente;

    public static String formatUser(String ing) {
        try {
            switch (ing) {
                case "1":
                    return "ADMIN";
                case "2":
                    return "ENTE FORMAZIONE";
                case "3":
                    return "PRESIDENTE COMMISSIONE";
                default:
                    return ing;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ing;
    }

    public static String formatIconUser(String ing) {
        try {
            switch (ing) {
                case "1":
                    return "User-Executive-Green-icon.png";
                case "2":
                case "3":
                    return "User-Administrator-Blue-icon.png";
                default:
                    return ing;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ing;
    }

    public User() {
    }

    public PresidenteCommissione getPresidente() {
        return presidente;
    }

    public void setPresidente(PresidenteCommissione presidente) {
        this.presidente = presidente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStato() {
        return stato;
    }

    public void setStato(int stato) {
        this.stato = stato;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public SoggettoProponente getSoggetto() {
        return soggetto;
    }

    public void setSoggetto(SoggettoProponente soggetto) {
        this.soggetto = soggetto;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, JSON_STYLE);
    }

}
