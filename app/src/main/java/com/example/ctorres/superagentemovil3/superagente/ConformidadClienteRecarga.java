package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

public class ConformidadClienteRecarga extends Activity {

    Button btn_confirmar_operacion;
    private UsuarioEntity usuario;
    String cliente, tipo_moneda, tarjeta_cargo, monto_pagar, banco, emisor_tarjeta, cli_dni;
    TextView tv_nombrecli,tv_numerofono,tv_tarjetacli,tv_tipomone,tv_montoPagar,txt_comisionRecarga,txt_totalPagar;
    int tipo_tarjeta_pago;
    String tipo_moneda_recarga,tipo_operador,nro_telefono;
    Double monto_recarga,comisionRecarga,montoTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conformidad_cliente_recarga);

        btn_confirmar_operacion = (Button) findViewById(R.id.btn_confirmarRecarga);

        tv_nombrecli = (TextView) findViewById(R.id.txt_confor_nomCli);
        tv_numerofono = (TextView) findViewById(R.id.txt_confor_fonoCli);
        tv_tarjetacli = (TextView) findViewById(R.id.txt_confor_tarjetaCli);
        tv_tipomone = (TextView) findViewById(R.id.txt_confor_tipoMoneda);
        tv_montoPagar = (TextView) findViewById(R.id.txt_confor_Monto);
        txt_comisionRecarga = (TextView) findViewById(R.id.txt_confor_comisionRecarga);
        txt_totalPagar = (TextView) findViewById(R.id.txt_confor_totalPagar);

        Bundle extras = getIntent().getExtras();
        usuario = extras.getParcelable("usuario");
        cliente = extras.getString("cliente");
        tarjeta_cargo = extras.getString("tarjeta_cargo");
        emisor_tarjeta = extras.getString("emisor_tarjeta");
        banco = extras.getString("banco");
        tipo_tarjeta_pago = extras.getInt("tipo_tarjeta_pago");
        cli_dni = extras.getString("cli_dni");
        tipo_moneda_recarga = extras.getString("tipo_moneda_recarga");
        nro_telefono = extras.getString("nro_telefono");
        tipo_operador = extras.getString("tipo_operador");
        monto_recarga = extras.getDouble("monto_recarga");

        tv_nombrecli.setText(cliente);
        tv_numerofono.setText(nro_telefono);
        tv_tarjetacli.setText(tarjeta_cargo);
        tv_tipomone.setText(tipo_moneda_recarga);
        tv_montoPagar.setText(monto_recarga.toString());
        comisionRecarga = 2.00;
        txt_comisionRecarga.setText(comisionRecarga.toString());
        montoTotal = monto_recarga+comisionRecarga;
        txt_totalPagar.setText(montoTotal.toString());



        btn_confirmar_operacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConformidadClienteRecarga.this, VoucherRecargaTelefonica.class);
                intent.putExtra("cliente", cliente);
                intent.putExtra("usuario", usuario);
                intent.putExtra("banco", banco);
                intent.putExtra("tipo_moneda_recarga", tipo_moneda_recarga);
                intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                intent.putExtra("monto_pagar", monto_pagar);
                intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                intent.putExtra("cli_dni", cli_dni);

                intent.putExtra("nro_telefono", nro_telefono);
                intent.putExtra("tipo_operador", tipo_operador);
                intent.putExtra("monto_recarga", monto_recarga);
                intent.putExtra("comisionRecarga", comisionRecarga);
                intent.putExtra("montoTotal", montoTotal);
                startActivity(intent);
            }
        });
    }
}
