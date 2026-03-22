<%-- 
    Página: listar_combustible.jsp
    Descripción: Muestra el historial de combustible de un vehículo específico
    e incluye un formulario para registrar nuevos gastos. Recibe la lista desde
    el CombustibleServlet mediante el atributo "combustibles".
    Autor: Tania Quezada
--%>
<%-- Importación de la librería JSTL core --%>
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

        <%-- Tabla que muestra el historial de combustible del vehículo --%>
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
                <%-- c:forEach recorre la lista "combustibles" enviada desde el Servlet --%>
                <c:forEach var="c" items="${combustibles}">
                    <tr>
                        <%-- Cada ${} imprime el valor del atributo del objeto combustible --%>
                        <td>${c.fecha}</td>
                        <td>${c.cantidad}</td>
                        <td class="text-success">$${c.costo}</td>
                        <td>${c.kilometraje} Km</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <%-- Formulario para registrar un nuevo gasto de combustible --%>
        <div class="card mt-4 p-3 border-danger">
            <form action="CombustibleServlet" method="POST" class="row g-2">
                <%-- Campo oculto que indica al Servlet qué acción ejecutar --%>
                <input type="hidden" name="accion" value="agregar">
                <%-- Campo oculto que identifica el vehículo al que pertenece el gasto --%>
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