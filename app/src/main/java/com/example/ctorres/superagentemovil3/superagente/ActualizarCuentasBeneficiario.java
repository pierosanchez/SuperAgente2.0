package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.CuentaEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

public class ActualizarCuentasBeneficiario extends Activity {

    Button btn_guardar_actualizacion_cuenta, btn_regresar_actualizacion_cuenta;
    EditText txt_nuevo_num_cuenta;
    int id_cuenta_benef;
    private UsuarioEntity usuario;
    String dni_benef;
    String cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_cuentas_beneficiario);

        txt_nuevo_num_cuenta = (EditText) findViewById(R.id.txt_nuevo_num_cuenta);

        btn_guardar_actualizacion_cuenta = (Button) findViewById(R.id.btn_guardar_actualizacion_cuenta);
        btn_regresar_actualizacion_cuenta = (Button) findViewById(R.id.btn_regresar_actualizacion_cuenta);

        Bundle bundle = getIntent().getExtras();
        id_cuenta_benef = bundle.getInt("id_cuenta_benef");
        usuario = bundle.getParcelable("usuario");
        dni_benef = bundle.getString("dni_benef");
        cliente = bundle.getString("cliente");

        btn_guardar_actualizacion_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActualizarCuentasBeneficiario.actualizarCuenta actualizarCuenta = new ActualizarCuentasBeneficiario.actualizarCuenta();
                actualizarCuenta.execute();

                Intent intent = new Intent(ActualizarCuentasBeneficiario.this, ListadoCuentasTarjetasBeneficiario.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("dni_benef", dni_benef);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });

        btn_regresar_actualizacion_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActualizarCuentasBeneficiario.this, ListadoCuentasTarjetasBeneficiario.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("dni_benef", dni_benef);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });

    }

    private class actualizarCuenta extends AsyncTask<String, Void, BeneficiarioEntity> {
        String numCuenta = txt_nuevo_num_cuenta.getText().toString();

        @Override
        protected BeneficiarioEntity doInBackground(String... params) {
            BeneficiarioEntity cuenta;
            try {

                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                cuenta = dao.actualizarCuentaBeneficiario(numCuenta, id_cuenta_benef);

            } catch (Exception e) {
                cuenta = null;
                //flag_clic_ingreso = 0;;
            }
            return cuenta;
        }

    }
}
