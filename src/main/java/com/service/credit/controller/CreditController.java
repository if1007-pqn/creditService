package com.service.credit.controller;

import java.util.List;

import com.service.credit.db.UserMongoService;
import com.service.credit.json.PurchaseInfoJson;
import com.service.credit.model.User;
import com.service.credit.model.Wallet;
import com.service.credit.util.JWTUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.ApiOperation;

@Controller
public class CreditController {
    UserMongoService userMongoService;

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
    }

    public void registerPurchase(String token, String cellphone, double value, double credit) {
        /** Register a purchase */
        
        //throw cellphone invalid format
        List<User> lu = getUserByCellphone(cellphone);
        User u;
        if (lu.size() > 0)
            u = lu.get(0);
        else
            u = new User(cellphone);

        Wallet w = getWalletByUseridAndStoreua(u.getId(), JWTUtil.getId(token, "store")); 
        
        PurchaseInfoJson j1 = getInfos(token, w.getCredits()); //request to GET /purchaseinfo
        w.register(value, credit, j1.getPorcent(), j1.getExpiration());
        save(w);
        
        if (lu.size() <= 0)
            save(u);
        
    }


    @RequestMapping(value = "/credit", method = RequestMethod.GET)
    @ApiOperation(value = "Return user credit amount in the store (of the token)", notes = "Return user credit amount")
    @ResponseBody
    public double b(
        @RequestHeader(name = "token", required = true) String token,
        @RequestHeader(name = "cellphone", required = true) String cellphone
    ) {
        
        return 0;
    }
}