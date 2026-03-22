<%-- 
    Página: listar_vehiculos.jsp
    Descripción: Muestra la tabla con los vehículos del usuario logueado.
    Recibe la lista desde el VehiculoServlet mediante el atributo "vehiculos".
    Incluye un modal para registrar nuevos vehículos sin salir de la página.
    Autor: Tania Quezada
--%>
<%-- Importación de la librería JSTL core para usar etiquetas como c:forEach y c:if --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>VehiTrack - Mis Vehículos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Poppins', sans-serif; }
        .table img { max-width: 100px; }
    </style>
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2><img src="img/vehitrack_logo.jpg" style="max-width: 120px;"> Mis Vehículos</h2>
            <a href="panel.jsp" class="btn btn-secondary">Volver al Panel</a>
        </div>

        <div class="card shadow-sm">
            <div class="card-body">
                <table class="table table-hover align-middle">
                    <thead class="table-dark">
                        <tr>
                            <th>Placa</th>
                            <th>Marca</th>
                            <th>Modelo</th>
                            <th>Tipo</th>
                            <th>Acciones de Gestión</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%-- c:forEach recorre la lista "vehiculos" enviada desde el Servlet --%>
                        <c:forEach var="v" items="${vehiculos}">
                            <tr>
                                <%-- Cada ${} imprime el valor del atributo del objeto vehiculo --%>
                                <td class="fw-bold text-primary">${v.placa}</td>
                                <td>${v.marca}</td>
                                <td>${v.modelo}</td>
                                <td>${v.tipo}</td>
                                <td>
                                    <%-- Botones de acción para mantenimiento, combustible y eliminar --%>
                                    <a href="MantenimientoServlet?accion=listar&idVehiculo=${v.id_vehiculo}" class="btn btn-warning btn-sm">🛠️ Mant.</a>
                                    <a href="CombustibleServlet?accion=listar&idVehiculo=${v.id_vehiculo}" class="btn btn-danger btn-sm">⛽ Comb.</a>
                                    <a href="VehiculoServlet?accion=eliminar&id=${v.id_vehiculo}" class="btn btn-outline-secondary btn-sm" onclick="return confirm('¿Eliminar vehículo?')">🗑️</a>
                                </td>
                            </tr>
                        </c:forEach>
                        <%-- c:if muestra un mensaje si el usuario no tiene vehículos registrados --%>
                        <c:if test="${empty vehiculos}">
                            <tr>
                                <td colspan="5" class="text-center text-muted">No tienes vehículos registrados aún.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
                <%-- Botón que abre el modal de registro de vehículo --%>
                <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#modalVehiculo">➕ Registrar Nuevo Vehículo</button>
            </div>
        </div>
    </div>

    <%-- Modal Bootstrap para registrar un nuevo vehículo sin salir de la página --%>
    <div class="modal fade" id="modalVehiculo" tabindex="-1" aria-labelledby="modalVehiculoLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-success text-white">
                    <h5 class="modal-title" id="modalVehiculoLabel">Registrar Nuevo Vehículo</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <%-- Formulario dentro del modal que envía los datos al VehiculoServlet --%>
                <form action="VehiculoServlet" method="POST">
                    <div class="modal-body">
                        <%-- Campo oculto que indica al Servlet qué acción ejecutar --%>
                        <input type="hidden" name="accion" value="agregar">
                        <%-- Campo oculto que vincula el vehículo al usuario logueado --%>
                        <input type="hidden" name="txtIdUsuario" value="${usuarioLogueado.id_usuario}">

                        <div class="mb-3">
                            <label class="form-label">Tipo de Vehículo</label>
                            <select name="txtTipo" class="form-select" required>
                                <option value="Carro">Carro</option>
                                <option value="Moto">Moto</option>
                                <option value="Camion">Camión</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Marca</label>
                            <input type="text" name="txtMarca" class="form-control" placeholder="Ej: Honda" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Modelo</label>
                            <input type="text" name="txtModelo" class="form-control" placeholder="Ej: Civic o CB500" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Placa</label>
                            <input type="text" name="txtPlaca" class="form-control" placeholder="ABC-123" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        <button type="submit" class="btn btn-success">Guardar Vehículo</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>