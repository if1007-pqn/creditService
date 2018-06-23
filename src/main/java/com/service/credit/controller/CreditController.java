package com.service.credit.controller;

import java.util.Calendar;
import java.util.List;

import com.google.gson.Gson;
import com.service.credit.ApplicationConfig;
import com.service.credit.db.UserMongoService;
import com.service.credit.db.WalletMongoService;
import com.service.credit.exception.InvalidCellphoneException;
import com.service.credit.exception.InvalidTokenException;
import com.service.credit.exception.ServiceNotReachableException;
import com.service.credit.exception.WalletNotFoundException;
import com.service.credit.json.GetLevelsJson;
import com.service.credit.model.Level;
import com.service.credit.model.User;
import com.service.credit.model.Wallet;
import com.service.credit.util.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import io.swagger.annotations.ApiOperation;

@Controller
public class CreditController {

    @Autowired
    private ApplicationConfig config;
    @Autowired
    private UserMongoService userMongoService;
    @Autowired
    private WalletMongoService walletMongoService;

    @RequestMapping(value = "/purchase", method = RequestMethod.POST)
    @ApiOperation(value = "Register a purchase", notes = "Register a purchase")
    @ResponseBody
    public void a(
        @RequestHeader(name = "token", required = true) String token,
        @RequestHeader(name = "cellphone", required = true) String cellphone,
        @RequestHeader(name = "value", required = true) double value,
        @RequestHeader(name = "credit", required = true) double credit
    ) {
        registerPurchase(token, cellphone, value, credit);
        // return getLevel(token, credit);
    }


    public void registerPurchase(String token, String cellphone, double value, double credit) {
        /** Register a purchase */
        
        //get user
        List<User> lu = userMongoService.findByCellphone(cellphone);
        User u;
        if (lu.size() > 0)
            u = lu.get(0);
        else {
            userMongoService.save(new User(cellphone));
            lu = userMongoService.findByCellphone(cellphone);
            u = lu.get(0);
        }

        //get wallet
        String storeid = JWTUtil.getId(config.getTokenKey(), token, "store");
        List<Wallet> lw = walletMongoService.findByUseridAndStoreid(u.getId(), storeid); 
        Wallet w;
        if (lw.size() > 0)
            w = lw.get(0);
        else
            w = new Wallet(u.getId(), storeid);
        
        //get levels and oldDaysPurchases
        GetLevelsJson jl = getLevelsJson(token);

        //calcule total expenses valid in current date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, (int) (-1 * jl.getOldDaysPurchases() * 24 * 60 * 60));
        double totalExpenses = w.getTotalExpenses(calendar.getTime());

        //select the level
        Level level = selectLevel(jl.getLevels(), totalExpenses);
        
        //registering the purchase
        calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, (int) level.getCreditDays()*24*60*60);
        w.register(value, credit, level.getPercent(), calendar.getTime());
        walletMongoService.save(w);
        
    }

    public Level selectLevel(List<Level> levels, double totalExpenses) {
        /** Select level by according with the total expenses */
        Level current = levels.get(0);
        for (Level l : levels) {
            current = l;
            if (totalExpenses >= l.getNextLevel()) {
                totalExpenses -= l.getNextLevel();
            } else {
                break;
            }
        }
        return current;
    }

    public GetLevelsJson getLevelsJson(String token) {
        /** Request levels and oldDaysPurchases of a store */

        HttpHeaders headers = new HttpHeaders();
        headers.set("token", token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(
                config.getStoreServiceAddress() + "/registerinfo", 
                HttpMethod.GET, entity, String.class 
            );
        } catch (HttpClientErrorException e) {
            if(HttpStatus.NOT_ACCEPTABLE.equals(e.getStatusCode())) {
                throw new InvalidTokenException();
            } else {
                throw e;
            }
        } catch (ResourceAccessException e) {
            throw new ServiceNotReachableException("Store");
        }
        String json = response.getBody();
        Gson gson = new Gson();
        return gson.fromJson(json, GetLevelsJson.class);
    }


    @RequestMapping(value = "/credit", method = RequestMethod.GET)
    @ApiOperation(value = "Return user credit", notes = "Return user credit amount in the store (of the token)")
    @ResponseBody
    public double b(
        @RequestHeader(name = "token", required = true) String token,
        @RequestHeader(name = "cellphone", required = true) String cellphone
    ) {
        return getCredit(token, cellphone);
    }

    public double getCredit(String token, String cellphone) {
        return getWallet(token, cellphone).getCredits();
    }

    public Wallet getWallet(String token, String cellphone) {
        List<User> lu = userMongoService.findByCellphone(cellphone);
        if (lu.size() <= 0)
            throw new InvalidCellphoneException();
        User u = lu.get(0);
        
        //get wallet
        List<Wallet> lw = walletMongoService.findByUseridAndStoreid(u.getId(), JWTUtil.getId(config.getTokenKey(), token, "store")); 
        if (lw.size() <= 0)
            throw new WalletNotFoundException();
        Wallet w = lw.get(0);
        return w;
    }

    public void deleteWallet(String token, String cellphone) {
        Wallet w = getWallet(token, cellphone);
        walletMongoService.delete(w.getId());
    }

    @RequestMapping(value = "/wallet", method = RequestMethod.DELETE)
    @ApiOperation(value = "Remove a wallet", notes = "Remove a wallet from the store")
    @ResponseBody
    public void d(
        @RequestHeader(name = "token", required = true) String token,
        @RequestHeader(name = "cellphone", required = true) String cellphone
    ) {
        deleteWallet(token, cellphone);
    }

}