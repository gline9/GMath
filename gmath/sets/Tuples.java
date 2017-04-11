package gmath.sets;

import java.util.LinkedList;

/**
 * util class for the Tuple class, used to construct common tuples quickly
 * 
 * @author Gavin
 *
 */
public final class Tuples {

	// private so you can't instantiate
	private Tuples() {}

	/**
	 * creates a new tuple from an array of data instead of a list
	 * 
	 * @param data
	 *            data to turn into tuple
	 * @return tuple with the given data
	 */
	@SafeVarargs
	public static <R> Tuple<R> construct(R... data) {
		LinkedList<R> list = new LinkedList<>();
		for (R element : data) {
			list.add(element);
		}

		return new Tuple<>(list);
	}

	/**
	 * creates a new tuple from two tuples concatenated together, i.e. (a, b) +
	 * (c, d) = (a, b, c, d)
	 * 
	 * @param a
	 *            beginning of concatenation
	 * @param b
	 *            end of concatenation
	 * @return two tuples concatenated together
	 */
	public static <R> Tuple<R> concat(Tuple<R> a, Tuple<R> b) {
		LinkedList<R> list = new LinkedList<>();
		for (R element : a) {
			list.add(element);
		}

		for (R element : b) {
			list.add(element);
		}

		return new Tuple<>(list);
	}
}
