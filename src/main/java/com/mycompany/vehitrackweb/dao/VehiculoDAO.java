package com.mycompany.vehitrackweb.dao;

import com.mycompany.vehitrackweb.conection.conectionDB;
import com.mycompany.vehitrackweb.models.Vehiculo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase VehiculoDAO: Implementa las operaciones de base de datos
 * para la tabla 'vehiculo'. Permite listar, agregar y eliminar vehículos
 * asociados a un usuario específico.
 * @author Jeison Guzman
 */
public class VehiculoDAO {

    // Variables JDBC para manejar la conexión y las consultas
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    /**
     * 1. LISTAR POR USUARIO: Obtiene solo los vehículos del usuario logueado.
     * Usa el id_usuario como filtro para no mostrar vehículos de otros usuarios.
     * @param idUsuario ID del usuario activo en sesión
     * @return Lista de vehículos pertenecientes al usuario
     */
    public List<Vehiculo> listarPorUsuario(int idUsuario) {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM vehiculo WHERE id_usuario = ?";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            // Se pasa el ID del usuario como parámetro para filtrar la consulta
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();
            // Se recorre cada fila del resultado y se crea un objeto Vehiculo
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

    /**
     * 2. AGREGAR: Registra un nuevo vehículo vinculado a un usuario.
     * @param v Objeto Vehiculo con los datos del formulario
     * @return 1 si se insertó correctamente, 0 si hubo error
     */
    public int agregar(Vehiculo v) {
        String sql = "INSERT INTO vehiculo (id_usuario, tipo, marca, modelo, placa) VALUES (?, ?, ?, ?, ?)";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            // Se asignan los valores recibidos del formulario JSP
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

    /**
     * 3. ELIMINAR: Borra un vehículo de la BD según su ID.
     * @param id ID del vehículo a eliminar
     */
    public void eliminar(int id) {
        String sql = "DELETE FROM vehiculo WHERE id_vehiculo = ?";
        try {
            con = conectionDB.getConexion();
            ps = con.prepareStatement(sql);
            // Se pasa el ID del vehículo como parámetro para identificar el registro
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar vehículo: " + e.getMessage());
        }
    }
}