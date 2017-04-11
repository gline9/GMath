package gmath.sets;

public abstract class Relation<R> extends Set<Tuple<R>> {
	/**
	 * use this function to determine whether a pair of elements is in a
	 * relation. This can be for example with (a,b) el of > <=> a > b
	 * 
	 * @param a
	 *            first element to check
	 * @param b
	 *            second element to check
	 * @return if the pair is in the relation
	 */
	public abstract boolean isTrue(R a, R b);

	@Override
	public final boolean contains(Tuple<R> x) {
		if (x.size() != 2)
			return false;

		return isTrue(x.getElement(0), x.getElement(1));
	}
}
