package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;

public class ListadoTarjetasPagar extends Activity {

    TextView tarjeta1, tarjeta2, tarjeta3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado_tarjetas_pagar);

        tarjeta1 = (TextView) findViewById(R.id.tv_tarjeta1);
        tarjeta2 = (TextView) findViewById(R.id.tv_tarjeta2);
        tarjeta3 = (TextView) findViewById(R.id.tv_tarjeta3);

        tarjeta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListadoTarjetasPagar.this, SeleccionModoMontoPago.class);
                startActivity(intent);
                finish();
            }
        });

        tarjeta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListadoTarjetasPagar.this, SeleccionModoMontoPago.class);
                startActivity(intent);
                finish();
            }
        });

        tarjeta3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListadoTarjetasPagar.this, SeleccionModoMontoPago.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
