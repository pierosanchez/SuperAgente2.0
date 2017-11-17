package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.text.DecimalFormat;

public class VoucherRecargaTelefonica extends Activity {

    Button btn_efectuar_otra_operacion, btn_efectuar_otra_recarga, btn_salir;
    private UsuarioEntity usuario;
    String cliente, tipo_moneda, tarjeta_cargo, banco, emisor_tarjeta, cli_dni;
    TextView tv_fecha, tv_hora, tv_numUnico, tv_operadora, tv_nrofono, tv_importe, tv_comision, tv_total, tv_banco, tv_nroTarjeta,tv_forpago;
    int tipo_tarjeta_pago;
    String tipo_moneda_recarga,tipo_operador,nro_telefono;
    Double monto_recarga,comisionRecarga,montoTotal;
    DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voucher_recarga_telefonica);

        tv_fecha = (TextView) findViewById(R.id.tv_fechaRecarga);
        tv_hora = (TextView) findViewById(R.id.tv_horaRecarga);
        tv_numUnico = (TextView) findViewById(R.id.tv_numUnicoRecarga);
        tv_operadora = (TextView) findViewById(R.id.tv_operadorRecarga);
        tv_nrofono = (TextView) findViewById(R.id.tv_numfonoRecarga);
        tv_importe = (TextView) findViewById(R.id.tv_importeRecarga);
        tv_comision = (TextView) findViewById(R.id.tv_comisionRecarga);
        tv_total = (TextView) findViewById(R.id.tv_totalRecarga);
        tv_forpago = (TextView) findViewById(R.id.tv_recarga_forma_pago);
        tv_banco = (TextView) findViewById(R.id.tv_bancoRecarga);
        tv_nroTarjeta = (TextView) findViewById(R.id.tv_nroTarjetaRecarga);

        btn_salir = (Button) findViewById(R.id.btn_salir);
        btn_efectuar_otra_recarga = (Button) findViewById(R.id.btn_efectuar_otra_recarga);
        btn_efectuar_otra_operacion = (Button) findViewById(R.id.btn_efectuar_otra_operacion);

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

        tv_fecha.setText(obtenerFecha());
        tv_hora.setText(obtenerHora());
        tv_operadora.setText(tipo_operador);
        tv_nrofono.setText(nro_telefono);
        tv_importe.setText(transformarMontoRecarga());
        tv_comision.setText(transformarComisionRecarga());
        tv_total.setText(transformarMontoTotalRecarga());
        tv_forpago.setText(transformarTipoTarjetaPago());
        tv_banco.setText(banco);
        tv_nroTarjeta.setText(tarjeta_cargo);

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });

        btn_efectuar_otra_operacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VoucherRecargaTelefonica.this, MenuCliente.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                intent.putExtra("cli_dni", cli_dni);
                startActivity(intent);
                finish();
            }
        });

        btn_efectuar_otra_recarga.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(VoucherRecargaTelefonica.this, RecargaTelefonica.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                intent.putExtra("cli_dni", cli_dni);
                startActivity(intent);
                finish();
            }
        });

    }

    public String obtenerHora() {
        String hora;

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        int horaS = today.hour;
        int min = today.minute;
        int seg = today.second;
        //para probar en celulares se comenta y cuando es con emuladores se descomenta
        //horaS = horaS - 5;

        hora = "HORA: " + horaS + ":" + min + ":" + seg;

        return hora;
    }

    public String obtenerFecha() {

        String fecha;

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        int dia = today.monthDay;
        int mes = today.month;
        int año = today.year;
        mes = mes + 1;

        fecha = "FECHA: " + dia + "/" + mes + "/" + año;

        return fecha;
    }

    public String transformarMontoRecarga(){
        return tipo_moneda_recarga + " " + decimalFormat.format(monto_recarga);
    }

    public String transformarComisionRecarga(){
        return tipo_moneda_recarga + " " + decimalFormat.format(comisionRecarga);
    }

    public String transformarMontoTotalRecarga(){
        return tipo_moneda_recarga + " " + decimalFormat.format(montoTotal);
    }

    public String transformarTipoTarjetaPago(){
        String tipoTarjeta = "";
        if (tipo_tarjeta_pago == 1){
            tipoTarjeta = "Crédito";
        } else if (tipo_tarjeta_pago == 2){
            tipoTarjeta = "Débito";
        }
        return tipoTarjeta;
    }

    public void salir() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Esta seguro que desea salir");
        alertDialog.setTitle("Cancelar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(VoucherRecargaTelefonica.this, LoginActivity.class);
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
}
