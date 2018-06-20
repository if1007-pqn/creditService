package com.service.credit.json;

import java.util.Date;

public class PurchaseInfoJson {
    private double porcent;
    private Date expiration;

    /**
     * @return the expiration
     */
    public Date getExpiration() {
        return expiration;
    }

    /**
     * @return the porcent
     */
    public double getPorcent() {
        return porcent;
    }

}