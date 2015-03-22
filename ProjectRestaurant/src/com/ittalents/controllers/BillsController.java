package com.ittalents.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ittalents.daos.BillsDAO;
import com.ittalents.pojos.Bill;
import com.ittalents.pojos.requests.Response;

@Controller
public class BillsController {
	@Autowired 
	private BillsDAO billsDao;
	
	
	@RequestMapping(value = "/getBill", method = RequestMethod.GET)
	public @ResponseBody Response getBillOnTable(
			@RequestParam(value = "tableId") int tableId) {
		Bill bill = null;
		try {
			bill = billsDao.getBillByTable(tableId);
		} catch (DataAccessException e) {
		}
		Response response = new Response(bill);
		return response;
	}
	
	@RequestMapping(value = "/getDailyReport", method = RequestMethod.GET)
	public @ResponseBody
	Response getDailyReport(@RequestParam(value = "date") String date) {
		List<Bill> bills = null;
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			try {
				java.util.Date date2 = format.parse(date);
				
				bills = billsDao.getDailyReport(date2);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		Response response = new Response(bills);
		return response;
	}

}
