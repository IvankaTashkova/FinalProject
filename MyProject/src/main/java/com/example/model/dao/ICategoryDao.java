package com.example.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.example.model.Category;
import com.example.util.InvalidArgumentsException;


public interface ICategoryDao {

	public List<Category> getAllCategories() throws SQLException, InvalidArgumentsException;
	
	public void deleteCategory(Category category) throws SQLException;
	
	public void updateCategory(Category category) throws SQLException;
	
	public void addNewtegory(Category category) throws SQLException;
	
}
