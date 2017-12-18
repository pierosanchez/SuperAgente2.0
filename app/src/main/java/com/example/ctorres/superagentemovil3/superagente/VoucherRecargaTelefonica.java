package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.adapter.NumeroUnicoAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteBD;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.NumeroUnico;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.entity.VoucherPagoRecargaEntity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class VoucherRecargaTelefonica extends Activity {

    Button btn_efectuar_otra_operacion, btn_efectuar_otra_recarga, btn_salir;
    private UsuarioEntity usuario;
    String cliente, tipo_moneda, tarjeta_cargo, banco, emisor_tarjeta, cli_dni;
    TextView tv_fecha, tv_hora, tv_numUnico, tv_operadora, tv_nrofono, tv_importe, tv_comision, tv_total, tv_banco, tv_nroTarjeta,tv_forpago;
    TextView tv_tipo_moneda_importe, tv_tipo_moneda_comision_recarga, tv_tipo_moneda_total;
    int tipo_tarjeta_pago;
    String tipo_moneda_recarga,tipo_operador,nro_telefono;
    Double monto_recarga,comisionRecarga,montoTotal;
    DecimalFormat decimalFormat = new DecimalFormat("0.00");
    NumeroUnicoAdapter numeroUnicoAdapter;
    ArrayList<NumeroUnico> numeroUnicoArrayList;

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
        tv_tipo_moneda_total = (TextView) findViewById(R.id.tv_tipo_moneda_total);
        tv_tipo_moneda_comision_recarga = (TextView) findViewById(R.id.tv_tipo_moneda_comision_recarga);
        tv_tipo_moneda_importe = (TextView) findViewById(R.id.tv_tipo_moneda_importe);

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

        numeroUnicoArrayList = null;
        numeroUnicoAdapter = new NumeroUnicoAdapter(numeroUnicoArrayList, getApplication());

        ejecutarLista();

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
        tv_tipo_moneda_total.setText(tipo_moneda_recarga);
        tv_tipo_moneda_comision_recarga.setText(tipo_moneda_recarga);
        tv_tipo_moneda_importe.setText(tipo_moneda_recarga);

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });

        btn_efectuar_otra_operacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoucherRecargaTelefonica.ingresarVoucher ingreso = new VoucherRecargaTelefonica.ingresarVoucher();
                ingreso.execute();

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
                VoucherRecargaTelefonica.ingresarVoucher ingreso = new VoucherRecargaTelefonica.ingresarVoucher();
                ingreso.execute();

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

    public String transformarMontoRecarga(){
        return decimalFormat.format(monto_recarga);
    }

    public String transformarComisionRecarga(){
        return decimalFormat.format(comisionRecarga);
    }

    public String transformarMontoTotalRecarga(){
        return decimalFormat.format(montoTotal);
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
                VoucherRecargaTelefonica.ingresarVoucher ingreso = new VoucherRecargaTelefonica.ingresarVoucher();
                ingreso.execute();

                Intent intent = new Intent(VoucherRecargaTelefonica.this, LoginActivity.class);
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

    private void ejecutarLista(){

        try {
            VoucherRecargaTelefonica.getNumeroUnico listadoBeneficiario = new VoucherRecargaTelefonica.getNumeroUnico();
            listadoBeneficiario.execute();
        } catch (Exception e){
            //listadoBeneficiario = null;
        }

    }

    private class getNumeroUnico extends AsyncTask<String,Void,Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                numeroUnicoArrayList = dao.getNumeroUnico();
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            numeroUnicoAdapter.setNewListNumeroUnico(numeroUnicoArrayList);
            numeroUnicoAdapter.notifyDataSetChanged();
            tv_numUnico.setText(numeroUnicoArrayList.get(0).getNumeroUnico());
        }
    }

    private class ingresarVoucher extends AsyncTask<String, Void, VoucherPagoRecargaEntity> {
        String _fecha = tv_fecha.getText().toString();
        String _hora = tv_hora.getText().toString();
        String _numUnico = tv_numUnico.getText().toString();
        String _operadora = tv_operadora.getText().toString();
        String _telefono = tv_nrofono.getText().toString();
        String _importe = tv_importe.getText().toString();
        String _comision = tv_comision.getText().toString();
        String _total = tv_total.getText().toString();
        String _formaPago = tv_forpago.getText().toString();
        String _banco = tv_banco.getText().toString();
        String _nroTarjeta = tv_nroTarjeta.getText().toString();
        @Override
        protected VoucherPagoRecargaEntity doInBackground(String... params) {
            VoucherPagoRecargaEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.ingresarVoucherRecargas(_numUnico, _fecha, _hora, _operadora, _formaPago, _importe, _comision, _total, _banco, _nroTarjeta, tipo_moneda_recarga, usuario.getUsuarioId());

            } catch (Exception e) {
                user = null;
                //fldag_clic_ingreso = 0;;
            }
            return user;
        }
    }
}
