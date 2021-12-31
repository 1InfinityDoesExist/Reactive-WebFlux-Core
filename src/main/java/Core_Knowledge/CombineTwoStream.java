package Core_Knowledge;

import java.util.ArrayList;
import java.util.List;

import reactor.core.publisher.Flux;

public class CombineTwoStream {
	public static void main(String[] args) {
		
		List<String> elements = new ArrayList<>();
		
		Flux.just(1, 2, 3, 4)
			.log()
			.map(i -> i *100)
			.zipWith(Flux.range(1, 10), (one, two) -> String.format("First flux : %d , second flux : %d", one, two))
			.subscribe(elements::add);
		
		elements.forEach(System.out::println);
	}

}
