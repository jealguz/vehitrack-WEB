<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Control de Combustible</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h3 class="text-danger">⛽ Registro de Combustible</h3>
            <a href="VehiculoServlet?accion=listar" class="btn btn-outline-primary">Volver a Vehículos</a>
        </div>

        <table class="table table-bordered bg-white">
            <thead class="table-danger text-white">
                <tr>
                    <th>Fecha</th>
                    <th>Cantidad (Gal/Lit)</th>
                    <th>Costo</th>
                    <th>Kilometraje</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="c" items="${combustibles}">
                    <tr>
                        <td>${c.fecha}</td>
                        <td>${c.cantidad}</td>
                        <td class="text-success">$${c.costo}</td>
                        <td>${c.kilometraje} Km</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="card mt-4 p-3 border-danger">
            <form action="CombustibleServlet" method="POST" class="row g-2">
                <input type="hidden" name="accion" value="agregar">
                <input type="hidden" name="idVehiculo" value="${idVehiculoActual}">
                <div class="col-md-3">
                    <label class="small">Fecha</label>
                    <input type="date" name="txtFecha" class="form-control" required>
                </div>
                <div class="col-md-3">
                    <label class="small">Cantidad</label>
                    <input type="number" step="0.01" name="txtCantidad" class="form-control" required>
                </div>
                <div class="col-md-3">
                    <label class="small">Costo Local</label>
                    <input type="number" step="0.01" name="txtCosto" class="form-control" required>
                </div>
                <div class="col-md-3">
                    <label class="small">Kilometraje Actual</label>
                    <input type="number" name="txtKm" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-danger mt-3">Registrar Gasto</button>
            </form>
        </div>
    </div>
</body>
</html>