package com.example.controller;

import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

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
	private ProductDao productDao;
	
	@RequestMapping(value = {"/","/home"},method = RequestMethod.GET)
	public String products(Model model,HttpSession session) throws SQLException, ClassNotFoundException {
		menu(model);
		
		if (session.getAttribute("user") != null) {
			return "logged";
		} else {
			return "index";
		}
	}
	
	@RequestMapping(value = "/menu",method = RequestMethod.GET)
	public String menu(Model m) {
			try {
				List<Product> products = productDao.getAllProducts();
				m.addAttribute("products",products);
			} catch (SQLException | InvalidArgumentsException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		return "menu";
	}
	
}
