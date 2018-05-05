package com.example.controller;

import java.sql.SQLException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.controller.manager.UserManager;
import com.example.model.User;
import com.example.model.dao.UserDao;
import com.example.util.BCrypt;
import com.example.util.InvalidArgumentsException;

@Controller
public class UserController {
	
	@Autowired
	private UserDao userDao;

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
		if (user == null) {
			return "login";
		}
		model.addAttribute("user", user);
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

	@RequestMapping(value = "/profile/{id}",method = RequestMethod.GET)
	public String getProfile(@PathVariable (value = "id") long id,Model model,HttpSession session) {
		
		return "profile";
	}
	
	@RequestMapping(value = "/profile/{id}",method = RequestMethod.POST)
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
		User user = (User) session.getAttribute("user");
		//password = BCrypt.hashpw(password, BCrypt.gensalt());
		//confirmpassword = BCrypt.hashpw(confirmpassword, BCrypt.gensalt());
		if (password.equals(confirmpassword) && user != null) {
			try {
				user.setFirstName((firstname == null) ? user.getFirstName() : firstname);
				user.setLastName((lastname == null) ? user.getFirstName() : lastname);
				user.setUsername((username == null) ? user.getFirstName() : username);
				user.setPassword((password == null) ? user.getFirstName() : password);
				user.setEmail((email == null) ? user.getFirstName() : email);
				user.setPhoneNumber((phoneNumber == null) ? user.getFirstName() : phoneNumber);
				user.setId(id);
				userDao.updateUser(user);
				model.addAttribute("info","Changes saved!");	
				session.setAttribute("user", user);
			} catch (InvalidArgumentsException | SQLException e) {
				model.addAttribute("info","Incorrect data! Try again!");
				System.out.println(e.getMessage());
				return "profile"; 
			}
		}
		else {
			model.addAttribute("info", "Passwords don't match!");
			return "profile";
		}
		model.addAttribute("info", "Invalid user");
		return "profile";
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
}
