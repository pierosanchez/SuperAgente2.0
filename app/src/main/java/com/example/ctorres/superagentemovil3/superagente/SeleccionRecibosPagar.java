package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;

import java.text.DecimalFormat;

public class SeleccionRecibosPagar extends Activity {

    private UsuarioEntity usuario;
    String num_servicio, servicio, cliente, tipo_servicio;
    Button btn_aceptar_recibo_pagar, btn_cancelar_recibo_pagar;
    CheckBox chbxk_fecha_vencimiento1, chbxk_fecha_vencimiento2;
    TextView tv_monto_recibo1, tv_monto_recibo2, txt_empresa_servicio_pagar, txt_numero_suministro, txt_nombre_titular_servicio;
    DecimalFormat decimal = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_recibos_pagar);

        btn_aceptar_recibo_pagar = (Button) findViewById(R.id.btn_aceptar_recibo_pagar);
        btn_cancelar_recibo_pagar = (Button) findViewById(R.id.btn_cancelar_recibo_pagar);

        tv_monto_recibo1 = (TextView) findViewById(R.id.tv_monto_recibo1);
        tv_monto_recibo2 = (TextView) findViewById(R.id.tv_monto_recibo2);
        txt_empresa_servicio_pagar = (TextView) findViewById(R.id.txt_empresa_servicio_pagar);
        txt_numero_suministro = (TextView) findViewById(R.id.txt_numero_suministro);
        txt_nombre_titular_servicio = (TextView) findViewById(R.id.txt_nombre_titular_servicio);

        chbxk_fecha_vencimiento1 = (CheckBox) findViewById(R.id.chbxk_fecha_vencimiento1);
        chbxk_fecha_vencimiento2 = (CheckBox) findViewById(R.id.chbxk_fecha_vencimiento2);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");
        servicio = bundle.getString("servicio");
        num_servicio = bundle.getString("num_servicio");
        cliente = bundle.getString("cliente");
        tipo_servicio = bundle.getString("tipo_servicio");

        txt_empresa_servicio_pagar.setText(servicio);
        txt_numero_suministro.setText(num_servicio);
        txt_nombre_titular_servicio.setText(cliente);

        chbxk_fecha_vencimiento1.setChecked(true);

        btn_aceptar_recibo_pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!chbxk_fecha_vencimiento1.isChecked() && !chbxk_fecha_vencimiento2.isChecked()) {
                    Toast.makeText(SeleccionRecibosPagar.this, "Seleccione un recibo a pagar", Toast.LENGTH_SHORT).show();
                } else if (chbxk_fecha_vencimiento2.isChecked() && !chbxk_fecha_vencimiento1.isChecked()) {
                    Toast.makeText(SeleccionRecibosPagar.this, "Seleccione el recibo más antiguo para pagar", Toast.LENGTH_SHORT).show();
                } else {
                    if (chbxk_fecha_vencimiento1.isChecked() && chbxk_fecha_vencimiento2.isChecked()) {
                        Intent intent = new Intent(SeleccionRecibosPagar.this, SeleccionTarjetaCargo.class);
                        intent.putExtra("monto_servicio", sumaTotal());
                        intent.putExtra("servicio", servicio);
                        intent.putExtra("num_servicio", num_servicio);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("cliente", cliente);
                        intent.putExtra("tipo_servicio", tipo_servicio);
                        startActivityForResult(intent, 0);
                        finish();
                    } else {
                        Intent intent = new Intent(SeleccionRecibosPagar.this, SeleccionTarjetaCargo.class);
                        intent.putExtra("monto_servicio", obtenerMonto());
                        intent.putExtra("servicio", servicio);
                        intent.putExtra("num_servicio", num_servicio);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("cliente", cliente);
                        intent.putExtra("tipo_servicio", tipo_servicio);
                        startActivityForResult(intent, 0);
                        finish();
                    }
                }
            }
        });
        btn_cancelar_recibo_pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });
    }

    public String obtenerMonto() {
        String monto = "";
        String fv1, fv2;

        if (chbxk_fecha_vencimiento1.isChecked()) {
            monto = tv_monto_recibo1.getText().toString();
        } else if (chbxk_fecha_vencimiento2.isChecked()) {
            monto = tv_monto_recibo2.getText().toString();
        }

        return monto;
    }

    public void cancelar() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Esta seguro que desea cacelar la transacción?");
        alertDialog.setTitle("Cancelar");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(SeleccionRecibosPagar.this, MenuCliente.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
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

    public String sumaTotal(){
        double suma;
        String monto1 = tv_monto_recibo1.getText().toString();
        String monto2 = tv_monto_recibo2.getText().toString();

        suma = Double.parseDouble(monto1) + Double.parseDouble(monto2);

        return decimal.format(suma);
    }

}
