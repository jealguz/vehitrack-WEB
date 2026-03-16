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

@WebServlet(name = "CombustibleServlet", urlPatterns = {"/CombustibleServlet"})
public class CombustibleServlet extends HttpServlet {
    CombustibleDAO dao = new CombustibleDAO();
    Combustible c = new Combustible();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        // Siempre necesitaremos saber a qué vehículo le cargamos la gasolina
        String idVehiculoStr = request.getParameter("idVehiculo");
        
        if (idVehiculoStr == null) {
            response.sendRedirect("VehiculoServlet?accion=listar");
            return;
        }
        
        int idVehiculo = Integer.parseInt(idVehiculoStr);

        switch (accion) {
            case "listar":
                List<Combustible> lista = dao.listarPorVehiculo(idVehiculo);
                request.setAttribute("combustibles", lista);
                request.setAttribute("idVehiculoActual", idVehiculo);
                request.getRequestDispatcher("listar_combustible.jsp").forward(request, response);
                break;

            case "agregar":
                c.setId_vehiculo(idVehiculo);
                c.setFecha(Date.valueOf(request.getParameter("txtFecha")));
                c.setCantidad(Float.parseFloat(request.getParameter("txtCantidad")));
                c.setCosto(Float.parseFloat(request.getParameter("txtCosto")));
                c.setKilometraje(Integer.parseInt(request.getParameter("txtKm")));
                
                dao.agregar(c);
                response.sendRedirect("CombustibleServlet?accion=listar&idVehiculo=" + idVehiculo);
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
