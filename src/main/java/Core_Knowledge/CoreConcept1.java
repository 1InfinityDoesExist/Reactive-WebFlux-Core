package Core_Knowledge;

import java.time.Duration;
import java.time.Instant;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.reactive.flux.core.model.Person;

/**
 * Drawback of restTemplate. Basically restTemplate is sync call. Sometimes it
 * can be a drawback.
 * 
 * 
 * @author mossad
 *
 */
public class CoreConcept1 {

	private static final RestTemplate restTemplate;
	private static final String baseURL;

	static {
		restTemplate = new RestTemplate();
		baseURL = "http://localhost:8080?delay=2";
		restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(baseURL));
	}

	public static void main(String[] args) {
		Instant start = Instant.now();

		for (int iter = 1; iter <= 5; iter++) {
			Person person = restTemplate.getForObject("/person/{id}", Person.class, iter);
			System.out.println(person);
		}

		// Each api takes 2 sec to complete its execution. Therefore time take to
		// execute the main method is 10sec
		System.out.println("log time : " + Duration.between(start, Instant.now()).toSeconds());

	}
}
