package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class LecturaInformacionComercio extends Activity {

    private UsuarioEntity usuario;
    String cliente, cli_dni;
    private Button scan_btn,aceptar_btn;
    String cadena_scanneo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lectura_informacion_comercio);

        scan_btn = (Button) findViewById(R.id.scan_btn);
        aceptar_btn = (Button) findViewById(R.id.btn_aceptar_comercio);
        final Activity activity = this;

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");
        cliente = bundle.getString("cliente");
        cli_dni = bundle.getString("cli_dni");

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("ENFOQUE EL CÓDIGO QR A LEER");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        aceptar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LecturaInformacionComercio.this, SeleccionTarjetaCargo.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                intent.putExtra("cli_dni", cli_dni);
                intent.putExtra("cadena_scanneo", cadena_scanneo);
                startActivityForResult(intent, 0);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){

                Toast.makeText(this,"Scanneo Cancelado", Toast.LENGTH_LONG).show();
            }
            else {
                cadena_scanneo = result.getContents();
                Toast.makeText(this,"Scanneo Realizado Satisfactoriamente", Toast.LENGTH_LONG).show();
                /*Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();*/
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }


    }



}
