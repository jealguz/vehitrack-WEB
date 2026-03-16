package com.mycompany.vehitrackweb.dao;

import com.mycompany.vehitrackweb.conection.conectionDB;
import com.mycompany.vehitrackweb.models.Vehiculo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // Listar solo los vehículos del usuario que inició sesión
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
                v.setPlaca(rs.getString("placa"));
                lista.add(v);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar vehículos: " + e.getMessage());
        }
        return lista;
    }

    // Agregar nuevo vehículo
    public int agregar(Vehiculo v) {
        String sql = "INSERT INTO vehiculo (id_usuario, tipo, marca, modelo, placa) VALUES (?, ?, ?, ?, ?)";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, v.getId_usuario());
            ps.setString(2, v.getTipo());
            ps.setString(3, v.getMarca());
            ps.setString(4, v.getModelo());
            ps.setString(5, v.getPlaca());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al agregar vehículo: " + e.getMessage());
            return 0;
        }
        return 1;
    }

    // Eliminar vehículo
    public void eliminar(int id) {
        String sql = "DELETE FROM vehiculo WHERE id_vehiculo = ?";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar vehículo: " + e.getMessage());
        }
    }
}