package com.example.jaen.proyecto;

import android.provider.ContactsContract;

/**
 * Created by jaen on 13/09/2015.
 */
public class Cliente {

    private String nombre;
    private String apellidos;
    private String telefono;
    private String correo;

    /**
     * Constructor class Cliente.
     * @param nombre
     * @param apellidos
     * @param telefono
     * @param correo
     */
    public Cliente(String nombre, String apellidos, String telefono, String correo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
    }

    // Getters && Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
}
