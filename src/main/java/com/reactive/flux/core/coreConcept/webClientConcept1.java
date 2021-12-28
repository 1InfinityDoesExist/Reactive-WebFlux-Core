package com.reactive.flux.core.coreConcept;

import java.time.Duration;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

import com.reactive.flux.core.model.Person;

import reactor.core.publisher.Flux;

public class webClientConcept1 {
	private static final Logger logger = LoggerFactory.getLogger(webClientConcept1.class);
	private static final WebClient webClient = WebClient.create("http://localhost:8080?delay=2");

	public static void main(String[] args) {
		Instant start = Instant.now();

//		List<Mono<Person>> personMono = Stream.of(1, 2, 3)
//				.map(iter -> webClient.get().uri("/person/{id}", iter).retrieve().bodyToMono(Person.class))
//				.collect(Collectors.toList());
//
//		Mono.when(personMono).block();

//		Flux.range(1, 5).flatMap(iter -> webClient.get().uri("/person/{id}", iter).retrieve().bodyToMono(Person.class))
//				.blockLast();

//		Flux.range(1, 10).flatMap(iter -> webClient.get().uri("/person/{id}", iter).exchange()
//				.flatMap(response -> response.bodyToMono(Person.class))).blockLast();

		Flux.range(1, 10).doOnNext(i -> System.out.println("Iterator : " + i))
				.flatMap(iter -> webClient.get().uri("/person/{id}", iter).exchange()).flatMap(response -> {
					HttpStatus status = response.statusCode();
					HttpHeaders headers = response.headers().asHttpHeaders();
					System.out.println(status);
					return response.bodyToMono(Person.class);
				}).doOnNext(person -> System.out.println("PersonName  " + person.getName())).blockLast();

		System.out.println("log time : " + Duration.between(start, Instant.now()).toMillis());
	}

}
