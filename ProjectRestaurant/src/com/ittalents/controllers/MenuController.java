package com.ittalents.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ittalents.daos.CategoryDAO;
import com.ittalents.daos.ProductDAO;
import com.ittalents.pojos.Category;
import com.ittalents.pojos.Menu;
import com.ittalents.pojos.products.DetailedProduct;

@Controller
public class MenuController {

	@Autowired
	private ProductDAO productDao;

	@Autowired
	private CategoryDAO categoryDao;

	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public @ResponseBody
	Menu getParentCategories() {
		try {
			Menu menu = new Menu();
			menu.setCategories(categoryDao.getParentCategories());
			return menu;
		} catch (DataAccessException e) {
			return new Menu();
		}

	}

	@RequestMapping(value = "/menu/subCategories", method = RequestMethod.GET)
	public @ResponseBody
	List<Category> getSubCategories(
			@RequestParam(value = "parentId") int parentId) {
		try {
			List<Category> subCategories = categoryDao
					.getSubCategories(parentId);
			return subCategories;
		} catch (DataAccessException e) {
			return new ArrayList<>();
		}
	}

	@RequestMapping(value = "menu/subCategories/productDetails", method = RequestMethod.GET)
	public @ResponseBody
	DetailedProduct getProductDetails(
			@RequestParam(value = "productId") int productId) {
		try {
			DetailedProduct product = productDao
					.getProductDetailsByID(productId);
			return product;
		} catch (DataAccessException e) {
			return new DetailedProduct();
		}
	}

}
