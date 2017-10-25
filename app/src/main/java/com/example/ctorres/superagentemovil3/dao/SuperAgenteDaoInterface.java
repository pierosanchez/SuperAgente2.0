package com.example.ctorres.superagentemovil3.dao;

/**
 * Created by CTORRES on 05/05/2017.
 */

import android.telecom.Call;

import com.example.ctorres.superagentemovil3.entity.BancosEntity;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.ClubsEntity;
import com.example.ctorres.superagentemovil3.entity.CuentaEntity;
import com.example.ctorres.superagentemovil3.entity.EmpresasServiciosEntity;
import com.example.ctorres.superagentemovil3.entity.MonedaEntity;
import com.example.ctorres.superagentemovil3.entity.OperadorEntity;
import com.example.ctorres.superagentemovil3.entity.ServiciosPublicEntity;
import com.example.ctorres.superagentemovil3.entity.TarjetaBinEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.GET;

public interface SuperAgenteDaoInterface {
    ArrayList<UsuarioEntity> getClienteReniec(String numDniCliente);
    UsuarioEntity getUsuarioLogin(String dni, String Cnombre, String CapellidoP, String CapellidoM, String Csexo, String Cemail, String Cmovil);
    UsuarioEntity getClaveAcceso(String usuarioId, String claveAcceso, String pregunta, String segundaClaveAcceso);
    UsuarioEntity getInsertarTarjeta(String usuarioId, String numeroTarjeta);
    UsuarioEntity getValidarTarjeta(String usuarioId, String numeroTarjeta, String fecha_venci, int cod_tipo_tarjeta, int cod_emisor_tarjeta, int banco_tarjeta);
    BeneficiarioEntity getInsertarBeneficiario(String nombre, String apellido, String celular1, String celular2, String email, String fechaNac, String pass, String dni, String idcliente, int tip_abono); //, String cod_interbancario, String num_tarjeta_beneficiario, int emisor_tarjeta, int cod_banco, int cod_tipo_cuenta);
    CuentaEntity getInsertarCuenta(String numCuenta, String idcliente, int cod_banco, int tipo_moneda);
    ArrayList<BeneficiarioEntity> listarBeneficiario(String idcliente);
    ArrayList<BeneficiarioEntity> listarCuentaBeneficiario(String idcliente);
    ArrayList<BeneficiarioEntity> listarTarjetasBeneficiario(String idcliente);
    UsuarioEntity getUsuarioLog(String Cmovil, String Cpassword);
    UsuarioEntity getUsuarioDomicilioLogin(String idcliente, String departamento, String provincia, String distrito, String direccion, String tel_fijo);
    ArrayList<UsuarioEntity> listarComisionDeliverySoles();
    ArrayList<UsuarioEntity> listarTarjetas(String idCliente);
    ArrayList<CuentaEntity> listarCuentasUsuario(String idCliente);
    BeneficiarioEntity eliminarBeneficiarioUsuario(String usuarioId);
    BeneficiarioEntity getInsertarCuentasBeneficiario(String dni_b, String cod_interbancario, String num_tarjeta_beneficiario, int cod_emisor_tarjeta, int cod_banco, int cod_tipo_cuenta);
    UsuarioEntity eliminarTarjetaUsuario(String benef_dni);
    CuentaEntity eliminarCuentaUsuario(String id_tarjeta);
    BeneficiarioEntity actualizarBeneficiario(String name, String lastname, String cel1, String cel2, String e_mail, String birth, String password, String DNI, int abonos);
    UsuarioEntity actualizarTarjeta(String idcard, String date, int emisor_card, int kind_card, String num_card, int cod_bank);
    CuentaEntity actualizarCuenta(String num_cuenta, String id_cuenta, int cod_banco);
    BeneficiarioEntity eliminarCuentasBeneficiario(int id_cuenta_benef);
    BeneficiarioEntity actualizarCuentaBeneficiario(String cci_beneficiario, int id_cuenta_benef);
    BeneficiarioEntity actualizarTarjetaBeneficiario(String num_tarjeta_beneficiario, int cod_banco, int cod_emisor_tarjeta, int id_cuenta_benef, int cod_tipo_cuenta);
    ArrayList<EmpresasServiciosEntity> listarEmpresasServicios();
    ArrayList<ServiciosPublicEntity> ListarServiciosPublicos();
    ArrayList<BeneficiarioEntity> DetalleBeneficiario(String idcliente);
    ArrayList<UsuarioEntity> detalleClaveAcceso(String idCliente);
    UsuarioEntity actualizarClaveAcceso(String clave, String idcliente, String clave_nueva, String resp_pregunta);
    UsuarioEntity ClaveAccesoOlvidada(String idcliente, String respuesta, String newPass);
    UsuarioEntity actualizarDomicilioCliente(String departamento, String provincia, String distrito, String direccion, String tel_fijo, String idcliente);
    ArrayList<ClubsEntity> ListadoClubs();
    BeneficiarioEntity IngresarCuentaBeneficiario(String beneficiario_DNI, String beneficiario_cuenta);
    BeneficiarioEntity IngresarTarjetaBeneficiario(String DNI_BENEF, String tarjeta_BENEF, int cod_emisor_tarjeta_BENEF, int cod_banco_BENEF, int cod_tipo_cuenta_BENEF);
    UsuarioEntity validarCelularCliente(String numCel);
    ArrayList<UsuarioEntity> ListadoDomicilioUsuario(String usuario_id);
    ArrayList<BancosEntity> ListadoBancos();
    ArrayList<TarjetaBinEntity> getDatosBinTarjeta(String numero_Tarjeta);
    ArrayList<UsuarioEntity> DetalleTarjeta(String id_Tarjeta);
    ArrayList<CuentaEntity> DetalleCuenta(String id_Tarjeta);
    ArrayList<MonedaEntity> ListarMoneda();
    ArrayList<OperadorEntity> ListarOperador();
}
