package com.programare_paralela_labs;

import com.programare_paralela_labs.domain.Account;
import com.programare_paralela_labs.domain.OperationRecord;
import com.programare_paralela_labs.domain.Transfer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class App {
	private static final int NR_OF_TRANSFERS = 1000;
	private static final int NR_OF_OPERATIONS_EXECUTED_BEFORE_CONSISTENCY_CHECK = 100;
	private static final String NOT_CONSISTENT_TRANSFERS_MESSAGE = "Not consistent transfers.\n";
	private static final int NUMBER_OF_ACCOUNTS = 20;
	private static final String CONSISTENT_TRANSFER_MESSAGE = "The transfers are consistent. GJ!\n";
	private static Random random = new Random();

	public static void main(String[] args) {
		try {
//			twoAccountsExample();
			exampleMultipleAccounts();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//-------------------------
	//Example with multiple accounts
	//-------------------------
	private static void exampleMultipleAccounts() throws InterruptedException {
		List<Account> accounts = getRandomAccounts();

		List<Thread> threads = new ArrayList<>();
		//create threads
		for (int i = 0; i < NR_OF_TRANSFERS; i++) {
			Thread t = generateRandomTransfer(accounts);
			threads.add(t);
		}

		//start threads/transfers
		for (int i = 0; i < NR_OF_TRANSFERS; i++) {
			threads.get(i).start();
			//Check consistency during the transfers
			if (i % NR_OF_OPERATIONS_EXECUTED_BEFORE_CONSISTENCY_CHECK == 0) {
				if (doConsistencyCheckForAccountList(accounts)) {
					System.out.println(CONSISTENT_TRANSFER_MESSAGE);
				} else {
					System.out.println(NOT_CONSISTENT_TRANSFERS_MESSAGE);
				}
			}
		}

		//wait for the threads to end their execution
		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		//do a final consistency check
		if (doConsistencyCheckForAccountList(accounts)) {
			System.out.println(CONSISTENT_TRANSFER_MESSAGE);
		} else {
			System.out.println(NOT_CONSISTENT_TRANSFERS_MESSAGE);
		}
	}

	private static boolean doConsistencyCheckForAccountList(List<Account> accounts) throws InterruptedException {
		for (Account a : accounts) {
			if (!a.isConsistent()) {
				return false;
			}
		}
		return true;
	}

	private static List<Account> getRandomAccounts() {
		List<Account> accounts = new ArrayList<>();
		for (int i = 0; i < NUMBER_OF_ACCOUNTS; i++) {
			int nonzeroNumber = generateRandomNumberLessThan(10) + 1;
			Account a = new Account(10000l * nonzeroNumber);
			accounts.add(a);
		}
		return accounts;
	}

	private static Thread generateRandomTransfer(List<Account> accounts) {
		Thread t;
		//alternate transfers
		int sourceAccountIndex = generateRandomNumberLessThan(NUMBER_OF_ACCOUNTS);
		Account account1 = accounts.get(sourceAccountIndex);
		Account account2 = accounts.get(generateRandomNumberLessThanDifferentThan(NUMBER_OF_ACCOUNTS, sourceAccountIndex));
		long amount = (generateRandomNumberLessThan(10) + 1) * 10l;

		t = new Thread(new Transfer(account1, account2, amount));
		return t;
	}

	private static int generateRandomNumberLessThan(Integer i) {
		return random.nextInt(i);
	}

	private static int generateRandomNumberLessThanDifferentThan(Integer i, Integer differentThanThis) {
		int n = generateRandomNumberLessThan(i);
		while (n == differentThanThis) {
			n = generateRandomNumberLessThan(i);
		}
		return n;
	}

	//-------------------------
	//Example with 2 accounts
	//-------------------------
	private static void twoAccountsExample() throws InterruptedException {
		Account account1 = new Account(100000l);
		Account account2 = new Account(200000l);

		List<Thread> threads = new ArrayList<>();
		//create threads
		for (int i = 0; i < NR_OF_TRANSFERS; i++) {
			Thread t;
			//alternate transfers
			if (i % 3 == 1) {
				t = new Thread(new Transfer(account1, account2, i + 1l));
			} else {
				t = new Thread(new Transfer(account2, account1, i + 2l));
			}
			threads.add(t);
		}

		//start threads/transfers
		for (int i = 0; i < NR_OF_TRANSFERS; i++) {
			threads.get(i).start();
			//Check consistency during the transfers
			if (i % NR_OF_OPERATIONS_EXECUTED_BEFORE_CONSISTENCY_CHECK == 0) {
				printConsistency(account1, account2);
			}
		}

		//wait for the threads to end their execution
		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("\nOp: " + account1.getOperationsString() + "\n");
		//do a final consistency check
		try {
			printConsistency(account1, account2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void printConsistency(Account account1, Account account2) throws InterruptedException {
		if (!consistencyCheck(account1, account2)) {
			System.out.println(NOT_CONSISTENT_TRANSFERS_MESSAGE);
		} else {
			System.out.println(CONSISTENT_TRANSFER_MESSAGE);
		}
	}

	private static boolean consistencyCheck(Account account1, Account account2) throws InterruptedException {
		long transferredFrom1 = 0l;
		long transferredFrom2 = 0l;
		if (account1.getLog().size() != account2.getLog().size()) {
			return false;
		}
		account1.getLock().tryLock(20, TimeUnit.SECONDS);
		account2.getLock().tryLock(20, TimeUnit.SECONDS);

		for (OperationRecord record : account1.getLog()) {
			transferredFrom1 += record.getAmount();
		}
		for (OperationRecord record : account2.getLog()) {
			transferredFrom2 += record.getAmount();
		}
		account1.getLock().unlock();
		account2.getLock().unlock();
		if (transferredFrom1 != transferredFrom2) {
			return false;
		}
		return true;
	}
}
