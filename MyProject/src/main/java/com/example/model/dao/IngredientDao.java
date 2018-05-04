package com.example.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import com.example.db.DBManager;
import com.example.model.Ingredient;
import com.example.util.InvalidArgumentsException;


@Component
public class IngredientDao implements IIngredientDao{
	
	private Connection connection;
	
	public IngredientDao() {
		connection = DBManager.getInstance().getConnection();
	}
	
	@Override
	public List<Ingredient> getAllIngredients() throws SQLException, InvalidArgumentsException {
		
		String sqlSelectAllIngredients = "SELECT ingredient_id,name FROM ingredients;";
		List<Ingredient> ingredients = new ArrayList<>();
		try(PreparedStatement ps = connection.prepareStatement(sqlSelectAllIngredients)){
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				long id = set.getInt("ingredient_id");
				String name = set.getString("name");
				ingredients.add(new Ingredient(id,name));
		}
		}
		return Collections.unmodifiableList(ingredients);
	}

	@Override
	public void deleteIngredient(Ingredient ingredient) throws SQLException{
		
		String sqlDeleteIngredient = "DELETE FROM ingredients WHERE ingredient_id = ?;";
		
		try(PreparedStatement ps = connection.prepareStatement(sqlDeleteIngredient)){
			ps.setLong(1, ingredient.getId());
			ps.executeUpdate();
		}
		
	}

	@Override
	public void updateIngredient(Ingredient ingredient) throws SQLException{
		
		String sqlUpdateIngredient  = "UPDATE ingredients SET name = ? WHERE ingredient_id = ?;";
		
		try(PreparedStatement ps = connection.prepareStatement(sqlUpdateIngredient)){
			ps.setString(1, ingredient.getName());
			ps.setLong(2, ingredient.getId());
			ps.executeUpdate();
		}

		
	}

	@Override
	public void addNewIngredient(Ingredient ingredient) throws SQLException{
		
		String sqlInsertIngredient = "INSERT INTO ingredients (name) VALUES(?)";
		
		try(PreparedStatement ps = connection.prepareStatement(sqlInsertIngredient)){
			ps.setString(1, ingredient.getName());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			ingredient.setId(rs.getLong("ingredient_id"));
		}

	}

}