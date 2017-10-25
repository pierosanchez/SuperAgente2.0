package com.example.ctorres.superagentemovil3.dao;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;

/**
 * Created by CTORRES on 18/05/2017.
 */

public class TarjetasUsuarioAdapter extends BaseAdapter {

    ArrayList<UsuarioEntity> items;
    Context context;
    LayoutInflater layoutInflater = null;

    public TarjetasUsuarioAdapter(ArrayList<UsuarioEntity> items, Context context) {
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

        viewHolder.iv_emisor_tarjeta = (ImageView) view.findViewById(R.id.iv_emisor_tarjeta);

        viewHolder.tv_emisor_tarjeta = (TextView) view.findViewById(R.id.tv_emisor_tarjeta);
        viewHolder.tv_numero_tarjeta = (TextView) view.findViewById(R.id.tv_numero_tarjeta);
        viewHolder.tv_tipo_tarjeta = (TextView) view.findViewById(R.id.tv_tipo_tarjeta);

        viewHolder.tv_emisor_tarjeta.setText(getItem(position).getBanco_tarjeta());
        viewHolder.tv_numero_tarjeta.setText(String.valueOf(getItem(position).getNumeroTarjeta()));

        UsuarioEntity data = getItem(position);

        if(data!=null){
            if (data.getCod_emisor_tarjeta() == 1) {
                if (data.getTipo_tarjeta() == 1) {
                    viewHolder.iv_emisor_tarjeta.setImageResource(R.drawable.visaicon);
                    //viewHolder.tv_emisor_tarjeta.setText("Crédito");
                    viewHolder.tv_emisor_tarjeta.setText(data.getBanco_tarjeta());
                    viewHolder.tv_numero_tarjeta.setText(data.getNumeroTarjeta());
                    viewHolder.tv_tipo_tarjeta.setText("FIRMA");
                } else {
                    viewHolder.iv_emisor_tarjeta.setImageResource(R.drawable.visaicon);
                    //viewHolder.tv_emisor_tarjeta.setText("Débito");
                    viewHolder.tv_emisor_tarjeta.setText(data.getBanco_tarjeta());
                    viewHolder.tv_numero_tarjeta.setText(data.getNumeroTarjeta());
                    viewHolder.tv_tipo_tarjeta.setText("PIN");
                }
            } else if (data.getCod_emisor_tarjeta() == 2) {
                if (data.getTipo_tarjeta() == 1) {
                    viewHolder.iv_emisor_tarjeta.setImageResource(R.drawable.mastercardlogo);
                    //viewHolder.tv_emisor_tarjeta.setText("Crédito");
                    viewHolder.tv_emisor_tarjeta.setText(data.getBanco_tarjeta());
                    viewHolder.tv_numero_tarjeta.setText(data.getNumeroTarjeta());
                    viewHolder.tv_tipo_tarjeta.setText("FIRMA");
                } else {
                    viewHolder.iv_emisor_tarjeta.setImageResource(R.drawable.mastercardlogo);
                    //viewHolder.tv_emisor_tarjeta.setText("Débito");
                    viewHolder.tv_numero_tarjeta.setText(data.getNumeroTarjeta());
                    viewHolder.tv_emisor_tarjeta.setText(data.getBanco_tarjeta());
                    viewHolder.tv_tipo_tarjeta.setText("PIN");
                }
            } else {
                if (data.getTipo_tarjeta() == 1) {
                    viewHolder.iv_emisor_tarjeta.setImageResource(R.drawable.americanexpressicon);
                    //viewHolder.tv_emisor_tarjeta.setText("Crédito");
                    viewHolder.tv_emisor_tarjeta.setText(data.getBanco_tarjeta());
                    viewHolder.tv_numero_tarjeta.setText(data.getNumeroTarjeta());
                    viewHolder.tv_tipo_tarjeta.setText("FIRMA");
                } else {
                    viewHolder.iv_emisor_tarjeta.setImageResource(R.drawable.americanexpressicon);
                    //viewHolder.tv_emisor_tarjeta.setText("Débito");
                    viewHolder.tv_emisor_tarjeta.setText(data.getBanco_tarjeta());
                    viewHolder.tv_numero_tarjeta.setText(data.getNumeroTarjeta());
                    viewHolder.tv_tipo_tarjeta.setText("PIN");
                }
            }
        } else {
            viewHolder.tv_emisor_tarjeta.setText("");
            viewHolder.tv_numero_tarjeta.setText("");
        }

        return view;
    }

    public static final class ViewHolder{
        TextView tv_numero_tarjeta, tv_emisor_tarjeta, tv_tipo_tarjeta;
        ImageView iv_emisor_tarjeta;
    }

    public void setNewListBeneficiario(ArrayList<UsuarioEntity> listBeneficiario){
        items = listBeneficiario;
    }
}
