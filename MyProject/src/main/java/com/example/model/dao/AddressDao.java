package com.example.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		String sqlSelectAllAddresses = "SELECT * FROM addresses  WHERE user_id = ?;";
		List<Address> addresses = new ArrayList<>();
		
		try(PreparedStatement ps = connection.prepareStatement(sqlSelectAllAddresses)){
			ps.setLong(1, user.getId());
			ResultSet set = ps.executeQuery();
			while (set.next()) {
				long id = set.getLong("address_id");
				String location = set.getString("location");
				addresses.add(new Address(id, location, user.getId()));
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
		
		try(PreparedStatement ps = connection.prepareStatement(sqlInsertAddress)){
			ps.setString(1, address.getLocation());
			ps.setLong(2, address.getUserId());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			address.setId(rs.getLong("address_id"));
		}
	}

}
