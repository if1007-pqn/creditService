package com.service.credit.controller;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.service.credit.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig(classes = Application.class)
@SpringBootTest
public class CreditControllerTest {
	@Autowired
	private WebApplicationContext context;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	
	/**
	 * test for register purchase on the associated store
	 */
	@Test
	public void testPurchase() {
		
	}
}
