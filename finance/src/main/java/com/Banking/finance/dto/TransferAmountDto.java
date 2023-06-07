package com.Banking.finance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferAmountDto {
	
	private long sourceCustomerId;
	private long destinationCustomerId;
	private long sourceBankId;
	private long destinationBankId;
	private long amount;
	
}
