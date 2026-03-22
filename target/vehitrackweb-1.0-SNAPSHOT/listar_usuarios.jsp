<%-- 
    Página: listar_usuarios.jsp
    Descripción: Muestra la tabla con todos los usuarios registrados en VehiTrack.
    Recibe la lista de usuarios desde el UsuarioServlet mediante el atributo "usuarios".
    Utiliza JSTL (c:forEach) para recorrer e imprimir la lista dinámicamente.
    Autor: Tania Quezada
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%-- Importación de la librería JSTL core para usar etiquetas como c:forEach --%>
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
                        </tr>
                    </thead>
                    <tbody>
                        <%-- c:forEach recorre la lista "usuarios" enviada desde el Servlet --%>
                        <c:forEach var="user" items="${usuarios}">
                            <tr>
                                <%-- Cada ${} imprime el valor del atributo del objeto usuario --%>
                                <td>${user.id_usuario}</td>
                                <td>${user.nombre}</td>
                                <td>${user.apellido}</td>
                                <td>${user.email}</td>
                                <td>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <%-- Botón para regresar al panel principal --%>
                <a href="panel.jsp" class="btn btn-secondary">Volver al Panel</a>
            </div>
        </div>
    </div>
</body>
</html>