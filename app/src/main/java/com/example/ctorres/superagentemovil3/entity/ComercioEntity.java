package com.example.ctorres.superagentemovil3.entity;

/**
 * Created by CTORRES on 02/11/2017.
 */

public class ComercioEntity {
   private String id_comercio, raz_social_comercio, direccion_comercio,desc_distrito;

    public ComercioEntity() {
    }

    public ComercioEntity(String id_comercio, String raz_social_comercio, String direccion_comercio, String desc_distrito) {
        this.id_comercio = id_comercio;
        this.raz_social_comercio = raz_social_comercio;
        this.direccion_comercio = direccion_comercio;
        this.desc_distrito = desc_distrito;
    }

    public String getId_comercio() {
        return id_comercio;
    }

    public void setId_comercio(String id_comercio) {
        this.id_comercio = id_comercio;
    }

    public String getRaz_social_comercio() {
        return raz_social_comercio;
    }

    public void setRaz_social_comercio(String raz_social_comercio) {
        this.raz_social_comercio = raz_social_comercio;
    }

    public String getDireccion_comercio() {
        return direccion_comercio;
    }

    public void setDireccion_comercio(String direccion_comercio) {
        this.direccion_comercio = direccion_comercio;
    }

    public String getDesc_distrito() {
        return desc_distrito;
    }

    public void setDesc_distrito(String desc_distrito) {
        this.desc_distrito = desc_distrito;
    }
}
