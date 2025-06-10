
package BoutiqueOnline.Reportes;

import BoutiqueOnline.modelo.DetalleOrden;
import BoutiqueOnline.modelo.Usuario;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
@Service
public class ReportePDFServicio {
    
    public ByteArrayInputStream generarPdfCarrito(List<DetalleOrden> detalles, Usuario usuario) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Título
            Paragraph titulo = new Paragraph("Reporte del Carrito de Compras")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold()
                    .setFontSize(18);
            document.add(titulo);

            // Datos del usuario
            Paragraph usuarioInfo = new Paragraph("Usuario: " + usuario.getNombre() + " " + usuario.getApellido())
                    .setFontSize(12)
                    .setMarginBottom(10);
            document.add(usuarioInfo);

            // Tabla de productos
            Table table = new Table(5);
            table.addHeaderCell("Producto");
            table.addHeaderCell("Precio");
            table.addHeaderCell("Cantidad");
            table.addHeaderCell("Total");
            table.addHeaderCell("Descripción");

            for (DetalleOrden detalle : detalles) {
                table.addCell(detalle.getProducto().getNombre());
                table.addCell(String.format("S/ %.2f", detalle.getPrecio()));
                table.addCell(String.valueOf(detalle.getCantidad()));
                table.addCell(String.format("S/ %.2f", detalle.getTotal()));
                table.addCell(detalle.getProducto().getDescripcion());
            }

            document.add(table);
            document.close();
            return new ByteArrayInputStream(out.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
