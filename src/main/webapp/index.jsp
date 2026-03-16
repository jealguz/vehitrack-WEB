<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>VehiTrack - Iniciar Sesión</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <style>
        body, html {
            height: 100%;
            margin: 0;
            font-family: 'Poppins', sans-serif;
            overflow: hidden; /* Evita scroll en la página principal */
        }
        .split-container {
            display: flex;
            height: 100vh;
            width: 100%;
        }
        /* Columna Izquierda: Presentación (50%) */
        .left-side {
            width: 50%; /* Ocupa la mitad de la pantalla */
            background: linear-gradient(rgba(0,0,0,0.8), rgba(0,0,0,0.8)), 
                        url('https://images.unsplash.com/photo-1486006920555-c77dcf18193c?ixlib=rb-4.0.3&auto=format&fit=crop&w=1350&q=80');
            background-size: cover;
            background-position: center;
            color: white;
            display: flex;
            flex-direction: column;
            justify-content: center;
            padding: 10% 8%; /* Ajusta el espaciado interno */
        }
        .logo-text {
            font-size: 2rem;
            font-weight: 600;
            letter-spacing: 2px;
            margin-bottom: 2rem;
            color: #fff;
            text-transform: uppercase;
        }
        /* Columna Derecha: Formulario de Login (50%) */
        .right-side {
            width: 50%; /* Ocupa la otra mitad de la pantalla */
            background-color: white;
            display: flex;
            flex-direction: column;
            justify-content: center;
            padding: 10% 8%; /* Ajusta el espaciado interno */
            box-shadow: -10px 0 20px rgba(0,0,0,0.1);
            z-index: 10;
        }
        .form-control-lg {
            border-radius: 10px;
            font-size: 1rem;
        }
        .btn-lg {
            border-radius: 10px;
        }
        /* Ajustes para pantallas pequeñas (móviles/tablets) */
        @media (max-width: 992px) {
            .split-container {
                flex-direction: column;
                overflow-y: auto;
            }
            .left-side {
                width: 100%;
                padding: 50px 30px;
                height: auto;
                min-height: 40vh;
            }
            .right-side {
                width: 100%;
                padding: 40px 30px;
                height: auto;
            }
        }
    </style>
</head>
<body>

    <div class="split-container">
        
        <div class="left-side">
            <div class="logo-text">VEHITRACK</div>
            
            <h1 class="display-4 fw-bold mb-4">Control Total de tu Vehículo en un solo lugar</h1>
            <p class="lead mb-5 text-white-50">
                La solución integral diseñada para conductores y administradores que buscan optimizar el consumo de combustible, gestionar mantenimientos preventivos y tener reportes claros de su flota o vehículo personal.
            </p>
                
            <div class="row g-4 mt-2">
                <div class="col-md-6">
                    <h5> Combustible</h5>
                    <p class="small text-white-50">Registra cargas, galonaje y costos para calcular eficiencia.</p>
                </div>
                <div class="col-md-6">
                    <h5>mantenimiento</h5>
                    <p class="small text-white-50">Historial de reparaciones y alertas de servicios.</p>
                </div>
            </div>
        </div>

        <div class="right-side">
            
            <div class="text-center mb-5">
                <img src="img/vehitrack_logo.jpg" alt="VehiTrack Logo" class="img-fluid mb-3 d-block mx-auto" style="max-height: 100px; width: auto;">
                <h3 class="fw-bold text-dark">Bienvenido</h3>
                <p class="text-muted">Ingresa tus credenciales para acceder</p>
            </div>
            
                                    <%-- Mostrar mensaje de error si el login falla --%>
                        <% if(request.getParameter("error") != null) { %>
                            <div class="alert alert-danger text-center p-2 small mb-3" role="alert">
                                <strong>Error:</strong> Correo o contraseña incorrectos.
                            </div>
                        <% } %>

                        <%-- Mostrar mensaje si viene de un registro exitoso --%>
                        <% if(request.getParameter("registro") != null) { %>
                            <div class="alert alert-success text-center p-2 small mb-3" role="alert">
                                ¡Registro exitoso! Ya puedes ingresar.
                            </div>
                        <% } %>

             <form action="${pageContext.request.contextPath}/UsuarioServlet" method="POST">
                <input type="hidden" name="accion" value="ingresar">
    
               <div class="mb-3">
               <label class="form-label fw-semibold text-muted">Correo Electrónico</label>
               <input type="email" name="txtEmail" class="form-control form-control-lg bg-light" placeholder="usuario@correo.com" required>
             </div>
    
            <div class="mb-4">
                <label class="form-label fw-semibold text-muted">Contraseña</label>
                <input type="password" name="txtPass" class="form-control form-control-lg bg-light" placeholder="••••••••" required>
            </div>
    
            <div class="d-grid gap-3">
                <button type="submit" class="btn btn-primary btn-lg shadow-sm">Entrar al Sistema</button>
                <a href="${pageContext.request.contextPath}/UsuarioServlet?accion=nuevo" class="btn btn-outline-secondary btn-lg">Crear Cuenta Nueva</a>
            </div>
            </form>
            
            <div class="text-center mt-5 text-muted small">
                <hr>
                Desarrollado por <strong>ADSO SENA</strong> &copy; 2026
            </div>
            
        </div>
        
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>