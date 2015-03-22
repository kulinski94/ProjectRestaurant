package com.ittalents.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ittalents.daos.TableDAO;
import com.ittalents.interfaces.ITable;
import com.ittalents.pojos.Table;
import com.ittalents.pojos.TableMessage;
import com.ittalents.pojos.requests.Response;

@Controller
public class TableController implements ITable{
	
	@Autowired
	private TableDAO tableDao;
	
	@RequestMapping(value = "/getTables", method = RequestMethod.GET)
	public @ResponseBody List<Table> getAllTables() {
		try {
			List<Table> tables = tableDao.getTables();
			return tables;
		} catch (DataAccessException e) {
			return new ArrayList<>();
		} catch (Exception e) {
			return new ArrayList<>();
		}

	}

	@ResponseBody
	@RequestMapping(value = "/setActive", method = RequestMethod.GET)
	public Response setActive(@RequestParam(value = "tableId") int id) {
		try {
			tableDao.setActive(id);
			return new Response("OK");
		} catch (DataAccessException e) {
			return new Response("No table with this ID!");
		} catch (Exception e) {
			return new Response("Oops, something went wrong!");
		} 

	}

	@ResponseBody
	@RequestMapping(value = "/setInactive", method = RequestMethod.GET)
	public Response setInactive(@RequestParam(value = "tableId") int id) {

		try {
			tableDao.setInactive(id);
			return new Response("OK");
		} catch (DataAccessException e) {
			return new Response("No table with this ID!");
		} catch (Exception e) {
			return new Response("Oops, something went wrong!");
		}
	}

	@RequestMapping(value = "/makeMessage", method = RequestMethod.POST, headers = {
			"Accept=*/*", "Content-Type=application/json" })
	public @ResponseBody Object makeMessage(@RequestBody TableMessage tableMessage) {
		try {
			tableDao.makeMessage(tableMessage.getTableId(),
					tableMessage.getText());
			return new Response("OK");
		} catch (DataAccessException e) {
			return new Response("No table with this ID!");
		}		
	}
}
