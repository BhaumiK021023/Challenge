package com.dws.challenge.service;

import com.dws.challenge.DTO.AccountDTO;
import com.dws.challenge.Thread.AccountThread;
import com.dws.challenge.domain.Account;
import com.dws.challenge.repository.AccountsRepository;
import com.dws.challenge.web.AccountsController;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountsService {

  @Getter
  private final AccountsRepository accountsRepository;
  @Getter
  private final NotificationService notificationService;
  private static final Logger log = LoggerFactory.getLogger(AccountsService.class);

  @Autowired
  public AccountsService(AccountsRepository accountsRepository,NotificationService notificationService) {
    this.accountsRepository = accountsRepository;
    this.notificationService=notificationService;
  }

  public void createAccount(Account account) {
    this.accountsRepository.createAccount(account);
  }

  public Account getAccount(String accountId) {
    return this.accountsRepository.getAccount(accountId);
  }

	public List<AccountDTO> transferMoney(String fromAccountId, String toAccountId, BigDecimal fromAccountAmt, BigDecimal toAccountAmt) {
	   Account fromAccount = this.accountsRepository.getAccount(fromAccountId);
	   Account toAccount = this.accountsRepository.getAccount(toAccountId);
	   
	   if(Objects.nonNull(fromAccount) && Objects.nonNull(toAccount)) {
		   AccountThread fromAccountThread = new AccountThread(fromAccount, toAccount, new AtomicReference<BigDecimal>(fromAccountAmt),notificationService);
	       AccountThread toAccountThread = new AccountThread(toAccount, fromAccount, new AtomicReference<BigDecimal>(toAccountAmt),notificationService);
	       
	       CompletableFuture.allOf(
	               CompletableFuture.runAsync(fromAccountThread),
	               CompletableFuture.runAsync(toAccountThread)
	       ).join();
	       	
	       AccountDTO fromAccountDTO= AccountDTO.builder().accountId(fromAccountId).balance(fromAccount.getBalance()).build();
	       AccountDTO toAccountDTO= AccountDTO.builder().accountId(toAccountId).balance(toAccount.getBalance()).build();

	      List<AccountDTO> list = new ArrayList<AccountDTO>();
	      list.add(fromAccountDTO);
	      list.add(toAccountDTO);
	      return list;	
	   }else {
		   log.error("fromAccountId "+fromAccountId+" or toAccountId "+toAccountId+" is not valid");
		   return null;
	   }
	         

	}
}
