package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.DetalleCuentaAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.CuentaEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;

public class ActualizarCuenta extends Activity {

    Button btn_guardar_actualizacion_cuenta, btn_regresar_actualizacion_cuenta;
    EditText txt_nuevo_num_cuenta;
    String id_cuenta;
    private UsuarioEntity usuario;
    Spinner spinnerBanco;
    String[] bancos = {"Interbank", "BCP", "BBVA", "Scotiabank", "Otros"};
    String cliente;
    DetalleCuentaAdapter detalleCuentaAdapter;
    ArrayList<CuentaEntity> cuentaEntityArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_cuenta);

        txt_nuevo_num_cuenta = (EditText) findViewById(R.id.txt_nuevo_num_cuenta);

        btn_guardar_actualizacion_cuenta = (Button) findViewById(R.id.btn_guardar_actualizacion_cuenta);
        btn_regresar_actualizacion_cuenta = (Button) findViewById(R.id.btn_regresar_actualizacion_cuenta);

        spinnerBanco = (Spinner) findViewById(R.id.spinnerBanco);

        Bundle bundle = getIntent().getExtras();
        id_cuenta = bundle.getString("id_cuenta");
        usuario = bundle.getParcelable("usuario");
        cliente = bundle.getString("cliente");

        cuentaEntityArrayList = null;
        detalleCuentaAdapter = new DetalleCuentaAdapter(cuentaEntityArrayList, getApplication());

        ejecutarLista();

        cargarBancos();

        btn_guardar_actualizacion_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActualizarCuenta.actualizarCuenta actualizarCuenta = new ActualizarCuenta.actualizarCuenta();
                actualizarCuenta.execute();

                Intent intent = new Intent(ActualizarCuenta.this, ListadoCuentasUsuario.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });

        btn_regresar_actualizacion_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActualizarCuenta.this, ListadoCuentasUsuario.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });
    }

    private class actualizarCuenta extends AsyncTask<String, Void, CuentaEntity> {
        String numCuenta = txt_nuevo_num_cuenta.getText().toString();

        @Override
        protected CuentaEntity doInBackground(String... params) {
            CuentaEntity cuenta;
            try {

                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                cuenta = dao.actualizarCuenta(numCuenta, id_cuenta, obtenerBancoCuenta());

            } catch (Exception e) {
                cuenta = null;
                //flag_clic_ingreso = 0;;
            }
            return cuenta;
        }
    }

    public void cargarBancos() {
        ArrayAdapter<String> adaptadorBanco = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bancos);
        spinnerBanco.setAdapter(adaptadorBanco);
    }

    public int obtenerBancoCuenta(){
        String banco;
        int cod_banco = 0;
        banco = spinnerBanco.getSelectedItem().toString();

        if (banco.equals("Interbank")) {
            cod_banco = 3;
        } else if (banco.equals("BCP")) {
            cod_banco = 2;
        } else if (banco.equals("BBVA")) {
            cod_banco = 4;
        } else if (banco.equals("Scotiabank")) {
            cod_banco = 1;
        } else if (banco.equals("Otros")) {
            cod_banco = 5;
        }

        return cod_banco;
    }

    private void ejecutarLista() {

        try {
            ActualizarCuenta.ListadoCuentas listadoBeneficiario = new ActualizarCuenta.ListadoCuentas();
            listadoBeneficiario.execute();
        } catch (Exception e) {
            //listadoBeneficiario = null;
        }

    }

    private class ListadoCuentas extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                cuentaEntityArrayList = dao.DetalleCuenta(id_cuenta);
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            detalleCuentaAdapter.setNewListTarjetaBin(cuentaEntityArrayList);
            detalleCuentaAdapter.notifyDataSetChanged();
            txt_nuevo_num_cuenta.setText(cuentaEntityArrayList.get(0).getNumCuenta());
        }
    }
}
