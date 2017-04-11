package gmath.types;

import java.text.DecimalFormat;

public class ComplexNumber extends Field<ComplexNumber>{
	private final double realValue;
	private final double imaginaryValue;
	public ComplexNumber(double real, double imaginary) {
		super(ComplexNumber.class);
		realValue = real;
		imaginaryValue = imaginary;
	}
	public ComplexNumber() {
		this(0, 0);
	}
	@Override
	public ComplexNumber invert() {
		double divisor = Math.pow(this.getReal(), 2) + Math.pow(this.getImaginary(), 2);
		return new ComplexNumber(this.getReal() / divisor, -1 * this.getImaginary() / divisor);
	}

	@Override
	public ComplexNumber IDENTITY() {
		return new ComplexNumber(1, 0);
	}

	@Override
	public ComplexNumber add(ComplexNumber add) {
		return new ComplexNumber(this.getReal() + add.getReal(), this.getImaginary() + add.getImaginary());
	}

	@Override
	public ComplexNumber multiply(ComplexNumber mult) {
		return new ComplexNumber(this.getReal() * mult.getReal() - this.getImaginary() * mult.getImaginary(), this.getReal() * mult.getImaginary() + this.getImaginary() * mult.getReal());
	}

	@Override
	public ComplexNumber negate() {
		return new ComplexNumber(-1 * this.getReal(), -1 * this.getImaginary());
	}

	@Override
	public ComplexNumber ZERO() {
		return new ComplexNumber(0, 0);
	}
	
	public double getReal() {
		return realValue;
	}
	
	public double getImaginary() {
		return imaginaryValue;
	}
	
	@Override
	public boolean equals(ComplexNumber compare) {
		return this.getReal() == compare.getReal() && this.getImaginary() == compare.getImaginary();
	}

	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("###.####");
		return df.format(getReal()) + "+" + df.format(getImaginary()) + "i";
	}
	@Override
	public int hashCode() {
		return (int)this.realValue + 100 * (int) this.imaginaryValue;
	}

}
