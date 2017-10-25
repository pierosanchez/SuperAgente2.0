package com.example.ctorres.superagentemovil3.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.CuentasBeneficiarioAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.superagente.IngresoCuentaTarjetaAbono;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListadoCuentasFragment extends Fragment {

    UsuarioEntity usuario;
    ArrayList<BeneficiarioEntity> arrayBenefeciarioEntity, arrayBeneficiarioTarjetas;
    CuentasBeneficiarioAdapter adapterCuentasBeneficiario;
    String benef, dni_benef;
    ListView lv_cuentas_beneficiario;
    private View rootView;

    public ListadoCuentasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_listado_cuentas, container, false);

        lv_cuentas_beneficiario = (ListView) rootView.findViewById(R.id.lv_cuentas_beneficiario);

        dni_benef = getArguments().getString("dni_benef");

        arrayBenefeciarioEntity = null;
        adapterCuentasBeneficiario = new CuentasBeneficiarioAdapter(arrayBenefeciarioEntity, getActivity());
        lv_cuentas_beneficiario.setAdapter(adapterCuentasBeneficiario);

        ejecutarLista();

        return rootView;
    }

    private void ejecutarLista(){
        //benef = usuario.getUsuarioId();

        try {
            ListadoCuentasFragment.ListadoBeneficiario listadoBeneficiario = new ListadoCuentasFragment.ListadoBeneficiario();
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

}
