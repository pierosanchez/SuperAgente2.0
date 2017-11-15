package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

public class IngresoMontoPagoPinRecargas extends Activity {

    String nro_telefono, tipo_moneda_recarga, tipo_operador;
    String tipo_moneda, cli_dni;
    String cliente, tarjeta_cargo, banco, emisor_tarjeta, validacion_tarjeta;
    private UsuarioEntity usuario;
    int tipo_tarjeta_pago;
    Double monto_recarga;
    Button btn_continuar_pago, btn_cancelar_pago;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingreso_monto_pago_pin_recargas);

        btn_continuar_pago = (Button) findViewById(R.id.btn_continuar_pagoRecarga);

        Bundle extras = getIntent().getExtras();
        usuario = extras.getParcelable("usuario");
        cliente = extras.getString("cliente");
        tarjeta_cargo = extras.getString("tarjeta_cargo");
        emisor_tarjeta = extras.getString("emisor_tarjeta");
        banco = extras.getString("banco");
        tipo_tarjeta_pago = extras.getInt("tipo_tarjeta_pago");
        cli_dni = extras.getString("cli_dni");
        nro_telefono = extras.getString("nro_telefono");
        tipo_moneda_recarga = extras.getString("tipo_moneda_recarga");
        tipo_operador = extras.getString("tipo_operador");
        monto_recarga = extras.getDouble("monto_recarga");
        validacion_tarjeta = extras.getString("validacion_tarjeta");

        btn_continuar_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngresoMontoPagoPinRecargas.this, ConformidadClienteRecarga.class);
                intent.putExtra("cliente", cliente);
                intent.putExtra("usuario", usuario);
                intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                intent.putExtra("banco", banco);
                intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                intent.putExtra("cli_dni", cli_dni);
                intent.putExtra("nro_telefono", nro_telefono);
                intent.putExtra("tipo_moneda_recarga", tipo_moneda_recarga);
                intent.putExtra("tipo_operador", tipo_operador);
                intent.putExtra("monto_recarga", monto_recarga);
                intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                startActivity(intent);
            }
        });
    }
}
