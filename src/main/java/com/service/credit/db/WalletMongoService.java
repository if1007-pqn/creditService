package com.service.credit.db;

import java.util.List;

import com.service.credit.model.Wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletMongoService {
 
    @Autowired
    private WalletMongoRepository repository;
 
    public void save(Wallet obj) {
        repository.save(obj);
    }
 
    public List<Wallet> findByUseridAndStoreid(String userid, String storeid) {
        return repository.findByUseridAndStoreid(userid, storeid);
    }

    public long count() {
        return repository.count();
    }
 
    public void delete(String id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
 
}