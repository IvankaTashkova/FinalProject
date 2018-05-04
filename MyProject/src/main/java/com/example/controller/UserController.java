package com.example.controller;

import java.sql.SQLException;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.controller.manager.UserManager;
import com.example.model.User;
import com.example.util.InvalidArgumentsException;

@Controller
public class UserController {
	
	/*
	@Autowired
	private UserDao userDao;
	*/

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
		
		try {
			user = UserManager.getInstance().logIn(username, password);
		} catch (SQLException | InvalidArgumentsException e) {
			e.getMessage();
			model.addAttribute("exception", e);
			return "error";
			
		}
		if (user != null) {
			session.setAttribute("user", user);
			return "logged";
		} else {
			model.addAttribute("error", "You are not logged in.Try again!");
			return "login";
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
	
	@RequestMapping(value = "/profile",method = RequestMethod.POST)
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
		User user = null;
		
		if (password.equals(confirmpassword)) {
			try {
				user = new User(username, firstname, lastname, phoneNumber, password, email);
			} catch (InvalidArgumentsException e) {
				model.addAttribute("info","Incorrect data! Try again!");
				System.out.println(e.getMessage());
			}
		}
		
		if (user != null) {
			try {
				user.setId(Integer.parseInt(id));
				UserManager.getInstance().updateUser(user);
				model.addAttribute("info","Changes saved!");				
			} catch (SQLException e) {
				model.addAttribute("info",e.getMessage());
			}
		} 
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
		User user = null;
		
		if (password.equals(confirmpassword)) {
			try {
				user = new User(username, firstname, lastname, phoneNumber, password, email);
			} catch (InvalidArgumentsException e) {
				model.addAttribute("info", "Invalid input data! Please try again!");
				System.out.println(e.getMessage());
				return "register";
			}
		}
		if (user != null) {
			try {
				if(UserManager.getInstance().register(user)) {
					return "login";
				}
				else {
					model.addAttribute("info", "Your registration failed.Please try again.");
				}	
			} catch (SQLException e) {
				model.addAttribute("info","Error occured:"+e.getMessage());
			}
		} 
		return "register";
	}
}
