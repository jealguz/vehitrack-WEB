<%-- 
    Página: registro.jsp
    Descripción: Formulario de registro para nuevos usuarios de VehiTrack.
    Envía los datos mediante POST al UsuarioServlet con la acción "agregar".
    Autor: Yulian Gamboa
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>VehiTrack - Registro de Usuario</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { 
            background: linear-gradient(135deg, #fdfdfd 0%, #e0e0e0 100%); 
            min-height: 100vh; 
        }
        .register-card { 
            border-radius: 20px; 
            box-shadow: 0 15px 35px rgba(0,0,0,0.1); 
            border: none;
            width: 100%;
            max-width: 450px;
        }
        .logo-small {
            max-width: 150px; /* Logo más discreto en el registro */
            height: auto;
        }
    </style>
</head>
<body class="d-flex align-items-center justify-content-center p-3">
    <div class="card register-card p-4 bg-white">
        <div class="text-center mb-4">
            <img src="img/vehitrack_logo.jpg" alt="Logo VehiTrack" class="img-fluid logo-small mb-2">
            <h4 class="fw-bold text-dark">Crear Nueva Cuenta</h4>
            <p class="text-muted small">Regístrate para gestionar tu flota de vehículos</p>
        </div>

        <%-- Formulario de registro: envía los datos al UsuarioServlet mediante POST --%>
        <form action="UsuarioServlet" method="POST">
            <%-- Campo oculto que indica al Servlet qué acción ejecutar --%>
            <input type="hidden" name="accion" value="agregar">
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label fw-semibold">Nombre</label>
                    <input type="text" name="txtNombre" class="form-control" placeholder="Ej: Jeison" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label fw-semibold">Apellido</label>
                    <input type="text" name="txtApellido" class="form-control" placeholder="Ej: Guzman" required>
                </div>
            </div>
            <div class="mb-3">
                <label class="form-label fw-semibold">Correo Electrónico</label>
                <div class="input-group">
                    <span class="input-group-text">@</span>
                    <input type="email" name="txtEmail" class="form-control" placeholder="nombre@ejemplo.com" required>
                </div>
            </div>
            <div class="mb-4">
                <label class="form-label fw-semibold">Contraseña</label>
                <input type="password" name="txtPass" class="form-control" placeholder="Cree una clave segura" required>
            </div>
            <div class="d-grid gap-2">
                <%-- Botón de envío del formulario --%>
                <button type="submit" class="btn btn-success btn-lg shadow-sm">Completar Registro</button>
                <%-- Enlace para volver al login si el usuario ya tiene cuenta --%>
                <a href="index.jsp" class="btn btn-link text-decoration-none text-muted">¿Ya tienes cuenta? Iniciar Sesión</a>
            </div>
        </form>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>