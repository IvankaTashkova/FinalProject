package com.example.controller.manager;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.User;
import com.example.model.dao.UserDao;
import com.example.util.InvalidArgumentsException;

@Component
public class UserManager {
	
	private static UserManager instance;
	@Autowired
	private UserDao userDao;
	
	private UserManager() {
		
	}

	public static UserManager getInstance() {
		if (instance == null) {
			instance = new UserManager();
		}
		return instance;
	}
	
	public boolean register(User user) throws SQLException {
		userDao.addNewUser(user);
		
		return false;
	}
	
	public User logIn(String username, String password) throws SQLException, InvalidArgumentsException {
		User user = null;
		if(userDao.checkUserData(username, password)) {
			user = userDao.getUserByUsername(username);
		}
		return user;
	}
	
	public void updateUser(User user) throws SQLException {
		userDao.updateUser(user);
	}

	public void deleteUser(User user) throws SQLException {
		userDao.deleteUser(user);
	}

}
