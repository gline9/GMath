package gmath.functions;

public class ExponentialFunction extends RFunction{

	@Override
	public Double evaluate(Double input) {
		return Math.exp(input);
	}

	@Override
	public RFunction derivate() {
		return this;
	}
	
	
}
