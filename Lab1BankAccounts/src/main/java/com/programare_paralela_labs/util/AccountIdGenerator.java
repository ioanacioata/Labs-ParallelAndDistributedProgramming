package com.programare_paralela_labs.util;

public class AccountIdGenerator {
	private static int counterAccount = 0;

	public static String generate() {
		counterAccount++;
		return "Account" + counterAccount;
	}
}
