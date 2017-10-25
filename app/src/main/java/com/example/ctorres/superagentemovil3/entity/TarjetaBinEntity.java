package com.example.ctorres.superagentemovil3.entity;

/**
 * Created by CTORRES on 10/10/2017.
 */

public class TarjetaBinEntity {
    private String emisor, descripcion_emisor, marca, rpta_bin, tipo_tarjeta, ambito;

    public TarjetaBinEntity() {
    }

    public TarjetaBinEntity(String emisor, String descripcion_emisor, String marca, String rpta_bin) {
        this.emisor = emisor;
        this.descripcion_emisor = descripcion_emisor;
        this.marca = marca;
        this.rpta_bin = rpta_bin;
    }

    public String getTipo_tarjeta() {
        return tipo_tarjeta;
    }

    public void setTipo_tarjeta(String tipo_tarjeta) {
        this.tipo_tarjeta = tipo_tarjeta;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getDescripcion_emisor() {
        return descripcion_emisor;
    }

    public void setDescripcion_emisor(String descripcion_emisor) {
        this.descripcion_emisor = descripcion_emisor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getRpta_bin() {
        return rpta_bin;
    }

    public void setRpta_bin(String rpta_bin) {
        this.rpta_bin = rpta_bin;
    }
}
