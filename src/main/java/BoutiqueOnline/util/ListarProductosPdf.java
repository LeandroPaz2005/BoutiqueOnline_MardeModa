package BoutiqueOnline.util;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import BoutiqueOnline.modelo.Producto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("productosPdf") 
public class ListarProductosPdf extends AbstractPdfView {

    @Override
    @SuppressWarnings("unchecked")
    protected void buildPdfDocument(Map<String, Object> model,
                                    Document document,
                                    PdfWriter writer,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws DocumentException, IOException {

        List<Producto> productos = (List<Producto>) model.getOrDefault("productos", List.of());

        document.setPageSize(PageSize.LETTER.rotate());
        document.setMargins(36, 36, 36, 36);
        document.open();

        // Título
        Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, Color.BLACK);
        PdfPCell tituloCell = new PdfPCell(new Phrase("Listado de Productos", tituloFont));
        tituloCell.setBorder(PdfPCell.NO_BORDER);
        tituloCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        tituloCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        tituloCell.setBackgroundColor(new Color(255, 182, 193));
        tituloCell.setPadding(16f);

        PdfPTable tituloTable = new PdfPTable(1);
        tituloTable.setWidthPercentage(100);
        tituloTable.addCell(tituloCell);
        tituloTable.setSpacingAfter(20f);
        document.add(tituloTable);

        // Tabla de productos
        PdfPTable tabla = new PdfPTable(new float[] {10f, 25f, 35f, 15f, 15f});
        tabla.setWidthPercentage(100);

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
        String[] encabezados = { "ID", "Nombre", "Descripción", "Precio", "Stock" };

        for (String encabezado : encabezados) {
            PdfPCell headerCell = new PdfPCell(new Phrase(encabezado, headerFont));
            headerCell.setBackgroundColor(Color.DARK_GRAY);
            headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            headerCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            headerCell.setPadding(6f);
            tabla.addCell(headerCell);
        }

        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 11, Color.BLACK);
        for (Producto p : productos) {
            tabla.addCell(new Phrase(String.valueOf(p.getId()), bodyFont));
            tabla.addCell(new Phrase(p.getNombre(), bodyFont));
            tabla.addCell(new Phrase(p.getDescripcion(), bodyFont));
            tabla.addCell(new Phrase(String.format("S/ %.2f", p.getPrecio()), bodyFont));
            tabla.addCell(new Phrase(String.valueOf(p.getCantidad()), bodyFont));
        }

        document.add(tabla);

        response.setHeader("Content-Disposition", "attachment; filename=\"productos.pdf\"");
    }
}
