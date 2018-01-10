package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.adapter.DepartamentosUbigeoAdapter;
import com.example.ctorres.superagentemovil3.adapter.DistritoUbigeoAdapter;
import com.example.ctorres.superagentemovil3.adapter.DomicilioUsuarioAdapter;
import com.example.ctorres.superagentemovil3.adapter.GetUsuarioReniecAdapter;
import com.example.ctorres.superagentemovil3.adapter.ProvinciaUbigeoAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.UbigeoEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;

public class InformacionDomicilioElectronicaPersonal extends Activity {

    private UsuarioEntity usuario;
    LinearLayout btn_aceptar_cuenta_tarjeta_abono;
    EditText txt_direccion, txt_tel_fijo;
    //EditText txt_departamento, txt_provincia, txt_distrito;
    Spinner sp_departamento, sp_provincia, sp_distrito;
    String dni;
    ArrayList<UsuarioEntity> usuarioEntityArrayList;
    GetUsuarioReniecAdapter getUsuarioReniecAdapter;
    DomicilioUsuarioAdapter domicilioUsuarioAdapter;
    ArrayList<UbigeoEntity> ubigeoArrayListDepartamento;
    ArrayList<UbigeoEntity> ubigeoArrayListDistrito;
    ArrayList<UbigeoEntity> ubigeoArrayListProvincia;
    DepartamentosUbigeoAdapter departamentosUbigeoAdapter;
    DistritoUbigeoAdapter distritoUbigeoAdapter;
    ProvinciaUbigeoAdapter provinciaUbigeoAdapter;
    String departamentoUbigeo, departamentoUbigeoDesc, distritoUbigeo,
            distritoUbigeoDesc, provinciaUbigeo, provinciaUbigeoDesc;
    private final int INTERNET=1;
    private final int ACCESS_NETWORK_STATE=2;
    private final int CHANGE_NETWORK_STATE=3;
    private final int ACCESS_WIFI_STATE=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informacion_domicilio_electronica_personal);

        btn_aceptar_cuenta_tarjeta_abono = (LinearLayout) findViewById(R.id.btn_aceptar_cuenta_tarjeta_abono);

        /*txt_departamento = (EditText) findViewById(R.id.txt_departamento);
        txt_provincia = (EditText) findViewById(R.id.txt_provincia);
        txt_distrito = (EditText) findViewById(R.id.txt_distrito);*/
        txt_direccion = (EditText) findViewById(R.id.txt_direccion);
        txt_tel_fijo = (EditText) findViewById(R.id.txt_tel_fijo);

        sp_departamento = (Spinner) findViewById(R.id.sp_departamento);
        sp_provincia = (Spinner) findViewById(R.id.sp_provincia);
        sp_distrito = (Spinner) findViewById(R.id.sp_distrito);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");
        dni = bundle.getString("dni");

        ubigeoArrayListDepartamento = null;
        departamentosUbigeoAdapter = new DepartamentosUbigeoAdapter(ubigeoArrayListDepartamento, getApplication());
        sp_departamento.setAdapter(departamentosUbigeoAdapter);

        ejecutarListaDepartamentos();

        usuarioEntityArrayList = null;
        getUsuarioReniecAdapter = new GetUsuarioReniecAdapter(usuarioEntityArrayList, getApplication());

        ejecutarLista();

        btn_aceptar_cuenta_tarjeta_abono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InformacionDomicilioElectronicaPersonal.ValidarUsuarioDireccion validarUsuarioDireccion = new InformacionDomicilioElectronicaPersonal.ValidarUsuarioDireccion();
                validarUsuarioDireccion.execute();

                /*Intent sanipesIntent = new Intent(InformacionDomicilioElectronicaPersonal.this, ClaveAcceso.class);
                sanipesIntent.putExtra("usuario", usuario);
                startActivity(sanipesIntent);
                finish();*/

                Intent sanipesIntent = new Intent(InformacionDomicilioElectronicaPersonal.this, ClaveAcceso.class);
                sanipesIntent.putExtra("usuario", usuario);
                startActivity(sanipesIntent);
                finish();
            }
        });

        sp_departamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                departamentoUbigeo = departamentosUbigeoAdapter.getItem(position).getUbigeo1();
                departamentoUbigeoDesc = departamentosUbigeoAdapter.getItem(position).getDepartamento();

                ubigeoArrayListProvincia = null;
                provinciaUbigeoAdapter = new ProvinciaUbigeoAdapter(ubigeoArrayListProvincia, getApplication());
                sp_provincia.setAdapter(provinciaUbigeoAdapter);

                ejecutarListaProvincias();

                sp_provincia.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_provincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                provinciaUbigeo = provinciaUbigeoAdapter.getItem(position).getUbigeo2();
                provinciaUbigeoDesc = provinciaUbigeoAdapter.getItem(position).getProvincia();

                ubigeoArrayListDistrito = null;
                distritoUbigeoAdapter = new DistritoUbigeoAdapter(ubigeoArrayListDistrito, getApplication());
                sp_distrito.setAdapter(distritoUbigeoAdapter);

                ejecutarListaDistritos();

                sp_distrito.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_distrito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                distritoUbigeo = distritoUbigeoAdapter.getItem(position).getUbigeo3();
                distritoUbigeoDesc = distritoUbigeoAdapter.getItem(position).getDistrito();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private class ValidarUsuarioDireccion extends AsyncTask<String, Void, UsuarioEntity> {

        /*String depa = txt_departamento.getText().toString();
        String provi = txt_provincia.getText().toString();
        String distri = txt_distrito.getText().toString();*/
        String direc = txt_direccion.getText().toString();
        String tel = txt_tel_fijo.getText().toString();

        @Override
        protected UsuarioEntity doInBackground(String... params) {
            UsuarioEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.getUsuarioDomicilioLogin(usuario.getUsuarioId(), departamentoUbigeoDesc, provinciaUbigeoDesc, distritoUbigeoDesc, direc, tel);
            } catch (Exception e) {
                user = null;
                //fldag_clic_ingreso = 0;;
            }
            return user;
        }
    }

    private void ejecutarLista(){

        try {
            InformacionDomicilioElectronicaPersonal.ListadoBeneficiario listadoBeneficiario = new InformacionDomicilioElectronicaPersonal.ListadoBeneficiario();
            listadoBeneficiario.execute();
        } catch (Exception e){
            //listadoBeneficiario = null;
        }

    }

    private class ListadoBeneficiario extends AsyncTask<String,Void,Void> {


        @Override
        protected Void doInBackground(String... params) {
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                usuarioEntityArrayList = dao.getClienteReniec(dni);
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getUsuarioReniecAdapter.setNewListUsuarioReniec(usuarioEntityArrayList);
            getUsuarioReniecAdapter.notifyDataSetChanged();
            /*if (usuarioEntityArrayList != null) {
                for (int i = 0; i < ubigeoArrayListDepartamento.size(); i++) {
                    if (usuarioEntityArrayList.get(0).getDepartamento().equals(departamentosUbigeoAdapter.getItem(i).getUbigeo1())){
                        sp_departamento.setSelection(i);
                    }
                }
                for (int i = 0; i < ubigeoArrayListProvincia.size(); i++) {
                    if (usuarioEntityArrayList.get(0).getPregunta().equals(provinciaUbigeoAdapter.getItem(i).getUbigeo2())){
                        sp_provincia.setSelection(i);
                    }
                }
                for (int i = 0; i < ubigeoArrayListDistrito.size(); i++) {
                    if (usuarioEntityArrayList.get(0).getDistrito().equals(distritoUbigeoAdapter.getItem(i).getUbigeo3())){
                        sp_distrito.setSelection(i);
                    }
                }
            }*/
            /*txt_departamento.setText(usuarioEntityArrayList.get(0).getDepartamento());
            txt_provincia.setText(usuarioEntityArrayList.get(0).getProvincia());
            txt_direccion.setText(usuarioEntityArrayList.get(0).getDireccion());
            txt_distrito.setText(usuarioEntityArrayList.get(0).getDistrito());*/
        }
    }

    private void ejecutarListaDepartamentos() {

        try {
            InformacionDomicilioElectronicaPersonal.ListadoDepartamentos listadoEmpresas = new InformacionDomicilioElectronicaPersonal.ListadoDepartamentos();
            listadoEmpresas.execute();
        } catch (Exception e) {
            //listadoBeneficiario = null;
        }

    }

    private class ListadoDepartamentos extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                ubigeoArrayListDepartamento = dao.ListarDepartamento();
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //usuarioEntityArrayList.remove(banco = banco_tarjeta);
            departamentosUbigeoAdapter.setNewListDepartamentoUbigeo(ubigeoArrayListDepartamento);
            departamentosUbigeoAdapter.notifyDataSetChanged();
        }
    }

    private void ejecutarListaDistritos() {

        try {
            InformacionDomicilioElectronicaPersonal.ListadoDistritos listadoEmpresas = new InformacionDomicilioElectronicaPersonal.ListadoDistritos();
            listadoEmpresas.execute();
        } catch (Exception e) {
            //listadoBeneficiario = null;
        }

    }

    private class ListadoDistritos extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                ubigeoArrayListDistrito = dao.ListarDistritoUbigeo(departamentoUbigeo, provinciaUbigeo);
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //usuarioEntityArrayList.remove(banco = banco_tarjeta);
            distritoUbigeoAdapter.setNewListDistritoUbigeo(ubigeoArrayListDistrito);
            distritoUbigeoAdapter.notifyDataSetChanged();
        }
    }

    private void ejecutarListaProvincias() {

        try {
            InformacionDomicilioElectronicaPersonal.ListadoProvincias listadoEmpresas = new InformacionDomicilioElectronicaPersonal.ListadoProvincias();
            listadoEmpresas.execute();
        } catch (Exception e) {
            //listadoBeneficiario = null;
        }

    }

    private class ListadoProvincias extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                ubigeoArrayListProvincia = dao.ListarProvinciaUbigeo(departamentoUbigeo);
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //usuarioEntityArrayList.remove(banco = banco_tarjeta);
            provinciaUbigeoAdapter.setNewListProvinciaUbigeo(ubigeoArrayListProvincia);
            provinciaUbigeoAdapter.notifyDataSetChanged();
        }
    }
}
