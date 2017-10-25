package com.example.ctorres.superagentemovil3.fragments;

import android.content.Context;
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
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClaveAccesoFragment extends Fragment {

    private Button btn_siguiente_informacion;
    private EditText creaKeyAccesDigito1,creaKeyAccesDigito2,creaKeyAccesDigito3,creaKeyAccesDigito4,creaKeyAccesDigito5,creaKeyAccesDigito6,creaKeyAccesDigito7;
    private EditText confirmKeyAccesDigito1,confirmKeyAccesDigito2,confirmKeyAccesDigito3,confirmKeyAccesDigito4,confirmKeyAccesDigito5,confirmKeyAccesDigito6,confirmKeyAccesDigito7;
    private EditText segundaKeyAccesDigito1,segundaKeyAccesDigito2,segundaKeyAccesDigito3,segundaKeyAccesDigito4,segundaKeyAccesDigito5,segundaKeyAccesDigito6,segundaKeyAccesDigito7;
    private EditText confirmSegundaKeyAccesDigito1,confirmSegundaKeyAccesDigito2,confirmSegundaKeyAccesDigito3,confirmSegundaKeyAccesDigito4,confirmSegundaKeyAccesDigito5,confirmSegundaKeyAccesDigito6,confirmSegundaKeyAccesDigito7;
    private EditText claveAcceso,confirmClaveAcceso, segundaClaveAcceso, confirmSegundaClaveAcceso, txt_pregunta_otros;
    private String pregunta="";
    private RadioButton rbtn_fecha_nacimiento, rbtn_nombre_mascota, rbtn_equipo_futbol, rbtn_distrito_donde_vives, rbtn_numero_pasaporte, rbtn_otro;
    //private StringBuilder claveAcceso,confirmClaveAcceso, segundaClaveAcceso, confirmSegundaClaveAcceso;
    UsuarioEntity usuario;
    private View rootView;
    OnClickOpcionFragmento optionSelected;

    public interface OnClickOpcionFragmento{
        void OnClickOpcionFragmento(int opcion, Object datos, String informacionTarjeta);
    }

    public ClaveAccesoFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_clave_acceso, container, false);
        usuario = getArguments().getParcelable("usuario");
        addClaveTextChangedListener();
        tipoSegundaClave();
        /*generarClaveAcceso();
        confirmarClaveAcceso();
        tipoSegundaClave();
        generarSegundaClaveAcceso();
        confirmarSegundaClaveAcceso();*/
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try{
            btn_siguiente_informacion = (Button) rootView.findViewById(R.id.btn_siguiente_informacion);
            txt_pregunta_otros = (EditText) rootView.findViewById(R.id.txt_pregunta_otros);
            btn_siguiente_informacion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*claveAcceso = new StringBuilder();
                    confirmClaveAcceso = new StringBuilder();
                    segundaClaveAcceso = new StringBuilder();
                    confirmSegundaClaveAcceso = new StringBuilder();

                    claveAcceso.append(creaKeyAccesDigito1.getText().toString()).append(creaKeyAccesDigito2.getText().toString()).append(creaKeyAccesDigito3.getText().toString()).append(creaKeyAccesDigito4.getText().toString()).append(creaKeyAccesDigito5.getText().toString()).append(creaKeyAccesDigito6.getText().toString()).append(creaKeyAccesDigito7.getText().toString());
                    confirmClaveAcceso.append(confirmKeyAccesDigito1.getText().toString()).append(confirmKeyAccesDigito2.getText().toString()).append(confirmKeyAccesDigito3.getText().toString()).append(confirmKeyAccesDigito4.getText().toString()).append(confirmKeyAccesDigito5.getText().toString()).append(confirmKeyAccesDigito6.getText().toString()).append(confirmKeyAccesDigito7.getText().toString());
                    segundaClaveAcceso.append(segundaKeyAccesDigito1.getText().toString()).append(segundaKeyAccesDigito2.getText().toString()).append(segundaKeyAccesDigito3.getText().toString()).append(segundaKeyAccesDigito4.getText().toString()).append(segundaKeyAccesDigito5.getText().toString()).append(segundaKeyAccesDigito6.getText().toString()).append(segundaKeyAccesDigito7.getText().toString());
                    confirmSegundaClaveAcceso.append(confirmSegundaKeyAccesDigito1.getText().toString()).append(confirmSegundaKeyAccesDigito2.getText().toString()).append(confirmSegundaKeyAccesDigito3.getText().toString()).append(confirmSegundaKeyAccesDigito4.getText().toString()).append(confirmSegundaKeyAccesDigito5.getText().toString()).append(confirmSegundaKeyAccesDigito6.getText().toString()).append(confirmSegundaKeyAccesDigito7.getText().toString());*/

                    //return tmp.toString();

                    /*Bundle arguments =new Bundle();
                    InformacionTarjetaFragment fragment = new InformacionTarjetaFragment();
                    fragment.setArguments(arguments);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_fragment,fragment);
                    transaction.commit();*/


                    //ESTA PARTE DEL CODIGO ES FUNCIONAL
                    if(!claveAcceso.getText().toString().equals("") && !segundaClaveAcceso.getText().toString().equals("") && !pregunta.toString().equals("")){
                        if(claveAcceso.getText().toString().equals(confirmClaveAcceso.getText().toString()) && segundaClaveAcceso.getText().toString().equals(confirmSegundaClaveAcceso.getText().toString())){
                            usuario.setClaveAcceso(claveAcceso.getText().toString().trim());
                            usuario.setPregunta(pregunta.toString().trim().replace(" ","%20"));
                            usuario.setSegundaClaveAcceso(segundaClaveAcceso.getText().toString().trim());

                            notifyOpcionSelected(1,usuario, "PrimeraTarjeta");
                        } else if(claveAcceso.getText().toString().equals(confirmClaveAcceso.getText().toString()) && !segundaClaveAcceso.getText().toString().equals(confirmSegundaClaveAcceso.getText().toString())){
                            Toast.makeText(getActivity(), "Su segunda clave de acceso es incorrecto, por favor vuelva a ingresar.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Su clave de acceso es incorrecto, por favor vuelva a ingresar", Toast.LENGTH_SHORT).show();
                        }
                    } else if(claveAcceso.getText().toString().equals("") || confirmClaveAcceso.getText().toString().equals("")){
                        Toast.makeText(getActivity(), "Ingrese su clave de acceso.", Toast.LENGTH_SHORT).show();
                    }else if(txt_pregunta_otros.getVisibility() == View.VISIBLE && txt_pregunta_otros.getText().toString().equals("")){
                        Toast.makeText(getActivity(), "Ingrese su Pregunta", Toast.LENGTH_SHORT).show();
                    } else if(pregunta.equals("")){
                        Toast.makeText(getActivity(), "Seleccione el tipo de pregunta", Toast.LENGTH_SHORT).show();
                    } else if(segundaClaveAcceso.getText().toString().equals("") || confirmSegundaClaveAcceso.getText().toString().equals("")){
                        Toast.makeText(getActivity(), "Ingrese su segunda clave de acceso.", Toast.LENGTH_SHORT).show();
                    }
                    //FIN DE LA PARTE FUNCIONAL DEL CODIGO
                }
            });
        }catch(Exception e){
            Log.e("ERROR", Log.getStackTraceString(e));
        }
        //FIN DE LA PARTE FUNCIONAL DEL CODIGO
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        if(activity instanceof OnClickOpcionFragmento){
            optionSelected = (OnClickOpcionFragmento)activity;
        } else throw new ClassCastException();
    }

    private void notifyOpcionSelected(int opcion, Object datos, String informacionTarjeta){
        optionSelected.OnClickOpcionFragmento(opcion, datos, informacionTarjeta);
    }

    private void addClaveTextChangedListener(){
        claveAcceso = (EditText) rootView.findViewById(R.id.claveAcceso);
        confirmClaveAcceso = (EditText) rootView.findViewById(R.id.confirmClaveAcceso);
        segundaClaveAcceso = (EditText) rootView.findViewById(R.id.segundaClaveAcceso);
        confirmSegundaClaveAcceso = (EditText) rootView.findViewById(R.id.confirmSegundaClaveAcceso);

        claveAcceso.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (claveAcceso.getText().toString().length() == 7)     //size as per your requirement
                {
                    //claveAcceso.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //claveAcceso.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmClaveAcceso.requestFocus();
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

        confirmClaveAcceso.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (confirmClaveAcceso.getText().toString().length() == 7)     //size as per your requirement
                {
                    //confirmClaveAcceso.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //confirmClaveAcceso.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    rbtn_fecha_nacimiento.requestFocus();
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

        segundaClaveAcceso.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (segundaClaveAcceso.getText().toString().length() == 7)     //size as per your requirement
                {
                    segundaClaveAcceso.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    segundaClaveAcceso.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmSegundaClaveAcceso.requestFocus();
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

        confirmSegundaClaveAcceso.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (confirmSegundaClaveAcceso.getText().toString().length() == 7)     //size as per your requirement
                {
                    confirmSegundaClaveAcceso.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmSegundaClaveAcceso.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    btn_siguiente_informacion.requestFocus();
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

    private void tipoSegundaClave(){
        rbtn_fecha_nacimiento = (RadioButton) rootView.findViewById(R.id.fechaNacimiento);
        rbtn_nombre_mascota = (RadioButton) rootView.findViewById(R.id.nombreMascota);
        rbtn_equipo_futbol = (RadioButton) rootView.findViewById(R.id.equipoFutbol);
        rbtn_distrito_donde_vives = (RadioButton) rootView.findViewById(R.id.distritoDondeVive);
        rbtn_numero_pasaporte = (RadioButton) rootView.findViewById(R.id.numeroPasaporte);
        rbtn_otro = (RadioButton) rootView.findViewById(R.id.otros);
        txt_pregunta_otros = (EditText) rootView.findViewById(R.id.txt_pregunta_otros);

        rbtn_fecha_nacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_pregunta_otros.setVisibility(View.INVISIBLE);
                pregunta = "fecha de nacimiento";
                segundaClaveAcceso.requestFocus();
            }
        });

        rbtn_nombre_mascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt_pregunta_otros.setVisibility(View.INVISIBLE);
                pregunta = "nombre de mascota";
                segundaClaveAcceso.requestFocus();
            }
        });

        rbtn_equipo_futbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_pregunta_otros.setVisibility(View.INVISIBLE);
                pregunta = "equipo de futbol";
                segundaClaveAcceso.requestFocus();
            }
        });

        rbtn_distrito_donde_vives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_pregunta_otros.setVisibility(View.INVISIBLE);
                pregunta = "distrito donde vives";
                segundaClaveAcceso.requestFocus();
            }
        });

        rbtn_numero_pasaporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_pregunta_otros.setVisibility(View.INVISIBLE);
                pregunta = "numero de pasaporte";
                segundaClaveAcceso.requestFocus();
            }
        });

        rbtn_otro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pregunta = "otro";
                //pregunta = txt_otros.getText().toString();
                txt_pregunta_otros.setVisibility(View.VISIBLE);
                segundaClaveAcceso.requestFocus();
            }
        });
    }
    /*private void generarClaveAcceso(){
        creaKeyAccesDigito1 = (EditText) rootView.findViewById(R.id.creaKeyAccesDigito1);
        creaKeyAccesDigito2 = (EditText) rootView.findViewById(R.id.creaKeyAccesDigito2);
        creaKeyAccesDigito3 = (EditText) rootView.findViewById(R.id.creaKeyAccesDigito3);
        creaKeyAccesDigito4 = (EditText) rootView.findViewById(R.id.creaKeyAccesDigito4);
        creaKeyAccesDigito6 = (EditText) rootView.findViewById(R.id.creaKeyAccesDigito6);
        creaKeyAccesDigito7 = (EditText) rootView.findViewById(R.id.creaKeyAccesDigito7);
        creaKeyAccesDigito5 = (EditText) rootView.findViewById(R.id.creaKeyAccesDigito5);

        creaKeyAccesDigito1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (creaKeyAccesDigito1.getText().toString().length() == 1)     //size as per your requirement
                {
                    creaKeyAccesDigito1.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    creaKeyAccesDigito1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    creaKeyAccesDigito2.requestFocus();
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

        creaKeyAccesDigito2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (creaKeyAccesDigito2.getText().toString().length() == 1)     //size as per your requirement
                {
                    creaKeyAccesDigito2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    creaKeyAccesDigito2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    creaKeyAccesDigito3.requestFocus();
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

        creaKeyAccesDigito3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (creaKeyAccesDigito3.getText().toString().length() == 1)     //size as per your requirement
                {
                    creaKeyAccesDigito3.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    creaKeyAccesDigito3.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    creaKeyAccesDigito4.requestFocus();
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

        creaKeyAccesDigito4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (creaKeyAccesDigito4.getText().toString().length() == 1)     //size as per your requirement
                {
                    creaKeyAccesDigito4.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    creaKeyAccesDigito4.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    creaKeyAccesDigito5.requestFocus();
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

        creaKeyAccesDigito5.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (creaKeyAccesDigito5.getText().toString().length() == 1)     //size as per your requirement
                {
                    creaKeyAccesDigito5.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    creaKeyAccesDigito5.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    creaKeyAccesDigito6.requestFocus();
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

        creaKeyAccesDigito6.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (creaKeyAccesDigito6.getText().toString().length() == 1)     //size as per your requirement
                {
                    creaKeyAccesDigito6.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    creaKeyAccesDigito6.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    creaKeyAccesDigito7.requestFocus();
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

        creaKeyAccesDigito7.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (creaKeyAccesDigito7.getText().toString().length() == 1)     //size as per your requirement
                {
                    creaKeyAccesDigito7.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    creaKeyAccesDigito7.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmKeyAccesDigito1.requestFocus();
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

    private void confirmarClaveAcceso(){
        confirmKeyAccesDigito1 = (EditText) rootView.findViewById(R.id.confirmKeyAccesDigito1);
        confirmKeyAccesDigito2 = (EditText) rootView.findViewById(R.id.confirmKeyAccesDigito2);
        confirmKeyAccesDigito3 = (EditText) rootView.findViewById(R.id.confirmKeyAccesDigito3);
        confirmKeyAccesDigito4 = (EditText) rootView.findViewById(R.id.confirmKeyAccesDigito4);
        confirmKeyAccesDigito5 = (EditText) rootView.findViewById(R.id.confirmKeyAccesDigito5);
        confirmKeyAccesDigito6 = (EditText) rootView.findViewById(R.id.confirmKeyAccesDigito6);
        confirmKeyAccesDigito7 = (EditText) rootView.findViewById(R.id.confirmKeyAccesDigito7);

        confirmKeyAccesDigito1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (confirmKeyAccesDigito1.getText().toString().length() == 1)     //size as per your requirement
                {
                    confirmKeyAccesDigito1.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmKeyAccesDigito1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmKeyAccesDigito2.requestFocus();
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

        confirmKeyAccesDigito2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (confirmKeyAccesDigito2.getText().toString().length() == 1)     //size as per your requirement
                {
                    confirmKeyAccesDigito2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmKeyAccesDigito2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmKeyAccesDigito3.requestFocus();
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
        confirmKeyAccesDigito3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (confirmKeyAccesDigito3.getText().toString().length() == 1)     //size as per your requirement
                {
                    confirmKeyAccesDigito3.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmKeyAccesDigito3.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmKeyAccesDigito4.requestFocus();
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
        confirmKeyAccesDigito4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (confirmKeyAccesDigito4.getText().toString().length() == 1)     //size as per your requirement
                {
                    confirmKeyAccesDigito4.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmKeyAccesDigito4.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmKeyAccesDigito5.requestFocus();
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
        confirmKeyAccesDigito5.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (confirmKeyAccesDigito5.getText().toString().length() == 1)     //size as per your requirement
                {
                    confirmKeyAccesDigito5.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmKeyAccesDigito5.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmKeyAccesDigito6.requestFocus();
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
        confirmKeyAccesDigito6.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (confirmKeyAccesDigito6.getText().toString().length() == 1)     //size as per your requirement
                {
                    confirmKeyAccesDigito6.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmKeyAccesDigito6.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmKeyAccesDigito7.requestFocus();
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

        confirmKeyAccesDigito7.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (confirmKeyAccesDigito7.getText().toString().length() == 1)     //size as per your requirement
                {
                    confirmKeyAccesDigito7.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmKeyAccesDigito7.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    rbtn_fecha_nacimiento.requestFocus();
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

    private void generarSegundaClaveAcceso(){
        segundaKeyAccesDigito1 = (EditText) rootView.findViewById(R.id.segundaKeyAccesDigito1);
        segundaKeyAccesDigito2 = (EditText) rootView.findViewById(R.id.segundaKeyAccesDigito2);
        segundaKeyAccesDigito3 = (EditText) rootView.findViewById(R.id.segundaKeyAccesDigito3);
        segundaKeyAccesDigito4 = (EditText) rootView.findViewById(R.id.segundaKeyAccesDigito4);
        segundaKeyAccesDigito5 = (EditText) rootView.findViewById(R.id.segundaKeyAccesDigito5);
        segundaKeyAccesDigito6 = (EditText) rootView.findViewById(R.id.segundaKeyAccesDigito6);
        segundaKeyAccesDigito7 = (EditText) rootView.findViewById(R.id.segundaKeyAccesDigito7);

        segundaKeyAccesDigito1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (segundaKeyAccesDigito1.getText().toString().length() == 1)     //size as per your requirement
                {
                    segundaKeyAccesDigito1.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    segundaKeyAccesDigito1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    segundaKeyAccesDigito2.requestFocus();
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

        segundaKeyAccesDigito2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (segundaKeyAccesDigito2.getText().toString().length() == 1)     //size as per your requirement
                {
                    segundaKeyAccesDigito2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    segundaKeyAccesDigito2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    segundaKeyAccesDigito3.requestFocus();
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

        segundaKeyAccesDigito3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (segundaKeyAccesDigito3.getText().toString().length() == 1)     //size as per your requirement
                {
                    segundaKeyAccesDigito3.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    segundaKeyAccesDigito3.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    segundaKeyAccesDigito4.requestFocus();
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

        segundaKeyAccesDigito4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (segundaKeyAccesDigito4.getText().toString().length() == 1)     //size as per your requirement
                {
                    segundaKeyAccesDigito4.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    segundaKeyAccesDigito4.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    segundaKeyAccesDigito5.requestFocus();
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

        segundaKeyAccesDigito5.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (segundaKeyAccesDigito5.getText().toString().length() == 1)     //size as per your requirement
                {
                    segundaKeyAccesDigito5.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    segundaKeyAccesDigito5.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    segundaKeyAccesDigito6.requestFocus();
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

        segundaKeyAccesDigito6.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (segundaKeyAccesDigito6.getText().toString().length() == 1)     //size as per your requirement
                {
                    segundaKeyAccesDigito6.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    segundaKeyAccesDigito6.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    segundaKeyAccesDigito7.requestFocus();
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

        segundaKeyAccesDigito7.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (segundaKeyAccesDigito7.getText().toString().length() == 1)     //size as per your requirement
                {
                    segundaKeyAccesDigito7.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    segundaKeyAccesDigito7.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmSegundaKeyAccesDigito1.requestFocus();
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

    private void confirmarSegundaClaveAcceso(){
        confirmSegundaKeyAccesDigito1 = (EditText) rootView.findViewById(R.id.confirmSegundaKeyAccesDigito1);
        confirmSegundaKeyAccesDigito2 = (EditText) rootView.findViewById(R.id.confirmSegundaKeyAccesDigito2);
        confirmSegundaKeyAccesDigito3 = (EditText) rootView.findViewById(R.id.confirmSegundaKeyAccesDigito3);
        confirmSegundaKeyAccesDigito4 = (EditText) rootView.findViewById(R.id.confirmSegundaKeyAccesDigito4);
        confirmSegundaKeyAccesDigito5 = (EditText) rootView.findViewById(R.id.confirmSegundaKeyAccesDigito5);
        confirmSegundaKeyAccesDigito6 = (EditText) rootView.findViewById(R.id.confirmSegundaKeyAccesDigito6);
        confirmSegundaKeyAccesDigito7 = (EditText) rootView.findViewById(R.id.confirmSegundaKeyAccesDigito7);

        confirmSegundaKeyAccesDigito1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (confirmSegundaKeyAccesDigito1.getText().toString().length() == 1)     //size as per your requirement
                {
                    confirmSegundaKeyAccesDigito1.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmSegundaKeyAccesDigito1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmSegundaKeyAccesDigito2.requestFocus();
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

        confirmSegundaKeyAccesDigito2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (confirmSegundaKeyAccesDigito2.getText().toString().length() == 1)     //size as per your requirement
                {
                    confirmSegundaKeyAccesDigito2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmSegundaKeyAccesDigito2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmSegundaKeyAccesDigito3.requestFocus();
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

        confirmSegundaKeyAccesDigito3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (confirmSegundaKeyAccesDigito3.getText().toString().length() == 1)     //size as per your requirement
                {
                    confirmSegundaKeyAccesDigito3.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmSegundaKeyAccesDigito3.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmSegundaKeyAccesDigito4.requestFocus();
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

        confirmSegundaKeyAccesDigito4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (confirmSegundaKeyAccesDigito4.getText().toString().length() == 1)     //size as per your requirement
                {
                    confirmSegundaKeyAccesDigito4.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmSegundaKeyAccesDigito4.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmSegundaKeyAccesDigito5.requestFocus();
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

        confirmSegundaKeyAccesDigito5.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (confirmSegundaKeyAccesDigito5.getText().toString().length() == 1)     //size as per your requirement
                {
                    confirmSegundaKeyAccesDigito5.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmSegundaKeyAccesDigito5.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmSegundaKeyAccesDigito6.requestFocus();
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

        confirmSegundaKeyAccesDigito6.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (confirmSegundaKeyAccesDigito6.getText().toString().length() == 1)     //size as per your requirement
                {
                    confirmSegundaKeyAccesDigito6.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmSegundaKeyAccesDigito6.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmSegundaKeyAccesDigito7.requestFocus();
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

        confirmSegundaKeyAccesDigito7.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (confirmSegundaKeyAccesDigito7.getText().toString().length() == 1)     //size as per your requirement
                {
                    confirmSegundaKeyAccesDigito7.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmSegundaKeyAccesDigito7.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    btn_siguiente_informacion.requestFocus();
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
    }*/
}
