package com.example.ctorres.superagentemovil3.entity;

/**
 * Created by Administrador on 14/12/2017.
 */

public class VoucherTransferenciasEntity {
    private String numeroUnico;
    private String fecha;
    private String hora;
    private String remitente;
    private String banco;
    private String tarjetaCargo;
    private String importeTransferencia;
    private String montoComision;
    private String comisionDelivery;
    private String comisionCheque;
    private String importeTotal;
    private String beneficiario;
    private String tipoTransferencia;
    private String idCliente;
    private String tipomoneda;

    public VoucherTransferenciasEntity(){

    }

    public VoucherTransferenciasEntity(String numeroUnico, String fecha, String hora, String remitente, String banco, String tarjetaCargo, String importeTransferencia, String montoComision, String comisionDelivery, String comisionCheque, String importeTotal, String beneficiario, String tipoTransferencia, String idCliente, String tipomoneda) {
        this.numeroUnico = numeroUnico;
        this.fecha = fecha;
        this.hora = hora;
        this.remitente = remitente;
        this.banco = banco;
        this.tarjetaCargo = tarjetaCargo;
        this.importeTransferencia = importeTransferencia;
        this.montoComision = montoComision;
        this.comisionDelivery = comisionDelivery;
        this.comisionCheque = comisionCheque;
        this.importeTotal = importeTotal;
        this.beneficiario = beneficiario;
        this.tipoTransferencia = tipoTransferencia;
        this.idCliente = idCliente;
        this.tipomoneda = tipomoneda;
    }

    public String getNumeroUnico() {
        return numeroUnico;
    }

    public void setNumeroUnico(String numeroUnico) {
        this.numeroUnico = numeroUnico;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getTarjetaCargo() {
        return tarjetaCargo;
    }

    public void setTarjetaCargo(String tarjetaCargo) {
        this.tarjetaCargo = tarjetaCargo;
    }

    public String getImporteTransferencia() {
        return importeTransferencia;
    }

    public void setImporteTransferencia(String importeTransferencia) {
        this.importeTransferencia = importeTransferencia;
    }

    public String getMontoComision() {
        return montoComision;
    }

    public void setMontoComision(String montoComision) {
        this.montoComision = montoComision;
    }

    public String getComisionDelivery() {
        return comisionDelivery;
    }

    public void setComisionDelivery(String comisionDelivery) {
        this.comisionDelivery = comisionDelivery;
    }

    public String getComisionCheque() {
        return comisionCheque;
    }

    public void setComisionCheque(String comisionCheque) {
        this.comisionCheque = comisionCheque;
    }

    public String getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(String importeTotal) {
        this.importeTotal = importeTotal;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getTipoTransferencia() {
        return tipoTransferencia;
    }

    public void setTipoTransferencia(String tipoTransferencia) {
        this.tipoTransferencia = tipoTransferencia;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getTipomoneda() {
        return tipomoneda;
    }

    public void setTipomoneda(String tipomoneda) {
        this.tipomoneda = tipomoneda;
    }
}
