package gmath.types;

public abstract class Ring<R extends Ring<R>> {
	private final Class<R> clazz;

	protected Ring(Class<R> clazz) {
		this.clazz = clazz;
	}
	
	protected Ring() {
		this.clazz = null;
	}

	public abstract R add(R add);

	public abstract R multiply(R mult);

	public abstract R negate();

	public final R subtract(R sub) {
		return this.add(sub.negate());
	}

	@SuppressWarnings("unchecked")
	public final R square() {
		return this.multiply((R) this);
	}

	/**
	 * scales the current ring number by the given number by repeated addition
	 * with fast multiplication method. This method can be overriden to add
	 * faster operations
	 * 
	 * @param scalar
	 *            number to scale by
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public R scale(int scalar) {
		// make two copies of the original.
		R num = (R) this;
		R original = num;

		// get the binary representation of the power to know when to add and
		// when to multiply.
		String binaryTimes = java.lang.Integer.toBinaryString(scalar);
		binaryTimes = binaryTimes.substring(1, binaryTimes.length());

		// each time multiply the previous number by 2, if there is a 1 also add
		// the original to it.
		for (char c : binaryTimes.toCharArray()) {
			num = num.add(num);
			switch (c) {
			case '1':
				num = num.add(original);
			}
		}

		// return the resulting computation
		return num;
	}

	/**
	 * raises the current ring number to the given scalar power by repeated
	 * multiplication using fast exponentiation.
	 * 
	 * @param bigInteger
	 *            power to raise current number to
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final R pow(int bigInteger) {
		// make two copies of the current number, one for storing the results
		// and the other for multiplying each time.
		R copy = (R) this;
		R original = copy;

		// get the binary representation of the power to know when to multiply
		// and when to square.
		String binaryPower = java.lang.Integer.toBinaryString(bigInteger);
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

	public abstract R ZERO();
	public abstract R IDENTITY();

	public abstract boolean equals(R compare);

	@SuppressWarnings("unchecked")
	public final boolean equals(Object obj) {
		try {
			if (clazz == null) {
				return this.equals((R) obj);
			}
			return this.equals(clazz.cast(obj));
		} catch (Exception e) {
			return false;
		}
	}

	public abstract int hashCode();

	public abstract String toString();
}
