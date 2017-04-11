package gmath.types.finitefields;

import gmath.types.Field;
import gmath.types.FieldPolynomial;

/**
 * element class for the finite field class. This class is what is created so
 * operations can be done on the elements of the field. All of the computation
 * is handled in the {@link gmath.types.finitefields.FiniteField FiniteField}
 * class however.
 * 
 * @author Gavin
 *
 */
public class FiniteFieldElement extends Field<FiniteFieldElement> {

	private final FiniteField field;

	private final FieldPolynomial<PrimeFieldElement> value;

	/**
	 * creates a new element from the field that it is enclosed within and the
	 * value it takes.
	 * 
	 * @param field
	 *            field the element is in
	 * @param value
	 *            value of the element.
	 */
	protected FiniteFieldElement(FiniteField field, FieldPolynomial<PrimeFieldElement> value) {
		super(FiniteFieldElement.class);
		this.field = field;
		this.value = value;
	}

	@Override
	public FiniteFieldElement invert() {
		// check for zero
		if (this.value.equals(value.ZERO()))
			throw new ArithmeticException("you can't divide by 0!");
		
		return new FiniteFieldElement(field,
				this.value.moduloInverse(field.getCharacteristicPolynomial()).mod(field.getCharacteristicPolynomial()));
	}

	@Override
	public FiniteFieldElement add(FiniteFieldElement add) {
		// make sure the two polynomials are in the same field
		if (this.field != add.field)
			throw new IllegalArgumentException("To add two field elements you need to be in the same field!");
		// simply add the two polynomials modulo the characteristic polynomial.
		FieldPolynomial<PrimeFieldElement> results = this.value.add(add.value).mod(field.getCharacteristicPolynomial());
		return new FiniteFieldElement(field, results);
	}

	@Override
	public FiniteFieldElement multiply(FiniteFieldElement mult) {
		// make sure the two polynomials are in the same field
		if (this.field != mult.field)
			throw new IllegalArgumentException("to add two field elements you need to be in the same field!");

		// simply multiply the two polynomials modulo the charactersitic
		// polynomial.
		FieldPolynomial<PrimeFieldElement> results = this.value.multiply(mult.value)
				.mod(field.getCharacteristicPolynomial());

		return new FiniteFieldElement(field, results);
	}

	@Override
	public FiniteFieldElement negate() {

		// just negate the value polynomial.
		FieldPolynomial<PrimeFieldElement> results = this.value.negate();
		return new FiniteFieldElement(field, results);
	}

	@Override
	public FiniteFieldElement ZERO() {

		// return a polynomial of all zeros
		return new FiniteFieldElement(field, value.ZERO());
	}

	@Override
	public FiniteFieldElement IDENTITY() {

		// return a polynomial that is 1
		return new FiniteFieldElement(field, value.IDENTITY());
	}

	@Override
	public boolean equals(FiniteFieldElement compare) {

		// check the fields for equality
		if (this.field != compare.field)
			return false;

		// check the values of their polynomials
		if (!this.value.equals(compare.value))
			return false;

		// if passed so far they are equal
		return true;
	}

	@Override
	public int hashCode() {
		// return the hashcode of the polynomial multiplied by the hashcode of
		// the characteristic times by a prime.
		return 31 * field.getCharacteristicPolynomial().hashCode() + value.hashCode();
	}

	@Override
	public String toString() {
		// just return the string representation of the polynomial
		return value.toString();
	}

}
