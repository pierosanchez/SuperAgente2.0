package com.example.ctorres.superagentemovil3.dao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.MonedaEntity;
import com.example.ctorres.superagentemovil3.entity.OperadorEntity;

import java.util.ArrayList;

/**
 * Created by CTORRES on 25/10/2017.
 */

public class OperadorAdapter extends BaseAdapter {

    ArrayList<OperadorEntity> items;
    Context context;
    LayoutInflater layoutInflater = null;

    public OperadorAdapter(ArrayList<OperadorEntity> items, Context context) {
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
    public OperadorEntity getItem(int position) {
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
        OperadorAdapter.ViewHolder viewHolder= new OperadorAdapter.ViewHolder();
        View view = layoutInflater.inflate(R.layout.row_operador,null);

        viewHolder.tv_operadora = (TextView) view.findViewById(R.id.tv_operador);

        viewHolder.tv_operadora.setText(String.valueOf(getItem(position).getOpe_nomcomercial()));

        OperadorEntity data = getItem(position);

        if(data!=null){
            viewHolder.tv_operadora.setText(data.getOpe_nomcomercial());
        } else {
            viewHolder.tv_operadora.setText("");
        }

        return view;
    }

    public static final class ViewHolder{
        TextView tv_operadora;
    }

    public void setNewListOperador(ArrayList<OperadorEntity> listOpreradora){
        items = listOpreradora;
    }



}
