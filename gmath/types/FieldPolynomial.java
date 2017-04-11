package gmath.types;

import gcore.tuples.Pair;

public class FieldPolynomial<F extends Field<F>> extends Ring<FieldPolynomial<F>> {

	Polynomial<F> polynomial;

	@SafeVarargs
	public FieldPolynomial(F... coefficients) {
		super();
		polynomial = new Polynomial<>(coefficients);
	}

	public FieldPolynomial(Polynomial<F> poly) {
		super();
		polynomial = poly;
	}

	public FieldPolynomial<F> mod(FieldPolynomial<F> modulus) {
		FieldPolynomial<F> results = this;
		F modLeading = modulus.getCoefficient(modulus.degree());

		// keep going until the degree of results is less than the modulus's
		// degree
		while (results.degree() >= modulus.degree()) {
			F resultsLeading = results.getCoefficient(results.degree());
			F scale = resultsLeading.divide(modLeading);
			FieldPolynomial<F> subtract = modulus.scale(scale).multiplyByPower(results.degree() - modulus.degree());
			results = results.subtract(subtract);
		}
		return results;
	}

	public FieldPolynomial<F> truncate(FieldPolynomial<F> modulus) {
		FieldPolynomial<F> tally = this;
		FieldPolynomial<F> results = this.ZERO();
		F modLeading = modulus.getCoefficient(modulus.degree());

		// used to store the value of x
		FieldPolynomial<F> x = new FieldPolynomial<F>(modLeading.IDENTITY(), modLeading.ZERO());

		// keep going until the degree of the tally is less than the modulus's
		// degree
		while (tally.degree() >= modulus.degree()) {
			F tallyLeading = tally.getCoefficient(tally.degree());
			F scale = tallyLeading.divide(modLeading);
			
			FieldPolynomial<F> subtract = modulus.scale(scale).multiplyByPower(tally.degree() - modulus.degree());
			
			
			// add the scaled subtraction to the results polynomial then
			// subtract subtract from tally.
			results = results.add(x.scale(scale).multiplyByPower(tally.degree() - modulus.degree() - 1));
			tally = tally.subtract(subtract);
		}

		return results;
	}

	public FieldPolynomial<F> gcd(FieldPolynomial<F> n) {
		if (n.equals(n.ZERO()))
			return this.monicize();
		if (this.equals(this.ZERO()))
			return n.monicize();
		if (this.degree() <= n.degree())
			return n.mod(this).gcd(this);
		else
			return this.mod(n).gcd(n);
	}

	/**
	 * returns the modulo inverse r of x with respect to the modulus m, i.e. r *
	 * x = 1 mod m.
	 * 
	 * @param x
	 *            number to invert
	 * @param m
	 *            modulus of inversion
	 * @return the inverse of x modulo m
	 * 
	 * @throws IllegalArgumentException
	 *             if gcd(x, m) != identity
	 */
	public FieldPolynomial<F> moduloInverse(FieldPolynomial<F> m) {
		// inverse of 1 is 1.
		if (this.equals(m.IDENTITY()))
			return m.IDENTITY();

		// returns the value that is multiplied x to get the gcd(x, m)
		if (this.degree() > m.degree()) {
			return inverseHelper(this, m).getFirst();
		}
		return inverseHelper(m, this).getSecond();
	}

	/**
	 * inverse helper used by the moduloInverse method. Takes in two arguments
	 * and applies extended euclidean algorithm to find the gcd and then loops
	 * back to find the inverse.
	 * 
	 * @param x
	 *            larger of the two polynomials.
	 * @param m
	 *            smaller of the two polynomials.
	 * @return pair relating how the two polynomials are a polynomial
	 *         combination of their gcd.
	 * 
	 * @throws IllegalArgumentException
	 *             if gcd(x, m) != identity
	 */
	private static <F extends Field<F>> Pair<FieldPolynomial<F>, FieldPolynomial<F>> inverseHelper(FieldPolynomial<F> x,
			FieldPolynomial<F> m) {
		// check if gcd was never found to be 1 if so throw an illegal argument
		// exception
		if (m.equals(m.ZERO())) {
			throw new IllegalArgumentException("gcd of two numbers needs to be 1 for there to be an inverse.");
		}

		// gets how many m go into x
		FieldPolynomial<F> d = x.truncate(m);

		// save the modulus result
		FieldPolynomial<F> mod = x.mod(m);
		// check if at the end of the algorithm
		if (mod.degree() == 0) {

			return new Pair<>(d.IDENTITY(), d.negate().scale(mod.getCoefficient(mod.degree()).invert()));
		}
		// recursive step
		Pair<FieldPolynomial<F>, FieldPolynomial<F>> previous = inverseHelper(m, mod);

		// return based off extended euclidean algorithm.
		return new Pair<>(previous.getSecond(), previous.getFirst().subtract(d.multiply(previous.getSecond())));
	}

	@Override
	public FieldPolynomial<F> add(FieldPolynomial<F> add) {
		return new FieldPolynomial<F>(this.polynomial.add(add.polynomial));
	}

	@Override
	public FieldPolynomial<F> multiply(FieldPolynomial<F> mult) {
		return new FieldPolynomial<F>(this.polynomial.multiply(mult.polynomial));
	}

	public FieldPolynomial<F> multiplyByPower(int power) {
		return new FieldPolynomial<F>(this.polynomial.multiplyByPower(power));
	}

	public FieldPolynomial<F> scale(F scalar) {
		return new FieldPolynomial<F>(this.polynomial.scale(scalar));
	}

	@Override
	public FieldPolynomial<F> negate() {
		return new FieldPolynomial<F>(this.polynomial.negate());
	}

	@Override
	public FieldPolynomial<F> ZERO() {
		return new FieldPolynomial<F>(this.polynomial.ZERO());
	}

	@Override
	public FieldPolynomial<F> IDENTITY() {
		return new FieldPolynomial<F>(this.polynomial.IDENTITY());
	}

	public FieldPolynomial<F> monicize() {
		return scale(getCoefficient(degree()).invert());
	}

	public int degree() {
		return this.polynomial.degree();
	}

	public F getCoefficient(int power) {
		return this.polynomial.getCoefficient(power);
	}

	public F evaluate(F x) {
		return this.polynomial.evaluate(x);
	}

	@Override
	public boolean equals(FieldPolynomial<F> compare) {
		return this.polynomial.equals(compare.polynomial);
	}

	@Override
	public int hashCode() {
		return 31 * this.polynomial.hashCode();
	}

	@Override
	public String toString() {
		return this.polynomial.toString();
	}

}
