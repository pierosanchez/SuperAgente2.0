package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.dao.TarjetasUsuarioAdapter;
import com.example.ctorres.superagentemovil3.dao.UsuarioAdapter;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;

public class ConfirmacionTarjetaCargo extends Activity {

    String[] moneda = {"S/.", "US$"};
    Spinner spinnerMonedaCargo;
    LinearLayout btn_continuar_tarjeta_cargo, btn_cancelar_tarjeta_cargo;
    Button btn_fimar;
    ImageView imageView;
    Bitmap b;
    String monto, num_tarjeta, usu, tipo_moneda_deuda, tarjeta_cargo;
    Bitmap bmp;
    EditText txt_monto_tarjeta_cargo_credito;
    TextView tv_numero_clave_cifrada_cargo, tv_nombre_cliente_tarjeta_cargo, tv_tipo_moneda_deuda;
    RadioButton rdbtn_visa_option, rdbtn_amex_option, rdbtn_mc_option;
    private UsuarioEntity usuario;
    int tipo_tarjeta, emisor_tarjeta;
    ArrayList<UsuarioEntity> usuarioEntityArrayList;
    UsuarioAdapter usuarioAdapter;
    String cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmacion_tarjeta_cargo);

        //spinnerMonedaCargo = (Spinner) findViewById(R.id.spinnerMonedaCargo);
        btn_continuar_tarjeta_cargo = (LinearLayout) findViewById(R.id.btn_continuar_tarjeta_cargo);
        btn_cancelar_tarjeta_cargo = (LinearLayout) findViewById(R.id.btn_cancelar_tarjeta_cargo);

        txt_monto_tarjeta_cargo_credito = (EditText) findViewById(R.id.txt_monto_tarjeta_cargo_credito);
        //btn_fimar = (Button) findViewById(R.id.btn_firmar);
        imageView = (ImageView) findViewById(R.id.imageView);

        tv_numero_clave_cifrada_cargo = (TextView) findViewById(R.id.tv_numero_clave_cifrada_cargo);
        tv_nombre_cliente_tarjeta_cargo = (TextView) findViewById(R.id.tv_nombre_cliente_tarjeta_cargo);
        tv_tipo_moneda_deuda = (TextView) findViewById(R.id.tv_tipo_moneda_deuda);

        /*rdbtn_visa_option = (RadioButton) findViewById(R.id.rdbtn_visa_option);
        rdbtn_amex_option = (RadioButton) findViewById(R.id.rdbtn_amex_option);
        rdbtn_mc_option = (RadioButton) findViewById(R.id.rdbtn_mc_option);*/

        //cargarTipoMoneda();

        Bundle extras = getIntent().getExtras();
        monto = extras.getString("monto");
        //bmp = (Bitmap) getIntent().getParcelableExtra("imagebitmap");
        usuario = extras.getParcelable("usuario");
        num_tarjeta = extras.getString("num_tarjeta");
        tipo_tarjeta = extras.getInt("tipo_tarjeta");
        emisor_tarjeta = extras.getInt("emisor_tarjeta");
        tipo_moneda_deuda = extras.getString("tipo_moneda_deuda");
        tarjeta_cargo = extras.getString("tarjeta_cargo");
        cliente = extras.getString("cliente");

        tv_numero_clave_cifrada_cargo.setText(num_tarjeta);
        tv_tipo_moneda_deuda.setText(tipo_moneda_deuda);
        txt_monto_tarjeta_cargo_credito.setText(extras.getString("monto"));
        tv_nombre_cliente_tarjeta_cargo.setText(cliente);

        focTipoTarjeta();

        btn_continuar_tarjeta_cargo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tipo_tarjeta==1) {
                    //Bitmap bitmap = ((BitmapDrawable)firma.getDrawable()).getBitmap();
                    Intent intent = new Intent(ConfirmacionTarjetaCargo.this, VoucherPagoTarjetaConCredito.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("monto", monto);
                    intent.putExtra("tipo_moneda_deuda", tipo_moneda_deuda);
                    intent.putExtra("num_tarjeta", num_tarjeta);
                    intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                    intent.putExtra("cliente", cliente);
                    startActivity(intent);
                    finish();
                } else if (tipo_tarjeta==2) {
                    Intent intent = new Intent(ConfirmacionTarjetaCargo.this, VoucherPagoTarjeta.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("monto", monto);
                    intent.putExtra("tipo_moneda_deuda", tipo_moneda_deuda);
                    intent.putExtra("num_tarjeta", num_tarjeta);
                    intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                    intent.putExtra("cliente", cliente);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btn_cancelar_tarjeta_cargo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                cancelar();
            }
        });

    }

    /*public void cargarTipoMoneda() {
        ArrayAdapter<String> adapterMoneda = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, moneda);
        spinnerMonedaCargo.setAdapter(adapterMoneda);
    }*/

    public void focTipoTarjeta(){
        if (emisor_tarjeta == 1) {
            imageView.setImageResource(R.drawable.visaicon);
        } else if (emisor_tarjeta == 2) {
            imageView.setImageResource(R.drawable.mastercardlogo);
        } else {
            imageView.setImageResource(R.drawable.americanexpressicon);
        }
    }

    private void ejecutarLista(){
        usu = usuario.getUsuarioId();

        try {
            ConfirmacionTarjetaCargo.ListadoTarjetas listadoBeneficiario = new ConfirmacionTarjetaCargo.ListadoTarjetas();
            listadoBeneficiario.execute();
        } catch (Exception e){
            //listadoBeneficiario = null;
        }

    }

    private class ListadoTarjetas extends AsyncTask<String,Void,Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                usuarioEntityArrayList = dao.listarTarjetas(usu);
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            usuarioAdapter.setNewListBeneficiario(usuarioEntityArrayList);
            usuarioAdapter.notifyDataSetChanged();
        }
    }

    public void cancelar() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Esta seguro que desea cacelar la transacción?");
        alertDialog.setTitle("Cancelar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ConfirmacionTarjetaCargo.this, MenuCliente.class);
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
}
