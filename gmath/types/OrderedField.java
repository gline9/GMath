package gmath.types;

import gmath.order.Ordering;

public abstract class OrderedField<F extends OrderedField<F>> extends Field<F> implements Ordering<F> {
	protected OrderedField(Class<F> clazz) {
		super(clazz);
	}
	public final boolean greaterThanOrEqual(F compare){
		return this.equals(compare) || this.greaterThan(compare);
	}
	public final boolean lessThanOrEqual(F compare){
		return this.equals(compare) || this.lessThan(compare);
	}
}
