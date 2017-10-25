package com.example.ctorres.superagentemovil3.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CTORRES on 11/05/2017.
 */

public class CuentaEntity {
    String numCuenta, banco, tipoCuenta, tipoMoneda, idcliente, idcuenta;
    int cod_banco, tipo_moneda;

    public CuentaEntity() {
    }

    public CuentaEntity(String numCuenta, String banco, String tipoCuenta, String tipoMoneda, String idcliente, String idcuenta, int cod_banco, int tipo_moneda) {
        this.numCuenta = numCuenta;
        this.banco = banco;
        this.tipoCuenta = tipoCuenta;
        this.tipoMoneda = tipoMoneda;
        this.idcliente = idcliente;
        this.idcuenta = idcuenta;
        this.cod_banco = cod_banco;
        this.tipo_moneda = tipo_moneda;
    }

    public int getTipo_moneda() {
        return tipo_moneda;
    }

    public void setTipo_moneda(int tipo_moneda) {
        this.tipo_moneda = tipo_moneda;
    }

    public int getCod_banco() {
        return cod_banco;
    }

    public void setCod_banco(int cod_banco) {
        this.cod_banco = cod_banco;
    }

    public String getIdcuenta() {
        return idcuenta;
    }

    public void setIdcuenta(String idcuenta) {
        this.idcuenta = idcuenta;
    }

    public String getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(String idcliente) {
        this.idcliente = idcliente;
    }
}
