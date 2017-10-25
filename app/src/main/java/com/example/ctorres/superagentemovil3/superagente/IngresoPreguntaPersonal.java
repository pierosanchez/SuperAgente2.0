package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

public class IngresoPreguntaPersonal extends Activity {

    EditText txt_pregunta, txt_respuesta, txt_confirm_respuesta;
    Button btn_regresar, btn_aceptar;
    private UsuarioEntity usuario;
    String clave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingreso_pregunta_personal);

        txt_pregunta = (EditText) findViewById(R.id.txt_pregunta);
        txt_respuesta = (EditText) findViewById(R.id.txt_respuesta);
        txt_confirm_respuesta = (EditText) findViewById(R.id.txt_confirma_respuesta);

        btn_aceptar = (Button) findViewById(R.id.btn_aceptar);
        btn_regresar = (Button) findViewById(R.id.btn_regresar);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");
        clave = bundle.getString("clave");

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resp = txt_respuesta.getText().toString();
                String conf_resp = txt_confirm_respuesta.getText().toString();
                String preg = txt_pregunta.getText().toString();
                if (!conf_resp.equals("") && !resp.equals("") && !preg.equals("") ) {
                    if(resp.equals(conf_resp)) {
                        IngresoPreguntaPersonal.validarPregunta pregunta = new IngresoPreguntaPersonal.validarPregunta();
                        pregunta.execute();

                        Intent intent = new Intent(IngresoPreguntaPersonal.this, InformacionTarjeta.class);
                        intent.putExtra("usuario", usuario);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(IngresoPreguntaPersonal.this, "Las respuestas no coinciden", Toast.LENGTH_SHORT).show();
                    }
                } else if (conf_resp.equals("")){
                    Toast.makeText(IngresoPreguntaPersonal.this, "Ingrese la respuesta de confirmación", Toast.LENGTH_SHORT).show();
                } else if (preg.equals("")){
                    Toast.makeText(IngresoPreguntaPersonal.this, "Ingrese su pregunta personal", Toast.LENGTH_SHORT).show();
                } else if (resp.equals("")){
                    Toast.makeText(IngresoPreguntaPersonal.this, "Ingrese su respuesta", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngresoPreguntaPersonal.this, ClaveAcceso.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
            }
        });
    }

    private class validarPregunta extends AsyncTask<String,Void,UsuarioEntity> {
        String respuesta = txt_respuesta.getText().toString();
        String pregunta = txt_pregunta.getText().toString();
        String confirmpreg = txt_confirm_respuesta.getText().toString();

        @Override
        protected UsuarioEntity doInBackground(String... params) {
            UsuarioEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.getClaveAcceso(usuario.getUsuarioId(), clave, pregunta, respuesta);
                Log.e("idCliente", "CodCliente=" + user.getCodCliente() + ", usuarioId=" + usuario.getUsuarioId());
                //usuario.setClaveAcceso(user.getClaveAcceso());
            } catch (Exception e) {
                user = null;
                //flag_clic_ingreso = 0;;
            }
            return user;
        }
    }

}
