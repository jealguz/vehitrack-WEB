package com.mycompany.vehitrackweb.models;

/**
 * Clase Modelo Usuario: Representa la entidad 'usuario' de la base de datos vehitrack_db.
 * Esta clase sirve para transportar los datos entre la base de datos y la interfaz.
 * @author Jeison Guzman
 */
public class Usuario {
    
    // Atributos privados: corresponden exactamente a las columnas de la tabla en MySQL
    private int id_usuario;      // Identificador único del usuario
    private String nombre;       // Nombres del usuario
    private String apellido;     // Apellidos del usuario
    private String email;        // Correo electrónico (usado para login)
    private String contraseña;   // Clave de acceso al sistema

    /**
     * Constructor vacío: Necesario para que frameworks y servlets puedan 
     * instanciar el objeto antes de llenarlo con datos.
     */
    public Usuario() {}

    // --- MÉTODOS GETTERS Y SETTERS ---
    // Permiten el acceso controlado a los atributos privados (Encapsulamiento)

    /** @return El ID único del usuario */
    public int getId_usuario() {
        return id_usuario;
    }

    /** @param id_usuario El nuevo ID a asignar */
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    /** @return El nombre del usuario */
    public String getNombre() {
        return nombre;
    }

    /** @param nombre El nombre a asignar */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return El apellido del usuario */
    public String getApellido() {
        return apellido;
    }

    /** @param apellido El apellido a asignar */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /** @return El email registrado */
    public String getEmail() {
        return email;
    }

    /** @param email El email a asignar */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return La contraseña (hash o texto plano según implementación) */
    public String getContraseña() {
        return contraseña;
    }

    /** @param contraseña La contraseña a asignar */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}