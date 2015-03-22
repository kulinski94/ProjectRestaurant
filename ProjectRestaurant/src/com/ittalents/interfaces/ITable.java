package com.ittalents.interfaces;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ittalents.pojos.Table;
import com.ittalents.pojos.TableMessage;
import com.ittalents.pojos.requests.Response;

public interface ITable {

	public List<Table> getAllTables();
	
	public Response setActive(@RequestParam(value = "tableId") int id);
	
	public Response setInactive(@RequestParam(value = "tableId") int id);

	public @ResponseBody Object makeMessage(@RequestBody TableMessage tableMessage);

}