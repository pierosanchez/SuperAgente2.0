package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.adapter.NumeroUnicoAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.NumeroUnico;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.entity.VoucherPagoConsumoEntity;
import com.example.ctorres.superagentemovil3.entity.VoucherPagoRecargaEntity;
import com.example.ctorres.superagentemovil3.entity.VoucherPagoServicioEntity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class VoucherPagoConsumo extends Activity {

    Button btn_efectuar_otra_operacion, btn_salir;
    private UsuarioEntity usuario;
    private VoucherPagoConsumoEntity voucherConsumo;
    String cliente, tipo_moneda, tarjeta_cargo, monto_pagar, importe, banco, emisor_tarjeta, tarjeta, cli_dni;
    TextView tv_fecha_pago, txt_hora_pago, tv_tipo_tarjeta_voucher_consumo, txt_numero_tarjeta_voucher_consumo, tv_banco_voucher_consumo, txt_importe_voucher_consumo;
    String parteDireccion, parteDistrito, parteRazon, id_com, horaV, fechaV, nro_unico;
    DecimalFormat decimalFormat = new DecimalFormat("0.00");
    TextView tv_nombre_comercio, tv_direccion_comercio, tv_distrito_comercio, txt_numero_unico_voucher_consumos;
    NumeroUnicoAdapter numeroUnicoAdapter;
    ArrayList<NumeroUnico> numeroUnicoArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voucher_pago_consumo);

        btn_efectuar_otra_operacion = (Button) findViewById(R.id.btn_efectuar_otra_operacion);
        btn_salir = (Button) findViewById(R.id.btn_salir);

        tv_fecha_pago = (TextView) findViewById(R.id.tv_fecha_pago);
        txt_hora_pago = (TextView) findViewById(R.id.txt_hora_pago);
        tv_tipo_tarjeta_voucher_consumo = (TextView) findViewById(R.id.tv_tipo_tarjeta_voucher_consumo);
        txt_numero_tarjeta_voucher_consumo = (TextView) findViewById(R.id.txt_numero_tarjeta_voucher_consumo);
        tv_banco_voucher_consumo = (TextView) findViewById(R.id.tv_banco_voucher_consumo);
        txt_importe_voucher_consumo = (TextView) findViewById(R.id.txt_importe_voucher_consumo);
        tv_distrito_comercio = (TextView) findViewById(R.id.tv_distrito_comercio);
        tv_direccion_comercio = (TextView) findViewById(R.id.tv_direccion_comercio);
        tv_nombre_comercio = (TextView) findViewById(R.id.tv_nombre_comercio);
        txt_numero_unico_voucher_consumos = (TextView) findViewById(R.id.txt_numero_unico_voucher_consumos);

        Bundle extras = getIntent().getExtras();
        usuario = extras.getParcelable("usuario");
        cliente = extras.getString("cliente");
        tipo_moneda = extras.getString("tipo_moneda");
        tarjeta_cargo = extras.getString("tarjeta_cargo");
        monto_pagar = extras.getString("monto_pagar");
        emisor_tarjeta = extras.getString("emisor_tarjeta");
        banco = extras.getString("banco");
        cli_dni = extras.getString("cli_dni");
        parteDireccion = extras.getString("parteDireccion");
        parteDistrito = extras.getString("parteDistrito");
        parteRazon = extras.getString("parteRazon");
        id_com = extras.getString("id_com");
        nro_unico = extras.getString("nro_unico");
        importe = tipo_moneda + " " + convertirImporte();
        tarjeta = emisor_tarjeta + " " + tarjeta_cargo;
        fechaV = "FECHA: " + obtenerFecha();
        horaV = "HORA: " + obtenerHora();

        VoucherPagoConsumo.getNumUnico numUnico = new VoucherPagoConsumo.getNumUnico();
        numUnico.execute();

        tv_fecha_pago.setText(fechaV);
        txt_hora_pago.setText(horaV);
        txt_importe_voucher_consumo.setText(importe);
        tv_banco_voucher_consumo.setText(banco);
        txt_numero_tarjeta_voucher_consumo.setText(tarjeta);
        tv_nombre_comercio.setText(parteRazon);
        tv_direccion_comercio.setText(parteDireccion);
        tv_distrito_comercio.setText(parteDistrito);

        btn_efectuar_otra_operacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(VoucherPagoConsumo.this, MenuCliente.class);
                intent.putExtra("cliente", cliente);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cli_dni", cli_dni);
                startActivity(intent);
                finish();
            }
        });

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
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

        hora = horaS + ":" + min + ":" + seg;

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

        fecha = dia + "/" + mes + "/" + año;

        return fecha;
    }

    public String convertirImporte(){

        double imp = Double.parseDouble(monto_pagar);
        return decimalFormat.format(imp);
    }

    public void salir() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Esta seguro que desea salir");
        alertDialog.setTitle("Cancelar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(VoucherPagoConsumo.this, LoginActivity.class);
                startActivityForResult(intent, 0);
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

    private class getNumUnico extends AsyncTask<String, Void, VoucherPagoConsumoEntity> {

        @Override
        protected VoucherPagoConsumoEntity doInBackground(String... params) {
            VoucherPagoConsumoEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.getNumeroUnicoConsumos(nro_unico);

            } catch (Exception e) {
                user = null;
                //fldag_clic_ingreso = 0;;
            }
            return user;
        }

        @Override
        protected void onPostExecute(VoucherPagoConsumoEntity voucherPagoServicioEntity){
            voucherConsumo = voucherPagoServicioEntity;
            if (voucherConsumo != null){
                if (voucherConsumo.getNumeroUnico() != null){
                    txt_numero_unico_voucher_consumos.setText(voucherConsumo.getNumeroUnico());
                } else {
                    Toast.makeText(VoucherPagoConsumo.this, "no se trajo el numero unico", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(VoucherPagoConsumo.this, "la entidad no tiene data", Toast.LENGTH_LONG).show();
            }
        }
    }
}
