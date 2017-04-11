package gmath.functions;

public class CosFunction extends RFunction{

	@Override
	public Double evaluate(Double input) {
		return Math.cos(input);
	}

	@Override
	public RFunction derivate() {
		return new SinFunction().times(-1);
	}

}
