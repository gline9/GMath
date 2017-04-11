package gmath.numbertheory;

import gmath.types.BigInteger;

public final class BigPrimes {
	private BigPrimes() {}

	/**
	 * tests a number to see if it is prime numbers less than 2 will return
	 * false
	 * 
	 * @param p
	 *            number to test for primality
	 * @return boolean value of if number is prime
	 */
	public static boolean isPrime(BigInteger p) {
		if (p.lessThan(new BigInteger(2)))
			return false;
		BigInteger sqrt = p.sqrt();
		for (BigInteger x = new BigInteger(2); x.lessThanOrEqual(sqrt); x = x.inc()) {
			if (p.mod(x).equals(p.ZERO()))
				return false;
		}
		return true;
	}

	private static final BigInteger[] prime10 = new BigInteger[] { new BigInteger(2), new BigInteger(3),
			new BigInteger(5), new BigInteger(7), new BigInteger(11), new BigInteger(13), new BigInteger(17),
			new BigInteger(19), new BigInteger(23), new BigInteger(29) };

	/**
	 * checks the first 10 primes for trivial primes and speed up the prime
	 * finding process. Only 1 out of every 300 composites should pass this
	 * test.
	 * 
	 * @param p
	 *            number to check
	 * @return whether the first 10 prime numbers divide it.
	 */
	private static boolean isBasicPrime(BigInteger p) {
		// loop through the primes and check the modulus.
		for (BigInteger prime : prime10) {

			// if any of them divide p then return false
			if (p.mod(prime).equals(p.ZERO()))
				return false;
		}

		// if they all pass return true
		return true;
	}

	/**
	 * uses the current number to find the first prime larger than it is.
	 * 
	 * @param c
	 *            number as the lower bound for the prime search
	 * @return first prime number larger than c.
	 */
	public static BigInteger getFirstPrimeLargerThan(BigInteger c) {
		// check if c is small enough, if so use the normal primes get first
		// prime larger than method.
		if (c.lessThan(new BigInteger(2147483648L))) {
			return new BigInteger(Primes.getFirstPrimeLargerThan(Long.valueOf(c.toString())));
		}

		// continue to loop until a prime number is found.
		boolean isntPrime = true;
		while (isntPrime) {

			// increment the currently tested number
			c = c.inc();

			// check if it fails the basic primality test, this is to eliminate
			// easily prooven prime numbers without having to spend time on the
			// costly fermat prime test.
			if (!isBasicPrime(c))
				continue;

			// check the current number for fermat primality
			if (!isFermatPrime(c, 3))
				continue;

			// if it passes the fermat test run a miller-rabin test
			if (!isMillerRabinPrime(c, 10))
				continue;

			// c has a chance of being prime of 1 : 8,388,608 which should be
			// good enough for most applications.
			isntPrime = false;
		}

		// return the number once it is found to be prime
		return c;
	}

	/**
	 * tests p for primality using fermat's little theorem. The fact that p is
	 * prime returned by this function is only a probability, additional tests
	 * need to be conducted to confirm it.
	 * 
	 * @param p
	 *            number to test.
	 * @param iterations
	 *            number of times to perform the test.
	 * @return
	 */
	public static boolean isFermatPrime(BigInteger p, int iterations) {
		// check for basic primality/non-primality.
		if (p.equals(p.ZERO()) || p.equals(new BigInteger(1)))
			return false;
		if (p.equals(new BigInteger(2)))
			return true;
		if (p.mod(new BigInteger(2)).equals(p.ZERO()))
			return false;

		// iterate the given number of times
		for (int i = 0; i < iterations; i++) {
			// generate a random big integer in the range 1 to p - 1.
			BigInteger r = BigInteger.randomBigInteger(p.dec()).inc();

			if (!BigModuloArithmetic.powerModulus(r, p.dec(), p).equals(new BigInteger(1))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * tests p for primality using the miller-rabin primality test. The fact
	 * that p is prime returned by this function is only a probability,
	 * additional tests need to be conducted to confirm it. This probability,
	 * however, is stronger than fermat with less iterations required.
	 * 
	 * @param p
	 *            number to test.
	 * @param iterations
	 *            number of times to perform the test.
	 * @return
	 */
	public static boolean isMillerRabinPrime(BigInteger p, int iterations) {
		// check for basic primality/non-primality.
		if (p.equals(p.ZERO()) || p.equals(new BigInteger(1)))
			return false;
		if (p.equals(new BigInteger(2)))
			return true;
		if (p.mod(new BigInteger(2)).equals(p.ZERO()))
			return false;
		// needed for the random number generation to work.
		if (p.equals(new BigInteger(3)))
			return true;

		// generate r and d in n - 1 = 2 ^ r * d so that d is odd
		BigInteger two = new BigInteger(2);
		int r = 1;
		// loop through until there are no more factors of 2 in p - 1.
		while (p.mod(two.pow(r)).equals(p.ZERO())) {
			// add 1 to r
			r++;
		}

		// get d by dividing p by 2 ^ r
		BigInteger d = p.truncate(two.pow(r));

		// go through the iteration the number of times provided
		for (int i = 0; i < iterations; i++) {
			// pick a random big integer in the range 2 to p - 2 inclusive
			BigInteger rand = BigInteger.randomBigInteger(p.subtract(new BigInteger(3))).add(two);

			// check if it passes fermat's little theorem.
			BigInteger x = BigModuloArithmetic.powerModulus(rand, d, p);
			if (x.equals(new BigInteger(1)) || x.equals(p.dec()))
				continue;

			// boolean value for whether the number was a witness or not
			boolean wasWitness = false;

			// square x and check for as many values as there are of r - 1
			for (int j = 0; j < r - 1; j++) {
				// square x modulo p
				x = BigModuloArithmetic.powerModulus(x, new BigInteger(2), p);

				// if it is 1 then p is composite
				if (x.equals(new BigInteger(1)))
					return false;

				// if it is p-1 then continue to the next iteration
				if (x.equals(p.dec())) {
					// x is a witness so set was witness to true
					wasWitness = true;

					// break out of the current loop
					break;
				}

			}

			// if rand wasn't a witness to the primality then it is composite
			// and return so
			if (!wasWitness)
				return false;
		}

		// if all the tests pass return true
		return true;
	}

}
