package com.example.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.example.model.Ingredient;
import com.example.util.InvalidArgumentsException;


public interface IIngredientDao {
	
	public List<Ingredient> getAllIngredients() throws SQLException, InvalidArgumentsException;
	
	public void deleteIngredient(Ingredient ingredient) throws SQLException;
	
	public void updateIngredient(Ingredient ingredient) throws SQLException;
	
	public void addNewIngredient(Ingredient ingredient) throws SQLException;

}