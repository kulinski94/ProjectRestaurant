package com.ittalents.pojos;

import java.util.List;

import com.ittalents.pojos.products.CategoryProduct;

public class Category extends ParentCategory {

	private List<CategoryProduct> products;

	public List<CategoryProduct> getProducts() {
		return products;
	}

	public void setProducts(List<CategoryProduct> products) {
		this.products = products;
	}

}
