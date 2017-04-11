package gmath.types;

public class Integer extends OrderedRing<Integer>{
	private final int value;
	public Integer(int value) {
		super(Integer.class);
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	@Override
	public Integer add(Integer add) {
		return new Integer(getValue() + add.getValue());
	}

	@Override
	public Integer multiply(Integer mult) {
		return new Integer(getValue() * mult.getValue());
	}

	@Override
	public Integer negate() {
		return this.multiply(new Integer(-1));
	}
	@Override
	public boolean equals(Integer compare) {
		return compare.getValue() == this.getValue();
	}
	@Override
	public boolean lessThan(Integer compare) {
		return this.getValue() < compare.getValue();
	}
	@Override
	public boolean greaterThan(Integer compare) {
		return this.getValue() > compare.getValue();
	}
	@Override
	public String toString() {
		return String.valueOf(this.getValue());
	}
	@Override
	public Integer ZERO() {
		return new Integer(0);
	}
	@Override
	public Integer IDENTITY() {
		return new Integer(1);
	}
	@Override
	public int hashCode() {
		return this.value;
	}

}
