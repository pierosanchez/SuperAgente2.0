package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.adapter.NumeroUnicoAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.NumeroUnico;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.entity.VoucherPagoConsumoEntity;
import com.example.ctorres.superagentemovil3.entity.VoucherPagoTarjetaCreditoEntity;
import com.example.ctorres.superagentemovil3.entity.VoucherTransferenciasEntity;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class VoucherPagoTarjeta extends Activity {

    LinearLayout btn_otra_operacion, btn_salir, btn_pagar_otra_tarjeta;
    String monto, num_tarjeta;
    TextView tv_monto_importe, tv_fecha_pago, txt_hora_pago, tv_importe_pagar, tv_tarjeta_cifrada,
            tv_tarjeta_cifrada_cargo, tv_banco_tarjeta_pago, tv_banco_tarjeta_cargo, txt_numero_unico;
    String importe, tipo_moneda_deuda, tarjeta_cargo, fechaV, horaV, numTarjetaCargo, numTarjetaPago, nro_unico;
    private UsuarioEntity usuario;
    String cliente, cli_dni, desc_corta_banco, banco_tarjeta_pago, banco_tarjeta_cargo, desc_corta_banco_tarjeta_cargo;
    NumeroUnicoAdapter numeroUnicoAdapter;
    ArrayList<NumeroUnico> numeroUnicoArrayList;
    private VoucherPagoTarjetaCreditoEntity voucherTarjetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voucher_pago_tarjeta);

        btn_otra_operacion = (LinearLayout) findViewById(R.id.btn_efectuar_otra_operacion);
        btn_salir = (LinearLayout) findViewById(R.id.btn_salir_operacion);
        btn_pagar_otra_tarjeta = (LinearLayout) findViewById(R.id.btn_pagar_otra_tarjeta);

        tv_banco_tarjeta_pago = (TextView) findViewById(R.id.tv_banco_tarjeta_pago);
        tv_monto_importe = (TextView) findViewById(R.id.tv_importe_pagar);
        tv_fecha_pago = (TextView) findViewById(R.id.tv_fecha_pago);
        txt_hora_pago = (TextView) findViewById(R.id.txt_hora_pago);
        tv_tarjeta_cifrada = (TextView) findViewById(R.id.tv_tarjeta_cifrada);
        tv_tarjeta_cifrada_cargo = (TextView) findViewById(R.id.tv_tarjeta_cifrada_cargo);
        tv_banco_tarjeta_cargo = (TextView) findViewById(R.id.tv_banco_tarjeta_cargo);
        txt_numero_unico = (TextView) findViewById(R.id.txt_numero_unico);
        //tv_fecha_pago = (TextView) findViewById(R.id.tv_fecha_pago);

        Bundle extras = getIntent().getExtras();
        monto = extras.getString("monto");
        importe = "IMPORTE: " + extras.getString("tipo_moneda_deuda") + " " +transformarMonto();
        usuario = extras.getParcelable("usuario");
        tipo_moneda_deuda = extras.getString("tipo_moneda_deuda");
        cliente = extras.getString("cliente");
        cli_dni = extras.getString("cli_dni");
        numTarjetaPago = extras.getString("num_tarjeta");
        numTarjetaCargo = extras.getString("tarjeta_cargo");
        nro_unico = extras.getString("nro_unico");
        num_tarjeta = "TARJETA CIFRADA: " + numTarjetaPago;
        tarjeta_cargo = "TARJETA DE CARGO: " + numTarjetaCargo;
        desc_corta_banco = extras.getString("desc_corta_banco");
        banco_tarjeta_pago = "BANCO DE LA TARJETA A PAGAR: " + desc_corta_banco;
        desc_corta_banco_tarjeta_cargo = extras.getString("desc_corta_banco_tarjeta_cargo");
        banco_tarjeta_cargo = "BANCO DE LA TARJETA DE CARGO: " + desc_corta_banco_tarjeta_cargo;
        fechaV = "FECHA: " + obtenerFecha();
        horaV = "HORA: " + obtenerHora();

        VoucherPagoTarjeta.getNumUnico numUnico = new VoucherPagoTarjeta.getNumUnico();
        numUnico.execute();

        tv_monto_importe.setText(importe);
        tv_tarjeta_cifrada.setText(num_tarjeta);
        tv_tarjeta_cifrada_cargo.setText(tarjeta_cargo);
        tv_banco_tarjeta_pago.setText(banco_tarjeta_pago);
        tv_fecha_pago.setText(fechaV);
        txt_hora_pago.setText(horaV);
        tv_banco_tarjeta_cargo.setText(banco_tarjeta_cargo);

        btn_otra_operacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(VoucherPagoTarjeta.this, MenuCliente.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                intent.putExtra("cli_dni", cli_dni);
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
                intent.putExtra("cli_dni", cli_dni);
                startActivity(intent);
                finish();
            }
        });

    }

    public void salir() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Está seguro que desea salir de la aplicación?");
        alertDialog.setTitle("Salir");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(VoucherPagoTarjeta.this, LoginNumeroCliente.class);
                startActivityForResult(intent, 0);
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
                    Toast.makeText(VoucherPagoTarjeta.this, "no se trajo el numero unico", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(VoucherPagoTarjeta.this, "la entidad no tiene data", Toast.LENGTH_LONG).show();
            }
        }
    }
}
