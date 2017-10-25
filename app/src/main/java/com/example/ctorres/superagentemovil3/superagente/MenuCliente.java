package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

public class MenuCliente extends Activity {

    Button btn_pagoTarjetas, btn_transferencias, btn_mantenimiento_usuario, btn_pago_consumos, btn_pago_servicios, ckbox_retiro_efectivo, ckbox_deposito_efectivo, ckbox_recargas, btn_cambio_clave;
    private UsuarioEntity usuario;
    String cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_cliente);

        btn_pagoTarjetas = (Button) findViewById(R.id.ckbox_pago_tarjetas);
        btn_transferencias = (Button) findViewById(R.id.ckbox_transferencias);
        btn_mantenimiento_usuario = (Button) findViewById(R.id.btn_mantenimiento_usuario);
        btn_pago_servicios = (Button) findViewById(R.id.ckbox_pago_servicios);
        btn_cambio_clave = (Button) findViewById(R.id.ckbox_cambio_clave);
        btn_pago_consumos = (Button) findViewById(R.id.ckbox_pago_consumos);
        ckbox_recargas = (Button) findViewById(R.id.ckbox_recargas);

        LinearLayout salir_menu = (LinearLayout) findViewById(R.id.salir_menu);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");
        cliente = bundle.getString("cliente");

        salir_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuCliente.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_pagoTarjetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuCliente.this, SeleccionTarjetaPago.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });

        btn_transferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuCliente.this, RelacionBeneficiarios.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });

        btn_mantenimiento_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuCliente.this, MantenimientoUsuario.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });

        btn_pago_servicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuCliente.this, SeleccionServicioPagar.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });

        btn_cambio_clave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuCliente.this, CambioClaveAcceso.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
                finish();
            }
        });

        btn_pago_consumos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuCliente.this, SeleccionTarjetaCargo.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cliente", cliente);
                startActivityForResult(intent, 0);
                finish();
            }
        });
    }
}
