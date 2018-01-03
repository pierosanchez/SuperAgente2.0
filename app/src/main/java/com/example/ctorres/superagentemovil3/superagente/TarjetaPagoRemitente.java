package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.adapter.TarjetasUsuarioAdapter;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;

public class TarjetaPagoRemitente extends Activity {

    ImageView img_tarjeta_seleccion_cargo, img_atras, img_adelante;
    int[] tarjetaid = {R.drawable.bin424137scotiabankjockeyvisa, R.drawable.bin377754interbankamexblueimgtaramexbluefc_jpg, R.drawable.bin457562scotiabankjockeyvisaplatinum, R.drawable.bin510308scotiabankmcpremiumnew, R.drawable.bin516003scotiabankmcdebitogold, R.drawable.bin545545scotiabankmastercardclassic, R.drawable.bin552271scotiabankmastercardblack, R.drawable.bin554911scotiabankmastercardplatinum};
    String[] tipotarjetas = {"Crédito", "Débito", "Débito", "Débito", "Crédito", "Débito", "Crédito", "Crédito"};
    int i = 0;
    int total;
    TextView tv_tipo_tarjeta;
    UsuarioEntity usuario;
    String nombreBeneficiario, dni_benef, num_tarjeta, banco;
    String usu;
    int emisor_tarjeta;
    TarjetasUsuarioAdapter tarjetasUsuarioAdapter;
    ArrayList<UsuarioEntity> usuarioEntityArrayList;
    ListView lv_tarjeta_cargo_remitente;
    String cliente, cli_dni;
    private ProgressBar circleProgressBar;
    Button btn_regresar, btn_cancelar_seleccion_tarjeta_cargo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarjeta_pago_remitente);

        /*img_tarjeta_seleccion_cargo = (ImageView) findViewById(R.id.img_tarjeta_seleccion_cargo);
        img_adelante = (ImageView) findViewById(R.id.img_adelante);
        img_atras = (ImageView) findViewById(R.id.img_atras);*/

        btn_regresar = (Button) findViewById(R.id.btn_regresar);
        btn_cancelar_seleccion_tarjeta_cargo = (Button) findViewById(R.id.btn_cancelar_seleccion_tarjeta_cargo);

        lv_tarjeta_cargo_remitente = (ListView) findViewById(R.id.lv_tarjeta_cargo_remitente);

        circleProgressBar = (ProgressBar) findViewById(R.id.circleProgressBar);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");
        nombreBeneficiario = bundle.getString("nombrebenef");
        dni_benef = bundle.getString("dni_benef");
        cliente = bundle.getString("cliente");
        cli_dni = bundle.getString("cli_dni");


        usuarioEntityArrayList = null;
        tarjetasUsuarioAdapter = new TarjetasUsuarioAdapter(usuarioEntityArrayList, getApplication());
        lv_tarjeta_cargo_remitente.setAdapter(tarjetasUsuarioAdapter);

        ejecutarLista();

        circleProgressBar.setVisibility(View.VISIBLE);

        lv_tarjeta_cargo_remitente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                emisor_tarjeta = tarjetasUsuarioAdapter.getItem(position).getCod_emisor_tarjeta();
                num_tarjeta = tarjetasUsuarioAdapter.getItem(position).getNumeroTarjeta();
                banco = tarjetasUsuarioAdapter.getItem(position).getBanco_tarjeta();
                int tipo_tarjeta = tarjetasUsuarioAdapter.getItem(position).getTipo_tarjeta();
                String validacion_tarjeta = tarjetasUsuarioAdapter.getItem(position).getValidacionTarjeta();
                if (tarjetasUsuarioAdapter.getItem(position).getTipo_tarjeta() == 2) {
                    Intent intent = new Intent(TarjetaPagoRemitente.this, IngresoMontoPagoPinTransferencias.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("nombrebenef", nombreBeneficiario);
                    intent.putExtra("dni_benef", dni_benef);
                    intent.putExtra("num_tarjeta", num_tarjeta);
                    intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                    intent.putExtra("banco", banco);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("tipo_tarjeta", tipo_tarjeta);
                    intent.putExtra("cli_dni", cli_dni);
                    intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                    startActivity(intent);
                    finish();
                } else {
                    if (tarjetasUsuarioAdapter.getItem(position).getValidacionTarjeta().equals("Firma")) {
                        Intent intent = new Intent(TarjetaPagoRemitente.this, IngresoMontoPagoFirmaTransferencias.class);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("nombrebenef", nombreBeneficiario);
                        intent.putExtra("dni_benef", dni_benef);
                        intent.putExtra("num_tarjeta", num_tarjeta);
                        intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                        intent.putExtra("banco", banco);
                        intent.putExtra("cliente", cliente);
                        intent.putExtra("tipo_tarjeta", tipo_tarjeta);
                        intent.putExtra("cli_dni", cli_dni);
                        intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                        startActivity(intent);
                        finish();
                    } else if (tarjetasUsuarioAdapter.getItem(position).getValidacionTarjeta().equals("Pin")){
                        Intent intent = new Intent(TarjetaPagoRemitente.this, IngresoMontoPagoPinTransferencias.class);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("nombrebenef", nombreBeneficiario);
                        intent.putExtra("dni_benef", dni_benef);
                        intent.putExtra("num_tarjeta", num_tarjeta);
                        intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                        intent.putExtra("banco", banco);
                        intent.putExtra("cliente", cliente);
                        intent.putExtra("tipo_tarjeta", tipo_tarjeta);
                        intent.putExtra("cli_dni", cli_dni);
                        intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        btn_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TarjetaPagoRemitente.this, RelacionBeneficiarios.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cli_dni", cli_dni);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });

        btn_cancelar_seleccion_tarjeta_cargo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });

        /*total = tarjetaid.length;

        tv_tipo_tarjeta = (TextView) findViewById(R.id.tv_tipo_tarjeta);

        img_tarjeta_seleccion_cargo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable)img_tarjeta_seleccion_cargo.getDrawable()).getBitmap();
                Intent intent = new Intent(TarjetaPagoRemitente.this, IngresoMontoPagoPinTransferencias.class);
                intent.putExtra("imagebitmap", bitmap);
                intent.putExtra("usuario", usuario);
                intent.putExtra("nombrebenef", nombreBeneficiario);
                startActivity(intent);
                finish();
            }
        });

        img_adelante.setOnClickListener(this);

        img_atras.setOnClickListener(this);*/
    }

    /*public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.img_adelante) {
            i--;
            if (i == -1) {
                i = total - 1;
            }
        }

        if (id == R.id.img_atras) {
            i++;
            if (i == total) {
                i = 0;
            }
        }
        img_tarjeta_seleccion_cargo.setImageResource(tarjetaid[i]);
        tv_tipo_tarjeta.setText(tipotarjetas[i]);
    }*/

    private void ejecutarLista(){
        usu = usuario.getUsuarioId();

        try {
            TarjetaPagoRemitente.ListadoTarjetas listadoBeneficiario = new TarjetaPagoRemitente.ListadoTarjetas();
            listadoBeneficiario.execute();
        } catch (Exception e){
            //listadoBeneficiario = null;
        }

    }

    private class ListadoTarjetas extends AsyncTask<String,Void,Void> {
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
            tarjetasUsuarioAdapter.setNewListBeneficiario(usuarioEntityArrayList);
            tarjetasUsuarioAdapter.notifyDataSetChanged();
            circleProgressBar.setVisibility(View.GONE);
            if (usuarioEntityArrayList.size() == 1){
                emisor_tarjeta = tarjetasUsuarioAdapter.getItem(0).getCod_emisor_tarjeta();
                num_tarjeta = tarjetasUsuarioAdapter.getItem(0).getNumeroTarjeta();
                banco = tarjetasUsuarioAdapter.getItem(0).getBanco_tarjeta();
                int tipo_tarjeta = tarjetasUsuarioAdapter.getItem(0).getTipo_tarjeta();
                String validacion_tarjeta = tarjetasUsuarioAdapter.getItem(0).getValidacionTarjeta();
                if (tarjetasUsuarioAdapter.getItem(0).getTipo_tarjeta() == 2) {
                    Intent intent = new Intent(TarjetaPagoRemitente.this, IngresoMontoPagoPinTransferencias.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("nombrebenef", nombreBeneficiario);
                    intent.putExtra("dni_benef", dni_benef);
                    intent.putExtra("num_tarjeta", num_tarjeta);
                    intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                    intent.putExtra("banco", banco);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("tipo_tarjeta", tipo_tarjeta);
                    intent.putExtra("cli_dni", cli_dni);
                    intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                    startActivity(intent);
                    finish();
                } else {
                    if (tarjetasUsuarioAdapter.getItem(0).getValidacionTarjeta().equals("Firma")) {
                        Intent intent = new Intent(TarjetaPagoRemitente.this, IngresoMontoPagoFirmaTransferencias.class);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("nombrebenef", nombreBeneficiario);
                        intent.putExtra("dni_benef", dni_benef);
                        intent.putExtra("num_tarjeta", num_tarjeta);
                        intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                        intent.putExtra("banco", banco);
                        intent.putExtra("cliente", cliente);
                        intent.putExtra("tipo_tarjeta", tipo_tarjeta);
                        intent.putExtra("cli_dni", cli_dni);
                        intent.putExtra("validacion_tarjeta", validacion_tarjeta);
                        startActivity(intent);
                        finish();
                    } else if (tarjetasUsuarioAdapter.getItem(0).getValidacionTarjeta().equals("Pin")){
                        Intent intent = new Intent(TarjetaPagoRemitente.this, IngresoMontoPagoPinTransferencias.class);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("nombrebenef", nombreBeneficiario);
                        intent.putExtra("dni_benef", dni_benef);
                        intent.putExtra("num_tarjeta", num_tarjeta);
                        intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                        intent.putExtra("banco", banco);
                        intent.putExtra("cliente", cliente);
                        intent.putExtra("tipo_tarjeta", tipo_tarjeta);
                        intent.putExtra("cli_dni", cli_dni);
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
        alertDialog.setMessage("¿Está seguro que desea cacelar la transacción?");
        alertDialog.setTitle("Cancelar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(TarjetaPagoRemitente.this, MenuCliente.class);
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
}
