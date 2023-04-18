package com.jbk.dao;

import java.util.List;
import com.jbk.entity.Product;
import com.jbk.entity.FinalProduct;

public interface ProductDao {
	
	public boolean saveProduct(Product product);
	public Product getProductById(String productId);
	public List<Product> getAllProducts();
	public boolean deleteProductById(String productId);
	public boolean updateProduct(Product product);
	public List<Product> sortProductById();
	public List<Product> getMaxPriceProduct();
	public double getMaxPrice();
	public String uploadProducts(List<Product> list);
}
