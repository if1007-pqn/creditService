package com.service.credit.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

/**
 * Purchase
 */
public class Purchase {

    @Id
    private String id;

    private double value;
    private Date date;


    public Purchase(double value, Date date) {
        this.value = value;
        this.date = date;
    }
}