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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "repertorio")
public class Repertorio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idrepertorio")
    Long idrepertorio;

    @Column(name = "edizione")
    String edizione;

    @Column(name = "denominazione")
    String denominazione;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "repertorio_professioni",
            joinColumns = @JoinColumn(name = "idrepertorio"),
            inverseJoinColumns = @JoinColumn(name = "codiceprofessioni"))
    private List<Professioni> professioni;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "repertorio_ateco",
            joinColumns = @JoinColumn(name = "idrepertorio"),
            inverseJoinColumns = @JoinColumn(name = "codiceateco"))
    private List<Ateco> ateco;

    @Column(name = "areaprofessionale")
    private String areaprofessionale;

    @Column(name = "sottoareaprofessionale")
    private String sottoareaprofessionale;

    @Column(name = "descrizione", columnDefinition = "LONGTEXT")
    private String descrizione;

    @ManyToOne
    @JoinColumn(name = "codicelivellocertificazione")
    private Livello_Certificazione livelloeqf;

    @ManyToOne
    @JoinColumn(name = "codicecertificazione")
    private Certificazione qualificarilasciata;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "repertorio_competenze",
            joinColumns = @JoinColumn(name = "idrepertorio"),
            inverseJoinColumns = @JoinColumn(name = "idattivita"))
    private List<Attivita> processolavoro;
    
    
    @Column(name = "durataprovafinale")
    private String durataprovafinale;

    @Column(name = "provaingresso")
    private String provaingresso;

    @OneToMany
    @JoinColumn(name = "idschedaattivita")
    private List<Scheda_Attivita> attivitadestinatariassociate;

    
    //
    @Column(name = "normativarif")
    private String normativarif;

    @Column(name = "tipologia")
    private String tipologia;

    @Column(name = "tipologiacategoria")
    private String tipologiacategoria;

    
    
    public Repertorio() {
    }

    public Long getIdrepertorio() {
        return idrepertorio;
    }

    public void setIdrepertorio(Long idrepertorio) {
        this.idrepertorio = idrepertorio;
    }

    public String getEdizione() {
        return edizione;
    }

    public void setEdizione(String edizione) {
        this.edizione = edizione;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public String getAreaprofessionale() {
        return areaprofessionale;
    }

    public void setAreaprofessionale(String areaprofessionale) {
        this.areaprofessionale = areaprofessionale;
    }

    public String getSottoareaprofessionale() {
        return sottoareaprofessionale;
    }

    public void setSottoareaprofessionale(String sottoareaprofessionale) {
        this.sottoareaprofessionale = sottoareaprofessionale;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getTipologiacategoria() {
        return tipologiacategoria;
    }

    public void setTipologiacategoria(String tipologiacategoria) {
        this.tipologiacategoria = tipologiacategoria;
    }

    public String getNormativarif() {
        return normativarif;
    }

    public void setNormativarif(String normativarif) {
        this.normativarif = normativarif;
    }

    public List<Professioni> getProfessioni() {
        List<Professioni> pro_t = new ArrayList<>();
        pro_t.addAll(professioni);
        return pro_t;
    }

    public void setProfessioni(List<Professioni> professioni) {
        this.professioni = professioni;
    }

    public List<Ateco> getAteco() {
        List<Ateco> at_t = new ArrayList<>();
        at_t.addAll(ateco);
        return at_t;
    }

    public void setAteco(List<Ateco> ateco) {
        this.ateco = ateco;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Livello_Certificazione getLivelloeqf() {
        return livelloeqf;
    }

    public void setLivelloeqf(Livello_Certificazione livelloeqf) {
        this.livelloeqf = livelloeqf;
    }

    public Certificazione getQualificarilasciata() {
        return qualificarilasciata;
    }

    public void setQualificarilasciata(Certificazione qualificarilasciata) {
        this.qualificarilasciata = qualificarilasciata;
    }

    public List<Attivita> getProcessolavoro() {
        List<Attivita> at_t = new ArrayList<>();
        at_t.addAll(processolavoro);
        return at_t;
    }

    public void setProcessolavoro(List<Attivita> processolavoro) {
        this.processolavoro = processolavoro;
    }

    public String getDurataprovafinale() {
        return durataprovafinale;
    }

    public void setDurataprovafinale(String durataprovafinale) {
        this.durataprovafinale = durataprovafinale;
    }

    public String getProvaingresso() {
        return provaingresso;
    }

    public void setProvaingresso(String provaingresso) {
        this.provaingresso = provaingresso;
    }

    public List<Scheda_Attivita> getAttivitadestinatariassociate() {
        List<Scheda_Attivita> sch_1 = new ArrayList<>();
        sch_1.addAll(attivitadestinatariassociate);
        return sch_1;
    }

    public void setAttivitadestinatariassociate(List<Scheda_Attivita> attivitadestinatariassociate) {
        this.attivitadestinatariassociate = attivitadestinatariassociate;
    }
    
    

}
