package com.example.demo.controller;

import com.example.demo.domain.ResourceNotFoundException;
import com.example.demo.domain.Transaction;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.Util;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@RestController
@Log
@RequestMapping("/transactions")
public class TransactionController {

  @Autowired
  private TransactionRepository repository;

  @GetMapping("/authorize/{accountId}")
  public ResponseEntity<Boolean> authorisze(@PathVariable(value = "accountId") String accountId,
        @RequestParam(value = "balance") String balance) {
    Util.validate(accountId, "AccountId");
    Util.validate(balance, "Balance");

    Optional<Transaction> optional = this.repository.get(accountId);
    if(!optional.isPresent()) {
        log.severe(String.format("Failed to find transaction by accountId: %s", accountId));
        return ResponseEntity.badRequest().body(false);
      };

    BigDecimal b = new BigDecimal(balance);
    BigDecimal a = optional.get().getAmount();
    BigDecimal r = a.signum() == -1 ? a.add(b) : b.subtract(a);
    boolean auth = r.signum() != -1;

    return auth ? ResponseEntity.ok().body(auth) : ResponseEntity.badRequest().body(auth);
  }

  @PostMapping()
  public ResponseEntity<String> createTransaction(@RequestBody Transaction transaction) {
    String accountId = this.repository.save(transaction);
    return ResponseEntity.ok().body(accountId);
  }

  @GetMapping("/{accountId}")
  public ResponseEntity<Transaction> getByAccountId(@PathVariable(value = "accountId") String accountId)
    throws ResourceNotFoundException {
    Transaction transaction = this.repository.get(accountId)
      .orElseThrow(() -> new ResourceNotFoundException("Transaction not found for account id :: " + accountId));
    log.info(transaction.toString());
    return ResponseEntity.ok().body(transaction);
  }

  @GetMapping
  public ResponseEntity<List<Transaction>> getAllTransactions()
    throws ResourceNotFoundException {
    List<Transaction> list = this.repository.get();
    log.info(list.toString());
    return ResponseEntity.ok().body(list);
  }

  @DeleteMapping("/{accountId}")
  public ResponseEntity<Boolean> deletePhone(@PathVariable(value = "accountId") String accountId)
    throws ResourceNotFoundException {
    boolean ok = this.repository.delete(accountId);
    if(!ok) {
      log.warning(String.format("No transaction found for accountId:%s", accountId));
      return ResponseEntity.badRequest().body(ok);
    } else {
      return ResponseEntity.ok().body(ok);
    }
  }

}
