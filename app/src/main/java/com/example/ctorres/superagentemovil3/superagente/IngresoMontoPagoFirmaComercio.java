package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.MonedaAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.MonedaEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;

public class IngresoMontoPagoFirmaComercio extends Activity {


    Button btn_continuar_pago, btn_cancelar_pago;
    String cliente, tarjeta_cargo, banco, emisor_tarjeta;
    TextView tv_nombre_cliente_comercio, tv_tarjeta_cifrada_comercio;
    EditText txt_monto_pago_comercio;
    private UsuarioEntity usuario;
    Spinner spinnerTipoMoneda;
    MonedaAdapter monedaAdapter;
    ArrayList<MonedaEntity> monedaEntityArrayList;
    String tipo_moneda, cli_dni;
    int tipo_tarjeta_pago;


    //Variables Proceso QR
    String cadena_scanneo;
    TextView tv_razonsoc_comercio,tv_direccion_comercio,tv_distrito_comercio;
    String parteRazon="";
    String parteDireccion="";
    String parteDistrito="";

    //Variables Proceso ComboBox
    String nom_comerciosp,direccion_comerciosp,distrito_comerciosp;


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






        String[] parts = cadena_scanneo.split("-");
        parteRazon = parts[0];
        parteDireccion = parts[1];
        parteDistrito = parts[2];

        tv_nombre_cliente_comercio.setText(cliente);
        tv_tarjeta_cifrada_comercio.setText(tarjeta_cargo);

        tv_razonsoc_comercio.setText(parteRazon);
        tv_direccion_comercio.setText(parteDireccion);
        tv_distrito_comercio.setText(parteDistrito);


        btn_continuar_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                intent.putExtra("parteRazon",parteRazon);
                intent.putExtra("parteDireccion",parteDireccion);
                intent.putExtra("parteDistrito",parteDistrito);
                startActivity(intent);
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
}
