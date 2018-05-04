package com.example.model;

public enum Size {
	
	AVERAGE("Average",1), 
	BIG("Big",2), 
	JUMBO("Jumbo",3),
	ONE("One",4);
	
	private String name;
	private int id;
	
	private Size(String name, int id) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}

}