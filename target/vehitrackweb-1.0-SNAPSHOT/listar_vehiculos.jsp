<%-- 
    Página: listar_vehiculos.jsp
    Descripción: Muestra la tabla de vehículos con diseño integrado de Sidebar.
    Actualizado por: Jeison Guzman
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
    <title>VehiTrack - Mis Vehículos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Roboto', sans-serif; background-color: #f4f7f6; }
        .sidebar { background-color: #0a2351; min-height: 100vh; color: white; box-shadow: 2px 0 5px rgba(0,0,0,0.1); }
        .logo-panel { max-width: 150px; }
        .table-container { background: white; border-radius: 15px; padding: 20px; box-shadow: 0 4px 12px rgba(0,0,0,0.05); }
        .btn-cerrar { color: #6c757d; background-color: transparent; border-color: #6c757d; transition: all 0.3s; }
        .btn-cerrar:hover { color: #fff; background-color: #dc3545; border-color: #dc3545; }
        .nav-link:hover { background-color: rgba(255,255,255,0.1); border-radius: 5px; }
    </style>
</head>
<body>

<div class="container-fluid">
    <div class="row">
        <%-- Barra lateral integrada --%>
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
                <h1 class="h2">🚙 Gestión de Vehículos</h1>
                <div class="btn-toolbar mb-2 mb-md-0">
                    <button class="btn btn-success btn-sm" data-bs-toggle="modal" data-bs-target="#modalVehiculo">
                        ➕ Registrar Nuevo
                    </button>
                </div>
            </div>

            <div class="table-container shadow-sm mt-3">
                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead class="table-light">
                            <tr>
                                <th>Placa</th>
                                <th>Marca/Modelo</th>
                                <th>Año</th>
                                <th>KM Actual</th>
                                <th>Vence SOAT</th>
                                <th>Vence RTM</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="v" items="${vehiculos}">
                                <tr>
                                    <td class="fw-bold text-primary">${v.placa}</td>
                                    <td>${v.marca} ${v.modelo}</td>
                                    <td>${v.anio}</td>
                                    <td>${v.kilometraje_actual} KM</td>
                                    <td><span class="badge bg-info text-dark">${v.vencimiento_soat}</span></td>
                                    <td><span class="badge bg-warning text-dark">${v.vencimiento_rtm}</span></td>
                                    <td class="text-center">
                                        <div class="btn-group">
                                            <a href="MantenimientoServlet?accion=listar&idVehiculo=${v.id_vehiculo}" class="btn btn-outline-warning btn-sm" title="Mantenimientos"> mantenimientos 🛠️</a>
                                            <a href="CombustibleServlet?accion=listar&idVehiculo=${v.id_vehiculo}" class="btn btn-outline-danger btn-sm" title="Combustible">combustible ⛽</a>
                                            <a href="VehiculoServlet?accion=eliminar&id=${v.id_vehiculo}" class="btn btn-outline-secondary btn-sm" onclick="return confirm('¿Eliminar vehículo ${v.placa}?')" title="Eliminar">eliminar 🗑️</a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty vehiculos}">
                                <tr>
                                    <td colspan="7" class="text-center py-4 text-muted">No tienes vehículos registrados aún.</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </main>
    </div>
</div>

<%-- El Modal se mantiene igual al final del body --%>
<div class="modal fade" id="modalVehiculo" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content border-0 shadow">
            <div class="modal-header bg-success text-white">
                <h5 class="modal-title">Registrar Nuevo Vehículo</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="VehiculoServlet" method="POST">
                <div class="modal-body p-4">
                    <input type="hidden" name="accion" value="agregar">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label fw-bold">Tipo</label>
                            <select name="txtTipo" class="form-select" required>
                                <option value="Carro">Carro</option>
                                <option value="Moto">Moto</option>
                                <option value="Camion">Camión</option>
                            </select>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label fw-bold">Placa</label>
                            <input type="text" name="txtPlaca" class="form-control" placeholder="ABC-123" required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4 mb-3">
                            <label class="form-label fw-bold">Marca</label>
                            <input type="text" name="txtMarca" class="form-control" required>
                        </div>
                        <div class="col-md-4 mb-3">
                            <label class="form-label fw-bold">Modelo</label>
                            <input type="text" name="txtModelo" class="form-control" required>
                        </div>
                        <div class="col-md-4 mb-3">
                            <label class="form-label fw-bold">Año</label>
                            <input type="number" name="txtAnio" class="form-control" required>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label fw-bold">Kilometraje Actual</label>
                        <input type="number" name="txtKilometraje" class="form-control" required>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label fw-bold text-danger">Vencimiento SOAT</label>
                            <input type="date" name="txtFechaSoat" class="form-control" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label fw-bold text-warning">Vencimiento RTM</label>
                            <input type="date" name="txtFechaRtm" class="form-control" required>
                        </div>
                    </div>
                </div>
                <div class="modal-footer bg-light">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    <button type="submit" class="btn btn-success px-4">Guardar Vehículo</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>