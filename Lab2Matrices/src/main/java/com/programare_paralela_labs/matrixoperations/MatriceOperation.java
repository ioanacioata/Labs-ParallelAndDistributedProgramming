package com.programare_paralela_labs.matrixoperations;

import com.programare_paralela_labs.domain.Matrice;

public abstract class MatriceOperation extends Thread implements Runnable {
	protected Matrice matrice1, matrice2, resultMatrice;
	protected int startLine, startColumn, nrElementsToCalculate;

	public MatriceOperation(Matrice matrice1, Matrice matrice2, Matrice resultMatrice, int startLine, int startColumn, int nrElementsToCalculate) {
		this.matrice1 = matrice1;
		this.matrice2 = matrice2;
		this.resultMatrice = resultMatrice;
		this.startLine = startLine;
		this.startColumn = startColumn;
		this.nrElementsToCalculate = nrElementsToCalculate;
//		System.out.println("Start line: "+startLine+" Start column: "+startColumn);
	}

	@Override
	public void run() {
		int j = startColumn;
		for (int i = startLine; i < resultMatrice.getNr_of_columns(); i++) {
			while (j < resultMatrice.getNr_of_columns()) {
				//perform multiplication
				int value = calculateValue(i, j);

//				System.out.println("Value "+i+","+j+": "+value);
				resultMatrice.setElement(i, j, value);
				j++;
				nrElementsToCalculate--;
				if (nrElementsToCalculate == 0) {
					return;
				}
			}
			j = 0;
		}
	}

	protected abstract int calculateValue(int lineIndex, int columnIndex);
}
