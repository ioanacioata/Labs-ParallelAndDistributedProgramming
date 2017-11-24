import java.util.concurrent.ExecutionException;

public class Main {
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		Polynomial p = new Polynomial(15);
		Polynomial q = new Polynomial(15);
//		Polynomial p = new Polynomial(new ArrayList<Integer>(Arrays.asList(5, 4, 2, 4)));
//		Polynomial q = new Polynomial(new ArrayList<Integer>(Arrays.asList(6, 3, 7)));

		System.out.println("pol p:" + p);
		System.out.println("pol q" + q);
		System.out.println("\n");

		//Simple
		multiplication1(p, q);
		multiplication2(p, q);

		//Karatsuba
		multiplication3(p, q);
		multiplication4(p, q);
	}

	private static void multiplication4(Polynomial p, Polynomial q) throws ExecutionException, InterruptedException {
		long startTime = System.currentTimeMillis();
		Polynomial result4 = PolynomialOperation.multiplicationKaratsubaParallelizedForm(p, q, 0);
		long endTime = System.currentTimeMillis();
		System.out.println("Karatsuba parallel multiplication of polynomials: ");
		System.out.println(result4);
		System.out.println("Execution time : " + (endTime - startTime) + " ms\n");
	}

	private static void multiplication3(Polynomial p, Polynomial q) {
		long startTime = System.currentTimeMillis();
		Polynomial result3 = PolynomialOperation.multiplicationKaratsubaSequentialForm(p, q);
		long endTime = System.currentTimeMillis();
		System.out.println("Karatsuba sequential multiplication of polynomials: ");
		System.out.println(result3);
		System.out.println("Execution time : " + (endTime - startTime) + " ms\n");
	}

	private static void multiplication2(Polynomial p, Polynomial q) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		Polynomial result2 = PolynomialOperation.multiplicationParallelizedForm(p, q, 5);
		long endTime = System.currentTimeMillis();
		System.out.println("Simple parallel multiplication of polynomials: ");
		System.out.println(result2);
		System.out.println("Execution time : " + (endTime - startTime) + " ms\n");
	}

	private static void multiplication1(Polynomial p, Polynomial q) {
		long startTime = System.currentTimeMillis();
		Polynomial result1 = PolynomialOperation.multiplicationSequentialForm(p, q);
		long endTime = System.currentTimeMillis();
		System.out.println("Simple sequential multiplication of polynomials: ");
		System.out.println(result1);
		System.out.println("Execution time : " + (endTime - startTime) + " ms\n");
	}
}
