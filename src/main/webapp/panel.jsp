<%-- 
    Página: panel.jsp
    Descripción: Panel principal de VehiTrack con integración de Mapa de Talleres (HU ID 13).
    Autor: Jeison Guzman
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%
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
        .sidebar { background-color: #0a2351; min-height: 100vh; color: white; box-shadow: 2px 0 5px rgba(0,0,0,0.1); }
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
        .logo-panel { max-width: 150px; }
        .btn-cerrar { color: #6c757d; background-color: transparent; border-color: #6c757d; transition: all 0.3s ease; }
        .btn-cerrar:hover { color: #fff; background-color: #dc3545; border-color: #dc3545; }
        
        /* Estilo para la animación de pulso en notificaciones */
        .animate-pulse { animation: pulse 2s infinite; }
        @keyframes pulse {
            0% { transform: scale(1); }
            50% { transform: scale(1.1); }
            100% { transform: scale(1); }
        }
    </style>
</head>
<body>

<%-- Lógica para calcular el total de alertas nuevas --%>
<c:set var="totalAlertas" value="${alertasDocumentos.size() + alertasMantenimiento.size()}" />

<div class="container-fluid">
    <div class="row">
        <%-- Barra lateral de navegación --%>
        <nav class="col-md-2 d-none d-md-block sidebar p-4">
            <div class="text-center mb-4">
                <img src="img/vehitrack_logo.jpg" alt="Logo" class="img-fluid logo-panel bg-white p-2 rounded">
            </div>
            <hr>
            <ul class="nav flex-column">
                <li class="nav-item mb-2"><a class="nav-link text-white fw-bold" href="panel.jsp"> Inicio</a></li>
                <li class="nav-item mb-2"><a class="nav-link text-white" href="UsuarioServlet?accion=listar">Usuarios</a></li>
                <li class="nav-item mb-2"><a class="nav-link text-white" href="VehiculoServlet?accion=listar">Vehículos</a></li>
                <%-- ENLACE MAPA EN SIDEBAR --%>
                <li class="nav-item mb-2"><a class="nav-link text-white" href="mapa_talleres.jsp">Talleres Cercanos 📍</a></li>
                <li class="nav-item mb-2">
                    <a class="nav-link text-white d-flex justify-content-between align-items-center" href="VehiculoServlet?accion=notificaciones">
                        <span>Notificaciones 🔔</span>
                        <c:if test="${totalAlertas > 0}">
                            <span class="badge rounded-pill bg-danger animate-pulse">${totalAlertas}</span>
                        </c:if>
                    </a>
                </li>
                <li class="nav-item mt-4"><a class="btn btn-cerrar btn-sm w-100" href="index.jsp">Cerrar Sesión</a></li>
            </ul>
        </nav>

        <main class="col-md-10 ms-sm-auto px-md-4">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Panel de Control</h1>
                <div class="btn-toolbar mb-2 mb-md-0">
                    <span class="badge bg-primary p-2">Usuario: ${usuarioLogueado.nombre} ${usuarioLogueado.apellido}</span>
                </div>
            </div>
                


            <%-- Banner de Alerta Crítica (Actualizado para detectar Documentos o Mantenimientos) --%>
            <c:if test="${totalAlertas > 0}">
                <div class="alert alert-warning border-0 shadow-sm d-flex align-items-center p-4 mb-4" style="border-left: 5px solid #dc3545 !important;">
                    <div class="flex-shrink-0"><h1 class="mb-0">⚠️</h1></div>
                    <div class="ms-3">
                        <h5 class="alert-heading fw-bold mb-1 text-dark">¡Atención! Tienes tareas pendientes</h5>
                        <p class="mb-0 text-muted">
                            Se han detectado <strong>${totalAlertas} alertas</strong> (vencimientos de ley o mantenimientos) que requieren tu revisión.
                        </p>
                    </div>
                    <div class="ms-auto">
                        <a href="VehiculoServlet?accion=notificaciones" class="btn btn-danger btn-sm px-4 shadow-sm">Ver Detalles</a>
                    </div>
                </div>
            </c:if>

            <div class="row row-cols-1 row-cols-md-3 g-4 mt-2">
                
                <%-- Tarjeta: Mis Vehículos --%>
                <div class="col">
                    <a href="VehiculoServlet?accion=listar" class="text-decoration-none">
                        <div class="card h-100 card-menu shadow-sm border-start border-success border-5">
                            <div class="card-body text-center py-5">
                                <h1 class="display-4">🚗</h1>
                                <h4 class="card-title text-dark fw-bold">Mis Vehículos</h4>
                                <p class="card-text text-muted">Gestión de placas, modelos y documentos.</p>
                            </div>
                        </div>
                    </a>
                </div>

                <%-- TARJETA NUEVA: MAPA DE TALLERES (HU ID 13) --%>
                <div class="col">
                    <a href="mapa_talleres.jsp" class="text-decoration-none">
                        <div class="card h-100 card-menu shadow-sm border-start border-5" style="border-left-color: #6610f2 !important;">
                            <div class="card-body text-center py-5">
                                <h1 class="display-4">📍</h1>
                                <h4 class="card-title text-dark fw-bold">Talleres Cercanos</h4>
                                <p class="card-text text-muted">Localiza servicios mecánicos por GPS y favoritos.</p>
                            </div>
                        </div>
                    </a>
                </div>

                <%-- Tarjeta: Combustible --%>
                <div class="col">
                    <a href="VehiculoServlet?accion=listar" class="text-decoration-none">
                        <div class="card h-100 card-menu shadow-sm border-start border-danger border-5">
                            <div class="card-body text-center py-5">
                                <h1 class="display-4">⛽</h1>
                                <h4 class="card-title text-dark fw-bold">Combustible</h4>
                                <p class="card-text text-muted">Registro de consumos, costos y rendimiento.</p>
                            </div>
                        </div>
                    </a>
                </div>

                <%-- Tarjeta: Mantenimiento --%>
                <div class="col">
                    <a href="VehiculoServlet?accion=listar" class="text-decoration-none">
                        <div class="card h-100 card-menu shadow-sm border-start border-warning border-5">
                            <div class="card-body text-center py-5">
                                <h1 class="display-4">🛠️</h1>
                                <h4 class="card-title text-dark fw-bold">Mantenimiento</h4>
                                <p class="card-text text-muted">Historial de servicios y citas programadas.</p>
                            </div>
                        </div>
                    </a>
                </div>

                <%-- Tarjeta: Notificaciones --%>
                <div class="col">
                    <a href="VehiculoServlet?accion=notificaciones" class="text-decoration-none">
                        <div class="card h-100 card-menu shadow-sm border-start border-info border-5">
                            <div class="card-body text-center py-5">
                                <h1 class="display-4">🔔</h1>
                                <h4 class="card-title text-dark fw-bold">Notificaciones</h4>
                                <p class="card-text text-muted">Alertas preventivas y recordatorios de ley.</p>
                                <c:if test="${totalAlertas > 0}">
                                    <span class="badge bg-danger rounded-pill">Pendientes: ${totalAlertas}</span>
                                </c:if>
                            </div>
                        </div>
                    </a>
                </div>

                <%-- Tarjeta: Gestión de Usuarios --%>
                <div class="col">
                    <a href="UsuarioServlet?accion=listar" class="text-decoration-none">
                        <div class="card h-100 card-menu shadow-sm border-start border-primary border-5">
                            <div class="card-body text-center py-5">
                                <h1 class="display-4">👥</h1>
                                <h4 class="card-title text-dark fw-bold">Usuarios</h4>
                                <p class="card-text text-muted">Administración de perfiles y seguridad.</p>
                            </div>
                        </div>
                    </a>
                </div>

            </div>
        </main>
    </div>
</div>
                
<div class="modal fade" id="modalBienvenida" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content border-0 shadow-lg" style="border-radius: 20px;">
      
      <div class="modal-header text-white" style="border-radius: 20px 20px 0 0; background-color: #0a2351;">
        <h5 class="modal-title" id="exampleModalLabel">¡Acceso Exitoso!</h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>

      <div class="modal-body text-center py-4">
        <h3 class="fw-bold text-dark">¡Hola, <%= request.getParameter("nombre") %>!</h3>
        <p class="text-muted">Bienvenido de nuevo a <strong>VehiTrack</strong>. Todo está listo para que gestiones tu vehículo.</p>
      </div>

      <div class="modal-footer border-0 justify-content-center">
        <button type="button" class="btn px-5 rounded-pill text-white" style="background-color: #0a2351;" data-bs-dismiss="modal">
            Comenzar
        </button>
      </div>
    </div>
  </div>
</div>  
        
        <% if(request.getParameter("mensaje") != null) { %>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        var myModal = new bootstrap.Modal(document.getElementById('modalBienvenida'));
        myModal.show();
    });
</script>
<% } %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>