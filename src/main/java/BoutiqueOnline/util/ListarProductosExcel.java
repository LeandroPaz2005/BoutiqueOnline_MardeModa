package BoutiqueOnline.util;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import BoutiqueOnline.modelo.Producto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ListarProductosExcel extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
            Workbook workbook,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        response.setHeader("Content-Disposition", "attachment; filename=\"listado-productos.xlsx\"");
        Sheet hoja = workbook.createSheet("Productos");
        hoja.setDefaultColumnWidth(20);

        //===Estilos===
        //Fuentes
        Font fontBold = workbook.createFont();
        fontBold.setBold(true);
        fontBold.setFontHeightInPoints((short) 12);

        //Estilo para el titulo
        CellStyle estiloTitulo = workbook.createCellStyle();
        estiloTitulo.setAlignment(HorizontalAlignment.CENTER);
        estiloTitulo.setFont(fontBold);

        //estilo para la cabecera
        CellStyle estiloCabecera = workbook.createCellStyle();
        estiloCabecera.setFillForegroundColor(IndexedColors.RED.getIndex());
        estiloCabecera.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloCabecera.setFont(fontBold);
        estiloCabecera.setBorderBottom(BorderStyle.THIN);
        estiloCabecera.setBorderTop(BorderStyle.THIN);
        estiloCabecera.setBorderLeft(BorderStyle.THIN);
        estiloCabecera.setBorderRight(BorderStyle.THIN);

        //estilo para la celdas normales
        CellStyle estiloCeldas = workbook.createCellStyle();
        estiloCeldas.setBorderBottom(BorderStyle.THIN);
        estiloCeldas.setBorderTop(BorderStyle.THIN);
        estiloCeldas.setBorderLeft(BorderStyle.THIN);
        estiloCeldas.setBorderRight(BorderStyle.THIN);

        //titulo (fila 1)
        Row filatitulo = hoja.createRow(0);
        Cell celda = filatitulo.createCell(0);
        celda.setCellValue("Listado de Productos");
        celda.setCellStyle(estiloTitulo);
        hoja.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

        //cabecera (fila 2)
        Row filaData = hoja.createRow(2);
        String[] columnas = {"ID", "Nombre", "Descripción", "Precio", "Cantidad"};
        for (int i = 0; i < columnas.length; i++) {
            celda = filaData.createCell(i);
            celda.setCellValue(columnas[i]);
            celda.setCellStyle(estiloCeldas);
        }

        //cuartpo de la tabla
        List<Producto> listaP = (List<Producto>) model.get("productos");
        int numFila = 3;
        for (Producto p : listaP) {
            Row fila = hoja.createRow(numFila++);
            Cell celda0 = fila.createCell(0);
            celda0.setCellValue(p.getId());
            celda0.setCellStyle(estiloCeldas);
            Cell celda1 = fila.createCell(1);
            celda1.setCellValue(p.getNombre());
            celda1.setCellStyle(estiloCeldas);
            Cell celda2 = fila.createCell(2);
            celda2.setCellValue(p.getDescripcion());
            celda2.setCellStyle(estiloCeldas);
            Cell celda3 = fila.createCell(3);
            celda3.setCellValue(p.getPrecio());
            celda3.setCellStyle(estiloCeldas);
            Cell celda4 = fila.createCell(4);
            celda4.setCellValue(p.getCantidad());
            celda4.setCellStyle(estiloCeldas);
        }

    }
}
