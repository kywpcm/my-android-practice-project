package com.example.databasesimplecursoradaptersample;

public class Person {

	int id;
	String name;
	String address;
	
	@Override
	public String toString() {
		return name + "\n\r" + address;
	}
}
