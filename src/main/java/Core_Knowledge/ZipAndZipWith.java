package Core_Knowledge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import reactor.core.publisher.Flux;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
class Anime {
	private String title;
	private String studio;
	private String episode;
}

public class ZipAndZipWith {
	public static void main(String[] args) {

		Flux<String> titleFlux = Flux.just("Avinash", "Patel");
		Flux<String> studioFluz = Flux.just("Tiger", "Zinda", "hai");
		Flux<String> episodeFlux = Flux.just("Avinash", "Rathod");

		// zip -- many
		Flux<Anime> flatMap = Flux.zip(titleFlux, studioFluz, episodeFlux)
				.flatMap(tuple -> Flux.just(new Anime(tuple.getT1(), tuple.getT2(), tuple.getT3())));

		flatMap.subscribe(anime -> System.out.println(anime.toString()));

		// zipWith - only two

		Flux<Anime> flatMap2 = titleFlux.zipWith(episodeFlux)
				.flatMap(tuple -> Flux.just(new Anime(tuple.getT1(), null, tuple.getT2())));
		flatMap2.subscribe(anime -> System.out.println(anime.toString()));
	}

}
