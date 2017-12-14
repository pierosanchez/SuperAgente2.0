package com.example.ctorres.superagentemovil3.entity;

/**
 * Created by Administrador on 14/12/2017.
 */

public class VoucherPagoTarjetaCreditoEntity {
    private String numeroUnico;
    private String fecha;
    private String hora;
    private String tarjetaPagada;
    private String banco;
    private String tarjetaCargo;
    private String bancoTarjetaCargo;
    private String importe;
    private String tipoMoneda;
    private String idCliente;

    public VoucherPagoTarjetaCreditoEntity(){

    }

    public VoucherPagoTarjetaCreditoEntity(String numeroUnico, String fecha, String hora, String tarjetaPagada, String banco, String tarjetaCargo, String bancoTarjetaCargo, String importe, String tipoMoneda, String idCliente) {
        this.numeroUnico = numeroUnico;
        this.fecha = fecha;
        this.hora = hora;
        this.tarjetaPagada = tarjetaPagada;
        this.banco = banco;
        this.tarjetaCargo = tarjetaCargo;
        this.bancoTarjetaCargo = bancoTarjetaCargo;
        this.importe = importe;
        this.tipoMoneda = tipoMoneda;
        this.idCliente = idCliente;
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

    public String getTarjetaPagada() {
        return tarjetaPagada;
    }

    public void setTarjetaPagada(String tarjetaPagada) {
        this.tarjetaPagada = tarjetaPagada;
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

    public String getBancoTarjetaCargo() {
        return bancoTarjetaCargo;
    }

    public void setBancoTarjetaCargo(String bancoTarjetaCargo) {
        this.bancoTarjetaCargo = bancoTarjetaCargo;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
}
