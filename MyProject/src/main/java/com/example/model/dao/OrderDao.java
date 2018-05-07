package com.example.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.example.db.DBManager;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.Status;
import com.example.model.User;


@Component
public class OrderDao implements IOrderDao{
	
	private Connection connection;
	
	public OrderDao() {
		connection = DBManager.getInstance().getConnection();
	}
	
	@Override
	public List<Order> getAllOrderByUser(User user){
		String sqlSelectAllOrders = "SELECT order_id,price,date,user_id,status_id,address_id,restaurant_id FROM orders"
				+ " WHERE user_id = ?;";
		List<Order> orders = new ArrayList<>();
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(sqlSelectAllOrders);
			ps.setLong(1, user.getId());
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				long id = result.getLong("order_id");
				Double price = result.getDouble("price");
				Timestamp date = result.getTimestamp("date");
				long userId = result.getLong("user_id");
				long status = result.getLong("status_id");
				Order order = new Order(id, price, Order.localDateFromTimestamp(date), status, userId);
				orders.add(order);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return orders;
	}

	@Override
	public void deleteOrder(Order order) throws SQLException {
		String sqlDeleteOrder = "DELETE FROM orders \nWHERE order_id = ?;";
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(sqlDeleteOrder);
			ps.setLong(1, order.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void updateOrderStatus(Order order, Status status) throws SQLException {
		String sqlUpdateIngredient  = "UPDATE orders \nSET status_id = ?\nWHERE order_id = ?;";
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(sqlUpdateIngredient);
			ps.setInt(1,status.getId());
			ps.setLong(2, order.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void addNewOrder(Order order) throws SQLException {
		String sqlInsertOrder = "INSERT INTO orders (price, date,address, user_id, status_id) \nVALUES(?,?,?,?,?)";
		PreparedStatement ps;
		try{
			ps = connection.prepareStatement(sqlInsertOrder,Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, order.getPrice());
			ps.setDate(2,null);
			ps.setString(3, order.getAddress());
			ps.setLong(4, order.getUserId());
			ps.setInt(5, 1);
			ps.executeUpdate();
			try(ResultSet rs = ps.getGeneratedKeys()){
				if(rs.next()) {
					order.setId(rs.getInt(1));
				}
			}
			catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
				
	}
	
	public void addOrderProducts(Order order) throws SQLException {
		ArrayList<Product> products = new ArrayList<>(order.getProducts().keySet());
		for(int i = 0; i < products.size(); i++) {
			String sql = "INSERT INTO orders_has_products (order_id, product_id) \\nVALUES(?,?)";
			try(PreparedStatement ps = connection.prepareStatement(sql)){
				ps.setLong(1, order.getId());
				ps.setLong(1, products.get(i).getId());
				ps.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
