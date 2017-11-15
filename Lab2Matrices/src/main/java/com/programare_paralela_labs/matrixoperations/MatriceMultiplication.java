package com.programare_paralela_labs.matrixoperations;

import com.programare_paralela_labs.domain.Matrice;

public class MatriceMultiplication extends MatriceOperation {
	public MatriceMultiplication(Matrice matrice1, Matrice matrice2, Matrice resultMatrice, int startLine, int startColumn, int nrElementsToCalculate) {
		super(matrice1, matrice2, resultMatrice, startLine, startColumn, nrElementsToCalculate);
	}

	protected int calculateValue(int lineIndex, int columnIndex) {
		int value = 0;
		for (int i = 0; i < super.matrice1.getNr_of_columns(); i++) {
			value += matrice1.getElement(lineIndex, i) * matrice2.getElement(i, columnIndex);
		}
		return value;
	}
}
