package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.utils.Constante;

public class VentanaErrores extends Activity {

    Button btn_opcion1, btn_opcion2, btn_opcion3;
    UsuarioEntity usuario;
    TextView tv_titulo, tv_mensaje;
    LinearLayout ll_boton_opcion3;
    String numCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ventana_errores);

        btn_opcion1 = (Button) findViewById(R.id.btn_opcion1);
        btn_opcion2 = (Button) findViewById(R.id.btn_opcion2);
        btn_opcion3 = (Button) findViewById(R.id.btn_opcion3);

        tv_titulo = (TextView) findViewById(R.id.tv_titulo);
        tv_mensaje = (TextView) findViewById(R.id.tv_mensaje);

        ll_boton_opcion3 = (LinearLayout) findViewById(R.id.ll_boton_opcion3);

        String callingActivity = this.getCallingActivity().getClassName();

        if (callingActivity.equals(Constante.ACTIVITYROOT + "LoginActivity")){
            Bundle bundle = getIntent().getExtras();
            usuario = bundle.getParcelable("usuario");
            numCliente = bundle.getString("movil");

            btn_opcion1.setText("Reintentar");
            btn_opcion2.setText("Registrarse");

            btn_opcion1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sanipesIntent = new Intent(VentanaErrores.this, LoginActivity.class);
                    //sanipesIntent.putExtra("usuario", usuario);
                    startActivity(sanipesIntent);
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

        }
    }
}
