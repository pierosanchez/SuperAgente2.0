package com.example.ctorres.superagentemovil3.entity;

/**
 * Created by CTORRES on 05/05/2017.
 */

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

public class UsuarioEntity implements Parcelable {

    private String UsuarioId;
    private String Usuario;
    private String Clave;
    private String Nombre;
    private String Apellido;
    private String Email;
    private String Movil;
    private String ClaveAcceso;
    private String ConfirmClaveAcceso;
    private String SegundaClaveAcceso;
    private String ConfirmSegundaClaveAcceso;
    private String pregunta;
    private String codCliente;
    private String numeroTarjeta;
    private String idTarjeta;
    private String confirmNumeroTarjeta;
    private Boolean insertaTarjeta;
    private int cantidadTarjetasInsertadas, cod_emisor_tarjeta;
    private Context contexto;
    private String telefonoFijo;
    private String redesSociales;
    private String dni;
    private String cvv;
    private String fecha_venci;
    private int tipo_tarjeta;
    private String departamento, provincia, distrito, direccion, tel_fijo;
    private double importe_comision;
    private String banco_tarjeta;
    private int banco_tarjeta_registro;
    private String rpta_cambio_clave;
    private String NombreApellido;
    private String RespTarjeta;
    private String identUser;
    private int validCelUsu;
    private String ape_materno;
    private String estado_civil;
    private String sexo;
    private String priParteNumTarjeta;
    private String segParteNumTarjeta;
    private String terParteNumTarjeta;
    private String cuaParteNumTarjeta;
    private String emisor_tarjeta;
    private String desc_tipo_tarjeta;
    private String desc_cortaBanco;
    private String desc_cortaEmisorTarjeta;


    public String getDesc_cortaEmisorTarjeta() {
        return desc_cortaEmisorTarjeta;
    }

    public void setDesc_cortaEmisorTarjeta(String desc_cortaEmisorTarjeta) {
        this.desc_cortaEmisorTarjeta = desc_cortaEmisorTarjeta;
    }

    public String getDesc_cortaBanco() {
        return desc_cortaBanco;
    }

    public void setDesc_cortaBanco(String desc_cortaBanco) {
        this.desc_cortaBanco = desc_cortaBanco;
    }

    public String getDesc_tipo_tarjeta() {
        return desc_tipo_tarjeta;
    }

    public void setDesc_tipo_tarjeta(String desc_tipo_tarjeta) {
        this.desc_tipo_tarjeta = desc_tipo_tarjeta;
    }

    public String getEmisor_tarjeta() {
        return emisor_tarjeta;
    }

    public void setEmisor_tarjeta(String emisor_tarjeta) {
        this.emisor_tarjeta = emisor_tarjeta;
    }

    public String getPriParteNumTarjeta() {
        return priParteNumTarjeta;
    }

    public void setPriParteNumTarjeta(String priParteNumTarjeta) {
        this.priParteNumTarjeta = priParteNumTarjeta;
    }

    public String getSegParteNumTarjeta() {
        return segParteNumTarjeta;
    }

    public void setSegParteNumTarjeta(String segParteNumTarjeta) {
        this.segParteNumTarjeta = segParteNumTarjeta;
    }

    public String getTerParteNumTarjeta() {
        return terParteNumTarjeta;
    }

    public void setTerParteNumTarjeta(String terParteNumTarjeta) {
        this.terParteNumTarjeta = terParteNumTarjeta;
    }

    public String getCuaParteNumTarjeta() {
        return cuaParteNumTarjeta;
    }

    public void setCuaParteNumTarjeta(String cuaParteNumTarjeta) {
        this.cuaParteNumTarjeta = cuaParteNumTarjeta;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEstado_civil() {
        return estado_civil;
    }

    public void setEstado_civil(String estado_civil) {
        this.estado_civil = estado_civil;
    }

    public String getApe_materno() {
        return ape_materno;
    }

    public void setApe_materno(String ape_materno) {
        this.ape_materno = ape_materno;
    }

    public int getValidCelUsu() {
        return validCelUsu;
    }

    public void setValidCelUsu(int validCelUsu) {
        this.validCelUsu = validCelUsu;
    }

    public String getIdentUser() {
        return identUser;
    }

    public void setIdentUser(String identUser) {
        this.identUser = identUser;
    }

    public String getRespTarjeta() {
        return RespTarjeta;
    }

    public void setRespTarjeta(String respTarjeta) {
        RespTarjeta = respTarjeta;
    }

    public String getNombreApellido() {
        return NombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        NombreApellido = nombreApellido;
    }

    public String getRpta_cambio_clave() {
        return rpta_cambio_clave;
    }

    public void setRpta_cambio_clave(String rpta_cambio_clave) {
        this.rpta_cambio_clave = rpta_cambio_clave;
    }

    public int getBanco_tarjeta_registro() {
        return banco_tarjeta_registro;
    }

    public void setBanco_tarjeta_registro(int banco_tarjeta_registro) {
        this.banco_tarjeta_registro = banco_tarjeta_registro;
    }

    public String getBanco_tarjeta() {
        return banco_tarjeta;
    }

    public void setBanco_tarjeta(String banco_tarjeta) {
        this.banco_tarjeta = banco_tarjeta;
    }

    public String getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(String idTarjeta) {
        this.idTarjeta = idTarjeta;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTel_fijo() {
        return tel_fijo;
    }

    public void setTel_fijo(String tel_fijo) {
        this.tel_fijo = tel_fijo;
    }

    public int getCod_emisor_tarjeta() {
        return cod_emisor_tarjeta;
    }

    public void setCod_emisor_tarjeta(int cod_emisor_tarjeta) {
        this.cod_emisor_tarjeta = cod_emisor_tarjeta;
    }

    public double getImporte_comision() {
        return importe_comision;
    }

    public void setImporte_comision(double importe_comision) {
        this.importe_comision = importe_comision;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getFecha_venci() {
        return fecha_venci;
    }

    public void setFecha_venci(String fecha_venci) {
        this.fecha_venci = fecha_venci;
    }

    public int getTipo_tarjeta() {
        return tipo_tarjeta;
    }

    public void setTipo_tarjeta(int tipo_tarjeta) {
        this.tipo_tarjeta = tipo_tarjeta;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Boolean getValidaTarjeta() {
        return validaTarjeta;
    }

    public void setValidaTarjeta(Boolean validaTarjeta) {
        this.validaTarjeta = validaTarjeta;
    }

    //parte validar tarjeta
    private  Boolean validaTarjeta;
    //fin parte de validar tarjeta

    public UsuarioEntity() {
    }

    public UsuarioEntity (Context context) {
        this.contexto = context;
    }

    /*public UsuarioEntity(String usuario, String clave, String usuarioId) {
        this.Usuario = usuario;
        this.Clave = clave;
        this.UsuarioId = usuarioId;
    }

    protected UsuarioEntity(Parcel in) {
        String[] data= new String[3];
        in.readStringArray(data);
        UsuarioId = data[0];
        Usuario = data[1];
        Clave = data[2];
    }*/

    public UsuarioEntity(String usuario, String clave, String usuarioId) {
        this.Usuario = usuario;
        this.Clave = clave;
        this.UsuarioId = usuarioId;
    }

    protected UsuarioEntity(Parcel in) {
        String[] data= new String[3];
        in.readStringArray(data);
        UsuarioId = data[0];
        Usuario = data[1];
        Clave = data[2];
    }


    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public String getRedesSociales() {
        return redesSociales;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public void setRedesSociales(String redesSociales) {
        this.redesSociales = redesSociales;
    }

    public String getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        UsuarioId = usuarioId;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMovil() {
        return Movil;
    }

    public void setMovil(String movil) {
        Movil = movil;
    }

    public String getClaveAcceso() {
        return ClaveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        ClaveAcceso = claveAcceso;
    }

    public String getConfirmClaveAcceso() {
        return ConfirmClaveAcceso;
    }

    public void setConfirmClaveAcceso(String confirmClaveAcceso) {
        ConfirmClaveAcceso = confirmClaveAcceso;
    }

    public String getSegundaClaveAcceso() {
        return SegundaClaveAcceso;
    }

    public void setSegundaClaveAcceso(String segundaClaveAcceso) {
        SegundaClaveAcceso = segundaClaveAcceso;
    }

    public String getConfirmSegundaClaveAcceso() {
        return ConfirmSegundaClaveAcceso;
    }

    public void setConfirmSegundaClaveAcceso(String confirmSegundaClaveAcceso) {
        ConfirmSegundaClaveAcceso = confirmSegundaClaveAcceso;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getConfirmNumeroTarjeta() {
        return confirmNumeroTarjeta;
    }

    public void setConfirmNumeroTarjeta(String confirmNumeroTarjeta) {
        this.confirmNumeroTarjeta = confirmNumeroTarjeta;
    }

    public Boolean getInsertaTarjeta() {
        return insertaTarjeta;
    }

    public void setInsertaTarjeta(Boolean insertaTarjeta) {
        this.insertaTarjeta = insertaTarjeta;
    }

    public int getCantidadTarjetasInsertadas() {
        return cantidadTarjetasInsertadas;
    }

    public void setCantidadTarjetasInsertadas(int cantidadTarjetasInsertadas) {
        this.cantidadTarjetasInsertadas = cantidadTarjetasInsertadas;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.UsuarioId,this.Usuario,this.Clave});
    }

    public static final Parcelable.Creator<UsuarioEntity> CREATOR = new Creator<UsuarioEntity>() {
        @Override
        public UsuarioEntity createFromParcel(Parcel source) {
            return new UsuarioEntity(source);
        }

        @Override
        public UsuarioEntity[] newArray(int size) {
            return new UsuarioEntity[size];
        }
    };
}

