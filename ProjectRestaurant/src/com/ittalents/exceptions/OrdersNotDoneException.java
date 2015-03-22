package com.ittalents.exceptions;

public class OrdersNotDoneException extends Exception{

	@Override
	public String getMessage() {
		return "Some orders in the bill have not been done yet.";
	}
}
