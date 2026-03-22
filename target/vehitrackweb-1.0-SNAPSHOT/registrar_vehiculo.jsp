<%-- 
    Página: registrar_vehiculo.jsp
    Descripción: Formulario para registrar un nuevo vehículo vinculado al usuario
    activo en sesión. Envía los datos mediante POST al VehiculoServlet con la acción "agregar".
    Autor: Jeison Guzman
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%-- Importación de la librería JSTL core --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    // Verificamos que el usuario esté logueado para obtener su ID
    if (session.getAttribute("usuarioLogueado") == null) {
        response.sendRedirect("index.jsp");
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>VehiTrack - Registrar Vehículo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Poppins', sans-serif; background-color: #f4f7f6; }
        .card-register { border-radius: 15px; border: none; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }
        .btn-custom { border-radius: 10px; padding: 10px 20px; }
    </style>
</head>
<body class="p-3 p-md-5">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card card-register p-4">
                    <div class="text-center mb-4">
                        <img src="img/vehitrack_logo.jpg" alt="Logo" style="max-height: 70px;">
                        <h3 class="fw-bold mt-3">Nuevo Vehículo</h3>
                        <p class="text-muted">Ingresa los datos de tu vehículo</p>
                    </div>

                    <%-- Formulario que envía los datos al VehiculoServlet mediante POST --%>
                    <form action="${pageContext.request.contextPath}/VehiculoServlet" method="POST">
                        <%-- Campo oculto que indica al Servlet qué acción ejecutar --%>
                        <input type="hidden" name="accion" value="agregar">
                        <%-- Campo oculto que envía el ID del usuario logueado para vincular el vehículo --%>
                        <input type="hidden" name="txtIdUsuario" value="${usuarioLogueado.id_usuario}">

                        <div class="mb-3">
                            <label class="form-label fw-semibold">Tipo de Vehículo</label>
                            <%-- Lista desplegable con los tipos de vehículo disponibles --%>
                            <select name="txtTipo" class="form-select" required>
                                <option value="" disabled selected>Selecciona tipo...</option>
                                <option value="Carro">Carro</option>
                                <option value="Moto">Moto</option>
                                <option value="Camión">Camión</option>
                            </select>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label fw-semibold">Marca</label>
                                <input type="text" name="txtMarca" class="form-control" placeholder="Ej: Toyota" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label fw-semibold">Modelo</label>
                                <input type="text" name="txtModelo" class="form-control" placeholder="Ej: Corolla" required>
                            </div>
                        </div>
                        <div class="mb-4">
                            <label class="form-label fw-semibold">Placa / Patente</label>
                            <input type="text" name="txtPlaca" class="form-control" placeholder="ABC-123" required>
                        </div>
                        <div class="d-grid gap-2">
                            <%-- Botón de envío del formulario --%>
                            <button type="submit" class="btn btn-primary btn-custom shadow-sm">Guardar Vehículo</button>
                            <%-- Enlace para cancelar y volver al panel --%>
                            <a href="panel.jsp" class="btn btn-light btn-custom">Cancelar</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>