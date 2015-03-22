package com.ittalents.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.ittalents.pojos.Table;

@Component("tableDAO")
public class TableDAO extends ObjectDAO {

	public List<Table> getTables() {

		return jdbc.query("SELECT * FROM restaurant.tables",
				new RowMapper<Table>() {

					public Table mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Table table = new Table();
						table.setActive(rs.getBoolean("isactive"));
						ArrayList<Integer> isThereOrder = (ArrayList<Integer>) getOrderedTables();
						ArrayList<Integer> isMessagged = (ArrayList<Integer>) getMessagedTables();
						table.setTabletId(rs.getInt("id"));
						if (isThereOrder.contains(table.getTableId())) {
							table.setHasOrder(true);
						}
						if (isMessagged.contains(table.getTableId())) {
							table.setHasMessage(true);
						}
						return table;
					}
				});

	}

	private List<Integer> getOrderedTables() {
		return jdbc
				.query("select distinct tableid from restaurant.bills b, restaurant.orderedproducts o where o.billid=b.id and o.status = 1 and b.ispaid = 0",
						new RowMapper<Integer>() {

							public Integer mapRow(ResultSet rs, int rowNum)
									throws SQLException {
								Integer table = new Integer(rs
										.getInt("tableid"));
								return table;
							}
						});
	}

	private List<Integer> getMessagedTables() {
		return jdbc
				.query("select distinct tableid from restaurant.tables t, restaurant.messages m where t.id=m.tableid and m.status = 0",
						new RowMapper<Integer>() {

							public Integer mapRow(ResultSet rs, int rowNum)
									throws SQLException {
								Integer tableId = new Integer(rs
										.getInt("tableid"));
								return tableId;
							}
						});
	}

	public void setActive(int tableId) {
		jdbc.update("update RESTAURANT.tables set isactive = 1 where id = ?",
				new Object[] { tableId });
	}

	public void setInactive(int tableId) {
		jdbc.update("update RESTAURANT.tables set isactive = 0 where id = ?",
				new Object[] { tableId });
	}

	public void makeMessage(int tableId, String text) {
		String sql = "insert into RESTAURANT.MESSAGES (tableid,messagetext,status) VALUES (?,?,?)";
		jdbc.update(sql, new Object[] { tableId, text, 0 });

	}

}
