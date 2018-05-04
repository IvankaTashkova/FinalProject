package com.example.model;

import com.example.util.InvalidArgumentsException;

public class Category {
	
	public static final int CATEGORY_NAME_MIN_LENGTH = 3;
	public static final String INVALID_CATEGORY_NAME = "Invalid ingredient name";
	
	private long id;
	private String name;
	
	public Category(String name) throws InvalidArgumentsException {
		setName(name);
	}

	public Category(long id, String name) throws InvalidArgumentsException {
		this(name);
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
		if(name != null && !(name.trim().equals("")) && name.trim().length() > CATEGORY_NAME_MIN_LENGTH) {
			this.name = name;
		} 
		else {
			throw new InvalidArgumentsException(INVALID_CATEGORY_NAME);
		}
	}

	
	
}
