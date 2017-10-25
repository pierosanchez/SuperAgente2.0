package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

public class VoucherPagoTarjetaConCredito extends Activity {

    LinearLayout btn_otra_operacion, btn_salir, btn_pagar_otra_tarjeta;
    Bitmap b;
    ImageView signImage;
    Button btn_fimar;
    TextView tv_fecha_pago, txt_hora_pago, txt_importe_pagar, tv_tarjeta_cifrada_credito, tv_tarjeta_cifrada_cargo_credito;
    private UsuarioEntity usuario;
    String monto, importe, tipo_moneda_deuda, num_tarjeta, tarjeta_cargo;
    String cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voucher_pago_tarjeta_con_credito);

        signImage = (ImageView) findViewById(R.id.signImage);
        btn_fimar = (Button) findViewById(R.id.btn_firmar);
        btn_otra_operacion = (LinearLayout) findViewById(R.id.btn_efectuar_otra_operacion);
        btn_pagar_otra_tarjeta = (LinearLayout) findViewById(R.id.btn_pagar_otra_tarjeta);
        btn_salir = (LinearLayout) findViewById(R.id.btn_salir_operacion);
        tv_fecha_pago = (TextView) findViewById(R.id.tv_fecha_pago);
        txt_hora_pago = (TextView) findViewById(R.id.txt_hora_pago);
        txt_importe_pagar = (TextView) findViewById(R.id.txt_importe_pagar);
        tv_tarjeta_cifrada_credito = (TextView) findViewById(R.id.tv_tarjeta_cifrada_credito);
        tv_tarjeta_cifrada_cargo_credito = (TextView) findViewById(R.id.tv_tarjeta_cifrada_cargo_credito);

        Bundle extras = getIntent().getExtras();
        usuario = extras.getParcelable("usuario");
        monto = extras.getString("monto");
        importe = "IMPORTE: " + extras.getString("tipo_moneda_deuda") + " " +extras.getString("monto");
        txt_importe_pagar.setText(importe);
        tipo_moneda_deuda = extras.getString("tipo_moneda_deuda");
        num_tarjeta = "TARJETA CIFRADA: " + extras.getString("num_tarjeta");
        tarjeta_cargo = "TARJETA DE CARGO: " + extras.getString("tarjeta_cargo");
        cliente = extras.getString("cliente");

        /*bmp = (Bitmap) extras.getParcelable("firmabitmap");
        if (bmp != null) {
            signImage.setImageBitmap(bmp);
        }*/

        tv_fecha_pago.setText(obtenerFecha());
        txt_hora_pago.setText(obtenerHora());
        tv_tarjeta_cifrada_credito.setText(num_tarjeta);
        tv_tarjeta_cifrada_cargo_credito.setText(tarjeta_cargo);

        btn_fimar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VoucherPagoTarjetaConCredito.this, CaptureSignature.class);
                intent.putExtra("cliente", cliente);
                startActivityForResult(intent, 0);
                /*startActivity(intent);
                finish();*/
            }
        });

        btn_otra_operacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VoucherPagoTarjetaConCredito.this, MenuCliente.class);
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
                Intent intent = new Intent(VoucherPagoTarjetaConCredito.this, SeleccionTarjetaPago.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == 1) {
            b = BitmapFactory.decodeByteArray(
                    data.getByteArrayExtra("byteArray"), 0,
                    data.getByteArrayExtra("byteArray").length);
            signImage.setImageBitmap(b);
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            b = BitmapFactory.decodeByteArray(
                    data.getByteArrayExtra("byteArray"), 0,
                    data.getByteArrayExtra("byteArray").length);
            signImage.setImageBitmap(b);
        }
    }

    public void salir(){
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
