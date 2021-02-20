package com.example.demo.controller;

import com.example.demo.domain.DomainTestHelper;
import com.example.demo.domain.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Log
public class TransactionControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper mapper;

  private DomainTestHelper helper;
  private Transaction transaction;
  private String content;

  @BeforeEach
  public void setUp() throws Exception {
    this.helper = new DomainTestHelper(this.mapper);
    this.createTransaction();
    this.content = this.mockMvc.perform(post("/transactions")
      .contentType(APPLICATION_JSON)
      .content(this.helper.toJson(this.transaction)))
      .andDo(print())
      .andExpect(status().isOk())
      .andReturn().getResponse().getContentAsString();
  }

  @AfterEach
  public void tearDown() throws Exception {
    this.mockMvc.perform(delete("/transactions/" + this.transaction.getAccountId())
      .contentType(APPLICATION_JSON)
      .content(this.helper.toJson(this.transaction)))
      .andDo(print())
      .andExpect(status().isOk());
    this.helper = null;
    this.transaction = null;
    this.content = null;
  }

  @Test
  public void testFindAllTransactions() throws Exception {
    String body = this.mockMvc.perform(get("/transactions")
      .contentType(APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andReturn().getResponse().getContentAsString();
    assertTrue(body.contains(this.content));
  }

  @Test
  public void testFindTransactionByAccountId() throws Exception {
    this.mockMvc.perform(get("/transactions/" + this.transaction.getAccountId())
      .contentType(APPLICATION_JSON)
      .content(this.helper.toJson(this.transaction)))
      .andDo(print())
      .andExpect(status().isOk());
  }

  @Test
  public void testAuthroriseTransactionByAccountId() throws Exception {
    String url = String.format("/transactions/authorize/%s/?balance=%s", this.transaction.getAccountId(), "1000.00");
    log.info(url);
    this.mockMvc.perform(get(url)
      .contentType(APPLICATION_JSON)
      .content(this.helper.toJson(this.transaction)))
      .andDo(print())
      .andExpect(status().isOk());
  }

  @Test
  public void testAuthroriseTransactionByAccountIdFail() throws Exception {
    String url = String.format("/transactions/authorize/%s/?balance=%s", this.transaction.getAccountId(), "900.00");
    log.info(url);
    this.mockMvc.perform(get(url)
      .contentType(APPLICATION_JSON)
      .content(this.helper.toJson(this.transaction)))
      .andDo(print())
      .andExpect(status().isBadRequest());
  }

  private void createTransaction() {
    this.transaction = Transaction.builder()
      .accountId("1")
      .transactionDate(LocalDateTime.now())
      .amount(new BigDecimal("1000.00"))
      .description("debit purchasing iphone")
      .method("debig").build();
  }

}
