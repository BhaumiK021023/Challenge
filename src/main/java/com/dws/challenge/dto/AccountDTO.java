package com.dws.challenge.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AccountDTO {
	
	String accountId;
	BigDecimal balance;

}
