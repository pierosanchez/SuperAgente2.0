package com.example.ctorres.superagentemovil3.entity;

/**
 * Created by CTORRES on 15/09/2017.
 */

public class ServiciosPublicEntity {
    private int cod_inst_edu;
    private String des_inst_edu;

    public ServiciosPublicEntity() {
    }

    public ServiciosPublicEntity(int cod_inst_edu, String des_inst_edu) {
        this.cod_inst_edu = cod_inst_edu;
        this.des_inst_edu = des_inst_edu;
    }

    public int getCod_inst_edu() {
        return cod_inst_edu;
    }

    public void setCod_inst_edu(int cod_inst_edu) {
        this.cod_inst_edu = cod_inst_edu;
    }

    public String getDes_inst_edu() {
        return des_inst_edu;
    }

    public void setDes_inst_edu(String des_inst_edu) {
        this.des_inst_edu = des_inst_edu;
    }
}
