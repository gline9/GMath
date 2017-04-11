package gmath.numbertheory;

import gcore.tuples.Pair;
import gmath.types.BigInteger;

/**
 * util class for basic modulo arithmetic operations using big integers.
 * 
 * @author Gavin
 *
 */
public final class BigModuloArithmetic {
	// make class non-instantiable
	private BigModuloArithmetic() {}

	/**
	 * takes two arguments and returns their greatest common divisor using the
	 * euclidean algorithm.
	 * 
	 * @param x
	 *            first number
	 * @param y
	 *            second number
	 * @return returns the gcd of the two numbers.
	 */
	public static BigInteger gcd(BigInteger x, BigInteger y) {
		// make sure the given arguments are positive
		x = x.abs();
		y = y.abs();

		// sends the arguments to gcd helper after they have been turned into
		// positive numbers. The helper requires that the first argument be the
		// bigger and the second be the smaller
		return gcdHelper(x.max(y), x.min(y));
	}

	/**
	 * helper function for gcd, assumes the arguments are positive so there is
	 * no need to check every iteration.
	 * 
	 * @param x
	 *            first number
	 * @param y
	 *            second number
	 * @return returns the gcd of the two numbers.
	 */
	private static BigInteger gcdHelper(BigInteger x, BigInteger y) {
		// if y is zero the gcd will be x
		if (y.equals(y.ZERO()))
			return x;

		// use recursion to decrease size of x and y, x will be what y was and y
		// will be x reduced modulo y.
		return gcdHelper(y, x.mod(y));
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
	 *             if gcd(x, m) != 1
	 */
	public static BigInteger moduloInverse(BigInteger x, BigInteger m) {
		// returns the value that is multiplied x to get the gcd(x, m)
		if (x.greaterThan(m)) {
			return inverseHelper(x, m).getFirst();
		}
		return inverseHelper(m, x).getSecond();
	}

	/**
	 * inverse helper used by the moduloInverse method. Takes in two arguments
	 * and applies extended euclidean algorithm to find the gcd and then loops
	 * back to find the inverse.
	 * 
	 * @param x
	 *            larger of the two numbers.
	 * @param m
	 *            smaller of the two numbers.
	 * @return pair relating how the two numbers are a linear combination of
	 *         their gcd.
	 * 
	 * @throws IllegalArgumentException
	 *             if gcd(x, m) != 1
	 */
	private static Pair<BigInteger, BigInteger> inverseHelper(BigInteger x, BigInteger m) {
		// check if gcd was never found to be 1 if so throw an illegal argument
		// exception
		if (m.equals(m.ZERO())) {
			throw new IllegalArgumentException("gcd of two numbers needs to be 1 for there to be an inverse.");
		}

		// gets how many m go into x
		BigInteger d = x.truncate(m);

		// check if at the end of the algorithm
		if (x.mod(m).equals(new BigInteger(1))) {

			// return a new pair of (-d * b + 1, -d * a)
			return new Pair<>(new BigInteger(1), d.negate());
		}
		// recursive step
		Pair<BigInteger, BigInteger> previous = inverseHelper(m, x.mod(m));

		// return based off extended euclidean algorithm.
		return new Pair<>(previous.getSecond(), previous.getFirst().subtract(d.multiply(previous.getSecond())));
	}

	/**
	 * raises x to the power p modulo m
	 * 
	 * @param x
	 *            number to raise to the power
	 * @param p
	 *            power to raise it to
	 * @param m
	 *            modulus to take
	 * @return x ^ p mod m
	 */
	public static BigInteger powerModulus(BigInteger x, BigInteger p, BigInteger m) {
		// copy the original
		BigInteger original = x;
		BigInteger num = original;

		// convert the power to binary to use a binary power method.
		String binaryPower = p.toBinaryString();

		// get rid of the first character as it will always be a 1
		binaryPower = binaryPower.substring(1, binaryPower.length());

		// loop through the digits if they are a 1 times by the number and if it
		// is a 0 just square.
		for (char c : binaryPower.toCharArray()) {
			num = num.square();
			switch (c) {
			case '1':
				num = num.multiply(original);
			}
			num = num.mod(m);
		}

		// return the result.
		return num;
	}

}