import java.util.Arrays;
import java.util.Random;

public class Main {

		public static final int SIZE = 16;
		private static final int POWER_OF_TWO = 5;

		public static void main(String[] args) throws InterruptedException {
				problem1();
				System.out.println("===========================================");
				problem2();
		}

		private static void problem1() throws InterruptedException {
				//				Integer[] s = {5, 3, -6, 2, 7, 10, -2, 8};
				Integer[] s = generateRandomArray();
				Integer[] copys = Arrays.copyOf(s, s.length);

				printArray(s);
				System.out.println("\nRUN SEQUENCIAL: \n");
				Problem1.runSequencial(copys);

				System.out.println("\nRUN PARALLEL: \n");
				Problem1.run(s, 4);
		}

		private static void problem2() {
				Problem2 p02 = new Problem2(generateBigNumberArray(POWER_OF_TWO));
//        String[] arr = {"2048", "70000", "876", "1"};
//        Problem2 p02 = new Problem2(arr);
				try {
						p02.run();
				} catch (InterruptedException e) {
						e.printStackTrace();
				}
		}

		public static String[] generateBigNumberArray(int powerOfTwo) {
				Random random = new Random();
				//compute the total number of elements from the list of big numbers
				int noOfElements = (int) Math.pow(2, powerOfTwo);
				String[] generatedS = new String[noOfElements];
				//each element from the list is set to a generated a big number
				for (int j = 0; j < noOfElements; j++) {
						StringBuilder bigNumber = new StringBuilder();
						//choose a random size of the big number between 5 and 15
						int bigNoLength = random.nextInt(16) + 5;
						//generate the digits of the big number
						for (int i = 0; i < bigNoLength; i++) {
								bigNumber.append(random.nextInt(10));
						}
						generatedS[j] = bigNumber.toString();
				}
				return generatedS;
		}

		private static Integer[] generateRandomArray() {
				Random random = new Random();
				Integer[] s = new Integer[SIZE];
				for (int i = 0; i < SIZE; i++) {
						s[i] = random.nextInt(20);
				}
				return s;
		}

		public static void printArray(Object[] s) {
				StringBuilder str = new StringBuilder("s: ");
				for (int i = 0; i < s.length; i++) {
						str.append(s[i]).append(" ");
				}
				System.out.println(str.toString());
		}
}
