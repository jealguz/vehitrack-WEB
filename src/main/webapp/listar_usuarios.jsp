<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Usuarios - VehiTrack</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <nav class="navbar navbar-dark bg-dark mb-4">
        <div class="container">
            <a class="navbar-brand" href="panel.jsp">VehiTrack / Usuarios</a>
        </div>
    </nav>
    <div class="container">
        <div class="card shadow">
            <div class="card-header bg-white py-3">
                <h5 class="mb-0 fw-bold">Usuarios Registrados</h5>
            </div>
            <div class="card-body">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Apellido</th>
                            <th>Email</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="user" items="${usuarios}">
                            <tr>
                                <td>${user.id_usuario}</td>
                                <td>${user.nombre}</td>
                                <td>${user.apellido}</td>
                                <td>${user.email}</td>
                                <td>
                                    <a href="UsuarioServlet?accion=editar&id=${user.id_usuario}" class="btn btn-sm btn-warning">Editar</a>
                                    <a href="UsuarioServlet?accion=eliminar&id=${user.id_usuario}" class="btn btn-sm btn-danger">Borrar</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <a href="panel.jsp" class="btn btn-secondary">Volver al Panel</a>
            </div>
        </div>
    </div>
</body>
</html>