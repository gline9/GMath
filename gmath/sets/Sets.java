package gmath.sets;

import java.util.ArrayList;
import java.util.Collection;

/**
 * constructor for some common sets
 * 
 * @author Gavin
 *
 */
public final class Sets {
	// private so it can't be instantiated
	private Sets() {}

	/**
	 * creates a set from the union of two sets that are of the same type
	 * 
	 * @param a
	 *            first set in union
	 * @param b
	 *            second set in union
	 * @return set that is the union of the two
	 */
	public static <R> Set<R> union(Set<R> a, Set<R> b) {
		return new Set<R>() {

			@Override
			public boolean contains(R x) {
				return a.contains(x) || b.contains(x);
			}
		};
	}

	/**
	 * creates a set from the union of two sets that are not of the same type
	 * 
	 * @param a
	 *            first set in union
	 * @param b
	 *            second set in union
	 * @param flag
	 *            pointless flag to differentiate from {@link #union(Set, Set)
	 *            union(Set<R> a, Set<R> b)} .
	 * @return a set containing the union of the two
	 */
	public static <R, S> Set<Object> union(Set<R> a, Set<S> b, boolean flag) {
		return new Set<Object>() {

			@SuppressWarnings("unchecked")
			@Override
			public boolean contains(Object x) {
				boolean results = false;
				try {
					results |= a.contains((R) x);
				} catch (ClassCastException e) {}
				try {
					results |= b.contains((S) x);
				} catch (ClassCastException e) {}

				return results;
			}

		};
	}

	/**
	 * creates the complement of the given set
	 * 
	 * @param a
	 *            set to complement
	 * @return complement of the set
	 */
	public static <R> Set<R> complement(Set<R> a) {
		return new Set<R>() {

			@Override
			public boolean contains(R x) {
				return !a.contains(x);
			}

		};
	}

	/**
	 * creates a set from the intersection of two sets that are of the same
	 * type. No need to provide different type intersection as that would be the
	 * same as the empty set.
	 * 
	 * @param a
	 *            first set in intersection
	 * @param b
	 *            second set in intersection
	 * @return set that is the intersection of the two
	 */
	public static <R> Set<R> intersection(Set<R> a, Set<R> b) {
		return new Set<R>() {

			@Override
			public boolean contains(R x) {
				return a.contains(x) && b.contains(x);
			}
		};
	}

	/**
	 * 
	 * @return the empty set for the given domain
	 */
	public static <R> Set<R> emptySet() {
		return new Set<R>() {

			@Override
			public boolean contains(R x) {
				return false;
			}

		};
	}

	/**
	 * converts a collection into a set containing its elements. This can be
	 * used for dynamic sets but may have unintended consequences.
	 * 
	 * @param collection
	 *            collection to convert
	 * @return set containing all of the elements of the collection
	 */
	public static <R> Set<R> convert(Collection<R> collection) {
		return new Set<R>() {

			@Override
			public boolean contains(R x) {
				return collection.contains(x);
			}

		};
	}

	/**
	 * creates the Cartesian product of a list of sets. This will allow for you
	 * to add coordinates with already created sets of elements.
	 * 
	 * @param sets
	 *            list of set to take Cartesian product in order
	 * @return Cartesian product of the sets
	 */
	@SafeVarargs
	public static <R> Set<Tuple<R>> cartesianProduct(Set<R>... sets) {
		return new Set<Tuple<R>>() {

			@Override
			public boolean contains(Tuple<R> x) {
				// make sure the tuple is the same size as the array of sets
				if (x.size() != sets.length)
					return false;
				
				// starting index for going through x
				int i = 0;

				// iterate through the sets
				for (Set<R> set : sets) {
					// if the current set doesn't contain x return false
					if (!set.contains(x.getElement(i++)))
						return false;
				}

				// if all coordinates match their appropriate sets return true
				return true;
			}

		};
	}

	/**
	 * constructs the Cartesian product of a set with a tuple and a normal set.
	 * This will just append the previous Cartesian product to the new element,
	 * i.e. (a, b, c) x d = (a, b, c, d)
	 * 
	 * @param tuple set of tuples in the front
	 * @param set set for the elements in the back
	 * @return the Cartesian products of the two sets
	 */
	public static <R> Set<Tuple<R>> cartesianProduct(Set<Tuple<R>> tuple, Set<R> set) {
		return new Set<Tuple<R>>() {

			@Override
			public boolean contains(Tuple<R> x) {
				// grab the first elements of x
				ArrayList<R> data = new ArrayList<R>(x.size() - 1);

				for (int i = 0; i < x.size() - 1; i++) {
					data.add(x.getElement(i));
				}

				// create a new tuple from the data
				Tuple<R> newTuple = new Tuple<>(data);

				// if the new tuple isn't in tuple return false
				if (!tuple.contains(newTuple))
					return false;

				// if the last element of x isn't in set return false
				if (!set.contains(x.getElement(x.size() - 1)))
					return false;

				// otherwise return true
				return true;
			}

		};
	}

}
