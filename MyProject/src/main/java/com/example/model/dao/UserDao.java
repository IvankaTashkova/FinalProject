package com.example.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.db.DBManager;
import com.example.model.Product;
import com.example.model.User;
import com.example.util.InvalidArgumentsException;



@Component
public class UserDao implements IUserDao{
	private Connection connection;
	
	public UserDao() {
		connection = DBManager.getInstance().getConnection();
	}
	@Autowired
	private ProductDao productDao;

	@Override
	public List<User> getAllUsers() throws SQLException, InvalidArgumentsException {
		String sqlSelectAllUsers = "SELECT * FROM users WHERE isAdmin = ?;";
		List<User> users = new ArrayList<>();
		try(PreparedStatement ps = connection.prepareStatement(sqlSelectAllUsers,Statement.RETURN_GENERATED_KEYS)){
			ps.setBoolean(1, false);
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				int user_id = set.getInt("user_id");
				String firstName = set.getString("first_name");
				String lastName = set.getString("last_name");
				String email = set.getString("email");
				String phoneNumber = set.getString("phone_number");
				String username = set.getString("username");
				String password = set.getString("password");
				User user = new User(username, firstName, lastName, phoneNumber, password, email);
				user.setId(user_id);
				users.add(user);
			}
		}
		return users;
	}
	
	
	@Override
	public User getUserById(int id) throws SQLException, InvalidArgumentsException{
		User user = null;
		String sqlSelectUser = "SELECT user_id,first_name,last_name,email,phone_number,username,password FROM users WHERE user_id = ? ;";
		try(PreparedStatement ps = connection.prepareStatement(sqlSelectUser)){
			ps.setInt(1, id);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				String firstName = result.getString("first_name");
				String lastName = result.getString("last_name");
				String email = result.getString("email");
				String phoneNumber = result.getString("phone_number");
				String username = result.getString("username");
				String password = result.getString("password");
				
				user = new User(username, firstName, lastName, phoneNumber, password, email);
				user.setId(id);
			}
		}
		
		return user;
	}
	
	@Override
	public User getUserByUsername(String username) throws SQLException, InvalidArgumentsException{
		User user = null;
		String sqlSelectUser = "SELECT user_id,first_name,last_name,email,phone_number,username,password \nFROM users \nWHERE username = ? ;";
		try(PreparedStatement ps = connection.prepareStatement(sqlSelectUser)){
			ps.setString(1, username);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				long id = result.getLong("user_id");
				String firstName = result.getString("first_name");
				String lastName = result.getString("last_name");
				String email = result.getString("email");
				String phoneNumber = result.getString("phone_number");
				String password = result.getString("password");
				
				user = new User(id, username, firstName, lastName, phoneNumber, password, email);
			}
		} 
		
		return user;
	}
	
	
	@Override
	public void deleteUser(User user) throws SQLException{
		String sqlDeleteUser = "DELETE FROM users \nWHERE user_id = ?;";
		try(PreparedStatement ps = connection.prepareStatement(sqlDeleteUser)){
			ps.setLong(1, user.getId());
			ps.executeUpdate();
			System.out.println("User has been deleted!");
		}
		
	}

	@Override
	public void updateUser(User user) throws SQLException {
		String sqlUpdateUser  = "UPDATE users SET first_name = ?, last_name = ?,email = ?,phone_number = ?,username = ?,password = ? WHERE user_id = ?;";
		try(PreparedStatement ps = connection.prepareStatement(sqlUpdateUser)){
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPhoneNumber());
			ps.setString(5, user.getUsername());
			ps.setString(6, user.getPassword());
			System.out.println();
			ps.setLong(7, user.getId());
			ps.executeUpdate();
			System.out.println("User data updated!");
		} 
	}

	@Override
	public boolean addNewUser(User user) throws SQLException {
		String sqlInsertUser = "INSERT INTO users (first_name, last_name,username,password,email,phone_number,isAdmin) VALUES(?,?,?,?,?,?,?)";
		try(PreparedStatement ps = connection.prepareStatement(sqlInsertUser,Statement.RETURN_GENERATED_KEYS)){
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getUsername());
			ps.setString(4, user.getPassword());
			ps.setString(5, user.getEmail());
			ps.setString(6, user.getPhoneNumber());
			ps.setInt(7, 0);
			ps.executeUpdate();
			try(ResultSet rs = ps.getGeneratedKeys()){
				if(rs.next()) {
					user.setId(rs.getLong(1));
					return true;
				}
				else{
					  throw new SQLException("Error occured while creating user!");
				}
			}
			finally {
				return false;
			}
		}
	}

	@Override
	public boolean checkUserExist(String username) throws SQLException{
		String sqlCheckUser = "SELECT user_id FROM users WHERE username = ? ;";
		try(PreparedStatement ps = connection.prepareStatement(sqlCheckUser)){
			ps.setString(1, username);
			ResultSet result = ps.executeQuery();
			int counter = 0;
			while(result.next()) {
				counter++;
			}
			if(counter != 0) {
				return true;
			}
		} 
		return false;
	}
	
	@Override
	public boolean checkUserData(String username, String password) throws SQLException{
		String sqlCheckUser = "SELECT password FROM users WHERE username = ? ;";
		try(PreparedStatement ps = connection.prepareStatement(sqlCheckUser,Statement.RETURN_GENERATED_KEYS)){
			ps.setString(1, username);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				String pass = result.getString("password");
				if(pass.equals(password)) {
					return true;
				}
			}
		} 
		
		return false;
	}

	@Override
	public void addFavoriteProduct(User user, long productId) throws SQLException {
		String sqlInsertFavorite = "INSERT INTO user_has_favorite_products (user_id, product_id) VALUES (?,?)";
	
		try(PreparedStatement ps = connection.prepareStatement(sqlInsertFavorite)){
			ps.setLong(1, user.getId());
			ps.setLong(2, productId);
			ps.executeUpdate();
		}
	}

	@Override
	public void deleteProductFromFavorites(User user, long productId) throws SQLException {
		String sqlDeleteFavorite = "DELETE FROM user_has_favorite_products WHERE user_id = ? AND product_id = ?;";
		try(PreparedStatement ps = connection.prepareStatement(sqlDeleteFavorite)){
			ps.setLong(1, user.getId());
			ps.setLong(2, productId);
			ps.executeUpdate();
		}
	}

	@Override
	public boolean checkIfFavoriteProduct(User user, long productId) throws SQLException {
		String sqlCheckFavorite = "SELECT * FROM user_has_favorite_products WHERE user_id = ? AND product_id = ?;";
		try(PreparedStatement ps = connection.prepareStatement(sqlCheckFavorite)){
			ps.setLong(1, user.getId());
			ps.setLong(2, productId);
			ResultSet result = ps.executeQuery();
			int counter = 0;
			while(result.next()) {
				counter++;
			}
			if(counter != 0) {
				return true;
			}
		}
		return false;
	}


	@Override
	public List<Product> getFavoriteProducts(User user) throws SQLException, InvalidArgumentsException {
		ArrayList<Product> favProducts = new ArrayList<>();
		String sqlSelectUser = "SELECT product_id FROM user_has_favorite_products WHERE user_id = ? ;";
		try(PreparedStatement ps = connection.prepareStatement(sqlSelectUser)){
			ps.setLong(1, user.getId());
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				long id = result.getLong("product_id");
				Product product = productDao.getProductById(id);
				favProducts.add(product);
			}
		}
		return favProducts;
	}
	
	

}
