package Core_Knowledge;

import java.time.Duration;
import java.time.Instant;

import org.springframework.web.reactive.function.client.WebClient;

import com.reactive.flux.core.model.Person;

public class StreamCall {
	private static final WebClient webClient = WebClient.create("http://localhost:8080");

	public static void main(String[] args) {
		Instant start = Instant.now();

		webClient.get()
			.uri("/persons/events")
			.retrieve()
			.bodyToFlux(Person.class)
			.doOnNext(person -> System.out.println("Person name : " + person.getName()))
			.take(4)
			.blockLast();

		System.out.println("log time : " + Duration.between(start, Instant.now()).toMillis());
	}
}
