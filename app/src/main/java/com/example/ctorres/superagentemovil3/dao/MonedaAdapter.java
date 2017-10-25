package com.example.ctorres.superagentemovil3.dao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.BancosEntity;
import com.example.ctorres.superagentemovil3.entity.MonedaEntity;

import java.util.ArrayList;

/**
 * Created by CTORRES on 18/05/2017.
 */

public class MonedaAdapter extends BaseAdapter {

    ArrayList<MonedaEntity> items;
    Context context;
    LayoutInflater layoutInflater = null;

    public MonedaAdapter(ArrayList<MonedaEntity> items, Context context) {
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
    public MonedaEntity getItem(int position) {
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
        View view = layoutInflater.inflate(R.layout.row_moneda,null);

        viewHolder.tv_moneda = (TextView) view.findViewById(R.id.tv_moneda);

        viewHolder.tv_moneda.setText(String.valueOf(getItem(position).getSigno_moneda()));

        MonedaEntity data = getItem(position);

        if(data!=null){
            viewHolder.tv_moneda.setText(data.getSigno_moneda());
        } else {
            viewHolder.tv_moneda.setText("");
        }

        return view;
    }

    public static final class ViewHolder{
        TextView tv_moneda;
    }

    public void setNewListMoneda(ArrayList<MonedaEntity> listBeneficiario){
        items = listBeneficiario;
    }
}
