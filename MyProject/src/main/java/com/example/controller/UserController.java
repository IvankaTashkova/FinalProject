package com.example.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.Address;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.User;
import com.example.model.dao.AddressDao;
import com.example.model.dao.OrderDao;
import com.example.model.dao.UserDao;
import com.example.util.BCrypt;
import com.example.util.InvalidArgumentsException;

@Controller
public class UserController {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private AddressDao addressDao;
	@Autowired
	private OrderDao orderDao;

	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public String showLogIn() {
		return "login";
	}
	
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public String logIn(Model model,
			@RequestParam String username,
			@RequestParam String password,
			HttpSession session) {
		User user = null;
		//password = BCrypt.hashpw(password, BCrypt.gensalt());
		try {
			if(userDao.checkUserExist(username)) {
				user = userDao.getUserByUsername(username);
				if(password.equals(user.getPassword())) {
					session.setAttribute("user", user);
					return "logged";
				}
				else {
					model.addAttribute("info", "Wrong username or password!");
					return "login";
				}
			}
			else {
				model.addAttribute("info", "Wrong username or password!");
				return "login";
			}
		} catch (SQLException | InvalidArgumentsException e) {
			e.printStackTrace();
			model.addAttribute("exception",e);
			return "error";
		}
	}
	
	@RequestMapping(value = "/logout",method = RequestMethod.POST)
	public String logOut(HttpSession session) {
		session.invalidate();
		return "login";
	}
	
	@RequestMapping(value = "/profile",method = RequestMethod.GET)
	public String getProfileInfo(HttpSession session,Model model) {
		User user = (User) session.getAttribute("user");
		HashSet<Product> favorites =  new HashSet<>();
		List<Address> addresses = new ArrayList<>();
		List<Order> orders = new ArrayList<>();
		if (user == null) {
			return "login";
		}
		if(session.getAttribute("favorites") == null) {
			try {
				favorites.addAll(userDao.getFavoriteProducts(user));	
			} catch (SQLException | InvalidArgumentsException e) {
				e.printStackTrace();
				model.addAttribute("exception", e);
			}
		}
		else {
			favorites.addAll((HashSet<Product>)session.getAttribute("favorites"));
		}
		try {
			orders.addAll(orderDao.getAllOrderByUser(user));
			model.addAttribute("orders", orders);
			session.setAttribute("orders", orders);
		} catch (SQLException | InvalidArgumentsException e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
		try {
			addresses.addAll(addressDao.getAllUserAddresses(user));
			model.addAttribute("addresses", addresses);
			session.setAttribute("addresses", addresses);
		} catch (SQLException | InvalidArgumentsException e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
		}
		if(addresses.isEmpty()) {
			model.addAttribute("addressMessage", "You don't have added addresses!");
		}
		model.addAttribute("user", user);
		model.addAttribute("cart", session.getAttribute("cart"));
		model.addAttribute("favorites", favorites);
		session.setAttribute("favorites", favorites);
		return "profile";
	}
	/*
	@RequestMapping(value = "/profile",method = RequestMethod.GET)
	public String editProfile(Model model,
			@RequestParam String id,
			@RequestParam String firstname, 
			@RequestParam String lastname,
			@RequestParam String username,
			@RequestParam String email,
			@RequestParam String password,
			@RequestParam String confirmpassword,
			@RequestParam String phoneNumber, 
			HttpSession session) {
		User user = (User) session.getAttribute("user");
		//password = BCrypt.hashpw(password, BCrypt.gensalt());
		//confirmpassword = BCrypt.hashpw(confirmpassword, BCrypt.gensalt());
		return "profile";
	}*/

	@RequestMapping(value = "/profile/update/{id}",method = RequestMethod.GET)
	public String getProfile(@PathVariable (value = "id") long id,Model model,HttpSession session) {
		User user = null;
		try {
			user = userDao.getUserById(id);
		} catch (SQLException | InvalidArgumentsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (user == null) {
			return "login";
		}
		model.addAttribute("user", user);
		return "/profile";
	}
	
	@RequestMapping(value = "/profile/update/{id}",method = RequestMethod.POST)
	public String updateProfile(Model model,
			@PathVariable (value = "id") long id,
			@RequestParam String firstname, 
			@RequestParam String lastname,
			@RequestParam String username,
			@RequestParam String email,
			@RequestParam String password,
			@RequestParam String confirmpassword,
			@RequestParam String phoneNumber, 
			HttpSession session) {
		User user = null;
		try {
			user = userDao.getUserById(id);
		} catch (SQLException | InvalidArgumentsException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//password = BCrypt.hashpw(password, BCrypt.gensalt());
		//confirmpassword = BCrypt.hashpw(confirmpassword, BCrypt.gensalt());
		if (password.equals(confirmpassword) && user != null) {
			firstname = (firstname.isEmpty()) ? user.getFirstName() : firstname;
			lastname = (lastname.isEmpty()) ? user.getLastName() : lastname;
			username = (username.isEmpty()) ? user.getUsername() : username;
			password = (password.isEmpty()) ? user.getPassword() : password;
			email = (email.isEmpty()) ? user.getEmail() : email;
			phoneNumber = (phoneNumber.isEmpty()) ? user.getPhoneNumber() : phoneNumber;
			
			try {
				user.setFirstName(firstname);
				user.setLastName(lastname);
				user.setUsername(username);
				user.setPassword(password);
				user.setEmail(email);
				user.setPhoneNumber(phoneNumber);
				user.setId(id);
				userDao.updateUser(user);
				model.addAttribute("info","Changes saved!");	
				session.setAttribute("user", user);
				return "profile";
			} catch (InvalidArgumentsException | SQLException e) {
				model.addAttribute("info","Incorrect data! Try again!");
				e.getStackTrace();
				System.out.println(e.getMessage());
				return "profile"; 
			}
		}
		else {
			model.addAttribute("info", "Passwords don't match!");
			return "profile";
		}
	}
	
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	public String register(Model model,
			@RequestParam String firstname, 
			@RequestParam String lastname,
			@RequestParam String username,
			@RequestParam String email,
			@RequestParam String password,
			@RequestParam String confirmpassword,
			@RequestParam String phoneNumber, 
			HttpSession session) {
		//password =  BCrypt.hashpw(password, BCrypt.gensalt());
		//confirmpassword = BCrypt.hashpw(confirmpassword, BCrypt.gensalt());
		User user = null;
		try {
			if(userDao.checkUserExist(username)) {
				model.addAttribute("info", "Username already taken!");
				return "register";
			}
			else {
				if (password.equals(confirmpassword)) {
					user = new User(username, firstname, lastname, phoneNumber, password, email);
					userDao.addNewUser(user);
					}
				}
			
		} catch (SQLException e ) {
			model.addAttribute("info", "Your registration failed! Please try again!");
			return "register";
		}
		catch (InvalidArgumentsException e) {
			model.addAttribute("info", "Invalid input data! Please try again!");
			return "register";
		}
		
		if (user != null) {
			model.addAttribute("info", "Successful registration! You can now log in!");
			return "login";
		}
		else {
			model.addAttribute("info", "Your registration failed.Please try again.");
			return "register";
		}	
	}
	
	@RequestMapping(value = "/register",method = RequestMethod.GET)
	public String getRegister() {
		return "register";
	}
	
	@RequestMapping(value = "/profile/address/delete/{id}",method = RequestMethod.GET)
	public String deleteAddress(Model model,HttpSession session,@PathVariable("id") String id) {
		User user = (User) session.getAttribute("user");
		Address address = null;
		ArrayList<Address> addresses = new ArrayList<>();
		try {
			address = addressDao.getAddressById(Long.parseLong(id));;
		} catch (SQLException e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
		addresses.addAll((ArrayList<Address>) session.getAttribute("addresses"));
		addresses.remove(address);
		try {
			addressDao.deleteAddress(address);
		} catch (SQLException e) {
				e.printStackTrace();
				model.addAttribute("exception", e);
				return "error";
		}
		model.addAttribute("addresses", addresses);
		session.setAttribute("addresses", addresses);
		return "profile";
	}
	@RequestMapping(value = "/profile/address",method = RequestMethod.GET)
	public String addAddress(Model model,HttpSession session,@RequestParam String address) {
		User user = (User) session.getAttribute("user");
		Address newAddress = null;
		if(!address.isEmpty()) {
			newAddress = new Address(address, user.getId());
			try {
				addressDao.addNewAddress(newAddress);
			} catch (SQLException e) {
				e.printStackTrace();
				model.addAttribute("exception", e);
				return "error";
			}
		}
		
		return "profile";
	}
}
