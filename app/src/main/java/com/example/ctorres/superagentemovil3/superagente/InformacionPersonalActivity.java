package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.DetalleBeneficiarioAdapter;
import com.example.ctorres.superagentemovil3.dao.GetUsuarioReniecAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteBD;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.utils.Constante;

public class InformacionPersonalActivity extends Activity {


    private Toolbar toolbar;
    //private RadioButton acepta_termino;
    private Button btn_siguiente, btnRegresar, btn_reniec;
    private EditText txt_nombre, txt_apellido, txt_pass, txt_re_pass, txt_email, txt_movil, dni_cliente, txt_ape_materno, txt_sexo;
    private String _nombreUsuario, _apellidoUsuario, _passUsuario, _repassUsuario, _emailUsuario, _movilUsuario, _dniUsuario, numCliente, _apMaternoUsuario, sexoUsuario;
    private UsuarioEntity usuario;
    private Drawable drawableRight;
    //private EditText txt_password;
    private ProgressBar circleProgressBar;
    ArrayList<UsuarioEntity> usuarioEntityArrayList;
    GetUsuarioReniecAdapter getUsuarioReniecAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_personal);

        //acepta_termino = (RadioButton) findViewById(R.id.rbtn_acepta_terminos);
        btn_siguiente = (Button) findViewById(R.id.btn_siguiente);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        btn_reniec = (Button) findViewById(R.id.btn_reniec);

        txt_nombre = (EditText) findViewById(R.id.nombre);
        txt_apellido = (EditText) findViewById(R.id.apellido);
        txt_ape_materno = (EditText) findViewById(R.id.apellidoM);
        txt_email = (EditText) findViewById(R.id.email);
        txt_movil = (EditText) findViewById(R.id.movil);
        txt_sexo = (EditText) findViewById(R.id.sexo);
        //txt_password = (EditText) findViewById(R.id.password);
        dni_cliente = (EditText) findViewById(R.id.dni_cliente);
        circleProgressBar = (ProgressBar) findViewById(R.id.circleProgressBar);

        Bundle bundle = getIntent().getExtras();
        numCliente = bundle.getString("movil");

        /*acepta_termino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_siguiente.setEnabled(true);
            }
        });*/

        txt_movil.setText(numCliente);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InformacionPersonalActivity.this, TerminosCondiciones.class);
                startActivity(intent);
                finish();
            }
        });

        btn_reniec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dni = dni_cliente.getText().toString();
                if (!dni.equals("")) {
                    usuarioEntityArrayList = null;
                    getUsuarioReniecAdapter = new GetUsuarioReniecAdapter(usuarioEntityArrayList, getApplication());

                    ejecutarLista();
                }
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

                    if (!_dniUsuario.equals("") && !_nombreUsuario.equals("") && !_apellidoUsuario.equals("") && !_emailUsuario.equals("") && !_movilUsuario.equals("")) {
                        if (_movilUsuario.length() == 9 && _emailUsuario.matches(Constante.EMAILPATTERN)) {
                            /*circleProgressBar.setVisibility(View.INVISIBLE);*/

                            InformacionPersonalActivity.ValidarUsuario validador = new InformacionPersonalActivity.ValidarUsuario();
                            validador.execute();

                        } else {
                            if (!_emailUsuario.matches(Constante.EMAILPATTERN)) {
                                Toast.makeText(InformacionPersonalActivity.this, "El mail ingresado es incorecto", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(InformacionPersonalActivity.this, "El movil ingresado es incorrecto, vuelva a ingresar", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        String mensaje_error = "";
                        if (_nombreUsuario.equals(""))
                            mensaje_error = getString(R.string.msg_error_ingresar_nombre);
                        if (_apellidoUsuario.equals(""))
                            mensaje_error = getString(R.string.msg_error_ingresar_apellido);
                        if (_emailUsuario.equals(""))
                            mensaje_error = getString(R.string.msg_error_ingresar_email);
                        if (_movilUsuario.equals(""))
                            mensaje_error = getString(R.string.msg_error_ingresar_movil);

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
        String dni = dni_cliente.getText().toString().trim();
        String apeMaterno = txt_ape_materno.getText().toString();
        String sexo = txt_sexo.getText().toString();
        @Override
        protected UsuarioEntity doInBackground(String... params) {
            UsuarioEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.getUsuarioLogin(_dniUsuario, _nombreUsuario, _apellidoUsuario, apeMaterno, sexo, _emailUsuario, _movilUsuario);
                SuperAgenteBD superAgenteBD = new SuperAgenteBD(InformacionPersonalActivity.this);
                SQLiteDatabase db = superAgenteBD.getWritableDatabase();
                db.execSQL("INSERT INTO Cliente(movil) VALUES('" + _movilUsuario + "')");
                db.close();

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
            txt_nombre.setText(usuarioEntityArrayList.get(0).getNombre());
            txt_apellido.setText(usuarioEntityArrayList.get(0).getApellido());
            txt_ape_materno.setText(usuarioEntityArrayList.get(0).getApe_materno());
            txt_sexo.setText(usuarioEntityArrayList.get(0).getSexo());
        }
    }
}
