package com.programare_paralela_labs.domain;

import com.programare_paralela_labs.util.AccountIdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
	private String id;
	private Long balance;
	private List<OperationRecord> log;
	private Long initialBalance;

	private Lock lock;

	public Account(Long balance) {
		this.id = AccountIdGenerator.generate();
		this.balance = balance;
		this.initialBalance = balance;
		this.log = new ArrayList<>();
		this.lock = new ReentrantLock();
	}

	public Lock getLock() {
		return lock;
	}

	public String getId() {
		return id;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public List<OperationRecord> getLog() {
		return log;
	}

	public void addOperation(OperationRecord record) {
		this.log.add(record);
	}

	@Override
	public String toString() {
		return id + ", balance=" + balance;
	}

	public String getOperationsString() {
		String s = "";
		for (OperationRecord o : log) {
			s += o.toString() + ",	";
		}
		return s;
	}

	public boolean isConsistent() throws InterruptedException {
		this.getLock().tryLock(20, TimeUnit.SECONDS);
		long currentCalculatedBalance = initialBalance;
		for (OperationRecord o : log) {
			if (o.getFromAccount().getId().equals(this.getId())) {
				currentCalculatedBalance = currentCalculatedBalance - o.getAmount();
			} else {
				if (o.getToAccount().getId().equals(this.getId())) {
					currentCalculatedBalance = currentCalculatedBalance + o.getAmount();
				}
			}
		}
		this.getLock().unlock();
		if (currentCalculatedBalance == this.getBalance()) {
			return true;
		}
		return false;
	}
}
