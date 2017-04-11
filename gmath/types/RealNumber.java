package gmath.types;

public class RealNumber extends OrderedField<RealNumber>{
	private final double value;
	public RealNumber(double value) {
		super(RealNumber.class);
		this.value = value;
	}
	@Override
	public boolean lessThan(RealNumber compare) {
		return this.getValue() < compare.getValue();
	}

	@Override
	public boolean greaterThan(RealNumber compare) {
		return this.getValue() > compare.getValue();
	}

	@Override
	public RealNumber invert() {
		return new RealNumber(1D / this.getValue());
	}

	@Override
	public RealNumber IDENTITY() {
		return new RealNumber(1);
	}

	@Override
	public RealNumber add(RealNumber add) {
		return new RealNumber(this.getValue() + add.getValue());
	}

	@Override
	public RealNumber multiply(RealNumber mult) {
		return new RealNumber(this.getValue() * mult.getValue());
	}

	@Override
	public RealNumber negate() {
		return new RealNumber(-1 * this.getValue());
	}

	@Override
	public RealNumber ZERO() {
		return new RealNumber(0);
	}
	public double getValue() {
		return this.value;
	}
	@Override
	public boolean equals(RealNumber compare) {
		return this.getValue() == compare.getValue();
	}

	@Override
	public String toString() {
		return String.valueOf(this.getValue());
	}
	@Override
	public int hashCode() {
		return (int) this.value;
	}

}
