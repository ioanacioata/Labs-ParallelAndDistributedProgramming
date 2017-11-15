package com.programare_paralela_labs.matrixoperations;

import com.programare_paralela_labs.domain.Matrice;

import java.util.concurrent.Callable;

/**
 * These are the tasks for the threads in the thread pool
 */
public abstract class MatriceOperation implements Callable<Integer> {
	protected Matrice matrice1, matrice2;
	protected int line, column;

	public MatriceOperation(Matrice matrice1, Matrice matrice2, int line, int column) {
		this.matrice1 = matrice1;
		this.matrice2 = matrice2;
		this.line = line;
		this.column = column;
	}

	@Override
	public Integer call() throws Exception {
		return calculateValue();
	}
	protected abstract int calculateValue();
}
