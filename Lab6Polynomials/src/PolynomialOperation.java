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
<<<<<<< HEAD
	public static Polynomial multiplicationSequentialForm(Polynomial p1, Polynomial p2) {
=======
	public static Polynomial multiplicationSequencialForm(Polynomial p1, Polynomial p2) {
>>>>>>> master
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
<<<<<<< HEAD
		int end;
=======
		int end ;
>>>>>>> master
		for (int i = 0; i < result.getLength(); i += step) {
			end = i + step;
			MultiplicationTask task = new MultiplicationTask(i, end, p1, p2, result);
			executor.execute(task);
		}

		executor.shutdown();
		executor.awaitTermination(50, TimeUnit.SECONDS);

		return result;
	}

<<<<<<< HEAD
	public static Polynomial multiplicationKaratsubaSequentialForm(Polynomial p1, Polynomial p2) {
		//not efficient if degree of polynomials <10
		if (p1.getDegree() < 10 || p2.getDegree() < 10) {
			return multiplicationSequentialForm(p1, p2);
		}

		int len = Math.max(p1.getDegree(), p2.getDegree()) / 2;
		Polynomial lowP1 = new Polynomial(p1.getCoefficients().subList(0, len));
		Polynomial highP1 = new Polynomial(p1.getCoefficients().subList(len, p1.getLength()));
		Polynomial lowP2 = new Polynomial(p2.getCoefficients().subList(0, len));
		Polynomial highP2 = new Polynomial(p2.getCoefficients().subList(len, p2.getLength()));

		Polynomial z0 = multiplicationKaratsubaSequentialForm(lowP1, lowP2);
		Polynomial z1 = multiplicationKaratsubaSequentialForm(add(lowP1, highP1), add(lowP2, highP2));
		Polynomial z2 = multiplicationKaratsubaSequentialForm(highP1, highP2);

		Polynomial q1 = shift(z2, 2 * len);
		Polynomial q2 = shift(subtract(subtract(z1, z2), z0), len);
		return add(add(q1, q2), z0);
	}

	/**
	 * Will increase the degree of the polynomial and will add a number of {offset} of 0s for the coefficients with
	 * the smallest degree.
	 *
	 * @param p      - Polynomial
	 * @param offset - Integer value
	 * @return - the shifted polynomial
	 */
	public static Polynomial shift(Polynomial p, int offset) {
		List<Integer> coefficients = new ArrayList<>();
		for (int i = 0; i < offset; i++) {
			coefficients.add(0);
		}
		for (int i = 0; i < p.getLength(); i++) {
			coefficients.add(p.getCoefficients().get(i));
		}
		return new Polynomial(coefficients);
=======
	public static Polynomial multiplicationKaratsubaSequencialForm(Polynomial p1, Polynomial p2) {

		return null;
>>>>>>> master
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

		addRemainingCoefficients(p1, p2, minDegree, maxDegree, coefficients);

		return new Polynomial(coefficients);
	}

	/**
	 * Complete the remaining part with the coefficients, from a certain polynomial (the one with the bigger degree)
	 */
	private static void addRemainingCoefficients(Polynomial p1, Polynomial p2, int minDegree, int maxDegree,
												 List<Integer> coefficients) {
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
	}

	/**
	 * Simple sequenctial subtraction operation over 2 polynomials.
	 *
	 * @param p1 - Polynomial
	 * @param p2 - Polynomial
	 * @return the resulted polynomials after the addition
	 */
	public static Polynomial subtract(Polynomial p1, Polynomial p2) {
		int minDegree = Math.min(p1.getDegree(), p2.getDegree());
		int maxDegree = Math.max(p1.getDegree(), p2.getDegree());
		List<Integer> coefficients = new ArrayList<>(maxDegree + 1);

		//Add the 2 polynomials
		for (int i = 0; i <= minDegree; i++) {
			coefficients.add(p1.getCoefficients().get(i) - p2.getCoefficients().get(i));
		}

		addRemainingCoefficients(p1, p2, minDegree, maxDegree, coefficients);

		//remove coefficients starting from biggest power if coefficient is 0
		int i = coefficients.size() - 1;
		while (coefficients.get(i) == 0) {
			coefficients.remove(i);
			i--;
		}
		return new Polynomial(coefficients);
	}
}
