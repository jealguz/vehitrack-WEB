package com.mycompany.vehitrackweb.models;

public class Favorito {
    private int id_favorito;
    private int id_usuario;
    private String nombre_taller; // Coincide con tu SQL
    private double latitud;
    private double longitud;
    private String direccion;

    public Favorito() {
    }

    // Getters y Setters
    public int getId_favorito() { return id_favorito; }
    public void setId_favorito(int id_favorito) { this.id_favorito = id_favorito; }

    public int getId_usuario() { return id_usuario; }
    public void setId_usuario(int id_usuario) { this.id_usuario = id_usuario; }

    public String getNombre_taller() { return nombre_taller; }
    public void setNombre_taller(String nombre_taller) { this.nombre_taller = nombre_taller; }

    public double getLatitud() { return latitud; }
    public void setLatitud(double latitud) { this.latitud = latitud; }

    public double getLongitud() { return longitud; }
    public void setLongitud(double longitud) { this.longitud = longitud; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}