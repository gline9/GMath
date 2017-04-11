package gmath.types;

public abstract class Field<F extends Field<F>> extends Ring<F> {
	protected Field(Class<F> clazz) {
		super(clazz);
	}
	public abstract F invert();
	public final F divide(F divisor){
		return this.multiply(divisor.invert());
	}
}
