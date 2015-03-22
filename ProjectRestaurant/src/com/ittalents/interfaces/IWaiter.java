package com.ittalents.interfaces;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ittalents.pojos.Bill;
import com.ittalents.pojos.TableMessage;
import com.ittalents.pojos.requests.Login;
import com.ittalents.pojos.requests.Response;

public interface IWaiter {

	public Response loginWaiter(Login login, HttpServletRequest request,
			HttpServletResponse response);
	
	public Response logoutWaiter(HttpServletRequest request);

	public Response makeOrderDone(int orderId, HttpServletRequest request);

	public Response payBill(Bill bill, HttpServletRequest request);
	
	public List<String> getWaiters();
	
	public Response getCurrentMessages();
	
	public Response makeMessageDone(TableMessage tableMessage, HttpServletRequest request);
}
