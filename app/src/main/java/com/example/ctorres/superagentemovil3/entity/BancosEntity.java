package com.example.ctorres.superagentemovil3.entity;

/**
 * Created by CTORRES on 05/10/2017.
 */

public class BancosEntity {
    private int cod_banco;
    private String acro_banco, desc_banco, desc_breve_banco;

    public BancosEntity(int cod_banco, String acro_banco, String desc_banco, String desc_breve_banco) {
        this.cod_banco = cod_banco;
        this.acro_banco = acro_banco;
        this.desc_banco = desc_banco;
        this.desc_breve_banco = desc_breve_banco;
    }

    public BancosEntity() {
    }

    public int getCod_banco() {
        return cod_banco;
    }

    public void setCod_banco(int cod_banco) {
        this.cod_banco = cod_banco;
    }

    public String getAcro_banco() {
        return acro_banco;
    }

    public void setAcro_banco(String acro_banco) {
        this.acro_banco = acro_banco;
    }

    public String getDesc_banco() {
        return desc_banco;
    }

    public void setDesc_banco(String desc_banco) {
        this.desc_banco = desc_banco;
    }

    public String getDesc_breve_banco() {
        return desc_breve_banco;
    }

    public void setDesc_breve_banco(String desc_breve_banco) {
        this.desc_breve_banco = desc_breve_banco;
    }
}
