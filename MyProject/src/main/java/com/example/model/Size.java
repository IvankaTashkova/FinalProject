package com.example.model;

public class Size {
	
	
	private String name;
	private long id;
	
	public Size( long id,String name) {
		setId(id);
		setName(name);
	}
	
	public String getName() {
		return name;
	}
	
	public long getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setId(long id) {
		this.id = id;
	}

}