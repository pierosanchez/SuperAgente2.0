package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.adapter.ComercioQRAdapter;
import com.example.ctorres.superagentemovil3.adapter.MonedaAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.ComercioEntity;
import com.example.ctorres.superagentemovil3.entity.MonedaEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.utils.MoneyValueFilter;

import java.util.ArrayList;

public class IngresoMontoPagoFirmaComercio extends Activity {


    Button btn_continuar_pago, btn_cancelar_pago;
    String cliente, tarjeta_cargo, banco, emisor_tarjeta, validacion_tarjeta;
    TextView tv_nombre_cliente_comercio, tv_tarjeta_cifrada_comercio;
    EditText txt_monto_pago_comercio;
    private UsuarioEntity usuario;
    Spinner spinnerTipoMoneda, sp_pago_cuotas, sp_cantidad_cuotas;
    MonedaAdapter monedaAdapter;
    ComercioQRAdapter comercioQRAdapter;
    ArrayList<ComercioEntity> comercioEntityArrayList;
    ArrayList<MonedaEntity> monedaEntityArrayList;
    String tipo_moneda, cli_dni;
    int tipo_tarjeta_pago;
    String[] cuotas = {"No", "Si"};
    String[] cantidadCuotas = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" ,"21" ,"22" ,"23" ,"24" ,"25" ,"26" ,"27" ,"28" ,"29" ,"30" ,"31" ,"32", "33", "34", "35" ,"36"};
    LinearLayout ll_cantidad_cuotas;

    //Variables Proceso QR
    String cadena_scanneo;
    TextView tv_razonsoc_comercio,tv_direccion_comercio,tv_distrito_comercio;
    String parteRazon="";
    String parteDireccion="";
    String parteDistrito="";

    //Variables Proceso ComboBox
    String nom_comerciosp,direccion_comerciosp,distrito_comerciosp, id_com;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingreso_monto_pago_firma_comercio);

        btn_continuar_pago = (Button) findViewById(R.id.btn_continuar_pago_comer);
        btn_cancelar_pago = (Button) findViewById(R.id.btn_cancelar_pago_comer);

        tv_tarjeta_cifrada_comercio = (TextView) findViewById(R.id.tv_tarjeta_cifrada_comercio);
        tv_nombre_cliente_comercio = (TextView) findViewById(R.id.tv_nombre_cliente_comercio);
        tv_razonsoc_comercio = (TextView) findViewById(R.id.tv_razonsoc_comercio);
        tv_direccion_comercio = (TextView) findViewById(R.id.tv_direccion_comercio);
        tv_distrito_comercio = (TextView) findViewById(R.id.tv_distrito_comercio);

        txt_monto_pago_comercio = (EditText) findViewById(R.id.txt_monto_pago_comercio);

        spinnerTipoMoneda = (Spinner) findViewById(R.id.sp_monedaComercio);
        sp_pago_cuotas = (Spinner) findViewById(R.id.sp_pago_cuotas);
        sp_cantidad_cuotas = (Spinner) findViewById(R.id.sp_cantidad_cuotas);

        ll_cantidad_cuotas = (LinearLayout) findViewById(R.id.ll_cantidad_cuotas);


        Bundle extras = getIntent().getExtras();
        usuario = extras.getParcelable("usuario");
        cliente = extras.getString("cliente");
        tarjeta_cargo = extras.getString("tarjeta_cargo");
        emisor_tarjeta = extras.getString("emisor_tarjeta");
        banco = extras.getString("banco");
        tipo_tarjeta_pago = extras.getInt("tipo_tarjeta_pago");
        cli_dni = extras.getString("cli_dni");


        cadena_scanneo = extras.getString("cadena_scanneo");

        /*nom_comerciosp = extras.getString("nom_comerciosp");
        direccion_comerciosp = extras.getString("direccion_comerciosp");
        distrito_comerciosp = extras.getString("distrito_comerciosp");*/
        validacion_tarjeta = extras.getString("validacion_tarjeta");


        /*String[] parts = cadena_scanneo.split("-");
        parteRazon = parts[0];
        parteDireccion = parts[1];
        parteDistrito = parts[2];*/

        tv_nombre_cliente_comercio.setText(cliente);
        tv_tarjeta_cifrada_comercio.setText(tarjeta_cargo);
        txt_monto_pago_comercio.setFilters(new InputFilter[] {new MoneyValueFilter()});

        /*tv_razonsoc_comercio.setText(parteRazon);
        tv_direccion_comercio.setText(parteDireccion);
        tv_distrito_comercio.setText(parteDistrito);*/

        comercioEntityArrayList = null;
        comercioQRAdapter = new ComercioQRAdapter(comercioEntityArrayList, getApplication());

        ejecutarListaDetalleComercio();

        cargarCuotas();
        deseaCuotas();

        sp_pago_cuotas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getAdapter().getItem(position).equals("Si")){
                    ll_cantidad_cuotas.setVisibility(View.VISIBLE);
                } else if (parent.getAdapter().getItem(position).equals("No")){
                    ll_cantidad_cuotas.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_cancelar_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });


        btn_continuar_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String montoPagar = txt_monto_pago_comercio.getText().toString();
                if (montoPagar.length() == 0){
                    Toast.makeText(IngresoMontoPagoFirmaComercio.this, "Por favor, ingrese el monto a pagar", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(IngresoMontoPagoFirmaComercio.this, ConformidadComercioComercios.class);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                    intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                    intent.putExtra("monto_pagar", txt_monto_pago_comercio.getText().toString());
                    intent.putExtra("tipo_moneda", tipo_moneda);
                    intent.putExtra("banco", banco);
                    intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                    intent.putExtra("cli_dni", cli_dni);
                    intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                    intent.putExtra("parteRazon", nom_comerciosp);
                    intent.putExtra("parteDireccion", direccion_comerciosp);
                    intent.putExtra("parteDistrito", distrito_comerciosp);
                    intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                    intent.putExtra("id_com", id_com);
                    startActivity(intent);
                    finish();
                }
            }
        });

        monedaEntityArrayList = null;
        monedaAdapter = new MonedaAdapter(monedaEntityArrayList, getApplication());
        spinnerTipoMoneda.setAdapter(monedaAdapter);

        ejecutarLista();

        spinnerTipoMoneda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipo_moneda = monedaAdapter.getItem(position).getSigno_moneda();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void ejecutarLista() {

        try {
            IngresoMontoPagoFirmaComercio.ListadoMoneda listadoBeneficiario = new IngresoMontoPagoFirmaComercio.ListadoMoneda();
            listadoBeneficiario.execute();
        } catch (Exception e) {
            //listadoBeneficiario = null;
        }

    }

    private class ListadoMoneda extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                monedaEntityArrayList = dao.ListarMoneda();
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
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

    public void cargarCuotas(){
        ArrayAdapter<String> cantidadCuota = new ArrayAdapter<String>(IngresoMontoPagoFirmaComercio.this, android.R.layout.simple_spinner_dropdown_item, cantidadCuotas);
        sp_cantidad_cuotas.setAdapter(cantidadCuota);
    }

    public void deseaCuotas(){
        ArrayAdapter<String> cuota = new ArrayAdapter<String>(IngresoMontoPagoFirmaComercio.this, android.R.layout.simple_spinner_dropdown_item, cuotas);
        sp_pago_cuotas.setAdapter(cuota);
    }

    public void cancelar() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Está seguro que desea cacelar la transacción?");
        alertDialog.setTitle("Cancelar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(IngresoMontoPagoFirmaComercio.this, MenuCliente.class);
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

    private void ejecutarListaDetalleComercio() {

        try {
            IngresoMontoPagoFirmaComercio.DetalleComercio listadoBeneficiario = new IngresoMontoPagoFirmaComercio.DetalleComercio();
            listadoBeneficiario.execute();
        } catch (Exception e) {
            //listadoBeneficiario = null;
        }

    }

    private class DetalleComercio extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                comercioEntityArrayList = dao.detalleComercio(cadena_scanneo);
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            comercioQRAdapter.setNewListDetalleComercio(comercioEntityArrayList);
            comercioQRAdapter.notifyDataSetChanged();
            tv_razonsoc_comercio.setText(comercioEntityArrayList.get(0).getRaz_social_comercio());
            tv_direccion_comercio.setText(comercioEntityArrayList.get(0).getDireccion_comercio());
            tv_distrito_comercio.setText(comercioEntityArrayList.get(0).getDesc_distrito());
            nom_comerciosp = comercioEntityArrayList.get(0).getRaz_social_comercio();
            direccion_comerciosp = comercioEntityArrayList.get(0).getDireccion_comercio();
            distrito_comerciosp = comercioEntityArrayList.get(0).getDesc_distrito();
            id_com = comercioEntityArrayList.get(0).getId_comercio();
        }
    }
}
