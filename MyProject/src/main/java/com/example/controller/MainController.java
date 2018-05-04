package com.example.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.model.Product;
import com.example.model.dao.ProductDao;
import com.example.util.InvalidArgumentsException;

@Controller
public class MainController {
	
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private ProductDao productDao;
	
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String products(Model model,ServletContext servletcontext) throws SQLException, ClassNotFoundException {
		this.servletContext =  servletcontext;
		if (servletContext.getAttribute("products") == null) {
			List<Product> products = new ArrayList<>();
			try {
				products = productDao.getAllProducts();
				System.out.println(products);
			//	ingredients = IngredientDao.getInstance().getAllIngredients();
			} catch (SQLException |InvalidArgumentsException e) {
				System.out.println(e.getMessage());
				model.addAttribute("exception", e);
				return "error";
			}
			servletContext.setAttribute("products", products);
		}
		boolean logged = false;
			
		if (model.containsAttribute("user")) {
			logged = true;
		}
		if (logged) {
			return "logged";
		} else {
			return "index.jsp";
		}
	}
	
	@RequestMapping(value = "/home",method = RequestMethod.GET)
	public String hi() {
		return "index";
	}
	
	
}
