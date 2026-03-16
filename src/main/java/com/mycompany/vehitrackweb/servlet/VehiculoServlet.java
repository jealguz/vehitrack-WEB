package com.mycompany.vehitrackweb.servlet;

import com.mycompany.vehitrackweb.dao.VehiculoDAO;
import com.mycompany.vehitrackweb.models.Usuario;
import com.mycompany.vehitrackweb.models.Vehiculo;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet VehiculoServlet: Controla la lógica de los vehículos vinculados al usuario.
 * @author Jeison Guzman
 */
@WebServlet(name = "VehiculoServlet", urlPatterns = {"/VehiculoServlet"})
public class VehiculoServlet extends HttpServlet {

    // Instancias de acceso a datos
    VehiculoDAO dao = new VehiculoDAO();
    Vehiculo v = new Vehiculo();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Verificación de Seguridad: Recuperamos la sesión
        HttpSession session = request.getSession();
        Usuario userLog = (Usuario) session.getAttribute("usuarioLogueado");
        
        // Si no hay nadie logueado, lo sacamos al login
        if (userLog == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {
            case "listar":
                // Listamos solo lo que le pertenece al ID del usuario en sesión
                List<Vehiculo> lista = dao.listarPorUsuario(userLog.getId_usuario());
                request.setAttribute("vehiculos", lista);
                request.getRequestDispatcher("listar_vehiculos.jsp").forward(request, response);
                break;

            case "agregar":
                // Capturamos datos del formulario y asignamos el dueño actual
                v.setId_usuario(userLog.getId_usuario());
                v.setTipo(request.getParameter("txtTipo"));
                v.setMarca(request.getParameter("txtMarca"));
                v.setModelo(request.getParameter("txtModelo"));
                v.setPlaca(request.getParameter("txtPlaca"));
                
                dao.agregar(v);
                // Volvemos al listado
                response.sendRedirect("VehiculoServlet?accion=listar");
                break;

            case "eliminar":
                int id = Integer.parseInt(request.getParameter("id"));
                dao.eliminar(id);
                response.sendRedirect("VehiculoServlet?accion=listar");
                break;
                
            default:
                response.sendRedirect("VehiculoServlet?accion=listar");
        }
    }

    // Estos métodos son OBLIGATORIOS en un Servlet para que responda a las peticiones
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controlador de Vehículos VehiTrack";
    }
}