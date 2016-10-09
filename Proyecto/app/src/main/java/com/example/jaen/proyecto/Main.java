package com.example.jaen.proyecto;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jaen.proyecto.com.example.jaen.proyecto.db.SQLiteHelperProyecto;

public class Main extends Activity {
    private String usuario;
    private String password;
    private String mensaje = "";
    private AlertDialog.Builder ventana;

    private SQLiteHelperProyecto dbHelper;
    private SQLiteDatabase db;

    private static final int ERROR = 0;

    private static final int SUBACTIVIDAD = 1;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Método Boton.
        final Button btnEntrar = (Button)findViewById(R.id.btnAceptar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Se crea el objeto EditText
                final EditText etUsuario = (EditText) findViewById(R.id.etUser);
                final EditText etPassword = (EditText) findViewById(R.id.etPassword);

                // Se almacena en al variable String los datos introducidos en los EditTet mediante CAST.
                usuario = etUsuario.getText().toString();
                password = etPassword.getText().toString();

                //Comprobamos si el usuario a rellenado los campos.
                if (usuario.length() == 0) {
                    // Mostramos el mensaje en el TextView.
                    Toast.makeText(getBaseContext(), "En el campo usuario es necesarios introducir texto", Toast.LENGTH_SHORT).show();

                } else if (password.length() == 0) {

                    // Mostramos el mensaje en el TextView.
                    Toast.makeText(getBaseContext(), "En el campo password es necesarios introducir texto", Toast.LENGTH_SHORT).show();
                }
                if (usuario.length() != 0 && password.length() != 0) {
                    // Realizamos la consulta con la base de datos en la tabla de usuarios.
                    dbHelper = new SQLiteHelperProyecto(Main.this, "proyecto.db", null, 1);

                    // Modo escritura,
                    db = dbHelper.getWritableDatabase();

                    if(db != null) {
                        String SQL = "SELECT usuario, password FROM usuario WHERE usuario LIKE '" + usuario +"' AND password LIKE '" + password + "'";
                        // Se realiza la consulta y recogemos el cursor.
                        Cursor cursor = db.rawQuery(SQL, null);

                        if(cursor.moveToFirst()) {
                          // El usuario existe. Lanzamos el intent para acceder a la siguiente actividad.
                            intent = new Intent(Main.this, Actividad.class);
                            startActivity(intent);

                        } else {
                            showDialog(ERROR);
                        }
                    }
                }
            }
        });
    }

    /**
     * Ventana emergente de error si no se introduce el usuario o contraseña correctos.
     * @param id
     * @return
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == 0) {
            ventana = new AlertDialog.Builder(this);
            ventana.setTitle("Error");
            ventana.setMessage("El usuario introducido o la contraseña no coinciden con ningún usurio");
        }
        return ventana.create();
    }

}
