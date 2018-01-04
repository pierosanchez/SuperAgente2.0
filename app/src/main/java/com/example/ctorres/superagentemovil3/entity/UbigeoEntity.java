package com.example.ctorres.superagentemovil3.entity;

/**
 * Created by Administrador on 02/01/2018.
 */

public class UbigeoEntity {
    private String ubigeo1;
    private String ubigeo2;
    private String ubigeo3;
    private String departamento;
    private String provincia;
    private String distrito;

    public UbigeoEntity(){

    }

    public UbigeoEntity(String ubigeo1, String ubigeo2, String ubigeo3, String departamento, String provincia, String distrito) {
        this.ubigeo1 = ubigeo1;
        this.ubigeo2 = ubigeo2;
        this.ubigeo3 = ubigeo3;
        this.departamento = departamento;
        this.provincia = provincia;
        this.distrito = distrito;
    }

    public String getUbigeo1() {
        return ubigeo1;
    }

    public void setUbigeo1(String ubigeo1) {
        this.ubigeo1 = ubigeo1;
    }

    public String getUbigeo2() {
        return ubigeo2;
    }

    public void setUbigeo2(String ubigeo2) {
        this.ubigeo2 = ubigeo2;
    }

    public String getUbigeo3() {
        return ubigeo3;
    }

    public void setUbigeo3(String ubigeo3) {
        this.ubigeo3 = ubigeo3;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }
}
