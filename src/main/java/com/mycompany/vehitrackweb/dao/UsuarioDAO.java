package com.mycompany.vehitrackweb.dao;

import com.mycompany.vehitrackweb.conection.conectionDB;
import com.mycompany.vehitrackweb.models.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase UsuarioDAO: Implementa el CRUD y la validación para la tabla 'usuario'.
 * @author Jeison Guzman
 */
public class UsuarioDAO {

    // Variables de JDBC para la manipulación de datos
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    /**
     * 1. VALIDAR (Login): Comprueba credenciales en la DB.
     */
    public Usuario validar(String email, String pass) {
        Usuario u = null;
        String sql = "SELECT * FROM usuario WHERE email=? AND contraseña=?";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            if (rs.next()) {
                u = new Usuario();
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setEmail(rs.getString("email"));
                u.setContraseña(rs.getString("contraseña"));
            }
        } catch (SQLException e) {
            System.err.println("Error en Login: " + e.getMessage());
        }
        return u;
    }

    /**
     * 2. LISTAR: Obtiene todos los usuarios de VehiTrack.
     */
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setEmail(rs.getString("email"));
                u.setContraseña(rs.getString("contraseña"));
                lista.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }

    /**
     * 3. AGREGAR: Registra un nuevo usuario.
     */
    public int agregar(Usuario u) {
        String sql = "INSERT INTO usuario (nombre, apellido, email, contraseña) VALUES (?, ?, ?, ?)";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getContraseña());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al agregar: " + e.getMessage());
            return 0;
        }
        return 1;
    }

    /**
     * 4. LISTAR POR ID: Busca un usuario específico para cargar el formulario de edición.
     */
    public Usuario listarId(int id) {
        Usuario u = new Usuario();
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setEmail(rs.getString("email"));
                u.setContraseña(rs.getString("contraseña"));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar por ID: " + e.getMessage());
        }
        return u;
    }

    /**
     * 5. EDITAR: Actualiza los datos de un usuario existente.
     */
    public int editar(Usuario u) {
        String sql = "UPDATE usuario SET nombre=?, apellido=?, email=?, contraseña=? WHERE id_usuario=?";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getContraseña());
            ps.setInt(5, u.getId_usuario());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al editar: " + e.getMessage());
            return 0;
        }
        return 1;
    }

    /**
     * 6. ELIMINAR: Borra un registro basado en el ID.
     */
    public void eliminar(int id) {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar: " + e.getMessage());
        }
    }
}