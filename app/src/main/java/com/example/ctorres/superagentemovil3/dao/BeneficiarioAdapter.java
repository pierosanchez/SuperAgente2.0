package com.example.ctorres.superagentemovil3.dao;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;

import java.util.ArrayList;

/**
 * Created by CTORRES on 18/05/2017.
 */

public class BeneficiarioAdapter extends BaseAdapter {

    ArrayList<BeneficiarioEntity> items;
    Context context;
    LayoutInflater layoutInflater = null;

    public BeneficiarioAdapter(ArrayList<BeneficiarioEntity> items, Context context) {
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
    public BeneficiarioEntity getItem(int position) {
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
        View view = layoutInflater.inflate(R.layout.row_beneficiario,null);

        viewHolder.tv_apellido = (TextView) view.findViewById(R.id.tv_apellido);
        viewHolder.tv_nombre = (TextView) view.findViewById(R.id.tv_nombre);

        viewHolder.tv_apellido.setText(String.valueOf(getItem(position).getApellido()));
        viewHolder.tv_nombre.setText(String.valueOf(getItem(position).getNombre()));

        BeneficiarioEntity data = getItem(position);

        if(data!=null){
            viewHolder.tv_apellido.setText(data.getApellido());
            viewHolder.tv_nombre.setText(data.getNombre());
        } else {
            viewHolder.tv_apellido.setText("");
            viewHolder.tv_nombre.setText("");
        }

        return view;
    }

    public static final class ViewHolder{
        TextView tv_nombre, tv_apellido;
    }

    public void setNewListBeneficiario(ArrayList<BeneficiarioEntity> listBeneficiario){
        items = listBeneficiario;
    }
}
