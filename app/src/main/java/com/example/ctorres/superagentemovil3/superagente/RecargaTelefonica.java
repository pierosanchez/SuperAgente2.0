package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.BancosAdapter;
import com.example.ctorres.superagentemovil3.dao.MonedaAdapter;
import com.example.ctorres.superagentemovil3.dao.OperadorAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.BancosEntity;
import com.example.ctorres.superagentemovil3.entity.MonedaEntity;
import com.example.ctorres.superagentemovil3.entity.OperadorEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;

public class RecargaTelefonica extends Activity {

    EditText txt_nroRecarga,txt_montoRecarga;
    Spinner sp_operadorRecarga,sp_monedaRecarga;
    Button btn_regMenuRecarga,btn_salirRecarga, btn_siguiente;
    ArrayList<OperadorEntity> operadorEntityArrayList;
    ArrayList<MonedaEntity> monedaEntityArrayList;
    OperadorAdapter operadorAdapter;
    MonedaAdapter monedaAdapter;
    double monto_recarga;
    String tipo_operador,tipo_moneda,nro_telefono;
    private UsuarioEntity usuario;
    String cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recarga_telefonica);

        txt_nroRecarga = (EditText) findViewById(R.id.tv_nroRecargar);
        txt_montoRecarga = (EditText) findViewById(R.id.tv_montoRecarga);

        sp_operadorRecarga = (Spinner) findViewById(R.id.spinner_operador);
        sp_monedaRecarga = (Spinner) findViewById(R.id.spinner_tipoMoneda);

        btn_regMenuRecarga = (Button) findViewById(R.id.btn_regresarMenu);
        btn_salirRecarga = (Button) findViewById(R.id.btn_monto_salirRecarga);
        btn_siguiente = (Button) findViewById(R.id.btn_siguiente);



        Bundle extras = getIntent().getExtras();
        usuario = extras.getParcelable("usuario");
        cliente = extras.getString("cliente");

        operadorEntityArrayList = null;
        operadorAdapter = new OperadorAdapter(operadorEntityArrayList, getApplication());
        sp_operadorRecarga.setAdapter(operadorAdapter);

        ejecutarListaOperador();

        monedaEntityArrayList = null;
        monedaAdapter = new MonedaAdapter(monedaEntityArrayList, getApplication());
        sp_monedaRecarga.setAdapter(monedaAdapter);

        ejecutarListaMoneda();

        btn_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nro_telefono = txt_nroRecarga.getText().toString();
                monto_recarga = Double.parseDouble(txt_montoRecarga.getText().toString());

                Intent intent = new Intent(RecargaTelefonica.this, SeleccionTarjetaCargo.class);
                intent.putExtra("cliente", cliente);
                intent.putExtra("usuario", usuario);
                intent.putExtra("nro_telefono", nro_telefono);
                intent.putExtra("tipo_moneda", tipo_moneda);
                intent.putExtra("tipo_operador", tipo_operador);
                intent.putExtra("monto_recarga", monto_recarga);
                startActivityForResult(intent, 0);
                finish();
            }
        });

        btn_regMenuRecarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irMenu();
            }
        });



        sp_operadorRecarga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipo_operador = operadorAdapter.getItem(position).getOpe_nomcomercial();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_monedaRecarga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipo_moneda = monedaAdapter.getItem(position).getSigno_moneda();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }


    private class ListadoOperadores extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                operadorEntityArrayList = dao.ListarOperador();
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //usuarioEntityArrayList.remove(banco = banco_tarjeta);
            operadorAdapter.setNewListOperador(operadorEntityArrayList);
            operadorAdapter.notifyDataSetChanged();
        }
    }


    private class ListadoMoneda extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                monedaEntityArrayList = dao.ListarMoneda();
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            monedaAdapter.setNewListMoneda(monedaEntityArrayList);
            monedaAdapter.notifyDataSetChanged();
        }
    }


    public void irMenu() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Esta seguro que desea cacelar la transacción?");
        alertDialog.setTitle("Regresar al Menú");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(RecargaTelefonica.this, MenuCliente.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
            }
        });
    }




    private void ejecutarListaOperador() {

        try {
            RecargaTelefonica.ListadoOperadores listadoOperadores = new RecargaTelefonica.ListadoOperadores();
            listadoOperadores.execute();
        } catch (Exception e) {
            //listadoBeneficiario = null;
        }

    }

    private void ejecutarListaMoneda() {

        try {

            RecargaTelefonica.ListadoMoneda listadoMonedas = new RecargaTelefonica.ListadoMoneda();
            listadoMonedas.execute();
        } catch (Exception e) {
            //listadoBeneficiario = null;
        }

    }

}
