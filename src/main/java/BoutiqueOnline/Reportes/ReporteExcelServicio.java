
package BoutiqueOnline.Reportes;

import BoutiqueOnline.modelo.Producto;
import BoutiqueOnline.modelo.Usuario;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReporteExcelServicio {
    public ByteArrayInputStream generarReporteExcel(List<Producto> productos) throws IOException {
        String[] columnas = {"ID", "Nombre", "Descripción", "Precio", "Cantidad"};

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Productos");

            // Cabecera
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
            }

            // Datos
            int fila = 1;
            for (Producto producto : productos) {
                Row row = sheet.createRow(fila++);
                row.createCell(0).setCellValue(producto.getId());
                row.createCell(1).setCellValue(producto.getNombre());
                row.createCell(2).setCellValue(producto.getDescripcion());
                row.createCell(3).setCellValue(producto.getPrecio());
                row.createCell(4).setCellValue(producto.getCantidad());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
    
    
    //para reporte de usaurio:
    public ByteArrayInputStream generarReporteExcelUsuario(List<Usuario> usuarios) throws IOException {
    String[] columnas = {"ID", "Nombre", "Apellido", "Email", "Roles"};

    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
        Sheet sheet = workbook.createSheet("Usuarios");

        // Estilo para la cabecera
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        // Cabecera
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(headerStyle);
        }

        // Datos
        int fila = 1;
        for (Usuario usuario : usuarios) {
            Row row = sheet.createRow(fila++);
            row.createCell(0).setCellValue(usuario.getId());
            row.createCell(1).setCellValue(usuario.getNombre());
            row.createCell(2).setCellValue(usuario.getApellido());
            row.createCell(3).setCellValue(usuario.getEmail());
        }

        // Autoajustar columnas
        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());
    }
}

}
