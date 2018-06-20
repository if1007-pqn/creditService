package com.service.credit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;

/**
 * User
 */
@Document(collection = "user")
@Getter
public class User {
    
    @Id
    private String id;

    @Indexed(unique = true)
    private String cellphone;

    public User(String cellphone) {
        this.cellphone = cellphone;
    }
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
}