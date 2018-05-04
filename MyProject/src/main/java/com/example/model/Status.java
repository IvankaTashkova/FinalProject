package com.example.model;

public enum Status {
	
	REGISTRATED("registrated",1), SEND("send",2), DELIVERED("delivered",3);
	
	private String name;
	private int id;
	
	private Status(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
}

}
