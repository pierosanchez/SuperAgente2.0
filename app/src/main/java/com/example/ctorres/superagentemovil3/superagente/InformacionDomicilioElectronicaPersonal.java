package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.adapter.GetUsuarioReniecAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;

public class InformacionDomicilioElectronicaPersonal extends Activity {

    private UsuarioEntity usuario;
    LinearLayout btn_aceptar_cuenta_tarjeta_abono;
    EditText txt_departamento, txt_provincia, txt_distrito, txt_direccion, txt_tel_fijo;
    String dni;
    ArrayList<UsuarioEntity> usuarioEntityArrayList;
    GetUsuarioReniecAdapter getUsuarioReniecAdapter;
    private final int INTERNET=1;
    private final int ACCESS_NETWORK_STATE=2;
    private final int CHANGE_NETWORK_STATE=3;
    private final int ACCESS_WIFI_STATE=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informacion_domicilio_electronica_personal);

        btn_aceptar_cuenta_tarjeta_abono = (LinearLayout) findViewById(R.id.btn_aceptar_cuenta_tarjeta_abono);

        txt_departamento = (EditText) findViewById(R.id.txt_departamento);
        txt_provincia = (EditText) findViewById(R.id.txt_provincia);
        txt_distrito = (EditText) findViewById(R.id.txt_distrito);
        txt_direccion = (EditText) findViewById(R.id.txt_direccion);
        txt_tel_fijo = (EditText) findViewById(R.id.txt_tel_fijo);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");
        dni = bundle.getString("dni");

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
                user = dao.getUsuarioDomicilioLogin(usuario.getUsuarioId(), depa, provi, distri, direc, tel);
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
            txt_departamento.setText(usuarioEntityArrayList.get(0).getDepartamento());
            txt_provincia.setText(usuarioEntityArrayList.get(0).getProvincia());
            txt_direccion.setText(usuarioEntityArrayList.get(0).getDireccion());
            txt_distrito.setText(usuarioEntityArrayList.get(0).getDistrito());
        }
    }
}
