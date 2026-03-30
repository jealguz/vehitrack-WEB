<%-- 
    Página: notificaciones.jsp
    Descripción: Centro de alertas de VehiTrack con persistencia de documentos legales y mantenimientos.
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
    <title>VehiTrack - Centro de Alertas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Roboto', sans-serif; background-color: #f4f7f6; }
        .sidebar { background-color: #0a2351; min-height: 100vh; color: white; box-shadow: 2px 0 5px rgba(0,0,0,0.1); }
        .logo-panel { max-width: 150px; }
        .card-alerta { border: none; border-radius: 15px; transition: transform 0.2s; }
        .vencimiento-item { padding: 10px; border-radius: 10px; background-color: #fff; margin-bottom: 5px; }
        .btn-cerrar { color: #6c757d; background-color: transparent; border-color: #6c757d; transition: all 0.3s ease; }
        .btn-cerrar:hover { color: #fff; background-color: #dc3545; border-color: #dc3545; }
        .badge-vencido { background-color: #dc3545; animation: pulse 2s infinite; }
        @keyframes pulse { 0% { opacity: 1; } 50% { opacity: 0.7; } 100% { opacity: 1; } }
    </style>
</head>
<body>

<div class="container-fluid">
    <div class="row">
        <%-- Barra lateral --%>
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

<%-- Contenido Principal --%>
        <main class="col-md-10 ms-sm-auto px-md-4">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">🔔 Centro de Alertas y Cumplimiento</h1>
                <div class="btn-toolbar mb-2 mb-md-0">
                    <a href="panel.jsp" class="btn btn-outline-secondary btn-sm me-2">🏠 Volver al Panel</a>
                    <span class="badge bg-primary p-2 d-flex align-items-center">
                        Usuario: ${usuarioLogueado.nombre}
                    </span>
                </div>
            </div>

            <p class="text-muted">Gestiona los vencimientos legales y mantenimientos preventivos de tus vehículos.</p>

            <%-- Mensaje de éxito si TODO está al día (Mantenimientos + Documentos) --%>
            <c:if test="${empty alertasMantenimiento && empty alertasDocumentos}">
                <div class="alert alert-success border-0 shadow-sm d-flex align-items-center" role="alert">
                    <span class="fs-4 me-3">✅</span>
                    <div>
                        <strong>¡Todo en orden!</strong> No tienes documentos vencidos ni mantenimientos pendientes para hoy.
                    </div>
                </div>
            </c:if>

            <%-- SECCIÓN 1: DOCUMENTACIÓN LEGAL (SOAT y Tecno) --%>
            <c:if test="${not empty alertasDocumentos}">
                <h4 class="mb-3 text-danger fw-bold">⚠️ Control de Documentos Obligatorios</h4>
                <div class="row mb-4">
                    <c:forEach var="v" items="${alertasDocumentos}">
                        
                        <%-- Alerta de SOAT --%>
                        <c:if test="${v.estadoSoat != null}">
                            <div class="col-md-6 col-lg-4 mb-3">
                                <div class="card card-alerta shadow-sm border-start border-5 ${v.estadoSoat == 'VENCIDO' ? 'border-danger' : 'border-warning'}">
                                    <div class="card-body">
                                        <div class="d-flex justify-content-between align-items-start mb-2">
                                            <div>
                                                <h6 class="fw-bold mb-0">SOAT: ${v.placa}</h6>
                                                <small class="text-muted">${v.marca} ${v.modelo}</small>
                                            </div>
                                            <span class="badge ${v.estadoSoat == 'VENCIDO' ? 'badge-vencido' : 'bg-warning text-dark'}">
                                                ${v.estadoSoat}
                                            </span>
                                        </div>
                                        <div class="p-2 bg-light rounded mb-3">
                                            <small class="text-uppercase text-muted d-block" style="font-size: 0.7rem;">Vencimiento</small>
                                            <span class="fw-bold ${v.estadoSoat == 'VENCIDO' ? 'text-danger' : 'text-dark'}">${v.vencimiento_soat}</span>
                                        </div>
                                        <button class="btn btn-sm ${v.estadoSoat == 'VENCIDO' ? 'btn-danger' : 'btn-outline-dark'} w-100 fw-bold" 
                                                onclick="abrirModalRenovacion('${v.id_vehiculo}', '${v.placa}', 'SOAT')">
                                            🔄 Renovar SOAT
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </c:if>

                        <%-- Alerta de RTM (Tecnomecánica) --%>
                        <c:if test="${v.estadoRtm != null}">
                            <div class="col-md-6 col-lg-4 mb-3">
                                <div class="card card-alerta shadow-sm border-start border-5 ${v.estadoRtm == 'VENCIDO' ? 'border-danger' : 'border-warning'}">
                                    <div class="card-body">
                                        <div class="d-flex justify-content-between align-items-start mb-2">
                                            <div>
                                                <h6 class="fw-bold mb-0">Tecno: ${v.placa}</h6>
                                                <small class="text-muted">${v.marca} ${v.modelo}</small>
                                            </div>
                                            <span class="badge ${v.estadoRtm == 'VENCIDO' ? 'badge-vencido' : 'bg-warning text-dark'}">
                                                ${v.estadoRtm}
                                            </span>
                                        </div>
                                        <div class="p-2 bg-light rounded mb-3">
                                            <small class="text-uppercase text-muted d-block" style="font-size: 0.7rem;">Vencimiento</small>
                                            <span class="fw-bold ${v.estadoRtm == 'VENCIDO' ? 'text-danger' : 'text-dark'}">${v.vencimiento_rtm}</span>
                                        </div>
                                        <button class="btn btn-sm ${v.estadoRtm == 'VENCIDO' ? 'btn-danger' : 'btn-outline-dark'} w-100 fw-bold" 
                                                onclick="abrirModalRenovacion('${v.id_vehiculo}', '${v.placa}', 'RTM')">
                                            🔄 Renovar Tecno
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </c:if>

                    </c:forEach>
                </div>
            </c:if>

<%-- SECCIÓN 2: MANTENIMIENTOS PROGRAMADOS --%>
            <c:if test="${not empty alertasMantenimiento}">
                <h4 class="mb-3 text-primary fw-bold mt-4">🛠️ Mantenimientos Preventivos Pendientes</h4>
                <div class="row">
                    <c:forEach var="m" items="${alertasMantenimiento}">
                        <div class="col-md-6 col-lg-4 mb-4">
                            <div class="card card-alerta shadow-sm h-100 border-start border-5 border-info">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between align-items-start mb-2">
                                        <div>
                                            <h6 class="fw-bold mb-0">Vehículo: ${m.placa}</h6>
                                            <small class="text-muted">Servicio Sugerido</small>
                                        </div>
                                        <span class="badge bg-info text-dark">PENDIENTE</span>
                                    </div>
                                    
                                    <div class="p-2 bg-light rounded mb-2">
                                        <small class="text-uppercase text-muted d-block" style="font-size: 0.7rem;">Tarea</small>
                                        <span class="fw-bold text-dark">${m.descripcion}</span>
                                    </div>

                                    <div class="p-2 bg-light rounded mb-3">
                                        <small class="text-uppercase text-muted d-block" style="font-size: 0.7rem;">Fecha Programada</small>
                                        <span class="fw-bold text-primary">${m.fecha_programada}</span>
                                    </div>

                                    <a href="MantenimientoServlet?accion=listar&idVehiculo=${m.id_vehiculo}" class="btn btn-sm btn-outline-primary w-100 fw-bold">
                                        📍 Gestionar en Taller
                                    </a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
            
            <div class="modal fade" id="modalRenovacion" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg-dark text-white">
                <h5 class="modal-title" id="tituloModal">Renovar Documento</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="VehiculoServlet" method="POST">
                <input type="hidden" name="accion" value="actualizar_fecha_legal">
                <input type="hidden" name="id_vehiculo" id="modal_id_vehiculo">
                <input type="hidden" name="tipo_doc" id="modal_tipo_doc">

                <div class="modal-body">
                    <p id="textoModal">Ingresa la nueva fecha de vencimiento para el vehículo.</p>
                    <div class="mb-3">
                        <label class="form-label fw-bold">Nueva Fecha de Vencimiento:</label>
                        <input type="date" name="nueva_fecha" class="form-control" required 
                               min="<%= new java.sql.Date(System.currentTimeMillis()) %>">
                        <div class="form-text">La alerta desaparecerá automáticamente al guardar una fecha futura.</div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button type="submit" class="btn btn-success">✅ Actualizar y Limpiar Alerta</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    function abrirModalRenovacion(id, placa, tipo) {
        document.getElementById('modal_id_vehiculo').value = id;
        document.getElementById('modal_tipo_doc').value = tipo;
        document.getElementById('tituloModal').innerText = "Renovar " + tipo + " - " + placa;
        document.getElementById('textoModal').innerText = "Estás actualizando el documento de la placa " + placa;
        
        var myModal = new bootstrap.Modal(document.getElementById('modalRenovacion'));
        myModal.show();
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>