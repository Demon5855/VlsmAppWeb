/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author beren
 */
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@WebServlet(name = "GenerarPDFServlet", urlPatterns = {"/GenerarPDFServlet"})
public class GenerarPDFServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Configuración del archivo PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Resultados_VLSM.pdf");

        Document document = new Document();
        try {
            OutputStream out = response.getOutputStream();
            PdfWriter.getInstance(document, out);
            document.open();

            // Agregar título al PDF
            Paragraph title = new Paragraph("Resultados del Cálculo VLSM");
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            // Obtener los resultados de la sesión
            HttpSession session = request.getSession();
            List<String[]> resultados = (List<String[]>) session.getAttribute("resultados");
            List<String> resumenBinario = (List<String>) session.getAttribute("resumenVLSM");

            if (resultados != null && !resultados.isEmpty()) {
                // Crear la tabla en el PDF
                PdfPTable table = new PdfPTable(6);
                table.setWidthPercentage(100);
                table.addCell("Subred");
                table.addCell("Dirección de Red");
                table.addCell("Primera IP Utilizable");
                table.addCell("Última IP Utilizable");
                table.addCell("Broadcast");
                table.addCell("Máscara");

                // Agregar datos a la tabla
                for (String[] fila : resultados) {
                    for (String celda : fila) {
                        table.addCell(celda);
                    }
                }

                document.add(table);
            } else {
                document.add(new Paragraph("No se encontraron resultados para mostrar."));
            }

            // Agregar el Resumen Binario
            document.add(new Paragraph("\nResumen Binario del Cálculo VLSM:\n"));
            if (resumenBinario != null && !resumenBinario.isEmpty()) {
                for (String linea : resumenBinario) {
                    document.add(new Paragraph(linea));
                }
            } else {
                document.add(new Paragraph("No se generó un resumen binario."));
            }

            document.close();
        } catch (DocumentException e) {
            throw new IOException("Error al generar el PDF: " + e.getMessage());
        }
    }
}
