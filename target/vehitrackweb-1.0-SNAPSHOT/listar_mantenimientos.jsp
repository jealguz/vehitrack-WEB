<%-- 
    Página: listar_mantenimientos.jsp
    Descripción: Historial de mantenimientos con soporte para Kilometraje y Exportación PDF.
    Actualizado por: Jeison Guzman para cumplimiento de HU ID 05, 09 y 11.
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
    <title>VehiTrack - Historial de Mantenimiento</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Roboto', sans-serif; background-color: #f4f7f6; }
        .sidebar { background-color: #0a2351; min-height: 100vh; color: white; box-shadow: 2px 0 5px rgba(0,0,0,0.1); }
        .logo-panel { max-width: 150px; }
        .table-container, .form-container { background: white; border-radius: 15px; padding: 20px; box-shadow: 0 4px 12px rgba(0,0,0,0.05); }
        .nav-link:hover { background-color: rgba(255,255,255,0.1); border-radius: 5px; }
        .btn-cerrar { color: #6c757d; background-color: transparent; border-color: #6c757d; transition: all 0.3s; }
        .btn-cerrar:hover { color: #fff; background-color: #dc3545; border-color: #dc3545; }
    </style>
</head>
<body>

<div class="container-fluid">
    <div class="row">
        <%-- Sidebar unificada --%>
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
                <h1 class="h2">🛠️ Historial de Mantenimientos</h1>
                <div class="btn-toolbar mb-2 mb-md-0">
                    <%-- BOTÓN EXPORTAR PDF (HU ID 11) --%>
                    <a href="ExportarServlet?idVehiculo=${idVehiculoActual}&tipo=mantenimiento" class="btn btn-danger btn-sm me-2">
                        📄 Exportar PDF
                    </a>
                    <a href="VehiculoServlet?accion=listar" class="btn btn-outline-primary btn-sm">
                        ⬅️ Volver
                    </a>
                </div>
            </div>

            <div class="row">
                <%-- Tabla de Historial --%>
                <div class="col-lg-8 mb-4">
                    <div class="table-container">
                        <h5 class="fw-bold mb-3 text-secondary">Registros de Taller</h5>
                        <div class="table-responsive">
                            <table class="table table-hover align-middle">
                                <thead class="table-warning">
                                    <tr>
                                        <th>Fecha Prog.</th>
                                        <th>Fecha Real.</th>
                                        <th>Descripción</th>
                                        <th>KM</th>
                                        <th class="text-end">Costo</th>
                                        <th class="text-center">Estado / Acción</th> 
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="m" items="${mantenimientos}">
                                        <tr>
                                            <td>${m.fecha_programada}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty m.fecha_realizacion}">
                                                        <span class="badge bg-success">${m.fecha_realizacion}</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-secondary">-- -- --</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${m.descripcion}</td>
                                            <td>${m.kilometraje_mantenimiento} KM</td>
                                            <td class="fw-bold text-success text-end">$${m.costo}</td>
                                            <td class="text-center">
                                                <c:choose>
                                                    <%-- Si está pendiente, botón para completar --%>
                                                    <c:when test="${empty m.fecha_realizacion}">
                                                        <a href="MantenimientoServlet?accion=finalizar&idMantenimiento=${m.id_mantenimiento}&idVehiculo=${idVehiculoActual}" 
                                                           class="btn btn-success btn-sm fw-bold shadow-sm" 
                                                           onclick="return confirm('¿Confirmas que el mantenimiento se completó hoy?')">
                                                            ✅ Completar
                                                        </a>
                                                    </c:when>
                                                    <%-- Si ya se hizo, solo mostramos el sello de completado --%>
                                                    <c:otherwise>
                                                        <span class="text-success fw-bold small">✨ Finalizado</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <%-- Formulario de Registro --%>
                <div class="col-lg-4">
                    <div class="form-container border-start border-warning border-4">
                        <h5 class="fw-bold mb-3">Nuevo Mantenimiento</h5>
                        <form action="MantenimientoServlet" method="POST">
                            <input type="hidden" name="accion" value="agregar">
                            <input type="hidden" name="idVehiculo" value="${idVehiculoActual}">
                            
                            <div class="mb-3">
                                <label class="form-label small fw-bold">Fecha Programada</label>
                                <input type="date" name="txtFechaProg" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label small fw-bold">Fecha Realización (Opcional)</label>
                                <input type="date" name="txtFechaReal" class="form-control">
                                <small class="text-muted" style="font-size: 0.7rem;">Dejar vacío si es una programación futura.</small>
                            </div>
                            <div class="mb-3">
                                <label class="form-label small fw-bold">Descripción</label>
                                <textarea name="txtDesc" class="form-control" rows="2" placeholder="Ej: Cambio de aceite" required></textarea>
                            </div>
                            <%-- CAMPO KILOMETRAJE (HU ID 05) --%>
                            <div class="mb-3">
                                <label class="form-label small fw-bold">Kilometraje del Servicio</label>
                                <input type="number" name="txtKilometraje" class="form-control" placeholder="Ej: 15000" required>
                            </div>
                            <div class="mb-4">
                                <label class="form-label small fw-bold">Costo del Servicio</label>
                                <div class="input-group">
                                    <span class="input-group-text">$</span>
                                    <input type="number" name="txtCosto" class="form-control" placeholder="0.00" step="0.01">
                                </div>
                            </div>
                            <button type="submit" class="btn btn-warning w-100 fw-bold shadow-sm">Guardar Registro</button>
                        </form>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>