package singh.maneesh.http;

import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RouterFunctions.route;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@SpringBootApplication
public class HttpClientPocApplication {

	public static void main(String[] args) {
		SpringApplication.run(HttpClientPocApplication.class, args);
	}

	@Bean
	public RouterFunction<ServerResponse> hello(){
		return route(GET("/hello"), request -> {
			Thread.sleep(5000);
			return ServerResponse.ok().body("hello, back");
		});
	}

}
