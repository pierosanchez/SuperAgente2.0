package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.utils.Constante;
import com.example.ctorres.superagentemovil3.utils.Utils;

import com.example.ctorres.superagentemovil3.R;

/**
 * Created by Administrador on 17/04/2017.
 */

public class TerminosCondiciones extends Activity {

    private RadioButton rdbtn_acepta_condiciones;
    private Button btn_aceptar, btnRegresar;
    String numCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terminos_condiciones);

        rdbtn_acepta_condiciones = (RadioButton) findViewById(R.id.radioTerminosCondiciones);
        //btn_aceptar = (Button) findViewById(R.id.btnAceptar) ;
        btnRegresar = (Button) findViewById(R.id.btnRegresar) ;

        Bundle bundle = getIntent().getExtras();
        numCliente = bundle.getString("movil");

        rdbtn_acepta_condiciones.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //btn_aceptar.setEnabled(true);
                Intent intent = new Intent(TerminosCondiciones.this, InformacionPersonalActivity.class);
                intent.putExtra("movil", numCliente);
                startActivity(intent);
                finish();
            }
        });

        /*btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TerminosCondiciones.this, InformacionPersonalActivity.class);
                startActivity(intent);
                finish();
            }
        });*/

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TerminosCondiciones.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }



}
