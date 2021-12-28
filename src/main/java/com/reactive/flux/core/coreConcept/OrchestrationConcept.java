package com.reactive.flux.core.coreConcept;

import java.time.Duration;
import java.time.Instant;

import org.springframework.web.reactive.function.client.WebClient;

import com.reactive.flux.core.model.Hobby;
import com.reactive.flux.core.model.Person;

import reactor.core.publisher.Flux;

public class OrchestrationConcept {
	private static final WebClient webClient = WebClient.create("http://localhost:8080?delay=2");

	public static void main(String[] args) {
		Instant start = Instant.now();

		Flux.range(1, 2)
		.doOnNext(i -> System.out.println("Iteration : " + i))
		.flatMap(iter -> webClient.get()
				.uri("/person/{id}", iter)
				.retrieve()
				.bodyToMono(Person.class)
				.doOnNext(person -> System.out.println("Person Name : " + person.getName()))
				.flatMap(person -> webClient.get()
						.uri("/person/{id}/hobby", iter)
						.retrieve()
						.bodyToMono(Hobby.class)
						.doOnNext(hobby -> System.out.println("Hobby Name : " + hobby.getHobby()))
						)
				).blockLast();

		System.out.println("log time : " + Duration.between(start, Instant.now()).toMillis());
	}
}
