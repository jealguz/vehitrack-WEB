package com.mycompany.vehitrackweb.models;

import java.sql.Date;

/**
 * Modelo para la tabla 'gasto_combustible'.
 * Representa la estructura de datos para el registro de consumo de combustible.
 * @author Jeison Guzman
 */
public class Combustible {
    // Atributos que representan las columnas en la base de datos
    private int id_gasto_combustible; // Identificador único del gasto
    private int id_vehiculo;          // Relación con el vehículo (FK)
    private Date fecha;               // Fecha del registro de carga
    private float cantidad;           // Volumen abastecido (galones o litros)
    private float costo;              // Valor total pagado
    private int kilometraje;          // Kilometraje actual al momento de tanquear

    /**
     * Constructor vacío para inicialización de objetos.
     */
    public Combustible() {}

    // --- Métodos Getters y Setters ---

    // Identificador del gasto
    public int getId_gasto_combustible() { return id_gasto_combustible; }
    public void setId_gasto_combustible(int id_gasto_combustible) { this.id_gasto_combustible = id_gasto_combustible; }

    // Identificador del vehículo
    public int getId_vehiculo() { return id_vehiculo; }
    public void setId_vehiculo(int id_vehiculo) { this.id_vehiculo = id_vehiculo; }

    // Fecha del registro
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    // Cantidad (Volumen)
    public float getCantidad() { return cantidad; }
    public void setCantidad(float cantidad) { this.cantidad = cantidad; }

    // Costo del gasto
    public float getCosto() { return costo; }
    public void setCosto(float costo) { this.costo = costo; }

    // Kilometraje del vehículo
    public int getKilometraje() { return kilometraje; }
    public void setKilometraje(int kilometraje) { this.kilometraje = kilometraje; }
}