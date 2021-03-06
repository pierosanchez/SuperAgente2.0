package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.adapter.MonedaAdapter;
import com.example.ctorres.superagentemovil3.entity.MonedaEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class IngresoMontoPagoFirmaRecarga extends Activity {

    Button btn_continuar_pago, btn_cancelar_pago;
    String cliente, tarjeta_cargo, banco, emisor_tarjeta, validacion_tarjeta;
    TextView tv_nombre_cliente_comercio, tv_tarjeta_cifrada_comercio, txt_moneda_pagar_recarga, spinnerTipoMoneda;
    EditText txt_monto_pago;
    private UsuarioEntity usuario;
    MonedaAdapter monedaAdapter;
    ArrayList<MonedaEntity> monedaEntityArrayList;
    String tipo_moneda, cli_dni;
    int tipo_tarjeta_pago;
    String nro_telefono, tipo_moneda_recarga, tipo_operador;
    Double monto_recarga;
    DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingreso_monto_pago_firma_recarga);

        btn_continuar_pago = (Button) findViewById(R.id.btn_continuar_pagoRecarga);
        btn_cancelar_pago = (Button) findViewById(R.id.btn_cancelar_pagoRecarga);

        tv_nombre_cliente_comercio = (TextView) findViewById(R.id.textViewNombreApellidoUsuario);
        tv_tarjeta_cifrada_comercio = (TextView) findViewById(R.id.tv_numero_clave_cifrada_cargo);
        txt_moneda_pagar_recarga = (TextView) findViewById(R.id.txt_moneda_pagar_recarga);

        spinnerTipoMoneda = (TextView) findViewById(R.id.spinnerMonedaPagarRecarga);


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

        spinnerTipoMoneda.setEnabled(false);
        txt_moneda_pagar_recarga.setEnabled(false);

        tv_nombre_cliente_comercio.setText(cliente);
        tv_tarjeta_cifrada_comercio.setText(tarjeta_cargo);
        txt_moneda_pagar_recarga.setText(transformarMontoRecarga());
        spinnerTipoMoneda.setText(tipo_moneda_recarga);
        String[] unicaOpcion = {tipo_moneda_recarga};

        //spinnerTipoMoneda.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,unicaOpcion));

        btn_continuar_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngresoMontoPagoFirmaRecarga.this, ConformidadClienteRecarga.class);
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
            }
        });

        btn_cancelar_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });
    }

    public String transformarMontoRecarga(){
        return decimalFormat.format(monto_recarga);
    }

    public void cancelar() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Está seguro que desea cancelar la transacción?");
        alertDialog.setTitle("Cancelar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(IngresoMontoPagoFirmaRecarga.this, MenuCliente.class);
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
}
