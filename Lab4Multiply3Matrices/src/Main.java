import java.io.IOException;

public class Main {

	public static int NR_OF_THREADS = 4;
	public static int NR_ROWS = 1000;
	public static int NR_COLUMNS = 1000;

	public static void main(String[] args) throws InterruptedException, IOException {

		Matrix matrix1 = new Matrix(NR_ROWS, NR_COLUMNS);
		matrix1.generateMatrix();
		Matrix matrix2 = new Matrix(NR_COLUMNS, NR_ROWS);
		matrix2.generateMatrix();
		Matrix matrix3 = new Matrix(NR_ROWS, NR_ROWS);
		matrix3.generateMatrix();

//		matrix1.printMatrix();
//		matrix2.printMatrix();
//		matrix3.printMatrix();

		Matrix resultMatrix = Matrix.multiply3Matrices(matrix1, matrix2, matrix3, NR_OF_THREADS);

//		resultMatrix.printMatrix();
	}
}
