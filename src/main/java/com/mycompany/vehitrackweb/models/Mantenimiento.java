package com.mycompany.vehitrackweb.models;

import java.sql.Date;

/**
 * Modelo Mantenimiento: Representa un registro de taller.
 * Incluye soporte para alertas y vinculación con la placa del vehículo.
 * @author Jeison Guzman
 */
public class Mantenimiento {

    private int id_mantenimiento;
    private int id_vehiculo;
    private String placa; // Atributo necesario para las notificaciones
    private Date fecha_programada;
    private Date fecha_realizacion;
    private String descripcion;
    private double costo;
    private int kilometraje_mantenimiento;

    // --- Constructor Vacío ---
    public Mantenimiento() {
    }

    // --- Métodos Accessores (Getters y Setters) ---

    // Gestión de Identificador Único
    public int getId_mantenimiento() { return id_mantenimiento; }
    public void setId_mantenimiento(int id_mantenimiento) { this.id_mantenimiento = id_mantenimiento; }

    // Gestión de Relación con Vehículo
    public int getId_vehiculo() { return id_vehiculo; }
    public void setId_vehiculo(int id_vehiculo) { this.id_vehiculo = id_vehiculo; }

    // Gestión de Placa 
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    // Gestión de Fecha Programada (Alerta)
    public Date getFecha_programada() { return fecha_programada; }
    public void setFecha_programada(Date fecha_programada) { this.fecha_programada = fecha_programada; }

    // Gestión de Fecha de Realización (Historial)
    public Date getFecha_realizacion() { return fecha_realizacion; }
    public void setFecha_realizacion(Date fecha_realizacion) { this.fecha_realizacion = fecha_realizacion; }

    // Gestión de Descripción del Trabajo
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    // Gestión de Costo Económico
    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }

    // Gestión de Kilometraje
    public int getKilometraje_mantenimiento() { return kilometraje_mantenimiento; }
    public void setKilometraje_mantenimiento(int kilometraje_mantenimiento) { 
        this.kilometraje_mantenimiento = kilometraje_mantenimiento; 
    }
}