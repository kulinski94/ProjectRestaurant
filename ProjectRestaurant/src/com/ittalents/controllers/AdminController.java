package com.ittalents.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ittalents.daos.AdminDAO;
import com.ittalents.daos.CategoryDAO;
import com.ittalents.daos.BillsDAO;
import com.ittalents.daos.OrderDAO;
import com.ittalents.daos.ProductDAO;
import com.ittalents.pojos.Admin;
import com.ittalents.pojos.Category;
import com.ittalents.pojos.ParentCategory;
import com.ittalents.pojos.Bill;
import com.ittalents.pojos.RegisterWaiter;
import com.ittalents.pojos.Restaurant;
import com.ittalents.pojos.products.DetailedProduct;
import com.ittalents.pojos.products.Product;
import com.ittalents.pojos.requests.Response;

@Controller
public class AdminController {

	@Autowired
	private Admin admin;

	@Autowired
	private Restaurant restaurant;

	@Autowired
	private MailSender mailSender;
	@Autowired
	private AdminDAO adminDao;
	@Autowired
	private ProductDAO productDao;
	@Autowired
	private OrderDAO orderDao;
	@Autowired
	private CategoryDAO categoryDao;
	@Autowired
	private BillsDAO billsDao;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String loginAdmin(ModelMap model) {
		setModelData(model);
		return "loginpage";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String homePage(ModelMap model) {
		setModelData(model);
		return "home";
	}
	
	@RequestMapping(value = "/dailyReport", method = RequestMethod.GET)
	public String getDailyReport(ModelMap model,HttpSession session,HttpServletResponse response) {
		if (session.getAttribute("isLogged") != null && session.getAttribute("isLogged").equals("true")) {
			setModelData(model);
			List<Bill> bills = billsDao.getDailyReport(new java.util.Date());
			long turnover = 0;
			for (Bill bill : bills) {
				turnover += bill.getTotalBill();
			}
			model.addAttribute("bills",bills);
			model.addAttribute("turnover", turnover);
			return "dailyReport";
		}else {
			return "/";
			
		}
		
	}
	
	@RequestMapping(value = "/registerWaiter", method = RequestMethod.GET)
	public String registerWaiter(ModelMap model) {
		setModelData(model);
		return "registerwaiter";
	}
	
	@RequestMapping(value = "/addProduct", method = RequestMethod.GET)
	public String addProduct(ModelMap model) {
		setModelData(model);
		List<ParentCategory> parentCategories = categoryDao.getParentCategories();
		List<Category> subCategories = new ArrayList<Category>();
		for (ParentCategory parentCategory : parentCategories) {
			subCategories.addAll(categoryDao.getSubCategories(parentCategory.getCategoryId()));
		}
		model.addAttribute("categories",subCategories);
		return "createproduct";
	}
	
	@RequestMapping(value = "/logoutAdmin", method = RequestMethod.GET)
	public void logout(ModelMap model,HttpServletRequest request,HttpServletResponse response) {
		request.getSession().invalidate();
		try {
			response.sendRedirect("/ProjectRestaurant/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/statistics", method = RequestMethod.GET)
	public String statistics(ModelMap model) {
		model.addAttribute("name", restaurant.getName());
		model.addAttribute("owner", restaurant.getOwnerName());
		try {
			List<Product> productsList = orderDao.getAllOrders();
			HashMap<Product, Integer> productsByCount = new HashMap<>();
			for (Product product : productsList) {
				if (!productsByCount.containsKey(product)) {
					productsByCount.put(product, 0);
				}
				productsByCount.put(product, productsByCount.get(product) + 1);
				model.addAttribute("productsByCount", productsByCount);
			}
		} catch (DataAccessException e) {
		}
		return "statistics";
	}

	@RequestMapping(value = "/Register", method = RequestMethod.POST, headers = {
			"Accept=*/*", "Content-Type=application/json" })
	public Response registerWaiter(@RequestBody RegisterWaiter waiter) {

		if (checkWaiterData(waiter)) {
			try {
				adminDao.registerWaiter(waiter);
				return new Response("OK");
			} catch (DuplicateKeyException de) {
				return new Response("Username is busy.");
			}
		} else {
			return new Response("Cannot create user with these properties!");
		}

	}

	private boolean checkWaiterData(RegisterWaiter waiter) {
		if (waiter.getfName().matches("[a-zA-Z]+")
				&& waiter.getlName().matches("[a-zA-Z]+")
				&& waiter.getPassword().length() > 3
				&& waiter.getUsername().length() > 2) {
			return true;
		}
		return false;
	}

	@RequestMapping(value = "/forgottenPassword", method = RequestMethod.GET)
	public String sendMessage(@RequestBody String email) {

		String text = "You username is:" + admin.getUsername() + " password: " + admin.getPassword();

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("tsvetelin.kulinski@gmail.com");
		mail.setTo(admin.getEmail());
		mail.setSubject("Re: " + "Forgotten password");
		mail.setText(text);

		try {
			mailSender.send(mail);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Can't send message");
			return "error";
		}

		Map<String, Object> rval = new HashMap<String, Object>();
		rval.put("success", true);
		return "emailsend";
	}

	@ResponseBody
	@RequestMapping(value = "/createProduct", method = RequestMethod.POST)
	public String addProduct(Model model,DetailedProduct product,
			BindingResult result) {
		if (result.hasErrors()) {
			return "error";
		} else {
			productDao.addProduct(product);
		}
		return "home";

	}

	@RequestMapping(value = "/checkAdmin", method = RequestMethod.POST)
	public String checkAdmin(HttpServletRequest request, ModelMap model) {
		if (isCorrectLogin(request.getParameter("username"),
				request.getParameter("password"))) {
			{
				setModelData(model);
				request.getSession().setAttribute("isLogged", "true");
				return "home";
			}
		}
		loginAdmin(model);
		return "loginpage";
	}

	private boolean isCorrectLogin(final String username, final String password) {
		System.out.println(username);
		System.out.println(password);
		if (admin.getUsername().equals(username)) {
			if (admin.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}

	private void setModelData(ModelMap model){
		model.addAttribute("name", restaurant.getName());
		model.addAttribute("owner", restaurant.getOwnerName());
	}
}
