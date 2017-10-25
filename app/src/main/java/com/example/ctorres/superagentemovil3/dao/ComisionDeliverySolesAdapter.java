package com.example.ctorres.superagentemovil3.dao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;

/**
 * Created by CTORRES on 18/05/2017.
 */

public class ComisionDeliverySolesAdapter extends BaseAdapter {

    ArrayList<UsuarioEntity> items;
    Context context;
    LayoutInflater layoutInflater = null;

    public ComisionDeliverySolesAdapter(ArrayList<UsuarioEntity> items, Context context) {
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
    public UsuarioEntity getItem(int position) {
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
        View view = layoutInflater.inflate(R.layout.row_comision_delivery_soles,null);

        //viewHolder.tv_apellido = (TextView) view.findViewById(R.id.tv_apellido);
        viewHolder.tv_importe_delivery = (TextView) view.findViewById(R.id.tv_importe_delivery);

        //viewHolder.tv_apellido.setText(String.valueOf(getItem(position).getApellido()));
        viewHolder.tv_importe_delivery.setText(String.valueOf(getItem(position).getImporte_comision()));

        UsuarioEntity data = getItem(position);

        if(data!=null){
            //viewHolder.tv_apellido.setText(data.getApellido());
            viewHolder.tv_importe_delivery.setText(String.valueOf(data.getImporte_comision()));
        } else {
            //viewHolder.tv_apellido.setText("");
            viewHolder.tv_importe_delivery.setText("");
        }

        return view;
    }

    public static final class ViewHolder{
        TextView tv_importe_delivery, tv_apellido;
    }

    public void setNewListBeneficiario(ArrayList<UsuarioEntity> listBeneficiario){
        items = listBeneficiario;
    }
}
