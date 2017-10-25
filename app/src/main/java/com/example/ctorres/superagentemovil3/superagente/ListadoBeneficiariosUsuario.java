package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.BeneficiarioAdapter;
import com.example.ctorres.superagentemovil3.dao.BeneficiarioMantenimientoAdapter;
import com.example.ctorres.superagentemovil3.dao.CuentasUsuarioAdapter;
import com.example.ctorres.superagentemovil3.dao.RelacionBeneficiarioAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;

public class ListadoBeneficiariosUsuario extends Activity {

    ListView lv_beneficiario_usuario_mant;
    BeneficiarioAdapter adapterBeneficiario;
    BeneficiarioMantenimientoAdapter beneficiarioMantenimientoAdapter;
    ArrayList<BeneficiarioEntity> arrayBenefeciarioEntity;
    String benef, dni_benef;
    private UsuarioEntity usuario;
    private ProgressBar circleProgressBar;
    Button btn_agregar_beneficiario, btn_regresar_mantenimiento;
    ImageView img1, img2, img3;
    String cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado_beneficiarios_usuario);

        lv_beneficiario_usuario_mant = (ListView) findViewById(R.id.lv_beneficiario_usuario_mant);

        btn_agregar_beneficiario = (Button) findViewById(R.id.btn_agregar_beneficiario);
        btn_regresar_mantenimiento = (Button) findViewById(R.id.btn_regresar_mantenimiento);

        circleProgressBar = (ProgressBar) findViewById(R.id.circleProgressBar);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");
        cliente = bundle.getString("cliente");

        /*arrayBenefeciarioEntity = null;
        adapterBeneficiario = new BeneficiarioAdapter(arrayBenefeciarioEntity, getApplication());
        lv_beneficiario_usuario_mant.setAdapter(adapterBeneficiario);

        ejecutarLista();*/

        circleProgressBar.setVisibility(View.VISIBLE);

        arrayBenefeciarioEntity = null;
        beneficiarioMantenimientoAdapter = new BeneficiarioMantenimientoAdapter(arrayBenefeciarioEntity, getApplication());
        lv_beneficiario_usuario_mant.setAdapter(beneficiarioMantenimientoAdapter);

        ejecutarLista();

        lv_beneficiario_usuario_mant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {

                view.setSelected(true);

                img1 = (ImageView) view.findViewById(R.id.iv_editar_beneficiario);
                img2 = (ImageView) view.findViewById(R.id.iv_eliminar_beneficiario);
                img3 = (ImageView) view.findViewById(R.id.iv_cuentas_beneficiario);

                img1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.setSelected(false);
                        dni_benef = beneficiarioMantenimientoAdapter.getItem(position).getDni();
                        Intent intent = new Intent(ListadoBeneficiariosUsuario.this, ActualizarBeneficiario.class);
                        intent.putExtra("dni_benef", dni_benef);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("cliente", cliente);
                        startActivity(intent);
                        finish();
                    }
                });

                img2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dni_benef = beneficiarioMantenimientoAdapter.getItem(position).getDni();
                        queDeseaHacer();
                        view.setSelected(false);
                    }
                });

                img3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view.setSelected(false);
                        dni_benef = beneficiarioMantenimientoAdapter.getItem(position).getDni();
                        Intent intent = new Intent(ListadoBeneficiariosUsuario.this, ListadoCuentasTarjetasBeneficiario.class);
                        intent.putExtra("dni_benef", dni_benef);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("cliente", cliente);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        /*btn_agreegar_beneficiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListadoBeneficiariosUsuario.this, IngresoDatosBeneficiarios2De4.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
            }
        });*/

        btn_regresar_mantenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListadoBeneficiariosUsuario.this, MantenimientoUsuario.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });

        btn_agregar_beneficiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListadoBeneficiariosUsuario.this, AgregarBeneficiarioUsuario.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });
    }

    private void ejecutarLista(){
        benef = usuario.getUsuarioId();

        try {
            ListadoBeneficiariosUsuario.ListadoBeneficiario listadoBeneficiario = new ListadoBeneficiariosUsuario.ListadoBeneficiario();
            listadoBeneficiario.execute();
        } catch (Exception e){
            //listadoBeneficiario = null;
        }

    }

    private class ListadoBeneficiario extends AsyncTask<String,Void,Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                arrayBenefeciarioEntity = dao.listarBeneficiario(benef);
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            beneficiarioMantenimientoAdapter.setNewListBeneficiario(arrayBenefeciarioEntity);
            beneficiarioMantenimientoAdapter.notifyDataSetChanged();
            circleProgressBar.setVisibility(View.GONE);
        }
    }

    private class eliminarBeneficiarioUsuario extends AsyncTask<String, Void, BeneficiarioEntity> {
        @Override
        protected BeneficiarioEntity doInBackground(String... params) {
            BeneficiarioEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.eliminarBeneficiarioUsuario(dni_benef);
            } catch (Exception e) {
                user = null;
                //fldag_clic_ingreso = 0;;
            }
            return user;
        }
    }

    public void queDeseaHacer() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Esta seguro que desea eliminar este beneficiario?");
        alertDialog.setTitle("Eliminar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListadoBeneficiariosUsuario.eliminarBeneficiarioUsuario eliminarBeneficiarioUsuario = new ListadoBeneficiariosUsuario.eliminarBeneficiarioUsuario();
                eliminarBeneficiarioUsuario.execute();

                arrayBenefeciarioEntity = null;
                beneficiarioMantenimientoAdapter = new BeneficiarioMantenimientoAdapter(arrayBenefeciarioEntity, getApplication());
                lv_beneficiario_usuario_mant.setAdapter(beneficiarioMantenimientoAdapter);

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

    public void TarjetasoCuentas() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Que desea ver de este beneficiario?");
        alertDialog.setTitle("¿Que desea ver?");
        alertDialog.setPositiveButton("Cuenta", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.setNegativeButton("Tarjeta", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }
}
