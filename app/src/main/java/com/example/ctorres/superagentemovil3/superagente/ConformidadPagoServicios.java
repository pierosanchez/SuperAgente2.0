package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.adapter.NumeroUnicoAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteBD;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.NumeroUnico;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.entity.VoucherPagoServicioEntity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ConformidadPagoServicios extends Activity {

    Button btn_continuar_pago, btn_cancelar_pago_servicio;
    private UsuarioEntity usuario;
    String num_tarjeta, monto_servicio, servicio, num_servicio, tipo_moneda_deuda,
            cliente, tipo_servicio, cli_dni, nombre_recibo, validacion_tarjeta, nro_unico;
    int tipo_tarjeta, emisor_tarjeta, tipo_tarjeta_pago, cod_banco;
    TextView tv_numero_tarjeta_cifrada_pago_servicio, txt_servicio_pagar, tv_monto_servicio_pagar, tv_monto_comision_servicio_pagar,
            tv_monto_total_servicio_pagar, txt_tipo_moneda_servicio_pagar, tv_nro_servicio_servicio_pagar,
            tv_nombre_titular_pago_servicio, txt_tipo_servicio_pagar,
            txt_tipo_moneda_total_pagar, txt_tipo_moneda_comision_pagar, tv_dni_cliente_conformidad, tv_nombre_recibo_usuario;
    DecimalFormat format = new DecimalFormat("0.00");
    LinearLayout ll_tipo_servicio_pagar_conforme;
    NumeroUnicoAdapter numeroUnicoAdapter;
    ArrayList<NumeroUnico> numeroUnicoArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conformidad_pago_servicios);

        ll_tipo_servicio_pagar_conforme = (LinearLayout) findViewById(R.id.ll_tipo_servicio_pagar_conforme);

        btn_continuar_pago = (Button) findViewById(R.id.btn_continuar_pago);
        btn_cancelar_pago_servicio = (Button) findViewById(R.id.btn_cancelar_pago_servicio);

        tv_numero_tarjeta_cifrada_pago_servicio = (TextView) findViewById(R.id.tv_numero_tarjeta_cifrada_pago_servicio);
        txt_servicio_pagar = (TextView) findViewById(R.id.txt_servicio_pagar);
        tv_monto_servicio_pagar = (TextView) findViewById(R.id.tv_monto_servicio_pagar);
        tv_monto_comision_servicio_pagar = (TextView) findViewById(R.id.tv_monto_comision_servicio_pagar);
        tv_monto_total_servicio_pagar = (TextView) findViewById(R.id.tv_monto_total_servicio_pagar);
        txt_tipo_moneda_servicio_pagar = (TextView) findViewById(R.id.txt_tipo_moneda_servicio_pagar);
        tv_nro_servicio_servicio_pagar = (TextView) findViewById(R.id.tv_nro_servicio_servicio_pagar);
        tv_nombre_titular_pago_servicio = (TextView) findViewById(R.id.tv_nombre_titular_pago_servicio);
        txt_tipo_servicio_pagar = (TextView) findViewById(R.id.txt_tipo_servicio_pagar);
        txt_tipo_moneda_total_pagar = (TextView) findViewById(R.id.txt_tipo_moneda_total_pagar);
        txt_tipo_moneda_comision_pagar = (TextView) findViewById(R.id.txt_tipo_moneda_comision_pagar);
        tv_dni_cliente_conformidad = (TextView) findViewById(R.id.tv_dni_cliente_conformidad);
        tv_nombre_recibo_usuario = (TextView) findViewById(R.id.tv_nombre_recibo_usuario);

        Bundle extras = getIntent().getExtras();
        usuario = extras.getParcelable("usuario");
        num_tarjeta = extras.getString("num_tarjeta");
        emisor_tarjeta = extras.getInt("emisor_tarjeta");
        tipo_tarjeta_pago = extras.getInt("tipo_tarjeta_pago");
        monto_servicio = extras.getString("monto_servicio");
        servicio = extras.getString("servicio");
        num_servicio = extras.getString("num_servicio");
        tipo_moneda_deuda = extras.getString("tipo_moneda_deuda");
        cod_banco = extras.getInt("cod_banco");
        tipo_servicio = extras.getString("tipo_servicio");
        cliente = extras.getString("cliente");
        cli_dni = extras.getString("cli_dni");
        nombre_recibo = extras.getString("nombre_recibo");
        validacion_tarjeta = extras.getString("validacion_tarjeta");

        numeroUnicoArrayList = null;
        numeroUnicoAdapter = new NumeroUnicoAdapter(numeroUnicoArrayList, getApplication());

        ejecutarLista();

        tv_numero_tarjeta_cifrada_pago_servicio.setText(num_tarjeta);
        txt_servicio_pagar.setText(servicio);
        tv_monto_servicio_pagar.setText(monto_servicio);
        txt_tipo_moneda_servicio_pagar.setText(tipo_moneda_deuda);
        tv_nro_servicio_servicio_pagar.setText(num_servicio);
        tv_monto_total_servicio_pagar.setText(totalServicioPagar());
        tv_nombre_titular_pago_servicio.setText(cliente);
        txt_tipo_moneda_total_pagar.setText(tipo_moneda_deuda);
        txt_tipo_moneda_comision_pagar.setText(tipo_moneda_deuda);
        tv_dni_cliente_conformidad.setText(cli_dni);
        tv_nombre_recibo_usuario.setText(nombre_recibo);

        if (tipo_servicio == null) {
            ll_tipo_servicio_pagar_conforme.setVisibility(View.GONE);
        } else {
            txt_tipo_servicio_pagar.setText(tipo_servicio);
            ll_tipo_servicio_pagar_conforme.setVisibility(View.VISIBLE);
        }

        btn_continuar_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tipo_tarjeta_pago == 2) {
                    ConformidadPagoServicios.ingresarVoucher validador = new ConformidadPagoServicios.ingresarVoucher();
                    validador.execute();
                    String comision = tv_monto_comision_servicio_pagar.getText().toString();
                    Intent intent = new Intent(ConformidadPagoServicios.this, VoucherPagoServicio.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("num_tarjeta", num_tarjeta);
                    intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                    intent.putExtra("monto_servicio", monto_servicio);
                    intent.putExtra("servicio", servicio);
                    intent.putExtra("num_servicio", num_servicio);
                    intent.putExtra("tipo_moneda_deuda", tipo_moneda_deuda);
                    intent.putExtra("comision", comision);
                    intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                    intent.putExtra("cod_banco", cod_banco);
                    intent.putExtra("tipo_servicio", tipo_servicio);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("cli_dni", cli_dni);
                    intent.putExtra("nombre_recibo", nombre_recibo);
                    intent.putExtra("nro_unico", nro_unico);
                    startActivity(intent);
                    finish();
                } else if (tipo_tarjeta_pago == 1) {
                    if (validacion_tarjeta.equals("Firma")) {
                        ConformidadPagoServicios.ingresarVoucher validador = new ConformidadPagoServicios.ingresarVoucher();
                        validador.execute();
                        String comision = tv_monto_comision_servicio_pagar.getText().toString();
                        Intent intent = new Intent(ConformidadPagoServicios.this, VoucherPagoServicioFirma.class);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("num_tarjeta", num_tarjeta);
                        intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                        intent.putExtra("monto_servicio", monto_servicio);
                        intent.putExtra("servicio", servicio);
                        intent.putExtra("num_servicio", num_servicio);
                        intent.putExtra("tipo_moneda_deuda", tipo_moneda_deuda);
                        intent.putExtra("comision", comision);
                        intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                        intent.putExtra("cod_banco", cod_banco);
                        intent.putExtra("tipo_servicio", tipo_servicio);
                        intent.putExtra("cliente", cliente);
                        intent.putExtra("cli_dni", cli_dni);
                        intent.putExtra("nombre_recibo", nombre_recibo);
                        intent.putExtra("nro_unico", nro_unico);
                        startActivity(intent);
                        finish();
                    } else if (validacion_tarjeta.equals("Pin")){
                        ConformidadPagoServicios.ingresarVoucher validador = new ConformidadPagoServicios.ingresarVoucher();
                        validador.execute();
                        String comision = tv_monto_comision_servicio_pagar.getText().toString();
                        Intent intent = new Intent(ConformidadPagoServicios.this, VoucherPagoServicio.class);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("num_tarjeta", num_tarjeta);
                        intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                        intent.putExtra("monto_servicio", monto_servicio);
                        intent.putExtra("servicio", servicio);
                        intent.putExtra("num_servicio", num_servicio);
                        intent.putExtra("tipo_moneda_deuda", tipo_moneda_deuda);
                        intent.putExtra("comision", comision);
                        intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                        intent.putExtra("cod_banco", cod_banco);
                        intent.putExtra("tipo_servicio", tipo_servicio);
                        intent.putExtra("cliente", cliente);
                        intent.putExtra("cli_dni", cli_dni);
                        intent.putExtra("nombre_recibo", nombre_recibo);
                        intent.putExtra("nro_unico", nro_unico);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        btn_cancelar_pago_servicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });
    }

    public String totalServicioPagar(){
        String total;

        String monto = tv_monto_servicio_pagar.getText().toString();
        String comision = tv_monto_comision_servicio_pagar.getText().toString();

        double monto_p = Double.parseDouble(monto);
        double comision_p = Double.parseDouble(comision);

        double importe = monto_p + comision_p;

        //total = String.valueOf(importe);

        return format.format(importe);
    }

    public void cancelar() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Esta seguro que desea cacelar la transacción?");
        alertDialog.setTitle("Cancelar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ConformidadPagoServicios.this, MenuCliente.class);
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

    public String tipoTarjeta(){
        String tipo = "";

        if (tipo_tarjeta_pago == 1) {
            tipo = "Crédito";
        } else if (tipo_tarjeta_pago == 2) {
            tipo = "Débito";
        }

        return tipo;
    }

    public String transformarComision(){
        String comision = tv_monto_comision_servicio_pagar.getText().toString();
        double convert = Double.parseDouble(comision);
        return format.format(convert);
    }

    public String transformarImporteServicio(){
        double convert = Double.parseDouble(monto_servicio);
        return format.format(convert);
    }

    private void ejecutarLista(){

        try {
            ConformidadPagoServicios.getNumeroUnico listadoBeneficiario = new ConformidadPagoServicios.getNumeroUnico();
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
            nro_unico = numeroUnicoArrayList.get(0).getNumeroUnico();
        }
    }

    private class ingresarVoucher extends AsyncTask<String, Void, VoucherPagoServicioEntity> {

        @Override
        protected VoucherPagoServicioEntity doInBackground(String... params) {
            VoucherPagoServicioEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.ingresarVoucherServicio(numeroUnicoArrayList.get(0).getNumeroUnico(), obtenerFecha(), obtenerHora(), servicio, tipo_servicio, usuario.getUsuarioId(), nombre_recibo, cliente, cli_dni, tipoTarjeta(), transformarImporteServicio(), transformarComision(), totalServicioPagar());

            } catch (Exception e) {
                user = null;
                //fldag_clic_ingreso = 0;;
            }
            return user;
        }
    }
}
