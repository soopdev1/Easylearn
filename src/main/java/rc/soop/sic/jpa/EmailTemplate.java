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
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "emailtemplate")
public class EmailTemplate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(name = "oggettomail")
    private String oggettomail;
    
    @Lob
    @Column(name = "hmtlmail")
    private String htmlmail;
    
    @Column(name = "info")
    private String info;
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOggettomail() {
        return oggettomail;
    }

    public void setOggettomail(String oggettomail) {
        this.oggettomail = oggettomail;
    }

    public String getHtmlmail() {
        return htmlmail;
    }

    public void setHtmlmail(String htmlmail) {
        this.htmlmail = htmlmail;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmailTemplate)) {
            return false;
        }
        EmailTemplate other = (EmailTemplate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rc.soop.sic.jpa.EmailTemplate[ id=" + id + " ]";
    }
    
}
