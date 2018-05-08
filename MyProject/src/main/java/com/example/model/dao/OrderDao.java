package com.example.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.db.DBManager;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.Status;
import com.example.model.User;
import com.example.util.InvalidArgumentsException;


@Component
public class OrderDao implements IOrderDao{
	
	private Connection connection;
	
	@Autowired
	private ProductDao productDao;
	
	public OrderDao() {
		connection = DBManager.getInstance().getConnection();
	}
	
	@Override
	public List<Order> getAllOrderByUser(User user) throws SQLException, InvalidArgumentsException{
		connection.setAutoCommit(false);
		String sqlSelectAllOrders = "SELECT order_id,price,date,user_id,status_id,address_id,restaurant_id FROM orders"
				+ " WHERE user_id = ?;";
		List<Order> orders = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(sqlSelectAllOrders)){
			ps.setLong(1, user.getId());
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				long id = result.getLong("order_id");
				Double price = result.getDouble("price");
				Timestamp date = result.getTimestamp("date");
				long userId = result.getLong("user_id");
				long statusId = result.getLong("status_id");
				Status status = getStatusById(statusId);
				Order order = new Order(id, price, Order.localDateFromTimestamp(date), status, userId);
				order.setProducts(getOrderProducts(id));
				orders.add(order);
			}
		}catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}finally {
			connection.setAutoCommit(true);
		}
		return orders;
	}

	@Override
	public void deleteOrder(Order order) throws SQLException {
		String sqlDeleteOrder = "DELETE FROM orders WHERE order_id = ?;";
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(sqlDeleteOrder);
			ps.setLong(1, order.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateOrderStatus(Order order, Status status) throws SQLException {
		String sqlUpdateIngredient  = "UPDATE orders SET status_id = ? WHERE order_id = ?;";
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(sqlUpdateIngredient);
			ps.setLong(1,status.getId());
			ps.setLong(2, order.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addNewOrder(Order order) throws SQLException {
		String sqlInsertOrder = "INSERT INTO orders (price, date,address_id, user_id, status_id) VALUES(?,?,?,?,?)";
		PreparedStatement ps;
		try{
			ps = connection.prepareStatement(sqlInsertOrder,Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, order.getPrice());
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setLong(3, order.getAddress().getId());;
			ps.setLong(4, order.getUserId());
			ps.setInt(5, 1);
			ps.executeUpdate();
			try(ResultSet rs = ps.getGeneratedKeys()){
				if(rs.next()) {
					order.setId(rs.getLong(1));
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
	}
	
	public void addOrderProducts(Order order) throws SQLException {
		ArrayList<Product> products = new ArrayList<>(order.getProducts().keySet());
		for(int i = 0; i < products.size(); i++) {
			String sql = "INSERT INTO orders_has_products (order_id, product_id,quantity,dough_id) VALUES(?,?,?,?)";
			try(PreparedStatement ps = connection.prepareStatement(sql)){
				ps.setLong(1, order.getId());
				ps.setLong(2, products.get(i).getId());
				ps.setInt(3, 1);
				ps.setLong(4, products.get(i).getDough().getId());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Status getStatusById(long id) throws SQLException {
		Status status = null;
		String sqlStatus = "SELECT status_id,name FROM statuses"
				+ " WHERE status_id = ?;";
		try(PreparedStatement ps = connection.prepareStatement(sqlStatus)){
			ps.setLong(1, id);
			try(ResultSet result = ps.executeQuery()){
				if(result.next()) {
					long status_id = result.getLong("status_id");
					String name = result.getString("name");
					status = new Status(status_id,name);
				}
			}
		}		
		return status;
	}
	
	public HashMap<Product,Integer> getOrderProducts(long id) throws SQLException, InvalidArgumentsException{
		connection.setAutoCommit(false);
		HashMap<Product,Integer> products = new HashMap<>();
		Product product = null;
		String sqlSelectOrdeProducts =  "SELECT product_id,quantity FROM orders_has_products"
				+ " WHERE order_id = ?;";
		try(PreparedStatement ps = connection.prepareStatement(sqlSelectOrdeProducts)) {
			ps.setLong(1, id);
			try(ResultSet result = ps.executeQuery()){
				while(result.next()) {
					long productId = result.getLong("product_id");
					int quantity =  result.getInt("quantity");
					product = productDao.getProductById(productId); 
					products.put(product,quantity);
				}
			}
		}catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}
		finally {
			connection.setAutoCommit(true);
		}
		return products;
	}
}
