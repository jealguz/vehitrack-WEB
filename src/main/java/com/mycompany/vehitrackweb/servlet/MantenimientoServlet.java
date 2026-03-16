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

@WebServlet(name = "MantenimientoServlet", urlPatterns = {"/MantenimientoServlet"})
public class MantenimientoServlet extends HttpServlet {
    MantenimientoDAO dao = new MantenimientoDAO();
    Mantenimiento m = new Mantenimiento();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        if (session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String accion = request.getParameter("accion");
        int idVehiculo = Integer.parseInt(request.getParameter("idVehiculo"));

        switch (accion) {
            case "listar":
                List<Mantenimiento> lista = dao.listarPorVehiculo(idVehiculo);
                request.setAttribute("mantenimientos", lista);
                request.setAttribute("idVehiculoActual", idVehiculo);
                request.getRequestDispatcher("listar_mantenimientos.jsp").forward(request, response);
                break;

            case "agregar":
                m.setId_vehiculo(idVehiculo);
                m.setFecha_programada(Date.valueOf(request.getParameter("txtFechaProg")));
                m.setFecha_realizacion(Date.valueOf(request.getParameter("txtFechaReal")));
                m.setDescripcion(request.getParameter("txtDesc"));
                m.setCosto(Double.parseDouble(request.getParameter("txtCosto")));
                
                dao.agregar(m);
                response.sendRedirect("MantenimientoServlet?accion=listar&idVehiculo=" + idVehiculo);
                break;
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