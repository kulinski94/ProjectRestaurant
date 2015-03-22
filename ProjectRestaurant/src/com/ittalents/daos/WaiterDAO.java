package com.ittalents.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.ittalents.pojos.TableMessage;
import com.ittalents.pojos.Waiter;

@Component("waiterDAO")
public class WaiterDAO extends ObjectDAO{

	public Waiter login(String username, String password) {
		try {
			return jdbc.queryForObject("select * from RESTAURANT.WAITER where username = ? and password = ?",new Object[]{username, password},
					new RowMapper<Waiter>() {
						public Waiter mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							Waiter waiter = new Waiter();
							waiter.setUsername(rs.getString("username"));
							waiter.setfName(rs.getString("fname"));
							waiter.setlName(rs.getString("lname"));
							return waiter;
						}
					});
		} catch (DataAccessException e) {
			return null;
		}
	}

	public List<String> getWaiters() {
			return jdbc.query("select username from RESTAURANT.WAITER",
					new RowMapper<String>() {
						public String mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							return rs.getString("username");
						}
					});
	}
	
	public List<TableMessage> getCurrentMessages(){
		return jdbc.query("SELECT * FROM restaurant.messages where status = 0", new RowMapper<TableMessage>() {
			public TableMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
				TableMessage tableMessage = new TableMessage();
				
				tableMessage.setMessageId(rs.getInt("messageId"));
				tableMessage.setStatus(rs.getInt("status"));
				tableMessage.setTableId(rs.getInt("tableid"));
				tableMessage.setText(rs.getString("messagetext"));
				return tableMessage;
			}
		});
	}

	public void makeMessageDone(int messageId){
		jdbc.update("update RESTAURANT.messages set status = 1 where messageId = ?", new Object[] { messageId });
	}
}
