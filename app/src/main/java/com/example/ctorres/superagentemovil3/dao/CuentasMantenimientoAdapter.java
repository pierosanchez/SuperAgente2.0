package com.example.ctorres.superagentemovil3.dao;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.ctorres.superagentemovil3.entity.CuentaEntity;
import com.example.ctorres.superagentemovil3.superagente.ActualizarCuenta;
import com.example.ctorres.superagentemovil3.superagente.ListadoCuentasUsuario;
import com.example.ctorres.superagentemovil3.utils.Constante;
import com.example.ctorres.superagentemovil3.utils.Global;

import java.util.ArrayList;

/**
 * Created by CTORRES on 11/08/2017.
 */

public class CuentasMantenimientoAdapter extends BaseAdapter {

    ArrayList<CuentaEntity> items;
    Context context;
    LayoutInflater layoutInflater = null;
    String id_cuenta;


    public CuentasMantenimientoAdapter(ArrayList<CuentaEntity> items, Context context) {
        this.items = items;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (items == null){
            return 0;
        } else {
            return items.size();
        }
    }

    @Override
    public CuentaEntity getItem(int position) {
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

        viewHolder.iv_eliminar_beneficiario = (ImageView) view.findViewById(R.id.iv_eliminar_beneficiario);
        viewHolder.iv_editar_beneficiario = (ImageView) view.findViewById(R.id.iv_editar_beneficiario);

        viewHolder.chkb_registro.setText(String.valueOf(getItem(position).getNumCuenta()));

        final CuentaEntity data = getItem(position);

        if (data != null) {
            viewHolder.chkb_registro.setText(data.getNumCuenta());

        } else {
            viewHolder.chkb_registro.setText("");
        }

        return view;
    }

    public static final class ViewHolder{
        TextView chkb_registro;
        ImageView iv_editar_beneficiario, iv_eliminar_beneficiario;
    }

    public void setNewListCuenta(ArrayList<CuentaEntity> cuenta){
        items = cuenta;
    }

    private class eliminarBeneficiarioUsuario extends AsyncTask<String, Void, CuentaEntity> {
        @Override
        protected CuentaEntity doInBackground(String... params) {
            CuentaEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.eliminarCuentaUsuario(id_cuenta);

            } catch (Exception e) {
                user = null;
                //fldag_clic_ingreso = 0;;
            }
            return user;
        }
    }

    public void queDeseaHacer() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage("¿Esta seguro que desea eliminar esta cuenta?");
        alertDialog.setTitle("Eliminar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CuentasMantenimientoAdapter.eliminarBeneficiarioUsuario eliminarBeneficiarioUsuario = new CuentasMantenimientoAdapter.eliminarBeneficiarioUsuario();
                eliminarBeneficiarioUsuario.execute();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }
}
