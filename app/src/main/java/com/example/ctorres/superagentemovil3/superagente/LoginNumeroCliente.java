package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
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

public class LoginNumeroCliente extends Activity {

    private EditText usuario;
    private Button btn_aceptar, btn_salir;
    private String _numero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_numero_cliente);

        usuario = (EditText) findViewById(R.id.usuario);

        btn_aceptar = (Button) findViewById(R.id.btn_aceptar);
        btn_salir = (Button) findViewById(R.id.btn_salir);

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _numero = usuario.getText().toString();
                if (_numero.length() == 0){
                    Toast.makeText(LoginNumeroCliente.this, "Ingrese su número de celular por favor", Toast.LENGTH_LONG).show();
                } else if (_numero.length() != 9){
                    Toast.makeText(LoginNumeroCliente.this, "Número de celular incorrecto", Toast.LENGTH_LONG).show();
                } else {
                    LoginNumeroCliente.ValidaNumeroCliente validaNumero = new LoginNumeroCliente.ValidaNumeroCliente();
                    validaNumero.execute();
                }
            }
        });
    }

    private class ValidaNumeroCliente extends AsyncTask<String, Void, UsuarioEntity>{

        String _celular = usuario.getText().toString();

        @Override
        protected UsuarioEntity doInBackground(String... params) {
            UsuarioEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.LoginValidaCelularCliente(_celular);

            } catch (Exception e) {
                user = null;
                //fldag_clic_ingreso = 0;;
            }
            return user;
        }

        @Override
        protected void onPostExecute(UsuarioEntity usuarioEntity) {
            if (usuarioEntity != null) {
                if (usuarioEntity.getValidaLoginCelular().equals("01")) {
                    Intent sanipesIntent = new Intent(LoginNumeroCliente.this, VentanaErrores.class);
                    startActivityForResult(sanipesIntent, 0);
                    finish();
                } else if (usuarioEntity.getValidaLoginCelular().equals("00")) {
                    Intent sanipesIntent = new Intent(LoginNumeroCliente.this, LoginPasswordCliente.class);
                    sanipesIntent.putExtra("numero", _celular);
                    startActivity(sanipesIntent);
                    finish();
                }
            }
        }
    }
}
