package com.jbk.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.jbk.dao.ProductDao;
import com.jbk.entity.Category;
import com.jbk.entity.Charges;
import com.jbk.entity.FinalProduct;
import com.jbk.entity.Product;
import com.jbk.entity.Supplier;
import com.jbk.service.ProductService;


@Service
public class ProductServiceIMPL implements ProductService {

	@Autowired
	private ProductDao dao;

	@Override
	public boolean saveProduct(Product product) {
		String productId = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
		product.setProductId(productId);
		boolean isAdded = dao.saveProduct(product);
		return isAdded;
	}

	@Override
	public Product getProductById(String productId) {

		return dao.getProductById(productId);
	}

	@Override
	public List<Product> getAllProducts() {

		return dao.getAllProducts();
	}

	@Override
	public boolean deleteProductById(String productId) {
		return dao.deleteProductById(productId);
	}

	@Override
	public boolean updateProduct(Product product) {
		return dao.updateProduct(product);
	}

	public FinalProduct getFinalProduct(String productId) {
		FinalProduct finalProduct = new FinalProduct();
		
		Product product = dao.getProductById(productId);
		finalProduct.setProductId(product.getProductId());
		finalProduct.setProductName(product.getProductName());
		finalProduct.setCategory(product.getCategory());
		finalProduct.setSupplier(product.getSupplier());
		finalProduct.setProductPrice(product.getProductPrice());
		finalProduct.setProductQty(product.getProductQty());
		finalProduct.setCharge(new Charges(product.getCategory().getGst(),product.getCategory().getDeliveryCharge()));
		double discountRupee =( product.getCategory().getDiscount()*product.getProductPrice())/100;
		finalProduct.setDiscountAmount(discountRupee);
		double price = product.getProductPrice()-discountRupee + product.getCategory().getDeliveryCharge();
		double gstRupee = (product.getCategory().getGst()*price)/100;
		 price = price + gstRupee;
		finalProduct.setFinalProductPrice(price);
		return finalProduct;
	}
	
	@Override
	public List<Product> sortProductById() {
		return dao.sortProductById();
	}

	@Override
	public List<Product> getMaxPriceProduct() {
		return dao.getMaxPriceProduct();
	}
	
	public List<Product> readExcelSheet(String path){
		Product product = null;
		List<Product> list = new ArrayList<>();
		try {
			FileInputStream fis = new FileInputStream(path);
			Workbook workBook = new XSSFWorkbook(fis);
			Sheet sheet = workBook.getSheetAt(0);
			Iterator<Row> rows = sheet.iterator();
			while (rows.hasNext()) {
				Row row = rows.next();
				int rowNum = row.getRowNum();
				if(rowNum==0) {
					continue;
				}
				product = new Product();
				String productId = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
				product.setProductId(productId);
				
				Iterator<Cell> cells = row.cellIterator();
				while (cells.hasNext()) {
					Cell cell = cells.next();
					int columnIndex = cell.getColumnIndex();
					switch (columnIndex) {
					case 0:{
							product.setProductName(cell.getStringCellValue());
							break;
						}
					case 1:{
							Supplier supplier = new Supplier();
							supplier.setSupplierId((int)cell.getNumericCellValue());
							product.setSupplier(supplier);
							break;
						}	
					case 2:{
							Category category = new Category();
							category.setCategoryId((int)cell.getNumericCellValue());
							break;
						}
					case 3:{
							product.setProductQty((int)cell.getNumericCellValue());
							break;
						}
					case 4:{
							product.setProductPrice(cell.getNumericCellValue());
							break;
						}
					}
				}
				list.add(product);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String uploadFile(MultipartFile myFile) {
		String path = "src/main/resources";
		File file = new File(path);
		String absolutePath = file.getAbsolutePath() ;
		String msg = null;
		try {
			byte[]  data = myFile.getBytes();
			FileOutputStream fos = new FileOutputStream(new File(absolutePath+File.separator+myFile.getOriginalFilename()));
			fos.write(data);
			List<Product> list = readExcelSheet(absolutePath+File.separator+myFile.getOriginalFilename());
			 msg = dao.uploadProducts(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
	
	private XSSFWorkbook write(List<Product> listOfProducts) {
		XSSFWorkbook workbook = new XSSFWorkbook(); 
	    XSSFSheet sheet;
        sheet = workbook.createSheet("Product");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "productId");
        createCell(row, 1, "productName");
        createCell(row, 2, "supplierId");
        createCell(row, 3, "categoryId");
        createCell(row, 4, "productQty");
        createCell(row, 5, "productPrice");
        
        int rowCount = 1;
        for (Product product: listOfProducts) {
             row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, product.getProductId());
            createCell(row, columnCount++, product.getProductName());
            createCell(row, columnCount++, product.getSupplier().getSupplierId());
            createCell(row, columnCount++, product.getCategory().getCategoryId());
            createCell(row, columnCount++, product.getProductQty());
            createCell(row, columnCount++, product.getProductPrice());
        }
        
        return workbook;
        
    }
    private void createCell(Row row, int columnCount, Object valueOfCell) {
       // sheet.autoSizeColumn(columnCount);
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
        
    }
    
    public String generateExcelFile(List<Product> listOfProducts) {
    	
    	XSSFWorkbook workbook = write(listOfProducts);
        String filePath = System.getProperty("user.home");//dynamic path
        filePath = filePath+"/Downloads/ExportedProducts.xlsx";
		File file = new File(filePath);	
        FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			workbook.write(fos);
			workbook.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
        
        return "Export into Excel Successfully";
    }

}
