package gmath.functions;

public class ProductFunction extends RFunction{
	
	private final RFunction[] functions;
	
	public ProductFunction(RFunction... functions){
		this.functions = functions;
	}
	
	@Override
	public Double evaluate(Double input) {
		double results = 1;
		for (RFunction function : functions){
			results *= function.evaluate(input);
		}
		return results;
	}

	@Override
	public RFunction derivate() {
		ProductFunction[] productFunctions = new ProductFunction[functions.length];
		for (int i = 0; i < functions.length; i++){
			RFunction[] functionCopy = functions.clone();
			functionCopy[i] = functions[i].derivate();
			productFunctions[i] = new ProductFunction(functionCopy);
		}
		
		return new SumFunction(productFunctions);
	}
	
}
