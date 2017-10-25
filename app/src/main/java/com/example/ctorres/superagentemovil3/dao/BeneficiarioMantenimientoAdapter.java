package com.example.ctorres.superagentemovil3.dao;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.superagente.ActualizarBeneficiario;
import com.example.ctorres.superagentemovil3.superagente.ListadoBeneficiariosUsuario;

import java.util.ArrayList;

/**
 * Created by CTORRES on 11/08/2017.
 */

public class BeneficiarioMantenimientoAdapter extends BaseAdapter{

    ArrayList<BeneficiarioEntity> items;
    Context context;
    LayoutInflater layoutInflater = null;
    String dni_benef;
    private UsuarioEntity usuario;

    public BeneficiarioMantenimientoAdapter(ArrayList<BeneficiarioEntity> items, Context context) {
        this.items = items;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (items == null){
            return 0;
        } else {
            return items.size();
        }
    }

    @Override
    public BeneficiarioEntity getItem(int position) {
        if (items == null) {
            return null;
        } else {
            return items.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        View view = layoutInflater.inflate(R.layout.row_listado_beneficiarios_mantenimiento, null);

        viewHolder.chkb_registro = (TextView) view.findViewById(R.id.chkb_registro);

        viewHolder.iv_editar_beneficiario = (ImageView) view.findViewById(R.id.iv_editar_beneficiario);
        viewHolder.iv_eliminar_beneficiario = (ImageView) view.findViewById(R.id.iv_eliminar_beneficiario);
        viewHolder.iv_cuentas_beneficiario = (ImageView) view.findViewById(R.id.iv_cuentas_beneficiario);

        viewHolder.chkb_registro.setText(String.valueOf(getItem(position).getNombre()));

        final BeneficiarioEntity data = getItem(position);

        if (data != null) {
            String nombre = data.getNombre();
            String apellido = data.getApellido();
            String nombreyapellido = nombre + " " + apellido;
            viewHolder.chkb_registro.setText(nombreyapellido);
        } else {
            viewHolder.chkb_registro.setText("");
        }

        return view;
    }

    public static final class ViewHolder{
        TextView chkb_registro;
        ImageView iv_editar_beneficiario, iv_eliminar_beneficiario, iv_cuentas_beneficiario;
    }

    public void setNewListBeneficiario(ArrayList<BeneficiarioEntity> beneficiario){
        items = beneficiario;
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
}
