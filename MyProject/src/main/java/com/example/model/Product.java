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
	private Dough dough;
	private HashSet<Ingredient> ingredients;
	private String imgUrl;
	
		   
	public Product(String name, double price, HashSet<Ingredient> ingredients) throws InvalidArgumentsException {
		setName(name);
		setPrice(price);
		setIngredients(ingredients);
		setImgUrl(name.toLowerCase()+".png");
	}
	
	public Product(long id,String name, double price, HashSet<Ingredient> ingredients) throws InvalidArgumentsException {
		this(name,price,ingredients);
		setId(id);
		setImgUrl(name.toLowerCase()+".png");
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

	public String getImgUrl() {
		return imgUrl;
	}
	
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public void setSize(Size size) {
		if(size.getId() == 1) {
			this.price = this.price - 2;
		}
		else {
			if(size.getId() == 3) {
				this.price = this.price +2;
			}
		}
		this.size = size;
	}
	
	public Size getSize() {
		return size;
	}
	
	public Dough getDough() {
		return dough;
	}
	
	public void setDough(Dough dough) {
		if(dough.getId() == 4) {
			this.price = this.price + 2;
		}
		this.dough = dough;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dough == null) ? 0 : dough.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
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
		if (dough == null) {
			if (other.dough != null)
				return false;
		} else if (!dough.equals(other.dough))
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		return true;
	}
	
	
	
}