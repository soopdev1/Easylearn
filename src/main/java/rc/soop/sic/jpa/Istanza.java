/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Raffaele
 */
@NamedQueries(value = {
    @NamedQuery(name = "istanza.codice", query = "SELECT i FROM Istanza i WHERE i.soggetto=:soggetto AND i.codiceistanza=:codice"),
    @NamedQuery(name = "istanza.onlycodice", query = "SELECT i FROM Istanza i WHERE i.codiceistanza=:codice"),
    @NamedQuery(name = "istanza.soggetto", query = "SELECT i FROM Istanza i WHERE i.soggetto=:soggetto ORDER BY i.idistanza DESC"),
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

    @Column(name = "codiceistanza")
    private String codiceistanza;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "idsoggetto")
    private SoggettoProponente soggetto;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "tipologiapercorso")
    private Tipologia_Percorso tipologiapercorso;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "stato")
    private CorsoStato statocorso;

    @Column(name = "quantitarichiesta")
    private int quantitarichiesta;

    @Column(name = "protocollosoggetto")
    private String protocollosoggetto;
    @Column(name = "protocollosoggettodata")
    private String protocollosoggettodata;

    @Column(name = "protocolloreg")
    private String protocolloreg;
    @Column(name = "protocolloregdata")
    private String protocolloregdata;

    @Column(name = "pathfirmato")
    private String pathfirmato;
    @Column(name = "datainvio")
    private String datainvio;

    @Column(name = "decreto")
    private String decreto;
    @Column(name = "datagestione")
    private String datagestione;

    @Transient
    private String datacreazione;

    public Long getIdistanza() {
        return idistanza;
    }

    public void setIdistanza(Long idistanza) {
        this.idistanza = idistanza;
    }

    public String getCodiceistanza() {
        return codiceistanza;
    }

    public void setCodiceistanza(String codiceistanza) {
        this.codiceistanza = codiceistanza;
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

    public Tipologia_Percorso getTipologiapercorso() {
        return tipologiapercorso;
    }

    public void setTipologiapercorso(Tipologia_Percorso tipologiapercorso) {
        this.tipologiapercorso = tipologiapercorso;
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

    public String getProtocolloreg() {
        return protocolloreg;
    }

    public void setProtocolloreg(String protocolloreg) {
        this.protocolloreg = protocolloreg;
    }

    public String getProtocolloregdata() {
        return protocolloregdata;
    }

    public void setProtocolloregdata(String protocolloregdata) {
        this.protocolloregdata = protocolloregdata;
    }

    public String getDatacreazione() {
        if (this.codiceistanza != null && !this.codiceistanza.equals("")) {
            this.datacreazione = StringUtils.substring(this.codiceistanza, 4, 6) + "/" + StringUtils.substring(this.codiceistanza, 2, 4) + "/20" + StringUtils.substring(this.codiceistanza, 0, 2);
        } else {
            this.datacreazione = "NON PRESENTE";
        }
        return this.datacreazione;
    }

    public void setDatacreazione(String datacreazione) {
        this.datacreazione = datacreazione;
    }

}
