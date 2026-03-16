package com.mycompany.vehitrackweb.models;

import java.sql.Date;

public class Mantenimiento {
    private int id_mantenimiento;
    private int id_vehiculo; // Llave foránea
    private Date fecha_programada;
    private Date fecha_realizacion;
    private String descripcion;
    private double costo;

    public Mantenimiento() {}

    // Getters y Setters
    public int getId_mantenimiento() { return id_mantenimiento; }
    public void setId_mantenimiento(int id_mantenimiento) { this.id_mantenimiento = id_mantenimiento; }

    public int getId_vehiculo() { return id_vehiculo; }
    public void setId_vehiculo(int id_vehiculo) { this.id_vehiculo = id_vehiculo; }

    public Date getFecha_programada() { return fecha_programada; }
    public void setFecha_programada(Date fecha_programada) { this.fecha_programada = fecha_programada; }

    public Date getFecha_realizacion() { return fecha_realizacion; }
    public void setFecha_realizacion(Date fecha_realizacion) { this.fecha_realizacion = fecha_realizacion; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }
}