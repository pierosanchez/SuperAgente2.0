package com.example.ctorres.superagentemovil3.superagente;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.fragments.InformacionTarjetaFragment;
import com.example.ctorres.superagentemovil3.utils.Constante;

import java.util.Calendar;

public class ClaveAcceso extends Activity {

    String[] segPregunta = {"Nombre de Mascota", "Fecha de Nacimiento", "Equipo de Football", "Distrito donde Vive", "Numero de Pasaporte"};
    Spinner sp_segPregunta;
    EditText txt_clave, txt_comfirm_clave, txt_seg_clave;
    Button btn_ingresar_pregunta, btn_siguiente, btnRegresar;
    TextView tv_fecha_nac_cli;
    private UsuarioEntity usuario;
    private Calendar calendar;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clave_acceso);

        sp_segPregunta = (Spinner) findViewById(R.id.spinnerSegClaveAcceso);

        txt_seg_clave = (EditText) findViewById(R.id.txt_resp_seg_clave);
        txt_clave = (EditText) findViewById(R.id.txt_clave_acceso);
        txt_comfirm_clave = (EditText) findViewById(R.id.txt_confirm_clave_acceso);

        btn_ingresar_pregunta = (Button) findViewById(R.id.btn_otra_pregunta);
        btn_siguiente = (Button) findViewById(R.id.btn_siguiente);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);

        tv_fecha_nac_cli = (TextView) findViewById(R.id.tv_fecha_nac_cli);


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");

        LlenarComboBox();

        txt_clave.requestFocus();

        btn_ingresar_pregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String confirm_clave = txt_comfirm_clave.getText().toString();
                String clave = txt_clave.getText().toString();
                if (!clave.isEmpty()) {
                    if (clave.equals(confirm_clave)) {
                        if (clave.length() >= 8) {
                            Intent intent = new Intent(ClaveAcceso.this, IngresoPreguntaPersonal.class);
                            intent.putExtra("clave", clave);
                            intent.putExtra("usuario", usuario);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(ClaveAcceso.this, "La clave debe tener 8 caractéres como mínimo", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ClaveAcceso.this, "Las claves de acceso deben coincidir", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ClaveAcceso.this, "Ingrese su clave de acceso", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String confirm_clave = txt_comfirm_clave.getText().toString();
                String clave = txt_clave.getText().toString();
                if (!clave.isEmpty()) {
                    if (clave.equals(confirm_clave) && clave.length() >= 8) {

                        ClaveAcceso.informacionTarjeta informTarjeta = new ClaveAcceso.informacionTarjeta();
                        informTarjeta.execute();

                        Intent intent = new Intent(ClaveAcceso.this, InformacionTarjeta.class);
                        intent.putExtra("usuario", usuario);
                        startActivity(intent);
                        finish();

                    } else if (!clave.equals(confirm_clave)) {
                        Toast.makeText(ClaveAcceso.this, "Las claves de acceso no coinciden", Toast.LENGTH_SHORT).show();
                    } else if (clave.length() < 8) {
                        Toast.makeText(ClaveAcceso.this, "La clave debe tener 8 caracteres como mínimo", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ClaveAcceso.this, "Ingrese su clave de acceso", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClaveAcceso.this, InformacionPersonalActivity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
            }
        });

        sp_segPregunta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pregunta = parent.getAdapter().getItem(position).toString();
                if (pregunta.equals("Nombre de Mascota") || pregunta.equals("Equipo de Football") ||
                        pregunta.equals("Distrito donde Vive") || pregunta.equals("Numero de Pasaporte")) {
                    txt_seg_clave.setAllCaps(true);
                    tv_fecha_nac_cli.setVisibility(View.GONE);
                    txt_seg_clave.setVisibility(View.VISIBLE);
                } else if (pregunta.equals("Fecha de Nacimiento")) {
                    tv_fecha_nac_cli.setVisibility(View.VISIBLE);
                    txt_seg_clave.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tv_fecha_nac_cli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (sp_segPregunta.getSelectedItem().equals("Fecha de Nacimiento")){
                    setDate();
                    showDate(year, month + 1, day);
                }
            }
        });


    }

    private void LlenarComboBox() {
        ArrayAdapter<String> adaptadorPreguntas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, segPregunta);
        sp_segPregunta.setAdapter(adaptadorPreguntas);
    }

    private class informacionTarjeta extends AsyncTask<String, Void, UsuarioEntity> {
        String respuesta = txt_seg_clave.getText().toString();
        String clave = txt_clave.getText().toString();
        String fecha_nac = tv_fecha_nac_cli.getText().toString();
        String spinnerPreguntaSecreta = sp_segPregunta.getSelectedItem().toString();

        @Override
        protected UsuarioEntity doInBackground(String... params) {
            UsuarioEntity user;
            try {
                if (spinnerPreguntaSecreta.equals("Fecha de Nacimiento")){
                    SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                    user = dao.getClaveAcceso(usuario.getUsuarioId(), clave, validaPregunta(), fecha_nac);
                } else {
                    SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                    user = dao.getClaveAcceso(usuario.getUsuarioId(), clave, validaPregunta(), respuesta);
                }
            } catch (Exception e) {
                user = null;
                //flag_clic_ingreso = 0;
            }
            return user;
        }

        /*@Override
        protected void onPostExecute(UsuarioEntity usuarioEntity) {
            usuario = usuarioEntity;
            if (usuario.getCodCliente() != null) {
                if (!usuario.getCodCliente().equals("00")) {
                    Intent intent = new Intent(ClaveAcceso.this, InformacionTarjeta.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ClaveAcceso.this, "La clave no ha sido ingresada correctamente", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ClaveAcceso.this, "Hubo un error al momento de ingresar la clave", Toast.LENGTH_SHORT).show();
            }
        }*/
    }

    public String validaPregunta() {
        String pregunta = "";
        pregunta = sp_segPregunta.getSelectedItem().toString();
        return pregunta;
    }

    @SuppressWarnings("deprecation")
    public void setDate() {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "Seleccione la fecha de Necimiento", Toast.LENGTH_LONG)
                .show();
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
            showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        tv_fecha_nac_cli.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
}
