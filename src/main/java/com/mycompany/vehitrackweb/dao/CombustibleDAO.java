package com.mycompany.vehitrackweb.dao;

import com.mycompany.vehitrackweb.conection.conectionDB;
import com.mycompany.vehitrackweb.models.Combustible;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CombustibleDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List<Combustible> listarPorVehiculo(int idVehiculo) {
        List<Combustible> lista = new ArrayList<>();
        String sql = "SELECT * FROM gasto_combustible WHERE id_vehiculo = ? ORDER BY fecha DESC";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idVehiculo);
            rs = ps.executeQuery();
            while (rs.next()) {
                Combustible c = new Combustible();
                c.setId_gasto_combustible(rs.getInt("id_gasto_combustible"));
                c.setId_vehiculo(rs.getInt("id_vehiculo"));
                c.setFecha(rs.getDate("fecha"));
                c.setCantidad(rs.getFloat("cantidad_galones_litros"));
                c.setCosto(rs.getFloat("costo_local"));
                c.setKilometraje(rs.getInt("kilometraje"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error en DAO Combustible: " + e.getMessage());
        }
        return lista;
    }

    public int agregar(Combustible c) {
        String sql = "INSERT INTO gasto_combustible (id_vehiculo, fecha, cantidad_galones_litros, costo_local, kilometraje) VALUES (?, ?, ?, ?, ?)";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getId_vehiculo());
            ps.setDate(2, c.getFecha());
            ps.setFloat(3, c.getCantidad());
            ps.setFloat(4, c.getCosto());
            ps.setInt(5, c.getKilometraje());
            ps.executeUpdate();
            return 1;
        } catch (SQLException e) {
            System.err.println("Error al insertar combustible: " + e.getMessage());
            return 0;
        }
    }
}