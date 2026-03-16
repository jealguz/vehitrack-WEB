package com.mycompany.vehitrackweb.conection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase conectionDB: Gestiona el enlace técnico entre la aplicación Java y MySQL.
 * Configurada para la base de datos de gestión de vehículos 'vehitrack_db'.
 * * @author Jeison Guzman
 */
public class conectionDB {
    
    // Configuración de los parámetros de conexión
    // IMPORTANTE: Cambiamos 'sistema_prueva2' por 'vehitrack_db'
    private static final String URL = "jdbc:mysql://localhost:3306/vehitrack_db?useSSL=false&serverTimezone=UTC";
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
            // Esto le dice a Java cómo hablar con el motor de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 2. Intentamos establecer el puente con los credenciales definidos
            con = DriverManager.getConnection(URL, USER, PASS);
            
            // Mensaje de control en la consola de NetBeans
            System.out.println("¡Conexión exitosa a VehiTrack DB!");
            
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