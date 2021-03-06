package com.reactive.flux.core;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.reactive.flux.core.bean.Account;
import com.reactive.flux.core.repository.AccountRepository;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner initData(AccountRepository repository) {
		return args -> {
			Flux<Account> accounts = Flux.just(new Account(1L, 680, 9L), // id, score, personId
					new Account(2L, 755, 7L), new Account(3L, 798, 2L), new Account(4L, 691, 6L),
					new Account(5L, 723, 8L), new Account(6L, 755, 3L), new Account(7L, 820, 1L),
					new Account(8L, 789, 4L), new Account(9L, 529, 10L), new Account(10L, 850, 5L));

			repository.deleteAll().thenMany(repository.saveAll(accounts)).blockLast();
		};
	}

}
