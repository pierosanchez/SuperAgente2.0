package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.RelacionBeneficiarioAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.dao.TarjetasUsuarioAdapter;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;

public class SeleccionTarjetaPago extends Activity {

    ImageView select_tarjeta, btn_atras, btn_adelante;
    LinearLayout btn_cancelar_tarjeta_pago;
    private UsuarioEntity usuario;
    String usu;
    TarjetasUsuarioAdapter tarjetasUsuarioAdapter;
    ArrayList<UsuarioEntity> usuarioEntityArrayList;
    ListView lv_tarjetas_usuario;
    String num_tarjeta, banco_tarjeta;
    int tipo_tarjeta, emisor_tarjeta;
    String cliente;
    private ProgressBar circleProgressBar;


    int[] tarjetaid = {R.drawable.bin424137scotiabankjockeyvisa, R.drawable.bin377754interbankamexblueimgtaramexbluefc_jpg, R.drawable.bin457562scotiabankjockeyvisaplatinum, R.drawable.bin510308scotiabankmcpremiumnew, R.drawable.bin516003scotiabankmcdebitogold, R.drawable.bin545545scotiabankmastercardclassic, R.drawable.bin552271scotiabankmastercardblack, R.drawable.bin554911scotiabankmastercardplatinum};
    int i = 0;
    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_tarjeta_pago);

        /*select_tarjeta = (ImageView) findViewById(R.id.img_tarjeta_seleccion_pago);
        btn_atras = (ImageView) findViewById(R.id.img_atras);
        btn_adelante = (ImageView) findViewById(R.id.img_adelante);*/

        btn_cancelar_tarjeta_pago = (LinearLayout) findViewById(R.id.btn_cancelar_tarjeta_pago);

        lv_tarjetas_usuario = (ListView) findViewById(R.id.lv_tarjetas_usuario);

        circleProgressBar = (ProgressBar) findViewById(R.id.circleProgressBar);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");
        cliente = bundle.getString("cliente");

        usuarioEntityArrayList = null;
        tarjetasUsuarioAdapter = new TarjetasUsuarioAdapter(usuarioEntityArrayList, getApplication());
        lv_tarjetas_usuario.setAdapter(tarjetasUsuarioAdapter);

        ejecutarLista();

        circleProgressBar.setVisibility(View.VISIBLE);

        lv_tarjetas_usuario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                num_tarjeta = tarjetasUsuarioAdapter.getItem(position).getNumeroTarjeta();
                tipo_tarjeta = tarjetasUsuarioAdapter.getItem(position).getTipo_tarjeta();
                emisor_tarjeta = tarjetasUsuarioAdapter.getItem(position).getCod_emisor_tarjeta();
                banco_tarjeta = tarjetasUsuarioAdapter.getItem(position).getBanco_tarjeta();
                if (tarjetasUsuarioAdapter.getItem(position).getTipo_tarjeta() == 2) {
                    Toast.makeText(SeleccionTarjetaPago.this, "No se puede pagar una tarjeta de Débito", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(SeleccionTarjetaPago.this, SeleccionModoMontoPago.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("num_tarjeta", num_tarjeta);
                    intent.putExtra("tipo_tarjeta", tipo_tarjeta);
                    intent.putExtra("emisor_tarjeta", emisor_tarjeta);
                    intent.putExtra("banco_tarjeta", banco_tarjeta);
                    intent.putExtra("cliente", cliente);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btn_cancelar_tarjeta_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });
    }

    public void cancelar() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Esta seguro que desea cacelar la transacción?");
        alertDialog.setTitle("Cancelar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(SeleccionTarjetaPago.this, MenuCliente.class);
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

    private void ejecutarLista() {
        usu = usuario.getUsuarioId();

        try {
            SeleccionTarjetaPago.ListadoTarjetas listadoBeneficiario = new SeleccionTarjetaPago.ListadoTarjetas();
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
            //usuarioEntityArrayList.remove(tipo_tarjeta=2);
            tarjetasUsuarioAdapter.setNewListBeneficiario(usuarioEntityArrayList);
            tarjetasUsuarioAdapter.notifyDataSetChanged();
            circleProgressBar.setVisibility(View.GONE);
            for (int i=usuarioEntityArrayList.size()-1; i>=0; i--) {
                if (usuarioEntityArrayList.get(i).getTipo_tarjeta() == 2) {
                    usuarioEntityArrayList.remove(i);
                }
            }
        }
    }
}
