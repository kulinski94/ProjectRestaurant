package com.ittalents.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ittalents.daos.BillsDAO;
import com.ittalents.daos.OrderDAO;
import com.ittalents.daos.WaiterDAO;
import com.ittalents.exceptions.OrdersNotDoneException;
import com.ittalents.interfaces.IWaiter;
import com.ittalents.pojos.Bill;
import com.ittalents.pojos.HashCoding;
import com.ittalents.pojos.TableMessage;
import com.ittalents.pojos.Waiter;
import com.ittalents.pojos.requests.Login;
import com.ittalents.pojos.requests.Response;

@Controller
public class WaiterController implements IWaiter {

	@Autowired
	private BillsDAO billsDao;

	@Autowired
	private WaiterDAO waiterDao;

	@Autowired
	private OrderDAO orderDao;

	@RequestMapping(value = "/LoginWaiter", method = RequestMethod.POST, headers = {
			"Accept=*/*", "Content-Type=application/json" })
	public @ResponseBody
	Response loginWaiter(@RequestBody Login login, HttpServletRequest request,
			HttpServletResponse response) {
	
		Waiter waiter;
		try {
			waiter = waiterDao.login(login.getUsername(),
					HashCoding.md5(login.getPassword()));
			if (waiter != null) {
				request.getSession().setAttribute("isLogged", true);
				request.getSession().setMaxInactiveInterval(28_000_000);
				response.setStatus(HttpServletResponse.SC_OK);
				return new Response(waiter);
			} else
				return new Response("Wrong username or password!");

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return new Response("There is something wrong in the code!");
		}


	}


	@ResponseBody
	@RequestMapping(value = "/LogoutWaiter", method = RequestMethod.POST)
	public Response logoutWaiter(HttpServletRequest request) {
		if (isLogged(request)) {
			request.getSession().invalidate();
			return new Response("OK");
		} else {
			return new Response("The user is not logged in!");
		}
	}

	@RequestMapping(value = "/makeOrderDone", method = RequestMethod.GET)
	public @ResponseBody
	Response makeOrderDone(@RequestParam(value = "orderId") int orderId,
			HttpServletRequest request) {
		try {
			if (isLogged(request)) {
				orderDao.makeOrderDone(orderId);
				return new Response("OK");
			}
			return new Response("User is not logged in!");
		} catch (DataAccessException e) {
			return new Response("Error occured! Order was not added!");
		}

	}

	public static boolean isLogged(HttpServletRequest request) {
		if (request.getSession().getAttribute("isLogged") instanceof Boolean) {
			if ((Boolean) (request.getSession().getAttribute("isLogged")) == true) {
				return true;
			}
		}

		return false;
	}


	@RequestMapping(value = "/payBill", method = RequestMethod.POST, headers = {
			"Accept=*/*", "Content-Type=application/json" })
	public @ResponseBody
	Response payBill(@RequestBody Bill bill, HttpServletRequest request) {
		System.out.println(bill.getBillId());
		try {
			if (isLogged(request)) {
				billsDao.payBill(bill.getBillId());
				return new Response("OK");
			} else
				return new Response("User is not logged in!");
		} catch (DataAccessException e) {
			return new Response("Error occured! The bill was not paid!");
		} catch (OrdersNotDoneException e) {
			return new Response(e.getMessage());
		}

	}

	@RequestMapping(value = "/getWaiters", method = RequestMethod.GET)
	public @ResponseBody
	List<String> getWaiters() {
		List<String> usernames = new ArrayList<String>();
		try {
			usernames = waiterDao.getWaiters();
			return usernames;
		} catch (DataAccessException e) {
			return usernames;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getCurrentMessages", method = RequestMethod.GET)
	public Response getCurrentMessages() {
		try {
			List<TableMessage> messages = waiterDao.getCurrentMessages();
			return new Response(messages);
		} catch (DataAccessException e) {
			return new Response("No messages!");
		} catch (Exception e) {
			return new Response("Oops, something went wrong!");
		}
	}

	@RequestMapping(value = "/makeMessageDone", method = RequestMethod.POST, headers = {
			"Accept=*/*", "Content-Type=application/json" })
	public @ResponseBody
	Response makeMessageDone(@RequestBody TableMessage tableMessage,
			HttpServletRequest request) {
		try {
			if (isLogged(request)) {
				waiterDao.makeMessageDone(tableMessage.getMessageId());
				return new Response("OK");
			} else
				return new Response("User is not logged in!");
		} catch (DataAccessException e) {
			return new Response("Error while proccessing the message!");
		}
	}
}
