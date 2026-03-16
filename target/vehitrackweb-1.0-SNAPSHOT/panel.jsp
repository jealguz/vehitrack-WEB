<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%
    // Verificación de seguridad: Si no hay sesión, regresa al login
    if(session.getAttribute("usuarioLogueado") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>VehiTrack - Panel Principal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Roboto', sans-serif; background-color: #f4f7f6; }
        .sidebar { background: #212529; min-height: 100vh; color: white; }
        .card-menu { 
            transition: transform 0.3s, box-shadow 0.3s; 
            border: none; 
            border-radius: 15px; 
            cursor: pointer;
        }
        .card-menu:hover { 
            transform: translateY(-10px); 
            box-shadow: 0 10px 20px rgba(0,0,0,0.15); 
        }
        .navbar-custom { background-color: white; box-shadow: 0 2px 4px rgba(0,0,0,0.05); }
        .logo-panel { max-width: 150px; }
        
        .sidebar { 
    /* Este es un azul marino profundo (Hex: #0a2351) */
    background-color: #0a2351; 
    min-height: 100vh; 
    color: white; 
    box-shadow: 2px 0 5px rgba(0,0,0,0.1); /* Le da profundidad */
}
    </style>
</head>
<body>

<div class="container-fluid">
    <div class="row">
        <nav class="col-md-2 d-none d-md-block sidebar p-4">
            <div class="text-center mb-4">
                <img src="img/vehitrack_logo.jpg" alt="Logo" class="img-fluid logo-panel bg-white p-2 rounded">
            </div>
            <hr>
            <ul class="nav flex-column">
                <li class="nav-item mb-2"><a class="nav-link text-white fw-bold" href="#"> Inicio</a></li>
                <li class="nav-item mb-2"><a class="nav-link text-white" href="UsuarioServlet?accion=listar">Usuarios</a></li>
                <li class="nav-item mb-2"><a class="nav-link text-white" href="VehiculoServlet?accion=listar">Vehículos</a></li>
                <li class="nav-item mt-4"><a class="btn btn-outline-danger btn-sm w-100" href="index.jsp">Cerrar Sesión</a></li>
            </ul>
        </nav>

        <main class="col-md-10 ms-sm-auto px-md-4">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Panel de Control</h1>
                <div class="btn-toolbar mb-2 mb-md-0">
                    <span class="badge bg-primary p-2">Usuario: ${usuarioLogueado.nombre} ${usuarioLogueado.apellido}</span>
                </div>
            </div>

            <div class="row row-cols-1 row-cols-md-3 g-4 mt-2">
                
                <div class="col">
                    <a href="UsuarioServlet?accion=listar" class="text-decoration-none">
                        <div class="card h-100 card-menu shadow-sm border-start border-primary border-5">
                            <div class="card-body text-center py-5">
                                <h1 class="display-4">👥</h1>
                                <h4 class="card-title text-dark fw-bold">Gestión de Usuarios</h4>
                                <p class="card-text text-muted">Administrar perfiles y permisos del sistema.</p>
                            </div>
                        </div>
                    </a>
                </div>

                <div class="col">
                    <a href="VehiculoServlet?accion=listar" class="text-decoration-none">
                        <div class="card h-100 card-menu shadow-sm border-start border-success border-5">
                            <div class="card-body text-center py-5">
                                <h1 class="display-4">🚗</h1>
                                <h4 class="card-title text-dark fw-bold">Mis Vehículos</h4>
                                <p class="card-text text-muted">Control total de tu flota, placas y modelos.</p>
                            </div>
                        </div>
                    </a>
                </div>

                <div class="col">
                    <a href="VehiculoServlet?accion=listar" class="text-decoration-none"> <div class="card h-100 card-menu shadow-sm border-start border-warning border-5">
                            <div class="card-body text-center py-5">
                                <h1 class="display-4">🛠️</h1>
                                <h4 class="card-title text-dark fw-bold">Mantenimiento</h4>
                                <p class="card-text text-muted">Historial de reparaciones y fechas programadas.</p>
                            </div>
                        </div>
                    </a>
                </div>

                <div class="col">
                    <a href="VehiculoServlet?accion=listar" class="text-decoration-none"> <div class="card h-100 card-menu shadow-sm border-start border-danger border-5">
                            <div class="card-body text-center py-5">
                                <h1 class="display-4">⛽</h1>
                                <h4 class="card-title text-dark fw-bold">Combustible</h4>
                                <p class="card-text text-muted">Registro de galonaje, costos y kilometraje.</p>
                            </div>
                        </div>
                    </a>
                </div>

            </div>
        </main>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>