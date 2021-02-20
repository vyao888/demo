package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
public class Transaction {
  private final String accountId;
  private final LocalDateTime transactionDate;
  private final BigDecimal amount;
  private final String method;
  private final String description;

}
