package com.example.ctorres.superagentemovil3.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrador on 14/12/2017.
 */

public class VoucherPagoRecargaEntity implements Parcelable{
    private String numeroUnico;
    private String fecha;
    private String hora;
    private String recarga;
    private String formaPago;
    private String importe;
    private String comisionRecarga;
    private String banco;
    private String nroTarjeta;
    private String tipoMoneda;
    private String idCliente;
    private String total;

    public VoucherPagoRecargaEntity(){

    }

    public VoucherPagoRecargaEntity(String numeroUnico, String fecha, String hora, String recarga, String formaPago, String importe, String comisionRecarga, String banco, String nroTarjeta, String tipoMoneda, String idCliente, String total) {
        this.numeroUnico = numeroUnico;
        this.fecha = fecha;
        this.hora = hora;
        this.recarga = recarga;
        this.formaPago = formaPago;
        this.importe = importe;
        this.comisionRecarga = comisionRecarga;
        this.banco = banco;
        this.nroTarjeta = nroTarjeta;
        this.tipoMoneda = tipoMoneda;
        this.idCliente = idCliente;
        this.total = total;
    }

    protected VoucherPagoRecargaEntity(Parcel in) {
        String[] data= new String[2];
        in.readStringArray(data);
        numeroUnico = data[0];
        idCliente = data[1];
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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

    public String getRecarga() {
        return recarga;
    }

    public void setRecarga(String recarga) {
        this.recarga = recarga;
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

    public String getComisionRecarga() {
        return comisionRecarga;
    }

    public void setComisionRecarga(String comisionRecarga) {
        this.comisionRecarga = comisionRecarga;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getNroTarjeta() {
        return nroTarjeta;
    }

    public void setNroTarjeta(String nroTarjeta) {
        this.nroTarjeta = nroTarjeta;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.numeroUnico,this.idCliente});
    }

    public static final Parcelable.Creator<VoucherPagoRecargaEntity> CREATOR = new Creator<VoucherPagoRecargaEntity>() {
        @Override
        public VoucherPagoRecargaEntity createFromParcel(Parcel source) {
            return new VoucherPagoRecargaEntity(source);
        }

        @Override
        public VoucherPagoRecargaEntity[] newArray(int size) {
            return new VoucherPagoRecargaEntity[size];
        }
    };
}
