package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.text.DecimalFormat;

public class ConformidadPagoServicios extends Activity {

    Button btn_continuar_pago;
    private UsuarioEntity usuario;
    String num_tarjeta, monto_servicio, servicio, num_servicio, tipo_moneda_deuda, cliente, tipo_servicio;
    int tipo_tarjeta, emisor_tarjeta, tipo_tarjeta_pago, cod_banco;
    TextView tv_numero_tarjeta_cifrada_pago_servicio, txt_servicio_pagar, tv_monto_servicio_pagar, tv_monto_comision_servicio_pagar, tv_monto_total_servicio_pagar, txt_tipo_moneda_servicio_pagar, tv_nro_servicio_servicio_pagar, tv_nombre_titular_pago_servicio, txt_tipo_servicio_pagar, txt_tipo_moneda_total_pagar, txt_tipo_moneda_comision_pagar;
    DecimalFormat format = new DecimalFormat("0.00");
    LinearLayout ll_tipo_servicio_pagar_conforme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conformidad_pago_servicios);

        ll_tipo_servicio_pagar_conforme = (LinearLayout) findViewById(R.id.ll_tipo_servicio_pagar_conforme);

        btn_continuar_pago = (Button) findViewById(R.id.btn_continuar_pago);

        tv_numero_tarjeta_cifrada_pago_servicio = (TextView) findViewById(R.id.tv_numero_tarjeta_cifrada_pago_servicio);
        txt_servicio_pagar = (TextView) findViewById(R.id.txt_servicio_pagar);
        tv_monto_servicio_pagar = (TextView) findViewById(R.id.tv_monto_servicio_pagar);
        tv_monto_comision_servicio_pagar = (TextView) findViewById(R.id.tv_monto_comision_servicio_pagar);
        tv_monto_total_servicio_pagar = (TextView) findViewById(R.id.tv_monto_total_servicio_pagar);
        txt_tipo_moneda_servicio_pagar = (TextView) findViewById(R.id.txt_tipo_moneda_servicio_pagar);
        tv_nro_servicio_servicio_pagar = (TextView) findViewById(R.id.tv_nro_servicio_servicio_pagar);
        tv_nombre_titular_pago_servicio = (TextView) findViewById(R.id.tv_nombre_titular_pago_servicio);
        txt_tipo_servicio_pagar = (TextView) findViewById(R.id.txt_tipo_servicio_pagar);
        txt_tipo_moneda_total_pagar = (TextView) findViewById(R.id.txt_tipo_moneda_total_pagar);
        txt_tipo_moneda_comision_pagar = (TextView) findViewById(R.id.txt_tipo_moneda_comision_pagar);

        Bundle extras = getIntent().getExtras();
        usuario = extras.getParcelable("usuario");
        num_tarjeta = extras.getString("num_tarjeta");
        emisor_tarjeta = extras.getInt("emisor_tarjeta");
        tipo_tarjeta_pago = extras.getInt("tipo_tarjeta_pago");
        monto_servicio = extras.getString("monto_servicio");
        servicio = extras.getString("servicio");
        num_servicio = extras.getString("num_servicio");
        tipo_moneda_deuda = extras.getString("tipo_moneda_deuda");
        cod_banco = extras.getInt("cod_banco");
        tipo_servicio = extras.getString("tipo_servicio");
        cliente = extras.getString("cliente");

        tv_numero_tarjeta_cifrada_pago_servicio.setText(num_tarjeta);
        txt_servicio_pagar.setText(servicio);
        tv_monto_servicio_pagar.setText(monto_servicio);
        txt_tipo_moneda_servicio_pagar.setText(tipo_moneda_deuda);
        tv_nro_servicio_servicio_pagar.setText(num_servicio);
        tv_monto_total_servicio_pagar.setText(totalServicioPagar());
        tv_nombre_titular_pago_servicio.setText(cliente);
        txt_tipo_moneda_total_pagar.setText(tipo_moneda_deuda);
        txt_tipo_moneda_comision_pagar.setText(tipo_moneda_deuda);

        if (tipo_servicio == null) {
            ll_tipo_servicio_pagar_conforme.setVisibility(View.GONE);
        } else {
            txt_tipo_servicio_pagar.setText(tipo_servicio);
            ll_tipo_servicio_pagar_conforme.setVisibility(View.VISIBLE);
        }

        btn_continuar_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comision = tv_monto_comision_servicio_pagar.getText().toString();
                Intent intent = new Intent(ConformidadPagoServicios.this, VoucherPagoServicio.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("num_tarjeta", num_tarjeta);
                intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                intent.putExtra("monto_servicio", monto_servicio);
                intent.putExtra("servicio", servicio);
                intent.putExtra("num_servicio", num_servicio);
                intent.putExtra("tipo_moneda_deuda", tipo_moneda_deuda);
                intent.putExtra("comision", comision);
                intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                intent.putExtra("cod_banco", cod_banco);
                intent.putExtra("tipo_servicio", tipo_servicio);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });
    }

    public String totalServicioPagar(){
        String total;

        String monto = tv_monto_servicio_pagar.getText().toString();
        String comision = tv_monto_comision_servicio_pagar.getText().toString();

        double monto_p = Double.parseDouble(monto);
        double comision_p = Double.parseDouble(comision);

        double importe = monto_p + comision_p;

        //total = String.valueOf(importe);

        return format.format(importe);
    }
}
