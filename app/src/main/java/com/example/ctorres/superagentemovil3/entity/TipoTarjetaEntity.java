package com.example.ctorres.superagentemovil3.entity;

/**
 * Created by Administrador on 01/12/2017.
 */

public class TipoTarjetaEntity {
    private int codTipoTarjeta;
    private String descTipoTarjeta;

    public TipoTarjetaEntity(){

    }

    public TipoTarjetaEntity(int codTipoTarjeta, String descTipoTarjeta) {
        this.codTipoTarjeta = codTipoTarjeta;
        this.descTipoTarjeta = descTipoTarjeta;
    }

    public int getCodTipoTarjeta() {
        return codTipoTarjeta;
    }

    public void setCodTipoTarjeta(int codTipoTarjeta) {
        this.codTipoTarjeta = codTipoTarjeta;
    }

    public String getDescTipoTarjeta() {
        return descTipoTarjeta;
    }

    public void setDescTipoTarjeta(String descTipoTarjeta) {
        this.descTipoTarjeta = descTipoTarjeta;
    }
}
