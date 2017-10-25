package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

public class IngresoMontoPagoPinTransferencias extends Activity {

    Button btn_continuar_pago, btn_cancelar_pago;
    String tipoMoneda[] = {"S/.", "US$"};
    Spinner spinnerMonedaPagar;
    EditText txt_moneda_pagar;
    Bitmap bmp;
    ImageView imageView;
    UsuarioEntity usuario;
    int emisor_tarjeta;
    TextView textViewNombreApellidoUsuario, tv_numero_clave_cifrada_cargo;
    String nombreBeneficiario, dni_benef, num_tarjeta, banco;
    String cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingreso_monto_pago_pin_transferencias);

        btn_continuar_pago = (Button) findViewById(R.id.btn_continuar_pago);
        btn_cancelar_pago = (Button) findViewById(R.id.btn_cancelar_pago);

        spinnerMonedaPagar = (Spinner) findViewById(R.id.spinnerMonedaPagar);

        txt_moneda_pagar = (EditText) findViewById(R.id.txt_moneda_pagar);

        imageView = (ImageView) findViewById(R.id.imageView);

        textViewNombreApellidoUsuario = (TextView) findViewById(R.id.textViewNombreApellidoUsuario);
        tv_numero_clave_cifrada_cargo = (TextView) findViewById(R.id.tv_numero_clave_cifrada_cargo);

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

        //imageView.setImageBitmap(bmp);

        textViewNombreApellidoUsuario.setText(cliente);
        tv_numero_clave_cifrada_cargo.setText(num_tarjeta);

        focTipoTarjeta();

        btn_continuar_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngresoMontoPagoPinTransferencias.this, IngresoCuentaTarjetaAbono.class);
                intent.putExtra("monto", ObtenerMonto());
                intent.putExtra("usuario", usuario);
                intent.putExtra("tipomoneda", ObtenerTipoMoneda());
                intent.putExtra("nombrebenef", nombreBeneficiario);
                intent.putExtra("dni_benef", dni_benef);
                intent.putExtra("banco", banco);
                intent.putExtra("num_tarjeta", num_tarjeta);
                intent.putExtra("cliente", cliente);
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

    public void cargarComboTipoMoneda() {
        ArrayAdapter<String> adapterTipoMoneda = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipoMoneda);
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
}
