package com.ittalents.pojos.products;

public class DetailedProduct extends CategoryProduct{

	private String description;
	private int minutesNeeded;
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getMinutesNeeded() {
		return minutesNeeded;
	}
	public void setMinutesNeeded(int minutesNeeded) {
		this.minutesNeeded = minutesNeeded;
	}
	
	
}
