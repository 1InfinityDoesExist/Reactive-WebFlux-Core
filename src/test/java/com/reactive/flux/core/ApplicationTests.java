package com.reactive.flux.core;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;

class ApplicationTests {

	@Test
	void fluxTest1() {
		Flux.just("Avinash", "Patel").subscribe(System.out::println);
	}

	/**
	 * .just .fromIterable .interval .range
	 */
	@Test
	void fluxTest2() {

		List<String> words = Arrays.asList("the", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog");

		// Returns the data in single object.
		Flux.just(words).subscribe(System.out::println);

		// Returns the stream of data.
		Flux.fromIterable(words).flatMap(word -> Flux.fromArray(word.split(""))).distinct().sort()
				.zipWith(Flux.range(1, 100), (word, line) -> line + ". " + word).subscribe(System.out::println);
	}

	
}
