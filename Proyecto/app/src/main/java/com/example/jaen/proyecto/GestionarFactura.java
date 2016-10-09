package com.example.jaen.proyecto;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jaen.proyecto.com.example.jaen.proyecto.db.ClienteBDAdapter;
import com.example.jaen.proyecto.com.example.jaen.proyecto.db.FacturaBDAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaen on 15/09/2015.
 */
public class GestionarFactura extends Activity {

    private Spinner nombre;
    private String nombreSeleccionado;
    private EditText lineaPedido;
    private Spinner iva;
    private EditText total;
    private Button update;
    private String ivaSeleccionado;
    private ArrayAdapter spinner_adapter;
    private Cursor cursor;
    private SimpleCursorAdapter cliente_adapter;
    private List<String> listaNombres;

    private Long filaId;
    private FacturaBDAdapter dbHelper;
    private ClienteBDAdapter dbHelperC;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.gestionar_factura);
        super.setTitle("Administración de facturas");

        // Creamos el adaptador y abrimos la BD.
        dbHelper = new FacturaBDAdapter(this);

        try {
            dbHelper.abrir();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Instanciamos la vista.
        nombre = (Spinner) findViewById(R.id.spinnerNombre);
        try {
            confSpinnerNombre();
        } catch (Exception e) {
            e.printStackTrace();
        }
        lineaPedido = (EditText) findViewById(R.id.etLineaPedido);
        // Configuramos el Spinner.
        confSpinner();
        total = (EditText) findViewById(R.id.etTotal);
        update = (Button) findViewById(R.id.btnActualizarFactura);

        // Variable con el id del regsitro actual
        filaId = null;
        // Recogemos el id pasado con el intent.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            filaId = extras.getLong(FacturaBDAdapter.CAMPO_ID);
        }

        nombre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nombreSeleccionado = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**
         * Definimos el evento seOnItemSelected
         */
        iva.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ivaSeleccionado = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Iva por defecto.
                ivaSeleccionado = "21";
            }
        });

        /**
         * Listener del botón Aceptar.
         */
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Recogemos los valores.
                Cliente cliente = new Cliente(nombreSeleccionado, "", "", "");
                String lp = lineaPedido.getText().toString();
                String t = total.getText().toString();

                if(nombreSeleccionado.length() == 0 || lp.length() == 0 || t.length() == 0) {
                    Toast.makeText(getBaseContext(), "Faltan campos a rellenar", Toast.LENGTH_LONG).show();
                } else {
                    // registramos o upgradeamos.
                    if (filaId == null) {
                        dbHelper.createFactura(cliente, lp, ivaSeleccionado, t);
                    } else {
                        dbHelper.updateFactura(filaId, nombreSeleccionado, lp, ivaSeleccionado, t);
                    }

                    setResult(RESULT_OK);

                    finish();
                }
            }

        });
    }

    public void confSpinnerNombre() throws Exception {

        dbHelperC = new ClienteBDAdapter(this);
        // Abrimos la base de datos.
        dbHelperC.abrir();

        listaNombres = new ArrayList<String>();
        // Alamcenamos la consulta en el ArrayList.
        listaNombres = dbHelperC.getNombre();
        // Pasamos el ArrayLsit por el ArrayAdapter
        ArrayAdapter<String> adaptador_cliente = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaNombres);
        // Ponemos el tipo de adaptador.
        adaptador_cliente.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Ponemos le adaptador en el spinner.
        nombre.setAdapter(adaptador_cliente);

        nombre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Almacenamos el usuario en su variable para crear la factura.
                nombreSeleccionado = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                nombreSeleccionado = null;
            }
        });
    }

    public void confSpinner() {
        // Añadimos al Spinner los elementos del arrayList.
        iva = (Spinner) findViewById(R.id.spinner);
        spinner_adapter = ArrayAdapter.createFromResource(this, R.array.impuestos, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        iva.setAdapter(spinner_adapter);
    }

}
