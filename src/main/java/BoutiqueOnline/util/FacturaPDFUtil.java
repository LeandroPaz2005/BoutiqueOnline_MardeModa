package BoutiqueOnline.util;

import BoutiqueOnline.modelo.DetalleOrden;
import BoutiqueOnline.modelo.Orden;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Date;

public class FacturaPDFUtil {

    public static void exportarFacturaPDF(Orden orden, OutputStream outputStream) {
        DecimalFormat df = new DecimalFormat("0.00");
        DeviceRgb colorRosa = new DeviceRgb(240, 115, 180);

        try {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document doc = new Document(pdfDoc, PageSize.A4);
            doc.setMargins(30, 30, 30, 30);

            // ---------- Estilos ----------
            Style tituloStyle = new Style()
                    .setFontSize(18)
                    .setBold()
                    .setFontColor(colorRosa)
                    .setTextAlignment(TextAlignment.CENTER);

            Style negrita = new Style().setBold();
            Style normal = new Style().setFontSize(12f);

            // ---------- Logo ----------
            String rutaLogo = System.getProperty("user.dir") + "/images/logo.png";
            File file = new File(rutaLogo);
            if (file.exists()) {
                ImageData logoData = ImageDataFactory.create(rutaLogo);
                Image logo = new Image(logoData).scaleToFit(100, 60);
                doc.add(logo);
            }

            // ---------- Título ----------
            Paragraph titulo = new Paragraph("Factura / Comprobante de Pago").addStyle(tituloStyle).setMarginBottom(15);
            doc.add(titulo);

            // ---------- Cliente ----------
            doc.add(new Paragraph("Cliente: " + orden.getUsuario().getNombre() + " " + orden.getUsuario().getApellido()).addStyle(normal));
            doc.add(new Paragraph("Correo: " + orden.getUsuario().getEmail()).addStyle(normal));
            doc.add(new Paragraph("Fecha: " + new Date()).addStyle(normal));
            doc.add(new Paragraph(" ")); // Espacio

            // ---------- Tabla productos ----------
            Table tabla = new Table(UnitValue.createPercentArray(new float[]{1.5f, 3f, 2f, 1.5f, 2f}))
                    .useAllAvailableWidth();

            // Cabecera
            tabla.addHeaderCell(crearCeldaEncabezado("Imagen", colorRosa));
            tabla.addHeaderCell(crearCeldaEncabezado("Producto", colorRosa));
            tabla.addHeaderCell(crearCeldaEncabezado("Precio", colorRosa));
            tabla.addHeaderCell(crearCeldaEncabezado("Cant.", colorRosa));
            tabla.addHeaderCell(crearCeldaEncabezado("Subtotal", colorRosa));

            String rutaBaseImagen = System.getProperty("user.dir") + "/images/productos/";

            double total = 0;
            for (DetalleOrden det : orden.getDetalle()) {
                try {
                    String rutaImg = rutaBaseImagen + det.getProducto().getImagen();
                    Image img = new Image(ImageDataFactory.create(rutaImg)).scaleAbsolute(50, 50);
                    tabla.addCell(new Cell().add(img).setBorder(Border.NO_BORDER));
                } catch (IOException e) {
                    tabla.addCell(new Cell().add(new Paragraph("Sin imagen")).setBorder(Border.NO_BORDER));
                }

                tabla.addCell(new Cell().add(new Paragraph(det.getNombre())).setBorder(Border.NO_BORDER));
                tabla.addCell(new Cell().add(new Paragraph("S/ " + df.format(det.getPrecio()))).setBorder(Border.NO_BORDER));
                tabla.addCell(new Cell().add(new Paragraph(String.valueOf(det.getCantidad()))).setBorder(Border.NO_BORDER));

                double subtotal = det.getPrecio() * det.getCantidad();
                total += subtotal;
                tabla.addCell(new Cell().add(new Paragraph("S/ " + df.format(subtotal))).setBorder(Border.NO_BORDER));
            }

            doc.add(tabla);
            doc.add(new Paragraph(" "));

            // ---------- Resumen de pago ----------
            double igv = total * 0.18;
            double totalFinal = total + igv;

            doc.add(new Paragraph("Resumen de Pago").addStyle(negrita).setMarginTop(15));
            doc.add(new Paragraph("Subtotal: S/ " + df.format(total)).addStyle(normal));
            doc.add(new Paragraph("IGV (18%): S/ " + df.format(igv)).addStyle(normal));
            doc.add(new Paragraph("Total a pagar: S/ " + df.format(totalFinal)).addStyle(negrita));

            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Cell crearCeldaEncabezado(String texto, DeviceRgb colorFondo) {
        return new Cell()
                .add(new Paragraph(texto).setFontColor(com.itextpdf.kernel.colors.ColorConstants.WHITE).setBold())
                .setBackgroundColor(colorFondo)
                .setTextAlignment(TextAlignment.CENTER)
                .setPadding(5);
    }
}
