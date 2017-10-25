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

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.CuentasBeneficiarioAdapter;
import com.example.ctorres.superagentemovil3.dao.CuentasUsuarioAdapter;
import com.example.ctorres.superagentemovil3.dao.MantenimientoCuentasBeneficiarioAdapter;
import com.example.ctorres.superagentemovil3.dao.MentenimientoTarjetasBeneficiarioAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.dao.TarjetasBeneficiarioAdapter;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.CuentaEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;

public class ListadoCuentasTarjetasBeneficiario extends Activity {

    ArrayList<BeneficiarioEntity> arrayBenefeciarioEntity, arrayBeneficiarioTarjetas;
    ArrayList<UsuarioEntity> arrayComisiones;
    MantenimientoCuentasBeneficiarioAdapter adapterCuentasBeneficiario;
    MentenimientoTarjetasBeneficiarioAdapter adapterTarjetasBeneficiario;
    ListView lv_lista_cuentas_beneficiario, lv_lista_tarjetas_beneficiario;
    int id_cuenta_benef, id_tarjeta_benef;
    String dni_benef;
    UsuarioEntity usuario;
    Button btn_regresar_cuentas, btn_agregar_cuenta_beneficiario;
    ImageView iv_editar_tarjeta_beneficiario, iv_eliminar_tarjeta_beneficiario, iv_editar_cuenta_beneficiario, iv_eliminar_cuenta_beneficiario;
    String cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_cuentas_tarjetas_beneficiario);

        lv_lista_cuentas_beneficiario = (ListView) findViewById(R.id.lv_lista_cuentas_beneficiario);
        lv_lista_tarjetas_beneficiario = (ListView) findViewById(R.id.lv_lista_tarjetas_beneficiario);

        btn_regresar_cuentas = (Button) findViewById(R.id.btn_regresar_cuentas);
        btn_agregar_cuenta_beneficiario = (Button) findViewById(R.id.btn_agregar_cuenta_beneficiario);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");
        dni_benef = bundle.getString("dni_benef");
        cliente = bundle.getString("cliente");

        arrayBenefeciarioEntity = null;
        adapterCuentasBeneficiario = new MantenimientoCuentasBeneficiarioAdapter(arrayBenefeciarioEntity, getApplication());
        lv_lista_cuentas_beneficiario.setAdapter(adapterCuentasBeneficiario);

        ejecutarLista();

        arrayBeneficiarioTarjetas = null;
        adapterTarjetasBeneficiario = new MentenimientoTarjetasBeneficiarioAdapter(arrayBeneficiarioTarjetas, getApplication());
        lv_lista_tarjetas_beneficiario.setAdapter(adapterTarjetasBeneficiario);

        ejecutarListaTarjetaBeneficiario();

        lv_lista_cuentas_beneficiario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                view.setSelected(true);

                iv_editar_cuenta_beneficiario = (ImageView) view.findViewById(R.id.iv_editar_cuenta_beneficiario);
                iv_eliminar_cuenta_beneficiario = (ImageView) view.findViewById(R.id.iv_eliminar_cuenta_beneficiario);

                iv_editar_cuenta_beneficiario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id_cuenta_benef = adapterCuentasBeneficiario.getItem(position).getId_cuenta_benef();

                        view.setSelected(false);

                        Intent intent = new Intent(ListadoCuentasTarjetasBeneficiario.this, ActualizarCuentasBeneficiario.class);
                        intent.putExtra("id_cuenta_benef", id_cuenta_benef);
                        intent.putExtra("dni_benef", dni_benef);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("cliente", cliente);
                        startActivity(intent);
                        finish();
                    }
                });

                iv_eliminar_cuenta_beneficiario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id_cuenta_benef = adapterCuentasBeneficiario.getItem(position).getId_cuenta_benef();

                        queDeseaHacerCuenta();

                        view.setSelected(false);
                    }
                });

                //queDeseaHacerCuenta();
            }
        });

        lv_lista_tarjetas_beneficiario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                view.setSelected(true);

                iv_editar_tarjeta_beneficiario = (ImageView) view.findViewById(R.id.iv_editar_tarjeta_beneficiario);
                iv_eliminar_tarjeta_beneficiario = (ImageView) view.findViewById(R.id.iv_eliminar_tarjeta_beneficiario);

                iv_editar_tarjeta_beneficiario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id_tarjeta_benef = adapterTarjetasBeneficiario.getItem(position).getId_cuenta_benef();

                        view.setSelected(false);

                        Intent intent = new Intent(ListadoCuentasTarjetasBeneficiario.this, ActualizarTarjetaBeneficiario.class);
                        intent.putExtra("id_cuenta_benef", id_tarjeta_benef);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("dni_benef", dni_benef);
                        intent.putExtra("cliente", cliente);
                        startActivity(intent);
                        finish();
                    }
                });

                iv_eliminar_tarjeta_beneficiario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id_tarjeta_benef = adapterTarjetasBeneficiario.getItem(position).getId_cuenta_benef();

                        queDeseaHacerTarjeta();

                        view.setSelected(false);
                    }
                });

                //queDeseaHacerTarjeta();
            }
        });

        btn_regresar_cuentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListadoCuentasTarjetasBeneficiario.this, ListadoBeneficiariosUsuario.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });

        btn_agregar_cuenta_beneficiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListadoCuentasTarjetasBeneficiario.this, AgregarCuentasBeneficiario.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("dni_benef", dni_benef);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });
    }

    private void ejecutarLista() {
        //benef = usuario.getUsuarioId();

        try {
            ListadoCuentasTarjetasBeneficiario.ListadoBeneficiario listadoBeneficiario = new ListadoCuentasTarjetasBeneficiario.ListadoBeneficiario();
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

        try {
            ListadoCuentasTarjetasBeneficiario.ListadoTarjetaBeneficiario listadoBeneficiario = new ListadoCuentasTarjetasBeneficiario.ListadoTarjetaBeneficiario();
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

    public void queDeseaHacerTarjeta() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Esta seguro que desea eliminar esta tarjeta?");
        alertDialog.setTitle("Eliminar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListadoCuentasTarjetasBeneficiario.eliminartarjetaBeneficiario cuentasBeneficiario = new ListadoCuentasTarjetasBeneficiario.eliminartarjetaBeneficiario();
                cuentasBeneficiario.execute();

                arrayBeneficiarioTarjetas = null;
                adapterTarjetasBeneficiario = new MentenimientoTarjetasBeneficiarioAdapter(arrayBeneficiarioTarjetas, getApplication());
                lv_lista_tarjetas_beneficiario.setAdapter(adapterTarjetasBeneficiario);

                ejecutarListaTarjetaBeneficiario();
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

    public void queDeseaHacerCuenta() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Esta seguro que desea eliminar esta cuenta?");
        alertDialog.setTitle("Eliminar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListadoCuentasTarjetasBeneficiario.eliminarCuentasBeneficiario cuentasBeneficiario = new ListadoCuentasTarjetasBeneficiario.eliminarCuentasBeneficiario();
                cuentasBeneficiario.execute();

                arrayBenefeciarioEntity = null;
                adapterCuentasBeneficiario = new MantenimientoCuentasBeneficiarioAdapter(arrayBenefeciarioEntity, getApplication());
                lv_lista_cuentas_beneficiario.setAdapter(adapterCuentasBeneficiario);

                ejecutarLista();
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

    private class eliminarCuentasBeneficiario extends AsyncTask<String, Void, BeneficiarioEntity> {
        @Override
        protected BeneficiarioEntity doInBackground(String... params) {
            BeneficiarioEntity beneficiarioEntity;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                beneficiarioEntity = dao.eliminarCuentasBeneficiario(id_cuenta_benef);

            } catch (Exception e) {
                beneficiarioEntity = null;
                //fldag_clic_ingreso = 0;;
            }
            return beneficiarioEntity;
        }
    }

    private class eliminartarjetaBeneficiario extends AsyncTask<String, Void, BeneficiarioEntity> {
        @Override
        protected BeneficiarioEntity doInBackground(String... params) {
            BeneficiarioEntity beneficiarioEntity;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                beneficiarioEntity = dao.eliminarCuentasBeneficiario(id_tarjeta_benef);

            } catch (Exception e) {
                beneficiarioEntity = null;
                //fldag_clic_ingreso = 0;;
            }
            return beneficiarioEntity;
        }
    }
}
