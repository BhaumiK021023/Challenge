package com.dws.challenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;
import lombok.Data;

import javax.validation.constraints.Min;

import javax.validation.constraints.NotNull;

@Data
public class Account {

  @NotNull
 @NotEmpty
  private final String accountId;

  @NotNull
  @Min(value = 0, message = "Initial balance must be positive.")
  private BigDecimal balance;

  public Account(String accountId) {
    this.accountId = accountId;
    this.balance = BigDecimal.ZERO;
  }

  @JsonCreator
  public Account(@JsonProperty("accountId") String accountId,
    @JsonProperty("balance") BigDecimal balance) {
    this.accountId = accountId;
    this.balance = balance;
  }

public synchronized void withdraw(BigDecimal amount) {
	
	checkAmountNonNegative(amount);
	
	if(this.balance.compareTo(amount) < 0) {
		 throw new IllegalArgumentException("not enough money to be withdraw");
	}
	this.balance=this.balance.subtract(amount);
	// TODO Auto-generated method stub
	
}

public synchronized void deposit(BigDecimal amount) {
	
	checkAmountNonNegative(amount);
	
	this.balance=this.balance.add(amount);
	
}

private  void checkAmountNonNegative(BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) < 0) {
        	throw new IllegalArgumentException("negative amount can be deposited or Withrawed");
    }
    
}

}
