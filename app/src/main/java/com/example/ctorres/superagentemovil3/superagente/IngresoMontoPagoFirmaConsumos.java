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
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.MonedaAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.MonedaEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;

public class IngresoMontoPagoFirmaConsumos extends Activity {

    String cliente, tarjeta_cargo, banco, emisor_tarjeta;
    Button btn_continuar_pago, btn_cancelar_pago;
    private UsuarioEntity usuario;
    EditText txt_monto_pago_consumo;
    TextView tv_nombre_cliente_consumo, tv_tarjeta_cifrada_consumos;
    Spinner spinnerTipoMoneda;
    MonedaAdapter monedaAdapter;
    ArrayList<MonedaEntity> monedaEntityArrayList;
    String tipo_moneda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingreso_monto_pago_firma_consumos);

        btn_continuar_pago = (Button) findViewById(R.id.btn_continuar_pago);
        btn_cancelar_pago = (Button) findViewById(R.id.btn_cancelar_pago);

        tv_tarjeta_cifrada_consumos = (TextView) findViewById(R.id.tv_tarjeta_cifrada_consumos);
        tv_nombre_cliente_consumo = (TextView) findViewById(R.id.tv_nombre_cliente_consumo);

        txt_monto_pago_consumo = (EditText) findViewById(R.id.txt_monto_pago_consumo);

        spinnerTipoMoneda = (Spinner) findViewById(R.id.spinnerTipoMoneda);

        Bundle extras = getIntent().getExtras();
        usuario = extras.getParcelable("usuario");
        cliente = extras.getString("cliente");
        tarjeta_cargo = extras.getString("tarjeta_cargo");
        emisor_tarjeta = extras.getString("emisor_tarjeta");
        banco = extras.getString("banco");

        tv_nombre_cliente_consumo.setText(cliente);
        tv_tarjeta_cifrada_consumos.setText(tarjeta_cargo);

        btn_continuar_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngresoMontoPagoFirmaConsumos.this, ConformidadComercioConsumos.class);
                intent.putExtra("cliente", cliente);
                intent.putExtra("usuario", usuario);
                intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                intent.putExtra("monto_pagar", txt_monto_pago_consumo.getText().toString());
                intent.putExtra("tipo_moneda", tipo_moneda);
                intent.putExtra("banco", banco);
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
            IngresoMontoPagoFirmaConsumos.ListadoMoneda listadoBeneficiario = new IngresoMontoPagoFirmaConsumos.ListadoMoneda();
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
