package com.example.controller.manager;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.Product;
import com.example.model.dao.ProductDao;
import com.example.util.InvalidArgumentsException;

@Component
public class ProductManager {
	
	private static ProductManager instance;
	@Autowired
	private ProductDao productDao;
	
	private ProductManager() {
		
	}

	public static ProductManager getInstance() {
		if (instance == null) {
			instance = new ProductManager();
		}
		return instance;
	}

	public void addNewProduct(Product product) throws SQLException {
		productDao.addNewProduct(product);
	}
	
	public void deleteProduct(Product product) throws SQLException{
		productDao.deleteProduct(product);
	}
	
	public void updateProduct(Product product) throws SQLException{
		productDao.updateProduct(product);
	}
	
	public List<Product> getListOfAllProduct() throws SQLException, InvalidArgumentsException{
		List<Product> products = productDao.getAllProducts();
		return products;
		
	}
}
