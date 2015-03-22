package com.ittalents.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.ittalents.pojos.products.DetailedProduct;

@Component("productDAO")
public class ProductDAO extends ObjectDAO{

	public DetailedProduct getProductDetailsByID(int id){
		return jdbc.queryForObject("select * from RESTAURANT.PRODUCT where id=?",new Object[]{id},
				new RowMapper<DetailedProduct>() {
					public DetailedProduct mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						DetailedProduct product = new DetailedProduct();
						product.setProductId(rs.getInt("id"));
						product.setName(rs.getString("name"));
						product.setPrice(rs.getDouble("price"));
						product.setImageUrl(rs.getString("imageURL"));
						product.setMinutesNeeded(rs.getInt("minsneeded"));
						product.setDescription(rs.getString("description"));
						return product;
					}
				});
	}
	
	public void addProduct(DetailedProduct product){
		String sql = "insert into RESTAURANT.orderedproducts (name,description,minsneeded,price,categoryid,imageurl) values (?,?,?,?,?,?)";
		jdbc.update(sql, new Object[] {product.getName(),product.getDescription(),product.getMinutesNeeded(),product.getCategoryId(),product.getImageUrl()});
	}
	
}
