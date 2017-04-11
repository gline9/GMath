package gmath.types;

import gmath.order.Ordering;

public abstract class OrderedRing<R extends OrderedRing<R>> extends Ring<R> implements Ordering<R>{
	protected OrderedRing(Class<R> clazz) {
		super(clazz);
	}
	public final boolean greaterThanOrEqual(R compare){
		return this.equals(compare) || this.greaterThan(compare);
	}
	public final boolean lessThanOrEqual(R compare){
		return this.equals(compare) || this.lessThan(compare);
	}
}
