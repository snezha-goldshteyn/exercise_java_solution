package com.exercise.accounting.repo;


import com.exercise.accounting.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountsRepository extends MongoRepository<Account, String> {
}
