package com.example.ctorres.superagentemovil3.superagente;

/**
 * Created by CTORRES on 05/05/2017.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.ctorres.superagentemovil3.R;

public class AfiliacionExitosaConsulta extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afiliacion_exitosa_consulta);

        Button btn_aceptar = (Button) findViewById(R.id.btn_aceptar_af_exitosa);

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfiliacionExitosaConsulta.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

