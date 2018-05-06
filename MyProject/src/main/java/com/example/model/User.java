 package com.example.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.example.util.InvalidArgumentsException;



public class User {
	
	private static final int MIN_FIRSTNAME_LENGTH = 3;
	private static final int MIN_LASTNAME_LENGTH = 3;
	public static final int PASSWORD_MIN_LENGTH = 6;
	public static final int PASSWORD_MAX_LENGTH = 15;
	public static final int USERNAME_MIN_LENGTH = 6;
	public static final int USERNAME_MAX_LENGTH = 15;
	public static final int PHONE_MIN_LENGTH = 8;
	public static final String INVALID_NAME = "Invalid name";
	public static final String INVALID_EMAIL = "Invalid email";
	public static final String INVALID_PASSWORD = "Invalid password";
	public static final String INVALID_PHONE = "Invalid phone number";
	public static final String INVALID_ADDRESS = "Invalid address";
	public static final String INVALID_USERNAME = "Invalid username";
	
	private long id;
	private String username;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String password;
	private String email;

	//user shopping cart
	private ConcurrentHashMap<Product, Integer> cart = new ConcurrentHashMap<>();
	private HashSet<Product> favorites =  new HashSet<>();

	public User(String username, String firstName, String lastName, String phoneNumber, String password, String email) throws InvalidArgumentsException {
		setUsername(username);
		setFirstName(firstName);
		setLastName(lastName);
		setPhoneNumber(phoneNumber);
		setPassword(password);
		setEmail(email);
	}

	public User(long id, String username, String firstName, String lastName, String phoneNumber, String password,
			String email) throws InvalidArgumentsException {
		this(username, firstName, lastName, phoneNumber, password, email);
		setId(id);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) throws InvalidArgumentsException {
		if (username.trim().length() >= USERNAME_MIN_LENGTH && username.length() <= USERNAME_MAX_LENGTH) {
			this.username = username;
		} else {
			throw new InvalidArgumentsException(INVALID_USERNAME);
		}
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) throws InvalidArgumentsException{
		if (firstName.trim().length() >= MIN_FIRSTNAME_LENGTH && firstName.matches("[a-zA-Z]+")) {
			this.firstName = firstName;
		} else {
			throw new InvalidArgumentsException(INVALID_NAME);
		}
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) throws InvalidArgumentsException {
		if (lastName.trim().length() >= MIN_LASTNAME_LENGTH && lastName.matches("[a-zA-Z]+")) {
			this.lastName = lastName;
		} else {
			throw new InvalidArgumentsException(INVALID_NAME);
}
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) throws InvalidArgumentsException {
		if ((phoneNumber.matches("[0-9]+")) && (!phoneNumber.isEmpty()) && (!phoneNumber.equals(""))
				&& phoneNumber.length() >= PHONE_MIN_LENGTH) {
			this.phoneNumber = phoneNumber;
		} else {
			throw new InvalidArgumentsException(INVALID_PHONE);
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws InvalidArgumentsException {
		if (password.trim().length() >= PASSWORD_MIN_LENGTH && password.trim().length() <= PASSWORD_MAX_LENGTH) {
			this.password = password;
		} else {
			throw new InvalidArgumentsException(INVALID_PASSWORD);
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws InvalidArgumentsException {
		if (email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
			this.email = email;
		} else {
			throw new InvalidArgumentsException(INVALID_EMAIL);
		}
	}
	
	public void addToShoppingCart(Product product, int count) {
		if(this.cart.containsKey(product)) {
			cart.put(product, this.cart.get(product) + count);
		}
		else{
			this.cart.put(product, count);
		}
	}
	
	public void removeProductFromShoppingCart(Product product) {
		for (Entry<Product, Integer> entry : this.cart.entrySet()) {
			if(entry.getKey().equals(product)) {
				int quantity = entry.getValue();
				if(quantity == 1) {
					this.cart.remove(product);
				}
				else {
					this.cart.put(product, quantity-1);
				}
			}
		}
	}

	public Map<Product, Integer> getCart() {
		return Collections.unmodifiableMap(cart);
	}
	
	public Set<Product> getFavorites() {
		return Collections.unmodifiableSet(favorites);
	}
	
	public void addToFavorite(Product product) {
		if(!this.favorites.contains(product)) {
			this.favorites.add(product);
		}
	}
	
	public void removeFromFavorite(Product product) {
		if(this.favorites.contains(product)) {
			this.favorites.remove(product);
		}
	}
}
