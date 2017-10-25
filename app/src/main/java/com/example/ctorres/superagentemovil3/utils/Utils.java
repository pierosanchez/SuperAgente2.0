package com.example.ctorres.superagentemovil3.utils;

/**
 * Created by CTORRES on 05/05/2017.
 */

import android.net.Uri;
import android.util.Log;

/*import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;*/
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

public class Utils {
    private static final String TAG = Utils.class.getSimpleName();
    public JSONObject getJSONfromURL(String url){
        JSONObject json = null;
        try{
            Log.e("1", "1");
            String result = getResultConnectivity(url);
            Log.e("Line",result.toString());
            Log.e("2", result );
            if(result!=null) {
                if(!result.equals("")) {
                    json = new JSONObject(result);
                }
            }
        }catch(JSONException e){
            Log.e("JSON", "Falló la conversión a JSONObject");
            //Log.e("ERROR",Log.getStackTraceString(e));
            return null;
        }

        return json;
    }

    public JSONArray getJSONArrayfromURL(String url){
        JSONArray jsonArray = null;
        try{
            String result = getResultConnectivity(url);
            if(result!=null) {
                if(!result.equals("")) {
                    jsonArray = new JSONArray(result);
                }
            }
        }catch(JSONException e){
            Log.e("JSON", "Falló la conversión a JSONObject");
            //Log.e("ERROR",Log.getStackTraceString(e));
            return null;
        }

        return jsonArray;
    }

    public String getJsonarrayFromUrl(String url){
        String request = null;

        try {
            URL u = new URL(url);
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(con.getInputStream());
            request = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }

        return request;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    public String getResultConnectivity(String url) {
        HttpURLConnection urlConnection;
        StringBuilder result = new StringBuilder();
        try {
            URL getUrl = new URL(url);
            try {
                urlConnection = (HttpURLConnection) getUrl.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            } catch (IOException e) {
                //e.printStackTrace();
                return null;
            }
        } catch (MalformedURLException e) {
            //e.printStackTrace();
            return null;
        }

        return result.toString();
    }

    /* String getResultConnectivity(String url){
        InputStream is = null;
        String result = "";
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(Uri.encode(url, Constante.ALLOWED_URI_CHARS));
            HttpResponse response = httpclient.execute(httpget);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
            }
        } catch (Exception e) {
            //Log.e("ERROR", Log.getStackTraceString(e));
            return null;
        }

        try{
            if(is!=null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                is.close();
                result = sb.toString();
            }
        } catch(Exception e){
            //Log.e("JSON", "Falló la descarga de archivo json");
            //Log.e("ERROR",Log.getStackTraceString(e));
            return null;
        }
        return result;
    }*/

    public String getValueStringOrNull(JSONObject objeto, String key){
        try {
            String resultado = (objeto.isNull(key) ? null : objeto.getString(key));

            if(resultado!=null) {
                if (resultado.equals("null")) {
                    resultado = null;
                }
            }

            return resultado;
        }catch(Exception e){
            //Log.e("ERROR",Log.getStackTraceString(e));
            return null;
        }
    }

    //Otra parte del codigo del web service


}
