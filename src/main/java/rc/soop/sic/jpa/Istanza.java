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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Raffaele
 */
@NamedQueries(value = {
    @NamedQuery(name = "istanza.codice", query = "SELECT i FROM Istanza i WHERE i.soggetto=:soggetto AND i.codice=:codice"),
    @NamedQuery(name = "istanza.onlycodice", query = "SELECT i FROM Istanza i WHERE i.codice=:codice"),
    @NamedQuery(name = "istanza.soggettowaiting", query = "SELECT i FROM Istanza i WHERE i.soggetto=:soggetto AND i.statocorso.codicestatocorso IN('01','02','07','08','09') ORDER BY i.idistanza DESC"),
    @NamedQuery(name = "istanza.listaaccettate", query = "SELECT i FROM Istanza i WHERE i.soggetto=:soggetto AND i.statocorso.codicestatocorso IN('08') ORDER BY i.idistanza DESC"),
    @NamedQuery(name = "istanza.dagestire", query = "SELECT i FROM Istanza i WHERE i.statocorso.codicestatocorso IN('07') ORDER BY i.idistanza DESC"),
    @NamedQuery(name = "istanza.gestite", query = "SELECT i FROM Istanza i WHERE i.statocorso.codicestatocorso IN('08','09') ORDER BY i.idistanza DESC")
})
@Entity
@Table(name = "istanza")
public class Istanza implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idistanza")
    Long idistanza;

    @Column(name = "codice")
    private String codice;

    @ManyToOne
    @JoinColumn(name = "idsoggetto")
    private SoggettoProponente soggetto;

    @ManyToOne
    @JoinColumn(name = "stato")
    private CorsoStato statocorso;

    @Column(name = "quantitarichiesta")
    private int quantitarichiesta;

    @Column(name = "protocollosoggetto")
    private String protocollosoggetto;
    @Column(name = "protocollosoggettodata")
    private String protocollosoggettodata;

    @Column(name = "pathfirmato")
    private String pathfirmato;
    @Column(name = "datainvio")
    private String datainvio;

    
    @Column(name = "decreto")
    private String decreto;
    @Column(name = "datagestione")
    private String datagestione;
    
    
    
    public Long getIdistanza() {
        return idistanza;
    }

    public void setIdistanza(Long idistanza) {
        this.idistanza = idistanza;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public SoggettoProponente getSoggetto() {
        return soggetto;
    }

    public void setSoggetto(SoggettoProponente soggetto) {
        this.soggetto = soggetto;
    }

    public CorsoStato getStatocorso() {
        return statocorso;
    }

    public void setStatocorso(CorsoStato statocorso) {
        this.statocorso = statocorso;
    }

    public int getQuantitarichiesta() {
        return quantitarichiesta;
    }

    public void setQuantitarichiesta(int quantitarichiesta) {
        this.quantitarichiesta = quantitarichiesta;
    }

    public String getProtocollosoggetto() {
        return protocollosoggetto;
    }

    public void setProtocollosoggetto(String protocollosoggetto) {
        this.protocollosoggetto = protocollosoggetto;
    }

    public String getProtocollosoggettodata() {
        return protocollosoggettodata;
    }

    public void setProtocollosoggettodata(String protocollosoggettodata) {
        this.protocollosoggettodata = protocollosoggettodata;
    }

    public String getPathfirmato() {
        return pathfirmato;
    }

    public void setPathfirmato(String pathfirmato) {
        this.pathfirmato = pathfirmato;
    }

    public String getDatainvio() {
        return datainvio;
    }

    public void setDatainvio(String datainvio) {
        this.datainvio = datainvio;
    }

    public String getDecreto() {
        return decreto;
    }

    public void setDecreto(String decreto) {
        this.decreto = decreto;
    }

    public String getDatagestione() {
        return datagestione;
    }

    public void setDatagestione(String datagestione) {
        this.datagestione = datagestione;
    }
        
}
