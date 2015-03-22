package com.ittalents.pojos;

import java.util.ArrayList;
import java.util.List;

public class Bill {
	private int billId;
	private int isPaid;
	private List<Order> orders = new ArrayList<Order>();
	private double totalBill;
	
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int id) {
		this.billId = id;
	}

	public int getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(int isPaid) {
		this.isPaid = isPaid;
	}

	public double getTotalBill() {
		return totalBill;
	}

	public void setTotalBill(double totalBill) {
		this.totalBill = totalBill;
	}
	
}
