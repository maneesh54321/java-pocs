package com.ms.testing;

import com.ms.parser.JsonParser;
import com.ms.testing.model.Person;
import java.io.InputStreamReader;
import java.util.Objects;

public class JSONDeserializerTesting {

	public static void main(String[] args) {
		JsonParser jsonParser = new JsonParser();
		Person person = jsonParser.parseJson(
				new InputStreamReader(Objects.requireNonNull(
						JSONDeserializerTesting.class.getResourceAsStream("/sample_without_arr.json"))),
				Person.class
		);
		System.out.println(person);
	}
}
