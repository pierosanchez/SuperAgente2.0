package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.Calendar;

public class ActualizarTarjetaBeneficiario extends Activity {

    EditText nroTarjetaDigito1, nroTarjetaDigito2, nroTarjetaDigito3, nroTarjetaDigito4;
    TextView txt_fecha_vcto_tarjeta;
    Button btn_guardar_actualizacion_tarjeta, btn_regresar_actualizacion_tarjeta;
    //String id_tarjeta;
    private UsuarioEntity usuario;
    String arrayTipoTarjeta[] = {"Débito", "Crédito"};
    String arrayBancoTarjeta[] = {"Scotiabank", "BCP", "Interbank", "BBVA", "Otros"};
    Spinner spinnerTipoTarjeta, spinnerBancoTarjeta;
    private Calendar calendar;
    private int year, month, day;
    int id_cuenta_benef;
    String dni_benef;
    RadioButton rdbtn_visa_option, rdbtn_amex_option, rdbtn_mc_option;
    String cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_tarjeta_beneficiario);

        nroTarjetaDigito1 = (EditText) findViewById(R.id.nroTarjetaDigito1);
        nroTarjetaDigito2 = (EditText) findViewById(R.id.nroTarjetaDigito2);
        nroTarjetaDigito3 = (EditText) findViewById(R.id.nroTarjetaDigito3);
        nroTarjetaDigito4 = (EditText) findViewById(R.id.nroTarjetaDigito4);

        txt_fecha_vcto_tarjeta = (TextView) findViewById(R.id.txt_fecha_vcto_tarjeta);

        spinnerTipoTarjeta = (Spinner) findViewById(R.id.spinnerTipoTarjeta);
        spinnerBancoTarjeta = (Spinner) findViewById(R.id.spinnerBancoTarjeta);

        rdbtn_visa_option = (RadioButton) findViewById(R.id.rdbtn_visa_option);
        rdbtn_amex_option = (RadioButton) findViewById(R.id.rdbtn_amex_option);
        rdbtn_mc_option = (RadioButton) findViewById(R.id.rdbtn_mc_option);

        btn_guardar_actualizacion_tarjeta = (Button) findViewById(R.id.btn_guardar_actualizacion_tarjeta);
        btn_regresar_actualizacion_tarjeta = (Button) findViewById(R.id.btn_regresar_actualizacion_tarjeta);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1);

        numeroTarjeta();
        //confirmarNumeroTarjeta();
        cargarTipoTarjeta();
        cargarBancoTarjeta();

        Bundle bundle = getIntent().getExtras();
        id_cuenta_benef = bundle.getInt("id_cuenta_benef");
        usuario = bundle.getParcelable("usuario");
        dni_benef = bundle.getString("dni_benef");
        cliente = bundle.getString("cliente");

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

        btn_guardar_actualizacion_tarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresarTarjetas();

                Intent intent = new Intent(ActualizarTarjetaBeneficiario.this, ListadoCuentasTarjetasBeneficiario.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("dni_benef", dni_benef);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });

        btn_regresar_actualizacion_tarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActualizarTarjetaBeneficiario.this, ListadoCuentasTarjetasBeneficiario.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("dni_benef", dni_benef);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });

    }

    private void numeroTarjeta() {


        nroTarjetaDigito1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (nroTarjetaDigito1.getText().toString().length() == 4)     //size as per your requirement
                {
                    nroTarjetaDigito2.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        nroTarjetaDigito2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (nroTarjetaDigito2.getText().toString().length() == 4)     //size as per your requirement
                {
                    nroTarjetaDigito3.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        nroTarjetaDigito3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (nroTarjetaDigito3.getText().toString().length() == 4)     //size as per your requirement
                {
                    nroTarjetaDigito4.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
    }

    private class actualizarTarjeta extends AsyncTask<String, Void, BeneficiarioEntity> {

        String tarjeta1 = nroTarjetaDigito1.getText().toString();
        String tarjeta2 = nroTarjetaDigito2.getText().toString();
        String tarjeta3 = nroTarjetaDigito3.getText().toString();
        String tarjeta4 = nroTarjetaDigito4.getText().toString();
        String numeroTarjeta = tarjeta1 + tarjeta2 + tarjeta3 + tarjeta4;
        //String cvv = txt_cvv_tarjeta.getText().toString();
        String venimientoTarjeta = txt_fecha_vcto_tarjeta.getText().toString();
        //String bancoTarjeta = spinnerBancoTarjeta.getSelectedItem().toString();

        @Override
        protected BeneficiarioEntity doInBackground(String... params) {
            BeneficiarioEntity user;
            try {

                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.actualizarTarjetaBeneficiario(numeroTarjeta, obtenerBancoTarjeta(), obtenerEmisorTarjeta(), id_cuenta_benef, obtenerTipoTarjeta());

            } catch (Exception e) {
                user = null;
                //flag_clic_ingreso = 0;;
            }
            return user;

        }
    }

    public int obtenerBancoTarjeta() {
        int banco;
        String bancoTarjeta = spinnerBancoTarjeta.getSelectedItem().toString();

        if (bancoTarjeta.equals("Scotiabank")) {
            banco = 1;
        } else if (bancoTarjeta.equals("BCP")) {
            banco = 2;
        } else if (bancoTarjeta.equals("Interbank")) {
            banco = 3;
        } else if (bancoTarjeta.equals("BBVA")) {
            banco = 4;
        } else {
            banco = 5;
        }

        return banco;
    }

    public void ingresarTarjetas() {
        String tarjeta1 = nroTarjetaDigito1.getText().toString();
        String tarjeta2 = nroTarjetaDigito2.getText().toString();
        String tarjeta3 = nroTarjetaDigito3.getText().toString();
        String tarjeta4 = nroTarjetaDigito4.getText().toString();
        String numeroTarjeta = tarjeta1 + tarjeta2 + tarjeta3 + tarjeta4;
        /*String confirmtar1 = confirmNroTarjetaDigito1.getText().toString();
        String confirmtar2 = confirmNroTarjetaDigito2.getText().toString();
        String confirmtar3 = confirmNroTarjetaDigito3.getText().toString();
        String confirmtar4 = confirmNroTarjetaDigito4.getText().toString();
        String confirmarTarjeta = confirmtar1 + confirmtar2 + confirmtar3 + confirmtar4;*/

        if (!numeroTarjeta.equals("")) {
            ActualizarTarjetaBeneficiario.actualizarTarjeta validarTarjeta = new ActualizarTarjetaBeneficiario.actualizarTarjeta();
            validarTarjeta.execute();
        } else {
            Toast.makeText(ActualizarTarjetaBeneficiario.this, "Ingrese su contraseña", Toast.LENGTH_SHORT).show();
        }

        /*Intent intent = new Intent(InformacionTarjeta.this, ControlMaximoNumeroCuentas.class);
        intent.putExtra("usuario", usuario);
        startActivity(intent);
        finish();*/
    }

    public void cargarTipoTarjeta() {
        ArrayAdapter<String> adaptadorBanco = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayTipoTarjeta);
        spinnerTipoTarjeta.setAdapter(adaptadorBanco);
    }

    public void cargarBancoTarjeta() {
        ArrayAdapter<String> adaptadorBanco = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayBancoTarjeta);
        spinnerBancoTarjeta.setAdapter(adaptadorBanco);
    }

    public void setDate(View view) {
        showDialog(999);
        //Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2 + 1);
        }
    };

    private void showDate(int year, int month) {
        txt_fecha_vcto_tarjeta.setText(new StringBuilder()
                .append(month).append("/").append(year));
    }

    public int obtenerEmisorTarjeta() {
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

    public int obtenerTipoTarjeta() {
        int tipo;

        if (spinnerTipoTarjeta.getSelectedItem().toString().equals("Crédito")) {
            tipo = 1;
        } else {
            tipo = 2;
        }

        return tipo;
    }
}
