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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.MonedaAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.MonedaEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;

public class IngresoMontoPagoFirmaTransferencias extends Activity {

    UsuarioEntity usuario;
    String nombreBeneficiario, dni_benef, num_tarjeta, banco;
    String cliente;
    int emisor_tarjeta;
    TextView textViewNombreApellidoUsuario, tv_numero_clave_cifrada_cargo;
    Spinner spinnerMonedaPagar;
    EditText txt_moneda_pagar;
    ImageView imageView;
    Button btn_continuar_pago, btn_cancelar_pago;
    MonedaAdapter monedaAdapter;
    ArrayList<MonedaEntity> monedaEntityArrayList;
    String tipo_moneda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingreso_monto_pago_firma_transferencias);

        btn_continuar_pago = (Button) findViewById(R.id.btn_continuar_pago);
        btn_cancelar_pago = (Button) findViewById(R.id.btn_cancelar_pago);

        spinnerMonedaPagar = (Spinner) findViewById(R.id.spinnerMonedaPagar);

        txt_moneda_pagar = (EditText) findViewById(R.id.txt_moneda_pagar);

        imageView = (ImageView) findViewById(R.id.imageView);

        textViewNombreApellidoUsuario = (TextView) findViewById(R.id.textViewNombreApellidoUsuario);
        tv_numero_clave_cifrada_cargo = (TextView) findViewById(R.id.tv_numero_clave_cifrada_cargo);

        Bundle extras = getIntent().getExtras();
        usuario = extras.getParcelable("usuario");
        nombreBeneficiario = extras.getString("nombrebenef");
        dni_benef = extras.getString("dni_benef");
        num_tarjeta = extras.getString("num_tarjeta");
        emisor_tarjeta = extras.getInt("emisor_tarjeta");
        banco = extras.getString("banco");
        cliente = extras.getString("cliente");

        textViewNombreApellidoUsuario.setText(cliente);
        tv_numero_clave_cifrada_cargo.setText(num_tarjeta);

        focTipoTarjeta();

        monedaEntityArrayList = null;
        monedaAdapter = new MonedaAdapter(monedaEntityArrayList, getApplication());
        spinnerMonedaPagar.setAdapter(monedaAdapter);

        ejecutarLista();

        btn_continuar_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngresoMontoPagoFirmaTransferencias.this, IngresoCuentaTarjetaAbono.class);
                intent.putExtra("monto", ObtenerMonto());
                intent.putExtra("usuario", usuario);
                intent.putExtra("tipomoneda", tipo_moneda);
                intent.putExtra("nombrebenef", nombreBeneficiario);
                intent.putExtra("dni_benef", dni_benef);
                intent.putExtra("banco", banco);
                intent.putExtra("num_tarjeta", num_tarjeta);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });

        spinnerMonedaPagar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipo_moneda = monedaAdapter.getItem(position).getSigno_moneda();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public String ObtenerMonto(){
        String monto;
        monto = txt_moneda_pagar.getText().toString();
        return monto;
    }

    public void cancelar() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Esta seguro que desea cacelar la transacción?");
        alertDialog.setTitle("Cancelar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(IngresoMontoPagoFirmaTransferencias.this, MenuCliente.class);
                intent.putExtra("usuario", usuario);
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

    public void focTipoTarjeta(){
        if (emisor_tarjeta == 1) {
            imageView.setImageResource(R.drawable.visaicon);
        } else if (emisor_tarjeta == 2) {
            imageView.setImageResource(R.drawable.mastercardlogo);
        } else {
            imageView.setImageResource(R.drawable.americanexpressicon);
        }
    }

    private void ejecutarLista() {

        try {
            IngresoMontoPagoFirmaTransferencias.ListadoMoneda listadoBeneficiario = new IngresoMontoPagoFirmaTransferencias.ListadoMoneda();
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
