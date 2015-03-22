package com.ittalents.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.ittalents.exceptions.OrdersNotDoneException;
import com.ittalents.pojos.Bill;
import com.ittalents.pojos.Order;
import com.ittalents.pojos.products.Product;

@Component("billsDAO")
public class BillsDAO extends ObjectDAO {

	public Integer getBillId(int tableId) {
		String sql = "select id from RESTAURANT.BILLS where tableid = ? and ispaid = 0";
		Integer billId;
		try {
			billId = jdbc.queryForObject(sql, new Object[] { tableId },
					Integer.class);
		} catch (DataAccessException e) {
			// throw noBillOnThisTable
			billId = null;
		}
		return billId;
	}

	public boolean createBillOnTable(int tableId) {
		Integer billId = getBillId(tableId);
		if (billId == null) {
			String sql = "insert into RESTAURANT.BILLS (amount,ispaid,tableId) VALUES (0,0,?)";
			jdbc.update(sql, new Object[] { tableId });
			return true;
		}
		return false;
	}

	public void payBill(int billId) throws OrdersNotDoneException {
		List<Integer> ordersStatuses = jdbc.query(
				"select status from RESTAURANT.ORDEREDPRODUCTS where billId = ?",
				new Object[] { billId }, new RowMapper<Integer>() {
					public Integer mapRow(ResultSet rs, int rowNum)
							throws SQLException {

						return rs.getInt("status");
					}
				});

		for (Integer integer : ordersStatuses) {
			System.out.println(integer);
		}
		if(ordersStatuses.contains(0) || ordersStatuses.contains(1)){
			throw new OrdersNotDoneException();
		}
		jdbc.update("update RESTAURANT.BILLS set ispaid = 1 where id = ?",
				new Object[] { billId });

	}

	public Bill getBillByTable(int tableId) {
		return jdbc
				.queryForObject(
						"select * from RESTAURANT.BILLS where tableid = ? and ispaid = 0",
						new Object[] { tableId }, new RowMapper<Bill>() {
							public Bill mapRow(ResultSet rs, int rowNum)
									throws SQLException {
								Bill bill = new Bill();
								bill.setBillId(rs.getInt("id"));
								bill.setIsPaid(rs.getInt("ispaid"));
								bill.setTotalBill(rs.getDouble("amount"));
								bill.setOrders(getOrdersByBill(rs.getInt("id")));
								return bill;
							}
						});
	}

	
	public List<Bill> getDailyReport(java.util.Date currentDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDay);
		java.sql.Date currentSqlDay = new java.sql.Date(cal.getTime().getTime());
		cal.add(Calendar.DATE, 1);
		java.sql.Date nextSqlDay = new java.sql.Date(cal.getTime().getTime());
		System.out.println(currentSqlDay);
		String sql = "select distinct billId,ispaid,amount from RESTAURANT.ORDEREDPRODUCTS o, RESTAURANT.BILLS b "
				+ "where o.billid = b.id and b.ispaid = 1 and o.ondate >= '"
				+ currentSqlDay + " 00:00:00' " + "and o.ondate < '"
				+ nextSqlDay + " 00:00:00'";

		return jdbc.query(sql, new RowMapper<Bill>() {

			public Bill mapRow(ResultSet rs, int rowNum) throws SQLException {
				Bill bill = new Bill();
				bill.setBillId(rs.getInt("billId"));
				bill.setIsPaid(rs.getInt("ispaid"));
				bill.setTotalBill(rs.getDouble("amount"));
				bill.setOrders(getOrdersByBill(rs.getInt("billId")));
				return bill;

			}
		});
	}

	private List<Order> getOrdersByBill(int billId) {
		return jdbc
				.query("select * from RESTAURANT.ORDEREDPRODUCTS where billid = ? order by RESTAURANT.ORDEREDPRODUCTS.STATUS ASC",
						new Object[] { billId }, new RowMapper<Order>() {

							public Order mapRow(ResultSet rs, int rowNum)
									throws SQLException {
								Order order = new Order();
								order.setOrderId(rs.getInt("id"));
								order.setDate(rs.getDate("ondate"));
								order.setStatus(rs.getInt("status"));
								Product product = getProductById(rs
										.getInt("productid"));
								order.setProduct(product);
								return order;
							}
						});
	}

	private Product getProductById(int id) {
		return jdbc.queryForObject(
				"select * from RESTAURANT.PRODUCT where id=?",
				new Object[] { id }, new RowMapper<Product>() {
					public Product mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Product product = new Product();
						product.setProductId(rs.getInt("id"));
						product.setName(rs.getString("name"));
						product.setPrice(rs.getDouble("price"));
						return product;
					}
				});
	}

}
