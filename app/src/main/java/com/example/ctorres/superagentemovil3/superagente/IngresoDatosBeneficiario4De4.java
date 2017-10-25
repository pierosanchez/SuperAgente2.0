package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.BeneficiarioAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;
import java.util.List;

public class IngresoDatosBeneficiario4De4 extends Activity {

    //RecyclerView recyclerView;
    String benef;
    ListView listView;
    BeneficiarioAdapter adapterBeneficiario;
    private UsuarioEntity usuario;
    ArrayList<BeneficiarioEntity>  arrayBenefeciarioEntity;
    Button btn_siguiente_beneficiario_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingreso_datos_beneficiario4_de4);

        //recyclerView = (RecyclerView) findViewById(R.id.reciclerViewListaBeneficiarios);
        listView = (ListView) findViewById(R.id.listViewListaBeneficiarios);
        btn_siguiente_beneficiario_list = (Button) findViewById(R.id.btn_siguiente_beneficiario_list);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");

        arrayBenefeciarioEntity = null;
        adapterBeneficiario = new BeneficiarioAdapter(arrayBenefeciarioEntity, getApplication());
        listView.setAdapter(adapterBeneficiario);

        ejecutarLista();

        btn_siguiente_beneficiario_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngresoDatosBeneficiario4De4.this, AfiliacionExitosaConsulta.class);
                //intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
            }
        });

    }

    private void ejecutarLista(){
        benef = usuario.getUsuarioId();

        try {
            IngresoDatosBeneficiario4De4.ListadoBeneficiario listadoBeneficiario = new IngresoDatosBeneficiario4De4.ListadoBeneficiario();
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
        }
    }
}
