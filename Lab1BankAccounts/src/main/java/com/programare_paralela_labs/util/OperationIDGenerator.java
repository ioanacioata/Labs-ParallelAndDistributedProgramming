package com.programare_paralela_labs.util;

public class OperationIDGenerator {
	private static int counterOperation = 1;

	public static int generate() {
		return counterOperation++;
	}
}
