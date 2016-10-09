package com.example.jaen.proyecto;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jaen.proyecto.com.example.jaen.proyecto.db.ClienteBDAdapter;


/**
 * Created by jaen on 14/09/2015.
 */
public class GestionarCliente extends Activity {

    private EditText nombre;
    private EditText apellidos;
    private EditText telefono;
    private EditText correo;
    private Button aceptar;

    private Long filaId;
    private ClienteBDAdapter dbHelper;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        super.setTitle("Administración de clientes");

        // Creamos el adaptador y abrimos la BD.
        dbHelper = new ClienteBDAdapter(this);

        try {
            dbHelper.abrir();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Instanciamos la vista.
        setContentView(R.layout.gestionar_cliente);
        nombre = (EditText) findViewById(R.id.etNombre);
        apellidos = (EditText) findViewById(R.id.etApellidos);
        telefono = (EditText) findViewById(R.id.etTelefono);
        correo = (EditText) findViewById(R.id.etCorreo);
        aceptar = (Button) findViewById(R.id.btnAceptar);

        // Variable con el id del regsitro actual
        filaId = null;
        // Recogemos el id pasado con el intent.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            filaId = extras.getLong(ClienteBDAdapter.CAMPO_ID);
        }


        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Recogemos los valores.
                String n = nombre.getText().toString();
                String a = apellidos.getText().toString();
                String t = telefono.getText().toString();
                String c = correo.getText().toString();

                if(nombre.length() == 0 || apellidos.length() == 0 || telefono.length() == 0 || correo.length() == 0) {
                    Toast.makeText(getBaseContext(), "Faltan campos a rellenar" , Toast.LENGTH_LONG).show();
                } else {
                    // registramos o upgradeamos.
                    if (filaId == null) {
                        dbHelper.createCliente(n, a, t, c);
                    } else {
                        dbHelper.updateCliente(filaId, n, a, t, c);
                    }
                    setResult(RESULT_OK);

                    finish();

                }

            }

        });
    }

}