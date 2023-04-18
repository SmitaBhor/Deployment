package com.jbk.controller;

import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.jbk.entity.Product;
import com.jbk.exception.ResourceAlreadyExistException;
import com.jbk.service.ProductService;
import com.jbk.entity.FinalProduct;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

	@Autowired
	ProductService service;

	@PostMapping(value = "/save-product")
	public ResponseEntity<Boolean> saveProduct(@Valid @RequestBody Product product) {
		boolean isAdded = service.saveProduct(product);
		if (isAdded) {
			return new ResponseEntity<Boolean>(isAdded, HttpStatus.CREATED);
		} else {
		throw new ResourceAlreadyExistException("Resource Already exist");
		}
	}
	
	@PostMapping("/import-sheet")
	public ResponseEntity<String> importSheet(@RequestParam MultipartFile myFile){
		System.out.println(myFile.getName());
		String msg = service.uploadFile(myFile);
		return  new ResponseEntity<String>(msg,HttpStatus.OK);
	}
	
	 @GetMapping("/export-to-excel")
	    public ResponseEntity<String> exportIntoExcelFile() throws IOException {
	        

	        List <Product> listOfProducts = service.getAllProducts();
	        for (Product product : listOfProducts) {
				System.out.println(product);
			}
	        //ExcelGenerator generator = new ExcelGenerator(listOfProducts);
	        String msg = service.generateExcelFile(listOfProducts);
	        return  new ResponseEntity<String>(msg,HttpStatus.OK);
	    }

	@GetMapping(value = "/get-product-by-id/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable String productId) {

		Product product = service.getProductById(productId);
		if (product != null) {
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		} else {
			return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
		}
	}

	@DeleteMapping(value = "/delete-product")
	public ResponseEntity<Boolean> deleteProduct(@RequestParam String productId) {
		boolean isDeleted = service.deleteProductById(productId);
		if (isDeleted) {
			return new ResponseEntity<Boolean>(isDeleted, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(isDeleted, HttpStatus.NO_CONTENT);
		}
	}

	@PutMapping(value = "/update-product")
	public ResponseEntity<Boolean> updateProduct(@RequestBody Product product) {
		boolean isUpdated = service.updateProduct(product);
		if (isUpdated) {
			return new ResponseEntity<Boolean>(isUpdated, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(isUpdated, HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping(value = "/get-allproducts")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = service.getAllProducts();
		if (products.isEmpty()) {
			return new ResponseEntity<List<Product>>(products, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
		}
	}

	@GetMapping(value = "/sort-product-by-id")
	public ResponseEntity<List<Product>> sortProductById() {
		List<Product> products = service.sortProductById();
		if (products.isEmpty()) {
			return new ResponseEntity<List<Product>>(products, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/get-max-priceproduct")
	public ResponseEntity<List<Product>> getMaxPriceProduct(){
		List<Product> list = service.getMaxPriceProduct();
		if(!list.isEmpty())
			return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
		else
			return new ResponseEntity<List<Product>>(list, HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/get-finalproduct/{productId}")
	public ResponseEntity<FinalProduct> getFinalProduct(@PathVariable String productId) {
		FinalProduct finalProduct = service.getFinalProduct(productId);
		if(finalProduct!=null)
			return new ResponseEntity<com.jbk.entity.FinalProduct>(finalProduct, HttpStatus.OK);
		else
			return new ResponseEntity<FinalProduct>( HttpStatus.NO_CONTENT);
	}

}
