package com.mycompany.vehitrackweb.dao;

import com.mycompany.vehitrackweb.conection.conectionDB;
import com.mycompany.vehitrackweb.models.Favorito;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoritoDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List<Favorito> listarPorUsuario(int idUsuario) {
        List<Favorito> lista = new ArrayList<>();
        String sql = "SELECT * FROM favoritos_talleres WHERE id_usuario = ?";
        try {
            // Se usa el método estático tal como en MantenimientoDAO
            con = conectionDB.getConexion(); 
            ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();
            while (rs.next()) {
                Favorito f = new Favorito();
                f.setId_favorito(rs.getInt("id_favorito"));
                f.setId_usuario(rs.getInt("id_usuario"));
                f.setNombre_taller(rs.getString("nombre_taller"));
                f.setLatitud(rs.getDouble("latitud"));
                f.setLongitud(rs.getDouble("longitud"));
                f.setDireccion(rs.getString("direccion"));
                lista.add(f);
            }
        } catch (SQLException e) {
            System.err.println("Error en FavoritoDAO.listar: " + e.getMessage());
        }
        return lista;
    }

    public boolean agregar(Favorito f) {
        String sql = "INSERT INTO favoritos_talleres (id_usuario, nombre_taller, latitud, longitud, direccion) VALUES (?,?,?,?,?)";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, f.getId_usuario());
            ps.setString(2, f.getNombre_taller());
            ps.setDouble(3, f.getLatitud());
            ps.setDouble(4, f.getLongitud());
            ps.setString(5, f.getDireccion());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en FavoritoDAO.agregar: " + e.getMessage());
            return false;
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM favoritos_talleres WHERE id_favorito = ?";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error en FavoritoDAO.eliminar: " + e.getMessage());
        }
    }
}