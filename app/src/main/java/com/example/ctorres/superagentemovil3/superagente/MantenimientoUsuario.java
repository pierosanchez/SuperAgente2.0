package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

public class MantenimientoUsuario extends Activity {

    RadioGroup rdgrp_mantenimiento_usuario;
    private UsuarioEntity usuario;
    Button btn_regresar_mantenimiento;
    String cliente;
    TextView tv_nombre_titular_cuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mantenimiento_usuario);

        rdgrp_mantenimiento_usuario = (RadioGroup) findViewById(R.id.rdgrp_mantenimiento_usuario);
        btn_regresar_mantenimiento = (Button) findViewById(R.id.btn_regresar_mantenimiento);

        tv_nombre_titular_cuenta = (TextView) findViewById(R.id.tv_nombre_titular_cuenta);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");
        cliente = bundle.getString("cliente");

        tv_nombre_titular_cuenta.setText(cliente);

        rdgrp_mantenimiento_usuario.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rdbtn_cuentas_usuario_mant) {
                    Intent intent = new Intent(MantenimientoUsuario.this, ListadoCuentasUsuario.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("cliente", cliente);
                    startActivity(intent);
                    finish();
                } else if (checkedId == R.id.rdbtn_tarjetas_usuario_mant){
                    Intent intent = new Intent(MantenimientoUsuario.this, ListadoTarjetasUsuario.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("cliente", cliente);
                    startActivity(intent);
                    finish();
                } else if (checkedId == R.id.rdbtn_domicilio_usuario_mant){
                    Intent intent = new Intent(MantenimientoUsuario.this, ActualizarDomicilio.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("cliente", cliente);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(MantenimientoUsuario.this, ListadoBeneficiariosUsuario.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("cliente", cliente);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btn_regresar_mantenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MantenimientoUsuario.this, MenuCliente.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });
    }
}
