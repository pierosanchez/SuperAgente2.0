package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

public class VoucherRecargaTelefonica extends Activity {

    Button btn_confirmar_operacion;
    private UsuarioEntity usuario;
    String cliente, tipo_moneda, tarjeta_cargo, banco, emisor_tarjeta, cli_dni;
    TextView tv_fecha, tv_hora, tv_numUnico, tv_operadora, tv_nrofono, tv_importe, tv_comision, tv_total, tv_banco, tv_nroTarjeta,tv_forpago;
    int tipo_tarjeta_pago;
    String tipo_moneda_recarga,tipo_operador,nro_telefono;
    Double monto_recarga,comisionRecarga,montoTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_recarga_telefonica);


        tv_fecha = (TextView) findViewById(R.id.tv_fechaRecarga);
        tv_hora = (TextView) findViewById(R.id.tv_horaRecarga);
        tv_numUnico = (TextView) findViewById(R.id.tv_numUnicoRecarga);
        tv_operadora = (TextView) findViewById(R.id.tv_operadorRecarga);
        tv_nrofono = (TextView) findViewById(R.id.tv_numfonoRecarga);
        tv_importe = (TextView) findViewById(R.id.tv_importeRecarga);
        tv_comision = (TextView) findViewById(R.id.tv_comisionRecarga);
        tv_total = (TextView) findViewById(R.id.tv_totalRecarga);
        tv_forpago = (TextView) findViewById(R.id.tv_formapagoRecarga);
        tv_banco = (TextView) findViewById(R.id.tv_bancoRecarga);
        tv_nroTarjeta = (TextView) findViewById(R.id.tv_nroTarjetaRecarga);

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
        comisionRecarga = extras.getDouble("comisionRecarga");
        montoTotal = extras.getDouble("montoTotal");

       /* tv_operadora.setText(tipo_operador);
        tv_nrofono.setText(nro_telefono);
        tv_importe.setText(monto_recarga.toString());
        tv_comision.setText(comisionRecarga.toString());
        tv_total.setText(montoTotal.toString());
        tv_forpago.setText(tipo_tarjeta_pago);
        tv_banco.setText(banco);
        tv_nroTarjeta.setText(tarjeta_cargo); */



    }
}
