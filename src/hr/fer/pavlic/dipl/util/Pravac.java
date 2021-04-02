package hr.fer.pavlic.dipl.util;

public class Pravac {
	
	private double x1;
	private double y1;
	private double x2;
	private double y2;
	
	public Pravac(double x1, double y1, double x2, double y2) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public double getX1() {
		return x1;
	}

	public void setX1(double x1) {
		this.x1 = x1;
	}

	public double getY1() {
		return y1;
	}

	public void setY1(double y1) {
		this.y1 = y1;
	}

	public double getX2() {
		return x2;
	}

	public void setX2(double x2) {
		this.x2 = x2;
	}

	public double getY2() {
		return y2;
	}

	public void setY2(double y2) {
		this.y2 = y2;
	}
	
	public double nadiKutIzmeduPravca(Pravac p2) {
		// Pravac p2 treba biti horizontalan (ali mu pocetna tocka mora biti jednaka pocetnoj tocki trenutnog pravca p1
		double kut1 = Math.atan2(this.getY1() - this.getY2(), this.getX1() - this.getX2());
		double kut2 = Math.atan2(p2.getY1() - p2.getY2(), p2.getX1() - p2.getX2());
		
		double rezultat = Math.toDegrees(kut1) - Math.toDegrees(kut2);
		
		if(rezultat + 360 < 360) {
			return 360 + rezultat;
		}
		
		return rezultat;
	}

}
