public class Main {
	public static void main(String[] args) {

		Polynomial p = new Polynomial(2);
		Polynomial q = new Polynomial(3);
		System.out.println("pol p:"+p);
		System.out.println("pol q"+q);
		System.out.println("Addition of polynomials: ");
		System.out.println(PolynomialOperation.add(p,q));
		System.out.println("Multiplication of polynomials: ");
		System.out.println(PolynomialOperation.multiplySequencial(p,q));
	}
}
