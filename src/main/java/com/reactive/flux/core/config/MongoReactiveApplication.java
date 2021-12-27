package com.reactive.flux.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

@EnableReactiveMongoRepositories
public class MongoReactiveApplication extends AbstractReactiveMongoConfiguration {

	@Value("${spring.data.mongodb.uri: mongodb://localhost:27017/reactive_flux}")
	private String mongo;

	@Bean
	public MongoClient mongoClient() {
		return MongoClients.create(mongo);
	}

	@Override
	protected String getDatabaseName() {
		return "reactive_flux";
	}
}