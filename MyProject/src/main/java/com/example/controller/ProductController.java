package com.example.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.model.Ingredient;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.Size;
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
	
	@RequestMapping(value="/pizza/{productId}",method = RequestMethod.GET)
	public String getProductPage(HttpSession session,@PathVariable (value = "productId") String productId,Model model) {		
		Product pizza = null;
		HashSet<Ingredient> ingredients =  new HashSet<>();
		try {
			pizza = productDao.getProductById(Long.parseLong(productId));
			ingredients.addAll(productDao.getProductIngredients(Long.parseLong(productId)));
		} catch (NumberFormatException | SQLException | InvalidArgumentsException e) {
			System.out.println(e.getMessage());
		}
		session.setAttribute("pizza", pizza);
		session.setAttribute("ingredients", ingredients);
	//	model.addAttribute("size", new Size());
	 //	model.addAttribute("dough", new Dough());
		return "pizza";
	}
	
	@RequestMapping(value = "/favorite/add/{productId}", method = RequestMethod.GET)
		public String addToFavorites(@PathVariable("productId") String productId, Model model, HttpSession session) {		
		if (session.getAttribute("user") == null) {
				return "login";
		}
		else {
			User user = (User) session.getAttribute("user");
			Product product = null;
			HashSet<Product>favorites =  new HashSet<>();
			try {
				product = productDao.getProductById(Long.parseLong(productId));
			} catch (NumberFormatException | SQLException | InvalidArgumentsException e) {
				model.addAttribute("exception", e);
				e.printStackTrace();
				return "error";
			}
			if(product != null) {
				if (session.getAttribute("favorites") == null) {
					try {
						favorites.addAll(userDao.getFavoriteProducts(user));
					} catch (SQLException | InvalidArgumentsException e) {
						model.addAttribute("exception", e);
						e.printStackTrace();
						return "error";
					}
					user.addToFavorite(product);
					favorites.add(product);		
					try {
						userDao.addFavoriteProduct(user, Long.parseLong(productId));
					} catch (NumberFormatException | SQLException e) {
						model.addAttribute("exception", e);
						e.printStackTrace();
						return "error";
					}
				}
				else {
					favorites.addAll((HashSet)session.getAttribute("favorites"));
					if(!favorites.contains(product)) {
						user.addToFavorite(product);
						favorites.add(product);
						try {
							userDao.addFavoriteProduct(user, Long.parseLong(productId));
						} catch (NumberFormatException | SQLException e) {
							model.addAttribute("exception", e);
							e.printStackTrace();
							return "error";
						}
						model.addAttribute("info", "Added to favorite products!");
					}
					else {
						model.addAttribute("info", "Already added to favorite products!");
						return "pizza";
					}
				}
			}
			session.setAttribute("user", user);
			session.setAttribute("favorites", favorites);
			model.addAttribute("favorites", favorites);
		}	
		
		return "profile";
	}
	
	@RequestMapping(value = "/favorite/remove/{productId}", method = RequestMethod.GET)
	public String removeFromFavorites(@PathVariable("productId") String productId, Model model, HttpSession session) {
		if (session.getAttribute("user") == null) {
			return "login";
		}
		else {
			User user = (User) session.getAttribute("user");
			HashSet<Product> favorites = new HashSet<>();
			Product product = null;
			try {
				product = productDao.getProductById(Long.parseLong(productId));
			} catch (NumberFormatException | SQLException | InvalidArgumentsException e) {
				e.printStackTrace();
				model.addAttribute("exception", e);
				return "error";
			}
			favorites.addAll((HashSet)session.getAttribute("favorites"));
			if(favorites.contains(product)) {
				user.removeFromFavorite(product);
				favorites.remove(product);
				try {
					userDao.deleteProductFromFavorites(user, Long.parseLong(productId));
				} catch (NumberFormatException | SQLException e) {
					model.addAttribute("exception", e);
					e.printStackTrace();
					return "error";
				}
				model.addAttribute("info", "Removed from favorite products!");
			}
			model.addAttribute("favorites", favorites);
			session.setAttribute("favorites", favorites);
		}
		return "profile";	
		}
}
