package com.ittalents.pojos;



import java.util.ArrayList;
import java.util.List;

public class Menu {
	private List<ParentCategory> categories;

	public Menu() {
		categories = new ArrayList<ParentCategory>();
	}

	public List<ParentCategory> getCategories() {
		return categories;
	}
	

	public void setCategories(List<ParentCategory> categories) {
		this.categories = categories;
	}


}
