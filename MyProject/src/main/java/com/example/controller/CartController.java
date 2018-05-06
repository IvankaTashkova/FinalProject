package com.example.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.model.Address;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.Restaurant;
import com.example.model.User;
import com.example.model.dao.AddressDao;
import com.example.model.dao.OrderDao;
import com.example.model.dao.ProductDao;
import com.example.model.dao.RestaurantDao;
import com.example.util.InvalidArgumentsException;


@Controller
public class CartController {
	
	@Autowired
	private ProductDao productDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private AddressDao addressDao;
	@Autowired
	private RestaurantDao restaurantDao;
	
	@RequestMapping(value = "/cart/add/{productId}", method = RequestMethod.GET)
	protected String getCart(HttpSession session,@PathVariable (value = "productId") String productId,Model model) {
		User user = (User) session.getAttribute("user");
		ConcurrentHashMap<Product, Integer> cart = new ConcurrentHashMap();
		Product product = null;
		try {
			product = productDao.getProductById(Long.parseLong(productId));
		} catch (SQLException | InvalidArgumentsException e) {
			e.printStackTrace();
		}
		if(product != null) {
			if (session.getAttribute("cart") == null) {
				user.addProductToShoppingCart(product, 1);
				cart.putAll(user.getCart());			
			}
			else {
				cart.putAll((Map<Product, Integer>)session.getAttribute("cart"));
				if(isExist(Long.parseLong(productId), cart)) {
					for (Entry<Product, Integer> entry : cart.entrySet()) {
						if(entry.getKey().equals(product)) {
							int quantity = entry.getValue();
							user.addProductToShoppingCart(product, quantity+1);
							cart.put(product, quantity+1);
						}
					}
				}
				else {
					cart.put(product, 1);
					user.addProductToShoppingCart(product, 1);
				}
			}	
			session.setAttribute("user", user);
			Order order = new Order(calculatePrice(cart), LocalDateTime.now(), cart);
			session.setAttribute("cart", cart);
			session.setAttribute("order", order);
			model.addAttribute("cart", cart);
			return "profile";
		}
		else {
			model.addAttribute("info", "nullproduct");
			return "profile";
		}
		
	}
	@RequestMapping(value = "/cart/delete/{productId}", method = RequestMethod.GET)
	protected String delete(HttpSession session,@PathVariable (value = "productId") String productId,Model model) {
		User user = (User) session.getAttribute("user");
		ConcurrentHashMap<Product, Integer> cart = new ConcurrentHashMap<>();
		Product product = null;
		try {
			product = productDao.getProductById(Long.parseLong(productId));
		} catch (NumberFormatException | SQLException | InvalidArgumentsException e) {
			System.out.println("exception");
			e.printStackTrace();
		}
		cart.putAll((Map<Product, Integer>)session.getAttribute("cart"));
		if(isExist(Long.parseLong(productId), cart)) {
			user.removeProductFromShoppingCart(product);
			for (Entry<Product, Integer> entry : cart.entrySet()) {
				if(entry.getKey().equals(product)) {
					int quantity = entry.getValue();
					if(quantity == 1) {
						cart.remove(product);
					}
					else {
						cart.put(product, quantity-1);
					}
				}
			}
		}
		model.addAttribute("cart", cart);
		session.setAttribute("cart", cart);
		return "profile";
	}
	
	private boolean isExist(long id,Map<Product, Integer> cart) {
		for (Entry<Product, Integer> e : cart.entrySet()) {
			if(e.getKey().getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	private double calculatePrice(Map<Product,Integer> cart) {
		double price = 0;
		for (Entry<Product,Integer> e : cart.entrySet()) {
			price += (e.getKey().getPrice()*e.getValue());
		}
		return price;
	}
	
	@RequestMapping(value="/order",method = RequestMethod.GET)
	public String getOrder(HttpSession session) {
		User user = (User) session.getAttribute("user");
		Map<Product,Integer> productsInOrder = new HashMap<>();
		productsInOrder.putAll((Map<Product, Integer>) ((Order)session.getAttribute("order")).getProducts());
		session.setAttribute("productsInOrder", productsInOrder);
		session.setAttribute("totalPrice", calculatePrice(productsInOrder));
		List<Address>addresses = new ArrayList<>();
		try {
			addresses.addAll(addressDao.getAllUserAddresses(user));
		} catch (SQLException | InvalidArgumentsException e) {
			System.out.println(e.getMessage());
		}
		session.setAttribute("userAddresses", addresses);
		List<Restaurant>restaurants = new ArrayList<>();
		try {
			restaurants.addAll(restaurantDao.getAllRestaurants());
		} catch (InvalidArgumentsException e) {
			System.out.println(e.getMessage());
		}
		session.setAttribute("restaurants", restaurants);
		return "order";
	}
	
	@RequestMapping(value="/order",method = RequestMethod.POST)
	public String makeOrder(HttpSession session) {
		Order order = (Order) session.getAttribute("order");
		User user = (User) session.getAttribute("user");
	//	order.setAddress(address); get address from form in order.jsp
		order.setUserId(user.getId());
		try {
			orderDao.addNewOrder(order);
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return "logged";
	}
}
