package com.service.credit.db;

import java.util.List;

import com.service.credit.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
@Service
public class UserMongoService {
 
    @Autowired
    private UserMongoRepository repository;
 
    public void save(User obj) {
        repository.save(obj);
    }
 
    public List<User> findByCellphone(String cellphone) {
        return repository.findByCellphone(cellphone);
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