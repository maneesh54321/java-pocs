package com.ms.testing.model;

public class Address {
	private String street;
	private String city;
	private String zip;
	private String country;

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getZip() {
		return zip;
	}

	public String getCountry() {
		return country;
	}

	@Override
	public String toString() {
		return "Address{" +
				"street='" + street + '\'' +
				", city='" + city + '\'' +
				", zip='" + zip + '\'' +
				", country='" + country + '\'' +
				'}';
	}
}
