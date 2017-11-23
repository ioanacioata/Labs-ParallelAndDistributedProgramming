import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PolynomialOperation {

	/**
	 * Simple sequenctial multiplication operation over 2 polynomials.
	 * Time complexity: O(n^2)
	 *
	 * @param p1 - Polynomial
	 * @param p2 - Polynomial
	 * @return the resulted polynomials after the multiplication
	 */
	public static Polynomial multiplicationSequencialForm(Polynomial p1, Polynomial p2) {
		int sizeOfResultCoefficientList = p1.getDegree() + p2.getDegree() + 1;
		List<Integer> coefficients = IntStream.of(new int[sizeOfResultCoefficientList]).boxed().collect(Collectors
				.toList());//initialize coefficient list with 0

		for (int i = 0; i < p1.getCoefficients().size(); i++) {
			for (int j = 0; j < p2.getCoefficients().size(); j++) {
				int index = i + j;
				int value = p1.getCoefficients().get(i) * p2.getCoefficients().get(j);
				coefficients.set(index, coefficients.get(index) + value);
			}
		}
		return new Polynomial(coefficients);
	}

	/**
	 * Simple multiplication operation over 2 polynomials, using threads
	 * Time complexity: O(n^2)
	 *
	 * @param p1 - Polynomial
	 * @param p2 - Polynomial
	 * @return the resulted polynomials after the multiplication
	 */
	public static Polynomial multiplicationParallelizedForm(Polynomial p1, Polynomial p2, int nrOfThreads) throws
			InterruptedException {
		//initialize result polynomial with coefficients = 0
		int sizeOfResultCoefficientList = p1.getDegree() + p2.getDegree() + 1;
		List<Integer> coefficients = IntStream.of(new int[sizeOfResultCoefficientList]).boxed().collect(Collectors
				.toList());
		Polynomial result = new Polynomial(coefficients);

		//calculate the coefficients

		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(nrOfThreads);
		int step = result.getLength() / nrOfThreads;
		if (step == 0) {
			step = 1;
		}
		//System.out.println("STEP: " + step);
		int end ;
		for (int i = 0; i < result.getLength(); i += step) {
			end = i + step;
			MultiplicationTask task = new MultiplicationTask(i, end, p1, p2, result);
			executor.execute(task);
		}

		executor.shutdown();
		executor.awaitTermination(50, TimeUnit.SECONDS);

		return result;
	}

	public static Polynomial multiplicationKaratsubaSequencialForm(Polynomial p1, Polynomial p2) {

		return null;
	}

	public static Polynomial multiplicationKaratsubaParallelizedForm(Polynomial p1, Polynomial p2) {

		return null;
	}

	/**
	 * Simple sequenctial addition operation over 2 polynomials.
	 *
	 * @param p1 - Polynomial
	 * @param p2 - Polynomial
	 * @return the resulted polynomials after the addition
	 */
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
