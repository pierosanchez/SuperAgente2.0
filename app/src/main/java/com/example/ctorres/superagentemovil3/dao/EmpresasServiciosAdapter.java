package com.example.ctorres.superagentemovil3.dao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.EmpresasServiciosEntity;

import java.util.ArrayList;

/**
 * Created by CTORRES on 18/05/2017.
 */

public class EmpresasServiciosAdapter extends BaseAdapter {

    ArrayList<EmpresasServiciosEntity> items;
    Context context;
    LayoutInflater layoutInflater = null;

    public EmpresasServiciosAdapter(ArrayList<EmpresasServiciosEntity> items, Context context) {
        this.items = items;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(items == null){
            return 0;
        }else {
            return items.size();
        }
    }

    @Override
    public EmpresasServiciosEntity getItem(int position) {
        if(items == null){
            return null;
        }else{
            return items.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder= new ViewHolder();
        View view = layoutInflater.inflate(R.layout.row_empresas_servicios,null);

        viewHolder.tv_empresas = (TextView) view.findViewById(R.id.tv_empresas);

        viewHolder.tv_empresas.setText(String.valueOf(getItem(position).getDes_emp_servicio()));

        EmpresasServiciosEntity data = getItem(position);

        if(data!=null){
            viewHolder.tv_empresas.setText(data.getDes_emp_servicio());
        } else {
            viewHolder.tv_empresas.setText("");
        }

        return view;
    }

    public static final class ViewHolder{
        TextView tv_empresas;
    }

    public void setNewListEmpresas(ArrayList<EmpresasServiciosEntity> listBeneficiario){
        items = listBeneficiario;
    }
}
