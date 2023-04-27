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
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "schedaattivita")
public class Scheda_Attivita implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idschedaattivita")
    private Long idschedaattivita;

    @Column(name = "tipologiapercorso")
    private String tipologiapercorso;

    @Column(name = "titolopercorso")
    private String titolopercorso;

    @Column(name = "titoloattestato")
    private String titoloattestato;

    @ManyToOne
    @JoinColumn(name = "codicecertificazione")
    private Certificazione certificazioneuscita;

    @Column(name = "orecorsominime")
    private int orecorsominime_num;

    @Column(name = "orecorsomassime")
    private int orecorsomassime_num;

    @Column(name = "orestageminime")
    private int orestageminime_num;

    @Column(name = "orestagemassime")
    private int orestagemassime_num;

    @Column(name = "oreelearningeminime")
    private int oreelearningeminime_perc;

    @Column(name = "oreelearningemassime")
    private int oreelearningemassime_perc;

    @Column(name = "oreassenzamassime")
    private int oreassenzamassime_perc;

    @ManyToOne
    @JoinColumn(name = "idrepertorio")
    private Repertorio repertorio;

    public Scheda_Attivita() {
    }

    public Long getIdschedaattivita() {
        return idschedaattivita;
    }

    public void setIdschedaattivita(Long idschedaattivita) {
        this.idschedaattivita = idschedaattivita;
    }

    public String getTipologiapercorso() {
        return tipologiapercorso;
    }

    public void setTipologiapercorso(String tipologiapercorso) {
        this.tipologiapercorso = tipologiapercorso;
    }

    public String getTitolopercorso() {
        return titolopercorso;
    }

    public void setTitolopercorso(String titolopercorso) {
        this.titolopercorso = titolopercorso;
    }

    public String getTitoloattestato() {
        return titoloattestato;
    }

    public void setTitoloattestato(String titoloattestato) {
        this.titoloattestato = titoloattestato;
    }

    public Certificazione getCertificazioneuscita() {
        return certificazioneuscita;
    }

    public void setCertificazioneuscita(Certificazione certificazioneuscita) {
        this.certificazioneuscita = certificazioneuscita;
    }

    public int getOrecorsominime_num() {
        return orecorsominime_num;
    }

    public void setOrecorsominime_num(int orecorsominime_num) {
        this.orecorsominime_num = orecorsominime_num;
    }

    public int getOrecorsomassime_num() {
        return orecorsomassime_num;
    }

    public void setOrecorsomassime_num(int orecorsomassime_num) {
        this.orecorsomassime_num = orecorsomassime_num;
    }

    public int getOrestageminime_num() {
        return orestageminime_num;
    }

    public void setOrestageminime_num(int orestageminime_num) {
        this.orestageminime_num = orestageminime_num;
    }

    public int getOrestagemassime_num() {
        return orestagemassime_num;
    }

    public void setOrestagemassime_num(int orestagemassime_num) {
        this.orestagemassime_num = orestagemassime_num;
    }

    public int getOreelearningeminime_perc() {
        return oreelearningeminime_perc;
    }

    public void setOreelearningeminime_perc(int oreelearningeminime_perc) {
        this.oreelearningeminime_perc = oreelearningeminime_perc;
    }

    public int getOreelearningemassime_perc() {
        return oreelearningemassime_perc;
    }

    public void setOreelearningemassime_perc(int oreelearningemassime_perc) {
        this.oreelearningemassime_perc = oreelearningemassime_perc;
    }

    public int getOreassenzamassime_perc() {
        return oreassenzamassime_perc;
    }

    public void setOreassenzamassime_perc(int oreassenzamassime_perc) {
        this.oreassenzamassime_perc = oreassenzamassime_perc;
    }

    public Repertorio getRepertorio() {
        return repertorio;
    }

    public void setRepertorio(Repertorio repertorio) {
        this.repertorio = repertorio;
    }

    @Override
    public String toString() {

        StringBuilder elelabel = new StringBuilder();

        if (this.oreelearningemassime_perc > 0) {
            elelabel.append("\"").append("MIN ")
                    .append(this.oreelearningeminime_perc).append(" - MAX ").append(this.oreelearningemassime_perc)
                    .append("\"");
        } else {
            elelabel.append("\"").append("NO")
                    .append("\"");
        }

        StringBuilder stagelabel = new StringBuilder();

        if (this.orestagemassime_num > 0) {
            stagelabel.append("\"").append("MIN ")
                    .append(this.orestageminime_num).append(" - MAX ").append(this.orestagemassime_num)
                    .append("\"");
        } else {
            stagelabel.append("\"").append("NO")
                    .append("\"");
        }

        return new StringBuilder()
                .append("{")
                .append("\"").append("oremax").append("\"").append(":").append(this.orecorsomassime_num).append(",")
                .append("\"").append("oremin").append("\"").append(":").append(this.orecorsominime_num).append(",")
                .append("\"").append("elemax").append("\"").append(":").append(this.oreelearningemassime_perc).append(",")
                .append("\"").append("elemin").append("\"").append(":").append(this.oreelearningeminime_perc).append(",")
                .append("\"").append("stagemax").append("\"").append(":").append(this.orestagemassime_num).append(",")
                .append("\"").append("stagemin").append("\"").append(":").append(this.orestageminime_num).append(",")
                .append("\"").append("assenzemax").append("\"").append(":").append(this.oreassenzamassime_perc).append(",")
                .append("\"").append("orelabel").append("\"").append(":").append("\"").append("MIN ").append(this.orecorsominime_num).append(" - MAX ").append(this.orecorsomassime_num).append("\"").append(",")
                .append("\"").append("stagelabel").append("\"").append(":").append(stagelabel.toString()).append(",")
                .append("\"").append("elelabel").append("\"").append(":").append(elelabel.toString())
                .append("}")
                .toString();
    }

}
