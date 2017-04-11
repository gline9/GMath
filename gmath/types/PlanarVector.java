package gmath.types;

public class PlanarVector extends Ring<PlanarVector>{
	private final double xPos;
	private final double yPos;
	public PlanarVector(double xPos, double yPos) {
		super(PlanarVector.class);
		this.xPos = xPos;
		this.yPos = yPos;
	}
	public double getXPos() {
		return xPos;
	}
	public double getYPos() {
		return yPos;
	}
	public PlanarVector add(PlanarVector add) {
		return new PlanarVector(this.getXPos() + add.getXPos(), this.getYPos() + add.getYPos());
	}
	public PlanarVector negate() {
		return new PlanarVector(-1 * this.getXPos(), -1 * this.getYPos());
	}
	public double direction(){
		if (this.getXPos() == 0){
			if (this.getYPos() < 0){
				return -Math.PI/2;
			}else{
				return Math.PI/2;
			}
		}else{
			double factor = 0;
			if (this.getXPos() < 0){
				factor = Math.PI;
			}
			if (this.getYPos() > 0){
				return Math.atan(this.getYPos() / this.getXPos()) + factor;
			}else{
				return Math.atan(this.getYPos() / this.getXPos()) - factor;
			}
		}
	}
	public double dotProduct(PlanarVector prod) {
		return this.getXPos() * prod.getXPos() + this.getYPos() * prod.getYPos();
	}
	public double magnitude(){
		return Math.sqrt(this.dotProduct(this));
	}
	public PlanarVector normalize(){
		double magnitude = magnitude();
		double scale = 1/magnitude;
		return this.scale(scale);
	}
	public PlanarVector perpendicular() {
		return new PlanarVector(-1 * this.getYPos(), this.getXPos());
	}
	public PlanarVector normalPerpendicular() {
		return this.perpendicular().normalize();
	}
	public PlanarVector scale(double scalar) {
		return new PlanarVector(scalar * this.getXPos(), scalar * this.getYPos());
	}
	public boolean equals(PlanarVector vec) {
		return vec.getXPos() == this.getXPos() && vec.getYPos() == this.getYPos();
	}
	public int hashCode() {
		return (int)(this.getXPos() * 100 + this.getYPos());
	}
	public String toString() {
		return String.format("%.2f", this.getXPos()) + "," + String.format("%.2f", this.getYPos());
	}
	@Override
	public PlanarVector multiply(PlanarVector mult) {
		return new PlanarVector(this.getXPos() * mult.getXPos(), this.getYPos() * mult.getXPos());
	}
	@Override
	public PlanarVector ZERO() {
		return new PlanarVector(0, 0);
	}
	@Override
	public PlanarVector IDENTITY() {
		return new PlanarVector(1, 1);
	}
}
