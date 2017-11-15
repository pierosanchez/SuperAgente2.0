package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.text.DecimalFormat;

public class ConformidadComercioComercios extends Activity {

    Button btn_confirmar_operacion_consumo;
    private UsuarioEntity usuario;
    String cliente, tipo_moneda, tarjeta_cargo, monto_pagar, banco, emisor_tarjeta, cli_dni;
    TextView tv_nombre_cliente_comercio, tv_numero_tarjeta_comercio, tv_tipo_moneda_comercio, tv_monto_importe_comercio, tv_dni_cliente_comercio;
    int tipo_tarjeta_pago;
    String parteRazon,parteDireccion,parteDistrito, validacion_tarjeta;
    TextView tv_razonsoc_comercioConfor,tv_direccion_comercioConfor,tv_distrito_comercioConfor;
    EditText txt_clave_comercio;
    DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conformidad_comercio_comercios);

        btn_confirmar_operacion_consumo = (Button) findViewById(R.id.btn_confirmar_operacion_consumo);

        tv_nombre_cliente_comercio = (TextView) findViewById(R.id.tv_nombre_cliente_comercio);
        tv_numero_tarjeta_comercio = (TextView) findViewById(R.id.tv_numero_tarjeta_comercio);
        tv_tipo_moneda_comercio = (TextView) findViewById(R.id.tv_tipo_moneda_comercio);
        tv_monto_importe_comercio = (TextView) findViewById(R.id.tv_monto_importe_comercio);
        tv_dni_cliente_comercio = (TextView) findViewById(R.id.tv_dni_cliente_comercio);
        tv_razonsoc_comercioConfor = (TextView) findViewById(R.id.tv_razonsoc_comercioConfor);
        tv_direccion_comercioConfor = (TextView) findViewById(R.id.tv_direccion_comercioConfor);
        tv_distrito_comercioConfor = (TextView) findViewById(R.id.tv_distrito_comercioConfor);

        txt_clave_comercio = (EditText) findViewById(R.id.txt_clave_comercio);

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
        parteRazon = extras.getString("parteRazon");
        parteDireccion = extras.getString("parteDireccion");
        parteDistrito = extras.getString("parteDistrito");
        validacion_tarjeta = extras.getString("validacion_tarjeta");

        if (!parteRazon.equals("") && !parteDireccion.equals("") && !parteDistrito.equals("")){
            tv_razonsoc_comercioConfor.setText(parteRazon);
            tv_direccion_comercioConfor.setText(parteDireccion);
            tv_distrito_comercioConfor.setText(parteDistrito);
        } else {
            Toast.makeText(ConformidadComercioComercios.this, "Hubo un error al obtener los datos del comercio", Toast.LENGTH_LONG).show();
        }

        tv_nombre_cliente_comercio.setText(cliente);
        tv_numero_tarjeta_comercio.setText(tarjeta_cargo);
        tv_tipo_moneda_comercio.setText(tipo_moneda);
        tv_monto_importe_comercio.setText(montoTransferencia());
        tv_dni_cliente_comercio.setText(cli_dni);

        /*btn_confirmar_operacion_consumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tipo_tarjeta_pago == 2) {
                    Intent intent = new Intent(ConformidadComercioComercios.this, VoucherPagoConsumo.class);
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
                    Intent intent = new Intent(ConformidadComercioComercios.this, VoucherPagoConsumoFirma.class);
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
        });*/

        btn_confirmar_operacion_consumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String claveComercio = txt_clave_comercio.getText().toString();
                if (tipo_tarjeta_pago == 2) {
                    if (claveComercio.length() == 0){
                        Toast.makeText(ConformidadComercioComercios.this, "Ingrese la clave del comercio", Toast.LENGTH_LONG).show();
                    } else if (claveComercio.length() != 0) {
                        Intent intent = new Intent(ConformidadComercioComercios.this, VoucherPagoConsumo.class);
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
                } else if (tipo_tarjeta_pago == 1){
                    if (claveComercio.length() == 0){
                        Toast.makeText(ConformidadComercioComercios.this, "Ingrese la clave del comercio", Toast.LENGTH_LONG).show();
                    } else if (claveComercio.length() != 0) {
                        if (validacion_tarjeta.equals("Firma")) {
                            Intent intent = new Intent(ConformidadComercioComercios.this, VoucherPagoConsumoFirma.class);
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
                        } else if (validacion_tarjeta.equals("Pin")){
                            Intent intent = new Intent(ConformidadComercioComercios.this, VoucherPagoConsumo.class);
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
                }
            }
        });
    }

    public String montoTransferencia(){
        double importeTransferencia = Double.parseDouble(monto_pagar);
        return decimalFormat.format(importeTransferencia);
    }
}
