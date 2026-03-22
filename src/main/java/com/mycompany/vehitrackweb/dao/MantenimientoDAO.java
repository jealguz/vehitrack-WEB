package com.mycompany.vehitrackweb.dao;

import com.mycompany.vehitrackweb.conection.conectionDB;
import com.mycompany.vehitrackweb.models.Mantenimiento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para gestionar las operaciones de persistencia de datos
 * relacionadas con los mantenimientos de los vehículos.
 */
public class MantenimientoDAO {
    // Objetos para la gestión de la base de datos
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    /**
     * Consulta y devuelve una lista de todos los mantenimientos de un vehículo.
     * @param idVehiculo ID del vehículo a consultar.
     * @return List de objetos Mantenimiento encontrados.
     */
    public List<Mantenimiento> listarPorVehiculo(int idVehiculo) {
        List<Mantenimiento> lista = new ArrayList<>();
        // Consulta para filtrar mantenimientos por el ID del vehículo
        String sql = "SELECT * FROM mantenimiento WHERE id_vehiculo = ?";
        try {
            // Obtención de conexión y preparación de la sentencia
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idVehiculo);
            rs = ps.executeQuery();
            
            // Mapeo de cada fila de la tabla al modelo Mantenimiento
            while (rs.next()) {
                Mantenimiento m = new Mantenimiento();
                m.setId_mantenimiento(rs.getInt("id_mantenimiento"));
                m.setId_vehiculo(rs.getInt("id_vehiculo"));
                m.setFecha_programada(rs.getDate("fecha_programada"));
                m.setFecha_realizacion(rs.getDate("fecha_realizacion"));
                m.setDescripcion(rs.getString("descripcion"));
                m.setCosto(rs.getDouble("costo"));
                lista.add(m); // Agregado a la colección de retorno
            }
        } catch (SQLException e) {
            // Impresión de error en caso de fallo en la consulta SQL
            System.err.println("Error en DAO Mantenimiento: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Inserta un nuevo registro de mantenimiento en la base de datos.
     * @param m Objeto Mantenimiento con la información a guardar.
     * @return 1 si la operación fue exitosa, 0 si ocurrió un error.
     */
    public int agregar(Mantenimiento m) {
        // Sentencia SQL de inserción
        String sql = "INSERT INTO mantenimiento (id_vehiculo, fecha_programada, fecha_realizacion, descripcion, costo) VALUES (?, ?, ?, ?, ?)";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            
            // Asignación de parámetros desde el objeto modelo
            ps.setInt(1, m.getId_vehiculo());
            ps.setDate(2, m.getFecha_programada());
            ps.setDate(3, m.getFecha_realizacion());
            ps.setString(4, m.getDescripcion());
            ps.setDouble(5, m.getCosto());
            
            // Ejecución de la actualización
            ps.executeUpdate();
        } catch (SQLException e) {
            // Retorno de 0 en caso de excepción (error en la DB)
            return 0;
        }
        return 1; // Retorno de éxito
    }
}