package gmath.types.util;

import gmath.types.PlanarVector;

public final class PlanarVectors {
	private PlanarVectors(){}
	
	public static PlanarVector makeUnitVector(double angle){
		double xPos = Math.cos(angle);
		double yPos = Math.sin(angle);
		return new PlanarVector(xPos, yPos);
	}
	
	public static PlanarVector makeVector(double magnitude, double angle){
		return makeUnitVector(angle).scale(magnitude);
	}
	
}
