package com.example.ctorres.superagentemovil3.entity;

/**
 * Created by CTORRES on 09/08/2017.
 */

public class EmpresasServiciosEntity {
    int cod_emp_servicio, cod_tipo_emps_servicio;
    String des_emp_servicio;

    public EmpresasServiciosEntity() {
    }

    public EmpresasServiciosEntity(int cod_emp_servicio, String des_emp_servicio, int cod_tipo_emps_servicio) {
        this.cod_emp_servicio = cod_emp_servicio;
        this.des_emp_servicio = des_emp_servicio;
        this.cod_tipo_emps_servicio = cod_tipo_emps_servicio;
    }

    public int getCod_emp_servicio() {
        return cod_emp_servicio;
    }

    public void setCod_emp_servicio(int cod_emp_servicio) {
        this.cod_emp_servicio = cod_emp_servicio;
    }

    public String getDes_emp_servicio() {
        return des_emp_servicio;
    }

    public void setDes_emp_servicio(String des_emp_servicio) {
        this.des_emp_servicio = des_emp_servicio;
    }

    public int getCod_tipo_emps_servicio() {
        return cod_tipo_emps_servicio;
    }

    public void setCod_tipo_emps_servicio(int cod_tipo_emps_servicio) {
        this.cod_tipo_emps_servicio = cod_tipo_emps_servicio;
    }
}
