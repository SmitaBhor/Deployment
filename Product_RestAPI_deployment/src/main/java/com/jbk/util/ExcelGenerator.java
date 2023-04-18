package com.jbk.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jbk.entity.Product;

public class ExcelGenerator {

	private List<Product> productList;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelGenerator(List <Product> productList) {
        this.productList = productList;
        workbook = new XSSFWorkbook();
    }
    
    private void writeHeader() {
        sheet = workbook.createSheet("Product");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "productId", style);
        createCell(row, 1, "productName", style);
        createCell(row, 2, "supplierId", style);
        createCell(row, 3, "categoryId", style);
        createCell(row, 4, "productQty", style);
        createCell(row, 5, "productPrice", style);
    }
    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else if(valueOfCell instanceof Double) {
        	cell.setCellValue((double) valueOfCell);
        }
        else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }
    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (Product product: productList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, product.getProductId(), style);
            createCell(row, columnCount++, product.getProductName(), style);
            createCell(row, columnCount++, product.getSupplier().getSupplierId(), style);
            createCell(row, columnCount++, product.getCategory().getCategoryId(), style);
            createCell(row, columnCount++, product.getProductQty(),style);
            createCell(row, columnCount++, product.getProductPrice(), style);
        }
    }
    public void generateExcelFile() throws IOException {
        writeHeader();
        write();
        String filePath = System.getProperty("user.home");//dynamic path
        filePath = filePath+"/Downloads/ExportedProducts.xlsx";
		File file = new File(filePath);	
        FileOutputStream fos = new FileOutputStream(file);
        workbook.write(fos);
        workbook.close();
    }
}
