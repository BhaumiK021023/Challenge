package com.dws.challenge.DTO;

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
