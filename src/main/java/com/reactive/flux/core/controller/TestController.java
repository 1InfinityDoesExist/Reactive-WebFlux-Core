package com.reactive.flux.core.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.reactive.flux.core.model.Hobby;
import com.reactive.flux.core.model.Person;
import com.reactive.flux.core.repository.AccountRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TestController {

	private static final Logger log = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private AccountRepository accountRepository;

	private static final WebClient webClient;

	static {
		webClient = WebClient.create("http://localhost:8080?delay=2");
	}

	@GetMapping("/persons")
	public Flux<Person> getPersons() {
		log.info("----Inside get Persons api");
		return this.webClient.get().uri("/persons?delay=2").retrieve().bodyToFlux(Person.class);
	}

	@GetMapping("/person/{id}")
	public Flux<Person> getPerson(@PathVariable(value = "id") Long id) {
		log.info("----Inside get Persons api by id");
		return this.webClient.get().uri("/person/{id}?delay=2", id).retrieve().bodyToFlux(Person.class);
	}

	/**
	 * Note for stream produces = MediaType.TEXT_EVENT_STREAM_VALUE is mandatory
	 * 
	 * @return
	 */
	@GetMapping(value = "/persons/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Person> getPersonStream() {
		log.info("----Inside get Persons api stream");
		return this.webClient.get().uri("/persons/events").accept(MediaType.TEXT_EVENT_STREAM).retrieve()
				.bodyToFlux(Person.class);
	}

	@GetMapping("/accounts/hobbies")
	public Flux<Map<String, String>> getTopAccountHobbies() {
		
		return this.accountRepository.findAll(Sort.by(Sort.Direction.DESC, "score"))
				.take(5)
				.flatMapSequential(account -> {
					Long personId = account.getPersonId();
					
					Mono<String> nameMono = this.webClient.get().uri("/person/{id}?delay=2", personId).retrieve()
							.bodyToMono(Person.class).map(Person::getName);
					
					Mono<String> hobbyMono = this.webClient.get().uri("/person/{id}/hobby?delay=2", personId).retrieve()
							.bodyToMono(Hobby.class).map(Hobby::getHobby);
					
					return Mono.zip(nameMono, hobbyMono, (personName, hobby) -> {
						Map<String, String> data = new LinkedHashMap<String, String>();
						data.put("person", personName);
						data.put("hobby", hobby);
						return data;
					});
				});

	}

}
