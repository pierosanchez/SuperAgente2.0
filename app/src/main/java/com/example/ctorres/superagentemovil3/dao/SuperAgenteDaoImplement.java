package com.example.ctorres.superagentemovil3.dao;

/**
 * Created by CTORRES on 05/05/2017.
 */

import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.ctorres.superagentemovil3.entity.*;
import com.example.ctorres.superagentemovil3.utils.Constante;
import com.example.ctorres.superagentemovil3.utils.Utils;
import com.google.gson.JsonArray;

import retrofit.RestAdapter;

/**
 * Created by Jove on 22/03/2017.
 */
public class SuperAgenteDaoImplement implements SuperAgenteDaoInterface {

    private Utils utils;
    BeneficiarioEntity benef;

    public SuperAgenteDaoImplement() {
        utils = new Utils();
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

        String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ListadoOperador/";

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

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ActualizarClave/?clave=" + clave + "&idcliente=" + idcliente + "&clave_nueva=" + clave_nueva + "&resp_pregunta=" + resp_pregunta;

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

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ValidarLogin/?usuario=" + Cmovil + "&contraseña=" + Cpassword;

            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {
                    JSONObject jsonObject = arrayJason.getJSONObject(0);

                    user.setUsuarioId(utils.getValueStringOrNull(jsonObject, "respuesta"));
                    user.setNombreApellido(utils.getValueStringOrNull(jsonObject, "cliente"));
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
            Log.e("METHOD","getUsuarioDomicilioLogin");
            Log.e("URL",url);

            String arrayJason = utils.getJsonarrayFromUrl(url);
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

            Log.e("METHOD","getClaveAcceso");
            Log.e("URL",url);

            String arrayJason = utils.getJsonarrayFromUrl(url);
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
    public UsuarioEntity getValidarTarjeta(String usuarioId, String numeroTarjeta, String fecha_venci, int cod_tipo_tarjeta, int cod_emisor_tarjeta, int banco_tarjeta) {
        UsuarioEntity user;
        try {
            user = new UsuarioEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ValidarTarjeta/?cliente=" + usuarioId + "&tarjeta=" + numeroTarjeta + "&fecha_venci=" + fecha_venci + "&cod_tipo_tarjeta=" + cod_tipo_tarjeta + "&cod_emisor_tarjeta=" + cod_emisor_tarjeta + "&banco_tarjeta=" + banco_tarjeta;
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

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/InsertarBeneficiario/?nombre=" + nombre + "&apellido=" + apellido + "&celular1=" + celular1 + "&celular2=" + celular2 + "&email=" + email + "&fechaNac=" + fechaNac + "&pass=" + pass + "&dni=" + dni + "&idcliente=" + idcliente + "&tip_abono=" + tip_abono; //+ "&cod_interbancario=" + cod_interbancario + "&num_tarjeta_beneficiario=" + num_tarjeta_beneficiario + "&emisor_tarjeta=" + emisor_tarjeta + "&cod_banco=" + cod_banco + "&cod_tipo_cuenta=" + cod_tipo_cuenta;

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
                    benef.setDni(dni_b);
                    benef.setCod_interbancario(cod_interbancario);
                    benef.setNum_tarjeta_beneficiario(num_tarjeta_beneficiario);
                    benef.setCod_emisor_tarjeta(cod_emisor_tarjeta);
                    benef.setCod_banco(cod_banco);
                    benef.setCod_tipo_cuenta(cod_tipo_cuenta);
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

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/InsertarCuenta/?numCuenta=" + numCuenta + "&idcliente=" + idcliente + "&cod_banco=" + cod_banco + "&tipo_moneda=" + tipo_moneda;

            JSONArray arrayJason = utils.getJSONArrayfromURL(url);
            Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {

                    JSONObject jsonObject = arrayJason.getJSONObject(0);

                    cuenta.setNumCuenta(utils.getValueStringOrNull(jsonObject, "cuentcliente"));
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
