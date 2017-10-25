package com.example.ctorres.superagentemovil3.dao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;

import java.util.ArrayList;

/**
 * Created by CTORRES on 18/05/2017.
 */

public class CuentasBeneficiarioAdapter extends BaseAdapter {

    ArrayList<BeneficiarioEntity> items;
    Context context;
    LayoutInflater layoutInflater = null;

    public CuentasBeneficiarioAdapter(ArrayList<BeneficiarioEntity> items, Context context) {
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
    public BeneficiarioEntity getItem(int position) {
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
        View view = layoutInflater.inflate(R.layout.row_cuentas_beneficiario,null);

        //viewHolder.tv_apellido = (TextView) view.findViewById(R.id.tv_apellido);
        viewHolder.tv_cuenta_beneficiario = (TextView) view.findViewById(R.id.tv_cuenta_beneficiario);

        //viewHolder.tv_apellido.setText(String.valueOf(getItem(position).getApellido()));
        viewHolder.tv_cuenta_beneficiario.setText(String.valueOf(getItem(position).getCod_interbancario()));

        BeneficiarioEntity data = getItem(position);

        if(data!=null){
            //viewHolder.tv_apellido.setText(data.getApellido());
            viewHolder.tv_cuenta_beneficiario.setText(data.getCod_interbancario());
        } else {
            //viewHolder.tv_apellido.setText("");
            viewHolder.tv_cuenta_beneficiario.setText("");
        }

        return view;
    }

    public static final class ViewHolder{
        TextView tv_cuenta_beneficiario, tv_apellido;
    }

    public void setNewListBeneficiario(ArrayList<BeneficiarioEntity> listBeneficiario){
        items = listBeneficiario;
    }
}
