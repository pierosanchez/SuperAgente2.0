package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.adapter.BancosAdapter;
import com.example.ctorres.superagentemovil3.adapter.GetTarjetaBinAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.BancosEntity;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.TarjetaBinEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;

public class AgregarCuentasBeneficiario extends Activity {

    String arrayTipoTarjeta[] = {"Débito", "Crédito"};
    String arrayBancoTarjeta[] = {"Scotiabank", "BCP", "Interbank", "BBVA", "Otros"};
    private RadioButton rdbtn_visa_option, rdbtn_amex_option, rdbtn_mc_option;
    private Spinner spinnerTipoTarjeta, spinnerBancoTarjeta;
    private EditText txt_cod_interbancario, txt_numero_tarjeta_beneficiario;
    String dni_benef;
    private LinearLayout ll_datos_tarjeta_beneficiario;
    private Button btn_agregar_cuenta_beneficiario, btn_regresar;
    private UsuarioEntity usuario;
    private BeneficiarioEntity beneficiarioEntityU;
    EditText txt_numero_tarjeta_beneficiario1, txt_numero_tarjeta_beneficiario2, txt_numero_tarjeta_beneficiario3, txt_numero_tarjeta_beneficiario4;
    EditText txt_cod_interbancario1, txt_cod_interbancario2, txt_cod_interbancario3, txt_cod_interbancario4;
    String cliente, cli_dni;
    ArrayList<BancosEntity> bancosEntityArrayList;
    ArrayList<TarjetaBinEntity> tarjetaBinEntityArrayList;
    GetTarjetaBinAdapter getTarjetaBinAdapter;
    BancosAdapter bancosAdapter;
    int bancos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_cuentas_beneficiario);

        rdbtn_visa_option = (RadioButton) findViewById(R.id.rdbtn_visa_option);
        rdbtn_amex_option = (RadioButton) findViewById(R.id.rdbtn_amex_option);
        rdbtn_mc_option = (RadioButton) findViewById(R.id.rdbtn_mc_option);

        spinnerTipoTarjeta = (Spinner) findViewById(R.id.spinnerTipoTarjeta);
        spinnerBancoTarjeta = (Spinner) findViewById(R.id.spinnerBancoTarjeta);

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
        btn_regresar = (Button) findViewById(R.id.btn_nuevo_beneficiario);

        cargarTipoTarjeta();
        cargarBancoTarjeta();
        numeroTarjetaBeneficiario();
        numeroCodigoInterbancarioBeneficiario();

        Bundle bundle = getIntent().getExtras();
        dni_benef = bundle.getString("dni_benef");
        usuario = bundle.getParcelable("usuario");
        cliente = bundle.getString("cliente");
        cli_dni = bundle.getString("cli_dni");

        bancosEntityArrayList = null;
        bancosAdapter = new BancosAdapter(bancosEntityArrayList, getApplication());
        spinnerBancoTarjeta.setAdapter(bancosAdapter);

        ejecutarLista();

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
                        Toast.makeText(AgregarCuentasBeneficiario.this, "Seleccione el emisor de la tarjeta", Toast.LENGTH_SHORT).show();
                        ll_datos_tarjeta_beneficiario.setVisibility(View.VISIBLE);
                    } else {
                        AgregarCuentasBeneficiario.insertarCuentasBeneficiario insertar = new AgregarCuentasBeneficiario.insertarCuentasBeneficiario();
                        insertar.execute();

                        Intent intent = new Intent(AgregarCuentasBeneficiario.this, ListadoCuentasTarjetasBeneficiario.class);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("dni_benef", dni_benef);
                        intent.putExtra("cliente", cliente);
                        intent.putExtra("cli_dni", cli_dni);
                        startActivity(intent);
                        finish();
                    }
                } else if (codigo_interbancario.length() == 20){
                    AgregarCuentasBeneficiario.insertarCuentasBeneficiario insertar = new AgregarCuentasBeneficiario.insertarCuentasBeneficiario();
                    insertar.execute();

                    Intent intent = new Intent(AgregarCuentasBeneficiario.this, ListadoCuentasTarjetasBeneficiario.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("dni_benef", dni_benef);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("cli_dni", cli_dni);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AgregarCuentasBeneficiario.this, "Numero de tarjeta o cuenta inválidos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AgregarCuentasBeneficiario.this, ListadoCuentasTarjetasBeneficiario.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("dni_benef", dni_benef);
                intent.putExtra("cliente", cliente);
                intent.putExtra("cli_dni", cli_dni);
                startActivity(intent);
                finish();
            }
        });

        spinnerBancoTarjeta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bancos = bancosAdapter.getItem(position).getCod_banco();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void ejecutarLista() {

        try {
            AgregarCuentasBeneficiario.ListadoEmpresas listadoEmpresas = new AgregarCuentasBeneficiario.ListadoEmpresas();
            listadoEmpresas.execute();
        } catch (Exception e) {
            //listadoBeneficiario = null;
        }

    }

    private class ListadoEmpresas extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                bancosEntityArrayList = dao.ListadoBancos();
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //usuarioEntityArrayList.remove(banco = banco_tarjeta);
            bancosAdapter.setNewListbancos(bancosEntityArrayList);
            bancosAdapter.notifyDataSetChanged();
        }
    }

    public void cargarTipoTarjeta(){
        ArrayAdapter<String> adaptadorBanco = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayTipoTarjeta);
        spinnerTipoTarjeta.setAdapter(adaptadorBanco);
    }

    public void cargarBancoTarjeta(){
        ArrayAdapter<String> adaptadorBanco = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayBancoTarjeta);
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
            BeneficiarioEntity user;

            try {

                if (tarjeta.isEmpty()){
                    tarjeta = "0000";
                } else if (codigo_interbancario.isEmpty()){
                    codigo_interbancario = "0000";
                }

                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.getInsertarCuentasBeneficiario(dni_benef, codigo_interbancario, tarjeta, obtenerEmisorTarjeta(), bancos, obtenerTipoTarjeta());//, obtenerEmisorTarjeta(), obtenerBancoTarjeta(), obtenerTipoTarjeta());

            } catch (Exception e) {
                user = null;
                //fldag_clic_ingreso = 0;
            }
            return user;
        }

        @Override
        protected void onPostExecute(BeneficiarioEntity beneficiarioEntity) {
            beneficiarioEntityU = beneficiarioEntity;
            if (beneficiarioEntity.getError() != null) {
                if (beneficiarioEntity.getError().equals("000")) {
                    Toast.makeText(AgregarCuentasBeneficiario.this, "Solo se puede ingresar una tarjeta o cuenta para el beneficiario", Toast.LENGTH_LONG).show();
                } else if (beneficiarioEntity.getError().equals("00")) {
                    Toast.makeText(AgregarCuentasBeneficiario.this, "Ingresado correctamente", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(AgregarCuentasBeneficiario.this, "Hubo un error", Toast.LENGTH_LONG).show();
            }
        }
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

        txt_numero_tarjeta_beneficiario4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_numero_tarjeta_beneficiario4.getText().toString().length() == 4) {
                    ll_datos_tarjeta_beneficiario.setVisibility(View.VISIBLE);
                    tarjetaBinEntityArrayList = null;
                    getTarjetaBinAdapter = new GetTarjetaBinAdapter(tarjetaBinEntityArrayList, getApplication());

                    ejecutarListaTarjetasBin();
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

    private void ejecutarListaTarjetasBin() {

        try {
            AgregarCuentasBeneficiario.ListadoTarjetaBin listadoEmpresas = new AgregarCuentasBeneficiario.ListadoTarjetaBin();
            listadoEmpresas.execute();
        } catch (Exception e) {
            //listadoBeneficiario = null;
        }

    }

    private class ListadoTarjetaBin extends AsyncTask<String, Void, Void> {
        String tarjeta1 = txt_numero_tarjeta_beneficiario1.getText().toString();
        String tarjeta2 = txt_numero_tarjeta_beneficiario2.getText().toString();
        String tarjeta3 = txt_numero_tarjeta_beneficiario3.getText().toString();
        String tarjeta4 = txt_numero_tarjeta_beneficiario4.getText().toString();
        String numeroTarjeta = tarjeta1 + tarjeta2 + tarjeta3 + tarjeta4;
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                tarjetaBinEntityArrayList = dao.getDatosBinTarjeta(numeroTarjeta);
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getTarjetaBinAdapter.setNewListTarjetaBin(tarjetaBinEntityArrayList);
            getTarjetaBinAdapter.notifyDataSetChanged();
            if (getTarjetaBinAdapter.getItem(0).getRpta_bin().equals("00")) {
                if (getTarjetaBinAdapter.getItem(0).getMarca().equals("Mastercard ")) {
                    if (getTarjetaBinAdapter.getItem(0).getTipo_tarjeta().equals("Crédito")) {
                        spinnerTipoTarjeta.setSelection(1);
                        rdbtn_mc_option.setChecked(true);
                        spinnerTipoTarjeta.setEnabled(false);
                        rdbtn_mc_option.setEnabled(false);
                        rdbtn_amex_option.setEnabled(false);
                        rdbtn_visa_option.setEnabled(false);
                    } else if (getTarjetaBinAdapter.getItem(0).getTipo_tarjeta().equals("Debito")) {
                        spinnerTipoTarjeta.setSelection(0);
                        rdbtn_mc_option.setChecked(true);
                        spinnerTipoTarjeta.setEnabled(false);
                        rdbtn_mc_option.setEnabled(false);
                        rdbtn_amex_option.setEnabled(false);
                        rdbtn_visa_option.setEnabled(false);
                    } else {
                        rdbtn_mc_option.setChecked(true);
                        rdbtn_mc_option.setEnabled(false);
                    }
                } else if (getTarjetaBinAdapter.getItem(0).getMarca().equals("AMEX ")) {
                    if (getTarjetaBinAdapter.getItem(0).getTipo_tarjeta().equals("Crédito")) {
                        spinnerTipoTarjeta.setSelection(1);
                        rdbtn_amex_option.setChecked(true);
                        spinnerTipoTarjeta.setEnabled(false);
                        rdbtn_mc_option.setEnabled(false);
                        rdbtn_amex_option.setEnabled(false);
                        rdbtn_visa_option.setEnabled(false);
                    } else if (getTarjetaBinAdapter.getItem(0).getTipo_tarjeta().equals("Debito")) {
                        spinnerTipoTarjeta.setSelection(0);
                        rdbtn_amex_option.setChecked(true);
                        spinnerTipoTarjeta.setEnabled(false);
                        rdbtn_mc_option.setEnabled(false);
                        rdbtn_amex_option.setEnabled(false);
                        rdbtn_visa_option.setEnabled(false);
                    } else {
                        rdbtn_amex_option.setChecked(true);
                        rdbtn_amex_option.setEnabled(false);
                    }
                } else if (getTarjetaBinAdapter.getItem(0).getMarca().equals("VISA")) {
                    if (getTarjetaBinAdapter.getItem(0).getTipo_tarjeta().equals("Crédito")) {
                        spinnerTipoTarjeta.setSelection(1);
                        rdbtn_visa_option.setChecked(true);
                        spinnerTipoTarjeta.setEnabled(false);
                        rdbtn_mc_option.setEnabled(false);
                        rdbtn_amex_option.setEnabled(false);
                        rdbtn_visa_option.setEnabled(false);
                    } else if (getTarjetaBinAdapter.getItem(0).getTipo_tarjeta().equals("Debito")) {
                        spinnerTipoTarjeta.setSelection(0);
                        rdbtn_visa_option.setChecked(true);
                        spinnerTipoTarjeta.setEnabled(false);
                        rdbtn_mc_option.setEnabled(false);
                        rdbtn_amex_option.setEnabled(false);
                        rdbtn_visa_option.setEnabled(false);
                    } else {
                        rdbtn_visa_option.setChecked(true);
                        rdbtn_mc_option.setEnabled(false);
                        rdbtn_amex_option.setEnabled(false);
                        rdbtn_visa_option.setEnabled(false);
                    }
                }
            } else if (getTarjetaBinAdapter.getItem(0).getRpta_bin().equals("01")){
                Toast.makeText(AgregarCuentasBeneficiario.this, "El numero de BIN no existe", Toast.LENGTH_LONG).show();
            }
        }
    }
}
