package gmath.types;

import java.util.Random;

import gcore.bits.BitSequence;

/**
 * BigInteger wrapper class into the ring and factorable classes given by gmath.
 * 
 * @author Gavin
 *
 */
public class BigInteger extends OrderedRing<BigInteger> {

	// value stored in a big integer value.
	private final java.math.BigInteger value;

	/**
	 * constructor takes a long as its value.
	 * 
	 * @param value
	 *            value for the big integer.
	 */
	public BigInteger(long value) {
		super(BigInteger.class);
		this.value = java.math.BigInteger.valueOf(value);
	}

	/**
	 * constructor takes a String as its value.
	 * 
	 * @param value
	 *            value for the big integer.
	 */
	public BigInteger(String value) {
		super(BigInteger.class);
		this.value = new java.math.BigInteger(value);
	}

	/**
	 * constructor takes a java big integer as its value.
	 * 
	 * @param value
	 *            value for the big integer.
	 */
	public BigInteger(java.math.BigInteger value) {
		super(BigInteger.class);
		this.value = value;
	}

	/**
	 * constructor takes a Gmath big integer as its value.
	 * 
	 * @param value
	 *            value for the big integer.
	 */
	public BigInteger(BigInteger value) {
		super(BigInteger.class);
		this.value = value.value;
	}

	/**
	 * constructor takes a byte array as its value using twos complement.
	 * 
	 * @param value
	 *            value for the big integer.
	 */
	public BigInteger(byte[] value) {
		super(BigInteger.class);
		this.value = new java.math.BigInteger(value);
	}

	@Override
	public boolean greaterThan(BigInteger compare) {
		return this.value.compareTo(compare.value) == 1;
	}

	@Override
	public boolean lessThan(BigInteger compare) {
		return this.value.compareTo(compare.value) == -1;
	}

	public BigInteger truncate(BigInteger divide) {
		return new BigInteger(this.value.divide(divide.value));
	}

	public BigInteger mod(BigInteger divide) {
		return new BigInteger(this.value.mod(divide.value));
	}

	public BigInteger inc() {
		return new BigInteger(this.value.add(java.math.BigInteger.valueOf(1)));
	}

	public BigInteger dec() {
		return new BigInteger(this.value.add(java.math.BigInteger.valueOf(-1)));
	}

	@Override
	public BigInteger add(BigInteger add) {
		return new BigInteger(this.value.add(add.value));
	}

	@Override
	public BigInteger multiply(BigInteger mult) {
		return new BigInteger(this.value.multiply(mult.value));
	}

	public final BigInteger pow(BigInteger p) {
		// make two copies of the current number, one for storing the results
		// and the other for multiplying each time.
		BigInteger copy = this;
		BigInteger original = copy;

		// get the binary representation of the power to know when to multiply
		// and when to square.
		String binaryPower = p.toBinaryString();

		binaryPower = binaryPower.substring(1, binaryPower.length());

		// each time square the previous number, if there is a 1 also
		// re-multiply by the original
		for (char c : binaryPower.toCharArray()) {
			copy = copy.square();
			switch (c) {
			case '1':
				copy = copy.multiply(original);
			}
		}

		// return the results of the computation.
		return copy;
	}

	/**
	 * returns the square root of the current big integer up to the integer
	 * value.
	 * 
	 * @author Eugene Fidelin
	 * 
	 * @return sqrt of current big integer
	 */
	public BigInteger sqrt() {
		BigInteger div = new BigInteger(java.math.BigInteger.ZERO.setBit(this.bitLength()/2));
	    BigInteger div2 = div;
	    // Loop until we hit the same value twice in a row, or wind
	    // up alternating.
	    for(;;) {
	        BigInteger y = new BigInteger(div.add(this.truncate(div)).value.shiftRight(1));
	        if (y.equals(div) || y.equals(div2))
	            return y;
	        div2 = div;
	        div = y;
	    }
	}

	private int bitLength() {
		return this.value.bitLength();
	}

	@Override
	public BigInteger negate() {
		return new BigInteger(this.value.negate());
	}

	public BigInteger abs() {
		return new BigInteger(this.value.abs());
	}

	public BigInteger max(BigInteger compare) {
		return new BigInteger(this.value.max(compare.value));
	}

	public BigInteger min(BigInteger compare) {
		return new BigInteger(this.value.min(compare.value));
	}

	@Override
	public BigInteger ZERO() {
		return new BigInteger(java.math.BigInteger.valueOf(0));
	}

	@Override
	public BigInteger IDENTITY() {
		return new BigInteger(java.math.BigInteger.valueOf(1));
	}

	public byte[] toByteArray() {
		return this.value.toByteArray();
	}

	@Override
	public boolean equals(BigInteger compare) {
		return this.value.equals(compare.value);
	}

	/**
	 * generates a random big integer in the range [0, max).
	 * 
	 * @param max
	 *            maximum size of big integer exclusive.
	 * @return
	 */
	public static BigInteger randomBigInteger(BigInteger max) {
		// initialize the random number generator.
		Random r = new Random();

		// get the resulting value i
		java.math.BigInteger i;
		do {
			// generate a random big integer with the specified bit length.
			i = new java.math.BigInteger(max.bitLength(), r);

			// make sure the generated integer is smaller than the max given.
		} while (i.compareTo(max.value) >= 0);

		// return the resulting integer
		return new BigInteger(i);
	}

	@Override
	public int hashCode() {
		return this.value.hashCode();
	}

	@Override
	public String toString() {
		return this.value.toString();
	}

	public String toBinaryString() {
		return this.value.toString(2);
	}

	public byte[] getBytes() {
		return value.toByteArray();
	}

	public BitSequence getBits() {
		return new BitSequence(value.toByteArray());
	}

	/**
	 * supports turning the big integer into a string up to the radix of 64
	 * 
	 * @param radix
	 *            base for the resulting string
	 * @return string representation of the big integer
	 * @since Sep 26, 2016
	 */
	public String toString(int radix) {
		if (radix > 64 || radix < 2)
			throw new RuntimeException(
					String.format("Radix %d is out of range for a radix, must be between 2 and 64", radix));
		
		// used to store the results of the number
		StringBuilder results = new StringBuilder();
		
		// copy the value of the big integer
		java.math.BigInteger copy = new java.math.BigInteger(value.toByteArray());
		
		// get the value of zero to reuse
		java.math.BigInteger zero = new java.math.BigInteger("0");
		
		java.math.BigInteger base = java.math.BigInteger.valueOf(radix);
		
		// keep going until there is no more data
		while (copy.compareTo(zero) != 0){
			// append the results of modulusing by the radix
			results.append(getChar(copy.mod(base).intValue()));
			
			// divide out by the base
			copy = copy.divide(base);
		}
		return results.reverse().toString();
	}

	private char getChar(int value) {
		if (value < 10) {
			return (char) ('0' + value);
		}
		if (value < 36) {
			return (char) ('A' + value - 10);
		}
		if (value < 62) {
			return (char) ('a' + value - 36);
		}
		if (value == 62) {
			return '+';
		}
		if (value == 63) {
			return '/';
		}
		return '\0';
	}

}