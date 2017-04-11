package gmath.order;

public interface Ordering<O extends Ordering<O>> {
	public boolean greaterThan(O compare);
	public boolean lessThan(O compare);
	
}
