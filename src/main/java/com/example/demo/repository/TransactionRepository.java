package com.example.demo.repository;

import com.example.demo.domain.Transaction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class TransactionRepository {

  List<Transaction> transactions = new ArrayList<>();

  public String save(Transaction transaction) {
    Objects.requireNonNull(transaction, "Transaction is not valid");
    if(this.transactions.contains(transaction)) {
      throw new IllegalArgumentException("Transaction already exists.");
    }
    this.transactions.add(transaction);
    return transaction.getAccountId();
  }

  public Optional<Transaction> get(String accountId) {
    if(StringUtils.isEmpty(accountId)) {
      throw new IllegalArgumentException(String.format("Invalid accountId: %s", accountId));
    }
    return this.transactions.stream()
      .filter(t -> accountId.equals(t.getAccountId()))
      .findFirst();
  }

  public List<Transaction> get() {
    return this.transactions;
  }


  public boolean delete(String accountId) {
    if(StringUtils.isEmpty(accountId)) {
      throw new IllegalArgumentException(String.format("Invalid accountId: %s", accountId));
    }

    Optional<Transaction> optional = this.transactions.stream()
      .filter(t -> accountId.equals(t.getAccountId()))
      .findFirst();

    if(optional.isPresent()) {
      return this.transactions.remove(optional.get());
    } else {
      return false;
    }

  }


}
