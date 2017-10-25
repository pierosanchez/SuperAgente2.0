package com.example.ctorres.superagentemovil3.dao;

import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.entity.BeneficiarioEntity;
import com.example.ctorres.superagentemovil3.entity.UsuarioEntity;
import com.example.ctorres.superagentemovil3.utils.Constante;
import com.example.ctorres.superagentemovil3.utils.Utils;
import junit.framework.TestCase;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Random;


public class TestPruebas extends TestCase {
    private Utils utils;

    //private ArrayList<AndroidVersion> data;
    //private Utils utils;
    //BeneficiarioEntity benef;

    /*public void testBeneficiario() {
        ArrayList lista = new ArrayList<>();

        String url = Constante.IPORHOST + "webApi/apigeneral/ApiGeneral/ListadoBeneficiario/?idcliente=%27409%27";

        try {
            JSONArray jsonArray = utils.getJSONArrayfromURL(url);
            if(jsonArray != null){
                if(jsonArray.length()>0){
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    lista.add(utils.getValueStringOrNull(jsonObject, "nombre"));
                } else {
                    lista =null;
                }
            } else {
                lista =null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(lista);
    }*/

    /*public void testBeneficiario(){
        Random r1 = new Random();
        Random r2 = new Random();
        Random r3 = new Random();
        Random r4 = new Random();
        int limite = 8;
        System.out.println(r1.nextInt(limite + 1) + r2.nextInt(limite + 1) + r3.nextInt(limite + 1) + r4.nextInt(limite + 1));
    }*/

    public void testBeneficiario() {
        UsuarioEntity benef;
        String arrayJason = null;
        try {
            benef = new UsuarioEntity();

            String url = Constante.IPORHOST + "webApi_2/apigeneral/ApiGeneral/ValidarLogin/?usuario=946876154&contraseña=12345678";

            arrayJason = utils.getJsonarrayFromUrl(url);
            //Log.e("Json", arrayJason.toString());
            if (arrayJason != null) {
                if (arrayJason.length() > 0) {


                } else {
                    benef = null;
                }
            } else {
                benef = null;
            }

        } catch (Exception e) {
            Log.getStackTraceString(e);
            benef = null;
        }

        System.out.println(arrayJason);
    }
}
