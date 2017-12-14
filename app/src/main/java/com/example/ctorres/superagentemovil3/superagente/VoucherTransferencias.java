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

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.adapter.NumeroUnicoAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.NumeroUnico;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class VoucherTransferencias extends Activity {

    TextView tv_fecha_pago, txt_hora_pago, tv_tipo_moneda_importe_voucher, tv_tipo_moneda_comision_voucher, tv_tipo_moneda_comision_delivery_voucher, tv_importe_voucher, tv_datos_beneficiario_transaccion_voucher, tv_tipo_transaccion_voucher, tv_tipo_transaccion_voucher_descripcion,
            tv_tipo_moneda_comision_cheque_voucher, tv_usuario_tarjeta_num_cifrado, tv_usuario_tarjeta_banco, tv_monto_comision_servicio_pagar, tv_monto_transferencia, tv_monto_total_pagar,
            tv_tipo_moneda_importe_total_voucher, tv_tipo_moneda_transferencia_voucher,
            tv_comision1, tv_comision2, tv_comision3, tv_remitente_transferencia_voucher, txt_numero_unico;
    LinearLayout btn_efectuar_otra_operacion, btn_salir_transferencias, ll_comision_delivery, ll_comision_cheque;
    UsuarioEntity usuario;
    String tipomoneda, importe, cheque, tarjeta, TipoAbono, DetalleAbono, CuentaBeneficiario, nombreBeneficiario,
            num_tarjeta, banco, monto, transferencia, comision0, comision1, comision2, comision3, importe_comision1, importe_comision2, importe_comision3;
    String cliente, remitente, cli_dni, importeTotal;
    DecimalFormat decimalFormat = new DecimalFormat("0.00");
    NumeroUnicoAdapter numeroUnicoAdapter;
    ArrayList<NumeroUnico> numeroUnicoArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voucher_transferencias);

        btn_efectuar_otra_operacion = (LinearLayout) findViewById(R.id.btn_efectuar_otra_operacion);
        btn_salir_transferencias = (LinearLayout) findViewById(R.id.btn_salir_transferencias);
        ll_comision_cheque = (LinearLayout) findViewById(R.id.ll_comision_cheque);
        ll_comision_delivery = (LinearLayout) findViewById(R.id.ll_comision_delivery);

        tv_fecha_pago = (TextView) findViewById(R.id.tv_fecha_pago);
        txt_hora_pago = (TextView) findViewById(R.id.txt_hora_pago);
        tv_tipo_moneda_comision_voucher = (TextView) findViewById(R.id.tv_tipo_moneda_comision_voucher);
        tv_tipo_moneda_comision_delivery_voucher = (TextView) findViewById(R.id.tv_tipo_moneda_comision_delivery_voucher);
        tv_tipo_moneda_comision_cheque_voucher = (TextView) findViewById(R.id.tv_tipo_moneda_comision_cheque_voucher);
        tv_tipo_moneda_importe_voucher = (TextView) findViewById(R.id.tv_tipo_moneda_importe_voucher);
        tv_importe_voucher = (TextView) findViewById(R.id.tv_importe_voucher);
        tv_tipo_transaccion_voucher_descripcion = (TextView) findViewById(R.id.tv_tipo_transaccion_voucher_descripcion);
        tv_tipo_transaccion_voucher = (TextView) findViewById(R.id.tv_tipo_transaccion_voucher);
        tv_datos_beneficiario_transaccion_voucher = (TextView) findViewById(R.id.tv_datos_beneficiario_transaccion_voucher);
        tv_usuario_tarjeta_num_cifrado = (TextView) findViewById(R.id.tv_usuario_tarjeta_num_cifrado);
        tv_usuario_tarjeta_banco = (TextView) findViewById(R.id.tv_usuario_tarjeta_banco);
        tv_monto_comision_servicio_pagar = (TextView) findViewById(R.id.tv_monto_comision_servicio_pagar);
        tv_monto_transferencia = (TextView) findViewById(R.id.tv_monto_transferencia);
        tv_monto_total_pagar = (TextView) findViewById(R.id.tv_monto_total_pagar);
        tv_tipo_moneda_importe_total_voucher = (TextView) findViewById(R.id.tv_tipo_moneda_importe_total_voucher);
        tv_tipo_moneda_transferencia_voucher = (TextView) findViewById(R.id.tv_tipo_moneda_transferencia_voucher);
        tv_comision1 = (TextView) findViewById(R.id.tv_comision1);
        tv_comision2 = (TextView) findViewById(R.id.tv_comision2);
        tv_comision3 = (TextView) findViewById(R.id.tv_comision3);
        tv_remitente_transferencia_voucher = (TextView) findViewById(R.id.tv_remitente_transferencia_voucher);

        Bundle extra = getIntent().getExtras();
        //usuario = extra.getParcelable("usuario");
        tipomoneda = extra.getString("tipomoneda");
        importe = extra.getString("importe");
        usuario = extra.getParcelable("usuario");
        //cheque = extra.getString("cheque");
        TipoAbono = extra.getString("tipoAbono");
        DetalleAbono = extra.getString("detalleAbono");
        nombreBeneficiario = extra.getString("nombrebenef");
        num_tarjeta = extra.getString("num_tarjeta");
        banco = extra.getString("banco");
        monto = extra.getString("monto");
        transferencia = extra.getString("transferencia");
        importe_comision1 = extra.getString("comision_cheque_delivery");
        importe_comision2 = extra.getString("comision_cheque");
        importe_comision3 = extra.getString("comision_monto");
        cliente = extra.getString("cliente");
        cli_dni = extra.getString("cli_dni");
        comision0 = tipomoneda + " " + montoTransferencia();
        comision1 = tipomoneda + " " + importe_comision1;
        comision2 = tipomoneda + " " + importe_comision2;
        comision3 = tipomoneda + " " + importe_comision3;
        remitente = "REMITENTE: " + cliente;
        importeTotal = tipomoneda + " " + importe;

        numeroUnicoArrayList = null;
        numeroUnicoAdapter = new NumeroUnicoAdapter(numeroUnicoArrayList, getApplication());

        ejecutarLista();

        if (importe_comision2 != null && importe_comision1 != null && importe_comision3 != null){
            ll_comision_cheque.setVisibility(View.VISIBLE);
            ll_comision_delivery.setVisibility(View.VISIBLE);
            tv_comision1.setText(importe_comision1);
            tv_comision2.setText(importe_comision2);
            tv_comision3.setText(importe_comision3);
        } else if (importe_comision2 == null && importe_comision1 == null && importe_comision3 == null) {
            tv_comision1.setVisibility(View.GONE);
            tv_comision2.setVisibility(View.GONE);
            //tv_comision3.setVisibility(View.GONE);
        }

        if (DetalleAbono.equals("Con Delivery")) {
            ll_comision_delivery.setVisibility(View.VISIBLE);
        } else if (DetalleAbono.equals("Con Recojo")) {
            ll_comision_delivery.setVisibility(View.GONE);
        }

        //CuentaBeneficiario = extra.getString("CuentaBeneficiario");

        tv_tipo_moneda_importe_voucher.setText(tipomoneda);
        tv_importe_voucher.setText(montoTransferencia());
        tv_tipo_transaccion_voucher.setText(TipoAbono);
        tv_tipo_transaccion_voucher_descripcion.setText(DetalleAbono);
        tv_datos_beneficiario_transaccion_voucher.setText(nombreBeneficiario);
        tv_usuario_tarjeta_num_cifrado.setText(num_tarjeta);
        tv_usuario_tarjeta_banco.setText(banco);
        tv_monto_transferencia.setText(montoTransferencia());
        tv_monto_total_pagar.setText(importe);
        tv_tipo_moneda_importe_total_voucher.setText(tipomoneda);
        tv_tipo_moneda_transferencia_voucher.setText(tipomoneda);
        tv_comision3.setText(importe_comision3);
        tv_remitente_transferencia_voucher.setText(remitente);
        tv_tipo_moneda_comision_voucher.setText(tipomoneda);
        tv_tipo_moneda_comision_delivery_voucher.setText(tipomoneda);
        tv_tipo_moneda_comision_cheque_voucher.setText(tipomoneda);

        tv_fecha_pago.setText(obtenerFecha());
        txt_hora_pago.setText(obtenerHora());

        btn_efectuar_otra_operacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VoucherTransferencias.this, MenuCliente.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                intent.putExtra("cli_dni", cli_dni);
                startActivity(intent);
                finish();
            }
        });

        btn_salir_transferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });
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

    public String montoTransferencia(){
        double importeTransferencia = Double.parseDouble(transferencia);
        return decimalFormat.format(importeTransferencia);
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

    private void ejecutarLista(){

        try {
            VoucherTransferencias.getNumeroUnico listadoBeneficiario = new VoucherTransferencias.getNumeroUnico();
            listadoBeneficiario.execute();
        } catch (Exception e){
            //listadoBeneficiario = null;
        }

    }

    private class getNumeroUnico extends AsyncTask<String,Void,Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                numeroUnicoArrayList = dao.getNumeroUnico();
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            numeroUnicoAdapter.setNewListNumeroUnico(numeroUnicoArrayList);
            numeroUnicoAdapter.notifyDataSetChanged();
            txt_numero_unico.setText(numeroUnicoArrayList.get(0).getNumeroUnico());
        }
    }
}
