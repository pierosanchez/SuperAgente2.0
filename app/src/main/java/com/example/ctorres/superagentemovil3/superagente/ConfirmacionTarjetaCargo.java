package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.adapter.NumeroUnicoAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.adapter.UsuarioAdapter;
import com.example.ctorres.superagentemovil3.entity.NumeroUnico;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.entity.VoucherPagoTarjetaCreditoEntity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ConfirmacionTarjetaCargo extends Activity {

    String[] moneda = {"S/.", "US$"};
    Spinner spinnerMonedaCargo;
    LinearLayout btn_continuar_tarjeta_cargo, btn_cancelar_tarjeta_cargo;
    Button btn_fimar;
    ImageView imageView;
    Bitmap b;
    String monto, num_tarjeta, usu, tipo_moneda_deuda, tarjeta_cargo, nro_unico;
    Bitmap bmp;
    EditText txt_monto_tarjeta_cargo_credito;
    TextView tv_numero_clave_cifrada_cargo, tv_nombre_cliente_tarjeta_cargo, tv_tipo_moneda_deuda, tv_pago_cuotas;
    RadioButton rdbtn_visa_option, rdbtn_amex_option, rdbtn_mc_option;
    private UsuarioEntity usuario;
    int tipo_tarjeta, emisor_tarjeta, tipo_tarjeta_pago;
    ArrayList<UsuarioEntity> usuarioEntityArrayList;
    UsuarioAdapter usuarioAdapter;
    String cliente, cli_dni, desc_corta_banco, desc_corta_banco_tarjeta_cargo, validacion_tarjeta;
    String[] cuotas = {"No", "Si"};
    String[] cantidadCuotas = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" ,"21" ,"22" ,"23" ,"24" ,"25" ,"26" ,"27" ,"28" ,"29" ,"30" ,"31" ,"32", "33", "34", "35" ,"36"};
    LinearLayout ll_cantidad_cuotas;
    Spinner sp_pago_cuotas, sp_cantidad_cuotas;
    NumeroUnicoAdapter numeroUnicoAdapter;
    ArrayList<NumeroUnico> numeroUnicoArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmacion_tarjeta_cargo);

        //spinnerMonedaCargo = (Spinner) findViewById(R.id.spinnerMonedaCargo);
        btn_continuar_tarjeta_cargo = (LinearLayout) findViewById(R.id.btn_continuar_tarjeta_cargo);
        btn_cancelar_tarjeta_cargo = (LinearLayout) findViewById(R.id.btn_cancelar_tarjeta_cargo);
        ll_cantidad_cuotas = (LinearLayout) findViewById(R.id.ll_cantidad_cuotas);

        txt_monto_tarjeta_cargo_credito = (EditText) findViewById(R.id.txt_monto_tarjeta_cargo_credito);
        //btn_fimar = (Button) findViewById(R.id.btn_firmar);
        imageView = (ImageView) findViewById(R.id.imageView);

        tv_numero_clave_cifrada_cargo = (TextView) findViewById(R.id.tv_numero_clave_cifrada_cargo);
        tv_nombre_cliente_tarjeta_cargo = (TextView) findViewById(R.id.tv_nombre_cliente_tarjeta_cargo);
        tv_tipo_moneda_deuda = (TextView) findViewById(R.id.tv_tipo_moneda_deuda);
        tv_pago_cuotas = (TextView) findViewById(R.id.tv_pago_cuotas);

        /*rdbtn_visa_option = (RadioButton) findViewById(R.id.rdbtn_visa_option);
        rdbtn_amex_option = (RadioButton) findViewById(R.id.rdbtn_amex_option);
        rdbtn_mc_option = (RadioButton) findViewById(R.id.rdbtn_mc_option);*/

        sp_pago_cuotas = (Spinner) findViewById(R.id.sp_pago_cuotas);
        sp_cantidad_cuotas = (Spinner) findViewById(R.id.sp_cantidad_cuotas);

        //cargarTipoMoneda();

        Bundle extras = getIntent().getExtras();
        monto = extras.getString("monto");
        //bmp = (Bitmap) getIntent().getParcelableExtra("imagebitmap");
        usuario = extras.getParcelable("usuario");
        num_tarjeta = extras.getString("num_tarjeta");
        tipo_tarjeta = extras.getInt("tipo_tarjeta");
        emisor_tarjeta = extras.getInt("emisor_tarjeta");
        tipo_moneda_deuda = extras.getString("tipo_moneda_deuda");
        tarjeta_cargo = extras.getString("tarjeta_cargo");
        cliente = extras.getString("cliente");
        cli_dni = extras.getString("cli_dni");
        desc_corta_banco = extras.getString("desc_corta_banco");
        desc_corta_banco_tarjeta_cargo = extras.getString("desc_corta_banco_tarjeta_cargo");
        validacion_tarjeta = extras.getString("validacion_tarjeta");
        tipo_tarjeta_pago = extras.getInt("tipo_tarjeta_pago");

        numeroUnicoArrayList = null;
        numeroUnicoAdapter = new NumeroUnicoAdapter(numeroUnicoArrayList, getApplication());

        ejecutarGetNumeroUnico();

        tv_numero_clave_cifrada_cargo.setText(num_tarjeta);
        tv_tipo_moneda_deuda.setText(tipo_moneda_deuda);
        txt_monto_tarjeta_cargo_credito.setText(transformarMonto());
        tv_nombre_cliente_tarjeta_cargo.setText(cliente);

        focTipoTarjeta();
        cargarCuotas();
        deseaCuotas();

        btn_continuar_tarjeta_cargo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tipo_tarjeta==1) {
                    //Bitmap bitmap = ((BitmapDrawable)firma.getDrawable()).getBitmap();
                    ConfirmacionTarjetaCargo.ingresarVoucher ingresar = new ConfirmacionTarjetaCargo.ingresarVoucher();
                    ingresar.execute();
                    Intent intent = new Intent(ConfirmacionTarjetaCargo.this, VoucherPagoTarjetaConCredito.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("monto", monto);
                    intent.putExtra("tipo_moneda_deuda", tipo_moneda_deuda);
                    intent.putExtra("num_tarjeta", num_tarjeta);
                    intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("cli_dni", cli_dni);
                    intent.putExtra("desc_corta_banco", desc_corta_banco);
                    intent.putExtra("desc_corta_banco_tarjeta_cargo", desc_corta_banco_tarjeta_cargo);
                    intent.putExtra("nro_unico", nro_unico);
                    startActivity(intent);
                    finish();
                } else if (tipo_tarjeta==2) {
                    ConfirmacionTarjetaCargo.ingresarVoucher ingresar = new ConfirmacionTarjetaCargo.ingresarVoucher();
                    ingresar.execute();
                    Intent intent = new Intent(ConfirmacionTarjetaCargo.this, VoucherPagoTarjeta.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("monto", monto);
                    intent.putExtra("tipo_moneda_deuda", tipo_moneda_deuda);
                    intent.putExtra("num_tarjeta", num_tarjeta);
                    intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("cli_dni", cli_dni);
                    intent.putExtra("desc_corta_banco", desc_corta_banco);
                    intent.putExtra("desc_corta_banco_tarjeta_cargo", desc_corta_banco_tarjeta_cargo);
                    intent.putExtra("nro_unico", nro_unico);
                    startActivity(intent);
                    finish();
                }
            }
        });

        sp_pago_cuotas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getAdapter().getItem(position).equals("Si")){
                    ll_cantidad_cuotas.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_cancelar_tarjeta_cargo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                cancelar();
            }
        });

    }

    /*public void cargarTipoMoneda() {
        ArrayAdapter<String> adapterMoneda = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, moneda);
        spinnerMonedaCargo.setAdapter(adapterMoneda);
    }*/

    public void focTipoTarjeta(){
        if (emisor_tarjeta == 1) {
            imageView.setImageResource(R.drawable.visaicon);
        } else if (emisor_tarjeta == 2) {
            imageView.setImageResource(R.drawable.mastercardlogo);
        } else {
            imageView.setImageResource(R.drawable.americanexpressicon);
        }
    }

    private void ejecutarGetNumeroUnico(){

        try {
            ConfirmacionTarjetaCargo.getNumeroUnico listadoBeneficiario = new ConfirmacionTarjetaCargo.getNumeroUnico();
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

    private void ejecutarLista(){
        usu = usuario.getUsuarioId();

        try {
            ConfirmacionTarjetaCargo.ListadoTarjetas listadoBeneficiario = new ConfirmacionTarjetaCargo.ListadoTarjetas();
            listadoBeneficiario.execute();
        } catch (Exception e){
            //listadoBeneficiario = null;
        }

    }

    private class ListadoTarjetas extends AsyncTask<String,Void,Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                usuarioEntityArrayList = dao.listarTarjetas(usu);
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            usuarioAdapter.setNewListBeneficiario(usuarioEntityArrayList);
            usuarioAdapter.notifyDataSetChanged();
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

    public void cancelar() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Esta seguro que desea cacelar la transacción?");
        alertDialog.setTitle("Cancelar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ConfirmacionTarjetaCargo.this, MenuCliente.class);
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

    public String transformarMonto(){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        double montoD = Double.parseDouble(monto);
        return decimalFormat.format(montoD);
    }

    public void cargarCuotas(){
        ArrayAdapter<String> cantidadCuota = new ArrayAdapter<String>(ConfirmacionTarjetaCargo.this, android.R.layout.simple_spinner_dropdown_item, cantidadCuotas);
        sp_cantidad_cuotas.setAdapter(cantidadCuota);
    }

    public void deseaCuotas(){
        ArrayAdapter<String> cuota = new ArrayAdapter<String>(ConfirmacionTarjetaCargo.this, android.R.layout.simple_spinner_dropdown_item, cuotas);
        sp_pago_cuotas.setAdapter(cuota);
    }

    private class ingresarVoucher extends AsyncTask<String, Void, VoucherPagoTarjetaCreditoEntity> {
        @Override
        protected VoucherPagoTarjetaCreditoEntity doInBackground(String... params) {
            VoucherPagoTarjetaCreditoEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.ingresarVoucherPagoTarjetaCredito(nro_unico, obtenerFecha(), obtenerHora(), num_tarjeta, desc_corta_banco, tarjeta_cargo, desc_corta_banco_tarjeta_cargo, transformarMonto(), tipo_moneda_deuda, usuario.getUsuarioId());

            } catch (Exception e) {
                user = null;
                //fldag_clic_ingreso = 0;;
            }
            return user;
        }
    }
}
