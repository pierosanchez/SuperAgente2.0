package com.example.ctorres.superagentemovil3.entity;

/**
 * Created by CTORRES on 18/09/2017.
 */

public class ClubsEntity {

    private int cod_club;
    private String des_club;

    public ClubsEntity() {
    }

    public ClubsEntity(int cod_club, String des_club) {
        this.cod_club = cod_club;
        this.des_club = des_club;
    }

    public int getCod_club() {
        return cod_club;
    }

    public void setCod_club(int cod_club) {
        this.cod_club = cod_club;
    }

    public String getDes_club() {
        return des_club;
    }

    public void setDes_club(String des_club) {
        this.des_club = des_club;
    }
}
