package com.example.ctorres.superagentemovil3.entity;

/**
 * Created by CTORRES on 05/05/2017.
 */

public class BeneficiarioEntity {
    private String nombre, apellido, celular1, celular2, email, fechaNac, pass, dni, idcliente, cod_interbancario, num_tarjeta_beneficiario, errror_ingreso_cuenta;
    private int cod_emisor_tarjeta, cod_banco, cod_tipo_tarjeta, cod_tipo_cuenta, id_cuenta_benef;

    public BeneficiarioEntity() {
    }

    public BeneficiarioEntity(String nombre, String apellido, String celular1, String celular2, String email, String fechaNac, String pass, String dni, String idcliente, String cod_interbancario, String num_tarjeta_beneficiario, int cod_emisor_tarjeta, int cod_banco, int cod_tipo_tarjeta, int cod_tipo_cuenta) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.celular1 = celular1;
        this.celular2 = celular2;
        this.email = email;
        this.fechaNac = fechaNac;
        this.pass = pass;
        this.dni = dni;
        this.idcliente = idcliente;
        this.dni = idcliente;
        this.cod_interbancario = cod_interbancario;
        this.num_tarjeta_beneficiario = num_tarjeta_beneficiario;
        this.cod_emisor_tarjeta = cod_emisor_tarjeta;
        this.cod_banco = cod_banco;
        this.cod_tipo_tarjeta = cod_tipo_tarjeta;
        this.cod_tipo_cuenta = cod_tipo_cuenta;
    }

    public BeneficiarioEntity(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public BeneficiarioEntity(String cod_interbancario) {
        this.cod_interbancario = cod_interbancario;
    }

    public String getErrror_ingreso_cuenta() {
        return errror_ingreso_cuenta;
    }

    public void setErrror_ingreso_cuenta(String errror_ingreso_cuenta) {
        this.errror_ingreso_cuenta = errror_ingreso_cuenta;
    }

    public int getId_cuenta_benef() {
        return id_cuenta_benef;
    }

    public void setId_cuenta_benef(int id_cuenta_benef) {
        this.id_cuenta_benef = id_cuenta_benef;
    }

    public int getCod_emisor_tarjeta() {
        return cod_emisor_tarjeta;
    }

    public void setCod_emisor_tarjeta(int cod_emisor_tarjeta) {
        this.cod_emisor_tarjeta = cod_emisor_tarjeta;
    }

    public int getCod_tipo_cuenta() {
        return cod_tipo_cuenta;
    }

    public void setCod_tipo_cuenta(int cod_tipo_cuenta) {
        this.cod_tipo_cuenta = cod_tipo_cuenta;
    }

    public int getCod_banco() {
        return cod_banco;
    }

    public void setCod_banco(int cod_banco) {
        this.cod_banco = cod_banco;
    }

    public int getCod_tipo_tarjeta() {
        return cod_tipo_tarjeta;
    }

    public void setCod_tipo_tarjeta(int cod_tipo_tarjeta) {
        this.cod_tipo_tarjeta = cod_tipo_tarjeta;
    }

    public String getNum_tarjeta_beneficiario() {
        return num_tarjeta_beneficiario;
    }

    public void setNum_tarjeta_beneficiario(String num_tarjeta_beneficiario) {
        this.num_tarjeta_beneficiario = num_tarjeta_beneficiario;
    }

    public String getCod_interbancario() {
        return cod_interbancario;
    }

    public void setCod_interbancario(String cod_interbancario) {
        this.cod_interbancario = cod_interbancario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCelular1() {
        return celular1;
    }

    public void setCelular1(String celular1) {
        this.celular1 = celular1;
    }

    public String getCelular2() {
        return celular2;
    }

    public void setCelular2(String celular2) {
        this.celular2 = celular2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String repass) {
        this.dni = repass;
    }

    public String getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(String idcliente) {
        this.idcliente = idcliente;
    }
}
