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
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.Address;
import com.example.model.Dough;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.Restaurant;
import com.example.model.Size;
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
	
	@RequestMapping(value = "/cart/add/{productId}", method = RequestMethod.POST)
	protected String getCart(HttpSession session,@PathVariable (value = "productId") String productId,Model model,@RequestParam String size,@RequestParam String dough) {
		User user = (User) session.getAttribute("user");
		ConcurrentHashMap<Product, Integer> cart = new ConcurrentHashMap();
		Product product = null;
		try {
			product = productDao.getProductById(Long.parseLong(productId));
			Size getSize = productDao.getSizeByName(size);
			Dough getDough = productDao.getDoughByName(dough);
			product.setSize(getSize);
			product.setDough(getDough);
		} catch (SQLException | InvalidArgumentsException e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
		if(product != null) {
			if (session.getAttribute("cart") == null) {
				user.addToShoppingCart(product, 1);
				cart.putAll(user.getCart());			
			}
			else {
				cart.putAll((Map<Product, Integer>)session.getAttribute("cart"));
				cart.put(product, 1);
				
			}	
			session.setAttribute("user", user);
			session.setAttribute("cart", cart);
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
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
		cart.putAll((Map<Product, Integer>)session.getAttribute("cart"));
		if(isExist(Long.parseLong(productId), cart)) {
			for (Entry<Product,Integer> entry : cart.entrySet()) {
				if(entry.getKey().equals(product)) {
					cart.remove(entry.getKey(), entry.getValue());
				}
			}
			user.removeProductFromShoppingCart(product);
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
	public String getOrder(HttpSession session,Model model) {
		User user = (User) session.getAttribute("user");
		ConcurrentHashMap<Product, Integer> productsInCart = new ConcurrentHashMap();
		List<Address>addresses = new ArrayList<>();
		if (session.getAttribute("cart") == null) {
			model.addAttribute("cartMessage", "Your shopping cart is empty");
			return "profile";	
		}
		else {
			productsInCart.putAll((Map<Product, Integer>)session.getAttribute("cart"));
			try {
				addresses.addAll(addressDao.getAllUserAddresses(user));
			} catch (SQLException | InvalidArgumentsException e) {
				e.printStackTrace();
				model.addAttribute("exception", e);
				return "error";
			}
		}	
		model.addAttribute("userAddresses", addresses);
		session.setAttribute("user", user);
		session.setAttribute("productsInCart", productsInCart);
		model.addAttribute("productsInCart", productsInCart);
		model.addAttribute("totalPrice", calculatePrice(productsInCart));
		session.setAttribute("totalPrice", calculatePrice(productsInCart));
		List<Restaurant>restaurants = new ArrayList<>();
		try {
			restaurants.addAll(restaurantDao.getAllRestaurants());
			model.addAttribute("restaurants", restaurants);
			session.setAttribute("restaurants", restaurants);
		} catch (InvalidArgumentsException e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
		session.setAttribute("restaurants", restaurants);
		return "order";
	}
	
	@RequestMapping(value="/order",method = RequestMethod.POST)
	public String makeOrder(HttpSession session,Model model,@RequestParam String useraddress,@RequestParam String restaurant){
		String address;
		if(useraddress.equals("default") && restaurant.equals("default")) {
			model.addAttribute("info", "You did not select address or restaurant!");
			return "order";
		}
		else {
			if(!useraddress.equals("default")) {
				address =  useraddress;
			}else {
				address = restaurant;
			}
		}
		Address orderAddress;
		try {
			orderAddress = addressDao.getAddressByLocation(address);
		} catch (SQLException e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
		double price = (double) session.getAttribute("totalPrice");
		HashMap<Product, Integer> products = new HashMap<>();
		products.putAll((ConcurrentHashMap<Product, Integer>) session.getAttribute("productsInCart"));
		Order order = new Order(price, LocalDateTime.now(),products);
		User user = (User) session.getAttribute("user");
		order.setAddress(orderAddress);
		order.setUserId(user.getId());
		try {
			orderDao.addNewOrder(order);
			orderDao.addOrderProducts(order);
			session.setAttribute("cart", null);
			user.clearCart();
		} catch (SQLException e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
		return "profile";
	}
		
}
