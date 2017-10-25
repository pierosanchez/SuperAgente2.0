package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.ctorres.superagentemovil3.R;

public class SeleccionMedioPago extends Activity {

    LinearLayout btn_continuar, btn_cancelar;
    String monto;
    Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_medio_pago);

        btn_continuar = (LinearLayout) findViewById(R.id.btn_continuar_medio_pago);
        btn_cancelar = (LinearLayout) findViewById(R.id.btn_cancelar_medio_pago);

        Bundle extras = getIntent().getExtras();
        monto = extras.getString("monto");
        bmp = getIntent().getParcelableExtra("imagebitmap");

        btn_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeleccionMedioPago.this, SeleccionTarjetaCargo.class);
                intent.putExtra("monto", monto);
                intent.putExtra("imagebitmap", bmp);
                startActivity(intent);
                finish();
            }
        });

    }
}
