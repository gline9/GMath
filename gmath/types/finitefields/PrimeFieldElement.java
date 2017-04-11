package gmath.types.finitefields;

import gmath.numbertheory.ModuloArithmetic;
import gmath.types.Field;

public class PrimeFieldElement extends Field<PrimeFieldElement> {

	// value for the finite field number
	private final int value;

	// size of the finite field the element is in.
	private final int modulus;

	/**
	 * creates a new finite field element with the value as its value and the
	 * modulus as the size of the field it is in. The modulus has to be prime
	 * for this class to work, but the only classes that can create instances
	 * should already be checking this fact.
	 * 
	 * @param value
	 *            value for the element
	 * @param modulus
	 *            size of field element is in.
	 */
	protected PrimeFieldElement(int value, int modulus) {
		super(PrimeFieldElement.class);

		this.value = Math.floorMod(value, modulus);
		this.modulus = modulus;
	}

	@Override
	public PrimeFieldElement invert() {
		// check for special modulus = 2
		if (modulus == 2)
			return new PrimeFieldElement(value, modulus);
		return new PrimeFieldElement((int) ModuloArithmetic.moduloInverse(value, modulus), modulus);
	}

	@Override
	public PrimeFieldElement IDENTITY() {
		return new PrimeFieldElement(1, modulus);
	}

	@Override
	public PrimeFieldElement add(PrimeFieldElement add) {

		if (modulus != add.modulus)
			throw new IllegalArgumentException("you can only add two field elements if they have the same modulus!");

		return new PrimeFieldElement(this.value + add.value % modulus, modulus);
	}

	@Override
	public PrimeFieldElement multiply(PrimeFieldElement mult) {
		if (modulus != mult.modulus)
			throw new IllegalArgumentException("you can only add two field elements if they have the same modulus!");
		return new PrimeFieldElement((int) (((long) this.value * mult.value) % modulus), modulus);
	}

	@Override
	public PrimeFieldElement negate() {
		return new PrimeFieldElement(modulus - value, modulus);
	}

	@Override
	public PrimeFieldElement ZERO() {
		return new PrimeFieldElement(0, modulus);
	}

	@Override
	public boolean equals(PrimeFieldElement compare) {
		if (modulus != compare.modulus)
			return false;
		if (value != compare.value)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int results = value;
		results = results * prime + modulus;
		return results;
	}

	@Override
	public String toString() {
		return value + "";
	}

}
