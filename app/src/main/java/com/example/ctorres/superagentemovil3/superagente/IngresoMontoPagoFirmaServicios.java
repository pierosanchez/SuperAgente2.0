package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.adapter.BancosAdapter;
import com.example.ctorres.superagentemovil3.adapter.CuotasAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.CuotasEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;
import java.util.List;

public class IngresoMontoPagoFirmaServicios extends Activity {

    String num_tarjeta, banco_tarjeta, tipo_moneda_deuda, monto_servicio, servicio, num_servicio, cliente, tipo_servicio, cli_dni, nombre_recibo, validacion_tarjeta;
    private UsuarioEntity usuario;
    int emisor_tarjeta, tipo_tarjeta_pago, cod_banco;
    Button btn_continuar_pago, btn_cancelar_pago;
    TextView tv_tarjeta_cifrada_pago_servicios, tv_nombre_cliente_pago_servicios, txt_servicio_pagar, tv_tipo_moneda_deuda, tv_tipo_servicio, tv_tipo_servicio_pagar;
    EditText txt_monto_pagar;
    ImageView imageView;
    LinearLayout ll_tipo_servicio_pagar;
    Spinner sp_pago_cuotas, sp_cantidad_cuotas;
    ArrayList<CuotasEntity> cuotasEntityArrayList;
    CuotasAdapter cuotasAdapter;
    String[] cuotas = {"No", "Si"};
    String[] cantidadCuotas = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" ,"21" ,"22" ,"23" ,"24" ,"25" ,"26" ,"27" ,"28" ,"29" ,"30" ,"31" ,"32", "33", "34", "35" ,"36"};
    LinearLayout ll_cantidad_cuotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingreso_monto_pago_firma_servicios);

        ll_tipo_servicio_pagar = (LinearLayout) findViewById(R.id.ll_tipo_servicio_pagar);

        btn_continuar_pago = (Button) findViewById(R.id.btn_continuar_pago);
        btn_cancelar_pago = (Button) findViewById(R.id.btn_cancelar_pago);

        imageView = (ImageView) findViewById(R.id.imageView);

        tv_tarjeta_cifrada_pago_servicios = (TextView) findViewById(R.id.tv_tarjeta_cifrada_pago_servicios);
        tv_nombre_cliente_pago_servicios = (TextView) findViewById(R.id.tv_nombre_cliente_pago_servicios);
        txt_servicio_pagar = (TextView) findViewById(R.id.txt_servicio_pagar);
        tv_tipo_moneda_deuda = (TextView) findViewById(R.id.tv_tipo_moneda_deuda);
        tv_tipo_servicio = (TextView) findViewById(R.id.tv_tipo_servicio);

        txt_monto_pagar = (EditText) findViewById(R.id.txt_monto_pagar);

        sp_pago_cuotas = (Spinner) findViewById(R.id.sp_pago_cuotas);
        sp_cantidad_cuotas = (Spinner) findViewById(R.id.sp_cantidad_cuotas);

        ll_cantidad_cuotas = (LinearLayout) findViewById(R.id.ll_cantidad_cuotas);

        Bundle extras = getIntent().getExtras();
        usuario = extras.getParcelable("usuario");
        num_tarjeta = extras.getString("num_tarjeta");
        emisor_tarjeta = extras.getInt("emisor_tarjeta");
        tipo_tarjeta_pago = extras.getInt("tipo_tarjeta_pago");
        banco_tarjeta = extras.getString("banco_tarjeta");
        tipo_moneda_deuda = extras.getString("tipo_moneda_deuda");
        monto_servicio = extras.getString("monto_servicio");
        servicio = extras.getString("servicio");
        num_servicio = extras.getString("num_servicio");
        cod_banco = extras.getInt("cod_banco");
        cliente = extras.getString("cliente");
        tipo_servicio = extras.getString("tipo_servicio");
        cli_dni = extras.getString("cli_dni");
        nombre_recibo = extras.getString("nombre_recibo");
        validacion_tarjeta = extras.getString("validacion_tarjeta");

        tv_tarjeta_cifrada_pago_servicios.setText(num_tarjeta);
        txt_servicio_pagar.setText(servicio);
        txt_monto_pagar.setText(monto_servicio);
        tv_nombre_cliente_pago_servicios.setText(cliente);

        if (tipo_servicio == null) {
            ll_tipo_servicio_pagar.setVisibility(View.GONE);
        } else {
            tv_tipo_servicio.setText(tipo_servicio);
            ll_tipo_servicio_pagar.setVisibility(View.VISIBLE);
        }

        /*cuotasEntityArrayList = null;
        cuotasAdapter = new CuotasAdapter(cuotasEntityArrayList, getApplication());
        sp_cantidad_cuotas.setAdapter(cuotasAdapter);

        ejecutarLista();*/

        focTipoTarjeta();
        cargarCuotas();
        deseaCuotas();

        sp_pago_cuotas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getAdapter().getItem(position).equals("Si")){
                    ll_cantidad_cuotas.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_continuar_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tipo_moneda_deuda = tv_tipo_moneda_deuda.getText().toString();
                Intent intent = new Intent(IngresoMontoPagoFirmaServicios.this, ConformidadPagoServicios.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("num_tarjeta", num_tarjeta);
                intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                intent.putExtra("monto_servicio", monto_servicio);
                intent.putExtra("servicio", servicio);
                intent.putExtra("num_servicio", num_servicio);
                intent.putExtra("tipo_moneda_deuda", tipo_moneda_deuda);
                intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                intent.putExtra("cod_banco", cod_banco);
                intent.putExtra("tipo_servicio", tipo_servicio);
                intent.putExtra("cliente", cliente);
                intent.putExtra("cli_dni", cli_dni);
                intent.putExtra("nombre_recibo", nombre_recibo);
                intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                startActivity(intent);
                finish();
            }
        });

        btn_cancelar_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });
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

    public void cancelar() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Esta seguro que desea cacelar la transacción?");
        alertDialog.setTitle("Cancelar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(IngresoMontoPagoFirmaServicios.this, MenuCliente.class);
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
        ArrayAdapter<String> cantidadCuota = new ArrayAdapter<String>(IngresoMontoPagoFirmaServicios.this, android.R.layout.simple_spinner_dropdown_item, cantidadCuotas);
        sp_cantidad_cuotas.setAdapter(cantidadCuota);
    }

    public void deseaCuotas(){
        ArrayAdapter<String> cuota = new ArrayAdapter<String>(IngresoMontoPagoFirmaServicios.this, android.R.layout.simple_spinner_dropdown_item, cuotas);
        sp_pago_cuotas.setAdapter(cuota);
    }

    private void ejecutarLista() {

        try {
            IngresoMontoPagoFirmaServicios.ListadoCuotas listadoEmpresas = new IngresoMontoPagoFirmaServicios.ListadoCuotas();
            listadoEmpresas.execute();
        } catch (Exception e) {
            //listadoBeneficiario = null;
        }

    }

    private class ListadoCuotas extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                cuotasEntityArrayList = dao.ListarCuota();
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            cuotasAdapter.setNewListCuota(cuotasEntityArrayList);
            cuotasAdapter.notifyDataSetChanged();
        }
    }
}
