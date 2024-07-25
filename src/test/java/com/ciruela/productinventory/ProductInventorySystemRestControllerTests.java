package com.ciruela.productinventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductInventorySystemRestControllerTests {

	@Autowired
	TestRestTemplate restTemplate;
	
	

}
