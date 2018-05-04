package com.example.model;

import javax.validation.constraints.NotNull;

import com.example.util.InvalidArgumentsException;

public class Restaurant {
	
	private long id;
	@NotNull
	private String name;
	@NotNull
	private String address;
	
	public Restaurant(String name, String address) throws InvalidArgumentsException {
		setName(name);
		setAddress(address);
	}
	
	public Restaurant(long id, String name, String address) throws InvalidArgumentsException {
		this(name,address);
		setId(id);
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) throws InvalidArgumentsException {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) throws InvalidArgumentsException {
		this.address = address;
	}
	
	

}
