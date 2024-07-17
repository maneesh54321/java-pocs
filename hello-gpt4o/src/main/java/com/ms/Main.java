package com.ms;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class Main {

	public static void main(String[] args) {
		var openAiKey = System.getenv("OPENAI_API_KEY");
		System.out.println(openAiKey);
		var body = """
				{
					"model": "gpt-3.5-turbo-0125",
					"messages": [
						{
							"role": "user",
							"content": "Tell me a good dad joke about cats"
						}
					]
				}
				""";

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://api.openai.com/v1/chat/completions"))
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + openAiKey)
				.POST(BodyPublishers.ofString(body))
				.build();

		try (HttpClient httpClient = HttpClient.newHttpClient()) {
			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
			System.out.println(response.body());
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}