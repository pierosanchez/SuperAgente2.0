package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
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
import com.example.ctorres.superagentemovil3.dao.GetTarjetaBinAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.TarjetaBinEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class InformacionTarjeta extends Activity {

    EditText nroTarjetaDigito1, nroTarjetaDigito2, nroTarjetaDigito3, nroTarjetaDigito4;
    TextView txt_fecha_vcto_tarjeta;
    //private EditText confirmNroTarjetaDigito1, confirmNroTarjetaDigito2, confirmNroTarjetaDigito3, confirmNroTarjetaDigito4;
    private Button agregarTarjeta, terminar, btnRegresar, btn_validar_tarjeta;
    private View rootView;
    private String numeroTarjeta, confirmNumeroTarjeta;
    private UsuarioEntity usuario;
    Spinner spinnerTipoTarjeta, spinnerBancoTarjeta;
    String arrayTipoTarjeta[] = {"Débito", "Crédito"};
    String arrayBancoTarjeta[] = {"Scotiabank", "BCP", "Interbank", "BBVA", "Otros"};
    private Calendar calendar;
    private int year, month, day;
    RadioButton rdbtn_visa_option, rdbtn_amex_option, rdbtn_mc_option;
    GetTarjetaBinAdapter getTarjetaBinAdapter;
    ArrayList<TarjetaBinEntity> tarjetaBinEntityArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informacion_tarjeta);

        nroTarjetaDigito1 = (EditText) findViewById(R.id.nroTarjetaDigito1);
        nroTarjetaDigito2 = (EditText) findViewById(R.id.nroTarjetaDigito2);
        nroTarjetaDigito3 = (EditText) findViewById(R.id.nroTarjetaDigito3);
        nroTarjetaDigito4 = (EditText) findViewById(R.id.nroTarjetaDigito4);

        txt_fecha_vcto_tarjeta = (TextView) findViewById(R.id.txt_fecha_vcto_tarjeta);

        spinnerTipoTarjeta = (Spinner) findViewById(R.id.spinnerTipoTarjeta);
        spinnerBancoTarjeta = (Spinner) findViewById(R.id.spinnerBancoTarjeta);


        agregarTarjeta = (Button) findViewById(R.id.agregarTarjeta);
        terminar = (Button) findViewById(R.id.terminar);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        btn_validar_tarjeta = (Button) findViewById(R.id.btn_validar_tarjeta);

        rdbtn_visa_option = (RadioButton) findViewById(R.id.rdbtn_visa_option);
        rdbtn_amex_option = (RadioButton) findViewById(R.id.rdbtn_amex_option);
        rdbtn_mc_option = (RadioButton) findViewById(R.id.rdbtn_mc_option);


        //Variables para el DatePicker
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");

        numeroTarjeta();
        //confirmarNumeroTarjeta();
        cargarTipoTarjeta();
        cargarBancoTarjeta();


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

        agregarTarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tarjeta1 = nroTarjetaDigito1.getText().toString();
                String tarjeta2 = nroTarjetaDigito2.getText().toString();
                String tarjeta3 = nroTarjetaDigito3.getText().toString();
                String tarjeta4 = nroTarjetaDigito4.getText().toString();
                String numeroTarjeta = tarjeta1 + tarjeta2 + tarjeta3 + tarjeta4;

                if (!numeroTarjeta.equals("")) {
                    if (rdbtn_amex_option.isChecked() || rdbtn_mc_option.isChecked() || rdbtn_visa_option.isChecked()) {

                        InformacionTarjeta.AgregarTarjeta validarTarjeta = new InformacionTarjeta.AgregarTarjeta();
                        validarTarjeta.execute();

                        //Toast.makeText(InformacionTarjeta.this, "Tarjeta ingresada correctamente", Toast.LENGTH_SHORT).show();

                        /*nroTarjetaDigito1.setText("");
                        nroTarjetaDigito2.setText("");
                        nroTarjetaDigito3.setText("");
                        nroTarjetaDigito4.setText("");
                        rdbtn_visa_option.setChecked(false);
                        rdbtn_amex_option.setChecked(false);
                        rdbtn_mc_option.setChecked(false);
                        nroTarjetaDigito1.requestFocus();*/

                    } else {
                        Toast.makeText(InformacionTarjeta.this, "Seleccione el tipo de tarjeta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(InformacionTarjeta.this, "Ingrese el numero de tarjeta", Toast.LENGTH_SHORT).show();
                }
            }
        });

        terminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ingresarTarjetas();
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InformacionTarjeta.this, ClaveAcceso.class);
                intent.putExtra("usuario", usuario);
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
                user = dao.getValidarTarjeta(usuario.getUsuarioId(), numeroTarjeta, venimientoTarjeta, obtenerTipoTarjeta(), obtenerEmisorTarjeta(), obtenerBancoTarjeta());
                usuario.setValidaTarjeta(user.getValidaTarjeta());

            } catch (Exception e) {
                user = null;
                //flag_clic_ingreso = 0;;
            }
            return user;

        }

        @Override
        protected void onPostExecute(UsuarioEntity user) {
            usuario = user;
            if (usuario.getRespTarjeta() != null) {
                if (usuario.getRespTarjeta().equals("00")) {
                    Toast.makeText(InformacionTarjeta.this, "El número de tarjeta ya existe", Toast.LENGTH_SHORT).show();
                } else if (usuario.getRespTarjeta().equals("01")) {
                    Toast.makeText(InformacionTarjeta.this, "No puede ingresar más de 5 tarjetas", Toast.LENGTH_SHORT).show();
                } /*else if (!usuario.getRespTarjeta().equals("00") || !usuario.getRespTarjeta().equals("01")) {
                    Intent intent = new Intent(InformacionTarjeta.this, VentanaErrores.class);
                    intent.putExtra("usuario", usuario);
                    startActivityForResult(intent, 0);
                    finish();
                }*/
            } else {

            }
        }
    }

    private class AgregarTarjeta extends AsyncTask<String, Void, UsuarioEntity> {

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
                user = dao.getValidarTarjeta(usuario.getUsuarioId(), numeroTarjeta, venimientoTarjeta, obtenerTipoTarjeta(), obtenerEmisorTarjeta(), obtenerBancoTarjeta());
                usuario.setValidaTarjeta(user.getValidaTarjeta());

            } catch (Exception e) {
                user = null;
                //flag_clic_ingreso = 0;;
            }
            return user;

        }

        @Override
        protected void onPostExecute(UsuarioEntity user) {
            usuario = user;
            if (usuario.getRespTarjeta() != null) {
                if (usuario.getRespTarjeta().equals("00")) {
                    Toast.makeText(InformacionTarjeta.this, "El número de tarjeta ya existe", Toast.LENGTH_SHORT).show();
                } else if (usuario.getRespTarjeta().equals("01")) {
                    Toast.makeText(InformacionTarjeta.this, "No puede ingresar más de 5 tarjetas", Toast.LENGTH_SHORT).show();
                } else if (!usuario.getRespTarjeta().equals("00") || !usuario.getRespTarjeta().equals("01")) {
                    Toast.makeText(InformacionTarjeta.this, "Tarjeta Ingresada Correctamente", Toast.LENGTH_SHORT).show();

                    nroTarjetaDigito1.setText("");
                    nroTarjetaDigito2.setText("");
                    nroTarjetaDigito3.setText("");
                    nroTarjetaDigito4.setText("");
                    rdbtn_visa_option.setChecked(false);
                    rdbtn_amex_option.setChecked(false);
                    rdbtn_mc_option.setChecked(false);
                    nroTarjetaDigito1.requestFocus();
                }
            } else {

            }
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

        if (!numeroTarjeta.equals("")) {
            if (rdbtn_amex_option.isChecked() || rdbtn_mc_option.isChecked() || rdbtn_visa_option.isChecked()) {

                InformacionTarjeta.ValidarTarjeta validarTarjeta = new InformacionTarjeta.ValidarTarjeta();
                validarTarjeta.execute();

                Intent intent = new Intent(InformacionTarjeta.this, VentanaErrores.class);
                intent.putExtra("usuario", usuario);
                startActivityForResult(intent, 0);
                finish();

            } else {
                Toast.makeText(InformacionTarjeta.this, "Seleccione el tipo de tarjeta", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(InformacionTarjeta.this, "Ingrese el numero de su Tarjeta", Toast.LENGTH_SHORT).show();
        }
    }

    public void AbonoOCargo() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Desea ingresar sus cuentas para abono o cargo?");
        alertDialog.setTitle("Cancelar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(InformacionTarjeta.this, ControlMaximoNumeroCuentas.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BeneficiarioTransferencia();
            }
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    public void BeneficiarioTransferencia() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Desea ingresar beneficiarios de transferencia?");
        alertDialog.setTitle("Cancelar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(InformacionTarjeta.this, IngresoDatosBeneficiarios2De4.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(InformacionTarjeta.this, AfiliacionExitosaConsulta.class);
                startActivity(intent);
                finish();
            }
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    public void cargarTipoTarjeta() {
        ArrayAdapter<String> adaptadorBanco = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayTipoTarjeta);
        spinnerTipoTarjeta.setAdapter(adaptadorBanco);
    }

    public void cargarBancoTarjeta() {
        ArrayAdapter<String> adaptadorBanco = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayBancoTarjeta);
        spinnerBancoTarjeta.setAdapter(adaptadorBanco);
    }

    public String validaTipoTarjeta() {
        String tipo = "";
        tipo = spinnerTipoTarjeta.getSelectedItem().toString();
        return tipo;
    }

    //Metodos para el DatePicker
    @SuppressWarnings("deprecation")
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

    private void ejecutarListaTarjetasBin() {

        try {
            InformacionTarjeta.ListadoTarjetaBin listadoEmpresas = new InformacionTarjeta.ListadoTarjetaBin();
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
