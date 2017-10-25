package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

public class CuentasBeneficiarios extends Activity {

    String arrayTipoTarjeta[] = {"Débito", "Crédito"};
    String arrayBancoTarjeta[] = {"Scotiabank", "BCP", "Interbank", "BBVA", "Otros"};
    private RadioButton rdbtn_visa_option, rdbtn_amex_option, rdbtn_mc_option;
    private Spinner spinnerTipoTarjeta, spinnerBancoTarjeta;
    private EditText txt_cod_interbancario, txt_numero_tarjeta_beneficiario;
    String dni_benef;
    private LinearLayout ll_datos_tarjeta_beneficiario;
    private Button btn_agregar_cuenta_beneficiario, btn_nuevo_beneficiario;
    private UsuarioEntity usuario;
    EditText txt_numero_tarjeta_beneficiario1, txt_numero_tarjeta_beneficiario2, txt_numero_tarjeta_beneficiario3, txt_numero_tarjeta_beneficiario4;
    EditText txt_cod_interbancario1, txt_cod_interbancario2, txt_cod_interbancario3, txt_cod_interbancario4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cuentas_beneficiarios);

        rdbtn_visa_option = (RadioButton) findViewById(R.id.rdbtn_visa_option);
        rdbtn_amex_option = (RadioButton) findViewById(R.id.rdbtn_amex_option);
        rdbtn_mc_option = (RadioButton) findViewById(R.id.rdbtn_mc_option);

        spinnerTipoTarjeta = (Spinner) findViewById(R.id.spinnerTipoTarjeta);
        spinnerBancoTarjeta = (Spinner) findViewById(R.id.spinnerBancoTarjeta);

        /*txt_cod_interbancario = (EditText) findViewById(R.id.txt_cod_interbancario);
        txt_numero_tarjeta_beneficiario = (EditText) findViewById(R.id.txt_numero_tarjeta_beneficiario);*/

        txt_numero_tarjeta_beneficiario1 = (EditText) findViewById(R.id.txt_numero_tarjeta_beneficiario1);
        txt_numero_tarjeta_beneficiario2 = (EditText) findViewById(R.id.txt_numero_tarjeta_beneficiario2);
        txt_numero_tarjeta_beneficiario3 = (EditText) findViewById(R.id.txt_numero_tarjeta_beneficiario3);
        txt_numero_tarjeta_beneficiario4 = (EditText) findViewById(R.id.txt_numero_tarjeta_beneficiario4);
        txt_cod_interbancario1 = (EditText) findViewById(R.id.txt_cod_interbancario1);
        txt_cod_interbancario2 = (EditText) findViewById(R.id.txt_cod_interbancario2);
        txt_cod_interbancario3 = (EditText) findViewById(R.id.txt_cod_interbancario3);
        txt_cod_interbancario4 = (EditText) findViewById(R.id.txt_cod_interbancario4);


        ll_datos_tarjeta_beneficiario = (LinearLayout) findViewById(R.id.ll_datos_tarjeta_beneficiario);

        btn_agregar_cuenta_beneficiario = (Button) findViewById(R.id.btn_agregar_cuenta_beneficiario);
        btn_nuevo_beneficiario = (Button) findViewById(R.id.btn_nuevo_beneficiario);

        cargarTipoTarjeta();
        cargarBancoTarjeta();
        numeroTarjetaBeneficiario();
        numeroCodigoInterbancarioBeneficiario();

        Bundle bundle = getIntent().getExtras();
        dni_benef = bundle.getString("dni_benef");
        usuario = bundle.getParcelable("usuario");

        rdbtn_mc_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdbtn_visa_option.setChecked(false);
                rdbtn_amex_option.setChecked(false);
            }
        });

        rdbtn_amex_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdbtn_visa_option.setChecked(false);
                rdbtn_mc_option.setChecked(false);
            }
        });

        rdbtn_visa_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdbtn_amex_option.setChecked(false);
                rdbtn_mc_option.setChecked(false);
            }
        });

        /*txt_numero_tarjeta_beneficiario.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                activarIngresoTarjeta();
            }
        });*/

        btn_agregar_cuenta_beneficiario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //if (!tarjeta.isEmpty() && !cuenta.isEmpty()){
                String tarjeta1 = txt_numero_tarjeta_beneficiario1.getText().toString();
                String tarjeta2 = txt_numero_tarjeta_beneficiario2.getText().toString();
                String tarjeta3 = txt_numero_tarjeta_beneficiario3.getText().toString();
                String tarjeta4 = txt_numero_tarjeta_beneficiario4.getText().toString();
                String cod_int1 = txt_cod_interbancario1.getText().toString();
                String cod_int2 = txt_cod_interbancario2.getText().toString();
                String cod_int3 = txt_cod_interbancario3.getText().toString();
                String cod_int4 = txt_cod_interbancario4.getText().toString();

                String tarjeta = tarjeta1 + tarjeta2 + tarjeta3 + tarjeta4;
                String codigo_interbancario = cod_int1 + cod_int2 + cod_int3 + cod_int4;
                    if (tarjeta.length() == 16){
                        if (!rdbtn_mc_option.isChecked() && !rdbtn_amex_option.isChecked() && !rdbtn_visa_option.isChecked()) {
                            Toast.makeText(CuentasBeneficiarios.this, "Seleccione el emisor de la tarjeta", Toast.LENGTH_SHORT).show();
                            //activarIngresoTarjeta();
                            ll_datos_tarjeta_beneficiario.setVisibility(View.VISIBLE);
                        } else {
                            CuentasBeneficiarios.insertarCuentasBeneficiario insertar = new CuentasBeneficiarios.insertarCuentasBeneficiario();
                            insertar.execute();

                            Intent intent = new Intent(CuentasBeneficiarios.this, IngresoDatosBeneficiario4De4.class);
                            intent.putExtra("usuario", usuario);
                            startActivity(intent);
                            finish();
                        }
                    } else if (codigo_interbancario.length() == 20){
                        CuentasBeneficiarios.insertarCuentasBeneficiario insertar = new CuentasBeneficiarios.insertarCuentasBeneficiario();
                        insertar.execute();

                        Intent intent = new Intent(CuentasBeneficiarios.this, IngresoDatosBeneficiario4De4.class);
                        intent.putExtra("usuario", usuario);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(CuentasBeneficiarios.this, "Numero de tarjeta o cuenta inválidos", Toast.LENGTH_SHORT).show();
                    }
                /*} else {
                    Toast.makeText(CuentasBeneficiarios.this, "Ingrese una cuenta o tarjeta del beneficiario", Toast.LENGTH_SHORT).show();
                }

                CuentasBeneficiarios.insertarCuentasBeneficiario insertar = new CuentasBeneficiarios.insertarCuentasBeneficiario();
                insertar.execute();*/
            }
        });

        btn_nuevo_beneficiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tarjeta1 = txt_numero_tarjeta_beneficiario1.getText().toString();
                String tarjeta2 = txt_numero_tarjeta_beneficiario2.getText().toString();
                String tarjeta3 = txt_numero_tarjeta_beneficiario3.getText().toString();
                String tarjeta4 = txt_numero_tarjeta_beneficiario4.getText().toString();
                String cod_int1 = txt_cod_interbancario1.getText().toString();
                String cod_int2 = txt_cod_interbancario2.getText().toString();
                String cod_int3 = txt_cod_interbancario3.getText().toString();
                String cod_int4 = txt_cod_interbancario4.getText().toString();

                String tarjeta = tarjeta1 + tarjeta2 + tarjeta3 + tarjeta4;
                String codigo_interbancario = cod_int1 + cod_int2 + cod_int3 + cod_int4;
                //if (!tarjeta.isEmpty() && !cuenta.isEmpty()){
                if (tarjeta.length() == 16){
                    if (!rdbtn_mc_option.isChecked() && !rdbtn_amex_option.isChecked() && !rdbtn_visa_option.isChecked()) {
                        Toast.makeText(CuentasBeneficiarios.this, "Seleccione el emisor de la tarjeta", Toast.LENGTH_SHORT).show();
                        //activarIngresoTarjeta();
                        ll_datos_tarjeta_beneficiario.setVisibility(View.VISIBLE);
                    } else {
                        CuentasBeneficiarios.insertarCuentasBeneficiario insertar = new CuentasBeneficiarios.insertarCuentasBeneficiario();
                        insertar.execute();

                        Intent intent = new Intent(CuentasBeneficiarios.this, IngresoDatosBeneficiarios2De4.class);
                        intent.putExtra("usuario", usuario);
                        startActivity(intent);
                        finish();
                    }
                } else if (codigo_interbancario.length() == 20){
                    CuentasBeneficiarios.insertarCuentasBeneficiario insertar = new CuentasBeneficiarios.insertarCuentasBeneficiario();
                    insertar.execute();

                    Intent intent = new Intent(CuentasBeneficiarios.this, IngresoDatosBeneficiarios2De4.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CuentasBeneficiarios.this, "Numero de tarjeta o cuenta inválidos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void cargarTipoTarjeta(){
        ArrayAdapter<String> adaptadorBanco = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayTipoTarjeta);
        spinnerTipoTarjeta.setAdapter(adaptadorBanco);
    }

    public void cargarBancoTarjeta(){
        ArrayAdapter<String> adaptadorBanco = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayBancoTarjeta);
        spinnerBancoTarjeta.setAdapter(adaptadorBanco);
    }

    public int obtenerEmisorTarjeta(){
        int emisor;

        if (rdbtn_visa_option.isChecked()) {
            emisor = 1;
        } else if (rdbtn_amex_option.isChecked()) {
            emisor = 3;
        } else {
            emisor = 2;
        }

        return emisor;
    }

    public int obtenerTipoTarjeta(){
        int tipo = 0;

        if (spinnerTipoTarjeta.getSelectedItem().toString().equals("Crédito")) {
            tipo = 1;
        } else if (spinnerTipoTarjeta.getSelectedItem().toString().equals("Débito")){
            tipo = 2;
        }

        return tipo;
    }

    public int obtenerBancoTarjeta(){
        int banco = 0;
        String bancoTarjeta = spinnerBancoTarjeta.getSelectedItem().toString();

        if (bancoTarjeta.equals("Scotiabank")) {
            banco = 1;
        } else if (bancoTarjeta.equals("BCP")) {
            banco = 2;
        } else if (bancoTarjeta.equals("Interbank")) {
            banco = 3;
        } else if (bancoTarjeta.equals("BBVA")) {
            banco = 4;
        } else if (bancoTarjeta.equals("Otros")) {
            banco = 5;
        }

        return banco;
    }

    private class insertarCuentasBeneficiario extends AsyncTask<String, Void, BeneficiarioEntity> {

        String tarjeta1 = txt_numero_tarjeta_beneficiario1.getText().toString();
        String tarjeta2 = txt_numero_tarjeta_beneficiario2.getText().toString();
        String tarjeta3 = txt_numero_tarjeta_beneficiario3.getText().toString();
        String tarjeta4 = txt_numero_tarjeta_beneficiario4.getText().toString();
        String cod_int1 = txt_cod_interbancario1.getText().toString();
        String cod_int2 = txt_cod_interbancario2.getText().toString();
        String cod_int3 = txt_cod_interbancario3.getText().toString();
        String cod_int4 = txt_cod_interbancario4.getText().toString();

        String tarjeta = tarjeta1 + tarjeta2 + tarjeta3 + tarjeta4;
        String codigo_interbancario = cod_int1 + cod_int2 + cod_int3 + cod_int4;

        @Override
        protected BeneficiarioEntity doInBackground(String... params) {
            BeneficiarioEntity user = null;

            try {

                if (tarjeta.isEmpty()){
                    tarjeta = "0000";
                    /*SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                    user = dao.getInsertarBeneficiario(nombre, apellido, celular1, celular2, email, fechaNac, pass, dni, usuario.getUsuarioId(), cod_ibkro, num_tarjeta);*/
                } else if (codigo_interbancario.isEmpty()){
                    codigo_interbancario = "0000";
                    /*SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                    user = dao.getInsertarBeneficiario(nombre, apellido, celular1, celular2, email, fechaNac, pass, dni, usuario.getUsuarioId(), cod_ibkro, num_tarjeta);*/
                }

                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.getInsertarCuentasBeneficiario(dni_benef, codigo_interbancario, tarjeta, obtenerEmisorTarjeta(), obtenerBancoTarjeta(), obtenerTipoTarjeta());//, obtenerEmisorTarjeta(), obtenerBancoTarjeta(), obtenerTipoTarjeta());

            } catch (Exception e) {
                user = null;
                //fldag_clic_ingreso = 0;;
            }
            return user;
        }
    }

    /*public void activarIngresoTarjeta(){
        String tarjeta_benef = txt_numero_tarjeta_beneficiario.getText().toString();
        if (tarjeta_benef.length() == 16) {
            ll_datos_tarjeta_beneficiario.setVisibility(View.VISIBLE);
        }
    }*/

    private void numeroTarjetaBeneficiario(){

        txt_numero_tarjeta_beneficiario1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_numero_tarjeta_beneficiario1.getText().toString().length() == 4) {
                    txt_numero_tarjeta_beneficiario2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_numero_tarjeta_beneficiario2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_numero_tarjeta_beneficiario2.getText().toString().length() == 4) {
                    txt_numero_tarjeta_beneficiario3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_numero_tarjeta_beneficiario3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_numero_tarjeta_beneficiario3.getText().toString().length() == 4) {
                    txt_numero_tarjeta_beneficiario4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_numero_tarjeta_beneficiario4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_cod_interbancario4.getText().toString().length() == 4) {
                    txt_cod_interbancario1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void numeroCodigoInterbancarioBeneficiario(){
        txt_cod_interbancario1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_cod_interbancario1.getText().toString().length() == 3) {
                    txt_cod_interbancario2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_cod_interbancario2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_cod_interbancario2.getText().toString().length() == 3) {
                    txt_cod_interbancario3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_cod_interbancario3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_cod_interbancario3.getText().toString().length() == 12) {
                    txt_cod_interbancario4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
