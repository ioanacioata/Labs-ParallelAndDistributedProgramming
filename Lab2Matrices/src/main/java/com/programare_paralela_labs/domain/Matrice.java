package com.programare_paralela_labs.domain;

import java.util.ArrayList;
import java.util.Random;

public class Matrice {
	private int nr_of_lines;
	private int nr_of_columns;
	private ArrayList<ArrayList<Integer>> matrix;
	private Random randomGenerator = new Random();

	public Matrice(int nr_of_lines, int nr_of_columns) {
		this.nr_of_lines = nr_of_lines;
		this.nr_of_columns = nr_of_columns;
		matrix = new ArrayList<ArrayList<Integer>>();
	}

	public void generateElements() {
		for (int i = 0; i < nr_of_lines; i++) {
			matrix.add(new ArrayList<>());
			for (int j = 0; j < nr_of_columns; j++) {
				//generate elements of the matrixoperations
				matrix.get(i).add(randomGenerator.nextInt(10));
			}
		}
	}

	public int getNrOfElements() {
		return nr_of_columns * nr_of_lines;
	}

	public void setElement(int lineNr, int columnNr, int value) {
		//if (lineNr >= 0 && lineNr < nr_of_lines && columnNr >= 0 && columnNr < nr_of_columns) {
			this.matrix.get(lineNr).set(columnNr, value);
//		}
//		throw new MatriceException("Index of of bond; This is not a valid position in this matrice!");
	}

	public void initializeElements() {
		for (int i = 0; i < nr_of_lines; i++) {
			matrix.add(new ArrayList<>());
			for (int j = 0; j < nr_of_columns; j++) {
				//add only 0 elements
				matrix.get(i).add(0);
			}
		}
	}

	public Integer getElement(int nrOfLine, int nrOfColumn) {
		return matrix.get(nrOfLine).get(nrOfColumn);
	}

	@Override
	public String toString() {
		String stringMatrix = "";
		for (int i = 0; i < nr_of_lines; i++) {
			for (int j = 0; j < nr_of_columns; j++) {
				stringMatrix += matrix.get(i).get(j) + " ";
			}
			stringMatrix += "\n";
		}
		return "Matrice{" +
				"nr_of_lines=" + nr_of_lines +
				", nr_of_columns=" + nr_of_columns +
				'}' + "\n" + stringMatrix;
	}

	public int getNr_of_lines() {
		return nr_of_lines;
	}

	public int getNr_of_columns() {
		return nr_of_columns;
	}
}
