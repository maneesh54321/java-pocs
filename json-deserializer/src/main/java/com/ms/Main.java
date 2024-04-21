package com.ms;

import com.ms.reader.JsonReader;
import com.ms.reader.token.Token;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {

	public static void main(String[] args) {

//		try (JsonReader jsonReader =
//				new JsonReader(new FileReader(Objects.requireNonNull(Main.class.getResource("/sample.json")).getPath()))
//				) {
//			Token token;
//			do {
//				token = jsonReader.readNext();
//				System.out.println(token);
//			} while (token != null);
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}

		List<String> list = new ArrayList<>();

		try {
			Type genericType = Person.class.getDeclaredField("list").getGenericType();
			System.out.println(((ParameterizedType)genericType).getRawType());
			System.out.println(((ParameterizedType)genericType).getActualTypeArguments()[0]);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}

	private static class Person {
		List<String> list = new ArrayList<>();
	}
}