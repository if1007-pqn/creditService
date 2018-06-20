package com.service.credit.model;

import java.util.Date;

/**
 * Credit
 */
public class Credit {    

    private Date expiration;
    private double value;

    public Credit(double value, Date expiration) {
        this.value = value;
        this.expiration = expiration;
    }

    /**
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * @return the expiration
     */
    public Date getExpiration() {
        return expiration;
    }

    /**
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }

}