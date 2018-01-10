package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteBD;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

public class LoginPasswordCliente extends Activity {

    private String numero;
    private EditText clave_acceso;
    private Button btn_aceptar, btn_salir;
    private String _clase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_password_cliente);

        clave_acceso = (EditText) findViewById(R.id.clave_acceso);

        btn_aceptar = (Button) findViewById(R.id.btn_aceptar);
        btn_salir = (Button) findViewById(R.id.btn_salir);

        Bundle bundle = getIntent().getExtras();
        numero = bundle.getString("numero");

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _clase = clave_acceso.getText().toString();
                if (_clase.length() == 0) {
                    Toast.makeText(LoginPasswordCliente.this, "Ingrese su clave de acceso por favor", Toast.LENGTH_LONG).show();
                } else {
                    LoginPasswordCliente.ValidarLogin validaNumero = new LoginPasswordCliente.ValidarLogin();
                    validaNumero.execute();
                }
            }
        });
    }

    private class ValidarLogin extends AsyncTask<String, Void, UsuarioEntity> {
        String clave = clave_acceso.getText().toString();

        @Override
        protected UsuarioEntity doInBackground(String... params) {
            UsuarioEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.getUsuarioLog(numero, clave);

            } catch (Exception e) {
                user = null;
                //fldag_clic_ingreso = 0;;
            }
            return user;
        }

        @Override
        protected void onPostExecute(UsuarioEntity usuarioEntity) {
            if (usuarioEntity == null) {
                Toast.makeText(LoginPasswordCliente.this, "Error de red, por favor conectese a una red Wi-Fi o actives sus datos moviles", Toast.LENGTH_LONG).show();
            } else if (usuarioEntity.getUsuarioId() != null) {
                if (usuarioEntity.getUsuarioId().equals("02")) {
                    Toast.makeText(LoginPasswordCliente.this, "La Contraseña ingresada, no es correcta", Toast.LENGTH_LONG).show();
                    clave_acceso.setText("");
                } else if (usuarioEntity.getUsuarioId().equals("01")) {
                    //queDeseaHacer();
                    Intent sanipesIntent = new Intent(LoginPasswordCliente.this, VentanaErrores.class);
                    //sanipesIntent.putExtra("usuario", userEntity);
                    //sanipesIntent.putExtra("movil", movil);
                    //sanipesIntent.putExtra("cliente", userEntity.getNombreApellido());
                    startActivityForResult(sanipesIntent, 0);
                    finish();
                } else if (usuarioEntity.getUsuarioId().equals("03")) {
                    Toast.makeText(LoginPasswordCliente.this, "Lo sentimos, este usuario se encuentra bloqueado. Contáctese a la central.", Toast.LENGTH_LONG).show();
                    clave_acceso.setText("");
                } else if (usuarioEntity.getUsuarioId().equals("04")) {
                    Toast.makeText(LoginPasswordCliente.this, "Lo sentimos, la cuenta ingresada aún no esta activa. Contáctese a la central.", Toast.LENGTH_LONG).show();
                    clave_acceso.setText("");
                } else {
                    try {
                        SuperAgenteBD superAgenteBD = new SuperAgenteBD(LoginPasswordCliente.this);
                        if (obtenerDataSQLite() == false) {
                            SQLiteDatabase db = superAgenteBD.getWritableDatabase();
                            db.execSQL("INSERT INTO Cliente(movil) VALUES('" + numero + "')");
                            db.close();

                            Intent sanipesIntent = new Intent(LoginPasswordCliente.this, MenuCliente.class);
                            sanipesIntent.putExtra("usuario", usuarioEntity);
                            sanipesIntent.putExtra("cliente", usuarioEntity.getNombreApellido());
                            sanipesIntent.putExtra("cli_dni", usuarioEntity.getDni());
                            startActivity(sanipesIntent);
                            finish();
                        } else {
                            Intent sanipesIntent = new Intent(LoginPasswordCliente.this, MenuCliente.class);
                            sanipesIntent.putExtra("usuario", usuarioEntity);
                            sanipesIntent.putExtra("cliente", usuarioEntity.getNombreApellido());
                            sanipesIntent.putExtra("cli_dni", usuarioEntity.getDni());
                            startActivity(sanipesIntent);
                            finish();
                        }
                    } catch (Exception e) {
                        //flag_clic_ingreso = 0;
                    }
                }
            }
        }
    }

    public boolean obtenerDataSQLite() {
        boolean result;
        SuperAgenteBD superAgenteBD = new SuperAgenteBD(this);
        /*SQLiteDatabase db = superAgenteBD.getReadableDatabase();

        db.execSQL("SELECT movil FROM Cliente");*/

        Cursor cursor = superAgenteBD.getReadableDatabase().rawQuery("SELECT movil FROM Cliente", null);

        if (cursor.getCount() > 0) {
            result = true;
        } else {
            result = false;
        }

        return result;
    }
}
