package com.example.ctorres.superagentemovil3.dao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;

/**
 * Created by CTORRES on 18/05/2017.
 */

public class UsuarioAdapter extends BaseAdapter {

    ArrayList<UsuarioEntity> items;
    Context context;
    LayoutInflater layoutInflater = null;

    public UsuarioAdapter(ArrayList<UsuarioEntity> items, Context context) {
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
        View view = layoutInflater.inflate(R.layout.row_tarjeta_usuario_listado,null);

        viewHolder.tv_nom_usu = (TextView) view.findViewById(R.id.tv_emisor_tarjeta);
        viewHolder.tv_ape_usu = (TextView) view.findViewById(R.id.tv_numero_tarjeta);

        viewHolder.tv_nom_usu.setText(getItem(position).getNombre());
        viewHolder.tv_ape_usu.setText(getItem(position).getApellido());

        UsuarioEntity data = getItem(position);

        if(data!=null){
            viewHolder.tv_nom_usu.setText(data.getNombre());
            viewHolder.tv_ape_usu.setText(data.getApellido());
        } else {
            viewHolder.tv_nom_usu.setText("");
            viewHolder.tv_ape_usu.setText("");
        }

        return view;
    }

    public static final class ViewHolder{
        TextView tv_nom_usu, tv_ape_usu;
    }

    public void setNewListBeneficiario(ArrayList<UsuarioEntity> listBeneficiario){
        items = listBeneficiario;
    }
}
