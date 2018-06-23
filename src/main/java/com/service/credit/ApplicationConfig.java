package com.service.credit;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("config")
public class ApplicationConfig {

    private String tokenKey;
    private String storeServiceAddress;

    /**
     * @return the tokenKey
     */
    public String getTokenKey() {
        return tokenKey;
    }

    /**
     * @param tokenKey the tokenKey to set
     */
    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }


    /**
     * @return the storeServiceAddress
     */
    public String getStoreServiceAddress() {
        return storeServiceAddress;
    }

    /**
     * @param storeServiceAddress the storeServiceAddress to set
     */
    public void setStoreServiceAddress(String storeServiceAddress) {
        this.storeServiceAddress = storeServiceAddress;
    }
}