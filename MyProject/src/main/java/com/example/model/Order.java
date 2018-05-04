package com.example.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Order {
	
	private long id;
	private long userId;
	private LocalDateTime date;
	private double price;
	private int status;
	private String address;
	private Restaurant restaurant;
	private Map<Product, Integer> products = new HashMap<>();
	
	public Order(double price, LocalDateTime date, Status status,long userId) {
		setPrice(price);
		setDate(date);
		setStatus(status);
		setUserId(userId);
	}
	
	public Order(double price,LocalDateTime date, Status status, Map<Product, Integer> products) {
		setPrice(price);
		setDate(date);
		setStatus(status);
		this.products = products;
	}
	
	public Order(double price,LocalDateTime date, Map<Product, Integer> products) {
		setPrice(price);
		setDate(date);
		setStatus(Status.REGISTRATED);
		this.products = products;
	}
	
	public Order(long id,double price,LocalDateTime date, Status status,long userId) {
		this(price,date,status,userId);
		setId(id);
	}
	
	public Order(long id,double price,LocalDateTime date, int status) {
		this.id = id;
		this.date = date;
		this.status = status;
		setId(id);
	}
	
	public Order(int id,double price,LocalDateTime date, int status, Map<Product, Integer> products) {
		this.id = id;
		this.date = date;
		this.status = status;
		setId(id);
		this.setProducts(products);
	}
	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status.getId();
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long id) {
		this.userId = id;
	}

	
	public Map<Product, Integer> getProducts() {
		return Collections.unmodifiableMap(products);
	}
	
	public void setProducts(Map<Product, Integer> products) {
		if(products != null) {
			this.products = products;
		}
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	public Restaurant getRestaurant() {
		return restaurant;
	}
}
