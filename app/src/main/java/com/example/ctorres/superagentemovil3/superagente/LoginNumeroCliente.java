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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteBD;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.utils.Constante;

public class LoginNumeroCliente extends Activity {

    private EditText usuario;
    private Button btn_aceptar, btn_salir;
    private String _numero;
    private ProgressBar circleProgressBar;
    String callingActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_numero_cliente);

        usuario = (EditText) findViewById(R.id.usuario);

        btn_aceptar = (Button) findViewById(R.id.btn_aceptar);
        btn_salir = (Button) findViewById(R.id.btn_salir);

        circleProgressBar = (ProgressBar) findViewById(R.id.circleProgressBar);

        //callingActivity = this.getCallingActivity().getClassName();

        /*if (callingActivity.equals(Constante.ACTIVITYROOT + "SplashActivity")) {
            setNumeroCelUsuarioFromSQLite();
        }*/

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _numero = usuario.getText().toString();
                if (_numero.length() == 0) {
                    Toast.makeText(LoginNumeroCliente.this, "Ingrese su número de celular por favor", Toast.LENGTH_LONG).show();
                } else if (_numero.length() != 9) {
                    Toast.makeText(LoginNumeroCliente.this, "Número de celular incorrecto", Toast.LENGTH_LONG).show();
                } else {
                    circleProgressBar.setVisibility(View.VISIBLE);
                    LoginNumeroCliente.ValidaNumeroCliente validaNumero = new LoginNumeroCliente.ValidaNumeroCliente();
                    validaNumero.execute();
                }
            }
        });

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
            }
        });
    }

    private class ValidaNumeroCliente extends AsyncTask<String, Void, UsuarioEntity> {

        String _celular = usuario.getText().toString();

        @Override
        protected UsuarioEntity doInBackground(String... params) {
            UsuarioEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.LoginValidaCelularCliente(_celular);
                /*SuperAgenteBD superAgenteBD = new SuperAgenteBD(LoginNumeroCliente.this);
                SQLiteDatabase db = superAgenteBD.getWritableDatabase();
                db.execSQL("INSERT INTO Cliente(movil) VALUES('" + _celular + "')");
                db.close();*/

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
                    circleProgressBar.setVisibility(View.GONE);
                    Intent sanipesIntent = new Intent(LoginNumeroCliente.this, VentanaErrores.class);
                    sanipesIntent.putExtra("numero", _celular);
                    startActivityForResult(sanipesIntent, 0);
                    finish();
                } else if (usuarioEntity.getValidaLoginCelular().equals("00")) {
                    circleProgressBar.setVisibility(View.GONE);
                    Intent sanipesIntent = new Intent(LoginNumeroCliente.this, LoginPasswordCliente.class);
                    sanipesIntent.putExtra("numero", _celular);
                    startActivity(sanipesIntent);
                    finish();
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

        //int iMovil = cursor.getColumnIndex("movil");

        if (cursor.getCount() > 0) {
            result = true;
            //usuario.setText(cursor.getString(iMovil)); //obtiene el valor de la columna que se pasa como parametro aun queda probar esta parte del codigo
            cursor.close();
        } else {
            result = false;
            cursor.close();
        }

        return result;
    }

    private void setNumeroCelUsuarioFromSQLite() {
        String _celular = usuario.getText().toString();
        //instacia de la base de datos que se maneja en la aplicación
        SuperAgenteBD superAgenteBD = new SuperAgenteBD(LoginNumeroCliente.this);
        //se crea un query para que la base de datos lo ejecute y lo almacene en el cursor
        Cursor cursor = superAgenteBD.getReadableDatabase().query("Cliente", new String[]{"movil"}, null, null, null, null, null, "1");

        //se verifica que el cursor tenga data dentro de él
        if (cursor.moveToFirst() && cursor.getCount() >= 1) {
            do {
                //se indica el nombre de la columna de la tabla a buscar
                int iMovil = cursor.getColumnIndex("movil");

                //se setea el indice dentro de una variable convirtiendo el resultado a string
                String numero = cursor.getString(iMovil);

                //setea la variable cadena dentro de la caja de texto del numero
                usuario.setText(numero);
                //usuario.setEnabled(false);
            } while (cursor.moveToNext());
        } else { //si no se encuentra ningun dato en la base de datos del celular
            circleProgressBar.setVisibility(View.GONE);
            //se envia a la pantalla de errores para que permita registrarse al nuevo usuario
            Intent sanipesIntent = new Intent(LoginNumeroCliente.this, VentanaErrores.class);
            sanipesIntent.putExtra("numero", _celular);
            startActivityForResult(sanipesIntent, 0);
            finish();
        }
        cursor.close();
    }
}
