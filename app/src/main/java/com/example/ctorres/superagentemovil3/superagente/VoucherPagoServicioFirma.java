package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.adapter.NumeroUnicoAdapter;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteBD;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.CuentaEntity;
import com.example.ctorres.superagentemovil3.entity.NumeroUnico;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.entity.VoucherPagoServicioEntity;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class VoucherPagoServicioFirma extends Activity {

    private UsuarioEntity usuario;
    private VoucherPagoServicioEntity voucherServicio;
    Bitmap b;
    ImageView signImage;
    Button btn_fimar, btn_salir, btn_pagar_otros_servicios, btn_efectuar_otra_operacion;
    String num_tarjeta, monto_servicio, servicio, num_servicio, tipo_moneda_deuda, comision, cliente,
            cli_dni, nombre_recibo, tipo_servicio, fechaV, horaV, nro_unico, validacion_tarjeta, tipoPago;
    int tipo_tarjeta, emisor_tarjeta, tipo_tarjeta_pago, cod_banco;
    TextView tv_fecha_pago, txt_hora_pago, tv_comision_oper_servicio, tv_importe_servicio, tv_forma_pago, txt_suministro_pagar_voucher, txt_servicio_pagar_voucher,
            tv_total_servicio_pagar_voucher, tv_banco_tarjeta_usuario, txt_pagado_por, tv_tipo_moneda_importe_voucher, tv_tipo_moneda_comision_voucher, tv_tipo_moneda_total_voucher,
            txt_dni, tv_nombre_recibo_usuario, txt_tipo_servicio_pagar_voucher, txt_numero_unico;
    TableRow tr_tipo_servicio_pagar;
    DecimalFormat decimal = new DecimalFormat("0.00");
    NumeroUnicoAdapter numeroUnicoAdapter;
    ArrayList<NumeroUnico> numeroUnicoArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voucher_pago_servicio_firma);

        btn_salir = (Button) findViewById(R.id.btn_salir);
        btn_pagar_otros_servicios = (Button) findViewById(R.id.btn_pagar_otros_servicios);
        btn_efectuar_otra_operacion = (Button) findViewById(R.id.btn_efectuar_otra_operacion);
        btn_fimar = (Button) findViewById(R.id.btn_firmar);

        tr_tipo_servicio_pagar = (TableRow) findViewById(R.id.tr_tipo_servicio_pagar);

        signImage = (ImageView) findViewById(R.id.signImage);

        tv_fecha_pago = (TextView) findViewById(R.id.tv_fecha_pago);
        txt_hora_pago = (TextView) findViewById(R.id.txt_hora_pago);
        tv_comision_oper_servicio = (TextView) findViewById(R.id.tv_comision_oper_servicio);
        tv_importe_servicio = (TextView) findViewById(R.id.tv_importe_servicio);
        tv_forma_pago = (TextView) findViewById(R.id.tv_forma_pago);
        txt_suministro_pagar_voucher = (TextView) findViewById(R.id.txt_suministro_pagar_voucher);
        txt_servicio_pagar_voucher = (TextView) findViewById(R.id.txt_servicio_pagar_voucher);
        tv_total_servicio_pagar_voucher = (TextView) findViewById(R.id.tv_total_servicio_pagar_voucher);
        //tv_banco_tarjeta_usuario = (TextView) findViewById(R.id.tv_banco_tarjeta_usuario);
        txt_pagado_por = (TextView) findViewById(R.id.txt_pagado_por);
        tv_tipo_moneda_importe_voucher = (TextView) findViewById(R.id.tv_tipo_moneda_importe_voucher);
        tv_tipo_moneda_comision_voucher = (TextView) findViewById(R.id.tv_tipo_moneda_comision_voucher);
        tv_tipo_moneda_total_voucher = (TextView) findViewById(R.id.tv_tipo_moneda_total_voucher);
        txt_dni = (TextView) findViewById(R.id.txt_dni);
        tv_nombre_recibo_usuario = (TextView) findViewById(R.id.tv_nombre_recibo_usuario);
        txt_tipo_servicio_pagar_voucher = (TextView) findViewById(R.id.txt_tipo_servicio_pagar_voucher);
        txt_numero_unico = (TextView) findViewById(R.id.txt_numero_unico);

        Bundle extras = getIntent().getExtras();
        usuario = extras.getParcelable("usuario");
        num_tarjeta = extras.getString("num_tarjeta");
        emisor_tarjeta = extras.getInt("emisor_tarjeta");
        monto_servicio = extras.getString("monto_servicio");
        servicio = extras.getString("servicio");
        num_servicio = extras.getString("num_servicio");
        tipo_moneda_deuda = extras.getString("tipo_moneda_deuda");
        comision = extras.getString("comision");
        tipo_tarjeta_pago = extras.getInt("tipo_tarjeta_pago");
        cod_banco = extras.getInt("cod_banco");
        cliente = extras.getString("cliente");
        cli_dni = extras.getString("cli_dni");
        nombre_recibo = extras.getString("nombre_recibo");
        tipo_servicio = extras.getString("tipo_servicio");
        nro_unico = extras.getString("nro_unico");
        validacion_tarjeta = extras.getString("validacion_tarjeta");
        tipoPago = tipoTarjeta() + " con " + validacion_tarjeta;
        fechaV = "FECHA: " + obtenerFecha();
        horaV = "HORA: " + obtenerHora();

        VoucherPagoServicioFirma.getNumUnico numUnico = new VoucherPagoServicioFirma.getNumUnico();
        numUnico.execute();

        if (tipo_servicio != null){
            tr_tipo_servicio_pagar.setVisibility(View.VISIBLE);
            txt_tipo_servicio_pagar_voucher.setText(tipo_servicio);
        } else {
            tr_tipo_servicio_pagar.setVisibility(View.GONE);
        }

        tv_fecha_pago.setText(fechaV);
        txt_hora_pago.setText(horaV);
        tv_comision_oper_servicio.setText(transformarComision());
        tv_importe_servicio.setText(transformarImporteServicio());
        txt_servicio_pagar_voucher.setText(servicio);
        txt_suministro_pagar_voucher.setText(num_servicio);
        tv_total_servicio_pagar_voucher.setText(totalServicioPagar());
        tv_forma_pago.setText(tipoPago);
        txt_pagado_por.setText(cliente);
        tv_tipo_moneda_importe_voucher.setText(tipo_moneda_deuda);
        tv_tipo_moneda_comision_voucher.setText(tipo_moneda_deuda);
        tv_tipo_moneda_total_voucher.setText(tipo_moneda_deuda);
        txt_dni.setText(cli_dni);
        tv_nombre_recibo_usuario.setText(nombre_recibo);

        btn_efectuar_otra_operacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signImage.getDrawable() == null) {
                    Toast.makeText(VoucherPagoServicioFirma.this, "Por favor registre su firma", Toast.LENGTH_LONG).show();
                } else {

                    //post();

                    Intent intent = new Intent(VoucherPagoServicioFirma.this, MenuCliente.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("cli_dni", cli_dni);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btn_pagar_otros_servicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signImage.getDrawable() == null) {
                    Toast.makeText(VoucherPagoServicioFirma.this, "Por favor registre su firma", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent = new Intent(VoucherPagoServicioFirma.this, SeleccionServicioPagar.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("cli_dni", cli_dni);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btn_fimar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VoucherPagoServicioFirma.this, CaptureSignature.class);
                intent.putExtra("cliente", cliente);
                intent.putExtra("usuario", usuario);
                intent.putExtra("cli_dni", cli_dni);
                startActivityForResult(intent, 0);
                /*startActivity(intent);
                finish();*/
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            b = BitmapFactory.decodeByteArray(
                    data.getByteArrayExtra("byteArray"), 0,
                    data.getByteArrayExtra("byteArray").length);
            signImage.setImageBitmap(b);
            btn_fimar.setVisibility(View.GONE);
        }
    }

    public String obtenerHora() {
        String hora;

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        int horaS = today.hour;
        int min = today.minute;
        int seg = today.second;

        //para probar en celulares se comenta y cuando es con emuladores se descomenta
        //horaS = horaS - 5;

        hora = horaS + ":" + min + ":" + seg;

        return hora;
    }

    public String obtenerFecha() {

        String fecha;

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        int dia = today.monthDay;
        int mes = today.month;
        int año = today.year;
        mes = mes + 1;

        fecha = dia + "/" + mes + "/" + año;

        return fecha;
    }

    public String totalServicioPagar() {

        double monto_p = Double.parseDouble(monto_servicio);
        double comision_p = Double.parseDouble(comision);

        double importe = monto_p + comision_p;

        return decimal.format(importe);
    }

    public String tipoTarjeta() {
        String tipo = "";

        if (tipo_tarjeta_pago == 1) {
            tipo = "Crédito";
        } else if (tipo_tarjeta_pago == 2) {
            tipo = "Débito";
        }

        return tipo;
    }

    public String transformarComision() {
        double convert = Double.parseDouble(comision);
        return decimal.format(convert);
    }

    public String transformarImporteServicio() {
        double convert = Double.parseDouble(monto_servicio);
        return decimal.format(convert);
    }

    public String drawableToBitmapToString(ImageView imageView) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);

        return temp;
    }

    public void post() {
        HashMap<String, String> postData = new HashMap<String, String>();
        postData.put("image", drawableToBitmapToString(signImage));
        //postData.put("keyCliente", usuario.getUsuarioId());

        PostResponseAsyncTask task = new PostResponseAsyncTask(VoucherPagoServicioFirma.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if (s.contains("uploaded_success")) {
                    Toast.makeText(VoucherPagoServicioFirma.this, "Imagen ingresada", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(VoucherPagoServicioFirma.this, "Imagen no ingresada, hubo un error" + s, Toast.LENGTH_LONG).show();
                }
            }
        });
        task.execute("http://10.0.2.2/news/index.php");
        //task.execute("http://localhost:4532/apigeneral/ApiGeneral/InsertarFirmaCliente");
        task.setEachExceptionsHandler(new EachExceptionsHandler() {
            @Override
            public void handleIOException(IOException e) {
                Toast.makeText(VoucherPagoServicioFirma.this, "Error de Servidor", Toast.LENGTH_LONG).show();
            }

            @Override
            public void handleMalformedURLException(MalformedURLException e) {
                Toast.makeText(VoucherPagoServicioFirma.this, "Error de URL", Toast.LENGTH_LONG).show();
            }

            @Override
            public void handleProtocolException(ProtocolException e) {
                Toast.makeText(VoucherPagoServicioFirma.this, "Error de Protocolo", Toast.LENGTH_LONG).show();
            }

            @Override
            public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                Toast.makeText(VoucherPagoServicioFirma.this, "Error de Codificación", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void GetText(){
        try {
            URL url = new URL("http://190.117.112.163/webApi_2/apigeneral/ApiGeneral/InsertarFirmaCliente");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            String urlParameters = "img=" + drawableToBitmapToString(signImage) + "&keyCliente=" + usuario.getUsuarioId();
            connection.setRequestMethod("POST");

            connection.setDoOutput(true);
            DataOutputStream dstream = new DataOutputStream(connection.getOutputStream());
            dstream.writeBytes(urlParameters);
            dstream.flush();
            dstream.close();

            Toast.makeText(VoucherPagoServicioFirma.this, "Response Code " + connection.getResponseCode(), Toast.LENGTH_LONG).show();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private class getNumUnico extends AsyncTask<String, Void, VoucherPagoServicioEntity> {

        @Override
        protected VoucherPagoServicioEntity doInBackground(String... params) {
            VoucherPagoServicioEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.getNumeroUnicoServicios(nro_unico);

            } catch (Exception e) {
                user = null;
                //fldag_clic_ingreso = 0;;
            }
            return user;
        }

        @Override
        protected void onPostExecute(VoucherPagoServicioEntity voucherPagoServicioEntity){
            voucherServicio = voucherPagoServicioEntity;
            if (voucherServicio != null){
                if (voucherServicio.getNumeroUnico() != null){
                    txt_numero_unico.setText(voucherServicio.getNumeroUnico());
                } else {
                    Toast.makeText(VoucherPagoServicioFirma.this, "no se trajo el numero unico", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(VoucherPagoServicioFirma.this, "la entidad no tiene data", Toast.LENGTH_LONG).show();
            }
        }
    }
}
