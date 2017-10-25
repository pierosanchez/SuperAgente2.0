package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.utils.Constante;

import java.util.Calendar;

public class AgregarBeneficiarioUsuario extends Activity {

    private Button btn_continuar_afiliacion, btnRegresar, btn_agregar_tarjeta_beneficiario, btn_agregar_cuenta_beneficiario;
    private EditText txt_num_cel1, txt_num_cel2, txt_email_beneficiario, txt_password_beneficiario1, txt_password_beneficiario2, txt_nombre, txt_apellido, txt_dni;
    private TextView txt_fecha_beneficiario;
    private UsuarioEntity usuario;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    private Spinner sp_tipo_abono_beneficiario;
    String[] tipo_abono = {"Cuentas", "Tarjetas", "Cheques"};
    String cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_beneficiario_usuario);

        txt_fecha_beneficiario = (TextView) findViewById(R.id.txt_fechaNac);

        txt_dni = (EditText) findViewById(R.id.txt_dni);
        txt_num_cel1 = (EditText) findViewById(R.id.txt_celular1);
        txt_num_cel2 = (EditText) findViewById(R.id.txt_celular2);
        txt_email_beneficiario = (EditText) findViewById(R.id.txt_Email);
        txt_password_beneficiario1 = (EditText) findViewById(R.id.txt_contraseña);
        txt_password_beneficiario2 = (EditText) findViewById(R.id.txt_re_contraseña);
        txt_nombre = (EditText) findViewById(R.id.txt_nombre);
        txt_apellido = (EditText) findViewById(R.id.txt_apellido);

        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        btn_continuar_afiliacion = (Button) findViewById(R.id.btn_continuar_beneficiario);
        btn_agregar_cuenta_beneficiario = (Button) findViewById(R.id.btn_agregar_cuenta_beneficiario);
        btn_agregar_tarjeta_beneficiario = (Button) findViewById(R.id.btn_agregar_tarjeta_beneficiario);

        sp_tipo_abono_beneficiario = (Spinner) findViewById(R.id.sp_tipo_abono_beneficiario);

        //Variables para el DatePicker
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");
        cliente = bundle.getString("cliente");

        cargarTipoAbono();

        btn_continuar_afiliacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String celular1 = txt_num_cel1.getText().toString();
                String celular2 = txt_num_cel2.getText().toString();
                String email = txt_email_beneficiario.getText().toString();
                String fechaNac = txt_fecha_beneficiario.getText().toString();
                String pass = txt_password_beneficiario1.getText().toString();
                String repass = txt_password_beneficiario2.getText().toString();
                String nombre = txt_nombre.getText().toString();
                String apellido = txt_apellido.getText().toString();
                String dni = txt_dni.getText().toString();

                if (!celular1.equals("") && !email.equals("") && !fechaNac.equals("") && !dni.equals("")
                        && !pass.equals("") && !repass.equals("") && !nombre.equals("") && !apellido.equals("") /*&& !tarjeta.equals("") && !cci.equals("")*/) {
                    if (celular1.length() == 9 && email.matches(Constante.EMAILPATTERN) /*&& tarjeta.length() == 16 && cci.length() == 20*/) {
                        if (!celular1.equals(celular2)) {
                            if (pass.equals(repass)) {
                                AgregarBeneficiarioUsuario.insertarBeneficiario insertar = new AgregarBeneficiarioUsuario.insertarBeneficiario();
                                insertar.execute();

                                Intent intent = new Intent(AgregarBeneficiarioUsuario.this, ListadoBeneficiariosUsuario.class);
                                intent.putExtra("usuario", usuario);
                                intent.putExtra("cliente", cliente);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(AgregarBeneficiarioUsuario.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AgregarBeneficiarioUsuario.this, "Los Numeros de celulares deben ser diferentes entre si", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (!email.matches(Constante.EMAILPATTERN)) {
                            Toast.makeText(AgregarBeneficiarioUsuario.this, "El mail ingresado es incorecto", Toast.LENGTH_SHORT).show();
                        } else if (celular1.length() != 9) {
                            Toast.makeText(AgregarBeneficiarioUsuario.this, "El movil ingresado es incorrecto, vuelva a ingresar", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(AgregarBeneficiarioUsuario.this, "Ingrese los datos del beneficiario", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AgregarBeneficiarioUsuario.this, ListadoBeneficiariosUsuario.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });

        sp_tipo_abono_beneficiario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tipo = parent.getAdapter().getItem(position).toString();
                if (tipo.equals("Cuentas")) {
                    btn_agregar_cuenta_beneficiario.setVisibility(View.VISIBLE);
                    btn_agregar_tarjeta_beneficiario.setVisibility(View.GONE);
                    btn_continuar_afiliacion.setVisibility(View.GONE);
                } else if (tipo.equals("Tarjetas")) {
                    btn_agregar_tarjeta_beneficiario.setVisibility(View.VISIBLE);
                    btn_agregar_cuenta_beneficiario.setVisibility(View.GONE);
                    btn_continuar_afiliacion.setVisibility(View.GONE);
                } else if (tipo.equals("Cheques")) {
                    btn_agregar_tarjeta_beneficiario.setVisibility(View.GONE);
                    btn_agregar_cuenta_beneficiario.setVisibility(View.GONE);
                    btn_continuar_afiliacion.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_agregar_cuenta_beneficiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String celular1 = txt_num_cel1.getText().toString();
                String celular2 = txt_num_cel2.getText().toString();
                String email = txt_email_beneficiario.getText().toString();
                String fechaNac = txt_fecha_beneficiario.getText().toString();
                String pass = txt_password_beneficiario1.getText().toString();
                String repass = txt_password_beneficiario2.getText().toString();
                String nombre = txt_nombre.getText().toString();
                String apellido = txt_apellido.getText().toString();
                String dni = txt_dni.getText().toString();

                if (!celular1.equals("") && !email.equals("") && !fechaNac.equals("") && !dni.equals("")
                        && !pass.equals("") && !repass.equals("") && !nombre.equals("") && !apellido.equals("")) {
                    if (celular1.length() == 9 && email.matches(Constante.EMAILPATTERN)) {
                        if (!celular1.equals(celular2)) {
                            if (pass.equals(repass)) {
                                AgregarBeneficiarioUsuario.insertarBeneficiario insertar = new AgregarBeneficiarioUsuario.insertarBeneficiario();
                                insertar.execute();

                                Intent intent = new Intent(AgregarBeneficiarioUsuario.this, AgregarCuentaBeneficiario.class);
                                intent.putExtra("usuario", usuario);
                                intent.putExtra("dni_benef", dni);
                                intent.putExtra("cliente", cliente);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(AgregarBeneficiarioUsuario.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AgregarBeneficiarioUsuario.this, "Los Numeros de celulares deben ser diferentes entre si", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (!email.matches(Constante.EMAILPATTERN)) {
                            Toast.makeText(AgregarBeneficiarioUsuario.this, "El mail ingresado es incorecto", Toast.LENGTH_SHORT).show();
                        } else if (celular1.length() != 9) {
                            Toast.makeText(AgregarBeneficiarioUsuario.this, "El movil ingresado es incorrecto, vuelva a ingresar", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(AgregarBeneficiarioUsuario.this, "Ingrese los datos del beneficiario", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_agregar_tarjeta_beneficiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String celular1 = txt_num_cel1.getText().toString();
                String celular2 = txt_num_cel2.getText().toString();
                String email = txt_email_beneficiario.getText().toString();
                String fechaNac = txt_fecha_beneficiario.getText().toString();
                String pass = txt_password_beneficiario1.getText().toString();
                String repass = txt_password_beneficiario2.getText().toString();
                String nombre = txt_nombre.getText().toString();
                String apellido = txt_apellido.getText().toString();
                String dni = txt_dni.getText().toString();

                if (!celular1.equals("") && !email.equals("") && !fechaNac.equals("") && !dni.equals("")
                        && !pass.equals("") && !repass.equals("") && !nombre.equals("") && !apellido.equals("")) {
                    if (celular1.length() == 9 && email.matches(Constante.EMAILPATTERN)) {
                        if (!celular1.equals(celular2)) {
                            if (pass.equals(repass)) {
                                AgregarBeneficiarioUsuario.insertarBeneficiario insertar = new AgregarBeneficiarioUsuario.insertarBeneficiario();
                                insertar.execute();

                                Intent intent = new Intent(AgregarBeneficiarioUsuario.this, AgregarTarjetaBeneficiario.class);
                                intent.putExtra("usuario", usuario);
                                intent.putExtra("dni_benef", dni);
                                intent.putExtra("cliente", cliente);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(AgregarBeneficiarioUsuario.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AgregarBeneficiarioUsuario.this, "Los Numeros de celulares deben ser diferentes entre si", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (!email.matches(Constante.EMAILPATTERN)) {
                            Toast.makeText(AgregarBeneficiarioUsuario.this, "El mail ingresado es incorecto", Toast.LENGTH_SHORT).show();
                        } else if (celular1.length() != 9) {
                            Toast.makeText(AgregarBeneficiarioUsuario.this, "El movil ingresado es incorrecto, vuelva a ingresar", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(AgregarBeneficiarioUsuario.this, "Ingrese los datos del beneficiario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void cargarTipoAbono(){
        ArrayAdapter<String> adaptadorBanco = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipo_abono);
        sp_tipo_abono_beneficiario.setAdapter(adaptadorBanco);
    }

    private class insertarBeneficiario extends AsyncTask<String, Void, BeneficiarioEntity> {

        String celular1 = txt_num_cel1.getText().toString();
        String celular2 = txt_num_cel2.getText().toString();
        String email = txt_email_beneficiario.getText().toString();
        String fechaNac = txt_fecha_beneficiario.getText().toString();
        String pass = txt_password_beneficiario1.getText().toString();
        String nombre = txt_nombre.getText().toString();
        String apellido = txt_apellido.getText().toString();
        String dni = txt_dni.getText().toString();

        @Override
        protected BeneficiarioEntity doInBackground(String... params) {
            BeneficiarioEntity user = null;
            try {

                if(celular2.equals("")){
                    celular2 = "-";
                }

                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.getInsertarBeneficiario(nombre, apellido, celular1, celular2, email, fechaNac, pass, dni, usuario.getUsuarioId(), obtenerTipoAbono());
            } catch (Exception e) {
                user = null;
                //fldag_clic_ingreso = 0;;
            }
            return user;
        }
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "Seleccione la fecha", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        txt_fecha_beneficiario.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    public int obtenerTipoAbono(){
        int abono = 0;
        String bancoTarjeta = sp_tipo_abono_beneficiario.getSelectedItem().toString();

        if (bancoTarjeta.equals("Cuentas")) {
            abono = 1;
        } else if (bancoTarjeta.equals("Tarjetas")) {
            abono = 2;
        } else if (bancoTarjeta.equals("Cheques")) {
            abono = 3;
        }

        return abono;
    }
}
