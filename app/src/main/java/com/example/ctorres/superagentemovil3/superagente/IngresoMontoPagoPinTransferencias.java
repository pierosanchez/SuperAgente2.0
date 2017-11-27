package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
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
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

public class IngresoMontoPagoPinTransferencias extends Activity {

    Button btn_continuar_pago, btn_cancelar_pago;
    String tipoMoneda[] = {"S/.", "US$"};
    Spinner spinnerMonedaPagar, sp_pago_cuotas, sp_cantidad_cuotas;
    EditText txt_moneda_pagar, txt_pin;
    Bitmap bmp;
    ImageView imageView;
    UsuarioEntity usuario;
    int emisor_tarjeta, tipo_tarjeta;
    TextView textViewNombreApellidoUsuario, tv_numero_clave_cifrada_cargo, tv_pago_cuotas;
    String nombreBeneficiario, dni_benef, num_tarjeta, banco;
    String cliente, cli_dni, validacion_tarjeta;
    String[] cuotas = {"No", "Si"};
    String[] cantidadCuotas = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" ,"21" ,"22" ,"23" ,"24" ,"25" ,"26" ,"27" ,"28" ,"29" ,"30" ,"31" ,"32", "33", "34", "35" ,"36"};
    LinearLayout ll_cantidad_cuotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingreso_monto_pago_pin_transferencias);

        btn_continuar_pago = (Button) findViewById(R.id.btn_continuar_pago);
        btn_cancelar_pago = (Button) findViewById(R.id.btn_cancelar_pago);

        spinnerMonedaPagar = (Spinner) findViewById(R.id.spinnerMonedaPagar);
        sp_pago_cuotas = (Spinner) findViewById(R.id.sp_pago_cuotas);
        sp_cantidad_cuotas = (Spinner) findViewById(R.id.sp_cantidad_cuotas);

        ll_cantidad_cuotas = (LinearLayout) findViewById(R.id.ll_cantidad_cuotas);

        txt_moneda_pagar = (EditText) findViewById(R.id.txt_moneda_pagar);
        txt_pin = (EditText) findViewById(R.id.txt_pin);

        imageView = (ImageView) findViewById(R.id.imageView);

        textViewNombreApellidoUsuario = (TextView) findViewById(R.id.textViewNombreApellidoUsuario);
        tv_numero_clave_cifrada_cargo = (TextView) findViewById(R.id.tv_numero_clave_cifrada_cargo);
        tv_pago_cuotas = (TextView) findViewById(R.id.tv_pago_cuotas);

        cargarComboTipoMoneda();

        Bundle extras = getIntent().getExtras();
        //bmp = (Bitmap) extras.getParcelable("imagebitmap");
        usuario = extras.getParcelable("usuario");
        nombreBeneficiario = extras.getString("nombrebenef");
        dni_benef = extras.getString("dni_benef");
        num_tarjeta = extras.getString("num_tarjeta");
        emisor_tarjeta = extras.getInt("emisor_tarjeta");
        banco = extras.getString("banco");
        cliente = extras.getString("cliente");
        tipo_tarjeta = extras.getInt("tipo_tarjeta");
        cli_dni = extras.getString("cli_dni");
        validacion_tarjeta = extras.getString("validacion_tarjeta");

        //imageView.setImageBitmap(bmp);

        textViewNombreApellidoUsuario.setText(cliente);
        tv_numero_clave_cifrada_cargo.setText(num_tarjeta);
        txt_moneda_pagar.requestFocus();

        focTipoTarjeta();
        cargarCuotas();
        deseaCuotas();

        if (tipo_tarjeta == 2){
            sp_pago_cuotas.setVisibility(View.GONE);
            tv_pago_cuotas.setVisibility(View.GONE);
        }

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
                String pin = txt_pin.getText().toString();
                String monto = txt_moneda_pagar.getText().toString();
                if (pin.length()!=0 && monto.length()!= 0) {
                    Intent intent = new Intent(IngresoMontoPagoPinTransferencias.this, IngresoCuentaTarjetaAbono.class);
                    intent.putExtra("monto", ObtenerMonto());
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("tipomoneda", ObtenerTipoMoneda());
                    intent.putExtra("nombrebenef", nombreBeneficiario);
                    intent.putExtra("dni_benef", dni_benef);
                    intent.putExtra("banco", banco);
                    intent.putExtra("num_tarjeta", num_tarjeta);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("tipo_tarjeta", tipo_tarjeta);
                    intent.putExtra("cli_dni", cli_dni);
                    intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                    startActivity(intent);
                    finish();
                } else if (pin.length()==0){
                    Toast.makeText(IngresoMontoPagoPinTransferencias.this, "INGRESE EL PIN", Toast.LENGTH_LONG).show();
                } else if (monto.length()==0){
                    Toast.makeText(IngresoMontoPagoPinTransferencias.this, "INGRESE EL MONTO A PAGAR", Toast.LENGTH_LONG).show();
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

    public void cargarComboTipoMoneda() {
        ArrayAdapter<String> adapterTipoMoneda = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tipoMoneda);
        spinnerMonedaPagar.setAdapter(adapterTipoMoneda);
    }

    public String ObtenerMonto(){
        String monto;
        monto = txt_moneda_pagar.getText().toString();
        return monto;
    }

    public String ObtenerTipoMoneda(){
        String tipomoneda;
        tipomoneda = spinnerMonedaPagar.getSelectedItem().toString();
        return tipomoneda;
    }

    public void cancelar() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Esta seguro que desea cacelar la transacción?");
        alertDialog.setTitle("Cancelar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(IngresoMontoPagoPinTransferencias.this, MenuCliente.class);
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

    public void focTipoTarjeta(){
        if (emisor_tarjeta == 1) {
            imageView.setImageResource(R.drawable.visaicon);
        } else if (emisor_tarjeta == 2) {
            imageView.setImageResource(R.drawable.mastercardlogo);
        } else {
            imageView.setImageResource(R.drawable.americanexpressicon);
        }
    }

    public void cargarCuotas(){
        ArrayAdapter<String> cantidadCuota = new ArrayAdapter<String>(IngresoMontoPagoPinTransferencias.this, android.R.layout.simple_spinner_dropdown_item, cantidadCuotas);
        sp_cantidad_cuotas.setAdapter(cantidadCuota);
    }

    public void deseaCuotas(){
        ArrayAdapter<String> cuota = new ArrayAdapter<String>(IngresoMontoPagoPinTransferencias.this, android.R.layout.simple_spinner_dropdown_item, cuotas);
        sp_pago_cuotas.setAdapter(cuota);
    }
}
