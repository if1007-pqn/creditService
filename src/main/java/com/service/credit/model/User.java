package com.service.credit.model;

import com.service.credit.exception.InvalidCellphoneException;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;

/**
 * User
 */
@Document(collection = "users")
@Getter
public class User {
    
    @Id
    private String id;

    @Indexed(unique = true)
    private String cellphone;

    public User(String cellphone) {
        if (cellphone.length() < 10 || cellphone.length() > 11) {
            throw new InvalidCellphoneException();
        }
        this.cellphone = cellphone;
    }
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
}