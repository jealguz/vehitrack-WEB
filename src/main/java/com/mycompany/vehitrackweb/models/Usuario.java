package com.mycompany.vehitrackweb.models;

/**
 * Clase Modelo Usuario: Representa la entidad 'usuario' de la base de datos vehitrack_db.
 * Esta clase sirve para transportar los datos entre la base de datos y la interfaz.
 * * @author Jeison Guzman
 */
public class Usuario {
    
    // Atributos privados: corresponden exactamente a las columnas de la tabla en MySQL
    private int id_usuario;
    private String nombre;
    private String apellido;
    private String email;
    private String contraseña;

    /**
     * Constructor vacío: Necesario para que frameworks y servlets puedan 
     * instanciar el objeto antes de llenarlo con datos.
     */
    public Usuario() {}

    // --- MÉTODOS GETTERS Y SETTERS ---
    // Permiten el acceso controlado a los atributos privados (Encapsulamiento)

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}