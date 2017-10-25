package com.example.ctorres.superagentemovil3.entity;

/**
 * Created by CTORRES on 24/10/2017.
 */

public class OperadorEntity {

    private int ope_ide;
    private String ope_ruc,ope_razonsoc,ope_nomcomercial;

    public OperadorEntity() {
    }

    public OperadorEntity(int ope_ide, String ope_ruc, String ope_razonsoc, String ope_nomcomercial) {
        this.ope_ide = ope_ide;
        this.ope_ruc = ope_ruc;
        this.ope_razonsoc = ope_razonsoc;
        this.ope_nomcomercial = ope_nomcomercial;
    }

    public int getOpe_ide() {
        return ope_ide;
    }

    public void setOpe_ide(int ope_ide) {
        this.ope_ide = ope_ide;
    }

    public String getOpe_ruc() {
        return ope_ruc;
    }

    public void setOpe_ruc(String ope_ruc) {
        this.ope_ruc = ope_ruc;
    }

    public String getOpe_razonsoc() {
        return ope_razonsoc;
    }

    public void setOpe_razonsoc(String ope_razonsoc) {
        this.ope_razonsoc = ope_razonsoc;
    }

    public String getOpe_nomcomercial() {
        return ope_nomcomercial;
    }

    public void setOpe_nomcomercial(String ope_nomcomercial) {
        this.ope_nomcomercial = ope_nomcomercial;
    }
}


