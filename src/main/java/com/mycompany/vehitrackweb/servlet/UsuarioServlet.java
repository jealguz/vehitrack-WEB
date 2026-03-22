package com.mycompany.vehitrackweb.servlet;

/**
 * Servlet principal para la gestión de usuarios.
 * Maneja las operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * de los usuarios del sistema VehiTrack.
 * 
 * Responde a las peticiones HTTP GET y POST según la acción recibida.
 */

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

// @WebServlet define la URL con la que se accede a este Servlet desde el navegador
@WebServlet(name = "UsuarioServlet", urlPatterns = {"/UsuarioServlet"})
public class UsuarioServlet extends HttpServlet {

    // Objeto DAO para acceder a la base de datos
    UsuarioDAO dao = new UsuarioDAO();
    
    // Objeto modelo para transportar los datos del usuario
    Usuario u = new Usuario();

    /**
     * Método central que procesa todas las peticiones (GET y POST).
     * Recibe el parámetro "accion" del formulario o URL para decidir
     * qué operación ejecutar
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Se obtiene el parámetro "accion" enviado desde el formulario o enlace
        String accion = request.getParameter("accion");
        
        // Si no se recibe ninguna acción, se asume que se quiere listar
        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {

            // Redirige al formulario JSP de registro de nuevo usuario
            case "nuevo":
                request.getRequestDispatcher("registro.jsp").forward(request, response);
                break;

            // Valida las credenciales del usuario en la base de datos.
            // Si son correctas, guarda el usuario en sesión y redirige al panel.
            // Si son incorrectas, redirige al login con un mensaje de error.
            case "ingresar":
                String correo = request.getParameter("txtEmail");
                String clave = request.getParameter("txtPass");
                u = dao.validar(correo, clave);
                
                if (u != null) {
                    // Se crea una sesión HTTP y se almacena el usuario logueado
                    HttpSession sesion = request.getSession();
                    sesion.setAttribute("usuarioLogueado", u);
                    response.sendRedirect("panel.jsp");
                } else {
                    // Credenciales incorrectas: regresa al login con parámetro de error
                    response.sendRedirect("index.jsp?error=1");
                }
                break;

            // Recibe los datos del formulario de registro y los guarda en la BD.
            // Al finalizar, redirige al login con mensaje de éxito.
            case "agregar":
                u.setNombre(request.getParameter("txtNombre"));
                u.setApellido(request.getParameter("txtApellido"));
                u.setEmail(request.getParameter("txtEmail"));
                u.setContraseña(request.getParameter("txtPass"));
                dao.agregar(u);
                response.sendRedirect("index.jsp?registro=exitoso");
                break;

            // Consulta todos los usuarios en la BD y los envía al JSP para mostrarlos
            case "listar":
                List<Usuario> lista = dao.listar();
                // Se almacena la lista como atributo del request para usarla en el JSP
                request.setAttribute("usuarios", lista);
                request.getRequestDispatcher("listar_usuarios.jsp").forward(request, response);
                break;

            // Busca un usuario por su ID y lo envía al formulario de edición
            case "editar":
                int idEdit = Integer.parseInt(request.getParameter("id"));
                Usuario user = dao.listarId(idEdit);
                // Se almacena el usuario encontrado para prellenar el formulario JSP
                request.setAttribute("usuarioEncontrado", user);
                request.getRequestDispatcher("editar_usuario.jsp").forward(request, response);
                break;

            // Recibe los datos modificados del formulario y los actualiza en la BD
            case "actualizar":
                u.setId_usuario(Integer.parseInt(request.getParameter("txtId")));
                u.setNombre(request.getParameter("txtNombre"));
                u.setApellido(request.getParameter("txtApellido"));
                u.setEmail(request.getParameter("txtEmail"));
                u.setContraseña(request.getParameter("txtPass"));
                dao.editar(u);
                // Redirige de vuelta al listado tras actualizar
                response.sendRedirect("UsuarioServlet?accion=listar");
                break;

            // Elimina un usuario de la BD según su ID y regresa al listado
            case "eliminar":
                int idDel = Integer.parseInt(request.getParameter("id"));
                dao.eliminar(idDel);
                response.sendRedirect("UsuarioServlet?accion=listar");
                break;

            // Cierra la sesión del usuario y redirige al login
            case "salir":
                request.getSession().invalidate();
                response.sendRedirect("index.jsp");
                break;

            // Si la acción no coincide con ninguna, redirige al login
            default:
                response.sendRedirect("index.jsp");
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