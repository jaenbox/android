package com.example.jaen.proyecto.com.example.jaen.proyecto.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.jaen.proyecto.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by jaen on 11/09/2015.
 */
public class SQLiteHelperProyecto extends SQLiteOpenHelper{

    private static String crearBD1 = "CREATE TABLE usuario (_id INTEGER PRIMARY KEY AUTOINCREMENT, usuario TEXT, password TEXT);";
    private static String crearBD2 = "CREATE TABLE cliente (_id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, apellidos TEXT, telefono TEXT, correo TEXT );";
    private static String crearBD3 = "CREATE TABLE factura (_id INTEGER PRIMARY KEY AUTOINCREMENT, cliente TEXT, lineaPedido TEXT, iva TEXT, total TEXT );";

    private static final String NOMBRE_BD = "proyecto";
    private static final String TABLA_BD = "usuario";
    private Context context;

    public SQLiteHelperProyecto(Context contexto, String nombre, SQLiteDatabase.CursorFactory cursorFactory, int version) {
        super(contexto, nombre, cursorFactory, version);
        this.context = contexto;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Creamos la base de datos
        db.execSQL(crearBD1);

        // Creamos el contenValues
        ContentValues registro = new ContentValues();
        registro.put("usuario", "root");
        registro.put("password","root");

        //Añadimos el usuario root a la db
        db.insert(TABLA_BD, null, registro);

        // Rellenamos la base de datos de clientes
        // Creamos unos registros en las tablas a partir del .sql
        try {
            // La estructura de la base de datos se define dentro del fichero bdnombres.sql
            // Cargamos también datos en la base de datos
            InputStream ficheroraw = context.getResources().openRawResource(R.raw.dbclientes);
            BufferedReader in = new BufferedReader(new InputStreamReader(ficheroraw));
            String auxStr = "";
            while ((auxStr = in.readLine()) != null){
                db.execSQL(auxStr);
            } // end while
            in.close();
        } catch (IOException e) {

        }
        // Rellenamos la base de datos de facturas.
        try {
            // La estructura de la base de datos se define dentro del fichero bdnombres.sql
            // Cargamos también datos en la base de datos
            InputStream ficheroraw = context.getResources().openRawResource(R.raw.dbfacturas);
            BufferedReader in = new BufferedReader(new InputStreamReader(ficheroraw));
            String auxStr = "";
            while ((auxStr = in.readLine()) != null){
                db.execSQL(auxStr);
            } // end while
            in.close();
        } catch (IOException e) {

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL(crearBD1);
        db.execSQL(crearBD2);
        db.execSQL(crearBD3);
    }

}
