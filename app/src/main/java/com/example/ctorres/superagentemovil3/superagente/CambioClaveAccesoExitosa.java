package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

public class CambioClaveAccesoExitosa extends Activity {

    private UsuarioEntity usuario;
    Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cambio_clave_acceso_exitosa);

        btn_ok = (Button) findViewById(R.id.btn_ok);

        /*Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");*/

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CambioClaveAccesoExitosa.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
