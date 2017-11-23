import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PolynomialOperation {

	public static Polynomial multiplySequencial(Polynomial p1, Polynomial p2) {
		int sizeOfResultCoefficientList = p1.getDegree() + p2.getDegree() + 1;
		List<Integer> coefficients = IntStream.of(new int[sizeOfResultCoefficientList]).boxed().collect(Collectors
				.toList());

		for (int i = 0; i < p1.getCoefficients().size(); i++) {
			for (int j = 0; j < p2.getCoefficients().size(); j++) {
				int index = i + j;
				int value = p1.getCoefficients().get(i) * p2.getCoefficients().get(j);
				coefficients.set(index, coefficients.get(index) + value);
			}
		}
		return new Polynomial(coefficients);
	}

	public static Polynomial add(Polynomial p1, Polynomial p2) {
		int minDegree = Math.min(p1.getDegree(), p2.getDegree());
		int maxDegree = Math.max(p1.getDegree(), p2.getDegree());
		List<Integer> coefficients = new ArrayList<>(maxDegree + 1);

		//Add the 2 polynomials
		for (int i = 0; i <= minDegree; i++) {
			coefficients.add(p1.getCoefficients().get(i) + p2.getCoefficients().get(i));
		}

		//Complete the remaining part with the coefficients, from a certain polynomial (the one with the bigger degree)
		if (minDegree != maxDegree) {
			if (maxDegree == p1.getDegree()) {
				for (int i = minDegree + 1; i <= maxDegree; i++) {
					coefficients.add(p1.getCoefficients().get(i));
				}
			} else {
				for (int i = minDegree + 1; i <= maxDegree; i++) {
					coefficients.add(p2.getCoefficients().get(i));
				}
			}
		}

		return new Polynomial(coefficients);
	}
}
