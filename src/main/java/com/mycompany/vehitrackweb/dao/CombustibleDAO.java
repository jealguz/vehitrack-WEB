package com.mycompany.vehitrackweb.dao;

import com.mycompany.vehitrackweb.conection.conectionDB;
import com.mycompany.vehitrackweb.models.Combustible;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para gestionar las operaciones de base de datos
 * relacionadas con el consumo de combustible.
 */
public class CombustibleDAO {
    // Variables para la gestión de la conexión y ejecución de sentencias SQL
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    /**
     * Obtiene una lista de registros de combustible asociados a un vehículo específico.
     * @param idVehiculo Identificador único del vehículo.
     * @return Lista de objetos Combustible ordenados por fecha descendente.
     */
    public List<Combustible> listarPorVehiculo(int idVehiculo) {
        List<Combustible> lista = new ArrayList<>();
        // Consulta SQL para obtener los gastos de combustible de un vehículo
        String sql = "SELECT * FROM gasto_combustible WHERE id_vehiculo = ? ORDER BY fecha DESC";
        
        try {
            // Establecer conexión y preparar la consulta
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idVehiculo); // Asignar el ID del vehículo al parámetro
            rs = ps.executeQuery();
            
            // Recorrer los resultados y mapearlos al modelo Combustible
            while (rs.next()) {
                Combustible c = new Combustible();
                c.setId_gasto_combustible(rs.getInt("id_gasto_combustible"));
                c.setId_vehiculo(rs.getInt("id_vehiculo"));
                c.setFecha(rs.getDate("fecha"));
                c.setCantidad(rs.getFloat("cantidad_galones_litros"));
                c.setCosto(rs.getFloat("costo_local"));
                c.setKilometraje(rs.getInt("kilometraje"));
                lista.add(c); // Agregar objeto a la lista
            }
        } catch (SQLException e) {
            // Registro de errores en consola en caso de fallo en la consulta
            System.err.println("Error en DAO Combustible: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Registra un nuevo gasto de combustible en la base de datos.
     * @param c Objeto Combustible con los datos a insertar.
     * @return 1 si la inserción fue exitosa, 0 en caso de error.
     */
    public int agregar(Combustible c) {
        // Sentencia SQL para la inserción de datos
        String sql = "INSERT INTO gasto_combustible (id_vehiculo, fecha, cantidad_galones_litros, costo_local, kilometraje) VALUES (?, ?, ?, ?, ?)";
        
        try {
            // Establecer conexión y preparar el INSERT
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            
            // Mapeo de atributos del objeto a los parámetros de la consulta SQL
            ps.setInt(1, c.getId_vehiculo());
            ps.setDate(2, c.getFecha());
            ps.setFloat(3, c.getCantidad());
            ps.setFloat(4, c.getCosto());
            ps.setInt(5, c.getKilometraje());
            
            // Ejecutar la actualización en la base de datos
            ps.executeUpdate();
            return 1; // Retorno exitoso
        } catch (SQLException e) {
            // Registro de errores en consola en caso de fallo en la inserción
            System.err.println("Error al insertar combustible: " + e.getMessage());
            return 0; // Retorno con error
        }
    }
}