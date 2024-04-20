package com.ms;

import com.ms.reader.JsonReader;
import com.ms.reader.token.Token;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class Main {

	public static void main(String[] args) {

		try (JsonReader jsonReader =
				new JsonReader(new FileReader(Objects.requireNonNull(Main.class.getResource("/sample.json")).getPath()))
				) {
			Token token;
			do {
				token = jsonReader.readNext();
				System.out.println(token);
			} while (token != null);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}