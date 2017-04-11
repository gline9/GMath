package gmath.types;

import java.util.Arrays;

public class NVector<R extends Ring<R>> extends Ring<NVector<R>> {

	private final R[] entries;

	private final int length;

	private final Class<?> clazz;

	@SafeVarargs
	public NVector(R... entries) {
		// make sure the array isn't empty
		if (entries == null || entries.length == 0)
			throw new IllegalArgumentException("You can't have a vector of length 0!");

		// copy the entry array.
		this.entries = Arrays.copyOf(entries, entries.length);

		// set the length of the vector
		this.length = entries.length;

		// set the class of the vector
		this.clazz = entries.getClass().getComponentType();
	}

	/**
	 * private constructor so the arguments don't need to be checked.
	 * 
	 * @param entries
	 * @param length
	 */
	private NVector(R[] entries, int length) {
		this.entries = entries;
		this.length = length;
		this.clazz = entries.getClass().getComponentType();
	}

	@Override
	public NVector<R> add(NVector<R> add) {

		// make sure they are the same size
		if (length != add.length)
			throw new IllegalArgumentException("Vectors need to have the same length to add them!");

		// simply add each element
		R[] copy = Arrays.copyOf(entries, length);
		for (int i = 0; i < length; i++) {
			copy[i] = copy[i].add(add.entries[i]);
		}
		return new NVector<>(copy, length);
	}

	@Override
	public NVector<R> multiply(NVector<R> mult) {

		// make sure they are the same size
		if (length != mult.length)
			throw new IllegalArgumentException("Vectors need to have the same length to multiply them!");

		// simply multiply each element
		R[] copy = Arrays.copyOf(entries, length);
		for (int i = 0; i < length; i++) {
			copy[i] = copy[i].multiply(mult.entries[i]);
		}
		return new NVector<>(copy, length);
	}

	/**
	 * takes the dot product of two vectors that are the same size.
	 * 
	 * @param mult
	 *            vector to take the dot product with
	 * @return dot product of the two vectors.
	 */
	public R dotProduct(NVector<R> mult) {
		// make sure they both have the same size
		if (length != mult.length)
			throw new IllegalArgumentException("Vectors need to have the same length to take their dot product!");

		// store the results in a single variable
		R results = entries[0].ZERO();

		// add together each of the multiplications of the products
		for (int i = 0; i < length; i++) {
			results = results.add(entries[i].multiply(mult.entries[i]));
		}

		return results;
	}

	@Override
	public NVector<R> negate() {

		// just negate each element
		R[] copy = Arrays.copyOf(entries, length);
		for (int i = 0; i < length; i++) {
			copy[i] = copy[i].negate();
		}
		return new NVector<>(copy, length);
	}

	@Override
	public NVector<R> ZERO() {
		// returns vector of the same length filled with the zero element
		R[] copy = Arrays.copyOf(entries, length);
		Arrays.fill(copy, copy[0].ZERO());
		return new NVector<>(copy, length);
	}

	@Override
	public NVector<R> IDENTITY() {
		// returns vector of the same length filled with the identity element
		R[] copy = Arrays.copyOf(entries, length);
		Arrays.fill(copy, copy[0].IDENTITY());
		return new NVector<>(copy, length);
	}

	@Override
	public boolean equals(NVector<R> compare) {
		// check that the classes are the same
		if (!clazz.equals(compare.clazz))
			return false;

		// check that the sizes are the same
		if (length != compare.length)
			return false;

		// check that the arrays are the same
		if (!Arrays.equals(entries, compare.entries))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		// return the arrays hash code
		return Arrays.hashCode(entries);
	}

	@Override
	public String toString() {
		// return the arrays representation
		return Arrays.toString(entries);
	}

}
