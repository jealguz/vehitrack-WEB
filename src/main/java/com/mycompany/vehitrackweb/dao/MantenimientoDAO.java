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

    public List<Mantenimiento> listarPorVehiculo(int idVehiculo) {
        List<Mantenimiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM mantenimiento WHERE id_vehiculo = ?";
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
                lista.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Error en DAO Mantenimiento: " + e.getMessage());
        }
        return lista;
    }

    public int agregar(Mantenimiento m) {
        String sql = "INSERT INTO mantenimiento (id_vehiculo, fecha_programada, fecha_realizacion, descripcion, costo) VALUES (?, ?, ?, ?, ?)";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, m.getId_vehiculo());
            ps.setDate(2, m.getFecha_programada());
            ps.setDate(3, m.getFecha_realizacion());
            ps.setString(4, m.getDescripcion());
            ps.setDouble(5, m.getCosto());
            ps.executeUpdate();
        } catch (SQLException e) {
            return 0;
        }
        return 1;
    }
}
