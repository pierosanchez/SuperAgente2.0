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

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.CuentasMantenimientoAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.dao.TarjetasMantenimientoAdapater;
import com.example.ctorres.superagentemovil3.dao.TarjetasUsuarioAdapter;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.CuentaEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;

public class ListadoTarjetasUsuario extends Activity {

    private UsuarioEntity usuario;
    private ProgressBar circleProgressBar;
    ListView lv_tarjetas_usuario_mant;
    String usu, num_tarjeta, id_tarjeta;
    TarjetasUsuarioAdapter tarjetasUsuarioAdapter;
    TarjetasMantenimientoAdapater tarjetasMantenimientoAdapater;
    ArrayList<UsuarioEntity> usuarioEntityArrayList;
    Button btn_regresar_mantenimiento, btn_agregar_tarjetas;
    ImageView img1, img2;
    String cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado_tarjetas_usuario);

        lv_tarjetas_usuario_mant = (ListView) findViewById(R.id.lv_tarjetas_usuario_mant);

        btn_regresar_mantenimiento = (Button) findViewById(R.id.btn_regresar_mantenimiento);
        btn_agregar_tarjetas = (Button) findViewById(R.id.btn_agregar_tarjetas);

        circleProgressBar = (ProgressBar) findViewById(R.id.circleProgressBar);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");
        cliente = bundle.getString("cliente");

        circleProgressBar.setVisibility(View.VISIBLE);

        usuarioEntityArrayList = null;
        tarjetasMantenimientoAdapater = new TarjetasMantenimientoAdapater(usuarioEntityArrayList, getApplication());
        lv_tarjetas_usuario_mant.setAdapter(tarjetasMantenimientoAdapater);

        ejecutarLista();

        lv_tarjetas_usuario_mant.setFocusable(false);

        lv_tarjetas_usuario_mant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {

                view.setSelected(true);

                img1 = (ImageView) view.findViewById(R.id.iv_editar_beneficiario);
                img2 = (ImageView) view.findViewById(R.id.iv_eliminar_beneficiario);

                img1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.setSelected(false);
                        id_tarjeta = tarjetasMantenimientoAdapater.getItem(position).getIdTarjeta();
                        Intent intent = new Intent(ListadoTarjetasUsuario.this, ActualizarTarjeta.class);
                        intent.putExtra("id_tarjeta", id_tarjeta);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("cliente", cliente);
                        startActivity(intent);
                        finish();
                    }
                });

                img2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id_tarjeta = tarjetasMantenimientoAdapater.getItem(position).getIdTarjeta();
                        queDeseaHacer();
                        view.setSelected(false);
                    }
                });
            }
        });

        btn_regresar_mantenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListadoTarjetasUsuario.this, MantenimientoUsuario.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });

        btn_agregar_tarjetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListadoTarjetasUsuario.this, AgregarTarjetasUsuario.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });
    }

    private void ejecutarLista(){
        usu = usuario.getUsuarioId();

        try {
            ListadoTarjetasUsuario.ListadoTarjetas listadoBeneficiario = new ListadoTarjetasUsuario.ListadoTarjetas();
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
            tarjetasMantenimientoAdapater.setNewListUsuario(usuarioEntityArrayList);
            tarjetasMantenimientoAdapater.notifyDataSetChanged();
            circleProgressBar.setVisibility(View.GONE);
        }
    }

    private class eliminarBeneficiarioUsuario extends AsyncTask<String, Void, UsuarioEntity> {
        @Override
        protected UsuarioEntity doInBackground(String... params) {
            UsuarioEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.eliminarTarjetaUsuario(id_tarjeta);

            } catch (Exception e) {
                user = null;
                //fldag_clic_ingreso = 0;;
            }
            return user;
        }
    }

    public void queDeseaHacer() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Esta seguro que desea eliminar esta tarjeta?");
        alertDialog.setTitle("Eliminar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListadoTarjetasUsuario.eliminarBeneficiarioUsuario eliminarBeneficiarioUsuario = new ListadoTarjetasUsuario.eliminarBeneficiarioUsuario();
                eliminarBeneficiarioUsuario.execute();

                usuarioEntityArrayList = null;
                tarjetasMantenimientoAdapater = new TarjetasMantenimientoAdapater(usuarioEntityArrayList, getApplication());
                lv_tarjetas_usuario_mant.setAdapter(tarjetasMantenimientoAdapater);

                ejecutarLista();

                circleProgressBar.setVisibility(View.VISIBLE);
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
