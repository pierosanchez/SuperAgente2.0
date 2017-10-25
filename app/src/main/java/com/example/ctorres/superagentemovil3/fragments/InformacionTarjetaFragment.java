package com.example.ctorres.superagentemovil3.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.superagente.ControlMaximoNumeroCuentas;
import com.example.ctorres.superagentemovil3.superagente.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformacionTarjetaFragment extends Fragment {

    OnClickOpcionFragmento optionSelected;
    private EditText nroTarjetaDigito1,nroTarjetaDigito2,nroTarjetaDigito3,nroTarjetaDigito4;
    private EditText confirmNroTarjetaDigito1,confirmNroTarjetaDigito2,confirmNroTarjetaDigito3,confirmNroTarjetaDigito4;
    private Button agregarTarjeta, terminar;
    private View rootView;
    private StringBuilder numeroTarjeta,confirmNumeroTarjeta;
    private UsuarioEntity usuario;

    public InformacionTarjetaFragment() {
        // Required empty public constructor
    }

    public interface OnClickOpcionFragmento{
        void OnClickOpcionFragmento(int opcion, Object datos, String informacionTarjeta);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_informacion_tarjeta, container, false);
        usuario = getArguments().getParcelable("usuario");

        numeroTarjeta();
        confirmarNumeroTarjeta();

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try{
            agregarTarjeta = (Button) rootView.findViewById(R.id.agregarTarjeta);
            terminar = (Button) rootView.findViewById(R.id.terminar);

            agregarTarjeta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //COMIENZO CODIGO FUNCIONAL DESCOMENTAR PARA MANTENER LA FUNCIONALIDAD DEL PROGRAMA
                    numeroTarjeta = new StringBuilder();
                    confirmNumeroTarjeta = new StringBuilder();

                    numeroTarjeta.append(nroTarjetaDigito1.getText().toString()).append(nroTarjetaDigito2.getText().toString()).append(nroTarjetaDigito3.getText().toString()).append(nroTarjetaDigito4.getText().toString());
                    confirmNumeroTarjeta.append(confirmNroTarjetaDigito1.getText().toString()).append(confirmNroTarjetaDigito2.getText().toString()).append(confirmNroTarjetaDigito3.getText().toString()).append(confirmNroTarjetaDigito4.getText().toString());

                    //return tmp.toString();
                    if(numeroTarjeta.toString().equals(confirmNumeroTarjeta.toString()) && !numeroTarjeta.toString().equals("") && !confirmNumeroTarjeta.toString().equals("")){
                        //Intent intent = new Intent(ClaveAcceso.this, InformacionTarjeta.class);
                        //startActivity(intent);
                        usuario.setNumeroTarjeta(numeroTarjeta.toString().trim());
                        usuario.setConfirmNumeroTarjeta(confirmNumeroTarjeta.toString().trim().replace(" ","%20"));

                        notifyOpcionSelected(1,usuario, "MasTarjetas");
                    } else if(numeroTarjeta.toString().equals("")){
                        Toast.makeText(getActivity(), "Ingrese el numero de tarjeta", Toast.LENGTH_SHORT).show();
                    } else if(confirmNumeroTarjeta.toString().equals("")){
                        Toast.makeText(getActivity(), "Ingrese la confirmacion del numero de tarjeta", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "La confirmación del numero de tarjeta es incorrecto, por favor vuelva a ingresar", Toast.LENGTH_SHORT).show();
                    }
                    //FIN DEL CODIGO FUNCIONAL DESCOMENTAR PARA MANTENER LA FUNCIONALIDAD DEL PROGRAMA
                }
            });
            terminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //COMIENZO CODIGO FUNCIONAL DESCOMENTAR PARA MANTENER LA FUNCIONALIDAD DEL PROGRAMA
                    notifyOpcionSelected(2,null, "");
                    //TERMINO CODIGO FUNCIONAL DESCOMENTAR PARA MANTENER LA FUNCIONALIDAD DEL PROGRAMA
                }
            });

        }catch(Exception e){
            Log.e("ERROR", Log.getStackTraceString(e));
        }
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        if(activity instanceof OnClickOpcionFragmento){
            optionSelected = (OnClickOpcionFragmento)activity;
        } else throw new ClassCastException();
    }

    private void notifyOpcionSelected(int opcion, Object datos, String informacionTarjeta){
        optionSelected.OnClickOpcionFragmento(opcion,datos,informacionTarjeta);
    }

    private void numeroTarjeta(){
        nroTarjetaDigito1 = (EditText) rootView.findViewById(R.id.nroTarjetaDigito1);
        nroTarjetaDigito2 = (EditText) rootView.findViewById(R.id.nroTarjetaDigito2);
        nroTarjetaDigito3 = (EditText) rootView.findViewById(R.id.nroTarjetaDigito3);
        nroTarjetaDigito4 = (EditText) rootView.findViewById(R.id.nroTarjetaDigito4);

        nroTarjetaDigito1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (nroTarjetaDigito1.getText().toString().length() == 4)     //size as per your requirement
                {
                    nroTarjetaDigito2.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        nroTarjetaDigito2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (nroTarjetaDigito2.getText().toString().length() == 4)     //size as per your requirement
                {
                    nroTarjetaDigito3.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        nroTarjetaDigito3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (nroTarjetaDigito3.getText().toString().length() == 4)     //size as per your requirement
                {
                    nroTarjetaDigito4.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        nroTarjetaDigito4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (nroTarjetaDigito4.getText().toString().length() == 4)     //size as per your requirement
                {
                    confirmNroTarjetaDigito1.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
    }

    private void confirmarNumeroTarjeta(){
        confirmNroTarjetaDigito1 = (EditText) rootView.findViewById(R.id.confirmNroTarjetaDigito1);
        confirmNroTarjetaDigito2 = (EditText) rootView.findViewById(R.id.confirmNroTarjetaDigito2);
        confirmNroTarjetaDigito3 = (EditText) rootView.findViewById(R.id.confirmNroTarjetaDigito3);
        confirmNroTarjetaDigito4 = (EditText) rootView.findViewById(R.id.confirmNroTarjetaDigito4);

        confirmNroTarjetaDigito1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (confirmNroTarjetaDigito1.getText().toString().length() == 4)     //size as per your requirement
                {
                    confirmNroTarjetaDigito2.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        confirmNroTarjetaDigito2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (confirmNroTarjetaDigito2.getText().toString().length() == 4)     //size as per your requirement
                {
                    confirmNroTarjetaDigito3.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        confirmNroTarjetaDigito3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (confirmNroTarjetaDigito3.getText().toString().length() == 4)     //size as per your requirement
                {
                    confirmNroTarjetaDigito4.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        confirmNroTarjetaDigito4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (confirmNroTarjetaDigito4.getText().toString().length() == 4)     //size as per your requirement
                {
                    agregarTarjeta.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
    }
}
