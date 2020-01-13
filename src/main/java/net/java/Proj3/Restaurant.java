package net.java.Proj3;

import java.math.BigInteger;

public class Restaurant {
	private String name;
	private String address;
	private float rating;
	private String price;
	//convert form meters to miles
	private float distance;
	//have a picture
	//have yelp link
	
	public Restaurant() {
		
	}
	public Restaurant(String name, String address, float rating, String price, float distance) {
		super();
		this.name = name;
		this.address = address;
		this.rating = rating;
		this.price = price;
		this.distance = distance;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
	
}
