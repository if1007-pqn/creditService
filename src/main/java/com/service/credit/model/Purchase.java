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
    private double creditUsed;
    private double creditReceived;


    public Purchase(double value, Date date, double creditUsed, double creditReceived) {
        this.value = value;
        this.date = date;
        this.creditUsed = creditUsed;
        this.creditReceived = creditReceived;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * @return the creditUsed
     */
    public double getCreditUsed() {
        return creditUsed;
    }

    /**
     * @return the creditReceived
     */
    public double getCreditReceived() {
        return creditReceived;
    }
}