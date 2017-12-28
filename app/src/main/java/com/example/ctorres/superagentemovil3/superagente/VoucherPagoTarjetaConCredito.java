package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.adapter.NumeroUnicoAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.NumeroUnico;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.entity.VoucherPagoTarjetaCreditoEntity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class VoucherPagoTarjetaConCredito extends Activity {

    LinearLayout btn_otra_operacion, btn_salir, btn_pagar_otra_tarjeta;
    Bitmap b;
    ImageView signImage;
    Button btn_fimar;
    TextView tv_fecha_pago, txt_hora_pago, txt_importe_pagar, tv_tarjeta_cifrada_credito,
            tv_tarjeta_cifrada_cargo_credito, txt_banco_tarjeta_pago, txt_banco_tarjeta_cargo, txt_numero_unico;
    private UsuarioEntity usuario;
    String monto, importe, tipo_moneda_deuda, num_tarjeta, tarjeta_cargo, numTarjetaCargo, numTarjetaPago;
    String cliente, cli_dni, desc_corta_banco, banco_tarjeta_pago, desc_corta_banco_tarjeta_cargo,
            banco_tarjeta_cargo, fechaV, horaV, nro_unico;
    NumeroUnicoAdapter numeroUnicoAdapter;
    ArrayList<NumeroUnico> numeroUnicoArrayList;
    private VoucherPagoTarjetaCreditoEntity voucherTarjetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voucher_pago_tarjeta_con_credito);

        signImage = (ImageView) findViewById(R.id.signImage);
        btn_fimar = (Button) findViewById(R.id.btn_firmar);

        btn_otra_operacion = (LinearLayout) findViewById(R.id.btn_efectuar_otra_operacion);
        btn_pagar_otra_tarjeta = (LinearLayout) findViewById(R.id.btn_pagar_otra_tarjeta);
        btn_salir = (LinearLayout) findViewById(R.id.btn_salir_operacion);

        txt_banco_tarjeta_pago = (TextView) findViewById(R.id.txt_banco_tarjeta_pago);
        tv_fecha_pago = (TextView) findViewById(R.id.tv_fecha_pago);
        txt_hora_pago = (TextView) findViewById(R.id.txt_hora_pago);
        txt_importe_pagar = (TextView) findViewById(R.id.txt_importe_pagar);
        tv_tarjeta_cifrada_credito = (TextView) findViewById(R.id.tv_tarjeta_cifrada_credito);
        tv_tarjeta_cifrada_cargo_credito = (TextView) findViewById(R.id.tv_tarjeta_cifrada_cargo_credito);
        txt_banco_tarjeta_cargo = (TextView) findViewById(R.id.txt_banco_tarjeta_cargo);
        txt_numero_unico = (TextView) findViewById(R.id.txt_numero_unico);

        Bundle extras = getIntent().getExtras();
        usuario = extras.getParcelable("usuario");
        monto = extras.getString("monto");
        importe = "IMPORTE: " + extras.getString("tipo_moneda_deuda") + " " + transformarMonto();
        txt_importe_pagar.setText(importe);
        tipo_moneda_deuda = extras.getString("tipo_moneda_deuda");
        numTarjetaPago = extras.getString("num_tarjeta");
        numTarjetaCargo = extras.getString("tarjeta_cargo");
        num_tarjeta = "TARJETA CIFRADA: " + numTarjetaPago;
        tarjeta_cargo = "TARJETA DE CARGO: " + numTarjetaCargo;
        cliente = extras.getString("cliente");
        cli_dni = extras.getString("cli_dni");
        desc_corta_banco = extras.getString("desc_corta_banco");
        nro_unico = extras.getString("nro_unico");
        banco_tarjeta_pago = "BANCO DE LA TARJETA A PAGAR: " + desc_corta_banco;
        desc_corta_banco_tarjeta_cargo = extras.getString("desc_corta_banco_tarjeta_cargo");
        banco_tarjeta_cargo = "BANCO DE LA TARJETA DE CARGO: " + desc_corta_banco_tarjeta_cargo;
        fechaV = "FECHA: " + obtenerFecha();
        horaV = "HORA: " + obtenerHora();

        VoucherPagoTarjetaConCredito.getNumUnico numUnico = new VoucherPagoTarjetaConCredito.getNumUnico();
        numUnico.execute();

        /*bmp = (Bitmap) extras.getParcelable("firmabitmap");
        if (bmp != null) {
            signImage.setImageBitmap(bmp);
        }*/

        tv_fecha_pago.setText(obtenerFecha());
        txt_hora_pago.setText(obtenerHora());
        tv_tarjeta_cifrada_credito.setText(num_tarjeta);
        tv_tarjeta_cifrada_cargo_credito.setText(tarjeta_cargo);
        txt_banco_tarjeta_pago.setText(banco_tarjeta_pago);
        txt_banco_tarjeta_cargo.setText(banco_tarjeta_cargo);

        btn_fimar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VoucherPagoTarjetaConCredito.this, CaptureSignature.class);
                intent.putExtra("cliente", cliente);
                intent.putExtra("cli_dni", cli_dni);
                startActivityForResult(intent, 0);
                /*startActivity(intent);
                finish();*/
            }
        });

        btn_otra_operacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signImage.getDrawable() == null) {
                    Toast.makeText(VoucherPagoTarjetaConCredito.this, "Por favor registre su firma", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(VoucherPagoTarjetaConCredito.this, MenuCliente.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("cli_dni", cli_dni);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signImage.getDrawable() == null) {
                    Toast.makeText(VoucherPagoTarjetaConCredito.this, "Por favor registre su firma", Toast.LENGTH_LONG).show();
                } else {
                    salir();
                }
            }
        });

        btn_pagar_otra_tarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signImage.getDrawable() == null) {
                    Toast.makeText(VoucherPagoTarjetaConCredito.this, "Por favor registre su firma", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(VoucherPagoTarjetaConCredito.this, SeleccionTarjetaPago.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("cli_dni", cli_dni);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            b = BitmapFactory.decodeByteArray(
                    data.getByteArrayExtra("byteArray"), 0,
                    data.getByteArrayExtra("byteArray").length);
            signImage.setImageBitmap(b);
            btn_fimar.setVisibility(View.GONE);
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

        hora = horaS + ":" + min + ":" + seg;

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

        fecha = dia + "/" + mes + "/" + año;

        return fecha;
    }

    public String transformarMonto(){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        double montoD = Double.parseDouble(monto);
        return decimalFormat.format(montoD);
    }

    private class getNumUnico extends AsyncTask<String, Void, VoucherPagoTarjetaCreditoEntity> {

        @Override
        protected VoucherPagoTarjetaCreditoEntity doInBackground(String... params) {
            VoucherPagoTarjetaCreditoEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.getNumeroUnicoTarjeta(nro_unico);

            } catch (Exception e) {
                user = null;
                //fldag_clic_ingreso = 0;
            }
            return user;
        }

        @Override
        protected void onPostExecute(VoucherPagoTarjetaCreditoEntity voucherTransferenciasEntity){
            voucherTarjetas = voucherTransferenciasEntity;
            if (voucherTarjetas != null){
                if (voucherTarjetas.getNumeroUnico() != null){
                    txt_numero_unico.setText(voucherTarjetas.getNumeroUnico());
                } else {
                    Toast.makeText(VoucherPagoTarjetaConCredito.this, "no se trajo el numero unico", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(VoucherPagoTarjetaConCredito.this, "la entidad no tiene data", Toast.LENGTH_LONG).show();
            }
        }
    }
}
