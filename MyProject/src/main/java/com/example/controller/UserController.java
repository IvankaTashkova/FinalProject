package com.example.controller;

import java.sql.SQLException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		password = BCrypt.hashpw(password, BCrypt.gensalt());
			try {
				if(userDao.checkUserData(username, password)) {
					user = userDao.getUserByUsername(username);
				}
			} catch (SQLException | InvalidArgumentsException e) {
				e.printStackTrace();
				model.addAttribute("exception",e);
				return "error";
			}
		
		if (user == null) {
			model.addAttribute("error", "You are not logged in.Try again!");
			return "login";
		} else {
			session.setAttribute("user", user);
			return "logged";
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
			HttpSession session,
			@RequestParam String action) {
		User user = null;
		password = BCrypt.hashpw(password, BCrypt.gensalt());
		confirmpassword = BCrypt.hashpw(confirmpassword, BCrypt.gensalt());
		if (password.equals(confirmpassword)) {
			try {
				user = new User(Long.parseLong(id),username, firstname, lastname, phoneNumber, password, email);
			} catch (InvalidArgumentsException e) {
				model.addAttribute("info","Incorrect data! Try again!");
				System.out.println(e.getMessage());
			}
		}
		if(action.equals("update")){
			if (user != null) {			
				try {
					user.setFirstName(firstname);
					user.setLastName(lastname);
					user.setUsername(username);
					user.setPassword(password);
					user.setEmail(email);
					user.setPhoneNumber(phoneNumber);
					user.setId(Long.parseLong(id));
					UserManager.getInstance().updateUser(user);
					model.addAttribute("info","Changes saved!");				
				} catch (SQLException|InvalidArgumentsException e) {
					model.addAttribute("info",e.getMessage());
				}
			} 
			return "profile";
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
		password =  BCrypt.hashpw(password, BCrypt.gensalt());
		confirmpassword = BCrypt.hashpw(confirmpassword, BCrypt.gensalt());
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
				if(userDao.addNewUser(user)) {
					return "login";
				}
				else {
					model.addAttribute("info", "Your registration failed.Please try again.");
					return "register";
				}	
			} catch (SQLException e) {
				model.addAttribute("exception",e);
				return "error";
			}
		}
		return "register"; 
	}
	
	@RequestMapping(value = "/register",method = RequestMethod.GET)
	public String getRegister() {
		return "register";
	}
}
