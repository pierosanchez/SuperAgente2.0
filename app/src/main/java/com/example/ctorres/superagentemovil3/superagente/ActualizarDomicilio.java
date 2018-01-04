package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.adapter.DepartamentosUbigeoAdapter;
import com.example.ctorres.superagentemovil3.adapter.DistritoUbigeoAdapter;
import com.example.ctorres.superagentemovil3.adapter.DomicilioUsuarioAdapter;
import com.example.ctorres.superagentemovil3.adapter.ProvinciaUbigeoAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.UbigeoEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;

public class ActualizarDomicilio extends Activity {

    private UsuarioEntity usuario;
    Button btn_aceptar_cambio_domicilio, btn_regresar_mantenimiento;
    EditText txt_departamento, txt_provincia, txt_distrito, txt_direccion, txt_tel_fijo;
    ArrayList<UsuarioEntity> usuarioEntityList;
    DomicilioUsuarioAdapter domicilioUsuarioAdapter;
    ArrayList<UbigeoEntity> ubigeoArrayListDepartamento;
    ArrayList<UbigeoEntity> ubigeoArrayListDistrito;
    ArrayList<UbigeoEntity> ubigeoArrayListProvincia;
    DepartamentosUbigeoAdapter departamentosUbigeoAdapter;
    DistritoUbigeoAdapter distritoUbigeoAdapter;
    ProvinciaUbigeoAdapter provinciaUbigeoAdapter;
    String cliente, cli_dni;
    Spinner sp_departamento, sp_provincia, sp_distrito;
    String departamentoUbigeo, departamentoUbigeoDesc, distritoUbigeo,
            distritoUbigeoDesc, provinciaUbigeo, provinciaUbigeoDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_domicilio);

        btn_aceptar_cambio_domicilio = (Button) findViewById(R.id.btn_aceptar_cambio_domicilio);
        btn_regresar_mantenimiento = (Button) findViewById(R.id.btn_regresar_mantenimiento);

        sp_departamento = (Spinner) findViewById(R.id.sp_departamento);
        sp_provincia = (Spinner) findViewById(R.id.sp_provincia);
        sp_distrito = (Spinner) findViewById(R.id.sp_distrito);

        /*txt_departamento = (EditText) findViewById(R.id.txt_departamento);
        txt_provincia = (EditText) findViewById(R.id.txt_provincia);
        txt_distrito = (EditText) findViewById(R.id.txt_distrito);*/
        txt_direccion = (EditText) findViewById(R.id.txt_direccion);
        txt_tel_fijo = (EditText) findViewById(R.id.txt_tel_fijo);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");
        cliente = bundle.getString("cliente");
        cli_dni = bundle.getString("cli_dni");

        usuarioEntityList = null;
        domicilioUsuarioAdapter = new DomicilioUsuarioAdapter(usuarioEntityList, getApplication());

        ejecutarLista();

        ubigeoArrayListDepartamento = null;
        departamentosUbigeoAdapter = new DepartamentosUbigeoAdapter(ubigeoArrayListDepartamento, getApplication());
        sp_departamento.setAdapter(departamentosUbigeoAdapter);

        ejecutarListaDepartamentos();

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

        btn_aceptar_cambio_domicilio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActualizarDomicilio.ValidarUsuarioDireccion valida = new ActualizarDomicilio.ValidarUsuarioDireccion();
                valida.execute();

                Intent sanipesIntent = new Intent(ActualizarDomicilio.this, MantenimientoUsuario.class);
                sanipesIntent.putExtra("usuario", usuario);
                sanipesIntent.putExtra("cliente", cliente);
                sanipesIntent.putExtra("cli_dni", cli_dni);
                startActivity(sanipesIntent);
                finish();
            }
        });

        btn_regresar_mantenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ActualizarDomicilio.this, MantenimientoUsuario.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                intent.putExtra("cli_dni", cli_dni);
                startActivity(intent);
                finish();
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
            ActualizarDomicilio.ListadoDomicilio listadoDomicilio = new ActualizarDomicilio.ListadoDomicilio();
            listadoDomicilio.execute();
        } catch (Exception e){
            //listadoBeneficiario = null;
        }

    }

    private class ListadoDomicilio extends AsyncTask<String,Void,Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                usuarioEntityList = dao.ListadoDomicilioUsuario(usuario.getUsuarioId());
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            domicilioUsuarioAdapter.setNewListDomicilioUsuario(usuarioEntityList);
            domicilioUsuarioAdapter.notifyDataSetChanged();
            /*txt_departamento.setText(usuarioEntityList.get(0).getDepartamento());
            txt_provincia.setText(usuarioEntityList.get(0).getProvincia());
            txt_distrito.setText(usuarioEntityList.get(0).getDistrito());
            txt_direccion.setText(usuarioEntityList.get(0).getDireccion());
            txt_tel_fijo.setText(usuarioEntityList.get(0).getTel_fijo());*/
        }
    }

    private void ejecutarListaDepartamentos() {

        try {
            ActualizarDomicilio.ListadoDepartamentos listadoEmpresas = new ActualizarDomicilio.ListadoDepartamentos();
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
            ActualizarDomicilio.ListadoDistritos listadoEmpresas = new ActualizarDomicilio.ListadoDistritos();
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
            ActualizarDomicilio.ListadoProvincias listadoEmpresas = new ActualizarDomicilio.ListadoProvincias();
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
