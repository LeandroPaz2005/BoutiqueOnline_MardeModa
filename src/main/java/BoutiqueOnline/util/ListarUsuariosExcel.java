package BoutiqueOnline.util;

import BoutiqueOnline.modelo.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import java.util.List;
import java.util.Map;
import org.apache.poi.ss.util.CellRangeAddress;

public class ListarUsuariosExcel extends AbstractXlsxView {
    
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        response.setHeader("Content-Disposition", "attachment; filename=\"usuarios.xlsx\"");
        Sheet hoja = workbook.createSheet("Usuarios");
        hoja.setDefaultColumnWidth(20);

        //estilo negrita
        Font fontBold = workbook.createFont();
        fontBold.setBold(true);
        fontBold.setFontHeightInPoints((short) 12);

        //estilo para titulo
        CellStyle estilotitulo = workbook.createCellStyle();
        estilotitulo.setAlignment(HorizontalAlignment.CENTER);
        estilotitulo.setFont(fontBold);

        //estilo para la cabecera 
        CellStyle estiloCabecera = workbook.createCellStyle();
        estiloCabecera.setFillForegroundColor(IndexedColors.RED.getIndex());
        estiloCabecera.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloCabecera.setFont(fontBold);
        estiloCabecera.setBorderBottom(BorderStyle.THIN);
        estiloCabecera.setBorderTop(BorderStyle.THIN);
        estiloCabecera.setBorderTop(BorderStyle.THIN);
        estiloCabecera.setBorderRight(BorderStyle.THIN);

        //estilo para las celdas 
        CellStyle estiloCeldas = workbook.createCellStyle();
        estiloCeldas.setBorderBottom(BorderStyle.THIN);
        estiloCeldas.setBorderTop(BorderStyle.THIN);
        estiloCeldas.setBorderLeft(BorderStyle.THIN);
        estiloCeldas.setBorderRight(BorderStyle.THIN);

        //tilulo
        Row filaTitulo = hoja.createRow(0);
        Cell celdaTitulo = filaTitulo.createCell(0);
        celdaTitulo.setCellValue("LISTADO DE USUARIOS");
        celdaTitulo.setCellStyle(estilotitulo);
        hoja.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

        //cabeceras
        Row filaEncabezados = hoja.createRow(2);
        String[] columnas = {"ID", "NOMBRE", "APELLIDO", "EMAIL", "TELEFONO", "DIRECCION", "USUARIO", "TIPO"};
        for (int i = 0; i < columnas.length; i++) {
            Cell celda = filaEncabezados.createCell(i);
            celda.setCellValue(columnas[i]);
            celda.setCellStyle(estiloCeldas);
        }

        // Datos
        List<Usuario> listaUsuarios = (List<Usuario>) model.get("usuarios");
        int numFila = 3;
        for (Usuario u : listaUsuarios) {
            Row fila = hoja.createRow(numFila++);
            fila.createCell(0).setCellValue(u.getId());
            fila.createCell(1).setCellValue(u.getNombre());
            fila.createCell(2).setCellValue(u.getApellido());
            fila.createCell(3).setCellValue(u.getEmail());
            fila.createCell(4).setCellValue(u.getTelefono());
            fila.createCell(5).setCellValue(u.getDireccion());
            fila.createCell(6).setCellValue(u.getUsername());
            fila.createCell(7).setCellValue(u.getTipo());

            //aplicar estilo a cada celda
            for (int i = 0; i < 8; i++) {
                fila.getCell(i).setCellStyle(estiloCeldas);
            }
        }
    }
}
