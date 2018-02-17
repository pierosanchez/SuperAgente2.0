package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.adapter.TarjetasUsuarioAdapter;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.utils.Constante;

import java.util.ArrayList;

public class SeleccionTarjetaCargo extends Activity {

    ImageView select_tarjeta_cargo, btn_atras, btn_adelante;
    TextView tv_tipo_tarjeta;
    private UsuarioEntity usuario;
    int[] tarjetaid = {R.drawable.bin424137scotiabankjockeyvisa, R.drawable.bin377754interbankamexblueimgtaramexbluefc_jpg, R.drawable.bin457562scotiabankjockeyvisaplatinum, R.drawable.bin510308scotiabankmcpremiumnew, R.drawable.bin516003scotiabankmcdebitogold, R.drawable.bin545545scotiabankmastercardclassic, R.drawable.bin552271scotiabankmastercardblack, R.drawable.bin554911scotiabankmastercardplatinum};
    String[] tipotarjetas = {"Crédito", "Débito", "Débito", "Débito", "Crédito", "Débito", "Crédito", "Crédito"};
    int i = 0;
    int tipo_tarjeta, emisor_tarjeta;
    String monto, tipo_moneda_deuda, cliente, tipo_servicio;
    Bitmap bmp;
    String usu, num_tarjeta, banco_tarjeta, banco, monto_servicio, servicio, num_servicio, cadena_scanneo, nro_telefono, tipo_moneda_recarga, tipo_operador;
    TarjetasUsuarioAdapter tarjetasUsuarioAdapter;
    ArrayList<UsuarioEntity> usuarioEntityArrayList;
    ListView lv_tarjetas_cargo_usuario;
    Button btn_regresar, btn_cancelar_seleccion_tarjeta_cargo;
    String callingActivity, cli_dni;
    double monto_recarga;
    String nom_comerciosp, direccion_comerciosp, distrito_comerciosp, desc_corta_banco, nombre_recibo;
    private ProgressBar circleProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_tarjeta_cargo);

        /*select_tarjeta_cargo = (ImageView) findViewById(R.id.img_tarjeta_seleccion_cargo);
        btn_atras = (ImageView) findViewById(R.id.img_atras);
        btn_adelante = (ImageView) findViewById(R.id.img_adelante);*/

        //tv_tipo_tarjeta = (TextView) findViewById(R.id.tv_tipo_tarjeta);

        lv_tarjetas_cargo_usuario = (ListView) findViewById(R.id.lv_tarjetas_cargo_usuario);

        btn_regresar = (Button) findViewById(R.id.btn_regresar);
        btn_cancelar_seleccion_tarjeta_cargo = (Button) findViewById(R.id.btn_cancelar_seleccion_tarjeta_cargo);

        circleProgressBar = (ProgressBar) findViewById(R.id.circleProgressBar);

        //total = tarjetaid.length;

        /*Bundle extras = getIntent().getExtras();
        monto = extras.getString("monto");
        usuario = extras.getParcelable("usuario");
        num_tarjeta = extras.getString("num_tarjeta");
        tipo_tarjeta = extras.getInt("tipo_tarjeta");
        emisor_tarjeta = extras.getInt("emisor_tarjeta");
        banco_tarjeta = extras.getString("banco_tarjeta");
        tipo_moneda_deuda = extras.getString("tipo_moneda_deuda");
        monto_servicio = extras.getString("monto_servicio");
        servicio = extras.getString("servicio");
        num_servicio = extras.getString("num_servicio");
        cliente = extras.getString("cliente");
        tipo_servicio = extras.getString("tipo_servicio");
        cli_dni = extras.getString("cli_dni");
        desc_corta_banco = extras.getString("desc_corta_banco");

        cadena_scanneo = extras.getString("cadena_scanneo");
        nom_comerciosp = extras.getString("nom_comerciosp");
        direccion_comerciosp = extras.getString("direccion_comerciosp");
        distrito_comerciosp = extras.getString("distrito_comerciosp");

        nro_telefono = extras.getString("nro_telefono");
        tipo_moneda_recarga = extras.getString("tipo_moneda");
        tipo_operador = extras.getString("tipo_operador");
        monto_recarga = extras.getDouble("monto_recarga");
        nombre_recibo = extras.getString("nombre_recibo");*/


        callingActivity = this.getCallingActivity().getClassName();

        if (callingActivity.equals(Constante.ACTIVITYROOT + "SeleccionRecibosPagar")) {

            actionSeleccionRecibosPagar();

        } else if (callingActivity.equals(Constante.ACTIVITYROOT + "SeleccionModoMontoPago")) {

            actionSeleccionModoMontoPago();

        } /*else if (callingActivity.equals(Constante.ACTIVITYROOT + "MenuCliente")) {

            actionPagoConsumos();
        }*/ else if (callingActivity.equals(Constante.ACTIVITYROOT + "LecturaInformacionComercio")) {

            actionPagoComercio();

        } else if (callingActivity.equals(Constante.ACTIVITYROOT + "RecargaTelefonica")) {

            actionPagoRecarga();
        }

        btn_cancelar_seleccion_tarjeta_cargo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });

        btn_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callingActivity.equals(Constante.ACTIVITYROOT + "SeleccionRecibosPagar")) {
                    Intent intent = new Intent(SeleccionTarjetaCargo.this, SeleccionRecibosPagar.class);
                    intent.putExtra("servicio", servicio);
                    intent.putExtra("num_servicio", num_servicio);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("cli_dni", cli_dni);
                    startActivity(intent);
                    finish();
                } else if (callingActivity.equals(Constante.ACTIVITYROOT + "SeleccionModoMontoPago")) {
                    Intent intent = new Intent(SeleccionTarjetaCargo.this, SeleccionModoMontoPago.class);
                    intent.putExtra("monto", monto);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("num_tarjeta", num_tarjeta);
                    intent.putExtra("tipo_tarjeta", tipo_tarjeta);
                    intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                    intent.putExtra("banco_tarjeta", banco_tarjeta);
                    intent.putExtra("tipo_moneda_deuda", tipo_moneda_deuda);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("cli_dni", cli_dni);
                    startActivity(intent);
                    finish();
                } /*else if (callingActivity.equals(Constante.ACTIVITYROOT + "MenuCliente")) {
                    Intent intent = new Intent(SeleccionTarjetaCargo.this, MenuCliente.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("cliente", cliente);
                    startActivity(intent);
                    finish();
                }*/ else if (callingActivity.equals(Constante.ACTIVITYROOT + "RecargaTelefonica")) {
                    Intent intent = new Intent(SeleccionTarjetaCargo.this, RecargaTelefonica.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("cli_dni", cli_dni);
                    startActivity(intent);
                    finish();
                } else if (callingActivity.equals(Constante.ACTIVITYROOT + "LecturaInformacionComercio")) {
                    Intent intent = new Intent(SeleccionTarjetaCargo.this, MenuCliente.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("cli_dni", cli_dni);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    private void ejecutarLista() {
        usu = usuario.getUsuarioId();

        try {
            SeleccionTarjetaCargo.ListadoTarjetas listadoBeneficiario = new SeleccionTarjetaCargo.ListadoTarjetas();
            listadoBeneficiario.execute();
        } catch (Exception e) {
            //listadoBeneficiario = null;
        }

    }

    private class ListadoTarjetas extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                usuarioEntityArrayList = dao.listarTarjetas(usu);
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //usuarioEntityArrayList.remove(banco = banco_tarjeta);
            tarjetasUsuarioAdapter.setNewListBeneficiario(usuarioEntityArrayList);
            tarjetasUsuarioAdapter.notifyDataSetChanged();
            circleProgressBar.setVisibility(View.GONE);
            if (callingActivity.equals(Constante.ACTIVITYROOT + "SeleccionModoMontoPago")) {
                for (int i = usuarioEntityArrayList.size() - 1; i >= 0; i--) {
                    if (usuarioEntityArrayList.get(i).getNumeroTarjeta().equals(num_tarjeta)) {
                        usuarioEntityArrayList.remove(i);
                    }
                }
            } else if (callingActivity.equals(Constante.ACTIVITYROOT + "SeleccionRecibosPagar")) {
                if (usuarioEntityArrayList.size() == 1) {
                    if (tarjetasUsuarioAdapter.getItem(0).getTipo_tarjeta() == 1) {
                        if (tarjetasUsuarioAdapter.getItem(0).getValidacionTarjeta().equals("Firma")) {
                            if (tarjetasUsuarioAdapter.getItem(0).getBanco_tarjeta().equals(banco_tarjeta)) {
                                Toast.makeText(SeleccionTarjetaCargo.this, "No puede pagar con una tarjeta del mismo banco", Toast.LENGTH_SHORT).show();
                            } else {
                                String num_tarjeta_cargo = tarjetasUsuarioAdapter.getItem(0).getNumeroTarjeta();
                                int tipo_tarjeta_pago = tarjetasUsuarioAdapter.getItem(0).getTipo_tarjeta();
                                int emisor_tarjeta_cargo = tarjetasUsuarioAdapter.getItem(0).getCod_emisor_tarjeta();
                                int cod_banco = tarjetasUsuarioAdapter.getItem(0).getBanco_tarjeta_registro();
                                String validacion_tarjeta = tarjetasUsuarioAdapter.getItem(0).getValidacionTarjeta();
                                Intent intent = new Intent(SeleccionTarjetaCargo.this, IngresoMontoPagoFirmaServicios.class);
                                intent.putExtra("monto", monto);
                                intent.putExtra("usuario", usuario);
                                intent.putExtra("num_tarjeta", num_tarjeta_cargo);
                                intent.putExtra("emisor_tarjeta", emisor_tarjeta_cargo);
                                intent.putExtra("monto_servicio", monto_servicio);
                                intent.putExtra("servicio", servicio);
                                intent.putExtra("num_servicio", num_servicio);
                                intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                                intent.putExtra("cod_banco", cod_banco);
                                intent.putExtra("cliente", cliente);
                                intent.putExtra("tipo_servicio", tipo_servicio);
                                intent.putExtra("cli_dni", cli_dni);
                                intent.putExtra("cod_banco", cod_banco);
                                intent.putExtra("nombre_recibo", nombre_recibo);
                                intent.putExtra("validacion_tarjeta", validacion_tarjeta);

                                startActivity(intent);
                                finish();
                            }
                        } else if (tarjetasUsuarioAdapter.getItem(0).getValidacionTarjeta().equals("Pin")) {
                            if (tarjetasUsuarioAdapter.getItem(0).getBanco_tarjeta().equals(banco_tarjeta)) {
                                Toast.makeText(SeleccionTarjetaCargo.this, "No puede pagar con una tarjeta del mismo banco", Toast.LENGTH_SHORT).show();
                            } else {
                                String num_tarjeta_cargo = tarjetasUsuarioAdapter.getItem(0).getNumeroTarjeta();
                                int emisor_tarjeta_cargo = tarjetasUsuarioAdapter.getItem(0).getCod_emisor_tarjeta();
                                int tipo_tarjeta_pago = tarjetasUsuarioAdapter.getItem(0).getTipo_tarjeta();
                                int cod_banco = tarjetasUsuarioAdapter.getItem(0).getBanco_tarjeta_registro();
                                String validacion_tarjeta = tarjetasUsuarioAdapter.getItem(0).getValidacionTarjeta();
                                Intent intent = new Intent(SeleccionTarjetaCargo.this, MontoPagoPinPagoServicios.class);
                                intent.putExtra("monto", monto);
                                intent.putExtra("usuario", usuario);
                                intent.putExtra("num_tarjeta", num_tarjeta_cargo);
                                intent.putExtra("emisor_tarjeta", emisor_tarjeta_cargo);
                                intent.putExtra("monto_servicio", monto_servicio);
                                intent.putExtra("servicio", servicio);
                                intent.putExtra("num_servicio", num_servicio);
                                intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                                intent.putExtra("cod_banco", cod_banco);
                                intent.putExtra("cliente", cliente);
                                intent.putExtra("tipo_servicio", tipo_servicio);
                                intent.putExtra("cli_dni", cli_dni);
                                intent.putExtra("nombre_recibo", nombre_recibo);
                                intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                                startActivity(intent);
                                finish();
                            }
                        }
                    } else {
                        if (tarjetasUsuarioAdapter.getItem(0).getBanco_tarjeta().equals(banco_tarjeta)) {
                            Toast.makeText(SeleccionTarjetaCargo.this, "No puede pagar con una tarjeta del mismo banco", Toast.LENGTH_SHORT).show();
                        } else {
                            String num_tarjeta_cargo = tarjetasUsuarioAdapter.getItem(0).getNumeroTarjeta();
                            int emisor_tarjeta_cargo = tarjetasUsuarioAdapter.getItem(0).getCod_emisor_tarjeta();
                            int tipo_tarjeta_pago = tarjetasUsuarioAdapter.getItem(0).getTipo_tarjeta();
                            int cod_banco = tarjetasUsuarioAdapter.getItem(0).getBanco_tarjeta_registro();
                            String validacion_tarjeta = tarjetasUsuarioAdapter.getItem(0).getValidacionTarjeta();
                            Intent intent = new Intent(SeleccionTarjetaCargo.this, MontoPagoPinPagoServicios.class);
                            intent.putExtra("monto", monto);
                            intent.putExtra("usuario", usuario);
                            intent.putExtra("num_tarjeta", num_tarjeta_cargo);
                            intent.putExtra("emisor_tarjeta", emisor_tarjeta_cargo);
                            intent.putExtra("monto_servicio", monto_servicio);
                            intent.putExtra("servicio", servicio);
                            intent.putExtra("num_servicio", num_servicio);
                            intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                            intent.putExtra("cod_banco", cod_banco);
                            intent.putExtra("cliente", cliente);
                            intent.putExtra("tipo_servicio", tipo_servicio);
                            intent.putExtra("cli_dni", cli_dni);
                            intent.putExtra("nombre_recibo", nombre_recibo);
                            intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            } else if (callingActivity.equals(Constante.ACTIVITYROOT + "RecargaTelefonica")) {
                if (usuarioEntityArrayList.size() == 1) {
                    if (tarjetasUsuarioAdapter.getItem(0).getTipo_tarjeta() == 1) {
                        if (tarjetasUsuarioAdapter.getItem(0).getValidacionTarjeta().equals("Firma")) {
                            banco = tarjetasUsuarioAdapter.getItem(0).getDesc_cortaBanco();
                            String emisor_tarjeta = tarjetasUsuarioAdapter.getItem(0).getDesc_cortaEmisorTarjeta();
                            String tarjeta_cargo = tarjetasUsuarioAdapter.getItem(0).getNumeroTarjeta();
                            int tipo_tarjeta_pago = tarjetasUsuarioAdapter.getItem(0).getTipo_tarjeta();
                            String validacion_tarjeta = tarjetasUsuarioAdapter.getItem(0).getValidacionTarjeta();
                            Intent intent = new Intent(SeleccionTarjetaCargo.this, IngresoMontoPagoFirmaRecarga.class);
                            intent.putExtra("usuario", usuario);
                            intent.putExtra("cliente", cliente);
                            intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                            intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                            intent.putExtra("banco", banco);
                            intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                            intent.putExtra("cli_dni", cli_dni);
                            intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                            intent.putExtra("nro_telefono", nro_telefono);
                            intent.putExtra("tipo_moneda_recarga", tipo_moneda_recarga);
                            intent.putExtra("tipo_operador", tipo_operador);
                            intent.putExtra("monto_recarga", monto_recarga);
                            startActivity(intent);
                            finish();
                        } else if (tarjetasUsuarioAdapter.getItem(0).getValidacionTarjeta().equals("Pin")) {
                            banco = tarjetasUsuarioAdapter.getItem(0).getDesc_cortaBanco();
                            String emisor_tarjeta = tarjetasUsuarioAdapter.getItem(0).getDesc_cortaEmisorTarjeta();
                            String tarjeta_cargo = tarjetasUsuarioAdapter.getItem(0).getNumeroTarjeta();
                            int tipo_tarjeta_pago = tarjetasUsuarioAdapter.getItem(0).getTipo_tarjeta();
                            String validacion_tarjeta = tarjetasUsuarioAdapter.getItem(0).getValidacionTarjeta();
                            Intent intent = new Intent(SeleccionTarjetaCargo.this, IngresoMontoPagoPinRecargas.class);
                            intent.putExtra("usuario", usuario);
                            intent.putExtra("cliente", cliente);
                            intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                            intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                            intent.putExtra("banco", banco);
                            intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                            intent.putExtra("cli_dni", cli_dni);
                            intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                            intent.putExtra("nro_telefono", nro_telefono);
                            intent.putExtra("tipo_moneda_recarga", tipo_moneda_recarga);
                            intent.putExtra("tipo_operador", tipo_operador);
                            intent.putExtra("monto_recarga", monto_recarga);
                            startActivity(intent);
                            finish();
                        }
                    } else if (tarjetasUsuarioAdapter.getItem(0).getTipo_tarjeta() == 2) {
                        banco = tarjetasUsuarioAdapter.getItem(0).getDesc_cortaBanco();
                        String emisor_tarjeta = tarjetasUsuarioAdapter.getItem(0).getDesc_cortaEmisorTarjeta();
                        String tarjeta_cargo = tarjetasUsuarioAdapter.getItem(0).getNumeroTarjeta();
                        int tipo_tarjeta_pago = tarjetasUsuarioAdapter.getItem(0).getTipo_tarjeta();
                        String validacion_tarjeta = tarjetasUsuarioAdapter.getItem(0).getValidacionTarjeta();
                        Intent intent = new Intent(SeleccionTarjetaCargo.this, IngresoMontoPagoPinRecargas.class);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("cliente", cliente);
                        intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                        intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                        intent.putExtra("banco", banco);
                        intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                        intent.putExtra("cli_dni", cli_dni);
                        intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                        intent.putExtra("nro_telefono", nro_telefono);
                        intent.putExtra("tipo_moneda_recarga", tipo_moneda_recarga);
                        intent.putExtra("tipo_operador", tipo_operador);
                        intent.putExtra("monto_recarga", monto_recarga);
                        startActivity(intent);
                        finish();


                    }
                }
            } else if (callingActivity.equals(Constante.ACTIVITYROOT + "LecturaInformacionComercio")){
                if (usuarioEntityArrayList.size() == 1){
                    if (tarjetasUsuarioAdapter.getItem(0).getTipo_tarjeta() == 1) {
                        if (tarjetasUsuarioAdapter.getItem(0).getValidacionTarjeta().equals("Firma")) {
                            banco = tarjetasUsuarioAdapter.getItem(0).getDesc_cortaBanco();
                            String emisor_tarjeta = tarjetasUsuarioAdapter.getItem(0).getDesc_cortaEmisorTarjeta();
                            String tarjeta_cargo = tarjetasUsuarioAdapter.getItem(0).getNumeroTarjeta();
                            int tipo_tarjeta_pago = tarjetasUsuarioAdapter.getItem(0).getTipo_tarjeta();
                            String validacion_tarjeta = tarjetasUsuarioAdapter.getItem(0).getValidacionTarjeta();
                            Intent intent = new Intent(SeleccionTarjetaCargo.this, IngresoMontoPagoFirmaComercio.class);
                            intent.putExtra("usuario", usuario);
                            intent.putExtra("cliente", cliente);
                            intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                            intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                            intent.putExtra("banco", banco);
                            intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                            intent.putExtra("cli_dni", cli_dni);
                            intent.putExtra("cadena_scanneo", cadena_scanneo);
                            intent.putExtra("nom_comerciosp", nom_comerciosp);
                            intent.putExtra("direccion_comerciosp", direccion_comerciosp);
                            intent.putExtra("distrito_comerciosp", distrito_comerciosp);
                            intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                            startActivity(intent);
                            finish();
                        } else if (tarjetasUsuarioAdapter.getItem(0).getValidacionTarjeta().equals("Pin")) {
                            banco = tarjetasUsuarioAdapter.getItem(0).getDesc_cortaBanco();
                            String emisor_tarjeta = tarjetasUsuarioAdapter.getItem(0).getDesc_cortaEmisorTarjeta();
                            String tarjeta_cargo = tarjetasUsuarioAdapter.getItem(0).getNumeroTarjeta();
                            int tipo_tarjeta_pago = tarjetasUsuarioAdapter.getItem(0).getTipo_tarjeta();
                            String validacion_tarjeta = tarjetasUsuarioAdapter.getItem(0).getValidacionTarjeta();
                            Intent intent = new Intent(SeleccionTarjetaCargo.this, IngresoMontoPagoPinConsumos.class);
                            intent.putExtra("usuario", usuario);
                            intent.putExtra("cliente", cliente);
                            intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                            intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                            intent.putExtra("banco", banco);
                            intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                            intent.putExtra("cli_dni", cli_dni);
                            intent.putExtra("cadena_scanneo", cadena_scanneo);
                            intent.putExtra("nom_comerciosp", nom_comerciosp);
                            intent.putExtra("direccion_comerciosp", direccion_comerciosp);
                            intent.putExtra("distrito_comerciosp", distrito_comerciosp);
                            intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                            startActivity(intent);
                            finish();
                        }

                    } else if (tarjetasUsuarioAdapter.getItem(0).getTipo_tarjeta() == 2) {
                        banco = tarjetasUsuarioAdapter.getItem(0).getDesc_cortaBanco();
                        String emisor_tarjeta = tarjetasUsuarioAdapter.getItem(0).getDesc_cortaEmisorTarjeta();
                        String tarjeta_cargo = tarjetasUsuarioAdapter.getItem(0).getNumeroTarjeta();
                        int tipo_tarjeta_pago = tarjetasUsuarioAdapter.getItem(0).getTipo_tarjeta();
                        String validacion_tarjeta = tarjetasUsuarioAdapter.getItem(0).getValidacionTarjeta();
                        Intent intent = new Intent(SeleccionTarjetaCargo.this, IngresoMontoPagoPinConsumos.class);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("cliente", cliente);
                        intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                        intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                        intent.putExtra("banco", banco);
                        intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                        intent.putExtra("cli_dni", cli_dni);
                        intent.putExtra("cadena_scanneo", cadena_scanneo);
                        intent.putExtra("nom_comerciosp", nom_comerciosp);
                        intent.putExtra("direccion_comerciosp", direccion_comerciosp);
                        intent.putExtra("distrito_comerciosp", distrito_comerciosp);
                        intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                        startActivity(intent);
                        finish();

                    }
                }
            }
        }
    }

    public void cancelar() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Está seguro que desea cancelar la transacción?");
        alertDialog.setTitle("Cancelar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(SeleccionTarjetaCargo.this, MenuCliente.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                intent.putExtra("cli_dni", cli_dni);
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

    public void actionSeleccionRecibosPagar() {
        Bundle extras = getIntent().getExtras();
        usuario = extras.getParcelable("usuario");
        monto_servicio = extras.getString("monto_servicio");
        servicio = extras.getString("servicio");
        num_servicio = extras.getString("num_servicio");
        cliente = extras.getString("cliente");
        tipo_servicio = extras.getString("tipo_servicio");
        cli_dni = extras.getString("cli_dni");
        nombre_recibo = extras.getString("nombre_recibo");

        usuarioEntityArrayList = null;
        tarjetasUsuarioAdapter = new TarjetasUsuarioAdapter(usuarioEntityArrayList, getApplication());
        lv_tarjetas_cargo_usuario.setAdapter(tarjetasUsuarioAdapter);

        ejecutarLista();

        circleProgressBar.setVisibility(View.VISIBLE);

        lv_tarjetas_cargo_usuario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (tarjetasUsuarioAdapter.getItem(position).getTipo_tarjeta() == 1) {
                    if (tarjetasUsuarioAdapter.getItem(position).getValidacionTarjeta().equals("Firma")) {
                        if (tarjetasUsuarioAdapter.getItem(position).getBanco_tarjeta().equals(banco_tarjeta)) {
                            Toast.makeText(SeleccionTarjetaCargo.this, "No puede pagar con una tarjeta del mismo banco", Toast.LENGTH_SHORT).show();
                        } else {
                            String num_tarjeta_cargo = tarjetasUsuarioAdapter.getItem(position).getNumeroTarjeta();
                            int tipo_tarjeta_pago = tarjetasUsuarioAdapter.getItem(position).getTipo_tarjeta();
                            int emisor_tarjeta_cargo = tarjetasUsuarioAdapter.getItem(position).getCod_emisor_tarjeta();
                            int cod_banco = tarjetasUsuarioAdapter.getItem(position).getBanco_tarjeta_registro();
                            String validacion_tarjeta = tarjetasUsuarioAdapter.getItem(position).getValidacionTarjeta();
                            Intent intent = new Intent(SeleccionTarjetaCargo.this, IngresoMontoPagoFirmaServicios.class);
                            intent.putExtra("monto", monto);
                            intent.putExtra("usuario", usuario);
                            intent.putExtra("num_tarjeta", num_tarjeta_cargo);
                            intent.putExtra("emisor_tarjeta", emisor_tarjeta_cargo);
                            intent.putExtra("monto_servicio", monto_servicio);
                            intent.putExtra("servicio", servicio);
                            intent.putExtra("num_servicio", num_servicio);
                            intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                            intent.putExtra("cod_banco", cod_banco);
                            intent.putExtra("cliente", cliente);
                            intent.putExtra("tipo_servicio", tipo_servicio);
                            intent.putExtra("cli_dni", cli_dni);
                            intent.putExtra("cod_banco", cod_banco);
                            intent.putExtra("nombre_recibo", nombre_recibo);
                            intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                            startActivity(intent);
                            finish();
                        }
                    } else if (tarjetasUsuarioAdapter.getItem(position).getValidacionTarjeta().equals("Pin")) {
                        if (tarjetasUsuarioAdapter.getItem(position).getBanco_tarjeta().equals(banco_tarjeta)) {
                            Toast.makeText(SeleccionTarjetaCargo.this, "No puede pagar con una tarjeta del mismo banco", Toast.LENGTH_SHORT).show();
                        } else {
                            String num_tarjeta_cargo = tarjetasUsuarioAdapter.getItem(position).getNumeroTarjeta();
                            int emisor_tarjeta_cargo = tarjetasUsuarioAdapter.getItem(position).getCod_emisor_tarjeta();
                            int tipo_tarjeta_pago = tarjetasUsuarioAdapter.getItem(position).getTipo_tarjeta();
                            int cod_banco = tarjetasUsuarioAdapter.getItem(position).getBanco_tarjeta_registro();
                            String validacion_tarjeta = tarjetasUsuarioAdapter.getItem(position).getValidacionTarjeta();
                            Intent intent = new Intent(SeleccionTarjetaCargo.this, MontoPagoPinPagoServicios.class);
                            intent.putExtra("monto", monto);
                            intent.putExtra("usuario", usuario);
                            intent.putExtra("num_tarjeta", num_tarjeta_cargo);
                            intent.putExtra("emisor_tarjeta", emisor_tarjeta_cargo);
                            intent.putExtra("monto_servicio", monto_servicio);
                            intent.putExtra("servicio", servicio);
                            intent.putExtra("num_servicio", num_servicio);
                            intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                            intent.putExtra("cod_banco", cod_banco);
                            intent.putExtra("cliente", cliente);
                            intent.putExtra("tipo_servicio", tipo_servicio);
                            intent.putExtra("cli_dni", cli_dni);
                            intent.putExtra("nombre_recibo", nombre_recibo);
                            intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                            startActivity(intent);
                            finish();
                        }
                    }
                } else {
                    if (tarjetasUsuarioAdapter.getItem(position).getBanco_tarjeta().equals(banco_tarjeta)) {
                        Toast.makeText(SeleccionTarjetaCargo.this, "No puede pagar con una tarjeta del mismo banco", Toast.LENGTH_SHORT).show();
                    } else {
                        String num_tarjeta_cargo = tarjetasUsuarioAdapter.getItem(position).getNumeroTarjeta();
                        int emisor_tarjeta_cargo = tarjetasUsuarioAdapter.getItem(position).getCod_emisor_tarjeta();
                        int tipo_tarjeta_pago = tarjetasUsuarioAdapter.getItem(position).getTipo_tarjeta();
                        int cod_banco = tarjetasUsuarioAdapter.getItem(position).getBanco_tarjeta_registro();
                        String validacion_tarjeta = tarjetasUsuarioAdapter.getItem(position).getValidacionTarjeta();
                        Intent intent = new Intent(SeleccionTarjetaCargo.this, MontoPagoPinPagoServicios.class);
                        intent.putExtra("monto", monto);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("num_tarjeta", num_tarjeta_cargo);
                        intent.putExtra("emisor_tarjeta", emisor_tarjeta_cargo);
                        intent.putExtra("monto_servicio", monto_servicio);
                        intent.putExtra("servicio", servicio);
                        intent.putExtra("num_servicio", num_servicio);
                        intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                        intent.putExtra("cod_banco", cod_banco);
                        intent.putExtra("cliente", cliente);
                        intent.putExtra("tipo_servicio", tipo_servicio);
                        intent.putExtra("nombre_recibo", nombre_recibo);
                        intent.putExtra("cli_dni", cli_dni);
                        intent.putExtra("nombre_recibo", nombre_recibo);
                        intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    public void actionSeleccionModoMontoPago() {

        Bundle extras = getIntent().getExtras();
        monto = extras.getString("monto");
        usuario = extras.getParcelable("usuario");
        num_tarjeta = extras.getString("num_tarjeta");
        tipo_tarjeta = extras.getInt("tipo_tarjeta");
        emisor_tarjeta = extras.getInt("emisor_tarjeta");
        banco_tarjeta = extras.getString("banco_tarjeta");
        tipo_moneda_deuda = extras.getString("tipo_moneda_deuda");
        cliente = extras.getString("cliente");
        cli_dni = extras.getString("cli_dni");
        desc_corta_banco = extras.getString("desc_corta_banco");

        usuarioEntityArrayList = null;
        tarjetasUsuarioAdapter = new TarjetasUsuarioAdapter(usuarioEntityArrayList, getApplication());
        lv_tarjetas_cargo_usuario.setAdapter(tarjetasUsuarioAdapter);

        ejecutarLista();

        circleProgressBar.setVisibility(View.VISIBLE);

        lv_tarjetas_cargo_usuario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (tarjetasUsuarioAdapter.getItem(position).getTipo_tarjeta() == 1) {
                    if (tarjetasUsuarioAdapter.getItem(position).getValidacionTarjeta().equals("Firma")) {
                        if (tarjetasUsuarioAdapter.getItem(position).getBanco_tarjeta().equals(banco_tarjeta)) {
                            Toast.makeText(SeleccionTarjetaCargo.this, "No puede pagar con una tarjeta del mismo banco", Toast.LENGTH_SHORT).show();
                        } else {
                            banco = tarjetasUsuarioAdapter.getItem(position).getBanco_tarjeta();
                            //emisor_tarjeta = tarjetasUsuarioAdapter.getItem(position).getCod_emisor_tarjeta();
                            String tarjeta_cargo = tarjetasUsuarioAdapter.getItem(position).getNumeroTarjeta();
                            String desc_corta_banco_tarjeta_cargo = tarjetasUsuarioAdapter.getItem(position).getDesc_cortaBanco();
                            String validacion_tarjeta = tarjetasUsuarioAdapter.getItem(position).getValidacionTarjeta();
                            int tipo_tarjeta_pago = tarjetasUsuarioAdapter.getItem(position).getTipo_tarjeta();
                            Intent intent = new Intent(SeleccionTarjetaCargo.this, ConfirmacionTarjetaCargo.class);
                            intent.putExtra("monto", monto);
                            intent.putExtra("usuario", usuario);
                            intent.putExtra("num_tarjeta", num_tarjeta);
                            intent.putExtra("tipo_tarjeta", tipo_tarjeta);
                            intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                            intent.putExtra("tipo_moneda_deuda", tipo_moneda_deuda);
                            intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                            intent.putExtra("cliente", cliente);
                            intent.putExtra("cli_dni", cli_dni);
                            intent.putExtra("desc_corta_banco", desc_corta_banco);
                            intent.putExtra("desc_corta_banco_tarjeta_cargo", desc_corta_banco_tarjeta_cargo);
                            intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                            intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                            startActivity(intent);
                            finish();
                        }
                    } else if (tarjetasUsuarioAdapter.getItem(position).getValidacionTarjeta().equals("Pin")) {
                        if (tarjetasUsuarioAdapter.getItem(position).getBanco_tarjeta().equals(banco_tarjeta)) {
                            Toast.makeText(SeleccionTarjetaCargo.this, "No puede pagar con una tarjeta del mismo banco", Toast.LENGTH_SHORT).show();
                        } else {
                            String tarjeta_cargo = tarjetasUsuarioAdapter.getItem(position).getNumeroTarjeta();
                            String desc_corta_banco_tarjeta_cargo = tarjetasUsuarioAdapter.getItem(position).getDesc_cortaBanco();
                            String validacion_tarjeta = tarjetasUsuarioAdapter.getItem(position).getValidacionTarjeta();
                            int tipo_tarjeta_pago = tarjetasUsuarioAdapter.getItem(position).getTipo_tarjeta();
                            Intent intent = new Intent(SeleccionTarjetaCargo.this, IngresoMontoPagoPin.class);
                            intent.putExtra("monto", monto);
                            intent.putExtra("usuario", usuario);
                            intent.putExtra("num_tarjeta", num_tarjeta);
                            intent.putExtra("tipo_tarjeta", tipo_tarjeta);
                            intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                            intent.putExtra("tipo_moneda_deuda", tipo_moneda_deuda);
                            intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                            intent.putExtra("cliente", cliente);
                            intent.putExtra("cli_dni", cli_dni);
                            intent.putExtra("desc_corta_banco", desc_corta_banco);
                            intent.putExtra("desc_corta_banco_tarjeta_cargo", desc_corta_banco_tarjeta_cargo);
                            intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                            intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                            startActivity(intent);
                            finish();
                        }
                    }
                } else if (tarjetasUsuarioAdapter.getItem(position).getTipo_tarjeta() == 2) {
                    if (tarjetasUsuarioAdapter.getItem(position).getBanco_tarjeta().equals(banco_tarjeta)) {
                        Toast.makeText(SeleccionTarjetaCargo.this, "No puede pagar con una tarjeta del mismo banco", Toast.LENGTH_SHORT).show();
                    } else {
                        String tarjeta_cargo = tarjetasUsuarioAdapter.getItem(position).getNumeroTarjeta();
                        String desc_corta_banco_tarjeta_cargo = tarjetasUsuarioAdapter.getItem(position).getDesc_cortaBanco();
                        int tipo_tarjeta_pago = tarjetasUsuarioAdapter.getItem(position).getTipo_tarjeta();
                        String validacion_tarjeta = tarjetasUsuarioAdapter.getItem(position).getValidacionTarjeta();
                        Intent intent = new Intent(SeleccionTarjetaCargo.this, IngresoMontoPagoPin.class);
                        intent.putExtra("monto", monto);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("num_tarjeta", num_tarjeta);
                        intent.putExtra("tipo_tarjeta", tipo_tarjeta);
                        intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                        intent.putExtra("tipo_moneda_deuda", tipo_moneda_deuda);
                        intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                        intent.putExtra("cliente", cliente);
                        intent.putExtra("cli_dni", cli_dni);
                        intent.putExtra("desc_corta_banco", desc_corta_banco);
                        intent.putExtra("desc_corta_banco_tarjeta_cargo", desc_corta_banco_tarjeta_cargo);
                        intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                        intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(SeleccionTarjetaCargo.this, "Hubo un error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void actionPagoComercio() {

        Bundle extras = getIntent().getExtras();
        usuario = extras.getParcelable("usuario");
        cliente = extras.getString("cliente");
        cli_dni = extras.getString("cli_dni");
        cadena_scanneo = extras.getString("cadena_scanneo");
        /*nom_comerciosp = extras.getString("nom_comerciosp");
        direccion_comerciosp = extras.getString("direccion_comerciosp");
        distrito_comerciosp = extras.getString("distrito_comerciosp");*/

        usuarioEntityArrayList = null;
        tarjetasUsuarioAdapter = new TarjetasUsuarioAdapter(usuarioEntityArrayList, getApplication());
        lv_tarjetas_cargo_usuario.setAdapter(tarjetasUsuarioAdapter);

        ejecutarLista();

        circleProgressBar.setVisibility(View.VISIBLE);

        lv_tarjetas_cargo_usuario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (tarjetasUsuarioAdapter.getItem(position).getTipo_tarjeta() == 1) {
                    if (tarjetasUsuarioAdapter.getItem(position).getValidacionTarjeta().equals("Firma")) {
                        banco = tarjetasUsuarioAdapter.getItem(position).getDesc_cortaBanco();
                        String emisor_tarjeta = tarjetasUsuarioAdapter.getItem(position).getDesc_cortaEmisorTarjeta();
                        String tarjeta_cargo = tarjetasUsuarioAdapter.getItem(position).getNumeroTarjeta();
                        int tipo_tarjeta_pago = tarjetasUsuarioAdapter.getItem(position).getTipo_tarjeta();
                        String validacion_tarjeta = tarjetasUsuarioAdapter.getItem(position).getValidacionTarjeta();
                        Intent intent = new Intent(SeleccionTarjetaCargo.this, IngresoMontoPagoFirmaComercio.class);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("cliente", cliente);
                        intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                        intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                        intent.putExtra("banco", banco);
                        intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                        intent.putExtra("cli_dni", cli_dni);
                        intent.putExtra("cadena_scanneo", cadena_scanneo);
                        /*intent.putExtra("nom_comerciosp", nom_comerciosp);
                        intent.putExtra("direccion_comerciosp", direccion_comerciosp);
                        intent.putExtra("distrito_comerciosp", distrito_comerciosp);*/
                        intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                        startActivity(intent);
                        finish();
                    } else if (tarjetasUsuarioAdapter.getItem(position).getValidacionTarjeta().equals("Pin")) {
                        banco = tarjetasUsuarioAdapter.getItem(position).getDesc_cortaBanco();
                        String emisor_tarjeta = tarjetasUsuarioAdapter.getItem(position).getDesc_cortaEmisorTarjeta();
                        String tarjeta_cargo = tarjetasUsuarioAdapter.getItem(position).getNumeroTarjeta();
                        int tipo_tarjeta_pago = tarjetasUsuarioAdapter.getItem(position).getTipo_tarjeta();
                        String validacion_tarjeta = tarjetasUsuarioAdapter.getItem(position).getValidacionTarjeta();
                        Intent intent = new Intent(SeleccionTarjetaCargo.this, IngresoMontoPagoPinConsumos.class);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("cliente", cliente);
                        intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                        intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                        intent.putExtra("banco", banco);
                        intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                        intent.putExtra("cli_dni", cli_dni);
                        intent.putExtra("cadena_scanneo", cadena_scanneo);
                        /*intent.putExtra("nom_comerciosp", nom_comerciosp);
                        intent.putExtra("direccion_comerciosp", direccion_comerciosp);
                        intent.putExtra("distrito_comerciosp", distrito_comerciosp);*/
                        intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                        startActivity(intent);
                        finish();
                    }


                } else if (tarjetasUsuarioAdapter.getItem(position).getTipo_tarjeta() == 2) {
                    banco = tarjetasUsuarioAdapter.getItem(position).getDesc_cortaBanco();
                    String emisor_tarjeta = tarjetasUsuarioAdapter.getItem(position).getDesc_cortaEmisorTarjeta();
                    String tarjeta_cargo = tarjetasUsuarioAdapter.getItem(position).getNumeroTarjeta();
                    int tipo_tarjeta_pago = tarjetasUsuarioAdapter.getItem(position).getTipo_tarjeta();
                    String validacion_tarjeta = tarjetasUsuarioAdapter.getItem(position).getValidacionTarjeta();
                    Intent intent = new Intent(SeleccionTarjetaCargo.this, IngresoMontoPagoPinConsumos.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                    intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                    intent.putExtra("banco", banco);
                    intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                    intent.putExtra("cli_dni", cli_dni);
                    intent.putExtra("cadena_scanneo", cadena_scanneo);
                    /*intent.putExtra("nom_comerciosp", nom_comerciosp);
                    intent.putExtra("direccion_comerciosp", direccion_comerciosp);
                    intent.putExtra("distrito_comerciosp", distrito_comerciosp);*/
                    intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(SeleccionTarjetaCargo.this, "Hubo un error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void actionPagoRecarga() {

        Bundle extras = getIntent().getExtras();

        usuario = extras.getParcelable("usuario");
        cliente = extras.getString("cliente");
        cli_dni = extras.getString("cli_dni");
        nro_telefono = extras.getString("nro_telefono");
        tipo_moneda_recarga = extras.getString("tipo_moneda");
        tipo_operador = extras.getString("tipo_operador");
        monto_recarga = extras.getDouble("monto_recarga");
        nombre_recibo = extras.getString("nombre_recibo");

        usuarioEntityArrayList = null;
        tarjetasUsuarioAdapter = new TarjetasUsuarioAdapter(usuarioEntityArrayList, getApplication());
        lv_tarjetas_cargo_usuario.setAdapter(tarjetasUsuarioAdapter);

        ejecutarLista();

        circleProgressBar.setVisibility(View.VISIBLE);

        lv_tarjetas_cargo_usuario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (tarjetasUsuarioAdapter.getItem(position).getTipo_tarjeta() == 1) {
                    if (tarjetasUsuarioAdapter.getItem(position).getValidacionTarjeta().equals("Firma")) {
                        banco = tarjetasUsuarioAdapter.getItem(position).getDesc_cortaBanco();
                        String emisor_tarjeta = tarjetasUsuarioAdapter.getItem(position).getDesc_cortaEmisorTarjeta();
                        String tarjeta_cargo = tarjetasUsuarioAdapter.getItem(position).getNumeroTarjeta();
                        int tipo_tarjeta_pago = tarjetasUsuarioAdapter.getItem(position).getTipo_tarjeta();
                        String validacion_tarjeta = tarjetasUsuarioAdapter.getItem(position).getValidacionTarjeta();
                        Intent intent = new Intent(SeleccionTarjetaCargo.this, IngresoMontoPagoFirmaRecarga.class);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("cliente", cliente);
                        intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                        intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                        intent.putExtra("banco", banco);
                        intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                        intent.putExtra("cli_dni", cli_dni);
                        intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                        intent.putExtra("nro_telefono", nro_telefono);
                        intent.putExtra("tipo_moneda_recarga", tipo_moneda_recarga);
                        intent.putExtra("tipo_operador", tipo_operador);
                        intent.putExtra("monto_recarga", monto_recarga);
                        startActivity(intent);
                        finish();
                    } else if (tarjetasUsuarioAdapter.getItem(position).getValidacionTarjeta().equals("Pin")) {
                        banco = tarjetasUsuarioAdapter.getItem(position).getDesc_cortaBanco();
                        String emisor_tarjeta = tarjetasUsuarioAdapter.getItem(position).getDesc_cortaEmisorTarjeta();
                        String tarjeta_cargo = tarjetasUsuarioAdapter.getItem(position).getNumeroTarjeta();
                        int tipo_tarjeta_pago = tarjetasUsuarioAdapter.getItem(position).getTipo_tarjeta();
                        String validacion_tarjeta = tarjetasUsuarioAdapter.getItem(position).getValidacionTarjeta();
                        Intent intent = new Intent(SeleccionTarjetaCargo.this, IngresoMontoPagoPinRecargas.class);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("cliente", cliente);
                        intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                        intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                        intent.putExtra("banco", banco);
                        intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                        intent.putExtra("cli_dni", cli_dni);
                        intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                        intent.putExtra("nro_telefono", nro_telefono);
                        intent.putExtra("tipo_moneda_recarga", tipo_moneda_recarga);
                        intent.putExtra("tipo_operador", tipo_operador);
                        intent.putExtra("monto_recarga", monto_recarga);
                        startActivity(intent);
                        finish();
                    }


                } else if (tarjetasUsuarioAdapter.getItem(position).getTipo_tarjeta() == 2) {
                    banco = tarjetasUsuarioAdapter.getItem(position).getDesc_cortaBanco();
                    String emisor_tarjeta = tarjetasUsuarioAdapter.getItem(position).getDesc_cortaEmisorTarjeta();
                    String tarjeta_cargo = tarjetasUsuarioAdapter.getItem(position).getNumeroTarjeta();
                    int tipo_tarjeta_pago = tarjetasUsuarioAdapter.getItem(position).getTipo_tarjeta();
                    String validacion_tarjeta = tarjetasUsuarioAdapter.getItem(position).getValidacionTarjeta();
                    Intent intent = new Intent(SeleccionTarjetaCargo.this, IngresoMontoPagoPinRecargas.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("tarjeta_cargo", tarjeta_cargo);
                    intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                    intent.putExtra("banco", banco);
                    intent.putExtra("tipo_tarjeta_pago", tipo_tarjeta_pago);
                    intent.putExtra("cli_dni", cli_dni);
                    intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                    intent.putExtra("nro_telefono", nro_telefono);
                    intent.putExtra("tipo_moneda_recarga", tipo_moneda_recarga);
                    intent.putExtra("tipo_operador", tipo_operador);
                    intent.putExtra("monto_recarga", monto_recarga);
                    startActivity(intent);
                    finish();


                } else {
                    Toast.makeText(SeleccionTarjetaCargo.this, "Hubo un error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}