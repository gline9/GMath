package gmath.functions;

public abstract class RFunction extends Function<Double, Double> {

	@Override
	public abstract Double evaluate(Double input);
	
	public abstract RFunction derivate();
	
	public final RFunction compose(RFunction function){
		RFunction sup = this;
		return new RFunction(){

			@Override
			public Double evaluate(Double input) {
				return sup.evaluate(function.evaluate(input));
			}

			@Override
			public RFunction derivate() {
				RFunction front = function.derivate();
				return new ProductFunction(front, sup.derivate().compose(function));
			}
			
		};
	}
	
	public final RFunction times(double value){
		RFunction sup = this;
		return new RFunction(){

			@Override
			public Double evaluate(Double input) {
				return value * sup.evaluate(input);
			}

			@Override
			public RFunction derivate() {
				if (value == 0) return new ZeroFunction();
				return sup.derivate().times(value);
			}
			
		};
	}

}
