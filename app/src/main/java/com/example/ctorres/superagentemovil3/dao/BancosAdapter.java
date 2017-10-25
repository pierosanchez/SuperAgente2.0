package com.example.ctorres.superagentemovil3.dao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.BancosEntity;
import com.example.ctorres.superagentemovil3.entity.ServiciosPublicEntity;

import java.util.ArrayList;

/**
 * Created by CTORRES on 18/05/2017.
 */

public class BancosAdapter extends BaseAdapter {

    ArrayList<BancosEntity> items;
    Context context;
    LayoutInflater layoutInflater = null;

    public BancosAdapter(ArrayList<BancosEntity> items, Context context) {
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
    public BancosEntity getItem(int position) {
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
        View view = layoutInflater.inflate(R.layout.row_bancos,null);

        viewHolder.tv_bancos = (TextView) view.findViewById(R.id.tv_bancos);

        viewHolder.tv_bancos.setText(String.valueOf(getItem(position).getDesc_breve_banco()));

        BancosEntity data = getItem(position);

        if(data!=null){
            viewHolder.tv_bancos.setText(data.getDesc_breve_banco());
        } else {
            viewHolder.tv_bancos.setText("");
        }

        return view;
    }

    public static final class ViewHolder{
        TextView tv_bancos;
    }

    public void setNewListbancos(ArrayList<BancosEntity> listBeneficiario){
        items = listBeneficiario;
    }
}
