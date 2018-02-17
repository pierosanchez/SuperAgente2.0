package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.adapter.GetUsuarioReniecAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteBD;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.utils.Constante;

public class InformacionPersonalActivity extends Activity {


    private Toolbar toolbar;
    //private RadioButton acepta_termino;
    private Button btn_siguiente, btnRegresar, btn_reniec;
    private EditText txt_nombre, txt_apellido, txt_pass, txt_re_pass, txt_email, txt_movil, dni_cliente, txt_ape_materno, dni_cliente_digito_control; //txt_sexo
    private String _nombreUsuario, _apellidoUsuario, _passUsuario, _repassUsuario, _emailUsuario, _movilUsuario, _dniUsuario, numCliente, _apMaternoUsuario, sexoUsuario;
    private UsuarioEntity usuario;
    private Drawable drawableRight;
    //private EditText txt_password;
    private ProgressBar circleProgressBar;
    ArrayList<UsuarioEntity> usuarioEntityArrayList;
    GetUsuarioReniecAdapter getUsuarioReniecAdapter;
    private int validaDNI;
    private String[] genero = {"Masculino", "Femenino"};
    private Spinner sp_genero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_personal);

        //acepta_termino = (RadioButton) findViewById(R.id.rbtn_acepta_terminos);
        btn_siguiente = (Button) findViewById(R.id.btn_siguiente);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        //btn_reniec = (Button) findViewById(R.id.btn_reniec);

        txt_nombre = (EditText) findViewById(R.id.nombre);
        txt_apellido = (EditText) findViewById(R.id.apellido);
        txt_ape_materno = (EditText) findViewById(R.id.apellidoM);
        txt_email = (EditText) findViewById(R.id.email);
        txt_movil = (EditText) findViewById(R.id.movil);
        //txt_sexo = (EditText) findViewById(R.id.sexo);
        //txt_password = (EditText) findViewById(R.id.password);
        dni_cliente = (EditText) findViewById(R.id.dni_cliente);
        dni_cliente_digito_control = (EditText) findViewById(R.id.dni_cliente_digito_control);

        sp_genero = (Spinner) findViewById(R.id.sp_genero);

        circleProgressBar = (ProgressBar) findViewById(R.id.circleProgressBar);

        Bundle bundle = getIntent().getExtras();
        numCliente = bundle.getString("movil");

        cargarGenero();

        /*acepta_termino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_siguiente.setEnabled(true);
            }
        });*/

        if (numCliente.length() != 0) {
            txt_movil.setText(numCliente);
            txt_movil.setEnabled(false);
        } else {
            txt_movil.setEnabled(true);
        }

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InformacionPersonalActivity.this, TerminosCondiciones.class);
                intent.putExtra("movil", numCliente);
                startActivity(intent);
                finish();
            }
        });

        /*btn_reniec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dni = dni_cliente.getText().toString();
                if (!dni.equals("")) {
                    usuarioEntityArrayList = null;
                    getUsuarioReniecAdapter = new GetUsuarioReniecAdapter(usuarioEntityArrayList, getApplication());

                    ejecutarLista();
                }
            }
        });*/

        dni_cliente.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String dni = dni_cliente.getText().toString();
                if (dni.length() == 8) {
                    dni_cliente_digito_control.requestFocus();
                    usuarioEntityArrayList = null;
                    getUsuarioReniecAdapter = new GetUsuarioReniecAdapter(usuarioEntityArrayList, getApplication());

                    ejecutarLista();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addListenerBotonSiguiente();
    }

    public void addListenerBotonSiguiente() {
        btn_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = txt_nombre.getText().toString().trim();
                String apellidos = txt_apellido.getText().toString().trim();
                String email = txt_email.getText().toString().trim();
                String movil = txt_movil.getText().toString().trim();
                //String pass = txt_password.getText().toString().trim();
                String dni = dni_cliente.getText().toString().trim();
                String dniDigitoControl = dni_cliente_digito_control.getText().toString().trim();
                circleProgressBar.setVisibility(View.VISIBLE);
                //String re_pass = txt_re_pass.getText().toString().trim();
                try {
                    //if (!pass.equals("")) {// && !re_pass.equals("")) {
                    _nombreUsuario = nombre.replace(" ", "%20");
                    _apellidoUsuario = apellidos.replace(" ", "%20");
                    _emailUsuario = email;
                    _movilUsuario = movil;
                    //_passUsuario = pass;
                    _dniUsuario = dni;

                    if (!_dniUsuario.equals("") && !_nombreUsuario.equals("") && !_apellidoUsuario.equals("") && !_emailUsuario.equals("") && !_movilUsuario.equals("") && dniDigitoControl.length() != 0) {
                        if (_movilUsuario.length() == 9 && dniDigitoControl.length() != 0 && _emailUsuario.matches(Constante.EMAILPATTERN)) {
                            /*circleProgressBar.setVisibility(View.INVISIBLE);*/

                            InformacionPersonalActivity.ValidarUsuario validador = new InformacionPersonalActivity.ValidarUsuario();
                            validador.execute();

                        } else {
                            if (!_emailUsuario.matches(Constante.EMAILPATTERN)) {
                                Toast.makeText(InformacionPersonalActivity.this, "El mail ingresado es incorecto", Toast.LENGTH_SHORT).show();
                                circleProgressBar.setVisibility(View.GONE);
                            } else if (dniDigitoControl.length() == 0) {
                                Toast.makeText(InformacionPersonalActivity.this, "Por favor ingrese el digito de control del DNI", Toast.LENGTH_SHORT).show();
                                circleProgressBar.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(InformacionPersonalActivity.this, "El movil ingresado es incorrecto, vuelva a ingresar", Toast.LENGTH_SHORT).show();
                                circleProgressBar.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        String mensaje_error = "";
                        if (_nombreUsuario.equals("")) {
                            mensaje_error = getString(R.string.msg_error_ingresar_nombre);
                            circleProgressBar.setVisibility(View.GONE);
                        }
                        if (_apellidoUsuario.equals("")) {
                            mensaje_error = getString(R.string.msg_error_ingresar_apellido);
                            circleProgressBar.setVisibility(View.GONE);
                        }
                        if (_emailUsuario.equals("")) {
                            mensaje_error = getString(R.string.msg_error_ingresar_email);
                            circleProgressBar.setVisibility(View.GONE);
                        }
                        if (_movilUsuario.equals("")) {
                            mensaje_error = getString(R.string.msg_error_ingresar_movil);
                            circleProgressBar.setVisibility(View.GONE);
                        }
                        if (dniDigitoControl.length() == 0) {
                            mensaje_error = getString(R.string.msg_error_digito_control_dni);
                            circleProgressBar.setVisibility(View.GONE);
                        }

                        Toast.makeText(InformacionPersonalActivity.this, mensaje_error, Toast.LENGTH_SHORT).show();
                    }
                    //} else {
                        /*mensaje_error = getString(R.string.msg_error_userpass);
                        msg_error.setText(mensaje_error);*/
                    //Toast.makeText(InformacionPersonalActivity.this, "Ingresar contraseña", Toast.LENGTH_SHORT).show();
                    //}
                } catch (Exception e) {
                    Log.e("ERROR", Log.getStackTraceString(e));
                }
            }
        });
    }

    private class ValidarUsuario extends AsyncTask<String, Void, UsuarioEntity> {
        String dni = dni_cliente.getText().toString().trim() + " - " + dni_cliente_digito_control.getText().toString().trim();
        String apeMaterno = txt_ape_materno.getText().toString();
        String sexo = sp_genero.getSelectedItem().toString();
        @Override
        protected UsuarioEntity doInBackground(String... params) {
            UsuarioEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.getUsuarioLogin(_dniUsuario, _nombreUsuario, _apellidoUsuario, apeMaterno, sexo, _emailUsuario, _movilUsuario);
                /*SuperAgenteBD superAgenteBD = new SuperAgenteBD(InformacionPersonalActivity.this);
                SQLiteDatabase db = superAgenteBD.getWritableDatabase();
                db.execSQL("INSERT INTO Cliente(movil) VALUES('" + _movilUsuario + "')");
                db.close();*/

            } catch (Exception e) {
                user = null;
                //fldag_clic_ingreso = 0;;
            }
            return user;
        }

        @Override
        protected void onPostExecute(UsuarioEntity user) {
            boolean sinConexion = false;
            String mensaje_error = "";
            usuario = user;
            //Log.e("BBDD", "Usuario" + usuario.getUsuario() + "Clave" + usuario.getClave());
            if (usuario != null) {//usuario.getUsuario() != null) {
                if (usuario.getUsuarioId() != null) {
                    if (Integer.parseInt(usuario.getUsuarioId()) == 0) {
                        Toast.makeText(InformacionPersonalActivity.this, "El número ingresado ya esta registrado para ese número de DNI", Toast.LENGTH_SHORT).show();
                        circleProgressBar.setVisibility(View.INVISIBLE);
                    } else if (Integer.parseInt(usuario.getUsuarioId()) == -1) {
                        Toast.makeText(InformacionPersonalActivity.this, "El número ingresado ya esta registrado en la base de datos", Toast.LENGTH_SHORT).show();
                        circleProgressBar.setVisibility(View.INVISIBLE);
                    } else {
                        Intent sanipesIntent = new Intent(InformacionPersonalActivity.this, InformacionDomicilioElectronicaPersonal.class);
                        sanipesIntent.putExtra("usuario", usuario);
                        sanipesIntent.putExtra("dni", dni);
                        startActivity(sanipesIntent);
                        finish();
                    }
                } else {
                    mensaje_error = getString(R.string.msg_error_sin_conexion);
                    sinConexion = true;
                    circleProgressBar.setVisibility(View.INVISIBLE);
                }
            } else {
                mensaje_error = getString(R.string.msg_error_sin_conexion);
                sinConexion = true;
                circleProgressBar.setVisibility(View.INVISIBLE);
            }

            if (sinConexion) {
                Toast.makeText(InformacionPersonalActivity.this, mensaje_error, Toast.LENGTH_SHORT).show();
                circleProgressBar.setVisibility(View.INVISIBLE);
            }

        }
    }

    private void ejecutarLista(){

        try {
            InformacionPersonalActivity.ListadoBeneficiario listadoBeneficiario = new InformacionPersonalActivity.ListadoBeneficiario();
            listadoBeneficiario.execute();
        } catch (Exception e){
            //listadoBeneficiario = null;
        }

    }

    private class ListadoBeneficiario extends AsyncTask<String,Void,Void> {

        String dni = dni_cliente.getText().toString();

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
            if (getUsuarioReniecAdapter.getItem(0).getRptaReniec().equals("01")){
                Toast.makeText(InformacionPersonalActivity.this, "Usted ya se encuentra afiliado", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(InformacionPersonalActivity.this, LoginNumeroCliente.class);
                startActivityForResult(intent, 0);
                finish();

            } else if (getUsuarioReniecAdapter.getItem(0).getRptaReniec().equals("00")){
                /*validaDNI++;
                if (validaDNI < 4) {
                    Toast.makeText(InformacionPersonalActivity.this, "El número de DNI ingresado no existe en la base de datos. Vuelva a ingresar su DNI.", Toast.LENGTH_LONG).show();
                    dni_cliente.setText("");
                } else if (validaDNI == 4){
                    Toast.makeText(InformacionPersonalActivity.this, "Ha excedido el intento de correcciones. El sistema lo regresará a la pantalla principal.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(InformacionPersonalActivity.this, LoginNumeroCliente.class);
                    startActivity(intent);
                    finish();
                }*/
            } else {
                txt_nombre.setText(usuarioEntityArrayList.get(0).getNombre());
                txt_apellido.setText(usuarioEntityArrayList.get(0).getApellido());
                txt_ape_materno.setText(usuarioEntityArrayList.get(0).getApe_materno());
                //txt_sexo.setText(usuarioEntityArrayList.get(0).getSexo());
            }
        }
    }

    private void cargarGenero(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, genero);
        sp_genero.setAdapter(adapter);
    }
}
