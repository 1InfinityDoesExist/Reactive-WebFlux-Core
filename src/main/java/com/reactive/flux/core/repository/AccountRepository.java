package com.reactive.flux.core.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.reactive.flux.core.bean.Account;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, Long> {

}
