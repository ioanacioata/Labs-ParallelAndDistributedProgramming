package com.programare_paralela_labs.domain;

import static java.lang.Thread.sleep;

public class Transfer implements Runnable {
	private Account fromAccount;
	private Account toAccount;
	private Long amount;

	public Transfer(Account fromAccount, Account toAccount, Long amount) {
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.amount = amount;
	}

	@Override
	public void run() {
		//acquire the locks -> to avoid deadlocks
		fromAccount.getLock().lock();
		while (!toAccount.getLock().tryLock()) {
			fromAccount.getLock().unlock();
			try {
				sleep((long) Math.random() * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			fromAccount.getLock().lock();
		}
		//verify if transfer can be made
		if (fromAccount.getBalance() < amount) {
			System.out.println("Not enough money. Account " + fromAccount.getBalance() + " amount: " + amount + "\n");
			fromAccount.getLock().unlock();
			toAccount.getLock().unlock();
		} else {
			//do transfer
			fromAccount.setBalance(fromAccount.getBalance() - amount);
			toAccount.setBalance(toAccount.getBalance() + amount);

			OperationRecord operationRecord = new OperationRecord(fromAccount, toAccount, amount);
			System.out.println(operationRecord);

			//add operation to operation records
			fromAccount.addOperation(operationRecord);
			toAccount.addOperation(operationRecord);

			//unlock
			fromAccount.getLock().unlock();
			toAccount.getLock().unlock();
		}
	}
}
