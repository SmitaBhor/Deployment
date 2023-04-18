package com.jbk.entity;

public class FinalProduct {
	
	public String productId;
	public String productName;
	public Supplier supplier;
	public Category category;
	private Charges charge;
	public int productQty;
	public double productPrice;
	public double discountAmount;
	public double finalProductPrice;
	
	public FinalProduct() {}
	public FinalProduct(String productId, String productName, Supplier supplier, Category category, Charges charge,
			int productQty, double productPrice, double discountAmount, double finalProductPrice) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.supplier = supplier;
		this.category = category;
		this.charge = charge;
		this.productQty = productQty;
		this.productPrice = productPrice;
		this.discountAmount = discountAmount;
		this.finalProductPrice = finalProductPrice;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public int getProductQty() {
		return productQty;
	}
	public void setProductQty(int productQty) {
		this.productQty = productQty;
	}
	public double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	public double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public double getFinalProductPrice() {
		return finalProductPrice;
	}
	public void setFinalProductPrice(double finalProductPrice) {
		this.finalProductPrice = finalProductPrice;
	}
	

	public Charges getCharge() {
		return charge;
	}
	public void setCharge(Charges charge) {
		this.charge = charge;
	}
	@Override
	public String toString() {
		return "FinalProduct [productId=" + productId + ", productName=" + productName + ", supplier=" + supplier
				+ ", category=" + category + ", charge=" + charge + ", productQty=" + productQty + ", productPrice="
				+ productPrice + ", discountAmount=" + discountAmount + ", finalProductPrice=" + finalProductPrice
				+ "]";
	}
	
	
}
