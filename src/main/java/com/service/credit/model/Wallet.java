package com.service.credit.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.service.credit.exception.InsufficientCreditsException;
import com.service.credit.exception.InvalidValueException;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;

/**
 * Wallet
 */
@Document(collection = "wallet")
@Getter
public class Wallet {
    @Id
    private String id;

    private String userid;
    private String storeid;

    private List<Purchase> purchases;
    private List<Credit> credits;

    public Wallet(String userid, String storeid) {
        this.userid = userid;
        this.storeid = storeid;
        purchases = new ArrayList<Purchase>();
        credits = new ArrayList<Credit>();
    }

    public void register(double value, double creditUse, double percent, Date expiration) {
        /** add a purchase in wallet */
        value = roundS2(value);
        creditUse = roundS2(creditUse);

        if (value <= 0 || creditUse < 0 || percent < 0 || creditUse > value) {
            throw new InvalidValueException();
        }
        this.useCredit(creditUse);
        double received = roundS2(value * percent / 100);
        this.purchases.add(new Purchase(value, new Date(), creditUse, received));
        this.credits.add(new Credit(received, expiration));

    }

    public double getTotalExpenses(Date minDate) {
        /** return the total expense in the wallet 
         * of purchases after minDate */
        double r = 0;
        for (Purchase p : purchases) {
            if (minDate.before(p.getDate())) 
                r += p.getValue();
        }
        return roundS2(r);
    }

    public double getCredits() {
        /** return restant credits of wallet */
        double r = 0;
        for (Credit c : credits) {
            if (new Date().before(c.getExpiration())) { //credit is valid
                r += c.getValue();
            }
        }
        return roundS2(r);
    }

    public void useCredit(double credit) {
        /** discount credits */
        if (roundS2(this.getCredits()) < roundS2(credit)) {
            throw new InsufficientCreditsException();
        }
        double used = 0;
        for (int i=0; i<credits.size(); i++) {
            Credit c = credits.get(i);
            if (new Date().before(c.getExpiration()) && c.getValue() > 0) {
                double min = Math.min(c.getValue(), subS2(credit, used));
                used += min;
                credits.set(i, new Credit(subS2(c.getValue(), min), c.getExpiration()));
                if (used >= credit) {
                    break;
                }
            }
        }
    }

    public static double roundS (double value, int scale) {
        return Math.round(value * Math.pow(10, scale)) / Math.pow(10, scale);
    }

    public static double roundS2 (double value) {
        return roundS(value, 2);
    }

    public static double subS2 (double a, double b) {
        return subS(a, b, 2);
    }

    public static double subS (double a, double b, int scale) {
        return Math.round(a*Math.pow(10, scale) - b*Math.pow(10, scale)) / (double) Math.pow(10, scale);
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the storeid
     */
    public String getStoreid() {
        return storeid;
    }

    /**
     * @return the userid
     */
    public String getUserid() {
        return userid;
    }
    
}