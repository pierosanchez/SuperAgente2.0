package com.example.ctorres.superagentemovil3.dao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;

import java.util.ArrayList;

/**
 * Created by CTORRES on 18/05/2017.
 */

public class TarjetasBeneficiarioAdapter extends BaseAdapter {

    ArrayList<BeneficiarioEntity> items;
    Context context;
    LayoutInflater layoutInflater = null;

    public TarjetasBeneficiarioAdapter(ArrayList<BeneficiarioEntity> items, Context context) {
        this.items = items;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (items == null) {
            return 0;
        } else {
            return items.size();
        }
    }

    @Override
    public BeneficiarioEntity getItem(int position) {
        if (items == null) {
            return null;
        } else {
            return items.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        View view = layoutInflater.inflate(R.layout.row_tarjetas_beneficiario, null);

        viewHolder.iv_emisor_tarjeta = (ImageView) view.findViewById(R.id.iv_emisor_tarjeta);
        viewHolder.tv_tarjeta_beneficiario = (TextView) view.findViewById(R.id.tv_tarjeta_beneficiario);

        //viewHolder.iv_emisor_tarjeta.setText(String.valueOf(getItem(position).getApellido()));
        viewHolder.tv_tarjeta_beneficiario.setText(String.valueOf(getItem(position).getNum_tarjeta_beneficiario()));

        BeneficiarioEntity data = getItem(position);

        if (data != null) {
            if (data.getCod_emisor_tarjeta() == 1) {
                viewHolder.iv_emisor_tarjeta.setImageResource(R.drawable.visaicon);
                viewHolder.tv_tarjeta_beneficiario.setText(data.getNum_tarjeta_beneficiario());
            } else if (data.getCod_emisor_tarjeta() == 2) {
                viewHolder.iv_emisor_tarjeta.setImageResource(R.drawable.mastercardlogo);
                viewHolder.tv_tarjeta_beneficiario.setText(data.getNum_tarjeta_beneficiario());
            } else {
                viewHolder.iv_emisor_tarjeta.setImageResource(R.drawable.americanexpressicon);
                viewHolder.tv_tarjeta_beneficiario.setText(data.getNum_tarjeta_beneficiario());
            }
        } else {
            //viewHolder.iv_emisor_tarjeta.setText("");
            viewHolder.tv_tarjeta_beneficiario.setText("");
        }

        return view;
    }

    public static final class ViewHolder {
        TextView tv_tarjeta_beneficiario;
        ImageView iv_emisor_tarjeta;
    }

    public void setNewListBeneficiario(ArrayList<BeneficiarioEntity> listBeneficiario) {
        items = listBeneficiario;
    }
}
