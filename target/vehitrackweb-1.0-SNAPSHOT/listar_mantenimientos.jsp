<%-- 
    Página: listar_mantenimientos.jsp
    Descripción: Muestra el historial de mantenimientos de un vehículo específico
    e incluye un formulario para registrar nuevos mantenimientos. Recibe la lista
    desde el MantenimientoServlet mediante el atributo "mantenimientos".
    Autor: Yulian Gamboa
--%>
<%-- Importación de la librería JSTL core --%>
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

        <%-- Tabla que muestra el historial de mantenimientos del vehículo --%>
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
                <%-- c:forEach recorre la lista "mantenimientos" enviada desde el Servlet --%>
                <c:forEach var="m" items="${mantenimientos}">
                    <tr>
                        <%-- Cada ${} imprime el valor del atributo del objeto mantenimiento --%>
                        <td>${m.fecha_programada}</td>
                        <td>${m.fecha_realizacion}</td>
                        <td>${m.descripcion}</td>
                        <td class="fw-bold text-success">$${m.costo}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <%-- Formulario para registrar un nuevo mantenimiento --%>
        <div class="card mt-4 p-3">
            <h5>Registrar Mantenimiento</h5>
            <form action="MantenimientoServlet" method="POST" class="row g-3">
                <%-- Campo oculto que indica al Servlet qué acción ejecutar --%>
                <input type="hidden" name="accion" value="agregar">
                <%-- Campo oculto que identifica el vehículo al que pertenece el mantenimiento --%>
                <input type="hidden" name="idVehiculo" value="${idVehiculoActual}">
                <div class="col-md-3">
                    <%-- Fecha en que se programó el mantenimiento --%>
                    <input type="date" name="txtFechaProg" class="form-control" required>
                </div>
                <div class="col-md-3">
                    <%-- Fecha en que se realizó el mantenimiento --%>
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