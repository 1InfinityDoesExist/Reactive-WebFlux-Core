package Core_Knowledge;

import java.time.Duration;

import reactor.core.publisher.Flux;

public class MapFlatMapSequentialMap {

	public static void main(String[] args) throws InterruptedException {

		Flux<String> flux = Flux.just("a", "b");
		Flux<Flux<String>> log = flux.map(String::toUpperCase).map(input -> findByname(input)).log();
		log.subscribe(a -> System.out.println(a.blockLast()));

		// Sequence is not preserved
		Flux<String> log2 = flux.map(String::toUpperCase).flatMap(input -> findByname(input)).log();
		log2.subscribe(a -> System.out.println(a.toString()));

//		// Sequence is preserved
		Flux<String> log3 = flux.map(String::toUpperCase).flatMapSequential(input -> findByname(input)).log();
		log3.subscribe(a -> a.toString());

		Thread.sleep(500);

	}

	public static Flux<String> findByname(String name) {
		return name.equals("A") ? Flux.just("Avinash", "Patel").delayElements(Duration.ofMillis(100))
				: Flux.just("Tiger", "Zinda", "Hey");

	}

}
