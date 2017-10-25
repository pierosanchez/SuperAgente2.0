package com.example.ctorres.superagentemovil3.dao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.CuentaEntity;

import java.util.ArrayList;

/**
 * Created by CTORRES on 18/05/2017.
 */

public class CuentasUsuarioAdapter extends BaseAdapter {

    ArrayList<CuentaEntity> items;
    Context context;
    LayoutInflater layoutInflater = null;

    public CuentasUsuarioAdapter(ArrayList<CuentaEntity> items, Context context) {
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
    public CuentaEntity getItem(int position) {
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
        View view = layoutInflater.inflate(R.layout.row_cuentas_usuario,null);

        //viewHolder.tv_apellido = (TextView) view.findViewById(R.id.tv_apellido);
        viewHolder.tv_cuentas_usuario = (TextView) view.findViewById(R.id.tv_cuentas_usuario);

        //viewHolder.tv_apellido.setText(String.valueOf(getItem(position).getApellido()));
        viewHolder.tv_cuentas_usuario.setText(getItem(position).getNumCuenta());

        CuentaEntity data = getItem(position);

        if(data!=null){
            //viewHolder.tv_apellido.setText(data.getApellido());
            viewHolder.tv_cuentas_usuario.setText(data.getNumCuenta());
        } else {
            //viewHolder.tv_apellido.setText("");
            viewHolder.tv_cuentas_usuario.setText("");
        }

        return view;
    }

    public static final class ViewHolder{
        TextView tv_cuentas_usuario, tv_apellido;
    }

    public void setNewListBeneficiario(ArrayList<CuentaEntity> listBeneficiario){
        items = listBeneficiario;
    }
}
