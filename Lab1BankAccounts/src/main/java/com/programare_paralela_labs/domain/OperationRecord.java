package com.programare_paralela_labs.domain;

import com.programare_paralela_labs.util.OperationIDGenerator;

public class OperationRecord {
	private int serialNumber;
	private Account fromAccount;
	private Account toAccount;
	private Long amount;

	public OperationRecord(Account fromAccount, Account toAccount, Long amount) {
		this.serialNumber= OperationIDGenerator.generate();
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.amount = amount;
	}

	public Account getFromAccount() {
		return fromAccount;
	}

	public Account getToAccount() {
		return toAccount;
	}

	public Long getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "OperationRecord{" +
				"serialNumber=" + serialNumber +
				", fromAccount=" + fromAccount +
				", toAccount=" + toAccount +
				", amount=" + amount +
				'}';
	}
}
