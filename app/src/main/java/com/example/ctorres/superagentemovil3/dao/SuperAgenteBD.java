package com.example.ctorres.superagentemovil3.dao;

/**
 * Created by CTORRES on 05/05/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ctorres.superagentemovil3.utils.Constante;

public class SuperAgenteBD extends SQLiteOpenHelper {
    private static int version = 1;
    private static String name = Constante.BDSUPERAGENTE;
    private static SQLiteDatabase.CursorFactory factory = null;
    String sql = "CREATE TABLE Cliente (movil TEXT)";
    String sqlOld = "CREATE TABLE USUARIO( " + "" +
            "IdCliente varchar(50) NULL," +
            "Nombres varchar(20) NULL," +
            "Apellidos varchar(20) NULL," +
            "Email varchar(50) NULL," +
            "Movil varchar(9) NULL," +
            "Clave varchar(50) NULL," +
            "ClaveAcceso varchar(50) NULL," +
            "PreguntaSegundaClave varchar(50) NULL," +
            "SegundaClaveAcceso varchar(50) NULL," +
            "NumeroTarjeta varchar(50) NULL," +
            "InsertarGTarjeta varchar(50) NULL," +
            "CantidadTarjetaInsertada varchar(50) NULL," +
            "importe varchar(50) NULL";

    /*public SuperAgenteBD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }*/

    public SuperAgenteBD(Context context){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
