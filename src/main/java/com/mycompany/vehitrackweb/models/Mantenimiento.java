package com.mycompany.vehitrackweb.models;

import java.sql.Date;

/**
 * Clase de modelo que representa un registro de mantenimiento.
 * Se utiliza para transportar los datos entre la base de datos y la interfaz.
 */
public class Mantenimiento {
    // Atributos privados que corresponden a las columnas de la tabla 'mantenimiento'
    private int id_mantenimiento;   // Identificador único del registro
    private int id_vehiculo;        // Llave foránea que vincula con la tabla vehículo
    private Date fecha_programada;  // Fecha en la que se planeó el mantenimiento
    private Date fecha_realizacion; // Fecha en la que efectivamente se hizo el trabajo
    private String descripcion;     // Detalles del trabajo realizado o por realizar
    private double costo;           // Valor monetario del mantenimiento

    /**
     * Constructor vacío necesario para frameworks de persistencia 
     * e instanciación manual en el DAO.
     */
    public Mantenimiento() {}

    // --- Métodos Accessores (Getters y Setters) ---

    // Gestión de ID Mantenimiento
    public int getId_mantenimiento() { return id_mantenimiento; }
    public void setId_mantenimiento(int id_mantenimiento) { this.id_mantenimiento = id_mantenimiento; }

    // Gestión de ID Vehículo (Relación)
    public int getId_vehiculo() { return id_vehiculo; }
    public void setId_vehiculo(int id_vehiculo) { this.id_vehiculo = id_vehiculo; }

    // Gestión de Fecha Programada
    public Date getFecha_programada() { return fecha_programada; }
    public void setFecha_programada(Date fecha_programada) { this.fecha_programada = fecha_programada; }

    // Gestión de Fecha Realización
    public Date getFecha_realizacion() { return fecha_realizacion; }
    public void setFecha_realizacion(Date fecha_realizacion) { this.fecha_realizacion = fecha_realizacion; }

    // Gestión de Descripción
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    // Gestión de Costo
    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }
}