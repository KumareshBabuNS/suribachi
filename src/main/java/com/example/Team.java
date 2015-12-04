package com.example;

public class Team {

	private String name;
	private String location;
	
	
	public Team() {
		super();
	}
	public Team(String name, String location) {
		this();
		this.name = name;
		this.location = location;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
}
