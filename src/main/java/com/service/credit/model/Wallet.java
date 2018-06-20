package com.service.credit.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private String storeua;

    private List<Purchase> history;
    private List<Credit> credits;

    public Wallet(String userid, String storeua) {
        this.userid = userid;
        this.storeua = storeua;
        history = new ArrayList<Purchase>();
        credits = new ArrayList<Credit>();
    }

    public void register(double value, double creditUse, double percent, Date expiration) { 
        /** add a purchase in wallet */
        this.useCredit(creditUse);
        this.history.add(new Purchase(value, new Date()));
        this.credits.add(new Credit(value * percent / 100, expiration));

    }

    public double getCredits() {
        /** return restant credits of wallet */
        double r = 0;
        for (Credit c : credits) {
            if (new Date().before(c.getExpiration())) { //credit is valid
                r += c.getValue();
            }
        }
        return r;
    }

    public void useCredit(double credit) {
        /** discount credits */
        if (this.getCredits() < credit) {
            //throw insufficient
        }
        double used = 0;
        for (int i=0; i<credits.size(); i++) {
            Credit c = credits.get(i);
            if (new Date().before(c.getExpiration()) && c.getValue() > 0) {
                double min = Math.min(c.getValue(), credit - used);
                used += min;
                credits.set(i, new Credit(c.getValue() - min, c.getExpiration()));
                if (used >= credit) {
                    break;
                }
            }
        }
    }
    
}