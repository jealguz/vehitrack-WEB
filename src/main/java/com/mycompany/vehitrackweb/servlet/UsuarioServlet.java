package com.mycompany.vehitrackweb.servlet;

import com.mycompany.vehitrackweb.dao.UsuarioDAO;
import com.mycompany.vehitrackweb.models.Usuario; 
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "UsuarioServlet", urlPatterns = {"/UsuarioServlet"})
public class UsuarioServlet extends HttpServlet {

    UsuarioDAO dao = new UsuarioDAO();
    Usuario u = new Usuario();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {

            case "nuevo":
                request.getRequestDispatcher("registro.jsp").forward(request, response);
                break;

            case "ingresar":
                String correo = request.getParameter("txtEmail");
                String clave = request.getParameter("txtPass");
                u = dao.validar(correo, clave);
                
                if (u != null) {
                    HttpSession sesion = request.getSession();
                    sesion.setAttribute("usuarioLogueado", u);
                    
                    // --- AJUSTE CLAVE AQUÍ ---
                    // Mantenemos el envío a VehiculoServlet, pero agregamos 
                    // los parámetros de mensaje y nombre en la URL.
                    response.sendRedirect("VehiculoServlet?mensaje=bienvenido&nombre=" + u.getNombre()); 
                    // -------------------------
                } else {
                    response.sendRedirect("index.jsp?error=1");
                }
                break;

            case "agregar":
                u.setNombre(request.getParameter("txtNombre"));
                u.setApellido(request.getParameter("txtApellido"));
                u.setEmail(request.getParameter("txtEmail"));
                u.setContraseña(request.getParameter("txtPass"));
                dao.agregar(u);
                response.sendRedirect("index.jsp?registro=exitoso");
                break;

            case "listar":
                List<Usuario> lista = dao.listar();
                request.setAttribute("usuarios", lista);
                request.getRequestDispatcher("listar_usuarios.jsp").forward(request, response);
                break;

            case "editar":
                int idEdit = Integer.parseInt(request.getParameter("id"));
                Usuario user = dao.listarId(idEdit);
                request.setAttribute("usuarioEncontrado", user);
                request.getRequestDispatcher("editar_usuario.jsp").forward(request, response);
                break;

            case "actualizar":
                u.setId_usuario(Integer.parseInt(request.getParameter("txtId")));
                u.setNombre(request.getParameter("txtNombre"));
                u.setApellido(request.getParameter("txtApellido"));
                u.setEmail(request.getParameter("txtEmail"));
                u.setContraseña(request.getParameter("txtPass"));
                dao.editar(u);
                response.sendRedirect("UsuarioServlet?accion=listar");
                break;

            case "eliminar":
                int idDel = Integer.parseInt(request.getParameter("id"));
                dao.eliminar(idDel);
                response.sendRedirect("UsuarioServlet?accion=listar");
                break;

            case "salir":
                request.getSession().invalidate();
                response.sendRedirect("index.jsp");
                break;

            default:
                response.sendRedirect("index.jsp");
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