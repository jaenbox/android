package com.example.jaen.proyecto;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jaen.proyecto.com.example.jaen.proyecto.db.ClienteBDAdapter;
import com.example.jaen.proyecto.com.example.jaen.proyecto.db.SQLiteHelperProyecto;

/**
 * Created by jaen on 14/09/2015.
 */
public class CursorAdapterCliente extends CursorAdapter {

    private ClienteBDAdapter dbAdapter;
    private CursorAdapterCliente cursorAdapterCliente;
    private Cursor cursor;
    private TextView nombre, apellidos, telefono;
    private ImageView icono;

    public CursorAdapterCliente(Context context, Cursor c) throws Exception {
        super(context, c, false);

        cursor = c;
        dbAdapter = new ClienteBDAdapter(context);
        dbAdapter.abrir();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Es necesario crear un inflater a partir del contexto
        final LayoutInflater inflater = LayoutInflater.from(context);
        // Creamos una vista que es un listado simple de tipo dropdown (definido por Android)
        final View view = inflater.inflate(R.layout.opcion, parent, false);

        // Devolvemos este nuevo listado creado para que el adaptador sepa como dibujar la pantalla
        // al desplegar las opciones del AutoCompleteTextView
        return view;
    }

    /**
     * Creamos el adaptador, asi es como se vera en el listView.
     * @param view
     * @param context
     * @param cursor
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        icono = (ImageView) view.findViewById(R.id.icono);
        nombre = (TextView) view.findViewById(R.id.nombre);
        apellidos = (TextView) view.findViewById(R.id.apellidos);
        telefono = (TextView) view.findViewById(R.id.telefono);

        icono.setImageResource(R.drawable.ic_action_user);
        nombre.setText(cursor.getString(cursor.getColumnIndex("nombre")));
        apellidos.setText(" " + cursor.getString(cursor.getColumnIndex("apellidos")));
        telefono.setText("   telf:  " + cursor.getString(cursor.getColumnIndex("telefono")));

    }
}
