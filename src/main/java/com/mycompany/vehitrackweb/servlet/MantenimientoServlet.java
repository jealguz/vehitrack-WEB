package com.mycompany.vehitrackweb.servlet;

import com.mycompany.vehitrackweb.dao.MantenimientoDAO;
import com.mycompany.vehitrackweb.models.Mantenimiento;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet para la gestión de mantenimientos del sistema VehiTrack.
 * Implementa las Historias de Usuario:
 * ID 04: Programar Mantenimiento.
 * ID 06: Registrar Mantenimiento Realizado.
 * ID 11: Generar Historial de Mantenimiento.
 * * Actualizado por: Jeison Guzman
 */
@WebServlet(name = "MantenimientoServlet", urlPatterns = {"/MantenimientoServlet"})
public class MantenimientoServlet extends HttpServlet {

    // Instancia del DAO para interactuar con la base de datos MySQL
    private MantenimientoDAO dao = new MantenimientoDAO();

    /**
     * Procesa las peticiones tanto GET como POST.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Verificación de Sesión: Seguridad para asegurar que el usuario está logueado
        HttpSession session = request.getSession();
        if (session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 2. Captura de parámetros básicos de la petición
        String accion = request.getParameter("accion");
        String idVehiculoStr = request.getParameter("idVehiculo");
        int idVehiculo = (idVehiculoStr != null) ? Integer.parseInt(idVehiculoStr) : 0;

        // 3. Controlador de acciones (Switch)
        switch (accion) {
            
            case "listar":
                // Obtiene la lista de mantenimientos (realizados y programados) del vehículo
                List<Mantenimiento> lista = dao.listarPorVehiculo(idVehiculo);
                
                // Envía los datos al JSP mediante atributos del request
                request.setAttribute("mantenimientos", lista);
                request.setAttribute("idVehiculoActual", idVehiculo);
                
                // Redirecciona a la vista del historial
                request.getRequestDispatcher("listar_mantenimientos.jsp").forward(request, response);
                break;

            case "agregar":
                // Creamos una nueva instancia del modelo para cada registro (Seguridad de hilos)
                Mantenimiento nuevoM = new Mantenimiento();
                nuevoM.setId_vehiculo(idVehiculo);
                
                // A. Procesar Fecha Programada (Obligatoria para la alerta)
                String fProg = request.getParameter("txtFechaProg");
                if (fProg != null && !fProg.isEmpty()) {
                    nuevoM.setFecha_programada(Date.valueOf(fProg));
                }

                // B. Procesar Fecha Realización (OPCIONAL - Historia ID 04)
                // Si viene vacío, se guarda como NULL en la DB, indicando que es una tarea pendiente
                String fReal = request.getParameter("txtFechaReal");
                if (fReal != null && !fReal.isEmpty()) {
                    nuevoM.setFecha_realizacion(Date.valueOf(fReal));
                } else {
                    nuevoM.setFecha_realizacion(null);
                }

                // C. Procesar Kilometraje (Campo numérico según estructura de DB)
                String kmStr = request.getParameter("txtKm");
                if (kmStr != null && !kmStr.isEmpty()) {
                    nuevoM.setKilometraje_mantenimiento(Integer.parseInt(kmStr));
                }

                // D. Procesar Descripción y Costo
                nuevoM.setDescripcion(request.getParameter("txtDesc"));
                String costoStr = request.getParameter("txtCosto");
                nuevoM.setCosto((costoStr != null && !costoStr.isEmpty()) ? Double.parseDouble(costoStr) : 0.0);

                // E. Ejecutar la inserción mediante el DAO
                dao.agregar(nuevoM);
                
                // F. Redirección: Se vuelve al listado para refrescar la tabla
                response.sendRedirect("MantenimientoServlet?accion=listar&idVehiculo=" + idVehiculo);
                break;
                
                case "finalizar":
                String idMantStr = request.getParameter("idMantenimiento");
                if (idMantStr != null) {
                    int idMantenimiento = Integer.parseInt(idMantStr);
                    // Llamamos al método del DAO que pone la fecha de hoy
                    dao.marcarComoRealizado(idMantenimiento);
                }
                
                // Redireccionamos al listado del mismo vehículo para ver el cambio
                response.sendRedirect("MantenimientoServlet?accion=listar&idVehiculo=" + idVehiculo);
                break;
                
            default:
                // En caso de una acción no reconocida, vuelve al inicio
                response.sendRedirect("panel.jsp");
                break;
        }
    }

    /**
     * Maneja solicitudes GET (clics en enlaces y carga de páginas)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Maneja solicitudes POST (envío de formularios)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}