package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.CuentaEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.text.Format;
import java.util.Formatter;
import java.util.IllegalFormatCodePointException;
import java.util.Timer;
import java.util.TimerTask;

import br.com.sapereaude.maskedEditText.MaskedEditText;

/**
 * Created by Administrador on 17/04/2017.
 */

public class ControlMaximoNumeroCuentas extends Activity {

    String[] bancos = {"Interbank", "BCP", "BBVA", "Scotiabank", "Otros"};
    String[] cuentas = {"Cuenta", "Ahorro", "CTS", "Cuenta Corriente"};
    String[] moneda = {"Soles", "Dólares"};
    Spinner sp_cuentas, sp_banco, spinnerMoneda;
    EditText txt_num_cuenta;
    Button btn_ingrese_otra_cuenta, btnRegresar;
    private UsuarioEntity usuario;
    private CuentaEntity cuentaU;
    //MaskedEditText txt_nrocuenta;
    Format format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control_maximo_numero_cuentas);

        Button btn_terminar_afi = (Button) findViewById(R.id.btn_terminar_afiliacion);
        //Button btn_afiliar_transferencias = (Button) findViewById(R.id.btn_afiliar_transferencias);
        btn_ingrese_otra_cuenta = (Button) findViewById(R.id.btn_ingrese_otra_cuenta);
        /*sp_cuentas = (Spinner) findViewById(R.id.spinnerTipoCuenta);*/
        sp_banco = (Spinner) findViewById(R.id.spinnerBanco);
        spinnerMoneda = (Spinner) findViewById(R.id.spinnerMoneda);
        /*sp_moneda = (Spinner) findViewById(R.id.spinnerTipoMoneda);*/
        txt_num_cuenta = (EditText) findViewById(R.id.txt_num_cuentas_control_cuentas);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        //txt_nrocuenta = (MaskedEditText) findViewById(R.id.txt_nrocuenta);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");

        cargarBancos();

        //txt_nrocuenta.setMask("###-###-############-##");

        btn_ingrese_otra_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txt_num_cuenta.equals("")) {
                    ControlMaximoNumeroCuentas.validarCuenta validarCuenta = new ControlMaximoNumeroCuentas.validarCuenta();
                    validarCuenta.execute();
                } else {
                    Toast.makeText(ControlMaximoNumeroCuentas.this, "Ingrese su numero de cuenta", Toast.LENGTH_SHORT).show();
                }

                txt_num_cuenta.setText("");
                //txt_num_cuenta.setText("");

                Toast.makeText(ControlMaximoNumeroCuentas.this, "Cuenta Agregada Correctamente", Toast.LENGTH_SHORT).show();
            }
        });

        btn_terminar_afi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cuenta = txt_num_cuenta.getText().toString().trim();
                if (!cuenta.equals("")) {
                    if (cuenta.length() == 20) {
                        try {
                            ControlMaximoNumeroCuentas.validarCuenta validarCuenta = new ControlMaximoNumeroCuentas.validarCuenta();
                            //if (Integer.parseInt(validarCuenta.get().getNumCuenta()) != 0) {
                            validarCuenta.execute();

                            Intent intent = new Intent(ControlMaximoNumeroCuentas.this, VentanaErrores.class);
                            intent.putExtra("usuario", usuario);
                            startActivityForResult(intent, 0);
                            finish();

                            //BeneficiarioTransferencia();
                        /*} else {
                            Toast.makeText(ControlMaximoNumeroCuentas.this, "El numero de TipoAbono ya existe en el sistema", Toast.LENGTH_SHORT).show();
                        }*/

                        /*Intent intent = new Intent(ControlMaximoNumeroCuentas.this, AfiliacionExitosaConsulta.class);
                        startActivity(intent);
                        finish();*/
                        } catch (Exception e) {
                            Log.e("ERROR", Log.getStackTraceString(e));
                        }
                    } else {
                        Toast.makeText(ControlMaximoNumeroCuentas.this, "El número CCI debe tener 20 dígitos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ControlMaximoNumeroCuentas.this, "Ingrese su numero de Cuenta", Toast.LENGTH_SHORT).show();
                }
            }
        });


        /*btn_afiliar_transferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControlMaximoNumeroCuentas.this, IngresoDatosBeneficiarios2De4.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
            }
        });*/

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControlMaximoNumeroCuentas.this, InformacionTarjeta.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
            }
        });

    }

    public void cargarBancos() {
        ArrayAdapter<String> adaptadorBanco = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bancos);
        //ArrayAdapter<String> adaptadorCuentas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cuentas);
        ArrayAdapter<String> adaptadorMoneda = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, moneda);
        sp_banco.setAdapter(adaptadorBanco);
        //sp_cuentas.setAdapter(adaptadorCuentas);
        spinnerMoneda.setAdapter(adaptadorMoneda);
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
                    Toast.makeText(ControlMaximoNumeroCuentas.this, "No puede tener mas de 6 cuentas asociadas", Toast.LENGTH_SHORT).show();
                } else if (cuentaEntity.getNumCuenta().equals("0")) {
                    //queDeseaHacer();
                    Toast.makeText(ControlMaximoNumeroCuentas.this, "El numero de cuenta ya existe", Toast.LENGTH_SHORT).show();
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

    public void BeneficiarioTransferencia() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Desea ingresar beneficiarios de transferencia?");
        alertDialog.setTitle("Cancelar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ControlMaximoNumeroCuentas.this, IngresoDatosBeneficiarios2De4.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ControlMaximoNumeroCuentas.this, AfiliacionExitosaConsulta.class);
                startActivity(intent);
                finish();
            }
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    public int obtenerBancoCuenta(){
        String banco;
        int cod_banco = 0;
        banco = sp_banco.getSelectedItem().toString();

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

}
