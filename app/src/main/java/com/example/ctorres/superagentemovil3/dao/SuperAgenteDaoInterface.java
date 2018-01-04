package com.example.ctorres.superagentemovil3.dao;

/**
 * Created by CTORRES on 05/05/2017.
 */

import com.example.ctorres.superagentemovil3.entity.BancosEntity;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.ClubsEntity;
import com.example.ctorres.superagentemovil3.entity.ComercioEntity;
import com.example.ctorres.superagentemovil3.entity.CuentaEntity;
import com.example.ctorres.superagentemovil3.entity.CuotasEntity;
import com.example.ctorres.superagentemovil3.entity.DeudasTarjetas;
import com.example.ctorres.superagentemovil3.entity.EmpresasServiciosEntity;
import com.example.ctorres.superagentemovil3.entity.MonedaEntity;
import com.example.ctorres.superagentemovil3.entity.NumeroUnico;
import com.example.ctorres.superagentemovil3.entity.OperadorEntity;
import com.example.ctorres.superagentemovil3.entity.ServiciosPublicEntity;
import com.example.ctorres.superagentemovil3.entity.TarjetaBinEntity;
import com.example.ctorres.superagentemovil3.entity.TipoTarjetaEntity;
import com.example.ctorres.superagentemovil3.entity.UbigeoEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.entity.VoucherPagoConsumoEntity;
import com.example.ctorres.superagentemovil3.entity.VoucherPagoRecargaEntity;
import com.example.ctorres.superagentemovil3.entity.VoucherPagoServicioEntity;
import com.example.ctorres.superagentemovil3.entity.VoucherPagoTarjetaCreditoEntity;
import com.example.ctorres.superagentemovil3.entity.VoucherTransferenciasEntity;

import java.util.ArrayList;

public interface SuperAgenteDaoInterface {
    ArrayList<UsuarioEntity> getClienteReniec(String numDniCliente);
    UsuarioEntity getUsuarioLogin(String dni, String Cnombre, String CapellidoP, String CapellidoM, String Csexo, String Cemail, String Cmovil);
    UsuarioEntity getClaveAcceso(String usuarioId, String claveAcceso, String pregunta, String segundaClaveAcceso);
    UsuarioEntity getInsertarTarjeta(String usuarioId, String numeroTarjeta);
    UsuarioEntity getValidarTarjeta(String usuarioId, String numeroTarjeta, String fecha_venci, int cod_tipo_tarjeta, int cod_emisor_tarjeta, int banco_tarjeta, String validacionTarjeta);
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
    ArrayList<ComercioEntity> ListarComercio();
    UsuarioEntity InsertarFirmaCliente(String img, String idcliente);
    ArrayList<CuotasEntity> ListarCuota();
    ArrayList<TipoTarjetaEntity> ListarTipoTarjeta();
    ArrayList<DeudasTarjetas> ListadoDeudasTarjetas(String idCliente);
    ArrayList<NumeroUnico> getNumeroUnico();
    VoucherPagoRecargaEntity ingresarVoucherRecargas(String numero_unicoR, String fechaR, String horaR, String recarga, String forma_pagoR, String importeR, String comision_recarga, String totalR, String bancoR, String nro_tarjetaR, String tipo_monedaR, String idclienteR);
    VoucherPagoServicioEntity ingresarVoucherServicio(String numero_unicoS, String fechaS, String horaS, String servicio, String tipo_servicio, String cod_clienteS, String nombre_tipo_servicio, String persona_paga, String dni_persona, String forma_pagoS, String importeS, String comisionS, String totalS);
    VoucherPagoConsumoEntity ingresarVoucherPagoConsumo(String numero_unicoPC, String fechaPC, String horaPC, String importePC, String nro_tarjetaPC, String marca_tarjetaPC, String banco_tarjetaPC, String nombre_comercioPC, String direccion_comercioPC, String distrito_comercioPC, String idclientePC, String idcomercioPC);
    VoucherTransferenciasEntity ingresarVoucherTransferencias(String numero_unicoT, String fechaT, String horaT, String remitente, String bancoT, String tarjeta_cargoT, String importe_transferencia, String monto_comision, String comision_delivery, String comision_cheque, String importe_total, String beneficiario, String tipo_transferencia, String idclienteT, String tipo_monedaT);
    VoucherPagoTarjetaCreditoEntity ingresarVoucherPagoTarjetaCredito(String numero_unicoPT, String fechaPT, String horaPT, String tarjeta_pagadaPT, String bancoPT, String tarjeta_cargoPT, String banco_tarjeta_cargo, String importePT, String tipo_monedaPT, String idclientePT);
    ArrayList<DeudasTarjetas> ingresarVoucherPagoTarjetaSoles(String idcliente);
    ArrayList<DeudasTarjetas> ingresarVoucherPagoTarjetaCredito(String idcliente);
    ArrayList<ComercioEntity> detalleComercio(String idComercio);
    VoucherPagoServicioEntity InsertarNumeroUnicoServicio(String numeroUni, String id_client);
    VoucherTransferenciasEntity getNumeroUnicoTransferencias(String numeroUni);
    VoucherPagoTarjetaCreditoEntity getNumeroUnicoTarjeta(String numeroUni);
    VoucherPagoConsumoEntity getNumeroUnicoConsumos(String numeroUni);
    VoucherPagoServicioEntity getNumeroUnicoServicios(String numeroUni);
    VoucherPagoRecargaEntity getNumeroUnicoRecargas(String numeroUni);
    ArrayList<UbigeoEntity> ListarDepartamento();
    ArrayList<UbigeoEntity> ListarDistritoUbigeo(String ubigeo1, String ubigeo2);
    ArrayList<UbigeoEntity> ListarProvinciaUbigeo(String ubigeo1);
}
