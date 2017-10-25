package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoImplement;
import com.example.ctorres.superagentemovil3.dao.SuperAgenteDaoInterface;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.fragments.ClaveAccesoFragment;
import com.example.ctorres.superagentemovil3.fragments.InformacionTarjetaFragment;
import com.example.ctorres.superagentemovil3.utils.Constante;

public class PrincipalActivity extends AppCompatActivity implements
        ClaveAccesoFragment.OnClickOpcionFragmento,
        InformacionTarjetaFragment.OnClickOpcionFragmento{

    private int caseOption = 0;
    private ClaveAccesoFragment claveAccesoFragment;
    private InformacionTarjetaFragment informacionTarjetaFragment;
    private UsuarioEntity usuario;
    private Toolbar toolbar;
    private MenuItem action_salir_super_agente;
    private boolean salirActivity=false;
    private TimerTask timerTask;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //esta parte del codigo solo invoca a un fragment

        /*Bundle arguments =new Bundle();
        claveAccesoFragment = new ClaveAccesoFragment();
        claveAccesoFragment.setArguments(arguments);
        FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
        tran.replace(R.id.frame_fragment,claveAccesoFragment);
        tran.commit();*/

        //fin de la invocacion del fragment eliminar cuando se decida implementar los fragment


        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getParcelable("usuario");
        if(savedInstanceState==null){
            //barra_sanipes.setTitle(txt_titulo_principal);
            showClaveAcceso();
        }

        /*toolbar = (Toolbar)findViewById(R.id.barra_super_agente);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_atras));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //iniciarLogin();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        action_salir_super_agente = menu.findItem(R.id.action_salir_super_agente);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_salir_super_agente: cerrarSesionSuperAgente(); break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!salirActivity){
            moveTaskToBack(true);
        }
        salirActivity = false;
    }

    @Override
    public void OnClickOpcionFragmento(int opcion, Object datos, String informacionTarjeta) {
        caseOption = opcion;
        switch (opcion){
            case Constante.OPCION_CLAVE_ACCESO: break;
            case Constante.OPCION_INFORMACION_TARJETAS: verInformacionTarjetaFragment(datos, informacionTarjeta); break;
            case Constante.OPCION_LOGIN: iniciarLogin(); break;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if(getSupportFragmentManager().getBackStackEntryCount()>0){
            switch (caseOption){
                case Constante.OPCION_CLAVE_ACCESO: claveAccesoFragment = null; break;
                case Constante.OPCION_INFORMACION_TARJETAS: informacionTarjetaFragment = null ;
            }
        } else {
            moveTaskToBack(true);
        }
    }

    private void showClaveAcceso(){
        Bundle arguments =new Bundle();
        arguments.putParcelable("usuario", usuario);

        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        setNullAllFragment();
        if(!usuario.equals("-1")){
            caseOption = Constante.OPCION_CLAVE_ACCESO;
            claveAccesoFragment = null;
            claveAccesoFragment = new ClaveAccesoFragment();
            claveAccesoFragment.setArguments(arguments);
            FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
            tran.replace(R.id.frame_fragment,claveAccesoFragment);
            tran.commit();
        }
    }

    public void setNullAllFragment(){
        informacionTarjetaFragment = null;
    }

    private void verInformacionTarjetaFragment(Object datos, String informacionTarjeta){
        UsuarioEntity usuarioEntity;
        if(datos!=null && informacionTarjeta.equals("PrimeraTarjeta")) {
            usuarioEntity = (UsuarioEntity)datos;
            usuario = usuarioEntity;
            InformacionTarjeta informTarjeta = new InformacionTarjeta();
            informTarjeta.execute();
        }//parte de validar tarjeta
        /*if(datos!=null && informacionTarjeta.equals("MasTarjetas")) {
            usuarioEntity = (UsuarioEntity)datos;
            usuario = usuarioEntity;
            ValidarTarjeta validarTarjeta = new ValidarTarjeta();
            validarTarjeta.execute();
        }//fin de aprte de validar tarjeta
        //Codigo de insercion de tarjetas funcional

        /*if(datos!=null && informacionTarjeta.equals("MasTarjetas")) {
            usuarioEntity = (UsuarioEntity)datos;
            usuario = usuarioEntity;
            InsertarTarjeta insertarTarjeta = new InsertarTarjeta();
            insertarTarjeta.execute();
        }*/
        //fin de codigo de insercion de tarjetas funcional
    }

    public void insertarFragmentoOpcion(Fragment fragment, String idFragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //ft.setCustomAnimations(R.anim.enter_animation_fragment, R.anim.exit_animation_fragment, R.anim.pop_enter_animation_fragment, R.anim.pop_exit_animation_fragment);
        ft.replace(R.id.frame_fragment, fragment);
        ft.addToBackStack(idFragment);
        ft.commit();
    }

    private class InformacionTarjeta extends AsyncTask<String,Void,UsuarioEntity> {
        @Override
        protected UsuarioEntity doInBackground(String... params) {
            UsuarioEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.getClaveAcceso(usuario.getUsuarioId(), usuario.getClaveAcceso(), usuario.getPregunta(), usuario.getSegundaClaveAcceso());
                Log.e("idCliente", "CodCliente=" + user.getCodCliente() + ", usuarioId=" + usuario.getUsuarioId());
            } catch (Exception e) {
                user = null;
                //flag_clic_ingreso = 0;;
            }
            return user;
        }
        @Override
        protected void onPostExecute(UsuarioEntity user) {
            boolean sinConexion = false;
            String mensaje_error ="";
            //Log.e("BBDD", "Usuario" + usuario.getUsuario() + "Clave" + usuario.getClave());
            //if (!_nombreUsuario.trim().equals("") && !_claveUsuario.trim().equals("")){//usuario != null) {
            if (usuario != null){//usuario.getUsuario() != null) {
                if (usuario.getUsuarioId() != null && user.getCodCliente() != null) {
                    Bundle arguments =new Bundle();
                    usuario.setCodCliente(user.getCodCliente());
                    arguments.putParcelable("usuario", usuario);
                    informacionTarjetaFragment = new InformacionTarjetaFragment();
                    informacionTarjetaFragment.setArguments(arguments);
                    insertarFragmentoOpcion(informacionTarjetaFragment, "InformacionTarjetaFragment");
                } else {
                    mensaje_error = getString(R.string.msg_error_sin_conexion);
                    sinConexion=true;
                }
            } else{
                mensaje_error = getString(R.string.msg_error_sin_conexion);
                sinConexion=true;
            }

            if(sinConexion){
                Toast.makeText(PrincipalActivity.this, mensaje_error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class InsertarTarjeta extends AsyncTask<String,Void,UsuarioEntity> {
        @Override
        protected UsuarioEntity doInBackground(String... params) {
            UsuarioEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.getInsertarTarjeta(usuario.getUsuarioId(), usuario.getNumeroTarjeta());
                usuario.setInsertaTarjeta(user.getInsertaTarjeta());
            } catch (Exception e) {
                user = null;
                //flag_clic_ingreso = 0;;
            }
            return user;
        }
        @Override
        protected void onPostExecute(UsuarioEntity user) {
            boolean sinConexion = false;
            String mensaje_error ="";
            int cantidad_tarjetas = 0;

            //Log.e("BBDD", "usuarioId" + usuario.getUsuarioId() + "codCliente" + usuario.getCodCliente() + "insertaTarjeta:" + usuario.getInsertaTarjeta());
            if (usuario != null){
                if (usuario.getUsuarioId() != null && usuario.getCodCliente() != null && usuario.getInsertaTarjeta() != null ) {
                    String mensaje="";
                    if(!usuario.getInsertaTarjeta()){
                        mensaje = "Ingrese un numero de tarjeta diferente";
                    } else {
                        cantidad_tarjetas = usuario.getCantidadTarjetasInsertadas() + 1;
                        usuario.setCantidadTarjetasInsertadas(cantidad_tarjetas);
                        if(cantidad_tarjetas == 5){
                            mensaje = "Se grabó su quinta tarjeta correctamente";
                            iniciarLogin();
                        } else {
                            mensaje="Se grabó el numero de tarjeta correctamente";

                            Bundle arguments =new Bundle();
                            usuario.setInsertaTarjeta(false);
                            arguments.putParcelable("usuario", usuario);
                            informacionTarjetaFragment = new InformacionTarjetaFragment();
                            informacionTarjetaFragment.setArguments(arguments);
                            insertarFragmentoOpcion(informacionTarjetaFragment, "InformacionTarjetaFragment");
                        }
                    }
                    Toast.makeText(PrincipalActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                } else {
                    mensaje_error = getString(R.string.msg_error_sin_conexion);
                    sinConexion=true;
                }
            } else{
                mensaje_error = getString(R.string.msg_error_sin_conexion);
                sinConexion=true;
            }

            if(sinConexion){
                Toast.makeText(PrincipalActivity.this, mensaje_error, Toast.LENGTH_SHORT).show();
            }

        }
    }

    /*private class ValidarTarjeta extends AsyncTask<String,Void,UsuarioEntity> {
        @Override
        protected UsuarioEntity doInBackground(String... params) {
            UsuarioEntity user;
            try {
                SuperAgenteDaoInterface dao = new SuperAgenteDaoImplement();
                user = dao.getValidarTarjeta(usuario.getUsuarioId(), usuario.getNumeroTarjeta());
                usuario.setValidaTarjeta(user.getValidaTarjeta());
            } catch (Exception e) {
                user = null;
                //flag_clic_ingreso = 0;;
            }
            return user;
        }

        /*protected void onPostExecute(UsuarioEntity user) {

            boolean sinConexion = false;
            String mensaje_error ="";
            int cantidad_tarjetas = 0;

            //esto no sirve para nada---->Log.e("BBDD", "usuarioId" + usuario.getUsuarioId() + "codCliente" + usuario.getCodCliente() + "insertaTarjeta:" + usuario.getInsertaTarjeta());
            //COMIENZO CODIGO FUNCIONAL
            if (usuario != null){
                if (usuario.getUsuarioId() != null && usuario.getCodCliente() != null && usuario.getValidaTarjeta() != null ) {
                    String mensaje="";
                    if(!usuario.getValidaTarjeta()){
                        mensaje = "Ingrese un numero de tarjeta diferente";
                    } else {
                        cantidad_tarjetas = usuario.getCantidadTarjetasInsertadas() + 1;
                        usuario.setCantidadTarjetasInsertadas(cantidad_tarjetas);
                        if(cantidad_tarjetas == 5){
                            mensaje = "Se grabó su quinta tarjeta correctamente";
                            iniciarLogin();
                        } else {
                            mensaje="Se grabó el numero de tarjeta correctamente";

                            Bundle arguments =new Bundle();
                            usuario.setValidaTarjeta(false);
                            arguments.putParcelable("usuario", usuario);
                            informacionTarjetaFragment = new InformacionTarjetaFragment();
                            informacionTarjetaFragment.setArguments(arguments);
                            insertarFragmentoOpcion(informacionTarjetaFragment, "InformacionTarjetaFragment");
                        }
                    }
                    Toast.makeText(PrincipalActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                } else {
                    mensaje_error = getString(R.string.msg_error_sin_conexion);
                    sinConexion=true;
                }
            } else{
                mensaje_error = getString(R.string.msg_error_sin_conexion);
                sinConexion=true;
            }

            if(sinConexion){
                Toast.makeText(PrincipalActivity.this, mensaje_error, Toast.LENGTH_SHORT).show();
            }
        }*/

        //TERMINO CODIGO FUNCIONAL
    //}

    private void cerrarSesionSuperAgente(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getString(R.string.title_confirmar));
        dialog.setMessage(getString(R.string.msg_confirmacion_salida));
        dialog.setPositiveButton(getString(R.string.titulo_si), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Utils.setValueSharedPreferences(PrincipalActivity.this, "SANIPES_userJson", "");
                //Utils.setValueSharedPreferences(PrincipalActivity.this, "SANIPES_userJson", "");
                //Utils.setValueSharedPreferences(PrincipalActivity.this, "SANIPES_clave_Json", "");
                salirActivity = true;

                if (timerTask != null) timerTask.cancel();
                if (timer != null) timer.cancel();

                Intent loginIntent = new Intent().setClass(PrincipalActivity.this, LoginActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);
                finish();
            }
        });
        dialog.setNegativeButton(getString(R.string.titulo_no), null);
        dialog.show();
    }

    private void iniciarLogin(){
        salirActivity = true;

        if (timerTask != null) timerTask.cancel();
        if (timer != null) timer.cancel();

        Intent loginIntent = new Intent().setClass(this, ControlMaximoNumeroCuentas.class);
        startActivity(loginIntent);
        finish();
    }
}
