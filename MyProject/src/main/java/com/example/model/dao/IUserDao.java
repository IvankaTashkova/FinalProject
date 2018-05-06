package com.example.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.example.model.Product;
import com.example.model.User;
import com.example.util.InvalidArgumentsException;

public interface IUserDao {
	
	public List<User> getAllUsers() throws SQLException, InvalidArgumentsException;
	
	public User getUserById(long id) throws SQLException, InvalidArgumentsException;
	
	public User getUserByUsername(String username) throws SQLException, InvalidArgumentsException;
	
	public void deleteUser(User user) throws SQLException;
	
	public void updateUser(User user) throws SQLException;
	
	public boolean addNewUser(User user) throws SQLException;
	
	public boolean checkUserExist(String username) throws SQLException;
	
	public boolean checkUserData(String username, String password) throws SQLException;
	
	public void addFavoriteProduct(User user, long productId) throws SQLException;
	
	public void deleteProductFromFavorites(User user, long productId) throws SQLException;
	
	public boolean checkIfFavoriteProduct(User user, long productId) throws SQLException;
	
	public List<Product> getFavoriteProducts(User user) throws SQLException, InvalidArgumentsException;
}