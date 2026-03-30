package com.mycompany.vehitrackweb.servlet;

import com.mycompany.vehitrackweb.dao.FavoritoDAO;
import com.mycompany.vehitrackweb.models.Favorito;
import com.mycompany.vehitrackweb.models.Usuario;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "FavoritoServlet", urlPatterns = {"/FavoritoServlet"})
public class FavoritoServlet extends HttpServlet {

    FavoritoDAO dao = new FavoritoDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. CONFIGURACIÓN DE ENTRADA: Para procesar tildes y eñes correctamente
        request.setCharacterEncoding("UTF-8");
    
        HttpSession session = request.getSession();
        Usuario userLog = (Usuario) session.getAttribute("usuarioLogueado");

        if (userLog == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "listar":
                List<Favorito> lista = dao.listarPorUsuario(userLog.getId_usuario());
                request.setAttribute("favoritos", lista); 
                session.setAttribute("favoritos", lista); 
                request.getRequestDispatcher("mapa_talleres.jsp").forward(request, response);
                break;

            case "agregar":
                // 2. CONFIGURACIÓN DE SALIDA: Para que el fetch de JS reciba UTF-8
                response.setContentType("text/plain;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                
                try {
                    Favorito f = new Favorito();
                    f.setId_usuario(userLog.getId_usuario());
                    // Los parámetros ahora se leen correctamente gracias al paso 1
                    f.setNombre_taller(request.getParameter("nombre"));
                    f.setLatitud(Double.parseDouble(request.getParameter("lat")));
                    f.setLongitud(Double.parseDouble(request.getParameter("lon")));
                    f.setDireccion(request.getParameter("direccion"));
                    
                    boolean ok = dao.agregar(f);
                    if (ok) {
                        response.getWriter().print("success");
                    } else {
                        response.getWriter().print("error");
                    }
                } catch (Exception e) {
                    response.getWriter().print("Error: " + e.getMessage());
                }
                break;

            case "eliminar":
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    dao.eliminar(id);
                    response.sendRedirect("FavoritoServlet?accion=listar");
                } catch (Exception e) {
                    response.sendRedirect("FavoritoServlet?accion=listar&error=1");
                }
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