package com.example.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.example.db.DBManager;
import com.example.model.Address;
import com.example.model.User;
import com.example.util.InvalidArgumentsException;


@Component
public class AddressDao implements IAddressDao{
	
	private Connection connection;
	
	public AddressDao() {
		connection = DBManager.getInstance().getConnection();
	}
	
	@Override
	public List<Address> getAllUserAddresses(User user) throws SQLException, InvalidArgumentsException {
		String sqlSelectAllAddresses = "SELECT address_id,location,user_id FROM addresses WHERE user_id = ?;";
		List<Address> addresses = new ArrayList<>();
		
		try(PreparedStatement ps = connection.prepareStatement(sqlSelectAllAddresses)){
			ps.setLong(1, user.getId());
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				long id = set.getLong("address_id");
				String location = set.getString("location");
				Address address =  new Address(id, location, user.getId());
				addresses.add(address);
			}
		}
		return addresses;
	}

	@Override
	public void deleteAddress(Address address) throws SQLException {
		String sqlDeleteAddress = "DELETE FROM addresses WHERE address_id = ? AND user_id = ?;";
		try(PreparedStatement ps = connection.prepareStatement(sqlDeleteAddress)){
			ps.setLong(1, address.getId());
			ps.setLong(2, address.getUserId());
			ps.executeUpdate();
		} 
	}

	@Override
	public void updateAddress(Address address) throws SQLException {
		String sqlUpdateAddress  = "UPDATE addresses SET location = ? WHERE address_id = ?;";
		try(PreparedStatement ps = connection.prepareStatement(sqlUpdateAddress)){
			ps.setString(1, address.getLocation());
			ps.setLong(2, address.getId());
			ps.executeUpdate();
		} 
	}

	@Override
	public void addNewAddress(Address address) throws SQLException {
		String sqlInsertAddress = "INSERT INTO addresses (location, user_id) VALUES(?,?)";
		
		try(PreparedStatement ps = connection.prepareStatement(sqlInsertAddress,Statement.RETURN_GENERATED_KEYS)){
			ps.setString(1, address.getLocation());
			ps.setLong(2, address.getUserId());
			ps.executeUpdate();
			try(ResultSet result = ps.getGeneratedKeys()){
				if(result.next()) {
					address.setId(result.getLong(1));
				}
			}
			
		}
	}

	@Override
	public Address getAddressById(long id) throws SQLException {
		Address address = null;
		String sqlGetById  = "SELECT address_id,location,user_id FROM addresses WHERE address_id = ?;";
		try(PreparedStatement ps = connection.prepareStatement(sqlGetById)){
			ps.setLong(1, id);
			ResultSet result = ps.executeQuery();
			if (result.next()) {
				long address_id = result.getLong("address_id");
				String location = result.getString("location");
				long user_id = result.getLong("user_id");
				address =  new Address(address_id, location, user_id);
			}
		}
		return address;
	}
	

}
