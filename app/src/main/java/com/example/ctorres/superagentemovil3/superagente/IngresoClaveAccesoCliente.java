package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.ctorres.superagentemovil3.R;

/**
 * Created by Administrador on 17/04/2017.
 */

public class IngresoClaveAccesoCliente extends Activity {
    Button btn_entrar;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingreso_clave_acceso_cliente);

        btn_entrar = (Button) findViewById(R.id.btn_entrar_ing_cli);

        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngresoClaveAccesoCliente.this, MenuCliente.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
