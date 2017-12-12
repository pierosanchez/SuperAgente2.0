package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.IdRes;
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
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

public class SeleccionModoMontoPago extends Activity {

    String tipoMoneda[] = {"S/.", "US$"};
    TextView spinnerTipoMonedaPago;
    LinearLayout btn_continuar, btn_cancelar;
    RadioGroup rdgp_montos;
    EditText txt_monto_minimo, txt_monto_mensual, txt_monto_total, txt_monto_cuenta;
    String monto;
    RadioButton rdbtn_minimo, rdbtn_mensual, rdbtn_total, rdbtn_cuenta;
    Bitmap bmp;
    private UsuarioEntity usuario;
    String num_tarjeta, banco_tarjeta;
    int tipo_tarjeta, emisor_tarjeta;
    ImageView imageView;
    TextView tv_numero_clave_cifrada_cargo, tv_tipo_moneda_modo_monto_1, tv_tipo_moneda_modo_monto_2, tv_tipo_moneda_modo_monto_3, tv_tipo_moneda_modo_monto_4;
    String cliente, cli_dni, desc_corta_banco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_modo_monto_pago);

        btn_continuar = (LinearLayout) findViewById(R.id.btn_continuar);
        btn_cancelar = (LinearLayout) findViewById(R.id.btn_cancelar);

        spinnerTipoMonedaPago = (TextView) findViewById(R.id.spinnerTipoMonedaPago);

        txt_monto_minimo = (EditText) findViewById(R.id.txt_monto_minimo);
        txt_monto_mensual = (EditText) findViewById(R.id.txt_monto_mensual);
        txt_monto_total = (EditText) findViewById(R.id.txt_monto_total);
        txt_monto_cuenta = (EditText) findViewById(R.id.txt_monto_cuenta);

        rdgp_montos = (RadioGroup) findViewById(R.id.rdgp_montos_pagar);
        rdbtn_minimo = (RadioButton) findViewById(R.id.rdbtn_minimo);
        rdbtn_mensual = (RadioButton) findViewById(R.id.rdbtn_mensual);
        rdbtn_total = (RadioButton) findViewById(R.id.rdbtn_total);
        rdbtn_cuenta = (RadioButton) findViewById(R.id.rdbtn_cuenta);

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
        spinnerTipoMonedaPago.setEnabled(false);

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

        tv_tipo_moneda_modo_monto_1.setText(spinnerTipoMonedaPago.getText().toString());
        tv_tipo_moneda_modo_monto_2.setText(spinnerTipoMonedaPago.getText().toString());
        tv_tipo_moneda_modo_monto_3.setText(spinnerTipoMonedaPago.getText().toString());
        tv_tipo_moneda_modo_monto_4.setText(spinnerTipoMonedaPago.getText().toString());

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
                if (!spinnerTipoMonedaPago.getSelectedItem().toString().equals("S/.")){
                    txt_monto_minimo.setText("15.10");
                    txt_monto_mensual.setText("25.12");
                    txt_monto_total.setText("100.75");
                } else {
                    txt_monto_minimo.setText("50.78");
                    txt_monto_mensual.setText("120.44");
                    txt_monto_total.setText("236.11");
                }
                tv_tipo_moneda_modo_monto_1.setText(obtenerTipoMonedaDeuda());
                tv_tipo_moneda_modo_monto_2.setText(obtenerTipoMonedaDeuda());
                tv_tipo_moneda_modo_monto_3.setText(obtenerTipoMonedaDeuda());
                tv_tipo_moneda_modo_monto_4.setText(obtenerTipoMonedaDeuda());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (!spinnerTipoMonedaPago.getSelectedItem().toString().equals("S/.")){
                    txt_monto_minimo.setText("15.10");
                    txt_monto_mensual.setText("25.12");
                    txt_monto_total.setText("100.75");
                } else {
                    txt_monto_minimo.setText("50.78");
                    txt_monto_mensual.setText("120.44");
                    txt_monto_total.setText("236.11");
                }
                tv_tipo_moneda_modo_monto_1.setText(obtenerTipoMonedaDeuda());
                tv_tipo_moneda_modo_monto_2.setText(obtenerTipoMonedaDeuda());
                tv_tipo_moneda_modo_monto_3.setText(obtenerTipoMonedaDeuda());
                tv_tipo_moneda_modo_monto_4.setText(obtenerTipoMonedaDeuda());
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
                                intent.putExtra("tipo_moneda_deuda", spinnerTipoMonedaPago.getText().toString());
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
                        intent.putExtra("tipo_moneda_deuda", spinnerTipoMonedaPago.getText().toString());
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

}
