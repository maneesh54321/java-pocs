package com.ms.testing.model;

import java.util.List;

public class Person {
	private String name;
	private int age;
	private String city;
	private String email;
	private boolean isStudent;
	private Address address;
	private List<String> languages;

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public String getCity() {
		return city;
	}

	public String getEmail() {
		return email;
	}

	public boolean isStudent() {
		return isStudent;
	}

	public Address getAddress() {
		return address;
	}

	public List<String> getLanguages() {
		return languages;
	}

	@Override
	public String toString() {
		return "Person{" +
				"name='" + name + '\'' +
				", age=" + age +
				", city='" + city + '\'' +
				", email='" + email + '\'' +
				", isStudent=" + isStudent +
				", address=" + address +
				", languages=" + languages +
				'}';
	}
}
