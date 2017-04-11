package gmath.types;

public class Rational extends OrderedField<Rational>{
	private final int numerator;
	private final int denominator;
	public Rational(int numerator, int denomenator) {
		super(Rational.class);
		int[] simplified = simplify(numerator, denomenator);
		this.numerator = simplified[0];
		this.denominator = simplified[1];
	}
	public int getNumerator() {
		return numerator;
	}
	public int getDenominator() {
		return denominator;
	}
	@Override
	public Rational invert() {
		return new Rational(denominator, numerator);
	}

	@Override
	public Rational add(Rational add) {
		return new Rational(this.getNumerator() * add.getDenominator() + this.getDenominator() * add.getNumerator(), this.getDenominator() * add.getDenominator());
	}

	@Override
	public Rational multiply(Rational mult) {
		return new Rational(this.getNumerator() * mult.getNumerator(), this.getDenominator() * mult.getDenominator());
	}

	@Override
	public Rational negate() {
		return new Rational(-1 * this.getNumerator(), this.getDenominator());
	}
	@Override
	public Rational IDENTITY() {
		return new Rational(1, 1);
	}
	@Override
	public Rational ZERO() {
		return new Rational(0, 1);
	}
	@Override
	public boolean equals(Rational compare) {
		return this.getNumerator() == compare.getNumerator() && this.getDenominator() == compare.getDenominator();
	}
	@Override
	public boolean lessThan(Rational compare) {
		return this.getDecimal() < compare.getDecimal();
	}
	@Override
	public boolean greaterThan(Rational compare) {
		return this.getDecimal() > compare.getDecimal();
	}
	public double getDecimal() {
		return (double)this.getNumerator() / this.getDenominator();
	}
	@Override
	public String toString() {
		if (this.getDenominator() == 1) {
			return String.valueOf(this.getNumerator());
		}else if (this.getNumerator() == 0) {
			return "0";
		}
		return String.valueOf(this.getNumerator()) + "/" + String.valueOf(this.getDenominator());
	}
	private int[] simplify(int num, int den) {
	    if (num != 0){
	    	int common = gcd(Math.abs(num), Math.abs(den));
	    	num = num / common;
	    	den = den / common;
	    	if (den < 0) {
	    		den *= -1;
	    		num *= -1;
	    	}
	    }else {
	    	den = 1;
	    }
	    int[] results = {num, den};
	    return results;
	}
	private int gcd(int num1, int num2){
		while (num1 != num2) {
			if (num1 > num2) {
				num1 = num1 - num2;
		 	}else {
		 		num2 = num2 - num1;
			}
		}
		return num1;
	}
	@Override
	public int hashCode() {
		return this.numerator + 100 * this.denominator;
	}
}
