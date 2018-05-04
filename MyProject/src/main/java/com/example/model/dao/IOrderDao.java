package com.example.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.example.model.Order;
import com.example.model.Status;
import com.example.model.User;
import com.example.util.InvalidArgumentsException;

public interface IOrderDao {

	public List<Order> getAllOrderByUser(User user) throws SQLException, InvalidArgumentsException;
	
	public void deleteOrder(Order order) throws SQLException;
	
	public void updateOrderStatus(Order order, Status status) throws SQLException;
	
	public void addNewOrder(Order order) throws SQLException;
}
