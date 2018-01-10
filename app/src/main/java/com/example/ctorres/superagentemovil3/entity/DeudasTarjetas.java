package com.example.ctorres.superagentemovil3.entity;

/**
 * Created by Administrador on 12/12/2017.
 */

public class DeudasTarjetas {
    private int idDeudaTarjetaCliente;
    private double montoMinimo;
    private double montoMensual;
    private double montoTotal;
    private int codTipoMoneda;
    private String signoMoneda;
    private String rptaDeudaTarjeta;

    public DeudasTarjetas(){

    }

    public DeudasTarjetas(int idDeudaTarjetaCliente, double montoMinimo, double montoMensual, double montoTotal, int codTipoMoneda, String signoMoneda) {
        this.idDeudaTarjetaCliente = idDeudaTarjetaCliente;
        this.montoMinimo = montoMinimo;
        this.montoMensual = montoMensual;
        this.montoTotal = montoTotal;
        this.codTipoMoneda = codTipoMoneda;
        this.signoMoneda = signoMoneda;
    }

    public String getRptaDeudaTarjeta() {
        return rptaDeudaTarjeta;
    }

    public void setRptaDeudaTarjeta(String rptaDeudaTarjeta) {
        this.rptaDeudaTarjeta = rptaDeudaTarjeta;
    }

    public int getIdDeudaTarjetaCliente() {
        return idDeudaTarjetaCliente;
    }

    public void setIdDeudaTarjetaCliente(int idDeudaTarjetaCliente) {
        this.idDeudaTarjetaCliente = idDeudaTarjetaCliente;
    }

    public double getMontoMinimo() {
        return montoMinimo;
    }

    public void setMontoMinimo(double montoMinimo) {
        this.montoMinimo = montoMinimo;
    }

    public double getMontoMensual() {
        return montoMensual;
    }

    public void setMontoMensual(double montoMensual) {
        this.montoMensual = montoMensual;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public int getCodTipoMoneda() {
        return codTipoMoneda;
    }

    public void setCodTipoMoneda(int codTipoMoneda) {
        this.codTipoMoneda = codTipoMoneda;
    }

    public String getSignoMoneda() {
        return signoMoneda;
    }

    public void setSignoMoneda(String signoMoneda) {
        this.signoMoneda = signoMoneda;
    }
}
