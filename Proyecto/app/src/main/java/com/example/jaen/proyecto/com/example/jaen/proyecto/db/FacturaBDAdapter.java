package com.example.jaen.proyecto.com.example.jaen.proyecto.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jaen.proyecto.Cliente;

/**
 * Created by jaen on 14/09/2015.
 */
public class FacturaBDAdapter {
    public static final String CAMPO_ID = "_id";
    public static final String CAMPO_CLIENTE = "cliente";
    public static final String CAMPO_LINEAPEDIDO = "lineaPedido";
    public static final String CAMPO_IVA = "iva";
    public static final String CAMPO_TOTAL = "total";
    private static final String TABLA_DB = "factura";
    private static final String DB = "proyecto.db";

    private Context contexto;
    private SQLiteDatabase db;
    private SQLiteHelperProyecto dbHelper;

    /**
     * Constructor.
     * @param contexto
     */
    public FacturaBDAdapter(Context contexto) {
        this.contexto = contexto;
    }

    /**
     * Método que abre la base de datos.
     * @return
     * @throws Exception
     */
    public FacturaBDAdapter abrir() throws Exception {
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
     * Crea una factura nueva.
     * @param cliente
     * @param lineaPedido
     * @param iva
     * @param total
     * @return
     */
    public long createFactura(Cliente cliente, String lineaPedido, String iva, String total) {
        ContentValues registro = crearContentValues(cliente.getNombre(), lineaPedido, iva, total);

        return db.insert(TABLA_DB, null, registro);
    }

    /**
     * Método para actualizar las facturas.
     * @param id
     * @param nombre
     * @param lineaPedido
     * @param iva
     * @param total
     * @return
     */
    public boolean updateFactura(long id, String nombre, String lineaPedido, String iva, String total) {
        ContentValues registro = crearContentValues(nombre, lineaPedido, iva, total);

        return db.update(TABLA_DB, registro, CAMPO_ID + "=" + id, null) > 0;
    }

    /**
     * Método para eliminar un cliente
     * @param id
     * @return
     */
    public boolean deleteFactura(long id) {
        return (db.delete(TABLA_DB, CAMPO_ID + "=" + id, null) > 0);
        // Reorganizar la base de datos.
        // Nos guardamos el id a eliminar.
        /*int identificador = (int) id;
        int nTuplas = 0;
        String ident = "";
        String nom = "";
        String lp = "";
        String imp = "";
        String tot = "";
        Cliente cliente = null;

        // Recogemos la tabla en el estado sin modificar.
        Cursor cursor = getTabla();
        // Comprobamos el total de tuplas existentes.
        if(cursor.moveToFirst()) {
            do {
                nTuplas++;
            } while (cursor.moveToNext());
        }
        // Recogemos el tupla siguiente a la tupla a eliminar.
        try {
            cursor = getFactura(id+1);
            if( cursor.moveToFirst() ) {
                ident = cursor.getColumnName(0).toString();
                nom = cursor.getColumnName(1).toString();
                cliente = new Cliente(nom, "", "", "");
                lp = cursor.getColumnName(2).toString();
                imp = cursor.getColumnName(3).toString();
                tot = cursor.getColumnName(4).toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Eliminamos la tupla que no queremos.
        if( db.delete(TABLA_DB, CAMPO_ID + "=" + id, null) > 0 ) {
            // Colocamos la factura siguiente a la eliminada en el sitio de la eliminada.
            createFactura(cliente, lp, imp, tot);
            for(int i = identificador; i < nTuplas ; i++) {

                db.delete(TABLA_DB, CAMPO_ID + "=" + id, null);

                cursor = getFactura(identificador+1);

                ident = cursor.getColumnName(0).toString();
                nom = cursor.getColumnName(1).toString();
                cliente = new Cliente(nom, "", "", "");
                lp = cursor.getColumnName(2).toString();
                imp = cursor.getColumnName(3).toString();
                tot = cursor.getColumnName(4).toString();

            }
        } else {
            return false;
        }*/

    }

    /**
     * Método para recoger una factura
     * @param id
     * @return
     * @throws Exception
     */
    public Cursor getFactura(long id) throws Exception {
        Cursor cursor = db.query(true, TABLA_DB, new String[] { CAMPO_ID, CAMPO_CLIENTE, CAMPO_LINEAPEDIDO, CAMPO_IVA, CAMPO_TOTAL },
                CAMPO_ID + "=" +id, null, null, null, null, null);

        if(cursor != null ) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    /**
     * Devuelve la consulta de todas las facturas.
     * @return
     */
    public Cursor getTabla() {
        return db.query(TABLA_DB, new String[] { CAMPO_ID, CAMPO_CLIENTE, CAMPO_LINEAPEDIDO, CAMPO_IVA, CAMPO_TOTAL }, null, null, null, null, null);
    }

    /**
     *  Método que crea un ContentValue.
     */
    public ContentValues crearContentValues(String nombre, String lineaPedido, String iva, String total) {

        ContentValues registro = new ContentValues();

        registro.put(CAMPO_CLIENTE, nombre);
        registro.put(CAMPO_LINEAPEDIDO, lineaPedido);
        registro.put(CAMPO_IVA, iva);
        registro.put(CAMPO_TOTAL, total);

        return registro;
    }
}
