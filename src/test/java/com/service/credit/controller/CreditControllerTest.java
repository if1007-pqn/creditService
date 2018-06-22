package com.service.credit.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.service.credit.Application;
import com.service.credit.db.UserMongoService;
import com.service.credit.db.WalletMongoService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig(classes = Application.class)
@SpringBootTest
public class CreditControllerTest {
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
    private WalletMongoService walletMongoService;
	
	@Autowired
    private UserMongoService userMongoService;

    private void dropDB() {
        this.walletMongoService.deleteAll();
        this.userMongoService.deleteAll();
    }
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private MockMvc mockMvc;
	
	private static final String storeToken = "eyJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJzdG9yZSIsImp0aSI6IjViMmQ3N2NjMWNkZTAwNmZhYTJjMzMzOCIsImV4cCI6MTUzNDk3NzEwNX0.OJ6VlrChk-FI4Rd8yZLcVjGwbtA-pG9DVbgG4neLSM142Sil2MlWKwQahb9cN89VRq9Hu9iBwATpCcaAVJxL3g";
	
	private MvcResult makePurchaseForCellphone(String cellphone) throws Exception
	{
		MvcResult result = this.mockMvc.perform(post("/purchase")
				.header("token", storeToken)
				.header("cellphone", cellphone)
				.header("value", 100.0)
				.header("credit", 0.0))
				.andExpect(status().is2xxSuccessful())
				.andReturn();
		
		return result;
	}
	
	@Before
	public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	/**
	 * make purchase for the first time (new user to the store)
	 * @throws Exception
	 */
	@Test
	public void testPurchaseFirstTime() throws Exception {
		String cellphone = "81123456789";
		
		this.mockMvc.perform(post("/purchase")
				.header("token", storeToken)
				.header("cellphone", cellphone)
				.header("value", 100.0)
				.header("credit", 0.0))
				.andExpect(status().is2xxSuccessful())
				.andReturn();
		
		
	}
	
	/**
	 * test to get information of credit purchase from user
	 *  on the associated store
	 * @throws Exception 
	 */
	@Test
	public void testCredit() throws Exception {
		String cellphone = "81123456789";
		makePurchaseForCellphone(cellphone);
		this.mockMvc.perform(get("/credit").header("token", storeToken)
				.header("cellphone", cellphone))
				.andExpect(status().is2xxSuccessful()).andReturn();
	}
	
	
	@After
    public void after() {
        this.dropDB();
    }
}
