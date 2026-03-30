package com.mycompany.vehitrackweb.servlet;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.mycompany.vehitrackweb.dao.MantenimientoDAO;
import com.mycompany.vehitrackweb.dao.CombustibleDAO; 
import com.mycompany.vehitrackweb.models.Mantenimiento;
import com.mycompany.vehitrackweb.models.Combustible; 
import com.mycompany.vehitrackweb.models.Usuario; 
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet: ExportarServlet
 * Descripción: Genera reportes PDF específicos según el tipo (Mantenimiento o Combustible).
 * Actualizado por: Jeison Guzman
 */
@WebServlet(name = "ExportarServlet", urlPatterns = {"/ExportarServlet"})
public class ExportarServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Verificación de Seguridad
        HttpSession session = request.getSession();
        Usuario usu = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usu == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 2. Captura de parámetros: ID del vehículo y Tipo de Reporte
        String idVehiculoStr = request.getParameter("idVehiculo");
        String tipo = request.getParameter("tipo"); // Puede ser "mantenimiento" o "combustible"
        int idVehiculo = (idVehiculoStr != null && !idVehiculoStr.isEmpty()) ? Integer.parseInt(idVehiculoStr) : 0;

        // 3. Configuración de cabeceras de respuesta según el tipo
        String nombreDoc = (tipo != null && tipo.equals("combustible")) ? "Reporte_Combustible_" : "Reporte_Mantenimiento_";
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + nombreDoc + idVehiculo + ".pdf");

        Document documento = new Document(PageSize.A4);
        
        try {
            PdfWriter.getInstance(documento, response.getOutputStream());
            documento.open();

            // --- ESTILOS DE FUENTES ---
            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.DARK_GRAY);
            Font fontSubtitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, new BaseColor(10, 35, 81));
            Font fontTablaEncabezado = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
            Font fontCuerpo = new Font(Font.FontFamily.HELVETICA, 9);

            // --- ENCABEZADO GENERAL ---
            String tituloTexto = (tipo != null && tipo.equals("combustible")) ? "REPORTE DE COMBUSTIBLE" : "REPORTE DE MANTENIMIENTO";
            Paragraph titulo = new Paragraph("VEHITRACK - " + tituloTexto, fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            
            documento.add(new Paragraph("Generado por: " + usu.getNombre() + " " + usu.getApellido(), fontCuerpo));
            documento.add(new Paragraph("Fecha de reporte: " + new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()), fontCuerpo));
            documento.add(new Paragraph(" ")); 

            // --- LÓGICA DE CONTENIDO ---

            if (tipo != null && tipo.equals("mantenimiento")) {
                // SECCIÓN 1: MANTENIMIENTOS
                documento.add(new Paragraph("1. HISTORIAL DE MANTENIMIENTOS", fontSubtitulo));
                documento.add(new Paragraph(" "));

                PdfPTable tablaM = new PdfPTable(new float[]{2, 2, 4, 2, 2});
                tablaM.setWidthPercentage(100);

                String[] encM = {"F. Programada", "F. Realizada", "Descripción", "Costo", "KM"};
                for (String h : encM) {
                    PdfPCell cell = new PdfPCell(new Phrase(h, fontTablaEncabezado));
                    cell.setBackgroundColor(new BaseColor(220, 53, 69)); // Rojo Institucional
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(5);
                    tablaM.addCell(cell);
                }

                MantenimientoDAO mDao = new MantenimientoDAO();
                List<Mantenimiento> listaM = mDao.listarPorVehiculo(idVehiculo);

                for (Mantenimiento m : listaM) {
                    tablaM.addCell(new Phrase(m.getFecha_programada().toString(), fontCuerpo));
                    tablaM.addCell(new Phrase(m.getFecha_realizacion() != null ? m.getFecha_realizacion().toString() : "PENDIENTE", fontCuerpo));
                    tablaM.addCell(new Phrase(m.getDescripcion(), fontCuerpo));
                    tablaM.addCell(new Phrase("$" + m.getCosto(), fontCuerpo));
                    tablaM.addCell(new Phrase(m.getKilometraje_mantenimiento() + " KM", fontCuerpo));
                }
                documento.add(tablaM);

            } else if (tipo != null && tipo.equals("combustible")) {
                // SECCIÓN 2: COMBUSTIBLE
                documento.add(new Paragraph("1. REGISTRO DE CONSUMO DE COMBUSTIBLE", fontSubtitulo));
                documento.add(new Paragraph(" "));

                PdfPTable tablaC = new PdfPTable(4);
                tablaC.setWidthPercentage(100);

                String[] encC = {"Fecha", "Cantidad", "Costo Total", "KM Lectura"};
                for (String col : encC) {
                    PdfPCell cell = new PdfPCell(new Phrase(col, fontTablaEncabezado));
                    cell.setBackgroundColor(BaseColor.DARK_GRAY);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(5);
                    tablaC.addCell(cell);
                }

                CombustibleDAO cDao = new CombustibleDAO();
                List<Combustible> listaC = cDao.listarPorVehiculo(idVehiculo);

                if(listaC.isEmpty()){
                    PdfPCell vacio = new PdfPCell(new Phrase("No hay registros de combustible", fontCuerpo));
                    vacio.setColspan(4);
                    vacio.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tablaC.addCell(vacio);
                } else {
                    for (Combustible c : listaC) {
                        tablaC.addCell(new Phrase(c.getFecha().toString(), fontCuerpo));
                        tablaC.addCell(new Phrase(c.getCantidad() + "", fontCuerpo));
                        tablaC.addCell(new Phrase("$" + c.getCosto(), fontCuerpo));
                        tablaC.addCell(new Phrase(c.getKilometraje() + " KM", fontCuerpo));
                    }
                }
                documento.add(tablaC);
            }

            // 4. CIERRE DEL DOCUMENTO
            documento.close();

        } catch (DocumentException e) {
            throw new IOException("Error al generar el PDF: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}