package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.Date;

public class IngresoMontoPagoPin extends Activity {

    String moneda[] = {"S/.", "US$"};
    Spinner spinnerMoneda;
    Button btn_continuar, btn_cancelar;
    EditText txt_moneda_pagar;
    ImageView imageView;
    Bitmap bmp;
    String monto, tipo_moneda_deuda;
    String num_tarjeta, tarjeta_cargo;
    TextView tv_numero_clave_cifrada_cargo, tv_tipo_moneda_deuda;
    int tipo_tarjeta, emisor_tarjeta;
    private UsuarioEntity usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingreso_monto_pago_pin);

        btn_continuar = (Button) findViewById(R.id.btn_continuar_pago);
        btn_cancelar = (Button) findViewById(R.id.btn_cancelar_pago);

        //spinnerMoneda = (Spinner) findViewById(R.id.spinnerMonedaPagar);

        txt_moneda_pagar = (EditText) findViewById(R.id.txt_moneda_pagar);

        imageView = (ImageView) findViewById(R.id.imageView);

        tv_numero_clave_cifrada_cargo = (TextView) findViewById(R.id.tv_numero_clave_cifrada_cargo);
        tv_tipo_moneda_deuda = (TextView) findViewById(R.id.tv_tipo_moneda_deuda);

        //cargarTipoMoneda();

        Bundle extras = getIntent().getExtras();
        monto = extras.getString("monto");
        txt_moneda_pagar.setText(extras.getString("monto"));
        usuario = extras.getParcelable("usuario");
        tipo_tarjeta = extras.getInt("tipo_tarjeta");
        emisor_tarjeta = extras.getInt("emisor_tarjeta");
        num_tarjeta = extras.getString("num_tarjeta");
        tipo_moneda_deuda = extras.getString("tipo_moneda_deuda");
        tarjeta_cargo = extras.getString("tarjeta_cargo");

        focTipoTarjeta();

        tv_numero_clave_cifrada_cargo.setText(num_tarjeta);
        tv_tipo_moneda_deuda.setText(tipo_moneda_deuda);

        btn_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngresoMontoPagoPin.this, VoucherPagoTarjeta.class);
                intent.putExtra("monto", monto);
                intent.putExtra("usuario", usuario);
                intent.putExtra("tipo_moneda_deuda", tipo_moneda_deuda);
                intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                intent.putExtra("num_tarjeta", num_tarjeta);
                startActivity(intent);
                finish();
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
                intent.putExtra("monto", monto);
                intent.putExtra("usuario", usuario);
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

    public void focTipoTarjeta(){
        if (emisor_tarjeta == 1) {
            imageView.setImageResource(R.drawable.visaicon);
        } else if (emisor_tarjeta == 2) {
            imageView.setImageResource(R.drawable.mastercardlogo);
        } else {
            imageView.setImageResource(R.drawable.americanexpressicon);
        }
    }

}
