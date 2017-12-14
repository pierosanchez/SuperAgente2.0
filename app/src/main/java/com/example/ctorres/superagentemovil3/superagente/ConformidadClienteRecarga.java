package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.text.DecimalFormat;

public class  ConformidadClienteRecarga extends Activity {

    Button btn_confirmar_operacion;
    private UsuarioEntity usuario;
    String cliente, tipo_moneda, tarjeta_cargo, monto_pagar, banco, emisor_tarjeta, cli_dni, validacion_tarjeta;
    TextView tv_nombrecli,tv_numerofono,tv_tarjetacli,tv_tipomone,tv_montoPagar,txt_comisionRecarga,txt_totalPagar, tv_dni_cliente_recarga;
    TextView tv_tipo_moneda_recarga, tv_tipo_moneda_comision, tv_tipo_moneda_total_recarga;
    int tipo_tarjeta_pago;
    String tipo_moneda_recarga,tipo_operador,nro_telefono;
    Double monto_recarga,comisionRecarga,montoTotal;
    DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conformidad_cliente_recarga);

        btn_confirmar_operacion = (Button) findViewById(R.id.btn_confirmarRecarga);

        tv_nombrecli = (TextView) findViewById(R.id.txt_confor_nomCli);
        tv_numerofono = (TextView) findViewById(R.id.txt_confor_fonoCli);
        tv_tarjetacli = (TextView) findViewById(R.id.txt_confor_tarjetaCli);
        tv_tipomone = (TextView) findViewById(R.id.txt_confor_tipoMoneda);
        tv_montoPagar = (TextView) findViewById(R.id.txt_confor_Monto);
        txt_comisionRecarga = (TextView) findViewById(R.id.txt_confor_comisionRecarga);
        txt_totalPagar = (TextView) findViewById(R.id.txt_confor_totalPagar);
        tv_dni_cliente_recarga = (TextView) findViewById(R.id.tv_dni_cliente_recarga);
        tv_tipo_moneda_recarga = (TextView) findViewById(R.id.tv_tipo_moneda_recarga);
        tv_tipo_moneda_comision = (TextView) findViewById(R.id.tv_tipo_moneda_comision);
        tv_tipo_moneda_total_recarga = (TextView) findViewById(R.id.tv_tipo_moneda_total_recarga);

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
        validacion_tarjeta = extras.getString("validacion_tarjeta");

        tv_nombrecli.setText(cliente);
        tv_numerofono.setText(nro_telefono);
        tv_tarjetacli.setText(tarjeta_cargo);
        tv_tipomone.setText(tipo_moneda_recarga);
        tv_montoPagar.setText(transformarMontoRecarga());
        comisionRecarga = 2.00;
        txt_comisionRecarga.setText(transformarComisionRecarga());
        montoTotal = monto_recarga+comisionRecarga;
        txt_totalPagar.setText(transformarMontoTotal());
        tv_dni_cliente_recarga.setText(cli_dni);
        tv_tipo_moneda_recarga.setText(tipo_moneda_recarga);
        tv_tipo_moneda_comision.setText(tipo_moneda_recarga);
        tv_tipo_moneda_total_recarga.setText(tipo_moneda_recarga);



        btn_confirmar_operacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tipo_tarjeta_pago == 1) {
                    if (validacion_tarjeta.equals("Pin")) {
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
                        finish();
                    } else if (validacion_tarjeta.equals("Firma")) {
                        Intent intent = new Intent(ConformidadClienteRecarga.this, VoucherRecargaTelefonicaFirma.class);
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
                        finish();
                    }
                } else if (tipo_tarjeta_pago == 2){
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
                    finish();
                }
            }
        });
    }

    public String transformarMontoRecarga(){
        return decimalFormat.format(monto_recarga);
    }

    public String transformarComisionRecarga(){
        return  decimalFormat.format(comisionRecarga);
    }

    public String transformarMontoTotal(){
        return decimalFormat.format(montoTotal);
    }
}
