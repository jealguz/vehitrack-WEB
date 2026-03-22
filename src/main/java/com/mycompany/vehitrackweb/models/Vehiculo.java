package com.mycompany.vehitrackweb.models;

/**
 * Clase Modelo Vehiculo: Representa la entidad 'vehiculo' de la base de datos vehitrack_db.
 * Esta clase sirve para transportar los datos entre la base de datos y la interfaz.
 * @author Yulian Gamboa
 */
public class Vehiculo {

    // Atributos privados: corresponden exactamente a las columnas de la tabla en MySQL
    private int id_vehiculo; // Identificador único del vehículo (PK)
    private int id_usuario;  // Llave foránea para saber de quién es el carro (FK)
    private String tipo;     // Ejemplo: Moto, Carro, Camioneta
    private String marca;    // Ejemplo: Yamaha, Chevrolet, Mazda
    private String modelo;   // Ejemplo: 2024, MT-03, Spark
    private String placa;    // Placa única de identificación del vehículo

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

    /** @return El modelo/año del vehículo */
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    /** @return La placa del vehículo */
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
}