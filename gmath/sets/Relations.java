package gmath.sets;

/**
 * utility class to get a lot of the common types of relations
 * 
 * @author Gavin
 *
 */
public final class Relations {
	// private so class can't be instantiated
	private Relations() {}

	public static Relation<Integer> greaterThanInt() {
		return new Relation<Integer>() {
			@Override
			public boolean isTrue(Integer a, Integer b) {
				return a > b;
			}
		};
	}

	public static Relation<Integer> lessThanInt() {
		return new Relation<Integer>() {

			@Override
			public boolean isTrue(Integer a, Integer b) {
				return a < b;
			}

		};
	}

	public static Relation<Integer> equalToInt() {
		return new Relation<Integer>() {

			@Override
			public boolean isTrue(Integer a, Integer b) {
				return a == b;
			}

		};
	}

	public static <R> Relation<R> equivalence() {
		return new Relation<R>() {

			@Override
			public boolean isTrue(R a, R b) {
				return a.equals(b);
			}

		};
	}

	public static <R> Relation<R> hashGreaterThan() {
		return new Relation<R>() {

			@Override
			public boolean isTrue(R a, R b) {
				return a.hashCode() > b.hashCode();
			}

		};
	}

	public static <R> Relation<R> hashLessThan() {
		return new Relation<R>() {

			@Override
			public boolean isTrue(R a, R b) {
				return a.hashCode() < b.hashCode();
			}

		};
	}

	public static <R> Relation<R> union(Relation<R> a, Relation<R> b) {
		return new Relation<R>() {

			@Override
			public boolean isTrue(R x, R y) {
				return a.isTrue(x, y) || b.isTrue(x, y);
			}

		};
	}

	public static <R> Relation<R> intersection(Relation<R> a, Relation<R> b) {
		return new Relation<R>() {

			@Override
			public boolean isTrue(R x, R y) {
				return a.isTrue(x, y) && b.isTrue(x, y);
			}

		};
	}

	public static <R> Relation<R> negate(Relation<R> a) {
		return new Relation<R>() {

			@Override
			public boolean isTrue(R x, R y) {
				return !a.isTrue(x, y);
			}

		};
	}
}
