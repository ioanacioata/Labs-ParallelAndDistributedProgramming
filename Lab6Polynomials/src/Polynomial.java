import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Contains the list of the coefficients of the polynomial.
 * The first coefficients in the list corresponds to the biggest power, and the last corresponds to x^0.
 */
public class Polynomial {
	private static final int BOUND = 10;
	private List<Integer> coefficients;
	private Random randomGenerator = new Random();

	public Polynomial(List<Integer> coefficients) {
		this.coefficients = coefficients;
	}

	public Polynomial(int biggestCoefficientRank) {
		coefficients = new ArrayList<>(biggestCoefficientRank + 1);
		//the coefficient of the biggest power has to be different than 0
		int firstCoefficient = randomGenerator.nextInt(BOUND);
		if (firstCoefficient == 0) {
			firstCoefficient++;
		}
		coefficients.add(firstCoefficient);
		//Generate the rest of the coefficients
		for (int i = 1; i <= biggestCoefficientRank; i++) {
			coefficients.add(randomGenerator.nextInt(10));
		}
	}

	public List<Integer> getCoefficients() {
		return coefficients;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		int power = coefficients.size() - 1;
		for (Integer c : coefficients) {
			str.append(" ").append(c).append("x^").append(power).append(" +");
			power--;
		}
		str.deleteCharAt(str.length() - 1); //delete last +
		return "Polynomial: " + str.toString();
	}
}
