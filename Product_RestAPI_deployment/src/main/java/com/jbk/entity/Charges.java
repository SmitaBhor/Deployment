package com.jbk.entity;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

public class Charges{

	public int gst;
	
	public double deliveryCharges;
	public Charges( int gst, double deliveryCharges) {
		
		this.gst = gst;
		this.deliveryCharges = deliveryCharges;
	}
	
	public int getGst() {
		return gst;
	}
	public void setGst(int gst) {
		this.gst = gst;
	}
	public double getDeliveryCharges() {
		return deliveryCharges;
	}
	public void setDeliveryCharges(double deliveryCharges) {
		this.deliveryCharges = deliveryCharges;
	}
	@Override
	public String toString() {
		return "Charges [gst=" + gst + ", deliveryCharges=" + deliveryCharges + "]";
	}
	
	
	
}
