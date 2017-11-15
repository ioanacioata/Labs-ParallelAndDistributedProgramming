package com.programare_paralela_labs.matrixoperations;

import com.programare_paralela_labs.domain.Matrice;

public class MatriceAddition extends MatriceOperation {
	public MatriceAddition(Matrice matrice1, Matrice matrice2, int line, int column) {
		super(matrice1, matrice2, line, column);
	}

	@Override
	protected int calculateValue() {
		return matrice1.getElement(line, column) + matrice2.getElement(line, column);
	}
}
