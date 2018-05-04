package com.example.controller.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.Ingredient;
import com.example.model.dao.IngredientDao;
import com.example.util.InvalidArgumentsException;

import java.sql.SQLException;

@Component
public class IngredientManager {
	
	@Autowired
	private IngredientDao ingredientDao;
	
	private static IngredientManager instance;
	
	private IngredientManager() {
		
	}

	public static IngredientManager getInstance() {
		if (instance == null) {
			instance = new IngredientManager();
		}
		return instance;
	}

	public void addNewIngredient(Ingredient ingredient) throws SQLException{
		ingredientDao.addNewIngredient(ingredient);
	}
	
	public void deleteIngredient(Ingredient ingredient) throws SQLException{
		ingredientDao.deleteIngredient(ingredient);
	}
	
	public void updateIngredient(Ingredient ingredient) throws SQLException{
		ingredientDao.updateIngredient(ingredient);
	}
	
	public List<Ingredient> getListOfAllIngredients() throws SQLException, InvalidArgumentsException{
		List<Ingredient> ingredients = ingredientDao.getAllIngredients();
		
		return ingredients;		
	}
	

}
