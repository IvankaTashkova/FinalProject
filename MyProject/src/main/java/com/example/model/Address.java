package com.example.model;

public class Address {
		
	private long id;
	private String location;
	private long userId;
	
	public Address(String location, long userId){
		setLocation(location);
		setUserId(userId);
	}
	
	public Address(long id, String location, long userId){
		this(location,userId);
		setId(userId);
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location){
		this.location = location;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}

}
