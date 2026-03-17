# VehiTrack-Web 

 - Módulos de software codificados y probados.

#  Descripción
VehiTrack es una solución integral diseñada para conductores y administradores que buscan optimizar el control
 de sus vehículos. Este módulo web permite la gestión de usuarios y vehículos, el seguimiento de consumo de combustible y el historial de mantenimientos preventivos.

#  Tecnologías Utilizadas
 Lenguaje Java 17+
 Tecnología Web Java Server Pages (JSP) y Servlets.
 Base de Datos MySQL (Conexión JDBC).
 Frontend Bootstrap 5 (Responsive Design) y Google Fonts.
 Herramientas NetBeans IDE, Maven y Git para versionamiento.

#  Funcionalidades Implementadas
 Autenticación Sistema de Login con validación de credenciales desde base de datos.
 Gestión de Usuarios CRUD completo para la administración de perfiles.
 Gestión de Vehículos Registro de vehículos (Placa, Marca, Modelo, Tipo) vinculado al usuario mediante ventanas Modales.
 Control Operativo Estructura para el seguimiento de galonaje de combustible y mantenimientos.

#  Estructura del Proyecto
 `srcmainjava` Contiene los Servlets (Controladores), los Modelos (POJO) y el DAO (Acceso a Datos).
 `srcmainwebapp` Contiene las vistas JSP y recursos estáticos (CSSImágenes).
 `WEB-INF` Configuración del despliegue.

#  Retos Técnicos y Soluciones (Bitácora de Desarrollo)

Durante la codificación de este módulo, se enfrentaron y resolvieron los siguientes puntos críticos:

1. *Gestión de Sesiones y Seguridad:
   * Problema: El acceso a las páginas internas (como el Panel) era posible sin loguearse.
   * Solución: Se implementaron validaciones de sesión en la cabecera de los archivos JSP, redirigiendo al `index.jsp` si el objeto `usuarioLogueado` es nulo.

2. *Manejo de Errores en la Autenticación:
   * Problema: El sistema no informaba al usuario por qué fallaba el inicio de sesión.
   * Solución: Se configuró una redirección mediante parámetros en la URL (`index.jsp?error=1`) y se usó código Java/JSP para mostrar alertas dinámicas de Bootstrap en pantalla.

3. *Flujo de Usuario Mejorado (Ventanas Modales):
   * Problema: La navegación se volvía tediosa al tener que cambiar de página para registros cortos.
   * Solución: Se integraron componentes de **Bootstrap 5 (Modales)** para el registro de vehículos, permitiendo que el usuario permanezca en la lista mientras agrega nuevos datos, optimizando la experiencia de usuario (UX).

4. *Persistencia de Datos (JDBC):
   * Problema: Sincronización entre el modelo de objetos en Java y la base de datos MySQL.
   * Solución: Se refinó el patrón **DAO (Data Access Object)** para asegurar que los registros de vehículos queden vinculados correctamente mediante el ID del usuario en sesión.

##  Autores
Jeison Alvin Guzman Londoño
Yulian Didier Gamboa Sanabria
Tania Yisela Quezada Ramos


Estudiante de ADSO (SENA) VEHITRAK 2026