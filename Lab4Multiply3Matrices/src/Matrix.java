import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Matrix {
	public int rows;
	public int columns;
	private int[][] matrix;
	private Random random = new Random();

	public Matrix(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		matrix = new int[rows][columns];
	}

	public static Matrix multiply3Matrices(Matrix matrix1, Matrix matrix2, Matrix matrix3, int threadNo)
			throws InterruptedException {

		int tasksPerThread = matrix1.getRows() / threadNo;
		int remainder = matrix1.getRows() % threadNo;
		Thread[] threads = new Thread[threadNo];
		Thread[] finalThreads = new Thread[matrix3.getRows()];

		Matrix intermediaryMatrix = new Matrix(matrix1.getRows(), matrix3.getColumns());
		Matrix resultMatrix = new Matrix(matrix1.getRows(), matrix2.getColumns());

		ExecutorService intermediaryService = Executors.newFixedThreadPool(threadNo);
		ExecutorService finalService = Executors.newFixedThreadPool(matrix3.getRows());

		final Lock completedRowsLock = new ReentrantLock();

		final Condition condition = completedRowsLock.newCondition();

		//Counter of the completed elements for each row, in the intermediary matrix
		int[] completedRows = new int[matrix3.getRows()];
		for (int i = 0; i < matrix3.getRows(); i++) {
			completedRows[i] = 0;
		}

		//Create thread pool for intermediary matrix
		int startRow = 0, stopRow;
		for (int i = 0; i < threadNo; i++) {
			stopRow = startRow + tasksPerThread;
			if (remainder > 0) {
				stopRow += 1;
				remainder--;
			}
			threads[i] = new MatrixMultiplication(matrix1, matrix2, intermediaryMatrix, completedRowsLock, condition,
					completedRows, startRow, stopRow);
			startRow = stopRow;
		}

		//Create thread pool for final matrix
		startRow = 0;
		stopRow = 1;
		for (int i = 0; i < matrix3.getRows(); i++) {
			finalThreads[i] = new MatrixMultiplication(intermediaryMatrix, matrix3, resultMatrix, null, null, null,
					startRow, stopRow);
			startRow = stopRow;
			stopRow++;
		}


		long startTime = System.currentTimeMillis();

		for (int i = 0; i < threadNo; i++) {
			intermediaryService.execute(threads[i]);
		}
		for (int j = 0; j < matrix3.getRows(); j++) {
			completedRowsLock.lock();
			while (completedRows[j] != matrix3.getRows()) {
				condition.await();
			}
			finalService.execute(finalThreads[j]);
			completedRowsLock.unlock();
		}

		intermediaryService.shutdown();
		intermediaryService.awaitTermination(1, TimeUnit.MINUTES);
		finalService.shutdown();
		finalService.awaitTermination(1, TimeUnit.MINUTES);

		long endTime = System.currentTimeMillis();

		System.out.println("Start Time: " + startTime);
		System.out.println("End Time: " + endTime);
		System.out.println("Total time: " + (endTime - startTime) + " milliseconds");
		System.out.println("Total time: " + (endTime - startTime) / 1000.0 + " seconds");

		return resultMatrix;
	}

	public void printMatrix() {
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < columns; ++j) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("\n");
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int i, int j, int sum) {
		this.matrix[i][j] = sum;
	}

	public void generateMatrix() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				matrix[i][j] = random.nextInt(10);
			}
		}
	}
}
