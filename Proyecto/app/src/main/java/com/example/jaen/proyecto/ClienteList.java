package com.example.jaen.proyecto;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.jaen.proyecto.com.example.jaen.proyecto.db.ClienteBDAdapter;
import com.example.jaen.proyecto.com.example.jaen.proyecto.db.FacturaBDAdapter;

/**
 * Created by jaen on 16/09/2015.
 */
public class ClienteList extends ListActivity {

    private ClienteBDAdapter dbHelper;
    private Cursor cursor;

    private static final int ACTIVIDAD_EDITAR = 1;
    private static final int MENU_ID = Menu.FIRST + 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cliente_list);
        ListView listado = (ListView)findViewById(android.R.id.list);

        dbHelper = new ClienteBDAdapter(this);
        //Abrimos la base de datos.
        try {
            dbHelper.abrir();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Cargamos los datos recogiendo el cursor.
        try {
            cargarDatos();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Indicamos el menú contextual asociado al listado
        registerForContextMenu(getListView());
    } // end onCreate

    /**
     *  Método donde definimos el menú contextual para poder eliminar las facturas.
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        // Definimos la cabecera del menú contextual
        menu.setHeaderTitle("Selecciona una opción");//listado.getAdapter().getItem(info.position).toString());
        menu.add(0, MENU_ID, 0, "Eliminar cliente");
    }

    /**
     *  El usuario hace clic en una opción del menú contextual del listado
      */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Buscamos la opción del menú contextual seleccionada
        switch (item.getItemId()) {
            case MENU_ID:
                // Obtenemos el id del elemento seleccionado
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                // Borramos ese registro
                dbHelper.deleteCliente(info.id);
                // Recargamos los datos
                try {
                    cargarDatos();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Indicamos que hemos manejado la opción del menú
                return true;
        }
        return super.onContextItemSelected(item);
    }

    /**
     *  Cuando hacemos click en un elemento del listView pasamos a editar la factura.
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // Creamos una actividad indicando el tipo de petición "ACTIVIDAD_EDITAR"
        // y esperamos el resultado de la misma
        Intent i = new Intent(this, GestionarCliente.class);
        // Pasamos el campo _id como un dato extra
        i.putExtra(FacturaBDAdapter.CAMPO_ID, id);
        startActivityForResult(i, ACTIVIDAD_EDITAR);
    }
    /**
     * Cargamos los datos de la base de datos al cursor y lo mostramos en el listView.
     */
    public void cargarDatos() throws Exception {

        cursor = dbHelper.getTabla();
        startManagingCursor(cursor);

        // Llamamos al cursorAdapter para adaptar la opción de la lista.
        CursorAdapterCliente adaptador = new CursorAdapterCliente(this, cursor);
        setListAdapter(adaptador);
    }
    /**
    * Cuando se acaba la actividad cerramos la base de datos.
    */
    @Override
    protected void onDestroy() {

        super.onDestroy();

        if(dbHelper != null) {
            dbHelper.cerrar();
        }
    }
}
