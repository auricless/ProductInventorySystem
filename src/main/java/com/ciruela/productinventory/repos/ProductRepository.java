package com.ciruela.productinventory.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ciruela.productinventory.entities.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

}
