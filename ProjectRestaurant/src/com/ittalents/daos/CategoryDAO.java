package com.ittalents.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.ittalents.pojos.Category;
import com.ittalents.pojos.ParentCategory;
import com.ittalents.pojos.products.CategoryProduct;

@Component("categoryDAO")
public class CategoryDAO extends ObjectDAO{

	/**
	 * Select all categories that parentId is null
	 * @return List of parent categories.
	 */
	public List<ParentCategory> getParentCategories() {
		return jdbc.query("select * from RESTAURANT.CATEGORIES where parent IS null", new RowMapper<ParentCategory>() {

			public ParentCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
				ParentCategory category = new ParentCategory();
				category.setId(rs.getInt("id"));
				category.setName(rs.getString("name"));
				return category;
			}
		});
	}
	
	public List<Category> getSubCategories(int parentId){
		
		return jdbc.query("select * from RESTAURANT.CATEGORIES where parent = ?",new Object[]{parentId}, new RowMapper<Category>() {

			public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
				Category category = new Category();
				category.setId(rs.getInt("id"));
				category.setName(rs.getString("name"));
				
			
				category.setProducts(getProductsByCategory(rs.getInt("id")));
				
				return category;
			}
		});
		
	}

	public List<CategoryProduct> getProductsByCategory(int categoryid) {
		//TODO
		return jdbc.query("select id,name,price,imageUrl from RESTAURANT.PRODUCT where categoryId=?",new Object[]{categoryid},
				new RowMapper<CategoryProduct>() {
					public CategoryProduct mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						CategoryProduct product = new CategoryProduct();
						product.setProductId(rs.getInt("id"));
						product.setName(rs.getString("name"));
						product.setPrice(rs.getDouble("price"));
						product.setImageUrl(rs.getString("imageURL"));
						return product;
					}
				});
	}

}
