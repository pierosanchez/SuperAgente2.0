package com.example.ctorres.superagentemovil3.dao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;

import java.util.ArrayList;

/**
 * Created by CTORRES on 17/08/2017.
 */

public class DetalleBeneficiarioAdapter extends BaseAdapter{

    ArrayList<BeneficiarioEntity> item;
    Context context;
    LayoutInflater layoutInflater = null;

    public DetalleBeneficiarioAdapter(ArrayList<BeneficiarioEntity> item, Context context) {
        this.item = item;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        if (item == null){
            return 0;
        } else {
            return item.size();
        }
    }

    @Override
    public BeneficiarioEntity getItem(int position){
        if (item == null){
            return null;
        } else {
            return item.get(position);
        }
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public void setNewListBeneficiario(ArrayList<BeneficiarioEntity> listBeneficiario){
        item = listBeneficiario;
    }
}
