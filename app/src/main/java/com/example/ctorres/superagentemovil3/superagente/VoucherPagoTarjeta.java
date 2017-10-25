package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;


import java.util.Date;

public class VoucherPagoTarjeta extends Activity {

    LinearLayout btn_otra_operacion, btn_salir, btn_pagar_otra_tarjeta;
    String monto, num_tarjeta;
    TextView tv_monto_importe, tv_fecha_pago, txt_hora_pago, tv_importe_pagar, tv_tarjeta_cifrada, tv_tarjeta_cifrada_cargo;
    String importe, tipo_moneda_deuda, tarjeta_cargo;
    private UsuarioEntity usuario;
    String cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voucher_pago_tarjeta);

        btn_otra_operacion = (LinearLayout) findViewById(R.id.btn_efectuar_otra_operacion);
        btn_salir = (LinearLayout) findViewById(R.id.btn_salir_operacion);
        btn_pagar_otra_tarjeta = (LinearLayout) findViewById(R.id.btn_pagar_otra_tarjeta);
        tv_monto_importe = (TextView) findViewById(R.id.tv_importe_pagar);
        tv_fecha_pago = (TextView) findViewById(R.id.tv_fecha_pago);
        txt_hora_pago = (TextView) findViewById(R.id.txt_hora_pago);
        tv_tarjeta_cifrada = (TextView) findViewById(R.id.tv_tarjeta_cifrada);
        tv_tarjeta_cifrada_cargo = (TextView) findViewById(R.id.tv_tarjeta_cifrada_cargo);
        //tv_fecha_pago = (TextView) findViewById(R.id.tv_fecha_pago);

        Bundle extras = getIntent().getExtras();
        monto = extras.getString("monto");
        importe = "IMPORTE: " + extras.getString("tipo_moneda_deuda") + " " +extras.getString("monto");
        usuario = extras.getParcelable("usuario");
        tipo_moneda_deuda = extras.getString("tipo_moneda_deuda");
        cliente = extras.getString("cliente");
        num_tarjeta = "TARJETA CIFRADA: " + extras.getString("num_tarjeta");
        tarjeta_cargo = "TARJETA DE CARGO: " + extras.getString("tarjeta_cargo");

        tv_monto_importe.setText(importe);
        tv_tarjeta_cifrada.setText(num_tarjeta);
        tv_tarjeta_cifrada_cargo.setText(tarjeta_cargo);
        tv_fecha_pago.setText(obtenerFecha());
        txt_hora_pago.setText(obtenerHora());


        btn_otra_operacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VoucherPagoTarjeta.this, MenuCliente.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });

        btn_pagar_otra_tarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VoucherPagoTarjeta.this, SeleccionTarjetaPago.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });

    }

    public void salir() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Esta seguro que desea salir de la aplicación?");
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

    public String obtenerHora() {
        String hora;

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        int horaS = today.hour;
        int min = today.minute;
        int seg = today.second;
        //para probar en celulares se comenta y cuando es con emuladores se descomenta
        //horaS = horaS - 5;

        hora = "HORA: " + horaS + ":" + min + ":" + seg;

        return hora;
    }

    public String obtenerFecha() {

        String fecha;

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        int dia = today.monthDay;
        int mes = today.month;
        int año = today.year;
        mes = mes + 1;

        fecha = "FECHA: " + dia + "/" + mes + "/" + año;

        return fecha;
    }
}
