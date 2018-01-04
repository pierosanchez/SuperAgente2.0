package com.example.ctorres.superagentemovil3.dao;

/**
 * Created by CTORRES on 05/05/2017.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

import com.example.ctorres.superagentemovil3.entity.*;
import com.example.ctorres.superagentemovil3.utils.Constante;
import com.example.ctorres.superagentemovil3.utils.Utils;

/**
 * Created by Jove on 22/03/2017.
 */
public class SuperAgenteDaoImplement implements SuperAgenteDaoInterface {

    private Utils utils;

    public SuperAgenteDaoImplement() {
        utils = new Utils();
    }

    @Override
    public ArrayList<UbigeoEntity> ListarProvinciaUbigeo(String ubigeo1) {
        ArrayList<UbigeoEntity> listaUsuario = new ArrayList<>();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ListarProvincia/?ubigeo1=" + ubigeo1;

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        UbigeoEntity usuarioEntity = new UbigeoEntity();
                        usuarioEntity.setUbigeo1(utils.getValueStringOrNull(jsonObject, "ubigeo1"));
                        usuarioEntity.setUbigeo2(utils.getValueStringOrNull(jsonObject, "ubigeo2"));
                        usuarioEntity.setUbigeo3(utils.getValueStringOrNull(jsonObject, "ubigeo3"));
                        usuarioEntity.setDepartamento(utils.getValueStringOrNull(jsonObject, "departamento"));
                        usuarioEntity.setDistrito(utils.getValueStringOrNull(jsonObject, "distrito"));
                        usuarioEntity.setProvincia(utils.getValueStringOrNull(jsonObject, "provincia"));
                        listaUsuario.add(usuarioEntity);
                    }
                } else {
                    listaUsuario = null;
                }
            } else {
                listaUsuario = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaUsuario;
    }

    @Override
    public ArrayList<UbigeoEntity> ListarDistritoUbigeo(String ubigeo1, String ubigeo2) {
        ArrayList<UbigeoEntity> listaUsuario = new ArrayList<>();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ListarDistrito/?ubigeo1=" + ubigeo1 + "&ubigeo2=" + ubigeo2;

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        UbigeoEntity usuarioEntity = new UbigeoEntity();
                        usuarioEntity.setUbigeo1(utils.getValueStringOrNull(jsonObject, "ubigeo1"));
                        usuarioEntity.setUbigeo2(utils.getValueStringOrNull(jsonObject, "ubigeo2"));
                        usuarioEntity.setUbigeo3(utils.getValueStringOrNull(jsonObject, "ubigeo3"));
                        usuarioEntity.setDepartamento(utils.getValueStringOrNull(jsonObject, "departamento"));
                        usuarioEntity.setDistrito(utils.getValueStringOrNull(jsonObject, "distrito"));
                        usuarioEntity.setProvincia(utils.getValueStringOrNull(jsonObject, "provincia"));
                        listaUsuario.add(usuarioEntity);
                    }
                } else {
                    listaUsuario = null;
                }
            } else {
                listaUsuario = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaUsuario;
    }

    @Override
    public ArrayList<UbigeoEntity> ListarDepartamento() {
        ArrayList<UbigeoEntity> listaUsuario = new ArrayList<>();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ListarDepartamento/?vac03=";

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        UbigeoEntity usuarioEntity = new UbigeoEntity();
                        usuarioEntity.setUbigeo1(utils.getValueStringOrNull(jsonObject, "ubigeo1"));
                        usuarioEntity.setUbigeo2(utils.getValueStringOrNull(jsonObject, "ubigeo2"));
                        usuarioEntity.setUbigeo3(utils.getValueStringOrNull(jsonObject, "ubigeo3"));
                        usuarioEntity.setDepartamento(utils.getValueStringOrNull(jsonObject, "departamento"));
                        usuarioEntity.setDistrito(utils.getValueStringOrNull(jsonObject, "distrito"));
                        usuarioEntity.setProvincia(utils.getValueStringOrNull(jsonObject, "provincia"));
                        listaUsuario.add(usuarioEntity);
                    }
                } else {
                    listaUsuario = null;
                }
            } else {
                listaUsuario = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaUsuario;
    }

    @Override
    public VoucherTransferenciasEntity getNumeroUnicoTransferencias(String numeroUni) {
        VoucherTransferenciasEntity tipoTarjetaEntity = new VoucherTransferenciasEntity();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/getNumeroUnicoTransferencias/?numeroUniTra=" + numeroUni;

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null){
                if (jsonArray.length() > 0){
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        tipoTarjetaEntity.setNumeroUnico(utils.getValueStringOrNull(jsonObject, "numero_unico"));
                    }
                }else {
                    tipoTarjetaEntity = null;
                }
            } else {
                tipoTarjetaEntity = null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return tipoTarjetaEntity;
    }

    @Override
    public VoucherPagoTarjetaCreditoEntity getNumeroUnicoTarjeta(String numeroUni) {
        VoucherPagoTarjetaCreditoEntity tipoTarjetaEntity = new VoucherPagoTarjetaCreditoEntity();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/getNumeroUnicoTarjetas/?numeroUniT=" + numeroUni;

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null){
                if (jsonArray.length() > 0){
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        tipoTarjetaEntity.setNumeroUnico(utils.getValueStringOrNull(jsonObject, "numero_unico"));
                    }
                }else {
                    tipoTarjetaEntity = null;
                }
            } else {
                tipoTarjetaEntity = null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return tipoTarjetaEntity;
    }

    @Override
    public VoucherPagoConsumoEntity getNumeroUnicoConsumos(String numeroUni) {
        VoucherPagoConsumoEntity tipoTarjetaEntity = new VoucherPagoConsumoEntity();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/getNumeroUnicoConsumos/?numeroUniC=" + numeroUni;

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null){
                if (jsonArray.length() > 0){
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        tipoTarjetaEntity.setNumeroUnico(utils.getValueStringOrNull(jsonObject, "numero_unico"));
                    }
                }else {
                    tipoTarjetaEntity = null;
                }
            } else {
                tipoTarjetaEntity = null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return tipoTarjetaEntity;
    }

    @Override
    public VoucherPagoServicioEntity getNumeroUnicoServicios(String numeroUni) {
        VoucherPagoServicioEntity tipoTarjetaEntity = new VoucherPagoServicioEntity();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/getNumeroUnicoServicios/?numeroUniS=" + numeroUni;

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null){
                if (jsonArray.length() > 0){
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        tipoTarjetaEntity.setNumeroUnico(utils.getValueStringOrNull(jsonObject, "numero_unico"));
                    }
                }else {
                    tipoTarjetaEntity = null;
                }
            } else {
                tipoTarjetaEntity = null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return tipoTarjetaEntity;
    }

    @Override
    public VoucherPagoRecargaEntity getNumeroUnicoRecargas(String numeroUni) {
        VoucherPagoRecargaEntity tipoTarjetaEntity = new VoucherPagoRecargaEntity();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/getNumeroUnicoRecargas/?numeroUniR=" + numeroUni;

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null){
                if (jsonArray.length() > 0){
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        tipoTarjetaEntity.setNumeroUnico(utils.getValueStringOrNull(jsonObject, "numero_unico"));
                    }
                }else {
                    tipoTarjetaEntity = null;
                }
            } else {
                tipoTarjetaEntity = null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return tipoTarjetaEntity;
    }

    @Override
    public VoucherPagoServicioEntity InsertarNumeroUnicoServicio(String numeroUni, String id_client) {
        VoucherPagoServicioEntity tipoTarjetaEntity = new VoucherPagoServicioEntity();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/InsertarNumeroUnicoServicio/?numeroUni=" + numeroUni + "&id_client=" + id_client;

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null){
                if (jsonArray.length() > 0){
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        tipoTarjetaEntity.setNumeroUnico(utils.getValueStringOrNull(jsonObject, "error"));
                    }
                }else {
                    tipoTarjetaEntity = null;
                }
            } else {
                tipoTarjetaEntity = null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return tipoTarjetaEntity;
    }

    @Override
    public ArrayList<ComercioEntity> detalleComercio(String idComercio) {
        ArrayList<ComercioEntity> listaTipoTarjeta = new ArrayList<>();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/DetalleComercioQR/?PKcomercio=" + idComercio;

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null){
                if (jsonArray.length() > 0){
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ComercioEntity tipoTarjetaEntity = new ComercioEntity();
                        tipoTarjetaEntity.setDesc_distrito(utils.getValueStringOrNull(jsonObject, "distrito"));
                        tipoTarjetaEntity.setDireccion_comercio(utils.getValueStringOrNull(jsonObject, "direccion_comercio"));
                        tipoTarjetaEntity.setId_comercio(utils.getValueStringOrNull(jsonObject, "id_comercio"));
                        tipoTarjetaEntity.setRaz_social_comercio(utils.getValueStringOrNull(jsonObject, "raz_social_comercio"));
                        listaTipoTarjeta.add(tipoTarjetaEntity);
                    }
                }else {
                    listaTipoTarjeta = null;
                }
            } else {
                listaTipoTarjeta = null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return listaTipoTarjeta;
    }

    @Override
    public ArrayList<DeudasTarjetas> ingresarVoucherPagoTarjetaSoles(String idcliente) {
        ArrayList<DeudasTarjetas> listaTipoTarjeta = new ArrayList<>();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ListadoDeudasTarjetasSoles/?PrKCliente=" + idcliente;

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null){
                if (jsonArray.length() > 0){
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        DeudasTarjetas tipoTarjetaEntity = new DeudasTarjetas();
                        tipoTarjetaEntity.setCodTipoMoneda(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "cod_tipo_moneda")));
                        tipoTarjetaEntity.setMontoMensual(Double.parseDouble(utils.getValueStringOrNull(jsonObject, "monto_mensual")));
                        tipoTarjetaEntity.setMontoTotal(Double.parseDouble(utils.getValueStringOrNull(jsonObject, "monto_total")));
                        tipoTarjetaEntity.setMontoMinimo(Double.parseDouble(utils.getValueStringOrNull(jsonObject, "monto_minimo")));
                        tipoTarjetaEntity.setSignoMoneda(utils.getValueStringOrNull(jsonObject, "signo_moneda"));
                        tipoTarjetaEntity.setIdDeudaTarjetaCliente(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "id_deuda_tarjeta_cliente")));
                        listaTipoTarjeta.add(tipoTarjetaEntity);
                    }
                }else {
                    listaTipoTarjeta = null;
                }
            } else {
                listaTipoTarjeta = null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return listaTipoTarjeta;
    }

    @Override
    public ArrayList<DeudasTarjetas> ingresarVoucherPagoTarjetaCredito(String idcliente) {
        ArrayList<DeudasTarjetas> listaTipoTarjeta = new ArrayList<>();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ListadoDeudasTarjetasDolares/?PriKCliente=" + idcliente;

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null){
                if (jsonArray.length() > 0){
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        DeudasTarjetas tipoTarjetaEntity = new DeudasTarjetas();
                        tipoTarjetaEntity.setCodTipoMoneda(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "cod_tipo_moneda")));
                        tipoTarjetaEntity.setMontoMensual(Double.parseDouble(utils.getValueStringOrNull(jsonObject, "monto_mensual")));
                        tipoTarjetaEntity.setMontoTotal(Double.parseDouble(utils.getValueStringOrNull(jsonObject, "monto_total")));
                        tipoTarjetaEntity.setMontoMinimo(Double.parseDouble(utils.getValueStringOrNull(jsonObject, "monto_minimo")));
                        tipoTarjetaEntity.setSignoMoneda(utils.getValueStringOrNull(jsonObject, "signo_moneda"));
                        tipoTarjetaEntity.setIdDeudaTarjetaCliente(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "id_deuda_tarjeta_cliente")));
                        listaTipoTarjeta.add(tipoTarjetaEntity);
                    }
                }else {
                    listaTipoTarjeta = null;
                }
            } else {
                listaTipoTarjeta = null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return listaTipoTarjeta;
    }

    @Override
    public VoucherPagoTarjetaCreditoEntity ingresarVoucherPagoTarjetaCredito(String numero_unicoPT, String fechaPT, String horaPT, String tarjeta_pagadaPT, String bancoPT, String tarjeta_cargoPT, String banco_tarjeta_cargo, String importePT, String tipo_monedaPT, String idclientePT) {
        VoucherPagoTarjetaCreditoEntity voucherPagoServicio;

        try {
            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/InsertarVoucherPagoTarjetaCredito/?numero_unicoPT=" + URLEncoder.encode(numero_unicoPT, "UTF-8") +
                    "&fechaPT=" + URLEncoder.encode(fechaPT, "UTF-8") +
                    "&horaPT=" + URLEncoder.encode(horaPT, "UTF-8") +
                    "&tarjeta_pagadaPT=" + URLEncoder.encode(tarjeta_pagadaPT, "UTF-8") +
                    "&bancoPT=" + URLEncoder.encode(bancoPT, "UTF-8") +
                    "&tarjeta_cargoPT=" + URLEncoder.encode(tarjeta_cargoPT, "UTF-8") +
                    "&banco_tarjeta_cargo=" + URLEncoder.encode(banco_tarjeta_cargo, "UTF-8") +
                    "&importePT=" + URLEncoder.encode(importePT, "UTF-8") +
                    "&tipo_monedaPT=" + URLEncoder.encode(tipo_monedaPT, "UTF-8") +
                    "&idclientePT=" + URLEncoder.encode(idclientePT, "UTF-8");

            voucherPagoServicio = new VoucherPagoTarjetaCreditoEntity();
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    VoucherPagoTarjetaCreditoEntity voucherPagoServicioEntity = new VoucherPagoTarjetaCreditoEntity();
                    voucherPagoServicioEntity.setNumeroUnico(numero_unicoPT);
                    voucherPagoServicioEntity.setFecha(fechaPT);
                    voucherPagoServicioEntity.setHora(horaPT);
                    voucherPagoServicioEntity.setTarjetaPagada(tarjeta_pagadaPT);
                    voucherPagoServicioEntity.setBanco(bancoPT);
                    voucherPagoServicioEntity.setTarjetaCargo(tarjeta_cargoPT);
                    voucherPagoServicioEntity.setBancoTarjetaCargo(banco_tarjeta_cargo);
                    voucherPagoServicioEntity.setImporte(importePT);
                    voucherPagoServicioEntity.setTipoMoneda(tipo_monedaPT);
                    voucherPagoServicioEntity.setIdCliente(idclientePT);
                } else {
                    voucherPagoServicio = null;
                }
            } else {
                voucherPagoServicio = null;
            }
        } catch (Exception e){
            e.printStackTrace();
            voucherPagoServicio = null;
        }
        return voucherPagoServicio;
    }

    @Override
    public VoucherTransferenciasEntity ingresarVoucherTransferencias(String numero_unicoT, String fechaT, String horaT, String remitente, String bancoT, String tarjeta_cargoT, String importe_transferencia, String monto_comision, String comision_delivery, String comision_cheque, String importe_total, String beneficiario, String tipo_transferencia, String idclienteT, String tipo_monedaT) {
        VoucherTransferenciasEntity voucherPagoServicio ;

        try {
            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/InsertarVoucherTransferencias/?numero_unicoT=" + URLEncoder.encode(numero_unicoT, "UTF-8") +
                    "&fechaT=" + URLEncoder.encode(fechaT, "UTF-8") +
                    "&horaT=" + URLEncoder.encode(horaT, "UTF-8") +
                    "&remitente=" + URLEncoder.encode(remitente, "UTF-8") +
                    "&bancoT=" + URLEncoder.encode(bancoT, "UTF-8") +
                    "&tarjeta_cargoT=" + URLEncoder.encode(tarjeta_cargoT, "UTF-8") +
                    "&importe_transferencia=" + URLEncoder.encode(importe_transferencia, "UTF-8") +
                    "&monto_comision=" + URLEncoder.encode(monto_comision, "UTF-8") +
                    "&comision_delivery=" + URLEncoder.encode(comision_delivery, "UTF-8") +
                    "&comision_cheque=" + URLEncoder.encode(comision_cheque, "UTF-8") +
                    "&importe_total=" + URLEncoder.encode(importe_total, "UTF-8") +
                    "&beneficiario=" + URLEncoder.encode(beneficiario, "UTF-8") +
                    "&tipo_transferencia=" + URLEncoder.encode(tipo_transferencia, "UTF-8") +
                    "&idclienteT=" + URLEncoder.encode(idclienteT, "UTF-8") +
                    "&tipo_monedaT=" + URLEncoder.encode(tipo_monedaT, "UTF-8");

            voucherPagoServicio = new VoucherTransferenciasEntity();
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    VoucherTransferenciasEntity voucherPagoServicioEntity = new VoucherTransferenciasEntity();
                    voucherPagoServicioEntity.setNumeroUnico(numero_unicoT);
                    voucherPagoServicioEntity.setFecha(fechaT);
                    voucherPagoServicioEntity.setHora(horaT);
                    voucherPagoServicioEntity.setRemitente(remitente);
                    voucherPagoServicioEntity.setBanco(bancoT);
                    voucherPagoServicioEntity.setTarjetaCargo(tarjeta_cargoT);
                    voucherPagoServicioEntity.setImporteTransferencia(importe_transferencia);
                    voucherPagoServicioEntity.setMontoComision(monto_comision);
                    voucherPagoServicioEntity.setComisionDelivery(comision_delivery);
                    voucherPagoServicioEntity.setComisionCheque(comision_cheque);
                    voucherPagoServicioEntity.setImporteTotal(importe_total);
                    voucherPagoServicioEntity.setBeneficiario(beneficiario);
                    voucherPagoServicioEntity.setTipoTransferencia(tipo_transferencia);
                    voucherPagoServicioEntity.setIdCliente(idclienteT);
                    voucherPagoServicioEntity.setTipomoneda(tipo_monedaT);
                } else {
                    voucherPagoServicio = null;
                }
            } else {
                voucherPagoServicio = null;
            }
        } catch (Exception e){
            e.printStackTrace();
            voucherPagoServicio = null;
        }
        return voucherPagoServicio;
    }

    @Override
    public VoucherPagoConsumoEntity ingresarVoucherPagoConsumo(String numero_unicoPC, String fechaPC, String horaPC, String importePC, String nro_tarjetaPC, String marca_tarjetaPC, String banco_tarjetaPC, String nombre_comercioPC, String direccion_comercioPC, String distrito_comercioPC, String idclientePC, String idcomercioPC) {
        VoucherPagoConsumoEntity voucherPagoServicio ;

        try {
            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/InsertarVoucherPagoConsumos/?numero_unicoPC=" + URLEncoder.encode(numero_unicoPC, "UTF-8") +
                    "&fechaPC=" + URLEncoder.encode(fechaPC, "UTF-8") +
                    "&horaPC=" + URLEncoder.encode(horaPC, "UTF-8") +
                    "&importePC=" + URLEncoder.encode(importePC, "UTF-8") +
                    "&nro_tarjetaPC=" + URLEncoder.encode(nro_tarjetaPC, "UTF-8") +
                    "&marca_tarjetaPC=" + URLEncoder.encode(marca_tarjetaPC, "UTF-8") +
                    "&banco_tarjetaPC=" + URLEncoder.encode(banco_tarjetaPC, "UTF-8") +
                    "&nombre_comercioPC=" + URLEncoder.encode(nombre_comercioPC, "UTF-8") +
                    "&direccion_comercioPC=" + URLEncoder.encode(direccion_comercioPC, "UTF-8") +
                    "&distrito_comercioPC=" + URLEncoder.encode(distrito_comercioPC, "UTF-8") +
                    "&idclientePC=" + URLEncoder.encode(idclientePC, "UTF-8") +
                    "&idcomercioPC=" + URLEncoder.encode(idcomercioPC, "UTF-8");

            voucherPagoServicio = new VoucherPagoConsumoEntity();
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    VoucherPagoConsumoEntity voucherPagoServicioEntity = new VoucherPagoConsumoEntity();
                    voucherPagoServicioEntity.setNumeroUnico(numero_unicoPC);
                    voucherPagoServicioEntity.setFecha(fechaPC);
                    voucherPagoServicioEntity.setHora(horaPC);
                    voucherPagoServicioEntity.setImporte(importePC);
                    voucherPagoServicioEntity.setNroTarjeta(nro_tarjetaPC);
                    voucherPagoServicioEntity.setMarcaTarjeta(marca_tarjetaPC);
                    voucherPagoServicioEntity.setBancoTarjeta(banco_tarjetaPC);
                    voucherPagoServicioEntity.setNombreComercio(nombre_comercioPC);
                    voucherPagoServicioEntity.setDireccionComercio(direccion_comercioPC);
                    voucherPagoServicioEntity.setDistritoComercio(distrito_comercioPC);
                    voucherPagoServicioEntity.setIdCliente(idclientePC);
                } else {
                    voucherPagoServicio = null;
                }
            } else {
                voucherPagoServicio = null;
            }
        } catch (Exception e){
            e.printStackTrace();
            voucherPagoServicio = null;
        }
        return voucherPagoServicio;
    }

    @Override
    public VoucherPagoServicioEntity ingresarVoucherServicio(String numero_unicoS, String fechaS, String horaS, String servicio, String tipo_servicio, String cod_clienteS, String nombre_tipo_servicio, String persona_paga, String dni_persona, String forma_pagoS, String importeS, String comisionS, String totalS) {
        VoucherPagoServicioEntity voucherPagoServicio ;

        try {
            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/IngresarVoucherPagoServicio/?numero_unicoS=" + URLEncoder.encode(numero_unicoS, "UTF-8") +
                    "&fechaS=" + URLEncoder.encode(fechaS, "UTF-8") +
                    "&horaS=" + URLEncoder.encode(horaS, "UTF-8") +
                    "&servicio=" + URLEncoder.encode(servicio, "UTF-8") +
                    "&tipo_servicio=" + URLEncoder.encode(tipo_servicio, "UTF-8") +
                    "&cod_clienteS=" + URLEncoder.encode(cod_clienteS, "UTF-8") +
                    "&nombre_tipo_servicio=" + URLEncoder.encode(nombre_tipo_servicio, "UTF-8") +
                    "&persona_paga=" + URLEncoder.encode(persona_paga, "UTF-8") +
                    "&dni_persona=" + URLEncoder.encode(dni_persona, "UTF-8") +
                    "&forma_pagoS=" + URLEncoder.encode(forma_pagoS, "UTF-8") +
                    "&importeS=" + URLEncoder.encode(importeS, "UTF-8") +
                    "&comisionS=" + URLEncoder.encode(comisionS, "UTF-8") +
                    "&totalS=" + URLEncoder.encode(totalS, "UTF-8");

            voucherPagoServicio = new VoucherPagoServicioEntity();
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    VoucherPagoServicioEntity voucherPagoServicioEntity = new VoucherPagoServicioEntity();
                    voucherPagoServicioEntity.setNumeroUnico(numero_unicoS);
                    voucherPagoServicioEntity.setFecha(fechaS);
                    voucherPagoServicioEntity.setHora(horaS);
                    voucherPagoServicioEntity.setServicio(servicio);
                    voucherPagoServicioEntity.setTipoServicio(tipo_servicio);
                    voucherPagoServicioEntity.setCodCliente(cod_clienteS);
                    voucherPagoServicioEntity.setNombreTipoServicio(nombre_tipo_servicio);
                    voucherPagoServicioEntity.setPersonaPaga(persona_paga);
                    voucherPagoServicioEntity.setDniPersona(dni_persona);
                    voucherPagoServicioEntity.setFormaPago(forma_pagoS);
                    voucherPagoServicioEntity.setImporte(importeS);
                    voucherPagoServicioEntity.setComision(comisionS);
                    voucherPagoServicioEntity.setTotal(totalS);
                } else {
                    voucherPagoServicio = null;
                }
            } else {
                voucherPagoServicio = null;
            }
        } catch (Exception e){
            e.printStackTrace();
            voucherPagoServicio = null;
        }
        return voucherPagoServicio;
    }

    @Override
    public VoucherPagoRecargaEntity ingresarVoucherRecargas(String numero_unicoR, String fechaR, String horaR, String recarga, String forma_pagoR, String importeR, String comision_recarga, String totalR, String bancoR, String nro_tarjetaR, String tipo_monedaR, String idclienteR) {
        VoucherPagoRecargaEntity voucherPagoServicio ;

        try {
            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/InsertarVoucherRecargas/?numero_unicoR=" + URLEncoder.encode(numero_unicoR, "UTF-8") +
                    "&fechaR=" + URLEncoder.encode(fechaR, "UTF-8") +
                    "&horaR=" + URLEncoder.encode(horaR, "UTF-8") +
                    "&recarga=" + URLEncoder.encode(recarga, "UTF-8") +
                    "&forma_pagoR=" + URLEncoder.encode(forma_pagoR, "UTF-8") +
                    "&importeR=" + URLEncoder.encode(importeR, "UTF-8") +
                    "&comision_recarga=" + URLEncoder.encode(comision_recarga, "UTF-8") +
                    "&totalR=" + URLEncoder.encode(totalR, "UTF-8") +
                    "&bancoR=" + URLEncoder.encode(bancoR, "UTF-8") +
                    "&nro_tarjetaR=" + URLEncoder.encode(nro_tarjetaR, "UTF-8") +
                    "&tipo_monedaR=" + URLEncoder.encode(tipo_monedaR, "UTF-8") +
                    "&idclienteR=" + URLEncoder.encode(idclienteR, "UTF-8");

            voucherPagoServicio = new VoucherPagoRecargaEntity();
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    VoucherPagoRecargaEntity voucherPagoServicioEntity = new VoucherPagoRecargaEntity();
                    voucherPagoServicioEntity.setNumeroUnico(numero_unicoR);
                    voucherPagoServicioEntity.setFecha(fechaR);
                    voucherPagoServicioEntity.setHora(horaR);
                    voucherPagoServicioEntity.setRecarga(recarga);
                    voucherPagoServicioEntity.setFormaPago(forma_pagoR);
                    voucherPagoServicioEntity.setImporte(importeR);
                    voucherPagoServicioEntity.setComisionRecarga(comision_recarga);
                    voucherPagoServicioEntity.setTotal(totalR);
                    voucherPagoServicioEntity.setBanco(bancoR);
                    voucherPagoServicioEntity.setNroTarjeta(nro_tarjetaR);
                    voucherPagoServicioEntity.setTipoMoneda(tipo_monedaR);
                    voucherPagoServicioEntity.setIdCliente(idclienteR);
                } else {
                    voucherPagoServicio = null;
                }
            } else {
                voucherPagoServicio = null;
            }
        } catch (Exception e){
            e.printStackTrace();
            voucherPagoServicio = null;
        }
        return voucherPagoServicio;
    }

    @Override
    public ArrayList<NumeroUnico> getNumeroUnico() {
        ArrayList<NumeroUnico> listaTipoTarjeta = new ArrayList<>();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/NumeroUnico/?vac02=";

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null){
                if (jsonArray.length() > 0){
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        NumeroUnico tipoTarjetaEntity = new NumeroUnico();
                        tipoTarjetaEntity.setNumeroUnico(utils.getValueStringOrNull(jsonObject, "numero_unico"));
                        listaTipoTarjeta.add(tipoTarjetaEntity);
                    }
                }else {
                    listaTipoTarjeta = null;
                }
            } else {
                listaTipoTarjeta = null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return listaTipoTarjeta;
    }

    @Override
    public ArrayList<DeudasTarjetas> ListadoDeudasTarjetas(String idCliente) {
        ArrayList<DeudasTarjetas> listaTipoTarjeta = new ArrayList<>();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ListadoDeudasTarjetas/?PKCliente=" + idCliente;

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null){
                if (jsonArray.length() > 0){
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        DeudasTarjetas tipoTarjetaEntity = new DeudasTarjetas();
                        tipoTarjetaEntity.setCodTipoMoneda(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "cod_tipo_moneda")));
                        tipoTarjetaEntity.setMontoMensual(Double.parseDouble(utils.getValueStringOrNull(jsonObject, "monto_mensual")));
                        tipoTarjetaEntity.setMontoTotal(Double.parseDouble(utils.getValueStringOrNull(jsonObject, "monto_total")));
                        tipoTarjetaEntity.setMontoMinimo(Double.parseDouble(utils.getValueStringOrNull(jsonObject, "monto_minimo")));
                        tipoTarjetaEntity.setSignoMoneda(utils.getValueStringOrNull(jsonObject, "signo_moneda"));
                        tipoTarjetaEntity.setIdDeudaTarjetaCliente(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "id_deuda_tarjeta_cliente")));
                        listaTipoTarjeta.add(tipoTarjetaEntity);
                    }
                }else {
                    listaTipoTarjeta = null;
                }
            } else {
                listaTipoTarjeta = null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return listaTipoTarjeta;
    }

    @Override
    public ArrayList<TipoTarjetaEntity> ListarTipoTarjeta() {

        ArrayList<TipoTarjetaEntity> listaTipoTarjeta = new ArrayList<>();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ListadoTipoTarjeta/?vac01=";

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null){
                if (jsonArray.length() > 0){
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        TipoTarjetaEntity tipoTarjetaEntity = new TipoTarjetaEntity();
                        tipoTarjetaEntity.setCodTipoTarjeta(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "cod_tipo_tarjeta")));
                        tipoTarjetaEntity.setDescTipoTarjeta(utils.getValueStringOrNull(jsonObject, "desc_tipo_tarjeta"));
                        listaTipoTarjeta.add(tipoTarjetaEntity);
                    }
                }else {
                    listaTipoTarjeta = null;
                }
            } else {
                listaTipoTarjeta = null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return listaTipoTarjeta;
    }

    @Override
    public ArrayList<CuotasEntity> ListarCuota() {

        ArrayList<CuotasEntity> listaCuenta = new ArrayList<>();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ListarCuotas/?vacio1=";

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        CuotasEntity cuotasEntity = new CuotasEntity();
                        cuotasEntity.setNumCuota(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "num_cuota")));
                        listaCuenta.add(cuotasEntity);
                    }
                } else {
                    listaCuenta = null;
                }
            } else {
                listaCuenta = null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaCuenta;
    }

    @Override
    public UsuarioEntity InsertarFirmaCliente(String img, String idcliente) {
        UsuarioEntity user;

        try {
            user = new UsuarioEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/InsertarFirmaCliente/";

            Log.e("METHOD", "InsertarFirmaCliente");
            Log.e("URL", url);

            String jsonArray = utils.getJsonarrayFromUrl(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    //JSONObject jsonObject = jsonArray.getJSONObject(0);
                    UsuarioEntity usuarioEntity = new UsuarioEntity();
                    //usuarioEntity.setCodCliente(utils.getValueStringOrNull(jsonObject, "codcliente"));
                } else {
                    user = null;
                }
            } else {
                user = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            user = null;
        }

        return user;
    }

    @Override
    public ArrayList<MonedaEntity> ListarMoneda() {

        ArrayList<MonedaEntity> listaMoneda = new ArrayList<>();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ListarMoneda/?blank=";

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        MonedaEntity monedaEntity = new MonedaEntity();
                        monedaEntity.setCod_tipo_moneda(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "cod_tipo_moneda")));
                        monedaEntity.setSigno_moneda(utils.getValueStringOrNull(jsonObject, "signo_moneda"));
                        monedaEntity.setTipo_moneda(utils.getValueStringOrNull(jsonObject, "tipo_moneda"));
                        listaMoneda.add(monedaEntity);
                    }
                } else {
                    listaMoneda = null;
                }
            } else {
                listaMoneda = null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaMoneda;
    }

    @Override
    public ArrayList<OperadorEntity> ListarOperador() {

        ArrayList<OperadorEntity> listaOperador = new ArrayList<>();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ListadoOperador/?nullyy";

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        OperadorEntity operadorEntity = new OperadorEntity();
                        operadorEntity.setOpe_ide(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "ope_id")));
                        operadorEntity.setOpe_ruc(utils.getValueStringOrNull(jsonObject, "ope_ruc"));
                        operadorEntity.setOpe_razonsoc(utils.getValueStringOrNull(jsonObject, "ope_razonsoc"));
                        operadorEntity.setOpe_nomcomercial(utils.getValueStringOrNull(jsonObject, "ope_nomcomercial"));
                        listaOperador.add(operadorEntity);
                    }
                } else {
                    listaOperador = null;
                }
            } else {
                listaOperador = null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaOperador;
    }

    @Override
    public ArrayList<ComercioEntity> ListarComercio() {

        ArrayList<ComercioEntity> listaComercio = new ArrayList<>();

        String url = Constante.IPORHOST + "webapi_2/apigeneral/ApiGeneral/ListadoComercio/?nul=";

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ComercioEntity comercioEntity = new ComercioEntity();

                        comercioEntity.setId_comercio(utils.getValueStringOrNull(jsonObject, "id_comercio"));
                        comercioEntity.setRaz_social_comercio(utils.getValueStringOrNull(jsonObject, "raz_social_comercio"));
                        comercioEntity.setDireccion_comercio(utils.getValueStringOrNull(jsonObject, "direccion_comercio"));
                        comercioEntity.setDesc_distrito(utils.getValueStringOrNull(jsonObject, "desc_distrito"));

                        listaComercio.add(comercioEntity);
                    }
                } else {
                    listaComercio = null;
                }
            } else {
                listaComercio = null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaComercio;
    }

    @Override
    public ArrayList<CuentaEntity> DetalleCuenta(String id_Tarjeta) {

        ArrayList<CuentaEntity> listaTarjeta = new ArrayList<>();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/DetalleCuenta/?id_Cuenta=" + id_Tarjeta;

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        CuentaEntity beneficiarioEntity = new CuentaEntity();
                        beneficiarioEntity.setNumCuenta(utils.getValueStringOrNull(jsonObject, "numCuenta"));
                        beneficiarioEntity.setTipoMoneda(utils.getValueStringOrNull(jsonObject, "tipo_moneda"));
                        beneficiarioEntity.setBanco(utils.getValueStringOrNull(jsonObject, "desc_breve_banco"));
                        beneficiarioEntity.setCod_banco(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "cod_banco")));
                        listaTarjeta.add(beneficiarioEntity);
                    }
                } else {
                    listaTarjeta = null;
                }
            } else {
                listaTarjeta = null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaTarjeta;
    }

    @Override
    public ArrayList<UsuarioEntity> DetalleTarjeta(String id_Tarjeta) {

        ArrayList<UsuarioEntity> listaTarjeta = new ArrayList<>();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/DetalleTarjeta/?id_Tarjeta=" + id_Tarjeta;

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        UsuarioEntity beneficiarioEntity = new UsuarioEntity();
                        beneficiarioEntity.setPriParteNumTarjeta(utils.getValueStringOrNull(jsonObject, "primParte"));
                        beneficiarioEntity.setSegParteNumTarjeta(utils.getValueStringOrNull(jsonObject, "segParte"));
                        beneficiarioEntity.setTerParteNumTarjeta(utils.getValueStringOrNull(jsonObject, "terParte"));
                        beneficiarioEntity.setCuaParteNumTarjeta(utils.getValueStringOrNull(jsonObject, "cuaParte"));
                        beneficiarioEntity.setBanco_tarjeta(utils.getValueStringOrNull(jsonObject, "desc_breve_banco"));
                        beneficiarioEntity.setEmisor_tarjeta(utils.getValueStringOrNull(jsonObject, "desc_emisor_tarjeta"));
                        beneficiarioEntity.setDesc_tipo_tarjeta(utils.getValueStringOrNull(jsonObject, "desc_tipo_tarjeta"));
                        beneficiarioEntity.setFecha_venci(utils.getValueStringOrNull(jsonObject, "fecha_venc"));
                        beneficiarioEntity.setCodBanco(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "cod_banco")));
                        beneficiarioEntity.setCodTipoTarjeta(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "cod_tipo_tarjeta")));
                        listaTarjeta.add(beneficiarioEntity);
                    }
                } else {
                    listaTarjeta = null;
                }
            } else {
                listaTarjeta = null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaTarjeta;
    }

    @Override
    public ArrayList<TarjetaBinEntity> getDatosBinTarjeta(String numero_Tarjeta) {
        ArrayList<TarjetaBinEntity> listaBin = new ArrayList<>();

        try {

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/TraerDatosBinTarjeta/?numero_Tarjeta=" + numero_Tarjeta;

            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {
                    JSONObject jsonObject = arrayJason.getJSONObject(0);
                    TarjetaBinEntity tarjetaBinEntity = new TarjetaBinEntity();
                    tarjetaBinEntity.setDescripcion_emisor(utils.getValueStringOrNull(jsonObject, "descripcion_emisor"));
                    tarjetaBinEntity.setEmisor(utils.getValueStringOrNull(jsonObject, "emisor"));
                    tarjetaBinEntity.setMarca(utils.getValueStringOrNull(jsonObject, "marca"));
                    tarjetaBinEntity.setRpta_bin(utils.getValueStringOrNull(jsonObject, "rpta_bin"));
                    tarjetaBinEntity.setTipo_tarjeta(utils.getValueStringOrNull(jsonObject, "tipo_tarjeta"));
                    tarjetaBinEntity.setAmbito(utils.getValueStringOrNull(jsonObject, "ambito"));
                    listaBin.add(tarjetaBinEntity);
                } else {
                    listaBin = null;
                }
            } else {
                listaBin = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            listaBin = null;
        }

        return listaBin;
    }

    @Override
    public ArrayList<UsuarioEntity> getClienteReniec(String numDniCliente) {
        ArrayList<UsuarioEntity> listaUsuario = new ArrayList<>();

        try {

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/TraerDatosClientes/?numDniCliente=" + numDniCliente;

            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {
                    JSONObject jsonObject = arrayJason.getJSONObject(0);
                    UsuarioEntity user = new UsuarioEntity();
                    user.setRpta_cambio_clave(utils.getValueStringOrNull(jsonObject, "av_jr_calle_lote"));
                    user.setDepartamento(utils.getValueStringOrNull(jsonObject, "departamento"));
                    user.setDireccion(utils.getValueStringOrNull(jsonObject, "direccion"));
                    user.setDistrito(utils.getValueStringOrNull(jsonObject, "distrito"));
                    user.setDni(utils.getValueStringOrNull(jsonObject, "dni"));
                    user.setEstado_civil(utils.getValueStringOrNull(jsonObject, "estado_civil"));
                    user.setNombre(utils.getValueStringOrNull(jsonObject, "nombres"));
                    user.setApellido(utils.getValueStringOrNull(jsonObject, "primer_apellido"));
                    user.setProvincia(utils.getValueStringOrNull(jsonObject, "provincia"));
                    user.setApe_materno(utils.getValueStringOrNull(jsonObject, "segundo_apellido"));
                    user.setSexo(utils.getValueStringOrNull(jsonObject, "sexo"));
                    listaUsuario.add(user);
                } else {
                    listaUsuario = null;
                }
            } else {
                listaUsuario = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            listaUsuario = null;
        }

        return listaUsuario;
    }

    @Override
    public UsuarioEntity actualizarClaveAcceso(String clave, String idcliente, String clave_nueva, String resp_pregunta) {
        UsuarioEntity user;
        try {
            user = new UsuarioEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ActualizarClave/?clave=" + URLEncoder.encode(clave, "UTF-8") + "&idcliente=" + idcliente + "&clave_nueva=" + URLEncoder.encode(clave_nueva, "UTF-8") + "&resp_pregunta=" + URLEncoder.encode(resp_pregunta, "UTF-8");

            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {
                    JSONObject jsonObject = arrayJason.getJSONObject(0);
                    user.setRpta_cambio_clave(utils.getValueStringOrNull(jsonObject, "rpta_cambio_clave"));
                } else {
                    user = null;
                }
            } else {
                user = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            user = null;
        }

        return user;
    }

    @Override
    public UsuarioEntity ClaveAccesoOlvidada(String idcliente, String respuesta, String newPass) {
        UsuarioEntity user;
        try {
            user = new UsuarioEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/OlvidoClaveAcceso/?identiCli=" + idcliente + "&response_q=" + respuesta + "&newPass=" + newPass;

            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {
                    JSONObject jsonObject = arrayJason.getJSONObject(0);
                    user.setRpta_cambio_clave(utils.getValueStringOrNull(jsonObject, "clave"));
                } else {
                    user = null;
                }
            } else {
                user = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            user = null;
        }

        return user;
    }

    @Override
    public ArrayList<UsuarioEntity> detalleClaveAcceso(String idCliente) {
        ArrayList<UsuarioEntity> listaUsuario = new ArrayList<>();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/DetalleClave/?ident_client=" + idCliente;

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        UsuarioEntity usuarioEntity = new UsuarioEntity();
                        usuarioEntity.setPregunta(utils.getValueStringOrNull(jsonObject, "pregunta"));
                        listaUsuario.add(usuarioEntity);
                    }
                } else {
                    listaUsuario = null;
                }
            } else {
                listaUsuario = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaUsuario;
    }

    @Override
    public ArrayList<BeneficiarioEntity> DetalleBeneficiario(String idcliente) {

        ArrayList<BeneficiarioEntity> listaBeneficiario = new ArrayList<>();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/DetalleBeneficiario/?beneficiario_dni=" + idcliente;

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        BeneficiarioEntity beneficiarioEntity = new BeneficiarioEntity();
                        beneficiarioEntity.setNombre(utils.getValueStringOrNull(jsonObject, "nombre"));
                        beneficiarioEntity.setApellido(utils.getValueStringOrNull(jsonObject, "apellido"));
                        beneficiarioEntity.setDni(utils.getValueStringOrNull(jsonObject, "dni_benef"));
                        beneficiarioEntity.setCelular1(utils.getValueStringOrNull(jsonObject, "celular1_benef"));
                        beneficiarioEntity.setCelular2(utils.getValueStringOrNull(jsonObject, "celular2_benef"));
                        beneficiarioEntity.setEmail(utils.getValueStringOrNull(jsonObject, "email_benef"));
                        beneficiarioEntity.setFechaNac(utils.getValueStringOrNull(jsonObject, "fecha_nac_benef"));
                        beneficiarioEntity.setPass(utils.getValueStringOrNull(jsonObject, "password_benef"));
                        listaBeneficiario.add(beneficiarioEntity);
                    }
                } else {
                    listaBeneficiario = null;
                }
            } else {
                listaBeneficiario = null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaBeneficiario;
    }

    @Override
    public ArrayList<EmpresasServiciosEntity> listarEmpresasServicios() {

        ArrayList<EmpresasServiciosEntity> listaServiciosEntities = new ArrayList<>();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ListarEmpresasServicios/?listar=";

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        EmpresasServiciosEntity empresasServiciosEntity = new EmpresasServiciosEntity();
                        empresasServiciosEntity.setCod_emp_servicio(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "cod_emp_servicio")));
                        empresasServiciosEntity.setDes_emp_servicio(utils.getValueStringOrNull(jsonObject, "des_emp_servicio"));
                        empresasServiciosEntity.setCod_tipo_emps_servicio(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "cod_tipo_emps_servicio")));
                        empresasServiciosEntity.setNombre_recibo(utils.getValueStringOrNull(jsonObject, "nombre_recibo"));
                        listaServiciosEntities.add(empresasServiciosEntity);
                    }
                } else {
                    listaServiciosEntities = null;
                }
            } else {
                listaServiciosEntities = null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaServiciosEntities;
    }

    @Override
    public ArrayList<ServiciosPublicEntity> ListarServiciosPublicos() {

        ArrayList<ServiciosPublicEntity> listaServiciosEntities = new ArrayList<>();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ListarServiciosPublicos/?nully=";

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ServiciosPublicEntity serviciospublic = new ServiciosPublicEntity();
                        serviciospublic.setCod_inst_edu(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "cod_inst_edu")));
                        serviciospublic.setDes_inst_edu(utils.getValueStringOrNull(jsonObject, "des_inst_edu"));
                        listaServiciosEntities.add(serviciospublic);
                    }
                } else {
                    listaServiciosEntities = null;
                }
            } else {
                listaServiciosEntities = null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaServiciosEntities;
    }

    @Override
    public BeneficiarioEntity eliminarCuentasBeneficiario(int id_cuenta_benef) {
        BeneficiarioEntity benef;
        try {
            benef = new BeneficiarioEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/EliminarCuentasBeneficiario/?id_account_beneficiary=" + id_cuenta_benef;

            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {

                    JSONObject jsonObject = arrayJason.getJSONObject(0);
                    benef.setId_cuenta_benef(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "del_account_ben")));

                } else {
                    benef = null;
                }
            } else {
                benef = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            benef = null;
        }

        return benef;
    }

    @Override
    public BeneficiarioEntity eliminarBeneficiarioUsuario(String benef_dni) {
        BeneficiarioEntity benef;
        try {
            benef = new BeneficiarioEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/EliminarBeneficiarioUsuario/?dni_beneficiario=" + benef_dni;

            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {

                    JSONObject jsonObject = arrayJason.getJSONObject(0);
                    benef.setDni(utils.getValueStringOrNull(jsonObject, "respta"));

                } else {
                    benef = null;
                }
            } else {
                benef = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            benef = null;
        }

        return benef;
    }

    @Override
    public UsuarioEntity eliminarTarjetaUsuario(String id_tarjeta) {
        UsuarioEntity tarjeta;
        try {
            tarjeta = new UsuarioEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/EliminarTarjetaUsuario/?id_tarjeta_usu=" + id_tarjeta;

            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {

                    JSONObject jsonObject = arrayJason.getJSONObject(0);
                    tarjeta.setIdTarjeta(utils.getValueStringOrNull(jsonObject, "rpta_del_tarjeta"));

                } else {
                    tarjeta = null;
                }
            } else {
                tarjeta = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            tarjeta = null;
        }

        return tarjeta;
    }

    @Override
    public CuentaEntity eliminarCuentaUsuario(String id_cuenta) {
        CuentaEntity cuenta;
        try {
            cuenta = new CuentaEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/EliminarCuentaUsuario/?numero_Cuenta=" + id_cuenta;

            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {

                    JSONObject jsonObject = arrayJason.getJSONObject(0);
                    cuenta.setIdcuenta(utils.getValueStringOrNull(jsonObject, "rpta_del_cuenta"));

                } else {
                    cuenta = null;
                }
            } else {
                cuenta = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            cuenta = null;
        }

        return cuenta;
    }

    @Override
    public UsuarioEntity getUsuarioLog(String Cmovil, String Cpassword) {
        UsuarioEntity user;
        try {
            user = new UsuarioEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ValidarLogin/?usuario=" + URLEncoder.encode(Cmovil, "UTF-8") + "&contraseña=" + URLEncoder.encode(Cpassword, "UTF-8");

            Log.e("METHOD", "getUsuarioLog");
            Log.e("URL", url);

            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            //Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {
                    JSONObject jsonObject = arrayJason.getJSONObject(0);

                    user.setUsuarioId(utils.getValueStringOrNull(jsonObject, "respuesta"));
                    user.setNombreApellido(utils.getValueStringOrNull(jsonObject, "cliente"));
                    user.setDni(utils.getValueStringOrNull(jsonObject, "dni_cliente"));

                } else {
                    user = null;
                }
            } else {
                user = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            user = null;
        }

        return user;
    }

    @Override
    public ArrayList<BeneficiarioEntity> listarBeneficiario(String idcliente) {

        ArrayList<BeneficiarioEntity> listaBeneficiario = new ArrayList<>();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ListadoBeneficiario/?idcliente=" + idcliente;

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        BeneficiarioEntity beneficiarioEntity = new BeneficiarioEntity();
                        beneficiarioEntity.setNombre(utils.getValueStringOrNull(jsonObject, "nombre"));
                        beneficiarioEntity.setApellido(utils.getValueStringOrNull(jsonObject, "apellido"));
                        beneficiarioEntity.setDni(utils.getValueStringOrNull(jsonObject, "dni_benef"));
                        beneficiarioEntity.setIdcliente(utils.getValueStringOrNull(jsonObject, "idcliente"));
                        listaBeneficiario.add(beneficiarioEntity);
                    }
                } else {
                    listaBeneficiario = null;
                }
            } else {
                listaBeneficiario = null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaBeneficiario;
    }

    @Override
    public ArrayList<UsuarioEntity> listarTarjetas(String idCliente) {
        ArrayList<UsuarioEntity> listaUsuario = new ArrayList<>();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ListadoTarjeta/?idC=" + idCliente;

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        UsuarioEntity usuarioEntity = new UsuarioEntity();
                        usuarioEntity.setNumeroTarjeta(utils.getValueStringOrNull(jsonObject, "num_tarjeta"));
                        usuarioEntity.setTipo_tarjeta(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "cod_tipo_tarjeta")));
                        usuarioEntity.setCod_emisor_tarjeta(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "cod_emisor_tajeta")));
                        usuarioEntity.setIdTarjeta(utils.getValueStringOrNull(jsonObject, "idtarjeta"));
                        usuarioEntity.setBanco_tarjeta(utils.getValueStringOrNull(jsonObject, "banco"));
                        usuarioEntity.setBanco_tarjeta_registro(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "cod_banco")));
                        usuarioEntity.setDesc_cortaBanco(utils.getValueStringOrNull(jsonObject, "desc_breve_banco"));
                        usuarioEntity.setDesc_cortaEmisorTarjeta(utils.getValueStringOrNull(jsonObject, "desc_corta_tarjeta"));
                        usuarioEntity.setValidacionTarjeta(utils.getValueStringOrNull(jsonObject, "validacion_tarjeta"));
                        usuarioEntity.setRespTarjeta(utils.getValueStringOrNull(jsonObject, "rpta_tarjeta"));
                        listaUsuario.add(usuarioEntity);
                    }
                } else {
                    listaUsuario = null;
                }
            } else {
                listaUsuario = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaUsuario;
    }

    @Override
    public ArrayList<CuentaEntity> listarCuentasUsuario(String idCliente) {
        ArrayList<CuentaEntity> listaCuentas = new ArrayList<>();

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ListadoCuenta/?idclientes=" + idCliente;

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        CuentaEntity cuentaEntity = new CuentaEntity();
                        cuentaEntity.setIdcliente(utils.getValueStringOrNull(jsonObject, "idcliente"));
                        cuentaEntity.setNumCuenta(utils.getValueStringOrNull(jsonObject, "numCuenta"));
                        cuentaEntity.setIdcuenta(utils.getValueStringOrNull(jsonObject, "idcuenta"));
                        listaCuentas.add(cuentaEntity);
                    }
                } else {
                    listaCuentas = null;
                }
            } else {
                listaCuentas = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaCuentas;
    }

    @Override
    public ArrayList<BeneficiarioEntity> listarCuentaBeneficiario(String idcliente) {

        ArrayList<BeneficiarioEntity> listaBeneficiario = new ArrayList<>();

        String url = Constante.IPORHOST + "/webApi_2/apigeneral/ApiGeneral/ListadoCuentaBeneficiario/?dnibenef=" + idcliente;

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        BeneficiarioEntity beneficiarioEntity = new BeneficiarioEntity();
                        beneficiarioEntity.setCod_interbancario(utils.getValueStringOrNull(jsonObject, "cod_interbancario"));
                        beneficiarioEntity.setId_cuenta_benef(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "id_cuenta_benef")));
                        listaBeneficiario.add(beneficiarioEntity);
                    }
                } else {
                    listaBeneficiario = null;
                }
            } else {
                listaBeneficiario = null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaBeneficiario;
    }

    @Override
    public ArrayList<BeneficiarioEntity> listarTarjetasBeneficiario(String idcliente) {

        ArrayList<BeneficiarioEntity> listaBeneficiario = new ArrayList<>();

        String url = Constante.IPORHOST + "/webApi_2/apigeneral/ApiGeneral/ListadoTarjetasBeneficiario/?dni_benef=" + idcliente;

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        BeneficiarioEntity beneficiarioEntity = new BeneficiarioEntity();
                        beneficiarioEntity.setNum_tarjeta_beneficiario(utils.getValueStringOrNull(jsonObject, "num_tarjeta_benef"));
                        beneficiarioEntity.setCod_emisor_tarjeta(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "cod_emisor_tarjeta")));
                        beneficiarioEntity.setId_cuenta_benef(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "id_cuenta_benef")));
                        beneficiarioEntity.setRpta_tarjetas_beneficiario(utils.getValueStringOrNull(jsonObject, "rpta_tarjetas_beneficiario"));
                        listaBeneficiario.add(beneficiarioEntity);
                    }
                } else {
                    listaBeneficiario = null;
                }
            } else {
                listaBeneficiario = null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaBeneficiario;
    }

    @Override
    public UsuarioEntity getUsuarioLogin(String dni, String Cnombre, String CapellidoP, String CapellidoM, String Csexo, String Cemail, String Cmovil) {
        UsuarioEntity user;
        try {
            user = new UsuarioEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/InsertarCliente/?dni=" + dni + "&nombre=" + Cnombre + "&apellidoP=" + CapellidoP + "&apellidoM=" + CapellidoM + "&sexo=" + Csexo + "&email=" + Cemail + "&movil=" + Cmovil;

            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {
                    JSONObject jsonObject = arrayJason.getJSONObject(0);

                    user.setUsuarioId(utils.getValueStringOrNull(jsonObject, "codcliente"));
                    user.setDni(dni);
                    user.setNombre(Cnombre);
                    user.setApellido(CapellidoP);
                    user.setApe_materno(CapellidoM);
                    user.setSexo(Csexo);
                    user.setEmail(Cemail);
                    user.setMovil(Cmovil);
                    //user.setClave(Cpassword);
                } else {
                    user = null;
                }
            } else {
                user = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            user = null;
        }

        return user;
    }

    @Override
    public UsuarioEntity getUsuarioDomicilioLogin(String idcliente, String departamento, String provincia, String distrito, String direccion, String tel_fijo) {
        UsuarioEntity user;
        try {
            user = new UsuarioEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/InsertarDomicilioCliente/?codcliente=" + idcliente +
                    "&departamento=" + URLEncoder.encode(departamento, "UTF-8") +
                    "&provincia=" + URLEncoder.encode(provincia, "UTF-8") +
                    "&distrito=" + URLEncoder.encode(distrito, "UTF-8") +
                    "&direccion=" + URLEncoder.encode(direccion, "UTF-8") +
                    "&tel_fijo=" + tel_fijo;

            //JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            Log.e("METHOD", "getUsuarioDomicilioLogin");
            Log.e("URL", url);


            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            //Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {
                    user.setUsuarioId(idcliente);
                    user.setDepartamento(departamento);
                    user.setProvincia(provincia);
                    user.setDistrito(distrito);
                    user.setDireccion(direccion);
                    user.setTel_fijo(tel_fijo);
                } else {
                    user = null;
                }
            } else {
                user = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            user = null;
        }

        return user;
    }

    @Override
    public UsuarioEntity getClaveAcceso(String usuarioId, String claveAcceso, String pregunta, String segundaClaveAcceso) {
        UsuarioEntity user;
        try {
            user = new UsuarioEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ValidarClave/?idcliente_usu=" + usuarioId +
                    "&pass1_usu=" + URLEncoder.encode(claveAcceso, "UTF-8") +
                    "&pregunta_usu=" + URLEncoder.encode(pregunta, "UTF-8") +
                    "&respuesta_usu=" + URLEncoder.encode(segundaClaveAcceso, "UTF-8");

            Log.e("METHOD", "getClaveAcceso");
            Log.e("URL", url);


            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            //Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {
                    //JSONObject jsonObject = arrayJason.getJSONObject(0);
                    //user.setCodCliente(utils.getValueStringOrNull(jsonObject, "codcliente"));
                    user.setCodCliente(usuarioId);
                    user.setClaveAcceso(claveAcceso);
                    user.setPregunta(pregunta);
                    user.setSegundaClaveAcceso(segundaClaveAcceso);
                } else {
                    user = null;
                }
            } else {
                user = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            user = null;
        }

        return user;
    }

    @Override
    public UsuarioEntity validarCelularCliente(String numCel) {
        UsuarioEntity user;
        try {
            user = new UsuarioEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ValidaCelularCliente/?numCel=" + numCel;

            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {
                    JSONObject jsonObject = arrayJason.getJSONObject(0);
                    user.setValidCelUsu(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "val")));
                    user.setUsuarioId(utils.getValueStringOrNull(jsonObject, "id"));

                } else {
                    user = null;
                }
            } else {
                user = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            user = null;
        }

        return user;
    }

    @Override
    public UsuarioEntity getInsertarTarjeta(String usuarioId, String numeroTarjeta) {
        UsuarioEntity user;
        try {
            Boolean insertarTarjeta = false;
            user = new UsuarioEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/Insertartarjeta/?idcliente=%27" + usuarioId + "%27&idtarjeta=%27" + numeroTarjeta + "%27";
            String bool = utils.getResultConnectivity(url);
            insertarTarjeta = bool.equals("true");
            /*if(arrayJason != null){
                if(arrayJason.length()>0){
                    JSONObject jsonObject = arrayJason.getJSONObject(0);*/
            user.setInsertaTarjeta(insertarTarjeta);
               /* } else {
                    user =null;
                }
            } else {
                user =null;
            }*/

        } catch (Exception e) {
            Log.getStackTraceString(e);
            user = null;
        }
        return user;
    }

    @Override
    public UsuarioEntity getValidarTarjeta(String usuarioId, String numeroTarjeta, String fecha_venci, int cod_tipo_tarjeta, int cod_emisor_tarjeta, int banco_tarjeta, String validacionTarjeta) {
        UsuarioEntity user;
        try {
            user = new UsuarioEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ValidarTarjeta/?cliente=" + usuarioId + "&tarjeta=" + numeroTarjeta + "&fecha_venci=" + fecha_venci + "&cod_tipo_tarjeta=" + cod_tipo_tarjeta + "&cod_emisor_tarjeta=" + cod_emisor_tarjeta + "&banco_tarjeta=" + banco_tarjeta + "&validacionTarjeta=" + validacionTarjeta;
            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {
                    JSONObject jsonObject = arrayJason.getJSONObject(0);

                    user.setUsuarioId(usuarioId);
                    user.setNumeroTarjeta(numeroTarjeta);
                    //user.setCvv(cvv);
                    user.setFecha_venci(fecha_venci);
                    user.setTipo_tarjeta(cod_tipo_tarjeta);
                    user.setCod_emisor_tarjeta(cod_emisor_tarjeta);
                    user.setBanco_tarjeta_registro(banco_tarjeta);
                    user.setValidacionTarjeta(validacionTarjeta);
                    user.setRespTarjeta(utils.getValueStringOrNull(jsonObject, "tarjeta"));

                } else {
                    user = null;
                }
            } else {
                user = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            user = null;
        }

        return user;
    }

    @Override
    public UsuarioEntity actualizarTarjeta(String idcard, String date, int emisor_card, int kind_card, String num_card, int cod_bank) {
        UsuarioEntity user;
        try {
            user = new UsuarioEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ActualizarTarjeta/?idcard=" + idcard + "&date=" + date + "&emisor_card=" + emisor_card + "&kind_card=" + kind_card + "&num_card=" + num_card + "&cod_bank=" + cod_bank;
            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {
                    //JSONObject jsonObject = arrayJason.getJSONObject(0);

                    user.setIdTarjeta(idcard);
                    user.setNumeroTarjeta(num_card);
                    //user.setCvv(cvv);
                    user.setFecha_venci(date);
                    user.setTipo_tarjeta(kind_card);
                    user.setCod_emisor_tarjeta(emisor_card);
                    user.setBanco_tarjeta_registro(cod_bank);

                } else {
                    user = null;
                }
            } else {
                user = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            user = null;
        }

        return user;
    }

    @Override
    public BeneficiarioEntity actualizarTarjetaBeneficiario(String num_tarjeta_beneficiario, int cod_banco, int cod_emisor_tarjeta, int id_cuenta_benef, int cod_tipo_cuenta) {
        BeneficiarioEntity user;
        try {
            user = new BeneficiarioEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ActualizarTarjetaBeneficiario/?num_tarjeta_beneficiario=" + num_tarjeta_beneficiario + "&cod_banco=" + cod_banco + "&cod_emisor_tarjeta=" + cod_emisor_tarjeta + "&id_cuenta_benef=" + id_cuenta_benef + "&cod_tipo_cuenta=" + cod_tipo_cuenta;
            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {
                    //JSONObject jsonObject = arrayJason.getJSONObject(0);

                    user.setNum_tarjeta_beneficiario(num_tarjeta_beneficiario);
                    user.setCod_banco(cod_banco);
                    user.setCod_emisor_tarjeta(cod_emisor_tarjeta);
                    user.setId_cuenta_benef(id_cuenta_benef);
                    user.setCod_tipo_cuenta(cod_tipo_cuenta);
                } else {
                    user = null;
                }
            } else {
                user = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            user = null;
        }

        return user;
    }

    @Override
    public BeneficiarioEntity getInsertarBeneficiario(String nombre, String apellido, String celular1, String celular2, String email, String fechaNac, String pass, String dni, String idcliente, int tip_abono) {//, String cod_interbancario, String num_tarjeta_beneficiario, int emisor_tarjeta, int cod_banco, int cod_tipo_cuenta) {
        BeneficiarioEntity benef;
        try {
            benef = new BeneficiarioEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/InsertarBeneficiario/?nombre=" + URLEncoder.encode(nombre, "UTF-8")
                    + "&apellido=" + URLEncoder.encode(apellido, "UTF-8")
                    + "&celular1=" + URLEncoder.encode(celular1, "UTF-8")
                    + "&celular2=" + URLEncoder.encode(celular2, "UTF-8")
                    + "&email=" + URLEncoder.encode(email, "UTF-8")
                    + "&fechaNac=" + URLEncoder.encode(fechaNac, "UTF-8")
                    + "&pass=" + URLEncoder.encode(pass, "UTF-8")
                    + "&dni=" + URLEncoder.encode(dni, "UTF-8")
                    + "&idcliente=" + URLEncoder.encode(idcliente, "UTF-8")
                    + "&tip_abono=" + URLEncoder.encode(String.valueOf(tip_abono), "UTF-8"); //+ "&cod_interbancario=" + cod_interbancario + "&num_tarjeta_beneficiario=" + num_tarjeta_beneficiario + "&emisor_tarjeta=" + emisor_tarjeta + "&cod_banco=" + cod_banco + "&cod_tipo_cuenta=" + cod_tipo_cuenta;

            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {
                    benef.setNombre(nombre);
                    benef.setApellido(apellido);
                    benef.setCelular1(celular1);
                    benef.setCelular2(celular2);
                    benef.setEmail(email);
                    benef.setFechaNac(fechaNac);
                    benef.setPass(pass);
                    benef.setDni(dni);
                    benef.setIdcliente(idcliente);
                    /*benef.setCod_interbancario(cod_interbancario);
                    benef.setNum_tarjeta_beneficiario(num_tarjeta_beneficiario);*/

                } else {
                    benef = null;
                }
            } else {
                benef = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            benef = null;
        }

        return benef;
    }

    @Override
    public BeneficiarioEntity actualizarBeneficiario(String name, String lastname, String cel1, String cel2, String e_mail, String birth, String password, String DNI, int abonos) {
        BeneficiarioEntity benef;
        try {
            benef = new BeneficiarioEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ActualizarBeneficiario/?name=" + name + "&lastname=" + lastname + "&cel1=" + cel1 + "&cel2=" + cel2 + "&e_mail=" + e_mail + "&birth=" + birth + "&password=" + password + "&DNI=" + DNI + "&abonos=" + abonos;

            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {
                    benef.setNombre(name);
                    benef.setApellido(lastname);
                    benef.setCelular1(cel1);
                    benef.setCelular2(cel2);
                    benef.setEmail(e_mail);
                    benef.setFechaNac(birth);
                    benef.setPass(password);
                    benef.setDni(DNI);
                } else {
                    benef = null;
                }
            } else {
                benef = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            benef = null;
        }

        return benef;
    }

    @Override
    public UsuarioEntity actualizarDomicilioCliente(String departamento, String provincia, String distrito, String direccion, String tel_fijo, String idcliente) {
        UsuarioEntity usuarioEntity;
        try {
            usuarioEntity = new UsuarioEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ActualizarDomicilioCliente/?departamento=" + departamento + "&provincia=" + provincia + "&distrito=" + distrito + "&direccion=" + direccion + "&tel_fijo=" + tel_fijo + "&clienteID=" + idcliente;

            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {
                    usuarioEntity.setDepartamento(departamento);
                    usuarioEntity.setProvincia(provincia);
                    usuarioEntity.setDistrito(distrito);
                    usuarioEntity.setDireccion(direccion);
                    usuarioEntity.setTel_fijo(tel_fijo);
                    usuarioEntity.setUsuarioId(idcliente);
                } else {
                    usuarioEntity = null;
                }
            } else {
                usuarioEntity = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            usuarioEntity = null;
        }

        return usuarioEntity;
    }

    @Override
    public BeneficiarioEntity getInsertarCuentasBeneficiario(String dni_b, String cod_interbancario, String num_tarjeta_beneficiario, int cod_emisor_tarjeta, int cod_banco, int cod_tipo_cuenta) {
        BeneficiarioEntity benef;
        try {
            benef = new BeneficiarioEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/InsertarCuentasBeneficiario/?dni_b=" + dni_b + "&cod_interbancario=" + cod_interbancario + "&num_tarjeta_beneficiario=" + num_tarjeta_beneficiario + "&cod_emisor_tarjeta=" + cod_emisor_tarjeta + "&cod_banco=" + cod_banco + "&cod_tipo_cuenta=" + cod_tipo_cuenta;

            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {
                    JSONObject jsonObject = arrayJason.getJSONObject(0);

                    benef.setDni(dni_b);
                    benef.setCod_interbancario(cod_interbancario);
                    benef.setNum_tarjeta_beneficiario(num_tarjeta_beneficiario);
                    benef.setCod_emisor_tarjeta(cod_emisor_tarjeta);
                    benef.setCod_banco(cod_banco);
                    benef.setCod_tipo_cuenta(cod_tipo_cuenta);
                    benef.setError(utils.getValueStringOrNull(jsonObject, "error"));
                } else {
                    benef = null;
                }
            } else {
                benef = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            benef = null;
        }

        return benef;
    }

    @Override
    public CuentaEntity getInsertarCuenta(String numCuenta, String idcliente, int cod_banco, int tipo_moneda) {
        CuentaEntity cuenta;
        try {
            cuenta = new CuentaEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/InsertarCuenta/?numCuenta=" + URLEncoder.encode(numCuenta, "UTF-8")
                    + "&idcliente=" + URLEncoder.encode(idcliente, "UTF-8")
                    + "&cod_banco=" + URLEncoder.encode(String.valueOf(cod_banco), "UTF-8")
                    + "&tipo_moneda=" + URLEncoder.encode(String.valueOf(tipo_moneda), "UTF-8");

            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {

                    JSONObject jsonObject = arrayJason.getJSONObject(0);

                    cuenta.setNumCuenta(numCuenta);
                    cuenta.setIdcliente(idcliente);
                    cuenta.setCod_banco(cod_banco);
                    cuenta.setTipo_moneda(tipo_moneda);

                } else {
                    cuenta = null;
                }
            } else {
                cuenta = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            cuenta = null;
        }

        return cuenta;
    }

    @Override
    public CuentaEntity actualizarCuenta(String num_cuenta, String id_cuenta, int cod_banco) {
        CuentaEntity cuenta;
        try {
            cuenta = new CuentaEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ActualizarCuenta/?num_cuenta=" + num_cuenta + "&id_cuenta=" + id_cuenta + "&cod_banco=" + cod_banco;

            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {

                    //JSONObject jsonObject = arrayJason.getJSONObject(0);

                    cuenta.setNumCuenta(num_cuenta);
                    cuenta.setIdcuenta(id_cuenta);
                    cuenta.setCod_banco(cod_banco);

                } else {
                    cuenta = null;
                }
            } else {
                cuenta = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            cuenta = null;
        }

        return cuenta;
    }

    @Override
    public BeneficiarioEntity actualizarCuentaBeneficiario(String cci_beneficiario, int id_cuenta_benef) {
        BeneficiarioEntity cuenta;
        try {
            cuenta = new BeneficiarioEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ActualizarCuentasBeneficiario/?cci_beneficiario=" + cci_beneficiario + "&id_cuenta_benef=" + id_cuenta_benef;

            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {

                    //JSONObject jsonObject = arrayJason.getJSONObject(0);

                    cuenta.setCod_interbancario(cci_beneficiario);
                    cuenta.setId_cuenta_benef(id_cuenta_benef);

                } else {
                    cuenta = null;
                }
            } else {
                cuenta = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            cuenta = null;
        }

        return cuenta;
    }

    @Override
    public ArrayList<UsuarioEntity> listarComisionDeliverySoles() {

        ArrayList<UsuarioEntity> listaBeneficiario = new ArrayList<>();

        String url = Constante.IPORHOST + "/webApi_2/apigeneral/ApiGeneral/ListadoComisionDeliverySoles/?na=";

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        UsuarioEntity usuarioEntity = new UsuarioEntity();
                        usuarioEntity.setImporte_comision(Double.parseDouble(utils.getValueStringOrNull(jsonObject, "importe")


                        ));
                        listaBeneficiario.add(usuarioEntity);
                    }
                } else {
                    listaBeneficiario = null;
                }
            } else {
                listaBeneficiario = null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaBeneficiario;
    }

    @Override
    public ArrayList<ClubsEntity> ListadoClubs() {

        ArrayList<ClubsEntity> listaClubes = new ArrayList<>();

        String url = Constante.IPORHOST + "/webApi_2/apigeneral/ApiGeneral/ListadoClubs/?nothing=";

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ClubsEntity clubEntity = new ClubsEntity();
                        clubEntity.setCod_club(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "cod_club")));
                        clubEntity.setDes_club(utils.getValueStringOrNull(jsonObject, "des_club"));
                        listaClubes.add(clubEntity);
                    }
                } else {
                    listaClubes = null;
                }
            } else {
                listaClubes = null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaClubes;
    }

    @Override
    public ArrayList<BancosEntity> ListadoBancos() {

        ArrayList<BancosEntity> listaBancos = new ArrayList<>();

        String url = Constante.IPORHOST + "/webApi_2/apigeneral/ApiGeneral/ListarBancos/?empt_y=";

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        BancosEntity bancosEntity = new BancosEntity();
                        bancosEntity.setCod_banco(Integer.parseInt(utils.getValueStringOrNull(jsonObject, "cod_banco")));
                        bancosEntity.setDesc_banco(utils.getValueStringOrNull(jsonObject, "desc_banco"));
                        bancosEntity.setAcro_banco(utils.getValueStringOrNull(jsonObject, "acro_banco"));
                        bancosEntity.setDesc_breve_banco(utils.getValueStringOrNull(jsonObject, "desc_breve_banco"));
                        listaBancos.add(bancosEntity);
                    }
                } else {
                    listaBancos = null;
                }
            } else {
                listaBancos = null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaBancos;
    }

    @Override
    public BeneficiarioEntity IngresarCuentaBeneficiario(String beneficiario_DNI, String beneficiario_cuenta) {
        BeneficiarioEntity beneficiarioEntity;
        try {
            beneficiarioEntity = new BeneficiarioEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/IngresarCuentaBeneficiario/?beneficiario_DNI=" + beneficiario_DNI + "&beneficiario_cuenta=" + beneficiario_cuenta;

            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {

                    //JSONObject jsonObject = arrayJason.getJSONObject(0);
                    beneficiarioEntity.setDni(beneficiario_DNI);
                    beneficiarioEntity.setCod_interbancario(beneficiario_cuenta);

                } else {
                    beneficiarioEntity = null;
                }
            } else {
                beneficiarioEntity = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            beneficiarioEntity = null;
        }

        return beneficiarioEntity;
    }

    @Override
    public BeneficiarioEntity IngresarTarjetaBeneficiario(String DNI_BENEF, String tarjeta_BENEF, int cod_emisor_tarjeta_BENEF, int cod_banco_BENEF, int cod_tipo_cuenta_BENEF) {
        BeneficiarioEntity beneficiarioEntity;
        try {
            beneficiarioEntity = new BeneficiarioEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/IngresarTarjetaBeneficiario/?DNI_BENEF=" + DNI_BENEF + "&tarjeta_BENEF=" + tarjeta_BENEF + "&cod_emisor_tarjeta_BENEF=" + cod_emisor_tarjeta_BENEF + "&cod_banco_BENEF=" + cod_banco_BENEF + "&cod_tipo_cuenta_BENEF=" + cod_tipo_cuenta_BENEF;

            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {

                    //JSONObject jsonObject = arrayJason.getJSONObject(0);
                    beneficiarioEntity.setDni(DNI_BENEF);
                    beneficiarioEntity.setCod_interbancario(tarjeta_BENEF);
                    beneficiarioEntity.setCod_emisor_tarjeta(cod_emisor_tarjeta_BENEF);
                    beneficiarioEntity.setCod_banco(cod_banco_BENEF);
                    beneficiarioEntity.setCod_tipo_cuenta(cod_tipo_cuenta_BENEF);

                } else {
                    beneficiarioEntity = null;
                }
            } else {
                beneficiarioEntity = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            beneficiarioEntity = null;
        }

        return beneficiarioEntity;
    }

    @Override
    public ArrayList<UsuarioEntity> ListadoDomicilioUsuario(String usuario_id) {

        ArrayList<UsuarioEntity> lista = new ArrayList<>();

        String url = Constante.IPORHOST + "/webApi_2/apigeneral/ApiGeneral/ListarDomicilioCliente/?usuario_id=" + usuario_id;

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if (jsonArray != null) {
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        UsuarioEntity usuarioEntity = new UsuarioEntity();
                        usuarioEntity.setDepartamento(utils.getValueStringOrNull(jsonObject, "departamento"));
                        usuarioEntity.setProvincia(utils.getValueStringOrNull(jsonObject, "provincia"));
                        usuarioEntity.setDistrito(utils.getValueStringOrNull(jsonObject, "distrito"));
                        usuarioEntity.setDireccion(utils.getValueStringOrNull(jsonObject, "direccion"));
                        usuarioEntity.setTel_fijo(utils.getValueStringOrNull(jsonObject, "tel_fijo"));
                        lista.add(usuarioEntity);
                    }
                } else {
                    lista = null;
                }
            } else {
                lista = null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
