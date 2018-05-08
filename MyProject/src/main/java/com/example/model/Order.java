package com.example.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Order {
	
	private long id;
	private long userId;
	private LocalDateTime date;
	private double price;
	private Status status;
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
		this.products = products;
	}
	
	public Order(long id,double price,LocalDateTime date, Status status) {
		setId(id);
		setDate(date);
		setPrice(price);
		setStatus(status);
	}
	
	public Order(long id,double price,LocalDateTime date, Status status,long userId) {
		this(id,price, date, status);
		setUserId(userId);
	}
	
	
	
	public Order(int id,double price,LocalDateTime date, long status, Map<Product, Integer> products) {
		this.id = id;
		this.date = date;
		setId(id);
		this.setProducts(products);
	}
	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
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
	
	public static LocalDateTime localDateFromTimestamp(Timestamp timestamp) {
	    return LocalDateTime
	        .ofInstant(timestamp.toInstant(), ZoneOffset.ofHours(0));
	  }
}
