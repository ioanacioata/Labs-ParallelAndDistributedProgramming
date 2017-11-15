package com.programare_paralela_labs;

import com.programare_paralela_labs.domain.Matrice;
import com.programare_paralela_labs.matrixoperations.MatriceMultiplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class App {
	private static final int NR_LINES = 500;
	private static final int NR_COLUMNS = 500;
	private static final int NR_OF_THREADS = 4;

	public static void main(String[] args) throws InterruptedException {
		matrixMultiplicationExample();
	}

	private static void matrixMultiplicationExample() throws InterruptedException {
		Matrice matrice1, matrice2, resultMatrice;
		matrice1 = new Matrice(NR_LINES, NR_COLUMNS);
		matrice1.generateElements();
//		System.out.println(matrice1);

		matrice2 = new Matrice(NR_COLUMNS, NR_LINES);
		matrice2.generateElements();
//		System.out.println(matrice2);

		resultMatrice = new Matrice(NR_LINES, NR_LINES);
		resultMatrice.initializeElements();

		ExecutorService service = Executors.newFixedThreadPool(NR_OF_THREADS);
		List<MatriceMultiplication> listTasks = getTasks(matrice1, matrice2, resultMatrice);
		List<Future<Integer>> listResults = service.invokeAll(listTasks);

		for (int i = 0; i < resultMatrice.getNr_of_lines(); i++) {
			for (int j = 0; j < resultMatrice.getNr_of_columns(); j++) {
				try {
					resultMatrice.setElement(i, j, listResults.get(NR_COLUMNS * i + j).get());
				} catch (ExecutionException e) {
					e.printStackTrace();
					service.shutdown();
				}
			}
		}
		long startTime = System.currentTimeMillis();

		service.shutdown();
		service.awaitTermination(1, TimeUnit.SECONDS);

		long endTime = System.currentTimeMillis();

		System.out.println("Start time: " + startTime);
		System.out.println("End time: " + endTime);
		long timeOfExecution = endTime - startTime;
		System.out.println("Time: " + timeOfExecution);

//		System.out.println("Result matrice: " + resultMatrice);
	}

	private static List<MatriceMultiplication> getTasks(Matrice matrice1, Matrice matrice2, Matrice resultMatrice) {
		List<MatriceMultiplication> tasks = new ArrayList<>();
		for (int i = 0; i < resultMatrice.getNr_of_lines(); i++) {
			for (int j = 0; j < resultMatrice.getNr_of_columns(); j++) {
				tasks.add(new MatriceMultiplication(matrice1, matrice2, i, j));
			}
		}
		return tasks;
	}
}
