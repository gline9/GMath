package gmath.functions;

public class RationalFunction extends RFunction {

	private final RFunction numerator;
	private final RFunction denominator;

	public RationalFunction(RFunction numerator, RFunction denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}

	@Override
	public Double evaluate(Double input) {
		return numerator.evaluate(input) / denominator.evaluate(input);
	}

	@Override
	public RFunction derivate() {
		RFunction ddenominator = denominator.derivate();
		RFunction dnumerator = numerator.derivate();

		return new RationalFunction(
				new SumFunction(new ProductFunction(dnumerator, denominator),
						new PowerFunction(-1, 1).compose(new ProductFunction(ddenominator, numerator))),
				new PowerFunction(1, 2).compose(denominator));
	}
}
