package com.programare_paralela_labs;

import com.programare_paralela_labs.domain.Matrice;
import com.programare_paralela_labs.matrixoperations.MatriceAddition;
import com.programare_paralela_labs.matrixoperations.MatriceMultiplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {
	private static final int NR_LINES = 500;
	private static final int NR_COLUMNS = 500;
	private static final int NR_OF_THREADS = 1;

	public static void main(String[] args) throws InterruptedException {
		matrixMultiplicationExample();
//		matrixAdditionExample();
	}

	public static void matrixMultiplicationExample() throws InterruptedException {
		Matrice matrice1 = new Matrice(NR_LINES, NR_COLUMNS);
		matrice1.generateElements();
//		System.out.println(matrice1);

		Matrice matrice2 = new Matrice(NR_COLUMNS, NR_LINES);
		matrice2.generateElements();
//		System.out.println(matrice2);

		Matrice resultMatrice = new Matrice(NR_LINES, NR_LINES);
		resultMatrice.initializeElements();

		Thread[] threads = new Thread[NR_OF_THREADS];

		ExecutorService service = Executors.newFixedThreadPool(NR_OF_THREADS);

		int nrOfTasksPerThread = resultMatrice.getNrOfElements() / NR_OF_THREADS;
		int remainder = resultMatrice.getNrOfElements() % NR_OF_THREADS;
		int iStart = 0, jStart = 0, elementEndIndex = 0, nrOfOperations;

		//Create threads
		for (int i = 0; i < NR_OF_THREADS; i++) {
			//set index nr of the last element calculated by this thread
			elementEndIndex += nrOfTasksPerThread;
			nrOfOperations = nrOfTasksPerThread;
			if (remainder > 0) {
				elementEndIndex++;
				nrOfOperations++;
				remainder--;
			}
//			System.out.println("				Nr operation thread " + i + ": " + nrOfOperations);
			threads[i] = getMatriceMultiplicationThread(matrice1, matrice2, resultMatrice, iStart, jStart, nrOfOperations);
			iStart = elementEndIndex / resultMatrice.getNr_of_lines();
			jStart = elementEndIndex % resultMatrice.getNr_of_columns();
		}

		long startTime = System.currentTimeMillis();

		//Execute Threads
		for (int i = 0; i < NR_OF_THREADS; i++) {
			service.execute(threads[i]);
		}
		service.shutdown();
		service.awaitTermination(1, TimeUnit.SECONDS);

		long endTime = System.currentTimeMillis();

//		System.out.println("Result matrice: " + resultMatrice);

		System.out.println("Start time: " + startTime);
		System.out.println("End time: " + endTime);
		long timeOfExecution=endTime-startTime;
		System.out.println("Time: " + timeOfExecution);
	}

	public static void matrixAdditionExample() throws InterruptedException {
		Matrice matrice1 = new Matrice(NR_LINES, NR_COLUMNS);
		matrice1.generateElements();
//		System.out.println(matrice1);

		Matrice matrice2 = new Matrice(NR_LINES, NR_COLUMNS);
		matrice2.generateElements();
//		System.out.println(matrice2);

		Matrice resultMatrice = new Matrice(NR_LINES, NR_COLUMNS);
		resultMatrice.initializeElements();

		Thread[] threads = new Thread[NR_OF_THREADS];

		ExecutorService service = Executors.newFixedThreadPool(NR_OF_THREADS);

		int nrOfTasksPerThread = resultMatrice.getNrOfElements() / NR_OF_THREADS;
		int remainder = resultMatrice.getNrOfElements() % NR_OF_THREADS;
		int iStart = 0, jStart = 0, elementEndIndex = 0, nrOfOperations;

		//Create threads
		for (int i = 0; i < NR_OF_THREADS; i++) {
			//set index nr of the last element calculated by this thread
			elementEndIndex += nrOfTasksPerThread;
			nrOfOperations = nrOfTasksPerThread;
			if (remainder > 0) {
				elementEndIndex++;
				nrOfOperations++;
				remainder--;
			}
//			System.out.println("				Nr operation thread " + i + ": " + nrOfOperations);
			threads[i] = getMatriceAdditionThread(matrice1, matrice2, resultMatrice, iStart, jStart, nrOfOperations);
			iStart = elementEndIndex / resultMatrice.getNr_of_lines();
			jStart = elementEndIndex % resultMatrice.getNr_of_columns();
		}

		long startTime = System.currentTimeMillis();

		//Execute Threads
		for (int i = 0; i < NR_OF_THREADS; i++) {
			service.execute(threads[i]);
		}
		service.shutdown();
		service.awaitTermination(1, TimeUnit.SECONDS);

		long endTime = System.currentTimeMillis();

//		System.out.println("Result matrice: " + resultMatrice);

		System.out.println("Start time: " + startTime);
		System.out.println("End time: " + endTime);

		long timeOfExecution=endTime-startTime;
		System.out.println("Time: " + timeOfExecution);
	}

	private static MatriceAddition getMatriceAdditionThread(Matrice matrice1, Matrice matrice2, Matrice resultMatrice, int iStart, int jStart, int nrOfOperations) {
		return new MatriceAddition(matrice1, matrice2, resultMatrice, iStart, jStart, nrOfOperations);
	}

	private static MatriceMultiplication getMatriceMultiplicationThread(Matrice matrice1, Matrice matrice2, Matrice resultMatrice, int iStart, int jStart, int nrOfOperations) {
		return new MatriceMultiplication(matrice1, matrice2, resultMatrice, iStart, jStart, nrOfOperations);
	}
}
