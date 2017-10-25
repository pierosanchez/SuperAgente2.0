package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.BancosAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.BancosEntity;
import com.example.ctorres.superagentemovil3.entity.CuentaEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;

public class AgregarCuentasUsuario extends Activity {

    EditText txt_num_cuenta;
    Button btn_ingrese_otra_cuenta, btnRegresar;
    private UsuarioEntity usuario;
    private CuentaEntity cuentaU;
    Button btn_terminar_afi;
    Spinner spinnerBanco, spinnerMoneda;
    String[] bancos = {"Interbank", "BCP", "BBVA", "Scotiabank", "Otros"};
    String[] moneda = {"Soles", "Dólares"};
    ArrayList<BancosEntity> bancosEntityArrayList;
    BancosAdapter bancosAdapter;
    String cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_cuentas_usuario);

        txt_num_cuenta = (EditText) findViewById(R.id.txt_num_cuentas_control_cuentas);

        btn_terminar_afi = (Button) findViewById(R.id.btn_terminar_afiliacion);
        btn_ingrese_otra_cuenta = (Button) findViewById(R.id.btn_ingrese_otra_cuenta);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);

        spinnerBanco = (Spinner) findViewById(R.id.spinnerBanco);
        spinnerMoneda = (Spinner) findViewById(R.id.spinnerMoneda);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");
        cliente = bundle.getString("cliente");

        cargarBancos();

        bancosEntityArrayList = null;
        bancosAdapter = new BancosAdapter(bancosEntityArrayList, getApplication());
        spinnerBanco.setAdapter(bancosAdapter);

        ejecutarLista();

        btn_terminar_afi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cuenta = txt_num_cuenta.getText().toString().trim();
                if (!cuenta.equals("")) {
                    if (cuenta.length() == 20) {
                        try {
                            AgregarCuentasUsuario.validarCuenta validarCuenta = new AgregarCuentasUsuario.validarCuenta();
                            validarCuenta.execute();

                            Intent intent = new Intent(AgregarCuentasUsuario.this, ListadoCuentasUsuario.class);
                            intent.putExtra("usuario", usuario);
                            intent.putExtra("cliente", cliente);
                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            Log.e("ERROR", Log.getStackTraceString(e));
                        }
                    } else {
                        Toast.makeText(AgregarCuentasUsuario.this, "Cuenta CCI inválida", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AgregarCuentasUsuario.this, "Ingrese su numero de Cuenta", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AgregarCuentasUsuario.this, ListadoCuentasUsuario.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });
    }

    private class validarCuenta extends AsyncTask<String, Void, CuentaEntity> {
        String numCuenta = txt_num_cuenta.getText().toString();

        @Override
        protected CuentaEntity doInBackground(String... params) {
            CuentaEntity cuenta;
            try {

                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                cuenta = dao.getInsertarCuenta(numCuenta, usuario.getUsuarioId(), obtenerBancoCuenta(), obtenerTipoMonedaCuenta());

            } catch (Exception e) {
                cuenta = null;
                //flag_clic_ingreso = 0;;
            }
            return cuenta;
        }

        @Override
        protected void onPostExecute(CuentaEntity cuentaEntity) {
            //super.onPostExecute(cuentaEntity);
            cuentaU = cuentaEntity;
            if (cuentaEntity.getNumCuenta() != null) {
                if (cuentaEntity.getNumCuenta().equals("01")) {
                    Toast.makeText(AgregarCuentasUsuario.this, "No puede tener mas de 6 cuentas asociadas", Toast.LENGTH_SHORT).show();
                } else if (cuentaEntity.getNumCuenta().equals("0")) {
                    //queDeseaHacer();
                    Toast.makeText(AgregarCuentasUsuario.this, "El numero de cuenta ya existe", Toast.LENGTH_SHORT).show();
                    /*Intent sanipesIntent = new Intent(LoginActivity.this, VentanaErrores.class);
                    sanipesIntent.putExtra("usuario", userEntity);
                    startActivityForResult(sanipesIntent, 0);
                    finish();*/
                }
            } else {
                //mensaje_error = getString(R.string.msg_error_sin_conexion);
                //sinConexion = true;

            }

        }
    }

    public void cargarBancos() {
        ArrayAdapter<String> adaptadorMoneda = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, moneda);
        spinnerMoneda.setAdapter(adaptadorMoneda);
    }

    public int obtenerBancoCuenta(){
        String banco;
        int cod_banco = 0;
        banco = spinnerBanco.getSelectedItem().toString();

        if (banco.equals("Interbank")) {
            cod_banco = 3;
        } else if (banco.equals("BCP")) {
            cod_banco = 2;
        } else if (banco.equals("BBVA")) {
            cod_banco = 4;
        } else if (banco.equals("Scotiabank")) {
            cod_banco = 1;
        } else if (banco.equals("Otros")) {
            cod_banco = 5;
        }

        return cod_banco;
    }

    public int obtenerTipoMonedaCuenta(){
        String banco;
        int tipo_moneda = 0;
        banco = spinnerMoneda.getSelectedItem().toString();

        if (banco.equals("Soles")) {
            tipo_moneda = 2;
        } else if (banco.equals("Dólares")) {
            tipo_moneda = 1;
        }

        return tipo_moneda;
    }

    public void formatoCuenta(){
        String cuenta = txt_num_cuenta.getText().toString();

        for (int i=0; cuenta.length()>i; i++){

        }
    }

    private void ejecutarLista() {

        try {
            AgregarCuentasUsuario.ListadoEmpresas listadoEmpresas = new AgregarCuentasUsuario.ListadoEmpresas();
            listadoEmpresas.execute();
        } catch (Exception e) {
            //listadoBeneficiario = null;
        }

    }

    private class ListadoEmpresas extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                bancosEntityArrayList = dao.ListadoBancos();
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //usuarioEntityArrayList.remove(banco = banco_tarjeta);
            bancosAdapter.setNewListbancos(bancosEntityArrayList);
            bancosAdapter.notifyDataSetChanged();
        }
    }
}
