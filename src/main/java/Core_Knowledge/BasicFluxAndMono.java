package Core_Knowledge;

import java.util.ArrayList;
import java.util.List;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import reactor.core.publisher.Flux;

public class BasicFluxAndMono {
	public static void main(String[] args) {

		
		//Simple 
		List<Integer> elements = new ArrayList<>();
		Flux.just(1, 2, 3, 4)
			.log()
			.subscribe(elements::add);

		
		
		//Logic behind the subscribe
		
		Flux.just( 5, 6, 7, 8)
			.log()
			.subscribe(new Subscriber() {
				@Override
				public void onSubscribe(Subscription s) {
					s.request(Long.MAX_VALUE);
					
				}

				@Override
				public void onNext(Object t) {
					elements.add((Integer)t);
					
				}

				@Override
				public void onError(Throwable t) {
					
				}

				@Override
				public void onComplete() {
					
				}
			});
		
		//Operating on a Stream : map() will be applied when onNext() is called
		Flux.just(1, 2, 3, 4)
			.log()
			.map(i -> i * 10)
			.subscribe(elements::add);
		
		elements.stream().forEach(System.out::println);
		
	}

}
