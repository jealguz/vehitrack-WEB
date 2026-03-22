package com.mycompany.vehitrackweb.servlet;

import com.mycompany.vehitrackweb.dao.CombustibleDAO;
import com.mycompany.vehitrackweb.models.Combustible;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// @WebServlet define la URL con la que se accede a este Servlet desde el navegador
@WebServlet(name = "CombustibleServlet", urlPatterns = {"/CombustibleServlet"})
public class CombustibleServlet extends HttpServlet {
    // Objeto DAO para acceder a la base de datos
    CombustibleDAO dao = new CombustibleDAO();
     // Objeto modelo para transportar los datos del combustible
    Combustible c = new Combustible();
    
    /**
     * Método central que procesa todas las peticiones GET y POST.
     * Verifica que se reciba el ID del vehículo antes de ejecutar cualquier acción.
     */

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         // Se obtiene la acción enviada desde la URL o formulario
        String accion = request.getParameter("accion");
        // Siempre necesitaremos saber a qué vehículo le cargamos la gasolina
        String idVehiculoStr = request.getParameter("idVehiculo");
        // Si no se recibe el ID del vehículo, redirige al listado de vehículos
        if (idVehiculoStr == null) {
            response.sendRedirect("VehiculoServlet?accion=listar");
            return;
        }
        // Se convierte el ID de String a entero para usarlo en las consultas
        int idVehiculo = Integer.parseInt(idVehiculoStr);

        switch (accion) {
             // Consulta los registros de combustible del vehículo y los envía al JSP

            case "listar":
                List<Combustible> lista = dao.listarPorVehiculo(idVehiculo);
                 // Se almacena la lista como atributo del request para usarla en el JSP
                request.setAttribute("combustibles", lista);
                // Se almacena el ID del vehículo para usarlo en el formulario de registro
                request.setAttribute("idVehiculoActual", idVehiculo);
                request.getRequestDispatcher("listar_combustible.jsp").forward(request, response);
                break;
                
                 // Recibe los datos del formulario y registra un nuevo gasto de combustible en la BD
            case "agregar":
                c.setId_vehiculo(idVehiculo);
                // Se convierte la fecha de String a formato Date para la BD
                c.setFecha(Date.valueOf(request.getParameter("txtFecha")));
                c.setCantidad(Float.parseFloat(request.getParameter("txtCantidad")));
                c.setCosto(Float.parseFloat(request.getParameter("txtCosto")));
                c.setKilometraje(Integer.parseInt(request.getParameter("txtKm")));
                
                dao.agregar(c);
                // Redirige de vuelta al listado tras registrar
                response.sendRedirect("CombustibleServlet?accion=listar&idVehiculo=" + idVehiculo);
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
    