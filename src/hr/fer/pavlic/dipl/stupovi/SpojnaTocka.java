package hr.fer.pavlic.dipl.stupovi;

public class SpojnaTocka {
	
	private double x;
	private double y;
	private double z;
	
	public SpojnaTocka(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof SpojnaTocka)) {
			return false;
		}
		
		SpojnaTocka other = (SpojnaTocka) obj;
		
		return this.x == other.x && this.y == other.y && this.z == other.z;
	}

}
