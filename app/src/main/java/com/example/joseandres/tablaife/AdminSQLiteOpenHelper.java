package com.example.joseandres.tablaife;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jose Andres on 17/04/2016.
 */
//Se codifica esta clase que tiene por objetivo administrar la base de datos.
// Primero hay que hacer que la clase herede de la clase SQLiteOpenHelper.
public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(Context context, String name,
                                 SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //La clase SQLiteOpenHelper requiere que se implementen dos métodos obligatoriamente
    // onCreate y onUpgrade.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table votante(codigo int primary key,direccion text," +
                "numero real)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
