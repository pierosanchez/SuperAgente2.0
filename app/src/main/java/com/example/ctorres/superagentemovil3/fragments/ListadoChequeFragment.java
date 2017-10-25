package com.example.ctorres.superagentemovil3.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ctorres.superagentemovil3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListadoChequeFragment extends Fragment {

    String tipoCheque[] = {"Con Delivery", "Con Recojo"};
    Spinner spinnerTipoCheque;
    TextView tv_fr_tipo_cheque, tv_tipo_moneda_comision_cheque_delivery, tv_comision_cheque_delivery, tv_tipo_moneda_comision_cheque, tv_comision_cheque, tv_total_pagar_transferencia;
    LinearLayout linearLayoutGastosEnvio;
    private View rootView;
    String tipomoneda;


    public ListadoChequeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_listado_cheque, container, false);

        spinnerTipoCheque = (Spinner) rootView.findViewById(R.id.spinnerTipoCheque);

        tv_fr_tipo_cheque = (TextView) rootView.findViewById(R.id.tv_fr_tipo_cheque);
        tv_comision_cheque = (TextView) rootView.findViewById(R.id.tv_comision_cheque);
        tv_comision_cheque_delivery = (TextView) rootView.findViewById(R.id.tv_comision_cheque_delivery);
        tv_tipo_moneda_comision_cheque = (TextView) rootView.findViewById(R.id.tv_tipo_moneda_comision_cheque);
        tv_tipo_moneda_comision_cheque_delivery = (TextView) rootView.findViewById(R.id.tv_tipo_moneda_comision_cheque_delivery);
        tv_total_pagar_transferencia = (TextView) getActivity().findViewById(R.id.tv_total_pagar_transferencia);

        linearLayoutGastosEnvio =(LinearLayout) rootView.findViewById(R.id.linearLayoutGastosEnvio);

        tipomoneda = getArguments().getString("tipomoneda");

        tv_tipo_moneda_comision_cheque_delivery.setText(tipomoneda);
        tv_tipo_moneda_comision_cheque.setText(tipomoneda);
        tv_total_pagar_transferencia.setText(CalculoTotalCheque());

        CargarTipoCheque();

        spinnerTipoCheque.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv_fr_tipo_cheque.setText(tipocheque());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tv_fr_tipo_cheque.setText(tipocheque());
            }
        });

        return rootView;
    }

    public void CargarTipoCheque(){
        ArrayAdapter<String> adapterTipoCheque = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tipoCheque);
        spinnerTipoCheque.setAdapter(adapterTipoCheque);
    }

    public String tipocheque(){
        String tipo = spinnerTipoCheque.getSelectedItem().toString();
        String msg;

        if (tipo.equals("Con Delivery")){
            msg = "Enviar a: Av. La Marina cdra. 25, San Miguel";
            linearLayoutGastosEnvio.setVisibility(View.VISIBLE);
            CalculoTotalChequeDelivery();
            tv_total_pagar_transferencia.setText(CalculoTotalChequeDelivery());
        } else {
            linearLayoutGastosEnvio.setVisibility(View.GONE);
            msg = "Recoger Cheque en Direccion: Av. 28 de Julio 1326, Miraflores";
            tv_total_pagar_transferencia.setText(CalculoTotalCheque());
        }
        return msg;
    }

    public String CalculoTotalCheque(){
        String total, comisioncheque, comisiontran, importe;

        TextView tv_comision_monto = (TextView) getActivity().findViewById(R.id.tv_comision_monto);
        TextView imp_cuenta_tarjeta = (TextView) getActivity().findViewById(R.id.txt_importe_cuenta_tarjeta);
        comisiontran = tv_comision_monto.getText().toString();
        importe = imp_cuenta_tarjeta.getText().toString();
        comisioncheque = tv_comision_cheque.getText().toString();

        double comisionChq = Double.parseDouble(comisioncheque);
        double importeV = Double.parseDouble(importe);
        double comision = Double.parseDouble(comisiontran);

        double totalPago = importeV + comision + comisionChq;

        total = String.valueOf(totalPago);

        return total;
    }

    public String CalculoTotalChequeDelivery(){
        String total, comisioncheque, comisiontran, importe, delivery;

        TextView tv_comision_monto = (TextView) getActivity().findViewById(R.id.tv_comision_monto);
        TextView imp_cuenta_tarjeta = (TextView) getActivity().findViewById(R.id.txt_importe_cuenta_tarjeta);

        delivery = tv_comision_cheque_delivery.getText().toString();
        comisiontran = tv_comision_monto.getText().toString();
        importe = imp_cuenta_tarjeta.getText().toString();
        comisioncheque = tv_comision_cheque.getText().toString();

        double comisionChq = Double.parseDouble(comisioncheque);
        double importeV = Double.parseDouble(importe);
        double comision = Double.parseDouble(comisiontran);
        double envio = Double.parseDouble(delivery);

        double totalPago = importeV + comision + comisionChq + envio;

        total = String.valueOf(totalPago);

        return total;
    }

}
