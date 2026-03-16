<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Historial de Mantenimiento</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="d-flex justify-content-between align-items-center mb-4 border-bottom pb-2">
            <h3>🛠️ Mantenimientos del Vehículo</h3>
            <a href="VehiculoServlet?accion=listar" class="btn btn-outline-primary">Volver a Vehículos</a>
        </div>

        <table class="table table-striped bg-white shadow-sm">
            <thead class="table-warning">
                <tr>
                    <th>Fecha Programada</th>
                    <th>Fecha Realización</th>
                    <th>Descripción</th>
                    <th>Costo</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="m" items="${mantenimientos}">
                    <tr>
                        <td>${m.fecha_programada}</td>
                        <td>${m.fecha_realizacion}</td>
                        <td>${m.descripcion}</td>
                        <td class="fw-bold text-success">$${m.costo}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div class="card mt-4 p-3">
            <h5>Registrar Mantenimiento</h5>
            <form action="MantenimientoServlet" method="POST" class="row g-3">
                <input type="hidden" name="accion" value="agregar">
                <input type="hidden" name="idVehiculo" value="${idVehiculoActual}">
                <div class="col-md-3">
                    <input type="date" name="txtFechaProg" class="form-control" required>
                </div>
                <div class="col-md-3">
                    <input type="date" name="txtFechaReal" class="form-control" required>
                </div>
                <div class="col-md-4">
                    <input type="text" name="txtDesc" class="form-control" placeholder="Descripción del arreglo">
                </div>
                <div class="col-md-2">
                    <input type="number" name="txtCosto" class="form-control" placeholder="Costo">
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-warning">Guardar Mantenimiento</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>