package com.ittalents.pojos;

import java.util.Date;

import com.ittalents.pojos.products.Product;

public class Order{
	
	private int orderId;
	private Date date ;
	private int status;// 1 = ordered; 2 = waiting; 3 = done  
	private Product product;
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int id) {
		this.orderId = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	
}
