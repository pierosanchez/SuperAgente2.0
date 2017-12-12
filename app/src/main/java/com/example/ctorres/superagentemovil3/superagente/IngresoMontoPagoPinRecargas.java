package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.adapter.MonedaAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.MonedaEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class IngresoMontoPagoPinRecargas extends Activity {

    String nro_telefono, tipo_moneda_recarga, tipo_operador;
    String tipo_moneda, cli_dni;
    String cliente, tarjeta_cargo, banco, emisor_tarjeta, validacion_tarjeta;
    private UsuarioEntity usuario;
    int tipo_tarjeta_pago;
    Double monto_recarga;
    Button btn_continuar_pago, btn_cancelar_pagoRecarga;
    ArrayList<MonedaEntity> monedaEntityArrayList;
    MonedaAdapter monedaAdapter;
    TextView textViewNombreApellidoUsuario, tv_numero_clave_cifrada_cargo, spinnerMonedaPagarRecarga;
    EditText txt_moneda_pagar_recarga, txt_pin_recargas;
    DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingreso_monto_pago_pin_recargas);

        btn_continuar_pago = (Button) findViewById(R.id.btn_continuar_pagoRecarga);
        btn_cancelar_pagoRecarga = (Button) findViewById(R.id.btn_cancelar_pagoRecarga);

        tv_numero_clave_cifrada_cargo = (TextView) findViewById(R.id.tv_numero_clave_cifrada_cargo);
        textViewNombreApellidoUsuario = (TextView) findViewById(R.id.textViewNombreApellidoUsuario);

        txt_moneda_pagar_recarga = (EditText) findViewById(R.id.txt_moneda_pagar_recarga);
        txt_pin_recargas = (EditText) findViewById(R.id.txt_pin_recargas);

        spinnerMonedaPagarRecarga = (TextView) findViewById(R.id.spinnerMonedaPagarRecarga);

        Bundle extras = getIntent().getExtras();
        usuario = extras.getParcelable("usuario");
        cliente = extras.getString("cliente");
        tarjeta_cargo = extras.getString("tarjeta_cargo");
        emisor_tarjeta = extras.getString("emisor_tarjeta");
        banco = extras.getString("banco");
        tipo_tarjeta_pago = extras.getInt("tipo_tarjeta_pago");
        cli_dni = extras.getString("cli_dni");
        nro_telefono = extras.getString("nro_telefono");
        tipo_moneda_recarga = extras.getString("tipo_moneda_recarga");
        tipo_operador = extras.getString("tipo_operador");
        monto_recarga = extras.getDouble("monto_recarga");
        validacion_tarjeta = extras.getString("validacion_tarjeta");

        tv_numero_clave_cifrada_cargo.setText(tarjeta_cargo);
        textViewNombreApellidoUsuario.setText(cliente);
        txt_moneda_pagar_recarga.setText(convertirDecimales());
        txt_pin_recargas.requestFocus();
        txt_moneda_pagar_recarga.setEnabled(false);
        spinnerMonedaPagarRecarga.setEnabled(false);
        spinnerMonedaPagarRecarga.setText(tipo_moneda_recarga);

        /*monedaEntityArrayList = null;
        monedaAdapter = new MonedaAdapter(monedaEntityArrayList, getApplication());
        spinnerMonedaPagarRecarga.setAdapter(monedaAdapter);

        ejecutarListaMoneda();*/

        btn_continuar_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pin = txt_pin_recargas.getText().toString();
                if (pin.length() != 0) {
                    Intent intent = new Intent(IngresoMontoPagoPinRecargas.this, ConformidadClienteRecarga.class);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                    intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                    intent.putExtra("banco", banco);
                    intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                    intent.putExtra("cli_dni", cli_dni);
                    intent.putExtra("nro_telefono", nro_telefono);
                    intent.putExtra("tipo_moneda_recarga", tipo_moneda_recarga);
                    intent.putExtra("tipo_operador", tipo_operador);
                    intent.putExtra("monto_recarga", monto_recarga);
                    intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(IngresoMontoPagoPinRecargas.this, "INGRESE EL PIN", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_cancelar_pagoRecarga.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                cancelar();
            }
        });
    }

    private void ejecutarListaMoneda() {

        try {

            IngresoMontoPagoPinRecargas.ListadoMoneda listadoMonedas = new IngresoMontoPagoPinRecargas.ListadoMoneda();
            listadoMonedas.execute();
        } catch (Exception e) {
            //listadoBeneficiario = null;
        }

    }

    private class ListadoMoneda extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                monedaEntityArrayList = dao.ListarMoneda();
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            monedaAdapter.setNewListMoneda(monedaEntityArrayList);
            monedaAdapter.notifyDataSetChanged();
        }
    }

    public void cancelar() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Esta seguro que desea cacelar la transacción?");
        alertDialog.setTitle("Cancelar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(IngresoMontoPagoPinRecargas.this, MenuCliente.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                intent.putExtra("cli_dni", cli_dni);
                startActivity(intent);
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

    public String convertirDecimales(){
        return decimalFormat.format(monto_recarga);
    }
}
