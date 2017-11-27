package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.adapter.ComercioAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.ComercioEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class LecturaInformacionComercio extends Activity {

    private UsuarioEntity usuario;
    String cliente, cli_dni;
    private Button scan_btn,aceptar_btn;
    String cadena_scanneo = "";
    //Spinner sp_Comercio;
    ArrayList<ComercioEntity> comercioEntityArrayList;
    ComercioAdapter comercioAdapter;
    String nom_comerciosp, direccion_comerciosp, distrito_comerciosp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lectura_informacion_comercio);

        scan_btn = (Button) findViewById(R.id.scan_btn);
        aceptar_btn = (Button) findViewById(R.id.btn_aceptar_comercio);
        //sp_Comercio = (Spinner) findViewById(R.id.spinnerComercio);
        final Activity activity = this;

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");
        cliente = bundle.getString("cliente");
        cli_dni = bundle.getString("cli_dni");

        /*comercioEntityArrayList = null;
        comercioAdapter = new ComercioAdapter(comercioEntityArrayList, getApplication());
        sp_Comercio.setAdapter(comercioAdapter);

        ejecutarListaComercio();*/

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
                /*intent.putExtra("nom_comerciosp",nom_comerciosp);
                intent.putExtra("direccion_comerciosp",direccion_comerciosp);
                intent.putExtra("distrito_comerciosp",distrito_comerciosp);*/
                startActivityForResult(intent, 0);
                finish();
            }
        });


        /*sp_Comercio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nom_comerciosp = comercioAdapter.getItem(position).getRaz_social_comercio();
                direccion_comerciosp = comercioAdapter.getItem(position).getDireccion_comercio();
                distrito_comerciosp = comercioAdapter.getItem(position).getDesc_distrito();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/



    }

    private class ListadoComercio extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                comercioEntityArrayList = dao.ListarComercio();
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            comercioAdapter.setNewListcomercio(comercioEntityArrayList);
            comercioAdapter.notifyDataSetChanged();
        }
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


    private void ejecutarListaComercio() {

        try {
            LecturaInformacionComercio.ListadoComercio listadoComercios = new LecturaInformacionComercio.ListadoComercio();
            listadoComercios.execute();
        } catch (Exception e) {
            //listadoBeneficiario = null;
        }

    }






}
