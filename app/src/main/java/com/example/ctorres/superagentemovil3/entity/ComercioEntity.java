package com.example.ctorres.superagentemovil3.entity;

/**
 * Created by CTORRES on 02/11/2017.
 */

public class ComercioEntity {
    private String id_comercio,nombre_comerciante, apellido_comerciante,ruc_comercio,razon_social,dni_comerciante;
    private int pais_comercio,provincia_comercio;
    private String direccion_comercio;

    public ComercioEntity() {
    }

    public ComercioEntity(String id_comercio, String nombre_comerciante, String apellido_comerciante,
                    String ruc_comercio, String razon_social, String dni_comerciante,
                    int pais_comercio, int provincia_comercio, String direccion_comercio) {
        this.id_comercio = id_comercio;
        this.nombre_comerciante = nombre_comerciante;
        this.apellido_comerciante = apellido_comerciante;
        this.ruc_comercio = ruc_comercio;
        this.razon_social = razon_social;
        this.dni_comerciante = dni_comerciante;
        this.pais_comercio = pais_comercio;
        this.provincia_comercio = provincia_comercio;
        this.direccion_comercio = direccion_comercio;
    }

    public String getId_comercio() {
        return id_comercio;
    }

    public void setId_comercio(String id_comercio) {
        this.id_comercio = id_comercio;
    }

    public String getNombre_comerciante() {
        return nombre_comerciante;
    }

    public void setNombre_comerciante(String nombre_comerciante) {
        this.nombre_comerciante = nombre_comerciante;
    }

    public String getApellido_comerciante() {
        return apellido_comerciante;
    }

    public void setApellido_comerciante(String apellido_comerciante) {
        this.apellido_comerciante = apellido_comerciante;
    }

    public String getRuc_comercio() {
        return ruc_comercio;
    }

    public void setRuc_comercio(String ruc_comercio) {
        this.ruc_comercio = ruc_comercio;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getDni_comerciante() {
        return dni_comerciante;
    }

    public void setDni_comerciante(String dni_comerciante) {
        this.dni_comerciante = dni_comerciante;
    }

    public int getPais_comercio() {
        return pais_comercio;
    }

    public void setPais_comercio(int pais_comercio) {
        this.pais_comercio = pais_comercio;
    }

    public int getProvincia_comercio() {
        return provincia_comercio;
    }

    public void setProvincia_comercio(int provincia_comercio) {
        this.provincia_comercio = provincia_comercio;
    }

    public String getDireccion_comercio() {
        return direccion_comercio;
    }

    public void setDireccion_comercio(String direccion_comercio) {
        this.direccion_comercio = direccion_comercio;
    }
}
