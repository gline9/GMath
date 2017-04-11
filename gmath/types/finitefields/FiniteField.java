package gmath.types.finitefields;

import java.util.Arrays;
import java.util.Random;

import gmath.numbertheory.Primes;
import gmath.types.FieldPolynomial;
import gmath.types.Integer;

/**
 * this class is like the {@link gmath.types.finitefields.PrimeField PrimeField}
 * class except it allows for finite fields of any possible size. This is the
 * same as all fields of size p^n where p is a prime number.
 * 
 * @author Gavin
 *
 */
public class FiniteField {

	// size of the the primitive-field
	private final int prime;

	// dimension of the field, i.e. size = prime ^ dimension
	private final int dimension;

	// prime subfield for the finite field
	private final PrimeField primeField;

	// characteristic polynomial for the field
	private final FieldPolynomial<PrimeFieldElement> characteristic;

	/**
	 * creates a finite field with the given size, note that the size must be a
	 * prime power for a field to exist so if this isn't the case an
	 * {@link java.lang.IllegalArgumentException IllegalArgumentException} will
	 * be thrown. The characteristic generated for the field will be random.
	 * 
	 * @param size
	 *            size to make the field, must be of form p ^ n with p prime.
	 */
	public FiniteField(int size) {
		// check if the field is a simple prime field
		if (Primes.isPrime(size)) {
			prime = size;
			dimension = 1;
		} else {
			// otherwise find the prime power
			boolean rootFound = false;
			int power = 2;
			int prime = 0;

			// continue until a root was found or until the power is greater
			// than what the root of 2 would have the power be.
			while (!rootFound && power <= (Math.log(size) / Math.log(2))) {
				// set the prime to the power'th root of the size
				prime = (int) Math.round(Math.pow(size, 1 / (double) power));

				// if it isn't prime continue to the next power
				if (!Primes.isPrime(prime)) {
					power++;
					continue;
				}

				// if it is check that it raised to the power is exactly the
				// size we have. Integer is being used as the built in math
				// double has accuracy that could be over looked.
				if (new Integer(prime).pow(power).equals(new Integer(size))) {
					rootFound = true;
				} else {

					// if not go to the next power
					power++;
				}
			}

			// if there wasn't a power return an illegal argument exception
			if (!rootFound)
				throw new IllegalArgumentException("Size of a finite field must be the power of a prime!");

			// set the values of the field to the prime and power
			this.prime = prime;
			this.dimension = power;
		}

		// set the prime subfield to the correct field
		primeField = new PrimeField(prime);

		// set the characteristic for the field, if the dimension of the field
		// is 1 there is no characteristic so just return x
		if (dimension == 1)
			characteristic = new FieldPolynomial<PrimeFieldElement>(primeField.element(1), primeField.element(0));
		else
			characteristic = generateIrreduciblePolynomial(dimension, primeField);

	}

	/**
	 * creates a finite field with the primitive field with prime prime, and
	 * dimension the power given. Note that if the prime isn't prime
	 * {@link java.lang.IllegalArgumentException IllegalArgumentException} will
	 * be thrown. The characteristic generated for the field will be random.
	 * 
	 * @param prime
	 *            the primitive fields prime value for the field
	 * @param power
	 *            the dimension of the field.
	 */
	public FiniteField(int prime, int power) {
		// check that the prime is indeed prime
		if (!Primes.isPrime(prime))
			throw new IllegalArgumentException("to create a finite field the base must be a prime number!");

		// make sure the dimension is a valid dimension
		if (power <= 0)
			throw new IllegalArgumentException("The dimension of a finite field must be biggerthan or equal to 1!");

		// set the prime field, prime and dimension of the field
		this.prime = prime;
		this.dimension = power;
		this.primeField = new PrimeField(prime);

		// set the characteristic for the field, if the dimension of the field
		// is 1 there is no characteristic so just set it to x.
		if (dimension == 1)
			characteristic = new FieldPolynomial<PrimeFieldElement>(primeField.element(1), primeField.element(0));
		else
			characteristic = generateIrreduciblePolynomial(dimension, primeField);
	}

	/**
	 * creates a finite field with the primitive field with prime prime, and
	 * dimension the power given. Note that if the prime isn't prime
	 * {@link java.lang.IllegalArgumentException IllegalArgumentException} will
	 * be thrown. The characteristic of the field will be the one given, but an
	 * exception will also be thrown if the characteristic given is reducible in
	 * the primitive field.
	 * 
	 * @param prime
	 *            the primitives field prime value for the field
	 * @param power
	 *            the dimension of the field
	 * @param characteristic
	 *            the characteristic polynomial for the field.
	 */
	public FiniteField(int prime, int power, FieldPolynomial<PrimeFieldElement> characteristic) {
		// check that the prime is indeed prime
		if (!Primes.isPrime(prime))
			throw new IllegalArgumentException("to create a finite field the base must be a prime number!");

		// make sure the dimension is a valid dimension
		if (power <= 0)
			throw new IllegalArgumentException("The dimension of a finite field must be biggerthan or equal to 1!");

		// make sure the characteristic polynomial is of correct dimension
		if (characteristic.degree() != power)
			throw new IllegalArgumentException(
					"The degree of the characteristic polynomial must be the same as the dimension of the field!");

		// set the prime field, prime and dimension of the field
		this.prime = prime;
		this.dimension = power;
		this.primeField = new PrimeField(prime);

		// make sure the characteristic isn't reducible before setting to the
		// characteristic of the field.
		if (!isIrreduciblePolynomial(characteristic, primeField))
			throw new IllegalArgumentException("characteristic of a field must be irreducible in the primitive field!");

		// if everything passed set the characteristic as the characteristic of
		// the field
		this.characteristic = characteristic;
	}

	/**
	 * helper function that will generate a characteristic polynomial for the
	 * given size of the field.
	 * 
	 * @return irreducible polynomial in the subfield of degree dimension of the
	 *         field
	 */
	public static FieldPolynomial<PrimeFieldElement> generateIrreduciblePolynomial(int dimension, PrimeField primeField) {

		// keep generating random polynomials and testing for irreducibility
		// until one is found. On average the degree of the polynomial is the
		// amount that need to be tested to find one.

		boolean irreducible = false;
		FieldPolynomial<PrimeFieldElement> results = null;
		while (!irreducible) {
			results = generateRandomPolynomial(dimension, primeField);
			irreducible = isIrreduciblePolynomial(results, primeField);
		}

		// once one is found return it
		return results;
	}

	/**
	 * helper function that will determine if the given polynomial is
	 * irreducible in the current prime sub field or not.
	 * 
	 * @param poly
	 *            polynomial to check
	 * @return if poly is irreducible
	 */
	private static boolean isIrreduciblePolynomial(FieldPolynomial<PrimeFieldElement> poly, PrimeField primeField) {
		// get the polynomial to subtract from the original polynomial i.e. x
		FieldPolynomial<PrimeFieldElement> x = new FieldPolynomial<>(primeField.element(1), primeField.element(0));

		// get the original power polynomial, i.e. x ^ p
		FieldPolynomial<PrimeFieldElement> xp = x.pow(primeField.size());

		// loop through all of the possible k values for if the polynomial
		for (int i = 0; i <= poly.degree() / 2; i++) {
			// if the gcd of poly and x^p^i - x isn't 1 the polynomial is
			// reducible this is the same as its degree being 0.
			FieldPolynomial<PrimeFieldElement> xpix = xp.pow(i).subtract(x);
			if (xpix.gcd(poly).degree() != 0) {
				return false;
			}
		}

		// if no factors found return true.
		return true;
	}

	/**
	 * helper function that will generate a random polynomial with the given
	 * degree that is monic.
	 * 
	 * @param degree
	 *            degree for generated polynomial
	 * @return random polynomial
	 */
	private static FieldPolynomial<PrimeFieldElement> generateRandomPolynomial(int degree, PrimeField primeField) {
		// initialize a random number generator
		Random r = new Random(System.currentTimeMillis());

		// initialize an array for the values of the polynomial
		PrimeFieldElement[] elements = new PrimeFieldElement[degree + 1];

		// set the first term to one as it is monic
		elements[0] = primeField.element(1);

		// loop through each of the remaining elements and put a random integer
		// in it. It doesn't matter how big it is because the prime field will
		// modularize it anyways.
		for (int i = 1; i < elements.length; i++) {
			elements[i] = primeField.element(r.nextInt());
		}

		// create a polynomial with the elements and return it.

		return new FieldPolynomial<>(elements);
	}

	/**
	 * generates an element from an array of values where the array is the same
	 * as the dimension of the field.
	 * 
	 * @param value
	 *            values for the element of the field.
	 * @return a finite field element with those values.
	 */
	public FiniteFieldElement element(int... value) {
		// check the dimension of the array if it isn't the correct dimension
		// throw IllegalArgumentException
		if (value == null || value.length != dimension)
			throw new IllegalArgumentException(
					"Value array for finite field must have the same size as the dimension of the Field!");

		// generate an array of the values in the prime sub field
		PrimeFieldElement[] fieldValues = new PrimeFieldElement[value.length];
		for (int i = 0; i < value.length; i++) {
			fieldValues[i] = primeField.element(value[i]);
		}

		// check for leading zeros and remove until there aren't any more or the
		// size is zero.
		while (fieldValues.length > 1 && fieldValues[0].equals(primeField.element(0))) {
			fieldValues = Arrays.copyOfRange(fieldValues, 1, fieldValues.length);
		}

		// create a polynomial to return as the value of the element
		FieldPolynomial<PrimeFieldElement> polynomial = new FieldPolynomial<>(fieldValues);

		// return a new finite field element with the appropriate values.
		return new FiniteFieldElement(this, polynomial);

	}

	/**
	 * method for retrieving the characteristic polynomial the field is using
	 * 
	 * @return the characteristic polynomial for the finite field.
	 */
	public FieldPolynomial<PrimeFieldElement> getCharacteristicPolynomial() {
		return characteristic;
	}

}
