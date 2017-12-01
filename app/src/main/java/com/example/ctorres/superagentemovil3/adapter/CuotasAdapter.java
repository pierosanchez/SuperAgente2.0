package com.example.ctorres.superagentemovil3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.CuotasEntity;
import com.example.ctorres.superagentemovil3.entity.MonedaEntity;

import java.util.ArrayList;

/**
 * Created by CTORRES on 18/05/2017.
 */

public class CuotasAdapter extends BaseAdapter {

    ArrayList<CuotasEntity> items;
    Context context;
    LayoutInflater layoutInflater = null;

    public CuotasAdapter(ArrayList<CuotasEntity> items, Context context) {
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
    public CuotasEntity getItem(int position) {
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
        View view = layoutInflater.inflate(R.layout.row_cuotas,null);

        viewHolder.tv_cuota = (TextView) view.findViewById(R.id.tv_cuota);

        viewHolder.tv_cuota.setText(String.valueOf(getItem(position).getNumCuota()));

        CuotasEntity data = getItem(position);

        if(data!=null){
            viewHolder.tv_cuota.setText(data.getNumCuota());
        } else {
            viewHolder.tv_cuota.setText("");
        }

        return view;
    }

    public static final class ViewHolder{
        TextView tv_cuota;
    }

    public void setNewListCuota(ArrayList<CuotasEntity> listCuota){
        items = listCuota;
    }
}
