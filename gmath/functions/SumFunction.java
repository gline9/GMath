package gmath.functions;

public class SumFunction extends RFunction{
	
	private final RFunction[] functions;
	public SumFunction(RFunction...functions){
		this.functions = functions;
	}
	
	@Override
	public Double evaluate(Double input) {
		double current = 0;
		for (RFunction function : functions){
			current += function.evaluate(input);
		}
		return current;
	}

	@Override
	public RFunction derivate() {
		RFunction[] derivatives = new RFunction[functions.length];
		for (int i = 0; i < functions.length; i++){
			derivatives[i] = functions[i].derivate();
		}
		return new SumFunction(derivatives);
	}

}
