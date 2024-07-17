package singh.maneesh.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.ResponseSpec;

public class HttpClientApplication {

	public static void main(String[] args) {

		RestClient restClient = RestClient.create("http://localhost:8080");

//		HttpRequest httpRequest = HttpRequest.newBuilder()
//				.uri(URI.create("http://localhost:8080/hello"))
//				.GET()
//				.build();
		int num_requests = 1_000;
//		HttpClient httpClient = HttpClient.newHttpClient();
		try(
				ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
			AtomicInteger completed = new AtomicInteger(0);
			AtomicInteger sent = new AtomicInteger(0);
			for (int i = 0; i < num_requests; i++) {
				executor.execute(() -> {
//						System.out.println("Sending requestNum: " + sent.incrementAndGet());
//						HttpResponse<String> response = httpClient.send(httpRequest, BodyHandlers.ofString());
//						System.out.println(response.body());
//						System.out.println("Completed: " + completed.incrementAndGet());
						System.out.println("Sending request no:" + sent.incrementAndGet());
						ResponseSpec responseSpec = restClient
								.get()
								.uri("/hello")
								.retrieve();
						System.out.println("Received response no:" + completed.incrementAndGet());
						System.out.println(responseSpec.body(String.class));
				});
			}
		}
	}

}
