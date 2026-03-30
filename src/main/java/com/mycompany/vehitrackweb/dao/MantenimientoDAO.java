package com.mycompany.vehitrackweb.dao;

import com.mycompany.vehitrackweb.conection.conectionDB;
import com.mycompany.vehitrackweb.models.Mantenimiento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MantenimientoDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // --- MÉTODO NUEVO PARA CAMBIO DE ESTADO (HU ID 09) ---
    public boolean marcarComoRealizado(int idMantenimiento) {
        // CURDATE() en MySQL pone la fecha actual automáticamente
        String sql = "UPDATE mantenimiento SET fecha_realizacion = CURDATE() WHERE id_mantenimiento = ?";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idMantenimiento);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al marcar como realizado: " + e.getMessage());
            return false;
        }
    }

    public List<Mantenimiento> listarPorVehiculo(int idVehiculo) {
        List<Mantenimiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM mantenimiento WHERE id_vehiculo = ? ORDER BY fecha_programada DESC";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idVehiculo);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Mantenimiento m = new Mantenimiento();
                m.setId_mantenimiento(rs.getInt("id_mantenimiento"));
                m.setId_vehiculo(rs.getInt("id_vehiculo"));
                m.setFecha_programada(rs.getDate("fecha_programada"));
                m.setFecha_realizacion(rs.getDate("fecha_realizacion"));
                m.setDescripcion(rs.getString("descripcion"));
                m.setCosto(rs.getDouble("costo"));
                m.setKilometraje_mantenimiento(rs.getInt("kilometraje_mantenimiento")); 
                lista.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Error en listarPorVehiculo: " + e.getMessage());
        }
        return lista;
    }

    public int agregar(Mantenimiento m) {
        String sql = "INSERT INTO mantenimiento (id_vehiculo, fecha_programada, fecha_realizacion, descripcion, costo, kilometraje_mantenimiento) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            
            ps.setInt(1, m.getId_vehiculo());
            ps.setDate(2, m.getFecha_programada());
            
            if (m.getFecha_realizacion() != null) {
                ps.setDate(3, m.getFecha_realizacion());
            } else {
                ps.setNull(3, java.sql.Types.DATE);
            }
            
            ps.setString(4, m.getDescripcion());
            ps.setDouble(5, m.getCosto());
            ps.setInt(6, m.getKilometraje_mantenimiento());
            
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al agregar mantenimiento: " + e.getMessage());
            return 0;
        }
    }

    public List<Mantenimiento> obtenerAlertasPendientes(int idUsuario) {
        List<Mantenimiento> alertas = new ArrayList<>();
        String sql = "SELECT m.*, v.placa FROM mantenimiento m " +
                     "JOIN vehiculo v ON m.id_vehiculo = v.id_vehiculo " +
                     "WHERE v.id_usuario = ? AND m.fecha_realizacion IS NULL " + 
                     "ORDER BY m.fecha_programada ASC";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Mantenimiento m = new Mantenimiento();
                m.setId_mantenimiento(rs.getInt("id_mantenimiento"));
                m.setId_vehiculo(rs.getInt("id_vehiculo"));
                m.setPlaca(rs.getString("placa")); 
                m.setDescripcion(rs.getString("descripcion"));
                m.setFecha_programada(rs.getDate("fecha_programada"));
                m.setCosto(rs.getDouble("costo"));
                m.setKilometraje_mantenimiento(rs.getInt("kilometraje_mantenimiento"));
                alertas.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Error en obtenerAlertasPendientes: " + e.getMessage());
        }
        return alertas;
    }
}