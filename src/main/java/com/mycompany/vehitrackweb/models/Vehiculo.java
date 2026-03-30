package com.mycompany.vehitrackweb.models;

import java.sql.Date; // Importante para manejar las fechas de la base de datos MySQL

/**
 * Clase Modelo Vehiculo: Representa la entidad 'vehiculo' de la base de datos vehitrack_db.
 * Esta clase ha sido actualizada para incluir control de documentos y kilometraje.
 * @author Jeison Guzman
 */
public class Vehiculo {

    // Atributos privados: corresponden exactamente a las columnas de la tabla en MySQL
    private int id_vehiculo;       // Identificador único del vehículo (PK)
    private int id_usuario;        // Llave foránea para saber de quién es el vehículo (FK)
    private String tipo;           // Ejemplo: Moto, Carro, Camioneta
    private String marca;          // Ejemplo: Yamaha, Chevrolet, Mazda
    private String modelo;         // Ejemplo: MT-03, Spark, Tracker
    private int anio;              // Año de fabricación del vehículo
    private String placa;          // Placa única de identificación del vehículo
    private int kilometraje_actual; // Kilometraje actual para alertas de mantenimiento
    private Date vencimiento_soat;  // Fecha de vencimiento del seguro SOAT
    private Date vencimiento_rtm;   // Fecha de vencimiento de la Técnico-Mecánica

    /**
     * Constructor vacío: Necesario para que frameworks y servlets puedan
     * instanciar el objeto antes de llenarlo con datos.
     */
    public Vehiculo() {}

    // --- MÉTODOS GETTERS Y SETTERS ---
    // Permiten el acceso controlado a los atributos privados (Encapsulamiento)

    /** @return El ID del vehículo */
    public int getId_vehiculo() { return id_vehiculo; }
    public void setId_vehiculo(int id_vehiculo) { this.id_vehiculo = id_vehiculo; }

    /** @return El ID del propietario (Usuario) */
    public int getId_usuario() { return id_usuario; }
    public void setId_usuario(int id_usuario) { this.id_usuario = id_usuario; }

    /** @return El tipo de vehículo */
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    /** @return La marca del vehículo */
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    /** @return El modelo o línea del vehículo */
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    /** @return El año de fabricación */
    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    /** @return La placa del vehículo */
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    /** @return El kilometraje actual registrado */
    public int getKilometraje_actual() { return kilometraje_actual; }
    public void setKilometraje_actual(int kilometraje_actual) { this.kilometraje_actual = kilometraje_actual; }

    /** @return La fecha de vencimiento del SOAT */
    public Date getVencimiento_soat() { return vencimiento_soat; }
    public void setVencimiento_soat(Date vencimiento_soat) { this.vencimiento_soat = vencimiento_soat; }

    /** @return La fecha de vencimiento de la RTM */
    public Date getVencimiento_rtm() { return vencimiento_rtm; }
    public void setVencimiento_rtm(Date vencimiento_rtm) { this.vencimiento_rtm = vencimiento_rtm; }
    
    // --- ATRIBUTOS ADICIONALES PARA LÓGICA DE ALERTAS (No requieren DB) ---
    private String estadoSoat; 
    private String estadoRtm;

    /** @return El estado calculado del SOAT (VENCIDO/PRÓXIMO) */
    public String getEstadoSoat() { return estadoSoat; }
    public void setEstadoSoat(String estadoSoat) { this.estadoSoat = estadoSoat; }

    /** @return El estado calculado de la RTM (VENCIDO/PRÓXIMO) */
    public String getEstadoRtm() { return estadoRtm; }
    public void setEstadoRtm(String estadoRtm) { this.estadoRtm = estadoRtm; }
}