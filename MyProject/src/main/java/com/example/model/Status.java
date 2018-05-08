package com.example.model;

public class Status {
	
	private String name;
	private long id;
	
	public Status(long id,String name) {
		setId(id);
		setName(name);
	}

	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
