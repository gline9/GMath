package gmath.numbertheory;

import java.util.Arrays;

import gcore.util.ArrayUtils;
import gcore.util.WrapperUtils;

public final class Primes {
	private Primes() {}

	/**
	 * tests a number to see if it is prime numbers less than 2 will return
	 * false
	 * 
	 * @param p
	 *            number to test for primality
	 * @return boolean value of if number is prime
	 */
	public static boolean isPrime(long p) {
		if (p < 2)
			return false;
		if (p == 2)
			return true;
		long sqrt = (long)(Math.sqrt(p) + 1);
		for (int x = 2; x <= sqrt; x++) {
			if (p % x == 0)
				return false;
		}
		return true;
	}

	/**
	 * uses the current number to find the first prime larger than it is.
	 * 
	 * @param c
	 *            number as the lower bound for the prime search
	 * @return first prime number larger than c.
	 */
	public static long getFirstPrimeLargerThan(long c) {
		// continue to loop until a prime number is found.
		boolean isntPrime = true;
		while (isntPrime) {

			// increment the currently tested number
			c++;
			
			// check the current number for primality
			if (isPrime(c))
				isntPrime = false;
		}

		// return the number once it is found to be prime
		return c;
	}

	/**
	 * sieves through all non-negative integers smaller than num and returns all
	 * prime values
	 * 
	 * @param num
	 *            number for the sieve to go to
	 * @return boolean array where the value is true if the index is prime,
	 *         false otherwise
	 */
	private static boolean[] primeSieveHelper(int num) {

		// if num is small return defaults
		if (num == 0) {
			return new boolean[0];
		}
		if (num == 1) {
			return new boolean[] { false };
		}

		// initialize results array to all true except 0 and 1
		boolean[] results = new boolean[num + 1];
		Arrays.fill(results, true);
		results[0] = false;
		results[1] = false;

		// loop through all possible divisors of numbers in the sieve
		int sqrt = (int)(Math.sqrt(num) + 1);
		for (int x = 2; x <= sqrt; x++) {

			// check if x is prime
			if (results[x]) {

				// loop through remainder of array with multiple x
				// and set all of the values to false that are divided by x
				for (int y = 2 * x; y <= num; y += x) {
					results[y] = false;
				}
			}
		}
		// return the boolean array
		return results;
	}

	/**
	 * sieves through all non-negative integers smaller than num and returns all
	 * prime values
	 * 
	 * @param num
	 *            number for the sieve to go to
	 * @return array of the integers that were found to be prime
	 */
	public static int[] basicPrimeSieve(int num) {
		// get the results of the helper function in a boolean array
		boolean[] sieveResults = primeSieveHelper(num);

		// gets the number of primes that were in the sieve to set the size of
		// the resulting array
		int numOfPrimes = ArrayUtils.countOccurences(WrapperUtils.toWrapper(sieveResults), true);

		// initialize the prime result array, and initialize the current index
		// to 0
		int currentIndex = 0;
		int[] primes = new int[numOfPrimes];

		// loop through sieveResults and put all of the primes into the array
		for (int i = 0; i <= num; i++) {
			if (sieveResults[i]) {
				primes[currentIndex] = i;
				currentIndex++;
			}
		}

		// returns the primes in the primes array
		return primes;
	}

}
