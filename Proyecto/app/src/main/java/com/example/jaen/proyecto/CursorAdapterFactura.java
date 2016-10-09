package com.example.jaen.proyecto;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jaen.proyecto.com.example.jaen.proyecto.db.FacturaBDAdapter;

/**
 * Created by jaen on 15/09/2015.
 */
public class CursorAdapterFactura extends CursorAdapter {

    private FacturaBDAdapter dbAdapter;
    private CursorAdapterFactura cursorAdapterFactura;
    private TextView nFactura, nombre, apellidos;
    private ImageView icono;
    private Cursor cursor;

    public CursorAdapterFactura(Context contexto, Cursor cursor)throws Exception{
        super(contexto, cursor, false);

        this.cursor = cursor;
        dbAdapter = new FacturaBDAdapter(contexto);
        dbAdapter.abrir();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Es necesario crear un inflater a partir del contexto
        final LayoutInflater inflater = LayoutInflater.from(context);
        // Creamos una vista que es un listado simple de tipo dropdown (definido por Android)
        final View view = inflater.inflate(R.layout.factura, parent, false);

        // Devolvemos este nuevo listado creado para que el adaptador sepa como dibujar la pantalla
        // al desplegar las opciones del AutoCompleteTextView
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        icono = (ImageView) view.findViewById(R.id.icono);
        nombre = (TextView) view.findViewById(R.id.nombre);
        nFactura = (TextView) view.findViewById(R.id.nFactura);

        icono.setImageResource(R.drawable.ic_action_document);
        nFactura.setText("Factura Nº : " + cursor.getString(cursor.getColumnIndex("_id")));
        nombre.setText("   " + cursor.getString(cursor.getColumnIndex("cliente")));
    }
}
