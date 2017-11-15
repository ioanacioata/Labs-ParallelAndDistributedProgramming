import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MatrixMultiplication extends Thread implements Runnable {
	private final int[] completedRows;
	private final Condition condition;
	private final Lock lock;
	Matrix matrix1, matrix2, matrix3;
	protected int startRow, stopRow;

	public MatrixMultiplication(Matrix matrix1, Matrix matrix2, Matrix matrix3, Lock lock, Condition condition, int[]
			completedRows, int iStart, int iStop) {
		this.matrix1 = matrix1;
		this.matrix2 = matrix2;
		this.matrix3 = matrix3;
		this.startRow = iStart;
		this.stopRow = iStop;
		this.lock = lock;
		this.condition = condition;
		this.completedRows = completedRows;
	}

	@Override
	public void run() {
		for (int i = startRow; i < stopRow; i++) {
			for (int j = 0; j < matrix2.getRows(); j++) {
				for (int k = 0; k < matrix1.getColumns(); k++) {
					matrix3.setMatrix(i, j, matrix3.getMatrix()[i][j] + matrix1.getMatrix()[i][k] * matrix2.getMatrix
							()[k][j]);
				}
				if (lock != null) {
					lock.lock();
					completedRows[i]++;
					if (completedRows[i] == matrix3.getRows()) {
						condition.signal();
					}
//					String str= "Completed rows: ";
//					for (int k = 0; k < completedRows.length; k++) {
//						str+= completedRows[k] + " ";
//					}
//					System.out.println(str);

					lock.unlock();
				}
			}
		}
	}
}
