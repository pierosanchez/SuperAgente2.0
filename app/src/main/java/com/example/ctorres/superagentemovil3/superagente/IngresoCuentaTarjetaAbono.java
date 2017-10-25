package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.ComisionDeliverySolesAdapter;
import com.example.ctorres.superagentemovil3.dao.CuentasBeneficiarioAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.dao.TarjetasBeneficiarioAdapter;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.fragments.ListadoChequeFragment;
import com.example.ctorres.superagentemovil3.fragments.ListadoCuentasFragment;
import com.example.ctorres.superagentemovil3.fragments.ListadoTarjetasFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class IngresoCuentaTarjetaAbono extends Activity {

    String bancosAfiliados[] = {"Seleccionar", "Continental", "Crédito", "ScotiaBank"};
    String tipoMoneda[] = {"Seleccionar", "Soles", "Dólares"};
    String tipoCuenta[] = {"Seleccionar", "Ahorro", "Tarjeta", "CTA. CTE."};
    String tipoCheque[] = {"Con Delivery", "Con Recojo"};
    Spinner spinnerBancoPagar, spinnerTipoMoneda, spinnerTipoCuenta, spinnerTipoCheque;
    LinearLayout btn_aceptar_cuenta_tarjeta_abono, btn_cancelar_cuenta_tarjeta_abono, linearLayoutGastosEnvio, linearLayoutCuentaTarjetasBeneficiario, linearLayoutChequeBeneficiario;
    String monto, tipomoneda, transferencia;
    EditText txt_codigo_interbancario;
    TextView tv_comision_monto, tv_total_pagar_transferencia, tv_tipo_moneda_importe, tv_tipo_moneda_comision, tv_total_pagar_transferencia_simbolo, tv_tipo_transferencia, txt_importe_cuenta_tarjeta, tv_tipo_moneda_comision_cheque, tv_comision_cheque, tv_tipo_moneda_comision_cheque_delivery, tv_comision_cheque_delivery, tv_tipo_abono_transferencia, tv_fr_tipo_cheque;
    UsuarioEntity usuario;
    ArrayList<BeneficiarioEntity> arrayBenefeciarioEntity, arrayBeneficiarioTarjetas;
    ArrayList<UsuarioEntity> arrayComisiones;
    CuentasBeneficiarioAdapter adapterCuentasBeneficiario;
    TarjetasBeneficiarioAdapter adapterTarjetasBeneficiario;
    ComisionDeliverySolesAdapter comisionDeliverySolesAdapter;
    ListView lv_cuentas_beneficiario;
    String benef, benefT, dni_benef, nombreBeneficiario, banco, num_tarjeta;
    RadioGroup rdgp_tipo_pago;
    RadioButton rdbtn_cuenta_transferencia, rdbtn_tarjeta_transferencia, rdbtn_cheque_transferencia;
    DecimalFormat decimal = new DecimalFormat("0.00");
    String cliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_tipo_abono);

        /*spinnerBancoPagar = (Spinner) findViewById(R.id.spinnerBancoPagar);
        spinnerTipoMoneda = (Spinner) findViewById(R.id.spinnerTipoMoneda);
        spinnerTipoCuenta = (Spinner) findViewById(R.id.spinnerTipoCuenta);*/

        spinnerTipoCheque = (Spinner) findViewById(R.id.spinnerTipoCheque);

        lv_cuentas_beneficiario = (ListView) findViewById(R.id.lv_cuentas_beneficiario);

        btn_aceptar_cuenta_tarjeta_abono = (LinearLayout) findViewById(R.id.btn_aceptar_cuenta_tarjeta_abono);
        btn_cancelar_cuenta_tarjeta_abono = (LinearLayout) findViewById(R.id.btn_cancelar_cuenta_tarjeta_abono);
        linearLayoutGastosEnvio = (LinearLayout) findViewById(R.id.linearLayoutGastosEnvio);
        linearLayoutCuentaTarjetasBeneficiario = (LinearLayout) findViewById(R.id.linearLayoutCuentaTarjetasBeneficiario);
        linearLayoutChequeBeneficiario = (LinearLayout) findViewById(R.id.linearLayoutChequeBeneficiario);

        rdgp_tipo_pago = (RadioGroup) findViewById(R.id.rdgp_tipo_pago);

        rdbtn_cuenta_transferencia = (RadioButton) findViewById(R.id.rdbtn_cuenta_transferencia);
        rdbtn_tarjeta_transferencia = (RadioButton) findViewById(R.id.rdbtn_tarjeta_transferencia);
        rdbtn_cheque_transferencia = (RadioButton) findViewById(R.id.rdbtn_cheque_transferencia);

        tv_comision_monto = (TextView) findViewById(R.id.tv_comision_monto);
        tv_total_pagar_transferencia = (TextView) findViewById(R.id.tv_total_pagar_transferencia);
        tv_tipo_moneda_comision = (TextView) findViewById(R.id.tv_tipo_moneda_comision);
        tv_tipo_moneda_importe = (TextView) findViewById(R.id.tv_tipo_moneda_importe);
        tv_total_pagar_transferencia_simbolo = (TextView) findViewById(R.id.tv_total_pagar_transferencia_simbolo);
        tv_tipo_abono_transferencia = (TextView) findViewById(R.id.tv_tipo_abono_transferencia);
        tv_fr_tipo_cheque = (TextView) findViewById(R.id.tv_fr_tipo_cheque);
        tv_tipo_moneda_comision_cheque = (TextView) findViewById(R.id.tv_tipo_moneda_comision_cheque);
        tv_comision_cheque = (TextView) findViewById(R.id.tv_comision_cheque);
        tv_comision_cheque_delivery = (TextView) findViewById(R.id.tv_comision_cheque_delivery);
        tv_tipo_moneda_comision_cheque_delivery = (TextView) findViewById(R.id.tv_tipo_moneda_comision_cheque_delivery);

        txt_importe_cuenta_tarjeta = (TextView) findViewById(R.id.txt_importe_cuenta_tarjeta);
        //txt_codigo_interbancario = (EditText) findViewById(R.id.txt_codigo_interbancario);

        Bundle extras = getIntent().getExtras();
        monto = extras.getString("monto");
        usuario = extras.getParcelable("usuario");
        tipomoneda = extras.getString("tipomoneda");
        dni_benef = extras.getString("dni_benef");
        nombreBeneficiario = extras.getString("nombrebenef");
        banco = extras.getString("banco");
        num_tarjeta = extras.getString("num_tarjeta");
        cliente = extras.getString("cliente");

        cargarCombos();

        txt_importe_cuenta_tarjeta.setText(monto);

        tv_total_pagar_transferencia.setText(CaluloTotal());

        //txt_codigo_interbancario.setText(ejecutarListaCuentas());

        tv_tipo_moneda_comision.setText(tipomoneda);
        tv_tipo_moneda_comision_cheque.setText(tipomoneda);
        tv_tipo_moneda_comision_cheque_delivery.setText(tipomoneda);
        tv_tipo_moneda_importe.setText(tipomoneda);
        tv_total_pagar_transferencia_simbolo.setText(tipomoneda);

        btn_aceptar_cuenta_tarjeta_abono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!rdbtn_cheque_transferencia.isChecked() && !rdbtn_tarjeta_transferencia.isChecked() && !rdbtn_cuenta_transferencia.isChecked()) {
                    Toast.makeText(IngresoCuentaTarjetaAbono.this, "Seleccione un tipo de transferencia", Toast.LENGTH_SHORT).show();
                } else {
                    if (rdbtn_cuenta_transferencia.isChecked()) {
                        if (arrayBenefeciarioEntity == null){
                            Toast.makeText(IngresoCuentaTarjetaAbono.this, "El beneficiario no tiene cuentas registradas", Toast.LENGTH_SHORT).show();
                        } else if (arrayBenefeciarioEntity != null){
                            String cuenta = rdbtn_cuenta_transferencia.getText().toString();
                            Intent intent = new Intent(IngresoCuentaTarjetaAbono.this, VoucherTransferencias.class);
                            intent.putExtra("importe", obtenerImporte());
                            intent.putExtra("tipomoneda", tipomoneda);
                            intent.putExtra("usuario", usuario);
                            intent.putExtra("tipoAbono", cuenta);
                            intent.putExtra("detalleAbono", obtenerCuentaUsuario());
                            intent.putExtra("nombrebenef", nombreBeneficiario);
                            intent.putExtra("num_tarjeta", num_tarjeta);
                            intent.putExtra("banco", banco);
                            intent.putExtra("transferencia", obtenerTransferencia());
                            intent.putExtra("comision_monto", tv_comision_monto.getText().toString());
                            intent.putExtra("cliente", cliente);
                            startActivity(intent);
                            finish();
                        }
                    } else if (rdbtn_tarjeta_transferencia.isChecked()) {
                        if (arrayBeneficiarioTarjetas == null){
                            Toast.makeText(IngresoCuentaTarjetaAbono.this, "El beneficiario no tiene tarjetas registradas", Toast.LENGTH_SHORT).show();
                        } else if (arrayBeneficiarioTarjetas != null) {
                            String tarjeta = rdbtn_tarjeta_transferencia.getText().toString();
                            Intent intent = new Intent(IngresoCuentaTarjetaAbono.this, VoucherTransferencias.class);
                            intent.putExtra("importe", obtenerImporte());
                            intent.putExtra("tipomoneda", tipomoneda);
                            intent.putExtra("usuario", usuario);
                            intent.putExtra("tipoAbono", tarjeta);
                            intent.putExtra("detalleAbono", obtenerTarjetaUsuario());
                            intent.putExtra("nombrebenef", nombreBeneficiario);
                            intent.putExtra("num_tarjeta", num_tarjeta);
                            intent.putExtra("banco", banco);
                            intent.putExtra("transferencia", obtenerTransferencia());
                            intent.putExtra("comision_monto", tv_comision_monto.getText().toString());
                            intent.putExtra("cliente", cliente);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        String cheque = rdbtn_cheque_transferencia.getText().toString();
                        Intent intent = new Intent(IngresoCuentaTarjetaAbono.this, VoucherTransferencias.class);
                        intent.putExtra("importe", obtenerImporte());
                        intent.putExtra("tipomoneda", tipomoneda);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("tipoAbono", cheque);
                        intent.putExtra("detalleAbono", obtenerChequeTransferencia());
                        intent.putExtra("nombrebenef", nombreBeneficiario);
                        intent.putExtra("num_tarjeta", num_tarjeta);
                        intent.putExtra("banco", banco);
                        intent.putExtra("transferencia", obtenerTransferencia());
                        intent.putExtra("comision_cheque_delivery", tv_comision_cheque_delivery.getText().toString());
                        intent.putExtra("comision_cheque", tv_comision_cheque.getText().toString());
                        intent.putExtra("comision_monto", tv_comision_monto.getText().toString());
                        intent.putExtra("cliente", cliente);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        btn_cancelar_cuenta_tarjeta_abono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });

        rdgp_tipo_pago.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rdbtn_cuenta_transferencia) {
                    tv_tipo_abono_transferencia.setVisibility(View.VISIBLE);
                    linearLayoutCuentaTarjetasBeneficiario.setVisibility(View.VISIBLE);
                    linearLayoutChequeBeneficiario.setVisibility(View.GONE);
                    tv_tipo_abono_transferencia.setText("CÓDIGO INTERBANCARIO");
                    tv_total_pagar_transferencia.setText(CaluloTotal());
                    /*Bundle arguments = new Bundle();
                    arguments.putString("dni_benef", dni_benef);
                    ListadoCuentasFragment fragment = new ListadoCuentasFragment();
                    fragment.setArguments(arguments);
                    reemplazarFragment(fragment);*/

                    arrayBenefeciarioEntity = null;
                    adapterCuentasBeneficiario = new CuentasBeneficiarioAdapter(arrayBenefeciarioEntity, getApplication());
                    lv_cuentas_beneficiario.setAdapter(adapterCuentasBeneficiario);

                    ejecutarLista();

                } else if (checkedId == R.id.rdbtn_tarjeta_transferencia) {
                    tv_tipo_abono_transferencia.setVisibility(View.VISIBLE);
                    linearLayoutCuentaTarjetasBeneficiario.setVisibility(View.VISIBLE);
                    linearLayoutChequeBeneficiario.setVisibility(View.GONE);
                    tv_tipo_abono_transferencia.setText("NÚMERO DE TARJETA");
                    tv_total_pagar_transferencia.setText(CaluloTotal());

                    arrayBeneficiarioTarjetas = null;
                    adapterTarjetasBeneficiario = new TarjetasBeneficiarioAdapter(arrayBeneficiarioTarjetas, getApplication());
                    lv_cuentas_beneficiario.setAdapter(adapterTarjetasBeneficiario);

                    ejecutarListaTarjetaBeneficiario();

                } else {
                    linearLayoutCuentaTarjetasBeneficiario.setVisibility(View.GONE);
                    linearLayoutChequeBeneficiario.setVisibility(View.VISIBLE);
                    tv_tipo_abono_transferencia.setVisibility(View.GONE);

                    tv_fr_tipo_cheque.setText(tipocheque());
                }
            }
        });

        spinnerTipoCheque.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //tv_fr_tipo_cheque.setText(tipocheque());
                if (parent.getAdapter().getItem(position).equals("Con Delivery")){
                    String msg = "Enviar a: Av. La Marina cdra. 25, San Miguel";
                    linearLayoutGastosEnvio.setVisibility(View.VISIBLE);
                    CalculoTotalChequeDelivery();
                    tv_total_pagar_transferencia.setText(CalculoTotalChequeDelivery());
                    tv_fr_tipo_cheque.setText(msg);
                } else if (parent.getAdapter().getItem(position).equals("Con Recojo")){
                    linearLayoutGastosEnvio.setVisibility(View.GONE);
                    String msg = "Recoger Cheque en Direccion: Av. 28 de Julio 1326, Miraflores";
                    tv_total_pagar_transferencia.setText(CalculoTotalCheque());
                    tv_fr_tipo_cheque.setText(msg);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //tv_fr_tipo_cheque.setText(tipocheque());
            }
        });
    }

    public void cargarCombos() {
        ArrayAdapter<String> adapterTipoCheque = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipoCheque);
        spinnerTipoCheque.setAdapter(adapterTipoCheque);
    }

    public void cancelar() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Esta seguro que desea cacelar la transacción?");
        alertDialog.setTitle("Cancelar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(IngresoCuentaTarjetaAbono.this, MenuCliente.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
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

    public String CaluloTotal() {
        DecimalFormat format = new DecimalFormat("0.00");

        String total;

        double importe = Double.parseDouble(txt_importe_cuenta_tarjeta.getText().toString());
        double comision = Double.parseDouble(tv_comision_monto.getText().toString());

        double totalPago = importe + comision;

        //total = String.valueOf(totalPago);

        return format.format(totalPago);

    }

    private void ejecutarLista() {
        //benef = usuario.getUsuarioId();

        try {
            IngresoCuentaTarjetaAbono.ListadoBeneficiario listadoBeneficiario = new IngresoCuentaTarjetaAbono.ListadoBeneficiario();
            listadoBeneficiario.execute();
        } catch (Exception e) {
            //listadoBeneficiario = null;
        }

    }

    private class ListadoBeneficiario extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                arrayBenefeciarioEntity = dao.listarCuentaBeneficiario(dni_benef);
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapterCuentasBeneficiario.setNewListBeneficiario(arrayBenefeciarioEntity);
            adapterCuentasBeneficiario.notifyDataSetChanged();
        }
    }

    private void ejecutarListaTarjetaBeneficiario() {
        benef = usuario.getUsuarioId();

        try {
            IngresoCuentaTarjetaAbono.ListadoTarjetaBeneficiario listadoBeneficiario = new IngresoCuentaTarjetaAbono.ListadoTarjetaBeneficiario();
            listadoBeneficiario.execute();
        } catch (Exception e) {
            //listadoBeneficiario = null;
        }

    }

    private class ListadoTarjetaBeneficiario extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                arrayBeneficiarioTarjetas = dao.listarTarjetasBeneficiario(dni_benef);
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapterTarjetasBeneficiario.setNewListBeneficiario(arrayBeneficiarioTarjetas);
            adapterTarjetasBeneficiario.notifyDataSetChanged();
        }
    }

    private void ejecutarListadoComisionDeliverySoles() {

        try {
            IngresoCuentaTarjetaAbono.ListadoComisionDeliverySoles listadoComisionDeliverySoles = new IngresoCuentaTarjetaAbono.ListadoComisionDeliverySoles();
            listadoComisionDeliverySoles.execute();
        } catch (Exception e) {
            //listadoBeneficiario = null;
        }

    }

    private class ListadoComisionDeliverySoles extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                arrayComisiones = dao.listarComisionDeliverySoles();
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            comisionDeliverySolesAdapter.setNewListBeneficiario(arrayComisiones);
            comisionDeliverySolesAdapter.notifyDataSetChanged();
        }
    }

    public String obtenerImporte() {
        String total;
        total = tv_total_pagar_transferencia.getText().toString();
        return total;
    }

    public String obtenerTransferencia() {
        String total;
        total = txt_importe_cuenta_tarjeta.getText().toString();
        return total;
    }

    public String tipocheque(){
        String tipo = spinnerTipoCheque.getSelectedItem().toString();
        String msg = "";

        if (tipo.equals("Con Delivery")){
            msg = "Enviar a: Av. La Marina cdra. 25, San Miguel";
            linearLayoutGastosEnvio.setVisibility(View.VISIBLE);
            CalculoTotalChequeDelivery();
            tv_total_pagar_transferencia.setText(CalculoTotalChequeDelivery());
        } else if (tipo.equals("Con Recojo")) {
            linearLayoutGastosEnvio.setVisibility(View.GONE);
            msg = "Recoger Cheque en Direccion: Av. 28 de Julio 1326, Miraflores";
            tv_total_pagar_transferencia.setText(CalculoTotalCheque());
        }
        return msg;
    }

    public String CalculoTotalChequeDelivery(){
        DecimalFormat format = new DecimalFormat("0.00");

        String comisioncheque, comisiontran, importe, delivery;

        TextView tv_comision_monto = (TextView) findViewById(R.id.tv_comision_monto);
        TextView imp_cuenta_tarjeta = (TextView) findViewById(R.id.txt_importe_cuenta_tarjeta);

        delivery = tv_comision_cheque_delivery.getText().toString();
        comisiontran = tv_comision_monto.getText().toString();
        importe = imp_cuenta_tarjeta.getText().toString();
        comisioncheque = tv_comision_cheque.getText().toString();

        float comisionChq = Float.parseFloat(comisioncheque);
        double importeV = Double.parseDouble(importe);
        double comision = Double.parseDouble(comisiontran);
        double envio = Double.parseDouble(delivery);

        double totalPago = importeV + comision + comisionChq + envio;

        //total = String.valueOf(totalPago);

        return format.format(totalPago);
    }

    public String CalculoTotalCheque(){
        DecimalFormat format = new DecimalFormat("0.00");

        String total, comisioncheque, comisiontran, importe;

        TextView tv_comision_monto = (TextView) findViewById(R.id.tv_comision_monto);
        TextView imp_cuenta_tarjeta = (TextView) findViewById(R.id.txt_importe_cuenta_tarjeta);

        comisiontran = tv_comision_monto.getText().toString();
        importe = imp_cuenta_tarjeta.getText().toString();
        comisioncheque = tv_comision_cheque.getText().toString();

        double comisionChq = Double.parseDouble(comisioncheque);
        double importeV = Double.parseDouble(importe);
        double comision = Double.parseDouble(comisiontran);

        double totalPago = importeV + comision + comisionChq;

        //total = String.valueOf(totalPago);

        return format.format(totalPago);
    }

    public String obtenerTarjetaUsuario(){
        String text;
        text = arrayBeneficiarioTarjetas.get(0).getNum_tarjeta_beneficiario();
        return text;
    }

    public String obtenerCuentaUsuario(){
        String text;
        text = arrayBenefeciarioEntity.get(0).getCod_interbancario();
        return text;
    }

    public String obtenerChequeTransferencia(){
        String texto;
        texto = spinnerTipoCheque.getSelectedItem().toString();
        return texto;
    }

}
