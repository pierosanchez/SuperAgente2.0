package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.adapter.MonedaAdapter;
import com.example.ctorres.superagentemovil3.adapter.OperadorAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.MonedaEntity;
import com.example.ctorres.superagentemovil3.entity.OperadorEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;

public class RecargaTelefonica extends Activity {

    EditText txt_nroRecarga,txt_montoRecarga;
    Spinner sp_operadorRecarga;
    Button btn_regMenuRecarga,btn_salirRecarga, btn_siguiente;
    ArrayList<OperadorEntity> operadorEntityArrayList;
    ArrayList<MonedaEntity> monedaEntityArrayList;
    OperadorAdapter operadorAdapter;
    MonedaAdapter monedaAdapter;
    double monto_recarga;
    String tipo_operador,tipo_moneda,nro_telefono;
    private UsuarioEntity usuario;
    String cliente, cli_dni;
    TextView sp_monedaRecarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recarga_telefonica);

        txt_nroRecarga = (EditText) findViewById(R.id.tv_nroRecargar);
        txt_montoRecarga = (EditText) findViewById(R.id.tv_montoRecarga);

        sp_operadorRecarga = (Spinner) findViewById(R.id.spinner_operador);
        sp_monedaRecarga = (TextView) findViewById(R.id.spinner_tipoMoneda);

        btn_regMenuRecarga = (Button) findViewById(R.id.btn_regresarMenu);
        btn_salirRecarga = (Button) findViewById(R.id.btn_salirRecarga);
        btn_siguiente = (Button) findViewById(R.id.btn_siguiente);

        Bundle extras = getIntent().getExtras();
        usuario = extras.getParcelable("usuario");
        cliente = extras.getString("cliente");
        cli_dni = extras.getString("cli_dni");

        operadorEntityArrayList = null;
        operadorAdapter = new OperadorAdapter(operadorEntityArrayList, getApplication());
        sp_operadorRecarga.setAdapter(operadorAdapter);

        ejecutarListaOperador();

        btn_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nro_telefono = txt_nroRecarga.getText().toString();
                String recarga = txt_montoRecarga.getText().toString();
                if (nro_telefono.length() != 0 && recarga.length() != 0) {
                    monto_recarga = Double.parseDouble(recarga);
                    Intent intent = new Intent(RecargaTelefonica.this, SeleccionTarjetaCargo.class);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("nro_telefono", nro_telefono);
                    intent.putExtra("tipo_moneda", sp_monedaRecarga.getText().toString());
                    intent.putExtra("tipo_operador", tipo_operador);
                    intent.putExtra("monto_recarga", monto_recarga);
                    intent.putExtra("cli_dni", cli_dni);
                    startActivityForResult(intent, 0);
                    finish();
                } else if (nro_telefono.length() == 0 && recarga.length() == 0){
                    Toast.makeText(RecargaTelefonica.this, "Ingrese el número y el monto de recarga", Toast.LENGTH_LONG).show();
                } else if (nro_telefono.length() == 0){
                    Toast.makeText(RecargaTelefonica.this, "Ingrese el número al cual recargar", Toast.LENGTH_LONG).show();
                } else if (recarga.length() == 0){
                    Toast.makeText(RecargaTelefonica.this, "Ingrese el monto de recarga", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_regMenuRecarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irMenu();
            }
        });

        btn_salirRecarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
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
                intent.putExtra("cli_dni", cli_dni);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();
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

    public void salir(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Esta seguro que desea salir de la aplicación?");
        alertDialog.setTitle("Salir");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

}
