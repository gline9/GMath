package gmath.types;

public class Quaternion extends Field<Quaternion> {
	private final double real;
	private final double i;
	private final double j;
	private final double k;

	public Quaternion(double real, double i, double j, double k) {
		super(Quaternion.class);
		this.real = real;
		this.i = i;
		this.j = j;
		this.k = k;
	}

	public double getReal() {
		return real;
	}

	public double getI() {
		return i;
	}

	public double getJ() {
		return j;
	}

	public double getK() {
		return k;
	}

	@Override
	public Quaternion invert() {
		return conjugate().scale(1 / Math.pow(magnitude(), 2));
	}

	@Override
	public Quaternion IDENTITY() {
		return new Quaternion(1, 0, 0, 0);
	}

	@Override
	public Quaternion add(Quaternion add) {
		return new Quaternion(real + add.real, i + add.i, j + add.j, k + add.k);
	}

	@Override
	public Quaternion multiply(Quaternion mult) {
		return new Quaternion(real * mult.real - i * mult.i - j * mult.j - k * mult.k,
				real * mult.i + mult.real * i + j * mult.k - k * mult.j,
				real * mult.j - i * mult.k + j * mult.real + k * mult.i,
				real * mult.k + i * mult.j - j * mult.i + k * mult.real);
	}

	@Override
	public Quaternion negate() {
		return new Quaternion(-1 * real, -1 * i, -1 * j, -1 * k);
	}

	public double magnitude() {
		return Math.sqrt(Math.pow(real, 2) + Math.pow(i, 2) + Math.pow(j, 2) + Math.pow(k, 2));
	}

	public Quaternion normalize() {
		return scale(1 / magnitude());
	}

	public Quaternion conjugate() {
		return new Quaternion(real, -1 * i, -1 * j, -1 * k);
	}

	public Quaternion scale(double scalar) {
		return new Quaternion(real * scalar, i * scalar, j * scalar, k * scalar);
	}

	@Override
	public Quaternion ZERO() {
		return new Quaternion(0, 0, 0, 0);
	}

	@Override
	public boolean equals(Quaternion compare) {
		return (real == compare.real && i == compare.i && j == compare.j && k == compare.k);
	}

	@Override
	public int hashCode() {
		return (int) (real * 1234567 + i * 1000 + j * 1000000 + k * 1000000000);
	}

	@Override
	public String toString() {
		String iString = (i == 0 ? "" : ((i > 0 ? " + " : " - ") + i + "i"));
		String jString = (j == 0 ? "" : ((j > 0 ? " + " : " - ") + j + "j"));
		String kString = (k == 0 ? "" : ((k > 0 ? " + " : " - ") + k + "k"));
		String rString = (real == 0 ? "" : real + "");
		if (iString.equals("") && jString.equals("") && kString.equals("") && rString.equals("")) {
			return "0";
		}
		return rString + iString + jString + kString;
	}

}
