package com.example.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.model.Product;
import com.example.model.User;
import com.example.model.dao.ProductDao;
import com.example.model.dao.UserDao;
import com.example.util.InvalidArgumentsException;


@Controller
public class ProductController {
	
	@Autowired
	private ProductDao productDao;

	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value="/pizza",method = RequestMethod.GET)
	public String getProductPage(@RequestParam String id,HttpSession session) {
		Product pizza = null;
		try {
			pizza = productDao.getProductById(Integer.parseInt(id));
		} catch (NumberFormatException | SQLException | InvalidArgumentsException e) {
			System.out.println(e.getMessage());
		}
		session.setAttribute("pizza", pizza);
		return "pizza";
	}
	
	@RequestMapping(value = "/addFav/{productId}", method = RequestMethod.POST)
		public String addToFavorites(@PathVariable("productId") String productId, Model model, HttpServletRequest request) {
			if (request.getSession().getAttribute("user") == null) {
				return "login";
		}
			else {
				Product product;
				try {
					product = productDao.getProductById(Integer.parseInt(productId));
				} catch (NumberFormatException | SQLException | InvalidArgumentsException e) {
					return "error";
				}
				HttpSession session = request.getSession();
					User user = (User) session.getAttribute("user");
					
				try {
					if(!userDao.checkIfFavoriteProduct(user, product.getId())){
						userDao.addFavoriteProduct(user, product.getId());
						model.addAttribute("products", product);
					}
				} catch (SQLException e) {
					return "error";
				}
			}
			return "menu";
		}
	
		@RequestMapping(value = "/removeFav/{productId}", method = RequestMethod.POST)
		public String removeFromFavorites(@PathVariable("productId") String productId, Model model, HttpServletRequest request) {
			if (request.getSession().getAttribute("user") == null) {
				return "login";
			}
			else {
				Product product;
				try {
					product = productDao.getProductById(Integer.parseInt(productId));
				} catch (NumberFormatException | SQLException | InvalidArgumentsException e) {
					return "error";
				}
				HttpSession session = request.getSession();
					User user = (User) session.getAttribute("user");
					
				try {
					if(userDao.checkIfFavoriteProduct(user, product.getId())){
						userDao.deleteProductFromFavorites(user, product.getId());
						model.addAttribute("products", product);
					}
				} catch (SQLException e) {
					return "error";
				}
			}
			return "menu";
		}
}
