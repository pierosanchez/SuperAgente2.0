package com.example.ctorres.superagentemovil3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.BancosEntity;
import com.example.ctorres.superagentemovil3.entity.DeudasTarjetas;

import java.util.ArrayList;

/**
 * Created by CTORRES on 18/05/2017.
 */

public class DeudasTarjetasAdapter extends BaseAdapter {

    ArrayList<DeudasTarjetas> items;
    Context context;
    LayoutInflater layoutInflater = null;

    public DeudasTarjetasAdapter(ArrayList<DeudasTarjetas> items, Context context) {
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
    public DeudasTarjetas getItem(int position) {
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
        DeudasTarjetasAdapter.ViewHolder viewHolder= new DeudasTarjetasAdapter.ViewHolder();
        View view = layoutInflater.inflate(R.layout.row_deudas_tarjetas,null);

        viewHolder.tv_moneda_deuda = (TextView) view.findViewById(R.id.tv_moneda_deuda);

        viewHolder.tv_moneda_deuda.setText(String.valueOf(getItem(position).getSignoMoneda()));

        DeudasTarjetas data = getItem(position);

        if(data!=null){
            viewHolder.tv_moneda_deuda.setText(data.getSignoMoneda());
        } else {
            viewHolder.tv_moneda_deuda.setText("");
        }

        return view;
    }

    public static final class ViewHolder{
        TextView tv_moneda_deuda;
    }

    public void setNewListDeudasTarjetas(ArrayList<DeudasTarjetas> listDeudasTarjetas){
        items = listDeudasTarjetas;
    }
}
