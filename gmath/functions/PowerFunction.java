package gmath.functions;

public class PowerFunction extends RFunction{
	
	private final double exponent;
	private final double coefficient;
	
	public PowerFunction(double coefficient, double exponent){
		this.exponent = exponent;
		this.coefficient = coefficient;
	}
	
	@Override
	public Double evaluate(Double input) {
		return coefficient * Math.pow(input, exponent);
	}

	@Override
	public RFunction derivate() {
		if (exponent == 0 || coefficient == 0) return new ZeroFunction();
		return new PowerFunction(coefficient * exponent, exponent - 1);
	}

}
