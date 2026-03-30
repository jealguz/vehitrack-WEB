package com.mycompany.vehitrackweb.conection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase conectionDB: Gestiona el enlace técnico entre la aplicación Java y MySQL.
 * Configurada para la base de datos de gestión de vehículos 'vehitrack_db'.
 * @author Jeison Guzman
 */
public class conectionDB {
    
    // AJUSTE CRÍTICO: Hemos añadido &useUnicode=true&characterEncoding=UTF-8 
    // Esto asegura que "Piñón" no se convierta en "PiÃ±Ã³n" al guardarse.
    private static final String URL = "jdbc:mysql://localhost:3306/vehitrack_db?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root"; 
    private static final String PASS = "1234"; 

    /**
     * Método estático que establece y retorna la conexión.
     * @return Objeto Connection activo o null si falla.
     */
    public static Connection getConexion() {
        Connection con = null;
        try {
            // 1. Cargamos el Driver de MySQL (Conector J)
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 2. Establecemos el puente con los parámetros de codificación incluidos
            con = DriverManager.getConnection(URL, USER, PASS);
            
            System.out.println("¡Conexión exitosa a VehiTrack DB (UTF-8 Activo)!");
            
        } catch (ClassNotFoundException e) {
            System.err.println("Error técnico: Driver de MySQL no detectado.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error de conexión: Verifica si el servidor MySQL está encendido.");
            System.err.println("Mensaje: " + e.getMessage());
        }
        return con;
    }
}