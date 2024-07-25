package com.ciruela.productinventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ciruela.productinventory.entities.Product;
import com.ciruela.productinventory.services.ProductService;

@RestController
public class ProductRestController {
	
	@Autowired
	private ProductService service;
	
	@GetMapping
	public ResponseEntity<Product> findById(){
		return null;
	}
	
	@PostMapping
	public ResponseEntity<String> save(){
		return null;
	}
	
	@GetMapping
	public ResponseEntity<List<Product>> findAll(){
		return null;
	}
	
	public ResponseEntity<String> delete(){
		return null;
	}

}
