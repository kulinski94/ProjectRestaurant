package com.ittalents.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ittalents.daos.BillsDAO;
import com.ittalents.daos.OrderDAO;
import com.ittalents.pojos.Order;
import com.ittalents.pojos.Quote;
import com.ittalents.pojos.requests.MakeOrder;
import com.ittalents.pojos.requests.Response;

@Controller
public class OrdersController {

	@Autowired 
	private BillsDAO billsDao;
	
	@Autowired 
	private OrderDAO orderDao;
	
	@RequestMapping(value = "/makeOrder", method = RequestMethod.POST, headers = {
			"Accept=*/*", "Content-Type=application/json" })
	public @ResponseBody
	Object makeOrder(@RequestBody MakeOrder makeOrder) {
		Integer billId = billsDao.getBillId(makeOrder.getTableId());
		if (billId == null) {
			if (billsDao.createBillOnTable(makeOrder.getTableId())) {
				billId = billsDao.getBillId(makeOrder.getTableId());
			}
		}
		try {
			int orderId = orderDao
					.createOrder(makeOrder.getProductId(), billId);
			sleepMinsNeeded(orderId);
			return new Response("OK");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataIntegrityViolationException e) {

		} 
		return new Response("Opps there was a problem making the order :)");

	}
	
	
	
	@RequestMapping(value = "/deleteOrder", method = RequestMethod.POST, headers = {
			"Accept=*/*", "Content-Type=application/json" })
	public @ResponseBody
	Object deleteOrder(@RequestBody Order order) {
			try {
				orderDao.deleteOrder(order.getOrderId());
				return new Response("OK");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return new Response("Opps there was a problem making the order :)");
	}

	private void sleepMinsNeeded(final int orderId) {

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					int minsNeeded = orderDao.getMinsNeeded(orderId);
					System.out.println(minsNeeded);
					System.out.println("sleeping");
					Thread.sleep(minsNeeded * 60000);
					ready(orderId);
					System.out.println("awake");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (DataAccessException e) {
					e.printStackTrace();
					System.out.println("not found");
				}

			}
		});
		t1.start();

	}
	
	private void ready(int orderId) throws DataAccessException{
		orderDao.makeReady(orderId);
	}
	
	@RequestMapping(value = "/getquote", method = RequestMethod.POST, headers = {
			"Accept=*/*", "Content-Type=application/json" })
	public @ResponseBody
	Quote getQuote(@RequestBody Quote quote) {
			System.out.println(quote);
		return quote;
	}

}
