package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.InputFilter;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.adapter.DeudasTarjetasAdapter;
import com.example.ctorres.superagentemovil3.adapter.DeudasTarjetasDolaresAdapter;
import com.example.ctorres.superagentemovil3.adapter.DeudasTarjetasSolesAdapter;
import com.example.ctorres.superagentemovil3.adapter.MonedaAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.DeudasTarjetas;
import com.example.ctorres.superagentemovil3.entity.MonedaEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.utils.MoneyValueFilter;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SeleccionModoMontoPago extends Activity {

    String tipoMoneda[] = {"S/.", "US$"};
    //Spinner spinnerTipoMonedaPago;
    LinearLayout btn_continuar, btn_cancelar;
    RadioGroup rdgp_montos, rdgrp_tipo_moneda;
    EditText txt_monto_minimo, txt_monto_mensual, txt_monto_total, txt_monto_cuenta;
    String monto;
    RadioButton rdbtn_minimo, rdbtn_mensual, rdbtn_total, rdbtn_cuenta, rdbtn_soles, rdbtn_dolares;
    Bitmap bmp;
    private UsuarioEntity usuario;
    String num_tarjeta, banco_tarjeta;
    int tipo_tarjeta, emisor_tarjeta;
    ImageView imageView;
    TextView tv_numero_clave_cifrada_cargo, tv_tipo_moneda_modo_monto_1, tv_tipo_moneda_modo_monto_2, tv_tipo_moneda_modo_monto_3, tv_tipo_moneda_modo_monto_4;
    String cliente, cli_dni, desc_corta_banco, tipo_moneda_deuda;
    DeudasTarjetasDolaresAdapter deudasTarjetasDolaresAdapter;
    DeudasTarjetasSolesAdapter deudasTarjetasSolesAdapter;
    ArrayList<DeudasTarjetas> deudasTarjetasSolesArrayList;
    ArrayList<DeudasTarjetas> deudasTarjetasDolaresArrayList;
    MonedaAdapter monedaAdapter;
    ArrayList<MonedaEntity> monedaEntityArrayList;
    DecimalFormat decimal = new DecimalFormat("0.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_modo_monto_pago);

        btn_continuar = (LinearLayout) findViewById(R.id.btn_continuar);
        btn_cancelar = (LinearLayout) findViewById(R.id.btn_cancelar);

        //spinnerTipoMonedaPago = (Spinner) findViewById(R.id.spinnerTipoMonedaPago);

        txt_monto_minimo = (EditText) findViewById(R.id.txt_monto_minimo);
        txt_monto_mensual = (EditText) findViewById(R.id.txt_monto_mensual);
        txt_monto_total = (EditText) findViewById(R.id.txt_monto_total);
        txt_monto_cuenta = (EditText) findViewById(R.id.txt_monto_cuenta);

        rdgp_montos = (RadioGroup) findViewById(R.id.rdgp_montos_pagar);
        rdgrp_tipo_moneda = (RadioGroup) findViewById(R.id.rdgrp_tipo_moneda);

        rdbtn_minimo = (RadioButton) findViewById(R.id.rdbtn_minimo);
        rdbtn_mensual = (RadioButton) findViewById(R.id.rdbtn_mensual);
        rdbtn_total = (RadioButton) findViewById(R.id.rdbtn_total);
        rdbtn_cuenta = (RadioButton) findViewById(R.id.rdbtn_cuenta);
        rdbtn_soles = (RadioButton) findViewById(R.id.rdbtn_soles);
        rdbtn_dolares = (RadioButton) findViewById(R.id.rdbtn_dolares);

        imageView = (ImageView) findViewById(R.id.imageView);

        tv_numero_clave_cifrada_cargo = (TextView) findViewById(R.id.tv_numero_clave_cifrada_cargo);
        tv_tipo_moneda_modo_monto_1 = (TextView) findViewById(R.id.tv_tipo_moneda_modo_monto_1);
        tv_tipo_moneda_modo_monto_2 = (TextView) findViewById(R.id.tv_tipo_moneda_modo_monto_2);
        tv_tipo_moneda_modo_monto_3 = (TextView) findViewById(R.id.tv_tipo_moneda_modo_monto_3);
        tv_tipo_moneda_modo_monto_4 = (TextView) findViewById(R.id.tv_tipo_moneda_modo_monto_4);


        Bundle extras = getIntent().getExtras();
        //bmp = (Bitmap) extras.getParcelable("imagebitmap");
        usuario = extras.getParcelable("usuario");
        num_tarjeta = extras.getString("num_tarjeta");
        tipo_tarjeta = extras.getInt("tipo_tarjeta");
        emisor_tarjeta = extras.getInt("emisor_tarjeta");
        banco_tarjeta = extras.getString("banco_tarjeta");
        cliente = extras.getString("cliente");
        cli_dni = extras.getString("cli_dni");
        desc_corta_banco = extras.getString("desc_corta_banco");

        tv_numero_clave_cifrada_cargo.setText(num_tarjeta);
        txt_monto_cuenta.setFilters(new InputFilter[] {new MoneyValueFilter()});

        focTipoTarjeta();
        //cargarTipoTarjetas();

        /*if (rdbtn_minimo.isChecked()){
            monto = txt_monto_minimo.getText().toString();
        }else if (rdbtn_mensual.isChecked()){
            monto = txt_monto_mensual.getText().toString();
        }else if (rdbtn_total.isChecked()){
            monto = txt_monto_total.getText().toString();
        }else{
            monto = txt_monto_cuenta.getText().toString();
        }*/

        /*tv_tipo_moneda_modo_monto_1.setText(spinnerTipoMonedaPago.getText().toString());
        tv_tipo_moneda_modo_monto_2.setText(spinnerTipoMonedaPago.getText().toString());
        tv_tipo_moneda_modo_monto_3.setText(spinnerTipoMonedaPago.getText().toString());
        tv_tipo_moneda_modo_monto_4.setText(spinnerTipoMonedaPago.getText().toString());*/


        rdgrp_tipo_moneda.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rdbtn_soles){
                    tipo_moneda_deuda = rdbtn_soles.getText().toString();
                    deudasTarjetasSolesArrayList = null;
                    deudasTarjetasSolesAdapter = new DeudasTarjetasSolesAdapter(deudasTarjetasSolesArrayList, getApplication());

                    ejecutarListaSoles();
                } else if (checkedId == R.id.rdbtn_dolares){
                    tipo_moneda_deuda = rdbtn_dolares.getText().toString();
                    deudasTarjetasDolaresArrayList = null;
                    deudasTarjetasDolaresAdapter = new DeudasTarjetasDolaresAdapter(deudasTarjetasDolaresArrayList, getApplication());

                    ejecutarListaDolares();
                }
            }
        });

        rdbtn_soles.setChecked(true);

        rdgp_montos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rdbtn_minimo){
                    txt_monto_cuenta.setEnabled(false);
                }else if (checkedId == R.id.rdbtn_mensual){
                    txt_monto_cuenta.setEnabled(false);
                }else if (checkedId == R.id.rdbtn_total){
                    txt_monto_cuenta.setEnabled(false);
                }else{
                    txt_monto_cuenta.setEnabled(true);
                    txt_monto_cuenta.requestFocus();
                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            }
        });

        /*spinnerTipoMonedaPago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipo_moneda_deuda = monedaAdapter.getItem(position).getSigno_moneda();

                if (monedaAdapter.getItem(position).getCod_tipo_moneda() == 2){
                    deudasTarjetasSolesArrayList = null;
                    deudasTarjetasSolesAdapter = new DeudasTarjetasSolesAdapter(deudasTarjetasSolesArrayList, getApplication());

                    ejecutarListaSoles();


                } else if (monedaAdapter.getItem(position).getCod_tipo_moneda() == 1){
                    deudasTarjetasDolaresArrayList = null;
                    deudasTarjetasDolaresAdapter = new DeudasTarjetasDolaresAdapter(deudasTarjetasDolaresArrayList, getApplication());

                    ejecutarListaDolares();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        btn_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rdbtn_minimo.isChecked() || rdbtn_mensual.isChecked() || rdbtn_total.isChecked() || rdbtn_cuenta.isChecked()) {
                    if (rdbtn_cuenta.isChecked()) {
                        String monto = txt_monto_cuenta.getText().toString();
                        if (monto.length() != 0) {
                            double montoCuenta = Double.parseDouble(txt_monto_cuenta.getText().toString());
                            if (montoCuenta <= 0) {
                                Toast.makeText(SeleccionModoMontoPago.this, "el monto ingresado debe ser mayor a 0", Toast.LENGTH_LONG).show();
                            } else {
                                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputMethodManager.toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0);

                                Intent intent = new Intent(SeleccionModoMontoPago.this, SeleccionTarjetaCargo.class);
                                intent.putExtra("monto", obtenerMonto());
                                //intent.putExtra("imagebitmap", bmp);
                                intent.putExtra("usuario", usuario);
                                intent.putExtra("num_tarjeta", num_tarjeta);
                                intent.putExtra("tipo_tarjeta", tipo_tarjeta);
                                intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                                intent.putExtra("banco_tarjeta", banco_tarjeta);
                                intent.putExtra("tipo_moneda_deuda", tipo_moneda_deuda);
                                intent.putExtra("cliente", cliente);
                                intent.putExtra("cli_dni", cli_dni);
                                intent.putExtra("desc_corta_banco", desc_corta_banco);
                                startActivityForResult(intent, 0);
                                finish();
                            }
                        } else {
                            Toast.makeText(SeleccionModoMontoPago.this, "Ingrese el monto a cuenta", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Intent intent = new Intent(SeleccionModoMontoPago.this, SeleccionTarjetaCargo.class);
                        intent.putExtra("monto", obtenerMonto());
                        //intent.putExtra("imagebitmap", bmp);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("num_tarjeta", num_tarjeta);
                        intent.putExtra("tipo_tarjeta", tipo_tarjeta);
                        intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                        intent.putExtra("banco_tarjeta", banco_tarjeta);
                        intent.putExtra("tipo_moneda_deuda", tipo_moneda_deuda);
                        intent.putExtra("cliente", cliente);
                        intent.putExtra("cli_dni", cli_dni);
                        intent.putExtra("desc_corta_banco", desc_corta_banco);
                        startActivityForResult(intent, 0);
                        finish();
                    }
                } else {
                    Toast.makeText(SeleccionModoMontoPago.this, "SELECCIONE ALGUNA OPCION DE PAGO", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });

        /*rdbtn_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rdbtn_cuenta.isChecked()) {
                    txt_monto_cuenta.requestFocus();
                } else {
                    txt_monto_cuenta.setEnabled(false);
                }
            }
        });*/
    }

    /*public void cargarTipoTarjetas(){
        ArrayAdapter<String> adapterTarjetas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tipoMoneda);
        spinnerTipoMonedaPago.setAdapter(adapterTarjetas);
    }*/

    public void cancelar() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Esta seguro que desea cacelar la transacción?");
        alertDialog.setTitle("Cancelar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(SeleccionModoMontoPago.this, MenuCliente.class);
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

    public String obtenerMonto() {
        String monto;
        if (rdbtn_minimo.isChecked()) {
            monto = txt_monto_minimo.getText().toString();
        } else if (rdbtn_mensual.isChecked()) {
            monto = txt_monto_mensual.getText().toString();
        } else if (rdbtn_total.isChecked()) {
            monto = txt_monto_total.getText().toString();
        } else {
            txt_monto_cuenta.requestFocus();
            monto = txt_monto_cuenta.getText().toString();
        }

        return monto;
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

    /*public String obtenerTipoMonedaDeuda(){
        String tipo;
        tipo = spinnerTipoMonedaPago.getSelectedItem().toString();
        return tipo;
    }*/

    private void ejecutarLista() {

        try {
            SeleccionModoMontoPago.ListadoMoneda listadoBeneficiario = new SeleccionModoMontoPago.ListadoMoneda();
            listadoBeneficiario.execute();
        } catch (Exception e) {
            //listadoBeneficiario = null;
        }

    }

    private class ListadoMoneda extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                monedaEntityArrayList = dao.ListarMoneda();
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            monedaAdapter.setNewListMoneda(monedaEntityArrayList);
            monedaAdapter.notifyDataSetChanged();
        }
    }

    private void ejecutarListaSoles(){

        try {
            SeleccionModoMontoPago.ListadoDeudasTarjetasSoles listadoDeudasTarjetasSoles = new SeleccionModoMontoPago.ListadoDeudasTarjetasSoles();
            listadoDeudasTarjetasSoles.execute();
        } catch (Exception e){
            //listadoBeneficiario = null;
        }

    }

    private class ListadoDeudasTarjetasSoles extends AsyncTask<String,Void,Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                deudasTarjetasSolesArrayList = dao.ingresarVoucherPagoTarjetaSoles(usuario.getUsuarioId());
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            deudasTarjetasSolesAdapter.setNewListDeudasTarjetas(deudasTarjetasSolesArrayList);
            deudasTarjetasSolesAdapter.notifyDataSetChanged();
            if (deudasTarjetasSolesArrayList == null){
                Toast.makeText(SeleccionModoMontoPago.this, "Usted no tiene deudas en soles", Toast.LENGTH_LONG).show();
                txt_monto_minimo.setText("0.00");
                txt_monto_mensual.setText("0.00");
                txt_monto_total.setText("0.00");
                rdbtn_soles.setChecked(false);
                rdbtn_dolares.setChecked(true);
            } else {
                txt_monto_minimo.setText(String.valueOf(decimal.format(deudasTarjetasSolesArrayList.get(0).getMontoMinimo())));
                txt_monto_mensual.setText(String.valueOf(decimal.format(deudasTarjetasSolesArrayList.get(0).getMontoMensual())));
                txt_monto_total.setText(String.valueOf(decimal.format(deudasTarjetasSolesArrayList.get(0).getMontoTotal())));
                tv_tipo_moneda_modo_monto_1.setText(deudasTarjetasSolesArrayList.get(0).getSignoMoneda());
                tv_tipo_moneda_modo_monto_2.setText(deudasTarjetasSolesArrayList.get(0).getSignoMoneda());
                tv_tipo_moneda_modo_monto_3.setText(deudasTarjetasSolesArrayList.get(0).getSignoMoneda());
                tv_tipo_moneda_modo_monto_4.setText(deudasTarjetasSolesArrayList.get(0).getSignoMoneda());
            }
        }
    }

    private void ejecutarListaDolares(){

        try {
            SeleccionModoMontoPago.ListadoDeudasTarjetasDolares listadoDeudasTarjetasSoles = new SeleccionModoMontoPago.ListadoDeudasTarjetasDolares();
            listadoDeudasTarjetasSoles.execute();
        } catch (Exception e){
            //listadoBeneficiario = null;
        }

    }

    private class ListadoDeudasTarjetasDolares extends AsyncTask<String,Void,Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                deudasTarjetasDolaresArrayList = dao.ingresarVoucherPagoTarjetaCredito(usuario.getUsuarioId());
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            deudasTarjetasDolaresAdapter.setNewListDeudasTarjetas(deudasTarjetasDolaresArrayList);
            deudasTarjetasDolaresAdapter.notifyDataSetChanged();
            if (deudasTarjetasDolaresArrayList == null){
                Toast.makeText(SeleccionModoMontoPago.this, "Usted no tiene deudas en dolares", Toast.LENGTH_LONG).show();
                txt_monto_minimo.setText("0.00");
                txt_monto_mensual.setText("0.00");
                txt_monto_total.setText("0.00");

                Intent intent = new Intent(SeleccionModoMontoPago.this, MenuCliente.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                intent.putExtra("cli_dni", cli_dni);
                startActivity(intent);
                finish();
            } else {
                txt_monto_minimo.setText(String.valueOf(decimal.format(deudasTarjetasDolaresArrayList.get(0).getMontoMinimo())));
                txt_monto_mensual.setText(String.valueOf(decimal.format(deudasTarjetasDolaresArrayList.get(0).getMontoMensual())));
                txt_monto_total.setText(String.valueOf(decimal.format(deudasTarjetasDolaresArrayList.get(0).getMontoTotal())));
                tv_tipo_moneda_modo_monto_1.setText(deudasTarjetasDolaresArrayList.get(0).getSignoMoneda());
                tv_tipo_moneda_modo_monto_2.setText(deudasTarjetasDolaresArrayList.get(0).getSignoMoneda());
                tv_tipo_moneda_modo_monto_3.setText(deudasTarjetasDolaresArrayList.get(0).getSignoMoneda());
                tv_tipo_moneda_modo_monto_4.setText(deudasTarjetasDolaresArrayList.get(0).getSignoMoneda());
            }
        }
    }
}
