package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.text.DecimalFormat;
import java.util.Date;

public class IngresoMontoPagoPin extends Activity {

    String moneda[] = {"S/.", "US$"};
    Spinner spinnerMoneda, sp_pago_cuotas, sp_cantidad_cuotas;
    Button btn_continuar, btn_cancelar;
    EditText txt_moneda_pagar, txt_pin;
    ImageView imageView;
    Bitmap bmp;
    String monto, tipo_moneda_deuda, cli_dni, desc_corta_banco_tarjeta_cargo, cliente, validacion_tarjeta;
    String num_tarjeta, tarjeta_cargo, desc_corta_banco;
    TextView tv_numero_clave_cifrada_cargo, tv_tipo_moneda_deuda, textViewNombreApellidoUsuario, tv_pago_cuotas;
    int tipo_tarjeta, emisor_tarjeta, tipo_tarjeta_pago;
    private UsuarioEntity usuario;
    String[] cuotas = {"No", "Si"};
    String[] cantidadCuotas = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" ,"21" ,"22" ,"23" ,"24" ,"25" ,"26" ,"27" ,"28" ,"29" ,"30" ,"31" ,"32", "33", "34", "35" ,"36"};
    LinearLayout ll_cantidad_cuotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingreso_monto_pago_pin);

        btn_continuar = (Button) findViewById(R.id.btn_continuar_pago);
        btn_cancelar = (Button) findViewById(R.id.btn_cancelar_pago);

        //spinnerMoneda = (Spinner) findViewById(R.id.spinnerMonedaPagar);
        sp_pago_cuotas = (Spinner) findViewById(R.id.sp_pago_cuotas);
        sp_cantidad_cuotas = (Spinner) findViewById(R.id.sp_cantidad_cuotas);

        txt_moneda_pagar = (EditText) findViewById(R.id.txt_moneda_pagar);
        txt_pin = (EditText) findViewById(R.id.txt_pin);

        imageView = (ImageView) findViewById(R.id.imageView);

        tv_numero_clave_cifrada_cargo = (TextView) findViewById(R.id.tv_numero_clave_cifrada_cargo);
        tv_tipo_moneda_deuda = (TextView) findViewById(R.id.tv_tipo_moneda_deuda);
        textViewNombreApellidoUsuario = (TextView) findViewById(R.id.textViewNombreApellidoUsuario);
        tv_pago_cuotas = (TextView) findViewById(R.id.tv_pago_cuotas);

        ll_cantidad_cuotas = (LinearLayout) findViewById(R.id.ll_cantidad_cuotas);

        //cargarTipoMoneda();

        Bundle extras = getIntent().getExtras();
        monto = extras.getString("monto");
        usuario = extras.getParcelable("usuario");
        tipo_tarjeta = extras.getInt("tipo_tarjeta");
        emisor_tarjeta = extras.getInt("emisor_tarjeta");
        num_tarjeta = extras.getString("num_tarjeta");
        tipo_moneda_deuda = extras.getString("tipo_moneda_deuda");
        tarjeta_cargo = extras.getString("tarjeta_cargo");
        cli_dni = extras.getString("cli_dni");
        cliente = extras.getString("cliente");
        desc_corta_banco = extras.getString("desc_corta_banco");
        desc_corta_banco_tarjeta_cargo = extras.getString("desc_corta_banco_tarjeta_cargo");
        validacion_tarjeta = extras.getString("validacion_tarjeta");
        tipo_tarjeta_pago = extras.getInt("tipo_tarjeta_pago");

        focTipoTarjeta();
        cargarCuotas();
        deseaCuotas();

        txt_pin.requestFocus();
        txt_moneda_pagar.setEnabled(false);
        tv_numero_clave_cifrada_cargo.setText(num_tarjeta);
        tv_tipo_moneda_deuda.setText(tipo_moneda_deuda);
        txt_moneda_pagar.setText(transformarMonto());
        textViewNombreApellidoUsuario.setText(cliente);

        if (tipo_tarjeta_pago == 2){
            sp_pago_cuotas.setVisibility(View.GONE);
            tv_pago_cuotas.setVisibility(View.GONE);
        }

        sp_pago_cuotas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getAdapter().getItem(position).equals("Si")){
                    ll_cantidad_cuotas.setVisibility(View.VISIBLE);
                } else if (parent.getAdapter().getItem(position).equals("No")){
                    ll_cantidad_cuotas.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pin = txt_pin.getText().toString();
                if (pin.length() == 0) {
                    Toast.makeText(IngresoMontoPagoPin.this, "INGRESE EL PIN", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(IngresoMontoPagoPin.this, VoucherPagoTarjeta.class);
                    intent.putExtra("monto", monto);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("tipo_moneda_deuda", tipo_moneda_deuda);
                    intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                    intent.putExtra("num_tarjeta", num_tarjeta);
                    intent.putExtra("cli_dni", cli_dni);
                    intent.putExtra("desc_corta_banco", desc_corta_banco);
                    intent.putExtra("desc_corta_banco_tarjeta_cargo", desc_corta_banco_tarjeta_cargo);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });

    }

    /*public void cargarTipoMoneda() {
        ArrayAdapter<String> adapterMoneda = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, moneda);
        spinnerMoneda.setAdapter(adapterMoneda);
    }*/

    public void cancelar() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Esta seguro que desea cacelar la transacción?");
        alertDialog.setTitle("Cancelar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(IngresoMontoPagoPin.this, MenuCliente.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cli_dni", cli_dni);
                intent.putExtra("cliente", cliente);
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

    public void focTipoTarjeta() {
        if (emisor_tarjeta == 1) {
            imageView.setImageResource(R.drawable.visaicon);
        } else if (emisor_tarjeta == 2) {
            imageView.setImageResource(R.drawable.mastercardlogo);
        } else {
            imageView.setImageResource(R.drawable.americanexpressicon);
        }
    }

    public String transformarMonto() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        double montoD = Double.parseDouble(monto);
        return decimalFormat.format(montoD);
    }

    public void cargarCuotas(){
        ArrayAdapter<String> cantidadCuota = new ArrayAdapter<String>(IngresoMontoPagoPin.this, android.R.layout.simple_spinner_dropdown_item, cantidadCuotas);
        sp_cantidad_cuotas.setAdapter(cantidadCuota);
    }

    public void deseaCuotas(){
        ArrayAdapter<String> cuota = new ArrayAdapter<String>(IngresoMontoPagoPin.this, android.R.layout.simple_spinner_dropdown_item, cuotas);
        sp_pago_cuotas.setAdapter(cuota);
    }
}
