package gmath.numbertheory;

import java.math.BigInteger;

import gcore.tuples.Pair;

/**
 * util class for basic modulo arithmetic operations.
 * 
 * @author Gavin
 *
 */
public final class ModuloArithmetic {
	// make class non-instantiable
	private ModuloArithmetic() {}

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
	public static long gcd(long x, long y) {
		// make sure the given arguments are positive
		x = Math.abs(x);
		y = Math.abs(y);

		// sends the arguments to gcd helper after they have been turned into
		// positive numbers. The helper requires that the first argument be the
		// bigger and the second be the smaller
		return gcdHelper(Math.max(x, y), Math.min(x, y));
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
	private static long gcdHelper(long x, long y) {
		// if y is zero the gcd will be x
		if (y == 0)
			return x;

		// use recursion to decrease size of x and y, x will be what y was and y
		// will be x reduced modulo y.
		return gcdHelper(y, x % y);
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
	public static long moduloInverse(long x, long m) {
		// inverse of 1 is 1.
		if (x == 1)
			return 1;
		
		// returns the value that is multiplied x to get the gcd(x, m)
		if (x > m) {
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
	private static Pair<Long, Long> inverseHelper(long x, long m) {
		// check if gcd was never found to be 1 if so throw an illegal argument
		// exception
		if (m == 0) {
			throw new IllegalArgumentException("gcd of two numbers needs to be 1 for there to be an inverse.");
		}

		// gets how many m go into x
		long d = x / m;

		// check if at the end of the algorithm
		if (x % m == 1) {

			// return a new pair of (-d * b + 1, -d * a)
			return new Pair<>(1L, -d);
		}
		// recursive step
		Pair<Long, Long> previous = inverseHelper(m, x % m);

		// return based off extended euclidean algorithm.
		return new Pair<>(previous.getSecond(), previous.getFirst() - d * previous.getSecond());
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
	public static long powerModulus(long x, long p, long m) {
		// convert x to a big integer to prevent overflow and copy the original.
		BigInteger original = BigInteger.valueOf(x);
		BigInteger num = original;

		// convert the modulus to big integer to use with the number.
		BigInteger modulus = BigInteger.valueOf(m);

		// convert the power to binary to use a binary power method.
		String binaryPower = Long.toBinaryString(p);

		// get rid of the first character as it isn't a 0 or 1 and is
		// irrelevant.
		binaryPower = binaryPower.substring(1, binaryPower.length());

		// loop through the digits if they are a 1 times by the number and if it
		// is a 0 just square.
		for (char c : binaryPower.toCharArray()) {
			num = num.pow(2);
			switch (c) {
			case '1':
				num = num.multiply(original);
			}
			num = num.mod(modulus);
		}

		// return the long value of the big integer, since the modulus is a long
		// the result should be strictly less than the modulus and therefore
		// also a long.
		return num.longValue();
	}

}
