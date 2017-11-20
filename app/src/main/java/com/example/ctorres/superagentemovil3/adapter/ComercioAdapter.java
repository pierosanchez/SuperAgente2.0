package com.example.ctorres.superagentemovil3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.ComercioEntity;

import java.util.ArrayList;

/**
 * Created by CTORRES on 03/11/2017.
 */

public class ComercioAdapter extends BaseAdapter {

    ArrayList<ComercioEntity> items;
    Context context;
    LayoutInflater layoutInflater = null;

    public ComercioAdapter(ArrayList<ComercioEntity> items, Context context) {
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
    public ComercioEntity getItem(int position) {
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
        ComercioAdapter.ViewHolder viewHolder= new ComercioAdapter.ViewHolder();
        View view = layoutInflater.inflate(R.layout.row_comercio,null);

        viewHolder.tv_comercio = (TextView) view.findViewById(R.id.tv_comercio);

        viewHolder.tv_comercio.setText(String.valueOf(getItem(position).getRaz_social_comercio()));

        ComercioEntity data = getItem(position);

        if(data!=null){
            viewHolder.tv_comercio.setText(data.getRaz_social_comercio());
        } else {
            viewHolder.tv_comercio.setText("");
        }

        return view;
    }

    public static final class ViewHolder{
        TextView tv_comercio;
    }

    public void setNewListcomercio(ArrayList<ComercioEntity> listBeneficiario){
        items = listBeneficiario;
    }
}
