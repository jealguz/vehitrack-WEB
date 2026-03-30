<%-- 
    Página: listar_usuarios.jsp
    Descripción: Gestión de usuarios con diseño de panel y cambio de contraseña seguro.
    Autor: Jeison Guzman
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
    <title>VehiTrack - Usuarios</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Roboto', sans-serif; background-color: #f4f7f6; }
        .sidebar { background-color: #0a2351; min-height: 100vh; color: white; box-shadow: 2px 0 5px rgba(0,0,0,0.1); }
        .logo-panel { max-width: 150px; }
        .table-card { background: white; border-radius: 15px; border: none; box-shadow: 0 4px 12px rgba(0,0,0,0.05); }
        .btn-cerrar { color: #6c757d; background-color: transparent; border-color: #6c757d; transition: all 0.3s ease; }
        .btn-cerrar:hover { color: #fff; background-color: #dc3545; border-color: #dc3545; }
        .nav-link:hover { background: rgba(255,255,255,0.1); border-radius: 5px; }
    </style>
</head>
<body>

<div class="container-fluid">
    <div class="row">
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

        <main class="col-md-10 ms-sm-auto px-md-4">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Gestión de Usuarios</h1>
                <div class="btn-toolbar mb-2 mb-md-0">
                    <span class="badge bg-primary p-2">Usuario: ${usuarioLogueado.nombre} ${usuarioLogueado.apellido}</span>
                </div>
            </div>

            <div class="table-card p-4 border-start border-primary border-5 mt-4">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h4 class="text-dark fw-bold mb-0">Listado de Usuarios</h4>
                </div>

                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>Nombre Completo</th>
                                <th>Correo Electrónico</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="u" items="${usuarios}">
                                <tr>
                                    <td><strong>${u.id_usuario}</strong></td>
                                    <td>${u.nombre} ${u.apellido}</td>
                                    <td>${u.email}</td>
                                    <td class="text-center">
                                        <button class="btn btn-primary btn-sm px-3 shadow-sm"
                                                onclick="llenarModal('${u.id_usuario}', '${u.nombre}', '${u.apellido}', '${u.email}')"
                                                data-bs-toggle="modal" data-bs-target="#modalEditar">
                                            Editar
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </main>
    </div>
</div>

<%-- Modal de Edición con Seguridad --%>
<div class="modal fade" id="modalEditar" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content border-0 shadow-lg" style="border-radius: 15px;">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title fw-bold"> Editar Usuario</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <form action="UsuarioServlet" method="POST">
                <div class="modal-body p-4">
                    <input type="hidden" name="accion" value="actualizar">
                    <input type="hidden" name="txtId" id="mid">
                    
                    <div class="row mb-3">
                        <div class="col">
                            <label class="form-label fw-bold">Nombre</label>
                            <input type="text" name="txtNombre" id="mnombre" class="form-control" required>
                        </div>
                        <div class="col">
                            <label class="form-label fw-bold">Apellido</label>
                            <input type="text" name="txtApellido" id="mapellido" class="form-control" required>
                        </div>
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label fw-bold">Email</label>
                        <input type="email" name="txtEmail" id="memail" class="form-control" required>
                    </div>

                    <div class="bg-light p-3 rounded border border-warning mt-4">
                        <p class="small text-dark fw-bold mb-2">Validación de Seguridad</p>
                        
                        <div class="mb-3">
                            <label class="form-label small fw-bold text-danger">Contraseña Actual *</label>
                            <input type="password" name="txtPassActual" id="passActual" class="form-control form-control-sm" placeholder="Requerida para guardar cambios">
                            <div id="aviso_pass" class="form-text text-danger d-none small">⚠️ Debes ingresar tu clave actual.</div>
                        </div>

                        <hr>

                        <div class="mb-2">
                            <label class="form-label small fw-bold text-muted">Nueva Contraseña (Opcional)</label>
                            <input type="password" name="txtPass" id="pass1" class="form-control form-control-sm" placeholder="Dejar vacío si no cambia">
                        </div>
                        <div class="mb-0">
                            <label class="form-label small fw-bold text-muted">Confirmar Nueva Contraseña</label>
                            <input type="password" id="pass2" class="form-control form-control-sm">
                            <div id="error_pass" class="form-text text-danger d-none small mt-1">⚠️ Las nuevas contraseñas no coinciden.</div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer bg-light">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button type="submit" id="btnGuardar" class="btn btn-primary px-4 fw-bold shadow-sm">Guardar Cambios</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    const pActual = document.getElementById('passActual');
    const p1 = document.getElementById('pass1');
    const p2 = document.getElementById('pass2');
    const btn = document.getElementById('btnGuardar');
    const err = document.getElementById('error_pass');
    const aviso = document.getElementById('aviso_pass');

    function llenarModal(id, nom, ape, mail) {
        document.getElementById('mid').value = id;
        document.getElementById('mnombre').value = nom;
        document.getElementById('mapellido').value = ape;
        document.getElementById('memail').value = mail;
        pActual.value = "";
        p1.value = "";
        p2.value = "";
        validarTodo();
    }

    function validarTodo() {
        let esValido = true;

        // Validar que la actual no esté vacía
        if (pActual.value.trim() === "") {
            aviso.classList.remove('d-none');
            esValido = false;
        } else {
            aviso.classList.add('d-none');
        }

        // Validar que las nuevas coincidan si se escriben
        if (p1.value !== "" || p2.value !== "") {
            if (p1.value !== p2.value) {
                err.classList.remove('d-none');
                esValido = false;
            } else {
                err.classList.add('d-none');
            }
        } else {
            err.classList.add('d-none');
        }

        btn.disabled = !esValido;
    }

    pActual.addEventListener('input', validarTodo);
    p1.addEventListener('input', validarTodo);
    p2.addEventListener('input', validarTodo);
</script>
</body>
</html>