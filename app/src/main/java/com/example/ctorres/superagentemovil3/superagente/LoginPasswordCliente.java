package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteBD;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

public class LoginPasswordCliente extends Activity {

    private String numero;
    private EditText clave_acceso;
    private TextView tv_olvido_contraseña;
    private Button btn_aceptar, btn_salir;
    private String _clase;
    private int validaContra = 0;
    private ProgressBar circleProgressBar;
    private int validar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_password_cliente);


        clave_acceso = (EditText) findViewById(R.id.clave_acceso);

        btn_aceptar = (Button) findViewById(R.id.btn_aceptar);
        btn_salir = (Button) findViewById(R.id.btn_salir);

        circleProgressBar = (ProgressBar) findViewById(R.id.circleProgressBar);

        tv_olvido_contraseña = (TextView) findViewById(R.id.tv_olvido_contraseña);

        Bundle bundle = getIntent().getExtras();
        numero = bundle.getString("numero");

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _clase = clave_acceso.getText().toString();
                if (_clase.length() == 0) {
                    Toast.makeText(LoginPasswordCliente.this, "Ingrese su clave de acceso por favor", Toast.LENGTH_LONG).show();
                } else {
                    circleProgressBar.setVisibility(View.VISIBLE);
                    LoginPasswordCliente.ValidarLogin validaNumero = new LoginPasswordCliente.ValidarLogin();
                    validaNumero.execute();
                }
            }
        });

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });

        tv_olvido_contraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sanipesIntent = new Intent(LoginPasswordCliente.this, ContrasenaOlvidada.class);
                sanipesIntent.putExtra("numero", numero);
                startActivity(sanipesIntent);
                finish();
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
                    validar++;
                    if (validar < 4) {
                        Toast.makeText(LoginPasswordCliente.this, "La Contraseña ingresada, no es correcta.", Toast.LENGTH_LONG).show();
                        clave_acceso.setText("");
                        circleProgressBar.setVisibility(View.GONE);
                    } else if (validar == 4) {
                        Intent sanipesIntent = new Intent(LoginPasswordCliente.this, VentanaErrores.class);
                        sanipesIntent.putExtra("numero", numero);
                        startActivityForResult(sanipesIntent, 0);
                        finish();
                    }
                } else if (usuarioEntity.getUsuarioId().equals("01")) {
                    //queDeseaHacer();
                    circleProgressBar.setVisibility(View.GONE);
                    Intent sanipesIntent = new Intent(LoginPasswordCliente.this, VentanaErrores.class);
                    //sanipesIntent.putExtra("usuario", userEntity);
                    //sanipesIntent.putExtra("movil", movil);
                    //sanipesIntent.putExtra("cliente", userEntity.getNombreApellido());
                    startActivityForResult(sanipesIntent, 0);
                    finish();
                } else if (usuarioEntity.getUsuarioId().equals("03")) {
                    Toast.makeText(LoginPasswordCliente.this, "Lo sentimos, este usuario se encuentra bloqueado. Contáctese a la central.", Toast.LENGTH_LONG).show();
                    clave_acceso.setText("");
                    circleProgressBar.setVisibility(View.GONE);
                } else if (usuarioEntity.getUsuarioId().equals("04")) {
                    Toast.makeText(LoginPasswordCliente.this, "Lo sentimos, la cuenta ingresada aún no esta activa. Contáctese a la central.", Toast.LENGTH_LONG).show();
                    clave_acceso.setText("");
                    circleProgressBar.setVisibility(View.GONE);
                } else {
                    try {
                        SuperAgenteBD superAgenteBD = new SuperAgenteBD(LoginPasswordCliente.this);
                        if (obtenerDataSQLite() == false) {
                            SQLiteDatabase db = superAgenteBD.getWritableDatabase();
                            db.execSQL("INSERT INTO Cliente(movil) VALUES('" + numero + "')");
                            db.close();

                            circleProgressBar.setVisibility(View.GONE);
                            Intent sanipesIntent = new Intent(LoginPasswordCliente.this, MenuCliente.class);
                            sanipesIntent.putExtra("usuario", usuarioEntity);
                            sanipesIntent.putExtra("cliente", usuarioEntity.getNombreApellido());
                            sanipesIntent.putExtra("cli_dni", usuarioEntity.getDni());
                            startActivity(sanipesIntent);
                            finish();
                        } else {
                            circleProgressBar.setVisibility(View.GONE);
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

    public void salir() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Está seguro que desea salir de la aplicación?");
        alertDialog.setTitle("Salir");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(LoginPasswordCliente.this, LoginNumeroCliente.class);
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
}
