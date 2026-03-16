package com.mycompany.vehitrackweb.models;

/**
 * Modelo que representa la tabla 'vehiculo'.
 * @author Jeison Guzman
 */
public class Vehiculo {
    private int id_vehiculo;
    private int id_usuario; // Llave foránea para saber de quién es el carro
    private String tipo;
    private String marca;
    private String modelo;
    private String placa;

    public Vehiculo() {}

    // Getters y Setters
    public int getId_vehiculo() { return id_vehiculo; }
    public void setId_vehiculo(int id_vehiculo) { this.id_vehiculo = id_vehiculo; }

    public int getId_usuario() { return id_usuario; }
    public void setId_usuario(int id_usuario) { this.id_usuario = id_usuario; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
}
