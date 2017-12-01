package com.example.ctorres.superagentemovil3.entity;

/**
 * Created by Administrador on 29/11/2017.
 */

public class CuotasEntity {
    private int idCuota;
    private int numCuota;

    public CuotasEntity(){

    }

    public CuotasEntity(int idCuota, int numCuota) {
        this.idCuota = idCuota;
        this.numCuota = numCuota;
    }

    public int getIdCuota() {
        return idCuota;
    }

    public void setIdCuota(int idCuota) {
        this.idCuota = idCuota;
    }

    public int getNumCuota() {
        return numCuota;
    }

    public void setNumCuota(int numCuota) {
        this.numCuota = numCuota;
    }
}
