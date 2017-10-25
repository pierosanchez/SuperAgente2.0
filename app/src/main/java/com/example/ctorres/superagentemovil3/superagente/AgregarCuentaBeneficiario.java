package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

public class AgregarCuentaBeneficiario extends Activity {

    private Button btn_agregar_cuenta_beneficiario;
    private UsuarioEntity usuario;
    String dni_benef;
    EditText txt_cod_interbancario1, txt_cod_interbancario2, txt_cod_interbancario3, txt_cod_interbancario4;
    String cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_cuenta_beneficiario);

        txt_cod_interbancario1 = (EditText) findViewById(R.id.txt_cod_interbancario1);
        txt_cod_interbancario2 = (EditText) findViewById(R.id.txt_cod_interbancario2);
        txt_cod_interbancario3 = (EditText) findViewById(R.id.txt_cod_interbancario3);
        txt_cod_interbancario4 = (EditText) findViewById(R.id.txt_cod_interbancario4);

        btn_agregar_cuenta_beneficiario = (Button) findViewById(R.id.btn_agregar_cuenta_beneficiario);

        Bundle bundle = getIntent().getExtras();
        dni_benef = bundle.getString("dni_benef");
        usuario = bundle.getParcelable("usuario");
        cliente = bundle.getString("cliente");

        numeroCodigoInterbancarioBeneficiario();

        btn_agregar_cuenta_beneficiario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //if (!tarjeta.isEmpty() && !cuenta.isEmpty()){
                String cod_int1 = txt_cod_interbancario1.getText().toString();
                String cod_int2 = txt_cod_interbancario2.getText().toString();
                String cod_int3 = txt_cod_interbancario3.getText().toString();
                String cod_int4 = txt_cod_interbancario4.getText().toString();

                String codigo_interbancario = cod_int1 + cod_int2 + cod_int3 + cod_int4;
                if (codigo_interbancario.length() == 20){
                    AgregarCuentaBeneficiario.insertarCuentasBeneficiario insertar = new AgregarCuentaBeneficiario.insertarCuentasBeneficiario();
                    insertar.execute();

                    Intent intent = new Intent(AgregarCuentaBeneficiario.this, ListadoCuentasTarjetasBeneficiario.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("dni_benef", dni_benef);
                    intent.putExtra("cliente", cliente);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AgregarCuentaBeneficiario.this, "Numero de tarjeta o cuenta inválidos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class insertarCuentasBeneficiario extends AsyncTask<String, Void, BeneficiarioEntity> {

        String cod_int1 = txt_cod_interbancario1.getText().toString();
        String cod_int2 = txt_cod_interbancario2.getText().toString();
        String cod_int3 = txt_cod_interbancario3.getText().toString();
        String cod_int4 = txt_cod_interbancario4.getText().toString();

        String codigo_interbancario = cod_int1 + cod_int2 + cod_int3 + cod_int4;

        @Override
        protected BeneficiarioEntity doInBackground(String... params) {
            BeneficiarioEntity user = null;

            try {

                if (codigo_interbancario.isEmpty()){
                    codigo_interbancario = "0000";
                }

                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.IngresarCuentaBeneficiario(dni_benef, codigo_interbancario);

            } catch (Exception e) {
                user = null;
                //fldag_clic_ingreso = 0;;
            }
            return user;
        }
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
