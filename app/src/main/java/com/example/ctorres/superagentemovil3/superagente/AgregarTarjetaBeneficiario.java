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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

public class AgregarTarjetaBeneficiario extends Activity {

    String arrayTipoTarjeta[] = {"Débito", "Crédito"};
    String arrayBancoTarjeta[] = {"Scotiabank", "BCP", "Interbank", "BBVA", "Otros"};
    private RadioButton rdbtn_visa_option, rdbtn_amex_option, rdbtn_mc_option;
    private Spinner spinnerTipoTarjeta, spinnerBancoTarjeta;
    String dni_benef;
    private UsuarioEntity usuario;
    EditText txt_numero_tarjeta_beneficiario1, txt_numero_tarjeta_beneficiario2, txt_numero_tarjeta_beneficiario3, txt_numero_tarjeta_beneficiario4;
    Button btn_agregar_tarjeta_beneficiario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_tarjeta_beneficiario);

        rdbtn_visa_option = (RadioButton) findViewById(R.id.rdbtn_visa_option);
        rdbtn_amex_option = (RadioButton) findViewById(R.id.rdbtn_amex_option);
        rdbtn_mc_option = (RadioButton) findViewById(R.id.rdbtn_mc_option);

        spinnerTipoTarjeta = (Spinner) findViewById(R.id.spinnerTipoTarjeta);
        spinnerBancoTarjeta = (Spinner) findViewById(R.id.spinnerBancoTarjeta);

        txt_numero_tarjeta_beneficiario1 = (EditText) findViewById(R.id.txt_numero_tarjeta_beneficiario1);
        txt_numero_tarjeta_beneficiario2 = (EditText) findViewById(R.id.txt_numero_tarjeta_beneficiario2);
        txt_numero_tarjeta_beneficiario3 = (EditText) findViewById(R.id.txt_numero_tarjeta_beneficiario3);
        txt_numero_tarjeta_beneficiario4 = (EditText) findViewById(R.id.txt_numero_tarjeta_beneficiario4);

        btn_agregar_tarjeta_beneficiario = (Button) findViewById(R.id.btn_agregar_tarjeta_beneficiario);

        cargarTipoTarjeta();
        cargarBancoTarjeta();
        numeroTarjetaBeneficiario();

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

        btn_agregar_tarjeta_beneficiario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //if (!tarjeta.isEmpty() && !cuenta.isEmpty()){
                String tarjeta1 = txt_numero_tarjeta_beneficiario1.getText().toString();
                String tarjeta2 = txt_numero_tarjeta_beneficiario2.getText().toString();
                String tarjeta3 = txt_numero_tarjeta_beneficiario3.getText().toString();
                String tarjeta4 = txt_numero_tarjeta_beneficiario4.getText().toString();

                String tarjeta = tarjeta1 + tarjeta2 + tarjeta3 + tarjeta4;

                if (tarjeta.length() == 16){
                    if (!rdbtn_mc_option.isChecked() && !rdbtn_amex_option.isChecked() && !rdbtn_visa_option.isChecked()) {
                        Toast.makeText(AgregarTarjetaBeneficiario.this, "Seleccione el emisor de la tarjeta", Toast.LENGTH_SHORT).show();
                    } else {
                        insertarTarjetaBeneficiario insertar = new insertarTarjetaBeneficiario();
                        insertar.execute();

                        Intent intent = new Intent(AgregarTarjetaBeneficiario.this, ListadoCuentasTarjetasBeneficiario.class);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("dni_benef", dni_benef);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(AgregarTarjetaBeneficiario.this, "Numero de tarjeta", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class insertarTarjetaBeneficiario extends AsyncTask<String, Void, BeneficiarioEntity> {

        String tarjeta1 = txt_numero_tarjeta_beneficiario1.getText().toString();
        String tarjeta2 = txt_numero_tarjeta_beneficiario2.getText().toString();
        String tarjeta3 = txt_numero_tarjeta_beneficiario3.getText().toString();
        String tarjeta4 = txt_numero_tarjeta_beneficiario4.getText().toString();

        String tarjeta = tarjeta1 + tarjeta2 + tarjeta3 + tarjeta4;

        @Override
        protected BeneficiarioEntity doInBackground(String... params) {
            BeneficiarioEntity user = null;

            try {

                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.IngresarTarjetaBeneficiario(dni_benef, tarjeta, obtenerEmisorTarjeta(), obtenerBancoTarjeta(), obtenerTipoTarjeta());

            } catch (Exception e) {
                user = null;
                //fldag_clic_ingreso = 0;;
            }
            return user;
        }
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
    }
}
