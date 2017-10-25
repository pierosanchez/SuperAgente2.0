package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.BancosAdapter;
import com.example.ctorres.superagentemovil3.dao.EmpresasServiciosAdapter;
import com.example.ctorres.superagentemovil3.dao.GetTarjetaBinAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.BancosEntity;
import com.example.ctorres.superagentemovil3.entity.TarjetaBinEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;
import java.util.Calendar;

public class AgregarTarjetasUsuario extends Activity {

    EditText nroTarjetaDigito1, nroTarjetaDigito2, nroTarjetaDigito3, nroTarjetaDigito4;
    TextView txt_fecha_vcto_tarjeta;
    private Button agregarTarjeta, terminar, btnRegresar, btn_validar_tarjeta;
    private View rootView;
    private String numeroTarjeta, confirmNumeroTarjeta;
    private UsuarioEntity usuario;
    private UsuarioEntity usResp;
    Spinner spinnerTipoTarjeta, spinnerBancoTarjeta;
    String arrayTipoTarjeta[] = {"Débito", "Crédito"};
    String arrayBancoTarjeta[] = {"Scotiabank", "BCP", "Interbank", "BBVA", "Otros"};
    private Calendar calendar;
    private int year, month, day;
    RadioButton rdbtn_visa_option, rdbtn_amex_option, rdbtn_mc_option;
    ArrayList<BancosEntity> bancosEntityArrayList;
    ArrayList<TarjetaBinEntity> tarjetaBinEntityArrayList;
    BancosAdapter bancosAdapter;
    GetTarjetaBinAdapter getTarjetaBinAdapter;
    int bancos;
    String cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_tarjetas_usuario);

        nroTarjetaDigito1 = (EditText) findViewById(R.id.nroTarjetaDigito1);
        nroTarjetaDigito2 = (EditText) findViewById(R.id.nroTarjetaDigito2);
        nroTarjetaDigito3 = (EditText) findViewById(R.id.nroTarjetaDigito3);
        nroTarjetaDigito4 = (EditText) findViewById(R.id.nroTarjetaDigito4);

        txt_fecha_vcto_tarjeta = (TextView) findViewById(R.id.txt_fecha_vcto_tarjeta);

        spinnerTipoTarjeta = (Spinner) findViewById(R.id.spinnerTipoTarjeta);
        spinnerBancoTarjeta = (Spinner) findViewById(R.id.spinnerBancoTarjeta);

        terminar = (Button) findViewById(R.id.terminar);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        btn_validar_tarjeta = (Button) findViewById(R.id.btn_validar_tarjeta);

        rdbtn_visa_option = (RadioButton) findViewById(R.id.rdbtn_visa_option);
        rdbtn_amex_option = (RadioButton) findViewById(R.id.rdbtn_amex_option);
        rdbtn_mc_option = (RadioButton) findViewById(R.id.rdbtn_mc_option);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");
        cliente = bundle.getString("cliente");

        numeroTarjeta();
        cargarTipoTarjeta();
        //cargarBancoTarjeta();

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

        spinnerBancoTarjeta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bancos = bancosAdapter.getItem(position).getCod_banco();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        terminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rdbtn_amex_option.isChecked() || rdbtn_mc_option.isChecked() || rdbtn_visa_option.isChecked()) {

                    //ingresarTarjetas();
                    AgregarTarjetasUsuario.ValidarTarjeta validarTarjeta = new AgregarTarjetasUsuario.ValidarTarjeta();
                    validarTarjeta.execute();

                } else {
                    Toast.makeText(AgregarTarjetasUsuario.this, "Seleccione el tipo de tarjeta", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AgregarTarjetasUsuario.this, ListadoTarjetasUsuario.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });

        btn_validar_tarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tarjetaBinEntityArrayList = null;
                getTarjetaBinAdapter = new GetTarjetaBinAdapter(tarjetaBinEntityArrayList, getApplication());

                ejecutarListaTarjetasBin();
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

    private class ValidarTarjeta extends AsyncTask<String, Void, UsuarioEntity> {

        String tarjeta1 = nroTarjetaDigito1.getText().toString();
        String tarjeta2 = nroTarjetaDigito2.getText().toString();
        String tarjeta3 = nroTarjetaDigito3.getText().toString();
        String tarjeta4 = nroTarjetaDigito4.getText().toString();
        String numeroTarjeta = tarjeta1 + tarjeta2 + tarjeta3 + tarjeta4;
        //String cvv = txt_cvv_tarjeta.getText().toString();
        String venimientoTarjeta = txt_fecha_vcto_tarjeta.getText().toString();
        //String bancoTarjeta = spinnerBancoTarjeta.getSelectedItem().toString();

        @Override
        protected UsuarioEntity doInBackground(String... params) {
            UsuarioEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.getValidarTarjeta(usuario.getUsuarioId(), numeroTarjeta, venimientoTarjeta, obtenerTipoTarjeta(), obtenerEmisorTarjeta(), bancos);
                usuario.setValidaTarjeta(user.getValidaTarjeta());
            } catch (Exception e) {
                user = null;
                //flag_clic_ingreso = 0;;
            }
            return user;
        }

        @Override
        protected void onPostExecute(UsuarioEntity usuarioEntity) {
            usuario = usuarioEntity;
            if (usuario.getRespTarjeta() != null) {
                if (usuario.getRespTarjeta().equals("00")) {
                    Toast.makeText(AgregarTarjetasUsuario.this, "El número de tarjeta ya existe", Toast.LENGTH_SHORT).show();
                } else if (usuario.getRespTarjeta().equals("01")) {
                    Toast.makeText(AgregarTarjetasUsuario.this, "No puede ingresar más de 5 tarjetas", Toast.LENGTH_SHORT).show();
                } else if (!usuario.getRespTarjeta().equals("00") || !usuario.getRespTarjeta().equals("01")) {
                    Intent intent = new Intent(AgregarTarjetasUsuario.this, ListadoTarjetasUsuario.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("cliente", cliente);
                    startActivity(intent);
                    finish();
                }
            } else {

            }
        }
    }

    /*public int obtenerBancoTarjeta() {
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

        if (!numeroTarjeta.equals("")) {
            *//*AgregarTarjetasUsuario.ValidarTarjeta validarTarjeta = new AgregarTarjetasUsuario.ValidarTarjeta();
            validarTarjeta.execute();*//*

            Intent intent = new Intent(AgregarTarjetasUsuario.this, ListadoTarjetasUsuario.class);
            intent.putExtra("usuario", usuario);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(AgregarTarjetasUsuario.this, "Ingrese su contraseña", Toast.LENGTH_SHORT).show();
        }
    }*/

    public void cargarTipoTarjeta() {
        ArrayAdapter<String> adaptadorBanco = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayTipoTarjeta);
        spinnerTipoTarjeta.setAdapter(adaptadorBanco);
    }

    /*public void cargarBancoTarjeta() {
        ArrayAdapter<String> adaptadorBanco = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayBancoTarjeta);
        spinnerBancoTarjeta.setAdapter(adaptadorBanco);
    }*/

    public String validaTipoTarjeta() {
        String tipo = "";
        tipo = spinnerTipoTarjeta.getSelectedItem().toString();
        return tipo;
    }

    //Metodos para el DatePicker
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "Seleccione la fecha de vencimiento", Toast.LENGTH_LONG).show();
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
        if (year < calendar.get(Calendar.YEAR)) {
            Toast.makeText(AgregarTarjetasUsuario.this, "Ingrese un año mayor al actual", Toast.LENGTH_LONG).show();
        } else {
            txt_fecha_vcto_tarjeta.setText(new StringBuilder()
                    .append(month).append("/").append(year));
        }
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

    private void ejecutarLista() {

        try {
            AgregarTarjetasUsuario.ListadoEmpresas listadoEmpresas = new AgregarTarjetasUsuario.ListadoEmpresas();
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

    private void ejecutarListaTarjetasBin() {

        try {
            AgregarTarjetasUsuario.ListadoTarjetaBin listadoEmpresas = new AgregarTarjetasUsuario.ListadoTarjetaBin();
            listadoEmpresas.execute();
        } catch (Exception e) {
            //listadoBeneficiario = null;
        }

    }

    private class ListadoTarjetaBin extends AsyncTask<String, Void, Void> {
        String tarjeta1 = nroTarjetaDigito1.getText().toString();
        String tarjeta2 = nroTarjetaDigito2.getText().toString();
        String tarjeta3 = nroTarjetaDigito3.getText().toString();
        String tarjeta4 = nroTarjetaDigito4.getText().toString();
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
            if (getTarjetaBinAdapter.getItem(0).getMarca().equals("Mastercard ")){
                if (getTarjetaBinAdapter.getItem(0).getTipo_tarjeta().equals("Crédito")){
                    spinnerTipoTarjeta.setSelection(1);
                    rdbtn_mc_option.setChecked(true);
                } else if (getTarjetaBinAdapter.getItem(0).getTipo_tarjeta().equals("Debito")){
                    spinnerTipoTarjeta.setSelection(0);
                    rdbtn_mc_option.setChecked(true);
                } else {
                    rdbtn_mc_option.setChecked(true);
                }
            } else if (getTarjetaBinAdapter.getItem(0).getMarca().equals("AMEX ")){
                if (getTarjetaBinAdapter.getItem(0).getTipo_tarjeta().equals("Crédito")){
                    spinnerTipoTarjeta.setSelection(1);
                    rdbtn_amex_option.setChecked(true);
                } else if (getTarjetaBinAdapter.getItem(0).getTipo_tarjeta().equals("Debito")){
                    spinnerTipoTarjeta.setSelection(0);
                    rdbtn_amex_option.setChecked(true);
                } else {
                    rdbtn_amex_option.setChecked(true);
                }
            } else if (getTarjetaBinAdapter.getItem(0).getMarca().equals("VISA")){
                if (getTarjetaBinAdapter.getItem(0).getTipo_tarjeta().equals("Crédito")){
                    spinnerTipoTarjeta.setSelection(1);
                    rdbtn_visa_option.setChecked(true);
                } else if (getTarjetaBinAdapter.getItem(0).getTipo_tarjeta().equals("Debito")){
                    spinnerTipoTarjeta.setSelection(0);
                    rdbtn_visa_option.setChecked(true);
                } else {
                    rdbtn_visa_option.setChecked(true);
                }
            }
        }
    }
}
