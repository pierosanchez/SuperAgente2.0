package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
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

import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends Activity {

    private EditText usuario, clave_acceso;
    private Button btn_aceptar, btn_registrarse, btn_salir;
    private UsuarioEntity userEntity;
    private TextView tv_olvido_contraseña;
    private ProgressBar circleProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = (EditText) findViewById(R.id.usuario);
        //usuario.setText(getPhoneNumber());
        clave_acceso = (EditText) findViewById(R.id.clave_acceso);

        btn_aceptar = (Button) findViewById(R.id.btn_aceptar);
        //btn_registrarse = (Button) findViewById(R.id.btn_registrarse);
        btn_salir = (Button) findViewById(R.id.btn_salir);

        tv_olvido_contraseña = (TextView) findViewById(R.id.tv_olvido_contraseña);

        circleProgressBar = (ProgressBar) findViewById(R.id.circleProgressBar);


        /*TelephonyManager tMgr = (TelephonyManager)getApplication().getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);*/

        tv_olvido_contraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sanipesIntent = new Intent(LoginActivity.this, ContrasenaOlvidada.class);
                //sanipesIntent.putExtra("cliente", userEntity.getNombreApellido());
                startActivity(sanipesIntent);
                finish();
            }
        });

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleProgressBar.setVisibility(View.VISIBLE);

                String user = usuario.getText().toString();
                String clave = clave_acceso.getText().toString();

                if (!user.trim().equals("") && !clave.trim().equals("")) {
                    if (user.length() == 9) {
                        try {
                            TimerTask task = new TimerTask() {
                                @Override
                                public void run() {
                                    LoginActivity.ValidarLogin validador = new LoginActivity.ValidarLogin();
                                    validador.execute();
                                }
                            };
                            Timer timer = new Timer();
                            timer.schedule(task, 500);
                        } catch (Exception e) {
                            //flag_clic_ingreso = 0;
                        }
                    } else if (user.length() != 9) {
                        Toast.makeText(LoginActivity.this, "Número de celular no válido", Toast.LENGTH_LONG).show();
                        circleProgressBar.setVisibility(View.GONE);
                    }
                } else if (user.equals("") && clave.equals("")) {
                    Toast.makeText(LoginActivity.this, "Ingrese sus credenciales de acceso", Toast.LENGTH_LONG).show();
                    circleProgressBar.setVisibility(View.GONE);
                } else if (clave.equals("")) {
                    Toast.makeText(LoginActivity.this, "Ingrese su clave de acceso", Toast.LENGTH_LONG).show();
                    circleProgressBar.setVisibility(View.GONE);
                } else if (user.equals("")) {
                    Toast.makeText(LoginActivity.this, "Ingrese su número de celular", Toast.LENGTH_LONG).show();
                    circleProgressBar.setVisibility(View.GONE);
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

        if (obtenerDataSQLite() == false) {
            Intent intent = new Intent(LoginActivity.this, VentanaErrores.class);
            startActivityForResult(intent, 0);
            finish();
        }
    }

    private String getPhoneNumber() {

        String numeroTelefono;
        //Get SIM Number
        try {

            TelephonyManager mTelephonyManager;
            mTelephonyManager = (TelephonyManager) LoginActivity.this.getSystemService(Context.TELEPHONY_SERVICE);
            numeroTelefono = mTelephonyManager.getLine1Number();
        } catch (Exception e) {
            numeroTelefono = "";
        }

        /*if(numeroTelefono.toString().equals("") || numeroTelefono.equals("null")){
            //Get Phone Number
            TelephonyManager telemamanger = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            numeroTelefono = telemamanger.getSimSerialNumber();
//            numeroTelefono = mTelephonyManager.getSubscriberId();
        }*/
        return numeroTelefono;
    }

    private class ValidarLogin extends AsyncTask<String, Void, UsuarioEntity> {
        String movil = usuario.getText().toString();
        String clave = clave_acceso.getText().toString();

        @Override
        protected UsuarioEntity doInBackground(String... params) {
            UsuarioEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.getUsuarioLog(movil, clave);

            } catch (Exception e) {
                user = null;
                //fldag_clic_ingreso = 0;;
            }
            return user;
        }

        @Override
        protected void onPostExecute(UsuarioEntity usuarioEntity) {
            userEntity = usuarioEntity;
            if (userEntity.getUsuarioId() != null) {
                if (userEntity.getUsuarioId().equals("02")) {
                    Toast.makeText(LoginActivity.this, "La Contraseña ingresada, no es correcta", Toast.LENGTH_LONG).show();
                    clave_acceso.setText("");
                    circleProgressBar.setVisibility(View.GONE);
                } else if (userEntity.getUsuarioId().equals("01")) {
                    //queDeseaHacer();
                    circleProgressBar.setVisibility(View.GONE);
                    Intent sanipesIntent = new Intent(LoginActivity.this, VentanaErrores.class);
                    sanipesIntent.putExtra("usuario", userEntity);
                    sanipesIntent.putExtra("movil", movil);
                    sanipesIntent.putExtra("cliente", userEntity.getNombreApellido());
                    startActivityForResult(sanipesIntent, 0);
                    finish();
                } else if (userEntity.getUsuarioId().equals("03")) {
                    Toast.makeText(LoginActivity.this, "Lo sentimos, este usuario se encuentra bloqueado. Contáctese a la central.", Toast.LENGTH_LONG).show();
                    clave_acceso.setText("");
                    circleProgressBar.setVisibility(View.GONE);
                } else {
                    try {
                        circleProgressBar.setVisibility(View.GONE);
                        Intent sanipesIntent = new Intent(LoginActivity.this, MenuCliente.class);
                        sanipesIntent.putExtra("usuario", userEntity);
                        sanipesIntent.putExtra("cliente", userEntity.getNombreApellido());
                        startActivity(sanipesIntent);
                        finish();
                    } catch (Exception e) {
                        //flag_clic_ingreso = 0;
                    }

                }
            } else {
                //mensaje_error = getString(R.string.msg_error_sin_conexion);
                //sinConexion = true;

            }
        }
    }

    public void queDeseaHacer() {
        // con este tema personalizado evitamos los bordes por defecto
        final Dialog customDialog = new Dialog(this);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.dialog);

        TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
        titulo.setText("Celular no registrado");

        TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
        contenido.setText("¿Que desea hacer?");

        customDialog.findViewById(R.id.aceptar).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, TerminosCondiciones.class);
                startActivity(intent);
                finish();
            }
        });

        customDialog.findViewById(R.id.cancelar).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                customDialog.cancel();
            }
        });

        customDialog.show();
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
