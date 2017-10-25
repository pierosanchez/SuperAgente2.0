package com.example.ctorres.superagentemovil3.dao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;

/**
 * Created by CTORRES on 18/05/2017.
 */

public class DomicilioUsuarioAdapter extends BaseAdapter {

    ArrayList<UsuarioEntity> items;
    Context context;
    LayoutInflater layoutInflater = null;

    public DomicilioUsuarioAdapter(ArrayList<UsuarioEntity> items, Context context) {
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
        return null;
    }

    public static final class ViewHolder{
        TextView tv_nombre, tv_apellido;
    }

    public void setNewListDomicilioUsuario(ArrayList<UsuarioEntity> listUsuarioReniec){
        items = listUsuarioReniec;
    }
}
