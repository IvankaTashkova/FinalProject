package com.example.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import com.example.util.InvalidArgumentsException;

public class Product {
	
	private long id;
	private String name;
	private double price;
	private Size size;
	private HashSet<Ingredient> ingredients;
	private String imgUrl;
		   
	public Product(String name, double price, HashSet<Ingredient> ingredients) throws InvalidArgumentsException {
		setName(name);
		setPrice(price);
		setIngredients(ingredients);
		setImgUrl(name.toLowerCase()+".jpg");
		setSize(Size.BIG);
	}
	
	public Product(long id,String name, double price, HashSet<Ingredient> ingredients) throws InvalidArgumentsException {
		this(name,price,ingredients);
		setId(id);
		setImgUrl(name.toLowerCase()+".jpg");
		setSize(Size.BIG);
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

	public void setName(String name){
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	
	public Set<Ingredient> getIngredients() {
		return Collections.unmodifiableSet(ingredients);
	}

	public void setIngredients(HashSet<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String getImgUrl() {
		return imgUrl;
	}
	
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public void setSize(Size size) {
		this.size = size;
	}
	
	public Size getSize() {
		return size;
	}
	
	
}