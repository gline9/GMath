package gmath.sets;

/**
 * set class that is the most basic a set can be
 * 
 * @author Gavin
 *
 * @param <R>
 *            element type that the set contians.
 */
public abstract class Set<R> {
	public abstract boolean contains(R x);
}
