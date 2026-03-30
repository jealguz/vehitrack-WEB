<%-- 
    Página: mapa_talleres.jsp
    Descripción: Localización de talleres mediante GPS y gestión de favoritos.
    Cumple con: Historia de Usuario ID 13 e Historia de Favoritos.
    Autor: Jeison Guzman
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%
    // Forzar codificación en la sesión y respuesta
    request.setCharacterEncoding("UTF-8");
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
    <title>VehiTrack - Mapa de Talleres</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" />

    <style>
        body { font-family: 'Roboto', sans-serif; background-color: #f4f7f6; }
        .sidebar { background-color: #0a2351; min-height: 100vh; color: white; box-shadow: 2px 0 5px rgba(0,0,0,0.1); }
        .logo-panel { max-width: 150px; }
        .map-container { background: white; border-radius: 15px; padding: 15px; box-shadow: 0 4px 12px rgba(0,0,0,0.05); }
        #map { height: 600px; width: 100%; border-radius: 10px; z-index: 1; }
        .nav-link:hover { background-color: rgba(255,255,255,0.1); border-radius: 5px; }
        .btn-cerrar { color: #6c757d; background-color: transparent; border-color: #6c757d; transition: all 0.3s; }
        .btn-cerrar:hover { color: #fff; background-color: #dc3545; border-color: #dc3545; }
        .fav-card { border-left: 4px solid #ffc107; }
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
                <li class="nav-item mb-2"><a class="nav-link text-white" href="panel.jsp"> Inicio</a></li>
                <li class="nav-item mb-2"><a class="nav-link text-white" href="UsuarioServlet?accion=listar">Usuarios</a></li>
                <li class="nav-item mb-2"><a class="nav-link text-white" href="VehiculoServlet?accion=listar">Vehículos</a></li>
                <li class="nav-item mb-2"><a class="nav-link text-white fw-bold active" href="FavoritoServlet?accion=listar">Mapa de Talleres 📍</a></li>
                <li class="nav-item mb-2"><a class="nav-link text-white" href="VehiculoServlet?accion=notificaciones">Notificaciones 🔔</a></li>
                <li class="nav-item mt-4"><a class="btn btn-cerrar btn-sm w-100" href="index.jsp">Cerrar Sesión</a></li>
            </ul>
        </nav>

        <main class="col-md-10 ms-sm-auto px-md-4">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">📍 Red de Talleres Aliados</h1>
                <div class="btn-group">
                    <button class="btn btn-sm btn-outline-primary" onclick="window.location.href='FavoritoServlet?accion=listar';">⭐ Ver Mis Favoritos</button>
                    <button class="btn btn-sm btn-outline-secondary" onclick="window.location.reload();">🔄 Mi Ubicación</button>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-9 mb-4">
                    <div class="map-container">
                        <div id="map"></div>
                    </div>
                </div>

                <div class="col-lg-3">
                    <div class="card shadow-sm mb-4">
                        <div class="card-header bg-primary text-white fw-bold">Filtros de Búsqueda</div>
                        <div class="card-body">
                            <label class="small fw-bold mb-1">Radio de Distancia</label>
                            <select class="form-select form-select-sm mb-3" id="distancia">
                                <option value="1">1 km</option>
                                <option value="5">5 km</option>
                                <option value="10" selected>10 km</option>
                                <option value="20">20 km</option>
                            </select>
                            
                            <label class="small fw-bold mb-1">Especialidad</label>
                            <select class="form-select form-select-sm" id="especialidad">
                                <option value="todos">Todos los servicios</option>
                                <option value="mecanica">Mecánica General</option>
                                <option value="electrico">Sistema Eléctrico</option>
                            </select>
                        </div>
                    </div>

                    <div class="card shadow-sm fav-card">
                        <div class="card-header bg-warning text-dark fw-bold">⭐ Mis Talleres Favoritos</div>
                        <ul class="list-group list-group-flush" id="listaFavoritos">
                            <c:forEach items="${favoritos}" var="fav">
                                <li class="list-group-item d-flex justify-content-between align-items-center">
                                    <div class="text-truncate" style="max-width: 80%;">
                                        <span class="small fw-bold d-block">${fav.nombre_taller}</span>
                                        <span class="text-muted" style="font-size: 0.7rem;">${fav.direccion}</span>
                                    </div>
                                    <button class="btn btn-link btn-sm text-danger p-0" onclick="eliminarFav(${fav.id_favorito})">🗑️</button>
                                </li>
                            </c:forEach>

                            <c:if test="${empty favoritos}">
                                <li class="list-group-item text-muted small py-3 text-center">
                                    <p class="mb-1">Sin favoritos cargados.</p>
                                    <a href="FavoritoServlet?accion=listar" class="btn btn-xs btn-link p-0">Sincronizar ahora</a>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>

<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
<script>
    var map = L.map('map').setView([4.6097, -74.0817], 12);
    var circuloRadio;
    var latActual, lonActual;

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors'
    }).addTo(map);

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            latActual = position.coords.latitude;
            lonActual = position.coords.longitude;
            map.setView([latActual, lonActual], 14);
            L.marker([latActual, lonActual]).addTo(map).bindPopup('<b>Estás aquí</b>').openPopup();
            actualizarRadio();

            // Talleres de prueba (Guavatá y alrededores)
            agregarTaller(latActual + 0.003, lonActual + 0.002, "MotoWorks Pro - Sede Norte", "5.0", "Inyección Electrónica");
            agregarTaller(latActual - 0.004, lonActual - 0.003, "Mecánica Eléctrica Guavatá", "4.8", "Sistemas Eléctricos");
            agregarTaller(latActual + 0.006, lonActual - 0.005, "Serviteca JeisMotos", "4.9", "Cambio de Aceite");
            agregarTaller(latActual - 0.002, lonActual + 0.007, "Taller El Piñón de Oro", "4.5", "Motores y Cajas");
            agregarTaller(latActual + 0.008, lonActual + 0.001, "Centro de Frenos Barbosa", "4.7", "Frenos ABS");
        });
    }

    function actualizarRadio() {
        var kms = document.getElementById('distancia').value;
        var metros = kms * 1000;
        if (circuloRadio) { circuloRadio.setRadius(metros); } 
        else {
            circuloRadio = L.circle([latActual, lonActual], {
                radius: metros, color: '#0a2351', fillColor: '#3388ff', fillOpacity: 0.15
            }).addTo(map);
        }
        map.fitBounds(circuloRadio.getBounds());
    }

    document.getElementById('distancia').addEventListener('change', actualizarRadio);

    function agregarTaller(lat, lon, nombre, ranking, especialidad) {
        var vLat = lat || 0;
        var vLon = lon || 0;
        var vNombre = nombre ? nombre.replace(/'/g, "\\'").replace(/"/g, "") : "Taller";
        var vEsp = especialidad ? especialidad.replace(/'/g, "\\'").replace(/"/g, "") : "General";

        var marker = L.marker([vLat, vLon]).addTo(map);
        
        var popupHTML = '<div class="text-center" style="min-width: 160px; padding: 5px;">' +
                        '<h6 class="fw-bold mb-1 text-primary">' + vNombre + '</h6>' +
                        '<p class="small mb-1"><b>Servicio:</b> ' + vEsp + '</p>' +
                        '<div class="mb-2"><span class="badge bg-success">' + ranking + ' ⭐</span></div>' +
                        '<button type="button" class="btn btn-warning btn-sm fw-bold w-100 shadow-sm" ' +
                        'onclick="confirmarGuardado(\'' + vNombre + '\',' + vLat + ',' + vLon + ',\'' + vEsp + '\')">' +
                        '⭐ Guardar Favorito</button>' +
                        '</div>';

        marker.bindPopup(popupHTML);
    }

    function confirmarGuardado(nombre, lat, lon, especialidad) {
        if (confirm("¿Seguro quieres guardar '" + nombre + "' en tus favoritos?")) {
            ejecutarGuardado(nombre, lat, lon, especialidad);
        }
    }

    function ejecutarGuardado(nombre, lat, lon, especialidad) {
        const params = "accion=agregar&nombre=" + encodeURIComponent(nombre) + 
                       "&lat=" + lat + 
                       "&lon=" + lon + 
                       "&direccion=" + encodeURIComponent(especialidad);

        fetch('FavoritoServlet', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: params
        })
        .then(response => response.text())
        .then(data => {
            if (data.trim() === "success") {
                // Redirección para refrescar el listado con el encoding correcto
                window.location.href = "FavoritoServlet?accion=listar";
            } else {
                alert("⚠️ Error: " + data);
            }
        })
        .catch(err => alert("⚠️ Error de conexión."));
    }

    function eliminarFav(id) {
        if(confirm("¿Quitar de favoritos?")) {
            window.location.href = "FavoritoServlet?accion=eliminar&id=" + id;
        }
    }
</script>
</body>
</html>