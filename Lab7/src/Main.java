import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

		public static final int SIZE = 1024;

		public static void main(String[] args) throws InterruptedException {
				//Integer[] s = {5, 3, -6, 2, 7, 10, -2, 8};
				Integer[] s = generateRandomArray();

				printArray(s);
				lab7pb1(s);
		}

		private static Integer[] generateRandomArray() {
				Random random = new Random();
				Integer[] s = new Integer[SIZE];
				for (int i = 0; i < SIZE; i++) {
						s[i] = random.nextInt(20);
				}
				return s;
		}

		/**
		 * 1. Given a sequence of n numbers, compute the sums of the first k numbers, for each k between 1 and n.
		 * Parallelize the computations, to optimize for low latency on a large number of processors. Use at most 2*n
		 * additions, but no more than 2*log(n) additions on each computation path from inputs to an output. Example: if
		 * the input sequence is 1 5 2 4, then the output should be 1 6 8 12.
		 * <p>
		 * Used link: https://cs.wmich.edu/gupta/teaching/cs5260/5260Sp15web/lectureNotes/thm14%20-%20parallel%20prefix
		 * %20from%20Ottman.pdf
		 * <p>
		 * !!! works only with powers of 2 because it constructs a binary tree
		 *
		 * @param s - array
		 * @throws InterruptedException
		 */
		private static void lab7pb1(Integer[] s) throws InterruptedException {
				ExecutorService service = Executors.newFixedThreadPool(4);
				//calculate from even positions - bottom up (binary tree)
				for (int d = 0; d < Math.log(s.length); d++) {
						int totalNumberOfTasks = (int) (s.length / Math.pow(2, d + 1));
						CountDownLatch latch = new CountDownLatch(totalNumberOfTasks);

						for (int i = 0; i < s.length; i += Math.pow(2, d + 1)) {
//								System.out.println("i= " + i);
//								System.out.println("d= " + d);
								int finalI = i;
								int finalD = d;
								service.execute(() -> {
//										System.out.println("s[ " + (int) (finalI + Math.pow(2, finalD + 1) - 1) + "]= s[" + (int) (finalI
//												+ Math
//												.pow(2, finalD) - 1) + "] + s[" + (int) (finalI + Math.pow(2, finalD + 1) - 1) + "]");

										s[(int) (finalI + Math.pow(2, finalD + 1) - 1)] =
												s[(int) (finalI + Math.pow(2, finalD) - 1)] + s[(int) (finalI + Math.pow(2, finalD + 1) - 1)];
										latch.countDown();
								});
						}

						latch.await();
				}

				System.out.println("After bottom up: ");
				printArray(s);

				int last = s[SIZE - 1];

				//calculate from odd positions - top down (binary tree)
				s[SIZE - 1] = 0;

				for (int d = (int) Math.log(s.length); d >= 0; d--) {
						int taskNumber = (int) (s.length / Math.pow(2, d + 1));
						CountDownLatch latch = new CountDownLatch(taskNumber);
						for (int i = 0; i < s.length; i += Math.pow(2, d + 1)) {
								int finalI = i;
								int finalD = d;
								service.execute(() -> {
										int temp = s[(int) (finalI + Math.pow(2, finalD)) - 1];
										s[(int) (finalI + Math.pow(2, finalD)) - 1] = s[(int) (finalI + Math.pow(2, finalD + 1)) - 1];
										s[(int) (finalI + Math.pow(2, finalD + 1)) - 1] = temp + s[(int) (finalI + Math.pow(2, finalD + 1)
										) - 1];
										latch.countDown();
								});
						}
						latch.await();
				}

				service.shutdownNow();

				System.out.println("After Top- down: ");
				printArray(s);

				//shift array for result
				Integer[] result = new Integer[s.length];
				for (int i = 0; i < result.length - 1; i++) {
						result[i] = s[i + 1];
				}
				result[result.length - 1] = last;

				System.out.println("Final result: ");
				printArray(result);
		}

		private static void printArray(Integer[] s) {
				StringBuilder str = new StringBuilder("s: ");
				for (int i = 0; i < s.length; i++) {
						str.append(s[i]).append(" ");
				}
				System.out.println(str.toString());
		}
}
