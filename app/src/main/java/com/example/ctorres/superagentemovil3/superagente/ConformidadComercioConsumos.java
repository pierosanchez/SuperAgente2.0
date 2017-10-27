package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

public class ConformidadComercioConsumos extends Activity {

    Button btn_confirmar_operacion_consumo;
    private UsuarioEntity usuario;
    String cliente, tipo_moneda, tarjeta_cargo, monto_pagar, banco, emisor_tarjeta, cli_dni;
    TextView tv_nombre_cliente_consumo, tv_numero_tarjeta_consumo, tv_tipo_moneda_consumo, tv_monto_importe_consum, tv_dni_cliente_consumo;
    int tipo_tarjeta_pago;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conformidad_comercio_consumos);

        btn_confirmar_operacion_consumo = (Button) findViewById(R.id.btn_confirmar_operacion_consumo);

        tv_nombre_cliente_consumo = (TextView) findViewById(R.id.tv_nombre_cliente_consumo);
        tv_numero_tarjeta_consumo = (TextView) findViewById(R.id.tv_numero_tarjeta_consumo);
        tv_tipo_moneda_consumo = (TextView) findViewById(R.id.tv_tipo_moneda_consumo);
        tv_monto_importe_consum = (TextView) findViewById(R.id.tv_monto_importe_consum);
        tv_dni_cliente_consumo = (TextView) findViewById(R.id.tv_dni_cliente_consumo);

        Bundle extras = getIntent().getExtras();
        usuario = extras.getParcelable("usuario");
        cliente = extras.getString("cliente");
        tipo_moneda = extras.getString("tipo_moneda");
        tarjeta_cargo = extras.getString("tarjeta_cargo");
        monto_pagar = extras.getString("monto_pagar");
        emisor_tarjeta = extras.getString("emisor_tarjeta");
        banco = extras.getString("banco");
        tipo_tarjeta_pago = extras.getInt("tipo_tarjeta_pago");
        cli_dni = extras.getString("cli_dni");

        tv_nombre_cliente_consumo.setText(cliente);
        tv_numero_tarjeta_consumo.setText(tarjeta_cargo);
        tv_tipo_moneda_consumo.setText(tipo_moneda);
        tv_monto_importe_consum.setText(monto_pagar);
        tv_dni_cliente_consumo.setText(cli_dni);

        btn_confirmar_operacion_consumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tipo_tarjeta_pago == 2) {
                    Intent intent = new Intent(ConformidadComercioConsumos.this, VoucherPagoConsumo.class);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("banco", banco);
                    intent.putExtra("tipo_moneda", tipo_moneda);
                    intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                    intent.putExtra("monto_pagar", monto_pagar);
                    intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                    intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                    intent.putExtra("cli_dni", cli_dni);
                    startActivity(intent);
                } else if (tipo_tarjeta_pago == 1){
                    Intent intent = new Intent(ConformidadComercioConsumos.this, VoucherPagoConsumoFirma.class);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("banco", banco);
                    intent.putExtra("tipo_moneda", tipo_moneda);
                    intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                    intent.putExtra("monto_pagar", monto_pagar);
                    intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                    intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                    intent.putExtra("cli_dni", cli_dni);
                    startActivity(intent);
                }
            }
        });
    }
}
