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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.adapter.BancosAdapter;
import com.example.ctorres.superagentemovil3.adapter.DetalleTarjetaAdapter;
import com.example.ctorres.superagentemovil3.adapter.TipoTarjetaAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.BancosEntity;
import com.example.ctorres.superagentemovil3.entity.TipoTarjetaEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;
import java.util.Calendar;

public class ActualizarTarjeta extends Activity {

    EditText nroTarjetaDigito1, nroTarjetaDigito2, nroTarjetaDigito3, nroTarjetaDigito4;
    TextView txt_fecha_vcto_tarjeta;
    Button btn_guardar_actualizacion_tarjeta, btn_regresar_actualizacion_tarjeta;
    String id_tarjeta;
    private UsuarioEntity usuario;
    String arrayTipoTarjeta[] = {"Débito", "Crédito"};
    String arrayBancoTarjeta[] = {"Scotiabank", "BCP", "Interbank", "BBVA", "Otros"};
    String tipoValidacion[] = {"Pin", "Firma"};
    Spinner spinnerTipoTarjeta, spinnerBancoTarjeta, spinnerValidacionTarjeta;
    private Calendar calendar;
    private int year, month, day;
    int tipoTarjeta, bancoTarjeta;
    RadioButton rdbtn_visa_option, rdbtn_amex_option, rdbtn_mc_option;
    String cliente, cli_dni;
    DetalleTarjetaAdapter detalleTarjetaAdapter;
    ArrayList<UsuarioEntity> tarjetaList;
    ArrayList<TipoTarjetaEntity> tipoTarjetaEntitiesArrayList;
    ArrayList<BancosEntity> bancosEntityArrayList;
    BancosAdapter bancosAdapter;
    TipoTarjetaAdapter tipoTarjetaAdapter;
    LinearLayout ll_validacion_tarjeta;
    String validaModi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_tarjeta);

        nroTarjetaDigito1 = (EditText) findViewById(R.id.nroTarjetaDigito1);
        nroTarjetaDigito2 = (EditText) findViewById(R.id.nroTarjetaDigito2);
        nroTarjetaDigito3 = (EditText) findViewById(R.id.nroTarjetaDigito3);
        nroTarjetaDigito4 = (EditText) findViewById(R.id.nroTarjetaDigito4);

        txt_fecha_vcto_tarjeta = (TextView) findViewById(R.id.txt_fecha_vcto_tarjeta);

        spinnerTipoTarjeta = (Spinner) findViewById(R.id.spinnerTipoTarjeta);
        spinnerBancoTarjeta = (Spinner) findViewById(R.id.spinnerBancoTarjeta);
        spinnerValidacionTarjeta = (Spinner) findViewById(R.id.spinnerValidacionTarjeta);

        rdbtn_visa_option = (RadioButton) findViewById(R.id.rdbtn_visa_option);
        rdbtn_amex_option = (RadioButton) findViewById(R.id.rdbtn_amex_option);
        rdbtn_mc_option = (RadioButton) findViewById(R.id.rdbtn_mc_option);

        btn_guardar_actualizacion_tarjeta = (Button) findViewById(R.id.btn_guardar_actualizacion_tarjeta);
        btn_regresar_actualizacion_tarjeta = (Button) findViewById(R.id.btn_regresar_actualizacion_tarjeta);

        ll_validacion_tarjeta = (LinearLayout) findViewById(R.id.ll_validacion_tarjeta);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1);

        numeroTarjeta();
        //confirmarNumeroTarjeta();
        //cargarTipoTarjeta();
        //cargarBancoTarjeta();
        cargarBancoTarjeta();

        Bundle bundle = getIntent().getExtras();
        id_tarjeta = bundle.getString("id_tarjeta");
        usuario = bundle.getParcelable("usuario");
        cliente = bundle.getString("cliente");
        cli_dni = bundle.getString("cli_dni");

        tipoTarjetaEntitiesArrayList = null;
        tipoTarjetaAdapter = new TipoTarjetaAdapter(tipoTarjetaEntitiesArrayList, getApplication());
        spinnerTipoTarjeta.setAdapter(tipoTarjetaAdapter);

        ejecutarListaTipoTarjeta();

        bancosEntityArrayList = null;
        bancosAdapter = new BancosAdapter(bancosEntityArrayList, getApplication());
        spinnerBancoTarjeta.setAdapter(bancosAdapter);

        ejecutarListaBancos();

        tarjetaList = null;
        detalleTarjetaAdapter = new DetalleTarjetaAdapter(tarjetaList, getApplication());

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

        spinnerTipoTarjeta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoTarjeta = tipoTarjetaAdapter.getItem(position).getCodTipoTarjeta();
                if (tipoTarjetaAdapter.getItem(position).getCodTipoTarjeta() == 1){
                    ll_validacion_tarjeta.setVisibility(View.VISIBLE);
                } else {
                    ll_validacion_tarjeta.setVisibility(View.GONE);
                    spinnerValidacionTarjeta.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerBancoTarjeta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bancoTarjeta = bancosAdapter.getItem(position).getCod_banco();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_guardar_actualizacion_tarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validaModi.equals("01")){
                    Toast.makeText(ActualizarTarjeta.this, "No se puede modificar esta tarjeta, ya que es perteneciente a un banco", Toast.LENGTH_LONG).show();
                } else if (validaModi.equals("00")) {
                    ingresarTarjetas();
                    Intent intent = new Intent(ActualizarTarjeta.this, ListadoTarjetasUsuario.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("cli_dni", cli_dni);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btn_regresar_actualizacion_tarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActualizarTarjeta.this, ListadoTarjetasUsuario.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                intent.putExtra("cli_dni", cli_dni);
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

    private class actualizarTarjeta extends AsyncTask<String, Void, UsuarioEntity> {

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
                user = dao.actualizarTarjeta(id_tarjeta, venimientoTarjeta, obtenerEmisorTarjeta(), tipoTarjeta, numeroTarjeta, bancoTarjeta);
                usuario.setValidaTarjeta(user.getValidaTarjeta());

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
            ActualizarTarjeta.actualizarTarjeta validarTarjeta = new ActualizarTarjeta.actualizarTarjeta();
            validarTarjeta.execute();
        } else {
            Toast.makeText(ActualizarTarjeta.this, "Ingrese su contraseña", Toast.LENGTH_SHORT).show();
        }

        /*Intent intent = new Intent(InformacionTarjeta.this, ControlMaximoNumeroCuentas.class);
        intent.putExtra("usuario", usuario);
        startActivity(intent);
        finish();*/
    }

    public void cargarTipoTarjeta() {
        ArrayAdapter<String> adaptadorBanco = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayTipoTarjeta);
        spinnerTipoTarjeta.setAdapter(adaptadorBanco);
    }

    public void cargarTipoValidacionTarjeta() {
        ArrayAdapter<String> adaptadorTipoTarjeta = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayTipoTarjeta);
        spinnerTipoTarjeta.setAdapter(adaptadorTipoTarjeta);
    }

    public void cargarBancoTarjeta() {
        ArrayAdapter<String> adaptadorTipoValidacionTarjeta = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tipoValidacion);
        spinnerValidacionTarjeta.setAdapter(adaptadorTipoValidacionTarjeta);
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

    private void ejecutarLista() {

        try {
            ActualizarTarjeta.ListadoTarjetas listadoBeneficiario = new ActualizarTarjeta.ListadoTarjetas();
            listadoBeneficiario.execute();
        } catch (Exception e) {
            //listadoBeneficiario = null;
        }

    }

    private class ListadoTarjetas extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                tarjetaList = dao.DetalleTarjeta(id_tarjeta);
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            detalleTarjetaAdapter.setNewListTarjetaBin(tarjetaList);
            detalleTarjetaAdapter.notifyDataSetChanged();
            nroTarjetaDigito1.setText(tarjetaList.get(0).getPriParteNumTarjeta());
            nroTarjetaDigito2.setText(tarjetaList.get(0).getSegParteNumTarjeta());
            nroTarjetaDigito3.setText(tarjetaList.get(0).getTerParteNumTarjeta());
            nroTarjetaDigito4.setText(tarjetaList.get(0).getCuaParteNumTarjeta());
            if (detalleTarjetaAdapter.getItem(0).getValidaModi().equals("01")){
                validaModi = "01";
                for (int i = bancosEntityArrayList.size() - 1; i >= 0; i--) {
                    if (detalleTarjetaAdapter.getItem(0).getCodBanco() == bancosAdapter.getItem(i).getCod_banco()) {
                        spinnerBancoTarjeta.setSelection(i);
                        break;
                    }
                }
                for (int i = tipoTarjetaEntitiesArrayList.size() - 1; i >= 0; i--) {
                    if (detalleTarjetaAdapter.getItem(0).getCodTipoTarjeta() == tipoTarjetaAdapter.getItem(i).getCodTipoTarjeta()) {
                        spinnerTipoTarjeta.setSelection(i);
                        break;
                    }
                }
            } else if (detalleTarjetaAdapter.getItem(0).getValidaModi().equals("00")) {
                validaModi = "00";
                for (int i = bancosEntityArrayList.size() - 1; i >= 0; i--) {
                    if (detalleTarjetaAdapter.getItem(0).getCodBanco() == bancosAdapter.getItem(i).getCod_banco()) {
                        spinnerBancoTarjeta.setSelection(i);
                        break;
                    }
                }
                for (int i = tipoTarjetaEntitiesArrayList.size() - 1; i >= 0; i--) {
                    if (detalleTarjetaAdapter.getItem(0).getCodTipoTarjeta() == tipoTarjetaAdapter.getItem(i).getCodTipoTarjeta()) {
                        spinnerTipoTarjeta.setSelection(i);
                        break;
                    }
                }
            }
        }
    }

    private void ejecutarListaBancos() {

        try {
            ActualizarTarjeta.ListadoBancos listadoEmpresas = new ActualizarTarjeta.ListadoBancos();
            listadoEmpresas.execute();
        } catch (Exception e) {
            //listadoBeneficiario = null;
        }

    }

    private class ListadoBancos extends AsyncTask<String, Void, Void> {
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

    private void ejecutarListaTipoTarjeta() {

        try {
            ActualizarTarjeta.ListadoTipoTarjeta listadoEmpresas = new ActualizarTarjeta.ListadoTipoTarjeta();
            listadoEmpresas.execute();
        } catch (Exception e) {
            //listadoBeneficiario = null;
        }

    }

    private class ListadoTipoTarjeta extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                tipoTarjetaEntitiesArrayList = dao.ListarTipoTarjeta();
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //usuarioEntityArrayList.remove(banco = banco_tarjeta);
            tipoTarjetaAdapter.setNewListTipoTarjeta(tipoTarjetaEntitiesArrayList);
            tipoTarjetaAdapter.notifyDataSetChanged();
        }
    }
}
