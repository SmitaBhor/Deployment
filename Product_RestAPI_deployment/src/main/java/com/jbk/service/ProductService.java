package com.jbk.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jbk.entity.FinalProduct;
import com.jbk.entity.Product;


public interface ProductService {
	
	public boolean saveProduct(Product product);
	public Product getProductById(String productId);
	public List<Product> getAllProducts();
	public boolean deleteProductById(String productId);
	public boolean updateProduct(Product product);
	public List<Product> sortProductById();
	public List<Product> getMaxPriceProduct();
	public FinalProduct getFinalProduct(String productId);
	public String uploadFile(MultipartFile myFile);
	public String generateExcelFile(List<Product> listOfProducts);
}
