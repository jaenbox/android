package com.example.jaen.proyecto;

import android.app.Activity;
import android.app.ListActivity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.jaen.proyecto.com.example.jaen.proyecto.db.ClienteBDAdapter;
import com.example.jaen.proyecto.com.example.jaen.proyecto.db.FacturaBDAdapter;


/**
 * Created by jaen on 13/09/2015.
 */
public class Actividad extends TabActivity {

    private static final int ACTIVIDAD_NUEVA = 0;

    // Intent para abrir las opciones de clientes y facturas.
    private Intent intent;
    private Intent intentCliente;
    private Intent intentFactura;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad);

        // Buscador de imagenes.
        Resources res = getResources();

        // Creamos el tabhost de la actividad
        TabHost tabHost = getTabHost();

        TabHost.TabSpec spec;   // Recurso para las propiedades de las opciones

        // Se crea el intent para abrir la actividad.
        // Configuramos la opción Cliente.
        intent = new Intent().setClass(this, ClienteList.class);

        spec = tabHost.newTabSpec("Clientes").setContent(intent).setIndicator("Clientes");
        tabHost.addTab(spec);
        tabHost.getTabWidget().getChildAt(0);

        // Se crea el intent para abrir la actividad.
        // Configuramos la opción Factura.
        intent = new Intent().setClass(this, FacturaList.class);

        spec = tabHost.newTabSpec("Factura").setContent(intent).setIndicator("Facturas");
        tabHost.addTab(spec);
        tabHost.getTabWidget().getChildAt(1);
    }

    /**
     * Menu principal de la aplicación.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Definimos el menú inflando el fichero menu_main.xml
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
        return true;
    }

    /**
     * Método del menu_contextual, se utiliza para crear clientes y facturas nuevas.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.anadirCliente:
                //Toast.makeText(getBaseContext(), "Has pulsado añadir cliente", Toast.LENGTH_LONG).show();
                intentCliente = new Intent(this, GestionarCliente.class);
                startActivityForResult(intentCliente, ACTIVIDAD_NUEVA);
                return true;
            case R.id.anadirFactura:
                //Toast.makeText(getBaseContext(), "Has pulsado añadir factura", Toast.LENGTH_LONG).show();
                // Intent a gestionarFactura.
                intentFactura = new Intent(this, GestionarFactura.class);
                startActivityForResult(intentFactura, ACTIVIDAD_NUEVA);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
