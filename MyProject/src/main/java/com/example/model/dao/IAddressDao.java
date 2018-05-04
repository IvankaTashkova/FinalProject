package com.example.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.example.model.Address;
import com.example.model.User;
import com.example.util.InvalidArgumentsException;


public interface IAddressDao {
	
	public List<Address> getAllUserAddresses(User user) throws SQLException, InvalidArgumentsException;
	
	public void deleteAddress(Address address) throws SQLException;
	
	public void updateAddress(Address address) throws SQLException;
	
	public void addNewAddress(Address address) throws SQLException;

}
