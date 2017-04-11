package gmath.types;

import java.util.Arrays;

public class Polynomial<R extends Ring<R>> extends Ring<Polynomial<R>> {

	private final R[] coefficients;

	private final Class<?> clazz;

	@SafeVarargs
	public Polynomial(R... coefficients) {
		super();
		if (coefficients.length == 0) {
			throw new IllegalArgumentException("You have to have elements in a polynomial!");
		} else {
			this.coefficients = coefficients;
			clazz = coefficients.getClass().getComponentType();
		}
	}

	public Polynomial(Polynomial<R> polynomial) {
		this.coefficients = polynomial.coefficients;
		this.clazz = polynomial.clazz;
	}

	@SafeVarargs
	private Polynomial(Class<?> clazz, R... coefficients) {
		this.coefficients = coefficients;
		this.clazz = clazz;
	}

	@Override
	public Polynomial<R> add(Polynomial<R> add) {
		// check for zero
		if (this.coefficients.equals(this.ZERO()))
			return add;
		if (add.coefficients.equals(this.ZERO()))
			return this;
		// add each term and then remove any leading zeros
		R[] results;

		// variables to store the shift in the polynomials since both of them
		// have leading terms in the front.
		int addShift;
		int thisShift;
		if (this.coefficients.length > add.coefficients.length) {
			// if this is bigger set the results to the bigger array and change
			// the add shift to the difference in size
			results = Arrays.copyOf(coefficients, coefficients.length);
			addShift = this.coefficients.length - add.coefficients.length;
			thisShift = 0;
		} else {
			// if add is bigger set the results to the bigger array and change
			// the this shift to the difference in size
			results = Arrays.copyOf(add.coefficients, add.coefficients.length);
			addShift = 0;
			thisShift = add.coefficients.length - this.coefficients.length;
		}

		// loop through and add all of the terms once we get to where both
		// polynomials exist
		for (int i = 0; i < results.length; i++) {
			// if we have entered both polynomials
			if (i >= addShift + thisShift) {
				// set the results to the addition of the add shifted add
				// component and the this shifted this component.
				results[i] = add.coefficients[i - addShift].add(this.coefficients[i - thisShift]);
			}
		}

		// remove zeros in the front of the polynomial
		while (results.length > 0 && results[0].equals(results[0].ZERO())) {
			results = Arrays.copyOfRange(results, 1, results.length);
		}

		// if no length return the zero polynomial
		if (results.length == 0) {
			return this.ZERO();
		}

		return new Polynomial<>(results);
	}

	@Override
	public Polynomial<R> multiply(Polynomial<R> mult) {
		// if either are zero return zero
		if (this.equals(this.ZERO()) || mult.equals(mult.ZERO()))
			return this.ZERO();

		// loop through each term in mult and multiply individually then add to
		// a results polynomial
		Polynomial<R> results = mult.ZERO();

		for (int i = 0; i < mult.coefficients.length; i++) {

			// if the current coefficient isn't zero multiply this by term and
			// add to the results
			if (!mult.coefficients[i].equals(mult.coefficients[i].ZERO())) {
				results = results
						.add(this.scale(mult.coefficients[i]).multiplyByPower(mult.coefficients.length - i - 1));
			}
		}

		// return the added results
		return results;
	}

	public Polynomial<R> multiplyByPower(int power) {
		// check for zero
		if (this.equals(this.ZERO()))
			return this.ZERO();

		// move the coefficients over by power
		R[] results = Arrays.copyOf(this.coefficients, power + coefficients.length);

		// get rid of all of the nulls
		for (int i = 1; i < results.length; i++) {
			if (results[i] == null) {
				results[i] = results[0].ZERO();
			}
		}

		return new Polynomial<R>(results);
	}

	@Override
	public Polynomial<R> negate() {
		// if zero return itself
		if (this.equals(this.ZERO()))
			return this;

		// negate each term.
		R[] results = Arrays.copyOf(coefficients, coefficients.length);
		for (int i = 0; i < results.length; i++) {
			results[i] = results[i].negate();
		}
		return new Polynomial<>(results);
	}

	public Polynomial<R> scale(R scalar) {
		// if scalar is zero or this is zero return zero
		if (scalar.equals(scalar.ZERO()) || this.equals(this.ZERO()))
			return this.ZERO();

		// multiply each term by the scalar
		R[] results = Arrays.copyOf(coefficients, coefficients.length);
		for (int i = 0; i < results.length; i++) {
			results[i] = results[i].multiply(scalar);
		}

		return new Polynomial<>(results);
	}

	@Override
	public Polynomial<R> ZERO() {
		return new Polynomial<>(clazz, coefficients[0].ZERO());
	}

	@Override
	public Polynomial<R> IDENTITY() {
		return new Polynomial<>(clazz, coefficients[0].IDENTITY());
	}

	public int degree() {
		// check for zero
		if (this.equals(this.ZERO()))
			return -1;

		return coefficients.length - 1;
	}

	public R getCoefficient(int power) {

		// check for out of bounds
		if (power >= coefficients.length)
			return null;

		return coefficients[coefficients.length - power - 1];
	}

	/**
	 * evaluates the current polynomial at the point x and returns the results
	 * of the evaluation.
	 * 
	 * @param x
	 *            number to plug in
	 * @return evaluation result.
	 */
	public R evaluate(R x) {

		// use streams to reduce the coefficients using x value as the
		// multiplier, this works since x ^ 2 + x + 1 = (1 * x + 1) * x + 1
		R results = coefficients[0].ZERO();

		// loop through all of the elements and perform the appropriate
		// operation
		for (R coefficient : coefficients) {
			results = results.multiply(x).add(coefficient);
		}

		// return the results
		return results;

	}

	@Override
	public boolean equals(Polynomial<R> compare) {
		
		// check for coefficient mismatch
		if (!Arrays.equals(this.coefficients, compare.coefficients))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(coefficients);
	}

	@Override
	public String toString() {
		return Arrays.toString(coefficients);
	}
}
