package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.CuentasMantenimientoAdapter;
import com.example.ctorres.superagentemovil3.dao.CuentasUsuarioAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.dao.TarjetasUsuarioAdapter;
import com.example.ctorres.superagentemovil3.entity.CuentaEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.utils.Constante;
import com.example.ctorres.superagentemovil3.utils.Global;

import java.util.ArrayList;

public class ListadoCuentasUsuario extends Activity {

    private UsuarioEntity usuario;
    private ProgressBar circleProgressBar;
    String usu, num_tarjeta, id_cuenta, idaccount;
    CuentasUsuarioAdapter cuentasUsuarioAdapter;
    CuentasMantenimientoAdapter cuentasMantenimientoAdapter;
    ArrayList<CuentaEntity> usuarioEntityArrayList;
    ListView lv_cuentas_usuario_mant;
    Button btn_regresar_mantenimiento, btn_agregar_cuentas;
    ImageView img1, img2;
    View v;
    String cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado_cuentas_usuario);

        lv_cuentas_usuario_mant = (ListView) findViewById(R.id.lv_cuentas_usuario_mant);

        btn_agregar_cuentas = (Button) findViewById(R.id.btn_agregar_cuentas);
        btn_regresar_mantenimiento = (Button) findViewById(R.id.btn_regresar_mantenimiento);

        circleProgressBar = (ProgressBar) findViewById(R.id.circleProgressBar);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");
        cliente = bundle.getString("cliente");

        circleProgressBar.setVisibility(View.VISIBLE);

        usuarioEntityArrayList = null;
        cuentasMantenimientoAdapter = new CuentasMantenimientoAdapter(usuarioEntityArrayList, getApplication());
        lv_cuentas_usuario_mant.setAdapter(cuentasMantenimientoAdapter);

        ejecutarLista();

        lv_cuentas_usuario_mant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {

                view.setSelected(true);

                img1 = (ImageView) view.findViewById(R.id.iv_editar_beneficiario);
                img2 = (ImageView) view.findViewById(R.id.iv_eliminar_beneficiario);

                img1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.setSelected(false);

                        id_cuenta = cuentasMantenimientoAdapter.getItem(position).getIdcuenta();

                        Intent intent = new Intent(ListadoCuentasUsuario.this, ActualizarCuenta.class);
                        intent.putExtra("id_cuenta", id_cuenta);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("cliente", cliente);
                        startActivity(intent);
                        finish();
                    }
                });

                img2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id_cuenta = cuentasMantenimientoAdapter.getItem(position).getIdcuenta();

                        queDeseaHacer();
                        view.setSelected(false);
                    }
                });
            }
        });

        btn_regresar_mantenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListadoCuentasUsuario.this, MantenimientoUsuario.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });

        btn_agregar_cuentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListadoCuentasUsuario.this, AgregarCuentasUsuario.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });


    }

    private void ejecutarLista() {
        usu = usuario.getUsuarioId();

        try {
            ListadoCuentasUsuario.ListadoTarjetas listadoBeneficiario = new ListadoCuentasUsuario.ListadoTarjetas();
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
                usuarioEntityArrayList = dao.listarCuentasUsuario(usu);
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            cuentasMantenimientoAdapter.setNewListCuenta(usuarioEntityArrayList);
            cuentasMantenimientoAdapter.notifyDataSetChanged();
            circleProgressBar.setVisibility(View.GONE);
        }
    }

    private class eliminarBeneficiarioUsuario extends AsyncTask<String, Void, CuentaEntity> {
        @Override
        protected CuentaEntity doInBackground(String... params) {
            CuentaEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.eliminarCuentaUsuario(id_cuenta);

            } catch (Exception e) {
                user = null;
                //fldag_clic_ingreso = 0;;
            }
            return user;
        }
    }

    public void queDeseaHacer() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Esta seguro que desea eliminar esta cuenta?");
        alertDialog.setTitle("Eliminar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListadoCuentasUsuario.eliminarBeneficiarioUsuario eliminarBeneficiarioUsuario = new ListadoCuentasUsuario.eliminarBeneficiarioUsuario();
                eliminarBeneficiarioUsuario.execute();

                usuarioEntityArrayList = null;
                cuentasMantenimientoAdapter = new CuentasMantenimientoAdapter(usuarioEntityArrayList, getApplication());
                lv_cuentas_usuario_mant.setAdapter(cuentasMantenimientoAdapter);

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
