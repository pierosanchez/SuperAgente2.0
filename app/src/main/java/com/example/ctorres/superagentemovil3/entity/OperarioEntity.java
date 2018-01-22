package com.example.ctorres.superagentemovil3.entity;

/**
 * Created by Administrador on 17/01/2018.
 */

public class OperarioEntity {
    private String rptaCambioClaveOperario;
    private String dniOpe;
    private String nomOpe;
    private String paterOpe;
    private String materOpe;
    private String celularOpe;
    private String fonoFijoOpe;
    private String sexoOpe;
    private String comercioOpe;
    private String departamentoOpe;
    private String distritoOpe;
    private String direccionOpe;
    private String usuRegOpe;

    public OperarioEntity(){

    }

    public OperarioEntity(String rptaCambioClaveOperario, String dniOpe, String nomOpe, String paterOpe, String materOpe, String celularOpe, String fonoFijoOpe, String sexoOpe, String comercioOpe, String departamentoOpe, String distritoOpe, String direccionOpe, String usuRegOpe) {
        this.rptaCambioClaveOperario = rptaCambioClaveOperario;
        this.dniOpe = dniOpe;
        this.nomOpe = nomOpe;
        this.paterOpe = paterOpe;
        this.materOpe = materOpe;
        this.celularOpe = celularOpe;
        this.fonoFijoOpe = fonoFijoOpe;
        this.sexoOpe = sexoOpe;
        this.comercioOpe = comercioOpe;
        this.departamentoOpe = departamentoOpe;
        this.distritoOpe = distritoOpe;
        this.direccionOpe = direccionOpe;
        this.usuRegOpe = usuRegOpe;
    }

    public String getRptaCambioClaveOperario() {
        return rptaCambioClaveOperario;
    }

    public void setRptaCambioClaveOperario(String rptaCambioClaveOperario) {
        this.rptaCambioClaveOperario = rptaCambioClaveOperario;
    }

    public String getDniOpe() {
        return dniOpe;
    }

    public void setDniOpe(String dniOpe) {
        this.dniOpe = dniOpe;
    }

    public String getNomOpe() {
        return nomOpe;
    }

    public void setNomOpe(String nomOpe) {
        this.nomOpe = nomOpe;
    }

    public String getPaterOpe() {
        return paterOpe;
    }

    public void setPaterOpe(String paterOpe) {
        this.paterOpe = paterOpe;
    }

    public String getMaterOpe() {
        return materOpe;
    }

    public void setMaterOpe(String materOpe) {
        this.materOpe = materOpe;
    }

    public String getCelularOpe() {
        return celularOpe;
    }

    public void setCelularOpe(String celularOpe) {
        this.celularOpe = celularOpe;
    }

    public String getFonoFijoOpe() {
        return fonoFijoOpe;
    }

    public void setFonoFijoOpe(String fonoFijoOpe) {
        this.fonoFijoOpe = fonoFijoOpe;
    }

    public String getSexoOpe() {
        return sexoOpe;
    }

    public void setSexoOpe(String sexoOpe) {
        this.sexoOpe = sexoOpe;
    }

    public String getComercioOpe() {
        return comercioOpe;
    }

    public void setComercioOpe(String comercioOpe) {
        this.comercioOpe = comercioOpe;
    }

    public String getDepartamentoOpe() {
        return departamentoOpe;
    }

    public void setDepartamentoOpe(String departamentoOpe) {
        this.departamentoOpe = departamentoOpe;
    }

    public String getDistritoOpe() {
        return distritoOpe;
    }

    public void setDistritoOpe(String distritoOpe) {
        this.distritoOpe = distritoOpe;
    }

    public String getDireccionOpe() {
        return direccionOpe;
    }

    public void setDireccionOpe(String direccionOpe) {
        this.direccionOpe = direccionOpe;
    }

    public String getUsuRegOpe() {
        return usuRegOpe;
    }

    public void setUsuRegOpe(String usuRegOpe) {
        this.usuRegOpe = usuRegOpe;
    }
}
