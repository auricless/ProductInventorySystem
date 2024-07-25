package com.ciruela.productinventory.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ciruela.productinventory.entities.Product;
import com.ciruela.productinventory.repos.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	public Iterable<Product> findAll(){
		return repository.findAll();
	}
	
	public Optional<Product> findById(Long id) {
		return repository.findById(id);
	}
	
	public Product save(Product product) {
		Product savedProduct = repository.save(product);
		return savedProduct;
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}

}
