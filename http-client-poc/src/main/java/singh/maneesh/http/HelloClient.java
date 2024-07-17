package singh.maneesh.http;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface HelloClient {

	@GetExchange("/hello")
	String hello();
}
