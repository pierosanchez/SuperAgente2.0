package com.example.ctorres.superagentemovil3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;

/**
 * Created by CTORRES on 11/08/2017.
 */

public class TarjetasMantenimientoAdapater extends BaseAdapter {

    ArrayList<UsuarioEntity> items;
    Context context;
    LayoutInflater layoutInflater = null;

    public TarjetasMantenimientoAdapater(ArrayList<UsuarioEntity> items, Context context) {
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
    public UsuarioEntity getItem(int position) {
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
        View view = layoutInflater.inflate(R.layout.row_listado_cuenta_mantenimiento, null);

        viewHolder.chkb_registro = (TextView) view.findViewById(R.id.chkb_registro);
        viewHolder.tv_tipo_tarjeta = (TextView) view.findViewById(R.id.tv_tipo_tarjeta);

        viewHolder.iv_emisor_tarjeta_mantenimiento = (ImageView) view.findViewById(R.id.iv_emisor_tarjeta_mantenimiento);

        viewHolder.chkb_registro.setText(String.valueOf(getItem(position).getNumeroTarjeta()));

        UsuarioEntity data = getItem(position);

        if (data != null) {
            if (data.getCod_emisor_tarjeta() == 1) {
                if (data.getTipo_tarjeta() == 1) {
                    if (data.getValidacionTarjeta().equals("Firma")) {
                        viewHolder.iv_emisor_tarjeta_mantenimiento.setImageResource(R.drawable.visaicon);
                        //viewHolder.tv_emisor_tarjeta.setText("Crédito");
                        viewHolder.chkb_registro.setText(data.getNumeroTarjeta());
                        viewHolder.tv_tipo_tarjeta.setText("CRÉDITO CON FIRMA");
                    } else if (data.getValidacionTarjeta().equals("Pin")){
                        viewHolder.iv_emisor_tarjeta_mantenimiento.setImageResource(R.drawable.visaicon);
                        //viewHolder.tv_emisor_tarjeta.setText("Crédito");
                        viewHolder.chkb_registro.setText(data.getNumeroTarjeta());
                        viewHolder.tv_tipo_tarjeta.setText("CRÉDITO CON PIN");
                    } else {
                        viewHolder.iv_emisor_tarjeta_mantenimiento.setImageResource(R.drawable.visaicon);
                        //viewHolder.tv_emisor_tarjeta.setText("Crédito");
                        viewHolder.chkb_registro.setText(data.getNumeroTarjeta());
                        viewHolder.tv_tipo_tarjeta.setText("CRÉDITO CON FIRMA");
                    }
                } else {
                    viewHolder.iv_emisor_tarjeta_mantenimiento.setImageResource(R.drawable.visaicon);
                    //viewHolder.tv_emisor_tarjeta.setText("Débito");
                    viewHolder.chkb_registro.setText(data.getNumeroTarjeta());
                    viewHolder.tv_tipo_tarjeta.setText("DÉBITO CON PIN");
                }
            } else if (data.getCod_emisor_tarjeta() == 2) {
                if (data.getTipo_tarjeta() == 1) {
                    if (data.getValidacionTarjeta().equals("Firma")) {
                        viewHolder.iv_emisor_tarjeta_mantenimiento.setImageResource(R.drawable.mastercardlogo);
                        //viewHolder.tv_emisor_tarjeta.setText("Crédito");
                        viewHolder.chkb_registro.setText(data.getNumeroTarjeta());
                        viewHolder.tv_tipo_tarjeta.setText("CRÉDITO CON FIRMA");
                    } else if (data.getValidacionTarjeta().equals("Pin")){
                        viewHolder.iv_emisor_tarjeta_mantenimiento.setImageResource(R.drawable.mastercardlogo);
                        //viewHolder.tv_emisor_tarjeta.setText("Crédito");
                        viewHolder.chkb_registro.setText(data.getNumeroTarjeta());
                        viewHolder.tv_tipo_tarjeta.setText("CRÉDITO CON PIN");
                    } else {
                        viewHolder.iv_emisor_tarjeta_mantenimiento.setImageResource(R.drawable.mastercardlogo);
                        //viewHolder.tv_emisor_tarjeta.setText("Crédito");
                        viewHolder.chkb_registro.setText(data.getNumeroTarjeta());
                        viewHolder.tv_tipo_tarjeta.setText("CRÉDITO CON FIRMA");
                    }
                } else {
                    viewHolder.iv_emisor_tarjeta_mantenimiento.setImageResource(R.drawable.mastercardlogo);
                    //viewHolder.tv_emisor_tarjeta.setText("Débito");
                    viewHolder.chkb_registro.setText(data.getNumeroTarjeta());
                    viewHolder.tv_tipo_tarjeta.setText("DÉBITO CON PIN");
                }
            } else {
                if (data.getTipo_tarjeta() == 1) {
                    if (data.getValidacionTarjeta().equals("Firma")) {
                        viewHolder.iv_emisor_tarjeta_mantenimiento.setImageResource(R.drawable.americanexpressicon);
                        //viewHolder.tv_emisor_tarjeta.setText("Crédito");
                        viewHolder.chkb_registro.setText(data.getNumeroTarjeta());
                        viewHolder.tv_tipo_tarjeta.setText("CRÉDITO CON FIRMA");
                    } else if (data.getValidacionTarjeta().equals("Pin")){
                        viewHolder.iv_emisor_tarjeta_mantenimiento.setImageResource(R.drawable.americanexpressicon);
                        //viewHolder.tv_emisor_tarjeta.setText("Crédito");
                        viewHolder.chkb_registro.setText(data.getNumeroTarjeta());
                        viewHolder.tv_tipo_tarjeta.setText("CRÉDITO CON PIN");
                    } else {
                        viewHolder.iv_emisor_tarjeta_mantenimiento.setImageResource(R.drawable.americanexpressicon);
                        //viewHolder.tv_emisor_tarjeta.setText("Crédito");
                        viewHolder.chkb_registro.setText(data.getNumeroTarjeta());
                        viewHolder.tv_tipo_tarjeta.setText("CRÉDITO CON FIRMA");
                    }
                } else {
                    viewHolder.iv_emisor_tarjeta_mantenimiento.setImageResource(R.drawable.americanexpressicon);
                    //viewHolder.tv_emisor_tarjeta.setText("Débito");
                    viewHolder.chkb_registro.setText(data.getNumeroTarjeta());
                    viewHolder.tv_tipo_tarjeta.setText("DÉBITO CON PIN");
                }
            }
        } else {
            viewHolder.chkb_registro.setText("");
        }

        return view;
    }

    public static final class ViewHolder{
        TextView chkb_registro, tv_tipo_tarjeta;
        ImageView iv_emisor_tarjeta_mantenimiento;
    }

    public void setNewListUsuario(ArrayList<UsuarioEntity> usuarioEntities){
        items = usuarioEntities;
    }
}
