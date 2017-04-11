package gmath.functions;

public class ZeroFunction extends RFunction{

	@Override
	public Double evaluate(Double input) {
		return new Double(0);
	}

	@Override
	public RFunction derivate() {
		return this;
	}

}
