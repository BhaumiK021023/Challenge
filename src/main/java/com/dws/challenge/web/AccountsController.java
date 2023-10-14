package com.dws.challenge.web;

import com.dws.challenge.DTO.AccountDTO;
import com.dws.challenge.domain.Account;
import com.dws.challenge.exception.DuplicateAccountIdException;
import com.dws.challenge.service.AccountsService;
import com.dws.challenge.service.NotificationService;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/accounts")
@Slf4j
public class AccountsController {

  private final AccountsService accountsService;
  
  private static final Logger log = LoggerFactory.getLogger(AccountsController.class);


  @Autowired
  public AccountsController(AccountsService accountsService) {
    this.accountsService = accountsService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> createAccount(@RequestBody @Valid Account account) {
    log.info("Creating account {}", account);

    try {
    this.accountsService.createAccount(account);
    } catch (DuplicateAccountIdException daie) {
      return new ResponseEntity<>(daie.getMessage(), HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping(path = "/{accountId}")
  public Account getAccount(@PathVariable String accountId) {
    log.info("Retrieving account for id {}", accountId);
    return this.accountsService.getAccount(accountId);
  }

	/*
	 * @RequestMapping("/transfer")
	 * 
	 * @GetMapping(path ="/{fromAccountId}/{toAccountId}") public List<AccountDTO>
	 * transferMoney(@PathVariable String fromAccountId, @PathVariable String
	 * toAccountId){ return
	 * this.accountsService.transferMoney(fromAccountId,toAccountId);
	 * 
	 * }
	 */
  
  @GetMapping("/transfer")
  public List<AccountDTO> transferMoney(@RequestParam String fromAccountId, @RequestParam BigDecimal fromAccountAmt,
		  									@RequestParam String toAccountId, @RequestParam BigDecimal toAccountAmt){
	  
	  log.info(" TranserMoney api called for fromAccountId {}, fromAccount amount {},"
	  		+ " toAccountId {}, toAccount amount ", fromAccountId, fromAccountAmt,toAccountId,toAccountAmt);
	  
	  return this.accountsService.transferMoney(fromAccountId,toAccountId,fromAccountAmt,toAccountAmt);
	 
  }
  
}
