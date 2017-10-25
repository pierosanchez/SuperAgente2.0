package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.BeneficiarioAdapter;
import com.example.ctorres.superagentemovil3.dao.RelacionBeneficiarioAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;
import java.util.List;

public class RelacionBeneficiarios extends Activity {

    LinearLayout btn_aceptar_beneficiario;
    String benef, dni_benef;
    private UsuarioEntity usuario;
    RelacionBeneficiarioAdapter adapterBeneficiario;
    ArrayList<BeneficiarioEntity> arrayBenefeciarioEntity;
    ListView listView;
    String cliente;
    private ProgressBar circleProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relacion_beneficiarios);

        //btn_aceptar_beneficiario = (LinearLayout) findViewById(R.id.btn_aceptar_beneficiario);
        listView = (ListView) findViewById(R.id.lv_relacion_beneficiario);

        circleProgressBar = (ProgressBar) findViewById(R.id.circleProgressBar);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");
        cliente = bundle.getString("cliente");

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
                startActivity(intent);
                finish();
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


}
