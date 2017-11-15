package com.programare_paralela_labs.matrixoperations;

import com.programare_paralela_labs.domain.Matrice;

public class MatriceAddition extends MatriceOperation {
	public MatriceAddition(Matrice matrice1, Matrice matrice2, Matrice resultMatrice, int startLine, int startColumn, int nrElementsToCalculate) {
		super(matrice1, matrice2, resultMatrice, startLine, startColumn, nrElementsToCalculate);
	}

	@Override
	protected int calculateValue(int lineIndex, int columnIndex) {
		return matrice1.getElement(lineIndex, columnIndex) + matrice2.getElement(lineIndex, columnIndex);
	}
}
