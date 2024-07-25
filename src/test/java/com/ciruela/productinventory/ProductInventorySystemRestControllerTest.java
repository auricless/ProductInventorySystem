package com.ciruela.productinventory;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ciruela.productinventory.entities.Product;
import com.ciruela.productinventory.entities.ProductType;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductInventorySystemRestControllerTest {
	
	@Autowired
    TestRestTemplate restTemplate;
	
	@Test
    void testGetAllProducts() {
        ResponseEntity<String> response = restTemplate.getForEntity("/v1/product/", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
	
	@Test
    void testGetProductById() {
		Product newProduct = new Product();
		newProduct.setId(1L);
        newProduct.setName("Product-1");
        newProduct.setDescription("Description-1");
        newProduct.setType(ProductType.APPLIANCE);
        newProduct.setQuantity(5);
        newProduct.setUnitPrice(BigDecimal.valueOf(49.99));
        
		ResponseEntity<String> response = restTemplate.getForEntity("/v1/product/{id}", String.class, 1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Product-1");
    }

    @Test
    void testCreateProduct() {
    	Product newProduct = new Product();
        newProduct.setName("Product-1");
        newProduct.setDescription("Description-1");
        newProduct.setType(ProductType.APPLIANCE);
        newProduct.setQuantity(5);
        newProduct.setUnitPrice(BigDecimal.valueOf(49.99));
        
        ResponseEntity<Void> response = restTemplate
            .postForEntity("/v1/product/", newProduct, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        
        URI locationOfNewProduct = response.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate
            .getForEntity(locationOfNewProduct, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number id = documentContext.read("$.id");
        String name = documentContext.read("$.name");
        String description = documentContext.read("$.description");
        ProductType type = documentContext.read("$.type");
        int quantity = documentContext.read("$.quantity");
        BigDecimal unitPrice = documentContext.read("$.unitPrice");

        assertThat(id).isNotNull();
        assertThat(name).isEqualTo("Product-1");
        assertThat(description).isEqualTo("Description-1");
        assertThat(type).isEqualTo(ProductType.APPLIANCE);
        assertThat(quantity).isEqualTo(5);
        assertThat(unitPrice).isEqualTo(BigDecimal.valueOf(49.99));
    }
    
    @Test
    public void testUpdateProduct() {
    	Product updatedProduct = new Product();
    	updatedProduct.setName("Product-Updated");
    	updatedProduct.setDescription("Description-Updated");
    	updatedProduct.setType(ProductType.ELECTRONIC);
    	updatedProduct.setQuantity(7);
    	updatedProduct.setUnitPrice(BigDecimal.valueOf(69.99));

        HttpEntity<Product> request = new HttpEntity<>(updatedProduct);
        ResponseEntity<Product> response = restTemplate.exchange("/v1/product/{id}", HttpMethod.PUT, request, Product.class, 1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        String name = documentContext.read("$.name");
        String description = documentContext.read("$.description");
        ProductType type = documentContext.read("$.type");
        int quantity = documentContext.read("$.quantity");
        BigDecimal unitPrice = documentContext.read("$.unitPrice");

        assertThat(id).isNotNull();
        assertThat(name).isEqualTo("Product-Updated");
        assertThat(description).isEqualTo("Description-Updated");
        assertThat(type).isEqualTo(ProductType.ELECTRONIC);
        assertThat(quantity).isEqualTo(7);
        assertThat(unitPrice).isEqualTo(BigDecimal.valueOf(69.99));
    }
    
    @Test
    public void testDeleteProduct() {
        restTemplate.delete("/v1/product/{id}", 1L);

        ResponseEntity<String> response = restTemplate.getForEntity("/v1/product/{id}", String.class, 1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
