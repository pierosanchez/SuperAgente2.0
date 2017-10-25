package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

public class IngresoClaveAcceso extends Activity {

    String[] segPregunta= {"Fecha de Nacimiento", "Nombre de Mascota" ,"Equipo de Football", "Distrito donde Vive", "Numero de Pasaporte", "Otros"};
    Spinner sp_segPregunta;
    EditText txt_clave, txt_comfirm_clave, txt_seg_clave;
    Button btn_ingresar_pregunta, btn_siguiente, btnRegresar;
    private UsuarioEntity usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingreso_clave_acceso);

        sp_segPregunta = (Spinner) findViewById(R.id.spinnerSegClaveAcceso);
        txt_clave = (EditText) findViewById(R.id.txt_clave_acceso);
        txt_comfirm_clave = (EditText) findViewById(R.id.txt_confirm_clave_acceso);
        btn_ingresar_pregunta = (Button) findViewById(R.id.btn_otra_pregunta);
        btn_siguiente = (Button) findViewById(R.id.btn_siguiente);
        txt_seg_clave = (EditText) findViewById(R.id.txt_resp_seg_clave);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");

        LlenarComboBox();

        btn_ingresar_pregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String confirm_clave = txt_comfirm_clave.getText().toString();
                String clave = txt_clave.getText().toString();
                if (!clave.isEmpty()) {
                    if (clave.equals(confirm_clave)) {
                        Intent intent = new Intent(IngresoClaveAcceso.this, IngresoPreguntaPersonal.class);
                        intent.putExtra("clave", clave);
                        intent.putExtra("usuario", usuario);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(IngresoClaveAcceso.this, "Las claves de acceso deben coincidir", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(IngresoClaveAcceso.this, "Ingrese su clave de acceso", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String confirm_clave = txt_comfirm_clave.getText().toString();
                String clave = txt_clave.getText().toString();
                if (!clave.isEmpty()) {
                    //if (clave.matches(Constante.PASSWORDPATTERN)) {
                        /*if (clave.matches("[A-Z]")) {
                            if (clave.matches("[0-9]")) {*/
                    if (clave.equals(confirm_clave) && clave.length() >= 8) {
                        IngresoClaveAcceso.informacionTarjeta informTarjeta = new IngresoClaveAcceso.informacionTarjeta();
                        informTarjeta.execute();

                        Intent intent = new Intent(IngresoClaveAcceso.this, InformacionTarjeta.class);
                        intent.putExtra("usuario", usuario);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(IngresoClaveAcceso.this, "Las claves de acceso no coinciden", Toast.LENGTH_SHORT).show();
                    }
                            /*} else {
                                Toast.makeText(ClaveAcceso.this, "La contraseña debe tener como mínimo 8 digitos y debe tener número", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ClaveAcceso.this, "La contraseña debe tener como mínimo 8 digitos y debe tener mayúscular", Toast.LENGTH_SHORT).show();
                        }*/
                    /*} else {
                        Toast.makeText(ClaveAcceso.this, "La contraseña debe tener como mínimo 8 digitos y debe tener minusculas ", Toast.LENGTH_SHORT).show();
                    }*/
                } else {
                    Toast.makeText(IngresoClaveAcceso.this, "Ingrese su clave de acceso", Toast.LENGTH_SHORT).show();
                }

                /*Intent intent = new Intent(ClaveAcceso.this, InformacionTarjeta.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();*/
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngresoClaveAcceso.this, InformacionPersonalActivity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
            }
        });

        sp_segPregunta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pregunta = sp_segPregunta.getSelectedItem().toString();
                if (pregunta.equals("Nombre de Mascota") || pregunta.equals("Equipo de Football") || pregunta.equals("Distrito donde Vive")){
                    txt_seg_clave.setAllCaps(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void LlenarComboBox(){
        ArrayAdapter<String> adaptadorPreguntas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, segPregunta);
        sp_segPregunta.setAdapter(adaptadorPreguntas);
    }

    private class informacionTarjeta extends AsyncTask<String,Void,UsuarioEntity> {
        String respuesta = txt_seg_clave.getText().toString();
        String clave = txt_clave.getText().toString();
        String confirmclave = txt_comfirm_clave.getText().toString();

        @Override
        protected UsuarioEntity doInBackground(String... params) {
            UsuarioEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.getClaveAcceso(usuario.getUsuarioId(), clave, validaPregunta(), respuesta);
                Log.e("idCliente", "CodCliente=" + user.getCodCliente() + ", usuarioId=" + usuario.getUsuarioId());
                //usuario.setClaveAcceso(user.getClaveAcceso());
            } catch (Exception e) {
                user = null;
                //flag_clic_ingreso = 0;;
            }
            return user;
        }
    }

    /*public void cargarCap(){
        String pregunta = sp_segPregunta.getSelectedItem().toString();
        if (pregunta.equals("Nombre de Mascota") || pregunta.equals("Equipo de Football") || pregunta.equals("Distrito donde Vive")){
            txt_seg_clave.setAllCaps(true);
        }
    }*/

    public String validaPregunta(){
        String pregunta = "";
        pregunta = sp_segPregunta.getSelectedItem().toString();
        return pregunta;
    }
}
