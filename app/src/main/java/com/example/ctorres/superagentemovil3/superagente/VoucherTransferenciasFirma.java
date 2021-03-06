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
import com.example.ctorres.superagentemovil3.entity.VoucherTransferenciasEntity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class VoucherTransferenciasFirma extends Activity {

    Bitmap b;
    Button btn_fimar, btn_efectuar_otra_operacion, btn_salir;
    ImageView signImage;
    private UsuarioEntity usuario;
    TextView tv_fecha_pago, txt_hora_pago, tv_tipo_moneda_importe_voucher, tv_tipo_moneda_comision_voucher, tv_tipo_moneda_comision_delivery_voucher, tv_importe_voucher, tv_datos_beneficiario_transaccion_voucher, tv_tipo_transaccion_voucher, tv_tipo_transaccion_voucher_descripcion,
            tv_tipo_moneda_comision_cheque_voucher, tv_usuario_tarjeta_num_cifrado,
            tv_usuario_tarjeta_banco, tv_monto_transferencia, tv_monto_total_pagar,
            tv_tipo_moneda_importe_total_voucher, tv_tipo_moneda_transferencia_voucher,
            tv_comision1, tv_comision2, tv_comision3, tv_remitente_transferencia_voucher, txt_numero_unico;
    LinearLayout ll_comision_delivery, ll_comision_cheque;
    String tipomoneda, importe, cheque, tarjeta, TipoAbono, DetalleAbono, CuentaBeneficiario, nombreBeneficiario, nro_unico,
            num_tarjeta, banco, monto, transferencia, comision0, comision1, comision2, comision3, importe_comision1, importe_comision2, importe_comision3;
    String cliente, remitente, cli_dni, importeTotal, fechaV, horaV;
    DecimalFormat decimalFormat = new DecimalFormat("0.00");
    NumeroUnicoAdapter numeroUnicoAdapter;
    ArrayList<NumeroUnico> numeroUnicoArrayList;
    private VoucherTransferenciasEntity voucherTransferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voucher_transferencias_firma);

        btn_fimar = (Button) findViewById(R.id.btn_firmar);
        btn_efectuar_otra_operacion = (Button) findViewById(R.id.btn_efectuar_otra_operacion);
        btn_salir = (Button) findViewById(R.id.btn_salir);

        signImage = (ImageView) findViewById(R.id.signImage);

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
        tv_monto_transferencia = (TextView) findViewById(R.id.tv_monto_transferencia);
        tv_monto_total_pagar = (TextView) findViewById(R.id.tv_monto_total_pagar);
        tv_tipo_moneda_importe_total_voucher = (TextView) findViewById(R.id.tv_tipo_moneda_importe_total_voucher);
        tv_tipo_moneda_transferencia_voucher = (TextView) findViewById(R.id.tv_tipo_moneda_transferencia_voucher);
        tv_comision1 = (TextView) findViewById(R.id.tv_comision1);
        tv_comision2 = (TextView) findViewById(R.id.tv_comision2);
        tv_comision3 = (TextView) findViewById(R.id.tv_comision3);
        tv_remitente_transferencia_voucher = (TextView) findViewById(R.id.tv_remitente_transferencia_voucher);
        txt_numero_unico = (TextView) findViewById(R.id.txt_numero_unico);

        Bundle extras = getIntent().getExtras();
        usuario = extras.getParcelable("usuario");
        cliente = extras.getString("cliente");
        tipomoneda = extras.getString("tipomoneda");
        importe = extras.getString("importe");
        TipoAbono = extras.getString("tipoAbono");
        DetalleAbono = extras.getString("detalleAbono");
        nombreBeneficiario = extras.getString("nombrebenef");
        num_tarjeta = extras.getString("num_tarjeta");
        banco = extras.getString("banco");
        monto = extras.getString("monto");
        transferencia = extras.getString("transferencia");
        importe_comision1 = extras.getString("comision_cheque_delivery");
        importe_comision2 = extras.getString("comision_cheque");
        importe_comision3 = extras.getString("comision_monto");
        cli_dni = extras.getString("cli_dni");
        nro_unico = extras.getString("nro_unico");
        fechaV = "FECHA: " + obtenerFecha();
        horaV = "HORA: " + obtenerHora();
        comision0 = tipomoneda + " " + montoTransferencia();
        comision1 = tipomoneda + " " + importe_comision1;
        comision2 = tipomoneda + " " + importe_comision2;
        comision3 = tipomoneda + " " + importe_comision3;
        remitente = "REMITENTE: " + cliente;
        importeTotal = tipomoneda + " " + importe;

        VoucherTransferenciasFirma.getNumUnico numUnico = new VoucherTransferenciasFirma.getNumUnico();
        numUnico.execute();

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

        tv_fecha_pago.setText(fechaV);
        txt_hora_pago.setText(horaV);

        btn_fimar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VoucherTransferenciasFirma.this, CaptureSignature.class);
                intent.putExtra("cliente", cliente);
                intent.putExtra("cli_dni", cli_dni);
                startActivityForResult(intent, 0);
                /*startActivity(intent);
                finish();*/
            }
        });

        btn_efectuar_otra_operacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signImage.getDrawable() == null) {
                    Toast.makeText(VoucherTransferenciasFirma.this, "Por favor registre su firma", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(VoucherTransferenciasFirma.this, MenuCliente.class);
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
                    Toast.makeText(VoucherTransferenciasFirma.this, "Por favor registre su firma", Toast.LENGTH_LONG).show();
                } else {
                    salir();
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

    public String montoTransferencia(){
        double importeTransferencia = Double.parseDouble(transferencia);
        return decimalFormat.format(importeTransferencia);
    }

    public void salir() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Está seguro que desea salir de la aplicación?");
        alertDialog.setTitle("Salir");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(VoucherTransferenciasFirma.this, LoginNumeroCliente.class);
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

    private class getNumUnico extends AsyncTask<String, Void, VoucherTransferenciasEntity> {

        @Override
        protected VoucherTransferenciasEntity doInBackground(String... params) {
            VoucherTransferenciasEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.getNumeroUnicoTransferencias(nro_unico);

            } catch (Exception e) {
                user = null;
                //fldag_clic_ingreso = 0;;
            }
            return user;
        }

        @Override
        protected void onPostExecute(VoucherTransferenciasEntity voucherTransferenciasEntity){
            voucherTransferencias = voucherTransferenciasEntity;
            if (voucherTransferencias != null){
                if (voucherTransferencias.getNumeroUnico() != null){
                    txt_numero_unico.setText(voucherTransferencias.getNumeroUnico());
                } else {
                    Toast.makeText(VoucherTransferenciasFirma.this, "no se trajo el numero unico", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(VoucherTransferenciasFirma.this, "la entidad no tiene data", Toast.LENGTH_LONG).show();
            }
        }
    }
}
