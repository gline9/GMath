package gmath.functions;

public class SinFunction extends RFunction{

	@Override
	public Double evaluate(Double input) {
		return Math.sin(input);
	}

	@Override
	public RFunction derivate() {
		return new CosFunction();
	}

}
