/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Administrator
 */
@NamedQueries(value = {
    @NamedQuery(name = "calendarioformativo.corso", query = "SELECT u FROM Calendario_Formativo u WHERE u.corsodiriferimento=:corsodiriferimento ORDER BY u.idcalendarioformativo"),
    @NamedQuery(name = "calendarioformativo.corsosolomoduli", query = "SELECT u FROM Calendario_Formativo u WHERE u.corsodiriferimento=:corsodiriferimento AND u.tipomodulo='MODULOFORMATIVO' ORDER BY u.idcalendarioformativo"),
    @NamedQuery(name = "calendarioformativo.pianificare", query = "SELECT u FROM Calendario_Formativo u WHERE u.corsodiriferimento=:corsodiriferimento AND (u.tipomodulo='MODULOFORMATIVO' OR u.tipomodulo='BASE') ORDER BY u.idcalendarioformativo")
})
@Entity
@Table(name = "calendarioformativo")
public class Calendario_Formativo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idcalendarioformativo")
    private Long idcalendarioformativo;

    @Column(name = "codicemodulo")
    private String codicemodulo;

    @Column(name = "tipomodulo")
    private String tipomodulo;

    @Column(name = "nomemodulo", columnDefinition = "LONGTEXT")
    private String nomemodulo;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idcorso")
    private Corso corsodiriferimento;

    @Column(name = "oreteoricaaula")
    private double ore_teorica_aula;

    @Column(name = "oreteoricael")
    private double ore_teorica_el;

    @Column(name = "oretecnicaoperat")
    private double ore_tecnica_operativa;

    @Column(name = "oreaula")
    private double ore_aula;

    @Column(name = "ore")
    private double ore;

    @Transient
    private double oreresidue;

    @Transient
    private double oreresiduefad;
    
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "idcompetenze")
    private Competenze_Trasversali competenzetrasversali;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "moduli_competenze",
            joinColumns = @JoinColumn(name = "idcalendarioformativo"),
            inverseJoinColumns = @JoinColumn(name = "idcompetenze"))
    List<Competenze> elencocompetenze;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "moduli_abilita",
            joinColumns = @JoinColumn(name = "idcalendarioformativo"),
            inverseJoinColumns = @JoinColumn(name = "idabilita"))
    List<Abilita> elencoabilita;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "moduli_conoscenze",
            joinColumns = @JoinColumn(name = "idcalendarioformativo"),
            inverseJoinColumns = @JoinColumn(name = "idconoscenze"))
    List<Conoscenze> elencoconoscenze;

    @Column(name = "ctcodicelingua")
    private String ctcodicelingua;

    @Column(name = "ctdescrizione", columnDefinition = "LONGTEXT")
    private String ctdescrizione;

    public String exportCompetenze() {
        if (this.elencocompetenze == null) {
            return "";
        } else {
            StringBuilder sb1 = new StringBuilder();
            this.elencocompetenze.forEach(f1 -> {
                sb1.append(String.valueOf(f1.getIdcompetenze())).append("; ");
            });
            return sb1.toString().trim();
        }
    }

    public String exportAbilita() {
        if (this.elencoabilita == null) {
            return "";
        } else {
            StringBuilder sb1 = new StringBuilder();
            this.elencoabilita.forEach(f1 -> {
                sb1.append(String.valueOf(f1.getIdabilita())).append("; ");
            });
            return sb1.toString().trim();
        }
    }

    public String exportConoscenze() {
        if (this.elencoconoscenze == null) {
            return "";
        } else {
            StringBuilder sb1 = new StringBuilder();
            this.elencoconoscenze.forEach(f1 -> {
                sb1.append(String.valueOf(f1.getIdconoscenze())).append("; ");
            });
            return sb1.toString().trim();
        }
    }

    public double getOreresiduefad() {
        return oreresiduefad;
    }

    public void setOreresiduefad(double oreresiduefad) {
        this.oreresiduefad = oreresiduefad;
    }

    public double getOreresidue() {
        return oreresidue;
    }

    public void setOreresidue(double oreresidue) {
        this.oreresidue = oreresidue;
    }
    
    public String getCtcodicelingua() {
        return ctcodicelingua;
    }

    public void setCtcodicelingua(String ctcodicelingua) {
        this.ctcodicelingua = ctcodicelingua;
    }

    public String getCtdescrizione() {
        return ctdescrizione;
    }

    public void setCtdescrizione(String ctdescrizione) {
        this.ctdescrizione = ctdescrizione;
    }

    public Calendario_Formativo() {
    }

    public Long getIdcalendarioformativo() {
        return idcalendarioformativo;
    }

    public void setIdcalendarioformativo(Long idcalendarioformativo) {
        this.idcalendarioformativo = idcalendarioformativo;
    }

    public String getNomemodulo() {
        return nomemodulo;
    }

    public void setNomemodulo(String nomemodulo) {
        this.nomemodulo = nomemodulo;
    }

    public String getTipomodulo() {
        return tipomodulo;
    }

    public void setTipomodulo(String tipomodulo) {
        this.tipomodulo = tipomodulo;
    }

    public Corso getCorsodiriferimento() {
        return corsodiriferimento;
    }

    public void setCorsodiriferimento(Corso corsodiriferimento) {
        this.corsodiriferimento = corsodiriferimento;
    }

    public double getOre_teorica_aula() {
        return ore_teorica_aula;
    }

    public void setOre_teorica_aula(double ore_teorica_aula) {
        this.ore_teorica_aula = ore_teorica_aula;
    }

    public double getOre_teorica_el() {
        return ore_teorica_el;
    }

    public void setOre_teorica_el(double ore_teorica_el) {
        this.ore_teorica_el = ore_teorica_el;
    }

    public double getOre_tecnica_operativa() {
        return ore_tecnica_operativa;
    }

    public void setOre_tecnica_operativa(double ore_tecnica_operativa) {
        this.ore_tecnica_operativa = ore_tecnica_operativa;
    }

    public double getOre_aula() {
        return ore_aula;
    }

    public void setOre_aula(double ore_aula) {
        this.ore_aula = ore_aula;
    }

    public double getOre() {
        return ore;
    }

    public void setOre(double ore) {
        this.ore = ore;
    }

    public String getCodicemodulo() {
        return codicemodulo;
    }

    public void setCodicemodulo(String codicemodulo) {
        this.codicemodulo = codicemodulo;
    }

    public Competenze_Trasversali getCompetenzetrasversali() {
        return competenzetrasversali;
    }

    public void setCompetenzetrasversali(Competenze_Trasversali competenzetrasversali) {
        this.competenzetrasversali = competenzetrasversali;
    }

    public List<Competenze> getElencocompetenze() {
        return elencocompetenze;
    }

    public void setElencocompetenze(List<Competenze> elencocompetenze) {
        this.elencocompetenze = elencocompetenze;
    }

    public List<Abilita> getElencoabilita() {
        return elencoabilita;
    }

    public void setElencoabilita(List<Abilita> elencoabilita) {
        this.elencoabilita = elencoabilita;
    }

    public List<Conoscenze> getElencoconoscenze() {
        return elencoconoscenze;
    }

    public void setElencoconoscenze(List<Conoscenze> elencoconoscenze) {
        this.elencoconoscenze = elencoconoscenze;
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
        Calendario_Formativo other = (Calendario_Formativo) obj;
        return Objects.equals(idcalendarioformativo, other.idcalendarioformativo);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.idcalendarioformativo);
        return hash;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("{")
                .append("\"").append("idcalendarioformativo").append("\"").append(":").append("\"").append(this.idcalendarioformativo).append("\"").append(",")
                .append("}")
                .toString();
    }

}
