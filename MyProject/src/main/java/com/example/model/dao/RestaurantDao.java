package com.example.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.example.db.DBManager;
import com.example.model.Restaurant;
import com.example.util.InvalidArgumentsException;


@Component
public class RestaurantDao implements IRestaurantDao{
	private Connection connection;
	
	public RestaurantDao() {
		connection = DBManager.getInstance().getConnection();
	}
	

	@Override
	public List<Restaurant> getAllRestaurants() throws InvalidArgumentsException {
		String sqlSelectAllRestaurants = "SELECT restaurant_id,name,address FROM restaurants;";
		List<Restaurant> restaurants = new ArrayList<>();
		
		try(PreparedStatement ps = connection.prepareStatement(sqlSelectAllRestaurants)){
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				long id = result.getLong("restaurant_id");
				String name = result.getString("name");
				String address = result.getString("address");
				Restaurant restaurant = new Restaurant(id, name, address);
				restaurants.add(restaurant);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return restaurants;
	}

	@Override
	public void deleteRestaurant(Restaurant restaurant) throws SQLException {
		String sqlDeleteRestaurant = "DELETE FROM restaurants WHERE restaurant_id = ?;";
		try(PreparedStatement ps = connection.prepareStatement(sqlDeleteRestaurant)){
			ps.setLong(1, restaurant.getId());
			ps.executeUpdate();
		} 
	}

	@Override
	public void updateRestaurant(Restaurant restaurant) throws SQLException {
		String sqlUpdateAddress  = "UPDATE restaurants SET name = ?, address = ? WHERE restaurant_id = ?;";
		try(PreparedStatement ps = connection.prepareStatement(sqlUpdateAddress)){
			ps.setString(1, restaurant.getName());
			ps.setString(2, restaurant.getAddress());
			ps.setLong(3, restaurant.getId());
			ps.executeUpdate();
		} 
	}

	@Override
	public void addNewRestaurant(Restaurant restaurant) throws SQLException {
		String sqlInsertRestaurant = "INSERT INTO restaurants (name, address) VALUES(?,?)";
		
		try(PreparedStatement ps = connection.prepareStatement(sqlInsertRestaurant)){
			ps.setString(1, restaurant.getName());
			ps.setString(2, restaurant.getAddress());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			restaurant.setId(rs.getLong("restaurant_id"));
		}
	}


}
