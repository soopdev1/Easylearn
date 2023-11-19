/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.GsonBuilder;
import java.util.Objects;

/**
 *
 * @author Administrator
 */
public class EsamefinaleDetails {

    @JsonProperty("idallievi")
    Long IDALLIEVI;
    
    boolean interno;
    String SESSO, AMMESSO, AMMESSOFORZATO, PRESENTE, VOTOAMM, VOTOMEDIA, VOTOPSC, VOTOCOLL, VOTOPPR, VOTOFINALE, ESITO;
    int eta;

    public EsamefinaleDetails() {
    }

    public EsamefinaleDetails(Long IDALLIEVI, boolean interno, String SESSO, String AMMESSO, String AMMESSOFORZATO, String PRESENTE, String VOTOAMM, String VOTOMEDIA, String VOTOPSC, String VOTOCOLL, String VOTOPPR, String VOTOFINALE, String ESITO, int eta) {
        this.IDALLIEVI = IDALLIEVI;
        this.interno = interno;
        this.SESSO = SESSO;
        this.AMMESSO = AMMESSO;
        this.AMMESSOFORZATO = AMMESSOFORZATO;
        this.PRESENTE = PRESENTE;
        this.VOTOAMM = VOTOAMM;
        this.VOTOMEDIA = VOTOMEDIA;
        this.VOTOPSC = VOTOPSC;
        this.VOTOCOLL = VOTOCOLL;
        this.VOTOPPR = VOTOPPR;
        this.VOTOFINALE = VOTOFINALE;
        this.ESITO = ESITO;
        this.eta = eta;
    }

    public Long getIDALLIEVI() {
        return IDALLIEVI;
    }

    public void setIDALLIEVI(Long IDALLIEVI) {
        this.IDALLIEVI = IDALLIEVI;
    }

    public boolean isInterno() {
        return interno;
    }

    public void setInterno(boolean interno) {
        this.interno = interno;
    }

    public String getSESSO() {
        return SESSO;
    }

    public void setSESSO(String SESSO) {
        this.SESSO = SESSO;
    }

    public String getAMMESSO() {
        return AMMESSO;
    }

    public void setAMMESSO(String AMMESSO) {
        this.AMMESSO = AMMESSO;
    }

    public String getAMMESSOFORZATO() {
        return AMMESSOFORZATO;
    }

    public void setAMMESSOFORZATO(String AMMESSOFORZATO) {
        this.AMMESSOFORZATO = AMMESSOFORZATO;
    }

    public String getPRESENTE() {
        return PRESENTE;
    }

    public void setPRESENTE(String PRESENTE) {
        this.PRESENTE = PRESENTE;
    }

    public String getVOTOAMM() {
        return VOTOAMM;
    }

    public void setVOTOAMM(String VOTOAMM) {
        this.VOTOAMM = VOTOAMM;
    }

    public String getVOTOMEDIA() {
        return VOTOMEDIA;
    }

    public void setVOTOMEDIA(String VOTOMEDIA) {
        this.VOTOMEDIA = VOTOMEDIA;
    }

    public String getVOTOPSC() {
        return VOTOPSC;
    }

    public void setVOTOPSC(String VOTOPSC) {
        this.VOTOPSC = VOTOPSC;
    }

    public String getVOTOCOLL() {
        return VOTOCOLL;
    }

    public void setVOTOCOLL(String VOTOCOLL) {
        this.VOTOCOLL = VOTOCOLL;
    }

    public String getVOTOPPR() {
        return VOTOPPR;
    }

    public void setVOTOPPR(String VOTOPPR) {
        this.VOTOPPR = VOTOPPR;
    }

    public String getVOTOFINALE() {
        return VOTOFINALE;
    }

    public void setVOTOFINALE(String VOTOFINALE) {
        this.VOTOFINALE = VOTOFINALE;
    }

    public String getESITO() {
        return ESITO;
    }

    public void setESITO(String ESITO) {
        this.ESITO = ESITO;
    }

    public int getEta() {
        return eta;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.IDALLIEVI);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EsamefinaleDetails other = (EsamefinaleDetails) obj;
        return Objects.equals(this.IDALLIEVI, other.IDALLIEVI);
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

}
