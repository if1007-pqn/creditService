package com.service.credit.db;

import java.util.List;

import com.service.credit.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<User, String> {

	List<User> findByUsername(String username);
}