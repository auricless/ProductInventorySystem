package com.ciruela.productinventory.entities;

public enum ProductType {

	FOOD("FOOD"), 
	SPORTS("SPORTS"), 
	HOUSEHOLD("HOUSEHOLD"), 
	MUSIC("MUSIC"), 
	ELECTRONIC("ELECTRONIC"),
	APPLIANCE("APPLIANCE");

	private String value;

	ProductType(String value) {
		this.value = value;
	}

}
