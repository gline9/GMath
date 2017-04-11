package gmath.types.finitefields;

import gmath.numbertheory.Primes;

/**
 * prime field is a class that acts as a space for the field with the given
 * modulus. In order to create a prime field element you must first create a
 * prime field and then create an element from the field. This is to reduce
 * computation on the individual element inside the field and to share the
 * computation between the elements with long computations.
 * 
 * @author Gavin
 *
 */
public class PrimeField {

	// modulus for the prime field, will be a prime number.
	private final int modulus;

	/**
	 * creates a new finite field with the modulus as the size of the field. The
	 * modulus has to be prime for this class to work, if it isn't an
	 * {@link java.lang.IllegalArgumentException IllegalArgumentException} is
	 * thrown.
	 * 
	 * @param modulus
	 *            size of field element is in.
	 */
	public PrimeField(int modulus) {
		// if the modulus is less than 2 it can't be prime
		if (modulus < 2)
			throw new IllegalArgumentException("modulus needs to be larger than or equal to 2!");

		// if it isn't prime throw an illegal argument exception
		if (!Primes.isPrime(modulus))
			throw new IllegalArgumentException("modulus needs to be a prime value but " + modulus + " was received!");

		// set the modulus for the prime field
		this.modulus = modulus;
	}

	/**
	 * creates a field element with the value as the value of the element
	 * reduced modulo the field size.
	 * 
	 * @param value
	 *            valule for the element
	 * @return a prime field element with the given value in the current field.
	 */
	public PrimeFieldElement element(int value) {
		return new PrimeFieldElement(value, modulus);
	}
	
	/**
	 * returns the dimension of the field
	 * @return dimension of the field
	 */
	public int size() {
		return modulus;
	}
}
