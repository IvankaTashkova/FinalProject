package com.example.model;

public class Dough {

	private long id;
	private String name;
	
	public Dough() {
		
	}
	
	public Dough(long id,String name) {
		setId(id);
		setName(name);
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
	
	public void setName(String name) {
		this.name = name;
	}
}
