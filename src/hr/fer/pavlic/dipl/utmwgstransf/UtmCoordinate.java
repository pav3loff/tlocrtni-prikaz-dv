package hr.fer.pavlic.dipl.utmwgstransf;

public class UtmCoordinate {
	
private String longZone;
	
	private String latZone;
	
	private double easting;
	
	private double northing;

	public UtmCoordinate(String longZone, String latZone, double easting, double northing) {
		super();
		this.longZone = longZone;
		this.latZone = latZone;
		this.easting = easting;
		this.northing = northing;
	}

	public String getLongZone() {
		return longZone;
	}

	public void setLongZone(String longZone) {
		this.longZone = longZone;
	}

	public String getLatZone() {
		return latZone;
	}

	public void setLatZone(String latZone) {
		this.latZone = latZone;
	}

	public double getEasting() {
		return easting;
	}

	public void setEasting(double easting) {
		this.easting = easting;
	}

	public double getNorthing() {
		return northing;
	}

	public void setNorthing(double northing) {
		this.northing = northing;
	}
	
	@Override
	public String toString() {
		return longZone + " " + latZone + " " + easting + " " + northing;
	}

}
