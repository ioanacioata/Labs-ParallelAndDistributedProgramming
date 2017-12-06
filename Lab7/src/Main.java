import java.util.Arrays;
import java.util.Random;

public class Main {

		public static final int SIZE = 16;

		public static void main(String[] args) throws InterruptedException {
//				Integer[] s = {5, 3, -6, 2, 7, 10, -2, 8};
				Integer[] s = generateRandomArray();
				Integer[] copys= Arrays.copyOf(s, s.length);

				printArray(s);
				System.out.println("\nRUN SEQUENCIAL: \n");
				Problem1.runSequencial(copys);

				System.out.println("\nRUN PARALLEL: \n");
				Problem1.run(s, 4);
		}

		private static Integer[] generateRandomArray() {
				Random random = new Random();
				Integer[] s = new Integer[SIZE];
				for (int i = 0; i < SIZE; i++) {
						s[i] = random.nextInt(20);
				}
				return s;
		}

		public static void printArray(Integer[] s) {
				StringBuilder str = new StringBuilder("s: ");
				for (int i = 0; i < s.length; i++) {
						str.append(s[i]).append(" ");
				}
				System.out.println(str.toString());
		}
}
