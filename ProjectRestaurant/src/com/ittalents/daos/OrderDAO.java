package com.ittalents.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ittalents.pojos.products.Product;

@Component("orderDAO")
public class OrderDAO extends ObjectDAO {

	@Transactional
	public Integer createOrder(int productId, Integer billId)
			throws SQLException, DataIntegrityViolationException {
		Integer orderId = null;
		if (billId != null) {
			orderId = makeOrder(productId, billId);

			String sqlProduct = "select price from RESTAURANT.PRODUCT where id = ?";
			Double price = jdbc.queryForObject(sqlProduct,
					new Object[] { productId }, Double.class);

			String sqlBill = "update RESTAURANT.BILLS SET amount = amount + ? WHERE id = ?";
			jdbc.update(sqlBill, new Object[] { price, billId });
		}
		return orderId;
	}

	private int makeOrder(final int productId, final Integer billId)
			throws SQLException {
		KeyHolder holder = new GeneratedKeyHolder();

		jdbc.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(
								"insert into RESTAURANT.ORDEREDPRODUCTS (billId,productId,status) VALUES (?,?,0)",
								Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, billId);
				ps.setInt(2, productId);
				return ps;
			}
		}, holder);

		int id = holder.getKey().intValue();
		return id;
	}
	
	public List<Product> getAllOrders() throws DataAccessException{
		String query = "select PRODUCTID, NAME, PRICE "
				+ "from RESTAURANT.ORDEREDPRODUCTS o, RESTAURANT.PRODUCT p "
				+ "where o.productid = p.id and o.status=2";
		//--------
		return jdbc.query(query, new RowMapper<Product>() {

			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
				Product product = new Product();
				product.setProductId(rs.getInt("productid"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getDouble("price"));
				//product.setDescription(rs.getString("description"));
				//TODO set properties
				return product;
			}
		});
	}
	
	public void deleteOrder(final int orderId)
			throws SQLException {
		String sql = "DELETE FROM RESTAURANT.orderedproducts where id = ?";
		jdbc.update(sql, new Object[] {orderId});
	}
	
	public void makeOrderDone(int orderId) throws DataAccessException {

		jdbc.update(
				"update RESTAURANT.ORDEREDPRODUCTS set status = 2 where id = ?",
				new Object[] { orderId });
	}

	public void makeReady(final int orderId) {
		try {
			jdbc.update(
					"update RESTAURANT.ORDEREDPRODUCTS set status = 1 where id = ?",
					new Object[] { orderId });
			// TODO PUSH NOTIFICATION

		} catch (DataAccessException e) {
			e.printStackTrace();
			System.out.println("not found");
		}

	}

	public int getMinsNeeded(int orderId) {
		return jdbc
				.queryForObject(
						"select minsNeeded from RESTAURANT.PRODUCT where id = (select productid from RESTAURANT.ORDEREDPRODUCTS where id = ?)",
						new Object[] { orderId }, new RowMapper<Integer>() {
							public Integer mapRow(ResultSet rs, int rowNum)
									throws SQLException {
								return rs.getInt("minsneeded");
							}
						});
	}
}
