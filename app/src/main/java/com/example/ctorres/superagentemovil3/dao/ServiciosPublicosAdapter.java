package com.example.ctorres.superagentemovil3.dao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.EmpresasServiciosEntity;
import com.example.ctorres.superagentemovil3.entity.ServiciosPublicEntity;

import java.util.ArrayList;

/**
 * Created by CTORRES on 18/05/2017.
 */

public class ServiciosPublicosAdapter extends BaseAdapter {

    ArrayList<ServiciosPublicEntity> items;
    Context context;
    LayoutInflater layoutInflater = null;

    public ServiciosPublicosAdapter(ArrayList<ServiciosPublicEntity> items, Context context) {
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
    public ServiciosPublicEntity getItem(int position) {
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
        View view = layoutInflater.inflate(R.layout.row_servicios_publicos,null);

        viewHolder.tv_empresas = (TextView) view.findViewById(R.id.tv_empresas);

        viewHolder.tv_empresas.setText(String.valueOf(getItem(position).getDes_inst_edu()));

        ServiciosPublicEntity data = getItem(position);

        if(data!=null){
            viewHolder.tv_empresas.setText(data.getDes_inst_edu());
        } else {
            viewHolder.tv_empresas.setText("");
        }

        return view;
    }

    public static final class ViewHolder{
        TextView tv_empresas;
    }

    public void setNewListServiciosPublicos(ArrayList<ServiciosPublicEntity> listBeneficiario){
        items = listBeneficiario;
    }
}
