package com.mycompany.vehitrackweb.servlet;

import com.mycompany.vehitrackweb.dao.VehiculoDAO;
import com.mycompany.vehitrackweb.dao.MantenimientoDAO; // Agregado: Import para el DAO correcto
import com.mycompany.vehitrackweb.models.Mantenimiento;
import com.mycompany.vehitrackweb.models.Usuario;
import com.mycompany.vehitrackweb.models.Vehiculo;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "VehiculoServlet", urlPatterns = {"/VehiculoServlet"})
public class VehiculoServlet extends HttpServlet {

    VehiculoDAO dao = new VehiculoDAO();
    MantenimientoDAO mDao = new MantenimientoDAO(); // Agregado: Instancia del DAO de mantenimientos

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario userLog = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (userLog == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // --- LÓGICA GLOBAL DE ALERTAS LEGALES (SOAT/RTM) ---
        List<Vehiculo> alertasGlobal = dao.obtenerNotificaciones(userLog.getId_usuario());
        long hoyMillis = System.currentTimeMillis();
        
        for (Vehiculo veh : alertasGlobal) {
            if (veh.getVencimiento_soat() != null) {
                long diasSoat = (veh.getVencimiento_soat().getTime() - hoyMillis) / (1000 * 60 * 60 * 24);
                if (diasSoat < 0) veh.setEstadoSoat("VENCIDO");
                else if (diasSoat <= 15) veh.setEstadoSoat("PRÓXIMO");
            }
            if (veh.getVencimiento_rtm() != null) {
                long diasRtm = (veh.getVencimiento_rtm().getTime() - hoyMillis) / (1000 * 60 * 60 * 24);
                if (diasRtm < 0) veh.setEstadoRtm("VENCIDO");
                else if (diasRtm <= 15) veh.setEstadoRtm("PRÓXIMO");
            }
        }

        // --- AJUSTE MAESTRO PARA MANTENIMIENTOS ---
        // Usamos el mDao para traer las alertas con PLACA que ajustamos antes
        List<Mantenimiento> listaMantenimientos = mDao.obtenerAlertasPendientes(userLog.getId_usuario());

        // Guardamos en SESSION con los nombres exactos que usa el JSP
        session.setAttribute("alertasDocumentos", alertasGlobal);
        session.setAttribute("alertasMantenimiento", listaMantenimientos); 
        
        // Calculamos el total para el contador del Sidebar
        int total = listaMantenimientos.size();
        for(Vehiculo v : alertasGlobal) {
            if(v.getEstadoSoat() != null) total++;
            if(v.getEstadoRtm() != null) total++;
        }
        session.setAttribute("totalAlertas", total);
        // ----------------------------------------------

        String accion = request.getParameter("accion");
        if (accion == null) {
            request.getRequestDispatcher("panel.jsp").forward(request, response);
            return;
        }

        switch (accion) {
            case "listar":
                List<Vehiculo> lista = dao.listarPorUsuario(userLog.getId_usuario());
                request.setAttribute("vehiculos", lista);
                request.getRequestDispatcher("listar_vehiculos.jsp").forward(request, response);
                break;

            case "agregar":
                Vehiculo v = new Vehiculo();
                v.setId_usuario(userLog.getId_usuario());
                v.setTipo(request.getParameter("txtTipo"));
                v.setMarca(request.getParameter("txtMarca"));
                v.setModelo(request.getParameter("txtModelo"));
                v.setPlaca(request.getParameter("txtPlaca"));
                v.setAnio(Integer.parseInt(request.getParameter("txtAnio")));
                v.setKilometraje_actual(Integer.parseInt(request.getParameter("txtKilometraje")));
                v.setVencimiento_soat(Date.valueOf(request.getParameter("txtFechaSoat")));
                v.setVencimiento_rtm(Date.valueOf(request.getParameter("txtFechaRtm")));
                
                dao.agregar(v);
                response.sendRedirect("VehiculoServlet?accion=listar");
                break;

            case "eliminar":
                int id = Integer.parseInt(request.getParameter("id"));
                dao.eliminar(id);
                response.sendRedirect("VehiculoServlet?accion=listar");
                break;
                
            case "notificaciones":
                // Antes de ir a notificaciones.jsp, refrescamos las listas de la sesión
                List<Mantenimiento> frescosMantenimientos = mDao.obtenerAlertasPendientes(userLog.getId_usuario());
                session.setAttribute("alertasMantenimiento", frescosMantenimientos);
                
                request.getRequestDispatcher("notificaciones.jsp").forward(request, response);
                break;
                
            default:
                request.getRequestDispatcher("panel.jsp").forward(request, response);
        }
    }

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
}