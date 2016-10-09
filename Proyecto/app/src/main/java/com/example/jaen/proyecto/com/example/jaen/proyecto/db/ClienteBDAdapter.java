package com.example.jaen.proyecto.com.example.jaen.proyecto.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaen on 13/09/2015.
 */
public class ClienteBDAdapter {
    public static final String CAMPO_ID = "_id";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_APELLIDOS = "apellidos";
    public static final String CAMPO_TELEFONO = "telefono";
    public static final String CAMPO_CORREO = "correo";
    private static final String TABLA_DB = "cliente";
    private static final String DB = "proyecto.db";

    private Context contexto;
    private SQLiteDatabase db;
    private SQLiteHelperProyecto dbHelper;

    /**
     * Constructor.
     * @param contexto
     */
    public ClienteBDAdapter(Context contexto) {
        this.contexto = contexto;
    }

    /**
     * Método que abre la base de datos.
     * @return
     * @throws Exception
     */
    public ClienteBDAdapter abrir() throws Exception {
        // Abrimos la base de datos en modo escritura.
        dbHelper = new SQLiteHelperProyecto(contexto, DB, null, 1);
        db = dbHelper.getWritableDatabase();

        return this;
    }

    /**
     * Método que cierra la base de datos
     */
    public void cerrar(){

        dbHelper.close();
    }

    /**
     * Método para crear un cliente nuevo.
     * @param nombre
     * @param apellidos
     * @param telefono
     * @param correo
     * @return
     */
    public long createCliente(String nombre, String apellidos, String telefono, String correo) {
        ContentValues registro = crearContentValues(nombre, apellidos, telefono, correo);

        return db.insert(TABLA_DB, null, registro);
    }

    /**
     * Método para actualizar cliente.
     * @param id
     * @param nombre
     * @param apellidos
     * @param telefono
     * @param correo
     * @return
     */
    public boolean updateCliente(long id, String nombre, String apellidos, String telefono, String correo) {
        ContentValues registro = crearContentValues(nombre, apellidos, telefono, correo);

        return db.update(TABLA_DB, registro, CAMPO_ID + "=" + id, null) > 0;
    }

    /**
     * Método para eliminar un cliente
     * @param id
     * @return
     */
    public boolean deleteCliente(long id) {
        return db.delete(TABLA_DB, CAMPO_ID + "=" + id, null) > 0;
    }

    /**
     * Método para recoger un cliente
     * @param id
     * @return
     * @throws Exception
     */
    public Cursor getCliente(long id) throws Exception {
        Cursor cursor = db.query(true, TABLA_DB, new String[] {CAMPO_ID, CAMPO_NOMBRE, CAMPO_APELLIDOS, CAMPO_TELEFONO, CAMPO_CORREO},
                CAMPO_ID + "=" +id, null, null, null, null, null);

        if(cursor != null ) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    /**
     * Devuelve la consulta de todos los clientes.
     * @return
     */
    public Cursor getTabla() {
        return db.query(TABLA_DB, new String[] {CAMPO_ID, CAMPO_NOMBRE, CAMPO_APELLIDOS, CAMPO_TELEFONO, CAMPO_CORREO}, null, null, null, null, null);
    }

    /**
     * Devolvemos String de nombres de clientes.
     * @return
     */
    public List getNombre() {

        List<String> lista = new ArrayList<String>();
        String SQL = "SELECT "+ CAMPO_NOMBRE + " , " + CAMPO_APELLIDOS + " FROM " + TABLA_DB;
        // Realizamos la consulta.
        //Cursor cursor = db.rawQuery(SQL, null);
        Cursor cursor = db.query(TABLA_DB, new String[] {CAMPO_NOMBRE, CAMPO_APELLIDOS}, null, null, null, null, null);

        Log.e("LogsAndroid", SQL);

        if(cursor.moveToFirst()) {
            do {
                //String n = cursor.getString(0);
                //String a = cursor.getString(1);
                lista.add(cursor.getString(0) + " " + cursor.getString(1));
            } while(cursor.moveToNext());
        }
        //Devolvemos la lista.
        return lista;
    }
    /**
     *  Método que crea un ContentValue.
     */
    public ContentValues crearContentValues(String nombre, String apellidos, String telefono, String correo) {

        ContentValues registro = new ContentValues();

        registro.put(CAMPO_NOMBRE, nombre);
        registro.put(CAMPO_APELLIDOS, apellidos);
        registro.put(CAMPO_TELEFONO, telefono);
        registro.put(CAMPO_CORREO, correo);

        return registro;
    }
}
