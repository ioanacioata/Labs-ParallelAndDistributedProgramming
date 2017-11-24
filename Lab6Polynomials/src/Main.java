public class Main {
	public static void main(String[] args) throws InterruptedException {

		Polynomial p = new Polynomial(15);
		Polynomial q = new Polynomial(15);
//		Polynomial p = new Polynomial(new ArrayList<Integer>(Arrays.asList(5, 4, 2, 4)));
//		Polynomial q = new Polynomial(new ArrayList<Integer>(Arrays.asList(6, 3, 7)));

		System.out.println("pol p:" + p);
		System.out.println("pol q" + q);

		System.out.println("Simple sequential multiplication of polynomials: ");
		System.out.println(PolynomialOperation.multiplicationSequentialForm(p, q));

		System.out.println("Simple parallel multiplication of polynomials: ");
		System.out.println(PolynomialOperation.multiplicationParallelizedForm(p, q, 5));

		System.out.println("Karatsuba sequential multiplication of polynomials: ");
		System.out.println(PolynomialOperation.multiplicationKaratsubaSequentialForm(p, q));
	}
}
