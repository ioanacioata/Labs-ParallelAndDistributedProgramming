package com.programare_paralela_labs.matrixoperations;

import com.programare_paralela_labs.domain.Matrice;

public class MatriceMultiplication extends MatriceOperation {
	public MatriceMultiplication(Matrice matrice1, Matrice matrice2, int line, int column) {
		super(matrice1, matrice2, line, column);
	}

	protected int calculateValue() {
		int value = 0;
		for (int i = 0; i < super.matrice1.getNr_of_columns(); i++) {
			value += matrice1.getElement(line, i) * matrice2.getElement(i, column);
		}
		return value;
	}
}
