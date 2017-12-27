package com.example.ctorres.superagentemovil3.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrador on 14/12/2017.
 */

public class VoucherPagoServicioEntity implements Parcelable{
    private String numeroUnico;
    private String fecha;
    private String hora;
    private String servicio;
    private String tipoServicio;
    private String codCliente;
    private String nombreTipoServicio;
    private String personaPaga;
    private String dniPersona;
    private String formaPago;
    private String importe;
    private String comision;
    private String total;

    public VoucherPagoServicioEntity(){

    }

    public VoucherPagoServicioEntity(String numeroUnico, String fecha, String hora, String servicio, String tipoServicio, String codCliente, String nombreTipoServicio, String personaPaga, String dniPersona, String formaPago, String importe, String comision, String total) {
        this.numeroUnico = numeroUnico;
        this.fecha = fecha;
        this.hora = hora;
        this.servicio = servicio;
        this.tipoServicio = tipoServicio;
        this.codCliente = codCliente;
        this.nombreTipoServicio = nombreTipoServicio;
        this.personaPaga = personaPaga;
        this.dniPersona = dniPersona;
        this.formaPago = formaPago;
        this.importe = importe;
        this.comision = comision;
        this.total = total;
    }

    protected VoucherPagoServicioEntity(Parcel in) {
        String[] data= new String[2];
        in.readStringArray(data);
        numeroUnico = data[0];
        codCliente = data[1];
    }

    public String getNumeroUnico() {
        return numeroUnico;
    }

    public void setNumeroUnico(String numeroUnico) {
        this.numeroUnico = numeroUnico;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getNombreTipoServicio() {
        return nombreTipoServicio;
    }

    public void setNombreTipoServicio(String nombreTipoServicio) {
        this.nombreTipoServicio = nombreTipoServicio;
    }

    public String getPersonaPaga() {
        return personaPaga;
    }

    public void setPersonaPaga(String personaPaga) {
        this.personaPaga = personaPaga;
    }

    public String getDniPersona() {
        return dniPersona;
    }

    public void setDniPersona(String dniPersona) {
        this.dniPersona = dniPersona;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getComision() {
        return comision;
    }

    public void setComision(String comision) {
        this.comision = comision;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.numeroUnico,this.codCliente});
    }

    public static final Parcelable.Creator<VoucherPagoServicioEntity> CREATOR = new Creator<VoucherPagoServicioEntity>() {
        @Override
        public VoucherPagoServicioEntity createFromParcel(Parcel source) {
            return new VoucherPagoServicioEntity(source);
        }

        @Override
        public VoucherPagoServicioEntity[] newArray(int size) {
            return new VoucherPagoServicioEntity[size];
        }
    };
}
