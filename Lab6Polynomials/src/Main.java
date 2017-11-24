import java.util.ArrayList;
import java.util.Arrays;

public class Main {
	public static void main(String[] args) throws InterruptedException {

<<<<<<< HEAD
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
=======
		Polynomial p = new Polynomial(10);
		Polynomial q = new Polynomial(10);
//		Polynomial p = new Polynomial(new ArrayList<Integer>(Arrays.asList(1,1))); //1x^1 + 1x^0
//		Polynomial q = new Polynomial(new ArrayList<Integer>(Arrays.asList(1,3))); //3x^1 + 1x^0  => 3x^2 + 4x^1 + 1x^0
		System.out.println("pol p:"+p);
		System.out.println("pol q"+q);
		System.out.println("Simple sequential multiplication of polynomials: ");
		System.out.println(PolynomialOperation.multiplicationSequencialForm(p,q));
		System.out.println("Simple parallel multiplication of polynomials: ");
		System.out.println(PolynomialOperation.multiplicationParallelizedForm(p,q,5));

>>>>>>> master
	}
}
