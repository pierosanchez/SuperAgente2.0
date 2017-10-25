package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.BeneficiarioMantenimientoAdapter;
import com.example.ctorres.superagentemovil3.dao.DetalleBeneficiarioAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.util.ArrayList;
import java.util.Calendar;

public class ActualizarBeneficiario extends Activity {

    EditText txt_num_cel1, txt_num_cel2, txt_email_beneficiario, txt_password_beneficiario1, txt_password_beneficiario2, txt_nombre, txt_apellido, txt_dni;
    TextView txt_fecha_beneficiario;
    UsuarioEntity usuario;
    DatePicker datePicker;
    Calendar calendar;
    int year, month, day;
    String dni_benef, usu;
    Button btn_guardar_actualizacion_beneficiario, btn_regresar_actualizacion_beneficiario;
    DetalleBeneficiarioAdapter detalleBeneficiarioAdapter;
    ArrayList<BeneficiarioEntity> arrayBenefeciarioEntity;
    String[] tipo_abono = {"Cuentas", "Tarjetas", "Cheques"};
    Spinner sp_tipo_abono_beneficiario;
    String cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_beneficiario);

        txt_fecha_beneficiario = (TextView) findViewById(R.id.txt_fechaNac);

        //txt_dni = (EditText) findViewById(R.id.txt_dni);
        txt_num_cel1 = (EditText) findViewById(R.id.txt_celular1);
        txt_num_cel2 = (EditText) findViewById(R.id.txt_celular2);
        txt_email_beneficiario = (EditText) findViewById(R.id.txt_Email);
        txt_password_beneficiario1 = (EditText) findViewById(R.id.txt_contraseña);
        txt_password_beneficiario2 = (EditText) findViewById(R.id.txt_re_contraseña);
        txt_nombre = (EditText) findViewById(R.id.txt_nombre);
        txt_apellido = (EditText) findViewById(R.id.txt_apellido);

        btn_guardar_actualizacion_beneficiario = (Button) findViewById(R.id.btn_guardar_actualizacion_beneficiario);
        btn_regresar_actualizacion_beneficiario = (Button) findViewById(R.id.btn_regresar_actualizacion_beneficiario);

        sp_tipo_abono_beneficiario = (Spinner) findViewById(R.id.sp_tipo_abono_beneficiario);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");
        dni_benef = bundle.getString("dni_benef");
        cliente = bundle.getString("cliente");

        arrayBenefeciarioEntity = null;
        detalleBeneficiarioAdapter = new DetalleBeneficiarioAdapter(arrayBenefeciarioEntity, getApplication());
        //lv_beneficiario_usuario_mant.setAdapter(beneficiarioMantenimientoAdapter);

        ejecutarLista();
        cargarTipoAbono();

        //String nombre = String.valueOf(arrayBenefeciarioEntity.get(0).getNombre());

        //txt_nombre.setText(arrayBenefeciarioEntity.get(0).getNombre());

        btn_guardar_actualizacion_beneficiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = arrayBenefeciarioEntity.get(0).getNombre();
                ActualizarBeneficiario.actualizarBeneficiario actualizarBeneficiario = new ActualizarBeneficiario.actualizarBeneficiario();
                actualizarBeneficiario.execute();

                Intent intent = new Intent(ActualizarBeneficiario.this, ListadoBeneficiariosUsuario.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });

        btn_regresar_actualizacion_beneficiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActualizarBeneficiario.this, ListadoBeneficiariosUsuario.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });
    }

    public void cargarTipoAbono(){
        ArrayAdapter<String> adaptadorBanco = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipo_abono);
        sp_tipo_abono_beneficiario.setAdapter(adaptadorBanco);
    }

    private class actualizarBeneficiario extends AsyncTask<String, Void, BeneficiarioEntity> {

        String celular1 = txt_num_cel1.getText().toString();
        String celular2 = txt_num_cel2.getText().toString();
        String email = txt_email_beneficiario.getText().toString();
        String fechaNac = txt_fecha_beneficiario.getText().toString();
        String pass = txt_password_beneficiario1.getText().toString();
        //String repass = txt_password_beneficiario2.getText().toString();
        String nombre = txt_nombre.getText().toString();
        String apellido = txt_apellido.getText().toString();
        //String dni = txt_dni.getText().toString();
        /*String cod_ibkro = txt_cod_interbancario.getText().toString();
        String num_tarjeta = txt_numero_tarjeta_beneficiario.getText().toString();*/

        @Override
        protected BeneficiarioEntity doInBackground(String... params) {
            BeneficiarioEntity user;
            try {

                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.actualizarBeneficiario(nombre, apellido, celular1, celular2, email, fechaNac, pass, dni_benef, obtenerTipoAbono());

            } catch (Exception e) {
                user = null;
                //fldag_clic_ingreso = 0;;
            }
            return user;
        }
    }

    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT)
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

    private void ejecutarLista(){

        try {
            ActualizarBeneficiario.ListadoBeneficiario listadoBeneficiario = new ActualizarBeneficiario.ListadoBeneficiario();
            listadoBeneficiario.execute();
        } catch (Exception e){
            //listadoBeneficiario = null;
        }

    }

    private class ListadoBeneficiario extends AsyncTask<String,Void,Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                arrayBenefeciarioEntity = dao.DetalleBeneficiario(dni_benef);
            } catch (Exception e) {
                //fldag_clic_ingreso = 0;;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            detalleBeneficiarioAdapter.setNewListBeneficiario(arrayBenefeciarioEntity);
            detalleBeneficiarioAdapter.notifyDataSetChanged();
            txt_nombre.setText(arrayBenefeciarioEntity.get(0).getNombre());
            txt_num_cel1.setText(arrayBenefeciarioEntity.get(0).getCelular1());
            txt_num_cel2.setText(arrayBenefeciarioEntity.get(0).getCelular2());
            txt_email_beneficiario.setText(arrayBenefeciarioEntity.get(0).getEmail());
            txt_password_beneficiario1.setText(arrayBenefeciarioEntity.get(0).getPass());
            txt_fecha_beneficiario.setText(arrayBenefeciarioEntity.get(0).getFechaNac());
            txt_password_beneficiario2.setText(arrayBenefeciarioEntity.get(0).getPass());
            txt_apellido.setText(arrayBenefeciarioEntity.get(0).getApellido());
        }
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
