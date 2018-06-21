package com.service.credit.db;

import java.util.List;

import com.service.credit.model.Wallet;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface WalletMongoRepository extends MongoRepository<Wallet, String> {

	List<Wallet> findByUseridAndStoreid(String userid, String storeid);
}