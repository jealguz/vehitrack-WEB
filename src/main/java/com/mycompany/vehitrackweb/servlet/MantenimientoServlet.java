package com.mycompany.vehitrackweb.servlet;

import com.mycompany.vehitrackweb.dao.MantenimientoDAO;
import com.mycompany.vehitrackweb.models.Mantenimiento;
import com.mycompany.vehitrackweb.models.Usuario;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// @WebServlet define la URL con la que se accede a este Servlet desde el navegador
@WebServlet(name = "MantenimientoServlet", urlPatterns = {"/MantenimientoServlet"})
public class MantenimientoServlet extends HttpServlet {

    // Objeto DAO para acceder a la base de datos
    MantenimientoDAO dao = new MantenimientoDAO();

    // Objeto modelo para transportar los datos del mantenimiento
    Mantenimiento m = new Mantenimiento();

    /**
     * Método central que procesa todas las peticiones GET y POST.
     * Verifica que haya sesión activa antes de ejecutar cualquier acción.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Verificación de seguridad: si no hay sesión activa, redirige al login
        HttpSession session = request.getSession();
        if (session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Se obtiene la acción y el ID del vehículo enviados desde la URL o formulario
        String accion = request.getParameter("accion");
        int idVehiculo = Integer.parseInt(request.getParameter("idVehiculo"));

        switch (accion) {

            // Consulta los mantenimientos del vehículo y los envía al JSP para mostrarlos
            case "listar":
                List<Mantenimiento> lista = dao.listarPorVehiculo(idVehiculo);
                // Se almacena la lista como atributo del request para usarla en el JSP
                request.setAttribute("mantenimientos", lista);
                // Se almacena el ID del vehículo para usarlo en el formulario de registro
                request.setAttribute("idVehiculoActual", idVehiculo);
                request.getRequestDispatcher("listar_mantenimientos.jsp").forward(request, response);
                break;

            // Recibe los datos del formulario y registra un nuevo mantenimiento en la BD
            case "agregar":
                m.setId_vehiculo(idVehiculo);
                // Se convierten las fechas de String a formato Date para la BD
                m.setFecha_programada(Date.valueOf(request.getParameter("txtFechaProg")));
                m.setFecha_realizacion(Date.valueOf(request.getParameter("txtFechaReal")));
                m.setDescripcion(request.getParameter("txtDesc"));
                m.setCosto(Double.parseDouble(request.getParameter("txtCosto")));
                dao.agregar(m);
                // Redirige de vuelta al listado tras registrar
                response.sendRedirect("MantenimientoServlet?accion=listar&idVehiculo=" + idVehiculo);
                break;
        }
    }

    /**
     * Maneja las peticiones HTTP GET.
     * Se usa cuando el usuario accede por URL o hace clic en un enlace.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Maneja las peticiones HTTP POST.
     * Se usa cuando el usuario envía un formulario HTML.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}