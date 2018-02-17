package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.adapter.DetalleClaveAccesoAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;

public class ContrasenaOlvidada extends Activity {

    private UsuarioEntity usuario;
    ArrayList<UsuarioEntity> usuarioEntityArrayList;
    DetalleClaveAccesoAdapter detalleClaveAccesoAdapter;
    String usu, numero, correo;
    EditText txt_pregunta_autenticacion, txt_respuesta_pregunta, txt_nueva_clave_por_olvido, tx_valida_num_cel_usuario;
    Button btn_aceptar, btn_salir, btn_validar;
    LinearLayout ll_botones_olvido_contra;
    TextView tv_olvido_pregunta_secreta;
    private int validaContra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contrasena_olvidada);

        txt_pregunta_autenticacion = (EditText) findViewById(R.id.txt_pregunta_autenticacion);
        txt_respuesta_pregunta = (EditText) findViewById(R.id.txt_respuesta_pregunta);
        txt_nueva_clave_por_olvido = (EditText) findViewById(R.id.txt_nueva_clave_por_olvido);
        tx_valida_num_cel_usuario = (EditText) findViewById(R.id.tx_valida_num_cel_usuario);

        ll_botones_olvido_contra = (LinearLayout) findViewById(R.id.ll_botones_olvido_contra);

        tv_olvido_pregunta_secreta = (TextView) findViewById(R.id.tv_olvido_pregunta_secreta);

        btn_aceptar = (Button) findViewById(R.id.btn_aceptar);
        btn_salir = (Button) findViewById(R.id.btn_salir);
        //btn_validar = (Button) findViewById(R.id.btn_validar);

        Bundle bundle = getIntent().getExtras();
        numero = bundle.getString("numero");

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clave = txt_nueva_clave_por_olvido.getText().toString();
                String respuesta = txt_respuesta_pregunta.getText().toString();
                if (clave.equals("") && respuesta.equals("")) {
                    Toast.makeText(ContrasenaOlvidada.this, "Ingrese los Datos", Toast.LENGTH_SHORT).show();
                } else if (clave.equals("")) {
                    Toast.makeText(ContrasenaOlvidada.this, "Ingrese su nueva Contraseña", Toast.LENGTH_SHORT).show();
                } else if (respuesta.equals("")) {
                    Toast.makeText(ContrasenaOlvidada.this, "Ingrese su Respuesta", Toast.LENGTH_SHORT).show();
                } else {
                    if (clave.length() >= 8) {
                        ContrasenaOlvidada.actualizarClaveAcceso actualizarClave = new ContrasenaOlvidada.actualizarClaveAcceso();
                        actualizarClave.execute();
                    } else {
                        Toast.makeText(ContrasenaOlvidada.this, "La clave debe tener 8 caractéres como mínimo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        /*btn_validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String celular = tx_valida_num_cel_usuario.getText().toString();
                if (celular.equals("")) {
                    Toast.makeText(ContrasenaOlvidada.this, "Ingrese el número", Toast.LENGTH_SHORT).show();
                } else {
                    ContrasenaOlvidada.validarCelularCliente actualizarClave = new ContrasenaOlvidada.validarCelularCliente();
                    actualizarClave.execute();
                }
            }
        });*/

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
                /*Intent intent = new Intent(ContrasenaOlvidada.this, LoginNumeroCliente.class);
                startActivity(intent);
                finish();*/
            }
        });

        tx_valida_num_cel_usuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String celular = tx_valida_num_cel_usuario.getText().toString();
                if (celular.length() == 9) {
                    ContrasenaOlvidada.validarCelularCliente actualizarClave = new ContrasenaOlvidada.validarCelularCliente();
                    actualizarClave.execute();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String celular = tx_valida_num_cel_usuario.getText().toString();
                if (celular.length() == 9) {
                    ContrasenaOlvidada.validarCelularCliente actualizarClave = new ContrasenaOlvidada.validarCelularCliente();
                    actualizarClave.execute();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tx_valida_num_cel_usuario.setText(numero);

        tv_olvido_pregunta_secreta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ContrasenaOlvidada.this, VentanaErrores.class);
                startActivityForResult(intent, 0);
                finish();
            }
        });
    }

    private class validarCelularCliente extends AsyncTask<String, Void, UsuarioEntity> {

        String celular = tx_valida_num_cel_usuario.getText().toString();

        @Override
        protected UsuarioEntity doInBackground(String... params) {
            UsuarioEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.validarCelularCliente(celular);
            } catch (Exception e) {
                user = null;
                //flag_clic_ingreso = 0;;
            }
            return user;
        }

        @Override
        protected void onPostExecute(UsuarioEntity usuarioEntity) {
            usuario = usuarioEntity;

            if (usuario.getValidCelUsu() == 0) {
                ll_botones_olvido_contra.setVisibility(View.VISIBLE);

                usuarioEntityArrayList = null;
                detalleClaveAccesoAdapter = new DetalleClaveAccesoAdapter(usuarioEntityArrayList, getApplication());

                ejecutarLista();

                txt_respuesta_pregunta.setEnabled(true);
                txt_nueva_clave_por_olvido.setEnabled(true);
                txt_respuesta_pregunta.requestFocus();
            } else if (usuario.getValidCelUsu() == 1) {
                Toast.makeText(ContrasenaOlvidada.this, "El numero ingresado no existe", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class actualizarClaveAcceso extends AsyncTask<String, Void, UsuarioEntity> {

        String respuesta = txt_respuesta_pregunta.getText().toString();
        String newPass = txt_nueva_clave_por_olvido.getText().toString();

        @Override
        protected UsuarioEntity doInBackground(String... params) {
            UsuarioEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.ClaveAccesoOlvidada(usuario.getUsuarioId(), respuesta, newPass);
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
            usuario = usuarioEntity;
            if (usuario.getRpta_cambio_clave() != null) {
                if (usuario.getRpta_cambio_clave().equals("1")) {
                    validaContra++;
                    if (validaContra < 4) {
                        Toast.makeText(ContrasenaOlvidada.this, "La respuesta ingresada, no es correcta", Toast.LENGTH_SHORT).show();
                        ContrasenaOlvidada.validarCelularCliente actualizarClave = new ContrasenaOlvidada.validarCelularCliente();
                        actualizarClave.execute();
                        txt_respuesta_pregunta.setText("");
                        txt_respuesta_pregunta.requestFocus();
                    } else if (validaContra == 4) {
                        Intent sanipesIntent = new Intent(ContrasenaOlvidada.this, VentanaErrores.class);
                        sanipesIntent.putExtra("numero", numero);
                        startActivityForResult(sanipesIntent, 0);
                        finish();
                    }
                } else if (usuario.getRpta_cambio_clave().equals("2")) {
                    Toast.makeText(ContrasenaOlvidada.this, "La contraseña ingresa ya esta siendo usada para otro usuario", Toast.LENGTH_SHORT).show();
                    ContrasenaOlvidada.validarCelularCliente actualizarClave = new ContrasenaOlvidada.validarCelularCliente();
                    actualizarClave.execute();
                    txt_nueva_clave_por_olvido.requestFocus();
                } else if (usuario.getRpta_cambio_clave().equals("0")) {

                    ContrasenaOlvidada.EnvioCorreoElectronico envioCorreo = new ContrasenaOlvidada.EnvioCorreoElectronico();
                    envioCorreo.execute();
                    Intent intent = new Intent(ContrasenaOlvidada.this, CambioClaveAccesoExitosa.class);
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

    private void ejecutarLista() {
        usu = usuario.getUsuarioId();

        try {
            ContrasenaOlvidada.ListadoPregunta listadoBeneficiario = new ContrasenaOlvidada.ListadoPregunta();
            listadoBeneficiario.execute();
        } catch (Exception e) {
            //listadoBeneficiario = null;
        }

    }

    private class ListadoPregunta extends AsyncTask<String, Void, Void> {
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
            //usuarioEntityArrayList.remove(tipo_tarjeta=2);
            detalleClaveAccesoAdapter.setNewListBeneficiario(usuarioEntityArrayList);
            detalleClaveAccesoAdapter.notifyDataSetChanged();
            txt_pregunta_autenticacion.setText(usuarioEntityArrayList.get(0).getPregunta());
            correo = detalleClaveAccesoAdapter.getItem(0).getEmail();
            //txt_respuesta_pregunta.requestFocus();
        }
    }

    public void salir() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Está seguro que desea salir de la aplicación?");
        alertDialog.setTitle("Salir");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(ContrasenaOlvidada.this, LoginNumeroCliente.class);
                startActivityForResult(intent, 0);
                finish();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    private class EnvioCorreoElectronico extends AsyncTask<String, Void, UsuarioEntity> {

        @Override
        protected UsuarioEntity doInBackground(String... params) {
            UsuarioEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.EnvioCorreo(correo, "CAMBIO DE CLAVE DE ACCESO POR OLVIDO", "SU CLAVE DE ACCESO HA SIDO CAMBIADA. SI USTED NO SOLICITÓ ESTE CAMBIO, POR FAVOR CONTÁCTESE CON NOSOTROS.");
            } catch (Exception e) {
                user = null;
                //flag_clic_ingreso = 0;;
            }
            return user;
        }
    }
}
