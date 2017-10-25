package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.DomicilioUsuarioAdapter;
import com.example.ctorres.superagentemovil3.dao.GetUsuarioReniecAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;
import java.util.List;

public class ActualizarDomicilio extends Activity {

    private UsuarioEntity usuario;
    Button btn_aceptar_cambio_domicilio, btn_regresar_mantenimiento;
    EditText txt_departamento, txt_provincia, txt_distrito, txt_direccion, txt_tel_fijo;
    ArrayList<UsuarioEntity> usuarioEntityList;
    DomicilioUsuarioAdapter domicilioUsuarioAdapter;
    String cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_domicilio);

        btn_aceptar_cambio_domicilio = (Button) findViewById(R.id.btn_aceptar_cambio_domicilio);
        btn_regresar_mantenimiento = (Button) findViewById(R.id.btn_regresar_mantenimiento);

        txt_departamento = (EditText) findViewById(R.id.txt_departamento);
        txt_provincia = (EditText) findViewById(R.id.txt_provincia);
        txt_distrito = (EditText) findViewById(R.id.txt_distrito);
        txt_direccion = (EditText) findViewById(R.id.txt_direccion);
        txt_tel_fijo = (EditText) findViewById(R.id.txt_tel_fijo);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");
        cliente = bundle.getString("cliente");

        usuarioEntityList = null;
        domicilioUsuarioAdapter = new DomicilioUsuarioAdapter(usuarioEntityList, getApplication());

        ejecutarLista();

        btn_aceptar_cambio_domicilio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActualizarDomicilio.ValidarUsuarioDireccion valida = new ActualizarDomicilio.ValidarUsuarioDireccion();
                valida.execute();

                Intent sanipesIntent = new Intent(ActualizarDomicilio.this, MantenimientoUsuario.class);
                sanipesIntent.putExtra("usuario", usuario);
                sanipesIntent.putExtra("cliente", cliente);
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
                startActivity(intent);
                finish();
            }
        });
    }

    private class ValidarUsuarioDireccion extends AsyncTask<String, Void, UsuarioEntity> {

        String depa = txt_departamento.getText().toString();
        String provi = txt_provincia.getText().toString();
        String distri = txt_distrito.getText().toString();
        String direc = txt_direccion.getText().toString();
        String tel = txt_tel_fijo.getText().toString();

        @Override
        protected UsuarioEntity doInBackground(String... params) {
            UsuarioEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.actualizarDomicilioCliente(depa, provi, distri, direc, tel, usuario.getUsuarioId());

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
            txt_departamento.setText(usuarioEntityList.get(0).getDepartamento());
            txt_provincia.setText(usuarioEntityList.get(0).getProvincia());
            txt_distrito.setText(usuarioEntityList.get(0).getDistrito());
            txt_direccion.setText(usuarioEntityList.get(0).getDireccion());
            txt_tel_fijo.setText(usuarioEntityList.get(0).getTel_fijo());
        }
    }
}
