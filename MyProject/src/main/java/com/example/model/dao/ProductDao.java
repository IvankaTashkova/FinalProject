package com.example.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.db.DBManager;
import com.example.model.Dough;
import com.example.model.Ingredient;
import com.example.model.Product;
import com.example.model.Size;
import com.example.model.Status;
import com.example.util.InvalidArgumentsException;


@Component
public class ProductDao {
	
	private Connection connection = DBManager.getInstance().getConnection();
	
	public ProductDao() {
		connection = DBManager.getInstance().getConnection();
	}
	
	public List<Product> getAllProducts() throws SQLException, InvalidArgumentsException {
		connection.setAutoCommit(false);
		String sqlSelectAllProducts = "SELECT product_id,name,price FROM products;";
		List<Product> products = new ArrayList<>();
		try {
			try(PreparedStatement ps = connection.prepareStatement(sqlSelectAllProducts,Statement.RETURN_GENERATED_KEYS)){
				ResultSet set = ps.executeQuery();
				while (set.next()) {
					long product_id =  set.getLong("product_id");
					String name = set.getString("name");
					Double price = set.getDouble("price");
				//	HashSet<Ingredient> ingredients = getProductIngredients(product_id);
					Product product = new Product(product_id, name, price,new HashSet<>());
					products.add(product);
					//ingredients.clear();
				}
			}
		}
		catch (SQLException e) {
			connection.rollback();
			throw e;
		}
		finally {
			connection.setAutoCommit(true);
		}
		
		return products;
	}
	
	public HashSet<Ingredient> getProductIngredients(long productId) throws SQLException, InvalidArgumentsException{
		HashSet<Ingredient> ingredients = new HashSet<>();
		String sqlGetProductIngredients = "SELECT i.ingredient_id as ingredient_id, i.name as name FROM products_has_ingredients p JOIN ingredients i ON p.ingredient_id = i.ingredient_id WHERE p.product_id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sqlGetProductIngredients)){
			ps.setLong(1, productId);
			ResultSet set = ps.executeQuery();
			while(set.next()) {
				long id = set.getLong("ingredient_id");
				String name = set.getString("name");
				ingredients.add(new Ingredient(id,name));
			}
		}
		return ingredients;
	}
	
	public void addNewProduct(Product product) throws SQLException {
		connection.setAutoCommit(false);
		String sqlInsertProduct = "INSERT INTO products (name, price) VALUES(?,?)";
		try {
			try(PreparedStatement ps = connection.prepareStatement(sqlInsertProduct,Statement.RETURN_GENERATED_KEYS)){
				ps.setString(1, product.getName());
				ps.setDouble(2, product.getPrice());
				ps.executeUpdate();
				try(ResultSet rs = ps.getGeneratedKeys()){
					if(rs.next()) {
						product.setId(rs.getLong(1));
					}
				}
			}
			addIngredients(product);
			connection.commit();
		}
		catch (SQLException e) {
			connection.rollback();
			throw e;
		}
		finally {
			connection.setAutoCommit(true);
		}
	}
	
	public void addIngredients(Product product) {
		ArrayList<Ingredient> ingredients = new ArrayList<>(product.getIngredients());
		for(int i = 0; i < ingredients.size(); i++) {
			String sql = "INSERT INTO products_has_ingredients (product_id, ingredient_id) VALUES(?,?)";
			try(PreparedStatement ps =connection.prepareStatement(sql)){
				ps.setLong(1, product.getId());
				ps.setLong(1, ingredients.get(i).getId());
				ps.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
}
	
	public void deleteProduct(Product product) throws SQLException{
		String sqlDeleteProduct = "DELETE FROM products \nWHERE product_id = ?;";
		try(PreparedStatement ps = connection.prepareStatement(sqlDeleteProduct)){
			ps.setLong(1, product.getId());
			ps.executeUpdate();
		}
		
	}
	
	public void updateProduct(Product product) throws SQLException {
		String sqlUpdateProduct  = "UPDATE products SET name,price WHERE product_id = ?;";
		try(PreparedStatement ps = connection.prepareStatement(sqlUpdateProduct)){
			ps.setString(1, product.getName());
			ps.setDouble(2, product.getPrice());
			ps.setLong(3, product.getId());
			ps.executeUpdate();
		} 
	}
	public Product getProductById(long id) throws SQLException, InvalidArgumentsException {
		Product product = null;
		String sqlSelectProduct = "SELECT name,price FROM products WHERE product_id = ? ;";
		try(PreparedStatement ps = connection.prepareStatement(sqlSelectProduct)){
			ps.setLong(1, id);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				String name = result.getString("name");
				double price = result.getDouble("price");
				product = new Product(id, name, price,getProductIngredients(id));
			}
		}
			
		return product;
	}	
	
	public Size getSizeByName(String name) throws SQLException {
		Size size = null;
		String sqlSelectStatus = "SELECT size_id,name FROM sizes WHERE name = ?";
		try(PreparedStatement ps = connection.prepareStatement(sqlSelectStatus)){
			ps.setString(1,name);
			try(ResultSet result =  ps.executeQuery()){
				if(result.next()) {
					long id = result.getLong("size_id");
					String sizeName = result.getString("name");
					size = new Size(id,sizeName);
				}
			}
		}
		return size;
	}
	
	public Dough getDoughByName(String name) throws SQLException {
		Dough dough = null;
		String sqlSelectStatus = "SELECT dough_id,name FROM doughs WHERE name = ?";
		try(PreparedStatement ps = connection.prepareStatement(sqlSelectStatus)){
			ps.setString(1,name);
			try(ResultSet result =  ps.executeQuery()){
				if(result.next()) {
					long id = result.getLong("dough_id");
					String doughName = result.getString("name");
					dough = new Dough(id, doughName);
				}
			}
		}
		return dough;
	}
}
