package com.mycompany.vehitrackweb.dao;

import com.mycompany.vehitrackweb.conection.conectionDB;
import com.mycompany.vehitrackweb.models.Vehiculo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase VehiculoDAO: Gestiona la persistencia de la tabla 'vehiculo'.
 * Implementa HU ID 05 (Kilometraje) e ID 07 (Alertas combinadas).
 * @author Jeison Guzman
 */
public class VehiculoDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    /**
     * 1. LISTAR POR USUARIO: Obtiene los vehículos del usuario activo.
     * @param idUsuario
     * @return 
     */
    public List<Vehiculo> listarPorUsuario(int idUsuario) {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM vehiculo WHERE id_usuario = ?";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();
            while (rs.next()) {
                Vehiculo v = new Vehiculo();
                v.setId_vehiculo(rs.getInt("id_vehiculo"));
                v.setId_usuario(rs.getInt("id_usuario"));
                v.setTipo(rs.getString("tipo"));
                v.setMarca(rs.getString("marca"));
                v.setModelo(rs.getString("modelo"));
                v.setAnio(rs.getInt("anio"));
                v.setPlaca(rs.getString("placa"));
                v.setKilometraje_actual(rs.getInt("kilometraje_actual"));
                v.setVencimiento_soat(rs.getDate("vencimiento_soat"));
                v.setVencimiento_rtm(rs.getDate("vencimiento_rtm"));
                lista.add(v);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar vehículos: " + e.getMessage());
        }
        return lista;
    }

    /**
     * 2. AGREGAR: Registro inicial del vehículo.
     * @param v
     * @return 
     */
    public int agregar(Vehiculo v) {
        String sql = "INSERT INTO vehiculo (id_usuario, tipo, marca, modelo, anio, placa, "
                   + "kilometraje_actual, vencimiento_soat, vencimiento_rtm) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, v.getId_usuario());
            ps.setString(2, v.getTipo());
            ps.setString(3, v.getMarca());
            ps.setString(4, v.getModelo());
            ps.setInt(5, v.getAnio());
            ps.setString(6, v.getPlaca());
            ps.setInt(7, v.getKilometraje_actual());
            ps.setDate(8, v.getVencimiento_soat());
            ps.setDate(9, v.getVencimiento_rtm());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al agregar vehículo: " + e.getMessage());
            return 0;
        }
    }

    /**
     * 3. ACTUALIZAR KILOMETRAJE (HU ID 05): 
     * Permite al usuario actualizar el recorrido actual de su vehículo.
     * @param idVehiculo
     * @param nuevoKm
     * @return 
     */
    public int actualizarKilometraje(int idVehiculo, int nuevoKm) {
        String sql = "UPDATE vehiculo SET kilometraje_actual = ? WHERE id_vehiculo = ?";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(ps.toString()); // Reutilizando conexión
            ps = con.prepareStatement(sql);
            ps.setInt(1, nuevoKm);
            ps.setInt(2, idVehiculo);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar KM: " + e.getMessage());
            return 0;
        }
    }

    /**
     * 4. ELIMINAR: Borra un registro por su ID.
     * @param id
     */
    public void eliminar(int id) {
        String sql = "DELETE FROM vehiculo WHERE id_vehiculo = ?";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar: " + e.getMessage());
        }
    }
    
    /**
     * 5. CONSULTAR NOTIFICACIONES (HU ID 07 e ID 09):
     * Busca alertas por fechas legales (SOAT/RTM) Y por kilometraje de mantenimiento.
     * @param idUsuario
     * @return 
     */
    public List<Vehiculo> obtenerNotificaciones(int idUsuario) {
        List<Vehiculo> listaAlertas = new ArrayList<>();
        // SQL mejorado: Trae el vehículo si sus documentos vencen pronto 
        // O si el kilometraje actual ya superó o igualó algún mantenimiento programado.
        String sql = "SELECT DISTINCT v.* FROM vehiculo v "
                   + "LEFT JOIN mantenimiento m ON v.id_vehiculo = m.id_vehiculo "
                   + "WHERE v.id_usuario = ? AND ("
                   + "v.vencimiento_soat <= DATE_ADD(CURDATE(), INTERVAL 15 DAY) OR "
                   + "v.vencimiento_rtm <= DATE_ADD(CURDATE(), INTERVAL 15 DAY) OR "
                   + "(m.fecha_realizacion IS NULL AND v.kilometraje_actual >= m.kilometraje_mantenimiento))";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();
            while (rs.next()) {
                Vehiculo v = new Vehiculo();
                v.setId_vehiculo(rs.getInt("id_vehiculo"));
                v.setPlaca(rs.getString("placa"));
                v.setMarca(rs.getString("marca"));
                v.setModelo(rs.getString("modelo"));
                v.setVencimiento_soat(rs.getDate("vencimiento_soat"));
                v.setVencimiento_rtm(rs.getDate("vencimiento_rtm"));
                v.setKilometraje_actual(rs.getInt("kilometraje_actual"));
                listaAlertas.add(v);
            }
        } catch (SQLException e) {
            System.err.println("Error en notificaciones unificadas: " + e.getMessage());
        }
        return listaAlertas;
    }
    
    /**
 * 6. OBTENER MANTENIMIENTOS PENDIENTES:
 * Trae la lista de tareas de taller que están marcadas como pendientes.
     * @param idUsuario
     * @return 
 */
public List<com.mycompany.vehitrackweb.models.Mantenimiento> obtenerMantenimientosPendientes(int idUsuario) {
    List lista = new ArrayList<>();
    // SQL que une la tabla vehiculo con mantenimiento para saber de qué placa es cada tarea
    String sql = "SELECT m.*, v.placa FROM mantenimiento m "
           + "JOIN vehiculo v ON m.id_vehiculo = v.id_vehiculo "
           + "WHERE v.id_usuario = ? AND (m.fecha_realizacion IS NULL OR m.fecha_realizacion = '')";
    
    try {
        con = conectionDB.getConexion();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idUsuario);
        rs = ps.executeQuery();
        
        while (rs.next()) {
            // Creamos un objeto genérico o de tu modelo Mantenimiento
            com.mycompany.vehitrackweb.models.Mantenimiento m = new com.mycompany.vehitrackweb.models.Mantenimiento();
            m.setId_mantenimiento(rs.getInt("id_mantenimiento"));
            m.setId_vehiculo(rs.getInt("id_vehiculo"));
            m.setPlaca(rs.getString("placa")); // Necesitamos la placa para la tarjeta
            m.setDescripcion(rs.getString("descripcion"));
            m.setFecha_programada(rs.getDate("fecha_programacion"));
            m.setKilometraje_mantenimiento(rs.getInt("kilometraje_mantenimiento"));
            lista.add(m);
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener mantenimientos pendientes: " + e.getMessage());
    }
    return lista;
}
}