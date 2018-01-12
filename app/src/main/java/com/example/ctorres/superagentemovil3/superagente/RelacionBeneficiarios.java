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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.adapter.RelacionBeneficiarioAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;

public class RelacionBeneficiarios extends Activity {

    LinearLayout btn_aceptar_beneficiario;
    String benef, dni_benef;
    private UsuarioEntity usuario;
    RelacionBeneficiarioAdapter adapterBeneficiario;
    ArrayList<BeneficiarioEntity> arrayBenefeciarioEntity;
    ListView listView;
    String cliente, cli_dni;
    private ProgressBar circleProgressBar;
    Button btn_regresar, btn_cancelar_transferencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relacion_beneficiarios);

        //btn_aceptar_beneficiario = (LinearLayout) findViewById(R.id.btn_aceptar_beneficiario);
        listView = (ListView) findViewById(R.id.lv_relacion_beneficiario);

        circleProgressBar = (ProgressBar) findViewById(R.id.circleProgressBar);

        btn_regresar = (Button) findViewById(R.id.btn_regresar);
        btn_cancelar_transferencia = (Button) findViewById(R.id.btn_cancelar_transferencia);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");
        cliente = bundle.getString("cliente");
        cli_dni = bundle.getString("cli_dni");

        arrayBenefeciarioEntity = null;
        adapterBeneficiario = new RelacionBeneficiarioAdapter(arrayBenefeciarioEntity, getApplication());
        listView.setAdapter(adapterBeneficiario);

        ejecutarLista();

        circleProgressBar.setVisibility(View.VISIBLE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Intent intent = new Intent(RelacionBeneficiarios.this, TarjetaPagoRemitente.class);
                intent.putExtra("usuario", usuario);
                //intent.putExtra("nombrebenef", nombreBeneficiario());
                startActivity(intent);
                finish();*/

                //TextView item = (TextView) view.findViewById(R.id.tv_nombre);
                String nombre = adapterBeneficiario.getItem(position).getNombre();
                String apellido = adapterBeneficiario.getItem(position).getApellido();

                String nomyape = nombre + " " + apellido;

                dni_benef = adapterBeneficiario.getItem(position).getDni();

                Intent intent = new Intent(RelacionBeneficiarios.this, TarjetaPagoRemitente.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("nombrebenef", nomyape);
                intent.putExtra("dni_benef", dni_benef);
                intent.putExtra("cliente", cliente);
                intent.putExtra("cli_dni", cli_dni);
                startActivity(intent);
                finish();
            }
        });

        btn_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RelacionBeneficiarios.this, MenuCliente.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                intent.putExtra("cli_dni", cli_dni);
                startActivity(intent);
                finish();
            }
        });

        btn_cancelar_transferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });

        /*btn_aceptar_beneficiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RelacionBeneficiarios.this, TarjetaPagoRemitente.class);
                intent.putExtra("usuario", usuario);
                //intent.putExtra("nombrebenef", nombreBeneficiario());
                startActivity(intent);
                finish();
            }
        });*/

    }

    private void ejecutarLista(){
        benef = usuario.getUsuarioId();

        try {
            RelacionBeneficiarios.ListadoBeneficiario listadoBeneficiario = new RelacionBeneficiarios.ListadoBeneficiario();
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
            adapterBeneficiario.setNewListBeneficiario(arrayBenefeciarioEntity);
            adapterBeneficiario.notifyDataSetChanged();
            circleProgressBar.setVisibility(View.GONE);
        }
    }

    public void cancelar() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Está seguro que desea cacelar la transacción?");
        alertDialog.setTitle("Cancelar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(RelacionBeneficiarios.this, MenuCliente.class);
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
