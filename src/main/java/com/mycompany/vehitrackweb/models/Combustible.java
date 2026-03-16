package com.mycompany.vehitrackweb.models;

import java.sql.Date;

/**
 * Modelo para la tabla 'gasto_combustible'.
 * @author Jeison Guzman
 */
public class Combustible {
    private int id_gasto_combustible;
    private int id_vehiculo;
    private Date fecha;
    private float cantidad; // galones o litros
    private float costo;
    private int kilometraje;

    public Combustible() {}

    // Getters y Setters
    public int getId_gasto_combustible() { return id_gasto_combustible; }
    public void setId_gasto_combustible(int id_gasto_combustible) { this.id_gasto_combustible = id_gasto_combustible; }

    public int getId_vehiculo() { return id_vehiculo; }
    public void setId_vehiculo(int id_vehiculo) { this.id_vehiculo = id_vehiculo; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public float getCantidad() { return cantidad; }
    public void setCantidad(float cantidad) { this.cantidad = cantidad; }

    public float getCosto() { return costo; }
    public void setCosto(float costo) { this.costo = costo; }

    public int getKilometraje() { return kilometraje; }
    public void setKilometraje(int kilometraje) { this.kilometraje = kilometraje; }
}