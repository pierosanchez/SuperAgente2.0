package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.adapter.DetalleClaveAccesoAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;

public class CambioClaveAcceso extends Activity {

    private UsuarioEntity usuario;
    Button btn_aceptar_cambio_clave, btn_regresar_cambio_clave;
    EditText txt_clave_acceso_actual_cambio, txt_respuesta_pregunta_cambio, txt_nueva_clave_acceso, txt_confirme_nueva_clave_acceso;
    TextView txt_primera_pregunta_cambio;
    String usu;
    DetalleClaveAccesoAdapter detalleClaveAccesoAdapter;
    ArrayList<UsuarioEntity> usuarioEntityArrayList;
    String cliente, cli_dni, correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cambio_clave_acceso);

        btn_aceptar_cambio_clave = (Button) findViewById(R.id.btn_aceptar_cambio_clave);
        btn_regresar_cambio_clave = (Button) findViewById(R.id.btn_regresar_cambio_clave);

        txt_clave_acceso_actual_cambio = (EditText) findViewById(R.id.txt_clave_acceso_actual_cambio);
        txt_primera_pregunta_cambio = (TextView) findViewById(R.id.txt_primera_pregunta_cambio);
        txt_respuesta_pregunta_cambio = (EditText) findViewById(R.id.txt_respuesta_pregunta_cambio);
        txt_nueva_clave_acceso = (EditText) findViewById(R.id.txt_nueva_clave_acceso);
        txt_confirme_nueva_clave_acceso = (EditText) findViewById(R.id.txt_confirme_nueva_clave_acceso);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");
        cliente = bundle.getString("cliente");
        cli_dni = bundle.getString("cli_dni");

        usuarioEntityArrayList = null;
        detalleClaveAccesoAdapter = new DetalleClaveAccesoAdapter(usuarioEntityArrayList, getApplication());

        ejecutarLista();

        btn_aceptar_cambio_clave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nueva_clave = txt_nueva_clave_acceso.getText().toString();
                String nueva_clave_confirmacion = txt_confirme_nueva_clave_acceso.getText().toString();

                if (nueva_clave.equals(nueva_clave_confirmacion)) {
                    if (nueva_clave.length() >= 8) {
                        CambioClaveAcceso.actualizarClaveAcceso validador = new CambioClaveAcceso.actualizarClaveAcceso();
                        validador.execute();
                    } else {
                        Toast.makeText(CambioClaveAcceso.this, "La clave debe tener 8 caractéres como mínimo", Toast.LENGTH_SHORT).show();
                    }
                } else if (!nueva_clave.equals(nueva_clave_confirmacion)) {
                    Toast.makeText(CambioClaveAcceso.this, "No coinciden las nuevas contraseñas ingresadas", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_regresar_cambio_clave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CambioClaveAcceso.this, MenuCliente.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                intent.putExtra("cli_dni", cli_dni);
                startActivity(intent);
                finish();
            }
        });
    }

    private class actualizarClaveAcceso extends AsyncTask<String,Void,UsuarioEntity> {
        String clave = txt_clave_acceso_actual_cambio.getText().toString();
        String nueva_clave = txt_nueva_clave_acceso.getText().toString();
        String respuesta = txt_respuesta_pregunta_cambio.getText().toString();

        @Override
        protected UsuarioEntity doInBackground(String... params) {
            UsuarioEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.actualizarClaveAcceso(clave, usuario.getUsuarioId(),  nueva_clave, respuesta);
                //Log.e("idCliente", "CodCliente=" + user.getCodCliente() + ", usuarioId=" + usuario.getUsuarioId());
                //usuario.setClaveAcceso(user.getClaveAcceso());
            } catch (Exception e) {
                user = null;
                //flag_clic_ingreso = 0;;
            }
            return user;
        }

        @Override
        protected void onPostExecute(UsuarioEntity usuarioEntity) {
            if (usuarioEntity.getRpta_cambio_clave() != null) {
                if (usuarioEntity.getRpta_cambio_clave().equals("1")) {
                    Toast.makeText(CambioClaveAcceso.this, "La Contraseña ingresada, no es correcta", Toast.LENGTH_SHORT).show();
                } else if (usuarioEntity.getRpta_cambio_clave().equals("2")) {
                    Toast.makeText(CambioClaveAcceso.this, "La respuesta ingresada, no es correcta", Toast.LENGTH_SHORT).show();
                } else if (usuarioEntity.getRpta_cambio_clave().equals("3")) {
                    Toast.makeText(CambioClaveAcceso.this, "La contraseña ingresada no puede ser igual a la anterior", Toast.LENGTH_SHORT).show();
                } else if (usuarioEntity.getRpta_cambio_clave().equals("0")) {
                    //sendEmail();
                    CambioClaveAcceso.EnvioCorreoElectronico envioCorreo = new CambioClaveAcceso.EnvioCorreoElectronico();
                    envioCorreo.execute();

                    Intent intent = new Intent(CambioClaveAcceso.this, CambioClaveAccesoExitosa.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                    finish();
                }
            } else {
                //mensaje_error = getString(R.string.msg_error_sin_conexion);
                //sinConexion = true;

            }
        }
    }

    private void ejecutarLista(){
        usu = usuario.getUsuarioId();

        try {
            CambioClaveAcceso.DetalleClaveAcceso listadoBeneficiario = new CambioClaveAcceso.DetalleClaveAcceso();
            listadoBeneficiario.execute();
        } catch (Exception e){
            //listadoBeneficiario = null;
        }

    }

    private class DetalleClaveAcceso extends AsyncTask<String,Void,Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                usuarioEntityArrayList = dao.detalleClaveAcceso(usu);
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            detalleClaveAccesoAdapter.setNewListBeneficiario(usuarioEntityArrayList);
            detalleClaveAccesoAdapter.notifyDataSetChanged();
            txt_primera_pregunta_cambio.setText(usuarioEntityArrayList.get(0).getPregunta());
            correo = detalleClaveAccesoAdapter.getItem(0).getEmail();
        }
    }

    private class EnvioCorreoElectronico extends AsyncTask<String, Void, UsuarioEntity> {

        @Override
        protected UsuarioEntity doInBackground(String... params) {
            UsuarioEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.EnvioCorreo(correo, "CAMBIO DE CLAVE DE ACCESO", "SU CLAVE DE ACCESO HA SIDO CAMBIADA. SI USTED NO SOLICITÓ ESTE CAMBIO, POR FAVOR CONTÁCTESE CON NOSOTROS.");
            } catch (Exception e) {
                user = null;
                //flag_clic_ingreso = 0;;
            }
            return user;
        }
    }

    /*private class getCorreoElectronico extends AsyncTask<String, Void, UsuarioEntity> {

        @Override
        protected UsuarioEntity doInBackground(String... params) {
            UsuarioEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.getCorreoCliente(usuario.getUsuarioId());
            } catch (Exception e) {
                user = null;
                //flag_clic_ingreso = 0;;
            }
            return user;
        }

        @Override
        protected void onPostExecute(UsuarioEntity usuarioEntity) {
            if (usuarioEntity != null){
                correo = usuarioEntity.getEmail();
            }
        }
    }*/
}
