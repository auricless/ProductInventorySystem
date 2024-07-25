package com.ciruela.productinventory.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ciruela.productinventory.entities.Product;
import com.ciruela.productinventory.services.ProductService;

@RestController
@RequestMapping("/v1/product")
public class ProductRestController {
	
	@Autowired
	private ProductService service;
	
	@GetMapping("{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id){
		 Optional<Product> productOptional = service.findById(id);
		 if(productOptional.isPresent()) {
			 return ResponseEntity.ok(productOptional.get());
		 }else {
			 return ResponseEntity.notFound().build();
		 }
	}
	
	@PostMapping("/")
	public ResponseEntity<Void> createNewProduct(@RequestBody Product product, UriComponentsBuilder ucb){
		Product savedProduct = service.save(product);
		URI locationOfSavedProduct = ucb
		        .path("v1/product/{id}")
		        .buildAndExpand(savedProduct.getId())
		        .toUri();
		return ResponseEntity.created(locationOfSavedProduct).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product){
		Product savedProduct = service.save(product);
		return ResponseEntity.ok(savedProduct);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Product>> findAll(){
		List<Product> products = new ArrayList<>();
		service.findAll().forEach(products::add);
		return ResponseEntity.ok(products);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id){
		service.delete(id);
        return ResponseEntity.noContent().build();
	}

}
