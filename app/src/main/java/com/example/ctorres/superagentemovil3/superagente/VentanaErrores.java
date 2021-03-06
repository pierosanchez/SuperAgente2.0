package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.utils.Constante;

import java.util.Timer;
import java.util.TimerTask;

public class VentanaErrores extends Activity {

    Button btn_opcion1, btn_opcion2, btn_opcion3;
    UsuarioEntity usuario;
    TextView tv_titulo, tv_mensaje, mensaje_ayuda;
    LinearLayout ll_boton_opcion3;
    String numCliente, cliente, cli_dni, asuntoMail, bodyMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ventana_errores);

        btn_opcion1 = (Button) findViewById(R.id.btn_opcion1);
        btn_opcion2 = (Button) findViewById(R.id.btn_opcion2);
        btn_opcion3 = (Button) findViewById(R.id.btn_opcion3);

        tv_titulo = (TextView) findViewById(R.id.tv_titulo);
        tv_mensaje = (TextView) findViewById(R.id.tv_mensaje);
        mensaje_ayuda = (TextView) findViewById(R.id.mensaje_ayuda);

        ll_boton_opcion3 = (LinearLayout) findViewById(R.id.ll_boton_opcion3);

        String callingActivity = this.getCallingActivity().getClassName();

        if (callingActivity.equals(Constante.ACTIVITYROOT + "LoginActivity")) {
            /*Bundle bundle = getIntent().getExtras();
            usuario = bundle.getParcelable("usuario");
            numCliente = bundle.getString("movil");*/


            btn_opcion1.setText("Reintentar");
            btn_opcion2.setText("Registrarse");

            btn_opcion1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sanipesIntent = new Intent(VentanaErrores.this, LoginActivity.class);
                    startActivityForResult(sanipesIntent, 0);
                    finish();
                }
            });

            btn_opcion2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sanipesIntent = new Intent(VentanaErrores.this, TerminosCondiciones.class);
                    //sanipesIntent.putExtra("movil", numCliente);
                    startActivity(sanipesIntent);
                    finish();
                }
            });
        } else if (callingActivity.equals(Constante.ACTIVITYROOT + "LoginNumeroCliente")) {
            Bundle bundle = getIntent().getExtras();
            //usuario = bundle.getParcelable("usuario");
            numCliente = bundle.getString("numero");


            btn_opcion1.setText("Reintentar");
            btn_opcion2.setText("Registrarse");
            tv_mensaje.setText(R.string.mensaje_celular_no_registrado);

            btn_opcion1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(VentanaErrores.this, LoginNumeroCliente.class);
                    startActivityForResult(intent, 0);
                    finish();
                }
            });

            btn_opcion2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sanipesIntent = new Intent(VentanaErrores.this, TerminosCondiciones.class);
                    sanipesIntent.putExtra("movil", numCliente);
                    startActivity(sanipesIntent);
                    finish();
                }
            });
        } else if (callingActivity.equals(Constante.ACTIVITYROOT + "InformacionTarjeta")) {
            Bundle bundle = getIntent().getExtras();
            usuario = bundle.getParcelable("usuario");

            tv_titulo.setText("¿QUE DESEA HACER?");

            ll_boton_opcion3.setVisibility(View.VISIBLE);

            btn_opcion1.setText("Agregar beneficiarios");
            btn_opcion2.setText("Agregar Cuentas");
            btn_opcion3.setText("Terminar Afiliación");
            tv_mensaje.setVisibility(View.GONE);

            btn_opcion1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(VentanaErrores.this, IngresoDatosBeneficiarios2De4.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                    finish();
                }
            });

            btn_opcion2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(VentanaErrores.this, ControlMaximoNumeroCuentas.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                    finish();
                }
            });

            btn_opcion3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(VentanaErrores.this, AfiliacionExitosaConsulta.class);
                    startActivity(intent);
                    finish();
                }
            });
        } else if (callingActivity.equals(Constante.ACTIVITYROOT + "ControlMaximoNumeroCuentas")) {
            Bundle bundle = getIntent().getExtras();
            usuario = bundle.getParcelable("usuario");

            tv_titulo.setText("¿QUE DESEA HACER?");

            btn_opcion1.setText("Agregar beneficiarios");
            btn_opcion2.setText("Terminar Afiliación");
            tv_mensaje.setVisibility(View.GONE);

            btn_opcion1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(VentanaErrores.this, IngresoDatosBeneficiarios2De4.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                    finish();
                }
            });

            btn_opcion2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(VentanaErrores.this, AfiliacionExitosaConsulta.class);
                    startActivity(intent);
                    finish();
                }
            });

        } else if (callingActivity.equals(Constante.ACTIVITYROOT + "SeleccionTarjetaPago")) {
            Bundle bundle = getIntent().getExtras();
            usuario = bundle.getParcelable("usuario");
            cliente = bundle.getString("cliente");
            cli_dni = bundle.getString("cli_dni");

            tv_titulo.setText("NO PUEDE REALIZAR EL PAGO DE TARJETAS");

            btn_opcion1.setText(R.string.btn_regresar_menu);
            btn_opcion2.setText(R.string.btn_salir);
            tv_mensaje.setText(R.string.mensaje_poder_hacer_pago);

            btn_opcion1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(VentanaErrores.this, MenuCliente.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("cli_dni", cli_dni);
                    startActivity(intent);
                    finish();
                }
            });

            btn_opcion2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    salir();
                }
            });
        } else if (callingActivity.equals(Constante.ACTIVITYROOT + "LoginPasswordCliente")) {

            Bundle bundle = getIntent().getExtras();
            numCliente = bundle.getString("numero");

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    finish();
                }
            };

            Timer timer = new Timer();
            timer.schedule(task, 30000);

            tv_titulo.setText(R.string.mensaje_clave_errada_exceso_intentos);
            mensaje_ayuda.setVisibility(View.GONE);

            btn_opcion1.setVisibility(View.GONE);
            btn_opcion2.setVisibility(View.VISIBLE);
            ll_boton_opcion3.setVisibility(View.VISIBLE);
            btn_opcion3.setVisibility(View.VISIBLE);
            btn_opcion2.setText(R.string.btn_ha_olvidado_clave_acceso);
            btn_opcion3.setText(R.string.btn_salir);
            tv_mensaje.setText(R.string.intentos_mas_limite);

            btn_opcion3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    salir();
                }
            });

            btn_opcion2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sanipesIntent = new Intent(VentanaErrores.this, ContrasenaOlvidada.class);
                    sanipesIntent.putExtra("numero", numCliente);
                    startActivity(sanipesIntent);
                    finish();
                }
            });

        } else if (callingActivity.equals(Constante.ACTIVITYROOT + "ContrasenaOlvidada")) {

            tv_titulo.setText(R.string.titulo_recuperacion_segunda_clave);

            btn_opcion1.setVisibility(View.GONE);
            btn_opcion2.setVisibility(View.GONE);
            ll_boton_opcion3.setVisibility(View.VISIBLE);
            btn_opcion3.setText(R.string.btn_salir);
            tv_mensaje.setText(R.string.tv_msg_llegara_correo);
            mensaje_ayuda.setText(R.string.tv_msg_llame_central_servicio);

            btn_opcion3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    salir();
                }
            });
        }

    }

    public void salir() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Está seguro que desea salir de la aplicación?");
        alertDialog.setTitle("Salir");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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

    /*private class EnvioCorreoElectronico extends AsyncTask<String, Void, UsuarioEntity> {

        @Override
        protected UsuarioEntity doInBackground(String... params) {
            UsuarioEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.EnvioCorreo(correo, "CAMBIO DE CLAVE DE ACCESO", "SU CLAVE DE ACCESO HA SIDO CAMBIADA. SI USTED NO SOLICITÓ ESTE CAMBIO, PORFAVOR CONTÁCTESE CON NOSTROS.");
            } catch (Exception e) {
                user = null;
                //flag_clic_ingreso = 0;;
            }
            return user;
        }
    }*/
}
