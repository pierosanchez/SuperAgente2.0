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

public class IngresoMontoPagoPinConsumos extends Activity {

    String cliente, tarjeta_cargo, banco, emisor_tarjeta;
    Button btn_continuar_pago, btn_cancelar_pago;
    private UsuarioEntity usuario;
    TextView tv_nombre_cliente_consumo, tv_tarjeta_cifrada_consumos;
    MonedaAdapter monedaAdapter;
    ComercioQRAdapter comercioQRAdapter;
    ArrayList<ComercioEntity> comercioEntityArrayList;
    ArrayList<MonedaEntity> monedaEntityArrayList;
    Spinner spinnerTipoMoneda, sp_pago_cuotas, sp_cantidad_cuotas;
    String tipo_moneda, cli_dni, validacion_tarjeta;
    EditText txt_monto_pago_consumo, txt_pin_pago_consumo;
    int tipo_tarjeta_pago;
    String direccion_comerciosp, distrito_comerciosp, nom_comerciosp, cadena_scanneo;
    String[] cuotas = {"No", "Si"};
    String[] cantidadCuotas = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" ,"21" ,"22" ,"23" ,"24" ,"25" ,"26" ,"27" ,"28" ,"29" ,"30" ,"31" ,"32", "33", "34", "35" ,"36"};
    String parteRazon="";
    String parteDireccion="";
    String parteDistrito="";
    String id_com;
    LinearLayout ll_cantidad_cuotas;
    TextView tv_razonsoc_comercio,tv_direccion_comercio,tv_distrito_comercio, tv_pago_cuotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingreso_monto_pago_pin_consumos);

        btn_continuar_pago = (Button) findViewById(R.id.btn_continuar_pago);
        btn_cancelar_pago = (Button) findViewById(R.id.btn_cancelar_pago);

        tv_tarjeta_cifrada_consumos = (TextView) findViewById(R.id.tv_tarjeta_cifrada_consumos);
        tv_nombre_cliente_consumo = (TextView) findViewById(R.id.tv_nombre_cliente_consumo);
        tv_razonsoc_comercio = (TextView) findViewById(R.id.tv_razonsoc_comercio);
        tv_direccion_comercio = (TextView) findViewById(R.id.tv_direccion_comercio);
        tv_distrito_comercio = (TextView) findViewById(R.id.tv_distrito_comercio);
        tv_pago_cuotas = (TextView) findViewById(R.id.tv_pago_cuotas);

        txt_monto_pago_consumo = (EditText) findViewById(R.id.txt_monto_pago_consumo);
        txt_pin_pago_consumo = (EditText) findViewById(R.id.txt_pin_pago_consumo);

        spinnerTipoMoneda = (Spinner) findViewById(R.id.spinnerTipoMoneda);
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

        nom_comerciosp = extras.getString("nom_comerciosp");
        direccion_comerciosp = extras.getString("direccion_comerciosp");
        distrito_comerciosp = extras.getString("distrito_comerciosp");
        validacion_tarjeta = extras.getString("validacion_tarjeta");
        txt_monto_pago_consumo.setFilters(new InputFilter[] {new MoneyValueFilter()});

        /*String[] parts = cadena_scanneo.split("-");
        parteRazon = parts[0];
        parteDireccion = parts[1];
        parteDistrito = parts[2];*/

        cargarCuotas();
        deseaCuotas();

        if (tipo_tarjeta_pago == 2){
            sp_pago_cuotas.setVisibility(View.GONE);
            tv_pago_cuotas.setVisibility(View.GONE);
        }

        tv_nombre_cliente_consumo.setText(cliente);
        tv_tarjeta_cifrada_consumos.setText(tarjeta_cargo);

        /*tv_razonsoc_comercio.setText(parteRazon);
        tv_direccion_comercio.setText(parteDireccion);
        tv_distrito_comercio.setText(parteDistrito);*/

        txt_pin_pago_consumo.requestFocus();

        monedaEntityArrayList = null;
        monedaAdapter = new MonedaAdapter(monedaEntityArrayList, getApplication());
        spinnerTipoMoneda.setAdapter(monedaAdapter);

        ejecutarLista();

        comercioEntityArrayList = null;
        comercioQRAdapter = new ComercioQRAdapter(comercioEntityArrayList, getApplication());

        ejecutarListaDetalleComercio();

        spinnerTipoMoneda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipo_moneda = monedaAdapter.getItem(position).getSigno_moneda();
                txt_monto_pago_consumo.requestFocus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

        btn_continuar_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pin_consumo = txt_pin_pago_consumo.getText().toString();
                String monto_pagar = txt_monto_pago_consumo.getText().toString();
                if (pin_consumo.equals("") && monto_pagar.equals("")) {
                    Toast.makeText(IngresoMontoPagoPinConsumos.this, "Ingrese el monto a pagar y su clave PIN", Toast.LENGTH_LONG).show();
                } else if (pin_consumo.equals("")) {
                    Toast.makeText(IngresoMontoPagoPinConsumos.this, "Ingrese el numero de pin", Toast.LENGTH_LONG).show();
                } else if (monto_pagar.equals("")) {
                    Toast.makeText(IngresoMontoPagoPinConsumos.this, "Ingrese el monto a pagar", Toast.LENGTH_LONG).show();
                } else if (!pin_consumo.equals("") && !monto_pagar.equals("")) {
                    Intent intent = new Intent(IngresoMontoPagoPinConsumos.this, ConformidadComercioComercios.class);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("tipo_moneda", tipo_moneda);
                    intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                    intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                    intent.putExtra("monto_pagar", txt_monto_pago_consumo.getText().toString());
                    intent.putExtra("banco", banco);
                    intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                    intent.putExtra("cli_dni", cli_dni);
                    intent.putExtra("parteRazon", nom_comerciosp);
                    intent.putExtra("parteDireccion", direccion_comerciosp);
                    intent.putExtra("parteDistrito", distrito_comerciosp);
                    intent.putExtra("id_com", id_com);
                    intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                    startActivity(intent);
                }
            }
        });

        btn_cancelar_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });
    }

    private void ejecutarLista() {

        try {
            IngresoMontoPagoPinConsumos.ListadoMoneda listadoBeneficiario = new IngresoMontoPagoPinConsumos.ListadoMoneda();
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

    public void cancelar() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Está seguro que desea cacelar la transacción?");
        alertDialog.setTitle("Cancelar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(IngresoMontoPagoPinConsumos.this, MenuCliente.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                intent.putExtra("cli_dni", cli_dni);
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

    public void cargarCuotas(){
        ArrayAdapter<String> cantidadCuota = new ArrayAdapter<String>(IngresoMontoPagoPinConsumos.this, android.R.layout.simple_spinner_dropdown_item, cantidadCuotas);
        sp_cantidad_cuotas.setAdapter(cantidadCuota);
    }

    public void deseaCuotas(){
        ArrayAdapter<String> cuota = new ArrayAdapter<String>(IngresoMontoPagoPinConsumos.this, android.R.layout.simple_spinner_dropdown_item, cuotas);
        sp_pago_cuotas.setAdapter(cuota);
    }

    private void ejecutarListaDetalleComercio() {

        try {
            IngresoMontoPagoPinConsumos.DetalleComercio listadoBeneficiario = new IngresoMontoPagoPinConsumos.DetalleComercio();
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
