package com.example.controller.manager;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.Order;
import com.example.model.Product;
import com.example.model.Status;
import com.example.model.User;
import com.example.model.dao.OrderDao;
import com.example.util.InvalidArgumentsException;

@Component
public class OrderManager {
	
	private static OrderManager instance;
	
	@Autowired
	private OrderDao orderDao;
	
	private OrderManager() {
	
	}

	public static OrderManager getInstance() {
		if (instance == null) {
			instance = new OrderManager();
		}
		return instance;
	}
	
	public Order createNewOrder( Map<Product, Integer> products) {
		Order order = new Order(calculateOrderPrice(products), LocalDateTime.now(), Status.REGISTRATED, products);
		return order;
	}
	
	public double calculateOrderPrice(Map<Product, Integer> products) {
		double money = 0;
		for(Entry<Product, Integer> e: products.entrySet()) {
			money += e.getKey().getPrice()*e.getValue();
		}
		return money;
	}
	
	public List<Order> allUsersOrders(User user) throws SQLException, InvalidArgumentsException{
		 List<Order> orders= orderDao.getAllOrderByUser(user);
		
		return orders;
		
	}

}
