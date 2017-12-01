package com.example.ctorres.superagentemovil3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.BancosEntity;
import com.example.ctorres.superagentemovil3.entity.TipoTarjetaEntity;

import java.util.ArrayList;

/**
 * Created by CTORRES on 18/05/2017.
 */

public class TipoTarjetaAdapter extends BaseAdapter {

    ArrayList<TipoTarjetaEntity> items;
    Context context;
    LayoutInflater layoutInflater = null;

    public TipoTarjetaAdapter(ArrayList<TipoTarjetaEntity> items, Context context) {
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
    public TipoTarjetaEntity getItem(int position) {
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
        View view = layoutInflater.inflate(R.layout.row_tipo_tarjeta,null);

        viewHolder.tv_tipo_tarjeta = (TextView) view.findViewById(R.id.tv_tipo_tarjeta);

        viewHolder.tv_tipo_tarjeta.setText(String.valueOf(getItem(position).getDescTipoTarjeta()));

        TipoTarjetaEntity data = getItem(position);

        if(data!=null){
            viewHolder.tv_tipo_tarjeta.setText(data.getDescTipoTarjeta());
        } else {
            viewHolder.tv_tipo_tarjeta.setText("");
        }

        return view;
    }

    public static final class ViewHolder{
        TextView tv_tipo_tarjeta;
    }

    public void setNewListTipoTarjeta(ArrayList<TipoTarjetaEntity> listTipoTarjeta){
        items = listTipoTarjeta;
    }
}
